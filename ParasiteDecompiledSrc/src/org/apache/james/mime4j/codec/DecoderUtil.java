/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.UnsupportedEncodingException;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.james.mime4j.util.CharsetUtil;
/*  10:    */ 
/*  11:    */ public class DecoderUtil
/*  12:    */ {
/*  13: 35 */   private static Log log = LogFactory.getLog(DecoderUtil.class);
/*  14:    */   
/*  15:    */   public static byte[] decodeBaseQuotedPrintable(String s)
/*  16:    */   {
/*  17: 44 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  18:    */     try
/*  19:    */     {
/*  20: 47 */       byte[] bytes = s.getBytes("US-ASCII");
/*  21:    */       
/*  22: 49 */       QuotedPrintableInputStream is = new QuotedPrintableInputStream(new ByteArrayInputStream(bytes));
/*  23:    */       
/*  24:    */ 
/*  25: 52 */       int b = 0;
/*  26: 53 */       while ((b = is.read()) != -1) {
/*  27: 54 */         baos.write(b);
/*  28:    */       }
/*  29:    */     }
/*  30:    */     catch (IOException e)
/*  31:    */     {
/*  32: 60 */       log.error(e);
/*  33:    */     }
/*  34: 63 */     return baos.toByteArray();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static byte[] decodeBase64(String s)
/*  38:    */   {
/*  39: 73 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  40:    */     try
/*  41:    */     {
/*  42: 76 */       byte[] bytes = s.getBytes("US-ASCII");
/*  43:    */       
/*  44: 78 */       Base64InputStream is = new Base64InputStream(new ByteArrayInputStream(bytes));
/*  45:    */       
/*  46:    */ 
/*  47: 81 */       int b = 0;
/*  48: 82 */       while ((b = is.read()) != -1) {
/*  49: 83 */         baos.write(b);
/*  50:    */       }
/*  51:    */     }
/*  52:    */     catch (IOException e)
/*  53:    */     {
/*  54: 89 */       log.error(e);
/*  55:    */     }
/*  56: 92 */     return baos.toByteArray();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String decodeB(String encodedWord, String charset)
/*  60:    */     throws UnsupportedEncodingException
/*  61:    */   {
/*  62:108 */     return new String(decodeBase64(encodedWord), charset);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String decodeQ(String encodedWord, String charset)
/*  66:    */     throws UnsupportedEncodingException
/*  67:    */   {
/*  68:127 */     StringBuilder sb = new StringBuilder(128);
/*  69:128 */     for (int i = 0; i < encodedWord.length(); i++)
/*  70:    */     {
/*  71:129 */       char c = encodedWord.charAt(i);
/*  72:130 */       if (c == '_') {
/*  73:131 */         sb.append("=20");
/*  74:    */       } else {
/*  75:133 */         sb.append(c);
/*  76:    */       }
/*  77:    */     }
/*  78:137 */     return new String(decodeBaseQuotedPrintable(sb.toString()), charset);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static String decodeEncodedWords(String body)
/*  82:    */   {
/*  83:150 */     int previousEnd = 0;
/*  84:151 */     boolean previousWasEncoded = false;
/*  85:    */     
/*  86:153 */     StringBuilder sb = new StringBuilder();
/*  87:    */     for (;;)
/*  88:    */     {
/*  89:156 */       int begin = body.indexOf("=?", previousEnd);
/*  90:157 */       int end = begin == -1 ? -1 : body.indexOf("?=", begin + 2);
/*  91:158 */       if (end == -1)
/*  92:    */       {
/*  93:159 */         if (previousEnd == 0) {
/*  94:160 */           return body;
/*  95:    */         }
/*  96:162 */         sb.append(body.substring(previousEnd));
/*  97:163 */         return sb.toString();
/*  98:    */       }
/*  99:165 */       end += 2;
/* 100:    */       
/* 101:167 */       String sep = body.substring(previousEnd, begin);
/* 102:    */       
/* 103:169 */       String decoded = decodeEncodedWord(body, begin, end);
/* 104:170 */       if (decoded == null)
/* 105:    */       {
/* 106:171 */         sb.append(sep);
/* 107:172 */         sb.append(body.substring(begin, end));
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:174 */         if ((!previousWasEncoded) || (!CharsetUtil.isWhitespace(sep))) {
/* 112:175 */           sb.append(sep);
/* 113:    */         }
/* 114:177 */         sb.append(decoded);
/* 115:    */       }
/* 116:180 */       previousEnd = end;
/* 117:181 */       previousWasEncoded = decoded != null;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static String decodeEncodedWord(String body, int begin, int end)
/* 122:    */   {
/* 123:187 */     int qm1 = body.indexOf('?', begin + 2);
/* 124:188 */     if (qm1 == end - 2) {
/* 125:189 */       return null;
/* 126:    */     }
/* 127:191 */     int qm2 = body.indexOf('?', qm1 + 1);
/* 128:192 */     if (qm2 == end - 2) {
/* 129:193 */       return null;
/* 130:    */     }
/* 131:195 */     String mimeCharset = body.substring(begin + 2, qm1);
/* 132:196 */     String encoding = body.substring(qm1 + 1, qm2);
/* 133:197 */     String encodedText = body.substring(qm2 + 1, end - 2);
/* 134:    */     
/* 135:199 */     String charset = CharsetUtil.toJavaCharset(mimeCharset);
/* 136:200 */     if (charset == null)
/* 137:    */     {
/* 138:201 */       if (log.isWarnEnabled()) {
/* 139:202 */         log.warn("MIME charset '" + mimeCharset + "' in encoded word '" + body.substring(begin, end) + "' doesn't have a " + "corresponding Java charset");
/* 140:    */       }
/* 141:206 */       return null;
/* 142:    */     }
/* 143:207 */     if (!CharsetUtil.isDecodingSupported(charset))
/* 144:    */     {
/* 145:208 */       if (log.isWarnEnabled()) {
/* 146:209 */         log.warn("Current JDK doesn't support decoding of charset '" + charset + "' (MIME charset '" + mimeCharset + "' in encoded word '" + body.substring(begin, end) + "')");
/* 147:    */       }
/* 148:214 */       return null;
/* 149:    */     }
/* 150:217 */     if (encodedText.length() == 0)
/* 151:    */     {
/* 152:218 */       if (log.isWarnEnabled()) {
/* 153:219 */         log.warn("Missing encoded text in encoded word: '" + body.substring(begin, end) + "'");
/* 154:    */       }
/* 155:222 */       return null;
/* 156:    */     }
/* 157:    */     try
/* 158:    */     {
/* 159:226 */       if (encoding.equalsIgnoreCase("Q")) {
/* 160:227 */         return decodeQ(encodedText, charset);
/* 161:    */       }
/* 162:228 */       if (encoding.equalsIgnoreCase("B")) {
/* 163:229 */         return decodeB(encodedText, charset);
/* 164:    */       }
/* 165:231 */       if (log.isWarnEnabled()) {
/* 166:232 */         log.warn("Warning: Unknown encoding in encoded word '" + body.substring(begin, end) + "'");
/* 167:    */       }
/* 168:235 */       return null;
/* 169:    */     }
/* 170:    */     catch (UnsupportedEncodingException e)
/* 171:    */     {
/* 172:239 */       if (log.isWarnEnabled()) {
/* 173:240 */         log.warn("Unsupported encoding in encoded word '" + body.substring(begin, end) + "'", e);
/* 174:    */       }
/* 175:243 */       return null;
/* 176:    */     }
/* 177:    */     catch (RuntimeException e)
/* 178:    */     {
/* 179:245 */       if (log.isWarnEnabled()) {
/* 180:246 */         log.warn("Could not decode encoded word '" + body.substring(begin, end) + "'", e);
/* 181:    */       }
/* 182:    */     }
/* 183:249 */     return null;
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.DecoderUtil
 * JD-Core Version:    0.7.0.1
 */