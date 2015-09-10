/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.InstructionList;
/*  5:   */ import org.apache.bcel.generic.MethodGen;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 11:   */ 
/* 12:   */ final class UnaryOpExpr
/* 13:   */   extends Expression
/* 14:   */ {
/* 15:   */   private Expression _left;
/* 16:   */   
/* 17:   */   public UnaryOpExpr(Expression left)
/* 18:   */   {
/* 19:39 */     (this._left = left).setParent(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean hasPositionCall()
/* 23:   */   {
/* 24:47 */     return this._left.hasPositionCall();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasLastCall()
/* 28:   */   {
/* 29:54 */     return this._left.hasLastCall();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setParser(Parser parser)
/* 33:   */   {
/* 34:58 */     super.setParser(parser);
/* 35:59 */     this._left.setParser(parser);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Type typeCheck(SymbolTable stable)
/* 39:   */     throws TypeCheckError
/* 40:   */   {
/* 41:63 */     Type tleft = this._left.typeCheck(stable);
/* 42:64 */     MethodType ptype = lookupPrimop(stable, "u-", new MethodType(Type.Void, tleft));
/* 43:68 */     if (ptype != null)
/* 44:   */     {
/* 45:69 */       Type arg1 = (Type)ptype.argsType().elementAt(0);
/* 46:70 */       if (!arg1.identicalTo(tleft)) {
/* 47:71 */         this._left = new CastExpr(this._left, arg1);
/* 48:   */       }
/* 49:73 */       return this._type = ptype.resultType();
/* 50:   */     }
/* 51:76 */     throw new TypeCheckError(this);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:80 */     return "u-(" + this._left + ')';
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 60:   */   {
/* 61:84 */     InstructionList il = methodGen.getInstructionList();
/* 62:85 */     this._left.translate(classGen, methodGen);
/* 63:86 */     il.append(this._type.NEG());
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UnaryOpExpr
 * JD-Core Version:    0.7.0.1
 */