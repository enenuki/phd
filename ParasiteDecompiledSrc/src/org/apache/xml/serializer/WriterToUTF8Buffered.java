/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ 
/*   7:    */ final class WriterToUTF8Buffered
/*   8:    */   extends Writer
/*   9:    */   implements WriterChain
/*  10:    */ {
/*  11:    */   private static final int BYTES_MAX = 16384;
/*  12:    */   private static final int CHARS_MAX = 5461;
/*  13:    */   private final OutputStream m_os;
/*  14:    */   private final byte[] m_outputBytes;
/*  15:    */   private final char[] m_inputChars;
/*  16:    */   private int count;
/*  17:    */   
/*  18:    */   public WriterToUTF8Buffered(OutputStream out)
/*  19:    */   {
/*  20: 83 */     this.m_os = out;
/*  21:    */     
/*  22:    */ 
/*  23: 86 */     this.m_outputBytes = new byte[16387];
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27: 90 */     this.m_inputChars = new char[5463];
/*  28: 91 */     this.count = 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void write(int c)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34:138 */     if (this.count >= 16384) {
/*  35:139 */       flushBuffer();
/*  36:    */     }
/*  37:141 */     if (c < 128)
/*  38:    */     {
/*  39:143 */       this.m_outputBytes[(this.count++)] = ((byte)c);
/*  40:    */     }
/*  41:145 */     else if (c < 2048)
/*  42:    */     {
/*  43:147 */       this.m_outputBytes[(this.count++)] = ((byte)(192 + (c >> 6)));
/*  44:148 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c & 0x3F)));
/*  45:    */     }
/*  46:150 */     else if (c < 65536)
/*  47:    */     {
/*  48:152 */       this.m_outputBytes[(this.count++)] = ((byte)(224 + (c >> 12)));
/*  49:153 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c >> 6 & 0x3F)));
/*  50:154 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c & 0x3F)));
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54:158 */       this.m_outputBytes[(this.count++)] = ((byte)(240 + (c >> 18)));
/*  55:159 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c >> 12 & 0x3F)));
/*  56:160 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c >> 6 & 0x3F)));
/*  57:161 */       this.m_outputBytes[(this.count++)] = ((byte)(128 + (c & 0x3F)));
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void write(char[] chars, int start, int length)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:186 */     int lengthx3 = 3 * length;
/*  65:    */     int start_chunk;
/*  66:188 */     if (lengthx3 >= 16384 - this.count)
/*  67:    */     {
/*  68:191 */       flushBuffer();
/*  69:193 */       if (lengthx3 > 16384)
/*  70:    */       {
/*  71:202 */         int split = length / 5461;
/*  72:    */         int chunks;
/*  73:204 */         if (length % 5461 > 0) {
/*  74:205 */           chunks = split + 1;
/*  75:    */         } else {
/*  76:207 */           chunks = split;
/*  77:    */         }
/*  78:208 */         int end_chunk = start;
/*  79:209 */         for (int chunk = 1; chunk <= chunks; chunk++)
/*  80:    */         {
/*  81:211 */           start_chunk = end_chunk;
/*  82:212 */           end_chunk = start + (int)(length * chunk / chunks);
/*  83:    */           
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:217 */           char c = chars[(end_chunk - 1)];
/*  88:218 */           int ic = chars[(end_chunk - 1)];
/*  89:219 */           if ((c >= 55296) && (c <= 56319)) {
/*  90:225 */             if (end_chunk < start + length) {
/*  91:228 */               end_chunk++;
/*  92:    */             } else {
/*  93:238 */               end_chunk--;
/*  94:    */             }
/*  95:    */           }
/*  96:243 */           int len_chunk = end_chunk - start_chunk;
/*  97:244 */           write(chars, start_chunk, len_chunk);
/*  98:    */         }
/*  99:246 */         return;
/* 100:    */       }
/* 101:    */     }
/* 102:252 */     int n = length + start;
/* 103:253 */     byte[] buf_loc = this.m_outputBytes;
/* 104:254 */     int count_loc = this.count;
/* 105:    */     char c;
/* 106:255 */     for (int i = start; (i < n) && ((c = chars[i]) < ''); i++) {
/* 107:264 */       buf_loc[(count_loc++)] = ((byte)start_chunk);
/* 108:    */     }
/* 109:266 */     for (; i < n; i++)
/* 110:    */     {
/* 111:269 */       char c = chars[i];
/* 112:271 */       if (c < '')
/* 113:    */       {
/* 114:272 */         buf_loc[(count_loc++)] = ((byte)c);
/* 115:    */       }
/* 116:273 */       else if (c < 'ࠀ')
/* 117:    */       {
/* 118:275 */         buf_loc[(count_loc++)] = ((byte)(192 + (c >> '\006')));
/* 119:276 */         buf_loc[(count_loc++)] = ((byte)('' + (c & 0x3F)));
/* 120:    */       }
/* 121:285 */       else if ((c >= 55296) && (c <= 56319))
/* 122:    */       {
/* 123:288 */         char high = c;
/* 124:289 */         i++;
/* 125:290 */         char low = chars[i];
/* 126:    */         
/* 127:292 */         buf_loc[(count_loc++)] = ((byte)(0xF0 | high + '@' >> 8 & 0xF0));
/* 128:293 */         buf_loc[(count_loc++)] = ((byte)(0x80 | high + '@' >> 2 & 0x3F));
/* 129:294 */         buf_loc[(count_loc++)] = ((byte)(0x80 | (low >> '\006' & 0xF) + (high << '\004' & 0x30)));
/* 130:295 */         buf_loc[(count_loc++)] = ((byte)(0x80 | low & 0x3F));
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:299 */         buf_loc[(count_loc++)] = ((byte)(224 + (c >> '\f')));
/* 135:300 */         buf_loc[(count_loc++)] = ((byte)(128 + (c >> '\006' & 0x3F)));
/* 136:301 */         buf_loc[(count_loc++)] = ((byte)('' + (c & 0x3F)));
/* 137:    */       }
/* 138:    */     }
/* 139:305 */     this.count = count_loc;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void write(String s)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:322 */     int length = s.length();
/* 146:323 */     int lengthx3 = 3 * length;
/* 147:    */     int start_chunk;
/* 148:325 */     if (lengthx3 >= 16384 - this.count)
/* 149:    */     {
/* 150:328 */       flushBuffer();
/* 151:330 */       if (lengthx3 > 16384)
/* 152:    */       {
/* 153:336 */         int start = 0;
/* 154:337 */         int split = length / 5461;
/* 155:    */         int chunks;
/* 156:339 */         if (length % 5461 > 0) {
/* 157:340 */           chunks = split + 1;
/* 158:    */         } else {
/* 159:342 */           chunks = split;
/* 160:    */         }
/* 161:343 */         int end_chunk = 0;
/* 162:344 */         for (int chunk = 1; chunk <= chunks; chunk++)
/* 163:    */         {
/* 164:346 */           start_chunk = end_chunk;
/* 165:347 */           end_chunk = 0 + (int)(length * chunk / chunks);
/* 166:348 */           s.getChars(start_chunk, end_chunk, this.m_inputChars, 0);
/* 167:349 */           int len_chunk = end_chunk - start_chunk;
/* 168:    */           
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:354 */           char c = this.m_inputChars[(len_chunk - 1)];
/* 173:355 */           if ((c >= 55296) && (c <= 56319))
/* 174:    */           {
/* 175:359 */             end_chunk--;
/* 176:360 */             len_chunk--;
/* 177:361 */             if (chunk != chunks) {}
/* 178:    */           }
/* 179:371 */           write(this.m_inputChars, 0, len_chunk);
/* 180:    */         }
/* 181:373 */         return;
/* 182:    */       }
/* 183:    */     }
/* 184:378 */     s.getChars(0, length, this.m_inputChars, 0);
/* 185:379 */     char[] chars = this.m_inputChars;
/* 186:380 */     int n = length;
/* 187:381 */     byte[] buf_loc = this.m_outputBytes;
/* 188:382 */     int count_loc = this.count;
/* 189:    */     char c;
/* 190:383 */     for (int i = 0; (i < n) && ((c = chars[i]) < ''); i++) {
/* 191:392 */       buf_loc[(count_loc++)] = ((byte)start_chunk);
/* 192:    */     }
/* 193:394 */     for (; i < n; i++)
/* 194:    */     {
/* 195:397 */       char c = chars[i];
/* 196:399 */       if (c < '')
/* 197:    */       {
/* 198:400 */         buf_loc[(count_loc++)] = ((byte)c);
/* 199:    */       }
/* 200:401 */       else if (c < 'ࠀ')
/* 201:    */       {
/* 202:403 */         buf_loc[(count_loc++)] = ((byte)(192 + (c >> '\006')));
/* 203:404 */         buf_loc[(count_loc++)] = ((byte)('' + (c & 0x3F)));
/* 204:    */       }
/* 205:413 */       else if ((c >= 55296) && (c <= 56319))
/* 206:    */       {
/* 207:416 */         char high = c;
/* 208:417 */         i++;
/* 209:418 */         char low = chars[i];
/* 210:    */         
/* 211:420 */         buf_loc[(count_loc++)] = ((byte)(0xF0 | high + '@' >> 8 & 0xF0));
/* 212:421 */         buf_loc[(count_loc++)] = ((byte)(0x80 | high + '@' >> 2 & 0x3F));
/* 213:422 */         buf_loc[(count_loc++)] = ((byte)(0x80 | (low >> '\006' & 0xF) + (high << '\004' & 0x30)));
/* 214:423 */         buf_loc[(count_loc++)] = ((byte)(0x80 | low & 0x3F));
/* 215:    */       }
/* 216:    */       else
/* 217:    */       {
/* 218:427 */         buf_loc[(count_loc++)] = ((byte)(224 + (c >> '\f')));
/* 219:428 */         buf_loc[(count_loc++)] = ((byte)(128 + (c >> '\006' & 0x3F)));
/* 220:429 */         buf_loc[(count_loc++)] = ((byte)('' + (c & 0x3F)));
/* 221:    */       }
/* 222:    */     }
/* 223:433 */     this.count = count_loc;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void flushBuffer()
/* 227:    */     throws IOException
/* 228:    */   {
/* 229:445 */     if (this.count > 0)
/* 230:    */     {
/* 231:447 */       this.m_os.write(this.m_outputBytes, 0, this.count);
/* 232:    */       
/* 233:449 */       this.count = 0;
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void flush()
/* 238:    */     throws IOException
/* 239:    */   {
/* 240:466 */     flushBuffer();
/* 241:467 */     this.m_os.flush();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void close()
/* 245:    */     throws IOException
/* 246:    */   {
/* 247:481 */     flushBuffer();
/* 248:482 */     this.m_os.close();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public OutputStream getOutputStream()
/* 252:    */   {
/* 253:493 */     return this.m_os;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Writer getWriter()
/* 257:    */   {
/* 258:500 */     return null;
/* 259:    */   }
/* 260:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.WriterToUTF8Buffered
 * JD-Core Version:    0.7.0.1
 */