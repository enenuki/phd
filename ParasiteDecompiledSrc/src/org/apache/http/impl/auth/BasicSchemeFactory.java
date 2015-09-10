/*  1:   */ package org.apache.http.impl.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.auth.AuthScheme;
/*  5:   */ import org.apache.http.auth.AuthSchemeFactory;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class BasicSchemeFactory
/* 10:   */   implements AuthSchemeFactory
/* 11:   */ {
/* 12:   */   public AuthScheme newInstance(HttpParams params)
/* 13:   */   {
/* 14:46 */     return new BasicScheme();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.BasicSchemeFactory
 * JD-Core Version:    0.7.0.1
 */