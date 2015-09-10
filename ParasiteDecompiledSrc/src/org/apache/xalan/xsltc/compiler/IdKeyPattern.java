/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.GOTO;
/*   6:    */ import org.apache.bcel.generic.IFNE;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.PUSH;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ 
/*  17:    */ abstract class IdKeyPattern
/*  18:    */   extends LocationPathPattern
/*  19:    */ {
/*  20: 42 */   protected RelativePathPattern _left = null;
/*  21: 43 */   private String _index = null;
/*  22: 44 */   private String _value = null;
/*  23:    */   
/*  24:    */   public IdKeyPattern(String index, String value)
/*  25:    */   {
/*  26: 47 */     this._index = index;
/*  27: 48 */     this._value = value;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getIndexName()
/*  31:    */   {
/*  32: 52 */     return this._index;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Type typeCheck(SymbolTable stable)
/*  36:    */     throws TypeCheckError
/*  37:    */   {
/*  38: 56 */     return Type.NodeSet;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isWildcard()
/*  42:    */   {
/*  43: 60 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setLeft(RelativePathPattern left)
/*  47:    */   {
/*  48: 64 */     this._left = left;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public StepPattern getKernelPattern()
/*  52:    */   {
/*  53: 68 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void reduceKernelPattern() {}
/*  57:    */   
/*  58:    */   public String toString()
/*  59:    */   {
/*  60: 74 */     return "id/keyPattern(" + this._index + ", " + this._value + ')';
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  64:    */   {
/*  65: 84 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  66: 85 */     InstructionList il = methodGen.getInstructionList();
/*  67:    */     
/*  68:    */ 
/*  69: 88 */     int getKeyIndex = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "getKeyIndex", "(Ljava/lang/String;)Lorg/apache/xalan/xsltc/dom/KeyIndex;");
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75: 94 */     int lookupId = cpg.addMethodref("org/apache/xalan/xsltc/dom/KeyIndex", "containsID", "(ILjava/lang/Object;)I");
/*  76:    */     
/*  77:    */ 
/*  78: 97 */     int lookupKey = cpg.addMethodref("org/apache/xalan/xsltc/dom/KeyIndex", "containsKey", "(ILjava/lang/Object;)I");
/*  79:    */     
/*  80:    */ 
/*  81:100 */     int getNodeIdent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeIdent", "(I)I");
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:106 */     il.append(classGen.loadTranslet());
/*  88:107 */     il.append(new PUSH(cpg, this._index));
/*  89:108 */     il.append(new INVOKEVIRTUAL(getKeyIndex));
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:112 */     il.append(InstructionConstants.SWAP);
/*  94:113 */     il.append(new PUSH(cpg, this._value));
/*  95:114 */     if ((this instanceof IdPattern)) {
/*  96:116 */       il.append(new INVOKEVIRTUAL(lookupId));
/*  97:    */     } else {
/*  98:120 */       il.append(new INVOKEVIRTUAL(lookupKey));
/*  99:    */     }
/* 100:123 */     this._trueList.add(il.append(new IFNE(null)));
/* 101:124 */     this._falseList.add(il.append(new GOTO(null)));
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.IdKeyPattern
 * JD-Core Version:    0.7.0.1
 */