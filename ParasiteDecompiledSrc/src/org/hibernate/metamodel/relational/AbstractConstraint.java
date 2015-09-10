/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.dialect.Dialect;
/*   7:    */ 
/*   8:    */ public abstract class AbstractConstraint
/*   9:    */   implements Constraint
/*  10:    */ {
/*  11:    */   private final TableSpecification table;
/*  12:    */   private final String name;
/*  13: 43 */   private List<Column> columns = new ArrayList();
/*  14:    */   
/*  15:    */   protected AbstractConstraint(TableSpecification table, String name)
/*  16:    */   {
/*  17: 46 */     this.table = table;
/*  18: 47 */     this.name = name;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public TableSpecification getTable()
/*  22:    */   {
/*  23: 51 */     return this.table;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getName()
/*  27:    */   {
/*  28: 55 */     return this.name;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Iterable<Column> getColumns()
/*  32:    */   {
/*  33: 59 */     return this.columns;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int getColumnSpan()
/*  37:    */   {
/*  38: 63 */     return this.columns.size();
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected List<Column> internalColumnAccess()
/*  42:    */   {
/*  43: 67 */     return this.columns;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void addColumn(Column column)
/*  47:    */   {
/*  48: 71 */     internalAddColumn(column);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void internalAddColumn(Column column)
/*  52:    */   {
/*  53: 75 */     if (column.getTable() != getTable()) {
/*  54: 76 */       throw new AssertionFailure(String.format("Unable to add column to constraint; tables [%s, %s] did not match", new Object[] { column.getTable().toLoggableString(), getTable().toLoggableString() }));
/*  55:    */     }
/*  56: 84 */     this.columns.add(column);
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected boolean isCreationVetoed(Dialect dialect)
/*  60:    */   {
/*  61: 88 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected abstract String sqlConstraintStringInAlterTable(Dialect paramDialect);
/*  65:    */   
/*  66:    */   public String[] sqlDropStrings(Dialect dialect)
/*  67:    */   {
/*  68: 94 */     if (isCreationVetoed(dialect)) {
/*  69: 95 */       return null;
/*  70:    */     }
/*  71: 98 */     return new String[] { "alter table " + getTable().getQualifiedName(dialect) + " drop constraint " + dialect.quote(getName()) };
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String[] sqlCreateStrings(Dialect dialect)
/*  75:    */   {
/*  76:110 */     if (isCreationVetoed(dialect)) {
/*  77:111 */       return null;
/*  78:    */     }
/*  79:114 */     return new String[] { "alter table " + getTable().getQualifiedName(dialect) + sqlConstraintStringInAlterTable(dialect) };
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.AbstractConstraint
 * JD-Core Version:    0.7.0.1
 */