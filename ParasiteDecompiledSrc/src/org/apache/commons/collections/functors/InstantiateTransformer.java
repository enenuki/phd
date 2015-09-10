/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import org.apache.commons.collections.FunctorException;
/*   7:    */ import org.apache.commons.collections.Transformer;
/*   8:    */ 
/*   9:    */ public class InstantiateTransformer
/*  10:    */   implements Transformer, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 3786388740793356347L;
/*  13: 40 */   public static final Transformer NO_ARG_INSTANCE = new InstantiateTransformer();
/*  14:    */   private final Class[] iParamTypes;
/*  15:    */   private final Object[] iArgs;
/*  16:    */   
/*  17:    */   public static Transformer getInstance(Class[] paramTypes, Object[] args)
/*  18:    */   {
/*  19: 55 */     if (((paramTypes == null) && (args != null)) || ((paramTypes != null) && (args == null)) || ((paramTypes != null) && (args != null) && (paramTypes.length != args.length))) {
/*  20: 58 */       throw new IllegalArgumentException("Parameter types must match the arguments");
/*  21:    */     }
/*  22: 61 */     if ((paramTypes == null) || (paramTypes.length == 0)) {
/*  23: 62 */       return NO_ARG_INSTANCE;
/*  24:    */     }
/*  25: 64 */     paramTypes = (Class[])paramTypes.clone();
/*  26: 65 */     args = (Object[])args.clone();
/*  27:    */     
/*  28: 67 */     return new InstantiateTransformer(paramTypes, args);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private InstantiateTransformer()
/*  32:    */   {
/*  33: 75 */     this.iParamTypes = null;
/*  34: 76 */     this.iArgs = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public InstantiateTransformer(Class[] paramTypes, Object[] args)
/*  38:    */   {
/*  39: 88 */     this.iParamTypes = paramTypes;
/*  40: 89 */     this.iArgs = args;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object transform(Object input)
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47:100 */       if (!(input instanceof Class)) {
/*  48:101 */         throw new FunctorException("InstantiateTransformer: Input object was not an instanceof Class, it was a " + (input == null ? "null object" : input.getClass().getName()));
/*  49:    */       }
/*  50:105 */       Constructor con = ((Class)input).getConstructor(this.iParamTypes);
/*  51:106 */       return con.newInstance(this.iArgs);
/*  52:    */     }
/*  53:    */     catch (NoSuchMethodException ex)
/*  54:    */     {
/*  55:109 */       throw new FunctorException("InstantiateTransformer: The constructor must exist and be public ");
/*  56:    */     }
/*  57:    */     catch (InstantiationException ex)
/*  58:    */     {
/*  59:111 */       throw new FunctorException("InstantiateTransformer: InstantiationException", ex);
/*  60:    */     }
/*  61:    */     catch (IllegalAccessException ex)
/*  62:    */     {
/*  63:113 */       throw new FunctorException("InstantiateTransformer: Constructor must be public", ex);
/*  64:    */     }
/*  65:    */     catch (InvocationTargetException ex)
/*  66:    */     {
/*  67:115 */       throw new FunctorException("InstantiateTransformer: Constructor threw an exception", ex);
/*  68:    */     }
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.InstantiateTransformer
 * JD-Core Version:    0.7.0.1
 */