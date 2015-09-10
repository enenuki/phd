/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import javax.persistence.OrderColumn;
/*   5:    */ import org.hibernate.mapping.Join;
/*   6:    */ 
/*   7:    */ public class IndexColumn
/*   8:    */   extends Ejb3Column
/*   9:    */ {
/*  10:    */   private int base;
/*  11:    */   
/*  12:    */   public IndexColumn(boolean isImplicit, String sqlType, int length, int precision, int scale, String name, boolean nullable, boolean unique, boolean insertable, boolean updatable, String secondaryTableName, Map<String, Join> joins, PropertyHolder propertyHolder, Mappings mappings)
/*  13:    */   {
/*  14: 55 */     setImplicit(isImplicit);
/*  15: 56 */     setSqlType(sqlType);
/*  16: 57 */     setLength(length);
/*  17: 58 */     setPrecision(precision);
/*  18: 59 */     setScale(scale);
/*  19: 60 */     setLogicalColumnName(name);
/*  20: 61 */     setNullable(nullable);
/*  21: 62 */     setUnique(unique);
/*  22: 63 */     setInsertable(insertable);
/*  23: 64 */     setUpdatable(updatable);
/*  24: 65 */     setSecondaryTableName(secondaryTableName);
/*  25: 66 */     setPropertyHolder(propertyHolder);
/*  26: 67 */     setJoins(joins);
/*  27: 68 */     setMappings(mappings);
/*  28: 69 */     bind();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getBase()
/*  32:    */   {
/*  33: 73 */     return this.base;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setBase(int base)
/*  37:    */   {
/*  38: 77 */     this.base = base;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static IndexColumn buildColumnFromAnnotation(OrderColumn ann, PropertyHolder propertyHolder, PropertyData inferredData, Map<String, Join> secondaryTables, Mappings mappings)
/*  42:    */   {
/*  43:    */     IndexColumn column;
/*  44:    */     IndexColumn column;
/*  45: 88 */     if (ann != null)
/*  46:    */     {
/*  47: 89 */       String sqlType = BinderHelper.isEmptyAnnotationValue(ann.columnDefinition()) ? null : ann.columnDefinition();
/*  48: 90 */       String name = BinderHelper.isEmptyAnnotationValue(ann.name()) ? inferredData.getPropertyName() + "_ORDER" : ann.name();
/*  49:    */       
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 98 */       column = new IndexColumn(false, sqlType, 0, 0, 0, name, ann.nullable(), false, ann.insertable(), ann.updatable(), null, secondaryTables, propertyHolder, mappings);
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60:105 */       column = new IndexColumn(true, null, 0, 0, 0, null, true, false, true, true, null, null, propertyHolder, mappings);
/*  61:    */     }
/*  62:110 */     return column;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static IndexColumn buildColumnFromAnnotation(org.hibernate.annotations.IndexColumn ann, PropertyHolder propertyHolder, PropertyData inferredData, Mappings mappings)
/*  66:    */   {
/*  67:    */     IndexColumn column;
/*  68:120 */     if (ann != null)
/*  69:    */     {
/*  70:121 */       String sqlType = BinderHelper.isEmptyAnnotationValue(ann.columnDefinition()) ? null : ann.columnDefinition();
/*  71:122 */       String name = BinderHelper.isEmptyAnnotationValue(ann.name()) ? inferredData.getPropertyName() : ann.name();
/*  72:    */       
/*  73:124 */       IndexColumn column = new IndexColumn(false, sqlType, 0, 0, 0, name, ann.nullable(), false, true, true, null, null, propertyHolder, mappings);
/*  74:    */       
/*  75:    */ 
/*  76:    */ 
/*  77:128 */       column.setBase(ann.base());
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:131 */       column = new IndexColumn(true, null, 0, 0, 0, null, true, false, true, true, null, null, propertyHolder, mappings);
/*  82:    */     }
/*  83:136 */     return column;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.IndexColumn
 * JD-Core Version:    0.7.0.1
 */