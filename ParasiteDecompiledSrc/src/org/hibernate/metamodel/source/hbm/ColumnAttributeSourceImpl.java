/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import org.hibernate.metamodel.relational.Datatype;
/*   4:    */ import org.hibernate.metamodel.relational.Size;
/*   5:    */ import org.hibernate.metamodel.source.binder.ColumnSource;
/*   6:    */ 
/*   7:    */ class ColumnAttributeSourceImpl
/*   8:    */   implements ColumnSource
/*   9:    */ {
/*  10:    */   private final String tableName;
/*  11:    */   private final String columnName;
/*  12:    */   private boolean includedInInsert;
/*  13:    */   private boolean includedInUpdate;
/*  14:    */   private boolean isForceNotNull;
/*  15:    */   
/*  16:    */   ColumnAttributeSourceImpl(String tableName, String columnName, boolean includedInInsert, boolean includedInUpdate)
/*  17:    */   {
/*  18: 45 */     this(tableName, columnName, includedInInsert, includedInUpdate, false);
/*  19:    */   }
/*  20:    */   
/*  21:    */   ColumnAttributeSourceImpl(String tableName, String columnName, boolean includedInInsert, boolean includedInUpdate, boolean isForceNotNull)
/*  22:    */   {
/*  23: 54 */     this.tableName = tableName;
/*  24: 55 */     this.columnName = columnName;
/*  25: 56 */     this.includedInInsert = includedInInsert;
/*  26: 57 */     this.includedInUpdate = includedInUpdate;
/*  27: 58 */     this.isForceNotNull = isForceNotNull;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isIncludedInInsert()
/*  31:    */   {
/*  32: 63 */     return this.includedInInsert;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isIncludedInUpdate()
/*  36:    */   {
/*  37: 68 */     return this.includedInUpdate;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getContainingTableName()
/*  41:    */   {
/*  42: 73 */     return this.tableName;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getName()
/*  46:    */   {
/*  47: 78 */     return this.columnName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isNullable()
/*  51:    */   {
/*  52: 83 */     return !this.isForceNotNull;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getDefaultValue()
/*  56:    */   {
/*  57: 88 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getSqlType()
/*  61:    */   {
/*  62: 93 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Datatype getDatatype()
/*  66:    */   {
/*  67: 98 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Size getSize()
/*  71:    */   {
/*  72:103 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getReadFragment()
/*  76:    */   {
/*  77:108 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getWriteFragment()
/*  81:    */   {
/*  82:113 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isUnique()
/*  86:    */   {
/*  87:118 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCheckCondition()
/*  91:    */   {
/*  92:123 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getComment()
/*  96:    */   {
/*  97:128 */     return null;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.ColumnAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */