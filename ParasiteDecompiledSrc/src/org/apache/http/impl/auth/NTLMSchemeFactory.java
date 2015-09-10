/*  1:   */ package org.apache.http.impl.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.auth.AuthScheme;
/*  5:   */ import org.apache.http.auth.AuthSchemeFactory;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class NTLMSchemeFactory
/* 10:   */   implements AuthSchemeFactory
/* 11:   */ {
/* 12:   */   public AuthScheme newInstance(HttpParams params)
/* 13:   */   {
/* 14:47 */     return new NTLMScheme(new NTLMEngineImpl());
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NTLMSchemeFactory
 * JD-Core Version:    0.7.0.1
 */