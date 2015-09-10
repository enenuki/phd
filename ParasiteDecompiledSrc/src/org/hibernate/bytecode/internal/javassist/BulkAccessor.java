/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public abstract class BulkAccessor
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   protected Class target;
/*   9:    */   protected String[] getters;
/*  10:    */   protected String[] setters;
/*  11:    */   protected Class[] types;
/*  12:    */   
/*  13:    */   public abstract void getPropertyValues(Object paramObject, Object[] paramArrayOfObject);
/*  14:    */   
/*  15:    */   public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject);
/*  16:    */   
/*  17:    */   public Object[] getPropertyValues(Object bean)
/*  18:    */   {
/*  19: 69 */     Object[] values = new Object[this.getters.length];
/*  20: 70 */     getPropertyValues(bean, values);
/*  21: 71 */     return values;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Class[] getPropertyTypes()
/*  25:    */   {
/*  26: 78 */     return (Class[])this.types.clone();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String[] getGetters()
/*  30:    */   {
/*  31: 85 */     return (String[])this.getters.clone();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String[] getSetters()
/*  35:    */   {
/*  36: 92 */     return (String[])this.setters.clone();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static BulkAccessor create(Class beanClass, String[] getters, String[] setters, Class[] types)
/*  40:    */   {
/*  41:111 */     BulkAccessorFactory factory = new BulkAccessorFactory(beanClass, getters, setters, types);
/*  42:112 */     return factory.create();
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.BulkAccessor
 * JD-Core Version:    0.7.0.1
 */