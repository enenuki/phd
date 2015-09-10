/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Modifier;
/*   4:    */ import org.hibernate.bytecode.buildtime.spi.ClassFilter;
/*   5:    */ import org.hibernate.bytecode.buildtime.spi.FieldFilter;
/*   6:    */ import org.hibernate.bytecode.spi.BytecodeProvider;
/*   7:    */ import org.hibernate.bytecode.spi.ClassTransformer;
/*   8:    */ import org.hibernate.bytecode.spi.ProxyFactoryFactory;
/*   9:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ 
/*  14:    */ public class BytecodeProviderImpl
/*  15:    */   implements BytecodeProvider
/*  16:    */ {
/*  17: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BytecodeProviderImpl.class.getName());
/*  18:    */   
/*  19:    */   public ProxyFactoryFactory getProxyFactoryFactory()
/*  20:    */   {
/*  21: 49 */     return new ProxyFactoryFactoryImpl();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ReflectionOptimizer getReflectionOptimizer(Class clazz, String[] getterNames, String[] setterNames, Class[] types)
/*  25:    */   {
/*  26:    */     FastClass fastClass;
/*  27:    */     BulkAccessor bulkAccessor;
/*  28:    */     try
/*  29:    */     {
/*  30: 60 */       fastClass = FastClass.create(clazz);
/*  31: 61 */       bulkAccessor = BulkAccessor.create(clazz, getterNames, setterNames, types);
/*  32: 62 */       if ((!clazz.isInterface()) && (!Modifier.isAbstract(clazz.getModifiers()))) {
/*  33: 63 */         if (fastClass == null)
/*  34:    */         {
/*  35: 64 */           bulkAccessor = null;
/*  36:    */         }
/*  37:    */         else
/*  38:    */         {
/*  39: 68 */           Object instance = fastClass.newInstance();
/*  40: 69 */           bulkAccessor.setPropertyValues(instance, bulkAccessor.getPropertyValues(instance));
/*  41:    */         }
/*  42:    */       }
/*  43:    */     }
/*  44:    */     catch (Throwable t)
/*  45:    */     {
/*  46: 74 */       fastClass = null;
/*  47: 75 */       bulkAccessor = null;
/*  48: 76 */       if (LOG.isDebugEnabled())
/*  49:    */       {
/*  50: 77 */         int index = 0;
/*  51: 78 */         if ((t instanceof BulkAccessorException)) {
/*  52: 78 */           index = ((BulkAccessorException)t).getIndex();
/*  53:    */         }
/*  54: 79 */         if (index >= 0) {
/*  55: 79 */           LOG.debugf("Reflection optimizer disabled for: %s [%s: %s (property %s)", new Object[] { clazz.getName(), StringHelper.unqualify(t.getClass().getName()), t.getMessage(), setterNames[index] });
/*  56:    */         } else {
/*  57: 84 */           LOG.debugf("Reflection optimizer disabled for: %s [%s: %s", clazz.getName(), StringHelper.unqualify(t.getClass().getName()), t.getMessage());
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61: 91 */     if ((fastClass != null) && (bulkAccessor != null)) {
/*  62: 92 */       return new ReflectionOptimizerImpl(new InstantiationOptimizerAdapter(fastClass), new AccessOptimizerAdapter(bulkAccessor, clazz));
/*  63:    */     }
/*  64: 97 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ClassTransformer getTransformer(ClassFilter classFilter, FieldFilter fieldFilter)
/*  68:    */   {
/*  69:101 */     return new JavassistClassTransformer(classFilter, fieldFilter);
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.BytecodeProviderImpl
 * JD-Core Version:    0.7.0.1
 */