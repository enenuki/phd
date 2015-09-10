/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.LinkedHashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class Delete
/*  10:    */ {
/*  11:    */   private String tableName;
/*  12:    */   private String versionColumnName;
/*  13:    */   private String where;
/*  14: 41 */   private Map primaryKeyColumns = new LinkedHashMap();
/*  15:    */   private String comment;
/*  16:    */   
/*  17:    */   public Delete setComment(String comment)
/*  18:    */   {
/*  19: 45 */     this.comment = comment;
/*  20: 46 */     return this;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Delete setTableName(String tableName)
/*  24:    */   {
/*  25: 50 */     this.tableName = tableName;
/*  26: 51 */     return this;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String toStatementString()
/*  30:    */   {
/*  31: 55 */     StringBuffer buf = new StringBuffer(this.tableName.length() + 10);
/*  32: 56 */     if (this.comment != null) {
/*  33: 57 */       buf.append("/* ").append(this.comment).append(" */ ");
/*  34:    */     }
/*  35: 59 */     buf.append("delete from ").append(this.tableName);
/*  36: 60 */     if ((this.where != null) || (!this.primaryKeyColumns.isEmpty()) || (this.versionColumnName != null)) {
/*  37: 61 */       buf.append(" where ");
/*  38:    */     }
/*  39: 63 */     boolean conditionsAppended = false;
/*  40: 64 */     Iterator iter = this.primaryKeyColumns.entrySet().iterator();
/*  41: 65 */     while (iter.hasNext())
/*  42:    */     {
/*  43: 66 */       Map.Entry e = (Map.Entry)iter.next();
/*  44: 67 */       buf.append(e.getKey()).append('=').append(e.getValue());
/*  45: 68 */       if (iter.hasNext()) {
/*  46: 69 */         buf.append(" and ");
/*  47:    */       }
/*  48: 71 */       conditionsAppended = true;
/*  49:    */     }
/*  50: 73 */     if (this.where != null)
/*  51:    */     {
/*  52: 74 */       if (conditionsAppended) {
/*  53: 75 */         buf.append(" and ");
/*  54:    */       }
/*  55: 77 */       buf.append(this.where);
/*  56: 78 */       conditionsAppended = true;
/*  57:    */     }
/*  58: 80 */     if (this.versionColumnName != null)
/*  59:    */     {
/*  60: 81 */       if (conditionsAppended) {
/*  61: 82 */         buf.append(" and ");
/*  62:    */       }
/*  63: 84 */       buf.append(this.versionColumnName).append("=?");
/*  64:    */     }
/*  65: 86 */     return buf.toString();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Delete setWhere(String where)
/*  69:    */   {
/*  70: 90 */     this.where = where;
/*  71: 91 */     return this;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Delete addWhereFragment(String fragment)
/*  75:    */   {
/*  76: 95 */     if (this.where == null) {
/*  77: 96 */       this.where = fragment;
/*  78:    */     } else {
/*  79: 99 */       this.where = (this.where + " and " + fragment);
/*  80:    */     }
/*  81:101 */     return this;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Delete setPrimaryKeyColumnNames(String[] columnNames)
/*  85:    */   {
/*  86:105 */     this.primaryKeyColumns.clear();
/*  87:106 */     addPrimaryKeyColumns(columnNames);
/*  88:107 */     return this;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Delete addPrimaryKeyColumns(String[] columnNames)
/*  92:    */   {
/*  93:111 */     for (int i = 0; i < columnNames.length; i++) {
/*  94:112 */       addPrimaryKeyColumn(columnNames[i], "?");
/*  95:    */     }
/*  96:114 */     return this;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Delete addPrimaryKeyColumns(String[] columnNames, boolean[] includeColumns, String[] valueExpressions)
/* 100:    */   {
/* 101:118 */     for (int i = 0; i < columnNames.length; i++) {
/* 102:119 */       if (includeColumns[i] != 0) {
/* 103:119 */         addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
/* 104:    */       }
/* 105:    */     }
/* 106:121 */     return this;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Delete addPrimaryKeyColumns(String[] columnNames, String[] valueExpressions)
/* 110:    */   {
/* 111:125 */     for (int i = 0; i < columnNames.length; i++) {
/* 112:126 */       addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
/* 113:    */     }
/* 114:128 */     return this;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Delete addPrimaryKeyColumn(String columnName, String valueExpression)
/* 118:    */   {
/* 119:132 */     this.primaryKeyColumns.put(columnName, valueExpression);
/* 120:133 */     return this;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Delete setVersionColumnName(String versionColumnName)
/* 124:    */   {
/* 125:137 */     this.versionColumnName = versionColumnName;
/* 126:138 */     return this;
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Delete
 * JD-Core Version:    0.7.0.1
 */