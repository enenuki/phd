/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.SemanticException;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.TypeMismatchException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.param.ParameterSpecification;
/*  13:    */ import org.hibernate.type.OneToOneType;
/*  14:    */ import org.hibernate.type.StandardBasicTypes;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public class BinaryLogicOperatorNode
/*  18:    */   extends HqlSqlWalkerNode
/*  19:    */   implements BinaryOperatorNode
/*  20:    */ {
/*  21:    */   public void initialize()
/*  22:    */     throws SemanticException
/*  23:    */   {
/*  24: 50 */     Node lhs = getLeftHandOperand();
/*  25: 51 */     if (lhs == null) {
/*  26: 52 */       throw new SemanticException("left-hand operand of a binary operator was null");
/*  27:    */     }
/*  28: 54 */     Node rhs = getRightHandOperand();
/*  29: 55 */     if (rhs == null) {
/*  30: 56 */       throw new SemanticException("right-hand operand of a binary operator was null");
/*  31:    */     }
/*  32: 59 */     Type lhsType = extractDataType(lhs);
/*  33: 60 */     Type rhsType = extractDataType(rhs);
/*  34: 62 */     if (lhsType == null) {
/*  35: 63 */       lhsType = rhsType;
/*  36:    */     }
/*  37: 65 */     if (rhsType == null) {
/*  38: 66 */       rhsType = lhsType;
/*  39:    */     }
/*  40: 69 */     if (ExpectedTypeAwareNode.class.isAssignableFrom(lhs.getClass())) {
/*  41: 70 */       ((ExpectedTypeAwareNode)lhs).setExpectedType(rhsType);
/*  42:    */     }
/*  43: 72 */     if (ExpectedTypeAwareNode.class.isAssignableFrom(rhs.getClass())) {
/*  44: 73 */       ((ExpectedTypeAwareNode)rhs).setExpectedType(lhsType);
/*  45:    */     }
/*  46: 76 */     mutateRowValueConstructorSyntaxesIfNecessary(lhsType, rhsType);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected final void mutateRowValueConstructorSyntaxesIfNecessary(Type lhsType, Type rhsType)
/*  50:    */   {
/*  51: 83 */     SessionFactoryImplementor sessionFactory = getSessionFactoryHelper().getFactory();
/*  52: 84 */     if ((lhsType != null) && (rhsType != null))
/*  53:    */     {
/*  54: 85 */       int lhsColumnSpan = getColumnSpan(lhsType, sessionFactory);
/*  55: 86 */       if (lhsColumnSpan != getColumnSpan(rhsType, sessionFactory)) {
/*  56: 87 */         throw new TypeMismatchException("left and right hand sides of a binary logic operator were incompatibile [" + lhsType.getName() + " : " + rhsType.getName() + "]");
/*  57:    */       }
/*  58: 92 */       if (lhsColumnSpan > 1) {
/*  59: 95 */         if (!sessionFactory.getDialect().supportsRowValueConstructorSyntax()) {
/*  60: 96 */           mutateRowValueConstructorSyntax(lhsColumnSpan);
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private int getColumnSpan(Type type, SessionFactoryImplementor sfi)
/*  67:    */   {
/*  68:103 */     int columnSpan = type.getColumnSpan(sfi);
/*  69:104 */     if ((columnSpan == 0) && ((type instanceof OneToOneType))) {
/*  70:105 */       columnSpan = ((OneToOneType)type).getIdentifierOrUniqueKeyType(sfi).getColumnSpan(sfi);
/*  71:    */     }
/*  72:107 */     return columnSpan;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void mutateRowValueConstructorSyntax(int valueElements)
/*  76:    */   {
/*  77:123 */     int comparisonType = getType();
/*  78:124 */     String comparisonText = getText();
/*  79:125 */     setType(6);
/*  80:126 */     setText("AND");
/*  81:127 */     String[] lhsElementTexts = extractMutationTexts(getLeftHandOperand(), valueElements);
/*  82:128 */     String[] rhsElementTexts = extractMutationTexts(getRightHandOperand(), valueElements);
/*  83:    */     
/*  84:130 */     ParameterSpecification lhsEmbeddedCompositeParameterSpecification = (getLeftHandOperand() == null) || (!ParameterNode.class.isInstance(getLeftHandOperand())) ? null : ((ParameterNode)getLeftHandOperand()).getHqlParameterSpecification();
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:135 */     ParameterSpecification rhsEmbeddedCompositeParameterSpecification = (getRightHandOperand() == null) || (!ParameterNode.class.isInstance(getRightHandOperand())) ? null : ((ParameterNode)getRightHandOperand()).getHqlParameterSpecification();
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:140 */     translate(valueElements, comparisonType, comparisonText, lhsElementTexts, rhsElementTexts, lhsEmbeddedCompositeParameterSpecification, rhsEmbeddedCompositeParameterSpecification, this);
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void translate(int valueElements, int comparisonType, String comparisonText, String[] lhsElementTexts, String[] rhsElementTexts, ParameterSpecification lhsEmbeddedCompositeParameterSpecification, ParameterSpecification rhsEmbeddedCompositeParameterSpecification, AST container)
/*  98:    */   {
/*  99:152 */     for (int i = valueElements - 1; i > 0; i--) {
/* 100:153 */       if (i == 1)
/* 101:    */       {
/* 102:154 */         AST op1 = getASTFactory().create(comparisonType, comparisonText);
/* 103:155 */         AST lhs1 = getASTFactory().create(142, lhsElementTexts[0]);
/* 104:156 */         AST rhs1 = getASTFactory().create(142, rhsElementTexts[0]);
/* 105:157 */         op1.setFirstChild(lhs1);
/* 106:158 */         lhs1.setNextSibling(rhs1);
/* 107:159 */         container.setFirstChild(op1);
/* 108:160 */         AST op2 = getASTFactory().create(comparisonType, comparisonText);
/* 109:161 */         AST lhs2 = getASTFactory().create(142, lhsElementTexts[1]);
/* 110:162 */         AST rhs2 = getASTFactory().create(142, rhsElementTexts[1]);
/* 111:163 */         op2.setFirstChild(lhs2);
/* 112:164 */         lhs2.setNextSibling(rhs2);
/* 113:165 */         op1.setNextSibling(op2);
/* 114:    */         
/* 115:    */ 
/* 116:    */ 
/* 117:169 */         SqlFragment fragment = (SqlFragment)lhs1;
/* 118:170 */         if (lhsEmbeddedCompositeParameterSpecification != null) {
/* 119:171 */           fragment.addEmbeddedParameter(lhsEmbeddedCompositeParameterSpecification);
/* 120:    */         }
/* 121:173 */         if (rhsEmbeddedCompositeParameterSpecification != null) {
/* 122:174 */           fragment.addEmbeddedParameter(rhsEmbeddedCompositeParameterSpecification);
/* 123:    */         }
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:178 */         AST op = getASTFactory().create(comparisonType, comparisonText);
/* 128:179 */         AST lhs = getASTFactory().create(142, lhsElementTexts[i]);
/* 129:180 */         AST rhs = getASTFactory().create(142, rhsElementTexts[i]);
/* 130:181 */         op.setFirstChild(lhs);
/* 131:182 */         lhs.setNextSibling(rhs);
/* 132:183 */         AST newContainer = getASTFactory().create(6, "AND");
/* 133:184 */         container.setFirstChild(newContainer);
/* 134:185 */         newContainer.setNextSibling(op);
/* 135:186 */         container = newContainer;
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected static String[] extractMutationTexts(Node operand, int count)
/* 141:    */   {
/* 142:192 */     if ((operand instanceof ParameterNode))
/* 143:    */     {
/* 144:193 */       String[] rtn = new String[count];
/* 145:194 */       for (int i = 0; i < count; i++) {
/* 146:195 */         rtn[i] = "?";
/* 147:    */       }
/* 148:197 */       return rtn;
/* 149:    */     }
/* 150:199 */     if (operand.getType() == 92)
/* 151:    */     {
/* 152:200 */       String[] rtn = new String[operand.getNumberOfChildren()];
/* 153:201 */       int x = 0;
/* 154:202 */       AST node = operand.getFirstChild();
/* 155:203 */       while (node != null)
/* 156:    */       {
/* 157:204 */         rtn[(x++)] = node.getText();
/* 158:205 */         node = node.getNextSibling();
/* 159:    */       }
/* 160:207 */       return rtn;
/* 161:    */     }
/* 162:209 */     if ((operand instanceof SqlNode))
/* 163:    */     {
/* 164:210 */       String nodeText = operand.getText();
/* 165:211 */       if (nodeText.startsWith("(")) {
/* 166:212 */         nodeText = nodeText.substring(1);
/* 167:    */       }
/* 168:214 */       if (nodeText.endsWith(")")) {
/* 169:215 */         nodeText = nodeText.substring(0, nodeText.length() - 1);
/* 170:    */       }
/* 171:217 */       String[] splits = StringHelper.split(", ", nodeText);
/* 172:218 */       if (count != splits.length) {
/* 173:219 */         throw new HibernateException("SqlNode's text did not reference expected number of columns");
/* 174:    */       }
/* 175:221 */       return splits;
/* 176:    */     }
/* 177:224 */     throw new HibernateException("dont know how to extract row value elements from node : " + operand);
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected Type extractDataType(Node operand)
/* 181:    */   {
/* 182:229 */     Type type = null;
/* 183:230 */     if ((operand instanceof SqlNode)) {
/* 184:231 */       type = ((SqlNode)operand).getDataType();
/* 185:    */     }
/* 186:233 */     if ((type == null) && ((operand instanceof ExpectedTypeAwareNode))) {
/* 187:234 */       type = ((ExpectedTypeAwareNode)operand).getExpectedType();
/* 188:    */     }
/* 189:236 */     return type;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Type getDataType()
/* 193:    */   {
/* 194:242 */     return StandardBasicTypes.BOOLEAN;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Node getLeftHandOperand()
/* 198:    */   {
/* 199:251 */     return (Node)getFirstChild();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Node getRightHandOperand()
/* 203:    */   {
/* 204:260 */     return (Node)getFirstChild().getNextSibling();
/* 205:    */   }
/* 206:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.BinaryLogicOperatorNode
 * JD-Core Version:    0.7.0.1
 */