/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.hibernate.mapping.Column;
/*  9:   */ import org.hibernate.mapping.ForeignKey;
/* 10:   */ import org.hibernate.mapping.PrimaryKey;
/* 11:   */ import org.hibernate.mapping.Table;
/* 12:   */ 
/* 13:   */ public class ForeignKeyMetadata
/* 14:   */ {
/* 15:   */   private final String name;
/* 16:   */   private final String refTable;
/* 17:42 */   private final Map references = new HashMap();
/* 18:   */   
/* 19:   */   ForeignKeyMetadata(ResultSet rs)
/* 20:   */     throws SQLException
/* 21:   */   {
/* 22:45 */     this.name = rs.getString("FK_NAME");
/* 23:46 */     this.refTable = rs.getString("PKTABLE_NAME");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getName()
/* 27:   */   {
/* 28:50 */     return this.name;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getReferencedTableName()
/* 32:   */   {
/* 33:54 */     return this.refTable;
/* 34:   */   }
/* 35:   */   
/* 36:   */   void addReference(ResultSet rs)
/* 37:   */     throws SQLException
/* 38:   */   {
/* 39:58 */     this.references.put(rs.getString("FKCOLUMN_NAME").toLowerCase(), rs.getString("PKCOLUMN_NAME"));
/* 40:   */   }
/* 41:   */   
/* 42:   */   private boolean hasReference(Column column, Column ref)
/* 43:   */   {
/* 44:62 */     String refName = (String)this.references.get(column.getName().toLowerCase());
/* 45:63 */     return ref.getName().equalsIgnoreCase(refName);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean matches(ForeignKey fk)
/* 49:   */   {
/* 50:67 */     if ((this.refTable.equalsIgnoreCase(fk.getReferencedTable().getName())) && 
/* 51:68 */       (fk.getColumnSpan() == this.references.size()))
/* 52:   */     {
/* 53:   */       List fkRefs;
/* 54:   */       List fkRefs;
/* 55:70 */       if (fk.isReferenceToPrimaryKey()) {
/* 56:71 */         fkRefs = fk.getReferencedTable().getPrimaryKey().getColumns();
/* 57:   */       } else {
/* 58:74 */         fkRefs = fk.getReferencedColumns();
/* 59:   */       }
/* 60:76 */       for (int i = 0; i < fk.getColumnSpan(); i++)
/* 61:   */       {
/* 62:77 */         Column column = fk.getColumn(i);
/* 63:78 */         Column ref = (Column)fkRefs.get(i);
/* 64:79 */         if (!hasReference(column, ref)) {
/* 65:80 */           return false;
/* 66:   */         }
/* 67:   */       }
/* 68:83 */       return true;
/* 69:   */     }
/* 70:86 */     return false;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String toString()
/* 74:   */   {
/* 75:90 */     return "ForeignKeyMetadata(" + this.name + ')';
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ForeignKeyMetadata
 * JD-Core Version:    0.7.0.1
 */