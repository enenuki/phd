/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 14:   */ 
/* 15:   */ final class StartsWithCall
/* 16:   */   extends FunctionCall
/* 17:   */ {
/* 18:42 */   private Expression _base = null;
/* 19:43 */   private Expression _token = null;
/* 20:   */   
/* 21:   */   public StartsWithCall(QName fname, Vector arguments)
/* 22:   */   {
/* 23:49 */     super(fname, arguments);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Type typeCheck(SymbolTable stable)
/* 27:   */     throws TypeCheckError
/* 28:   */   {
/* 29:58 */     if (argumentCount() != 2)
/* 30:   */     {
/* 31:59 */       ErrorMsg err = new ErrorMsg("ILLEGAL_ARG_ERR", getName(), this);
/* 32:   */       
/* 33:61 */       throw new TypeCheckError(err);
/* 34:   */     }
/* 35:65 */     this._base = argument(0);
/* 36:66 */     Type baseType = this._base.typeCheck(stable);
/* 37:67 */     if (baseType != Type.String) {
/* 38:68 */       this._base = new CastExpr(this._base, Type.String);
/* 39:   */     }
/* 40:71 */     this._token = argument(1);
/* 41:72 */     Type tokenType = this._token.typeCheck(stable);
/* 42:73 */     if (tokenType != Type.String) {
/* 43:74 */       this._token = new CastExpr(this._token, Type.String);
/* 44:   */     }
/* 45:76 */     return this._type = Type.Boolean;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 49:   */   {
/* 50:83 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 51:84 */     InstructionList il = methodGen.getInstructionList();
/* 52:85 */     this._base.translate(classGen, methodGen);
/* 53:86 */     this._token.translate(classGen, methodGen);
/* 54:87 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "startsWith", "(Ljava/lang/String;)Z")));
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.StartsWithCall
 * JD-Core Version:    0.7.0.1
 */