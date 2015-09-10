/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ 
/*   9:    */ public class ForeignKey
/*  10:    */   extends Constraint
/*  11:    */ {
/*  12:    */   private Table referencedTable;
/*  13:    */   private String referencedEntityName;
/*  14:    */   private boolean cascadeDeleteEnabled;
/*  15: 41 */   private List referencedColumns = new ArrayList();
/*  16:    */   
/*  17:    */   public String sqlConstraintString(Dialect dialect, String constraintName, String defaultCatalog, String defaultSchema)
/*  18:    */   {
/*  19: 44 */     String[] cols = new String[getColumnSpan()];
/*  20: 45 */     String[] refcols = new String[getColumnSpan()];
/*  21: 46 */     int i = 0;
/*  22: 47 */     Iterator refiter = null;
/*  23: 48 */     if (isReferenceToPrimaryKey()) {
/*  24: 49 */       refiter = this.referencedTable.getPrimaryKey().getColumnIterator();
/*  25:    */     } else {
/*  26: 52 */       refiter = this.referencedColumns.iterator();
/*  27:    */     }
/*  28: 55 */     Iterator iter = getColumnIterator();
/*  29: 56 */     while (iter.hasNext())
/*  30:    */     {
/*  31: 57 */       cols[i] = ((Column)iter.next()).getQuotedName(dialect);
/*  32: 58 */       refcols[i] = ((Column)refiter.next()).getQuotedName(dialect);
/*  33: 59 */       i++;
/*  34:    */     }
/*  35: 61 */     String result = dialect.getAddForeignKeyConstraintString(constraintName, cols, this.referencedTable.getQualifiedName(dialect, defaultCatalog, defaultSchema), refcols, isReferenceToPrimaryKey());
/*  36:    */     
/*  37:    */ 
/*  38: 64 */     return (this.cascadeDeleteEnabled) && (dialect.supportsCascadeDelete()) ? result + " on delete cascade" : result;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Table getReferencedTable()
/*  42:    */   {
/*  43: 70 */     return this.referencedTable;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void appendColumns(StringBuffer buf, Iterator columns)
/*  47:    */   {
/*  48: 74 */     while (columns.hasNext())
/*  49:    */     {
/*  50: 75 */       Column column = (Column)columns.next();
/*  51: 76 */       buf.append(column.getName());
/*  52: 77 */       if (columns.hasNext()) {
/*  53: 77 */         buf.append(",");
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setReferencedTable(Table referencedTable)
/*  59:    */     throws MappingException
/*  60:    */   {
/*  61: 84 */     this.referencedTable = referencedTable;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void alignColumns()
/*  65:    */   {
/*  66: 93 */     if (isReferenceToPrimaryKey()) {
/*  67: 93 */       alignColumns(this.referencedTable);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void alignColumns(Table referencedTable)
/*  72:    */   {
/*  73: 97 */     if (referencedTable.getPrimaryKey().getColumnSpan() != getColumnSpan())
/*  74:    */     {
/*  75: 98 */       StringBuffer sb = new StringBuffer();
/*  76: 99 */       sb.append("Foreign key (").append(getName() + ":").append(getTable().getName()).append(" [");
/*  77:    */       
/*  78:    */ 
/*  79:    */ 
/*  80:103 */       appendColumns(sb, getColumnIterator());
/*  81:104 */       sb.append("])").append(") must have same number of columns as the referenced primary key (").append(referencedTable.getName()).append(" [");
/*  82:    */       
/*  83:    */ 
/*  84:    */ 
/*  85:108 */       appendColumns(sb, referencedTable.getPrimaryKey().getColumnIterator());
/*  86:109 */       sb.append("])");
/*  87:110 */       throw new MappingException(sb.toString());
/*  88:    */     }
/*  89:113 */     Iterator fkCols = getColumnIterator();
/*  90:114 */     Iterator pkCols = referencedTable.getPrimaryKey().getColumnIterator();
/*  91:115 */     while (pkCols.hasNext()) {
/*  92:116 */       ((Column)fkCols.next()).setLength(((Column)pkCols.next()).getLength());
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getReferencedEntityName()
/*  97:    */   {
/*  98:122 */     return this.referencedEntityName;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setReferencedEntityName(String referencedEntityName)
/* 102:    */   {
/* 103:126 */     this.referencedEntityName = referencedEntityName;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/* 107:    */   {
/* 108:130 */     return "alter table " + getTable().getQualifiedName(dialect, defaultCatalog, defaultSchema) + dialect.getDropForeignKeyString() + getName();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isCascadeDeleteEnabled()
/* 112:    */   {
/* 113:137 */     return this.cascadeDeleteEnabled;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setCascadeDeleteEnabled(boolean cascadeDeleteEnabled)
/* 117:    */   {
/* 118:141 */     this.cascadeDeleteEnabled = cascadeDeleteEnabled;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isPhysicalConstraint()
/* 122:    */   {
/* 123:145 */     return (this.referencedTable.isPhysicalTable()) && (getTable().isPhysicalTable()) && (!this.referencedTable.hasDenormalizedTables());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List getReferencedColumns()
/* 127:    */   {
/* 128:152 */     return this.referencedColumns;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isReferenceToPrimaryKey()
/* 132:    */   {
/* 133:157 */     return this.referencedColumns.isEmpty();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void addReferencedColumns(Iterator referencedColumnsIterator)
/* 137:    */   {
/* 138:161 */     while (referencedColumnsIterator.hasNext())
/* 139:    */     {
/* 140:162 */       Selectable col = (Selectable)referencedColumnsIterator.next();
/* 141:163 */       if (!col.isFormula()) {
/* 142:163 */         addReferencedColumn((Column)col);
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void addReferencedColumn(Column column)
/* 148:    */   {
/* 149:168 */     if (!this.referencedColumns.contains(column)) {
/* 150:168 */       this.referencedColumns.add(column);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String toString()
/* 155:    */   {
/* 156:172 */     if (!isReferenceToPrimaryKey())
/* 157:    */     {
/* 158:173 */       StringBuffer result = new StringBuffer(getClass().getName() + '(' + getTable().getName() + getColumns());
/* 159:174 */       result.append(" ref-columns:(" + getReferencedColumns());
/* 160:175 */       result.append(") as " + getName());
/* 161:176 */       return result.toString();
/* 162:    */     }
/* 163:179 */     return super.toString();
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.ForeignKey
 * JD-Core Version:    0.7.0.1
 */