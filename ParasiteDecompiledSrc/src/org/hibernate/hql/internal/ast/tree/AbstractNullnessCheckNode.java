/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public abstract class AbstractNullnessCheckNode
/*  12:    */   extends UnaryLogicOperatorNode
/*  13:    */ {
/*  14:    */   public void initialize()
/*  15:    */   {
/*  16: 52 */     Type operandType = extractDataType(getOperand());
/*  17: 53 */     if (operandType == null) {
/*  18: 54 */       return;
/*  19:    */     }
/*  20: 56 */     SessionFactoryImplementor sessionFactory = getSessionFactoryHelper().getFactory();
/*  21: 57 */     int operandColumnSpan = operandType.getColumnSpan(sessionFactory);
/*  22: 58 */     if (operandColumnSpan > 1) {
/*  23: 59 */       mutateRowValueConstructorSyntax(operandColumnSpan);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected abstract int getExpansionConnectorType();
/*  28:    */   
/*  29:    */   protected abstract String getExpansionConnectorText();
/*  30:    */   
/*  31:    */   private void mutateRowValueConstructorSyntax(int operandColumnSpan)
/*  32:    */   {
/*  33: 80 */     int comparisonType = getType();
/*  34: 81 */     String comparisonText = getText();
/*  35:    */     
/*  36: 83 */     int expansionConnectorType = getExpansionConnectorType();
/*  37: 84 */     String expansionConnectorText = getExpansionConnectorText();
/*  38:    */     
/*  39: 86 */     setType(expansionConnectorType);
/*  40: 87 */     setText(expansionConnectorText);
/*  41:    */     
/*  42: 89 */     String[] mutationTexts = extractMutationTexts(getOperand(), operandColumnSpan);
/*  43:    */     
/*  44: 91 */     AST container = this;
/*  45: 92 */     for (int i = operandColumnSpan - 1; i > 0; i--) {
/*  46: 93 */       if (i == 1)
/*  47:    */       {
/*  48: 94 */         AST op1 = getASTFactory().create(comparisonType, comparisonText);
/*  49: 95 */         AST operand1 = getASTFactory().create(142, mutationTexts[0]);
/*  50: 96 */         op1.setFirstChild(operand1);
/*  51: 97 */         container.setFirstChild(op1);
/*  52: 98 */         AST op2 = getASTFactory().create(comparisonType, comparisonText);
/*  53: 99 */         AST operand2 = getASTFactory().create(142, mutationTexts[1]);
/*  54:100 */         op2.setFirstChild(operand2);
/*  55:101 */         op1.setNextSibling(op2);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:104 */         AST op = getASTFactory().create(comparisonType, comparisonText);
/*  60:105 */         AST operand = getASTFactory().create(142, mutationTexts[i]);
/*  61:106 */         op.setFirstChild(operand);
/*  62:107 */         AST newContainer = getASTFactory().create(expansionConnectorType, expansionConnectorText);
/*  63:108 */         container.setFirstChild(newContainer);
/*  64:109 */         newContainer.setNextSibling(op);
/*  65:110 */         container = newContainer;
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static Type extractDataType(Node operand)
/*  71:    */   {
/*  72:116 */     Type type = null;
/*  73:117 */     if ((operand instanceof SqlNode)) {
/*  74:118 */       type = ((SqlNode)operand).getDataType();
/*  75:    */     }
/*  76:120 */     if ((type == null) && ((operand instanceof ExpectedTypeAwareNode))) {
/*  77:121 */       type = ((ExpectedTypeAwareNode)operand).getExpectedType();
/*  78:    */     }
/*  79:123 */     return type;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static String[] extractMutationTexts(Node operand, int count)
/*  83:    */   {
/*  84:127 */     if ((operand instanceof ParameterNode))
/*  85:    */     {
/*  86:128 */       String[] rtn = new String[count];
/*  87:129 */       for (int i = 0; i < count; i++) {
/*  88:130 */         rtn[i] = "?";
/*  89:    */       }
/*  90:132 */       return rtn;
/*  91:    */     }
/*  92:134 */     if (operand.getType() == 92)
/*  93:    */     {
/*  94:135 */       String[] rtn = new String[operand.getNumberOfChildren()];
/*  95:136 */       int x = 0;
/*  96:137 */       AST node = operand.getFirstChild();
/*  97:138 */       while (node != null)
/*  98:    */       {
/*  99:139 */         rtn[(x++)] = node.getText();
/* 100:140 */         node = node.getNextSibling();
/* 101:    */       }
/* 102:142 */       return rtn;
/* 103:    */     }
/* 104:144 */     if ((operand instanceof SqlNode))
/* 105:    */     {
/* 106:145 */       String nodeText = operand.getText();
/* 107:146 */       if (nodeText.startsWith("(")) {
/* 108:147 */         nodeText = nodeText.substring(1);
/* 109:    */       }
/* 110:149 */       if (nodeText.endsWith(")")) {
/* 111:150 */         nodeText = nodeText.substring(0, nodeText.length() - 1);
/* 112:    */       }
/* 113:152 */       String[] splits = StringHelper.split(", ", nodeText);
/* 114:153 */       if (count != splits.length) {
/* 115:154 */         throw new HibernateException("SqlNode's text did not reference expected number of columns");
/* 116:    */       }
/* 117:156 */       return splits;
/* 118:    */     }
/* 119:159 */     throw new HibernateException("dont know how to extract row value elements from node : " + operand);
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AbstractNullnessCheckNode
 * JD-Core Version:    0.7.0.1
 */