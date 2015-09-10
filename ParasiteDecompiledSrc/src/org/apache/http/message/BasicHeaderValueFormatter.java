/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import org.apache.http.HeaderElement;
/*   4:    */ import org.apache.http.NameValuePair;
/*   5:    */ import org.apache.http.util.CharArrayBuffer;
/*   6:    */ 
/*   7:    */ public class BasicHeaderValueFormatter
/*   8:    */   implements HeaderValueFormatter
/*   9:    */ {
/*  10: 50 */   public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
/*  11:    */   public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
/*  12:    */   public static final String UNSAFE_CHARS = "\"\\";
/*  13:    */   
/*  14:    */   public static final String formatElements(HeaderElement[] elems, boolean quote, HeaderValueFormatter formatter)
/*  15:    */   {
/*  16: 88 */     if (formatter == null) {
/*  17: 89 */       formatter = DEFAULT;
/*  18:    */     }
/*  19: 90 */     return formatter.formatElements(null, elems, quote).toString();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CharArrayBuffer formatElements(CharArrayBuffer buffer, HeaderElement[] elems, boolean quote)
/*  23:    */   {
/*  24: 98 */     if (elems == null) {
/*  25: 99 */       throw new IllegalArgumentException("Header element array must not be null.");
/*  26:    */     }
/*  27:103 */     int len = estimateElementsLen(elems);
/*  28:104 */     if (buffer == null) {
/*  29:105 */       buffer = new CharArrayBuffer(len);
/*  30:    */     } else {
/*  31:107 */       buffer.ensureCapacity(len);
/*  32:    */     }
/*  33:110 */     for (int i = 0; i < elems.length; i++)
/*  34:    */     {
/*  35:111 */       if (i > 0) {
/*  36:112 */         buffer.append(", ");
/*  37:    */       }
/*  38:114 */       formatHeaderElement(buffer, elems[i], quote);
/*  39:    */     }
/*  40:117 */     return buffer;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected int estimateElementsLen(HeaderElement[] elems)
/*  44:    */   {
/*  45:129 */     if ((elems == null) || (elems.length < 1)) {
/*  46:130 */       return 0;
/*  47:    */     }
/*  48:132 */     int result = (elems.length - 1) * 2;
/*  49:133 */     for (int i = 0; i < elems.length; i++) {
/*  50:134 */       result += estimateHeaderElementLen(elems[i]);
/*  51:    */     }
/*  52:137 */     return result;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static final String formatHeaderElement(HeaderElement elem, boolean quote, HeaderValueFormatter formatter)
/*  56:    */   {
/*  57:157 */     if (formatter == null) {
/*  58:158 */       formatter = DEFAULT;
/*  59:    */     }
/*  60:159 */     return formatter.formatHeaderElement(null, elem, quote).toString();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public CharArrayBuffer formatHeaderElement(CharArrayBuffer buffer, HeaderElement elem, boolean quote)
/*  64:    */   {
/*  65:167 */     if (elem == null) {
/*  66:168 */       throw new IllegalArgumentException("Header element must not be null.");
/*  67:    */     }
/*  68:172 */     int len = estimateHeaderElementLen(elem);
/*  69:173 */     if (buffer == null) {
/*  70:174 */       buffer = new CharArrayBuffer(len);
/*  71:    */     } else {
/*  72:176 */       buffer.ensureCapacity(len);
/*  73:    */     }
/*  74:179 */     buffer.append(elem.getName());
/*  75:180 */     String value = elem.getValue();
/*  76:181 */     if (value != null)
/*  77:    */     {
/*  78:182 */       buffer.append('=');
/*  79:183 */       doFormatValue(buffer, value, quote);
/*  80:    */     }
/*  81:186 */     int parcnt = elem.getParameterCount();
/*  82:187 */     if (parcnt > 0) {
/*  83:188 */       for (int i = 0; i < parcnt; i++)
/*  84:    */       {
/*  85:189 */         buffer.append("; ");
/*  86:190 */         formatNameValuePair(buffer, elem.getParameter(i), quote);
/*  87:    */       }
/*  88:    */     }
/*  89:194 */     return buffer;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected int estimateHeaderElementLen(HeaderElement elem)
/*  93:    */   {
/*  94:206 */     if (elem == null) {
/*  95:207 */       return 0;
/*  96:    */     }
/*  97:209 */     int result = elem.getName().length();
/*  98:210 */     String value = elem.getValue();
/*  99:211 */     if (value != null) {
/* 100:213 */       result += 3 + value.length();
/* 101:    */     }
/* 102:216 */     int parcnt = elem.getParameterCount();
/* 103:217 */     if (parcnt > 0) {
/* 104:218 */       for (int i = 0; i < parcnt; i++) {
/* 105:219 */         result += 2 + estimateNameValuePairLen(elem.getParameter(i));
/* 106:    */       }
/* 107:    */     }
/* 108:224 */     return result;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static final String formatParameters(NameValuePair[] nvps, boolean quote, HeaderValueFormatter formatter)
/* 112:    */   {
/* 113:245 */     if (formatter == null) {
/* 114:246 */       formatter = DEFAULT;
/* 115:    */     }
/* 116:247 */     return formatter.formatParameters(null, nvps, quote).toString();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public CharArrayBuffer formatParameters(CharArrayBuffer buffer, NameValuePair[] nvps, boolean quote)
/* 120:    */   {
/* 121:255 */     if (nvps == null) {
/* 122:256 */       throw new IllegalArgumentException("Parameters must not be null.");
/* 123:    */     }
/* 124:260 */     int len = estimateParametersLen(nvps);
/* 125:261 */     if (buffer == null) {
/* 126:262 */       buffer = new CharArrayBuffer(len);
/* 127:    */     } else {
/* 128:264 */       buffer.ensureCapacity(len);
/* 129:    */     }
/* 130:267 */     for (int i = 0; i < nvps.length; i++)
/* 131:    */     {
/* 132:268 */       if (i > 0) {
/* 133:269 */         buffer.append("; ");
/* 134:    */       }
/* 135:271 */       formatNameValuePair(buffer, nvps[i], quote);
/* 136:    */     }
/* 137:274 */     return buffer;
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected int estimateParametersLen(NameValuePair[] nvps)
/* 141:    */   {
/* 142:286 */     if ((nvps == null) || (nvps.length < 1)) {
/* 143:287 */       return 0;
/* 144:    */     }
/* 145:289 */     int result = (nvps.length - 1) * 2;
/* 146:290 */     for (int i = 0; i < nvps.length; i++) {
/* 147:291 */       result += estimateNameValuePairLen(nvps[i]);
/* 148:    */     }
/* 149:294 */     return result;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static final String formatNameValuePair(NameValuePair nvp, boolean quote, HeaderValueFormatter formatter)
/* 153:    */   {
/* 154:313 */     if (formatter == null) {
/* 155:314 */       formatter = DEFAULT;
/* 156:    */     }
/* 157:315 */     return formatter.formatNameValuePair(null, nvp, quote).toString();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public CharArrayBuffer formatNameValuePair(CharArrayBuffer buffer, NameValuePair nvp, boolean quote)
/* 161:    */   {
/* 162:323 */     if (nvp == null) {
/* 163:324 */       throw new IllegalArgumentException("NameValuePair must not be null.");
/* 164:    */     }
/* 165:328 */     int len = estimateNameValuePairLen(nvp);
/* 166:329 */     if (buffer == null) {
/* 167:330 */       buffer = new CharArrayBuffer(len);
/* 168:    */     } else {
/* 169:332 */       buffer.ensureCapacity(len);
/* 170:    */     }
/* 171:335 */     buffer.append(nvp.getName());
/* 172:336 */     String value = nvp.getValue();
/* 173:337 */     if (value != null)
/* 174:    */     {
/* 175:338 */       buffer.append('=');
/* 176:339 */       doFormatValue(buffer, value, quote);
/* 177:    */     }
/* 178:342 */     return buffer;
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected int estimateNameValuePairLen(NameValuePair nvp)
/* 182:    */   {
/* 183:354 */     if (nvp == null) {
/* 184:355 */       return 0;
/* 185:    */     }
/* 186:357 */     int result = nvp.getName().length();
/* 187:358 */     String value = nvp.getValue();
/* 188:359 */     if (value != null) {
/* 189:361 */       result += 3 + value.length();
/* 190:    */     }
/* 191:363 */     return result;
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected void doFormatValue(CharArrayBuffer buffer, String value, boolean quote)
/* 195:    */   {
/* 196:381 */     if (!quote) {
/* 197:382 */       for (int i = 0; (i < value.length()) && (!quote); i++) {
/* 198:383 */         quote = isSeparator(value.charAt(i));
/* 199:    */       }
/* 200:    */     }
/* 201:387 */     if (quote) {
/* 202:388 */       buffer.append('"');
/* 203:    */     }
/* 204:390 */     for (int i = 0; i < value.length(); i++)
/* 205:    */     {
/* 206:391 */       char ch = value.charAt(i);
/* 207:392 */       if (isUnsafe(ch)) {
/* 208:393 */         buffer.append('\\');
/* 209:    */       }
/* 210:395 */       buffer.append(ch);
/* 211:    */     }
/* 212:397 */     if (quote) {
/* 213:398 */       buffer.append('"');
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected boolean isSeparator(char ch)
/* 218:    */   {
/* 219:412 */     return " ;,:@()<>\\\"/[]?={}\t".indexOf(ch) >= 0;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected boolean isUnsafe(char ch)
/* 223:    */   {
/* 224:425 */     return "\"\\".indexOf(ch) >= 0;
/* 225:    */   }
/* 226:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeaderValueFormatter
 * JD-Core Version:    0.7.0.1
 */