/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.text.MessageFormat;
/*   4:    */ import java.util.ListResourceBundle;
/*   5:    */ import java.util.Locale;
/*   6:    */ import java.util.MissingResourceException;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ 
/*   9:    */ public class XMLMessages
/*  10:    */ {
/*  11: 36 */   protected Locale fLocale = Locale.getDefault();
/*  12: 39 */   private static ListResourceBundle XMLBundle = null;
/*  13:    */   private static final String XML_ERROR_RESOURCES = "org.apache.xml.res.XMLErrorResources";
/*  14:    */   protected static final String BAD_CODE = "BAD_CODE";
/*  15:    */   protected static final String FORMAT_FAILED = "FORMAT_FAILED";
/*  16:    */   
/*  17:    */   public void setLocale(Locale locale)
/*  18:    */   {
/*  19: 58 */     this.fLocale = locale;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Locale getLocale()
/*  23:    */   {
/*  24: 68 */     return this.fLocale;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static final String createXMLMessage(String msgKey, Object[] args)
/*  28:    */   {
/*  29: 83 */     if (XMLBundle == null) {
/*  30: 84 */       XMLBundle = loadResourceBundle("org.apache.xml.res.XMLErrorResources");
/*  31:    */     }
/*  32: 86 */     if (XMLBundle != null) {
/*  33: 88 */       return createMsg(XMLBundle, msgKey, args);
/*  34:    */     }
/*  35: 91 */     return "Could not load any resource bundles.";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static final String createMsg(ListResourceBundle fResourceBundle, String msgKey, Object[] args)
/*  39:    */   {
/*  40:109 */     String fmsg = null;
/*  41:110 */     boolean throwex = false;
/*  42:111 */     String msg = null;
/*  43:113 */     if (msgKey != null) {
/*  44:114 */       msg = fResourceBundle.getString(msgKey);
/*  45:    */     }
/*  46:116 */     if (msg == null)
/*  47:    */     {
/*  48:118 */       msg = fResourceBundle.getString("BAD_CODE");
/*  49:119 */       throwex = true;
/*  50:    */     }
/*  51:122 */     if (args != null) {
/*  52:    */       try
/*  53:    */       {
/*  54:130 */         int n = args.length;
/*  55:132 */         for (int i = 0; i < n; i++) {
/*  56:134 */           if (null == args[i]) {
/*  57:135 */             args[i] = "";
/*  58:    */           }
/*  59:    */         }
/*  60:138 */         fmsg = MessageFormat.format(msg, args);
/*  61:    */       }
/*  62:    */       catch (Exception e)
/*  63:    */       {
/*  64:142 */         fmsg = fResourceBundle.getString("FORMAT_FAILED");
/*  65:143 */         fmsg = fmsg + " " + msg;
/*  66:    */       }
/*  67:    */     } else {
/*  68:147 */       fmsg = msg;
/*  69:    */     }
/*  70:149 */     if (throwex) {
/*  71:151 */       throw new RuntimeException(fmsg);
/*  72:    */     }
/*  73:154 */     return fmsg;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static ListResourceBundle loadResourceBundle(String className)
/*  77:    */     throws MissingResourceException
/*  78:    */   {
/*  79:168 */     Locale locale = Locale.getDefault();
/*  80:    */     try
/*  81:    */     {
/*  82:172 */       return (ListResourceBundle)ResourceBundle.getBundle(className, locale);
/*  83:    */     }
/*  84:    */     catch (MissingResourceException e)
/*  85:    */     {
/*  86:    */       try
/*  87:    */       {
/*  88:181 */         return (ListResourceBundle)ResourceBundle.getBundle(className, new Locale("en", "US"));
/*  89:    */       }
/*  90:    */       catch (MissingResourceException e2)
/*  91:    */       {
/*  92:189 */         throw new MissingResourceException("Could not load any resource bundles." + className, className, "");
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected static String getResourceSuffix(Locale locale)
/*  98:    */   {
/*  99:206 */     String suffix = "_" + locale.getLanguage();
/* 100:207 */     String country = locale.getCountry();
/* 101:209 */     if (country.equals("TW")) {
/* 102:210 */       suffix = suffix + "_" + country;
/* 103:    */     }
/* 104:212 */     return suffix;
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.res.XMLMessages
 * JD-Core Version:    0.7.0.1
 */