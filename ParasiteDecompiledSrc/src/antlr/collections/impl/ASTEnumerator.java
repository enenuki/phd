/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import antlr.collections.ASTEnumeration;
/*  5:   */ import java.util.NoSuchElementException;
/*  6:   */ 
/*  7:   */ public class ASTEnumerator
/*  8:   */   implements ASTEnumeration
/*  9:   */ {
/* 10:   */   VectorEnumerator nodes;
/* 11:19 */   int i = 0;
/* 12:   */   
/* 13:   */   public ASTEnumerator(Vector paramVector)
/* 14:   */   {
/* 15:23 */     this.nodes = new VectorEnumerator(paramVector);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasMoreNodes()
/* 19:   */   {
/* 20:27 */     synchronized (this.nodes)
/* 21:   */     {
/* 22:28 */       return this.i <= this.nodes.vector.lastElement;
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public AST nextNode()
/* 27:   */   {
/* 28:33 */     synchronized (this.nodes)
/* 29:   */     {
/* 30:34 */       if (this.i <= this.nodes.vector.lastElement) {
/* 31:35 */         return (AST)this.nodes.vector.data[(this.i++)];
/* 32:   */       }
/* 33:37 */       throw new NoSuchElementException("ASTEnumerator");
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.ASTEnumerator
 * JD-Core Version:    0.7.0.1
 */