/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ 
/*   5:    */ public class XML11Char
/*   6:    */ {
/*   7: 53 */   private static final byte[] XML11CHARS = new byte[65536];
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
/*  20: 91 */     Arrays.fill(XML11CHARS, 1, 9, (byte)17);
/*  21: 92 */     XML11CHARS[9] = 35;
/*  22: 93 */     XML11CHARS[10] = 3;
/*  23: 94 */     Arrays.fill(XML11CHARS, 11, 13, (byte)17);
/*  24: 95 */     XML11CHARS[13] = 3;
/*  25: 96 */     Arrays.fill(XML11CHARS, 14, 32, (byte)17);
/*  26: 97 */     XML11CHARS[32] = 35;
/*  27: 98 */     Arrays.fill(XML11CHARS, 33, 38, (byte)33);
/*  28: 99 */     XML11CHARS[38] = 1;
/*  29:100 */     Arrays.fill(XML11CHARS, 39, 45, (byte)33);
/*  30:101 */     Arrays.fill(XML11CHARS, 45, 47, (byte)-87);
/*  31:102 */     XML11CHARS[47] = 33;
/*  32:103 */     Arrays.fill(XML11CHARS, 48, 58, (byte)-87);
/*  33:104 */     XML11CHARS[58] = 45;
/*  34:105 */     XML11CHARS[59] = 33;
/*  35:106 */     XML11CHARS[60] = 1;
/*  36:107 */     Arrays.fill(XML11CHARS, 61, 65, (byte)33);
/*  37:108 */     Arrays.fill(XML11CHARS, 65, 91, (byte)-19);
/*  38:109 */     Arrays.fill(XML11CHARS, 91, 93, (byte)33);
/*  39:110 */     XML11CHARS[93] = 1;
/*  40:111 */     XML11CHARS[94] = 33;
/*  41:112 */     XML11CHARS[95] = -19;
/*  42:113 */     XML11CHARS[96] = 33;
/*  43:114 */     Arrays.fill(XML11CHARS, 97, 123, (byte)-19);
/*  44:115 */     Arrays.fill(XML11CHARS, 123, 127, (byte)33);
/*  45:116 */     Arrays.fill(XML11CHARS, 127, 133, (byte)17);
/*  46:117 */     XML11CHARS[''] = 35;
/*  47:118 */     Arrays.fill(XML11CHARS, 134, 160, (byte)17);
/*  48:119 */     Arrays.fill(XML11CHARS, 160, 183, (byte)33);
/*  49:120 */     XML11CHARS['·'] = -87;
/*  50:121 */     Arrays.fill(XML11CHARS, 184, 192, (byte)33);
/*  51:122 */     Arrays.fill(XML11CHARS, 192, 215, (byte)-19);
/*  52:123 */     XML11CHARS['×'] = 33;
/*  53:124 */     Arrays.fill(XML11CHARS, 216, 247, (byte)-19);
/*  54:125 */     XML11CHARS['÷'] = 33;
/*  55:126 */     Arrays.fill(XML11CHARS, 248, 768, (byte)-19);
/*  56:127 */     Arrays.fill(XML11CHARS, 768, 880, (byte)-87);
/*  57:128 */     Arrays.fill(XML11CHARS, 880, 894, (byte)-19);
/*  58:129 */     XML11CHARS[894] = 33;
/*  59:130 */     Arrays.fill(XML11CHARS, 895, 8192, (byte)-19);
/*  60:131 */     Arrays.fill(XML11CHARS, 8192, 8204, (byte)33);
/*  61:132 */     Arrays.fill(XML11CHARS, 8204, 8206, (byte)-19);
/*  62:133 */     Arrays.fill(XML11CHARS, 8206, 8232, (byte)33);
/*  63:134 */     XML11CHARS[8232] = 35;
/*  64:135 */     Arrays.fill(XML11CHARS, 8233, 8255, (byte)33);
/*  65:136 */     Arrays.fill(XML11CHARS, 8255, 8257, (byte)-87);
/*  66:137 */     Arrays.fill(XML11CHARS, 8257, 8304, (byte)33);
/*  67:138 */     Arrays.fill(XML11CHARS, 8304, 8592, (byte)-19);
/*  68:139 */     Arrays.fill(XML11CHARS, 8592, 11264, (byte)33);
/*  69:140 */     Arrays.fill(XML11CHARS, 11264, 12272, (byte)-19);
/*  70:141 */     Arrays.fill(XML11CHARS, 12272, 12289, (byte)33);
/*  71:142 */     Arrays.fill(XML11CHARS, 12289, 55296, (byte)-19);
/*  72:143 */     Arrays.fill(XML11CHARS, 57344, 63744, (byte)33);
/*  73:144 */     Arrays.fill(XML11CHARS, 63744, 64976, (byte)-19);
/*  74:145 */     Arrays.fill(XML11CHARS, 64976, 65008, (byte)33);
/*  75:146 */     Arrays.fill(XML11CHARS, 65008, 65534, (byte)-19);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean isXML11Space(int c)
/*  79:    */   {
/*  80:161 */     return (c < 65536) && ((XML11CHARS[c] & 0x2) != 0);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static boolean isXML11Valid(int c)
/*  84:    */   {
/*  85:175 */     return ((c < 65536) && ((XML11CHARS[c] & 0x1) != 0)) || ((65536 <= c) && (c <= 1114111));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static boolean isXML11Invalid(int c)
/*  89:    */   {
/*  90:185 */     return !isXML11Valid(c);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static boolean isXML11ValidLiteral(int c)
/*  94:    */   {
/*  95:197 */     return ((c < 65536) && ((XML11CHARS[c] & 0x1) != 0) && ((XML11CHARS[c] & 0x10) == 0)) || ((65536 <= c) && (c <= 1114111));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static boolean isXML11Content(int c)
/*  99:    */   {
/* 100:208 */     return ((c < 65536) && ((XML11CHARS[c] & 0x20) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static boolean isXML11InternalEntityContent(int c)
/* 104:    */   {
/* 105:219 */     return ((c < 65536) && ((XML11CHARS[c] & 0x30) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static boolean isXML11NameStart(int c)
/* 109:    */   {
/* 110:231 */     return ((c < 65536) && ((XML11CHARS[c] & 0x4) != 0)) || ((65536 <= c) && (c < 983040));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean isXML11Name(int c)
/* 114:    */   {
/* 115:243 */     return ((c < 65536) && ((XML11CHARS[c] & 0x8) != 0)) || ((c >= 65536) && (c < 983040));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static boolean isXML11NCNameStart(int c)
/* 119:    */   {
/* 120:255 */     return ((c < 65536) && ((XML11CHARS[c] & 0x40) != 0)) || ((65536 <= c) && (c < 983040));
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static boolean isXML11NCName(int c)
/* 124:    */   {
/* 125:267 */     return ((c < 65536) && ((XML11CHARS[c] & 0x80) != 0)) || ((65536 <= c) && (c < 983040));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static boolean isXML11NameHighSurrogate(int c)
/* 129:    */   {
/* 130:280 */     return (55296 <= c) && (c <= 56191);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static boolean isXML11ValidName(String name)
/* 134:    */   {
/* 135:294 */     int length = name.length();
/* 136:295 */     if (length == 0) {
/* 137:296 */       return false;
/* 138:    */     }
/* 139:297 */     int i = 1;
/* 140:298 */     char ch = name.charAt(0);
/* 141:299 */     if (!isXML11NameStart(ch)) {
/* 142:300 */       if ((length > 1) && (isXML11NameHighSurrogate(ch)))
/* 143:    */       {
/* 144:301 */         char ch2 = name.charAt(1);
/* 145:302 */         if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NameStart(XMLChar.supplemental(ch, ch2)))) {
/* 146:304 */           return false;
/* 147:    */         }
/* 148:306 */         i = 2;
/* 149:    */       }
/* 150:    */     }
/* 151:312 */     while (i < length)
/* 152:    */     {
/* 153:313 */       ch = name.charAt(i);
/* 154:314 */       if (!isXML11Name(ch))
/* 155:    */       {
/* 156:315 */         i++;
/* 157:315 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 158:    */         {
/* 159:316 */           char ch2 = name.charAt(i);
/* 160:317 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11Name(XMLChar.supplemental(ch, ch2)))) {
/* 161:319 */             return false;
/* 162:    */           }
/* 163:    */         }
/* 164:    */         else
/* 165:    */         {
/* 166:323 */           return false;
/* 167:    */         }
/* 168:    */       }
/* 169:326 */       i++;
/* 170:    */     }
/* 171:328 */     return true;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static boolean isXML11ValidNCName(String ncName)
/* 175:    */   {
/* 176:344 */     int length = ncName.length();
/* 177:345 */     if (length == 0) {
/* 178:346 */       return false;
/* 179:    */     }
/* 180:347 */     int i = 1;
/* 181:348 */     char ch = ncName.charAt(0);
/* 182:349 */     if (!isXML11NCNameStart(ch)) {
/* 183:350 */       if ((length > 1) && (isXML11NameHighSurrogate(ch)))
/* 184:    */       {
/* 185:351 */         char ch2 = ncName.charAt(1);
/* 186:352 */         if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NCNameStart(XMLChar.supplemental(ch, ch2)))) {
/* 187:354 */           return false;
/* 188:    */         }
/* 189:356 */         i = 2;
/* 190:    */       }
/* 191:    */     }
/* 192:362 */     while (i < length)
/* 193:    */     {
/* 194:363 */       ch = ncName.charAt(i);
/* 195:364 */       if (!isXML11NCName(ch))
/* 196:    */       {
/* 197:365 */         i++;
/* 198:365 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 199:    */         {
/* 200:366 */           char ch2 = ncName.charAt(i);
/* 201:367 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11NCName(XMLChar.supplemental(ch, ch2)))) {
/* 202:369 */             return false;
/* 203:    */           }
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:373 */           return false;
/* 208:    */         }
/* 209:    */       }
/* 210:376 */       i++;
/* 211:    */     }
/* 212:378 */     return true;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static boolean isXML11ValidNmtoken(String nmtoken)
/* 216:    */   {
/* 217:392 */     int length = nmtoken.length();
/* 218:393 */     if (length == 0) {
/* 219:394 */       return false;
/* 220:    */     }
/* 221:395 */     for (int i = 0; i < length; i++)
/* 222:    */     {
/* 223:396 */       char ch = nmtoken.charAt(i);
/* 224:397 */       if (!isXML11Name(ch))
/* 225:    */       {
/* 226:398 */         i++;
/* 227:398 */         if ((i < length) && (isXML11NameHighSurrogate(ch)))
/* 228:    */         {
/* 229:399 */           char ch2 = nmtoken.charAt(i);
/* 230:400 */           if ((!XMLChar.isLowSurrogate(ch2)) || (!isXML11Name(XMLChar.supplemental(ch, ch2)))) {
/* 231:402 */             return false;
/* 232:    */           }
/* 233:    */         }
/* 234:    */         else
/* 235:    */         {
/* 236:406 */           return false;
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:410 */     return true;
/* 241:    */   }
/* 242:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.XML11Char
 * JD-Core Version:    0.7.0.1
 */