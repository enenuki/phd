/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.regex.Matcher;
/*   8:    */ import java.util.regex.Pattern;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.http.impl.cookie.DateParseException;
/*  12:    */ import org.apache.http.impl.cookie.DateUtils;
/*  13:    */ 
/*  14:    */ public final class StringUtils
/*  15:    */ {
/*  16: 41 */   private static final Pattern HEX_COLOR = Pattern.compile("#([0-9a-fA-F]{3}|[0-9a-fA-F]{6})");
/*  17: 42 */   private static final Pattern RGB_COLOR = Pattern.compile("rgb\\s*?\\(\\s*?(\\d{1,3})\\s*?,\\s*?(\\d{1,3})\\s*?,\\s*?(\\d{1,3})\\s*?\\)");
/*  18: 44 */   private static final Log LOG = LogFactory.getLog(StringUtils.class);
/*  19:    */   
/*  20:    */   public static String escapeXmlChars(String s)
/*  21:    */   {
/*  22: 64 */     return org.apache.commons.lang.StringUtils.replaceEach(s, new String[] { "&", "<", ">" }, new String[] { "&amp;", "&lt;", "&gt;" });
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static String escapeXmlAttributeValue(String attValue)
/*  26:    */   {
/*  27: 75 */     int len = attValue.length();
/*  28: 76 */     StringBuilder sb = null;
/*  29: 77 */     for (int i = len - 1; i >= 0; i--)
/*  30:    */     {
/*  31: 78 */       char c = attValue.charAt(i);
/*  32: 79 */       String replacement = null;
/*  33: 80 */       if (c == '<') {
/*  34: 81 */         replacement = "&lt;";
/*  35: 83 */       } else if (c == '&') {
/*  36: 84 */         replacement = "&amp;";
/*  37: 86 */       } else if (c == '"') {
/*  38: 87 */         replacement = "&quot;";
/*  39:    */       }
/*  40: 90 */       if (replacement != null)
/*  41:    */       {
/*  42: 91 */         if (sb == null) {
/*  43: 92 */           sb = new StringBuilder(attValue);
/*  44:    */         }
/*  45: 94 */         sb.replace(i, i + 1, replacement);
/*  46:    */       }
/*  47:    */     }
/*  48: 98 */     if (sb != null) {
/*  49: 99 */       return sb.toString();
/*  50:    */     }
/*  51:101 */     return attValue;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static boolean containsWhitespace(String s)
/*  55:    */   {
/*  56:111 */     for (char c : s.toCharArray()) {
/*  57:112 */       if (Character.isWhitespace(c)) {
/*  58:113 */         return true;
/*  59:    */       }
/*  60:    */     }
/*  61:116 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static int indexOf(String s, char searchChar, int beginIndex, int endIndex)
/*  65:    */   {
/*  66:130 */     for (int i = beginIndex; i < endIndex; i++) {
/*  67:131 */       if (s.charAt(i) == searchChar) {
/*  68:132 */         return i;
/*  69:    */       }
/*  70:    */     }
/*  71:135 */     return -1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean isFloat(String s, boolean trim)
/*  75:    */   {
/*  76:146 */     if (trim) {
/*  77:147 */       s = s.trim();
/*  78:    */     }
/*  79:    */     boolean ok;
/*  80:    */     try
/*  81:    */     {
/*  82:152 */       Float.parseFloat(s);
/*  83:153 */       ok = true;
/*  84:    */     }
/*  85:    */     catch (NumberFormatException e)
/*  86:    */     {
/*  87:156 */       ok = false;
/*  88:    */     }
/*  89:159 */     return ok;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static boolean containsCaseInsensitive(Collection<String> strings, String string)
/*  93:    */   {
/*  94:170 */     string = string.toLowerCase();
/*  95:171 */     for (String s : strings) {
/*  96:172 */       if (s.equalsIgnoreCase(string)) {
/*  97:173 */         return true;
/*  98:    */       }
/*  99:    */     }
/* 100:176 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static Date parseHttpDate(String s)
/* 104:    */   {
/* 105:187 */     if (s == null) {
/* 106:188 */       return null;
/* 107:    */     }
/* 108:    */     try
/* 109:    */     {
/* 110:191 */       return DateUtils.parseDate(s);
/* 111:    */     }
/* 112:    */     catch (DateParseException e)
/* 113:    */     {
/* 114:194 */       LOG.warn("Unable to parse http date: '" + s + "'");
/* 115:    */     }
/* 116:195 */     return null;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static boolean isColorHexadecimal(String token)
/* 120:    */   {
/* 121:205 */     if (token == null) {
/* 122:206 */       return false;
/* 123:    */     }
/* 124:208 */     return HEX_COLOR.matcher(token.trim()).matches();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Color asColorHexadecimal(String token)
/* 128:    */   {
/* 129:217 */     if (token == null) {
/* 130:218 */       return null;
/* 131:    */     }
/* 132:220 */     Matcher tmpMatcher = HEX_COLOR.matcher(token);
/* 133:221 */     boolean tmpFound = tmpMatcher.matches();
/* 134:222 */     if (!tmpFound) {
/* 135:223 */       return null;
/* 136:    */     }
/* 137:226 */     String tmpHex = tmpMatcher.group(1);
/* 138:227 */     if (tmpHex.length() == 6)
/* 139:    */     {
/* 140:228 */       int tmpRed = Integer.parseInt(tmpHex.substring(0, 2), 16);
/* 141:229 */       int tmpGreen = Integer.parseInt(tmpHex.substring(2, 4), 16);
/* 142:230 */       int tmpBlue = Integer.parseInt(tmpHex.substring(4, 6), 16);
/* 143:231 */       Color tmpColor = new Color(tmpRed, tmpGreen, tmpBlue);
/* 144:232 */       return tmpColor;
/* 145:    */     }
/* 146:235 */     int tmpRed = Integer.parseInt(tmpHex.substring(0, 1) + tmpHex.substring(0, 1), 16);
/* 147:236 */     int tmpGreen = Integer.parseInt(tmpHex.substring(1, 2) + tmpHex.substring(1, 2), 16);
/* 148:237 */     int tmpBlue = Integer.parseInt(tmpHex.substring(2, 3) + tmpHex.substring(2, 3), 16);
/* 149:238 */     Color tmpColor = new Color(tmpRed, tmpGreen, tmpBlue);
/* 150:239 */     return tmpColor;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static boolean isColorRGB(String token)
/* 154:    */   {
/* 155:248 */     if (token == null) {
/* 156:249 */       return false;
/* 157:    */     }
/* 158:251 */     return RGB_COLOR.matcher(token.trim()).matches();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static Color asColorRGB(String token)
/* 162:    */   {
/* 163:260 */     if (token == null) {
/* 164:261 */       return null;
/* 165:    */     }
/* 166:263 */     Matcher tmpMatcher = RGB_COLOR.matcher(token);
/* 167:264 */     boolean tmpFound = tmpMatcher.matches();
/* 168:265 */     if (!tmpFound) {
/* 169:266 */       return null;
/* 170:    */     }
/* 171:269 */     int tmpRed = Integer.parseInt(tmpMatcher.group(1));
/* 172:270 */     int tmpGreen = Integer.parseInt(tmpMatcher.group(2));
/* 173:271 */     int tmpBlue = Integer.parseInt(tmpMatcher.group(3));
/* 174:272 */     Color tmpColor = new Color(tmpRed, tmpGreen, tmpBlue);
/* 175:273 */     return tmpColor;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static Color findColorRGB(String token)
/* 179:    */   {
/* 180:282 */     if (token == null) {
/* 181:283 */       return null;
/* 182:    */     }
/* 183:285 */     Matcher tmpMatcher = RGB_COLOR.matcher(token);
/* 184:286 */     boolean tmpFound = tmpMatcher.find();
/* 185:287 */     if (!tmpFound) {
/* 186:288 */       return null;
/* 187:    */     }
/* 188:291 */     int tmpRed = Integer.parseInt(tmpMatcher.group(1));
/* 189:292 */     int tmpGreen = Integer.parseInt(tmpMatcher.group(2));
/* 190:293 */     int tmpBlue = Integer.parseInt(tmpMatcher.group(3));
/* 191:294 */     Color tmpColor = new Color(tmpRed, tmpGreen, tmpBlue);
/* 192:295 */     return tmpColor;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static String formatColor(Color aColor)
/* 196:    */   {
/* 197:305 */     return "rgb(" + aColor.getRed() + ", " + aColor.getGreen() + ", " + aColor.getBlue() + ")";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static String formatHttpDate(Date date)
/* 201:    */   {
/* 202:315 */     WebAssert.notNull("date", date);
/* 203:316 */     return DateUtils.formatDate(date);
/* 204:    */   }
/* 205:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.StringUtils
 * JD-Core Version:    0.7.0.1
 */