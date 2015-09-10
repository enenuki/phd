/*  1:   */ package org.hibernate.param;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.engine.spi.QueryParameters;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.engine.spi.TypedValue;
/*  9:   */ import org.hibernate.type.Type;
/* 10:   */ 
/* 11:   */ public class NamedParameterSpecification
/* 12:   */   extends AbstractExplicitParameterSpecification
/* 13:   */   implements ParameterSpecification
/* 14:   */ {
/* 15:   */   private final String name;
/* 16:   */   
/* 17:   */   public NamedParameterSpecification(int sourceLine, int sourceColumn, String name)
/* 18:   */   {
/* 19:49 */     super(sourceLine, sourceColumn);
/* 20:50 */     this.name = name;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int position)
/* 24:   */     throws SQLException
/* 25:   */   {
/* 26:65 */     TypedValue typedValue = (TypedValue)qp.getNamedParameters().get(this.name);
/* 27:66 */     typedValue.getType().nullSafeSet(statement, typedValue.getValue(), position, session);
/* 28:67 */     return typedValue.getType().getColumnSpan(session.getFactory());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String renderDisplayInfo()
/* 32:   */   {
/* 33:74 */     return "name=" + this.name + ", expectedType=" + getExpectedType();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getName()
/* 37:   */   {
/* 38:83 */     return this.name;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.NamedParameterSpecification
 * JD-Core Version:    0.7.0.1
 */