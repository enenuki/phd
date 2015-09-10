/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  4:   */ import org.hibernate.param.ParameterSpecification;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class ParameterNode
/*  8:   */   extends HqlSqlWalkerNode
/*  9:   */   implements DisplayableNode, ExpectedTypeAwareNode
/* 10:   */ {
/* 11:   */   private ParameterSpecification parameterSpecification;
/* 12:   */   
/* 13:   */   public ParameterSpecification getHqlParameterSpecification()
/* 14:   */   {
/* 15:39 */     return this.parameterSpecification;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setHqlParameterSpecification(ParameterSpecification parameterSpecification)
/* 19:   */   {
/* 20:43 */     this.parameterSpecification = parameterSpecification;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getDisplayText()
/* 24:   */   {
/* 25:47 */     return "{" + (this.parameterSpecification == null ? "???" : this.parameterSpecification.renderDisplayInfo()) + "}";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setExpectedType(Type expectedType)
/* 29:   */   {
/* 30:51 */     getHqlParameterSpecification().setExpectedType(expectedType);
/* 31:52 */     setDataType(expectedType);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Type getExpectedType()
/* 35:   */   {
/* 36:56 */     return getHqlParameterSpecification() == null ? null : getHqlParameterSpecification().getExpectedType();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getRenderText(SessionFactoryImplementor sessionFactory)
/* 40:   */   {
/* 41:60 */     int count = 0;
/* 42:61 */     if ((getExpectedType() != null) && ((count = getExpectedType().getColumnSpan(sessionFactory)) > 1))
/* 43:   */     {
/* 44:62 */       StringBuffer buffer = new StringBuffer();
/* 45:63 */       buffer.append("(?");
/* 46:64 */       for (int i = 1; i < count; i++) {
/* 47:65 */         buffer.append(", ?");
/* 48:   */       }
/* 49:67 */       buffer.append(")");
/* 50:68 */       return buffer.toString();
/* 51:   */     }
/* 52:71 */     return "?";
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ParameterNode
 * JD-Core Version:    0.7.0.1
 */