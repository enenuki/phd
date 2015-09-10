/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Random;
/*   7:    */ import java.util.Vector;
/*   8:    */ import org.apache.bcel.Repository;
/*   9:    */ import org.apache.bcel.classfile.AccessFlags;
/*  10:    */ import org.apache.bcel.classfile.JavaClass;
/*  11:    */ import org.apache.bcel.classfile.Method;
/*  12:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  13:    */ import org.apache.bcel.generic.FieldGenOrMethodGen;
/*  14:    */ import org.apache.bcel.generic.InstructionHandle;
/*  15:    */ import org.apache.bcel.generic.InstructionList;
/*  16:    */ import org.apache.bcel.generic.JsrInstruction;
/*  17:    */ import org.apache.bcel.generic.MethodGen;
/*  18:    */ import org.apache.bcel.generic.ObjectType;
/*  19:    */ import org.apache.bcel.generic.RET;
/*  20:    */ import org.apache.bcel.generic.ReturnInstruction;
/*  21:    */ import org.apache.bcel.generic.ReturnaddressType;
/*  22:    */ import org.apache.bcel.generic.Type;
/*  23:    */ import org.apache.bcel.verifier.PassVerifier;
/*  24:    */ import org.apache.bcel.verifier.VerificationResult;
/*  25:    */ import org.apache.bcel.verifier.Verifier;
/*  26:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*  27:    */ import org.apache.bcel.verifier.exc.VerifierConstraintViolatedException;
/*  28:    */ 
/*  29:    */ public final class Pass3bVerifier
/*  30:    */   extends PassVerifier
/*  31:    */ {
/*  32:    */   private static final boolean DEBUG = true;
/*  33:    */   private Verifier myOwner;
/*  34:    */   private int method_no;
/*  35:    */   
/*  36:    */   private static final class InstructionContextQueue
/*  37:    */   {
/*  38:    */     InstructionContextQueue(Pass3bVerifier.1 x0)
/*  39:    */     {
/*  40:100 */       this();
/*  41:    */     }
/*  42:    */     
/*  43:101 */     private Vector ics = new Vector();
/*  44:102 */     private Vector ecs = new Vector();
/*  45:    */     
/*  46:    */     public void add(InstructionContext ic, ArrayList executionChain)
/*  47:    */     {
/*  48:104 */       this.ics.add(ic);
/*  49:105 */       this.ecs.add(executionChain);
/*  50:    */     }
/*  51:    */     
/*  52:    */     public boolean isEmpty()
/*  53:    */     {
/*  54:108 */       return this.ics.isEmpty();
/*  55:    */     }
/*  56:    */     
/*  57:    */     public void remove()
/*  58:    */     {
/*  59:111 */       remove(0);
/*  60:    */     }
/*  61:    */     
/*  62:    */     public void remove(int i)
/*  63:    */     {
/*  64:114 */       this.ics.remove(i);
/*  65:115 */       this.ecs.remove(i);
/*  66:    */     }
/*  67:    */     
/*  68:    */     public InstructionContext getIC(int i)
/*  69:    */     {
/*  70:118 */       return (InstructionContext)this.ics.get(i);
/*  71:    */     }
/*  72:    */     
/*  73:    */     public ArrayList getEC(int i)
/*  74:    */     {
/*  75:121 */       return (ArrayList)this.ecs.get(i);
/*  76:    */     }
/*  77:    */     
/*  78:    */     public int size()
/*  79:    */     {
/*  80:124 */       return this.ics.size();
/*  81:    */     }
/*  82:    */     
/*  83:    */     private InstructionContextQueue() {}
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Pass3bVerifier(Verifier owner, int method_no)
/*  87:    */   {
/*  88:143 */     this.myOwner = owner;
/*  89:144 */     this.method_no = method_no;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void circulationPump(ControlFlowGraph cfg, InstructionContext start, Frame vanillaFrame, InstConstraintVisitor icv, ExecutionVisitor ev)
/*  93:    */   {
/*  94:155 */     Random random = new Random();
/*  95:156 */     InstructionContextQueue icq = new InstructionContextQueue(null);
/*  96:    */     
/*  97:158 */     start.execute(vanillaFrame, new ArrayList(), icv, ev);
/*  98:    */     
/*  99:160 */     icq.add(start, new ArrayList());
/* 100:    */     ExceptionHandler[] exc_hds;
/* 101:    */     int s;
/* 102:163 */     for (; !icq.isEmpty(); s < exc_hds.length)
/* 103:    */     {
/* 104:173 */       InstructionContext u = icq.getIC(0);
/* 105:174 */       ArrayList ec = icq.getEC(0);
/* 106:175 */       icq.remove(0);
/* 107:    */       
/* 108:    */ 
/* 109:178 */       ArrayList oldchain = (ArrayList)ec.clone();
/* 110:179 */       ArrayList newchain = (ArrayList)ec.clone();
/* 111:180 */       newchain.add(u);
/* 112:182 */       if ((u.getInstruction().getInstruction() instanceof RET))
/* 113:    */       {
/* 114:186 */         RET ret = (RET)u.getInstruction().getInstruction();
/* 115:187 */         ReturnaddressType t = (ReturnaddressType)u.getOutFrame(oldchain).getLocals().get(ret.getIndex());
/* 116:188 */         InstructionContext theSuccessor = cfg.contextOf(t.getTarget());
/* 117:    */         
/* 118:    */ 
/* 119:191 */         InstructionContext lastJSR = null;
/* 120:192 */         int skip_jsr = 0;
/* 121:193 */         for (int ss = oldchain.size() - 1; ss >= 0; ss--)
/* 122:    */         {
/* 123:194 */           if (skip_jsr < 0) {
/* 124:195 */             throw new AssertionViolatedException("More RET than JSR in execution chain?!");
/* 125:    */           }
/* 126:198 */           if ((((InstructionContext)oldchain.get(ss)).getInstruction().getInstruction() instanceof JsrInstruction))
/* 127:    */           {
/* 128:199 */             if (skip_jsr == 0)
/* 129:    */             {
/* 130:200 */               lastJSR = (InstructionContext)oldchain.get(ss);
/* 131:201 */               break;
/* 132:    */             }
/* 133:204 */             skip_jsr--;
/* 134:    */           }
/* 135:207 */           if ((((InstructionContext)oldchain.get(ss)).getInstruction().getInstruction() instanceof RET)) {
/* 136:208 */             skip_jsr++;
/* 137:    */           }
/* 138:    */         }
/* 139:211 */         if (lastJSR == null) {
/* 140:212 */           throw new AssertionViolatedException("RET without a JSR before in ExecutionChain?! EC: '" + oldchain + "'.");
/* 141:    */         }
/* 142:214 */         JsrInstruction jsr = (JsrInstruction)lastJSR.getInstruction().getInstruction();
/* 143:215 */         if (theSuccessor != cfg.contextOf(jsr.physicalSuccessor())) {
/* 144:216 */           throw new AssertionViolatedException("RET '" + u.getInstruction() + "' info inconsistent: jump back to '" + theSuccessor + "' or '" + cfg.contextOf(jsr.physicalSuccessor()) + "'?");
/* 145:    */         }
/* 146:219 */         if (theSuccessor.execute(u.getOutFrame(oldchain), newchain, icv, ev)) {
/* 147:220 */           icq.add(theSuccessor, (ArrayList)newchain.clone());
/* 148:    */         }
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:226 */         InstructionContext[] succs = u.getSuccessors();
/* 153:227 */         for (int s = 0; s < succs.length; s++)
/* 154:    */         {
/* 155:228 */           InstructionContext v = succs[s];
/* 156:229 */           if (v.execute(u.getOutFrame(oldchain), newchain, icv, ev)) {
/* 157:230 */             icq.add(v, (ArrayList)newchain.clone());
/* 158:    */           }
/* 159:    */         }
/* 160:    */       }
/* 161:237 */       exc_hds = u.getExceptionHandlers();
/* 162:238 */       s = 0; continue;
/* 163:239 */       InstructionContext v = cfg.contextOf(exc_hds[s].getHandlerStart());
/* 164:250 */       if (v.execute(new Frame(u.getOutFrame(oldchain).getLocals(), new OperandStack(u.getOutFrame(oldchain).getStack().maxStack(), exc_hds[s].getExceptionType() == null ? Type.THROWABLE : exc_hds[s].getExceptionType())), new ArrayList(), icv, ev)) {
/* 165:251 */         icq.add(v, new ArrayList());
/* 166:    */       }
/* 167:238 */       s++;
/* 168:    */     }
/* 169:257 */     InstructionHandle ih = start.getInstruction();
/* 170:    */     do
/* 171:    */     {
/* 172:259 */       if (((ih.getInstruction() instanceof ReturnInstruction)) && (!cfg.isDead(ih)))
/* 173:    */       {
/* 174:260 */         InstructionContext ic = cfg.contextOf(ih);
/* 175:261 */         Frame f = ic.getOutFrame(new ArrayList());
/* 176:262 */         LocalVariables lvs = f.getLocals();
/* 177:263 */         for (int i = 0; i < lvs.maxLocals(); i++) {
/* 178:264 */           if ((lvs.get(i) instanceof UninitializedObjectType)) {
/* 179:265 */             addMessage("Warning: ReturnInstruction '" + ic + "' may leave method with an uninitialized object in the local variables array '" + lvs + "'.");
/* 180:    */           }
/* 181:    */         }
/* 182:268 */         OperandStack os = f.getStack();
/* 183:269 */         for (int i = 0; i < os.size(); i++) {
/* 184:270 */           if ((os.peek(i) instanceof UninitializedObjectType)) {
/* 185:271 */             addMessage("Warning: ReturnInstruction '" + ic + "' may leave method with an uninitialized object on the operand stack '" + os + "'.");
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:275 */     } while ((ih = ih.getNext()) != null);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public VerificationResult do_verify()
/* 193:    */   {
/* 194:290 */     if (!this.myOwner.doPass3a(this.method_no).equals(VerificationResult.VR_OK)) {
/* 195:291 */       return VerificationResult.VR_NOTYET;
/* 196:    */     }
/* 197:296 */     JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/* 198:    */     
/* 199:298 */     ConstantPoolGen constantPoolGen = new ConstantPoolGen(jc.getConstantPool());
/* 200:    */     
/* 201:300 */     InstConstraintVisitor icv = new InstConstraintVisitor();
/* 202:301 */     icv.setConstantPoolGen(constantPoolGen);
/* 203:    */     
/* 204:303 */     ExecutionVisitor ev = new ExecutionVisitor();
/* 205:304 */     ev.setConstantPoolGen(constantPoolGen);
/* 206:    */     
/* 207:306 */     Method[] methods = jc.getMethods();
/* 208:    */     try
/* 209:    */     {
/* 210:310 */       MethodGen mg = new MethodGen(methods[this.method_no], this.myOwner.getClassName(), constantPoolGen);
/* 211:    */       
/* 212:312 */       icv.setMethodGen(mg);
/* 213:315 */       if ((!mg.isAbstract()) && (!mg.isNative()))
/* 214:    */       {
/* 215:317 */         ControlFlowGraph cfg = new ControlFlowGraph(mg);
/* 216:    */         
/* 217:    */ 
/* 218:320 */         Frame f = new Frame(mg.getMaxLocals(), mg.getMaxStack());
/* 219:321 */         if (!mg.isStatic()) {
/* 220:322 */           if (mg.getName().equals("<init>"))
/* 221:    */           {
/* 222:323 */             Frame._this = new UninitializedObjectType(new ObjectType(jc.getClassName()));
/* 223:324 */             f.getLocals().set(0, Frame._this);
/* 224:    */           }
/* 225:    */           else
/* 226:    */           {
/* 227:327 */             Frame._this = null;
/* 228:328 */             f.getLocals().set(0, new ObjectType(jc.getClassName()));
/* 229:    */           }
/* 230:    */         }
/* 231:331 */         Type[] argtypes = mg.getArgumentTypes();
/* 232:332 */         int twoslotoffset = 0;
/* 233:333 */         for (int j = 0; j < argtypes.length; j++)
/* 234:    */         {
/* 235:334 */           if ((argtypes[j] == Type.SHORT) || (argtypes[j] == Type.BYTE) || (argtypes[j] == Type.CHAR) || (argtypes[j] == Type.BOOLEAN)) {
/* 236:335 */             argtypes[j] = Type.INT;
/* 237:    */           }
/* 238:337 */           f.getLocals().set(twoslotoffset + j + (mg.isStatic() ? 0 : 1), argtypes[j]);
/* 239:338 */           if (argtypes[j].getSize() == 2)
/* 240:    */           {
/* 241:339 */             twoslotoffset++;
/* 242:340 */             f.getLocals().set(twoslotoffset + j + (mg.isStatic() ? 0 : 1), Type.UNKNOWN);
/* 243:    */           }
/* 244:    */         }
/* 245:343 */         circulationPump(cfg, cfg.contextOf(mg.getInstructionList().getStart()), f, icv, ev);
/* 246:    */       }
/* 247:    */     }
/* 248:    */     catch (VerifierConstraintViolatedException ce)
/* 249:    */     {
/* 250:347 */       ce.extendMessage("Constraint violated in method '" + methods[this.method_no] + "':\n", "");
/* 251:348 */       return new VerificationResult(2, ce.getMessage());
/* 252:    */     }
/* 253:    */     catch (RuntimeException re)
/* 254:    */     {
/* 255:353 */       StringWriter sw = new StringWriter();
/* 256:354 */       PrintWriter pw = new PrintWriter(sw);
/* 257:355 */       re.printStackTrace(pw);
/* 258:    */       
/* 259:357 */       throw new AssertionViolatedException("Some RuntimeException occured while verify()ing class '" + jc.getClassName() + "', method '" + methods[this.method_no] + "'. Original RuntimeException's stack trace:\n---\n" + sw + "---\n");
/* 260:    */     }
/* 261:359 */     return VerificationResult.VR_OK;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int getMethodNo()
/* 265:    */   {
/* 266:364 */     return this.method_no;
/* 267:    */   }
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.Pass3bVerifier
 * JD-Core Version:    0.7.0.1
 */