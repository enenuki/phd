/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.net.URLConnection;
/*  10:    */ import javax.xml.transform.ErrorListener;
/*  11:    */ import javax.xml.transform.SourceLocator;
/*  12:    */ import javax.xml.transform.TransformerException;
/*  13:    */ import org.apache.xml.res.XMLMessages;
/*  14:    */ import org.xml.sax.ErrorHandler;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ import org.xml.sax.SAXParseException;
/*  17:    */ 
/*  18:    */ public class ListingErrorHandler
/*  19:    */   implements ErrorHandler, ErrorListener
/*  20:    */ {
/*  21: 56 */   protected PrintWriter m_pw = null;
/*  22:    */   
/*  23:    */   public ListingErrorHandler(PrintWriter pw)
/*  24:    */   {
/*  25: 64 */     if (null == pw) {
/*  26: 65 */       throw new NullPointerException(XMLMessages.createXMLMessage("ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", null));
/*  27:    */     }
/*  28: 68 */     this.m_pw = pw;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ListingErrorHandler()
/*  32:    */   {
/*  33: 76 */     this.m_pw = new PrintWriter(System.err, true);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void warning(SAXParseException exception)
/*  37:    */     throws SAXException
/*  38:    */   {
/*  39:104 */     logExceptionLocation(this.m_pw, exception);
/*  40:    */     
/*  41:    */ 
/*  42:107 */     this.m_pw.println("warning: " + exception.getMessage());
/*  43:108 */     this.m_pw.flush();
/*  44:110 */     if (getThrowOnWarning()) {
/*  45:111 */       throw exception;
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void error(SAXParseException exception)
/*  50:    */     throws SAXException
/*  51:    */   {
/*  52:143 */     logExceptionLocation(this.m_pw, exception);
/*  53:144 */     this.m_pw.println("error: " + exception.getMessage());
/*  54:145 */     this.m_pw.flush();
/*  55:147 */     if (getThrowOnError()) {
/*  56:148 */       throw exception;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void fatalError(SAXParseException exception)
/*  61:    */     throws SAXException
/*  62:    */   {
/*  63:175 */     logExceptionLocation(this.m_pw, exception);
/*  64:176 */     this.m_pw.println("fatalError: " + exception.getMessage());
/*  65:177 */     this.m_pw.flush();
/*  66:179 */     if (getThrowOnFatalError()) {
/*  67:180 */       throw exception;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void warning(TransformerException exception)
/*  72:    */     throws TransformerException
/*  73:    */   {
/*  74:208 */     logExceptionLocation(this.m_pw, exception);
/*  75:209 */     this.m_pw.println("warning: " + exception.getMessage());
/*  76:210 */     this.m_pw.flush();
/*  77:212 */     if (getThrowOnWarning()) {
/*  78:213 */       throw exception;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void error(TransformerException exception)
/*  83:    */     throws TransformerException
/*  84:    */   {
/*  85:235 */     logExceptionLocation(this.m_pw, exception);
/*  86:236 */     this.m_pw.println("error: " + exception.getMessage());
/*  87:237 */     this.m_pw.flush();
/*  88:239 */     if (getThrowOnError()) {
/*  89:240 */       throw exception;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void fatalError(TransformerException exception)
/*  94:    */     throws TransformerException
/*  95:    */   {
/*  96:263 */     logExceptionLocation(this.m_pw, exception);
/*  97:264 */     this.m_pw.println("error: " + exception.getMessage());
/*  98:265 */     this.m_pw.flush();
/*  99:267 */     if (getThrowOnError()) {
/* 100:268 */       throw exception;
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void logExceptionLocation(PrintWriter pw, Throwable exception)
/* 105:    */   {
/* 106:286 */     if (null == pw) {
/* 107:287 */       pw = new PrintWriter(System.err, true);
/* 108:    */     }
/* 109:289 */     SourceLocator locator = null;
/* 110:290 */     Throwable cause = exception;
/* 111:    */     do
/* 112:    */     {
/* 113:296 */       if ((cause instanceof SAXParseException))
/* 114:    */       {
/* 115:303 */         locator = new SAXSourceLocator((SAXParseException)cause);
/* 116:    */       }
/* 117:305 */       else if ((cause instanceof TransformerException))
/* 118:    */       {
/* 119:307 */         SourceLocator causeLocator = ((TransformerException)cause).getLocator();
/* 120:308 */         if (null != causeLocator) {
/* 121:310 */           locator = causeLocator;
/* 122:    */         }
/* 123:    */       }
/* 124:315 */       if ((cause instanceof TransformerException)) {
/* 125:316 */         cause = ((TransformerException)cause).getCause();
/* 126:317 */       } else if ((cause instanceof WrappedRuntimeException)) {
/* 127:318 */         cause = ((WrappedRuntimeException)cause).getException();
/* 128:319 */       } else if ((cause instanceof SAXException)) {
/* 129:320 */         cause = ((SAXException)cause).getException();
/* 130:    */       } else {
/* 131:322 */         cause = null;
/* 132:    */       }
/* 133:324 */     } while (null != cause);
/* 134:329 */     if (null != locator)
/* 135:    */     {
/* 136:331 */       String id = null != locator.getSystemId() ? locator.getSystemId() : locator.getPublicId() != locator.getPublicId() ? locator.getPublicId() : "SystemId-Unknown";
/* 137:    */       
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:336 */       pw.print(id + ":Line=" + locator.getLineNumber() + ";Column=" + locator.getColumnNumber() + ": ");
/* 142:    */       
/* 143:338 */       pw.println("exception:" + exception.getMessage());
/* 144:339 */       pw.println("root-cause:" + (null != cause ? cause.getMessage() : "null"));
/* 145:    */       
/* 146:341 */       logSourceLine(pw, locator);
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:345 */       pw.print("SystemId-Unknown:locator-unavailable: ");
/* 151:346 */       pw.println("exception:" + exception.getMessage());
/* 152:347 */       pw.println("root-cause:" + (null != cause ? cause.getMessage() : "null"));
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void logSourceLine(PrintWriter pw, SourceLocator locator)
/* 157:    */   {
/* 158:363 */     if (null == locator) {
/* 159:364 */       return;
/* 160:    */     }
/* 161:366 */     if (null == pw) {
/* 162:367 */       pw = new PrintWriter(System.err, true);
/* 163:    */     }
/* 164:369 */     String url = locator.getSystemId();
/* 165:373 */     if (null == url)
/* 166:    */     {
/* 167:375 */       pw.println("line: (No systemId; cannot read file)");
/* 168:376 */       pw.println();
/* 169:377 */       return;
/* 170:    */     }
/* 171:    */     try
/* 172:    */     {
/* 173:384 */       int line = locator.getLineNumber();
/* 174:385 */       int column = locator.getColumnNumber();
/* 175:386 */       pw.println("line: " + getSourceLine(url, line));
/* 176:387 */       StringBuffer buf = new StringBuffer("line: ");
/* 177:388 */       for (int i = 1; i < column; i++) {
/* 178:390 */         buf.append(' ');
/* 179:    */       }
/* 180:392 */       buf.append('^');
/* 181:393 */       pw.println(buf.toString());
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:397 */       pw.println("line: logSourceLine unavailable due to: " + e.getMessage());
/* 186:398 */       pw.println();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected static String getSourceLine(String sourceUrl, int lineNum)
/* 191:    */     throws Exception
/* 192:    */   {
/* 193:412 */     URL url = null;
/* 194:    */     try
/* 195:    */     {
/* 196:417 */       url = new URL(sourceUrl);
/* 197:    */     }
/* 198:    */     catch (MalformedURLException mue)
/* 199:    */     {
/* 200:421 */       int indexOfColon = sourceUrl.indexOf(':');
/* 201:422 */       int indexOfSlash = sourceUrl.indexOf('/');
/* 202:424 */       if ((indexOfColon != -1) && (indexOfSlash != -1) && (indexOfColon < indexOfSlash)) {
/* 203:430 */         throw mue;
/* 204:    */       }
/* 205:435 */       url = new URL(SystemIDResolver.getAbsoluteURI(sourceUrl));
/* 206:    */     }
/* 207:440 */     String line = null;
/* 208:441 */     InputStream is = null;
/* 209:442 */     BufferedReader br = null;
/* 210:    */     try
/* 211:    */     {
/* 212:446 */       URLConnection uc = url.openConnection();
/* 213:447 */       is = uc.getInputStream();
/* 214:448 */       br = new BufferedReader(new InputStreamReader(is));
/* 215:452 */       for (int i = 1; i <= lineNum; i++) {
/* 216:454 */         line = br.readLine();
/* 217:    */       }
/* 218:    */     }
/* 219:    */     finally
/* 220:    */     {
/* 221:462 */       br.close();
/* 222:463 */       is.close();
/* 223:    */     }
/* 224:467 */     return line;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setThrowOnWarning(boolean b)
/* 228:    */   {
/* 229:485 */     this.throwOnWarning = b;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean getThrowOnWarning()
/* 233:    */   {
/* 234:495 */     return this.throwOnWarning;
/* 235:    */   }
/* 236:    */   
/* 237:499 */   protected boolean throwOnWarning = false;
/* 238:    */   
/* 239:    */   public void setThrowOnError(boolean b)
/* 240:    */   {
/* 241:517 */     this.throwOnError = b;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean getThrowOnError()
/* 245:    */   {
/* 246:527 */     return this.throwOnError;
/* 247:    */   }
/* 248:    */   
/* 249:531 */   protected boolean throwOnError = true;
/* 250:    */   
/* 251:    */   public void setThrowOnFatalError(boolean b)
/* 252:    */   {
/* 253:550 */     this.throwOnFatalError = b;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean getThrowOnFatalError()
/* 257:    */   {
/* 258:560 */     return this.throwOnFatalError;
/* 259:    */   }
/* 260:    */   
/* 261:564 */   protected boolean throwOnFatalError = true;
/* 262:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.ListingErrorHandler
 * JD-Core Version:    0.7.0.1
 */