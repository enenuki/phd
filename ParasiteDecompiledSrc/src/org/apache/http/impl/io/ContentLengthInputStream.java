/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.http.ConnectionClosedException;
/*   6:    */ import org.apache.http.io.BufferInfo;
/*   7:    */ import org.apache.http.io.SessionInputBuffer;
/*   8:    */ 
/*   9:    */ public class ContentLengthInputStream
/*  10:    */   extends InputStream
/*  11:    */ {
/*  12:    */   private static final int BUFFER_SIZE = 2048;
/*  13:    */   private long contentLength;
/*  14: 63 */   private long pos = 0L;
/*  15: 66 */   private boolean closed = false;
/*  16: 71 */   private SessionInputBuffer in = null;
/*  17:    */   
/*  18:    */   public ContentLengthInputStream(SessionInputBuffer in, long contentLength)
/*  19:    */   {
/*  20: 83 */     if (in == null) {
/*  21: 84 */       throw new IllegalArgumentException("Input stream may not be null");
/*  22:    */     }
/*  23: 86 */     if (contentLength < 0L) {
/*  24: 87 */       throw new IllegalArgumentException("Content length may not be negative");
/*  25:    */     }
/*  26: 89 */     this.in = in;
/*  27: 90 */     this.contentLength = contentLength;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void close()
/*  31:    */     throws IOException
/*  32:    */   {
/*  33:101 */     if (!this.closed) {
/*  34:    */       try
/*  35:    */       {
/*  36:103 */         if (this.pos < this.contentLength)
/*  37:    */         {
/*  38:104 */           byte[] buffer = new byte[2048];
/*  39:105 */           while (read(buffer) >= 0) {}
/*  40:    */         }
/*  41:    */       }
/*  42:    */       finally
/*  43:    */       {
/*  44:111 */         this.closed = true;
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int available()
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:117 */     if ((this.in instanceof BufferInfo))
/*  53:    */     {
/*  54:118 */       int len = ((BufferInfo)this.in).length();
/*  55:119 */       return Math.min(len, (int)(this.contentLength - this.pos));
/*  56:    */     }
/*  57:121 */     return 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int read()
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:132 */     if (this.closed) {
/*  64:133 */       throw new IOException("Attempted read from closed stream.");
/*  65:    */     }
/*  66:136 */     if (this.pos >= this.contentLength) {
/*  67:137 */       return -1;
/*  68:    */     }
/*  69:139 */     int b = this.in.read();
/*  70:140 */     if (b == -1)
/*  71:    */     {
/*  72:141 */       if (this.pos < this.contentLength) {
/*  73:142 */         throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
/*  74:    */       }
/*  75:    */     }
/*  76:    */     else {
/*  77:147 */       this.pos += 1L;
/*  78:    */     }
/*  79:149 */     return b;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int read(byte[] b, int off, int len)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:165 */     if (this.closed) {
/*  86:166 */       throw new IOException("Attempted read from closed stream.");
/*  87:    */     }
/*  88:169 */     if (this.pos >= this.contentLength) {
/*  89:170 */       return -1;
/*  90:    */     }
/*  91:173 */     if (this.pos + len > this.contentLength) {
/*  92:174 */       len = (int)(this.contentLength - this.pos);
/*  93:    */     }
/*  94:176 */     int count = this.in.read(b, off, len);
/*  95:177 */     if ((count == -1) && (this.pos < this.contentLength)) {
/*  96:178 */       throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
/*  97:    */     }
/*  98:182 */     if (count > 0) {
/*  99:183 */       this.pos += count;
/* 100:    */     }
/* 101:185 */     return count;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int read(byte[] b)
/* 105:    */     throws IOException
/* 106:    */   {
/* 107:197 */     return read(b, 0, b.length);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public long skip(long n)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:209 */     if (n <= 0L) {
/* 114:210 */       return 0L;
/* 115:    */     }
/* 116:212 */     byte[] buffer = new byte[2048];
/* 117:    */     
/* 118:    */ 
/* 119:215 */     long remaining = Math.min(n, this.contentLength - this.pos);
/* 120:    */     
/* 121:217 */     long count = 0L;
/* 122:218 */     while (remaining > 0L)
/* 123:    */     {
/* 124:219 */       int l = read(buffer, 0, (int)Math.min(2048L, remaining));
/* 125:220 */       if (l == -1) {
/* 126:    */         break;
/* 127:    */       }
/* 128:223 */       count += l;
/* 129:224 */       remaining -= l;
/* 130:    */     }
/* 131:226 */     return count;
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.ContentLengthInputStream
 * JD-Core Version:    0.7.0.1
 */