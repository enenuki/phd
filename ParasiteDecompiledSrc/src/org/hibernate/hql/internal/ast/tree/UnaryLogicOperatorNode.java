/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.StandardBasicTypes;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class UnaryLogicOperatorNode
/*  7:   */   extends HqlSqlWalkerNode
/*  8:   */   implements UnaryOperatorNode
/*  9:   */ {
/* 10:   */   public Node getOperand()
/* 11:   */   {
/* 12:36 */     return (Node)getFirstChild();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void initialize() {}
/* 16:   */   
/* 17:   */   public Type getDataType()
/* 18:   */   {
/* 19:46 */     return StandardBasicTypes.BOOLEAN;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.UnaryLogicOperatorNode
 * JD-Core Version:    0.7.0.1
 */