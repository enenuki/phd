/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ final class Arguments
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = 4275508002492040609L;
/*   7:    */   private static final String FTAG = "Arguments";
/*   8:    */   private static final int Id_callee = 1;
/*   9:    */   private static final int Id_length = 2;
/*  10:    */   private static final int Id_caller = 3;
/*  11:    */   private static final int Id_constructor = 4;
/*  12:    */   private static final int MAX_INSTANCE_ID = 4;
/*  13:    */   private Object callerObj;
/*  14:    */   private Object calleeObj;
/*  15:    */   private Object lengthObj;
/*  16:    */   private Object constructor;
/*  17:    */   private NativeCall activation;
/*  18:    */   private BaseFunction objectCtor;
/*  19:    */   private Object[] args;
/*  20:    */   
/*  21:    */   public Arguments(NativeCall activation)
/*  22:    */   {
/*  23: 58 */     this.activation = activation;
/*  24:    */     
/*  25: 60 */     Scriptable parent = activation.getParentScope();
/*  26: 61 */     setParentScope(parent);
/*  27: 62 */     setPrototype(ScriptableObject.getObjectPrototype(parent));
/*  28:    */     
/*  29: 64 */     this.args = activation.originalArgs;
/*  30: 65 */     this.lengthObj = Integer.valueOf(this.args.length);
/*  31:    */     
/*  32: 67 */     NativeFunction f = activation.function;
/*  33: 68 */     this.calleeObj = f;
/*  34:    */     
/*  35: 70 */     Scriptable topLevel = getTopLevelScope(parent);
/*  36: 71 */     this.objectCtor = ((BaseFunction)getProperty(topLevel, "Object"));
/*  37:    */     
/*  38: 73 */     this.constructor = this.objectCtor;
/*  39:    */     
/*  40: 75 */     int version = f.getLanguageVersion();
/*  41: 76 */     if ((version <= 130) && (version != 0)) {
/*  42: 79 */       this.callerObj = null;
/*  43:    */     } else {
/*  44: 81 */       this.callerObj = NOT_FOUND;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getClassName()
/*  49:    */   {
/*  50: 88 */     return "Object";
/*  51:    */   }
/*  52:    */   
/*  53:    */   private Object arg(int index)
/*  54:    */   {
/*  55: 92 */     if ((index < 0) || (this.args.length <= index)) {
/*  56: 92 */       return NOT_FOUND;
/*  57:    */     }
/*  58: 93 */     return this.args[index];
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void putIntoActivation(int index, Object value)
/*  62:    */   {
/*  63: 99 */     String argName = this.activation.function.getParamOrVarName(index);
/*  64:100 */     this.activation.put(argName, this.activation, value);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private Object getFromActivation(int index)
/*  68:    */   {
/*  69:104 */     String argName = this.activation.function.getParamOrVarName(index);
/*  70:105 */     return this.activation.get(argName, this.activation);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void replaceArg(int index, Object value)
/*  74:    */   {
/*  75:109 */     if (sharedWithActivation(index)) {
/*  76:110 */       putIntoActivation(index, value);
/*  77:    */     }
/*  78:112 */     synchronized (this)
/*  79:    */     {
/*  80:113 */       if (this.args == this.activation.originalArgs) {
/*  81:114 */         this.args = ((Object[])this.args.clone());
/*  82:    */       }
/*  83:116 */       this.args[index] = value;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void removeArg(int index)
/*  88:    */   {
/*  89:121 */     synchronized (this)
/*  90:    */     {
/*  91:122 */       if (this.args[index] != NOT_FOUND)
/*  92:    */       {
/*  93:123 */         if (this.args == this.activation.originalArgs) {
/*  94:124 */           this.args = ((Object[])this.args.clone());
/*  95:    */         }
/*  96:126 */         this.args[index] = NOT_FOUND;
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean has(int index, Scriptable start)
/* 102:    */   {
/* 103:136 */     if (arg(index) != NOT_FOUND) {
/* 104:137 */       return true;
/* 105:    */     }
/* 106:139 */     return super.has(index, start);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Object get(int index, Scriptable start)
/* 110:    */   {
/* 111:145 */     Object value = arg(index);
/* 112:146 */     if (value == NOT_FOUND) {
/* 113:147 */       return super.get(index, start);
/* 114:    */     }
/* 115:149 */     if (sharedWithActivation(index)) {
/* 116:150 */       return getFromActivation(index);
/* 117:    */     }
/* 118:152 */     return value;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private boolean sharedWithActivation(int index)
/* 122:    */   {
/* 123:159 */     NativeFunction f = this.activation.function;
/* 124:160 */     int definedCount = f.getParamCount();
/* 125:161 */     if (index < definedCount)
/* 126:    */     {
/* 127:164 */       if (index < definedCount - 1)
/* 128:    */       {
/* 129:165 */         String argName = f.getParamOrVarName(index);
/* 130:166 */         for (int i = index + 1; i < definedCount; i++) {
/* 131:167 */           if (argName.equals(f.getParamOrVarName(i))) {
/* 132:168 */             return false;
/* 133:    */           }
/* 134:    */         }
/* 135:    */       }
/* 136:172 */       return true;
/* 137:    */     }
/* 138:174 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void put(int index, Scriptable start, Object value)
/* 142:    */   {
/* 143:180 */     if (arg(index) == NOT_FOUND) {
/* 144:181 */       super.put(index, start, value);
/* 145:    */     } else {
/* 146:183 */       replaceArg(index, value);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void delete(int index)
/* 151:    */   {
/* 152:190 */     if ((0 <= index) && (index < this.args.length)) {
/* 153:191 */       removeArg(index);
/* 154:    */     }
/* 155:193 */     super.delete(index);
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected int getMaxInstanceId()
/* 159:    */   {
/* 160:209 */     return 4;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected int findInstanceIdInfo(String s)
/* 164:    */   {
/* 165:217 */     int id = 0;String X = null;
/* 166:218 */     int s_length = s.length();
/* 167:219 */     if (s_length == 6)
/* 168:    */     {
/* 169:220 */       int c = s.charAt(5);
/* 170:221 */       if (c == 101)
/* 171:    */       {
/* 172:221 */         X = "callee";id = 1;
/* 173:    */       }
/* 174:222 */       else if (c == 104)
/* 175:    */       {
/* 176:222 */         X = "length";id = 2;
/* 177:    */       }
/* 178:223 */       else if (c == 114)
/* 179:    */       {
/* 180:223 */         X = "caller";id = 3;
/* 181:    */       }
/* 182:    */     }
/* 183:225 */     else if (s_length == 11)
/* 184:    */     {
/* 185:225 */       X = "constructor";id = 4;
/* 186:    */     }
/* 187:226 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 188:226 */       id = 0;
/* 189:    */     }
/* 190:231 */     if (id == 0) {
/* 191:231 */       return super.findInstanceIdInfo(s);
/* 192:    */     }
/* 193:    */     int attr;
/* 194:234 */     switch (id)
/* 195:    */     {
/* 196:    */     case 1: 
/* 197:    */     case 2: 
/* 198:    */     case 3: 
/* 199:    */     case 4: 
/* 200:239 */       attr = 2;
/* 201:240 */       break;
/* 202:    */     default: 
/* 203:241 */       throw new IllegalStateException();
/* 204:    */     }
/* 205:243 */     return instanceIdInfo(attr, id);
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected String getInstanceIdName(int id)
/* 209:    */   {
/* 210:251 */     switch (id)
/* 211:    */     {
/* 212:    */     case 1: 
/* 213:252 */       return "callee";
/* 214:    */     case 2: 
/* 215:253 */       return "length";
/* 216:    */     case 3: 
/* 217:254 */       return "caller";
/* 218:    */     case 4: 
/* 219:255 */       return "constructor";
/* 220:    */     }
/* 221:257 */     return null;
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected Object getInstanceIdValue(int id)
/* 225:    */   {
/* 226:263 */     switch (id)
/* 227:    */     {
/* 228:    */     case 1: 
/* 229:264 */       return this.calleeObj;
/* 230:    */     case 2: 
/* 231:265 */       return this.lengthObj;
/* 232:    */     case 3: 
/* 233:267 */       Object value = this.callerObj;
/* 234:268 */       if (value == UniqueTag.NULL_VALUE)
/* 235:    */       {
/* 236:268 */         value = null;
/* 237:    */       }
/* 238:269 */       else if (value == null)
/* 239:    */       {
/* 240:270 */         NativeCall caller = this.activation.parentActivationCall;
/* 241:271 */         if (caller != null) {
/* 242:272 */           value = caller.get("arguments", caller);
/* 243:    */         }
/* 244:    */       }
/* 245:275 */       return value;
/* 246:    */     case 4: 
/* 247:278 */       return this.constructor;
/* 248:    */     }
/* 249:280 */     return super.getInstanceIdValue(id);
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected void setInstanceIdValue(int id, Object value)
/* 253:    */   {
/* 254:286 */     switch (id)
/* 255:    */     {
/* 256:    */     case 1: 
/* 257:287 */       this.calleeObj = value;return;
/* 258:    */     case 2: 
/* 259:288 */       this.lengthObj = value;return;
/* 260:    */     case 3: 
/* 261:290 */       this.callerObj = (value != null ? value : UniqueTag.NULL_VALUE);
/* 262:291 */       return;
/* 263:    */     case 4: 
/* 264:292 */       this.constructor = value;return;
/* 265:    */     }
/* 266:294 */     super.setInstanceIdValue(id, value);
/* 267:    */   }
/* 268:    */   
/* 269:    */   Object[] getIds(boolean getAll)
/* 270:    */   {
/* 271:300 */     Object[] ids = super.getIds(getAll);
/* 272:301 */     if (this.args.length != 0)
/* 273:    */     {
/* 274:302 */       boolean[] present = new boolean[this.args.length];
/* 275:303 */       int extraCount = this.args.length;
/* 276:304 */       for (int i = 0; i != ids.length; i++)
/* 277:    */       {
/* 278:305 */         Object id = ids[i];
/* 279:306 */         if ((id instanceof Integer))
/* 280:    */         {
/* 281:307 */           int index = ((Integer)id).intValue();
/* 282:308 */           if ((0 <= index) && (index < this.args.length) && 
/* 283:309 */             (present[index] == 0))
/* 284:    */           {
/* 285:310 */             present[index] = true;
/* 286:311 */             extraCount--;
/* 287:    */           }
/* 288:    */         }
/* 289:    */       }
/* 290:316 */       if (!getAll) {
/* 291:317 */         for (int i = 0; i < present.length; i++) {
/* 292:318 */           if ((present[i] == 0) && (super.has(i, this)))
/* 293:    */           {
/* 294:319 */             present[i] = true;
/* 295:320 */             extraCount--;
/* 296:    */           }
/* 297:    */         }
/* 298:    */       }
/* 299:324 */       if (extraCount != 0)
/* 300:    */       {
/* 301:325 */         Object[] tmp = new Object[extraCount + ids.length];
/* 302:326 */         System.arraycopy(ids, 0, tmp, extraCount, ids.length);
/* 303:327 */         ids = tmp;
/* 304:328 */         int offset = 0;
/* 305:329 */         for (int i = 0; i != this.args.length; i++) {
/* 306:330 */           if ((present == null) || (present[i] == 0))
/* 307:    */           {
/* 308:331 */             ids[offset] = Integer.valueOf(i);
/* 309:332 */             offset++;
/* 310:    */           }
/* 311:    */         }
/* 312:335 */         if (offset != extraCount) {
/* 313:335 */           Kit.codeBug();
/* 314:    */         }
/* 315:    */       }
/* 316:    */     }
/* 317:338 */     return ids;
/* 318:    */   }
/* 319:    */   
/* 320:    */   protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id)
/* 321:    */   {
/* 322:343 */     double d = ScriptRuntime.toNumber(id);
/* 323:344 */     int index = (int)d;
/* 324:345 */     if (d != index) {
/* 325:346 */       return super.getOwnPropertyDescriptor(cx, id);
/* 326:    */     }
/* 327:348 */     Object value = arg(index);
/* 328:349 */     if (value == NOT_FOUND) {
/* 329:350 */       return super.getOwnPropertyDescriptor(cx, id);
/* 330:    */     }
/* 331:352 */     if (sharedWithActivation(index)) {
/* 332:353 */       value = getFromActivation(index);
/* 333:    */     }
/* 334:355 */     if (super.has(index, this))
/* 335:    */     {
/* 336:356 */       ScriptableObject desc = super.getOwnPropertyDescriptor(cx, id);
/* 337:357 */       desc.put("value", desc, value);
/* 338:358 */       return desc;
/* 339:    */     }
/* 340:360 */     Scriptable scope = getParentScope();
/* 341:361 */     if (scope == null) {
/* 342:361 */       scope = this;
/* 343:    */     }
/* 344:362 */     return buildDataDescriptor(scope, value, 0);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void defineOwnProperty(Context cx, Object id, ScriptableObject desc)
/* 348:    */   {
/* 349:368 */     super.defineOwnProperty(cx, id, desc);
/* 350:    */     
/* 351:370 */     double d = ScriptRuntime.toNumber(id);
/* 352:371 */     int index = (int)d;
/* 353:372 */     if (d != index) {
/* 354:372 */       return;
/* 355:    */     }
/* 356:374 */     Object value = arg(index);
/* 357:375 */     if (value == NOT_FOUND) {
/* 358:375 */       return;
/* 359:    */     }
/* 360:377 */     if (isAccessorDescriptor(desc))
/* 361:    */     {
/* 362:378 */       removeArg(index);
/* 363:379 */       return;
/* 364:    */     }
/* 365:382 */     Object newValue = getProperty(desc, "value");
/* 366:383 */     if (newValue == NOT_FOUND) {
/* 367:383 */       return;
/* 368:    */     }
/* 369:385 */     replaceArg(index, newValue);
/* 370:387 */     if (isFalse(getProperty(desc, "writable"))) {
/* 371:388 */       removeArg(index);
/* 372:    */     }
/* 373:    */   }
/* 374:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Arguments
 * JD-Core Version:    0.7.0.1
 */