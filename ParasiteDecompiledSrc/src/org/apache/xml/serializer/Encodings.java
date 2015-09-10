/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.io.UnsupportedEncodingException;
/*   8:    */ import java.io.Writer;
/*   9:    */ import java.net.MalformedURLException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Enumeration;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Properties;
/*  15:    */ import java.util.StringTokenizer;
/*  16:    */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*  17:    */ 
/*  18:    */ public final class Encodings
/*  19:    */ {
/*  20: 51 */   private static final String ENCODINGS_FILE = SerializerBase.PKG_PATH + "/Encodings.properties";
/*  21:    */   static final String DEFAULT_MIME_ENCODING = "UTF-8";
/*  22:    */   
/*  23:    */   static Writer getWriter(OutputStream output, String encoding)
/*  24:    */     throws UnsupportedEncodingException
/*  25:    */   {
/*  26: 69 */     for (int i = 0; i < _encodings.length; i++) {
/*  27: 71 */       if (_encodings[i].name.equalsIgnoreCase(encoding)) {
/*  28:    */         try
/*  29:    */         {
/*  30: 75 */           String javaName = _encodings[i].javaName;
/*  31: 76 */           return new OutputStreamWriter(output, javaName);
/*  32:    */         }
/*  33:    */         catch (IllegalArgumentException iae) {}catch (UnsupportedEncodingException usee) {}
/*  34:    */       }
/*  35:    */     }
/*  36:    */     try
/*  37:    */     {
/*  38: 93 */       return new OutputStreamWriter(output, encoding);
/*  39:    */     }
/*  40:    */     catch (IllegalArgumentException iae)
/*  41:    */     {
/*  42: 97 */       throw new UnsupportedEncodingException(encoding);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   static EncodingInfo getEncodingInfo(String encoding)
/*  47:    */   {
/*  48:118 */     String normalizedEncoding = toUpperCaseFast(encoding);
/*  49:119 */     EncodingInfo ei = (EncodingInfo)_encodingTableKeyJava.get(normalizedEncoding);
/*  50:120 */     if (ei == null) {
/*  51:121 */       ei = (EncodingInfo)_encodingTableKeyMime.get(normalizedEncoding);
/*  52:    */     }
/*  53:122 */     if (ei == null) {
/*  54:124 */       ei = new EncodingInfo(null, null, '\000');
/*  55:    */     }
/*  56:127 */     return ei;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static boolean isRecognizedEncoding(String encoding)
/*  60:    */   {
/*  61:141 */     String normalizedEncoding = encoding.toUpperCase();
/*  62:142 */     EncodingInfo ei = (EncodingInfo)_encodingTableKeyJava.get(normalizedEncoding);
/*  63:143 */     if (ei == null) {
/*  64:144 */       ei = (EncodingInfo)_encodingTableKeyMime.get(normalizedEncoding);
/*  65:    */     }
/*  66:145 */     if (ei != null) {
/*  67:146 */       return true;
/*  68:    */     }
/*  69:147 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static String toUpperCaseFast(String s)
/*  73:    */   {
/*  74:162 */     boolean different = false;
/*  75:163 */     int mx = s.length();
/*  76:164 */     char[] chars = new char[mx];
/*  77:165 */     for (int i = 0; i < mx; i++)
/*  78:    */     {
/*  79:166 */       char ch = s.charAt(i);
/*  80:168 */       if (('a' <= ch) && (ch <= 'z'))
/*  81:    */       {
/*  82:170 */         ch = (char)(ch + '￠');
/*  83:171 */         different = true;
/*  84:    */       }
/*  85:173 */       chars[i] = ch;
/*  86:    */     }
/*  87:    */     String upper;
/*  88:179 */     if (different) {
/*  89:180 */       upper = String.valueOf(chars);
/*  90:    */     } else {
/*  91:182 */       upper = s;
/*  92:    */     }
/*  93:184 */     return upper;
/*  94:    */   }
/*  95:    */   
/*  96:    */   static String getMimeEncoding(String encoding)
/*  97:    */   {
/*  98:212 */     if (null == encoding) {
/*  99:    */       try
/* 100:    */       {
/* 101:220 */         encoding = System.getProperty("file.encoding", "UTF8");
/* 102:222 */         if (null != encoding)
/* 103:    */         {
/* 104:232 */           String jencoding = (encoding.equalsIgnoreCase("Cp1252")) || (encoding.equalsIgnoreCase("ISO8859_1")) || (encoding.equalsIgnoreCase("8859_1")) || (encoding.equalsIgnoreCase("UTF8")) ? "UTF-8" : convertJava2MimeEncoding(encoding);
/* 105:    */           
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:240 */           encoding = null != jencoding ? jencoding : "UTF-8";
/* 113:    */         }
/* 114:    */         else
/* 115:    */         {
/* 116:245 */           encoding = "UTF-8";
/* 117:    */         }
/* 118:    */       }
/* 119:    */       catch (SecurityException se)
/* 120:    */       {
/* 121:250 */         encoding = "UTF-8";
/* 122:    */       }
/* 123:    */     } else {
/* 124:255 */       encoding = convertJava2MimeEncoding(encoding);
/* 125:    */     }
/* 126:258 */     return encoding;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private static String convertJava2MimeEncoding(String encoding)
/* 130:    */   {
/* 131:272 */     EncodingInfo enc = (EncodingInfo)_encodingTableKeyJava.get(toUpperCaseFast(encoding));
/* 132:274 */     if (null != enc) {
/* 133:275 */       return enc.name;
/* 134:    */     }
/* 135:276 */     return encoding;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static String convertMime2JavaEncoding(String encoding)
/* 139:    */   {
/* 140:294 */     for (int i = 0; i < _encodings.length; i++) {
/* 141:296 */       if (_encodings[i].name.equalsIgnoreCase(encoding)) {
/* 142:298 */         return _encodings[i].javaName;
/* 143:    */       }
/* 144:    */     }
/* 145:302 */     return encoding;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static EncodingInfo[] loadEncodingInfo()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:319 */       SecuritySupport ss = SecuritySupport.getInstance();
/* 153:320 */       InputStream is = ss.getResourceAsStream(ObjectFactory.findClassLoader(), ENCODINGS_FILE);
/* 154:    */       
/* 155:    */ 
/* 156:323 */       Properties props = new Properties();
/* 157:324 */       if (is != null)
/* 158:    */       {
/* 159:325 */         props.load(is);
/* 160:326 */         is.close();
/* 161:    */       }
/* 162:336 */       int totalEntries = props.size();
/* 163:    */       
/* 164:338 */       List encodingInfo_list = new ArrayList();
/* 165:339 */       Enumeration keys = props.keys();
/* 166:340 */       for (int i = 0; i < totalEntries; i++)
/* 167:    */       {
/* 168:342 */         String javaName = (String)keys.nextElement();
/* 169:343 */         String val = props.getProperty(javaName);
/* 170:344 */         int len = lengthOfMimeNames(val);
/* 171:    */         String mimeName;
/* 172:    */         char highChar;
/* 173:348 */         if (len == 0)
/* 174:    */         {
/* 175:351 */           mimeName = javaName;
/* 176:352 */           highChar = '\000';
/* 177:    */         }
/* 178:    */         else
/* 179:    */         {
/* 180:    */           try
/* 181:    */           {
/* 182:358 */             String highVal = val.substring(len).trim();
/* 183:359 */             highChar = (char)Integer.decode(highVal).intValue();
/* 184:    */           }
/* 185:    */           catch (NumberFormatException e)
/* 186:    */           {
/* 187:362 */             highChar = '\000';
/* 188:    */           }
/* 189:364 */           String mimeNames = val.substring(0, len);
/* 190:365 */           StringTokenizer st = new StringTokenizer(mimeNames, ",");
/* 191:367 */           for (boolean first = true; st.hasMoreTokens(); first = false)
/* 192:    */           {
/* 193:371 */             mimeName = st.nextToken();
/* 194:372 */             EncodingInfo ei = new EncodingInfo(mimeName, javaName, highChar);
/* 195:373 */             encodingInfo_list.add(ei);
/* 196:374 */             _encodingTableKeyMime.put(mimeName.toUpperCase(), ei);
/* 197:375 */             if (first) {
/* 198:376 */               _encodingTableKeyJava.put(javaName.toUpperCase(), ei);
/* 199:    */             }
/* 200:    */           }
/* 201:    */         }
/* 202:    */       }
/* 203:382 */       EncodingInfo[] ret_ei = new EncodingInfo[encodingInfo_list.size()];
/* 204:383 */       encodingInfo_list.toArray(ret_ei);
/* 205:384 */       return ret_ei;
/* 206:    */     }
/* 207:    */     catch (MalformedURLException mue)
/* 208:    */     {
/* 209:388 */       throw new WrappedRuntimeException(mue);
/* 210:    */     }
/* 211:    */     catch (IOException ioe)
/* 212:    */     {
/* 213:392 */       throw new WrappedRuntimeException(ioe);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   private static int lengthOfMimeNames(String val)
/* 218:    */   {
/* 219:405 */     int len = val.indexOf(' ');
/* 220:408 */     if (len < 0) {
/* 221:409 */       len = val.length();
/* 222:    */     }
/* 223:411 */     return len;
/* 224:    */   }
/* 225:    */   
/* 226:    */   static boolean isHighUTF16Surrogate(char ch)
/* 227:    */   {
/* 228:422 */     return (55296 <= ch) && (ch <= 56319);
/* 229:    */   }
/* 230:    */   
/* 231:    */   static boolean isLowUTF16Surrogate(char ch)
/* 232:    */   {
/* 233:432 */     return (56320 <= ch) && (ch <= 57343);
/* 234:    */   }
/* 235:    */   
/* 236:    */   static int toCodePoint(char highSurrogate, char lowSurrogate)
/* 237:    */   {
/* 238:443 */     int codePoint = (highSurrogate - 55296 << 10) + (lowSurrogate - 56320) + 65536;
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:447 */     return codePoint;
/* 243:    */   }
/* 244:    */   
/* 245:    */   static int toCodePoint(char ch)
/* 246:    */   {
/* 247:459 */     int codePoint = ch;
/* 248:460 */     return codePoint;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static char getHighChar(String encoding)
/* 252:    */   {
/* 253:483 */     String normalizedEncoding = toUpperCaseFast(encoding);
/* 254:484 */     EncodingInfo ei = (EncodingInfo)_encodingTableKeyJava.get(normalizedEncoding);
/* 255:485 */     if (ei == null) {
/* 256:486 */       ei = (EncodingInfo)_encodingTableKeyMime.get(normalizedEncoding);
/* 257:    */     }
/* 258:    */     char highCodePoint;
/* 259:487 */     if (ei != null) {
/* 260:488 */       highCodePoint = ei.getHighChar();
/* 261:    */     } else {
/* 262:490 */       highCodePoint = '\000';
/* 263:    */     }
/* 264:491 */     return highCodePoint;
/* 265:    */   }
/* 266:    */   
/* 267:494 */   private static final Hashtable _encodingTableKeyJava = new Hashtable();
/* 268:495 */   private static final Hashtable _encodingTableKeyMime = new Hashtable();
/* 269:496 */   private static final EncodingInfo[] _encodings = loadEncodingInfo();
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.Encodings
 * JD-Core Version:    0.7.0.1
 */