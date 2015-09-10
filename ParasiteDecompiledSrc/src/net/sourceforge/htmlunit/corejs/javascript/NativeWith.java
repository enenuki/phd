/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class NativeWith
/*   6:    */   implements Scriptable, IdFunctionCall, Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9:    */   
/*  10:    */   static void init(Scriptable scope, boolean sealed)
/*  11:    */   {
/*  12: 56 */     NativeWith obj = new NativeWith();
/*  13:    */     
/*  14: 58 */     obj.setParentScope(scope);
/*  15: 59 */     obj.setPrototype(ScriptableObject.getObjectPrototype(scope));
/*  16:    */     
/*  17: 61 */     IdFunctionObject ctor = new IdFunctionObject(obj, FTAG, 1, "With", 0, scope);
/*  18:    */     
/*  19: 63 */     ctor.markAsConstructor(obj);
/*  20: 64 */     if (sealed) {
/*  21: 65 */       ctor.sealObject();
/*  22:    */     }
/*  23: 67 */     ctor.exportAsScopeProperty();
/*  24:    */   }
/*  25:    */   
/*  26:    */   private NativeWith() {}
/*  27:    */   
/*  28:    */   protected NativeWith(Scriptable parent, Scriptable prototype)
/*  29:    */   {
/*  30: 74 */     this.parent = parent;
/*  31: 75 */     this.prototype = prototype;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getClassName()
/*  35:    */   {
/*  36: 79 */     return "With";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean has(String id, Scriptable start)
/*  40:    */   {
/*  41: 84 */     return this.prototype.has(id, this.prototype);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean has(int index, Scriptable start)
/*  45:    */   {
/*  46: 89 */     return this.prototype.has(index, this.prototype);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object get(String id, Scriptable start)
/*  50:    */   {
/*  51: 94 */     if (start == this) {
/*  52: 95 */       start = this.prototype;
/*  53:    */     }
/*  54: 96 */     return this.prototype.get(id, start);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object get(int index, Scriptable start)
/*  58:    */   {
/*  59:101 */     if (start == this) {
/*  60:102 */       start = this.prototype;
/*  61:    */     }
/*  62:103 */     return this.prototype.get(index, start);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void put(String id, Scriptable start, Object value)
/*  66:    */   {
/*  67:108 */     if (start == this) {
/*  68:109 */       start = this.prototype;
/*  69:    */     }
/*  70:110 */     this.prototype.put(id, start, value);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void put(int index, Scriptable start, Object value)
/*  74:    */   {
/*  75:115 */     if (start == this) {
/*  76:116 */       start = this.prototype;
/*  77:    */     }
/*  78:117 */     this.prototype.put(index, start, value);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void delete(String id)
/*  82:    */   {
/*  83:122 */     this.prototype.delete(id);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void delete(int index)
/*  87:    */   {
/*  88:127 */     this.prototype.delete(index);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Scriptable getPrototype()
/*  92:    */   {
/*  93:131 */     return this.prototype;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setPrototype(Scriptable prototype)
/*  97:    */   {
/*  98:135 */     this.prototype = prototype;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Scriptable getParentScope()
/* 102:    */   {
/* 103:139 */     return this.parent;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setParentScope(Scriptable parent)
/* 107:    */   {
/* 108:143 */     this.parent = parent;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Object[] getIds()
/* 112:    */   {
/* 113:147 */     return this.prototype.getIds();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Object getDefaultValue(Class<?> typeHint)
/* 117:    */   {
/* 118:151 */     return this.prototype.getDefaultValue(typeHint);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean hasInstance(Scriptable value)
/* 122:    */   {
/* 123:155 */     return this.prototype.hasInstance(value);
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected Object updateDotQuery(boolean value)
/* 127:    */   {
/* 128:164 */     throw new IllegalStateException();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 132:    */   {
/* 133:170 */     if ((f.hasTag(FTAG)) && 
/* 134:171 */       (f.methodId() == 1)) {
/* 135:172 */       throw Context.reportRuntimeError1("msg.cant.call.indirect", "With");
/* 136:    */     }
/* 137:175 */     throw f.unknown();
/* 138:    */   }
/* 139:    */   
/* 140:    */   static boolean isWithFunction(Object functionObj)
/* 141:    */   {
/* 142:180 */     if ((functionObj instanceof IdFunctionObject))
/* 143:    */     {
/* 144:181 */       IdFunctionObject f = (IdFunctionObject)functionObj;
/* 145:182 */       return (f.hasTag(FTAG)) && (f.methodId() == 1);
/* 146:    */     }
/* 147:184 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   static Object newWithSpecial(Context cx, Scriptable scope, Object[] args)
/* 151:    */   {
/* 152:189 */     ScriptRuntime.checkDeprecated(cx, "With");
/* 153:190 */     scope = ScriptableObject.getTopLevelScope(scope);
/* 154:191 */     NativeWith thisObj = new NativeWith();
/* 155:192 */     thisObj.setPrototype(args.length == 0 ? ScriptableObject.getObjectPrototype(scope) : ScriptRuntime.toObject(cx, scope, args[0]));
/* 156:    */     
/* 157:    */ 
/* 158:195 */     thisObj.setParentScope(scope);
/* 159:196 */     return thisObj;
/* 160:    */   }
/* 161:    */   
/* 162:199 */   private static final Object FTAG = "With";
/* 163:    */   private static final int Id_constructor = 1;
/* 164:    */   protected Scriptable prototype;
/* 165:    */   protected Scriptable parent;
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeWith
 * JD-Core Version:    0.7.0.1
 */