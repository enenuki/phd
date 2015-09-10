/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ 
/*   5:    */ public final class NativeIterator
/*   6:    */   extends IdScriptableObject
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = -4136968203581667681L;
/*   9: 43 */   private static final Object ITERATOR_TAG = "Iterator";
/*  10:    */   private static final String STOP_ITERATION = "StopIteration";
/*  11:    */   public static final String ITERATOR_PROPERTY_NAME = "__iterator__";
/*  12:    */   private static final int Id_constructor = 1;
/*  13:    */   private static final int Id_next = 2;
/*  14:    */   private static final int Id___iterator__ = 3;
/*  15:    */   private static final int MAX_PROTOTYPE_ID = 3;
/*  16:    */   private Object objectIterator;
/*  17:    */   
/*  18:    */   static void init(ScriptableObject scope, boolean sealed)
/*  19:    */   {
/*  20: 47 */     NativeIterator iterator = new NativeIterator();
/*  21: 48 */     iterator.exportAsJSClass(3, scope, sealed);
/*  22:    */     
/*  23:    */ 
/*  24: 51 */     NativeGenerator.init(scope, sealed);
/*  25:    */     
/*  26:    */ 
/*  27: 54 */     NativeObject obj = new StopIteration();
/*  28: 55 */     obj.setPrototype(getObjectPrototype(scope));
/*  29: 56 */     obj.setParentScope(scope);
/*  30: 57 */     if (sealed) {
/*  31: 57 */       obj.sealObject();
/*  32:    */     }
/*  33: 58 */     ScriptableObject.defineProperty(scope, "StopIteration", obj, 2);
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38: 63 */     scope.associateValue(ITERATOR_TAG, obj);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private NativeIterator() {}
/*  42:    */   
/*  43:    */   private NativeIterator(Object objectIterator)
/*  44:    */   {
/*  45: 73 */     this.objectIterator = objectIterator;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static Object getStopIterationObject(Scriptable scope)
/*  49:    */   {
/*  50: 85 */     Scriptable top = ScriptableObject.getTopLevelScope(scope);
/*  51: 86 */     return ScriptableObject.getTopScopeValue(top, ITERATOR_TAG);
/*  52:    */   }
/*  53:    */   
/*  54:    */   static class StopIteration
/*  55:    */     extends NativeObject
/*  56:    */   {
/*  57:    */     private static final long serialVersionUID = 2485151085722377663L;
/*  58:    */     
/*  59:    */     public String getClassName()
/*  60:    */     {
/*  61: 97 */       return "StopIteration";
/*  62:    */     }
/*  63:    */     
/*  64:    */     public boolean hasInstance(Scriptable instance)
/*  65:    */     {
/*  66:105 */       return instance instanceof StopIteration;
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getClassName()
/*  71:    */   {
/*  72:111 */     return "Iterator";
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void initPrototypeId(int id)
/*  76:    */   {
/*  77:    */     int arity;
/*  78:    */     String s;
/*  79:118 */     switch (id)
/*  80:    */     {
/*  81:    */     case 1: 
/*  82:119 */       arity = 2;s = "constructor"; break;
/*  83:    */     case 2: 
/*  84:120 */       arity = 0;s = "next"; break;
/*  85:    */     case 3: 
/*  86:121 */       arity = 1;s = "__iterator__"; break;
/*  87:    */     default: 
/*  88:122 */       throw new IllegalArgumentException(String.valueOf(id));
/*  89:    */     }
/*  90:124 */     initPrototypeMethod(ITERATOR_TAG, id, s, arity);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  94:    */   {
/*  95:131 */     if (!f.hasTag(ITERATOR_TAG)) {
/*  96:132 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  97:    */     }
/*  98:134 */     int id = f.methodId();
/*  99:136 */     if (id == 1) {
/* 100:137 */       return jsConstructor(cx, scope, thisObj, args);
/* 101:    */     }
/* 102:140 */     if (!(thisObj instanceof NativeIterator)) {
/* 103:141 */       throw incompatibleCallError(f);
/* 104:    */     }
/* 105:143 */     NativeIterator iterator = (NativeIterator)thisObj;
/* 106:145 */     switch (id)
/* 107:    */     {
/* 108:    */     case 2: 
/* 109:148 */       return iterator.next(cx, scope);
/* 110:    */     case 3: 
/* 111:152 */       return thisObj;
/* 112:    */     }
/* 113:155 */     throw new IllegalArgumentException(String.valueOf(id));
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static Object jsConstructor(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 117:    */   {
/* 118:163 */     if ((args.length == 0) || (args[0] == null) || (args[0] == Undefined.instance))
/* 119:    */     {
/* 120:166 */       Object argument = args.length == 0 ? Undefined.instance : args[0];
/* 121:167 */       throw ScriptRuntime.typeError1("msg.no.properties", ScriptRuntime.toString(argument));
/* 122:    */     }
/* 123:170 */     Scriptable obj = ScriptRuntime.toObject(scope, args[0]);
/* 124:171 */     boolean keyOnly = (args.length > 1) && (ScriptRuntime.toBoolean(args[1]));
/* 125:172 */     if (thisObj != null)
/* 126:    */     {
/* 127:178 */       Iterator<?> iterator = VMBridge.instance.getJavaIterator(cx, scope, obj);
/* 128:180 */       if (iterator != null)
/* 129:    */       {
/* 130:181 */         scope = ScriptableObject.getTopLevelScope(scope);
/* 131:182 */         return cx.getWrapFactory().wrap(cx, scope, new WrappedJavaIterator(iterator, scope), WrappedJavaIterator.class);
/* 132:    */       }
/* 133:188 */       Scriptable jsIterator = ScriptRuntime.toIterator(cx, scope, obj, keyOnly);
/* 134:190 */       if (jsIterator != null) {
/* 135:191 */         return jsIterator;
/* 136:    */       }
/* 137:    */     }
/* 138:197 */     Object objectIterator = ScriptRuntime.enumInit(obj, cx, keyOnly ? 3 : 5);
/* 139:    */     
/* 140:    */ 
/* 141:200 */     ScriptRuntime.setEnumNumbers(objectIterator, true);
/* 142:201 */     NativeIterator result = new NativeIterator(objectIterator);
/* 143:202 */     result.setPrototype(ScriptableObject.getClassPrototype(scope, result.getClassName()));
/* 144:    */     
/* 145:204 */     result.setParentScope(scope);
/* 146:205 */     return result;
/* 147:    */   }
/* 148:    */   
/* 149:    */   private Object next(Context cx, Scriptable scope)
/* 150:    */   {
/* 151:209 */     Boolean b = ScriptRuntime.enumNext(this.objectIterator);
/* 152:210 */     if (!b.booleanValue()) {
/* 153:212 */       throw new JavaScriptException(getStopIterationObject(scope), null, 0);
/* 154:    */     }
/* 155:215 */     return ScriptRuntime.enumId(this.objectIterator, cx);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static class WrappedJavaIterator
/* 159:    */   {
/* 160:    */     private Iterator<?> iterator;
/* 161:    */     private Scriptable scope;
/* 162:    */     
/* 163:    */     WrappedJavaIterator(Iterator<?> iterator, Scriptable scope)
/* 164:    */     {
/* 165:221 */       this.iterator = iterator;
/* 166:222 */       this.scope = scope;
/* 167:    */     }
/* 168:    */     
/* 169:    */     public Object next()
/* 170:    */     {
/* 171:226 */       if (!this.iterator.hasNext()) {
/* 172:228 */         throw new JavaScriptException(NativeIterator.getStopIterationObject(this.scope), null, 0);
/* 173:    */       }
/* 174:231 */       return this.iterator.next();
/* 175:    */     }
/* 176:    */     
/* 177:    */     public Object __iterator__(boolean b)
/* 178:    */     {
/* 179:235 */       return this;
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected int findPrototypeId(String s)
/* 184:    */   {
/* 185:248 */     int id = 0;String X = null;
/* 186:249 */     int s_length = s.length();
/* 187:250 */     if (s_length == 4)
/* 188:    */     {
/* 189:250 */       X = "next";id = 2;
/* 190:    */     }
/* 191:251 */     else if (s_length == 11)
/* 192:    */     {
/* 193:251 */       X = "constructor";id = 1;
/* 194:    */     }
/* 195:252 */     else if (s_length == 12)
/* 196:    */     {
/* 197:252 */       X = "__iterator__";id = 3;
/* 198:    */     }
/* 199:253 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 200:253 */       id = 0;
/* 201:    */     }
/* 202:257 */     return id;
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeIterator
 * JD-Core Version:    0.7.0.1
 */