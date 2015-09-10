/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ class SpecialRef
/*   4:    */   extends Ref
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -7521596632456797847L;
/*   7:    */   private static final int SPECIAL_NONE = 0;
/*   8:    */   private static final int SPECIAL_PROTO = 1;
/*   9:    */   private static final int SPECIAL_PARENT = 2;
/*  10:    */   private Scriptable target;
/*  11:    */   private int type;
/*  12:    */   private String name;
/*  13:    */   
/*  14:    */   private SpecialRef(Scriptable target, int type, String name)
/*  15:    */   {
/*  16: 55 */     this.target = target;
/*  17: 56 */     this.type = type;
/*  18: 57 */     this.name = name;
/*  19:    */   }
/*  20:    */   
/*  21:    */   static Ref createSpecial(Context cx, Object object, String name)
/*  22:    */   {
/*  23: 62 */     Scriptable target = ScriptRuntime.toObjectOrNull(cx, object);
/*  24: 63 */     if (target == null) {
/*  25: 64 */       throw ScriptRuntime.undefReadError(object, name);
/*  26:    */     }
/*  27:    */     int type;
/*  28: 68 */     if (name.equals("__proto__"))
/*  29:    */     {
/*  30: 69 */       type = 1;
/*  31:    */     }
/*  32:    */     else
/*  33:    */     {
/*  34:    */       int type;
/*  35: 70 */       if (name.equals("__parent__")) {
/*  36: 71 */         type = 2;
/*  37:    */       } else {
/*  38: 73 */         throw new IllegalArgumentException(name);
/*  39:    */       }
/*  40:    */     }
/*  41:    */     int type;
/*  42: 76 */     if (!cx.hasFeature(5)) {
/*  43: 78 */       type = 0;
/*  44:    */     }
/*  45: 81 */     return new SpecialRef(target, type, name);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object get(Context cx)
/*  49:    */   {
/*  50: 87 */     switch (this.type)
/*  51:    */     {
/*  52:    */     case 0: 
/*  53: 89 */       return ScriptRuntime.getObjectProp(this.target, this.name, cx);
/*  54:    */     case 1: 
/*  55: 91 */       return this.target.getPrototype();
/*  56:    */     case 2: 
/*  57: 93 */       return this.target.getParentScope();
/*  58:    */     }
/*  59: 95 */     throw Kit.codeBug();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object set(Context cx, Object value)
/*  63:    */   {
/*  64:102 */     switch (this.type)
/*  65:    */     {
/*  66:    */     case 0: 
/*  67:104 */       return ScriptRuntime.setObjectProp(this.target, this.name, value, cx);
/*  68:    */     case 1: 
/*  69:    */     case 2: 
/*  70:108 */       Scriptable obj = ScriptRuntime.toObjectOrNull(cx, value);
/*  71:109 */       if (obj != null)
/*  72:    */       {
/*  73:112 */         Scriptable search = obj;
/*  74:    */         do
/*  75:    */         {
/*  76:114 */           if (search == this.target) {
/*  77:115 */             throw Context.reportRuntimeError1("msg.cyclic.value", this.name);
/*  78:    */           }
/*  79:118 */           if (this.type == 1) {
/*  80:119 */             search = search.getPrototype();
/*  81:    */           } else {
/*  82:121 */             search = search.getParentScope();
/*  83:    */           }
/*  84:123 */         } while (search != null);
/*  85:    */       }
/*  86:125 */       if (this.type == 1) {
/*  87:126 */         this.target.setPrototype(obj);
/*  88:    */       } else {
/*  89:128 */         this.target.setParentScope(obj);
/*  90:    */       }
/*  91:130 */       return obj;
/*  92:    */     }
/*  93:133 */     throw Kit.codeBug();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean has(Context cx)
/*  97:    */   {
/*  98:140 */     if (this.type == 0) {
/*  99:141 */       return ScriptRuntime.hasObjectElem(this.target, this.name, cx);
/* 100:    */     }
/* 101:143 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean delete(Context cx)
/* 105:    */   {
/* 106:149 */     if (this.type == 0) {
/* 107:150 */       return ScriptRuntime.deleteObjectElem(this.target, this.name, cx);
/* 108:    */     }
/* 109:152 */     return false;
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.SpecialRef
 * JD-Core Version:    0.7.0.1
 */