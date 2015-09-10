/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Delegator;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   6:    */ 
/*   7:    */ public abstract class SimpleScriptableProxy<T extends SimpleScriptable>
/*   8:    */   extends Delegator
/*   9:    */   implements ScriptableWithFallbackGetter, Serializable
/*  10:    */ {
/*  11:    */   public abstract T getDelegee();
/*  12:    */   
/*  13:    */   public Object get(int index, Scriptable start)
/*  14:    */   {
/*  15: 43 */     if ((start instanceof SimpleScriptableProxy)) {
/*  16: 44 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  17:    */     }
/*  18: 46 */     return getDelegee().get(index, start);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Object get(String name, Scriptable start)
/*  22:    */   {
/*  23: 54 */     if ((start instanceof SimpleScriptableProxy)) {
/*  24: 55 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  25:    */     }
/*  26: 57 */     return getDelegee().get(name, start);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object getWithFallback(String name)
/*  30:    */   {
/*  31: 64 */     SimpleScriptable delegee = getDelegee();
/*  32: 65 */     if ((delegee instanceof ScriptableWithFallbackGetter)) {
/*  33: 66 */       return ((ScriptableWithFallbackGetter)delegee).getWithFallback(name);
/*  34:    */     }
/*  35: 68 */     return NOT_FOUND;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean has(int index, Scriptable start)
/*  39:    */   {
/*  40: 76 */     if ((start instanceof SimpleScriptableProxy)) {
/*  41: 77 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  42:    */     }
/*  43: 79 */     return getDelegee().has(index, start);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean has(String name, Scriptable start)
/*  47:    */   {
/*  48: 87 */     if ((start instanceof SimpleScriptableProxy)) {
/*  49: 88 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  50:    */     }
/*  51: 90 */     return getDelegee().has(name, start);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean hasInstance(Scriptable instance)
/*  55:    */   {
/*  56: 98 */     if ((instance instanceof SimpleScriptableProxy)) {
/*  57: 99 */       instance = ((SimpleScriptableProxy)instance).getDelegee();
/*  58:    */     }
/*  59:101 */     return getDelegee().hasInstance(instance);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void put(int index, Scriptable start, Object value)
/*  63:    */   {
/*  64:109 */     if ((start instanceof SimpleScriptableProxy)) {
/*  65:110 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  66:    */     }
/*  67:112 */     getDelegee().put(index, start, value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void put(String name, Scriptable start, Object value)
/*  71:    */   {
/*  72:120 */     if ((start instanceof SimpleScriptableProxy)) {
/*  73:121 */       start = ((SimpleScriptableProxy)start).getDelegee();
/*  74:    */     }
/*  75:123 */     getDelegee().put(name, start, value);
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.SimpleScriptableProxy
 * JD-Core Version:    0.7.0.1
 */