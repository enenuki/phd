/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import javax.xml.transform.ErrorListener;
/*   6:    */ import javax.xml.transform.SourceLocator;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xml.res.XMLMessages;
/*   9:    */ import org.xml.sax.ErrorHandler;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ import org.xml.sax.SAXParseException;
/*  12:    */ 
/*  13:    */ public class DefaultErrorHandler
/*  14:    */   implements ErrorHandler, ErrorListener
/*  15:    */ {
/*  16:    */   PrintWriter m_pw;
/*  17: 51 */   boolean m_throwExceptionOnError = true;
/*  18:    */   
/*  19:    */   public DefaultErrorHandler(PrintWriter pw)
/*  20:    */   {
/*  21: 58 */     this.m_pw = pw;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public DefaultErrorHandler(PrintStream pw)
/*  25:    */   {
/*  26: 66 */     this.m_pw = new PrintWriter(pw, true);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DefaultErrorHandler()
/*  30:    */   {
/*  31: 74 */     this(true);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DefaultErrorHandler(boolean throwExceptionOnError)
/*  35:    */   {
/*  36: 83 */     this.m_throwExceptionOnError = throwExceptionOnError;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PrintWriter getErrorWriter()
/*  40:    */   {
/*  41: 95 */     if (this.m_pw == null) {
/*  42: 96 */       this.m_pw = new PrintWriter(System.err, true);
/*  43:    */     }
/*  44: 98 */     return this.m_pw;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void warning(SAXParseException exception)
/*  48:    */     throws SAXException
/*  49:    */   {
/*  50:119 */     PrintWriter pw = getErrorWriter();
/*  51:    */     
/*  52:121 */     printLocation(pw, exception);
/*  53:122 */     pw.println("Parser warning: " + exception.getMessage());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void error(SAXParseException exception)
/*  57:    */     throws SAXException
/*  58:    */   {
/*  59:151 */     throw exception;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void fatalError(SAXParseException exception)
/*  63:    */     throws SAXException
/*  64:    */   {
/*  65:178 */     throw exception;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void warning(TransformerException exception)
/*  69:    */     throws TransformerException
/*  70:    */   {
/*  71:200 */     PrintWriter pw = getErrorWriter();
/*  72:    */     
/*  73:202 */     printLocation(pw, exception);
/*  74:203 */     pw.println(exception.getMessage());
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void error(TransformerException exception)
/*  78:    */     throws TransformerException
/*  79:    */   {
/*  80:232 */     if (this.m_throwExceptionOnError) {
/*  81:233 */       throw exception;
/*  82:    */     }
/*  83:236 */     PrintWriter pw = getErrorWriter();
/*  84:    */     
/*  85:238 */     printLocation(pw, exception);
/*  86:239 */     pw.println(exception.getMessage());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void fatalError(TransformerException exception)
/*  90:    */     throws TransformerException
/*  91:    */   {
/*  92:267 */     if (this.m_throwExceptionOnError) {
/*  93:268 */       throw exception;
/*  94:    */     }
/*  95:271 */     PrintWriter pw = getErrorWriter();
/*  96:    */     
/*  97:273 */     printLocation(pw, exception);
/*  98:274 */     pw.println(exception.getMessage());
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void ensureLocationSet(TransformerException exception)
/* 102:    */   {
/* 103:281 */     SourceLocator locator = null;
/* 104:282 */     Throwable cause = exception;
/* 105:    */     do
/* 106:    */     {
/* 107:287 */       if ((cause instanceof SAXParseException))
/* 108:    */       {
/* 109:289 */         locator = new SAXSourceLocator((SAXParseException)cause);
/* 110:    */       }
/* 111:291 */       else if ((cause instanceof TransformerException))
/* 112:    */       {
/* 113:293 */         SourceLocator causeLocator = ((TransformerException)cause).getLocator();
/* 114:294 */         if (null != causeLocator) {
/* 115:295 */           locator = causeLocator;
/* 116:    */         }
/* 117:    */       }
/* 118:298 */       if ((cause instanceof TransformerException)) {
/* 119:299 */         cause = ((TransformerException)cause).getCause();
/* 120:300 */       } else if ((cause instanceof SAXException)) {
/* 121:301 */         cause = ((SAXException)cause).getException();
/* 122:    */       } else {
/* 123:303 */         cause = null;
/* 124:    */       }
/* 125:305 */     } while (null != cause);
/* 126:307 */     exception.setLocator(locator);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void printLocation(PrintStream pw, TransformerException exception)
/* 130:    */   {
/* 131:312 */     printLocation(new PrintWriter(pw), exception);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void printLocation(PrintStream pw, SAXParseException exception)
/* 135:    */   {
/* 136:317 */     printLocation(new PrintWriter(pw), exception);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void printLocation(PrintWriter pw, Throwable exception)
/* 140:    */   {
/* 141:322 */     SourceLocator locator = null;
/* 142:323 */     Throwable cause = exception;
/* 143:    */     do
/* 144:    */     {
/* 145:328 */       if ((cause instanceof SAXParseException))
/* 146:    */       {
/* 147:330 */         locator = new SAXSourceLocator((SAXParseException)cause);
/* 148:    */       }
/* 149:332 */       else if ((cause instanceof TransformerException))
/* 150:    */       {
/* 151:334 */         SourceLocator causeLocator = ((TransformerException)cause).getLocator();
/* 152:335 */         if (null != causeLocator) {
/* 153:336 */           locator = causeLocator;
/* 154:    */         }
/* 155:    */       }
/* 156:338 */       if ((cause instanceof TransformerException)) {
/* 157:339 */         cause = ((TransformerException)cause).getCause();
/* 158:340 */       } else if ((cause instanceof WrappedRuntimeException)) {
/* 159:341 */         cause = ((WrappedRuntimeException)cause).getException();
/* 160:342 */       } else if ((cause instanceof SAXException)) {
/* 161:343 */         cause = ((SAXException)cause).getException();
/* 162:    */       } else {
/* 163:345 */         cause = null;
/* 164:    */       }
/* 165:347 */     } while (null != cause);
/* 166:349 */     if (null != locator)
/* 167:    */     {
/* 168:352 */       String id = null != locator.getSystemId() ? locator.getSystemId() : null != locator.getPublicId() ? locator.getPublicId() : XMLMessages.createXMLMessage("ER_SYSTEMID_UNKNOWN", null);
/* 169:    */       
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:357 */       pw.print(id + "; " + XMLMessages.createXMLMessage("line", null) + locator.getLineNumber() + "; " + XMLMessages.createXMLMessage("column", null) + locator.getColumnNumber() + "; ");
/* 174:    */     }
/* 175:    */     else
/* 176:    */     {
/* 177:361 */       pw.print("(" + XMLMessages.createXMLMessage("ER_LOCATION_UNKNOWN", null) + ")");
/* 178:    */     }
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.DefaultErrorHandler
 * JD-Core Version:    0.7.0.1
 */