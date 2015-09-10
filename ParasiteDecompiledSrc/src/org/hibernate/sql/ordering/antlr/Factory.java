/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ 
/*  5:   */ public class Factory
/*  6:   */   extends ASTFactory
/*  7:   */   implements OrderByTemplateTokenTypes
/*  8:   */ {
/*  9:   */   public Class getASTNodeType(int i)
/* 10:   */   {
/* 11:38 */     switch (i)
/* 12:   */     {
/* 13:   */     case 4: 
/* 14:40 */       return OrderByFragment.class;
/* 15:   */     case 5: 
/* 16:42 */       return SortSpecification.class;
/* 17:   */     case 6: 
/* 18:44 */       return OrderingSpecification.class;
/* 19:   */     case 12: 
/* 20:46 */       return CollationSpecification.class;
/* 21:   */     case 7: 
/* 22:48 */       return SortKey.class;
/* 23:   */     }
/* 24:50 */     return NodeSupport.class;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.Factory
 * JD-Core Version:    0.7.0.1
 */