/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import org.apache.commons.collections.functors.ConstantFactory;
/*   4:    */ import org.apache.commons.collections.functors.ExceptionFactory;
/*   5:    */ import org.apache.commons.collections.functors.InstantiateFactory;
/*   6:    */ import org.apache.commons.collections.functors.PrototypeFactory;
/*   7:    */ 
/*   8:    */ public class FactoryUtils
/*   9:    */ {
/*  10:    */   public static Factory exceptionFactory()
/*  11:    */   {
/*  12: 59 */     return ExceptionFactory.INSTANCE;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static Factory nullFactory()
/*  16:    */   {
/*  17: 71 */     return ConstantFactory.NULL_INSTANCE;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Factory constantFactory(Object constantToReturn)
/*  21:    */   {
/*  22: 86 */     return ConstantFactory.getInstance(constantToReturn);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Factory prototypeFactory(Object prototype)
/*  26:    */   {
/*  27:107 */     return PrototypeFactory.getInstance(prototype);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Factory instantiateFactory(Class classToInstantiate)
/*  31:    */   {
/*  32:121 */     return InstantiateFactory.getInstance(classToInstantiate, null, null);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Factory instantiateFactory(Class classToInstantiate, Class[] paramTypes, Object[] args)
/*  36:    */   {
/*  37:139 */     return InstantiateFactory.getInstance(classToInstantiate, paramTypes, args);
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.FactoryUtils
 * JD-Core Version:    0.7.0.1
 */