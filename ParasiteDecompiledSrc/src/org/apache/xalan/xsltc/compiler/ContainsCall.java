/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.IFLT;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  14:    */ 
/*  15:    */ final class ContainsCall
/*  16:    */   extends FunctionCall
/*  17:    */ {
/*  18: 43 */   private Expression _base = null;
/*  19: 44 */   private Expression _token = null;
/*  20:    */   
/*  21:    */   public ContainsCall(QName fname, Vector arguments)
/*  22:    */   {
/*  23: 50 */     super(fname, arguments);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isBoolean()
/*  27:    */   {
/*  28: 57 */     return true;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Type typeCheck(SymbolTable stable)
/*  32:    */     throws TypeCheckError
/*  33:    */   {
/*  34: 66 */     if (argumentCount() != 2) {
/*  35: 67 */       throw new TypeCheckError("ILLEGAL_ARG_ERR", getName(), this);
/*  36:    */     }
/*  37: 71 */     this._base = argument(0);
/*  38: 72 */     Type baseType = this._base.typeCheck(stable);
/*  39: 73 */     if (baseType != Type.String) {
/*  40: 74 */       this._base = new CastExpr(this._base, Type.String);
/*  41:    */     }
/*  42: 77 */     this._token = argument(1);
/*  43: 78 */     Type tokenType = this._token.typeCheck(stable);
/*  44: 79 */     if (tokenType != Type.String) {
/*  45: 80 */       this._token = new CastExpr(this._token, Type.String);
/*  46:    */     }
/*  47: 82 */     return this._type = Type.Boolean;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  51:    */   {
/*  52: 89 */     translateDesynthesized(classGen, methodGen);
/*  53: 90 */     synthesize(classGen, methodGen);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/*  57:    */   {
/*  58: 98 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  59: 99 */     InstructionList il = methodGen.getInstructionList();
/*  60:100 */     this._base.translate(classGen, methodGen);
/*  61:101 */     this._token.translate(classGen, methodGen);
/*  62:102 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "indexOf", "(Ljava/lang/String;)I")));
/*  63:    */     
/*  64:    */ 
/*  65:105 */     this._falseList.add(il.append(new IFLT(null)));
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ContainsCall
 * JD-Core Version:    0.7.0.1
 */