/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.BadBytecode;
/*   4:    */ import javassist.bytecode.CodeIterator;
/*   5:    */ 
/*   6:    */ public class Liveness
/*   7:    */ {
/*   8:    */   protected static final byte UNKNOWN = 0;
/*   9:    */   protected static final byte READ = 1;
/*  10:    */   protected static final byte UPDATED = 2;
/*  11:    */   protected byte[] localsUsage;
/*  12: 32 */   public static boolean useArgs = true;
/*  13:    */   static final int NOT_YET = 0;
/*  14:    */   static final int CHANGED_LAST = 1;
/*  15:    */   static final int DONE = 2;
/*  16:    */   static final int CHANGED_NOW = 3;
/*  17:    */   
/*  18:    */   public void compute(CodeIterator ci, TypedBlock[] blocks, int maxLocals, TypeData[] args)
/*  19:    */     throws BadBytecode
/*  20:    */   {
/*  21: 38 */     computeUsage(ci, blocks, maxLocals);
/*  22: 39 */     if (useArgs) {
/*  23: 40 */       useAllArgs(blocks, args);
/*  24:    */     }
/*  25: 42 */     computeLiveness1(blocks[0]);
/*  26: 43 */     while (hasChanged(blocks)) {
/*  27: 44 */       computeLiveness2(blocks[0]);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   private void useAllArgs(TypedBlock[] blocks, TypeData[] args)
/*  32:    */   {
/*  33: 48 */     for (int k = 0; k < blocks.length; k++)
/*  34:    */     {
/*  35: 49 */       byte[] usage = blocks[k].localsUsage;
/*  36: 50 */       for (int i = 0; i < args.length; i++) {
/*  37: 51 */         if (args[i] != TypeTag.TOP) {
/*  38: 52 */           usage[i] = 1;
/*  39:    */         }
/*  40:    */       }
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void computeLiveness1(TypedBlock tb)
/*  45:    */   {
/*  46: 62 */     if (tb.updating)
/*  47:    */     {
/*  48: 64 */       computeLiveness1u(tb);
/*  49: 65 */       return;
/*  50:    */     }
/*  51: 68 */     if (tb.inputs != null) {
/*  52: 69 */       return;
/*  53:    */     }
/*  54: 71 */     tb.updating = true;
/*  55: 72 */     byte[] usage = tb.localsUsage;
/*  56: 73 */     int n = usage.length;
/*  57: 74 */     boolean[] in = new boolean[n];
/*  58: 75 */     for (int i = 0; i < n; i++) {
/*  59: 76 */       in[i] = (usage[i] == 1 ? 1 : false);
/*  60:    */     }
/*  61: 78 */     BasicBlock.Catch handlers = tb.toCatch;
/*  62: 79 */     while (handlers != null)
/*  63:    */     {
/*  64: 80 */       TypedBlock h = (TypedBlock)handlers.body;
/*  65: 81 */       computeLiveness1(h);
/*  66: 82 */       for (int k = 0; k < n; k++) {
/*  67: 83 */         if (h.inputs[k] != 0) {
/*  68: 84 */           in[k] = true;
/*  69:    */         }
/*  70:    */       }
/*  71: 86 */       handlers = handlers.next;
/*  72:    */     }
/*  73: 89 */     if (tb.exit != null) {
/*  74: 90 */       for (int i = 0; i < tb.exit.length; i++)
/*  75:    */       {
/*  76: 91 */         TypedBlock e = (TypedBlock)tb.exit[i];
/*  77: 92 */         computeLiveness1(e);
/*  78: 93 */         for (int k = 0; k < n; k++) {
/*  79: 94 */           if (in[k] == 0) {
/*  80: 95 */             in[k] = ((usage[k] == 0) && (e.inputs[k] != 0) ? 1 : false);
/*  81:    */           }
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85: 99 */     tb.updating = false;
/*  86:100 */     if (tb.inputs == null)
/*  87:    */     {
/*  88:101 */       tb.inputs = in;
/*  89:102 */       tb.status = 2;
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:105 */       for (int i = 0; i < n; i++) {
/*  94:106 */         if ((in[i] != 0) && (tb.inputs[i] == 0))
/*  95:    */         {
/*  96:107 */           tb.inputs[i] = true;
/*  97:108 */           tb.status = 3;
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void computeLiveness1u(TypedBlock tb)
/* 104:    */   {
/* 105:114 */     if (tb.inputs == null)
/* 106:    */     {
/* 107:115 */       byte[] usage = tb.localsUsage;
/* 108:116 */       int n = usage.length;
/* 109:117 */       boolean[] in = new boolean[n];
/* 110:118 */       for (int i = 0; i < n; i++) {
/* 111:119 */         in[i] = (usage[i] == 1 ? 1 : false);
/* 112:    */       }
/* 113:121 */       tb.inputs = in;
/* 114:122 */       tb.status = 2;
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void computeLiveness2(TypedBlock tb)
/* 119:    */   {
/* 120:127 */     if ((tb.updating) || (tb.status >= 2)) {
/* 121:128 */       return;
/* 122:    */     }
/* 123:130 */     tb.updating = true;
/* 124:131 */     if (tb.exit == null)
/* 125:    */     {
/* 126:132 */       tb.status = 2;
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:134 */       boolean changed = false;
/* 131:135 */       for (int i = 0; i < tb.exit.length; i++)
/* 132:    */       {
/* 133:136 */         TypedBlock e = (TypedBlock)tb.exit[i];
/* 134:137 */         computeLiveness2(e);
/* 135:138 */         if (e.status != 2) {
/* 136:139 */           changed = true;
/* 137:    */         }
/* 138:    */       }
/* 139:142 */       if (changed)
/* 140:    */       {
/* 141:143 */         changed = false;
/* 142:144 */         byte[] usage = tb.localsUsage;
/* 143:145 */         int n = usage.length;
/* 144:146 */         for (int i = 0; i < tb.exit.length; i++)
/* 145:    */         {
/* 146:147 */           TypedBlock e = (TypedBlock)tb.exit[i];
/* 147:148 */           if (e.status != 2) {
/* 148:149 */             for (int k = 0; k < n; k++) {
/* 149:150 */               if ((tb.inputs[k] == 0) && 
/* 150:151 */                 (usage[k] == 0) && (e.inputs[k] != 0))
/* 151:    */               {
/* 152:152 */                 tb.inputs[k] = true;
/* 153:153 */                 changed = true;
/* 154:    */               }
/* 155:    */             }
/* 156:    */           }
/* 157:    */         }
/* 158:158 */         tb.status = (changed ? 3 : 2);
/* 159:    */       }
/* 160:    */       else
/* 161:    */       {
/* 162:161 */         tb.status = 2;
/* 163:    */       }
/* 164:    */     }
/* 165:164 */     if (computeLiveness2except(tb)) {
/* 166:165 */       tb.status = 3;
/* 167:    */     }
/* 168:167 */     tb.updating = false;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private boolean computeLiveness2except(TypedBlock tb)
/* 172:    */   {
/* 173:171 */     BasicBlock.Catch handlers = tb.toCatch;
/* 174:172 */     boolean changed = false;
/* 175:173 */     while (handlers != null)
/* 176:    */     {
/* 177:174 */       TypedBlock h = (TypedBlock)handlers.body;
/* 178:175 */       computeLiveness2(h);
/* 179:176 */       if (h.status != 2)
/* 180:    */       {
/* 181:177 */         boolean[] in = tb.inputs;
/* 182:178 */         int n = in.length;
/* 183:179 */         for (int k = 0; k < n; k++) {
/* 184:180 */           if ((in[k] == 0) && (h.inputs[k] != 0))
/* 185:    */           {
/* 186:181 */             in[k] = true;
/* 187:182 */             changed = true;
/* 188:    */           }
/* 189:    */         }
/* 190:    */       }
/* 191:186 */       handlers = handlers.next;
/* 192:    */     }
/* 193:189 */     return changed;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private boolean hasChanged(TypedBlock[] blocks)
/* 197:    */   {
/* 198:193 */     int n = blocks.length;
/* 199:194 */     boolean changed = false;
/* 200:195 */     for (int i = 0; i < n; i++)
/* 201:    */     {
/* 202:196 */       TypedBlock tb = blocks[i];
/* 203:197 */       if (tb.status == 3)
/* 204:    */       {
/* 205:198 */         tb.status = 1;
/* 206:199 */         changed = true;
/* 207:    */       }
/* 208:    */       else
/* 209:    */       {
/* 210:202 */         tb.status = 0;
/* 211:    */       }
/* 212:    */     }
/* 213:205 */     return changed;
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void computeUsage(CodeIterator ci, TypedBlock[] blocks, int maxLocals)
/* 217:    */     throws BadBytecode
/* 218:    */   {
/* 219:211 */     int n = blocks.length;
/* 220:212 */     for (int i = 0; i < n; i++)
/* 221:    */     {
/* 222:213 */       TypedBlock tb = blocks[i];
/* 223:214 */       this.localsUsage = (tb.localsUsage = new byte[maxLocals]);
/* 224:215 */       int pos = tb.position;
/* 225:216 */       analyze(ci, pos, pos + tb.length);
/* 226:217 */       this.localsUsage = null;
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected final void readLocal(int reg)
/* 231:    */   {
/* 232:222 */     if (this.localsUsage[reg] == 0) {
/* 233:223 */       this.localsUsage[reg] = 1;
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   protected final void writeLocal(int reg)
/* 238:    */   {
/* 239:227 */     if (this.localsUsage[reg] == 0) {
/* 240:228 */       this.localsUsage[reg] = 2;
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected void analyze(CodeIterator ci, int begin, int end)
/* 245:    */     throws BadBytecode
/* 246:    */   {
/* 247:234 */     ci.begin();
/* 248:235 */     ci.move(begin);
/* 249:236 */     while (ci.hasNext())
/* 250:    */     {
/* 251:237 */       int index = ci.next();
/* 252:238 */       if (index >= end) {
/* 253:    */         break;
/* 254:    */       }
/* 255:241 */       int op = ci.byteAt(index);
/* 256:242 */       if (op < 96)
/* 257:    */       {
/* 258:243 */         if (op < 54) {
/* 259:244 */           doOpcode0_53(ci, index, op);
/* 260:    */         } else {
/* 261:246 */           doOpcode54_95(ci, index, op);
/* 262:    */         }
/* 263:    */       }
/* 264:248 */       else if (op == 132) {
/* 265:250 */         readLocal(ci.byteAt(index + 1));
/* 266:252 */       } else if (op == 196) {
/* 267:253 */         doWIDE(ci, index);
/* 268:    */       }
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   private void doOpcode0_53(CodeIterator ci, int pos, int op)
/* 273:    */   {
/* 274:258 */     switch (op)
/* 275:    */     {
/* 276:    */     case 21: 
/* 277:    */     case 22: 
/* 278:    */     case 23: 
/* 279:    */     case 24: 
/* 280:    */     case 25: 
/* 281:264 */       readLocal(ci.byteAt(pos + 1));
/* 282:265 */       break;
/* 283:    */     case 26: 
/* 284:    */     case 27: 
/* 285:    */     case 28: 
/* 286:    */     case 29: 
/* 287:270 */       readLocal(op - 26);
/* 288:271 */       break;
/* 289:    */     case 30: 
/* 290:    */     case 31: 
/* 291:    */     case 32: 
/* 292:    */     case 33: 
/* 293:276 */       readLocal(op - 30);
/* 294:277 */       break;
/* 295:    */     case 34: 
/* 296:    */     case 35: 
/* 297:    */     case 36: 
/* 298:    */     case 37: 
/* 299:282 */       readLocal(op - 34);
/* 300:283 */       break;
/* 301:    */     case 38: 
/* 302:    */     case 39: 
/* 303:    */     case 40: 
/* 304:    */     case 41: 
/* 305:288 */       readLocal(op - 38);
/* 306:289 */       break;
/* 307:    */     case 42: 
/* 308:    */     case 43: 
/* 309:    */     case 44: 
/* 310:    */     case 45: 
/* 311:294 */       readLocal(op - 42);
/* 312:    */     }
/* 313:    */   }
/* 314:    */   
/* 315:    */   private void doOpcode54_95(CodeIterator ci, int pos, int op)
/* 316:    */   {
/* 317:300 */     switch (op)
/* 318:    */     {
/* 319:    */     case 54: 
/* 320:    */     case 55: 
/* 321:    */     case 56: 
/* 322:    */     case 57: 
/* 323:    */     case 58: 
/* 324:306 */       writeLocal(ci.byteAt(pos + 1));
/* 325:307 */       break;
/* 326:    */     case 59: 
/* 327:    */     case 60: 
/* 328:    */     case 61: 
/* 329:    */     case 62: 
/* 330:312 */       writeLocal(op - 59);
/* 331:313 */       break;
/* 332:    */     case 63: 
/* 333:    */     case 64: 
/* 334:    */     case 65: 
/* 335:    */     case 66: 
/* 336:318 */       writeLocal(op - 63);
/* 337:319 */       break;
/* 338:    */     case 67: 
/* 339:    */     case 68: 
/* 340:    */     case 69: 
/* 341:    */     case 70: 
/* 342:324 */       writeLocal(op - 67);
/* 343:325 */       break;
/* 344:    */     case 71: 
/* 345:    */     case 72: 
/* 346:    */     case 73: 
/* 347:    */     case 74: 
/* 348:330 */       writeLocal(op - 71);
/* 349:331 */       break;
/* 350:    */     case 75: 
/* 351:    */     case 76: 
/* 352:    */     case 77: 
/* 353:    */     case 78: 
/* 354:336 */       writeLocal(op - 75);
/* 355:    */     }
/* 356:    */   }
/* 357:    */   
/* 358:    */   private void doWIDE(CodeIterator ci, int pos)
/* 359:    */     throws BadBytecode
/* 360:    */   {
/* 361:342 */     int op = ci.byteAt(pos + 1);
/* 362:343 */     int var = ci.u16bitAt(pos + 2);
/* 363:344 */     switch (op)
/* 364:    */     {
/* 365:    */     case 21: 
/* 366:    */     case 22: 
/* 367:    */     case 23: 
/* 368:    */     case 24: 
/* 369:    */     case 25: 
/* 370:350 */       readLocal(var);
/* 371:351 */       break;
/* 372:    */     case 54: 
/* 373:    */     case 55: 
/* 374:    */     case 56: 
/* 375:    */     case 57: 
/* 376:    */     case 58: 
/* 377:357 */       writeLocal(var);
/* 378:358 */       break;
/* 379:    */     case 132: 
/* 380:360 */       readLocal(var);
/* 381:    */     }
/* 382:    */   }
/* 383:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.Liveness
 * JD-Core Version:    0.7.0.1
 */