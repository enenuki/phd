/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.XHtmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.io.Serializable;
/*  11:    */ import java.util.Locale;
/*  12:    */ import org.apache.commons.io.IOUtils;
/*  13:    */ import org.apache.commons.lang.ArrayUtils;
/*  14:    */ import org.apache.commons.lang.StringUtils;
/*  15:    */ 
/*  16:    */ public class DefaultPageCreator
/*  17:    */   implements PageCreator, Serializable
/*  18:    */ {
/*  19:    */   public Page createPage(WebResponse webResponse, WebWindow webWindow)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22:100 */     String contentType = determineContentType(webResponse.getContentType().toLowerCase(), webResponse.getContentAsStream());
/*  23:    */     
/*  24:    */ 
/*  25:    */ 
/*  26:104 */     String pageType = determinePageType(contentType);
/*  27:    */     Page newPage;
/*  28:    */     Page newPage;
/*  29:105 */     if ("html".equals(pageType))
/*  30:    */     {
/*  31:106 */       newPage = createHtmlPage(webResponse, webWindow);
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35:    */       Page newPage;
/*  36:108 */       if ("javascript".equals(pageType))
/*  37:    */       {
/*  38:109 */         newPage = createJavaScriptPage(webResponse, webWindow);
/*  39:    */       }
/*  40:    */       else
/*  41:    */       {
/*  42:    */         Page newPage;
/*  43:111 */         if ("xml".equals(pageType))
/*  44:    */         {
/*  45:112 */           XmlPage xml = createXmlPage(webResponse, webWindow);
/*  46:113 */           DomElement doc = xml.getDocumentElement();
/*  47:    */           Page newPage;
/*  48:114 */           if ((doc != null) && ("http://www.w3.org/1999/xhtml".equals(doc.getNamespaceURI()))) {
/*  49:115 */             newPage = createXHtmlPage(webResponse, webWindow);
/*  50:    */           } else {
/*  51:118 */             newPage = xml;
/*  52:    */           }
/*  53:    */         }
/*  54:    */         else
/*  55:    */         {
/*  56:    */           Page newPage;
/*  57:121 */           if ("text".equals(pageType)) {
/*  58:122 */             newPage = createTextPage(webResponse, webWindow);
/*  59:    */           } else {
/*  60:125 */             newPage = createUnexpectedPage(webResponse, webWindow);
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65:127 */     return newPage;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String determineContentType(String contentType, InputStream contentAsStream)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:141 */     byte[] markerUTF8 = { -17, -69, -65 };
/*  72:142 */     byte[] markerUTF16BE = { -2, -1 };
/*  73:143 */     byte[] markerUTF16LE = { -1, -2 };
/*  74:    */     try
/*  75:    */     {
/*  76:146 */       if (!StringUtils.isEmpty(contentType)) {
/*  77:147 */         return contentType;
/*  78:    */       }
/*  79:150 */       byte[] bytes = read(contentAsStream, 500);
/*  80:151 */       if (bytes.length == 0) {
/*  81:152 */         return "text/plain";
/*  82:    */       }
/*  83:155 */       String asAsciiString = new String(bytes, "ASCII").toUpperCase(Locale.ENGLISH);
/*  84:    */       String str3;
/*  85:156 */       if (asAsciiString.contains("<HTML")) {
/*  86:157 */         return "text/html";
/*  87:    */       }
/*  88:159 */       if ((startsWith(bytes, markerUTF8)) || (startsWith(bytes, markerUTF16BE)) || (startsWith(bytes, markerUTF16LE))) {
/*  89:161 */         return "text/plain";
/*  90:    */       }
/*  91:163 */       if (isBinary(bytes)) {
/*  92:164 */         return "application/octet-stream";
/*  93:    */       }
/*  94:    */     }
/*  95:    */     finally
/*  96:    */     {
/*  97:168 */       IOUtils.closeQuietly(contentAsStream);
/*  98:    */     }
/*  99:170 */     return "text/plain";
/* 100:    */   }
/* 101:    */   
/* 102:    */   private boolean isBinary(byte[] bytes)
/* 103:    */   {
/* 104:178 */     for (byte b : bytes) {
/* 105:179 */       if ((b < 8) || (b == 11) || ((b >= 14) && (b <= 26)) || ((b >= 28) && (b <= 31))) {
/* 106:183 */         return true;
/* 107:    */       }
/* 108:    */     }
/* 109:186 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private boolean startsWith(byte[] bytes, byte[] lookFor)
/* 113:    */   {
/* 114:190 */     if (bytes.length < lookFor.length) {
/* 115:191 */       return false;
/* 116:    */     }
/* 117:194 */     for (int i = 0; i < lookFor.length; i++) {
/* 118:195 */       if (bytes[i] != lookFor[i]) {
/* 119:196 */         return false;
/* 120:    */       }
/* 121:    */     }
/* 122:200 */     return true;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private byte[] read(InputStream stream, int maxNb)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:204 */     byte[] buffer = new byte[maxNb];
/* 129:205 */     int nbRead = stream.read(buffer);
/* 130:206 */     if (nbRead == buffer.length) {
/* 131:207 */       return buffer;
/* 132:    */     }
/* 133:209 */     return ArrayUtils.subarray(buffer, 0, nbRead);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected HtmlPage createHtmlPage(WebResponse webResponse, WebWindow webWindow)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:221 */     return HTMLParser.parseHtml(webResponse, webWindow);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected XHtmlPage createXHtmlPage(WebResponse webResponse, WebWindow webWindow)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:233 */     return HTMLParser.parseXHtml(webResponse, webWindow);
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected JavaScriptPage createJavaScriptPage(WebResponse webResponse, WebWindow webWindow)
/* 149:    */   {
/* 150:244 */     JavaScriptPage newPage = new JavaScriptPage(webResponse, webWindow);
/* 151:245 */     webWindow.setEnclosedPage(newPage);
/* 152:246 */     return newPage;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected TextPage createTextPage(WebResponse webResponse, WebWindow webWindow)
/* 156:    */   {
/* 157:257 */     TextPage newPage = new TextPage(webResponse, webWindow);
/* 158:258 */     webWindow.setEnclosedPage(newPage);
/* 159:259 */     return newPage;
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected UnexpectedPage createUnexpectedPage(WebResponse webResponse, WebWindow webWindow)
/* 163:    */   {
/* 164:270 */     UnexpectedPage newPage = new UnexpectedPage(webResponse, webWindow);
/* 165:271 */     webWindow.setEnclosedPage(newPage);
/* 166:272 */     return newPage;
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected XmlPage createXmlPage(WebResponse webResponse, WebWindow webWindow)
/* 170:    */     throws IOException
/* 171:    */   {
/* 172:284 */     XmlPage newPage = new XmlPage(webResponse, webWindow);
/* 173:285 */     webWindow.setEnclosedPage(newPage);
/* 174:286 */     return newPage;
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected String determinePageType(String contentType)
/* 178:    */   {
/* 179:295 */     if ("text/html".equals(contentType)) {
/* 180:296 */       return "html";
/* 181:    */     }
/* 182:298 */     if (("text/javascript".equals(contentType)) || ("application/x-javascript".equals(contentType))) {
/* 183:299 */       return "javascript";
/* 184:    */     }
/* 185:301 */     if (("text/xml".equals(contentType)) || ("application/xml".equals(contentType)) || ("text/vnd.wap.wml".equals(contentType)) || (contentType.matches(".*\\+xml"))) {
/* 186:305 */       return "xml";
/* 187:    */     }
/* 188:307 */     if (contentType.startsWith("text/")) {
/* 189:308 */       return "text";
/* 190:    */     }
/* 191:311 */     return "unknown";
/* 192:    */   }
/* 193:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.DefaultPageCreator
 * JD-Core Version:    0.7.0.1
 */