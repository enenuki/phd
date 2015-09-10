/*   1:    */ package org.apache.http.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import org.apache.http.HttpEntity;
/*   7:    */ import org.apache.http.annotation.NotThreadSafe;
/*   8:    */ import org.apache.http.entity.HttpEntityWrapper;
/*   9:    */ import org.apache.http.util.EntityUtils;
/*  10:    */ 
/*  11:    */ @NotThreadSafe
/*  12:    */ public class BasicManagedEntity
/*  13:    */   extends HttpEntityWrapper
/*  14:    */   implements ConnectionReleaseTrigger, EofSensorWatcher
/*  15:    */ {
/*  16:    */   protected ManagedClientConnection managedConn;
/*  17:    */   protected final boolean attemptReuse;
/*  18:    */   
/*  19:    */   public BasicManagedEntity(HttpEntity entity, ManagedClientConnection conn, boolean reuse)
/*  20:    */   {
/*  21: 71 */     super(entity);
/*  22: 73 */     if (conn == null) {
/*  23: 74 */       throw new IllegalArgumentException("Connection may not be null.");
/*  24:    */     }
/*  25: 77 */     this.managedConn = conn;
/*  26: 78 */     this.attemptReuse = reuse;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isRepeatable()
/*  30:    */   {
/*  31: 83 */     return false;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public InputStream getContent()
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 88 */     return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void ensureConsumed()
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 92 */     if (this.managedConn == null) {
/*  44: 93 */       return;
/*  45:    */     }
/*  46:    */     try
/*  47:    */     {
/*  48: 96 */       if (this.attemptReuse)
/*  49:    */       {
/*  50: 98 */         EntityUtils.consume(this.wrappedEntity);
/*  51: 99 */         this.managedConn.markReusable();
/*  52:    */       }
/*  53:    */     }
/*  54:    */     finally
/*  55:    */     {
/*  56:102 */       releaseManagedConnection();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   @Deprecated
/*  61:    */   public void consumeContent()
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:109 */     ensureConsumed();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void writeTo(OutputStream outstream)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:114 */     super.writeTo(outstream);
/*  71:115 */     ensureConsumed();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void releaseConnection()
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:119 */     ensureConsumed();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void abortConnection()
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:124 */     if (this.managedConn != null) {
/*  84:    */       try
/*  85:    */       {
/*  86:126 */         this.managedConn.abortConnection();
/*  87:    */       }
/*  88:    */       finally
/*  89:    */       {
/*  90:128 */         this.managedConn = null;
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean eofDetected(InputStream wrapped)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:135 */       if ((this.attemptReuse) && (this.managedConn != null))
/* 101:    */       {
/* 102:138 */         wrapped.close();
/* 103:139 */         this.managedConn.markReusable();
/* 104:    */       }
/* 105:    */     }
/* 106:    */     finally
/* 107:    */     {
/* 108:142 */       releaseManagedConnection();
/* 109:    */     }
/* 110:144 */     return false;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean streamClosed(InputStream wrapped)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:149 */       if ((this.attemptReuse) && (this.managedConn != null))
/* 119:    */       {
/* 120:152 */         wrapped.close();
/* 121:153 */         this.managedConn.markReusable();
/* 122:    */       }
/* 123:    */     }
/* 124:    */     finally
/* 125:    */     {
/* 126:156 */       releaseManagedConnection();
/* 127:    */     }
/* 128:158 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean streamAbort(InputStream wrapped)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:162 */     if (this.managedConn != null) {
/* 135:163 */       this.managedConn.abortConnection();
/* 136:    */     }
/* 137:165 */     return false;
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected void releaseManagedConnection()
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:179 */     if (this.managedConn != null) {
/* 144:    */       try
/* 145:    */       {
/* 146:181 */         this.managedConn.releaseConnection();
/* 147:    */       }
/* 148:    */       finally
/* 149:    */       {
/* 150:183 */         this.managedConn = null;
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.BasicManagedEntity
 * JD-Core Version:    0.7.0.1
 */