/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.cookie.CookieSpec;
/*  5:   */ import org.apache.http.cookie.CookieSpecFactory;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class IgnoreSpecFactory
/* 10:   */   implements CookieSpecFactory
/* 11:   */ {
/* 12:   */   public CookieSpec newInstance(HttpParams params)
/* 13:   */   {
/* 14:44 */     return new IgnoreSpec();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.IgnoreSpecFactory
 * JD-Core Version:    0.7.0.1
 */