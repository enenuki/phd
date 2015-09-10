/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TreeSpecifierNode
/*  4:   */ {
/*  5:11 */   private TreeSpecifierNode parent = null;
/*  6:12 */   private TreeSpecifierNode firstChild = null;
/*  7:13 */   private TreeSpecifierNode nextSibling = null;
/*  8:   */   private Token tok;
/*  9:   */   
/* 10:   */   TreeSpecifierNode(Token paramToken)
/* 11:   */   {
/* 12:18 */     this.tok = paramToken;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public TreeSpecifierNode getFirstChild()
/* 16:   */   {
/* 17:22 */     return this.firstChild;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public TreeSpecifierNode getNextSibling()
/* 21:   */   {
/* 22:26 */     return this.nextSibling;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public TreeSpecifierNode getParent()
/* 26:   */   {
/* 27:31 */     return this.parent;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Token getToken()
/* 31:   */   {
/* 32:35 */     return this.tok;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setFirstChild(TreeSpecifierNode paramTreeSpecifierNode)
/* 36:   */   {
/* 37:39 */     this.firstChild = paramTreeSpecifierNode;
/* 38:40 */     paramTreeSpecifierNode.parent = this;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setNextSibling(TreeSpecifierNode paramTreeSpecifierNode)
/* 42:   */   {
/* 43:45 */     this.nextSibling = paramTreeSpecifierNode;
/* 44:46 */     paramTreeSpecifierNode.parent = this.parent;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TreeSpecifierNode
 * JD-Core Version:    0.7.0.1
 */