/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class ASTPair
/*  6:   */ {
/*  7:   */   public AST root;
/*  8:   */   public AST child;
/*  9:   */   
/* 10:   */   public final void advanceChildToEnd()
/* 11:   */   {
/* 12:23 */     if (this.child != null) {
/* 13:24 */       while (this.child.getNextSibling() != null) {
/* 14:25 */         this.child = this.child.getNextSibling();
/* 15:   */       }
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ASTPair copy()
/* 20:   */   {
/* 21:32 */     ASTPair localASTPair = new ASTPair();
/* 22:33 */     localASTPair.root = this.root;
/* 23:34 */     localASTPair.child = this.child;
/* 24:35 */     return localASTPair;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:39 */     String str1 = this.root == null ? "null" : this.root.getText();
/* 30:40 */     String str2 = this.child == null ? "null" : this.child.getText();
/* 31:41 */     return "[" + str1 + "," + str2 + "]";
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASTPair
 * JD-Core Version:    0.7.0.1
 */