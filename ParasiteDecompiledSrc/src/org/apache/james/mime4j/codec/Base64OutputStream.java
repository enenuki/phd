/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.FilterOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class Base64OutputStream
/*  10:    */   extends FilterOutputStream
/*  11:    */ {
/*  12:    */   private static final int DEFAULT_LINE_LENGTH = 76;
/*  13:    */   private static final byte[] CRLF_SEPARATOR;
/*  14:    */   static final byte[] BASE64_TABLE;
/*  15:    */   private static final byte BASE64_PAD = 61;
/*  16:    */   private static final Set<Byte> BASE64_CHARS;
/*  17:    */   private static final int MASK_6BITS = 63;
/*  18:    */   private static final int ENCODED_BUFFER_SIZE = 2048;
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22: 43 */     CRLF_SEPARATOR = new byte[] { 13, 10 };
/*  23:    */     
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27: 48 */     BASE64_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*  28:    */     
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39: 60 */     BASE64_CHARS = new HashSet();
/*  40: 63 */     for (byte b : BASE64_TABLE) {
/*  41: 64 */       BASE64_CHARS.add(Byte.valueOf(b));
/*  42:    */     }
/*  43: 66 */     BASE64_CHARS.add(Byte.valueOf((byte)61));
/*  44:    */   }
/*  45:    */   
/*  46: 74 */   private final byte[] singleByte = new byte[1];
/*  47:    */   private final int lineLength;
/*  48:    */   private final byte[] lineSeparator;
/*  49: 79 */   private boolean closed = false;
/*  50:    */   private final byte[] encoded;
/*  51: 82 */   private int position = 0;
/*  52: 84 */   private int data = 0;
/*  53: 85 */   private int modulus = 0;
/*  54: 87 */   private int linePosition = 0;
/*  55:    */   
/*  56:    */   public Base64OutputStream(OutputStream out)
/*  57:    */   {
/*  58: 98 */     this(out, 76, CRLF_SEPARATOR);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Base64OutputStream(OutputStream out, int lineLength)
/*  62:    */   {
/*  63:115 */     this(out, lineLength, CRLF_SEPARATOR);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Base64OutputStream(OutputStream out, int lineLength, byte[] lineSeparator)
/*  67:    */   {
/*  68:139 */     super(out);
/*  69:141 */     if (out == null) {
/*  70:142 */       throw new IllegalArgumentException();
/*  71:    */     }
/*  72:143 */     if (lineLength < 0) {
/*  73:144 */       throw new IllegalArgumentException();
/*  74:    */     }
/*  75:145 */     checkLineSeparator(lineSeparator);
/*  76:    */     
/*  77:147 */     this.lineLength = lineLength;
/*  78:148 */     this.lineSeparator = new byte[lineSeparator.length];
/*  79:149 */     System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
/*  80:    */     
/*  81:    */ 
/*  82:152 */     this.encoded = new byte[2048];
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final void write(int b)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:157 */     if (this.closed) {
/*  89:158 */       throw new IOException("Base64OutputStream has been closed");
/*  90:    */     }
/*  91:160 */     this.singleByte[0] = ((byte)b);
/*  92:161 */     write0(this.singleByte, 0, 1);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final void write(byte[] buffer)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:166 */     if (this.closed) {
/*  99:167 */       throw new IOException("Base64OutputStream has been closed");
/* 100:    */     }
/* 101:169 */     if (buffer == null) {
/* 102:170 */       throw new NullPointerException();
/* 103:    */     }
/* 104:172 */     if (buffer.length == 0) {
/* 105:173 */       return;
/* 106:    */     }
/* 107:175 */     write0(buffer, 0, buffer.length);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public final void write(byte[] buffer, int offset, int length)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:181 */     if (this.closed) {
/* 114:182 */       throw new IOException("Base64OutputStream has been closed");
/* 115:    */     }
/* 116:184 */     if (buffer == null) {
/* 117:185 */       throw new NullPointerException();
/* 118:    */     }
/* 119:187 */     if ((offset < 0) || (length < 0) || (offset + length > buffer.length)) {
/* 120:188 */       throw new IndexOutOfBoundsException();
/* 121:    */     }
/* 122:190 */     if (length == 0) {
/* 123:191 */       return;
/* 124:    */     }
/* 125:193 */     write0(buffer, offset, offset + length);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void flush()
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:198 */     if (this.closed) {
/* 132:199 */       throw new IOException("Base64OutputStream has been closed");
/* 133:    */     }
/* 134:201 */     flush0();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void close()
/* 138:    */     throws IOException
/* 139:    */   {
/* 140:206 */     if (this.closed) {
/* 141:207 */       return;
/* 142:    */     }
/* 143:209 */     this.closed = true;
/* 144:210 */     close0();
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void write0(byte[] buffer, int from, int to)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:215 */     for (int i = from; i < to; i++)
/* 151:    */     {
/* 152:216 */       this.data = (this.data << 8 | buffer[i] & 0xFF);
/* 153:218 */       if (++this.modulus == 3)
/* 154:    */       {
/* 155:219 */         this.modulus = 0;
/* 156:223 */         if ((this.lineLength > 0) && (this.linePosition >= this.lineLength))
/* 157:    */         {
/* 158:226 */           this.linePosition = 0;
/* 159:228 */           if (this.encoded.length - this.position < this.lineSeparator.length) {
/* 160:229 */             flush0();
/* 161:    */           }
/* 162:231 */           for (byte ls : this.lineSeparator) {
/* 163:232 */             this.encoded[(this.position++)] = ls;
/* 164:    */           }
/* 165:    */         }
/* 166:237 */         if (this.encoded.length - this.position < 4) {
/* 167:238 */           flush0();
/* 168:    */         }
/* 169:240 */         this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 18 & 0x3F)];
/* 170:241 */         this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 12 & 0x3F)];
/* 171:242 */         this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 6 & 0x3F)];
/* 172:243 */         this.encoded[(this.position++)] = BASE64_TABLE[(this.data & 0x3F)];
/* 173:    */         
/* 174:245 */         this.linePosition += 4;
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   private void flush0()
/* 180:    */     throws IOException
/* 181:    */   {
/* 182:251 */     if (this.position > 0)
/* 183:    */     {
/* 184:252 */       this.out.write(this.encoded, 0, this.position);
/* 185:253 */       this.position = 0;
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void close0()
/* 190:    */     throws IOException
/* 191:    */   {
/* 192:258 */     if (this.modulus != 0) {
/* 193:259 */       writePad();
/* 194:    */     }
/* 195:263 */     if ((this.lineLength > 0) && (this.linePosition > 0)) {
/* 196:264 */       writeLineSeparator();
/* 197:    */     }
/* 198:267 */     flush0();
/* 199:    */   }
/* 200:    */   
/* 201:    */   private void writePad()
/* 202:    */     throws IOException
/* 203:    */   {
/* 204:273 */     if ((this.lineLength > 0) && (this.linePosition >= this.lineLength)) {
/* 205:274 */       writeLineSeparator();
/* 206:    */     }
/* 207:279 */     if (this.encoded.length - this.position < 4) {
/* 208:280 */       flush0();
/* 209:    */     }
/* 210:282 */     if (this.modulus == 1)
/* 211:    */     {
/* 212:283 */       this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 2 & 0x3F)];
/* 213:284 */       this.encoded[(this.position++)] = BASE64_TABLE[(this.data << 4 & 0x3F)];
/* 214:285 */       this.encoded[(this.position++)] = 61;
/* 215:286 */       this.encoded[(this.position++)] = 61;
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:288 */       assert (this.modulus == 2);
/* 220:289 */       this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 10 & 0x3F)];
/* 221:290 */       this.encoded[(this.position++)] = BASE64_TABLE[(this.data >> 4 & 0x3F)];
/* 222:291 */       this.encoded[(this.position++)] = BASE64_TABLE[(this.data << 2 & 0x3F)];
/* 223:292 */       this.encoded[(this.position++)] = 61;
/* 224:    */     }
/* 225:295 */     this.linePosition += 4;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private void writeLineSeparator()
/* 229:    */     throws IOException
/* 230:    */   {
/* 231:299 */     this.linePosition = 0;
/* 232:301 */     if (this.encoded.length - this.position < this.lineSeparator.length) {
/* 233:302 */       flush0();
/* 234:    */     }
/* 235:304 */     for (byte ls : this.lineSeparator) {
/* 236:305 */       this.encoded[(this.position++)] = ls;
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   private void checkLineSeparator(byte[] lineSeparator)
/* 241:    */   {
/* 242:309 */     if (lineSeparator.length > 2048) {
/* 243:310 */       throw new IllegalArgumentException("line separator length exceeds 2048");
/* 244:    */     }
/* 245:313 */     for (byte b : lineSeparator) {
/* 246:314 */       if (BASE64_CHARS.contains(Byte.valueOf(b))) {
/* 247:315 */         throw new IllegalArgumentException("line separator must not contain base64 character '" + (char)(b & 0xFF) + "'");
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.Base64OutputStream
 * JD-Core Version:    0.7.0.1
 */