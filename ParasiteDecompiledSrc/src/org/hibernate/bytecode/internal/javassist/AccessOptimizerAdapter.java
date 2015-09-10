/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.PropertyAccessException;
/*   5:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
/*   6:    */ 
/*   7:    */ public class AccessOptimizerAdapter
/*   8:    */   implements ReflectionOptimizer.AccessOptimizer, Serializable
/*   9:    */ {
/*  10:    */   public static final String PROPERTY_GET_EXCEPTION = "exception getting property value with Javassist (set hibernate.bytecode.use_reflection_optimizer=false for more info)";
/*  11:    */   public static final String PROPERTY_SET_EXCEPTION = "exception setting property value with Javassist (set hibernate.bytecode.use_reflection_optimizer=false for more info)";
/*  12:    */   private final BulkAccessor bulkAccessor;
/*  13:    */   private final Class mappedClass;
/*  14:    */   
/*  15:    */   public AccessOptimizerAdapter(BulkAccessor bulkAccessor, Class mappedClass)
/*  16:    */   {
/*  17: 49 */     this.bulkAccessor = bulkAccessor;
/*  18: 50 */     this.mappedClass = mappedClass;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String[] getPropertyNames()
/*  22:    */   {
/*  23: 54 */     return this.bulkAccessor.getGetters();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object[] getPropertyValues(Object object)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 59 */       return this.bulkAccessor.getPropertyValues(object);
/*  31:    */     }
/*  32:    */     catch (Throwable t)
/*  33:    */     {
/*  34: 62 */       throw new PropertyAccessException(t, "exception getting property value with Javassist (set hibernate.bytecode.use_reflection_optimizer=false for more info)", false, this.mappedClass, getterName(t, this.bulkAccessor));
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setPropertyValues(Object object, Object[] values)
/*  39:    */   {
/*  40:    */     try
/*  41:    */     {
/*  42: 74 */       this.bulkAccessor.setPropertyValues(object, values);
/*  43:    */     }
/*  44:    */     catch (Throwable t)
/*  45:    */     {
/*  46: 77 */       throw new PropertyAccessException(t, "exception setting property value with Javassist (set hibernate.bytecode.use_reflection_optimizer=false for more info)", true, this.mappedClass, setterName(t, this.bulkAccessor));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static String setterName(Throwable t, BulkAccessor accessor)
/*  51:    */   {
/*  52: 88 */     if ((t instanceof BulkAccessorException)) {
/*  53: 89 */       return accessor.getSetters()[((BulkAccessorException)t).getIndex()];
/*  54:    */     }
/*  55: 92 */     return "?";
/*  56:    */   }
/*  57:    */   
/*  58:    */   private static String getterName(Throwable t, BulkAccessor accessor)
/*  59:    */   {
/*  60: 97 */     if ((t instanceof BulkAccessorException)) {
/*  61: 98 */       return accessor.getGetters()[((BulkAccessorException)t).getIndex()];
/*  62:    */     }
/*  63:101 */     return "?";
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.AccessOptimizerAdapter
 * JD-Core Version:    0.7.0.1
 */