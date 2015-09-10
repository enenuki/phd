/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.SyntaxTreeNode;
/*  4:   */ 
/*  5:   */ public class TypeCheckError
/*  6:   */   extends Exception
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 3246224233917854640L;
/*  9:32 */   ErrorMsg _error = null;
/* 10:33 */   SyntaxTreeNode _node = null;
/* 11:   */   
/* 12:   */   public TypeCheckError(SyntaxTreeNode node)
/* 13:   */   {
/* 14:37 */     this._node = node;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TypeCheckError(ErrorMsg error)
/* 18:   */   {
/* 19:42 */     this._error = error;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public TypeCheckError(String code, Object param)
/* 23:   */   {
/* 24:47 */     this._error = new ErrorMsg(code, param);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public TypeCheckError(String code, Object param1, Object param2)
/* 28:   */   {
/* 29:52 */     this._error = new ErrorMsg(code, param1, param2);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ErrorMsg getErrorMsg()
/* 33:   */   {
/* 34:56 */     return this._error;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getMessage()
/* 38:   */   {
/* 39:60 */     return toString();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String toString()
/* 43:   */   {
/* 44:66 */     if (this._error == null) {
/* 45:67 */       if (this._node != null) {
/* 46:68 */         this._error = new ErrorMsg("TYPE_CHECK_ERR", this._node.toString());
/* 47:   */       } else {
/* 48:71 */         this._error = new ErrorMsg("TYPE_CHECK_UNK_LOC_ERR");
/* 49:   */       }
/* 50:   */     }
/* 51:75 */     return this._error.toString();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.TypeCheckError
 * JD-Core Version:    0.7.0.1
 */