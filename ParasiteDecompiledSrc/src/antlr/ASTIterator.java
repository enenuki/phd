/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class ASTIterator
/*  6:   */ {
/*  7:13 */   protected AST cursor = null;
/*  8:14 */   protected AST original = null;
/*  9:   */   
/* 10:   */   public ASTIterator(AST paramAST)
/* 11:   */   {
/* 12:18 */     this.original = (this.cursor = paramAST);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isSubtree(AST paramAST1, AST paramAST2)
/* 16:   */   {
/* 17:26 */     if (paramAST2 == null) {
/* 18:27 */       return true;
/* 19:   */     }
/* 20:31 */     if (paramAST1 == null)
/* 21:   */     {
/* 22:32 */       if (paramAST2 != null) {
/* 23:32 */         return false;
/* 24:   */       }
/* 25:33 */       return true;
/* 26:   */     }
/* 27:37 */     AST localAST = paramAST1;
/* 28:38 */     for (; (localAST != null) && (paramAST2 != null); paramAST2 = paramAST2.getNextSibling())
/* 29:   */     {
/* 30:41 */       if (localAST.getType() != paramAST2.getType()) {
/* 31:41 */         return false;
/* 32:   */       }
/* 33:43 */       if ((localAST.getFirstChild() != null) && 
/* 34:44 */         (!isSubtree(localAST.getFirstChild(), paramAST2.getFirstChild()))) {
/* 35:44 */         return false;
/* 36:   */       }
/* 37:39 */       localAST = localAST.getNextSibling();
/* 38:   */     }
/* 39:47 */     return true;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public AST next(AST paramAST)
/* 43:   */   {
/* 44:54 */     AST localAST = null;
/* 45:55 */     Object localObject = null;
/* 46:57 */     if (this.cursor == null) {
/* 47:58 */       return null;
/* 48:   */     }
/* 49:62 */     for (; this.cursor != null; this.cursor = this.cursor.getNextSibling()) {
/* 50:64 */       if (this.cursor.getType() == paramAST.getType()) {
/* 51:66 */         if ((this.cursor.getFirstChild() != null) && 
/* 52:67 */           (isSubtree(this.cursor.getFirstChild(), paramAST.getFirstChild()))) {
/* 53:68 */           return this.cursor;
/* 54:   */         }
/* 55:   */       }
/* 56:   */     }
/* 57:73 */     return localAST;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASTIterator
 * JD-Core Version:    0.7.0.1
 */