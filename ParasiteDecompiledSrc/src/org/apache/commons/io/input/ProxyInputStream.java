/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.FilterInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ 
/*   7:    */ public abstract class ProxyInputStream
/*   8:    */   extends FilterInputStream
/*   9:    */ {
/*  10:    */   public ProxyInputStream(InputStream proxy)
/*  11:    */   {
/*  12: 46 */     super(proxy);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public int read()
/*  16:    */     throws IOException
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 58 */       beforeRead(1);
/*  21: 59 */       int b = this.in.read();
/*  22: 60 */       afterRead(b != -1 ? 1 : -1);
/*  23: 61 */       return b;
/*  24:    */     }
/*  25:    */     catch (IOException e)
/*  26:    */     {
/*  27: 63 */       handleIOException(e);
/*  28:    */     }
/*  29: 64 */     return -1;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int read(byte[] bts)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 77 */       beforeRead(bts != null ? bts.length : 0);
/*  38: 78 */       int n = this.in.read(bts);
/*  39: 79 */       afterRead(n);
/*  40: 80 */       return n;
/*  41:    */     }
/*  42:    */     catch (IOException e)
/*  43:    */     {
/*  44: 82 */       handleIOException(e);
/*  45:    */     }
/*  46: 83 */     return -1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int read(byte[] bts, int off, int len)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 98 */       beforeRead(len);
/*  55: 99 */       int n = this.in.read(bts, off, len);
/*  56:100 */       afterRead(n);
/*  57:101 */       return n;
/*  58:    */     }
/*  59:    */     catch (IOException e)
/*  60:    */     {
/*  61:103 */       handleIOException(e);
/*  62:    */     }
/*  63:104 */     return -1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public long skip(long ln)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:117 */       return this.in.skip(ln);
/*  72:    */     }
/*  73:    */     catch (IOException e)
/*  74:    */     {
/*  75:119 */       handleIOException(e);
/*  76:    */     }
/*  77:120 */     return 0L;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int available()
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:132 */       return super.available();
/*  86:    */     }
/*  87:    */     catch (IOException e)
/*  88:    */     {
/*  89:134 */       handleIOException(e);
/*  90:    */     }
/*  91:135 */     return 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void close()
/*  95:    */     throws IOException
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:146 */       this.in.close();
/* 100:    */     }
/* 101:    */     catch (IOException e)
/* 102:    */     {
/* 103:148 */       handleIOException(e);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public synchronized void mark(int readlimit)
/* 108:    */   {
/* 109:158 */     this.in.mark(readlimit);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized void reset()
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:168 */       this.in.reset();
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:170 */       handleIOException(e);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean markSupported()
/* 126:    */   {
/* 127:180 */     return this.in.markSupported();
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void beforeRead(int n)
/* 131:    */     throws IOException
/* 132:    */   {}
/* 133:    */   
/* 134:    */   protected void afterRead(int n)
/* 135:    */     throws IOException
/* 136:    */   {}
/* 137:    */   
/* 138:    */   protected void handleIOException(IOException e)
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:234 */     throw e;
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.ProxyInputStream
 * JD-Core Version:    0.7.0.1
 */