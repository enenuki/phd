/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.type.LiteralType;
/*  6:   */ import org.hibernate.type.StandardBasicTypes;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class BooleanLiteralNode
/* 10:   */   extends LiteralNode
/* 11:   */   implements ExpectedTypeAwareNode
/* 12:   */ {
/* 13:   */   private Type expectedType;
/* 14:   */   
/* 15:   */   public Type getDataType()
/* 16:   */   {
/* 17:41 */     return this.expectedType == null ? StandardBasicTypes.BOOLEAN : this.expectedType;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Boolean getValue()
/* 21:   */   {
/* 22:45 */     return Boolean.valueOf(getType() == 49);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setExpectedType(Type expectedType)
/* 26:   */   {
/* 27:50 */     this.expectedType = expectedType;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Type getExpectedType()
/* 31:   */   {
/* 32:55 */     return this.expectedType;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getRenderText(SessionFactoryImplementor sessionFactory)
/* 36:   */   {
/* 37:   */     try
/* 38:   */     {
/* 39:62 */       return typeAsLiteralType().objectToSQLString(getValue(), sessionFactory.getDialect());
/* 40:   */     }
/* 41:   */     catch (Throwable t)
/* 42:   */     {
/* 43:65 */       throw new QueryException("Unable to render boolean literal value", t);
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   private LiteralType typeAsLiteralType()
/* 48:   */   {
/* 49:70 */     return (LiteralType)getDataType();
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.BooleanLiteralNode
 * JD-Core Version:    0.7.0.1
 */