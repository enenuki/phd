/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GOTO_W;
/*   7:    */ import org.apache.bcel.generic.IF_ICMPEQ;
/*   8:    */ import org.apache.bcel.generic.ILOAD;
/*   9:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  10:    */ import org.apache.bcel.generic.ISTORE;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionHandle;
/*  13:    */ import org.apache.bcel.generic.InstructionList;
/*  14:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  15:    */ import org.apache.bcel.generic.MethodGen;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  22:    */ 
/*  23:    */ final class AbsolutePathPattern
/*  24:    */   extends LocationPathPattern
/*  25:    */ {
/*  26:    */   private final RelativePathPattern _left;
/*  27:    */   
/*  28:    */   public AbsolutePathPattern(RelativePathPattern left)
/*  29:    */   {
/*  30: 50 */     this._left = left;
/*  31: 51 */     if (left != null) {
/*  32: 52 */       left.setParent(this);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setParser(Parser parser)
/*  37:    */   {
/*  38: 57 */     super.setParser(parser);
/*  39: 58 */     if (this._left != null) {
/*  40: 59 */       this._left.setParser(parser);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Type typeCheck(SymbolTable stable)
/*  45:    */     throws TypeCheckError
/*  46:    */   {
/*  47: 63 */     return this._left == null ? Type.Root : this._left.typeCheck(stable);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isWildcard()
/*  51:    */   {
/*  52: 67 */     return false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public StepPattern getKernelPattern()
/*  56:    */   {
/*  57: 71 */     return this._left != null ? this._left.getKernelPattern() : null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void reduceKernelPattern()
/*  61:    */   {
/*  62: 75 */     this._left.reduceKernelPattern();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  66:    */   {
/*  67: 79 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  68: 80 */     InstructionList il = methodGen.getInstructionList();
/*  69: 82 */     if (this._left != null) {
/*  70: 83 */       if ((this._left instanceof StepPattern))
/*  71:    */       {
/*  72: 84 */         LocalVariableGen local = methodGen.addLocalVariable2("apptmp", Util.getJCRefType("I"), null);
/*  73:    */         
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77: 89 */         il.append(InstructionConstants.DUP);
/*  78: 90 */         local.setStart(il.append(new ISTORE(local.getIndex())));
/*  79: 91 */         this._left.translate(classGen, methodGen);
/*  80: 92 */         il.append(methodGen.loadDOM());
/*  81: 93 */         local.setEnd(il.append(new ILOAD(local.getIndex())));
/*  82: 94 */         methodGen.removeLocalVariable(local);
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86: 97 */         this._left.translate(classGen, methodGen);
/*  87:    */       }
/*  88:    */     }
/*  89:101 */     int getParent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getParent", "(I)I");
/*  90:    */     
/*  91:    */ 
/*  92:104 */     int getType = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:108 */     InstructionHandle begin = il.append(methodGen.loadDOM());
/*  97:109 */     il.append(InstructionConstants.SWAP);
/*  98:110 */     il.append(new INVOKEINTERFACE(getParent, 2));
/*  99:111 */     if ((this._left instanceof AncestorPattern))
/* 100:    */     {
/* 101:112 */       il.append(methodGen.loadDOM());
/* 102:113 */       il.append(InstructionConstants.SWAP);
/* 103:    */     }
/* 104:115 */     il.append(new INVOKEINTERFACE(getType, 2));
/* 105:116 */     il.append(new PUSH(cpg, 9));
/* 106:    */     
/* 107:118 */     BranchHandle skip = il.append(new IF_ICMPEQ(null));
/* 108:119 */     this._falseList.add(il.append(new GOTO_W(null)));
/* 109:120 */     skip.setTarget(il.append(InstructionConstants.NOP));
/* 110:122 */     if (this._left != null)
/* 111:    */     {
/* 112:123 */       this._left.backPatchTrueList(begin);
/* 113:129 */       if ((this._left instanceof AncestorPattern))
/* 114:    */       {
/* 115:130 */         AncestorPattern ancestor = (AncestorPattern)this._left;
/* 116:131 */         this._falseList.backPatch(ancestor.getLoopHandle());
/* 117:    */       }
/* 118:133 */       this._falseList.append(this._left._falseList);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String toString()
/* 123:    */   {
/* 124:138 */     return "absolutePathPattern(" + (this._left != null ? this._left.toString() : ")");
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AbsolutePathPattern
 * JD-Core Version:    0.7.0.1
 */