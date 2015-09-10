/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   7:    */ 
/*   8:    */ final class OptFunctionNode
/*   9:    */ {
/*  10:    */   FunctionNode fnode;
/*  11:    */   private boolean[] numberVarFlags;
/*  12:    */   
/*  13:    */   OptFunctionNode(FunctionNode fnode)
/*  14:    */   {
/*  15: 50 */     this.fnode = fnode;
/*  16: 51 */     fnode.setCompilerData(this);
/*  17:    */   }
/*  18:    */   
/*  19:    */   static OptFunctionNode get(ScriptNode scriptOrFn, int i)
/*  20:    */   {
/*  21: 56 */     FunctionNode fnode = scriptOrFn.getFunctionNode(i);
/*  22: 57 */     return (OptFunctionNode)fnode.getCompilerData();
/*  23:    */   }
/*  24:    */   
/*  25:    */   static OptFunctionNode get(ScriptNode scriptOrFn)
/*  26:    */   {
/*  27: 62 */     return (OptFunctionNode)scriptOrFn.getCompilerData();
/*  28:    */   }
/*  29:    */   
/*  30:    */   boolean isTargetOfDirectCall()
/*  31:    */   {
/*  32: 67 */     return this.directTargetIndex >= 0;
/*  33:    */   }
/*  34:    */   
/*  35:    */   int getDirectTargetIndex()
/*  36:    */   {
/*  37: 72 */     return this.directTargetIndex;
/*  38:    */   }
/*  39:    */   
/*  40:    */   void setDirectTargetIndex(int directTargetIndex)
/*  41:    */   {
/*  42: 78 */     if ((directTargetIndex < 0) || (this.directTargetIndex >= 0)) {
/*  43: 79 */       Kit.codeBug();
/*  44:    */     }
/*  45: 80 */     this.directTargetIndex = directTargetIndex;
/*  46:    */   }
/*  47:    */   
/*  48:    */   void setParameterNumberContext(boolean b)
/*  49:    */   {
/*  50: 85 */     this.itsParameterNumberContext = b;
/*  51:    */   }
/*  52:    */   
/*  53:    */   boolean getParameterNumberContext()
/*  54:    */   {
/*  55: 90 */     return this.itsParameterNumberContext;
/*  56:    */   }
/*  57:    */   
/*  58:    */   int getVarCount()
/*  59:    */   {
/*  60: 95 */     return this.fnode.getParamAndVarCount();
/*  61:    */   }
/*  62:    */   
/*  63:    */   boolean isParameter(int varIndex)
/*  64:    */   {
/*  65:100 */     return varIndex < this.fnode.getParamCount();
/*  66:    */   }
/*  67:    */   
/*  68:    */   boolean isNumberVar(int varIndex)
/*  69:    */   {
/*  70:105 */     varIndex -= this.fnode.getParamCount();
/*  71:106 */     if ((varIndex >= 0) && (this.numberVarFlags != null)) {
/*  72:107 */       return this.numberVarFlags[varIndex];
/*  73:    */     }
/*  74:109 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   void setIsNumberVar(int varIndex)
/*  78:    */   {
/*  79:114 */     varIndex -= this.fnode.getParamCount();
/*  80:116 */     if (varIndex < 0) {
/*  81:116 */       Kit.codeBug();
/*  82:    */     }
/*  83:117 */     if (this.numberVarFlags == null)
/*  84:    */     {
/*  85:118 */       int size = this.fnode.getParamAndVarCount() - this.fnode.getParamCount();
/*  86:119 */       this.numberVarFlags = new boolean[size];
/*  87:    */     }
/*  88:121 */     this.numberVarFlags[varIndex] = true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   int getVarIndex(Node n)
/*  92:    */   {
/*  93:126 */     int index = n.getIntProp(7, -1);
/*  94:127 */     if (index == -1)
/*  95:    */     {
/*  96:129 */       int type = n.getType();
/*  97:    */       Node node;
/*  98:130 */       if (type == 55)
/*  99:    */       {
/* 100:131 */         node = n;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:    */         Node node;
/* 105:132 */         if ((type == 56) || (type == 156)) {
/* 106:134 */           node = n.getFirstChild();
/* 107:    */         } else {
/* 108:136 */           throw Kit.codeBug();
/* 109:    */         }
/* 110:    */       }
/* 111:    */       Node node;
/* 112:138 */       index = this.fnode.getIndexForNameNode(node);
/* 113:139 */       if (index < 0) {
/* 114:139 */         throw Kit.codeBug();
/* 115:    */       }
/* 116:140 */       n.putIntProp(7, index);
/* 117:    */     }
/* 118:142 */     return index;
/* 119:    */   }
/* 120:    */   
/* 121:147 */   private int directTargetIndex = -1;
/* 122:    */   private boolean itsParameterNumberContext;
/* 123:    */   boolean itsContainsCalls0;
/* 124:    */   boolean itsContainsCalls1;
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.OptFunctionNode
 * JD-Core Version:    0.7.0.1
 */