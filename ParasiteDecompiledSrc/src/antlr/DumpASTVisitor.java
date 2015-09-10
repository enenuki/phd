/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ 
/*  6:   */ public class DumpASTVisitor
/*  7:   */   implements ASTVisitor
/*  8:   */ {
/*  9:16 */   protected int level = 0;
/* 10:   */   
/* 11:   */   private void tabs()
/* 12:   */   {
/* 13:20 */     for (int i = 0; i < this.level; i++) {
/* 14:21 */       System.out.print("   ");
/* 15:   */     }
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void visit(AST paramAST)
/* 19:   */   {
/* 20:27 */     int i = 0;
/* 21:29 */     for (AST localAST = paramAST; localAST != null; localAST = localAST.getNextSibling()) {
/* 22:30 */       if (localAST.getFirstChild() != null)
/* 23:   */       {
/* 24:31 */         i = 0;
/* 25:32 */         break;
/* 26:   */       }
/* 27:   */     }
/* 28:36 */     for (localAST = paramAST; localAST != null; localAST = localAST.getNextSibling())
/* 29:   */     {
/* 30:37 */       if ((i == 0) || (localAST == paramAST)) {
/* 31:38 */         tabs();
/* 32:   */       }
/* 33:40 */       if (localAST.getText() == null) {
/* 34:41 */         System.out.print("nil");
/* 35:   */       } else {
/* 36:44 */         System.out.print(localAST.getText());
/* 37:   */       }
/* 38:47 */       System.out.print(" [" + localAST.getType() + "] ");
/* 39:49 */       if (i != 0) {
/* 40:50 */         System.out.print(" ");
/* 41:   */       } else {
/* 42:53 */         System.out.println("");
/* 43:   */       }
/* 44:56 */       if (localAST.getFirstChild() != null)
/* 45:   */       {
/* 46:57 */         this.level += 1;
/* 47:58 */         visit(localAST.getFirstChild());
/* 48:59 */         this.level -= 1;
/* 49:   */       }
/* 50:   */     }
/* 51:63 */     if (i != 0) {
/* 52:64 */       System.out.println("");
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DumpASTVisitor
 * JD-Core Version:    0.7.0.1
 */