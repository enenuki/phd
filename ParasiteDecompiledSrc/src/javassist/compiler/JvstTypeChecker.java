/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.CtPrimitiveType;
/*   6:    */ import javassist.NotFoundException;
/*   7:    */ import javassist.compiler.ast.ASTList;
/*   8:    */ import javassist.compiler.ast.ASTree;
/*   9:    */ import javassist.compiler.ast.CallExpr;
/*  10:    */ import javassist.compiler.ast.CastExpr;
/*  11:    */ import javassist.compiler.ast.Expr;
/*  12:    */ import javassist.compiler.ast.Member;
/*  13:    */ import javassist.compiler.ast.Symbol;
/*  14:    */ 
/*  15:    */ public class JvstTypeChecker
/*  16:    */   extends TypeChecker
/*  17:    */ {
/*  18:    */   private JvstCodeGen codeGen;
/*  19:    */   
/*  20:    */   public JvstTypeChecker(CtClass cc, ClassPool cp, JvstCodeGen gen)
/*  21:    */   {
/*  22: 28 */     super(cc, cp);
/*  23: 29 */     this.codeGen = gen;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void addNullIfVoid()
/*  27:    */   {
/*  28: 36 */     if (this.exprType == 344)
/*  29:    */     {
/*  30: 37 */       this.exprType = 307;
/*  31: 38 */       this.arrayDim = 0;
/*  32: 39 */       this.className = "java/lang/Object";
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void atMember(Member mem)
/*  37:    */     throws CompileError
/*  38:    */   {
/*  39: 47 */     String name = mem.get();
/*  40: 48 */     if (name.equals(this.codeGen.paramArrayName))
/*  41:    */     {
/*  42: 49 */       this.exprType = 307;
/*  43: 50 */       this.arrayDim = 1;
/*  44: 51 */       this.className = "java/lang/Object";
/*  45:    */     }
/*  46: 53 */     else if (name.equals("$sig"))
/*  47:    */     {
/*  48: 54 */       this.exprType = 307;
/*  49: 55 */       this.arrayDim = 1;
/*  50: 56 */       this.className = "java/lang/Class";
/*  51:    */     }
/*  52: 58 */     else if ((name.equals("$type")) || (name.equals("$class")))
/*  53:    */     {
/*  54: 60 */       this.exprType = 307;
/*  55: 61 */       this.arrayDim = 0;
/*  56: 62 */       this.className = "java/lang/Class";
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 65 */       super.atMember(mem);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void atFieldAssign(Expr expr, int op, ASTree left, ASTree right)
/*  65:    */     throws CompileError
/*  66:    */   {
/*  67: 71 */     if (((left instanceof Member)) && (((Member)left).get().equals(this.codeGen.paramArrayName)))
/*  68:    */     {
/*  69: 73 */       right.accept(this);
/*  70: 74 */       CtClass[] params = this.codeGen.paramTypeList;
/*  71: 75 */       if (params == null) {
/*  72: 76 */         return;
/*  73:    */       }
/*  74: 78 */       int n = params.length;
/*  75: 79 */       for (int i = 0; i < n; i++) {
/*  76: 80 */         compileUnwrapValue(params[i]);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81: 83 */       super.atFieldAssign(expr, op, left, right);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void atCastExpr(CastExpr expr)
/*  86:    */     throws CompileError
/*  87:    */   {
/*  88: 87 */     ASTList classname = expr.getClassName();
/*  89: 88 */     if ((classname != null) && (expr.getArrayDim() == 0))
/*  90:    */     {
/*  91: 89 */       ASTree p = classname.head();
/*  92: 90 */       if (((p instanceof Symbol)) && (classname.tail() == null))
/*  93:    */       {
/*  94: 91 */         String typename = ((Symbol)p).get();
/*  95: 92 */         if (typename.equals(this.codeGen.returnCastName))
/*  96:    */         {
/*  97: 93 */           atCastToRtype(expr);
/*  98: 94 */           return;
/*  99:    */         }
/* 100: 96 */         if (typename.equals("$w"))
/* 101:    */         {
/* 102: 97 */           atCastToWrapper(expr);
/* 103: 98 */           return;
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:103 */     super.atCastExpr(expr);
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void atCastToRtype(CastExpr expr)
/* 111:    */     throws CompileError
/* 112:    */   {
/* 113:111 */     CtClass returnType = this.codeGen.returnType;
/* 114:112 */     expr.getOprand().accept(this);
/* 115:113 */     if ((this.exprType == 344) || (CodeGen.isRefType(this.exprType)) || (this.arrayDim > 0))
/* 116:    */     {
/* 117:114 */       compileUnwrapValue(returnType);
/* 118:    */     }
/* 119:115 */     else if ((returnType instanceof CtPrimitiveType))
/* 120:    */     {
/* 121:116 */       CtPrimitiveType pt = (CtPrimitiveType)returnType;
/* 122:117 */       int destType = MemberResolver.descToType(pt.getDescriptor());
/* 123:118 */       this.exprType = destType;
/* 124:119 */       this.arrayDim = 0;
/* 125:120 */       this.className = null;
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void atCastToWrapper(CastExpr expr)
/* 130:    */     throws CompileError
/* 131:    */   {
/* 132:125 */     expr.getOprand().accept(this);
/* 133:126 */     if ((CodeGen.isRefType(this.exprType)) || (this.arrayDim > 0)) {
/* 134:127 */       return;
/* 135:    */     }
/* 136:129 */     CtClass clazz = this.resolver.lookupClass(this.exprType, this.arrayDim, this.className);
/* 137:130 */     if ((clazz instanceof CtPrimitiveType))
/* 138:    */     {
/* 139:131 */       this.exprType = 307;
/* 140:132 */       this.arrayDim = 0;
/* 141:133 */       this.className = "java/lang/Object";
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void atCallExpr(CallExpr expr)
/* 146:    */     throws CompileError
/* 147:    */   {
/* 148:141 */     ASTree method = expr.oprand1();
/* 149:142 */     if ((method instanceof Member))
/* 150:    */     {
/* 151:143 */       String name = ((Member)method).get();
/* 152:144 */       if ((this.codeGen.procHandler != null) && (name.equals(this.codeGen.proceedName)))
/* 153:    */       {
/* 154:146 */         this.codeGen.procHandler.setReturnType(this, (ASTList)expr.oprand2());
/* 155:    */         
/* 156:148 */         return;
/* 157:    */       }
/* 158:150 */       if (name.equals("$cflow"))
/* 159:    */       {
/* 160:151 */         atCflow((ASTList)expr.oprand2());
/* 161:152 */         return;
/* 162:    */       }
/* 163:    */     }
/* 164:156 */     super.atCallExpr(expr);
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected void atCflow(ASTList cname)
/* 168:    */     throws CompileError
/* 169:    */   {
/* 170:162 */     this.exprType = 324;
/* 171:163 */     this.arrayDim = 0;
/* 172:164 */     this.className = null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isParamListName(ASTList args)
/* 176:    */   {
/* 177:171 */     if ((this.codeGen.paramTypeList != null) && (args != null) && (args.tail() == null))
/* 178:    */     {
/* 179:173 */       ASTree left = args.head();
/* 180:174 */       return ((left instanceof Member)) && (((Member)left).get().equals(this.codeGen.paramListName));
/* 181:    */     }
/* 182:178 */     return false;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int getMethodArgsLength(ASTList args)
/* 186:    */   {
/* 187:182 */     String pname = this.codeGen.paramListName;
/* 188:183 */     int n = 0;
/* 189:184 */     while (args != null)
/* 190:    */     {
/* 191:185 */       ASTree a = args.head();
/* 192:186 */       if (((a instanceof Member)) && (((Member)a).get().equals(pname)))
/* 193:    */       {
/* 194:187 */         if (this.codeGen.paramTypeList != null) {
/* 195:188 */           n += this.codeGen.paramTypeList.length;
/* 196:    */         }
/* 197:    */       }
/* 198:    */       else {
/* 199:191 */         n++;
/* 200:    */       }
/* 201:193 */       args = args.tail();
/* 202:    */     }
/* 203:196 */     return n;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void atMethodArgs(ASTList args, int[] types, int[] dims, String[] cnames)
/* 207:    */     throws CompileError
/* 208:    */   {
/* 209:201 */     CtClass[] params = this.codeGen.paramTypeList;
/* 210:202 */     String pname = this.codeGen.paramListName;
/* 211:203 */     int i = 0;
/* 212:204 */     while (args != null)
/* 213:    */     {
/* 214:205 */       ASTree a = args.head();
/* 215:206 */       if (((a instanceof Member)) && (((Member)a).get().equals(pname)))
/* 216:    */       {
/* 217:207 */         if (params != null)
/* 218:    */         {
/* 219:208 */           int n = params.length;
/* 220:209 */           for (int k = 0; k < n; k++)
/* 221:    */           {
/* 222:210 */             CtClass p = params[k];
/* 223:211 */             setType(p);
/* 224:212 */             types[i] = this.exprType;
/* 225:213 */             dims[i] = this.arrayDim;
/* 226:214 */             cnames[i] = this.className;
/* 227:215 */             i++;
/* 228:    */           }
/* 229:    */         }
/* 230:    */       }
/* 231:    */       else
/* 232:    */       {
/* 233:220 */         a.accept(this);
/* 234:221 */         types[i] = this.exprType;
/* 235:222 */         dims[i] = this.arrayDim;
/* 236:223 */         cnames[i] = this.className;
/* 237:224 */         i++;
/* 238:    */       }
/* 239:227 */       args = args.tail();
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   void compileInvokeSpecial(ASTree target, String classname, String methodname, String descriptor, ASTList args)
/* 244:    */     throws CompileError
/* 245:    */   {
/* 246:238 */     target.accept(this);
/* 247:239 */     int nargs = getMethodArgsLength(args);
/* 248:240 */     atMethodArgs(args, new int[nargs], new int[nargs], new String[nargs]);
/* 249:    */     
/* 250:242 */     setReturnType(descriptor);
/* 251:243 */     addNullIfVoid();
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected void compileUnwrapValue(CtClass type)
/* 255:    */     throws CompileError
/* 256:    */   {
/* 257:248 */     if (type == CtClass.voidType) {
/* 258:249 */       addNullIfVoid();
/* 259:    */     } else {
/* 260:251 */       setType(type);
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setType(CtClass type)
/* 265:    */     throws CompileError
/* 266:    */   {
/* 267:258 */     setType(type, 0);
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void setType(CtClass type, int dim)
/* 271:    */     throws CompileError
/* 272:    */   {
/* 273:262 */     if (type.isPrimitive())
/* 274:    */     {
/* 275:263 */       CtPrimitiveType pt = (CtPrimitiveType)type;
/* 276:264 */       this.exprType = MemberResolver.descToType(pt.getDescriptor());
/* 277:265 */       this.arrayDim = dim;
/* 278:266 */       this.className = null;
/* 279:    */     }
/* 280:268 */     else if (type.isArray())
/* 281:    */     {
/* 282:    */       try
/* 283:    */       {
/* 284:270 */         setType(type.getComponentType(), dim + 1);
/* 285:    */       }
/* 286:    */       catch (NotFoundException e)
/* 287:    */       {
/* 288:273 */         throw new CompileError("undefined type: " + type.getName());
/* 289:    */       }
/* 290:    */     }
/* 291:    */     else
/* 292:    */     {
/* 293:276 */       this.exprType = 307;
/* 294:277 */       this.arrayDim = dim;
/* 295:278 */       this.className = MemberResolver.javaToJvmName(type.getName());
/* 296:    */     }
/* 297:    */   }
/* 298:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.JvstTypeChecker
 * JD-Core Version:    0.7.0.1
 */