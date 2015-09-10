/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.BranchHandle;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.GOTO_W;
/*   8:    */ import org.apache.bcel.generic.IFEQ;
/*   9:    */ import org.apache.bcel.generic.InstructionConstants;
/*  10:    */ import org.apache.bcel.generic.InstructionHandle;
/*  11:    */ import org.apache.bcel.generic.InstructionList;
/*  12:    */ import org.apache.bcel.generic.MethodGen;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  21:    */ 
/*  22:    */ abstract class Expression
/*  23:    */   extends SyntaxTreeNode
/*  24:    */ {
/*  25:    */   protected Type _type;
/*  26: 57 */   protected FlowList _trueList = new FlowList();
/*  27: 62 */   protected FlowList _falseList = new FlowList();
/*  28:    */   
/*  29:    */   public Type getType()
/*  30:    */   {
/*  31: 65 */     return this._type;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public abstract String toString();
/*  35:    */   
/*  36:    */   public boolean hasPositionCall()
/*  37:    */   {
/*  38: 71 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean hasLastCall()
/*  42:    */   {
/*  43: 75 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object evaluateAtCompileTime()
/*  47:    */   {
/*  48: 84 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Type typeCheck(SymbolTable stable)
/*  52:    */     throws TypeCheckError
/*  53:    */   {
/*  54: 91 */     return typeCheckContents(stable);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  58:    */   {
/*  59: 98 */     ErrorMsg msg = new ErrorMsg("NOT_IMPLEMENTED_ERR", getClass(), this);
/*  60:    */     
/*  61:100 */     getParser().reportError(2, msg);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final InstructionList compile(ClassGenerator classGen, MethodGenerator methodGen)
/*  65:    */   {
/*  66:109 */     InstructionList save = methodGen.getInstructionList();
/*  67:    */     InstructionList result;
/*  68:110 */     methodGen.setInstructionList(result = new InstructionList());
/*  69:111 */     translate(classGen, methodGen);
/*  70:112 */     methodGen.setInstructionList(save);
/*  71:113 */     return result;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/*  75:    */   {
/*  76:121 */     translate(classGen, methodGen);
/*  77:122 */     if ((this._type instanceof BooleanType)) {
/*  78:123 */       desynthesize(classGen, methodGen);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void startIterator(ClassGenerator classGen, MethodGenerator methodGen)
/*  83:    */   {
/*  84:134 */     if (!(this._type instanceof NodeSetType)) {
/*  85:135 */       return;
/*  86:    */     }
/*  87:139 */     Expression expr = this;
/*  88:140 */     if ((expr instanceof CastExpr)) {
/*  89:141 */       expr = ((CastExpr)expr).getExpr();
/*  90:    */     }
/*  91:143 */     if (!(expr instanceof VariableRefBase))
/*  92:    */     {
/*  93:144 */       InstructionList il = methodGen.getInstructionList();
/*  94:145 */       il.append(methodGen.loadContextNode());
/*  95:146 */       il.append(methodGen.setStartNode());
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void synthesize(ClassGenerator classGen, MethodGenerator methodGen)
/* 100:    */   {
/* 101:156 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 102:157 */     InstructionList il = methodGen.getInstructionList();
/* 103:158 */     this._trueList.backPatch(il.append(InstructionConstants.ICONST_1));
/* 104:159 */     BranchHandle truec = il.append(new GOTO_W(null));
/* 105:160 */     this._falseList.backPatch(il.append(InstructionConstants.ICONST_0));
/* 106:161 */     truec.setTarget(il.append(InstructionConstants.NOP));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void desynthesize(ClassGenerator classGen, MethodGenerator methodGen)
/* 110:    */   {
/* 111:166 */     InstructionList il = methodGen.getInstructionList();
/* 112:167 */     this._falseList.add(il.append(new IFEQ(null)));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public FlowList getFalseList()
/* 116:    */   {
/* 117:171 */     return this._falseList;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public FlowList getTrueList()
/* 121:    */   {
/* 122:175 */     return this._trueList;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void backPatchFalseList(InstructionHandle ih)
/* 126:    */   {
/* 127:179 */     this._falseList.backPatch(ih);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void backPatchTrueList(InstructionHandle ih)
/* 131:    */   {
/* 132:183 */     this._trueList.backPatch(ih);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public MethodType lookupPrimop(SymbolTable stable, String op, MethodType ctype)
/* 136:    */   {
/* 137:195 */     MethodType result = null;
/* 138:196 */     Vector primop = stable.lookupPrimop(op);
/* 139:197 */     if (primop != null)
/* 140:    */     {
/* 141:198 */       int n = primop.size();
/* 142:199 */       int minDistance = 2147483647;
/* 143:200 */       for (int i = 0; i < n; i++)
/* 144:    */       {
/* 145:201 */         MethodType ptype = (MethodType)primop.elementAt(i);
/* 146:203 */         if (ptype.argsCount() == ctype.argsCount())
/* 147:    */         {
/* 148:208 */           if (result == null) {
/* 149:209 */             result = ptype;
/* 150:    */           }
/* 151:213 */           int distance = ctype.distanceTo(ptype);
/* 152:214 */           if (distance < minDistance)
/* 153:    */           {
/* 154:215 */             minDistance = distance;
/* 155:216 */             result = ptype;
/* 156:    */           }
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:220 */     return result;
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Expression
 * JD-Core Version:    0.7.0.1
 */