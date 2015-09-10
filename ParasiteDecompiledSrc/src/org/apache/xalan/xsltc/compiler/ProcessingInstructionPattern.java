/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.BranchHandle;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.GOTO;
/*   8:    */ import org.apache.bcel.generic.IFEQ;
/*   9:    */ import org.apache.bcel.generic.IF_ICMPEQ;
/*  10:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  11:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  12:    */ import org.apache.bcel.generic.InstructionConstants;
/*  13:    */ import org.apache.bcel.generic.InstructionHandle;
/*  14:    */ import org.apache.bcel.generic.InstructionList;
/*  15:    */ import org.apache.bcel.generic.MethodGen;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  21:    */ 
/*  22:    */ final class ProcessingInstructionPattern
/*  23:    */   extends StepPattern
/*  24:    */ {
/*  25: 46 */   private String _name = null;
/*  26: 47 */   private boolean _typeChecked = false;
/*  27:    */   
/*  28:    */   public ProcessingInstructionPattern(String name)
/*  29:    */   {
/*  30: 53 */     super(3, 7, null);
/*  31: 54 */     this._name = name;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public double getDefaultPriority()
/*  35:    */   {
/*  36: 62 */     return this._name != null ? 0.0D : -0.5D;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String toString()
/*  40:    */   {
/*  41: 65 */     if (this._predicates == null) {
/*  42: 66 */       return "processing-instruction(" + this._name + ")";
/*  43:    */     }
/*  44: 68 */     return "processing-instruction(" + this._name + ")" + this._predicates;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void reduceKernelPattern()
/*  48:    */   {
/*  49: 72 */     this._typeChecked = true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isWildcard()
/*  53:    */   {
/*  54: 76 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Type typeCheck(SymbolTable stable)
/*  58:    */     throws TypeCheckError
/*  59:    */   {
/*  60: 80 */     if (hasPredicates())
/*  61:    */     {
/*  62: 82 */       int n = this._predicates.size();
/*  63: 83 */       for (int i = 0; i < n; i++)
/*  64:    */       {
/*  65: 84 */         Predicate pred = (Predicate)this._predicates.elementAt(i);
/*  66: 85 */         pred.typeCheck(stable);
/*  67:    */       }
/*  68:    */     }
/*  69: 88 */     return Type.NodeSet;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  73:    */   {
/*  74: 92 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  75: 93 */     InstructionList il = methodGen.getInstructionList();
/*  76:    */     
/*  77:    */ 
/*  78: 96 */     int gname = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeName", "(I)Ljava/lang/String;");
/*  79:    */     
/*  80:    */ 
/*  81: 99 */     int cmp = cpg.addMethodref("java.lang.String", "equals", "(Ljava/lang/Object;)Z");
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:103 */     il.append(methodGen.loadCurrentNode());
/*  86:104 */     il.append(InstructionConstants.SWAP);
/*  87:    */     
/*  88:    */ 
/*  89:107 */     il.append(methodGen.storeCurrentNode());
/*  90:110 */     if (!this._typeChecked)
/*  91:    */     {
/*  92:111 */       il.append(methodGen.loadCurrentNode());
/*  93:112 */       int getType = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*  94:    */       
/*  95:    */ 
/*  96:115 */       il.append(methodGen.loadDOM());
/*  97:116 */       il.append(methodGen.loadCurrentNode());
/*  98:117 */       il.append(new INVOKEINTERFACE(getType, 2));
/*  99:118 */       il.append(new PUSH(cpg, 7));
/* 100:119 */       this._falseList.add(il.append(new IF_ICMPEQ(null)));
/* 101:    */     }
/* 102:123 */     il.append(new PUSH(cpg, this._name));
/* 103:    */     
/* 104:125 */     il.append(methodGen.loadDOM());
/* 105:126 */     il.append(methodGen.loadCurrentNode());
/* 106:127 */     il.append(new INVOKEINTERFACE(gname, 2));
/* 107:    */     
/* 108:129 */     il.append(new INVOKEVIRTUAL(cmp));
/* 109:130 */     this._falseList.add(il.append(new IFEQ(null)));
/* 110:133 */     if (hasPredicates())
/* 111:    */     {
/* 112:134 */       int n = this._predicates.size();
/* 113:135 */       for (int i = 0; i < n; i++)
/* 114:    */       {
/* 115:136 */         Predicate pred = (Predicate)this._predicates.elementAt(i);
/* 116:137 */         Expression exp = pred.getExpr();
/* 117:138 */         exp.translateDesynthesized(classGen, methodGen);
/* 118:139 */         this._trueList.append(exp._trueList);
/* 119:140 */         this._falseList.append(exp._falseList);
/* 120:    */       }
/* 121:    */     }
/* 122:146 */     InstructionHandle restore = il.append(methodGen.storeCurrentNode());
/* 123:147 */     backPatchTrueList(restore);
/* 124:148 */     BranchHandle skipFalse = il.append(new GOTO(null));
/* 125:    */     
/* 126:    */ 
/* 127:151 */     restore = il.append(methodGen.storeCurrentNode());
/* 128:152 */     backPatchFalseList(restore);
/* 129:153 */     this._falseList.add(il.append(new GOTO(null)));
/* 130:    */     
/* 131:    */ 
/* 132:156 */     skipFalse.setTarget(il.append(InstructionConstants.NOP));
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ProcessingInstructionPattern
 * JD-Core Version:    0.7.0.1
 */