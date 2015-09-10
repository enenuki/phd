/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.InstructionList;
/*  6:   */ import org.apache.bcel.generic.MethodGen;
/*  7:   */ import org.apache.bcel.generic.PUSH;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 12:   */ 
/* 13:   */ final class LiteralExpr
/* 14:   */   extends Expression
/* 15:   */ {
/* 16:   */   private final String _value;
/* 17:   */   private final String _namespace;
/* 18:   */   
/* 19:   */   public LiteralExpr(String value)
/* 20:   */   {
/* 21:46 */     this._value = value;
/* 22:47 */     this._namespace = null;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public LiteralExpr(String value, String namespace)
/* 26:   */   {
/* 27:56 */     this._value = value;
/* 28:57 */     this._namespace = (namespace.equals("") ? null : namespace);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Type typeCheck(SymbolTable stable)
/* 32:   */     throws TypeCheckError
/* 33:   */   {
/* 34:61 */     return this._type = Type.String;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String toString()
/* 38:   */   {
/* 39:65 */     return "literal-expr(" + this._value + ')';
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected boolean contextDependent()
/* 43:   */   {
/* 44:69 */     return false;
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected String getValue()
/* 48:   */   {
/* 49:73 */     return this._value;
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected String getNamespace()
/* 53:   */   {
/* 54:77 */     return this._namespace;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 58:   */   {
/* 59:81 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 60:82 */     InstructionList il = methodGen.getInstructionList();
/* 61:83 */     il.append(new PUSH(cpg, this._value));
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LiteralExpr
 * JD-Core Version:    0.7.0.1
 */