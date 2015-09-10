/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.Dialect;
/*   4:    */ 
/*   5:    */ public class UniqueKey
/*   6:    */   extends AbstractConstraint
/*   7:    */   implements Constraint
/*   8:    */ {
/*   9:    */   protected UniqueKey(Table table, String name)
/*  10:    */   {
/*  11: 36 */     super(table, name);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public String getExportIdentifier()
/*  15:    */   {
/*  16: 41 */     StringBuilder sb = new StringBuilder(getTable().getLoggableValueQualifier());
/*  17: 42 */     sb.append(".UK");
/*  18: 43 */     for (Column column : getColumns()) {
/*  19: 44 */       sb.append('_').append(column.getColumnName().getName());
/*  20:    */     }
/*  21: 46 */     return sb.toString();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean isCreationVetoed(Dialect dialect)
/*  25:    */   {
/*  26: 51 */     if (dialect.supportsNotNullUnique()) {
/*  27: 52 */       return false;
/*  28:    */     }
/*  29: 55 */     for (Column column : getColumns()) {
/*  30: 56 */       if (column.isNullable()) {
/*  31: 57 */         return true;
/*  32:    */       }
/*  33:    */     }
/*  34: 60 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String sqlConstraintStringInCreateTable(Dialect dialect)
/*  38:    */   {
/*  39: 64 */     StringBuffer buf = new StringBuffer("unique (");
/*  40: 65 */     boolean hadNullableColumn = false;
/*  41: 66 */     boolean first = true;
/*  42: 67 */     for (Column column : getColumns())
/*  43:    */     {
/*  44: 68 */       if (first) {
/*  45: 69 */         first = false;
/*  46:    */       } else {
/*  47: 72 */         buf.append(", ");
/*  48:    */       }
/*  49: 74 */       if ((!hadNullableColumn) && (column.isNullable())) {
/*  50: 75 */         hadNullableColumn = true;
/*  51:    */       }
/*  52: 77 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/*  53:    */     }
/*  54: 80 */     return (!hadNullableColumn) || (dialect.supportsNotNullUnique()) ? ')' : null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String sqlConstraintStringInAlterTable(Dialect dialect)
/*  58:    */   {
/*  59: 87 */     StringBuffer buf = new StringBuffer(dialect.getAddUniqueConstraintString(getName())).append('(');
/*  60:    */     
/*  61:    */ 
/*  62: 90 */     boolean nullable = false;
/*  63: 91 */     boolean first = true;
/*  64: 92 */     for (Column column : getColumns())
/*  65:    */     {
/*  66: 93 */       if (first) {
/*  67: 94 */         first = false;
/*  68:    */       } else {
/*  69: 97 */         buf.append(", ");
/*  70:    */       }
/*  71: 99 */       if ((!nullable) && (column.isNullable())) {
/*  72:100 */         nullable = true;
/*  73:    */       }
/*  74:102 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/*  75:    */     }
/*  76:104 */     return (!nullable) || (dialect.supportsNotNullUnique()) ? ')' : null;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.UniqueKey
 * JD-Core Version:    0.7.0.1
 */