/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ 
/*  9:   */ public class InsertSelect
/* 10:   */ {
/* 11:   */   private Dialect dialect;
/* 12:   */   private String tableName;
/* 13:   */   private String comment;
/* 14:43 */   private List columnNames = new ArrayList();
/* 15:   */   private Select select;
/* 16:   */   
/* 17:   */   public InsertSelect(Dialect dialect)
/* 18:   */   {
/* 19:47 */     this.dialect = dialect;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public InsertSelect setTableName(String tableName)
/* 23:   */   {
/* 24:51 */     this.tableName = tableName;
/* 25:52 */     return this;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public InsertSelect setComment(String comment)
/* 29:   */   {
/* 30:56 */     this.comment = comment;
/* 31:57 */     return this;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public InsertSelect addColumn(String columnName)
/* 35:   */   {
/* 36:61 */     this.columnNames.add(columnName);
/* 37:62 */     return this;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public InsertSelect addColumns(String[] columnNames)
/* 41:   */   {
/* 42:66 */     for (int i = 0; i < columnNames.length; i++) {
/* 43:67 */       this.columnNames.add(columnNames[i]);
/* 44:   */     }
/* 45:69 */     return this;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public InsertSelect setSelect(Select select)
/* 49:   */   {
/* 50:73 */     this.select = select;
/* 51:74 */     return this;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toStatementString()
/* 55:   */   {
/* 56:78 */     if (this.tableName == null) {
/* 57:78 */       throw new HibernateException("no table name defined for insert-select");
/* 58:   */     }
/* 59:79 */     if (this.select == null) {
/* 60:79 */       throw new HibernateException("no select defined for insert-select");
/* 61:   */     }
/* 62:81 */     StringBuffer buf = new StringBuffer(this.columnNames.size() * 15 + this.tableName.length() + 10);
/* 63:82 */     if (this.comment != null) {
/* 64:83 */       buf.append("/* ").append(this.comment).append(" */ ");
/* 65:   */     }
/* 66:85 */     buf.append("insert into ").append(this.tableName);
/* 67:86 */     if (!this.columnNames.isEmpty())
/* 68:   */     {
/* 69:87 */       buf.append(" (");
/* 70:88 */       Iterator itr = this.columnNames.iterator();
/* 71:89 */       while (itr.hasNext())
/* 72:   */       {
/* 73:90 */         buf.append(itr.next());
/* 74:91 */         if (itr.hasNext()) {
/* 75:92 */           buf.append(", ");
/* 76:   */         }
/* 77:   */       }
/* 78:95 */       buf.append(")");
/* 79:   */     }
/* 80:97 */     buf.append(' ').append(this.select.toStatementString());
/* 81:98 */     return buf.toString();
/* 82:   */   }
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.InsertSelect
 * JD-Core Version:    0.7.0.1
 */