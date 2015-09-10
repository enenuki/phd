/*   1:    */ package org.hibernate.param;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   8:    */ import org.hibernate.engine.spi.QueryParameters;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ 
/*  12:    */ public class DynamicFilterParameterSpecification
/*  13:    */   implements ParameterSpecification
/*  14:    */ {
/*  15:    */   private final String filterName;
/*  16:    */   private final String parameterName;
/*  17:    */   private final Type definedParameterType;
/*  18:    */   
/*  19:    */   public DynamicFilterParameterSpecification(String filterName, String parameterName, Type definedParameterType)
/*  20:    */   {
/*  21: 58 */     this.filterName = filterName;
/*  22: 59 */     this.parameterName = parameterName;
/*  23: 60 */     this.definedParameterType = definedParameterType;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int start)
/*  27:    */     throws SQLException
/*  28:    */   {
/*  29: 71 */     int columnSpan = this.definedParameterType.getColumnSpan(session.getFactory());
/*  30: 72 */     Object value = session.getLoadQueryInfluencers().getFilterParameterValue(this.filterName + '.' + this.parameterName);
/*  31: 73 */     if (Collection.class.isInstance(value))
/*  32:    */     {
/*  33: 74 */       int positions = 0;
/*  34: 75 */       Iterator itr = ((Collection)value).iterator();
/*  35: 76 */       while (itr.hasNext())
/*  36:    */       {
/*  37: 77 */         this.definedParameterType.nullSafeSet(statement, itr.next(), start + positions, session);
/*  38: 78 */         positions += columnSpan;
/*  39:    */       }
/*  40: 80 */       return positions;
/*  41:    */     }
/*  42: 83 */     this.definedParameterType.nullSafeSet(statement, value, start, session);
/*  43: 84 */     return columnSpan;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type getExpectedType()
/*  47:    */   {
/*  48: 92 */     return this.definedParameterType;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setExpectedType(Type expectedType) {}
/*  52:    */   
/*  53:    */   public String renderDisplayInfo()
/*  54:    */   {
/*  55:106 */     return "dynamic-filter={filterName=" + this.filterName + ",paramName=" + this.parameterName + "}";
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.DynamicFilterParameterSpecification
 * JD-Core Version:    0.7.0.1
 */