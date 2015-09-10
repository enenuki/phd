/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class Delegator
/*   4:    */   implements Function
/*   5:    */ {
/*   6: 59 */   protected Scriptable obj = null;
/*   7:    */   
/*   8:    */   public Delegator() {}
/*   9:    */   
/*  10:    */   public Delegator(Scriptable obj)
/*  11:    */   {
/*  12: 80 */     this.obj = obj;
/*  13:    */   }
/*  14:    */   
/*  15:    */   protected Delegator newInstance()
/*  16:    */   {
/*  17:    */     try
/*  18:    */     {
/*  19: 92 */       return (Delegator)getClass().newInstance();
/*  20:    */     }
/*  21:    */     catch (Exception ex)
/*  22:    */     {
/*  23: 94 */       throw Context.throwAsScriptRuntimeEx(ex);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Scriptable getDelegee()
/*  28:    */   {
/*  29:104 */     return this.obj;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDelegee(Scriptable obj)
/*  33:    */   {
/*  34:113 */     this.obj = obj;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getClassName()
/*  38:    */   {
/*  39:119 */     return getDelegee().getClassName();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object get(String name, Scriptable start)
/*  43:    */   {
/*  44:125 */     return getDelegee().get(name, start);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object get(int index, Scriptable start)
/*  48:    */   {
/*  49:131 */     return getDelegee().get(index, start);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean has(String name, Scriptable start)
/*  53:    */   {
/*  54:137 */     return getDelegee().has(name, start);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean has(int index, Scriptable start)
/*  58:    */   {
/*  59:143 */     return getDelegee().has(index, start);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void put(String name, Scriptable start, Object value)
/*  63:    */   {
/*  64:149 */     getDelegee().put(name, start, value);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void put(int index, Scriptable start, Object value)
/*  68:    */   {
/*  69:155 */     getDelegee().put(index, start, value);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void delete(String name)
/*  73:    */   {
/*  74:161 */     getDelegee().delete(name);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void delete(int index)
/*  78:    */   {
/*  79:167 */     getDelegee().delete(index);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Scriptable getPrototype()
/*  83:    */   {
/*  84:173 */     return getDelegee().getPrototype();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setPrototype(Scriptable prototype)
/*  88:    */   {
/*  89:179 */     getDelegee().setPrototype(prototype);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Scriptable getParentScope()
/*  93:    */   {
/*  94:185 */     return getDelegee().getParentScope();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setParentScope(Scriptable parent)
/*  98:    */   {
/*  99:191 */     getDelegee().setParentScope(parent);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object[] getIds()
/* 103:    */   {
/* 104:197 */     return getDelegee().getIds();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Object getDefaultValue(Class<?> hint)
/* 108:    */   {
/* 109:212 */     return (hint == null) || (hint == ScriptRuntime.ScriptableClass) || (hint == ScriptRuntime.FunctionClass) ? this : getDelegee().getDefaultValue(hint);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean hasInstance(Scriptable instance)
/* 113:    */   {
/* 114:221 */     return getDelegee().hasInstance(instance);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 118:    */   {
/* 119:229 */     return ((Function)getDelegee()).call(cx, scope, thisObj, args);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/* 123:    */   {
/* 124:249 */     if (getDelegee() == null)
/* 125:    */     {
/* 126:252 */       Delegator n = newInstance();
/* 127:    */       Scriptable delegee;
/* 128:    */       Scriptable delegee;
/* 129:254 */       if (args.length == 0) {
/* 130:255 */         delegee = new NativeObject();
/* 131:    */       } else {
/* 132:257 */         delegee = ScriptRuntime.toObject(cx, scope, args[0]);
/* 133:    */       }
/* 134:259 */       n.setDelegee(delegee);
/* 135:260 */       return n;
/* 136:    */     }
/* 137:263 */     return ((Function)getDelegee()).construct(cx, scope, args);
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Delegator
 * JD-Core Version:    0.7.0.1
 */