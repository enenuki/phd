/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ 
/*  7:   */ public class ASTParentsFirstIterator
/*  8:   */   implements Iterator
/*  9:   */ {
/* 10:   */   private AST next;
/* 11:   */   private AST current;
/* 12:   */   private AST tree;
/* 13:38 */   private LinkedList parents = new LinkedList();
/* 14:   */   
/* 15:   */   public void remove()
/* 16:   */   {
/* 17:41 */     throw new UnsupportedOperationException("remove() is not supported");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasNext()
/* 21:   */   {
/* 22:45 */     return this.next != null;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object next()
/* 26:   */   {
/* 27:49 */     return nextNode();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ASTParentsFirstIterator(AST tree)
/* 31:   */   {
/* 32:53 */     this.tree = (this.next = tree);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public AST nextNode()
/* 36:   */   {
/* 37:57 */     this.current = this.next;
/* 38:58 */     if (this.next != null)
/* 39:   */     {
/* 40:59 */       AST child = this.next.getFirstChild();
/* 41:60 */       if (child == null)
/* 42:   */       {
/* 43:61 */         AST sibling = this.next.getNextSibling();
/* 44:62 */         if (sibling == null)
/* 45:   */         {
/* 46:63 */           AST parent = pop();
/* 47:64 */           while ((parent != null) && (parent.getNextSibling() == null)) {
/* 48:65 */             parent = pop();
/* 49:   */           }
/* 50:66 */           this.next = (parent != null ? parent.getNextSibling() : null);
/* 51:   */         }
/* 52:   */         else
/* 53:   */         {
/* 54:69 */           this.next = sibling;
/* 55:   */         }
/* 56:   */       }
/* 57:   */       else
/* 58:   */       {
/* 59:73 */         if (this.next != this.tree) {
/* 60:74 */           push(this.next);
/* 61:   */         }
/* 62:76 */         this.next = child;
/* 63:   */       }
/* 64:   */     }
/* 65:79 */     return this.current;
/* 66:   */   }
/* 67:   */   
/* 68:   */   private void push(AST parent)
/* 69:   */   {
/* 70:83 */     this.parents.addFirst(parent);
/* 71:   */   }
/* 72:   */   
/* 73:   */   private AST pop()
/* 74:   */   {
/* 75:87 */     if (this.parents.size() == 0) {
/* 76:88 */       return null;
/* 77:   */     }
/* 78:91 */     return (AST)this.parents.removeFirst();
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ASTParentsFirstIterator
 * JD-Core Version:    0.7.0.1
 */