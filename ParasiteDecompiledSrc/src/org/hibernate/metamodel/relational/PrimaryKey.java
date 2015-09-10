/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ 
/*  5:   */ public class PrimaryKey
/*  6:   */   extends AbstractConstraint
/*  7:   */   implements Constraint, Exportable
/*  8:   */ {
/*  9:   */   private String name;
/* 10:   */   
/* 11:   */   protected PrimaryKey(TableSpecification table)
/* 12:   */   {
/* 13:47 */     super(table, null);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:52 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setName(String name)
/* 22:   */   {
/* 23:56 */     this.name = name;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getExportIdentifier()
/* 27:   */   {
/* 28:61 */     return getTable().getLoggableValueQualifier() + ".PK";
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String sqlConstraintStringInCreateTable(Dialect dialect)
/* 32:   */   {
/* 33:65 */     StringBuilder buf = new StringBuilder("primary key (");
/* 34:66 */     boolean first = true;
/* 35:67 */     for (Column column : getColumns())
/* 36:   */     {
/* 37:68 */       if (first) {
/* 38:69 */         first = false;
/* 39:   */       } else {
/* 40:72 */         buf.append(", ");
/* 41:   */       }
/* 42:74 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/* 43:   */     }
/* 44:76 */     return ')';
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String sqlConstraintStringInAlterTable(Dialect dialect)
/* 48:   */   {
/* 49:80 */     StringBuffer buf = new StringBuffer(dialect.getAddPrimaryKeyConstraintString(getName())).append('(');
/* 50:   */     
/* 51:   */ 
/* 52:83 */     boolean first = true;
/* 53:84 */     for (Column column : getColumns())
/* 54:   */     {
/* 55:85 */       if (first) {
/* 56:86 */         first = false;
/* 57:   */       } else {
/* 58:89 */         buf.append(", ");
/* 59:   */       }
/* 60:91 */       buf.append(column.getColumnName().encloseInQuotesIfQuoted(dialect));
/* 61:   */     }
/* 62:93 */     return ')';
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.PrimaryKey
 * JD-Core Version:    0.7.0.1
 */