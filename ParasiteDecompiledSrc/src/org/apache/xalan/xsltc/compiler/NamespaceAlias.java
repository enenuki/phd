/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  4:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  7:   */ 
/*  8:   */ final class NamespaceAlias
/*  9:   */   extends TopLevelElement
/* 10:   */ {
/* 11:   */   private String sPrefix;
/* 12:   */   private String rPrefix;
/* 13:   */   
/* 14:   */   public void parseContents(Parser parser)
/* 15:   */   {
/* 16:43 */     this.sPrefix = getAttribute("stylesheet-prefix");
/* 17:44 */     this.rPrefix = getAttribute("result-prefix");
/* 18:45 */     parser.getSymbolTable().addPrefixAlias(this.sPrefix, this.rPrefix);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Type typeCheck(SymbolTable stable)
/* 22:   */     throws TypeCheckError
/* 23:   */   {
/* 24:49 */     return Type.Void;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NamespaceAlias
 * JD-Core Version:    0.7.0.1
 */