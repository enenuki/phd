/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   9:    */ import org.apache.commons.lang.ArrayUtils;
/*  10:    */ 
/*  11:    */ public class MethodWrapper
/*  12:    */   extends ScriptableObject
/*  13:    */   implements Function
/*  14:    */ {
/*  15:    */   private final Class<?> clazz_;
/*  16:    */   private final Method method_;
/*  17:    */   private final int[] jsTypeTags_;
/*  18:    */   
/*  19:    */   MethodWrapper(String methodName, Class<?> clazz)
/*  20:    */     throws NoSuchMethodException
/*  21:    */   {
/*  22: 47 */     this(methodName, clazz, ArrayUtils.EMPTY_CLASS_ARRAY);
/*  23:    */   }
/*  24:    */   
/*  25:    */   MethodWrapper(String methodName, Class<?> clazz, Class<?>[] parameterTypes)
/*  26:    */     throws NoSuchMethodException
/*  27:    */   {
/*  28: 60 */     this.clazz_ = clazz;
/*  29: 61 */     this.method_ = clazz.getMethod(methodName, parameterTypes);
/*  30: 62 */     this.jsTypeTags_ = new int[parameterTypes.length];
/*  31: 63 */     int i = 0;
/*  32: 64 */     for (Class<?> klass : parameterTypes) {
/*  33: 65 */       this.jsTypeTags_[(i++)] = FunctionObject.getTypeTag(klass);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getClassName()
/*  38:    */   {
/*  39: 75 */     return "function " + this.method_.getName();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object call(Context context, Scriptable scope, Scriptable thisObj, Object[] args)
/*  43:    */   {
/*  44:    */     Object javaResp;
/*  45: 83 */     if ((thisObj instanceof ScriptableWrapper))
/*  46:    */     {
/*  47: 84 */       ScriptableWrapper wrapper = (ScriptableWrapper)thisObj;
/*  48: 85 */       Object wrappedObject = wrapper.getWrappedObject();
/*  49: 86 */       if (this.clazz_.isInstance(wrappedObject))
/*  50:    */       {
/*  51: 88 */         Object[] javaArgs = convertJSArgsToJavaArgs(context, scope, args);
/*  52:    */         try
/*  53:    */         {
/*  54: 90 */           javaResp = this.method_.invoke(wrappedObject, javaArgs);
/*  55:    */         }
/*  56:    */         catch (Exception e)
/*  57:    */         {
/*  58:    */           Object javaResp;
/*  59: 93 */           throw Context.reportRuntimeError("Exception calling wrapped function " + this.method_.getName() + ": " + e.getMessage());
/*  60:    */         }
/*  61:    */       }
/*  62:    */       else
/*  63:    */       {
/*  64: 98 */         throw buildInvalidCallException(thisObj);
/*  65:    */       }
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:103 */       throw buildInvalidCallException(thisObj);
/*  70:    */     }
/*  71:    */     Object javaResp;
/*  72:106 */     Object jsResp = Context.javaToJS(javaResp, ScriptableObject.getTopLevelScope(scope));
/*  73:107 */     return jsResp;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private RuntimeException buildInvalidCallException(Scriptable thisObj)
/*  77:    */   {
/*  78:111 */     return Context.reportRuntimeError("Function " + this.method_.getName() + " called on incompatible object: " + thisObj);
/*  79:    */   }
/*  80:    */   
/*  81:    */   Object[] convertJSArgsToJavaArgs(Context context, Scriptable scope, Object[] jsArgs)
/*  82:    */   {
/*  83:123 */     if (jsArgs.length != this.jsTypeTags_.length) {
/*  84:124 */       throw Context.reportRuntimeError("Bad number of parameters for function " + this.method_.getName() + ": expected " + this.jsTypeTags_.length + " got " + jsArgs.length);
/*  85:    */     }
/*  86:127 */     Object[] javaArgs = new Object[jsArgs.length];
/*  87:128 */     int i = 0;
/*  88:129 */     for (Object object : jsArgs) {
/*  89:130 */       javaArgs[i] = FunctionObject.convertArg(context, scope, object, this.jsTypeTags_[(i++)]);
/*  90:    */     }
/*  91:132 */     return javaArgs;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Scriptable construct(Context context, Scriptable scope, Object[] args)
/*  95:    */   {
/*  96:139 */     throw Context.reportRuntimeError("Function " + this.method_.getName() + " can't be used as a constructor");
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.MethodWrapper
 * JD-Core Version:    0.7.0.1
 */