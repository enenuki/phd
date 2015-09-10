/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ import org.apache.http.cookie.CookieSpec;
/*  6:   */ import org.apache.http.cookie.CookieSpecFactory;
/*  7:   */ import org.apache.http.params.HttpParams;
/*  8:   */ 
/*  9:   */ @Immutable
/* 10:   */ public class BestMatchSpecFactory
/* 11:   */   implements CookieSpecFactory
/* 12:   */ {
/* 13:   */   public CookieSpec newInstance(HttpParams params)
/* 14:   */   {
/* 15:56 */     if (params != null)
/* 16:   */     {
/* 17:58 */       String[] patterns = null;
/* 18:59 */       Collection<?> param = (Collection)params.getParameter("http.protocol.cookie-datepatterns");
/* 19:61 */       if (param != null)
/* 20:   */       {
/* 21:62 */         patterns = new String[param.size()];
/* 22:63 */         patterns = (String[])param.toArray(patterns);
/* 23:   */       }
/* 24:65 */       boolean singleHeader = params.getBooleanParameter("http.protocol.single-cookie-header", false);
/* 25:   */       
/* 26:   */ 
/* 27:68 */       return new BestMatchSpec(patterns, singleHeader);
/* 28:   */     }
/* 29:70 */     return new BestMatchSpec();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BestMatchSpecFactory
 * JD-Core Version:    0.7.0.1
 */