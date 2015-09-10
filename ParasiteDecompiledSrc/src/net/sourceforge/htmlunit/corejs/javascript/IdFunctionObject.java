/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class IdFunctionObject
/*   4:    */   extends BaseFunction
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -5332312783643935019L;
/*   7:    */   private final IdFunctionCall idcall;
/*   8:    */   private final Object tag;
/*   9:    */   private final int methodId;
/*  10:    */   private int arity;
/*  11:    */   private boolean useCallAsConstructor;
/*  12:    */   private String functionName;
/*  13:    */   
/*  14:    */   public IdFunctionObject(IdFunctionCall idcall, Object tag, int id, int arity)
/*  15:    */   {
/*  16: 50 */     if (arity < 0) {
/*  17: 51 */       throw new IllegalArgumentException();
/*  18:    */     }
/*  19: 53 */     this.idcall = idcall;
/*  20: 54 */     this.tag = tag;
/*  21: 55 */     this.methodId = id;
/*  22: 56 */     this.arity = arity;
/*  23: 57 */     if (arity < 0) {
/*  24: 57 */       throw new IllegalArgumentException();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IdFunctionObject(IdFunctionCall idcall, Object tag, int id, String name, int arity, Scriptable scope)
/*  29:    */   {
/*  30: 63 */     super(scope, null);
/*  31: 65 */     if (arity < 0) {
/*  32: 66 */       throw new IllegalArgumentException();
/*  33:    */     }
/*  34: 67 */     if (name == null) {
/*  35: 68 */       throw new IllegalArgumentException();
/*  36:    */     }
/*  37: 70 */     this.idcall = idcall;
/*  38: 71 */     this.tag = tag;
/*  39: 72 */     this.methodId = id;
/*  40: 73 */     this.arity = arity;
/*  41: 74 */     this.functionName = name;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void initFunction(String name, Scriptable scope)
/*  45:    */   {
/*  46: 79 */     if (name == null) {
/*  47: 79 */       throw new IllegalArgumentException();
/*  48:    */     }
/*  49: 80 */     if (scope == null) {
/*  50: 80 */       throw new IllegalArgumentException();
/*  51:    */     }
/*  52: 81 */     this.functionName = name;
/*  53: 82 */     setParentScope(scope);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final boolean hasTag(Object tag)
/*  57:    */   {
/*  58: 87 */     return this.tag == tag;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final int methodId()
/*  62:    */   {
/*  63: 92 */     return this.methodId;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final void markAsConstructor(Scriptable prototypeProperty)
/*  67:    */   {
/*  68: 97 */     this.useCallAsConstructor = true;
/*  69: 98 */     setImmunePrototypeProperty(prototypeProperty);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final void addAsProperty(Scriptable target)
/*  73:    */   {
/*  74:103 */     ScriptableObject.defineProperty(target, this.functionName, this, 2);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void exportAsScopeProperty()
/*  78:    */   {
/*  79:109 */     addAsProperty(getParentScope());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Scriptable getPrototype()
/*  83:    */   {
/*  84:117 */     Scriptable proto = super.getPrototype();
/*  85:118 */     if (proto == null)
/*  86:    */     {
/*  87:119 */       proto = getFunctionPrototype(getParentScope());
/*  88:120 */       setPrototype(proto);
/*  89:    */     }
/*  90:122 */     return proto;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  94:    */   {
/*  95:129 */     return this.idcall.execIdCall(this, cx, scope, thisObj, args);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Scriptable createObject(Context cx, Scriptable scope)
/*  99:    */   {
/* 100:135 */     if (this.useCallAsConstructor) {
/* 101:136 */       return null;
/* 102:    */     }
/* 103:142 */     throw ScriptRuntime.typeError1("msg.not.ctor", this.functionName);
/* 104:    */   }
/* 105:    */   
/* 106:    */   String decompile(int indent, int flags)
/* 107:    */   {
/* 108:148 */     StringBuffer sb = new StringBuffer();
/* 109:149 */     boolean justbody = 0 != (flags & 0x1);
/* 110:150 */     if (!justbody)
/* 111:    */     {
/* 112:151 */       sb.append("function ");
/* 113:152 */       sb.append(getFunctionName());
/* 114:153 */       sb.append("() { ");
/* 115:    */     }
/* 116:155 */     sb.append("[native code for ");
/* 117:156 */     if ((this.idcall instanceof Scriptable))
/* 118:    */     {
/* 119:157 */       Scriptable sobj = (Scriptable)this.idcall;
/* 120:158 */       sb.append(sobj.getClassName());
/* 121:159 */       sb.append('.');
/* 122:    */     }
/* 123:161 */     sb.append(getFunctionName());
/* 124:162 */     sb.append(", arity=");
/* 125:163 */     sb.append(getArity());
/* 126:164 */     sb.append(justbody ? "]\n" : "] }\n");
/* 127:165 */     return sb.toString();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getArity()
/* 131:    */   {
/* 132:171 */     return this.arity;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getLength()
/* 136:    */   {
/* 137:175 */     return getArity();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getFunctionName()
/* 141:    */   {
/* 142:180 */     return this.functionName == null ? "" : this.functionName;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final RuntimeException unknown()
/* 146:    */   {
/* 147:186 */     return new IllegalArgumentException("BAD FUNCTION ID=" + this.methodId + " MASTER=" + this.idcall);
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.IdFunctionObject
 * JD-Core Version:    0.7.0.1
 */