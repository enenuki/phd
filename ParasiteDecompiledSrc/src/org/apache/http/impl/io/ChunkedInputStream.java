/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.http.Header;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.MalformedChunkCodingException;
/*   8:    */ import org.apache.http.TruncatedChunkException;
/*   9:    */ import org.apache.http.io.BufferInfo;
/*  10:    */ import org.apache.http.io.SessionInputBuffer;
/*  11:    */ import org.apache.http.util.CharArrayBuffer;
/*  12:    */ import org.apache.http.util.ExceptionUtils;
/*  13:    */ 
/*  14:    */ public class ChunkedInputStream
/*  15:    */   extends InputStream
/*  16:    */ {
/*  17:    */   private static final int CHUNK_LEN = 1;
/*  18:    */   private static final int CHUNK_DATA = 2;
/*  19:    */   private static final int CHUNK_CRLF = 3;
/*  20:    */   private static final int BUFFER_SIZE = 2048;
/*  21:    */   private final SessionInputBuffer in;
/*  22:    */   private final CharArrayBuffer buffer;
/*  23:    */   private int state;
/*  24:    */   private int chunkSize;
/*  25:    */   private int pos;
/*  26: 80 */   private boolean eof = false;
/*  27: 83 */   private boolean closed = false;
/*  28: 85 */   private Header[] footers = new Header[0];
/*  29:    */   
/*  30:    */   public ChunkedInputStream(SessionInputBuffer in)
/*  31:    */   {
/*  32: 94 */     if (in == null) {
/*  33: 95 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*  34:    */     }
/*  35: 97 */     this.in = in;
/*  36: 98 */     this.pos = 0;
/*  37: 99 */     this.buffer = new CharArrayBuffer(16);
/*  38:100 */     this.state = 1;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int available()
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:104 */     if ((this.in instanceof BufferInfo))
/*  45:    */     {
/*  46:105 */       int len = ((BufferInfo)this.in).length();
/*  47:106 */       return Math.min(len, this.chunkSize - this.pos);
/*  48:    */     }
/*  49:108 */     return 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int read()
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:125 */     if (this.closed) {
/*  56:126 */       throw new IOException("Attempted read from closed stream.");
/*  57:    */     }
/*  58:128 */     if (this.eof) {
/*  59:129 */       return -1;
/*  60:    */     }
/*  61:131 */     if (this.state != 2)
/*  62:    */     {
/*  63:132 */       nextChunk();
/*  64:133 */       if (this.eof) {
/*  65:134 */         return -1;
/*  66:    */       }
/*  67:    */     }
/*  68:137 */     int b = this.in.read();
/*  69:138 */     if (b != -1)
/*  70:    */     {
/*  71:139 */       this.pos += 1;
/*  72:140 */       if (this.pos >= this.chunkSize) {
/*  73:141 */         this.state = 3;
/*  74:    */       }
/*  75:    */     }
/*  76:144 */     return b;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int read(byte[] b, int off, int len)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:159 */     if (this.closed) {
/*  83:160 */       throw new IOException("Attempted read from closed stream.");
/*  84:    */     }
/*  85:163 */     if (this.eof) {
/*  86:164 */       return -1;
/*  87:    */     }
/*  88:166 */     if (this.state != 2)
/*  89:    */     {
/*  90:167 */       nextChunk();
/*  91:168 */       if (this.eof) {
/*  92:169 */         return -1;
/*  93:    */       }
/*  94:    */     }
/*  95:172 */     len = Math.min(len, this.chunkSize - this.pos);
/*  96:173 */     int bytesRead = this.in.read(b, off, len);
/*  97:174 */     if (bytesRead != -1)
/*  98:    */     {
/*  99:175 */       this.pos += bytesRead;
/* 100:176 */       if (this.pos >= this.chunkSize) {
/* 101:177 */         this.state = 3;
/* 102:    */       }
/* 103:179 */       return bytesRead;
/* 104:    */     }
/* 105:181 */     this.eof = true;
/* 106:182 */     throw new TruncatedChunkException("Truncated chunk ( expected size: " + this.chunkSize + "; actual size: " + this.pos + ")");
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int read(byte[] b)
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:196 */     return read(b, 0, b.length);
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void nextChunk()
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:204 */     this.chunkSize = getChunkSize();
/* 119:205 */     if (this.chunkSize < 0) {
/* 120:206 */       throw new MalformedChunkCodingException("Negative chunk size");
/* 121:    */     }
/* 122:208 */     this.state = 2;
/* 123:209 */     this.pos = 0;
/* 124:210 */     if (this.chunkSize == 0)
/* 125:    */     {
/* 126:211 */       this.eof = true;
/* 127:212 */       parseTrailerHeaders();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private int getChunkSize()
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:230 */     int st = this.state;
/* 135:    */     int i;
/* 136:231 */     switch (st)
/* 137:    */     {
/* 138:    */     case 3: 
/* 139:233 */       this.buffer.clear();
/* 140:234 */       i = this.in.readLine(this.buffer);
/* 141:235 */       if (i == -1) {
/* 142:236 */         return 0;
/* 143:    */       }
/* 144:238 */       if (!this.buffer.isEmpty()) {
/* 145:239 */         throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
/* 146:    */       }
/* 147:242 */       this.state = 1;
/* 148:    */     case 1: 
/* 149:245 */       this.buffer.clear();
/* 150:246 */       i = this.in.readLine(this.buffer);
/* 151:247 */       if (i == -1) {
/* 152:248 */         return 0;
/* 153:    */       }
/* 154:250 */       int separator = this.buffer.indexOf(59);
/* 155:251 */       if (separator < 0) {
/* 156:252 */         separator = this.buffer.length();
/* 157:    */       }
/* 158:    */       try
/* 159:    */       {
/* 160:255 */         return Integer.parseInt(this.buffer.substringTrimmed(0, separator), 16);
/* 161:    */       }
/* 162:    */       catch (NumberFormatException e)
/* 163:    */       {
/* 164:257 */         throw new MalformedChunkCodingException("Bad chunk header");
/* 165:    */       }
/* 166:    */     }
/* 167:260 */     throw new IllegalStateException("Inconsistent codec state");
/* 168:    */   }
/* 169:    */   
/* 170:    */   private void parseTrailerHeaders()
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:    */     try
/* 174:    */     {
/* 175:270 */       this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
/* 176:    */     }
/* 177:    */     catch (HttpException e)
/* 178:    */     {
/* 179:273 */       IOException ioe = new MalformedChunkCodingException("Invalid footer: " + e.getMessage());
/* 180:    */       
/* 181:275 */       ExceptionUtils.initCause(ioe, e);
/* 182:276 */       throw ioe;
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void close()
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:287 */     if (!this.closed) {
/* 190:    */       try
/* 191:    */       {
/* 192:289 */         if (!this.eof)
/* 193:    */         {
/* 194:291 */           byte[] buffer = new byte[2048];
/* 195:292 */           while (read(buffer) >= 0) {}
/* 196:    */         }
/* 197:    */       }
/* 198:    */       finally
/* 199:    */       {
/* 200:296 */         this.eof = true;
/* 201:297 */         this.closed = true;
/* 202:    */       }
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Header[] getFooters()
/* 207:    */   {
/* 208:303 */     return (Header[])this.footers.clone();
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.ChunkedInputStream
 * JD-Core Version:    0.7.0.1
 */