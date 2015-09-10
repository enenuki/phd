/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.Modifier;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class NativeJavaClass
/*   8:    */   extends NativeJavaObject
/*   9:    */   implements Function
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = -6460763940409461664L;
/*  12:    */   static final String javaClassPropertyName = "__javaObject__";
/*  13:    */   private Map<String, FieldAndMethods> staticFieldAndMethods;
/*  14:    */   
/*  15:    */   public NativeJavaClass() {}
/*  16:    */   
/*  17:    */   public NativeJavaClass(Scriptable scope, Class<?> cl)
/*  18:    */   {
/*  19: 76 */     this.parent = scope;
/*  20: 77 */     this.javaObject = cl;
/*  21: 78 */     initMembers();
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void initMembers()
/*  25:    */   {
/*  26: 83 */     Class<?> cl = (Class)this.javaObject;
/*  27: 84 */     this.members = JavaMembers.lookupClass(this.parent, cl, cl, false);
/*  28: 85 */     this.staticFieldAndMethods = this.members.getFieldAndMethodsObjects(this, cl, true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getClassName()
/*  32:    */   {
/*  33: 91 */     return "JavaClass";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean has(String name, Scriptable start)
/*  37:    */   {
/*  38: 96 */     return (this.members.has(name, true)) || ("__javaObject__".equals(name));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object get(String name, Scriptable start)
/*  42:    */   {
/*  43:105 */     if (name.equals("prototype")) {
/*  44:106 */       return null;
/*  45:    */     }
/*  46:108 */     if (this.staticFieldAndMethods != null)
/*  47:    */     {
/*  48:109 */       Object result = this.staticFieldAndMethods.get(name);
/*  49:110 */       if (result != null) {
/*  50:111 */         return result;
/*  51:    */       }
/*  52:    */     }
/*  53:114 */     if (this.members.has(name, true)) {
/*  54:115 */       return this.members.get(this, name, this.javaObject, true);
/*  55:    */     }
/*  56:118 */     Context cx = Context.getContext();
/*  57:119 */     Scriptable scope = ScriptableObject.getTopLevelScope(start);
/*  58:120 */     WrapFactory wrapFactory = cx.getWrapFactory();
/*  59:122 */     if ("__javaObject__".equals(name)) {
/*  60:123 */       return wrapFactory.wrap(cx, scope, this.javaObject, ScriptRuntime.ClassClass);
/*  61:    */     }
/*  62:129 */     Class<?> nestedClass = findNestedClass(getClassObject(), name);
/*  63:130 */     if (nestedClass != null)
/*  64:    */     {
/*  65:131 */       Scriptable nestedValue = wrapFactory.wrapJavaClass(cx, scope, nestedClass);
/*  66:    */       
/*  67:133 */       nestedValue.setParentScope(this);
/*  68:134 */       return nestedValue;
/*  69:    */     }
/*  70:137 */     throw this.members.reportMemberNotFound(name);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void put(String name, Scriptable start, Object value)
/*  74:    */   {
/*  75:142 */     this.members.put(this, name, this.javaObject, value, true);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object[] getIds()
/*  79:    */   {
/*  80:147 */     return this.members.getIds(true);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Class<?> getClassObject()
/*  84:    */   {
/*  85:151 */     return (Class)super.unwrap();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object getDefaultValue(Class<?> hint)
/*  89:    */   {
/*  90:156 */     if ((hint == null) || (hint == ScriptRuntime.StringClass)) {
/*  91:157 */       return toString();
/*  92:    */     }
/*  93:158 */     if (hint == ScriptRuntime.BooleanClass) {
/*  94:159 */       return Boolean.TRUE;
/*  95:    */     }
/*  96:160 */     if (hint == ScriptRuntime.NumberClass) {
/*  97:161 */       return ScriptRuntime.NaNobj;
/*  98:    */     }
/*  99:162 */     return this;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 103:    */   {
/* 104:171 */     if ((args.length == 1) && ((args[0] instanceof Scriptable)))
/* 105:    */     {
/* 106:172 */       Class<?> c = getClassObject();
/* 107:173 */       Scriptable p = (Scriptable)args[0];
/* 108:    */       do
/* 109:    */       {
/* 110:175 */         if ((p instanceof Wrapper))
/* 111:    */         {
/* 112:176 */           Object o = ((Wrapper)p).unwrap();
/* 113:177 */           if (c.isInstance(o)) {
/* 114:178 */             return p;
/* 115:    */           }
/* 116:    */         }
/* 117:180 */         p = p.getPrototype();
/* 118:181 */       } while (p != null);
/* 119:    */     }
/* 120:183 */     return construct(cx, scope, args);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/* 124:    */   {
/* 125:188 */     Class<?> classObject = getClassObject();
/* 126:189 */     int modifiers = classObject.getModifiers();
/* 127:190 */     if ((!Modifier.isInterface(modifiers)) && (!Modifier.isAbstract(modifiers)))
/* 128:    */     {
/* 129:193 */       MemberBox[] ctors = this.members.ctors;
/* 130:194 */       int index = NativeJavaMethod.findFunction(cx, ctors, args);
/* 131:195 */       if (index < 0)
/* 132:    */       {
/* 133:196 */         String sig = NativeJavaMethod.scriptSignature(args);
/* 134:197 */         throw Context.reportRuntimeError2("msg.no.java.ctor", classObject.getName(), sig);
/* 135:    */       }
/* 136:202 */       return constructSpecific(cx, scope, args, ctors[index]);
/* 137:    */     }
/* 138:204 */     Scriptable topLevel = ScriptableObject.getTopLevelScope(this);
/* 139:205 */     String msg = "";
/* 140:    */     try
/* 141:    */     {
/* 142:210 */       Object v = topLevel.get("JavaAdapter", topLevel);
/* 143:211 */       if (v != NOT_FOUND)
/* 144:    */       {
/* 145:212 */         Function f = (Function)v;
/* 146:    */         
/* 147:214 */         Object[] adapterArgs = { this, args[0] };
/* 148:215 */         return f.construct(cx, topLevel, adapterArgs);
/* 149:    */       }
/* 150:    */     }
/* 151:    */     catch (Exception ex)
/* 152:    */     {
/* 153:219 */       String m = ex.getMessage();
/* 154:220 */       if (m != null) {
/* 155:221 */         msg = m;
/* 156:    */       }
/* 157:    */     }
/* 158:223 */     throw Context.reportRuntimeError2("msg.cant.instantiate", msg, classObject.getName());
/* 159:    */   }
/* 160:    */   
/* 161:    */   static Scriptable constructSpecific(Context cx, Scriptable scope, Object[] args, MemberBox ctor)
/* 162:    */   {
/* 163:231 */     Scriptable topLevel = ScriptableObject.getTopLevelScope(scope);
/* 164:232 */     Class<?>[] argTypes = ctor.argTypes;
/* 165:234 */     if (ctor.vararg)
/* 166:    */     {
/* 167:236 */       Object[] newArgs = new Object[argTypes.length];
/* 168:237 */       for (int i = 0; i < argTypes.length - 1; i++) {
/* 169:238 */         newArgs[i] = Context.jsToJava(args[i], argTypes[i]);
/* 170:    */       }
/* 171:    */       Object varArgs;
/* 172:    */       Object varArgs;
/* 173:245 */       if ((args.length == argTypes.length) && ((args[(args.length - 1)] == null) || ((args[(args.length - 1)] instanceof NativeArray)) || ((args[(args.length - 1)] instanceof NativeJavaArray))))
/* 174:    */       {
/* 175:251 */         varArgs = Context.jsToJava(args[(args.length - 1)], argTypes[(argTypes.length - 1)]);
/* 176:    */       }
/* 177:    */       else
/* 178:    */       {
/* 179:255 */         Class<?> componentType = argTypes[(argTypes.length - 1)].getComponentType();
/* 180:    */         
/* 181:257 */         varArgs = Array.newInstance(componentType, args.length - argTypes.length + 1);
/* 182:259 */         for (int i = 0; i < Array.getLength(varArgs); i++)
/* 183:    */         {
/* 184:260 */           Object value = Context.jsToJava(args[(argTypes.length - 1 + i)], componentType);
/* 185:    */           
/* 186:262 */           Array.set(varArgs, i, value);
/* 187:    */         }
/* 188:    */       }
/* 189:267 */       newArgs[(argTypes.length - 1)] = varArgs;
/* 190:    */       
/* 191:269 */       args = newArgs;
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:271 */       Object[] origArgs = args;
/* 196:272 */       for (int i = 0; i < args.length; i++)
/* 197:    */       {
/* 198:273 */         Object arg = args[i];
/* 199:274 */         Object x = Context.jsToJava(arg, argTypes[i]);
/* 200:275 */         if (x != arg)
/* 201:    */         {
/* 202:276 */           if (args == origArgs) {
/* 203:277 */             args = (Object[])origArgs.clone();
/* 204:    */           }
/* 205:279 */           args[i] = x;
/* 206:    */         }
/* 207:    */       }
/* 208:    */     }
/* 209:284 */     Object instance = ctor.newInstance(args);
/* 210:    */     
/* 211:    */ 
/* 212:287 */     return cx.getWrapFactory().wrapNewObject(cx, topLevel, instance);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String toString()
/* 216:    */   {
/* 217:292 */     return "[JavaClass " + getClassObject().getName() + "]";
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean hasInstance(Scriptable value)
/* 221:    */   {
/* 222:306 */     if (((value instanceof Wrapper)) && (!(value instanceof NativeJavaClass)))
/* 223:    */     {
/* 224:308 */       Object instance = ((Wrapper)value).unwrap();
/* 225:    */       
/* 226:310 */       return getClassObject().isInstance(instance);
/* 227:    */     }
/* 228:314 */     return false;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private static Class<?> findNestedClass(Class<?> parentClass, String name)
/* 232:    */   {
/* 233:318 */     String nestedClassName = parentClass.getName() + '$' + name;
/* 234:319 */     ClassLoader loader = parentClass.getClassLoader();
/* 235:320 */     if (loader == null) {
/* 236:325 */       return Kit.classOrNull(nestedClassName);
/* 237:    */     }
/* 238:327 */     return Kit.classOrNull(loader, nestedClassName);
/* 239:    */   }
/* 240:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaClass
 * JD-Core Version:    0.7.0.1
 */