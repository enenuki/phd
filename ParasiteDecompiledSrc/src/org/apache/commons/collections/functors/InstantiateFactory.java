/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import org.apache.commons.collections.Factory;
/*   7:    */ import org.apache.commons.collections.FunctorException;
/*   8:    */ 
/*   9:    */ public class InstantiateFactory
/*  10:    */   implements Factory, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -7732226881069447957L;
/*  13:    */   private final Class iClassToInstantiate;
/*  14:    */   private final Class[] iParamTypes;
/*  15:    */   private final Object[] iArgs;
/*  16: 46 */   private transient Constructor iConstructor = null;
/*  17:    */   
/*  18:    */   public static Factory getInstance(Class classToInstantiate, Class[] paramTypes, Object[] args)
/*  19:    */   {
/*  20: 57 */     if (classToInstantiate == null) {
/*  21: 58 */       throw new IllegalArgumentException("Class to instantiate must not be null");
/*  22:    */     }
/*  23: 60 */     if (((paramTypes == null) && (args != null)) || ((paramTypes != null) && (args == null)) || ((paramTypes != null) && (args != null) && (paramTypes.length != args.length))) {
/*  24: 63 */       throw new IllegalArgumentException("Parameter types must match the arguments");
/*  25:    */     }
/*  26: 66 */     if ((paramTypes == null) || (paramTypes.length == 0)) {
/*  27: 67 */       return new InstantiateFactory(classToInstantiate);
/*  28:    */     }
/*  29: 69 */     paramTypes = (Class[])paramTypes.clone();
/*  30: 70 */     args = (Object[])args.clone();
/*  31: 71 */     return new InstantiateFactory(classToInstantiate, paramTypes, args);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public InstantiateFactory(Class classToInstantiate)
/*  35:    */   {
/*  36: 83 */     this.iClassToInstantiate = classToInstantiate;
/*  37: 84 */     this.iParamTypes = null;
/*  38: 85 */     this.iArgs = null;
/*  39: 86 */     findConstructor();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public InstantiateFactory(Class classToInstantiate, Class[] paramTypes, Object[] args)
/*  43:    */   {
/*  44: 99 */     this.iClassToInstantiate = classToInstantiate;
/*  45:100 */     this.iParamTypes = paramTypes;
/*  46:101 */     this.iArgs = args;
/*  47:102 */     findConstructor();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void findConstructor()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54:110 */       this.iConstructor = this.iClassToInstantiate.getConstructor(this.iParamTypes);
/*  55:    */     }
/*  56:    */     catch (NoSuchMethodException ex)
/*  57:    */     {
/*  58:113 */       throw new IllegalArgumentException("InstantiateFactory: The constructor must exist and be public ");
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object create()
/*  63:    */   {
/*  64:124 */     if (this.iConstructor == null) {
/*  65:125 */       findConstructor();
/*  66:    */     }
/*  67:    */     try
/*  68:    */     {
/*  69:129 */       return this.iConstructor.newInstance(this.iArgs);
/*  70:    */     }
/*  71:    */     catch (InstantiationException ex)
/*  72:    */     {
/*  73:132 */       throw new FunctorException("InstantiateFactory: InstantiationException", ex);
/*  74:    */     }
/*  75:    */     catch (IllegalAccessException ex)
/*  76:    */     {
/*  77:134 */       throw new FunctorException("InstantiateFactory: Constructor must be public", ex);
/*  78:    */     }
/*  79:    */     catch (InvocationTargetException ex)
/*  80:    */     {
/*  81:136 */       throw new FunctorException("InstantiateFactory: Constructor threw an exception", ex);
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.InstantiateFactory
 * JD-Core Version:    0.7.0.1
 */