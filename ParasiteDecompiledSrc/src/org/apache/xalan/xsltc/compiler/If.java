/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.bcel.generic.InstructionConstants;
/*   5:    */ import org.apache.bcel.generic.InstructionHandle;
/*   6:    */ import org.apache.bcel.generic.InstructionList;
/*   7:    */ import org.apache.bcel.generic.MethodGen;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  14:    */ 
/*  15:    */ final class If
/*  16:    */   extends Instruction
/*  17:    */ {
/*  18:    */   private Expression _test;
/*  19: 42 */   private boolean _ignore = false;
/*  20:    */   
/*  21:    */   public void display(int indent)
/*  22:    */   {
/*  23: 48 */     indent(indent);
/*  24: 49 */     Util.println("If");
/*  25: 50 */     indent(indent + 4);
/*  26: 51 */     System.out.print("test ");
/*  27: 52 */     Util.println(this._test.toString());
/*  28: 53 */     displayContents(indent + 4);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void parseContents(Parser parser)
/*  32:    */   {
/*  33: 61 */     this._test = parser.parseExpression(this, "test", null);
/*  34: 64 */     if (this._test.isDummy())
/*  35:    */     {
/*  36: 65 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "test");
/*  37: 66 */       return;
/*  38:    */     }
/*  39: 71 */     Object result = this._test.evaluateAtCompileTime();
/*  40: 72 */     if ((result != null) && ((result instanceof Boolean))) {
/*  41: 73 */       this._ignore = (!((Boolean)result).booleanValue());
/*  42:    */     }
/*  43: 76 */     parseChildren(parser);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type typeCheck(SymbolTable stable)
/*  47:    */     throws TypeCheckError
/*  48:    */   {
/*  49: 85 */     if (!(this._test.typeCheck(stable) instanceof BooleanType)) {
/*  50: 86 */       this._test = new CastExpr(this._test, Type.Boolean);
/*  51:    */     }
/*  52: 89 */     if (!this._ignore) {
/*  53: 90 */       typeCheckContents(stable);
/*  54:    */     }
/*  55: 92 */     return Type.Void;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  59:    */   {
/*  60:100 */     InstructionList il = methodGen.getInstructionList();
/*  61:101 */     this._test.translateDesynthesized(classGen, methodGen);
/*  62:    */     
/*  63:103 */     InstructionHandle truec = il.getEnd();
/*  64:104 */     if (!this._ignore) {
/*  65:105 */       translateContents(classGen, methodGen);
/*  66:    */     }
/*  67:107 */     this._test.backPatchFalseList(il.append(InstructionConstants.NOP));
/*  68:108 */     this._test.backPatchTrueList(truec.getNext());
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.If
 * JD-Core Version:    0.7.0.1
 */