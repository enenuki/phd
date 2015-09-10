/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.net.URLConnection;
/*  5:   */ 
/*  6:   */ public class DefaultUrlConnectionExpiryCalculator
/*  7:   */   implements UrlConnectionExpiryCalculator, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private final long relativeExpiry;
/* 11:   */   
/* 12:   */   public DefaultUrlConnectionExpiryCalculator()
/* 13:   */   {
/* 14:25 */     this(60000L);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public DefaultUrlConnectionExpiryCalculator(long relativeExpiry)
/* 18:   */   {
/* 19:34 */     if (relativeExpiry < 0L) {
/* 20:35 */       throw new IllegalArgumentException("relativeExpiry < 0");
/* 21:   */     }
/* 22:37 */     this.relativeExpiry = relativeExpiry;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public long calculateExpiry(URLConnection urlConnection)
/* 26:   */   {
/* 27:41 */     return System.currentTimeMillis() + this.relativeExpiry;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.DefaultUrlConnectionExpiryCalculator
 * JD-Core Version:    0.7.0.1
 */