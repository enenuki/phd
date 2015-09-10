/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.InstructionList;
/*  7:   */ import org.apache.bcel.generic.MethodGen;
/*  8:   */ import org.apache.bcel.generic.PUSH;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 14:   */ 
/* 15:   */ final class ElementAvailableCall
/* 16:   */   extends FunctionCall
/* 17:   */ {
/* 18:   */   public ElementAvailableCall(QName fname, Vector arguments)
/* 19:   */   {
/* 20:41 */     super(fname, arguments);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type typeCheck(SymbolTable stable)
/* 24:   */     throws TypeCheckError
/* 25:   */   {
/* 26:48 */     if ((argument() instanceof LiteralExpr)) {
/* 27:49 */       return this._type = Type.Boolean;
/* 28:   */     }
/* 29:51 */     ErrorMsg err = new ErrorMsg("NEED_LITERAL_ERR", "element-available", this);
/* 30:   */     
/* 31:53 */     throw new TypeCheckError(err);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object evaluateAtCompileTime()
/* 35:   */   {
/* 36:62 */     return getResult() ? Boolean.TRUE : Boolean.FALSE;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean getResult()
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:70 */       LiteralExpr arg = (LiteralExpr)argument();
/* 44:71 */       String qname = arg.getValue();
/* 45:72 */       int index = qname.indexOf(':');
/* 46:73 */       String localName = index > 0 ? qname.substring(index + 1) : qname;
/* 47:   */       
/* 48:75 */       return getParser().elementSupported(arg.getNamespace(), localName);
/* 49:   */     }
/* 50:   */     catch (ClassCastException e) {}
/* 51:79 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 55:   */   {
/* 56:89 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 57:90 */     boolean result = getResult();
/* 58:91 */     methodGen.getInstructionList().append(new PUSH(cpg, result));
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ElementAvailableCall
 * JD-Core Version:    0.7.0.1
 */