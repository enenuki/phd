/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.IdFunctionObject;
/*  8:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  9:   */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/* 10:   */ 
/* 11:   */ class NativeFunctionToStringFunction
/* 12:   */   extends FunctionWrapper
/* 13:   */ {
/* 14:   */   private final String separator_;
/* 15:   */   
/* 16:   */   NativeFunctionToStringFunction(Function wrapped, String separator)
/* 17:   */   {
/* 18:36 */     super(wrapped);
/* 19:37 */     this.separator_ = separator;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 23:   */   {
/* 24:44 */     String s = (String)super.call(cx, scope, thisObj, args);
/* 25:46 */     if (((thisObj instanceof IdFunctionObject)) && (s.contains("() { [native code for ")))
/* 26:   */     {
/* 27:47 */       String functionName = ((IdFunctionObject)thisObj).getFunctionName();
/* 28:48 */       return this.separator_ + "function " + functionName + "() {\n    [native code]\n}" + this.separator_;
/* 29:   */     }
/* 30:50 */     return s.trim();
/* 31:   */   }
/* 32:   */   
/* 33:   */   static void installFix(Scriptable window, BrowserVersion browserVersion)
/* 34:   */   {
/* 35:59 */     ScriptableObject fnPrototype = (ScriptableObject)ScriptableObject.getClassPrototype(window, "Function");
/* 36:60 */     Function originalToString = (Function)ScriptableObject.getProperty(fnPrototype, "toString");
/* 37:   */     String separator;
/* 38:   */     String separator;
/* 39:62 */     if (browserVersion.hasFeature(BrowserVersionFeatures.JS_NATIVE_FUNCTION_TOSTRING_NEW_LINE)) {
/* 40:63 */       separator = "\n";
/* 41:   */     } else {
/* 42:66 */       separator = "";
/* 43:   */     }
/* 44:68 */     Function newToString = new NativeFunctionToStringFunction(originalToString, separator);
/* 45:69 */     ScriptableObject.putProperty(fnPrototype, "toString", newToString);
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.NativeFunctionToStringFunction
 * JD-Core Version:    0.7.0.1
 */