/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class ImporterTopLevel
/*   4:    */   extends TopLevel
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -9095380847465315412L;
/*   7: 78 */   private static final Object IMPORTER_TAG = "Importer";
/*   8:    */   private static final int Id_constructor = 1;
/*   9:    */   private static final int Id_importClass = 2;
/*  10:    */   private static final int Id_importPackage = 3;
/*  11:    */   private static final int MAX_PROTOTYPE_ID = 3;
/*  12:    */   
/*  13:    */   public ImporterTopLevel() {}
/*  14:    */   
/*  15:    */   public ImporterTopLevel(Context cx)
/*  16:    */   {
/*  17: 83 */     this(cx, false);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ImporterTopLevel(Context cx, boolean sealed)
/*  21:    */   {
/*  22: 88 */     initStandardObjects(cx, sealed);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getClassName()
/*  26:    */   {
/*  27: 94 */     return this.topScopeFlag ? "global" : "JavaImporter";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*  31:    */   {
/*  32: 99 */     ImporterTopLevel obj = new ImporterTopLevel();
/*  33:100 */     obj.exportAsJSClass(3, scope, sealed);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void initStandardObjects(Context cx, boolean sealed)
/*  37:    */   {
/*  38:107 */     cx.initStandardObjects(this, sealed);
/*  39:108 */     this.topScopeFlag = true;
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:112 */     IdFunctionObject ctor = exportAsJSClass(3, this, false);
/*  44:113 */     if (sealed) {
/*  45:114 */       ctor.sealObject();
/*  46:    */     }
/*  47:119 */     delete("constructor");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean has(String name, Scriptable start)
/*  51:    */   {
/*  52:124 */     return (super.has(name, start)) || (getPackageProperty(name, start) != NOT_FOUND);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object get(String name, Scriptable start)
/*  56:    */   {
/*  57:130 */     Object result = super.get(name, start);
/*  58:131 */     if (result != NOT_FOUND) {
/*  59:132 */       return result;
/*  60:    */     }
/*  61:133 */     result = getPackageProperty(name, start);
/*  62:134 */     return result;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private Object getPackageProperty(String name, Scriptable start)
/*  66:    */   {
/*  67:138 */     Object result = NOT_FOUND;
/*  68:    */     Object[] elements;
/*  69:140 */     synchronized (this.importedPackages)
/*  70:    */     {
/*  71:141 */       elements = this.importedPackages.toArray();
/*  72:    */     }
/*  73:143 */     for (int i = 0; i < elements.length; i++)
/*  74:    */     {
/*  75:144 */       NativeJavaPackage p = (NativeJavaPackage)elements[i];
/*  76:145 */       Object v = p.getPkgProperty(name, start, false);
/*  77:146 */       if ((v != null) && (!(v instanceof NativeJavaPackage))) {
/*  78:147 */         if (result == NOT_FOUND) {
/*  79:148 */           result = v;
/*  80:    */         } else {
/*  81:150 */           throw Context.reportRuntimeError2("msg.ambig.import", result.toString(), v.toString());
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85:155 */     return result;
/*  86:    */   }
/*  87:    */   
/*  88:    */   /**
/*  89:    */    * @deprecated
/*  90:    */    */
/*  91:    */   public void importPackage(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  92:    */   {
/*  93:164 */     js_importPackage(args);
/*  94:    */   }
/*  95:    */   
/*  96:    */   private Object js_construct(Scriptable scope, Object[] args)
/*  97:    */   {
/*  98:169 */     ImporterTopLevel result = new ImporterTopLevel();
/*  99:170 */     for (int i = 0; i != args.length; i++)
/* 100:    */     {
/* 101:171 */       Object arg = args[i];
/* 102:172 */       if ((arg instanceof NativeJavaClass)) {
/* 103:173 */         result.importClass((NativeJavaClass)arg);
/* 104:174 */       } else if ((arg instanceof NativeJavaPackage)) {
/* 105:175 */         result.importPackage((NativeJavaPackage)arg);
/* 106:    */       } else {
/* 107:177 */         throw Context.reportRuntimeError1("msg.not.class.not.pkg", Context.toString(arg));
/* 108:    */       }
/* 109:    */     }
/* 110:186 */     result.setParentScope(scope);
/* 111:187 */     result.setPrototype(this);
/* 112:188 */     return result;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private Object js_importClass(Object[] args)
/* 116:    */   {
/* 117:193 */     for (int i = 0; i != args.length; i++)
/* 118:    */     {
/* 119:194 */       Object arg = args[i];
/* 120:195 */       if (!(arg instanceof NativeJavaClass)) {
/* 121:196 */         throw Context.reportRuntimeError1("msg.not.class", Context.toString(arg));
/* 122:    */       }
/* 123:199 */       importClass((NativeJavaClass)arg);
/* 124:    */     }
/* 125:201 */     return Undefined.instance;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private Object js_importPackage(Object[] args)
/* 129:    */   {
/* 130:206 */     for (int i = 0; i != args.length; i++)
/* 131:    */     {
/* 132:207 */       Object arg = args[i];
/* 133:208 */       if (!(arg instanceof NativeJavaPackage)) {
/* 134:209 */         throw Context.reportRuntimeError1("msg.not.pkg", Context.toString(arg));
/* 135:    */       }
/* 136:212 */       importPackage((NativeJavaPackage)arg);
/* 137:    */     }
/* 138:214 */     return Undefined.instance;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void importPackage(NativeJavaPackage pkg)
/* 142:    */   {
/* 143:219 */     if (pkg == null) {
/* 144:220 */       return;
/* 145:    */     }
/* 146:222 */     synchronized (this.importedPackages)
/* 147:    */     {
/* 148:223 */       for (int j = 0; j != this.importedPackages.size(); j++) {
/* 149:224 */         if (pkg.equals(this.importedPackages.get(j))) {
/* 150:225 */           return;
/* 151:    */         }
/* 152:    */       }
/* 153:228 */       this.importedPackages.add(pkg);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   private void importClass(NativeJavaClass cl)
/* 158:    */   {
/* 159:234 */     String s = cl.getClassObject().getName();
/* 160:235 */     String n = s.substring(s.lastIndexOf('.') + 1);
/* 161:236 */     Object val = get(n, this);
/* 162:237 */     if ((val != NOT_FOUND) && (val != cl)) {
/* 163:238 */       throw Context.reportRuntimeError1("msg.prop.defined", n);
/* 164:    */     }
/* 165:241 */     put(n, this, cl);
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected void initPrototypeId(int id)
/* 169:    */   {
/* 170:    */     int arity;
/* 171:    */     String s;
/* 172:249 */     switch (id)
/* 173:    */     {
/* 174:    */     case 1: 
/* 175:250 */       arity = 0;s = "constructor"; break;
/* 176:    */     case 2: 
/* 177:251 */       arity = 1;s = "importClass"; break;
/* 178:    */     case 3: 
/* 179:252 */       arity = 1;s = "importPackage"; break;
/* 180:    */     default: 
/* 181:253 */       throw new IllegalArgumentException(String.valueOf(id));
/* 182:    */     }
/* 183:255 */     initPrototypeMethod(IMPORTER_TAG, id, s, arity);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 187:    */   {
/* 188:262 */     if (!f.hasTag(IMPORTER_TAG)) {
/* 189:263 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 190:    */     }
/* 191:265 */     int id = f.methodId();
/* 192:266 */     switch (id)
/* 193:    */     {
/* 194:    */     case 1: 
/* 195:268 */       return js_construct(scope, args);
/* 196:    */     case 2: 
/* 197:271 */       return realThis(thisObj, f).js_importClass(args);
/* 198:    */     case 3: 
/* 199:274 */       return realThis(thisObj, f).js_importPackage(args);
/* 200:    */     }
/* 201:276 */     throw new IllegalArgumentException(String.valueOf(id));
/* 202:    */   }
/* 203:    */   
/* 204:    */   private ImporterTopLevel realThis(Scriptable thisObj, IdFunctionObject f)
/* 205:    */   {
/* 206:281 */     if (this.topScopeFlag) {
/* 207:284 */       return this;
/* 208:    */     }
/* 209:286 */     if (!(thisObj instanceof ImporterTopLevel)) {
/* 210:287 */       throw incompatibleCallError(f);
/* 211:    */     }
/* 212:288 */     return (ImporterTopLevel)thisObj;
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected int findPrototypeId(String s)
/* 216:    */   {
/* 217:298 */     int id = 0;String X = null;
/* 218:299 */     int s_length = s.length();
/* 219:300 */     if (s_length == 11)
/* 220:    */     {
/* 221:301 */       int c = s.charAt(0);
/* 222:302 */       if (c == 99)
/* 223:    */       {
/* 224:302 */         X = "constructor";id = 1;
/* 225:    */       }
/* 226:303 */       else if (c == 105)
/* 227:    */       {
/* 228:303 */         X = "importClass";id = 2;
/* 229:    */       }
/* 230:    */     }
/* 231:305 */     else if (s_length == 13)
/* 232:    */     {
/* 233:305 */       X = "importPackage";id = 3;
/* 234:    */     }
/* 235:306 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 236:306 */       id = 0;
/* 237:    */     }
/* 238:310 */     return id;
/* 239:    */   }
/* 240:    */   
/* 241:321 */   private ObjArray importedPackages = new ObjArray();
/* 242:    */   private boolean topScopeFlag;
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ImporterTopLevel
 * JD-Core Version:    0.7.0.1
 */