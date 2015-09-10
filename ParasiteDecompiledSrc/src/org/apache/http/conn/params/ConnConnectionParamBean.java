/*  1:   */ package org.apache.http.conn.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.NotThreadSafe;
/*  4:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  5:   */ import org.apache.http.params.HttpParams;
/*  6:   */ 
/*  7:   */ @NotThreadSafe
/*  8:   */ public class ConnConnectionParamBean
/*  9:   */   extends HttpAbstractParamBean
/* 10:   */ {
/* 11:   */   public ConnConnectionParamBean(HttpParams params)
/* 12:   */   {
/* 13:46 */     super(params);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setMaxStatusLineGarbage(int maxStatusLineGarbage)
/* 17:   */   {
/* 18:53 */     this.params.setIntParameter("http.connection.max-status-line-garbage", maxStatusLineGarbage);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnConnectionParamBean
 * JD-Core Version:    0.7.0.1
 */