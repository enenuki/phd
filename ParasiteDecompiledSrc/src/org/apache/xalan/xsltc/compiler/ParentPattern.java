/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.ILOAD;
/*   6:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   7:    */ import org.apache.bcel.generic.ISTORE;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionHandle;
/*  10:    */ import org.apache.bcel.generic.InstructionList;
/*  11:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  12:    */ import org.apache.bcel.generic.MethodGen;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  18:    */ 
/*  19:    */ final class ParentPattern
/*  20:    */   extends RelativePathPattern
/*  21:    */ {
/*  22:    */   private final Pattern _left;
/*  23:    */   private final RelativePathPattern _right;
/*  24:    */   
/*  25:    */   public ParentPattern(Pattern left, RelativePathPattern right)
/*  26:    */   {
/*  27: 46 */     (this._left = left).setParent(this);
/*  28: 47 */     (this._right = right).setParent(this);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setParser(Parser parser)
/*  32:    */   {
/*  33: 51 */     super.setParser(parser);
/*  34: 52 */     this._left.setParser(parser);
/*  35: 53 */     this._right.setParser(parser);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isWildcard()
/*  39:    */   {
/*  40: 57 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public StepPattern getKernelPattern()
/*  44:    */   {
/*  45: 61 */     return this._right.getKernelPattern();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void reduceKernelPattern()
/*  49:    */   {
/*  50: 65 */     this._right.reduceKernelPattern();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Type typeCheck(SymbolTable stable)
/*  54:    */     throws TypeCheckError
/*  55:    */   {
/*  56: 69 */     this._left.typeCheck(stable);
/*  57: 70 */     return this._right.typeCheck(stable);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  61:    */   {
/*  62: 74 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  63: 75 */     InstructionList il = methodGen.getInstructionList();
/*  64: 76 */     LocalVariableGen local = methodGen.addLocalVariable2("ppt", Util.getJCRefType("I"), null);
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69: 81 */     org.apache.bcel.generic.Instruction loadLocal = new ILOAD(local.getIndex());
/*  70:    */     
/*  71: 83 */     org.apache.bcel.generic.Instruction storeLocal = new ISTORE(local.getIndex());
/*  72: 86 */     if (this._right.isWildcard())
/*  73:    */     {
/*  74: 87 */       il.append(methodGen.loadDOM());
/*  75: 88 */       il.append(InstructionConstants.SWAP);
/*  76:    */     }
/*  77: 90 */     else if ((this._right instanceof StepPattern))
/*  78:    */     {
/*  79: 91 */       il.append(InstructionConstants.DUP);
/*  80: 92 */       local.setStart(il.append(storeLocal));
/*  81:    */       
/*  82: 94 */       this._right.translate(classGen, methodGen);
/*  83:    */       
/*  84: 96 */       il.append(methodGen.loadDOM());
/*  85: 97 */       local.setEnd(il.append(loadLocal));
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:100 */       this._right.translate(classGen, methodGen);
/*  90:102 */       if ((this._right instanceof AncestorPattern))
/*  91:    */       {
/*  92:103 */         il.append(methodGen.loadDOM());
/*  93:104 */         il.append(InstructionConstants.SWAP);
/*  94:    */       }
/*  95:    */     }
/*  96:108 */     int getParent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
/*  97:    */     
/*  98:    */ 
/*  99:111 */     il.append(new INVOKEINTERFACE(getParent, 2));
/* 100:    */     
/* 101:113 */     SyntaxTreeNode p = getParent();
/* 102:114 */     if ((p == null) || ((p instanceof Instruction)) || ((p instanceof TopLevelElement)))
/* 103:    */     {
/* 104:117 */       this._left.translate(classGen, methodGen);
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:120 */       il.append(InstructionConstants.DUP);
/* 109:121 */       InstructionHandle storeInst = il.append(storeLocal);
/* 110:123 */       if (local.getStart() == null) {
/* 111:124 */         local.setStart(storeInst);
/* 112:    */       }
/* 113:126 */       this._left.translate(classGen, methodGen);
/* 114:    */       
/* 115:128 */       il.append(methodGen.loadDOM());
/* 116:129 */       local.setEnd(il.append(loadLocal));
/* 117:    */     }
/* 118:132 */     methodGen.removeLocalVariable(local);
/* 119:138 */     if ((this._right instanceof AncestorPattern))
/* 120:    */     {
/* 121:139 */       AncestorPattern ancestor = (AncestorPattern)this._right;
/* 122:140 */       this._left.backPatchFalseList(ancestor.getLoopHandle());
/* 123:    */     }
/* 124:143 */     this._trueList.append(this._right._trueList.append(this._left._trueList));
/* 125:144 */     this._falseList.append(this._right._falseList.append(this._left._falseList));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:148 */     return "Parent(" + this._left + ", " + this._right + ')';
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ParentPattern
 * JD-Core Version:    0.7.0.1
 */