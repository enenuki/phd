/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeFunction;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeGenerator;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeIterator;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  15:    */ 
/*  16:    */ public final class OptRuntime
/*  17:    */   extends ScriptRuntime
/*  18:    */ {
/*  19: 47 */   public static final Double zeroObj = new Double(0.0D);
/*  20: 48 */   public static final Double oneObj = new Double(1.0D);
/*  21: 49 */   public static final Double minusOneObj = new Double(-1.0D);
/*  22:    */   
/*  23:    */   public static Object call0(Callable fun, Scriptable thisObj, Context cx, Scriptable scope)
/*  24:    */   {
/*  25: 57 */     return fun.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static Object call1(Callable fun, Scriptable thisObj, Object arg0, Context cx, Scriptable scope)
/*  29:    */   {
/*  30: 66 */     return fun.call(cx, scope, thisObj, new Object[] { arg0 });
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static Object call2(Callable fun, Scriptable thisObj, Object arg0, Object arg1, Context cx, Scriptable scope)
/*  34:    */   {
/*  35: 76 */     return fun.call(cx, scope, thisObj, new Object[] { arg0, arg1 });
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static Object callN(Callable fun, Scriptable thisObj, Object[] args, Context cx, Scriptable scope)
/*  39:    */   {
/*  40: 86 */     return fun.call(cx, scope, thisObj, args);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static Object callName(Object[] args, String name, Context cx, Scriptable scope)
/*  44:    */   {
/*  45: 95 */     Callable f = getNameFunctionAndThis(name, cx, scope);
/*  46: 96 */     Scriptable thisObj = lastStoredScriptable(cx);
/*  47: 97 */     return f.call(cx, scope, thisObj, args);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Object callName0(String name, Context cx, Scriptable scope)
/*  51:    */   {
/*  52:106 */     Callable f = getNameFunctionAndThis(name, cx, scope);
/*  53:107 */     Scriptable thisObj = lastStoredScriptable(cx);
/*  54:108 */     return f.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static Object callProp0(Object value, String property, Context cx, Scriptable scope)
/*  58:    */   {
/*  59:117 */     Callable f = getPropFunctionAndThis(value, property, cx, scope);
/*  60:118 */     Scriptable thisObj = lastStoredScriptable(cx);
/*  61:119 */     return f.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Object add(Object val1, double val2)
/*  65:    */   {
/*  66:124 */     if ((val1 instanceof Scriptable)) {
/*  67:125 */       val1 = ((Scriptable)val1).getDefaultValue(null);
/*  68:    */     }
/*  69:126 */     if (!(val1 instanceof String)) {
/*  70:127 */       return wrapDouble(toNumber(val1) + val2);
/*  71:    */     }
/*  72:128 */     return ((String)val1).concat(toString(val2));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Object add(double val1, Object val2)
/*  76:    */   {
/*  77:133 */     if ((val2 instanceof Scriptable)) {
/*  78:134 */       val2 = ((Scriptable)val2).getDefaultValue(null);
/*  79:    */     }
/*  80:135 */     if (!(val2 instanceof String)) {
/*  81:136 */       return wrapDouble(toNumber(val2) + val1);
/*  82:    */     }
/*  83:137 */     return toString(val1).concat((String)val2);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static Object elemIncrDecr(Object obj, double index, Context cx, int incrDecrMask)
/*  87:    */   {
/*  88:143 */     return ScriptRuntime.elemIncrDecr(obj, new Double(index), cx, incrDecrMask);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Object[] padStart(Object[] currentArgs, int count)
/*  92:    */   {
/*  93:148 */     Object[] result = new Object[currentArgs.length + count];
/*  94:149 */     System.arraycopy(currentArgs, 0, result, count, currentArgs.length);
/*  95:150 */     return result;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static void initFunction(NativeFunction fn, int functionType, Scriptable scope, Context cx)
/*  99:    */   {
/* 100:156 */     ScriptRuntime.initFunction(cx, scope, fn, functionType, false);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static Object callSpecial(Context cx, Callable fun, Scriptable thisObj, Object[] args, Scriptable scope, Scriptable callerThis, int callType, String fileName, int lineNumber)
/* 104:    */   {
/* 105:165 */     return ScriptRuntime.callSpecial(cx, fun, thisObj, args, scope, callerThis, callType, fileName, lineNumber);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static Object newObjectSpecial(Context cx, Object fun, Object[] args, Scriptable scope, Scriptable callerThis, int callType)
/* 109:    */   {
/* 110:174 */     return ScriptRuntime.newSpecial(cx, fun, args, scope, callType);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static Double wrapDouble(double num)
/* 114:    */   {
/* 115:179 */     if (num == 0.0D)
/* 116:    */     {
/* 117:180 */       if (1.0D / num > 0.0D) {
/* 118:182 */         return zeroObj;
/* 119:    */       }
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:184 */       if (num == 1.0D) {
/* 124:185 */         return oneObj;
/* 125:    */       }
/* 126:186 */       if (num == -1.0D) {
/* 127:187 */         return minusOneObj;
/* 128:    */       }
/* 129:188 */       if (num != num) {
/* 130:189 */         return NaNobj;
/* 131:    */       }
/* 132:    */     }
/* 133:191 */     return new Double(num);
/* 134:    */   }
/* 135:    */   
/* 136:    */   static String encodeIntArray(int[] array)
/* 137:    */   {
/* 138:197 */     if (array == null) {
/* 139:197 */       return null;
/* 140:    */     }
/* 141:198 */     int n = array.length;
/* 142:199 */     char[] buffer = new char[1 + n * 2];
/* 143:200 */     buffer[0] = '\001';
/* 144:201 */     for (int i = 0; i != n; i++)
/* 145:    */     {
/* 146:202 */       int value = array[i];
/* 147:203 */       int shift = 1 + i * 2;
/* 148:204 */       buffer[shift] = ((char)(value >>> 16));
/* 149:205 */       buffer[(shift + 1)] = ((char)value);
/* 150:    */     }
/* 151:207 */     return new String(buffer);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static int[] decodeIntArray(String str, int arraySize)
/* 155:    */   {
/* 156:213 */     if (arraySize == 0)
/* 157:    */     {
/* 158:214 */       if (str != null) {
/* 159:214 */         throw new IllegalArgumentException();
/* 160:    */       }
/* 161:215 */       return null;
/* 162:    */     }
/* 163:217 */     if ((str.length() != 1 + arraySize * 2) && (str.charAt(0) != '\001')) {
/* 164:218 */       throw new IllegalArgumentException();
/* 165:    */     }
/* 166:220 */     int[] array = new int[arraySize];
/* 167:221 */     for (int i = 0; i != arraySize; i++)
/* 168:    */     {
/* 169:222 */       int shift = 1 + i * 2;
/* 170:223 */       array[i] = (str.charAt(shift) << '\020' | str.charAt(shift + 1));
/* 171:    */     }
/* 172:225 */     return array;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static Scriptable newArrayLiteral(Object[] objects, String encodedInts, int skipCount, Context cx, Scriptable scope)
/* 176:    */   {
/* 177:234 */     int[] skipIndexces = decodeIntArray(encodedInts, skipCount);
/* 178:235 */     return newArrayLiteral(objects, skipIndexces, cx, scope);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static void main(final Script script, String[] args)
/* 182:    */   {
/* 183:240 */     ContextFactory.getGlobal().call(new ContextAction()
/* 184:    */     {
/* 185:    */       public Object run(Context cx)
/* 186:    */       {
/* 187:243 */         ScriptableObject global = ScriptRuntime.getGlobal(cx);
/* 188:    */         
/* 189:    */ 
/* 190:    */ 
/* 191:247 */         Object[] argsCopy = new Object[this.val$args.length];
/* 192:248 */         System.arraycopy(this.val$args, 0, argsCopy, 0, this.val$args.length);
/* 193:249 */         Scriptable argsObj = cx.newArray(global, argsCopy);
/* 194:250 */         global.defineProperty("arguments", argsObj, 2);
/* 195:    */         
/* 196:252 */         script.exec(cx, global);
/* 197:253 */         return null;
/* 198:    */       }
/* 199:    */     });
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static void throwStopIteration(Object obj)
/* 203:    */   {
/* 204:259 */     throw new JavaScriptException(NativeIterator.getStopIterationObject((Scriptable)obj), "", 0);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static Scriptable createNativeGenerator(NativeFunction funObj, Scriptable scope, Scriptable thisObj, int maxLocals, int maxStack)
/* 208:    */   {
/* 209:269 */     return new NativeGenerator(scope, funObj, new GeneratorState(thisObj, maxLocals, maxStack));
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static Object[] getGeneratorStackState(Object obj)
/* 213:    */   {
/* 214:274 */     GeneratorState rgs = (GeneratorState)obj;
/* 215:275 */     if (rgs.stackState == null) {
/* 216:276 */       rgs.stackState = new Object[rgs.maxStack];
/* 217:    */     }
/* 218:277 */     return rgs.stackState;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static Object[] getGeneratorLocalsState(Object obj)
/* 222:    */   {
/* 223:281 */     GeneratorState rgs = (GeneratorState)obj;
/* 224:282 */     if (rgs.localsState == null) {
/* 225:283 */       rgs.localsState = new Object[rgs.maxLocals];
/* 226:    */     }
/* 227:284 */     return rgs.localsState;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static class GeneratorState
/* 231:    */   {
/* 232:    */     static final String CLASS_NAME = "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime$GeneratorState";
/* 233:    */     public int resumptionPoint;
/* 234:    */     static final String resumptionPoint_NAME = "resumptionPoint";
/* 235:    */     static final String resumptionPoint_TYPE = "I";
/* 236:    */     public Scriptable thisObj;
/* 237:    */     static final String thisObj_NAME = "thisObj";
/* 238:    */     static final String thisObj_TYPE = "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;";
/* 239:    */     Object[] stackState;
/* 240:    */     Object[] localsState;
/* 241:    */     int maxLocals;
/* 242:    */     int maxStack;
/* 243:    */     
/* 244:    */     GeneratorState(Scriptable thisObj, int maxLocals, int maxStack)
/* 245:    */     {
/* 246:306 */       this.thisObj = thisObj;
/* 247:307 */       this.maxLocals = maxLocals;
/* 248:308 */       this.maxStack = maxStack;
/* 249:    */     }
/* 250:    */   }
/* 251:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.OptRuntime
 * JD-Core Version:    0.7.0.1
 */