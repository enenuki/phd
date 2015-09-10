/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.AssertionFailure;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.jboss.logging.Logger;
/*  10:    */ 
/*  11:    */ public class ForeignKey
/*  12:    */   extends AbstractConstraint
/*  13:    */   implements Constraint, Exportable
/*  14:    */ {
/*  15: 46 */   private static final Logger LOG = Logger.getLogger(ForeignKey.class);
/*  16:    */   private static final String ON_DELETE = " on delete ";
/*  17:    */   private static final String ON_UPDATE = " on update ";
/*  18:    */   private final TableSpecification targetTable;
/*  19:    */   private List<Column> targetColumns;
/*  20: 54 */   private ReferentialAction deleteRule = ReferentialAction.NO_ACTION;
/*  21: 55 */   private ReferentialAction updateRule = ReferentialAction.NO_ACTION;
/*  22:    */   
/*  23:    */   protected ForeignKey(TableSpecification sourceTable, TableSpecification targetTable, String name)
/*  24:    */   {
/*  25: 58 */     super(sourceTable, name);
/*  26: 59 */     this.targetTable = targetTable;
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected ForeignKey(TableSpecification sourceTable, TableSpecification targetTable)
/*  30:    */   {
/*  31: 63 */     this(sourceTable, targetTable, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public TableSpecification getSourceTable()
/*  35:    */   {
/*  36: 67 */     return getTable();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public TableSpecification getTargetTable()
/*  40:    */   {
/*  41: 71 */     return this.targetTable;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Iterable<Column> getSourceColumns()
/*  45:    */   {
/*  46: 75 */     return getColumns();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Iterable<Column> getTargetColumns()
/*  50:    */   {
/*  51: 79 */     return this.targetColumns == null ? getTargetTable().getPrimaryKey().getColumns() : this.targetColumns;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addColumn(Column column)
/*  55:    */   {
/*  56: 86 */     addColumnMapping(column, null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addColumnMapping(Column sourceColumn, Column targetColumn)
/*  60:    */   {
/*  61: 90 */     if (targetColumn == null)
/*  62:    */     {
/*  63: 91 */       if (this.targetColumns != null) {
/*  64: 92 */         LOG.debugf("Attempt to map column [%s] to no target column after explicit target column(s) named for FK [name=%s]", sourceColumn.toLoggableString(), getName());
/*  65:    */       }
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:100 */       checkTargetTable(targetColumn);
/*  70:101 */       if (this.targetColumns == null)
/*  71:    */       {
/*  72:102 */         if (!internalColumnAccess().isEmpty()) {
/*  73:103 */           LOG.debugf("Value mapping mismatch as part of FK [table=%s, name=%s] while adding source column [%s]", getTable().toLoggableString(), getName(), sourceColumn.toLoggableString());
/*  74:    */         }
/*  75:110 */         this.targetColumns = new ArrayList();
/*  76:    */       }
/*  77:112 */       this.targetColumns.add(targetColumn);
/*  78:    */     }
/*  79:114 */     internalAddColumn(sourceColumn);
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void checkTargetTable(Column targetColumn)
/*  83:    */   {
/*  84:118 */     if (targetColumn.getTable() != getTargetTable()) {
/*  85:119 */       throw new AssertionFailure(String.format("Unable to add column to constraint; tables [%s, %s] did not match", new Object[] { targetColumn.getTable().toLoggableString(), getTargetTable().toLoggableString() }));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getExportIdentifier()
/*  90:    */   {
/*  91:131 */     return getSourceTable().getLoggableValueQualifier() + ".FK-" + getName();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ReferentialAction getDeleteRule()
/*  95:    */   {
/*  96:135 */     return this.deleteRule;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setDeleteRule(ReferentialAction deleteRule)
/* 100:    */   {
/* 101:139 */     this.deleteRule = deleteRule;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public ReferentialAction getUpdateRule()
/* 105:    */   {
/* 106:143 */     return this.updateRule;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setUpdateRule(ReferentialAction updateRule)
/* 110:    */   {
/* 111:147 */     this.updateRule = updateRule;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String[] sqlDropStrings(Dialect dialect)
/* 115:    */   {
/* 116:152 */     return new String[] { "alter table " + getTable().getQualifiedName(dialect) + dialect.getDropForeignKeyString() + getName() };
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String sqlConstraintStringInAlterTable(Dialect dialect)
/* 120:    */   {
/* 121:161 */     String[] columnNames = new String[getColumnSpan()];
/* 122:162 */     String[] targetColumnNames = new String[getColumnSpan()];
/* 123:163 */     int i = 0;
/* 124:164 */     Iterator<Column> itTargetColumn = getTargetColumns().iterator();
/* 125:165 */     for (Column column : getColumns())
/* 126:    */     {
/* 127:166 */       if (!itTargetColumn.hasNext()) {
/* 128:167 */         throw new MappingException("More constraint columns that foreign key target columns.");
/* 129:    */       }
/* 130:169 */       columnNames[i] = column.getColumnName().encloseInQuotesIfQuoted(dialect);
/* 131:170 */       targetColumnNames[i] = ((Column)itTargetColumn.next()).getColumnName().encloseInQuotesIfQuoted(dialect);
/* 132:171 */       i++;
/* 133:    */     }
/* 134:173 */     if (itTargetColumn.hasNext()) {
/* 135:174 */       throw new MappingException("More foreign key target columns than constraint columns.");
/* 136:    */     }
/* 137:176 */     StringBuilder sb = new StringBuilder(dialect.getAddForeignKeyConstraintString(getName(), columnNames, this.targetTable.getQualifiedName(dialect), targetColumnNames, this.targetColumns == null));
/* 138:188 */     if (dialect.supportsCascadeDelete())
/* 139:    */     {
/* 140:189 */       if (this.deleteRule != ReferentialAction.NO_ACTION) {
/* 141:190 */         sb.append(" on delete ").append(this.deleteRule.getActionString());
/* 142:    */       }
/* 143:192 */       if (this.updateRule != ReferentialAction.NO_ACTION) {
/* 144:193 */         sb.append(" on update ").append(this.updateRule.getActionString());
/* 145:    */       }
/* 146:    */     }
/* 147:196 */     return sb.toString();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static enum ReferentialAction
/* 151:    */   {
/* 152:200 */     NO_ACTION("no action"),  CASCADE("cascade"),  SET_NULL("set null"),  SET_DEFAULT("set default"),  RESTRICT("restrict");
/* 153:    */     
/* 154:    */     private final String actionString;
/* 155:    */     
/* 156:    */     private ReferentialAction(String actionString)
/* 157:    */     {
/* 158:209 */       this.actionString = actionString;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public String getActionString()
/* 162:    */     {
/* 163:213 */       return this.actionString;
/* 164:    */     }
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.ForeignKey
 * JD-Core Version:    0.7.0.1
 */