/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.NamingStrategy;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ import org.hibernate.metamodel.source.annotations.entity.EntityBindingContext;
/*  6:   */ 
/*  7:   */ public class ColumnSourceImpl
/*  8:   */   extends ColumnValuesSourceImpl
/*  9:   */ {
/* 10:   */   private final MappedAttribute attribute;
/* 11:   */   private final String name;
/* 12:   */   
/* 13:   */   ColumnSourceImpl(MappedAttribute attribute, AttributeOverride attributeOverride)
/* 14:   */   {
/* 15:36 */     super(attribute.getColumnValues());
/* 16:37 */     if (attributeOverride != null) {
/* 17:38 */       setOverrideColumnValues(attributeOverride.getColumnValues());
/* 18:   */     }
/* 19:40 */     this.attribute = attribute;
/* 20:41 */     this.name = resolveColumnName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected String resolveColumnName()
/* 24:   */   {
/* 25:45 */     if (StringHelper.isEmpty(super.getName())) {
/* 26:47 */       return this.attribute.getContext().getNamingStrategy().propertyToColumnName(this.attribute.getName());
/* 27:   */     }
/* 28:50 */     return super.getName();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getName()
/* 32:   */   {
/* 33:56 */     return this.name;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getReadFragment()
/* 37:   */   {
/* 38:61 */     if ((this.attribute instanceof BasicAttribute)) {
/* 39:62 */       return ((BasicAttribute)this.attribute).getCustomReadFragment();
/* 40:   */     }
/* 41:65 */     return null;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getWriteFragment()
/* 45:   */   {
/* 46:71 */     if ((this.attribute instanceof BasicAttribute)) {
/* 47:72 */       return ((BasicAttribute)this.attribute).getCustomWriteFragment();
/* 48:   */     }
/* 49:75 */     return null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String getCheckCondition()
/* 53:   */   {
/* 54:81 */     if ((this.attribute instanceof BasicAttribute)) {
/* 55:82 */       return ((BasicAttribute)this.attribute).getCheckCondition();
/* 56:   */     }
/* 57:85 */     return null;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.ColumnSourceImpl
 * JD-Core Version:    0.7.0.1
 */