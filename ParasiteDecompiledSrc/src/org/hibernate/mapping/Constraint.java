/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ import org.hibernate.engine.spi.Mapping;
/*   9:    */ 
/*  10:    */ public abstract class Constraint
/*  11:    */   implements RelationalModel, Serializable
/*  12:    */ {
/*  13:    */   private String name;
/*  14: 41 */   private final List columns = new ArrayList();
/*  15:    */   private Table table;
/*  16:    */   
/*  17:    */   public String getName()
/*  18:    */   {
/*  19: 45 */     return this.name;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setName(String name)
/*  23:    */   {
/*  24: 49 */     this.name = name;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Iterator getColumnIterator()
/*  28:    */   {
/*  29: 53 */     return this.columns.iterator();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void addColumn(Column column)
/*  33:    */   {
/*  34: 57 */     if (!this.columns.contains(column)) {
/*  35: 57 */       this.columns.add(column);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addColumns(Iterator columnIterator)
/*  40:    */   {
/*  41: 61 */     while (columnIterator.hasNext())
/*  42:    */     {
/*  43: 62 */       Selectable col = (Selectable)columnIterator.next();
/*  44: 63 */       if (!col.isFormula()) {
/*  45: 63 */         addColumn((Column)col);
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean containsColumn(Column column)
/*  51:    */   {
/*  52: 72 */     return this.columns.contains(column);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getColumnSpan()
/*  56:    */   {
/*  57: 76 */     return this.columns.size();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Column getColumn(int i)
/*  61:    */   {
/*  62: 80 */     return (Column)this.columns.get(i);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Iterator columnIterator()
/*  66:    */   {
/*  67: 84 */     return this.columns.iterator();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Table getTable()
/*  71:    */   {
/*  72: 88 */     return this.table;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setTable(Table table)
/*  76:    */   {
/*  77: 92 */     this.table = table;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isGenerated(Dialect dialect)
/*  81:    */   {
/*  82: 96 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/*  86:    */   {
/*  87:100 */     if (isGenerated(dialect)) {
/*  88:101 */       return "alter table " + getTable().getQualifiedName(dialect, defaultCatalog, defaultSchema) + " drop constraint " + dialect.quote(getName());
/*  89:    */     }
/*  90:109 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String sqlCreateString(Dialect dialect, Mapping p, String defaultCatalog, String defaultSchema)
/*  94:    */   {
/*  95:114 */     if (isGenerated(dialect))
/*  96:    */     {
/*  97:115 */       String constraintString = sqlConstraintString(dialect, getName(), defaultCatalog, defaultSchema);
/*  98:116 */       StringBuffer buf = new StringBuffer("alter table ").append(getTable().getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(constraintString);
/*  99:    */       
/* 100:    */ 
/* 101:119 */       return buf.toString();
/* 102:    */     }
/* 103:122 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List getColumns()
/* 107:    */   {
/* 108:127 */     return this.columns;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public abstract String sqlConstraintString(Dialect paramDialect, String paramString1, String paramString2, String paramString3);
/* 112:    */   
/* 113:    */   public String toString()
/* 114:    */   {
/* 115:134 */     return getClass().getName() + '(' + getTable().getName() + getColumns() + ") as " + this.name;
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Constraint
 * JD-Core Version:    0.7.0.1
 */