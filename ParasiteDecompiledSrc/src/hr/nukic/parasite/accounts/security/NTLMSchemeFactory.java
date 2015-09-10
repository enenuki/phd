/*  1:   */ package hr.nukic.parasite.accounts.security;
/*  2:   */ 
/*  3:   */ import org.apache.http.auth.AuthScheme;
/*  4:   */ import org.apache.http.auth.AuthSchemeFactory;
/*  5:   */ import org.apache.http.impl.auth.NTLMScheme;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ public class NTLMSchemeFactory
/*  9:   */   implements AuthSchemeFactory
/* 10:   */ {
/* 11:   */   public AuthScheme newInstance(HttpParams params)
/* 12:   */   {
/* 13:10 */     return new NTLMScheme(new JCIFSEngine());
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.security.NTLMSchemeFactory
 * JD-Core Version:    0.7.0.1
 */