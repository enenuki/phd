/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ import java.util.Locale;
/*  4:   */ 
/*  5:   */ public class LocaleUtility
/*  6:   */ {
/*  7:   */   public static final char IETF_SEPARATOR = '-';
/*  8:   */   public static final String EMPTY_STRING = "";
/*  9:   */   
/* 10:   */   public static Locale langToLocale(String lang)
/* 11:   */   {
/* 12:40 */     if ((lang == null) || (lang.equals(""))) {
/* 13:41 */       return Locale.getDefault();
/* 14:   */     }
/* 15:43 */     String language = "";
/* 16:44 */     String country = "";
/* 17:45 */     String variant = "";
/* 18:   */     
/* 19:47 */     int i1 = lang.indexOf('-');
/* 20:48 */     if (i1 < 0)
/* 21:   */     {
/* 22:49 */       language = lang;
/* 23:   */     }
/* 24:   */     else
/* 25:   */     {
/* 26:51 */       language = lang.substring(0, i1);
/* 27:52 */       i1++;
/* 28:53 */       int i2 = lang.indexOf('-', i1);
/* 29:54 */       if (i2 < 0)
/* 30:   */       {
/* 31:55 */         country = lang.substring(i1);
/* 32:   */       }
/* 33:   */       else
/* 34:   */       {
/* 35:57 */         country = lang.substring(i1, i2);
/* 36:58 */         variant = lang.substring(i2 + 1);
/* 37:   */       }
/* 38:   */     }
/* 39:62 */     if (language.length() == 2) {
/* 40:63 */       language = language.toLowerCase();
/* 41:   */     } else {
/* 42:65 */       language = "";
/* 43:   */     }
/* 44:68 */     if (country.length() == 2) {
/* 45:69 */       country = country.toUpperCase();
/* 46:   */     } else {
/* 47:71 */       country = "";
/* 48:   */     }
/* 49:74 */     if ((variant.length() > 0) && ((language.length() == 2) || (country.length() == 2))) {
/* 50:76 */       variant = variant.toUpperCase();
/* 51:   */     } else {
/* 52:78 */       variant = "";
/* 53:   */     }
/* 54:81 */     return new Locale(language, country, variant);
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.LocaleUtility
 * JD-Core Version:    0.7.0.1
 */