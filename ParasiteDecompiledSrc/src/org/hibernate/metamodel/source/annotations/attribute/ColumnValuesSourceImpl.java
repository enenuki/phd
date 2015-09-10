/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import org.hibernate.metamodel.relational.Datatype;
/*   4:    */ import org.hibernate.metamodel.relational.Size;
/*   5:    */ import org.hibernate.metamodel.relational.Size.LobMultiplier;
/*   6:    */ import org.hibernate.metamodel.source.binder.ColumnSource;
/*   7:    */ 
/*   8:    */ public class ColumnValuesSourceImpl
/*   9:    */   implements ColumnSource
/*  10:    */ {
/*  11:    */   private ColumnValues columnValues;
/*  12:    */   
/*  13:    */   public ColumnValuesSourceImpl(ColumnValues columnValues)
/*  14:    */   {
/*  15: 37 */     this.columnValues = columnValues;
/*  16:    */   }
/*  17:    */   
/*  18:    */   void setOverrideColumnValues(ColumnValues columnValues)
/*  19:    */   {
/*  20: 41 */     this.columnValues = columnValues;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getName()
/*  24:    */   {
/*  25: 46 */     return this.columnValues.getName();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean isNullable()
/*  29:    */   {
/*  30: 51 */     return this.columnValues.isNullable();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getDefaultValue()
/*  34:    */   {
/*  35: 56 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getSqlType()
/*  39:    */   {
/*  40: 62 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Datatype getDatatype()
/*  44:    */   {
/*  45: 68 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Size getSize()
/*  49:    */   {
/*  50: 73 */     return new Size(this.columnValues.getPrecision(), this.columnValues.getScale(), this.columnValues.getLength(), Size.LobMultiplier.NONE);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isUnique()
/*  54:    */   {
/*  55: 83 */     return this.columnValues.isUnique();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getComment()
/*  59:    */   {
/*  60: 89 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isIncludedInInsert()
/*  64:    */   {
/*  65: 94 */     return this.columnValues.isInsertable();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isIncludedInUpdate()
/*  69:    */   {
/*  70: 99 */     return this.columnValues.isUpdatable();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getContainingTableName()
/*  74:    */   {
/*  75:104 */     return this.columnValues.getTable();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getReadFragment()
/*  79:    */   {
/*  80:112 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getWriteFragment()
/*  84:    */   {
/*  85:117 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCheckCondition()
/*  89:    */   {
/*  90:122 */     return null;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.ColumnValuesSourceImpl
 * JD-Core Version:    0.7.0.1
 */