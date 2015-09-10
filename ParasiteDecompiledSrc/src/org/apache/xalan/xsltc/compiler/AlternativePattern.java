/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.GOTO;
/*  4:   */ import org.apache.bcel.generic.InstructionHandle;
/*  5:   */ import org.apache.bcel.generic.InstructionList;
/*  6:   */ import org.apache.bcel.generic.MethodGen;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 11:   */ 
/* 12:   */ final class AlternativePattern
/* 13:   */   extends Pattern
/* 14:   */ {
/* 15:   */   private final Pattern _left;
/* 16:   */   private final Pattern _right;
/* 17:   */   
/* 18:   */   public AlternativePattern(Pattern left, Pattern right)
/* 19:   */   {
/* 20:45 */     this._left = left;
/* 21:46 */     this._right = right;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setParser(Parser parser)
/* 25:   */   {
/* 26:50 */     super.setParser(parser);
/* 27:51 */     this._left.setParser(parser);
/* 28:52 */     this._right.setParser(parser);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Pattern getLeft()
/* 32:   */   {
/* 33:56 */     return this._left;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Pattern getRight()
/* 37:   */   {
/* 38:60 */     return this._right;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Type typeCheck(SymbolTable stable)
/* 42:   */     throws TypeCheckError
/* 43:   */   {
/* 44:67 */     this._left.typeCheck(stable);
/* 45:68 */     this._right.typeCheck(stable);
/* 46:69 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public double getPriority()
/* 50:   */   {
/* 51:73 */     double left = this._left.getPriority();
/* 52:74 */     double right = this._right.getPriority();
/* 53:76 */     if (left < right) {
/* 54:77 */       return left;
/* 55:   */     }
/* 56:79 */     return right;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String toString()
/* 60:   */   {
/* 61:83 */     return "alternative(" + this._left + ", " + this._right + ')';
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 65:   */   {
/* 66:87 */     InstructionList il = methodGen.getInstructionList();
/* 67:   */     
/* 68:89 */     this._left.translate(classGen, methodGen);
/* 69:90 */     InstructionHandle gotot = il.append(new GOTO(null));
/* 70:91 */     il.append(methodGen.loadContextNode());
/* 71:92 */     this._right.translate(classGen, methodGen);
/* 72:   */     
/* 73:94 */     this._left._trueList.backPatch(gotot);
/* 74:95 */     this._left._falseList.backPatch(gotot.getNext());
/* 75:   */     
/* 76:97 */     this._trueList.append(this._right._trueList.add(gotot));
/* 77:98 */     this._falseList.append(this._right._falseList);
/* 78:   */   }
/* 79:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AlternativePattern
 * JD-Core Version:    0.7.0.1
 */