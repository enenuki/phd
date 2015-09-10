/*   1:    */ package org.apache.http.params;
/*   2:    */ 
/*   3:    */ public final class DefaultedHttpParams
/*   4:    */   extends AbstractHttpParams
/*   5:    */ {
/*   6:    */   private final HttpParams local;
/*   7:    */   private final HttpParams defaults;
/*   8:    */   
/*   9:    */   public DefaultedHttpParams(HttpParams local, HttpParams defaults)
/*  10:    */   {
/*  11: 51 */     if (local == null) {
/*  12: 52 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  13:    */     }
/*  14: 54 */     this.local = local;
/*  15: 55 */     this.defaults = defaults;
/*  16:    */   }
/*  17:    */   
/*  18:    */   /**
/*  19:    */    * @deprecated
/*  20:    */    */
/*  21:    */   public HttpParams copy()
/*  22:    */   {
/*  23: 64 */     HttpParams clone = this.local.copy();
/*  24: 65 */     return new DefaultedHttpParams(clone, this.defaults);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Object getParameter(String name)
/*  28:    */   {
/*  29: 74 */     Object obj = this.local.getParameter(name);
/*  30: 75 */     if ((obj == null) && (this.defaults != null)) {
/*  31: 76 */       obj = this.defaults.getParameter(name);
/*  32:    */     }
/*  33: 78 */     return obj;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean removeParameter(String name)
/*  37:    */   {
/*  38: 86 */     return this.local.removeParameter(name);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public HttpParams setParameter(String name, Object value)
/*  42:    */   {
/*  43: 94 */     return this.local.setParameter(name, value);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public HttpParams getDefaults()
/*  47:    */   {
/*  48:102 */     return this.defaults;
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.DefaultedHttpParams
 * JD-Core Version:    0.7.0.1
 */