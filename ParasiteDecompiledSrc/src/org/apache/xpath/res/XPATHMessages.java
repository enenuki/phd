/*   1:    */ package org.apache.xpath.res;
/*   2:    */ 
/*   3:    */ import java.text.MessageFormat;
/*   4:    */ import java.util.ListResourceBundle;
/*   5:    */ import java.util.ResourceBundle;
/*   6:    */ import org.apache.xml.res.XMLMessages;
/*   7:    */ 
/*   8:    */ public class XPATHMessages
/*   9:    */   extends XMLMessages
/*  10:    */ {
/*  11: 34 */   private static ListResourceBundle XPATHBundle = null;
/*  12:    */   private static final String XPATH_ERROR_RESOURCES = "org.apache.xpath.res.XPATHErrorResources";
/*  13:    */   
/*  14:    */   public static final String createXPATHMessage(String msgKey, Object[] args)
/*  15:    */   {
/*  16: 52 */     if (XPATHBundle == null) {
/*  17: 53 */       XPATHBundle = XMLMessages.loadResourceBundle("org.apache.xpath.res.XPATHErrorResources");
/*  18:    */     }
/*  19: 55 */     if (XPATHBundle != null) {
/*  20: 57 */       return createXPATHMsg(XPATHBundle, msgKey, args);
/*  21:    */     }
/*  22: 60 */     return "Could not load any resource bundles.";
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static final String createXPATHWarning(String msgKey, Object[] args)
/*  26:    */   {
/*  27: 75 */     if (XPATHBundle == null) {
/*  28: 76 */       XPATHBundle = XMLMessages.loadResourceBundle("org.apache.xpath.res.XPATHErrorResources");
/*  29:    */     }
/*  30: 78 */     if (XPATHBundle != null) {
/*  31: 80 */       return createXPATHMsg(XPATHBundle, msgKey, args);
/*  32:    */     }
/*  33: 83 */     return "Could not load any resource bundles.";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static final String createXPATHMsg(ListResourceBundle fResourceBundle, String msgKey, Object[] args)
/*  37:    */   {
/*  38:101 */     String fmsg = null;
/*  39:102 */     boolean throwex = false;
/*  40:103 */     String msg = null;
/*  41:105 */     if (msgKey != null) {
/*  42:106 */       msg = fResourceBundle.getString(msgKey);
/*  43:    */     }
/*  44:108 */     if (msg == null)
/*  45:    */     {
/*  46:110 */       msg = fResourceBundle.getString("BAD_CODE");
/*  47:111 */       throwex = true;
/*  48:    */     }
/*  49:114 */     if (args != null) {
/*  50:    */       try
/*  51:    */       {
/*  52:122 */         int n = args.length;
/*  53:124 */         for (int i = 0; i < n; i++) {
/*  54:126 */           if (null == args[i]) {
/*  55:127 */             args[i] = "";
/*  56:    */           }
/*  57:    */         }
/*  58:130 */         fmsg = MessageFormat.format(msg, args);
/*  59:    */       }
/*  60:    */       catch (Exception e)
/*  61:    */       {
/*  62:134 */         fmsg = fResourceBundle.getString("FORMAT_FAILED");
/*  63:135 */         fmsg = fmsg + " " + msg;
/*  64:    */       }
/*  65:    */     } else {
/*  66:139 */       fmsg = msg;
/*  67:    */     }
/*  68:141 */     if (throwex) {
/*  69:143 */       throw new RuntimeException(fmsg);
/*  70:    */     }
/*  71:146 */     return fmsg;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.res.XPATHMessages
 * JD-Core Version:    0.7.0.1
 */