/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public abstract interface Scriptable
/*  4:   */ {
/*  5:76 */   public static final Object NOT_FOUND = UniqueTag.NOT_FOUND;
/*  6:   */   
/*  7:   */   public abstract String getClassName();
/*  8:   */   
/*  9:   */   public abstract Object get(String paramString, Scriptable paramScriptable);
/* 10:   */   
/* 11:   */   public abstract Object get(int paramInt, Scriptable paramScriptable);
/* 12:   */   
/* 13:   */   public abstract boolean has(String paramString, Scriptable paramScriptable);
/* 14:   */   
/* 15:   */   public abstract boolean has(int paramInt, Scriptable paramScriptable);
/* 16:   */   
/* 17:   */   public abstract void put(String paramString, Scriptable paramScriptable, Object paramObject);
/* 18:   */   
/* 19:   */   public abstract void put(int paramInt, Scriptable paramScriptable, Object paramObject);
/* 20:   */   
/* 21:   */   public abstract void delete(String paramString);
/* 22:   */   
/* 23:   */   public abstract void delete(int paramInt);
/* 24:   */   
/* 25:   */   public abstract Scriptable getPrototype();
/* 26:   */   
/* 27:   */   public abstract void setPrototype(Scriptable paramScriptable);
/* 28:   */   
/* 29:   */   public abstract Scriptable getParentScope();
/* 30:   */   
/* 31:   */   public abstract void setParentScope(Scriptable paramScriptable);
/* 32:   */   
/* 33:   */   public abstract Object[] getIds();
/* 34:   */   
/* 35:   */   public abstract Object getDefaultValue(Class<?> paramClass);
/* 36:   */   
/* 37:   */   public abstract boolean hasInstance(Scriptable paramScriptable);
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Scriptable
 * JD-Core Version:    0.7.0.1
 */