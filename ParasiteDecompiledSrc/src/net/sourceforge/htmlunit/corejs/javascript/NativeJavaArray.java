/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ 
/*   5:    */ public class NativeJavaArray
/*   6:    */   extends NativeJavaObject
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -924022554283675333L;
/*   9:    */   Object array;
/*  10:    */   int length;
/*  11:    */   Class<?> cls;
/*  12:    */   
/*  13:    */   public String getClassName()
/*  14:    */   {
/*  15: 62 */     return "JavaArray";
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static NativeJavaArray wrap(Scriptable scope, Object array)
/*  19:    */   {
/*  20: 66 */     return new NativeJavaArray(scope, array);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Object unwrap()
/*  24:    */   {
/*  25: 71 */     return this.array;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NativeJavaArray(Scriptable scope, Object array)
/*  29:    */   {
/*  30: 75 */     super(scope, null, ScriptRuntime.ObjectClass);
/*  31: 76 */     Class<?> cl = array.getClass();
/*  32: 77 */     if (!cl.isArray()) {
/*  33: 78 */       throw new RuntimeException("Array expected");
/*  34:    */     }
/*  35: 80 */     this.array = array;
/*  36: 81 */     this.length = Array.getLength(array);
/*  37: 82 */     this.cls = cl.getComponentType();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean has(String id, Scriptable start)
/*  41:    */   {
/*  42: 87 */     return (id.equals("length")) || (super.has(id, start));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean has(int index, Scriptable start)
/*  46:    */   {
/*  47: 92 */     return (0 <= index) && (index < this.length);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object get(String id, Scriptable start)
/*  51:    */   {
/*  52: 97 */     if (id.equals("length")) {
/*  53: 98 */       return Integer.valueOf(this.length);
/*  54:    */     }
/*  55: 99 */     Object result = super.get(id, start);
/*  56:100 */     if ((result == NOT_FOUND) && (!ScriptableObject.hasProperty(getPrototype(), id))) {
/*  57:103 */       throw Context.reportRuntimeError2("msg.java.member.not.found", this.array.getClass().getName(), id);
/*  58:    */     }
/*  59:106 */     return result;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object get(int index, Scriptable start)
/*  63:    */   {
/*  64:111 */     if ((0 <= index) && (index < this.length))
/*  65:    */     {
/*  66:112 */       Context cx = Context.getContext();
/*  67:113 */       Object obj = Array.get(this.array, index);
/*  68:114 */       return cx.getWrapFactory().wrap(cx, this, obj, this.cls);
/*  69:    */     }
/*  70:116 */     return Undefined.instance;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void put(String id, Scriptable start, Object value)
/*  74:    */   {
/*  75:122 */     if (!id.equals("length")) {
/*  76:123 */       throw Context.reportRuntimeError1("msg.java.array.member.not.found", id);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void put(int index, Scriptable start, Object value)
/*  81:    */   {
/*  82:129 */     if ((0 <= index) && (index < this.length)) {
/*  83:130 */       Array.set(this.array, index, Context.jsToJava(value, this.cls));
/*  84:    */     } else {
/*  85:133 */       throw Context.reportRuntimeError2("msg.java.array.index.out.of.bounds", String.valueOf(index), String.valueOf(this.length - 1));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object getDefaultValue(Class<?> hint)
/*  90:    */   {
/*  91:141 */     if ((hint == null) || (hint == ScriptRuntime.StringClass)) {
/*  92:142 */       return this.array.toString();
/*  93:    */     }
/*  94:143 */     if (hint == ScriptRuntime.BooleanClass) {
/*  95:144 */       return Boolean.TRUE;
/*  96:    */     }
/*  97:145 */     if (hint == ScriptRuntime.NumberClass) {
/*  98:146 */       return ScriptRuntime.NaNobj;
/*  99:    */     }
/* 100:147 */     return this;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object[] getIds()
/* 104:    */   {
/* 105:152 */     Object[] result = new Object[this.length];
/* 106:153 */     int i = this.length;
/* 107:    */     for (;;)
/* 108:    */     {
/* 109:154 */       i--;
/* 110:154 */       if (i < 0) {
/* 111:    */         break;
/* 112:    */       }
/* 113:155 */       result[i] = Integer.valueOf(i);
/* 114:    */     }
/* 115:156 */     return result;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean hasInstance(Scriptable value)
/* 119:    */   {
/* 120:161 */     if (!(value instanceof Wrapper)) {
/* 121:162 */       return false;
/* 122:    */     }
/* 123:163 */     Object instance = ((Wrapper)value).unwrap();
/* 124:164 */     return this.cls.isInstance(instance);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Scriptable getPrototype()
/* 128:    */   {
/* 129:169 */     if (this.prototype == null) {
/* 130:170 */       this.prototype = ScriptableObject.getArrayPrototype(getParentScope());
/* 131:    */     }
/* 132:173 */     return this.prototype;
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaArray
 * JD-Core Version:    0.7.0.1
 */