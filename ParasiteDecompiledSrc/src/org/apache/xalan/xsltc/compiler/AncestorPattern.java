/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GOTO;
/*   7:    */ import org.apache.bcel.generic.IFLT;
/*   8:    */ import org.apache.bcel.generic.ILOAD;
/*   9:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  10:    */ import org.apache.bcel.generic.ISTORE;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionHandle;
/*  13:    */ import org.apache.bcel.generic.InstructionList;
/*  14:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  15:    */ import org.apache.bcel.generic.MethodGen;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ 
/*  22:    */ final class AncestorPattern
/*  23:    */   extends RelativePathPattern
/*  24:    */ {
/*  25:    */   private final Pattern _left;
/*  26:    */   private final RelativePathPattern _right;
/*  27:    */   private InstructionHandle _loop;
/*  28:    */   
/*  29:    */   public AncestorPattern(RelativePathPattern right)
/*  30:    */   {
/*  31: 52 */     this(null, right);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AncestorPattern(Pattern left, RelativePathPattern right)
/*  35:    */   {
/*  36: 56 */     this._left = left;
/*  37: 57 */     (this._right = right).setParent(this);
/*  38: 58 */     if (left != null) {
/*  39: 59 */       left.setParent(this);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public InstructionHandle getLoopHandle()
/*  44:    */   {
/*  45: 64 */     return this._loop;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setParser(Parser parser)
/*  49:    */   {
/*  50: 68 */     super.setParser(parser);
/*  51: 69 */     if (this._left != null) {
/*  52: 70 */       this._left.setParser(parser);
/*  53:    */     }
/*  54: 72 */     this._right.setParser(parser);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isWildcard()
/*  58:    */   {
/*  59: 77 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public StepPattern getKernelPattern()
/*  63:    */   {
/*  64: 81 */     return this._right.getKernelPattern();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void reduceKernelPattern()
/*  68:    */   {
/*  69: 85 */     this._right.reduceKernelPattern();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Type typeCheck(SymbolTable stable)
/*  73:    */     throws TypeCheckError
/*  74:    */   {
/*  75: 89 */     if (this._left != null) {
/*  76: 90 */       this._left.typeCheck(stable);
/*  77:    */     }
/*  78: 92 */     return this._right.typeCheck(stable);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  82:    */   {
/*  83: 97 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  84: 98 */     InstructionList il = methodGen.getInstructionList();
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:104 */     LocalVariableGen local = methodGen.addLocalVariable2("app", Util.getJCRefType("I"), il.getEnd());
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:108 */     org.apache.bcel.generic.Instruction loadLocal = new ILOAD(local.getIndex());
/*  95:    */     
/*  96:110 */     org.apache.bcel.generic.Instruction storeLocal = new ISTORE(local.getIndex());
/*  97:113 */     if ((this._right instanceof StepPattern))
/*  98:    */     {
/*  99:114 */       il.append(InstructionConstants.DUP);
/* 100:115 */       il.append(storeLocal);
/* 101:116 */       this._right.translate(classGen, methodGen);
/* 102:117 */       il.append(methodGen.loadDOM());
/* 103:118 */       il.append(loadLocal);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:121 */       this._right.translate(classGen, methodGen);
/* 108:123 */       if ((this._right instanceof AncestorPattern))
/* 109:    */       {
/* 110:124 */         il.append(methodGen.loadDOM());
/* 111:125 */         il.append(InstructionConstants.SWAP);
/* 112:    */       }
/* 113:    */     }
/* 114:129 */     if (this._left != null)
/* 115:    */     {
/* 116:130 */       int getParent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
/* 117:    */       
/* 118:    */ 
/* 119:133 */       InstructionHandle parent = il.append(new INVOKEINTERFACE(getParent, 2));
/* 120:    */       
/* 121:135 */       il.append(InstructionConstants.DUP);
/* 122:136 */       il.append(storeLocal);
/* 123:137 */       this._falseList.add(il.append(new IFLT(null)));
/* 124:138 */       il.append(loadLocal);
/* 125:    */       
/* 126:140 */       this._left.translate(classGen, methodGen);
/* 127:    */       
/* 128:142 */       SyntaxTreeNode p = getParent();
/* 129:143 */       if ((p != null) && (!(p instanceof Instruction)) && (!(p instanceof TopLevelElement))) {
/* 130:149 */         il.append(loadLocal);
/* 131:    */       }
/* 132:152 */       BranchHandle exit = il.append(new GOTO(null));
/* 133:153 */       this._loop = il.append(methodGen.loadDOM());
/* 134:154 */       il.append(loadLocal);
/* 135:155 */       local.setEnd(this._loop);
/* 136:156 */       il.append(new GOTO(parent));
/* 137:157 */       exit.setTarget(il.append(InstructionConstants.NOP));
/* 138:158 */       this._left.backPatchFalseList(this._loop);
/* 139:    */       
/* 140:160 */       this._trueList.append(this._left._trueList);
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:163 */       il.append(InstructionConstants.POP2);
/* 145:    */     }
/* 146:170 */     if ((this._right instanceof AncestorPattern))
/* 147:    */     {
/* 148:171 */       AncestorPattern ancestor = (AncestorPattern)this._right;
/* 149:172 */       this._falseList.backPatch(ancestor.getLoopHandle());
/* 150:    */     }
/* 151:175 */     this._trueList.append(this._right._trueList);
/* 152:176 */     this._falseList.append(this._right._falseList);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String toString()
/* 156:    */   {
/* 157:180 */     return "AncestorPattern(" + this._left + ", " + this._right + ')';
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AncestorPattern
 * JD-Core Version:    0.7.0.1
 */