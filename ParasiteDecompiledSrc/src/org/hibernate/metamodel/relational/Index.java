/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.Dialect;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ 
/*   6:    */ public class Index
/*   7:    */   extends AbstractConstraint
/*   8:    */   implements Constraint
/*   9:    */ {
/*  10:    */   protected Index(Table table, String name)
/*  11:    */   {
/*  12: 37 */     super(table, name);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public String getExportIdentifier()
/*  16:    */   {
/*  17: 43 */     StringBuilder sb = new StringBuilder(getTable().getLoggableValueQualifier());
/*  18: 44 */     sb.append(".IDX");
/*  19: 45 */     for (Column column : getColumns()) {
/*  20: 46 */       sb.append('_').append(column.getColumnName().getName());
/*  21:    */     }
/*  22: 48 */     return sb.toString();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String[] sqlCreateStrings(Dialect dialect)
/*  26:    */   {
/*  27: 52 */     return new String[] { buildSqlCreateIndexString(dialect, getName(), getTable(), getColumns(), false) };
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String buildSqlCreateIndexString(Dialect dialect, String name, TableSpecification table, Iterable<Column> columns, boolean unique)
/*  31:    */   {
/*  32: 67 */     StringBuilder buf = new StringBuilder("create").append(unique ? " unique" : "").append(" index ").append(dialect.qualifyIndexName() ? name : StringHelper.unqualify(name)).append(" on ").append(table.getQualifiedName(dialect)).append(" (");
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 78 */     boolean first = true;
/*  44: 79 */     for (Column column : columns)
/*  45:    */     {
/*  46: 80 */       if (first) {
/*  47: 81 */         first = false;
/*  48:    */       } else {
/*  49: 84 */         buf.append(", ");
/*  50:    */       }
/*  51: 86 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/*  52:    */     }
/*  53: 88 */     buf.append(")");
/*  54: 89 */     return buf.toString();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String sqlConstraintStringInAlterTable(Dialect dialect)
/*  58:    */   {
/*  59: 93 */     StringBuilder buf = new StringBuilder(" index (");
/*  60: 94 */     boolean first = true;
/*  61: 95 */     for (Column column : getColumns())
/*  62:    */     {
/*  63: 96 */       if (first) {
/*  64: 97 */         first = false;
/*  65:    */       } else {
/*  66:100 */         buf.append(", ");
/*  67:    */       }
/*  68:102 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/*  69:    */     }
/*  70:104 */     return ')';
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String[] sqlDropStrings(Dialect dialect)
/*  74:    */   {
/*  75:108 */     return new String[] { "drop index " + StringHelper.qualify(getTable().getQualifiedName(dialect), getName()) };
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Index
 * JD-Core Version:    0.7.0.1
 */