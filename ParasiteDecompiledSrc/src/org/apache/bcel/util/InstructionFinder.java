/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.AbstractList;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.apache.bcel.generic.ClassGenException;
/*  10:    */ import org.apache.bcel.generic.Instruction;
/*  11:    */ import org.apache.bcel.generic.InstructionHandle;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.regexp.RE;
/*  14:    */ import org.apache.regexp.RESyntaxException;
/*  15:    */ 
/*  16:    */ public class InstructionFinder
/*  17:    */ {
/*  18:    */   private static final int OFFSET = 32767;
/*  19:    */   private static final int NO_OPCODES = 256;
/*  20: 93 */   private static final HashMap map = new HashMap();
/*  21:    */   private InstructionList il;
/*  22:    */   private String il_string;
/*  23:    */   private InstructionHandle[] handles;
/*  24:    */   
/*  25:    */   public InstructionFinder(InstructionList il)
/*  26:    */   {
/*  27:103 */     this.il = il;
/*  28:104 */     reread();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final void reread()
/*  32:    */   {
/*  33:111 */     int size = this.il.getLength();
/*  34:112 */     char[] buf = new char[size];
/*  35:113 */     this.handles = this.il.getInstructionHandles();
/*  36:116 */     for (int i = 0; i < size; i++) {
/*  37:117 */       buf[i] = makeChar(this.handles[i].getInstruction().getOpcode());
/*  38:    */     }
/*  39:119 */     this.il_string = new String(buf);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private static final String mapName(String pattern)
/*  43:    */   {
/*  44:129 */     String result = (String)map.get(pattern);
/*  45:131 */     if (result != null) {
/*  46:132 */       return result;
/*  47:    */     }
/*  48:134 */     for (short i = 0; i < 256; i = (short)(i + 1)) {
/*  49:135 */       if (pattern.equals(org.apache.bcel.Constants.OPCODE_NAMES[i])) {
/*  50:136 */         return "" + makeChar(i);
/*  51:    */       }
/*  52:    */     }
/*  53:138 */     throw new RuntimeException("Instruction unknown: " + pattern);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static final String compilePattern(String pattern)
/*  57:    */   {
/*  58:149 */     String lower = pattern.toLowerCase();
/*  59:150 */     StringBuffer buf = new StringBuffer();
/*  60:151 */     int size = pattern.length();
/*  61:153 */     for (int i = 0; i < size; i++)
/*  62:    */     {
/*  63:154 */       char ch = lower.charAt(i);
/*  64:156 */       if (Character.isLetterOrDigit(ch))
/*  65:    */       {
/*  66:157 */         StringBuffer name = new StringBuffer();
/*  67:159 */         while (((Character.isLetterOrDigit(ch)) || (ch == '_')) && (i < size))
/*  68:    */         {
/*  69:160 */           name.append(ch);
/*  70:    */           
/*  71:162 */           i++;
/*  72:162 */           if (i >= size) {
/*  73:    */             break;
/*  74:    */           }
/*  75:163 */           ch = lower.charAt(i);
/*  76:    */         }
/*  77:168 */         i--;
/*  78:    */         
/*  79:170 */         buf.append(mapName(name.toString()));
/*  80:    */       }
/*  81:171 */       else if (!Character.isWhitespace(ch))
/*  82:    */       {
/*  83:172 */         buf.append(ch);
/*  84:    */       }
/*  85:    */     }
/*  86:175 */     return buf.toString();
/*  87:    */   }
/*  88:    */   
/*  89:    */   private InstructionHandle[] getMatch(int matched_from, int match_length)
/*  90:    */   {
/*  91:182 */     InstructionHandle[] match = new InstructionHandle[match_length];
/*  92:183 */     System.arraycopy(this.handles, matched_from, match, 0, match_length);
/*  93:    */     
/*  94:185 */     return match;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final Iterator search(String pattern, InstructionHandle from, CodeConstraint constraint)
/*  98:    */   {
/*  99:218 */     String search = compilePattern(pattern);
/* 100:219 */     int start = -1;
/* 101:221 */     for (int i = 0; i < this.handles.length; i++) {
/* 102:222 */       if (this.handles[i] == from)
/* 103:    */       {
/* 104:223 */         start = i;
/* 105:224 */         break;
/* 106:    */       }
/* 107:    */     }
/* 108:228 */     if (start == -1) {
/* 109:229 */       throw new ClassGenException("Instruction handle " + from + " not found in instruction list.");
/* 110:    */     }
/* 111:    */     try
/* 112:    */     {
/* 113:232 */       RE regex = new RE(search);
/* 114:233 */       ArrayList matches = new ArrayList();
/* 115:235 */       while ((start < this.il_string.length()) && (regex.match(this.il_string, start)))
/* 116:    */       {
/* 117:236 */         int startExpr = regex.getParenStart(0);
/* 118:237 */         int endExpr = regex.getParenEnd(0);
/* 119:238 */         int lenExpr = regex.getParenLength(0);
/* 120:    */         
/* 121:240 */         InstructionHandle[] match = getMatch(startExpr, lenExpr);
/* 122:242 */         if ((constraint == null) || (constraint.checkCode(match))) {
/* 123:243 */           matches.add(match);
/* 124:    */         }
/* 125:244 */         start = endExpr;
/* 126:    */       }
/* 127:247 */       return matches.iterator();
/* 128:    */     }
/* 129:    */     catch (RESyntaxException e)
/* 130:    */     {
/* 131:249 */       System.err.println(e);
/* 132:    */     }
/* 133:252 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final Iterator search(String pattern)
/* 137:    */   {
/* 138:264 */     return search(pattern, this.il.getStart(), null);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public final Iterator search(String pattern, InstructionHandle from)
/* 142:    */   {
/* 143:276 */     return search(pattern, from, null);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public final Iterator search(String pattern, CodeConstraint constraint)
/* 147:    */   {
/* 148:288 */     return search(pattern, this.il.getStart(), constraint);
/* 149:    */   }
/* 150:    */   
/* 151:    */   private static final char makeChar(short opcode)
/* 152:    */   {
/* 153:295 */     return (char)(opcode + 32767);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public final InstructionList getInstructionList()
/* 157:    */   {
/* 158:301 */     return this.il;
/* 159:    */   }
/* 160:    */   
/* 161:    */   static
/* 162:    */   {
/* 163:320 */     map.put("arithmeticinstruction", "(irem|lrem|iand|ior|ineg|isub|lneg|fneg|fmul|ldiv|fadd|lxor|frem|idiv|land|ixor|ishr|fsub|lshl|fdiv|iadd|lor|dmul|lsub|ishl|imul|lmul|lushr|dneg|iushr|lshr|ddiv|drem|dadd|ladd|dsub)");
/* 164:321 */     map.put("invokeinstruction", "(invokevirtual|invokeinterface|invokestatic|invokespecial)");
/* 165:322 */     map.put("arrayinstruction", "(baload|aastore|saload|caload|fastore|lastore|iaload|castore|iastore|aaload|bastore|sastore|faload|laload|daload|dastore)");
/* 166:323 */     map.put("gotoinstruction", "(goto|goto_w)");
/* 167:324 */     map.put("conversioninstruction", "(d2l|l2d|i2s|d2i|l2i|i2b|l2f|d2f|f2i|i2d|i2l|f2d|i2c|f2l|i2f)");
/* 168:325 */     map.put("localvariableinstruction", "(fstore|iinc|lload|dstore|dload|iload|aload|astore|istore|fload|lstore)");
/* 169:326 */     map.put("loadinstruction", "(fload|dload|lload|iload|aload)");
/* 170:327 */     map.put("fieldinstruction", "(getfield|putstatic|getstatic|putfield)");
/* 171:328 */     map.put("cpinstruction", "(ldc2_w|invokeinterface|multianewarray|putstatic|instanceof|getstatic|checkcast|getfield|invokespecial|ldc_w|invokestatic|invokevirtual|putfield|ldc|new|anewarray)");
/* 172:329 */     map.put("stackinstruction", "(dup2|swap|dup2_x2|pop|pop2|dup|dup2_x1|dup_x2|dup_x1)");
/* 173:330 */     map.put("branchinstruction", "(ifle|if_acmpne|if_icmpeq|if_acmpeq|ifnonnull|goto_w|iflt|ifnull|if_icmpne|tableswitch|if_icmple|ifeq|if_icmplt|jsr_w|if_icmpgt|ifgt|jsr|goto|ifne|ifge|lookupswitch|if_icmpge)");
/* 174:331 */     map.put("returninstruction", "(lreturn|ireturn|freturn|dreturn|areturn|return)");
/* 175:332 */     map.put("storeinstruction", "(istore|fstore|dstore|astore|lstore)");
/* 176:333 */     map.put("select", "(tableswitch|lookupswitch)");
/* 177:334 */     map.put("ifinstruction", "(ifeq|ifgt|if_icmpne|if_icmpeq|ifge|ifnull|ifne|if_icmple|if_icmpge|if_acmpeq|if_icmplt|if_acmpne|ifnonnull|iflt|if_icmpgt|ifle)");
/* 178:335 */     map.put("jsrinstruction", "(jsr|jsr_w)");
/* 179:336 */     map.put("variablelengthinstruction", "(tableswitch|jsr|goto|lookupswitch)");
/* 180:337 */     map.put("unconditionalbranch", "(goto|jsr|jsr_w|athrow|goto_w)");
/* 181:338 */     map.put("constantpushinstruction", "(dconst|bipush|sipush|fconst|iconst|lconst)");
/* 182:339 */     map.put("typedinstruction", "(imul|lsub|aload|fload|lor|new|aaload|fcmpg|iand|iaload|lrem|idiv|d2l|isub|dcmpg|dastore|ret|f2d|f2i|drem|iinc|i2c|checkcast|frem|lreturn|astore|lushr|daload|dneg|fastore|istore|lshl|ldiv|lstore|areturn|ishr|ldc_w|invokeinterface|aastore|lxor|ishl|l2d|i2f|return|faload|sipush|iushr|caload|instanceof|invokespecial|putfield|fmul|ireturn|laload|d2f|lneg|ixor|i2l|fdiv|lastore|multianewarray|i2b|getstatic|i2d|putstatic|fcmpl|saload|ladd|irem|dload|jsr_w|dconst|dcmpl|fsub|freturn|ldc|aconst_null|castore|lmul|ldc2_w|dadd|iconst|f2l|ddiv|dstore|land|jsr|anewarray|dmul|bipush|dsub|sastore|d2i|i2s|lshr|iadd|l2i|lload|bastore|fstore|fneg|iload|fadd|baload|fconst|ior|ineg|dreturn|l2f|lconst|getfield|invokevirtual|invokestatic|iastore)");
/* 183:340 */     map.put("popinstruction", "(fstore|dstore|pop|pop2|astore|putstatic|istore|lstore)");
/* 184:341 */     map.put("allocationinstruction", "(multianewarray|new|anewarray|newarray)");
/* 185:342 */     map.put("indexedinstruction", "(lload|lstore|fload|ldc2_w|invokeinterface|multianewarray|astore|dload|putstatic|instanceof|getstatic|checkcast|getfield|invokespecial|dstore|istore|iinc|ldc_w|ret|fstore|invokestatic|iload|putfield|invokevirtual|ldc|new|aload|anewarray)");
/* 186:343 */     map.put("pushinstruction", "(dup|lload|dup2|bipush|fload|ldc2_w|sipush|lconst|fconst|dload|getstatic|ldc_w|aconst_null|dconst|iload|ldc|iconst|aload)");
/* 187:344 */     map.put("stackproducer", "(imul|lsub|aload|fload|lor|new|aaload|fcmpg|iand|iaload|lrem|idiv|d2l|isub|dcmpg|dup|f2d|f2i|drem|i2c|checkcast|frem|lushr|daload|dneg|lshl|ldiv|ishr|ldc_w|invokeinterface|lxor|ishl|l2d|i2f|faload|sipush|iushr|caload|instanceof|invokespecial|fmul|laload|d2f|lneg|ixor|i2l|fdiv|getstatic|i2b|swap|i2d|dup2|fcmpl|saload|ladd|irem|dload|jsr_w|dconst|dcmpl|fsub|ldc|arraylength|aconst_null|tableswitch|lmul|ldc2_w|iconst|dadd|f2l|ddiv|land|jsr|anewarray|dmul|bipush|dsub|d2i|newarray|i2s|lshr|iadd|lload|l2i|fneg|iload|fadd|baload|fconst|lookupswitch|ior|ineg|lconst|l2f|getfield|invokevirtual|invokestatic)");
/* 188:345 */     map.put("stackconsumer", "(imul|lsub|lor|iflt|fcmpg|if_icmpgt|iand|ifeq|if_icmplt|lrem|ifnonnull|idiv|d2l|isub|dcmpg|dastore|if_icmpeq|f2d|f2i|drem|i2c|checkcast|frem|lreturn|astore|lushr|pop2|monitorexit|dneg|fastore|istore|lshl|ldiv|lstore|areturn|if_icmpge|ishr|monitorenter|invokeinterface|aastore|lxor|ishl|l2d|i2f|return|iushr|instanceof|invokespecial|fmul|ireturn|d2f|lneg|ixor|pop|i2l|ifnull|fdiv|lastore|i2b|if_acmpeq|ifge|swap|i2d|putstatic|fcmpl|ladd|irem|dcmpl|fsub|freturn|ifgt|castore|lmul|dadd|f2l|ddiv|dstore|land|if_icmpne|if_acmpne|dmul|dsub|sastore|ifle|d2i|i2s|lshr|iadd|l2i|bastore|fstore|fneg|fadd|ior|ineg|ifne|dreturn|l2f|if_icmple|getfield|invokevirtual|invokestatic|iastore)");
/* 189:346 */     map.put("exceptionthrower", "(irem|lrem|laload|putstatic|baload|dastore|areturn|getstatic|ldiv|anewarray|iastore|castore|idiv|saload|lastore|fastore|putfield|lreturn|caload|getfield|return|aastore|freturn|newarray|instanceof|multianewarray|athrow|faload|iaload|aaload|dreturn|monitorenter|checkcast|bastore|arraylength|new|invokevirtual|sastore|ldc_w|ireturn|invokespecial|monitorexit|invokeinterface|ldc|invokestatic|daload)");
/* 190:347 */     map.put("loadclass", "(multianewarray|invokeinterface|instanceof|invokespecial|putfield|checkcast|putstatic|invokevirtual|new|getstatic|invokestatic|getfield|anewarray)");
/* 191:348 */     map.put("instructiontargeter", "(ifle|if_acmpne|if_icmpeq|if_acmpeq|ifnonnull|goto_w|iflt|ifnull|if_icmpne|tableswitch|if_icmple|ifeq|if_icmplt|jsr_w|if_icmpgt|ifgt|jsr|goto|ifne|ifge|lookupswitch|if_icmpge)");
/* 192:    */     
/* 193:    */ 
/* 194:351 */     map.put("if_icmp", "(if_icmpne|if_icmpeq|if_icmple|if_icmpge|if_icmplt|if_icmpgt)");
/* 195:352 */     map.put("if_acmp", "(if_acmpeq|if_acmpne)");
/* 196:353 */     map.put("if", "(ifeq|ifne|iflt|ifge|ifgt|ifle)");
/* 197:    */     
/* 198:    */ 
/* 199:356 */     map.put("iconst", precompile((short)3, (short)8, (short)2));
/* 200:357 */     map.put("lconst", new String(new char[] { '(', makeChar(9), '|', makeChar(10), ')' }));
/* 201:    */     
/* 202:359 */     map.put("dconst", new String(new char[] { '(', makeChar(14), '|', makeChar(15), ')' }));
/* 203:    */     
/* 204:361 */     map.put("fconst", new String(new char[] { '(', makeChar(11), '|', makeChar(12), ')' }));
/* 205:    */     
/* 206:    */ 
/* 207:364 */     map.put("iload", precompile((short)26, (short)29, (short)21));
/* 208:365 */     map.put("dload", precompile((short)38, (short)41, (short)24));
/* 209:366 */     map.put("fload", precompile((short)34, (short)37, (short)23));
/* 210:367 */     map.put("aload", precompile((short)42, (short)45, (short)25));
/* 211:    */     
/* 212:369 */     map.put("istore", precompile((short)59, (short)62, (short)54));
/* 213:370 */     map.put("dstore", precompile((short)71, (short)74, (short)57));
/* 214:371 */     map.put("fstore", precompile((short)67, (short)70, (short)56));
/* 215:372 */     map.put("astore", precompile((short)75, (short)78, (short)58));
/* 216:376 */     for (Iterator i = map.keySet().iterator(); i.hasNext();)
/* 217:    */     {
/* 218:377 */       String key = (String)i.next();
/* 219:378 */       String value = (String)map.get(key);
/* 220:    */       
/* 221:380 */       char ch = value.charAt(1);
/* 222:381 */       if (ch < '翿') {
/* 223:382 */         map.put(key, compilePattern(value));
/* 224:    */       }
/* 225:    */     }
/* 226:388 */     StringBuffer buf = new StringBuffer("(");
/* 227:390 */     for (short i = 0; i < 256; i = (short)(i + 1)) {
/* 228:391 */       if (org.apache.bcel.Constants.NO_OF_OPERANDS[i] != -1)
/* 229:    */       {
/* 230:392 */         buf.append(makeChar(i));
/* 231:394 */         if (i < 255) {
/* 232:395 */           buf.append('|');
/* 233:    */         }
/* 234:    */       }
/* 235:    */     }
/* 236:398 */     buf.append(')');
/* 237:    */     
/* 238:400 */     map.put("instruction", buf.toString());
/* 239:    */   }
/* 240:    */   
/* 241:    */   private static String precompile(short from, short to, short extra)
/* 242:    */   {
/* 243:404 */     StringBuffer buf = new StringBuffer("(");
/* 244:406 */     for (short i = from; i <= to; i = (short)(i + 1))
/* 245:    */     {
/* 246:407 */       buf.append(makeChar(i));
/* 247:408 */       buf.append('|');
/* 248:    */     }
/* 249:411 */     buf.append(makeChar(extra));
/* 250:412 */     buf.append(")");
/* 251:413 */     return buf.toString();
/* 252:    */   }
/* 253:    */   
/* 254:    */   private static final String pattern2string(String pattern)
/* 255:    */   {
/* 256:420 */     return pattern2string(pattern, true);
/* 257:    */   }
/* 258:    */   
/* 259:    */   private static final String pattern2string(String pattern, boolean make_string)
/* 260:    */   {
/* 261:424 */     StringBuffer buf = new StringBuffer();
/* 262:426 */     for (int i = 0; i < pattern.length(); i++)
/* 263:    */     {
/* 264:427 */       char ch = pattern.charAt(i);
/* 265:429 */       if (ch >= '翿')
/* 266:    */       {
/* 267:430 */         if (make_string) {
/* 268:431 */           buf.append(org.apache.bcel.Constants.OPCODE_NAMES[(ch - '翿')]);
/* 269:    */         } else {
/* 270:433 */           buf.append(ch - '翿');
/* 271:    */         }
/* 272:    */       }
/* 273:    */       else {
/* 274:435 */         buf.append(ch);
/* 275:    */       }
/* 276:    */     }
/* 277:438 */     return buf.toString();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static abstract interface CodeConstraint
/* 281:    */   {
/* 282:    */     public abstract boolean checkCode(InstructionHandle[] paramArrayOfInstructionHandle);
/* 283:    */   }
/* 284:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.InstructionFinder
 * JD-Core Version:    0.7.0.1
 */