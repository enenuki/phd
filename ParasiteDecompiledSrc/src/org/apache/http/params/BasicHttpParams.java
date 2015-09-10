/*   1:    */ package org.apache.http.params;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class BasicHttpParams
/*  10:    */   extends AbstractHttpParams
/*  11:    */   implements Serializable, Cloneable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -7086398485908701455L;
/*  14: 50 */   private final HashMap parameters = new HashMap();
/*  15:    */   
/*  16:    */   public Object getParameter(String name)
/*  17:    */   {
/*  18: 57 */     return this.parameters.get(name);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public HttpParams setParameter(String name, Object value)
/*  22:    */   {
/*  23: 61 */     this.parameters.put(name, value);
/*  24: 62 */     return this;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean removeParameter(String name)
/*  28:    */   {
/*  29: 67 */     if (this.parameters.containsKey(name))
/*  30:    */     {
/*  31: 68 */       this.parameters.remove(name);
/*  32: 69 */       return true;
/*  33:    */     }
/*  34: 71 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setParameters(String[] names, Object value)
/*  38:    */   {
/*  39: 82 */     for (int i = 0; i < names.length; i++) {
/*  40: 83 */       setParameter(names[i], value);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isParameterSet(String name)
/*  45:    */   {
/*  46: 99 */     return getParameter(name) != null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isParameterSetLocally(String name)
/*  50:    */   {
/*  51:113 */     return this.parameters.get(name) != null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void clear()
/*  55:    */   {
/*  56:120 */     this.parameters.clear();
/*  57:    */   }
/*  58:    */   
/*  59:    */   /**
/*  60:    */    * @deprecated
/*  61:    */    */
/*  62:    */   public HttpParams copy()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:135 */       return (HttpParams)clone();
/*  67:    */     }
/*  68:    */     catch (CloneNotSupportedException ex)
/*  69:    */     {
/*  70:137 */       throw new UnsupportedOperationException("Cloning not supported");
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object clone()
/*  75:    */     throws CloneNotSupportedException
/*  76:    */   {
/*  77:146 */     BasicHttpParams clone = (BasicHttpParams)super.clone();
/*  78:147 */     copyParams(clone);
/*  79:148 */     return clone;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void copyParams(HttpParams target)
/*  83:    */   {
/*  84:152 */     Iterator iter = this.parameters.entrySet().iterator();
/*  85:153 */     while (iter.hasNext())
/*  86:    */     {
/*  87:154 */       Map.Entry me = (Map.Entry)iter.next();
/*  88:155 */       if ((me.getKey() instanceof String)) {
/*  89:156 */         target.setParameter((String)me.getKey(), me.getValue());
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.BasicHttpParams
 * JD-Core Version:    0.7.0.1
 */