/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.Constructor;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Member;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.lang.reflect.Modifier;
/*  12:    */ 
/*  13:    */ final class MemberBox
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = 6358550398665688245L;
/*  17:    */   private transient Member memberObject;
/*  18:    */   transient Class<?>[] argTypes;
/*  19:    */   transient Object delegateTo;
/*  20:    */   transient boolean vararg;
/*  21:    */   
/*  22:    */   MemberBox(Method method)
/*  23:    */   {
/*  24: 67 */     init(method);
/*  25:    */   }
/*  26:    */   
/*  27:    */   MemberBox(Constructor<?> constructor)
/*  28:    */   {
/*  29: 72 */     init(constructor);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void init(Method method)
/*  33:    */   {
/*  34: 77 */     this.memberObject = method;
/*  35: 78 */     this.argTypes = method.getParameterTypes();
/*  36: 79 */     this.vararg = VMBridge.instance.isVarArgs(method);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void init(Constructor<?> constructor)
/*  40:    */   {
/*  41: 84 */     this.memberObject = constructor;
/*  42: 85 */     this.argTypes = constructor.getParameterTypes();
/*  43: 86 */     this.vararg = VMBridge.instance.isVarArgs(constructor);
/*  44:    */   }
/*  45:    */   
/*  46:    */   Method method()
/*  47:    */   {
/*  48: 91 */     return (Method)this.memberObject;
/*  49:    */   }
/*  50:    */   
/*  51:    */   Constructor<?> ctor()
/*  52:    */   {
/*  53: 96 */     return (Constructor)this.memberObject;
/*  54:    */   }
/*  55:    */   
/*  56:    */   Member member()
/*  57:    */   {
/*  58:101 */     return this.memberObject;
/*  59:    */   }
/*  60:    */   
/*  61:    */   boolean isMethod()
/*  62:    */   {
/*  63:106 */     return this.memberObject instanceof Method;
/*  64:    */   }
/*  65:    */   
/*  66:    */   boolean isCtor()
/*  67:    */   {
/*  68:111 */     return this.memberObject instanceof Constructor;
/*  69:    */   }
/*  70:    */   
/*  71:    */   boolean isStatic()
/*  72:    */   {
/*  73:116 */     return Modifier.isStatic(this.memberObject.getModifiers());
/*  74:    */   }
/*  75:    */   
/*  76:    */   String getName()
/*  77:    */   {
/*  78:121 */     return this.memberObject.getName();
/*  79:    */   }
/*  80:    */   
/*  81:    */   Class<?> getDeclaringClass()
/*  82:    */   {
/*  83:126 */     return this.memberObject.getDeclaringClass();
/*  84:    */   }
/*  85:    */   
/*  86:    */   String toJavaDeclaration()
/*  87:    */   {
/*  88:131 */     StringBuffer sb = new StringBuffer();
/*  89:132 */     if (isMethod())
/*  90:    */     {
/*  91:133 */       Method method = method();
/*  92:134 */       sb.append(method.getReturnType());
/*  93:135 */       sb.append(' ');
/*  94:136 */       sb.append(method.getName());
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:138 */       Constructor<?> ctor = ctor();
/*  99:139 */       String name = ctor.getDeclaringClass().getName();
/* 100:140 */       int lastDot = name.lastIndexOf('.');
/* 101:141 */       if (lastDot >= 0) {
/* 102:142 */         name = name.substring(lastDot + 1);
/* 103:    */       }
/* 104:144 */       sb.append(name);
/* 105:    */     }
/* 106:146 */     sb.append(JavaMembers.liveConnectSignature(this.argTypes));
/* 107:147 */     return sb.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String toString()
/* 111:    */   {
/* 112:153 */     return this.memberObject.toString();
/* 113:    */   }
/* 114:    */   
/* 115:    */   Object invoke(Object target, Object[] args)
/* 116:    */   {
/* 117:158 */     Method method = method();
/* 118:161 */     if ((target instanceof Delegator)) {
/* 119:162 */       target = ((Delegator)target).getDelegee();
/* 120:    */     }
/* 121:164 */     for (int i = 0; i < args.length; i++) {
/* 122:165 */       if ((args[i] instanceof Delegator)) {
/* 123:166 */         args[i] = ((Delegator)args[i]).getDelegee();
/* 124:    */       }
/* 125:    */     }
/* 126:    */     try
/* 127:    */     {
/* 128:172 */       return method.invoke(target, args);
/* 129:    */     }
/* 130:    */     catch (IllegalAccessException ex)
/* 131:    */     {
/* 132:174 */       Method accessible = searchAccessibleMethod(method, this.argTypes);
/* 133:175 */       if (accessible != null)
/* 134:    */       {
/* 135:176 */         this.memberObject = accessible;
/* 136:177 */         method = accessible;
/* 137:    */       }
/* 138:179 */       else if (!VMBridge.instance.tryToMakeAccessible(method))
/* 139:    */       {
/* 140:180 */         throw Context.throwAsScriptRuntimeEx(ex);
/* 141:    */       }
/* 142:184 */       return method.invoke(target, args);
/* 143:    */     }
/* 144:    */     catch (InvocationTargetException ite)
/* 145:    */     {
/* 146:188 */       Throwable e = ite;
/* 147:    */       do
/* 148:    */       {
/* 149:190 */         e = ((InvocationTargetException)e).getTargetException();
/* 150:191 */       } while ((e instanceof InvocationTargetException));
/* 151:192 */       if ((e instanceof ContinuationPending)) {
/* 152:193 */         throw ((ContinuationPending)e);
/* 153:    */       }
/* 154:195 */       if (((e instanceof RhinoException)) || (Context.getCurrentContext().hasFeature(15))) {
/* 155:196 */         throw Context.throwAsScriptRuntimeEx(e);
/* 156:    */       }
/* 157:198 */       throw new RuntimeException("Exception invoking " + method.getName(), e);
/* 158:    */     }
/* 159:    */     catch (Exception ex)
/* 160:    */     {
/* 161:200 */       throw Context.throwAsScriptRuntimeEx(ex);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   Object newInstance(Object[] args)
/* 166:    */   {
/* 167:206 */     Constructor<?> ctor = ctor();
/* 168:    */     try
/* 169:    */     {
/* 170:209 */       return ctor.newInstance(args);
/* 171:    */     }
/* 172:    */     catch (IllegalAccessException ex)
/* 173:    */     {
/* 174:211 */       if (!VMBridge.instance.tryToMakeAccessible(ctor)) {
/* 175:212 */         throw Context.throwAsScriptRuntimeEx(ex);
/* 176:    */       }
/* 177:215 */       return ctor.newInstance(args);
/* 178:    */     }
/* 179:    */     catch (Exception ex)
/* 180:    */     {
/* 181:217 */       throw Context.throwAsScriptRuntimeEx(ex);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   private static Method searchAccessibleMethod(Method method, Class<?>[] params)
/* 186:    */   {
/* 187:223 */     int modifiers = method.getModifiers();
/* 188:224 */     if ((Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers)))
/* 189:    */     {
/* 190:225 */       Class<?> c = method.getDeclaringClass();
/* 191:226 */       if (!Modifier.isPublic(c.getModifiers()))
/* 192:    */       {
/* 193:227 */         String name = method.getName();
/* 194:228 */         Class<?>[] intfs = c.getInterfaces();
/* 195:229 */         int i = 0;
/* 196:229 */         for (int N = intfs.length; i != N; i++)
/* 197:    */         {
/* 198:230 */           Class<?> intf = intfs[i];
/* 199:231 */           if (Modifier.isPublic(intf.getModifiers())) {
/* 200:    */             try
/* 201:    */             {
/* 202:233 */               return intf.getMethod(name, params);
/* 203:    */             }
/* 204:    */             catch (NoSuchMethodException ex) {}catch (SecurityException ex) {}
/* 205:    */           }
/* 206:    */         }
/* 207:    */         for (;;)
/* 208:    */         {
/* 209:239 */           c = c.getSuperclass();
/* 210:240 */           if (c == null) {
/* 211:    */             break;
/* 212:    */           }
/* 213:241 */           if (Modifier.isPublic(c.getModifiers())) {
/* 214:    */             try
/* 215:    */             {
/* 216:243 */               Method m = c.getMethod(name, params);
/* 217:244 */               int mModifiers = m.getModifiers();
/* 218:245 */               if ((Modifier.isPublic(mModifiers)) && (!Modifier.isStatic(mModifiers))) {
/* 219:248 */                 return m;
/* 220:    */               }
/* 221:    */             }
/* 222:    */             catch (NoSuchMethodException ex) {}catch (SecurityException ex) {}
/* 223:    */           }
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:256 */     return null;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void readObject(ObjectInputStream in)
/* 231:    */     throws IOException, ClassNotFoundException
/* 232:    */   {
/* 233:262 */     in.defaultReadObject();
/* 234:263 */     Member member = readMember(in);
/* 235:264 */     if ((member instanceof Method)) {
/* 236:265 */       init((Method)member);
/* 237:    */     } else {
/* 238:267 */       init((Constructor)member);
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   private void writeObject(ObjectOutputStream out)
/* 243:    */     throws IOException
/* 244:    */   {
/* 245:274 */     out.defaultWriteObject();
/* 246:275 */     writeMember(out, this.memberObject);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private static void writeMember(ObjectOutputStream out, Member member)
/* 250:    */     throws IOException
/* 251:    */   {
/* 252:288 */     if (member == null)
/* 253:    */     {
/* 254:289 */       out.writeBoolean(false);
/* 255:290 */       return;
/* 256:    */     }
/* 257:292 */     out.writeBoolean(true);
/* 258:293 */     if ((!(member instanceof Method)) && (!(member instanceof Constructor))) {
/* 259:294 */       throw new IllegalArgumentException("not Method or Constructor");
/* 260:    */     }
/* 261:295 */     out.writeBoolean(member instanceof Method);
/* 262:296 */     out.writeObject(member.getName());
/* 263:297 */     out.writeObject(member.getDeclaringClass());
/* 264:298 */     if ((member instanceof Method)) {
/* 265:299 */       writeParameters(out, ((Method)member).getParameterTypes());
/* 266:    */     } else {
/* 267:301 */       writeParameters(out, ((Constructor)member).getParameterTypes());
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   private static Member readMember(ObjectInputStream in)
/* 272:    */     throws IOException, ClassNotFoundException
/* 273:    */   {
/* 274:311 */     if (!in.readBoolean()) {
/* 275:312 */       return null;
/* 276:    */     }
/* 277:313 */     boolean isMethod = in.readBoolean();
/* 278:314 */     String name = (String)in.readObject();
/* 279:315 */     Class<?> declaring = (Class)in.readObject();
/* 280:316 */     Class<?>[] parms = readParameters(in);
/* 281:    */     try
/* 282:    */     {
/* 283:318 */       if (isMethod) {
/* 284:319 */         return declaring.getMethod(name, parms);
/* 285:    */       }
/* 286:321 */       return declaring.getConstructor(parms);
/* 287:    */     }
/* 288:    */     catch (NoSuchMethodException e)
/* 289:    */     {
/* 290:324 */       throw new IOException("Cannot find member: " + e);
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:328 */   private static final Class<?>[] primitives = { Boolean.TYPE, Byte.TYPE, Character.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE, Void.TYPE };
/* 295:    */   
/* 296:    */   private static void writeParameters(ObjectOutputStream out, Class<?>[] parms)
/* 297:    */     throws IOException
/* 298:    */   {
/* 299:349 */     out.writeShort(parms.length);
/* 300:    */     label115:
/* 301:351 */     for (int i = 0; i < parms.length; i++)
/* 302:    */     {
/* 303:352 */       Class<?> parm = parms[i];
/* 304:353 */       boolean primitive = parm.isPrimitive();
/* 305:354 */       out.writeBoolean(primitive);
/* 306:355 */       if (!primitive)
/* 307:    */       {
/* 308:356 */         out.writeObject(parm);
/* 309:    */       }
/* 310:    */       else
/* 311:    */       {
/* 312:359 */         for (int j = 0; j < primitives.length; j++) {
/* 313:360 */           if (parm.equals(primitives[j]))
/* 314:    */           {
/* 315:361 */             out.writeByte(j);
/* 316:    */             break label115;
/* 317:    */           }
/* 318:    */         }
/* 319:365 */         throw new IllegalArgumentException("Primitive " + parm + " not found");
/* 320:    */       }
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private static Class<?>[] readParameters(ObjectInputStream in)
/* 325:    */     throws IOException, ClassNotFoundException
/* 326:    */   {
/* 327:376 */     Class<?>[] result = new Class[in.readShort()];
/* 328:377 */     for (int i = 0; i < result.length; i++) {
/* 329:378 */       if (!in.readBoolean()) {
/* 330:379 */         result[i] = ((Class)in.readObject());
/* 331:    */       } else {
/* 332:382 */         result[i] = primitives[in.readByte()];
/* 333:    */       }
/* 334:    */     }
/* 335:384 */     return result;
/* 336:    */   }
/* 337:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.MemberBox
 * JD-Core Version:    0.7.0.1
 */