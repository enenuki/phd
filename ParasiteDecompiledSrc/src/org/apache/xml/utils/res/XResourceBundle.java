/*   1:    */ package org.apache.xml.utils.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XResourceBundle
/*   9:    */   extends ListResourceBundle
/*  10:    */ {
/*  11:    */   public static final String ERROR_RESOURCES = "org.apache.xalan.res.XSLTErrorResources";
/*  12:    */   public static final String XSLT_RESOURCE = "org.apache.xml.utils.res.XResourceBundle";
/*  13:    */   public static final String LANG_BUNDLE_NAME = "org.apache.xml.utils.res.XResources";
/*  14:    */   public static final String MULT_ORDER = "multiplierOrder";
/*  15:    */   public static final String MULT_PRECEDES = "precedes";
/*  16:    */   public static final String MULT_FOLLOWS = "follows";
/*  17:    */   public static final String LANG_ORIENTATION = "orientation";
/*  18:    */   public static final String LANG_RIGHTTOLEFT = "rightToLeft";
/*  19:    */   public static final String LANG_LEFTTORIGHT = "leftToRight";
/*  20:    */   public static final String LANG_NUMBERING = "numbering";
/*  21:    */   public static final String LANG_ADDITIVE = "additive";
/*  22:    */   public static final String LANG_MULT_ADD = "multiplicative-additive";
/*  23:    */   public static final String LANG_MULTIPLIER = "multiplier";
/*  24:    */   public static final String LANG_MULTIPLIER_CHAR = "multiplierChar";
/*  25:    */   public static final String LANG_NUMBERGROUPS = "numberGroups";
/*  26:    */   public static final String LANG_NUM_TABLES = "tables";
/*  27:    */   public static final String LANG_ALPHABET = "alphabet";
/*  28:    */   public static final String LANG_TRAD_ALPHABET = "tradAlphabet";
/*  29:    */   
/*  30:    */   public static final XResourceBundle loadResourceBundle(String className, Locale locale)
/*  31:    */     throws MissingResourceException
/*  32:    */   {
/*  33: 60 */     String suffix = getResourceSuffix(locale);
/*  34:    */     try
/*  35:    */     {
/*  36: 67 */       String resourceName = className + suffix;
/*  37: 68 */       return (XResourceBundle)ResourceBundle.getBundle(resourceName, locale);
/*  38:    */     }
/*  39:    */     catch (MissingResourceException e)
/*  40:    */     {
/*  41:    */       try
/*  42:    */       {
/*  43: 77 */         return (XResourceBundle)ResourceBundle.getBundle("org.apache.xml.utils.res.XResourceBundle", new Locale("en", "US"));
/*  44:    */       }
/*  45:    */       catch (MissingResourceException e2)
/*  46:    */       {
/*  47: 85 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static final String getResourceSuffix(Locale locale)
/*  53:    */   {
/*  54:102 */     String lang = locale.getLanguage();
/*  55:103 */     String country = locale.getCountry();
/*  56:104 */     String variant = locale.getVariant();
/*  57:105 */     String suffix = "_" + locale.getLanguage();
/*  58:107 */     if (lang.equals("zh")) {
/*  59:108 */       suffix = suffix + "_" + country;
/*  60:    */     }
/*  61:110 */     if (country.equals("JP")) {
/*  62:111 */       suffix = suffix + "_" + country + "_" + variant;
/*  63:    */     }
/*  64:113 */     return suffix;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object[][] getContents()
/*  68:    */   {
/*  69:123 */     return new Object[][] { { "ui_language", "en" }, { "help_language", "en" }, { "language", "en" }, { "alphabet", new CharArrayWrapper(new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' }) }, { "tradAlphabet", new CharArrayWrapper(new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' }) }, { "orientation", "LeftToRight" }, { "numbering", "additive" } };
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.res.XResourceBundle
 * JD-Core Version:    0.7.0.1
 */