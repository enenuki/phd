/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.InstructionList;
/*  5:   */ import org.apache.bcel.generic.MethodGen;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.Util;
/* 12:   */ 
/* 13:   */ class TopLevelElement
/* 14:   */   extends SyntaxTreeNode
/* 15:   */ {
/* 16:40 */   protected Vector _dependencies = null;
/* 17:   */   
/* 18:   */   public Type typeCheck(SymbolTable stable)
/* 19:   */     throws TypeCheckError
/* 20:   */   {
/* 21:46 */     return typeCheckContents(stable);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 25:   */   {
/* 26:53 */     ErrorMsg msg = new ErrorMsg("NOT_IMPLEMENTED_ERR", getClass(), this);
/* 27:   */     
/* 28:55 */     getParser().reportError(2, msg);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public InstructionList compile(ClassGenerator classGen, MethodGenerator methodGen)
/* 32:   */   {
/* 33:64 */     InstructionList save = methodGen.getInstructionList();
/* 34:   */     InstructionList result;
/* 35:65 */     methodGen.setInstructionList(result = new InstructionList());
/* 36:66 */     translate(classGen, methodGen);
/* 37:67 */     methodGen.setInstructionList(save);
/* 38:68 */     return result;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void display(int indent)
/* 42:   */   {
/* 43:72 */     indent(indent);
/* 44:73 */     Util.println("TopLevelElement");
/* 45:74 */     displayContents(indent + 4);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void addDependency(TopLevelElement other)
/* 49:   */   {
/* 50:82 */     if (this._dependencies == null) {
/* 51:83 */       this._dependencies = new Vector();
/* 52:   */     }
/* 53:85 */     if (!this._dependencies.contains(other)) {
/* 54:86 */       this._dependencies.addElement(other);
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Vector getDependencies()
/* 59:   */   {
/* 60:95 */     return this._dependencies;
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.TopLevelElement
 * JD-Core Version:    0.7.0.1
 */