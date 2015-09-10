/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.StringTokenizer;
/*  6:   */ 
/*  7:   */ public class ColumnMetadata
/*  8:   */ {
/*  9:   */   private final String name;
/* 10:   */   private final String typeName;
/* 11:   */   private final int columnSize;
/* 12:   */   private final int decimalDigits;
/* 13:   */   private final String isNullable;
/* 14:   */   private final int typeCode;
/* 15:   */   
/* 16:   */   ColumnMetadata(ResultSet rs)
/* 17:   */     throws SQLException
/* 18:   */   {
/* 19:43 */     this.name = rs.getString("COLUMN_NAME");
/* 20:44 */     this.columnSize = rs.getInt("COLUMN_SIZE");
/* 21:45 */     this.decimalDigits = rs.getInt("DECIMAL_DIGITS");
/* 22:46 */     this.isNullable = rs.getString("IS_NULLABLE");
/* 23:47 */     this.typeCode = rs.getInt("DATA_TYPE");
/* 24:48 */     this.typeName = new StringTokenizer(rs.getString("TYPE_NAME"), "() ").nextToken();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getName()
/* 28:   */   {
/* 29:52 */     return this.name;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getTypeName()
/* 33:   */   {
/* 34:56 */     return this.typeName;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getColumnSize()
/* 38:   */   {
/* 39:60 */     return this.columnSize;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int getDecimalDigits()
/* 43:   */   {
/* 44:64 */     return this.decimalDigits;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getNullable()
/* 48:   */   {
/* 49:68 */     return this.isNullable;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:72 */     return "ColumnMetadata(" + this.name + ')';
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int getTypeCode()
/* 58:   */   {
/* 59:76 */     return this.typeCode;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ColumnMetadata
 * JD-Core Version:    0.7.0.1
 */