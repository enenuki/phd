/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.ILOAD;
/*  7:   */ import org.apache.bcel.generic.INVOKESTATIC;
/*  8:   */ import org.apache.bcel.generic.InstructionList;
/*  9:   */ import org.apache.bcel.generic.MethodGen;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.FilterGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.StringType;
/* 14:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 15:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 16:   */ 
/* 17:   */ final class LangCall
/* 18:   */   extends FunctionCall
/* 19:   */ {
/* 20:   */   private Expression _lang;
/* 21:   */   private Type _langType;
/* 22:   */   
/* 23:   */   public LangCall(QName fname, Vector arguments)
/* 24:   */   {
/* 25:49 */     super(fname, arguments);
/* 26:50 */     this._lang = argument(0);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Type typeCheck(SymbolTable stable)
/* 30:   */     throws TypeCheckError
/* 31:   */   {
/* 32:57 */     this._langType = this._lang.typeCheck(stable);
/* 33:58 */     if (!(this._langType instanceof StringType)) {
/* 34:59 */       this._lang = new CastExpr(this._lang, Type.String);
/* 35:   */     }
/* 36:61 */     return Type.Boolean;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Type getType()
/* 40:   */   {
/* 41:68 */     return Type.Boolean;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 45:   */   {
/* 46:77 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 47:78 */     InstructionList il = methodGen.getInstructionList();
/* 48:   */     
/* 49:80 */     int tst = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "testLanguage", "(Ljava/lang/String;Lorg/apache/xalan/xsltc/DOM;I)Z");
/* 50:   */     
/* 51:   */ 
/* 52:83 */     this._lang.translate(classGen, methodGen);
/* 53:84 */     il.append(methodGen.loadDOM());
/* 54:85 */     if ((classGen instanceof FilterGenerator)) {
/* 55:86 */       il.append(new ILOAD(1));
/* 56:   */     } else {
/* 57:88 */       il.append(methodGen.loadContextNode());
/* 58:   */     }
/* 59:89 */     il.append(new INVOKESTATIC(tst));
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LangCall
 * JD-Core Version:    0.7.0.1
 */