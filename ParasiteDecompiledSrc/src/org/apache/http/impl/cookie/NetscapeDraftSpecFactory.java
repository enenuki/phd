/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ import org.apache.http.cookie.CookieSpec;
/*  6:   */ import org.apache.http.cookie.CookieSpecFactory;
/*  7:   */ import org.apache.http.params.HttpParams;
/*  8:   */ 
/*  9:   */ @Immutable
/* 10:   */ public class NetscapeDraftSpecFactory
/* 11:   */   implements CookieSpecFactory
/* 12:   */ {
/* 13:   */   public CookieSpec newInstance(HttpParams params)
/* 14:   */   {
/* 15:55 */     if (params != null)
/* 16:   */     {
/* 17:57 */       String[] patterns = null;
/* 18:58 */       Collection<?> param = (Collection)params.getParameter("http.protocol.cookie-datepatterns");
/* 19:60 */       if (param != null)
/* 20:   */       {
/* 21:61 */         patterns = new String[param.size()];
/* 22:62 */         patterns = (String[])param.toArray(patterns);
/* 23:   */       }
/* 24:64 */       return new NetscapeDraftSpec(patterns);
/* 25:   */     }
/* 26:66 */     return new NetscapeDraftSpec();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.NetscapeDraftSpecFactory
 * JD-Core Version:    0.7.0.1
 */