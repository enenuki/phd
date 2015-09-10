/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.type.LiteralType;
/*  10:    */ 
/*  11:    */ public class Insert
/*  12:    */ {
/*  13:    */   private Dialect dialect;
/*  14:    */   private String tableName;
/*  15:    */   private String comment;
/*  16: 42 */   private Map columns = new LinkedHashMap();
/*  17:    */   
/*  18:    */   public Insert(Dialect dialect)
/*  19:    */   {
/*  20: 45 */     this.dialect = dialect;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected Dialect getDialect()
/*  24:    */   {
/*  25: 49 */     return this.dialect;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Insert setComment(String comment)
/*  29:    */   {
/*  30: 53 */     this.comment = comment;
/*  31: 54 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Insert addColumn(String columnName)
/*  35:    */   {
/*  36: 58 */     return addColumn(columnName, "?");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Insert addColumns(String[] columnNames)
/*  40:    */   {
/*  41: 62 */     for (int i = 0; i < columnNames.length; i++) {
/*  42: 63 */       addColumn(columnNames[i]);
/*  43:    */     }
/*  44: 65 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Insert addColumns(String[] columnNames, boolean[] insertable)
/*  48:    */   {
/*  49: 69 */     for (int i = 0; i < columnNames.length; i++) {
/*  50: 70 */       if (insertable[i] != 0) {
/*  51: 71 */         addColumn(columnNames[i]);
/*  52:    */       }
/*  53:    */     }
/*  54: 74 */     return this;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Insert addColumns(String[] columnNames, boolean[] insertable, String[] valueExpressions)
/*  58:    */   {
/*  59: 78 */     for (int i = 0; i < columnNames.length; i++) {
/*  60: 79 */       if (insertable[i] != 0) {
/*  61: 80 */         addColumn(columnNames[i], valueExpressions[i]);
/*  62:    */       }
/*  63:    */     }
/*  64: 83 */     return this;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Insert addColumn(String columnName, String valueExpression)
/*  68:    */   {
/*  69: 87 */     this.columns.put(columnName, valueExpression);
/*  70: 88 */     return this;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Insert addColumn(String columnName, Object value, LiteralType type)
/*  74:    */     throws Exception
/*  75:    */   {
/*  76: 92 */     return addColumn(columnName, type.objectToSQLString(value, this.dialect));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Insert addIdentityColumn(String columnName)
/*  80:    */   {
/*  81: 96 */     String value = this.dialect.getIdentityInsertString();
/*  82: 97 */     if (value != null) {
/*  83: 98 */       addColumn(columnName, value);
/*  84:    */     }
/*  85:100 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Insert setTableName(String tableName)
/*  89:    */   {
/*  90:104 */     this.tableName = tableName;
/*  91:105 */     return this;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String toStatementString()
/*  95:    */   {
/*  96:109 */     StringBuffer buf = new StringBuffer(this.columns.size() * 15 + this.tableName.length() + 10);
/*  97:110 */     if (this.comment != null) {
/*  98:111 */       buf.append("/* ").append(this.comment).append(" */ ");
/*  99:    */     }
/* 100:113 */     buf.append("insert into ").append(this.tableName);
/* 101:115 */     if (this.columns.size() == 0)
/* 102:    */     {
/* 103:116 */       buf.append(' ').append(this.dialect.getNoColumnsInsertString());
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:119 */       buf.append(" (");
/* 108:120 */       Iterator iter = this.columns.keySet().iterator();
/* 109:121 */       while (iter.hasNext())
/* 110:    */       {
/* 111:122 */         buf.append(iter.next());
/* 112:123 */         if (iter.hasNext()) {
/* 113:124 */           buf.append(", ");
/* 114:    */         }
/* 115:    */       }
/* 116:127 */       buf.append(") values (");
/* 117:128 */       iter = this.columns.values().iterator();
/* 118:129 */       while (iter.hasNext())
/* 119:    */       {
/* 120:130 */         buf.append(iter.next());
/* 121:131 */         if (iter.hasNext()) {
/* 122:132 */           buf.append(", ");
/* 123:    */         }
/* 124:    */       }
/* 125:135 */       buf.append(')');
/* 126:    */     }
/* 127:137 */     return buf.toString();
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Insert
 * JD-Core Version:    0.7.0.1
 */