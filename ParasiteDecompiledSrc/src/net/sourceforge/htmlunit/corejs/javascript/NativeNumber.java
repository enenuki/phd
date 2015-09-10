/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ final class NativeNumber
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = 3504516769741512101L;
/*   7: 54 */   private static final Object NUMBER_TAG = "Number";
/*   8:    */   private static final int MAX_PRECISION = 100;
/*   9:    */   private static final int Id_constructor = 1;
/*  10:    */   private static final int Id_toString = 2;
/*  11:    */   private static final int Id_toLocaleString = 3;
/*  12:    */   private static final int Id_toSource = 4;
/*  13:    */   private static final int Id_valueOf = 5;
/*  14:    */   private static final int Id_toFixed = 6;
/*  15:    */   private static final int Id_toExponential = 7;
/*  16:    */   private static final int Id_toPrecision = 8;
/*  17:    */   private static final int MAX_PROTOTYPE_ID = 8;
/*  18:    */   private double doubleValue;
/*  19:    */   
/*  20:    */   static void init(Scriptable scope, boolean sealed)
/*  21:    */   {
/*  22: 60 */     NativeNumber obj = new NativeNumber(0.0D);
/*  23: 61 */     obj.exportAsJSClass(8, scope, sealed);
/*  24:    */   }
/*  25:    */   
/*  26:    */   NativeNumber(double number)
/*  27:    */   {
/*  28: 66 */     this.doubleValue = number;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getClassName()
/*  32:    */   {
/*  33: 72 */     return "Number";
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void fillConstructorProperties(IdFunctionObject ctor)
/*  37:    */   {
/*  38: 78 */     int attr = 7;
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42: 82 */     ctor.defineProperty("NaN", ScriptRuntime.NaNobj, 7);
/*  43: 83 */     ctor.defineProperty("POSITIVE_INFINITY", ScriptRuntime.wrapNumber((1.0D / 0.0D)), 7);
/*  44:    */     
/*  45:    */ 
/*  46: 86 */     ctor.defineProperty("NEGATIVE_INFINITY", ScriptRuntime.wrapNumber((-1.0D / 0.0D)), 7);
/*  47:    */     
/*  48:    */ 
/*  49: 89 */     ctor.defineProperty("MAX_VALUE", ScriptRuntime.wrapNumber(1.7976931348623157E+308D), 7);
/*  50:    */     
/*  51:    */ 
/*  52: 92 */     ctor.defineProperty("MIN_VALUE", ScriptRuntime.wrapNumber(4.9E-324D), 7);
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
/*  56: 96 */     super.fillConstructorProperties(ctor);
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void initPrototypeId(int id)
/*  60:    */   {
/*  61:    */     int arity;
/*  62:    */     String s;
/*  63:104 */     switch (id)
/*  64:    */     {
/*  65:    */     case 1: 
/*  66:105 */       arity = 1;s = "constructor"; break;
/*  67:    */     case 2: 
/*  68:106 */       arity = 1;s = "toString"; break;
/*  69:    */     case 3: 
/*  70:107 */       arity = 1;s = "toLocaleString"; break;
/*  71:    */     case 4: 
/*  72:108 */       arity = 0;s = "toSource"; break;
/*  73:    */     case 5: 
/*  74:109 */       arity = 0;s = "valueOf"; break;
/*  75:    */     case 6: 
/*  76:110 */       arity = 1;s = "toFixed"; break;
/*  77:    */     case 7: 
/*  78:111 */       arity = 1;s = "toExponential"; break;
/*  79:    */     case 8: 
/*  80:112 */       arity = 1;s = "toPrecision"; break;
/*  81:    */     default: 
/*  82:113 */       throw new IllegalArgumentException(String.valueOf(id));
/*  83:    */     }
/*  84:115 */     initPrototypeMethod(NUMBER_TAG, id, s, arity);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  88:    */   {
/*  89:122 */     if (!f.hasTag(NUMBER_TAG)) {
/*  90:123 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  91:    */     }
/*  92:125 */     int id = f.methodId();
/*  93:126 */     if (id == 1)
/*  94:    */     {
/*  95:127 */       double val = args.length >= 1 ? ScriptRuntime.toNumber(args[0]) : 0.0D;
/*  96:129 */       if (thisObj == null) {
/*  97:131 */         return new NativeNumber(val);
/*  98:    */       }
/*  99:134 */       return ScriptRuntime.wrapNumber(val);
/* 100:    */     }
/* 101:139 */     if (!(thisObj instanceof NativeNumber)) {
/* 102:140 */       throw incompatibleCallError(f);
/* 103:    */     }
/* 104:141 */     double value = ((NativeNumber)thisObj).doubleValue;
/* 105:143 */     switch (id)
/* 106:    */     {
/* 107:    */     case 2: 
/* 108:    */     case 3: 
/* 109:149 */       int base = args.length == 0 ? 10 : ScriptRuntime.toInt32(args[0]);
/* 110:    */       
/* 111:151 */       return ScriptRuntime.numberToString(value, base);
/* 112:    */     case 4: 
/* 113:155 */       return "(new Number(" + ScriptRuntime.toString(value) + "))";
/* 114:    */     case 5: 
/* 115:158 */       return ScriptRuntime.wrapNumber(value);
/* 116:    */     case 6: 
/* 117:161 */       return num_to(value, args, 2, 2, -20, 0);
/* 118:    */     case 7: 
/* 119:166 */       if (Double.isNaN(value)) {
/* 120:167 */         return "NaN";
/* 121:    */       }
/* 122:169 */       if (Double.isInfinite(value))
/* 123:    */       {
/* 124:170 */         if (value >= 0.0D) {
/* 125:171 */           return "Infinity";
/* 126:    */         }
/* 127:174 */         return "-Infinity";
/* 128:    */       }
/* 129:178 */       return num_to(value, args, 1, 3, 0, 1);
/* 130:    */     case 8: 
/* 131:184 */       if ((args.length == 0) || (args[0] == Undefined.instance)) {
/* 132:185 */         return ScriptRuntime.numberToString(value, 10);
/* 133:    */       }
/* 134:188 */       if (Double.isNaN(value)) {
/* 135:189 */         return "NaN";
/* 136:    */       }
/* 137:191 */       if (Double.isInfinite(value))
/* 138:    */       {
/* 139:192 */         if (value >= 0.0D) {
/* 140:193 */           return "Infinity";
/* 141:    */         }
/* 142:196 */         return "-Infinity";
/* 143:    */       }
/* 144:199 */       return num_to(value, args, 0, 4, 1, 0);
/* 145:    */     }
/* 146:203 */     throw new IllegalArgumentException(String.valueOf(id));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:209 */     return ScriptRuntime.numberToString(this.doubleValue, 10);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static String num_to(double val, Object[] args, int zeroArgMode, int oneArgMode, int precisionMin, int precisionOffset)
/* 155:    */   {
/* 156:    */     int precision;
/* 157:218 */     if (args.length == 0)
/* 158:    */     {
/* 159:219 */       int precision = 0;
/* 160:220 */       oneArgMode = zeroArgMode;
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:224 */       precision = ScriptRuntime.toInt32(args[0]);
/* 165:225 */       if ((precision < precisionMin) || (precision > 100))
/* 166:    */       {
/* 167:226 */         String msg = ScriptRuntime.getMessage1("msg.bad.precision", ScriptRuntime.toString(args[0]));
/* 168:    */         
/* 169:228 */         throw ScriptRuntime.constructError("RangeError", msg);
/* 170:    */       }
/* 171:    */     }
/* 172:231 */     StringBuffer sb = new StringBuffer();
/* 173:232 */     DToA.JS_dtostr(sb, oneArgMode, precision + precisionOffset, val);
/* 174:233 */     return sb.toString();
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected int findPrototypeId(String s)
/* 178:    */   {
/* 179:243 */     int id = 0;String X = null;
/* 180:    */     int c;
/* 181:244 */     switch (s.length())
/* 182:    */     {
/* 183:    */     case 7: 
/* 184:245 */       c = s.charAt(0);
/* 185:246 */       if (c == 116)
/* 186:    */       {
/* 187:246 */         X = "toFixed";id = 6;
/* 188:    */       }
/* 189:247 */       else if (c == 118)
/* 190:    */       {
/* 191:247 */         X = "valueOf";id = 5;
/* 192:    */       }
/* 193:    */       break;
/* 194:    */     case 8: 
/* 195:249 */       c = s.charAt(3);
/* 196:250 */       if (c == 111)
/* 197:    */       {
/* 198:250 */         X = "toSource";id = 4;
/* 199:    */       }
/* 200:251 */       else if (c == 116)
/* 201:    */       {
/* 202:251 */         X = "toString";id = 2;
/* 203:    */       }
/* 204:    */       break;
/* 205:    */     case 11: 
/* 206:253 */       c = s.charAt(0);
/* 207:254 */       if (c == 99)
/* 208:    */       {
/* 209:254 */         X = "constructor";id = 1;
/* 210:    */       }
/* 211:255 */       else if (c == 116)
/* 212:    */       {
/* 213:255 */         X = "toPrecision";id = 8;
/* 214:    */       }
/* 215:    */       break;
/* 216:    */     case 13: 
/* 217:257 */       X = "toExponential";id = 7; break;
/* 218:    */     case 14: 
/* 219:258 */       X = "toLocaleString";id = 3; break;
/* 220:    */     }
/* 221:260 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 222:260 */       id = 0;
/* 223:    */     }
/* 224:264 */     return id;
/* 225:    */   }
/* 226:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeNumber
 * JD-Core Version:    0.7.0.1
 */