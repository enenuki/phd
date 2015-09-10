/*  1:   */ package org.apache.http.impl.auth;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.auth.AuthScheme;
/*  5:   */ import org.apache.http.auth.AuthSchemeFactory;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class NegotiateSchemeFactory
/* 10:   */   implements AuthSchemeFactory
/* 11:   */ {
/* 12:   */   private final SpnegoTokenGenerator spengoGenerator;
/* 13:   */   private final boolean stripPort;
/* 14:   */   
/* 15:   */   public NegotiateSchemeFactory(SpnegoTokenGenerator spengoGenerator, boolean stripPort)
/* 16:   */   {
/* 17:48 */     this.spengoGenerator = spengoGenerator;
/* 18:49 */     this.stripPort = stripPort;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public NegotiateSchemeFactory(SpnegoTokenGenerator spengoGenerator)
/* 22:   */   {
/* 23:53 */     this(spengoGenerator, false);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public NegotiateSchemeFactory()
/* 27:   */   {
/* 28:57 */     this(null, false);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public AuthScheme newInstance(HttpParams params)
/* 32:   */   {
/* 33:61 */     return new NegotiateScheme(this.spengoGenerator, this.stripPort);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean isStripPort()
/* 37:   */   {
/* 38:65 */     return this.stripPort;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public SpnegoTokenGenerator getSpengoGenerator()
/* 42:   */   {
/* 43:69 */     return this.spengoGenerator;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.NegotiateSchemeFactory
 * JD-Core Version:    0.7.0.1
 */