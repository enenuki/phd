/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ final class NativeError
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -5338413581437645187L;
/*   7: 53 */   private static final Object ERROR_TAG = "Error";
/*   8:    */   private RhinoException stackProvider;
/*   9:    */   private static final int Id_constructor = 1;
/*  10:    */   private static final int Id_toString = 2;
/*  11:    */   private static final int Id_toSource = 3;
/*  12:    */   private static final int MAX_PROTOTYPE_ID = 3;
/*  13:    */   
/*  14:    */   static void init(Scriptable scope, boolean sealed)
/*  15:    */   {
/*  16: 59 */     NativeError obj = new NativeError();
/*  17: 60 */     ScriptableObject.putProperty(obj, "name", "Error");
/*  18: 61 */     ScriptableObject.putProperty(obj, "message", "");
/*  19: 62 */     ScriptableObject.putProperty(obj, "fileName", "");
/*  20: 63 */     ScriptableObject.putProperty(obj, "lineNumber", Integer.valueOf(0));
/*  21: 64 */     obj.exportAsJSClass(3, scope, sealed);
/*  22:    */   }
/*  23:    */   
/*  24:    */   static NativeError make(Context cx, Scriptable scope, IdFunctionObject ctorObj, Object[] args)
/*  25:    */   {
/*  26: 70 */     Scriptable proto = (Scriptable)ctorObj.get("prototype", ctorObj);
/*  27:    */     
/*  28: 72 */     NativeError obj = new NativeError();
/*  29: 73 */     obj.setPrototype(proto);
/*  30: 74 */     obj.setParentScope(scope);
/*  31:    */     
/*  32: 76 */     int arglen = args.length;
/*  33: 77 */     if (arglen >= 1)
/*  34:    */     {
/*  35: 78 */       ScriptableObject.putProperty(obj, "message", ScriptRuntime.toString(args[0]));
/*  36: 80 */       if (arglen >= 2)
/*  37:    */       {
/*  38: 81 */         ScriptableObject.putProperty(obj, "fileName", args[1]);
/*  39: 82 */         if (arglen >= 3)
/*  40:    */         {
/*  41: 83 */           int line = ScriptRuntime.toInt32(args[2]);
/*  42: 84 */           ScriptableObject.putProperty(obj, "lineNumber", Integer.valueOf(line));
/*  43:    */         }
/*  44:    */       }
/*  45:    */     }
/*  46: 89 */     return obj;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getClassName()
/*  50:    */   {
/*  51: 95 */     return "Error";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String toString()
/*  55:    */   {
/*  56:102 */     Object toString = js_toString(this);
/*  57:103 */     return (toString instanceof String) ? (String)toString : super.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void initPrototypeId(int id)
/*  61:    */   {
/*  62:    */     int arity;
/*  63:    */     String s;
/*  64:111 */     switch (id)
/*  65:    */     {
/*  66:    */     case 1: 
/*  67:112 */       arity = 1;s = "constructor"; break;
/*  68:    */     case 2: 
/*  69:113 */       arity = 0;s = "toString"; break;
/*  70:    */     case 3: 
/*  71:114 */       arity = 0;s = "toSource"; break;
/*  72:    */     default: 
/*  73:115 */       throw new IllegalArgumentException(String.valueOf(id));
/*  74:    */     }
/*  75:117 */     initPrototypeMethod(ERROR_TAG, id, s, arity);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  79:    */   {
/*  80:124 */     if (!f.hasTag(ERROR_TAG)) {
/*  81:125 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  82:    */     }
/*  83:127 */     int id = f.methodId();
/*  84:128 */     switch (id)
/*  85:    */     {
/*  86:    */     case 1: 
/*  87:130 */       return make(cx, scope, f, args);
/*  88:    */     case 2: 
/*  89:133 */       return js_toString(thisObj);
/*  90:    */     case 3: 
/*  91:136 */       return js_toSource(cx, scope, thisObj);
/*  92:    */     }
/*  93:138 */     throw new IllegalArgumentException(String.valueOf(id));
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setStackProvider(RhinoException re)
/*  97:    */   {
/*  98:146 */     if (this.stackProvider == null)
/*  99:    */     {
/* 100:147 */       this.stackProvider = re;
/* 101:    */       try
/* 102:    */       {
/* 103:149 */         defineProperty("stack", null, NativeError.class.getMethod("getStack", new Class[0]), NativeError.class.getMethod("setStack", new Class[] { Object.class }), 0);
/* 104:    */       }
/* 105:    */       catch (NoSuchMethodException nsm)
/* 106:    */       {
/* 107:154 */         throw new RuntimeException(nsm);
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Object getStack()
/* 113:    */   {
/* 114:160 */     RhinoException.useMozillaStackStyle(true);
/* 115:161 */     Object value = this.stackProvider == null ? NOT_FOUND : this.stackProvider.getScriptStackTrace();
/* 116:    */     
/* 117:163 */     RhinoException.useMozillaStackStyle(false);
/* 118:    */     
/* 119:    */ 
/* 120:166 */     setStack(value);
/* 121:167 */     return value;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setStack(Object value)
/* 125:    */   {
/* 126:171 */     if (this.stackProvider != null)
/* 127:    */     {
/* 128:172 */       this.stackProvider = null;
/* 129:173 */       delete("stack");
/* 130:    */     }
/* 131:175 */     put("stack", this, value);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static Object js_toString(Scriptable thisObj)
/* 135:    */   {
/* 136:179 */     Object name = ScriptableObject.getProperty(thisObj, "name");
/* 137:180 */     if ((name == NOT_FOUND) || (name == Undefined.instance)) {
/* 138:181 */       name = "Error";
/* 139:    */     } else {
/* 140:183 */       name = ScriptRuntime.toString(name);
/* 141:    */     }
/* 142:185 */     Object msg = ScriptableObject.getProperty(thisObj, "message");
/* 143:    */     Object result;
/* 144:    */     Object result;
/* 145:187 */     if ((msg == NOT_FOUND) || (msg == Undefined.instance)) {
/* 146:188 */       result = Undefined.instance;
/* 147:    */     } else {
/* 148:190 */       result = (String)name + ": " + ScriptRuntime.toString(msg);
/* 149:    */     }
/* 150:192 */     return result;
/* 151:    */   }
/* 152:    */   
/* 153:    */   private static String js_toSource(Context cx, Scriptable scope, Scriptable thisObj)
/* 154:    */   {
/* 155:199 */     Object name = ScriptableObject.getProperty(thisObj, "name");
/* 156:200 */     Object message = ScriptableObject.getProperty(thisObj, "message");
/* 157:201 */     Object fileName = ScriptableObject.getProperty(thisObj, "fileName");
/* 158:202 */     Object lineNumber = ScriptableObject.getProperty(thisObj, "lineNumber");
/* 159:    */     
/* 160:204 */     StringBuffer sb = new StringBuffer();
/* 161:205 */     sb.append("(new ");
/* 162:206 */     if (name == NOT_FOUND) {
/* 163:207 */       name = Undefined.instance;
/* 164:    */     }
/* 165:209 */     sb.append(ScriptRuntime.toString(name));
/* 166:210 */     sb.append("(");
/* 167:211 */     if ((message != NOT_FOUND) || (fileName != NOT_FOUND) || (lineNumber != NOT_FOUND))
/* 168:    */     {
/* 169:215 */       if (message == NOT_FOUND) {
/* 170:216 */         message = "";
/* 171:    */       }
/* 172:218 */       sb.append(ScriptRuntime.uneval(cx, scope, message));
/* 173:219 */       if ((fileName != NOT_FOUND) || (lineNumber != NOT_FOUND))
/* 174:    */       {
/* 175:220 */         sb.append(", ");
/* 176:221 */         if (fileName == NOT_FOUND) {
/* 177:222 */           fileName = "";
/* 178:    */         }
/* 179:224 */         sb.append(ScriptRuntime.uneval(cx, scope, fileName));
/* 180:225 */         if (lineNumber != NOT_FOUND)
/* 181:    */         {
/* 182:226 */           int line = ScriptRuntime.toInt32(lineNumber);
/* 183:227 */           if (line != 0)
/* 184:    */           {
/* 185:228 */             sb.append(", ");
/* 186:229 */             sb.append(ScriptRuntime.toString(line));
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:234 */     sb.append("))");
/* 192:235 */     return sb.toString();
/* 193:    */   }
/* 194:    */   
/* 195:    */   private static String getString(Scriptable obj, String id)
/* 196:    */   {
/* 197:240 */     Object value = ScriptableObject.getProperty(obj, id);
/* 198:241 */     if (value == NOT_FOUND) {
/* 199:241 */       return "";
/* 200:    */     }
/* 201:242 */     return ScriptRuntime.toString(value);
/* 202:    */   }
/* 203:    */   
/* 204:    */   protected int findPrototypeId(String s)
/* 205:    */   {
/* 206:251 */     int id = 0;String X = null;
/* 207:252 */     int s_length = s.length();
/* 208:253 */     if (s_length == 8)
/* 209:    */     {
/* 210:254 */       int c = s.charAt(3);
/* 211:255 */       if (c == 111)
/* 212:    */       {
/* 213:255 */         X = "toSource";id = 3;
/* 214:    */       }
/* 215:256 */       else if (c == 116)
/* 216:    */       {
/* 217:256 */         X = "toString";id = 2;
/* 218:    */       }
/* 219:    */     }
/* 220:258 */     else if (s_length == 11)
/* 221:    */     {
/* 222:258 */       X = "constructor";id = 1;
/* 223:    */     }
/* 224:259 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 225:259 */       id = 0;
/* 226:    */     }
/* 227:263 */     return id;
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeError
 * JD-Core Version:    0.7.0.1
 */