/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import org.hibernate.type.Type;
/*   4:    */ 
/*   5:    */ public final class Projections
/*   6:    */ {
/*   7:    */   public static Projection distinct(Projection proj)
/*   8:    */   {
/*   9: 50 */     return new Distinct(proj);
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static ProjectionList projectionList()
/*  13:    */   {
/*  14: 57 */     return new ProjectionList();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static Projection rowCount()
/*  18:    */   {
/*  19: 64 */     return new RowCountProjection();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static CountProjection count(String propertyName)
/*  23:    */   {
/*  24: 71 */     return new CountProjection(propertyName);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static CountProjection countDistinct(String propertyName)
/*  28:    */   {
/*  29: 78 */     return new CountProjection(propertyName).setDistinct();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static AggregateProjection max(String propertyName)
/*  33:    */   {
/*  34: 85 */     return new AggregateProjection("max", propertyName);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static AggregateProjection min(String propertyName)
/*  38:    */   {
/*  39: 92 */     return new AggregateProjection("min", propertyName);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static AggregateProjection avg(String propertyName)
/*  43:    */   {
/*  44: 99 */     return new AvgProjection(propertyName);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static AggregateProjection sum(String propertyName)
/*  48:    */   {
/*  49:106 */     return new AggregateProjection("sum", propertyName);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Projection sqlProjection(String sql, String[] columnAliases, Type[] types)
/*  53:    */   {
/*  54:113 */     return new SQLProjection(sql, columnAliases, types);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static Projection sqlGroupProjection(String sql, String groupBy, String[] columnAliases, Type[] types)
/*  58:    */   {
/*  59:120 */     return new SQLProjection(sql, groupBy, columnAliases, types);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static PropertyProjection groupProperty(String propertyName)
/*  63:    */   {
/*  64:127 */     return new PropertyProjection(propertyName, true);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static PropertyProjection property(String propertyName)
/*  68:    */   {
/*  69:134 */     return new PropertyProjection(propertyName);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static IdentifierProjection id()
/*  73:    */   {
/*  74:141 */     return new IdentifierProjection();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static Projection alias(Projection projection, String alias)
/*  78:    */   {
/*  79:148 */     return new AliasedProjection(projection, alias);
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Projections
 * JD-Core Version:    0.7.0.1
 */