/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.Calendar;
/*   6:    */ import java.util.Date;
/*   7:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*   8:    */ import org.hibernate.type.StandardBasicTypes;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class BinaryArithmeticOperatorNode
/*  12:    */   extends AbstractSelectExpression
/*  13:    */   implements BinaryOperatorNode, DisplayableNode
/*  14:    */ {
/*  15:    */   public void initialize()
/*  16:    */     throws SemanticException
/*  17:    */   {
/*  18: 41 */     Node lhs = getLeftHandOperand();
/*  19: 42 */     Node rhs = getRightHandOperand();
/*  20: 43 */     if (lhs == null) {
/*  21: 44 */       throw new SemanticException("left-hand operand of a binary operator was null");
/*  22:    */     }
/*  23: 46 */     if (rhs == null) {
/*  24: 47 */       throw new SemanticException("right-hand operand of a binary operator was null");
/*  25:    */     }
/*  26: 50 */     Type lhType = (lhs instanceof SqlNode) ? ((SqlNode)lhs).getDataType() : null;
/*  27: 51 */     Type rhType = (rhs instanceof SqlNode) ? ((SqlNode)rhs).getDataType() : null;
/*  28: 53 */     if ((ExpectedTypeAwareNode.class.isAssignableFrom(lhs.getClass())) && (rhType != null))
/*  29:    */     {
/*  30: 54 */       Type expectedType = null;
/*  31: 56 */       if (isDateTimeType(rhType)) {
/*  32: 62 */         expectedType = getType() == 115 ? StandardBasicTypes.DOUBLE : rhType;
/*  33:    */       } else {
/*  34: 65 */         expectedType = rhType;
/*  35:    */       }
/*  36: 67 */       ((ExpectedTypeAwareNode)lhs).setExpectedType(expectedType);
/*  37:    */     }
/*  38: 69 */     else if ((ParameterNode.class.isAssignableFrom(rhs.getClass())) && (lhType != null))
/*  39:    */     {
/*  40: 70 */       Type expectedType = null;
/*  41: 72 */       if (isDateTimeType(lhType))
/*  42:    */       {
/*  43: 79 */         if (getType() == 115) {
/*  44: 80 */           expectedType = StandardBasicTypes.DOUBLE;
/*  45:    */         }
/*  46:    */       }
/*  47:    */       else {
/*  48: 84 */         expectedType = lhType;
/*  49:    */       }
/*  50: 86 */       ((ExpectedTypeAwareNode)rhs).setExpectedType(expectedType);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Type getDataType()
/*  55:    */   {
/*  56: 96 */     if (super.getDataType() == null) {
/*  57: 97 */       super.setDataType(resolveDataType());
/*  58:    */     }
/*  59: 99 */     return super.getDataType();
/*  60:    */   }
/*  61:    */   
/*  62:    */   private Type resolveDataType()
/*  63:    */   {
/*  64:106 */     Node lhs = getLeftHandOperand();
/*  65:107 */     Node rhs = getRightHandOperand();
/*  66:108 */     Type lhType = (lhs instanceof SqlNode) ? ((SqlNode)lhs).getDataType() : null;
/*  67:109 */     Type rhType = (rhs instanceof SqlNode) ? ((SqlNode)rhs).getDataType() : null;
/*  68:110 */     if ((isDateTimeType(lhType)) || (isDateTimeType(rhType))) {
/*  69:111 */       return resolveDateTimeArithmeticResultType(lhType, rhType);
/*  70:    */     }
/*  71:114 */     if (lhType == null)
/*  72:    */     {
/*  73:115 */       if (rhType == null) {
/*  74:117 */         return StandardBasicTypes.DOUBLE;
/*  75:    */       }
/*  76:121 */       return rhType;
/*  77:    */     }
/*  78:125 */     if (rhType == null) {
/*  79:127 */       return lhType;
/*  80:    */     }
/*  81:130 */     if ((lhType == StandardBasicTypes.DOUBLE) || (rhType == StandardBasicTypes.DOUBLE)) {
/*  82:131 */       return StandardBasicTypes.DOUBLE;
/*  83:    */     }
/*  84:133 */     if ((lhType == StandardBasicTypes.FLOAT) || (rhType == StandardBasicTypes.FLOAT)) {
/*  85:134 */       return StandardBasicTypes.FLOAT;
/*  86:    */     }
/*  87:136 */     if ((lhType == StandardBasicTypes.BIG_DECIMAL) || (rhType == StandardBasicTypes.BIG_DECIMAL)) {
/*  88:137 */       return StandardBasicTypes.BIG_DECIMAL;
/*  89:    */     }
/*  90:139 */     if ((lhType == StandardBasicTypes.BIG_INTEGER) || (rhType == StandardBasicTypes.BIG_INTEGER)) {
/*  91:140 */       return StandardBasicTypes.BIG_INTEGER;
/*  92:    */     }
/*  93:142 */     if ((lhType == StandardBasicTypes.LONG) || (rhType == StandardBasicTypes.LONG)) {
/*  94:143 */       return StandardBasicTypes.LONG;
/*  95:    */     }
/*  96:145 */     if ((lhType == StandardBasicTypes.INTEGER) || (rhType == StandardBasicTypes.INTEGER)) {
/*  97:146 */       return StandardBasicTypes.INTEGER;
/*  98:    */     }
/*  99:148 */     return lhType;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private boolean isDateTimeType(Type type)
/* 103:    */   {
/* 104:155 */     if (type == null) {
/* 105:156 */       return false;
/* 106:    */     }
/* 107:158 */     return (Date.class.isAssignableFrom(type.getReturnedClass())) || (Calendar.class.isAssignableFrom(type.getReturnedClass()));
/* 108:    */   }
/* 109:    */   
/* 110:    */   private Type resolveDateTimeArithmeticResultType(Type lhType, Type rhType)
/* 111:    */   {
/* 112:178 */     boolean lhsIsDateTime = isDateTimeType(lhType);
/* 113:179 */     boolean rhsIsDateTime = isDateTimeType(rhType);
/* 114:183 */     if (getType() == 115) {
/* 115:185 */       return lhsIsDateTime ? lhType : rhType;
/* 116:    */     }
/* 117:187 */     if (getType() == 116)
/* 118:    */     {
/* 119:189 */       if ((lhsIsDateTime) && (!rhsIsDateTime)) {
/* 120:190 */         return lhType;
/* 121:    */       }
/* 122:193 */       if ((lhsIsDateTime) && (rhsIsDateTime)) {
/* 123:194 */         return StandardBasicTypes.DOUBLE;
/* 124:    */       }
/* 125:    */     }
/* 126:197 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setScalarColumnText(int i)
/* 130:    */     throws SemanticException
/* 131:    */   {
/* 132:201 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Node getLeftHandOperand()
/* 136:    */   {
/* 137:210 */     return (Node)getFirstChild();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Node getRightHandOperand()
/* 141:    */   {
/* 142:219 */     return (Node)getFirstChild().getNextSibling();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getDisplayText()
/* 146:    */   {
/* 147:223 */     return "{dataType=" + getDataType() + "}";
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.BinaryArithmeticOperatorNode
 * JD-Core Version:    0.7.0.1
 */