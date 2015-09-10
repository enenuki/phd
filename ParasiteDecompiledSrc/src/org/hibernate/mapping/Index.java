/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.Mapping;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ 
/*  12:    */ public class Index
/*  13:    */   implements RelationalModel, Serializable
/*  14:    */ {
/*  15:    */   private Table table;
/*  16: 43 */   private List columns = new ArrayList();
/*  17:    */   private String name;
/*  18:    */   
/*  19:    */   public String sqlCreateString(Dialect dialect, Mapping mapping, String defaultCatalog, String defaultSchema)
/*  20:    */     throws HibernateException
/*  21:    */   {
/*  22: 48 */     return buildSqlCreateIndexString(dialect, getName(), getTable(), getColumnIterator(), false, defaultCatalog, defaultSchema);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static String buildSqlDropIndexString(Dialect dialect, Table table, String name, String defaultCatalog, String defaultSchema)
/*  26:    */   {
/*  27: 66 */     return "drop index " + StringHelper.qualify(table.getQualifiedName(dialect, defaultCatalog, defaultSchema), name);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String buildSqlCreateIndexString(Dialect dialect, String name, Table table, Iterator columns, boolean unique, String defaultCatalog, String defaultSchema)
/*  31:    */   {
/*  32: 83 */     StringBuffer buf = new StringBuffer("create").append(unique ? " unique" : "").append(" index ").append(dialect.qualifyIndexName() ? name : StringHelper.unqualify(name)).append(" on ").append(table.getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(" (");
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
/*  43: 94 */     Iterator iter = columns;
/*  44: 95 */     while (iter.hasNext())
/*  45:    */     {
/*  46: 96 */       buf.append(((Column)iter.next()).getQuotedName(dialect));
/*  47: 97 */       if (iter.hasNext()) {
/*  48: 97 */         buf.append(", ");
/*  49:    */       }
/*  50:    */     }
/*  51: 99 */     buf.append(")");
/*  52:100 */     return buf.toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String sqlConstraintString(Dialect dialect)
/*  56:    */   {
/*  57:106 */     StringBuffer buf = new StringBuffer(" index (");
/*  58:107 */     Iterator iter = getColumnIterator();
/*  59:108 */     while (iter.hasNext())
/*  60:    */     {
/*  61:109 */       buf.append(((Column)iter.next()).getQuotedName(dialect));
/*  62:110 */       if (iter.hasNext()) {
/*  63:110 */         buf.append(", ");
/*  64:    */       }
/*  65:    */     }
/*  66:112 */     return ')';
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/*  70:    */   {
/*  71:116 */     return "drop index " + StringHelper.qualify(this.table.getQualifiedName(dialect, defaultCatalog, defaultSchema), this.name);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Table getTable()
/*  75:    */   {
/*  76:124 */     return this.table;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setTable(Table table)
/*  80:    */   {
/*  81:128 */     this.table = table;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getColumnSpan()
/*  85:    */   {
/*  86:132 */     return this.columns.size();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Iterator getColumnIterator()
/*  90:    */   {
/*  91:136 */     return this.columns.iterator();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void addColumn(Column column)
/*  95:    */   {
/*  96:140 */     if (!this.columns.contains(column)) {
/*  97:140 */       this.columns.add(column);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void addColumns(Iterator extraColumns)
/* 102:    */   {
/* 103:144 */     while (extraColumns.hasNext()) {
/* 104:144 */       addColumn((Column)extraColumns.next());
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean containsColumn(Column column)
/* 109:    */   {
/* 110:152 */     return this.columns.contains(column);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getName()
/* 114:    */   {
/* 115:156 */     return this.name;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setName(String name)
/* 119:    */   {
/* 120:160 */     this.name = name;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String toString()
/* 124:    */   {
/* 125:164 */     return getClass().getName() + "(" + getName() + ")";
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Index
 * JD-Core Version:    0.7.0.1
 */