/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.bytecode.BadBytecode;
/*   6:    */ import javassist.bytecode.CodeAttribute;
/*   7:    */ import javassist.bytecode.CodeIterator;
/*   8:    */ import javassist.bytecode.ConstPool;
/*   9:    */ import javassist.bytecode.ExceptionTable;
/*  10:    */ import javassist.bytecode.MethodInfo;
/*  11:    */ 
/*  12:    */ public class ExprEditor
/*  13:    */ {
/*  14:    */   public boolean doit(CtClass clazz, MethodInfo minfo)
/*  15:    */     throws CannotCompileException
/*  16:    */   {
/*  17: 81 */     CodeAttribute codeAttr = minfo.getCodeAttribute();
/*  18: 82 */     if (codeAttr == null) {
/*  19: 83 */       return false;
/*  20:    */     }
/*  21: 85 */     CodeIterator iterator = codeAttr.iterator();
/*  22: 86 */     boolean edited = false;
/*  23: 87 */     LoopContext context = new LoopContext(codeAttr.getMaxLocals());
/*  24: 89 */     while (iterator.hasNext()) {
/*  25: 90 */       if (loopBody(iterator, clazz, minfo, context)) {
/*  26: 91 */         edited = true;
/*  27:    */       }
/*  28:    */     }
/*  29: 93 */     ExceptionTable et = codeAttr.getExceptionTable();
/*  30: 94 */     int n = et.size();
/*  31: 95 */     for (int i = 0; i < n; i++)
/*  32:    */     {
/*  33: 96 */       Handler h = new Handler(et, i, iterator, clazz, minfo);
/*  34: 97 */       edit(h);
/*  35: 98 */       if (h.edited())
/*  36:    */       {
/*  37: 99 */         edited = true;
/*  38:100 */         context.updateMax(h.locals(), h.stack());
/*  39:    */       }
/*  40:    */     }
/*  41:106 */     if (codeAttr.getMaxLocals() < context.maxLocals) {
/*  42:107 */       codeAttr.setMaxLocals(context.maxLocals);
/*  43:    */     }
/*  44:109 */     codeAttr.setMaxStack(codeAttr.getMaxStack() + context.maxStack);
/*  45:    */     try
/*  46:    */     {
/*  47:111 */       if (edited) {
/*  48:112 */         minfo.rebuildStackMapIf6(clazz.getClassPool(), clazz.getClassFile2());
/*  49:    */       }
/*  50:    */     }
/*  51:    */     catch (BadBytecode b)
/*  52:    */     {
/*  53:116 */       throw new CannotCompileException(b.getMessage(), b);
/*  54:    */     }
/*  55:119 */     return edited;
/*  56:    */   }
/*  57:    */   
/*  58:    */   boolean doit(CtClass clazz, MethodInfo minfo, LoopContext context, CodeIterator iterator, int endPos)
/*  59:    */     throws CannotCompileException
/*  60:    */   {
/*  61:129 */     boolean edited = false;
/*  62:130 */     while ((iterator.hasNext()) && (iterator.lookAhead() < endPos))
/*  63:    */     {
/*  64:131 */       int size = iterator.getCodeLength();
/*  65:132 */       if (loopBody(iterator, clazz, minfo, context))
/*  66:    */       {
/*  67:133 */         edited = true;
/*  68:134 */         int size2 = iterator.getCodeLength();
/*  69:135 */         if (size != size2) {
/*  70:136 */           endPos += size2 - size;
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:140 */     return edited;
/*  75:    */   }
/*  76:    */   
/*  77:    */   static final class NewOp
/*  78:    */   {
/*  79:    */     NewOp next;
/*  80:    */     int pos;
/*  81:    */     String type;
/*  82:    */     
/*  83:    */     NewOp(NewOp n, int p, String t)
/*  84:    */     {
/*  85:149 */       this.next = n;
/*  86:150 */       this.pos = p;
/*  87:151 */       this.type = t;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   static final class LoopContext
/*  92:    */   {
/*  93:    */     ExprEditor.NewOp newList;
/*  94:    */     int maxLocals;
/*  95:    */     int maxStack;
/*  96:    */     
/*  97:    */     LoopContext(int locals)
/*  98:    */     {
/*  99:161 */       this.maxLocals = locals;
/* 100:162 */       this.maxStack = 0;
/* 101:163 */       this.newList = null;
/* 102:    */     }
/* 103:    */     
/* 104:    */     void updateMax(int locals, int stack)
/* 105:    */     {
/* 106:167 */       if (this.maxLocals < locals) {
/* 107:168 */         this.maxLocals = locals;
/* 108:    */       }
/* 109:170 */       if (this.maxStack < stack) {
/* 110:171 */         this.maxStack = stack;
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   final boolean loopBody(CodeIterator iterator, CtClass clazz, MethodInfo minfo, LoopContext context)
/* 116:    */     throws CannotCompileException
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:180 */       Expr expr = null;
/* 121:181 */       int pos = iterator.next();
/* 122:182 */       int c = iterator.byteAt(pos);
/* 123:184 */       if (c >= 178) {
/* 124:186 */         if (c < 188)
/* 125:    */         {
/* 126:187 */           if ((c == 184) || (c == 185) || (c == 182))
/* 127:    */           {
/* 128:190 */             expr = new MethodCall(pos, iterator, clazz, minfo);
/* 129:191 */             edit((MethodCall)expr);
/* 130:    */           }
/* 131:193 */           else if ((c == 180) || (c == 178) || (c == 181) || (c == 179))
/* 132:    */           {
/* 133:196 */             expr = new FieldAccess(pos, iterator, clazz, minfo, c);
/* 134:197 */             edit((FieldAccess)expr);
/* 135:    */           }
/* 136:199 */           else if (c == 187)
/* 137:    */           {
/* 138:200 */             int index = iterator.u16bitAt(pos + 1);
/* 139:201 */             context.newList = new NewOp(context.newList, pos, minfo.getConstPool().getClassInfo(index));
/* 140:    */           }
/* 141:204 */           else if (c == 183)
/* 142:    */           {
/* 143:205 */             NewOp newList = context.newList;
/* 144:206 */             if ((newList != null) && (minfo.getConstPool().isConstructor(newList.type, iterator.u16bitAt(pos + 1)) > 0))
/* 145:    */             {
/* 146:209 */               expr = new NewExpr(pos, iterator, clazz, minfo, newList.type, newList.pos);
/* 147:    */               
/* 148:211 */               edit((NewExpr)expr);
/* 149:212 */               context.newList = newList.next;
/* 150:    */             }
/* 151:    */             else
/* 152:    */             {
/* 153:215 */               MethodCall mcall = new MethodCall(pos, iterator, clazz, minfo);
/* 154:216 */               if (mcall.getMethodName().equals("<init>"))
/* 155:    */               {
/* 156:217 */                 ConstructorCall ccall = new ConstructorCall(pos, iterator, clazz, minfo);
/* 157:218 */                 expr = ccall;
/* 158:219 */                 edit(ccall);
/* 159:    */               }
/* 160:    */               else
/* 161:    */               {
/* 162:222 */                 expr = mcall;
/* 163:223 */                 edit(mcall);
/* 164:    */               }
/* 165:    */             }
/* 166:    */           }
/* 167:    */         }
/* 168:229 */         else if ((c == 188) || (c == 189) || (c == 197))
/* 169:    */         {
/* 170:231 */           expr = new NewArray(pos, iterator, clazz, minfo, c);
/* 171:232 */           edit((NewArray)expr);
/* 172:    */         }
/* 173:234 */         else if (c == 193)
/* 174:    */         {
/* 175:235 */           expr = new Instanceof(pos, iterator, clazz, minfo);
/* 176:236 */           edit((Instanceof)expr);
/* 177:    */         }
/* 178:238 */         else if (c == 192)
/* 179:    */         {
/* 180:239 */           expr = new Cast(pos, iterator, clazz, minfo);
/* 181:240 */           edit((Cast)expr);
/* 182:    */         }
/* 183:    */       }
/* 184:244 */       if ((expr != null) && (expr.edited()))
/* 185:    */       {
/* 186:245 */         context.updateMax(expr.locals(), expr.stack());
/* 187:246 */         return true;
/* 188:    */       }
/* 189:249 */       return false;
/* 190:    */     }
/* 191:    */     catch (BadBytecode e)
/* 192:    */     {
/* 193:252 */       throw new CannotCompileException(e);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void edit(NewExpr e)
/* 198:    */     throws CannotCompileException
/* 199:    */   {}
/* 200:    */   
/* 201:    */   public void edit(NewArray a)
/* 202:    */     throws CannotCompileException
/* 203:    */   {}
/* 204:    */   
/* 205:    */   public void edit(MethodCall m)
/* 206:    */     throws CannotCompileException
/* 207:    */   {}
/* 208:    */   
/* 209:    */   public void edit(ConstructorCall c)
/* 210:    */     throws CannotCompileException
/* 211:    */   {}
/* 212:    */   
/* 213:    */   public void edit(FieldAccess f)
/* 214:    */     throws CannotCompileException
/* 215:    */   {}
/* 216:    */   
/* 217:    */   public void edit(Instanceof i)
/* 218:    */     throws CannotCompileException
/* 219:    */   {}
/* 220:    */   
/* 221:    */   public void edit(Cast c)
/* 222:    */     throws CannotCompileException
/* 223:    */   {}
/* 224:    */   
/* 225:    */   public void edit(Handler h)
/* 226:    */     throws CannotCompileException
/* 227:    */   {}
/* 228:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.ExprEditor
 * JD-Core Version:    0.7.0.1
 */