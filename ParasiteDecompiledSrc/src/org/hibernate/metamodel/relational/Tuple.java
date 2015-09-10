/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashSet;
/*  4:   */ 
/*  5:   */ public class Tuple
/*  6:   */   implements Value, ValueContainer, Loggable
/*  7:   */ {
/*  8:   */   private final TableSpecification table;
/*  9:   */   private final String name;
/* 10:47 */   private final LinkedHashSet<SimpleValue> values = new LinkedHashSet();
/* 11:   */   
/* 12:   */   public Tuple(TableSpecification table, String name)
/* 13:   */   {
/* 14:50 */     this.table = table;
/* 15:51 */     this.name = name;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TableSpecification getTable()
/* 19:   */   {
/* 20:56 */     return this.table;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int valuesSpan()
/* 24:   */   {
/* 25:60 */     return this.values.size();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Iterable<SimpleValue> values()
/* 29:   */   {
/* 30:65 */     return this.values;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void addValue(SimpleValue value)
/* 34:   */   {
/* 35:69 */     if (!value.getTable().equals(getTable())) {
/* 36:70 */       throw new IllegalArgumentException("Tuple can only group values from same table");
/* 37:   */     }
/* 38:72 */     this.values.add(value);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getLoggableValueQualifier()
/* 42:   */   {
/* 43:77 */     return getTable().getLoggableValueQualifier() + '.' + this.name + "{tuple}";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String toLoggableString()
/* 47:   */   {
/* 48:82 */     return getLoggableValueQualifier();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void validateJdbcTypes(Value.JdbcCodes typeCodes)
/* 52:   */   {
/* 53:87 */     for (Value value : values()) {
/* 54:88 */       value.validateJdbcTypes(typeCodes);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Tuple
 * JD-Core Version:    0.7.0.1
 */