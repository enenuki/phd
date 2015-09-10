/*  1:   */ package org.apache.xalan.res;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ import org.apache.xml.res.XMLMessages;
/*  5:   */ import org.apache.xpath.res.XPATHMessages;
/*  6:   */ 
/*  7:   */ public class XSLMessages
/*  8:   */   extends XPATHMessages
/*  9:   */ {
/* 10:36 */   private static ListResourceBundle XSLTBundle = null;
/* 11:   */   private static final String XSLT_ERROR_RESOURCES = "org.apache.xalan.res.XSLTErrorResources";
/* 12:   */   
/* 13:   */   public static final String createMessage(String msgKey, Object[] args)
/* 14:   */   {
/* 15:54 */     if (XSLTBundle == null) {
/* 16:55 */       XSLTBundle = XMLMessages.loadResourceBundle("org.apache.xalan.res.XSLTErrorResources");
/* 17:   */     }
/* 18:57 */     if (XSLTBundle != null) {
/* 19:59 */       return XMLMessages.createMsg(XSLTBundle, msgKey, args);
/* 20:   */     }
/* 21:62 */     return "Could not load any resource bundles.";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static final String createWarning(String msgKey, Object[] args)
/* 25:   */   {
/* 26:77 */     if (XSLTBundle == null) {
/* 27:78 */       XSLTBundle = XMLMessages.loadResourceBundle("org.apache.xalan.res.XSLTErrorResources");
/* 28:   */     }
/* 29:80 */     if (XSLTBundle != null) {
/* 30:82 */       return XMLMessages.createMsg(XSLTBundle, msgKey, args);
/* 31:   */     }
/* 32:85 */     return "Could not load any resource bundles.";
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.res.XSLMessages
 * JD-Core Version:    0.7.0.1
 */