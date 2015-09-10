/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import java.text.Collator;
/*  4:   */ import java.util.Locale;
/*  5:   */ import org.apache.xalan.xsltc.CollatorFactory;
/*  6:   */ 
/*  7:   */ public class CollatorFactoryBase
/*  8:   */   implements CollatorFactory
/*  9:   */ {
/* 10:34 */   public static final Locale DEFAULT_LOCALE = ;
/* 11:35 */   public static final Collator DEFAULT_COLLATOR = Collator.getInstance();
/* 12:   */   
/* 13:   */   public Collator getCollator(String lang, String country)
/* 14:   */   {
/* 15:41 */     return Collator.getInstance(new Locale(lang, country));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Collator getCollator(Locale locale)
/* 19:   */   {
/* 20:45 */     if (locale == DEFAULT_LOCALE) {
/* 21:46 */       return DEFAULT_COLLATOR;
/* 22:   */     }
/* 23:48 */     return Collator.getInstance(locale);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.CollatorFactoryBase
 * JD-Core Version:    0.7.0.1
 */