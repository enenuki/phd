/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*   5:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   6:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   7:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  11:    */ 
/*  12:    */ final class When
/*  13:    */   extends Instruction
/*  14:    */ {
/*  15:    */   private Expression _test;
/*  16: 40 */   private boolean _ignore = false;
/*  17:    */   
/*  18:    */   public void display(int indent)
/*  19:    */   {
/*  20: 43 */     indent(indent);
/*  21: 44 */     Util.println("When");
/*  22: 45 */     indent(indent + 4);
/*  23: 46 */     System.out.print("test ");
/*  24: 47 */     Util.println(this._test.toString());
/*  25: 48 */     displayContents(indent + 4);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Expression getTest()
/*  29:    */   {
/*  30: 52 */     return this._test;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean ignore()
/*  34:    */   {
/*  35: 56 */     return this._ignore;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void parseContents(Parser parser)
/*  39:    */   {
/*  40: 60 */     this._test = parser.parseExpression(this, "test", null);
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44: 64 */     Object result = this._test.evaluateAtCompileTime();
/*  45: 65 */     if ((result != null) && ((result instanceof Boolean))) {
/*  46: 66 */       this._ignore = (!((Boolean)result).booleanValue());
/*  47:    */     }
/*  48: 69 */     parseChildren(parser);
/*  49: 72 */     if (this._test.isDummy()) {
/*  50: 73 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "test");
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Type typeCheck(SymbolTable stable)
/*  55:    */     throws TypeCheckError
/*  56:    */   {
/*  57: 86 */     if (!(this._test.typeCheck(stable) instanceof BooleanType)) {
/*  58: 87 */       this._test = new CastExpr(this._test, Type.Boolean);
/*  59:    */     }
/*  60: 90 */     if (!this._ignore) {
/*  61: 91 */       typeCheckContents(stable);
/*  62:    */     }
/*  63: 94 */     return Type.Void;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  67:    */   {
/*  68:102 */     ErrorMsg msg = new ErrorMsg("STRAY_WHEN_ERR", this);
/*  69:103 */     getParser().reportError(3, msg);
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.When
 * JD-Core Version:    0.7.0.1
 */