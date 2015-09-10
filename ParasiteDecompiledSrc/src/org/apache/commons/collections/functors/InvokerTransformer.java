/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import org.apache.commons.collections.FunctorException;
/*   7:    */ import org.apache.commons.collections.Transformer;
/*   8:    */ 
/*   9:    */ public class InvokerTransformer
/*  10:    */   implements Transformer, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -8653385846894047688L;
/*  13:    */   private final String iMethodName;
/*  14:    */   private final Class[] iParamTypes;
/*  15:    */   private final Object[] iArgs;
/*  16:    */   
/*  17:    */   public static Transformer getInstance(String methodName)
/*  18:    */   {
/*  19: 54 */     if (methodName == null) {
/*  20: 55 */       throw new IllegalArgumentException("The method to invoke must not be null");
/*  21:    */     }
/*  22: 57 */     return new InvokerTransformer(methodName);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Transformer getInstance(String methodName, Class[] paramTypes, Object[] args)
/*  26:    */   {
/*  27: 69 */     if (methodName == null) {
/*  28: 70 */       throw new IllegalArgumentException("The method to invoke must not be null");
/*  29:    */     }
/*  30: 72 */     if (((paramTypes == null) && (args != null)) || ((paramTypes != null) && (args == null)) || ((paramTypes != null) && (args != null) && (paramTypes.length != args.length))) {
/*  31: 75 */       throw new IllegalArgumentException("The parameter types must match the arguments");
/*  32:    */     }
/*  33: 77 */     if ((paramTypes == null) || (paramTypes.length == 0)) {
/*  34: 78 */       return new InvokerTransformer(methodName);
/*  35:    */     }
/*  36: 80 */     paramTypes = (Class[])paramTypes.clone();
/*  37: 81 */     args = (Object[])args.clone();
/*  38: 82 */     return new InvokerTransformer(methodName, paramTypes, args);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private InvokerTransformer(String methodName)
/*  42:    */   {
/*  43: 93 */     this.iMethodName = methodName;
/*  44: 94 */     this.iParamTypes = null;
/*  45: 95 */     this.iArgs = null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public InvokerTransformer(String methodName, Class[] paramTypes, Object[] args)
/*  49:    */   {
/*  50:108 */     this.iMethodName = methodName;
/*  51:109 */     this.iParamTypes = paramTypes;
/*  52:110 */     this.iArgs = args;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object transform(Object input)
/*  56:    */   {
/*  57:120 */     if (input == null) {
/*  58:121 */       return null;
/*  59:    */     }
/*  60:    */     try
/*  61:    */     {
/*  62:124 */       Class cls = input.getClass();
/*  63:125 */       Method method = cls.getMethod(this.iMethodName, this.iParamTypes);
/*  64:126 */       return method.invoke(input, this.iArgs);
/*  65:    */     }
/*  66:    */     catch (NoSuchMethodException ex)
/*  67:    */     {
/*  68:129 */       throw new FunctorException("InvokerTransformer: The method '" + this.iMethodName + "' on '" + input.getClass() + "' does not exist");
/*  69:    */     }
/*  70:    */     catch (IllegalAccessException ex)
/*  71:    */     {
/*  72:131 */       throw new FunctorException("InvokerTransformer: The method '" + this.iMethodName + "' on '" + input.getClass() + "' cannot be accessed");
/*  73:    */     }
/*  74:    */     catch (InvocationTargetException ex)
/*  75:    */     {
/*  76:133 */       throw new FunctorException("InvokerTransformer: The method '" + this.iMethodName + "' on '" + input.getClass() + "' threw an exception", ex);
/*  77:    */     }
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.InvokerTransformer
 * JD-Core Version:    0.7.0.1
 */