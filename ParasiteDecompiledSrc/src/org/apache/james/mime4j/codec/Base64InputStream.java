/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ 
/*   8:    */ public class Base64InputStream
/*   9:    */   extends InputStream
/*  10:    */ {
/*  11:    */   private static Log log;
/*  12:    */   private static final int ENCODED_BUFFER_SIZE = 1536;
/*  13:    */   private static final int[] BASE64_DECODE;
/*  14:    */   private static final byte BASE64_PAD = 61;
/*  15:    */   private static final int EOF = -1;
/*  16:    */   
/*  17:    */   static
/*  18:    */   {
/*  19: 32 */     log = LogFactory.getLog(Base64InputStream.class);
/*  20:    */     
/*  21:    */ 
/*  22:    */ 
/*  23: 36 */     BASE64_DECODE = new int[256];
/*  24: 39 */     for (int i = 0; i < 256; i++) {
/*  25: 40 */       BASE64_DECODE[i] = -1;
/*  26:    */     }
/*  27: 41 */     for (int i = 0; i < Base64OutputStream.BASE64_TABLE.length; i++) {
/*  28: 42 */       BASE64_DECODE[(Base64OutputStream.BASE64_TABLE[i] & 0xFF)] = i;
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32: 49 */   private final byte[] singleByte = new byte[1];
/*  33:    */   private boolean strict;
/*  34:    */   private final InputStream in;
/*  35: 54 */   private boolean closed = false;
/*  36: 56 */   private final byte[] encoded = new byte[1536];
/*  37: 57 */   private int position = 0;
/*  38: 58 */   private int size = 0;
/*  39: 60 */   private final ByteQueue q = new ByteQueue();
/*  40:    */   private boolean eof;
/*  41:    */   
/*  42:    */   public Base64InputStream(InputStream in)
/*  43:    */   {
/*  44: 65 */     this(in, false);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Base64InputStream(InputStream in, boolean strict)
/*  48:    */   {
/*  49: 69 */     if (in == null) {
/*  50: 70 */       throw new IllegalArgumentException();
/*  51:    */     }
/*  52: 72 */     this.in = in;
/*  53: 73 */     this.strict = strict;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int read()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 78 */     if (this.closed) {
/*  60: 79 */       throw new IOException("Base64InputStream has been closed");
/*  61:    */     }
/*  62:    */     for (;;)
/*  63:    */     {
/*  64: 82 */       int bytes = read0(this.singleByte, 0, 1);
/*  65: 83 */       if (bytes == -1) {
/*  66: 84 */         return -1;
/*  67:    */       }
/*  68: 86 */       if (bytes == 1) {
/*  69: 87 */         return this.singleByte[0] & 0xFF;
/*  70:    */       }
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int read(byte[] buffer)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77: 93 */     if (this.closed) {
/*  78: 94 */       throw new IOException("Base64InputStream has been closed");
/*  79:    */     }
/*  80: 96 */     if (buffer == null) {
/*  81: 97 */       throw new NullPointerException();
/*  82:    */     }
/*  83: 99 */     if (buffer.length == 0) {
/*  84:100 */       return 0;
/*  85:    */     }
/*  86:102 */     return read0(buffer, 0, buffer.length);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int read(byte[] buffer, int offset, int length)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:107 */     if (this.closed) {
/*  93:108 */       throw new IOException("Base64InputStream has been closed");
/*  94:    */     }
/*  95:110 */     if (buffer == null) {
/*  96:111 */       throw new NullPointerException();
/*  97:    */     }
/*  98:113 */     if ((offset < 0) || (length < 0) || (offset + length > buffer.length)) {
/*  99:114 */       throw new IndexOutOfBoundsException();
/* 100:    */     }
/* 101:116 */     if (length == 0) {
/* 102:117 */       return 0;
/* 103:    */     }
/* 104:119 */     return read0(buffer, offset, offset + length);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void close()
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:124 */     if (this.closed) {
/* 111:125 */       return;
/* 112:    */     }
/* 113:127 */     this.closed = true;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private int read0(byte[] buffer, int from, int to)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:132 */     int index = from;
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:136 */     int qCount = this.q.count();
/* 124:137 */     while ((qCount-- > 0) && (index < to)) {
/* 125:138 */       buffer[(index++)] = this.q.dequeue();
/* 126:    */     }
/* 127:143 */     if (this.eof) {
/* 128:144 */       return index == from ? -1 : index - from;
/* 129:    */     }
/* 130:148 */     int data = 0;
/* 131:149 */     int sextets = 0;
/* 132:151 */     while (index < to)
/* 133:    */     {
/* 134:154 */       while (this.position == this.size)
/* 135:    */       {
/* 136:155 */         int n = this.in.read(this.encoded, 0, this.encoded.length);
/* 137:156 */         if (n == -1)
/* 138:    */         {
/* 139:157 */           this.eof = true;
/* 140:159 */           if (sextets != 0) {
/* 141:161 */             handleUnexpectedEof(sextets);
/* 142:    */           }
/* 143:164 */           return index == from ? -1 : index - from;
/* 144:    */         }
/* 145:165 */         if (n > 0)
/* 146:    */         {
/* 147:166 */           this.position = 0;
/* 148:167 */           this.size = n;
/* 149:    */         }
/* 150:    */         else
/* 151:    */         {
/* 152:169 */           assert (n == 0);
/* 153:    */         }
/* 154:    */       }
/* 155:175 */       while ((this.position < this.size) && (index < to))
/* 156:    */       {
/* 157:176 */         int value = this.encoded[(this.position++)] & 0xFF;
/* 158:178 */         if (value == 61)
/* 159:    */         {
/* 160:179 */           index = decodePad(data, sextets, buffer, index, to);
/* 161:180 */           return index - from;
/* 162:    */         }
/* 163:183 */         int decoded = BASE64_DECODE[value];
/* 164:184 */         if (decoded >= 0)
/* 165:    */         {
/* 166:187 */           data = data << 6 | decoded;
/* 167:188 */           sextets++;
/* 168:190 */           if (sextets == 4)
/* 169:    */           {
/* 170:191 */             sextets = 0;
/* 171:    */             
/* 172:193 */             byte b1 = (byte)(data >>> 16);
/* 173:194 */             byte b2 = (byte)(data >>> 8);
/* 174:195 */             byte b3 = (byte)data;
/* 175:197 */             if (index < to - 2)
/* 176:    */             {
/* 177:198 */               buffer[(index++)] = b1;
/* 178:199 */               buffer[(index++)] = b2;
/* 179:200 */               buffer[(index++)] = b3;
/* 180:    */             }
/* 181:    */             else
/* 182:    */             {
/* 183:202 */               if (index < to - 1)
/* 184:    */               {
/* 185:203 */                 buffer[(index++)] = b1;
/* 186:204 */                 buffer[(index++)] = b2;
/* 187:205 */                 this.q.enqueue(b3);
/* 188:    */               }
/* 189:206 */               else if (index < to)
/* 190:    */               {
/* 191:207 */                 buffer[(index++)] = b1;
/* 192:208 */                 this.q.enqueue(b2);
/* 193:209 */                 this.q.enqueue(b3);
/* 194:    */               }
/* 195:    */               else
/* 196:    */               {
/* 197:211 */                 this.q.enqueue(b1);
/* 198:212 */                 this.q.enqueue(b2);
/* 199:213 */                 this.q.enqueue(b3);
/* 200:    */               }
/* 201:216 */               assert (index == to);
/* 202:217 */               return to - from;
/* 203:    */             }
/* 204:    */           }
/* 205:    */         }
/* 206:    */       }
/* 207:    */     }
/* 208:223 */     assert (sextets == 0);
/* 209:224 */     assert (index == to);
/* 210:225 */     return to - from;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private int decodePad(int data, int sextets, byte[] buffer, int index, int end)
/* 214:    */     throws IOException
/* 215:    */   {
/* 216:230 */     this.eof = true;
/* 217:232 */     if (sextets == 2)
/* 218:    */     {
/* 219:235 */       byte b = (byte)(data >>> 4);
/* 220:236 */       if (index < end) {
/* 221:237 */         buffer[(index++)] = b;
/* 222:    */       } else {
/* 223:239 */         this.q.enqueue(b);
/* 224:    */       }
/* 225:    */     }
/* 226:241 */     else if (sextets == 3)
/* 227:    */     {
/* 228:244 */       byte b1 = (byte)(data >>> 10);
/* 229:245 */       byte b2 = (byte)(data >>> 2 & 0xFF);
/* 230:247 */       if (index < end - 1)
/* 231:    */       {
/* 232:248 */         buffer[(index++)] = b1;
/* 233:249 */         buffer[(index++)] = b2;
/* 234:    */       }
/* 235:250 */       else if (index < end)
/* 236:    */       {
/* 237:251 */         buffer[(index++)] = b1;
/* 238:252 */         this.q.enqueue(b2);
/* 239:    */       }
/* 240:    */       else
/* 241:    */       {
/* 242:254 */         this.q.enqueue(b1);
/* 243:255 */         this.q.enqueue(b2);
/* 244:    */       }
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:259 */       handleUnexpecedPad(sextets);
/* 249:    */     }
/* 250:262 */     return index;
/* 251:    */   }
/* 252:    */   
/* 253:    */   private void handleUnexpectedEof(int sextets)
/* 254:    */     throws IOException
/* 255:    */   {
/* 256:266 */     if (this.strict) {
/* 257:267 */       throw new IOException("unexpected end of file");
/* 258:    */     }
/* 259:269 */     log.warn("unexpected end of file; dropping " + sextets + " sextet(s)");
/* 260:    */   }
/* 261:    */   
/* 262:    */   private void handleUnexpecedPad(int sextets)
/* 263:    */     throws IOException
/* 264:    */   {
/* 265:274 */     if (this.strict) {
/* 266:275 */       throw new IOException("unexpected padding character");
/* 267:    */     }
/* 268:277 */     log.warn("unexpected padding character; dropping " + sextets + " sextet(s)");
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.Base64InputStream
 * JD-Core Version:    0.7.0.1
 */