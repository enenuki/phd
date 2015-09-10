/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ public final class EncodingInfo
/*   4:    */ {
/*   5:    */   private final char m_highCharInContiguousGroup;
/*   6:    */   final String name;
/*   7:    */   final String javaName;
/*   8:    */   private InEncoding m_encoding;
/*   9:    */   
/*  10:    */   public boolean isInEncoding(char ch)
/*  11:    */   {
/*  12:105 */     if (this.m_encoding == null) {
/*  13:106 */       this.m_encoding = new EncodingImpl(null);
/*  14:    */     }
/*  15:113 */     return this.m_encoding.isInEncoding(ch);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean isInEncoding(char high, char low)
/*  19:    */   {
/*  20:126 */     if (this.m_encoding == null) {
/*  21:127 */       this.m_encoding = new EncodingImpl(null);
/*  22:    */     }
/*  23:134 */     return this.m_encoding.isInEncoding(high, low);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public EncodingInfo(String name, String javaName, char highChar)
/*  27:    */   {
/*  28:152 */     this.name = name;
/*  29:153 */     this.javaName = javaName;
/*  30:154 */     this.m_highCharInContiguousGroup = highChar;
/*  31:    */   }
/*  32:    */   
/*  33:    */   private class EncodingImpl
/*  34:    */     implements EncodingInfo.InEncoding
/*  35:    */   {
/*  36:    */     private final String m_encoding;
/*  37:    */     private final int m_first;
/*  38:    */     private final int m_explFirst;
/*  39:    */     private final int m_explLast;
/*  40:    */     private final int m_last;
/*  41:    */     private EncodingInfo.InEncoding m_before;
/*  42:    */     private EncodingInfo.InEncoding m_after;
/*  43:    */     private static final int RANGE = 128;
/*  44:    */     
/*  45:    */     EncodingImpl(EncodingInfo.1 x1)
/*  46:    */     {
/*  47:183 */       this();
/*  48:    */     }
/*  49:    */     
/*  50:    */     public boolean isInEncoding(char ch1)
/*  51:    */     {
/*  52:189 */       int codePoint = Encodings.toCodePoint(ch1);
/*  53:    */       boolean ret;
/*  54:190 */       if (codePoint < this.m_explFirst)
/*  55:    */       {
/*  56:195 */         if (this.m_before == null) {
/*  57:196 */           this.m_before = new EncodingImpl(EncodingInfo.this, this.m_encoding, this.m_first, this.m_explFirst - 1, codePoint);
/*  58:    */         }
/*  59:202 */         ret = this.m_before.isInEncoding(ch1);
/*  60:    */       }
/*  61:203 */       else if (this.m_explLast < codePoint)
/*  62:    */       {
/*  63:208 */         if (this.m_after == null) {
/*  64:209 */           this.m_after = new EncodingImpl(EncodingInfo.this, this.m_encoding, this.m_explLast + 1, this.m_last, codePoint);
/*  65:    */         }
/*  66:215 */         ret = this.m_after.isInEncoding(ch1);
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70:218 */         int idx = codePoint - this.m_explFirst;
/*  71:221 */         if (this.m_alreadyKnown[idx] != 0)
/*  72:    */         {
/*  73:222 */           ret = this.m_isInEncoding[idx];
/*  74:    */         }
/*  75:    */         else
/*  76:    */         {
/*  77:226 */           ret = EncodingInfo.inEncoding(ch1, this.m_encoding);
/*  78:227 */           this.m_alreadyKnown[idx] = true;
/*  79:228 */           this.m_isInEncoding[idx] = ret;
/*  80:    */         }
/*  81:    */       }
/*  82:231 */       return ret;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public boolean isInEncoding(char high, char low)
/*  86:    */     {
/*  87:236 */       int codePoint = Encodings.toCodePoint(high, low);
/*  88:    */       boolean ret;
/*  89:237 */       if (codePoint < this.m_explFirst)
/*  90:    */       {
/*  91:242 */         if (this.m_before == null) {
/*  92:243 */           this.m_before = new EncodingImpl(EncodingInfo.this, this.m_encoding, this.m_first, this.m_explFirst - 1, codePoint);
/*  93:    */         }
/*  94:249 */         ret = this.m_before.isInEncoding(high, low);
/*  95:    */       }
/*  96:250 */       else if (this.m_explLast < codePoint)
/*  97:    */       {
/*  98:255 */         if (this.m_after == null) {
/*  99:256 */           this.m_after = new EncodingImpl(EncodingInfo.this, this.m_encoding, this.m_explLast + 1, this.m_last, codePoint);
/* 100:    */         }
/* 101:262 */         ret = this.m_after.isInEncoding(high, low);
/* 102:    */       }
/* 103:    */       else
/* 104:    */       {
/* 105:265 */         int idx = codePoint - this.m_explFirst;
/* 106:268 */         if (this.m_alreadyKnown[idx] != 0)
/* 107:    */         {
/* 108:269 */           ret = this.m_isInEncoding[idx];
/* 109:    */         }
/* 110:    */         else
/* 111:    */         {
/* 112:273 */           ret = EncodingInfo.inEncoding(high, low, this.m_encoding);
/* 113:274 */           this.m_alreadyKnown[idx] = true;
/* 114:275 */           this.m_isInEncoding[idx] = ret;
/* 115:    */         }
/* 116:    */       }
/* 117:278 */       return ret;
/* 118:    */     }
/* 119:    */     
/* 120:330 */     private final boolean[] m_alreadyKnown = new boolean[''];
/* 121:335 */     private final boolean[] m_isInEncoding = new boolean[''];
/* 122:    */     
/* 123:    */     private EncodingImpl()
/* 124:    */     {
/* 125:340 */       this(EncodingInfo.this.javaName, 0, 2147483647, 0);
/* 126:    */     }
/* 127:    */     
/* 128:    */     private EncodingImpl(String encoding, int first, int last, int codePoint)
/* 129:    */     {
/* 130:346 */       this.m_first = first;
/* 131:347 */       this.m_last = last;
/* 132:    */       
/* 133:    */ 
/* 134:    */ 
/* 135:351 */       this.m_explFirst = codePoint;
/* 136:352 */       this.m_explLast = (codePoint + 127);
/* 137:    */       
/* 138:354 */       this.m_encoding = encoding;
/* 139:356 */       if (EncodingInfo.this.javaName != null)
/* 140:    */       {
/* 141:359 */         if ((0 <= this.m_explFirst) && (this.m_explFirst <= 127)) {
/* 142:362 */           if (("UTF8".equals(EncodingInfo.this.javaName)) || ("UTF-16".equals(EncodingInfo.this.javaName)) || ("ASCII".equals(EncodingInfo.this.javaName)) || ("US-ASCII".equals(EncodingInfo.this.javaName)) || ("Unicode".equals(EncodingInfo.this.javaName)) || ("UNICODE".equals(EncodingInfo.this.javaName)) || (EncodingInfo.this.javaName.startsWith("ISO8859"))) {
/* 143:381 */             for (int unicode = 1; unicode < 127; unicode++)
/* 144:    */             {
/* 145:382 */               int idx = unicode - this.m_explFirst;
/* 146:383 */               if ((0 <= idx) && (idx < 128))
/* 147:    */               {
/* 148:384 */                 this.m_alreadyKnown[idx] = true;
/* 149:385 */                 this.m_isInEncoding[idx] = true;
/* 150:    */               }
/* 151:    */             }
/* 152:    */           }
/* 153:    */         }
/* 154:401 */         if (EncodingInfo.this.javaName == null) {
/* 155:402 */           for (int idx = 0; idx < this.m_alreadyKnown.length; idx++)
/* 156:    */           {
/* 157:403 */             this.m_alreadyKnown[idx] = true;
/* 158:404 */             this.m_isInEncoding[idx] = true;
/* 159:    */           }
/* 160:    */         }
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static boolean inEncoding(char ch, String encoding)
/* 166:    */   {
/* 167:    */     boolean isInEncoding;
/* 168:    */     try
/* 169:    */     {
/* 170:428 */       char[] cArray = new char[1];
/* 171:429 */       cArray[0] = ch;
/* 172:    */       
/* 173:431 */       String s = new String(cArray);
/* 174:    */       
/* 175:    */ 
/* 176:434 */       byte[] bArray = s.getBytes(encoding);
/* 177:435 */       isInEncoding = inEncoding(ch, bArray);
/* 178:    */     }
/* 179:    */     catch (Exception e)
/* 180:    */     {
/* 181:438 */       isInEncoding = false;
/* 182:443 */       if (encoding == null) {
/* 183:444 */         isInEncoding = true;
/* 184:    */       }
/* 185:    */     }
/* 186:446 */     return isInEncoding;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static boolean inEncoding(char high, char low, String encoding)
/* 190:    */   {
/* 191:    */     boolean isInEncoding;
/* 192:    */     try
/* 193:    */     {
/* 194:467 */       char[] cArray = new char[2];
/* 195:468 */       cArray[0] = high;
/* 196:469 */       cArray[1] = low;
/* 197:    */       
/* 198:471 */       String s = new String(cArray);
/* 199:    */       
/* 200:    */ 
/* 201:474 */       byte[] bArray = s.getBytes(encoding);
/* 202:475 */       isInEncoding = inEncoding(high, bArray);
/* 203:    */     }
/* 204:    */     catch (Exception e)
/* 205:    */     {
/* 206:477 */       isInEncoding = false;
/* 207:    */     }
/* 208:480 */     return isInEncoding;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private static boolean inEncoding(char ch, byte[] data)
/* 212:    */   {
/* 213:    */     boolean isInEncoding;
/* 214:500 */     if ((data == null) || (data.length == 0)) {
/* 215:501 */       isInEncoding = false;
/* 216:504 */     } else if (data[0] == 0) {
/* 217:505 */       isInEncoding = false;
/* 218:506 */     } else if ((data[0] == 63) && (ch != '?')) {
/* 219:507 */       isInEncoding = false;
/* 220:    */     } else {
/* 221:527 */       isInEncoding = true;
/* 222:    */     }
/* 223:530 */     return isInEncoding;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public final char getHighChar()
/* 227:    */   {
/* 228:559 */     return this.m_highCharInContiguousGroup;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private static abstract interface InEncoding
/* 232:    */   {
/* 233:    */     public abstract boolean isInEncoding(char paramChar);
/* 234:    */     
/* 235:    */     public abstract boolean isInEncoding(char paramChar1, char paramChar2);
/* 236:    */   }
/* 237:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.EncodingInfo
 * JD-Core Version:    0.7.0.1
 */