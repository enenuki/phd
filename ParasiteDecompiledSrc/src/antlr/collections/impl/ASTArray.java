/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class ASTArray
/*  6:   */ {
/*  7:18 */   public int size = 0;
/*  8:   */   public AST[] array;
/*  9:   */   
/* 10:   */   public ASTArray(int paramInt)
/* 11:   */   {
/* 12:23 */     this.array = new AST[paramInt];
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ASTArray add(AST paramAST)
/* 16:   */   {
/* 17:27 */     this.array[(this.size++)] = paramAST;
/* 18:28 */     return this;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.ASTArray
 * JD-Core Version:    0.7.0.1
 */