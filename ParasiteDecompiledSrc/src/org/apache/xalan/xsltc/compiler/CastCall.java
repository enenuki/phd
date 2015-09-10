/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.CHECKCAST;
/*  5:   */ import org.apache.bcel.generic.ClassGen;
/*  6:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.ObjectType;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 14:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 15:   */ 
/* 16:   */ final class CastCall
/* 17:   */   extends FunctionCall
/* 18:   */ {
/* 19:   */   private String _className;
/* 20:   */   private Expression _right;
/* 21:   */   
/* 22:   */   public CastCall(QName fname, Vector arguments)
/* 23:   */   {
/* 24:56 */     super(fname, arguments);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Type typeCheck(SymbolTable stable)
/* 28:   */     throws TypeCheckError
/* 29:   */   {
/* 30:64 */     if (argumentCount() != 2) {
/* 31:65 */       throw new TypeCheckError(new ErrorMsg("ILLEGAL_ARG_ERR", getName(), this));
/* 32:   */     }
/* 33:70 */     Expression exp = argument(0);
/* 34:71 */     if ((exp instanceof LiteralExpr))
/* 35:   */     {
/* 36:72 */       this._className = ((LiteralExpr)exp).getValue();
/* 37:73 */       this._type = Type.newObjectType(this._className);
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:76 */       throw new TypeCheckError(new ErrorMsg("NEED_LITERAL_ERR", getName(), this));
/* 42:   */     }
/* 43:81 */     this._right = argument(1);
/* 44:82 */     Type tright = this._right.typeCheck(stable);
/* 45:83 */     if ((tright != Type.Reference) && (!(tright instanceof ObjectType))) {
/* 46:86 */       throw new TypeCheckError(new ErrorMsg("DATA_CONVERSION_ERR", tright, this._type, this));
/* 47:   */     }
/* 48:90 */     return this._type;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 52:   */   {
/* 53:94 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 54:95 */     InstructionList il = methodGen.getInstructionList();
/* 55:   */     
/* 56:97 */     this._right.translate(classGen, methodGen);
/* 57:98 */     il.append(new CHECKCAST(cpg.addClass(this._className)));
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CastCall
 * JD-Core Version:    0.7.0.1
 */