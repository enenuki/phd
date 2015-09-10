/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ public class IsNotNullLogicOperatorNode
/*  4:   */   extends AbstractNullnessCheckNode
/*  5:   */ {
/*  6:   */   protected int getExpansionConnectorType()
/*  7:   */   {
/*  8:35 */     return 40;
/*  9:   */   }
/* 10:   */   
/* 11:   */   protected String getExpansionConnectorText()
/* 12:   */   {
/* 13:39 */     return "OR";
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode
 * JD-Core Version:    0.7.0.1
 */