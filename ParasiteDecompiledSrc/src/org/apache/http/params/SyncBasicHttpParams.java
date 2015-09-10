/*  1:   */ package org.apache.http.params;
/*  2:   */ 
/*  3:   */ public class SyncBasicHttpParams
/*  4:   */   extends BasicHttpParams
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 5387834869062660642L;
/*  7:   */   
/*  8:   */   public synchronized boolean removeParameter(String name)
/*  9:   */   {
/* 10:43 */     return super.removeParameter(name);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public synchronized HttpParams setParameter(String name, Object value)
/* 14:   */   {
/* 15:47 */     return super.setParameter(name, value);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public synchronized Object getParameter(String name)
/* 19:   */   {
/* 20:51 */     return super.getParameter(name);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public synchronized boolean isParameterSet(String name)
/* 24:   */   {
/* 25:55 */     return super.isParameterSet(name);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public synchronized boolean isParameterSetLocally(String name)
/* 29:   */   {
/* 30:59 */     return super.isParameterSetLocally(name);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public synchronized void setParameters(String[] names, Object value)
/* 34:   */   {
/* 35:63 */     super.setParameters(names, value);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public synchronized void clear()
/* 39:   */   {
/* 40:67 */     super.clear();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public synchronized Object clone()
/* 44:   */     throws CloneNotSupportedException
/* 45:   */   {
/* 46:71 */     return super.clone();
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.SyncBasicHttpParams
 * JD-Core Version:    0.7.0.1
 */