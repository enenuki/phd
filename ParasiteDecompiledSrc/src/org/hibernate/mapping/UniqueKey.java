/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.dialect.Dialect;
/*   5:    */ import org.hibernate.engine.spi.Mapping;
/*   6:    */ 
/*   7:    */ public class UniqueKey
/*   8:    */   extends Constraint
/*   9:    */ {
/*  10:    */   public String sqlConstraintString(Dialect dialect)
/*  11:    */   {
/*  12: 38 */     StringBuffer buf = new StringBuffer("unique (");
/*  13: 39 */     boolean hadNullableColumn = false;
/*  14: 40 */     Iterator iter = getColumnIterator();
/*  15: 41 */     while (iter.hasNext())
/*  16:    */     {
/*  17: 42 */       Column column = (Column)iter.next();
/*  18: 43 */       if ((!hadNullableColumn) && (column.isNullable())) {
/*  19: 44 */         hadNullableColumn = true;
/*  20:    */       }
/*  21: 46 */       buf.append(column.getQuotedName(dialect));
/*  22: 47 */       if (iter.hasNext()) {
/*  23: 48 */         buf.append(", ");
/*  24:    */       }
/*  25:    */     }
/*  26: 52 */     return (!hadNullableColumn) || (dialect.supportsNotNullUnique()) ? ')' : null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String sqlConstraintString(Dialect dialect, String constraintName, String defaultCatalog, String defaultSchema)
/*  30:    */   {
/*  31: 63 */     StringBuffer buf = new StringBuffer(dialect.getAddUniqueConstraintString(constraintName)).append('(');
/*  32:    */     
/*  33:    */ 
/*  34: 66 */     Iterator iter = getColumnIterator();
/*  35: 67 */     boolean nullable = false;
/*  36: 68 */     while (iter.hasNext())
/*  37:    */     {
/*  38: 69 */       Column column = (Column)iter.next();
/*  39: 70 */       if ((!nullable) && (column.isNullable())) {
/*  40: 70 */         nullable = true;
/*  41:    */       }
/*  42: 71 */       buf.append(column.getQuotedName(dialect));
/*  43: 72 */       if (iter.hasNext()) {
/*  44: 72 */         buf.append(", ");
/*  45:    */       }
/*  46:    */     }
/*  47: 74 */     return (!nullable) || (dialect.supportsNotNullUnique()) ? ')' : null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String sqlCreateString(Dialect dialect, Mapping p, String defaultCatalog, String defaultSchema)
/*  51:    */   {
/*  52: 79 */     if (dialect.supportsUniqueConstraintInCreateAlterTable()) {
/*  53: 80 */       return super.sqlCreateString(dialect, p, defaultCatalog, defaultSchema);
/*  54:    */     }
/*  55: 83 */     return Index.buildSqlCreateIndexString(dialect, getName(), getTable(), getColumnIterator(), true, defaultCatalog, defaultSchema);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/*  59:    */   {
/*  60: 90 */     if (dialect.supportsUniqueConstraintInCreateAlterTable()) {
/*  61: 91 */       return super.sqlDropString(dialect, defaultCatalog, defaultSchema);
/*  62:    */     }
/*  63: 94 */     return Index.buildSqlDropIndexString(dialect, getTable(), getName(), defaultCatalog, defaultSchema);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isGenerated(Dialect dialect)
/*  67:    */   {
/*  68:100 */     if (dialect.supportsNotNullUnique()) {
/*  69:100 */       return true;
/*  70:    */     }
/*  71:101 */     Iterator iter = getColumnIterator();
/*  72:102 */     while (iter.hasNext()) {
/*  73:103 */       if (((Column)iter.next()).isNullable()) {
/*  74:104 */         return false;
/*  75:    */       }
/*  76:    */     }
/*  77:107 */     return true;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.UniqueKey
 * JD-Core Version:    0.7.0.1
 */