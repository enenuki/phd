/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ 
/*  6:   */ public class ASTAppender
/*  7:   */ {
/*  8:   */   private AST parent;
/*  9:   */   private AST last;
/* 10:   */   private ASTFactory factory;
/* 11:   */   
/* 12:   */   public ASTAppender(ASTFactory factory, AST parent)
/* 13:   */   {
/* 14:40 */     this(parent);
/* 15:41 */     this.factory = factory;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ASTAppender(AST parent)
/* 19:   */   {
/* 20:45 */     this.parent = parent;
/* 21:46 */     this.last = ASTUtil.getLastChild(parent);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public AST append(int type, String text, boolean appendIfEmpty)
/* 25:   */   {
/* 26:50 */     if ((text != null) && ((appendIfEmpty) || (text.length() > 0))) {
/* 27:51 */       return append(this.factory.create(type, text));
/* 28:   */     }
/* 29:54 */     return null;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public AST append(AST child)
/* 33:   */   {
/* 34:59 */     if (this.last == null) {
/* 35:60 */       this.parent.setFirstChild(child);
/* 36:   */     } else {
/* 37:63 */       this.last.setNextSibling(child);
/* 38:   */     }
/* 39:65 */     this.last = child;
/* 40:66 */     return this.last;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ASTAppender
 * JD-Core Version:    0.7.0.1
 */