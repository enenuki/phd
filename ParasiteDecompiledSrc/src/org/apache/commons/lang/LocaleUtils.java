/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ 
/*  13:    */ public class LocaleUtils
/*  14:    */ {
/*  15:    */   private static List cAvailableLocaleList;
/*  16:    */   private static Set cAvailableLocaleSet;
/*  17: 49 */   private static final Map cLanguagesByCountry = Collections.synchronizedMap(new HashMap());
/*  18: 52 */   private static final Map cCountriesByLanguage = Collections.synchronizedMap(new HashMap());
/*  19:    */   
/*  20:    */   public static Locale toLocale(String str)
/*  21:    */   {
/*  22: 94 */     if (str == null) {
/*  23: 95 */       return null;
/*  24:    */     }
/*  25: 97 */     int len = str.length();
/*  26: 98 */     if ((len != 2) && (len != 5) && (len < 7)) {
/*  27: 99 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*  28:    */     }
/*  29:101 */     char ch0 = str.charAt(0);
/*  30:102 */     char ch1 = str.charAt(1);
/*  31:103 */     if ((ch0 < 'a') || (ch0 > 'z') || (ch1 < 'a') || (ch1 > 'z')) {
/*  32:104 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*  33:    */     }
/*  34:106 */     if (len == 2) {
/*  35:107 */       return new Locale(str, "");
/*  36:    */     }
/*  37:109 */     if (str.charAt(2) != '_') {
/*  38:110 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*  39:    */     }
/*  40:112 */     char ch3 = str.charAt(3);
/*  41:113 */     if (ch3 == '_') {
/*  42:114 */       return new Locale(str.substring(0, 2), "", str.substring(4));
/*  43:    */     }
/*  44:116 */     char ch4 = str.charAt(4);
/*  45:117 */     if ((ch3 < 'A') || (ch3 > 'Z') || (ch4 < 'A') || (ch4 > 'Z')) {
/*  46:118 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*  47:    */     }
/*  48:120 */     if (len == 5) {
/*  49:121 */       return new Locale(str.substring(0, 2), str.substring(3, 5));
/*  50:    */     }
/*  51:123 */     if (str.charAt(5) != '_') {
/*  52:124 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*  53:    */     }
/*  54:126 */     return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static List localeLookupList(Locale locale)
/*  58:    */   {
/*  59:145 */     return localeLookupList(locale, locale);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static List localeLookupList(Locale locale, Locale defaultLocale)
/*  63:    */   {
/*  64:167 */     List list = new ArrayList(4);
/*  65:168 */     if (locale != null)
/*  66:    */     {
/*  67:169 */       list.add(locale);
/*  68:170 */       if (locale.getVariant().length() > 0) {
/*  69:171 */         list.add(new Locale(locale.getLanguage(), locale.getCountry()));
/*  70:    */       }
/*  71:173 */       if (locale.getCountry().length() > 0) {
/*  72:174 */         list.add(new Locale(locale.getLanguage(), ""));
/*  73:    */       }
/*  74:176 */       if (!list.contains(defaultLocale)) {
/*  75:177 */         list.add(defaultLocale);
/*  76:    */       }
/*  77:    */     }
/*  78:180 */     return Collections.unmodifiableList(list);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static List availableLocaleList()
/*  82:    */   {
/*  83:194 */     if (cAvailableLocaleList == null) {
/*  84:195 */       initAvailableLocaleList();
/*  85:    */     }
/*  86:197 */     return cAvailableLocaleList;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static synchronized void initAvailableLocaleList()
/*  90:    */   {
/*  91:206 */     if (cAvailableLocaleList == null)
/*  92:    */     {
/*  93:207 */       List list = Arrays.asList(Locale.getAvailableLocales());
/*  94:208 */       cAvailableLocaleList = Collections.unmodifiableList(list);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Set availableLocaleSet()
/*  99:    */   {
/* 100:223 */     if (cAvailableLocaleSet == null) {
/* 101:224 */       initAvailableLocaleSet();
/* 102:    */     }
/* 103:226 */     return cAvailableLocaleSet;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private static synchronized void initAvailableLocaleSet()
/* 107:    */   {
/* 108:235 */     if (cAvailableLocaleSet == null) {
/* 109:236 */       cAvailableLocaleSet = Collections.unmodifiableSet(new HashSet(availableLocaleList()));
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean isAvailableLocale(Locale locale)
/* 114:    */   {
/* 115:248 */     return availableLocaleList().contains(locale);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static List languagesByCountry(String countryCode)
/* 119:    */   {
/* 120:262 */     List langs = (List)cLanguagesByCountry.get(countryCode);
/* 121:263 */     if (langs == null)
/* 122:    */     {
/* 123:264 */       if (countryCode != null)
/* 124:    */       {
/* 125:265 */         langs = new ArrayList();
/* 126:266 */         List locales = availableLocaleList();
/* 127:267 */         for (int i = 0; i < locales.size(); i++)
/* 128:    */         {
/* 129:268 */           Locale locale = (Locale)locales.get(i);
/* 130:269 */           if ((countryCode.equals(locale.getCountry())) && (locale.getVariant().length() == 0)) {
/* 131:271 */             langs.add(locale);
/* 132:    */           }
/* 133:    */         }
/* 134:274 */         langs = Collections.unmodifiableList(langs);
/* 135:    */       }
/* 136:    */       else
/* 137:    */       {
/* 138:276 */         langs = Collections.EMPTY_LIST;
/* 139:    */       }
/* 140:278 */       cLanguagesByCountry.put(countryCode, langs);
/* 141:    */     }
/* 142:280 */     return langs;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static List countriesByLanguage(String languageCode)
/* 146:    */   {
/* 147:294 */     List countries = (List)cCountriesByLanguage.get(languageCode);
/* 148:295 */     if (countries == null)
/* 149:    */     {
/* 150:296 */       if (languageCode != null)
/* 151:    */       {
/* 152:297 */         countries = new ArrayList();
/* 153:298 */         List locales = availableLocaleList();
/* 154:299 */         for (int i = 0; i < locales.size(); i++)
/* 155:    */         {
/* 156:300 */           Locale locale = (Locale)locales.get(i);
/* 157:301 */           if ((languageCode.equals(locale.getLanguage())) && (locale.getCountry().length() != 0) && (locale.getVariant().length() == 0)) {
/* 158:304 */             countries.add(locale);
/* 159:    */           }
/* 160:    */         }
/* 161:307 */         countries = Collections.unmodifiableList(countries);
/* 162:    */       }
/* 163:    */       else
/* 164:    */       {
/* 165:309 */         countries = Collections.EMPTY_LIST;
/* 166:    */       }
/* 167:311 */       cCountriesByLanguage.put(languageCode, countries);
/* 168:    */     }
/* 169:313 */     return countries;
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.LocaleUtils
 * JD-Core Version:    0.7.0.1
 */