/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ 
/*   5:    */ public class XML11Char
/*   6:    */ {
/*   7: 48 */   private static final byte[] XML11CHARS = new byte[65536];
/*   8:    */   public static final int MASK_XML11_VALID = 1;
/*   9:    */   public static final int MASK_XML11_SPACE = 2;
/*  10:    */   public static final int MASK_XML11_NAME_START = 4;
/*  11:    */   public static final int MASK_XML11_NAME = 8;
/*  12:    */   public static final int MASK_XML11_CONTROL = 16;
/*  13:    */   public static final int MASK_XML11_CONTENT = 32;
/*  14:    */   public static final int MASK_XML11_NCNAME_START = 64;
/*  15:    */   public static final int MASK_XML11_NCNAME = 128;
/*  16:    */   public static final int MASK_XML11_CONTENT_INTERNAL = 48;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 86 */     Arrays.fill(XML11CHARS, 1, 9, (byte)17);
/*  21: 87 */     XML11CHARS[9] = 35;
/*  22: 88 */     XML11CHARS[10] = 3;
/*  23: 89 */     Arrays.fill(XML11CHARS, 11, 13, (byte)17);
/*  24: 90 */     XML11CHARS[13] = 3;
/*  25: 91 */     Arrays.fill(XML11CHARS, 14, 32, (byte)17);
/*  26: 92 */     XML11CHARS[32] = 35;
/*  27: 93 */     Arrays.fill(XML11CHARS, 33, 38, (byte)33);
/*  28: 94 */     XML11CHARS[38] = 1;
/*  29: 95 */     Arrays.fill(XML11CHARS, 39, 45, (byte)33);
/*  30: 96 */     Arrays.fill(XML11CHARS, 45, 47, (byte)-87);
/*  31: 97 */     XML11CHARS[47] = 33;
/*  32: 98 */     Arrays.fill(XML11CHARS, 48, 58, (byte)-87);
/*  33: 99 */     XML11CHARS[58] = 45;
/*  34:100 */     XML11CHARS[59] = 33;
/*  35:101 */     XML11CHARS[60] = 1;
/*  36:102 */     Arrays.fill(XML11CHARS, 61, 65, (byte)33);
/*  37:103 */     Arrays.fill(XML11CHARS, 65, 91, (byte)-19);
/*  38:104 */     Arrays.fill(XML11CHARS, 91, 93, (byte)33);
/*  39:105 */     XML11CHARS[93] = 1;
/*  40:106 */     XML11CHARS[94] = 33;
/*  41:107 */     XML11CHARS[95] = -19;
/*  42:108 */     XML11CHARS[96] = 33;
/*  43:109 */     Arrays.fill(XML11CHARS, 97, 123, (byte)-19);
/*  44:110 */     Arrays.fill(XML11CHARS, 123, 127, (byte)33);
/*  45:111 */     Arrays.fill(XML11CHARS, 127, 133, (byte)17);
/*  46:112 */     XML11CHARS[''] = 35;
/*  47:113 */     Arrays.fill(XML11CHARS, 134, 160, (byte)17);
/*  48:114 */     Arrays.fill(XML11CHARS, 160, 183, (byte)33);
/*  49:115 */     XML11CHARS['·'] = -87;
/*  50:116 */     Arrays.fill(XML11CHARS, 184, 192, (byte)33);
/*  51:117 */     Arrays.fill(XML11CHARS, 192, 215, (byte)-19);
/*  52:118 */     XML11CHARS['×'] = 33;
/*  53:119 */     Arrays.fill(XML11CHARS, 216, 247, (byte)-19);
/*  54:120 */     XML11CHARS['÷'] = 33;
/*  55:121 */     Arrays.fill(XML11CHARS, 248, 768, (byte)-19);
/*  56:122 */     Arrays.fill(XML11CHARS, 768, 880, (byte)-87);
/*  57:123 */     Arrays.fill(XML11CHARS, 880, 894, (byte)-19);
/*  58:124 */     XML11CHARS[894] = 33;
/*  59:125 */     Arrays.fill(XML11CHARS, 895, 8192, (byte)-19);
/*  60:126 */     Arrays.fill(XML11CHARS, 8192, 8204, (byte)33);
/*  61:127 */     Arrays.fill(XML11CHARS, 8204, 8206, (byte)-19);
/*  62:128 */     Arrays.fill(XML11CHARS, 8206, 8232, (byte)33);
/*  63:129 */     XML11CHARS[8232] = 35;
/*  64:130 */     Arrays.fill(XML11CHARS, 8233, 8255, (byte)33);
/*  65:131 */     Arrays.fill(XML11CHARS, 8255, 8257, (byte)-87);
/*  66:132 */     Arrays.fill(XML11CHARS, 8257, 8304, (byte)33);
/*  67:133 */     Arrays.fill(XML11CHARS, 8304, 8592, (byte)-19);
/*  68:134 */     Arrays.fill(XML11CHARS, 8592, 11264, (byte)33);
/*  69:135 */     Arrays.fill(XML11CHARS, 11264, 12272, (byte)-19);
/*  70:136 */     Arrays.fill(XML11CHARS, 12272, 12289, (byte)33);
/*  71:137 */     Arrays.fill(XML11CHARS, 12289, 55296, (byte)-19);
/*  72:138 */     Arrays.fill(XML11CHARS, 57344, 63744, (byte)33);
/*  73:139 */     Arrays.fill(XML11CHARS, 63744, 64976, (byte)-19);
/*  74:140 */     Arrays.fill(XML11CHARS, 64976, 65008, (byte)33);
/*  75:141 */     Arrays.fill(XML11CHARS, 65008, 65534, (byte)-19);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean isXML11Space(int c)
/*  79:    */   {
/*  80:156 */     return (c < 65536) && ((XML11CHARS[c] & 0x2) != 0);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static boolean isXML11Valid(int c)
/*  84:    */   {
/*  85:170 */     return ((c < 65536) && ((XML11CHARS[c] & 0x1) != 0)) || ((65536 <= c) && (c <= 1114111));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static boolean isXML11Invalid(int c)
/*  89:    */   {
/*  90:180 */     return !isXML11Valid(c);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static boolean isXML11ValidLiteral(int c)
/*  94:    */   {
/*  95:192 */     return ((c < 65536) && ((XML11CHARS[c] & 0x1) != 0) && ((XML11CHARS[c] & 0x10) == 0)) || ((65536 <= c) && (c <= 1114111));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static boolean isXML11Content(int c)
/*  99:    */   {
/* 100:203 */     return ((c < 65536) && ((XML11CHARS[c] & 0x20) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static boolean isXML11InternalEntityContent(int c)
/* 104:    */   {
/* 105:214 */     return ((c < 65536) && ((XML11CHARS[c] & 0x30) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static boolean isXML11NameStart(int c)
/* 109:    */   {
/* 110:226 */     return ((c < 65536) && ((XML11CHARS[c] & 0x4) != 0)) || ((65536 <= c) && (c < 983040));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean isXML11Name(int c)
/* 114:    */   {
/* 115:238 */     return ((c < 65536) && ((XML11CHARS[c] & 0x8) != 0)) || ((c >= 65536) && (c < 983040));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static boolean isXML11NCNameStart(int c)
/* 119:    */   {
/* 120:250 */     return ((c < 65536) && ((XML11CHARS[c] & 0x40) != 0)) || ((65536 <= c) && (c < 983040));
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static boolean isXML11NCName(int c)
/* 124:    */   {
/* 125:262 */     return ((c < 65536) && ((XML11CHARS[c] & 0x80) != 0)) || ((65536 <= c) && (c < 983040));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static boolean isXML11NameHighSurrogate(int c)
/* 129:    */   {
/* 130:275 */     return (55296 <= c) && (c <= 56191);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static boolean isXML11ValidName(String name)
/* 134:    */   {
/* 135:289 */     int length = name.length();
/* 136:290 */     if (length == 0) {
/* 137:291 */       return false;
/* 138:    */     }
/* 139:292 */     int i = 1;
/* 140:293 */     char ch = name.charAt(0);
/* 141:294 */     if (!isXML11NameStart(ch)) {
/* 142:295 */       if ((length > 1) && (isXML11NameHighSurrogate(ch)))
/* 143:    */       {
/* 144:296 */         char ch2 = name.charAt(1);
/* 145:297 */         if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NameStart(XMLChar.supplemental(ch, ch2)))) {
/* 146:299 */           return false;
/* 147:    */         }
/* 148:301 */         i = 2;
/* 149:    */       }
/* 150:    */     }
/* 151:307 */     while (i < length)
/* 152:    */     {
/* 153:308 */       ch = name.charAt(i);
/* 154:309 */       if (!isXML11Name(ch))
/* 155:    */       {
/* 156:310 */         i++;
/* 157:310 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 158:    */         {
/* 159:311 */           char ch2 = name.charAt(i);
/* 160:312 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11Name(XMLChar.supplemental(ch, ch2)))) {
/* 161:314 */             return false;
/* 162:    */           }
/* 163:    */         }
/* 164:    */         else
/* 165:    */         {
/* 166:318 */           return false;
/* 167:    */         }
/* 168:    */       }
/* 169:321 */       i++;
/* 170:    */     }
/* 171:323 */     return true;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static boolean isXML11ValidNCName(String ncName)
/* 175:    */   {
/* 176:339 */     int length = ncName.length();
/* 177:340 */     if (length == 0) {
/* 178:341 */       return false;
/* 179:    */     }
/* 180:342 */     int i = 1;
/* 181:343 */     char ch = ncName.charAt(0);
/* 182:344 */     if (!isXML11NCNameStart(ch)) {
/* 183:345 */       if ((length > 1) && (isXML11NameHighSurrogate(ch)))
/* 184:    */       {
/* 185:346 */         char ch2 = ncName.charAt(1);
/* 186:347 */         if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NCNameStart(XMLChar.supplemental(ch, ch2)))) {
/* 187:349 */           return false;
/* 188:    */         }
/* 189:351 */         i = 2;
/* 190:    */       }
/* 191:    */     }
/* 192:357 */     while (i < length)
/* 193:    */     {
/* 194:358 */       ch = ncName.charAt(i);
/* 195:359 */       if (!isXML11NCName(ch))
/* 196:    */       {
/* 197:360 */         i++;
/* 198:360 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 199:    */         {
/* 200:361 */           char ch2 = ncName.charAt(i);
/* 201:362 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NCName(XMLChar.supplemental(ch, ch2)))) {
/* 202:364 */             return false;
/* 203:    */           }
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:368 */           return false;
/* 208:    */         }
/* 209:    */       }
/* 210:371 */       i++;
/* 211:    */     }
/* 212:373 */     return true;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static boolean isXML11ValidNmtoken(String nmtoken)
/* 216:    */   {
/* 217:387 */     int length = nmtoken.length();
/* 218:388 */     if (length == 0) {
/* 219:389 */       return false;
/* 220:    */     }
/* 221:390 */     for (int i = 0; i < length; i++)
/* 222:    */     {
/* 223:391 */       char ch = nmtoken.charAt(i);
/* 224:392 */       if (!isXML11Name(ch))
/* 225:    */       {
/* 226:393 */         i++;
/* 227:393 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 228:    */         {
/* 229:394 */           char ch2 = nmtoken.charAt(i);
/* 230:395 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11Name(XMLChar.supplemental(ch, ch2)))) {
/* 231:397 */             return false;
/* 232:    */           }
/* 233:    */         }
/* 234:    */         else
/* 235:    */         {
/* 236:401 */           return false;
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:405 */     return true;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static boolean isXML11ValidQName(String str)
/* 244:    */   {
/* 245:415 */     int colon = str.indexOf(':');
/* 246:417 */     if ((colon == 0) || (colon == str.length() - 1)) {
/* 247:418 */       return false;
/* 248:    */     }
/* 249:421 */     if (colon > 0)
/* 250:    */     {
/* 251:422 */       String prefix = str.substring(0, colon);
/* 252:423 */       String localPart = str.substring(colon + 1);
/* 253:424 */       return (isXML11ValidNCName(prefix)) && (isXML11ValidNCName(localPart));
/* 254:    */     }
/* 255:427 */     return isXML11ValidNCName(str);
/* 256:    */   }
/* 257:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XML11Char
 * JD-Core Version:    0.7.0.1
 */