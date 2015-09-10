/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.util.Stack;
/*  5:   */ 
/*  6:   */ public class NodeTraverser
/*  7:   */ {
/*  8:   */   private final VisitationStrategy strategy;
/*  9:   */   
/* 10:   */   public NodeTraverser(VisitationStrategy strategy)
/* 11:   */   {
/* 12:47 */     this.strategy = strategy;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void traverseDepthFirst(AST ast)
/* 16:   */   {
/* 17:62 */     if (ast == null) {
/* 18:63 */       throw new IllegalArgumentException("node to traverse cannot be null!");
/* 19:   */     }
/* 20:66 */     visitDepthFirst(ast.getFirstChild());
/* 21:   */   }
/* 22:   */   
/* 23:   */   private void visitDepthFirst(AST ast)
/* 24:   */   {
/* 25:70 */     if (ast == null) {
/* 26:71 */       return;
/* 27:   */     }
/* 28:73 */     Stack stack = new Stack();
/* 29:74 */     if (ast != null)
/* 30:   */     {
/* 31:75 */       stack.push(ast);
/* 32:76 */       while (!stack.empty())
/* 33:   */       {
/* 34:77 */         ast = (AST)stack.pop();
/* 35:78 */         this.strategy.visit(ast);
/* 36:79 */         if (ast.getNextSibling() != null) {
/* 37:80 */           stack.push(ast.getNextSibling());
/* 38:   */         }
/* 39:81 */         if (ast.getFirstChild() != null) {
/* 40:82 */           stack.push(ast.getFirstChild());
/* 41:   */         }
/* 42:   */       }
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static abstract interface VisitationStrategy
/* 47:   */   {
/* 48:   */     public abstract void visit(AST paramAST);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.NodeTraverser
 * JD-Core Version:    0.7.0.1
 */