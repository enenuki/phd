/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.type.StandardBasicTypes;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class BetweenOperatorNode
/*  9:   */   extends SqlNode
/* 10:   */   implements OperatorNode
/* 11:   */ {
/* 12:   */   public void initialize()
/* 13:   */     throws SemanticException
/* 14:   */   {
/* 15:39 */     Node fixture = getFixtureOperand();
/* 16:40 */     if (fixture == null) {
/* 17:41 */       throw new SemanticException("fixture operand of a between operator was null");
/* 18:   */     }
/* 19:43 */     Node low = getLowOperand();
/* 20:44 */     if (low == null) {
/* 21:45 */       throw new SemanticException("low operand of a between operator was null");
/* 22:   */     }
/* 23:47 */     Node high = getHighOperand();
/* 24:48 */     if (high == null) {
/* 25:49 */       throw new SemanticException("high operand of a between operator was null");
/* 26:   */     }
/* 27:51 */     check(fixture, low, high);
/* 28:52 */     check(low, high, fixture);
/* 29:53 */     check(high, fixture, low);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Type getDataType()
/* 33:   */   {
/* 34:58 */     return StandardBasicTypes.BOOLEAN;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Node getFixtureOperand()
/* 38:   */   {
/* 39:62 */     return (Node)getFirstChild();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Node getLowOperand()
/* 43:   */   {
/* 44:66 */     return (Node)getFirstChild().getNextSibling();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Node getHighOperand()
/* 48:   */   {
/* 49:70 */     return (Node)getFirstChild().getNextSibling().getNextSibling();
/* 50:   */   }
/* 51:   */   
/* 52:   */   private void check(Node check, Node first, Node second)
/* 53:   */   {
/* 54:74 */     if (ExpectedTypeAwareNode.class.isAssignableFrom(check.getClass()))
/* 55:   */     {
/* 56:75 */       Type expectedType = null;
/* 57:76 */       if (SqlNode.class.isAssignableFrom(first.getClass())) {
/* 58:77 */         expectedType = ((SqlNode)first).getDataType();
/* 59:   */       }
/* 60:79 */       if ((expectedType == null) && (SqlNode.class.isAssignableFrom(second.getClass()))) {
/* 61:80 */         expectedType = ((SqlNode)second).getDataType();
/* 62:   */       }
/* 63:82 */       ((ExpectedTypeAwareNode)check).setExpectedType(expectedType);
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.BetweenOperatorNode
 * JD-Core Version:    0.7.0.1
 */