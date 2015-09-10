/*  1:   */ package org.apache.http.cookie.params;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  6:   */ import org.apache.http.params.HttpParams;
/*  7:   */ 
/*  8:   */ @NotThreadSafe
/*  9:   */ public class CookieSpecParamBean
/* 10:   */   extends HttpAbstractParamBean
/* 11:   */ {
/* 12:   */   public CookieSpecParamBean(HttpParams params)
/* 13:   */   {
/* 14:48 */     super(params);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setDatePatterns(Collection<String> patterns)
/* 18:   */   {
/* 19:52 */     this.params.setParameter("http.protocol.cookie-datepatterns", patterns);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setSingleHeader(boolean singleHeader)
/* 23:   */   {
/* 24:56 */     this.params.setBooleanParameter("http.protocol.single-cookie-header", singleHeader);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.params.CookieSpecParamBean
 * JD-Core Version:    0.7.0.1
 */