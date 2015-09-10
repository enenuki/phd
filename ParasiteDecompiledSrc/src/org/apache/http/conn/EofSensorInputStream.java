/*   1:    */ package org.apache.http.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ 
/*   7:    */ @NotThreadSafe
/*   8:    */ public class EofSensorInputStream
/*   9:    */   extends InputStream
/*  10:    */   implements ConnectionReleaseTrigger
/*  11:    */ {
/*  12:    */   protected InputStream wrappedStream;
/*  13:    */   private boolean selfClosed;
/*  14:    */   private final EofSensorWatcher eofWatcher;
/*  15:    */   
/*  16:    */   public EofSensorInputStream(InputStream in, EofSensorWatcher watcher)
/*  17:    */   {
/*  18: 89 */     if (in == null) {
/*  19: 90 */       throw new IllegalArgumentException("Wrapped stream may not be null.");
/*  20:    */     }
/*  21: 94 */     this.wrappedStream = in;
/*  22: 95 */     this.selfClosed = false;
/*  23: 96 */     this.eofWatcher = watcher;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected boolean isReadAllowed()
/*  27:    */     throws IOException
/*  28:    */   {
/*  29:109 */     if (this.selfClosed) {
/*  30:110 */       throw new IOException("Attempted read on closed stream.");
/*  31:    */     }
/*  32:112 */     return this.wrappedStream != null;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int read()
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:117 */     int l = -1;
/*  39:119 */     if (isReadAllowed()) {
/*  40:    */       try
/*  41:    */       {
/*  42:121 */         l = this.wrappedStream.read();
/*  43:122 */         checkEOF(l);
/*  44:    */       }
/*  45:    */       catch (IOException ex)
/*  46:    */       {
/*  47:124 */         checkAbort();
/*  48:125 */         throw ex;
/*  49:    */       }
/*  50:    */     }
/*  51:129 */     return l;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int read(byte[] b, int off, int len)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:134 */     int l = -1;
/*  58:136 */     if (isReadAllowed()) {
/*  59:    */       try
/*  60:    */       {
/*  61:138 */         l = this.wrappedStream.read(b, off, len);
/*  62:139 */         checkEOF(l);
/*  63:    */       }
/*  64:    */       catch (IOException ex)
/*  65:    */       {
/*  66:141 */         checkAbort();
/*  67:142 */         throw ex;
/*  68:    */       }
/*  69:    */     }
/*  70:146 */     return l;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int read(byte[] b)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:151 */     int l = -1;
/*  77:153 */     if (isReadAllowed()) {
/*  78:    */       try
/*  79:    */       {
/*  80:155 */         l = this.wrappedStream.read(b);
/*  81:156 */         checkEOF(l);
/*  82:    */       }
/*  83:    */       catch (IOException ex)
/*  84:    */       {
/*  85:158 */         checkAbort();
/*  86:159 */         throw ex;
/*  87:    */       }
/*  88:    */     }
/*  89:162 */     return l;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int available()
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:167 */     int a = 0;
/*  96:169 */     if (isReadAllowed()) {
/*  97:    */       try
/*  98:    */       {
/*  99:171 */         a = this.wrappedStream.available();
/* 100:    */       }
/* 101:    */       catch (IOException ex)
/* 102:    */       {
/* 103:174 */         checkAbort();
/* 104:175 */         throw ex;
/* 105:    */       }
/* 106:    */     }
/* 107:179 */     return a;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void close()
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:185 */     this.selfClosed = true;
/* 114:186 */     checkClose();
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void checkEOF(int eof)
/* 118:    */     throws IOException
/* 119:    */   {
/* 120:207 */     if ((this.wrappedStream != null) && (eof < 0)) {
/* 121:    */       try
/* 122:    */       {
/* 123:209 */         boolean scws = true;
/* 124:210 */         if (this.eofWatcher != null) {
/* 125:211 */           scws = this.eofWatcher.eofDetected(this.wrappedStream);
/* 126:    */         }
/* 127:212 */         if (scws) {
/* 128:213 */           this.wrappedStream.close();
/* 129:    */         }
/* 130:    */       }
/* 131:    */       finally
/* 132:    */       {
/* 133:215 */         this.wrappedStream = null;
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void checkClose()
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:233 */     if (this.wrappedStream != null) {
/* 142:    */       try
/* 143:    */       {
/* 144:235 */         boolean scws = true;
/* 145:236 */         if (this.eofWatcher != null) {
/* 146:237 */           scws = this.eofWatcher.streamClosed(this.wrappedStream);
/* 147:    */         }
/* 148:238 */         if (scws) {
/* 149:239 */           this.wrappedStream.close();
/* 150:    */         }
/* 151:    */       }
/* 152:    */       finally
/* 153:    */       {
/* 154:241 */         this.wrappedStream = null;
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void checkAbort()
/* 160:    */     throws IOException
/* 161:    */   {
/* 162:261 */     if (this.wrappedStream != null) {
/* 163:    */       try
/* 164:    */       {
/* 165:263 */         boolean scws = true;
/* 166:264 */         if (this.eofWatcher != null) {
/* 167:265 */           scws = this.eofWatcher.streamAbort(this.wrappedStream);
/* 168:    */         }
/* 169:266 */         if (scws) {
/* 170:267 */           this.wrappedStream.close();
/* 171:    */         }
/* 172:    */       }
/* 173:    */       finally
/* 174:    */       {
/* 175:269 */         this.wrappedStream = null;
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void releaseConnection()
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:278 */     close();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void abortConnection()
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:290 */     this.selfClosed = true;
/* 190:291 */     checkAbort();
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.EofSensorInputStream
 * JD-Core Version:    0.7.0.1
 */