/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import javassist.bytecode.BadBytecode;
/*   8:    */ import javassist.bytecode.CodeAttribute;
/*   9:    */ import javassist.bytecode.CodeIterator;
/*  10:    */ import javassist.bytecode.ExceptionTable;
/*  11:    */ import javassist.bytecode.MethodInfo;
/*  12:    */ 
/*  13:    */ public class BasicBlock
/*  14:    */ {
/*  15:    */   public int position;
/*  16:    */   public int length;
/*  17:    */   public int incoming;
/*  18:    */   public BasicBlock[] exit;
/*  19:    */   public boolean stop;
/*  20:    */   public Catch toCatch;
/*  21:    */   
/*  22:    */   protected BasicBlock(int pos)
/*  23:    */   {
/*  24: 30 */     this.position = pos;
/*  25: 31 */     this.length = 0;
/*  26: 32 */     this.incoming = 0;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static BasicBlock find(BasicBlock[] blocks, int pos)
/*  30:    */     throws BadBytecode
/*  31:    */   {
/*  32: 38 */     for (int i = 0; i < blocks.length; i++)
/*  33:    */     {
/*  34: 39 */       int iPos = blocks[i].position;
/*  35: 40 */       if ((iPos <= pos) && (pos < iPos + blocks[i].length)) {
/*  36: 41 */         return blocks[i];
/*  37:    */       }
/*  38:    */     }
/*  39: 44 */     throw new BadBytecode("no basic block at " + pos);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static class Catch
/*  43:    */   {
/*  44:    */     Catch next;
/*  45:    */     BasicBlock body;
/*  46:    */     int typeIndex;
/*  47:    */     
/*  48:    */     Catch(BasicBlock b, int i, Catch c)
/*  49:    */     {
/*  50: 52 */       this.body = b;
/*  51: 53 */       this.typeIndex = i;
/*  52: 54 */       this.next = c;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toString()
/*  57:    */   {
/*  58: 59 */     StringBuffer sbuf = new StringBuffer();
/*  59: 60 */     String cname = getClass().getName();
/*  60: 61 */     int i = cname.lastIndexOf('.');
/*  61: 62 */     sbuf.append(i < 0 ? cname : cname.substring(i + 1));
/*  62: 63 */     sbuf.append("[");
/*  63: 64 */     toString2(sbuf);
/*  64: 65 */     sbuf.append("]");
/*  65: 66 */     return sbuf.toString();
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void toString2(StringBuffer sbuf)
/*  69:    */   {
/*  70: 70 */     sbuf.append("pos=").append(this.position).append(", len=").append(this.length).append(", in=").append(this.incoming).append(", exit{");
/*  71: 73 */     if (this.exit != null) {
/*  72: 74 */       for (int i = 0; i < this.exit.length; i++) {
/*  73: 75 */         sbuf.append(this.exit[i].position).append(", ");
/*  74:    */       }
/*  75:    */     }
/*  76: 78 */     sbuf.append("}, {");
/*  77: 79 */     Catch th = this.toCatch;
/*  78: 80 */     while (th != null)
/*  79:    */     {
/*  80: 81 */       sbuf.append("(").append(th.body.position).append(", ").append(th.typeIndex).append("), ");
/*  81:    */       
/*  82: 83 */       th = th.next;
/*  83:    */     }
/*  84: 86 */     sbuf.append("}");
/*  85:    */   }
/*  86:    */   
/*  87:    */   static class Mark
/*  88:    */     implements Comparable
/*  89:    */   {
/*  90:    */     int position;
/*  91:    */     BasicBlock block;
/*  92:    */     BasicBlock[] jump;
/*  93:    */     boolean alwaysJmp;
/*  94:    */     int size;
/*  95:    */     BasicBlock.Catch catcher;
/*  96:    */     
/*  97:    */     Mark(int p)
/*  98:    */     {
/*  99: 98 */       this.position = p;
/* 100: 99 */       this.block = null;
/* 101:100 */       this.jump = null;
/* 102:101 */       this.alwaysJmp = false;
/* 103:102 */       this.size = 0;
/* 104:103 */       this.catcher = null;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public int compareTo(Object obj)
/* 108:    */     {
/* 109:107 */       if ((obj instanceof Mark))
/* 110:    */       {
/* 111:108 */         int pos = ((Mark)obj).position;
/* 112:109 */         return this.position - pos;
/* 113:    */       }
/* 114:112 */       return -1;
/* 115:    */     }
/* 116:    */     
/* 117:    */     void setJump(BasicBlock[] bb, int s, boolean always)
/* 118:    */     {
/* 119:116 */       this.jump = bb;
/* 120:117 */       this.size = s;
/* 121:118 */       this.alwaysJmp = always;
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static class Maker
/* 126:    */   {
/* 127:    */     protected BasicBlock makeBlock(int pos)
/* 128:    */     {
/* 129:127 */       return new BasicBlock(pos);
/* 130:    */     }
/* 131:    */     
/* 132:    */     protected BasicBlock[] makeArray(int size)
/* 133:    */     {
/* 134:131 */       return new BasicBlock[size];
/* 135:    */     }
/* 136:    */     
/* 137:    */     private BasicBlock[] makeArray(BasicBlock b)
/* 138:    */     {
/* 139:135 */       BasicBlock[] array = makeArray(1);
/* 140:136 */       array[0] = b;
/* 141:137 */       return array;
/* 142:    */     }
/* 143:    */     
/* 144:    */     private BasicBlock[] makeArray(BasicBlock b1, BasicBlock b2)
/* 145:    */     {
/* 146:141 */       BasicBlock[] array = makeArray(2);
/* 147:142 */       array[0] = b1;
/* 148:143 */       array[1] = b2;
/* 149:144 */       return array;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public BasicBlock[] make(MethodInfo minfo)
/* 153:    */       throws BadBytecode
/* 154:    */     {
/* 155:148 */       CodeAttribute ca = minfo.getCodeAttribute();
/* 156:149 */       if (ca == null) {
/* 157:150 */         return null;
/* 158:    */       }
/* 159:152 */       CodeIterator ci = ca.iterator();
/* 160:153 */       return make(ci, 0, ci.getCodeLength(), ca.getExceptionTable());
/* 161:    */     }
/* 162:    */     
/* 163:    */     public BasicBlock[] make(CodeIterator ci, int begin, int end, ExceptionTable et)
/* 164:    */       throws BadBytecode
/* 165:    */     {
/* 166:160 */       HashMap marks = makeMarks(ci, begin, end, et);
/* 167:161 */       BasicBlock[] bb = makeBlocks(marks);
/* 168:162 */       addCatchers(bb, et);
/* 169:163 */       return bb;
/* 170:    */     }
/* 171:    */     
/* 172:    */     private BasicBlock.Mark makeMark(HashMap table, int pos)
/* 173:    */     {
/* 174:169 */       return makeMark0(table, pos, true, true);
/* 175:    */     }
/* 176:    */     
/* 177:    */     private BasicBlock.Mark makeMark(HashMap table, int pos, BasicBlock[] jump, int size, boolean always)
/* 178:    */     {
/* 179:177 */       BasicBlock.Mark m = makeMark0(table, pos, false, false);
/* 180:178 */       m.setJump(jump, size, always);
/* 181:179 */       return m;
/* 182:    */     }
/* 183:    */     
/* 184:    */     private BasicBlock.Mark makeMark0(HashMap table, int pos, boolean isBlockBegin, boolean isTarget)
/* 185:    */     {
/* 186:184 */       Integer p = new Integer(pos);
/* 187:185 */       BasicBlock.Mark m = (BasicBlock.Mark)table.get(p);
/* 188:186 */       if (m == null)
/* 189:    */       {
/* 190:187 */         m = new BasicBlock.Mark(pos);
/* 191:188 */         table.put(p, m);
/* 192:    */       }
/* 193:191 */       if (isBlockBegin)
/* 194:    */       {
/* 195:192 */         if (m.block == null) {
/* 196:193 */           m.block = makeBlock(pos);
/* 197:    */         }
/* 198:195 */         if (isTarget) {
/* 199:196 */           m.block.incoming += 1;
/* 200:    */         }
/* 201:    */       }
/* 202:199 */       return m;
/* 203:    */     }
/* 204:    */     
/* 205:    */     private HashMap makeMarks(CodeIterator ci, int begin, int end, ExceptionTable et)
/* 206:    */       throws BadBytecode
/* 207:    */     {
/* 208:206 */       ci.begin();
/* 209:207 */       ci.move(begin);
/* 210:208 */       HashMap marks = new HashMap();
/* 211:209 */       while (ci.hasNext())
/* 212:    */       {
/* 213:210 */         int index = ci.next();
/* 214:211 */         if (index >= end) {
/* 215:    */           break;
/* 216:    */         }
/* 217:214 */         int op = ci.byteAt(index);
/* 218:215 */         if (((153 <= op) && (op <= 166)) || (op == 198) || (op == 199))
/* 219:    */         {
/* 220:217 */           BasicBlock.Mark to = makeMark(marks, index + ci.s16bitAt(index + 1));
/* 221:218 */           BasicBlock.Mark next = makeMark(marks, index + 3);
/* 222:219 */           makeMark(marks, index, makeArray(to.block, next.block), 3, false);
/* 223:    */         }
/* 224:221 */         else if ((167 <= op) && (op <= 171))
/* 225:    */         {
/* 226:222 */           switch (op)
/* 227:    */           {
/* 228:    */           case 167: 
/* 229:    */           case 168: 
/* 230:225 */             makeGotoJsr(marks, index, index + ci.s16bitAt(index + 1), op == 167, 3);
/* 231:    */             
/* 232:227 */             break;
/* 233:    */           case 169: 
/* 234:229 */             makeMark(marks, index, null, 1, true);
/* 235:230 */             break;
/* 236:    */           case 170: 
/* 237:232 */             int pos = (index & 0xFFFFFFFC) + 4;
/* 238:233 */             int low = ci.s32bitAt(pos + 4);
/* 239:234 */             int high = ci.s32bitAt(pos + 8);
/* 240:235 */             int ncases = high - low + 1;
/* 241:236 */             BasicBlock[] to = makeArray(ncases + 1);
/* 242:237 */             to[0] = makeMark(marks, index + ci.s32bitAt(pos)).block;
/* 243:238 */             int p = pos + 12;
/* 244:239 */             int n = p + ncases * 4;
/* 245:240 */             int k = 1;
/* 246:241 */             while (p < n)
/* 247:    */             {
/* 248:242 */               to[(k++)] = makeMark(marks, index + ci.s32bitAt(p)).block;
/* 249:243 */               p += 4;
/* 250:    */             }
/* 251:245 */             makeMark(marks, index, to, n - index, true);
/* 252:246 */             break;
/* 253:    */           case 171: 
/* 254:248 */             int pos = (index & 0xFFFFFFFC) + 4;
/* 255:249 */             int ncases = ci.s32bitAt(pos + 4);
/* 256:250 */             BasicBlock[] to = makeArray(ncases + 1);
/* 257:251 */             to[0] = makeMark(marks, index + ci.s32bitAt(pos)).block;
/* 258:252 */             int p = pos + 8 + 4;
/* 259:253 */             int n = p + ncases * 8 - 4;
/* 260:254 */             int k = 1;
/* 261:255 */             while (p < n)
/* 262:    */             {
/* 263:256 */               to[(k++)] = makeMark(marks, index + ci.s32bitAt(p)).block;
/* 264:257 */               p += 8;
/* 265:    */             }
/* 266:259 */             makeMark(marks, index, to, n - index, true);
/* 267:260 */             break;
/* 268:    */           }
/* 269:    */         }
/* 270:262 */         else if (((172 <= op) && (op <= 177)) || (op == 191))
/* 271:    */         {
/* 272:263 */           makeMark(marks, index, null, 1, true);
/* 273:    */         }
/* 274:264 */         else if ((op == 200) || (op == 201))
/* 275:    */         {
/* 276:265 */           makeGotoJsr(marks, index, index + ci.s32bitAt(index + 1), op == 200, 5);
/* 277:    */         }
/* 278:267 */         else if ((op == 196) && (ci.byteAt(index + 1) == 169))
/* 279:    */         {
/* 280:268 */           makeMark(marks, index, null, 1, true);
/* 281:    */         }
/* 282:    */       }
/* 283:271 */       if (et != null)
/* 284:    */       {
/* 285:272 */         int i = et.size();
/* 286:    */         for (;;)
/* 287:    */         {
/* 288:273 */           i--;
/* 289:273 */           if (i < 0) {
/* 290:    */             break;
/* 291:    */           }
/* 292:274 */           makeMark0(marks, et.startPc(i), true, false);
/* 293:275 */           makeMark(marks, et.handlerPc(i));
/* 294:    */         }
/* 295:    */       }
/* 296:279 */       return marks;
/* 297:    */     }
/* 298:    */     
/* 299:    */     private void makeGotoJsr(HashMap marks, int pos, int target, boolean isGoto, int size)
/* 300:    */     {
/* 301:283 */       BasicBlock.Mark to = makeMark(marks, target);
/* 302:    */       BasicBlock[] jumps;
/* 303:    */       BasicBlock[] jumps;
/* 304:285 */       if (isGoto)
/* 305:    */       {
/* 306:286 */         jumps = makeArray(to.block);
/* 307:    */       }
/* 308:    */       else
/* 309:    */       {
/* 310:288 */         BasicBlock.Mark next = makeMark(marks, pos + size);
/* 311:289 */         jumps = makeArray(to.block, next.block);
/* 312:    */       }
/* 313:292 */       makeMark(marks, pos, jumps, size, isGoto);
/* 314:    */     }
/* 315:    */     
/* 316:    */     private BasicBlock[] makeBlocks(HashMap markTable)
/* 317:    */     {
/* 318:296 */       BasicBlock.Mark[] marks = (BasicBlock.Mark[])markTable.values().toArray(new BasicBlock.Mark[markTable.size()]);
/* 319:    */       
/* 320:298 */       Arrays.sort(marks);
/* 321:299 */       ArrayList blocks = new ArrayList();
/* 322:300 */       int i = 0;
/* 323:    */       BasicBlock prev;
/* 324:    */       BasicBlock prev;
/* 325:302 */       if ((marks.length > 0) && (marks[0].position == 0) && (marks[0].block != null)) {
/* 326:303 */         prev = getBBlock(marks[(i++)]);
/* 327:    */       } else {
/* 328:305 */         prev = makeBlock(0);
/* 329:    */       }
/* 330:307 */       blocks.add(prev);
/* 331:308 */       while (i < marks.length)
/* 332:    */       {
/* 333:309 */         BasicBlock.Mark m = marks[(i++)];
/* 334:310 */         BasicBlock bb = getBBlock(m);
/* 335:311 */         if (bb == null)
/* 336:    */         {
/* 337:313 */           if (prev.length > 0)
/* 338:    */           {
/* 339:315 */             prev = makeBlock(prev.position + prev.length);
/* 340:316 */             blocks.add(prev);
/* 341:    */           }
/* 342:319 */           prev.length = (m.position + m.size - prev.position);
/* 343:320 */           prev.exit = m.jump;
/* 344:321 */           prev.stop = m.alwaysJmp;
/* 345:    */         }
/* 346:    */         else
/* 347:    */         {
/* 348:325 */           if (prev.length == 0)
/* 349:    */           {
/* 350:326 */             prev.length = (m.position - prev.position);
/* 351:327 */             bb.incoming += 1;
/* 352:328 */             prev.exit = makeArray(bb);
/* 353:    */           }
/* 354:    */           else
/* 355:    */           {
/* 356:332 */             int prevPos = prev.position;
/* 357:333 */             if (prevPos + prev.length < m.position)
/* 358:    */             {
/* 359:334 */               prev = makeBlock(prevPos + prev.length);
/* 360:335 */               prev.length = (m.position - prevPos);
/* 361:    */               
/* 362:    */ 
/* 363:338 */               prev.exit = makeArray(bb);
/* 364:    */             }
/* 365:    */           }
/* 366:342 */           blocks.add(bb);
/* 367:343 */           prev = bb;
/* 368:    */         }
/* 369:    */       }
/* 370:347 */       return (BasicBlock[])blocks.toArray(makeArray(blocks.size()));
/* 371:    */     }
/* 372:    */     
/* 373:    */     private static BasicBlock getBBlock(BasicBlock.Mark m)
/* 374:    */     {
/* 375:351 */       BasicBlock b = m.block;
/* 376:352 */       if ((b != null) && (m.size > 0))
/* 377:    */       {
/* 378:353 */         b.exit = m.jump;
/* 379:354 */         b.length = m.size;
/* 380:355 */         b.stop = m.alwaysJmp;
/* 381:    */       }
/* 382:358 */       return b;
/* 383:    */     }
/* 384:    */     
/* 385:    */     private void addCatchers(BasicBlock[] blocks, ExceptionTable et)
/* 386:    */       throws BadBytecode
/* 387:    */     {
/* 388:364 */       if (et == null) {
/* 389:365 */         return;
/* 390:    */       }
/* 391:367 */       int i = et.size();
/* 392:    */       for (;;)
/* 393:    */       {
/* 394:368 */         i--;
/* 395:368 */         if (i < 0) {
/* 396:    */           break;
/* 397:    */         }
/* 398:369 */         BasicBlock handler = BasicBlock.find(blocks, et.handlerPc(i));
/* 399:370 */         int start = et.startPc(i);
/* 400:371 */         int end = et.endPc(i);
/* 401:372 */         int type = et.catchType(i);
/* 402:373 */         handler.incoming -= 1;
/* 403:374 */         for (int k = 0; k < blocks.length; k++)
/* 404:    */         {
/* 405:375 */           BasicBlock bb = blocks[k];
/* 406:376 */           int iPos = bb.position;
/* 407:377 */           if ((start <= iPos) && (iPos < end))
/* 408:    */           {
/* 409:378 */             bb.toCatch = new BasicBlock.Catch(handler, type, bb.toCatch);
/* 410:379 */             handler.incoming += 1;
/* 411:    */           }
/* 412:    */         }
/* 413:    */       }
/* 414:    */     }
/* 415:    */   }
/* 416:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.BasicBlock
 * JD-Core Version:    0.7.0.1
 */