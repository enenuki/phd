/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   6:    */ 
/*   7:    */ class FunctionWrapper
/*   8:    */   implements Function
/*   9:    */ {
/*  10:    */   private final Function wrapped_;
/*  11:    */   
/*  12:    */   FunctionWrapper(Function wrapped)
/*  13:    */   {
/*  14: 30 */     this.wrapped_ = wrapped;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  18:    */   {
/*  19: 34 */     return this.wrapped_.call(cx, scope, thisObj, args);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getClassName()
/*  23:    */   {
/*  24: 38 */     return this.wrapped_.getClassName();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  28:    */   {
/*  29: 42 */     return this.wrapped_.construct(cx, scope, args);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object get(String name, Scriptable start)
/*  33:    */   {
/*  34: 46 */     return this.wrapped_.get(name, start);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object get(int index, Scriptable start)
/*  38:    */   {
/*  39: 50 */     return this.wrapped_.get(index, start);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean has(String name, Scriptable start)
/*  43:    */   {
/*  44: 54 */     return this.wrapped_.has(name, start);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean has(int index, Scriptable start)
/*  48:    */   {
/*  49: 58 */     return this.wrapped_.has(index, start);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void put(String name, Scriptable start, Object value)
/*  53:    */   {
/*  54: 62 */     this.wrapped_.put(name, start, value);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void put(int index, Scriptable start, Object value)
/*  58:    */   {
/*  59: 66 */     this.wrapped_.put(index, start, value);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void delete(String name)
/*  63:    */   {
/*  64: 70 */     this.wrapped_.delete(name);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void delete(int index)
/*  68:    */   {
/*  69: 74 */     this.wrapped_.delete(index);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Scriptable getPrototype()
/*  73:    */   {
/*  74: 78 */     return this.wrapped_.getPrototype();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setPrototype(Scriptable prototype)
/*  78:    */   {
/*  79: 82 */     this.wrapped_.setPrototype(prototype);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Scriptable getParentScope()
/*  83:    */   {
/*  84: 86 */     return this.wrapped_.getParentScope();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setParentScope(Scriptable parent)
/*  88:    */   {
/*  89: 90 */     this.wrapped_.setParentScope(parent);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object[] getIds()
/*  93:    */   {
/*  94: 94 */     return this.wrapped_.getIds();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Object getDefaultValue(Class<?> hint)
/*  98:    */   {
/*  99: 98 */     return this.wrapped_.getDefaultValue(hint);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean hasInstance(Scriptable instance)
/* 103:    */   {
/* 104:102 */     return this.wrapped_.hasInstance(instance);
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.FunctionWrapper
 * JD-Core Version:    0.7.0.1
 */