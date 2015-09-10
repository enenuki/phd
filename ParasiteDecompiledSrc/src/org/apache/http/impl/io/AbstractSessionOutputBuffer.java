/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import org.apache.http.io.BufferInfo;
/*   6:    */ import org.apache.http.io.HttpTransportMetrics;
/*   7:    */ import org.apache.http.io.SessionOutputBuffer;
/*   8:    */ import org.apache.http.params.HttpParams;
/*   9:    */ import org.apache.http.params.HttpProtocolParams;
/*  10:    */ import org.apache.http.util.ByteArrayBuffer;
/*  11:    */ import org.apache.http.util.CharArrayBuffer;
/*  12:    */ 
/*  13:    */ public abstract class AbstractSessionOutputBuffer
/*  14:    */   implements SessionOutputBuffer, BufferInfo
/*  15:    */ {
/*  16: 63 */   private static final byte[] CRLF = { 13, 10 };
/*  17:    */   private OutputStream outstream;
/*  18:    */   private ByteArrayBuffer buffer;
/*  19: 68 */   private String charset = "US-ASCII";
/*  20: 69 */   private boolean ascii = true;
/*  21: 70 */   private int minChunkLimit = 512;
/*  22:    */   private HttpTransportMetricsImpl metrics;
/*  23:    */   
/*  24:    */   protected void init(OutputStream outstream, int buffersize, HttpParams params)
/*  25:    */   {
/*  26: 82 */     if (outstream == null) {
/*  27: 83 */       throw new IllegalArgumentException("Input stream may not be null");
/*  28:    */     }
/*  29: 85 */     if (buffersize <= 0) {
/*  30: 86 */       throw new IllegalArgumentException("Buffer size may not be negative or zero");
/*  31:    */     }
/*  32: 88 */     if (params == null) {
/*  33: 89 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  34:    */     }
/*  35: 91 */     this.outstream = outstream;
/*  36: 92 */     this.buffer = new ByteArrayBuffer(buffersize);
/*  37: 93 */     this.charset = HttpProtocolParams.getHttpElementCharset(params);
/*  38: 94 */     this.ascii = ((this.charset.equalsIgnoreCase("US-ASCII")) || (this.charset.equalsIgnoreCase("ASCII")));
/*  39:    */     
/*  40: 96 */     this.minChunkLimit = params.getIntParameter("http.connection.min-chunk-limit", 512);
/*  41: 97 */     this.metrics = createTransportMetrics();
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected HttpTransportMetricsImpl createTransportMetrics()
/*  45:    */   {
/*  46:104 */     return new HttpTransportMetricsImpl();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int capacity()
/*  50:    */   {
/*  51:111 */     return this.buffer.capacity();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int length()
/*  55:    */   {
/*  56:118 */     return this.buffer.length();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int available()
/*  60:    */   {
/*  61:125 */     return capacity() - length();
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void flushBuffer()
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:129 */     int len = this.buffer.length();
/*  68:130 */     if (len > 0)
/*  69:    */     {
/*  70:131 */       this.outstream.write(this.buffer.buffer(), 0, len);
/*  71:132 */       this.buffer.clear();
/*  72:133 */       this.metrics.incrementBytesTransferred(len);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void flush()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:138 */     flushBuffer();
/*  80:139 */     this.outstream.flush();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void write(byte[] b, int off, int len)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:143 */     if (b == null) {
/*  87:144 */       return;
/*  88:    */     }
/*  89:149 */     if ((len > this.minChunkLimit) || (len > this.buffer.capacity()))
/*  90:    */     {
/*  91:151 */       flushBuffer();
/*  92:    */       
/*  93:153 */       this.outstream.write(b, off, len);
/*  94:154 */       this.metrics.incrementBytesTransferred(len);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:157 */       int freecapacity = this.buffer.capacity() - this.buffer.length();
/*  99:158 */       if (len > freecapacity) {
/* 100:160 */         flushBuffer();
/* 101:    */       }
/* 102:163 */       this.buffer.append(b, off, len);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void write(byte[] b)
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:168 */     if (b == null) {
/* 110:169 */       return;
/* 111:    */     }
/* 112:171 */     write(b, 0, b.length);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void write(int b)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:175 */     if (this.buffer.isFull()) {
/* 119:176 */       flushBuffer();
/* 120:    */     }
/* 121:178 */     this.buffer.append(b);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void writeLine(String s)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:191 */     if (s == null) {
/* 128:192 */       return;
/* 129:    */     }
/* 130:194 */     if (s.length() > 0) {
/* 131:195 */       write(s.getBytes(this.charset));
/* 132:    */     }
/* 133:197 */     write(CRLF);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void writeLine(CharArrayBuffer s)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:210 */     if (s == null) {
/* 140:211 */       return;
/* 141:    */     }
/* 142:213 */     if (this.ascii)
/* 143:    */     {
/* 144:214 */       int off = 0;
/* 145:215 */       int remaining = s.length();
/* 146:216 */       while (remaining > 0)
/* 147:    */       {
/* 148:217 */         int chunk = this.buffer.capacity() - this.buffer.length();
/* 149:218 */         chunk = Math.min(chunk, remaining);
/* 150:219 */         if (chunk > 0) {
/* 151:220 */           this.buffer.append(s, off, chunk);
/* 152:    */         }
/* 153:222 */         if (this.buffer.isFull()) {
/* 154:223 */           flushBuffer();
/* 155:    */         }
/* 156:225 */         off += chunk;
/* 157:226 */         remaining -= chunk;
/* 158:    */       }
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:231 */       byte[] tmp = s.toString().getBytes(this.charset);
/* 163:232 */       write(tmp);
/* 164:    */     }
/* 165:234 */     write(CRLF);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public HttpTransportMetrics getMetrics()
/* 169:    */   {
/* 170:238 */     return this.metrics;
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.AbstractSessionOutputBuffer
 * JD-Core Version:    0.7.0.1
 */