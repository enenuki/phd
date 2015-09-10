/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  4:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  8:   */ 
/*  9:   */ final class UnresolvedRef
/* 10:   */   extends VariableRefBase
/* 11:   */ {
/* 12:35 */   private QName _variableName = null;
/* 13:36 */   private VariableRefBase _ref = null;
/* 14:   */   
/* 15:   */   public UnresolvedRef(QName name)
/* 16:   */   {
/* 17:40 */     this._variableName = name;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public QName getName()
/* 21:   */   {
/* 22:44 */     return this._variableName;
/* 23:   */   }
/* 24:   */   
/* 25:   */   private ErrorMsg reportError()
/* 26:   */   {
/* 27:48 */     ErrorMsg err = new ErrorMsg("VARIABLE_UNDEF_ERR", this._variableName, this);
/* 28:   */     
/* 29:50 */     getParser().reportError(3, err);
/* 30:51 */     return err;
/* 31:   */   }
/* 32:   */   
/* 33:   */   private VariableRefBase resolve(Parser parser, SymbolTable stable)
/* 34:   */   {
/* 35:57 */     VariableBase ref = parser.lookupVariable(this._variableName);
/* 36:58 */     if (ref == null) {
/* 37:59 */       ref = (VariableBase)stable.lookupName(this._variableName);
/* 38:   */     }
/* 39:61 */     if (ref == null)
/* 40:   */     {
/* 41:62 */       reportError();
/* 42:63 */       return null;
/* 43:   */     }
/* 44:67 */     this._variable = ref;
/* 45:68 */     addParentDependency();
/* 46:70 */     if ((ref instanceof Variable)) {
/* 47:71 */       return new VariableRef((Variable)ref);
/* 48:   */     }
/* 49:73 */     if ((ref instanceof Param)) {
/* 50:74 */       return new ParameterRef((Param)ref);
/* 51:   */     }
/* 52:76 */     return null;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Type typeCheck(SymbolTable stable)
/* 56:   */     throws TypeCheckError
/* 57:   */   {
/* 58:   */     ErrorMsg err;
/* 59:80 */     if (this._ref != null)
/* 60:   */     {
/* 61:81 */       String name = this._variableName.toString();
/* 62:82 */       err = new ErrorMsg("CIRCULAR_VARIABLE_ERR", name, this);
/* 63:   */     }
/* 64:85 */     if ((this._ref = resolve(getParser(), stable)) != null) {
/* 65:86 */       return this._type = this._ref.typeCheck(stable);
/* 66:   */     }
/* 67:88 */     throw new TypeCheckError(reportError());
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 71:   */   {
/* 72:92 */     if (this._ref != null) {
/* 73:93 */       this._ref.translate(classGen, methodGen);
/* 74:   */     } else {
/* 75:95 */       reportError();
/* 76:   */     }
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String toString()
/* 80:   */   {
/* 81:99 */     return "unresolved-ref()";
/* 82:   */   }
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UnresolvedRef
 * JD-Core Version:    0.7.0.1
 */