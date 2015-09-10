/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.SemanticException;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  11:    */ import org.hibernate.param.ParameterSpecification;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ 
/*  14:    */ public class InLogicOperatorNode
/*  15:    */   extends BinaryLogicOperatorNode
/*  16:    */   implements BinaryOperatorNode
/*  17:    */ {
/*  18:    */   public Node getInList()
/*  19:    */   {
/*  20: 44 */     return getRightHandOperand();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void initialize()
/*  24:    */     throws SemanticException
/*  25:    */   {
/*  26: 48 */     Node lhs = getLeftHandOperand();
/*  27: 49 */     if (lhs == null) {
/*  28: 50 */       throw new SemanticException("left-hand operand of in operator was null");
/*  29:    */     }
/*  30: 52 */     Node inList = getInList();
/*  31: 53 */     if (inList == null) {
/*  32: 54 */       throw new SemanticException("right-hand operand of in operator was null");
/*  33:    */     }
/*  34: 60 */     if (SqlNode.class.isAssignableFrom(lhs.getClass()))
/*  35:    */     {
/*  36: 61 */       Type lhsType = ((SqlNode)lhs).getDataType();
/*  37: 62 */       AST inListChild = inList.getFirstChild();
/*  38: 63 */       while (inListChild != null)
/*  39:    */       {
/*  40: 64 */         if (ExpectedTypeAwareNode.class.isAssignableFrom(inListChild.getClass())) {
/*  41: 65 */           ((ExpectedTypeAwareNode)inListChild).setExpectedType(lhsType);
/*  42:    */         }
/*  43: 67 */         inListChild = inListChild.getNextSibling();
/*  44:    */       }
/*  45:    */     }
/*  46: 70 */     SessionFactoryImplementor sessionFactory = getSessionFactoryHelper().getFactory();
/*  47: 71 */     if (sessionFactory.getDialect().supportsRowValueConstructorSyntaxInInList()) {
/*  48: 72 */       return;
/*  49:    */     }
/*  50: 74 */     Type lhsType = extractDataType(lhs);
/*  51: 75 */     if (lhsType == null) {
/*  52: 76 */       return;
/*  53:    */     }
/*  54: 77 */     int lhsColumnSpan = lhsType.getColumnSpan(sessionFactory);
/*  55: 78 */     Node rhsNode = (Node)inList.getFirstChild();
/*  56: 79 */     if (!isNodeAcceptable(rhsNode)) {
/*  57: 80 */       return;
/*  58:    */     }
/*  59: 81 */     int rhsColumnSpan = 0;
/*  60: 82 */     if (rhsNode.getType() == 92)
/*  61:    */     {
/*  62: 83 */       rhsColumnSpan = rhsNode.getNumberOfChildren();
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66: 85 */       Type rhsType = extractDataType(rhsNode);
/*  67: 86 */       if (rhsType == null) {
/*  68: 87 */         return;
/*  69:    */       }
/*  70: 88 */       rhsColumnSpan = rhsType.getColumnSpan(sessionFactory);
/*  71:    */     }
/*  72: 90 */     if ((lhsColumnSpan > 1) && (rhsColumnSpan > 1)) {
/*  73: 91 */       mutateRowValueConstructorSyntaxInInListSyntax(lhsColumnSpan, rhsColumnSpan);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   private boolean isNodeAcceptable(Node rhsNode)
/*  78:    */   {
/*  79: 99 */     return ((rhsNode instanceof LiteralNode)) || ((rhsNode instanceof ParameterNode)) || (rhsNode.getType() == 92);
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void mutateRowValueConstructorSyntaxInInListSyntax(int lhsColumnSpan, int rhsColumnSpan)
/*  83:    */   {
/*  84:116 */     String[] lhsElementTexts = extractMutationTexts(getLeftHandOperand(), lhsColumnSpan);
/*  85:    */     
/*  86:118 */     Node rhsNode = (Node)getInList().getFirstChild();
/*  87:    */     
/*  88:120 */     ParameterSpecification lhsEmbeddedCompositeParameterSpecification = (getLeftHandOperand() == null) || (!ParameterNode.class.isInstance(getLeftHandOperand())) ? null : ((ParameterNode)getLeftHandOperand()).getHqlParameterSpecification();
/*  89:129 */     if ((rhsNode != null) && (rhsNode.getNextSibling() == null))
/*  90:    */     {
/*  91:130 */       String[] rhsElementTexts = extractMutationTexts(rhsNode, rhsColumnSpan);
/*  92:    */       
/*  93:132 */       setType(6);
/*  94:133 */       setText("AND");
/*  95:134 */       ParameterSpecification rhsEmbeddedCompositeParameterSpecification = (rhsNode == null) || (!ParameterNode.class.isInstance(rhsNode)) ? null : ((ParameterNode)rhsNode).getHqlParameterSpecification();
/*  96:    */       
/*  97:    */ 
/*  98:    */ 
/*  99:138 */       translate(lhsColumnSpan, 102, "=", lhsElementTexts, rhsElementTexts, lhsEmbeddedCompositeParameterSpecification, rhsEmbeddedCompositeParameterSpecification, this);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:143 */       List andElementsNodeList = new ArrayList();
/* 104:144 */       while (rhsNode != null)
/* 105:    */       {
/* 106:145 */         String[] rhsElementTexts = extractMutationTexts(rhsNode, rhsColumnSpan);
/* 107:    */         
/* 108:147 */         AST and = getASTFactory().create(6, "AND");
/* 109:148 */         ParameterSpecification rhsEmbeddedCompositeParameterSpecification = (rhsNode == null) || (!ParameterNode.class.isInstance(rhsNode)) ? null : ((ParameterNode)rhsNode).getHqlParameterSpecification();
/* 110:    */         
/* 111:    */ 
/* 112:    */ 
/* 113:152 */         translate(lhsColumnSpan, 102, "=", lhsElementTexts, rhsElementTexts, lhsEmbeddedCompositeParameterSpecification, rhsEmbeddedCompositeParameterSpecification, and);
/* 114:    */         
/* 115:    */ 
/* 116:    */ 
/* 117:156 */         andElementsNodeList.add(and);
/* 118:157 */         rhsNode = (Node)rhsNode.getNextSibling();
/* 119:    */       }
/* 120:159 */       setType(40);
/* 121:160 */       setText("OR");
/* 122:161 */       AST curNode = this;
/* 123:162 */       for (int i = andElementsNodeList.size() - 1; i > 1; i--)
/* 124:    */       {
/* 125:163 */         AST or = getASTFactory().create(40, "OR");
/* 126:164 */         curNode.setFirstChild(or);
/* 127:165 */         curNode = or;
/* 128:166 */         AST and = (AST)andElementsNodeList.get(i);
/* 129:167 */         or.setNextSibling(and);
/* 130:    */       }
/* 131:169 */       AST node0 = (AST)andElementsNodeList.get(0);
/* 132:170 */       AST node1 = (AST)andElementsNodeList.get(1);
/* 133:171 */       node0.setNextSibling(node1);
/* 134:172 */       curNode.setFirstChild(node0);
/* 135:    */     }
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.InLogicOperatorNode
 * JD-Core Version:    0.7.0.1
 */