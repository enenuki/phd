/*  1:   */ package org.hibernate.param;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.engine.spi.QueryParameters;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class CollectionFilterKeyParameterSpecification
/* 10:   */   implements ParameterSpecification
/* 11:   */ {
/* 12:   */   private final String collectionRole;
/* 13:   */   private final Type keyType;
/* 14:   */   private final int queryParameterPosition;
/* 15:   */   
/* 16:   */   public CollectionFilterKeyParameterSpecification(String collectionRole, Type keyType, int queryParameterPosition)
/* 17:   */   {
/* 18:53 */     this.collectionRole = collectionRole;
/* 19:54 */     this.keyType = keyType;
/* 20:55 */     this.queryParameterPosition = queryParameterPosition;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int position)
/* 24:   */     throws SQLException
/* 25:   */   {
/* 26:66 */     Object value = qp.getPositionalParameterValues()[this.queryParameterPosition];
/* 27:67 */     this.keyType.nullSafeSet(statement, value, position, session);
/* 28:68 */     return this.keyType.getColumnSpan(session.getFactory());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Type getExpectedType()
/* 32:   */   {
/* 33:75 */     return this.keyType;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setExpectedType(Type expectedType) {}
/* 37:   */   
/* 38:   */   public String renderDisplayInfo()
/* 39:   */   {
/* 40:89 */     return "collection-filter-key=" + this.collectionRole;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.CollectionFilterKeyParameterSpecification
 * JD-Core Version:    0.7.0.1
 */