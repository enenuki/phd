/*   1:    */ package org.apache.http.params;
/*   2:    */ 
/*   3:    */ public abstract class AbstractHttpParams
/*   4:    */   implements HttpParams
/*   5:    */ {
/*   6:    */   public long getLongParameter(String name, long defaultValue)
/*   7:    */   {
/*   8: 49 */     Object param = getParameter(name);
/*   9: 50 */     if (param == null) {
/*  10: 51 */       return defaultValue;
/*  11:    */     }
/*  12: 53 */     return ((Long)param).longValue();
/*  13:    */   }
/*  14:    */   
/*  15:    */   public HttpParams setLongParameter(String name, long value)
/*  16:    */   {
/*  17: 57 */     setParameter(name, new Long(value));
/*  18: 58 */     return this;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getIntParameter(String name, int defaultValue)
/*  22:    */   {
/*  23: 62 */     Object param = getParameter(name);
/*  24: 63 */     if (param == null) {
/*  25: 64 */       return defaultValue;
/*  26:    */     }
/*  27: 66 */     return ((Integer)param).intValue();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public HttpParams setIntParameter(String name, int value)
/*  31:    */   {
/*  32: 70 */     setParameter(name, new Integer(value));
/*  33: 71 */     return this;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public double getDoubleParameter(String name, double defaultValue)
/*  37:    */   {
/*  38: 75 */     Object param = getParameter(name);
/*  39: 76 */     if (param == null) {
/*  40: 77 */       return defaultValue;
/*  41:    */     }
/*  42: 79 */     return ((Double)param).doubleValue();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public HttpParams setDoubleParameter(String name, double value)
/*  46:    */   {
/*  47: 83 */     setParameter(name, new Double(value));
/*  48: 84 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean getBooleanParameter(String name, boolean defaultValue)
/*  52:    */   {
/*  53: 88 */     Object param = getParameter(name);
/*  54: 89 */     if (param == null) {
/*  55: 90 */       return defaultValue;
/*  56:    */     }
/*  57: 92 */     return ((Boolean)param).booleanValue();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public HttpParams setBooleanParameter(String name, boolean value)
/*  61:    */   {
/*  62: 96 */     setParameter(name, value ? Boolean.TRUE : Boolean.FALSE);
/*  63: 97 */     return this;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isParameterTrue(String name)
/*  67:    */   {
/*  68:101 */     return getBooleanParameter(name, false);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isParameterFalse(String name)
/*  72:    */   {
/*  73:105 */     return !getBooleanParameter(name, false);
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.AbstractHttpParams
 * JD-Core Version:    0.7.0.1
 */