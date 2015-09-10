/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.TopLevel.Builtins;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   9:    */ 
/*  10:    */ class NativeRegExpCtor
/*  11:    */   extends BaseFunction
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -5733330028285400526L;
/*  14:    */   private static final int Id_multiline = 1;
/*  15:    */   private static final int Id_STAR = 2;
/*  16:    */   private static final int Id_input = 3;
/*  17:    */   private static final int Id_UNDERSCORE = 4;
/*  18:    */   private static final int Id_lastMatch = 5;
/*  19:    */   private static final int Id_AMPERSAND = 6;
/*  20:    */   private static final int Id_lastParen = 7;
/*  21:    */   private static final int Id_PLUS = 8;
/*  22:    */   private static final int Id_leftContext = 9;
/*  23:    */   private static final int Id_BACK_QUOTE = 10;
/*  24:    */   private static final int Id_rightContext = 11;
/*  25:    */   private static final int Id_QUOTE = 12;
/*  26:    */   private static final int DOLLAR_ID_BASE = 12;
/*  27:    */   private static final int Id_DOLLAR_1 = 13;
/*  28:    */   private static final int Id_DOLLAR_2 = 14;
/*  29:    */   private static final int Id_DOLLAR_3 = 15;
/*  30:    */   private static final int Id_DOLLAR_4 = 16;
/*  31:    */   private static final int Id_DOLLAR_5 = 17;
/*  32:    */   private static final int Id_DOLLAR_6 = 18;
/*  33:    */   private static final int Id_DOLLAR_7 = 19;
/*  34:    */   private static final int Id_DOLLAR_8 = 20;
/*  35:    */   private static final int Id_DOLLAR_9 = 21;
/*  36:    */   private static final int MAX_INSTANCE_ID = 21;
/*  37:    */   
/*  38:    */   public String getFunctionName()
/*  39:    */   {
/*  40: 69 */     return "RegExp";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  44:    */   {
/*  45: 76 */     if ((args.length > 0) && ((args[0] instanceof NativeRegExp)) && ((args.length == 1) || (args[1] == Undefined.instance))) {
/*  46: 79 */       return args[0];
/*  47:    */     }
/*  48: 81 */     return construct(cx, scope, args);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  52:    */   {
/*  53: 87 */     NativeRegExp re = new NativeRegExp();
/*  54: 88 */     re.compile(cx, scope, args);
/*  55: 89 */     ScriptRuntime.setBuiltinProtoAndParent(re, scope, TopLevel.Builtins.RegExp);
/*  56: 90 */     return re;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private static RegExpImpl getImpl()
/*  60:    */   {
/*  61: 95 */     Context cx = Context.getCurrentContext();
/*  62: 96 */     return (RegExpImpl)ScriptRuntime.getRegExpProxy(cx);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected int getMaxInstanceId()
/*  66:    */   {
/*  67:138 */     return super.getMaxInstanceId() + 21;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected int findInstanceIdInfo(String s)
/*  71:    */   {
/*  72:145 */     int id = 0;String X = null;
/*  73:146 */     switch (s.length())
/*  74:    */     {
/*  75:    */     case 2: 
/*  76:147 */       switch (s.charAt(1))
/*  77:    */       {
/*  78:    */       case '&': 
/*  79:148 */         if (s.charAt(0) == '$') {
/*  80:148 */           id = 6;
/*  81:    */         }
/*  82:    */         break;
/*  83:    */       case '\'': 
/*  84:149 */         if (s.charAt(0) == '$') {
/*  85:149 */           id = 12;
/*  86:    */         }
/*  87:    */         break;
/*  88:    */       case '*': 
/*  89:150 */         if (s.charAt(0) == '$') {
/*  90:150 */           id = 2;
/*  91:    */         }
/*  92:    */         break;
/*  93:    */       case '+': 
/*  94:151 */         if (s.charAt(0) == '$') {
/*  95:151 */           id = 8;
/*  96:    */         }
/*  97:    */         break;
/*  98:    */       case '1': 
/*  99:152 */         if (s.charAt(0) == '$') {
/* 100:152 */           id = 13;
/* 101:    */         }
/* 102:    */         break;
/* 103:    */       case '2': 
/* 104:153 */         if (s.charAt(0) == '$') {
/* 105:153 */           id = 14;
/* 106:    */         }
/* 107:    */         break;
/* 108:    */       case '3': 
/* 109:154 */         if (s.charAt(0) == '$') {
/* 110:154 */           id = 15;
/* 111:    */         }
/* 112:    */         break;
/* 113:    */       case '4': 
/* 114:155 */         if (s.charAt(0) == '$') {
/* 115:155 */           id = 16;
/* 116:    */         }
/* 117:    */         break;
/* 118:    */       case '5': 
/* 119:156 */         if (s.charAt(0) == '$') {
/* 120:156 */           id = 17;
/* 121:    */         }
/* 122:    */         break;
/* 123:    */       case '6': 
/* 124:157 */         if (s.charAt(0) == '$') {
/* 125:157 */           id = 18;
/* 126:    */         }
/* 127:    */         break;
/* 128:    */       case '7': 
/* 129:158 */         if (s.charAt(0) == '$') {
/* 130:158 */           id = 19;
/* 131:    */         }
/* 132:    */         break;
/* 133:    */       case '8': 
/* 134:159 */         if (s.charAt(0) == '$') {
/* 135:159 */           id = 20;
/* 136:    */         }
/* 137:    */         break;
/* 138:    */       case '9': 
/* 139:160 */         if (s.charAt(0) == '$') {
/* 140:160 */           id = 21;
/* 141:    */         }
/* 142:    */         break;
/* 143:    */       case '_': 
/* 144:161 */         if (s.charAt(0) == '$') {
/* 145:161 */           id = 4;
/* 146:    */         }
/* 147:    */         break;
/* 148:    */       case '`': 
/* 149:162 */         if (s.charAt(0) == '$')
/* 150:    */         {
/* 151:162 */           id = 10;
/* 152:    */           break label663;
/* 153:    */         }
/* 154:    */         break;
/* 155:    */       }
/* 156:163 */       break;
/* 157:    */     case 5: 
/* 158:164 */       X = "input";id = 3; break;
/* 159:    */     case 9: 
/* 160:165 */       int c = s.charAt(4);
/* 161:166 */       if (c == 77)
/* 162:    */       {
/* 163:166 */         X = "lastMatch";id = 5;
/* 164:    */       }
/* 165:167 */       else if (c == 80)
/* 166:    */       {
/* 167:167 */         X = "lastParen";id = 7;
/* 168:    */       }
/* 169:168 */       else if (c == 105)
/* 170:    */       {
/* 171:168 */         X = "multiline";id = 1;
/* 172:    */       }
/* 173:    */       break;
/* 174:    */     case 11: 
/* 175:170 */       X = "leftContext";id = 9; break;
/* 176:    */     case 12: 
/* 177:171 */       X = "rightContext";id = 11; break;
/* 178:    */     }
/* 179:173 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 180:173 */       id = 0;
/* 181:    */     }
/* 182:    */     label663:
/* 183:177 */     if (id == 0) {
/* 184:177 */       return super.findInstanceIdInfo(s);
/* 185:    */     }
/* 186:    */     int attr;
/* 187:180 */     switch (id)
/* 188:    */     {
/* 189:    */     case 1: 
/* 190:    */     case 2: 
/* 191:    */     case 3: 
/* 192:    */     case 4: 
/* 193:185 */       attr = 4;
/* 194:186 */       break;
/* 195:    */     default: 
/* 196:188 */       attr = 5;
/* 197:    */     }
/* 198:192 */     return instanceIdInfo(attr, super.getMaxInstanceId() + id);
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected String getInstanceIdName(int id)
/* 202:    */   {
/* 203:200 */     int shifted = id - super.getMaxInstanceId();
/* 204:201 */     if ((1 <= shifted) && (shifted <= 21))
/* 205:    */     {
/* 206:202 */       switch (shifted)
/* 207:    */       {
/* 208:    */       case 1: 
/* 209:203 */         return "multiline";
/* 210:    */       case 2: 
/* 211:204 */         return "$*";
/* 212:    */       case 3: 
/* 213:206 */         return "input";
/* 214:    */       case 4: 
/* 215:207 */         return "$_";
/* 216:    */       case 5: 
/* 217:209 */         return "lastMatch";
/* 218:    */       case 6: 
/* 219:210 */         return "$&";
/* 220:    */       case 7: 
/* 221:212 */         return "lastParen";
/* 222:    */       case 8: 
/* 223:213 */         return "$+";
/* 224:    */       case 9: 
/* 225:215 */         return "leftContext";
/* 226:    */       case 10: 
/* 227:216 */         return "$`";
/* 228:    */       case 11: 
/* 229:218 */         return "rightContext";
/* 230:    */       case 12: 
/* 231:219 */         return "$'";
/* 232:    */       }
/* 233:222 */       int substring_number = shifted - 12 - 1;
/* 234:223 */       char[] buf = { '$', (char)(49 + substring_number) };
/* 235:224 */       return new String(buf);
/* 236:    */     }
/* 237:226 */     return super.getInstanceIdName(id);
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected Object getInstanceIdValue(int id)
/* 241:    */   {
/* 242:232 */     int shifted = id - super.getMaxInstanceId();
/* 243:233 */     if ((1 <= shifted) && (shifted <= 21))
/* 244:    */     {
/* 245:234 */       RegExpImpl impl = getImpl();
/* 246:    */       Object stringResult;
/* 247:236 */       switch (shifted)
/* 248:    */       {
/* 249:    */       case 1: 
/* 250:    */       case 2: 
/* 251:239 */         return ScriptRuntime.wrapBoolean(impl.multiline);
/* 252:    */       case 3: 
/* 253:    */       case 4: 
/* 254:243 */         stringResult = impl.input;
/* 255:244 */         break;
/* 256:    */       case 5: 
/* 257:    */       case 6: 
/* 258:248 */         stringResult = impl.lastMatch;
/* 259:249 */         break;
/* 260:    */       case 7: 
/* 261:    */       case 8: 
/* 262:253 */         stringResult = impl.lastParen;
/* 263:254 */         break;
/* 264:    */       case 9: 
/* 265:    */       case 10: 
/* 266:258 */         stringResult = impl.leftContext;
/* 267:259 */         break;
/* 268:    */       case 11: 
/* 269:    */       case 12: 
/* 270:263 */         stringResult = impl.rightContext;
/* 271:264 */         break;
/* 272:    */       default: 
/* 273:269 */         int substring_number = shifted - 12 - 1;
/* 274:270 */         stringResult = impl.getParenSubString(substring_number);
/* 275:271 */         break;
/* 276:    */       }
/* 277:274 */       return stringResult == null ? "" : stringResult.toString();
/* 278:    */     }
/* 279:276 */     return super.getInstanceIdValue(id);
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected void setInstanceIdValue(int id, Object value)
/* 283:    */   {
/* 284:282 */     int shifted = id - super.getMaxInstanceId();
/* 285:283 */     switch (shifted)
/* 286:    */     {
/* 287:    */     case 1: 
/* 288:    */     case 2: 
/* 289:286 */       getImpl().multiline = ScriptRuntime.toBoolean(value);
/* 290:287 */       return;
/* 291:    */     case 3: 
/* 292:    */     case 4: 
/* 293:291 */       getImpl().input = ScriptRuntime.toString(value);
/* 294:292 */       return;
/* 295:    */     case 5: 
/* 296:    */     case 6: 
/* 297:    */     case 7: 
/* 298:    */     case 8: 
/* 299:    */     case 9: 
/* 300:    */     case 10: 
/* 301:    */     case 11: 
/* 302:    */     case 12: 
/* 303:302 */       return;
/* 304:    */     }
/* 305:304 */     int substring_number = shifted - 12 - 1;
/* 306:305 */     if ((0 <= substring_number) && (substring_number <= 8)) {
/* 307:306 */       return;
/* 308:    */     }
/* 309:309 */     super.setInstanceIdValue(id, value);
/* 310:    */   }
/* 311:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.NativeRegExpCtor
 * JD-Core Version:    0.7.0.1
 */