/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbColumnElement;
/*   4:    */ import org.hibernate.metamodel.relational.Datatype;
/*   5:    */ import org.hibernate.metamodel.relational.Size;
/*   6:    */ import org.hibernate.metamodel.relational.Size.LobMultiplier;
/*   7:    */ import org.hibernate.metamodel.source.binder.ColumnSource;
/*   8:    */ 
/*   9:    */ class ColumnSourceImpl
/*  10:    */   implements ColumnSource
/*  11:    */ {
/*  12:    */   private final String tableName;
/*  13:    */   private final JaxbColumnElement columnElement;
/*  14:    */   private boolean includedInInsert;
/*  15:    */   private boolean includedInUpdate;
/*  16:    */   private final boolean isForceNotNull;
/*  17:    */   
/*  18:    */   ColumnSourceImpl(String tableName, JaxbColumnElement columnElement, boolean isIncludedInInsert, boolean isIncludedInUpdate)
/*  19:    */   {
/*  20: 46 */     this(tableName, columnElement, isIncludedInInsert, isIncludedInUpdate, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   ColumnSourceImpl(String tableName, JaxbColumnElement columnElement, boolean isIncludedInInsert, boolean isIncludedInUpdate, boolean isForceNotNull)
/*  24:    */   {
/*  25: 54 */     this.tableName = tableName;
/*  26: 55 */     this.columnElement = columnElement;
/*  27: 56 */     this.isForceNotNull = isForceNotNull;
/*  28: 57 */     this.includedInInsert = isIncludedInInsert;
/*  29: 58 */     this.includedInUpdate = isIncludedInUpdate;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getName()
/*  33:    */   {
/*  34: 63 */     return this.columnElement.getName();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isNullable()
/*  38:    */   {
/*  39: 68 */     if (this.isForceNotNull) {
/*  40: 68 */       return false;
/*  41:    */     }
/*  42: 69 */     return !this.columnElement.isNotNull().booleanValue();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getDefaultValue()
/*  46:    */   {
/*  47: 74 */     return this.columnElement.getDefault();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getSqlType()
/*  51:    */   {
/*  52: 79 */     return this.columnElement.getSqlType();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Datatype getDatatype()
/*  56:    */   {
/*  57: 84 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Size getSize()
/*  61:    */   {
/*  62: 89 */     return new Size(Helper.getIntValue(this.columnElement.getPrecision(), -1), Helper.getIntValue(this.columnElement.getScale(), -1), Helper.getLongValue(this.columnElement.getLength(), -1L), Size.LobMultiplier.NONE);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getReadFragment()
/*  66:    */   {
/*  67: 99 */     return this.columnElement.getRead();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getWriteFragment()
/*  71:    */   {
/*  72:104 */     return this.columnElement.getWrite();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isUnique()
/*  76:    */   {
/*  77:109 */     return this.columnElement.isUnique().booleanValue();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getCheckCondition()
/*  81:    */   {
/*  82:114 */     return this.columnElement.getCheck();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getComment()
/*  86:    */   {
/*  87:119 */     return this.columnElement.getComment();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isIncludedInInsert()
/*  91:    */   {
/*  92:124 */     return this.includedInInsert;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isIncludedInUpdate()
/*  96:    */   {
/*  97:129 */     return this.includedInUpdate;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getContainingTableName()
/* 101:    */   {
/* 102:134 */     return this.tableName;
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.ColumnSourceImpl
 * JD-Core Version:    0.7.0.1
 */