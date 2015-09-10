/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.StringType;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 14:   */ 
/* 15:   */ final class UnparsedEntityUriCall
/* 16:   */   extends FunctionCall
/* 17:   */ {
/* 18:   */   private Expression _entity;
/* 19:   */   
/* 20:   */   public UnparsedEntityUriCall(QName fname, Vector arguments)
/* 21:   */   {
/* 22:44 */     super(fname, arguments);
/* 23:45 */     this._entity = argument();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Type typeCheck(SymbolTable stable)
/* 27:   */     throws TypeCheckError
/* 28:   */   {
/* 29:49 */     Type entity = this._entity.typeCheck(stable);
/* 30:50 */     if (!(entity instanceof StringType)) {
/* 31:51 */       this._entity = new CastExpr(this._entity, Type.String);
/* 32:   */     }
/* 33:53 */     return this._type = Type.String;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 37:   */   {
/* 38:57 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 39:58 */     InstructionList il = methodGen.getInstructionList();
/* 40:   */     
/* 41:60 */     il.append(methodGen.loadDOM());
/* 42:   */     
/* 43:62 */     this._entity.translate(classGen, methodGen);
/* 44:   */     
/* 45:64 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getUnparsedEntityURI", "(Ljava/lang/String;)Ljava/lang/String;"), 2));
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UnparsedEntityUriCall
 * JD-Core Version:    0.7.0.1
 */