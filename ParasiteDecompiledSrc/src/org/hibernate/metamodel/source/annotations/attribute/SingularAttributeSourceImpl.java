/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.mapping.PropertyGeneration;
/*   7:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*   8:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*   9:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  10:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  11:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  12:    */ 
/*  13:    */ public class SingularAttributeSourceImpl
/*  14:    */   implements SingularAttributeSource
/*  15:    */ {
/*  16:    */   private final MappedAttribute attribute;
/*  17:    */   private final AttributeOverride attributeOverride;
/*  18:    */   
/*  19:    */   public SingularAttributeSourceImpl(MappedAttribute attribute)
/*  20:    */   {
/*  21: 45 */     this(attribute, null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public SingularAttributeSourceImpl(MappedAttribute attribute, AttributeOverride attributeOverride)
/*  25:    */   {
/*  26: 49 */     this.attribute = attribute;
/*  27: 50 */     this.attributeOverride = attributeOverride;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ExplicitHibernateTypeSource getTypeInformation()
/*  31:    */   {
/*  32: 55 */     return new ExplicitHibernateTypeSourceImpl(this.attribute.getHibernateTypeResolver());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getPropertyAccessorName()
/*  36:    */   {
/*  37: 60 */     return this.attribute.getAccessType();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isInsertable()
/*  41:    */   {
/*  42: 65 */     return this.attribute.isInsertable();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isUpdatable()
/*  46:    */   {
/*  47: 70 */     return this.attribute.isUpdatable();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PropertyGeneration getGeneration()
/*  51:    */   {
/*  52: 75 */     return this.attribute.getPropertyGeneration();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isLazy()
/*  56:    */   {
/*  57: 80 */     return this.attribute.isLazy();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isIncludedInOptimisticLocking()
/*  61:    */   {
/*  62: 85 */     return this.attribute.isOptimisticLockable();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67: 90 */     return this.attribute.getName();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<RelationalValueSource> relationalValueSources()
/*  71:    */   {
/*  72: 95 */     List<RelationalValueSource> valueSources = new ArrayList();
/*  73: 96 */     valueSources.add(new ColumnSourceImpl(this.attribute, this.attributeOverride));
/*  74: 97 */     return valueSources;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isVirtualAttribute()
/*  78:    */   {
/*  79:102 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isSingular()
/*  83:    */   {
/*  84:107 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public SingularAttributeNature getNature()
/*  88:    */   {
/*  89:112 */     return SingularAttributeNature.BASIC;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Iterable<MetaAttributeSource> metaAttributes()
/*  93:    */   {
/*  94:117 */     return Collections.emptySet();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean areValuesIncludedInInsertByDefault()
/*  98:    */   {
/*  99:122 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 103:    */   {
/* 104:127 */     return true;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean areValuesNullableByDefault()
/* 108:    */   {
/* 109:132 */     return true;
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.SingularAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */