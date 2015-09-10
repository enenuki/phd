/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.util.Locale;
/*   5:    */ 
/*   6:    */ public final class Messages
/*   7:    */ {
/*   8:    */   public static <T> T getBundle(Class<T> type)
/*   9:    */   {
/*  10: 46 */     return getBundle(type, Locale.getDefault());
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static <T> T getBundle(Class<T> type, Locale locale)
/*  14:    */   {
/*  15: 58 */     String language = locale.getLanguage();
/*  16: 59 */     String country = locale.getCountry();
/*  17: 60 */     String variant = locale.getVariant();
/*  18:    */     
/*  19: 62 */     Class<? extends T> bundleClass = null;
/*  20: 63 */     if ((variant != null) && (variant.length() > 0)) {
/*  21:    */       try
/*  22:    */       {
/*  23: 64 */         bundleClass = Class.forName(join(type.getName(), "$bundle", language, country, variant), true, type.getClassLoader()).asSubclass(type);
/*  24:    */       }
/*  25:    */       catch (ClassNotFoundException e) {}
/*  26:    */     }
/*  27: 68 */     if ((bundleClass == null) && (country != null) && (country.length() > 0)) {
/*  28:    */       try
/*  29:    */       {
/*  30: 69 */         bundleClass = Class.forName(join(type.getName(), "$bundle", language, country, null), true, type.getClassLoader()).asSubclass(type);
/*  31:    */       }
/*  32:    */       catch (ClassNotFoundException e) {}
/*  33:    */     }
/*  34: 73 */     if ((bundleClass == null) && (language != null) && (language.length() > 0)) {
/*  35:    */       try
/*  36:    */       {
/*  37: 74 */         bundleClass = Class.forName(join(type.getName(), "$bundle", language, null, null), true, type.getClassLoader()).asSubclass(type);
/*  38:    */       }
/*  39:    */       catch (ClassNotFoundException e) {}
/*  40:    */     }
/*  41: 78 */     if (bundleClass == null) {
/*  42:    */       try
/*  43:    */       {
/*  44: 79 */         bundleClass = Class.forName(join(type.getName(), "$bundle", null, null, null), true, type.getClassLoader()).asSubclass(type);
/*  45:    */       }
/*  46:    */       catch (ClassNotFoundException e)
/*  47:    */       {
/*  48: 81 */         throw new IllegalArgumentException("Invalid bundle " + type + " (implementation not found)");
/*  49:    */       }
/*  50:    */     }
/*  51:    */     Field field;
/*  52:    */     try
/*  53:    */     {
/*  54: 85 */       field = bundleClass.getField("INSTANCE");
/*  55:    */     }
/*  56:    */     catch (NoSuchFieldException e)
/*  57:    */     {
/*  58: 87 */       throw new IllegalArgumentException("Bundle implementation " + bundleClass + " has no instance field");
/*  59:    */     }
/*  60:    */     try
/*  61:    */     {
/*  62: 90 */       return type.cast(field.get(null));
/*  63:    */     }
/*  64:    */     catch (IllegalAccessException e)
/*  65:    */     {
/*  66: 92 */       throw new IllegalArgumentException("Bundle implementation " + bundleClass + " could not be instantiated", e);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static String join(String interfaceName, String a, String b, String c, String d)
/*  71:    */   {
/*  72: 97 */     StringBuilder build = new StringBuilder();
/*  73: 98 */     build.append(interfaceName).append('_').append(a);
/*  74: 99 */     if ((b != null) && (b.length() > 0))
/*  75:    */     {
/*  76:100 */       build.append('_');
/*  77:101 */       build.append(b);
/*  78:    */     }
/*  79:103 */     if ((c != null) && (c.length() > 0))
/*  80:    */     {
/*  81:104 */       build.append('_');
/*  82:105 */       build.append(c);
/*  83:    */     }
/*  84:107 */     if ((d != null) && (d.length() > 0))
/*  85:    */     {
/*  86:108 */       build.append('_');
/*  87:109 */       build.append(d);
/*  88:    */     }
/*  89:111 */     return build.toString();
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Messages
 * JD-Core Version:    0.7.0.1
 */