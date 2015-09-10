/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  6:   */ import org.hibernate.internal.util.ReflectHelper;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.hibernate.type.LiteralType;
/*  9:   */ import org.hibernate.type.Type;
/* 10:   */ import org.hibernate.type.TypeResolver;
/* 11:   */ 
/* 12:   */ public class JavaConstantNode
/* 13:   */   extends Node
/* 14:   */   implements ExpectedTypeAwareNode, SessionFactoryAwareNode
/* 15:   */ {
/* 16:   */   private SessionFactoryImplementor factory;
/* 17:   */   private String constantExpression;
/* 18:   */   private Object constantValue;
/* 19:   */   private Type heuristicType;
/* 20:   */   private Type expectedType;
/* 21:   */   
/* 22:   */   public void setText(String s)
/* 23:   */   {
/* 24:55 */     if (StringHelper.isNotEmpty(s))
/* 25:   */     {
/* 26:56 */       this.constantExpression = s;
/* 27:57 */       this.constantValue = ReflectHelper.getConstantValue(s);
/* 28:58 */       this.heuristicType = this.factory.getTypeResolver().heuristicType(this.constantValue.getClass().getName());
/* 29:59 */       super.setText(s);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setExpectedType(Type expectedType)
/* 34:   */   {
/* 35:64 */     this.expectedType = expectedType;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Type getExpectedType()
/* 39:   */   {
/* 40:68 */     return this.expectedType;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setSessionFactory(SessionFactoryImplementor factory)
/* 44:   */   {
/* 45:72 */     this.factory = factory;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getRenderText(SessionFactoryImplementor sessionFactory)
/* 49:   */   {
/* 50:77 */     Type type = Number.class.isAssignableFrom(this.heuristicType.getReturnedClass()) ? this.heuristicType : this.expectedType == null ? this.heuristicType : this.expectedType;
/* 51:   */     try
/* 52:   */     {
/* 53:83 */       LiteralType literalType = (LiteralType)type;
/* 54:84 */       Dialect dialect = this.factory.getDialect();
/* 55:85 */       return literalType.objectToSQLString(this.constantValue, dialect);
/* 56:   */     }
/* 57:   */     catch (Throwable t)
/* 58:   */     {
/* 59:88 */       throw new QueryException("Could not format constant value to SQL literal: " + this.constantExpression, t);
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.JavaConstantNode
 * JD-Core Version:    0.7.0.1
 */