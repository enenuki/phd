/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ public class CharUtils
/*   4:    */ {
/*   5:    */   private static final String CHAR_STRING = "";
/*   6: 51 */   private static final String[] CHAR_STRING_ARRAY = new String[''];
/*   7: 52 */   private static final Character[] CHAR_ARRAY = new Character[''];
/*   8:    */   public static final char LF = '\n';
/*   9:    */   public static final char CR = '\r';
/*  10:    */   
/*  11:    */   static
/*  12:    */   {
/*  13: 74 */     for (int i = 127; i >= 0; i--)
/*  14:    */     {
/*  15: 75 */       CHAR_STRING_ARRAY[i] = "".substring(i, i + 1);
/*  16: 76 */       CHAR_ARRAY[i] = new Character((char)i);
/*  17:    */     }
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Character toCharacterObject(char ch)
/*  21:    */   {
/*  22:107 */     if (ch < CHAR_ARRAY.length) {
/*  23:108 */       return CHAR_ARRAY[ch];
/*  24:    */     }
/*  25:110 */     return new Character(ch);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static Character toCharacterObject(String str)
/*  29:    */   {
/*  30:131 */     if (StringUtils.isEmpty(str)) {
/*  31:132 */       return null;
/*  32:    */     }
/*  33:134 */     return toCharacterObject(str.charAt(0));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static char toChar(Character ch)
/*  37:    */   {
/*  38:152 */     if (ch == null) {
/*  39:153 */       throw new IllegalArgumentException("The Character must not be null");
/*  40:    */     }
/*  41:155 */     return ch.charValue();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static char toChar(Character ch, char defaultValue)
/*  45:    */   {
/*  46:172 */     if (ch == null) {
/*  47:173 */       return defaultValue;
/*  48:    */     }
/*  49:175 */     return ch.charValue();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static char toChar(String str)
/*  53:    */   {
/*  54:195 */     if (StringUtils.isEmpty(str)) {
/*  55:196 */       throw new IllegalArgumentException("The String must not be empty");
/*  56:    */     }
/*  57:198 */     return str.charAt(0);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static char toChar(String str, char defaultValue)
/*  61:    */   {
/*  62:217 */     if (StringUtils.isEmpty(str)) {
/*  63:218 */       return defaultValue;
/*  64:    */     }
/*  65:220 */     return str.charAt(0);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static int toIntValue(char ch)
/*  69:    */   {
/*  70:240 */     if (!isAsciiNumeric(ch)) {
/*  71:241 */       throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
/*  72:    */     }
/*  73:243 */     return ch - '0';
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static int toIntValue(char ch, int defaultValue)
/*  77:    */   {
/*  78:262 */     if (!isAsciiNumeric(ch)) {
/*  79:263 */       return defaultValue;
/*  80:    */     }
/*  81:265 */     return ch - '0';
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static int toIntValue(Character ch)
/*  85:    */   {
/*  86:285 */     if (ch == null) {
/*  87:286 */       throw new IllegalArgumentException("The character must not be null");
/*  88:    */     }
/*  89:288 */     return toIntValue(ch.charValue());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static int toIntValue(Character ch, int defaultValue)
/*  93:    */   {
/*  94:308 */     if (ch == null) {
/*  95:309 */       return defaultValue;
/*  96:    */     }
/*  97:311 */     return toIntValue(ch.charValue(), defaultValue);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String toString(char ch)
/* 101:    */   {
/* 102:330 */     if (ch < '') {
/* 103:331 */       return CHAR_STRING_ARRAY[ch];
/* 104:    */     }
/* 105:333 */     return new String(new char[] { ch });
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String toString(Character ch)
/* 109:    */   {
/* 110:354 */     if (ch == null) {
/* 111:355 */       return null;
/* 112:    */     }
/* 113:357 */     return toString(ch.charValue());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String unicodeEscaped(char ch)
/* 117:    */   {
/* 118:375 */     if (ch < '\020') {
/* 119:376 */       return "\\u000" + Integer.toHexString(ch);
/* 120:    */     }
/* 121:377 */     if (ch < 'Ā') {
/* 122:378 */       return "\\u00" + Integer.toHexString(ch);
/* 123:    */     }
/* 124:379 */     if (ch < 'က') {
/* 125:380 */       return "\\u0" + Integer.toHexString(ch);
/* 126:    */     }
/* 127:382 */     return "\\u" + Integer.toHexString(ch);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static String unicodeEscaped(Character ch)
/* 131:    */   {
/* 132:402 */     if (ch == null) {
/* 133:403 */       return null;
/* 134:    */     }
/* 135:405 */     return unicodeEscaped(ch.charValue());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static boolean isAscii(char ch)
/* 139:    */   {
/* 140:425 */     return ch < '';
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static boolean isAsciiPrintable(char ch)
/* 144:    */   {
/* 145:444 */     return (ch >= ' ') && (ch < '');
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static boolean isAsciiControl(char ch)
/* 149:    */   {
/* 150:463 */     return (ch < ' ') || (ch == '');
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static boolean isAsciiAlpha(char ch)
/* 154:    */   {
/* 155:482 */     return ((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z'));
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static boolean isAsciiAlphaUpper(char ch)
/* 159:    */   {
/* 160:501 */     return (ch >= 'A') && (ch <= 'Z');
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static boolean isAsciiAlphaLower(char ch)
/* 164:    */   {
/* 165:520 */     return (ch >= 'a') && (ch <= 'z');
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static boolean isAsciiNumeric(char ch)
/* 169:    */   {
/* 170:539 */     return (ch >= '0') && (ch <= '9');
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static boolean isAsciiAlphanumeric(char ch)
/* 174:    */   {
/* 175:558 */     return ((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z')) || ((ch >= '0') && (ch <= '9'));
/* 176:    */   }
/* 177:    */   
/* 178:    */   static boolean isHighSurrogate(char ch)
/* 179:    */   {
/* 180:573 */     return (55296 <= ch) && (56319 >= ch);
/* 181:    */   }
/* 182:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.CharUtils
 * JD-Core Version:    0.7.0.1
 */