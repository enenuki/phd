/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ public class IsNullLogicOperatorNode
/*  4:   */   extends AbstractNullnessCheckNode
/*  5:   */ {
/*  6:   */   protected int getExpansionConnectorType()
/*  7:   */   {
/*  8:35 */     return 6;
/*  9:   */   }
/* 10:   */   
/* 11:   */   protected String getExpansionConnectorText()
/* 12:   */   {
/* 13:39 */     return "AND";
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode
 * JD-Core Version:    0.7.0.1
 */