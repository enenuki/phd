/*  1:   */ package org.hibernate.param;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.engine.spi.QueryParameters;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class PositionalParameterSpecification
/* 10:   */   extends AbstractExplicitParameterSpecification
/* 11:   */   implements ParameterSpecification
/* 12:   */ {
/* 13:   */   private final int hqlPosition;
/* 14:   */   
/* 15:   */   public PositionalParameterSpecification(int sourceLine, int sourceColumn, int hqlPosition)
/* 16:   */   {
/* 17:49 */     super(sourceLine, sourceColumn);
/* 18:50 */     this.hqlPosition = hqlPosition;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int position)
/* 22:   */     throws SQLException
/* 23:   */   {
/* 24:64 */     Type type = qp.getPositionalParameterTypes()[this.hqlPosition];
/* 25:65 */     Object value = qp.getPositionalParameterValues()[this.hqlPosition];
/* 26:   */     
/* 27:67 */     type.nullSafeSet(statement, value, position, session);
/* 28:68 */     return type.getColumnSpan(session.getFactory());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String renderDisplayInfo()
/* 32:   */   {
/* 33:75 */     return "ordinal=" + this.hqlPosition + ", expectedType=" + getExpectedType();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getHqlPosition()
/* 37:   */   {
/* 38:84 */     return this.hqlPosition;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.PositionalParameterSpecification
 * JD-Core Version:    0.7.0.1
 */