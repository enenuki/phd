/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class UnaryArithmeticNode
/*  8:   */   extends AbstractSelectExpression
/*  9:   */   implements UnaryOperatorNode
/* 10:   */ {
/* 11:   */   public Type getDataType()
/* 12:   */   {
/* 13:36 */     return ((SqlNode)getOperand()).getDataType();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setScalarColumnText(int i)
/* 17:   */     throws SemanticException
/* 18:   */   {
/* 19:40 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void initialize() {}
/* 23:   */   
/* 24:   */   public Node getOperand()
/* 25:   */   {
/* 26:49 */     return (Node)getFirstChild();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.UnaryArithmeticNode
 * JD-Core Version:    0.7.0.1
 */