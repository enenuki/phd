/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ 
/*  7:   */ public class ASTIterator
/*  8:   */   implements Iterator
/*  9:   */ {
/* 10:   */   private AST next;
/* 11:   */   private AST current;
/* 12:38 */   private LinkedList parents = new LinkedList();
/* 13:   */   
/* 14:   */   public void remove()
/* 15:   */   {
/* 16:41 */     throw new UnsupportedOperationException("remove() is not supported");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean hasNext()
/* 20:   */   {
/* 21:45 */     return this.next != null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object next()
/* 25:   */   {
/* 26:49 */     return nextNode();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ASTIterator(AST tree)
/* 30:   */   {
/* 31:53 */     this.next = tree;
/* 32:54 */     down();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public AST nextNode()
/* 36:   */   {
/* 37:58 */     this.current = this.next;
/* 38:59 */     if (this.next != null)
/* 39:   */     {
/* 40:60 */       AST nextSibling = this.next.getNextSibling();
/* 41:61 */       if (nextSibling == null)
/* 42:   */       {
/* 43:62 */         this.next = pop();
/* 44:   */       }
/* 45:   */       else
/* 46:   */       {
/* 47:65 */         this.next = nextSibling;
/* 48:66 */         down();
/* 49:   */       }
/* 50:   */     }
/* 51:69 */     return this.current;
/* 52:   */   }
/* 53:   */   
/* 54:   */   private void down()
/* 55:   */   {
/* 56:73 */     while ((this.next != null) && (this.next.getFirstChild() != null))
/* 57:   */     {
/* 58:74 */       push(this.next);
/* 59:75 */       this.next = this.next.getFirstChild();
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   private void push(AST parent)
/* 64:   */   {
/* 65:80 */     this.parents.addFirst(parent);
/* 66:   */   }
/* 67:   */   
/* 68:   */   private AST pop()
/* 69:   */   {
/* 70:84 */     if (this.parents.size() == 0) {
/* 71:85 */       return null;
/* 72:   */     }
/* 73:88 */     return (AST)this.parents.removeFirst();
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ASTIterator
 * JD-Core Version:    0.7.0.1
 */