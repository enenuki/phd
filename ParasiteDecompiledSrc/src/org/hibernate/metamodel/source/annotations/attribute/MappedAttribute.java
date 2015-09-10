/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.mapping.PropertyGeneration;
/*   6:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*   7:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*   8:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*   9:    */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolver;
/*  10:    */ import org.hibernate.metamodel.source.annotations.entity.EntityBindingContext;
/*  11:    */ import org.jboss.jandex.AnnotationInstance;
/*  12:    */ import org.jboss.jandex.AnnotationValue;
/*  13:    */ import org.jboss.jandex.DotName;
/*  14:    */ 
/*  15:    */ public abstract class MappedAttribute
/*  16:    */   implements Comparable<MappedAttribute>
/*  17:    */ {
/*  18:    */   private final Map<DotName, List<AnnotationInstance>> annotations;
/*  19:    */   private final String name;
/*  20:    */   private final Class<?> attributeType;
/*  21:    */   private final String accessType;
/*  22:    */   private ColumnValues columnValues;
/*  23:    */   private final boolean isId;
/*  24:    */   private final boolean isOptimisticLockable;
/*  25:    */   private final EntityBindingContext context;
/*  26:    */   
/*  27:    */   MappedAttribute(String name, Class<?> attributeType, String accessType, Map<DotName, List<AnnotationInstance>> annotations, EntityBindingContext context)
/*  28:    */   {
/*  29: 88 */     this.context = context;
/*  30: 89 */     this.annotations = annotations;
/*  31: 90 */     this.name = name;
/*  32: 91 */     this.attributeType = attributeType;
/*  33: 92 */     this.accessType = accessType;
/*  34:    */     
/*  35:    */ 
/*  36: 95 */     AnnotationInstance idAnnotation = JandexHelper.getSingleAnnotation(annotations, JPADotNames.ID);
/*  37: 96 */     AnnotationInstance embeddedIdAnnotation = JandexHelper.getSingleAnnotation(annotations, JPADotNames.EMBEDDED_ID);
/*  38:    */     
/*  39:    */ 
/*  40:    */ 
/*  41:100 */     this.isId = ((idAnnotation != null) || (embeddedIdAnnotation != null));
/*  42:    */     
/*  43:102 */     AnnotationInstance columnAnnotation = JandexHelper.getSingleAnnotation(annotations, JPADotNames.COLUMN);
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:106 */     this.columnValues = new ColumnValues(columnAnnotation);
/*  48:    */     
/*  49:108 */     this.isOptimisticLockable = checkOptimisticLockAnnotation();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getName()
/*  53:    */   {
/*  54:112 */     return this.name;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final Class<?> getAttributeType()
/*  58:    */   {
/*  59:116 */     return this.attributeType;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getAccessType()
/*  63:    */   {
/*  64:120 */     return this.accessType;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public EntityBindingContext getContext()
/*  68:    */   {
/*  69:124 */     return this.context;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Map<DotName, List<AnnotationInstance>> annotations()
/*  73:    */   {
/*  74:128 */     return this.annotations;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ColumnValues getColumnValues()
/*  78:    */   {
/*  79:132 */     return this.columnValues;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isId()
/*  83:    */   {
/*  84:136 */     return this.isId;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isOptimisticLockable()
/*  88:    */   {
/*  89:140 */     return this.isOptimisticLockable;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int compareTo(MappedAttribute mappedProperty)
/*  93:    */   {
/*  94:145 */     return this.name.compareTo(mappedProperty.getName());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String toString()
/*  98:    */   {
/*  99:150 */     StringBuilder sb = new StringBuilder();
/* 100:151 */     sb.append("MappedAttribute");
/* 101:152 */     sb.append("{name='").append(this.name).append('\'');
/* 102:153 */     sb.append('}');
/* 103:154 */     return sb.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public abstract AttributeTypeResolver getHibernateTypeResolver();
/* 107:    */   
/* 108:    */   public abstract boolean isLazy();
/* 109:    */   
/* 110:    */   public abstract boolean isOptional();
/* 111:    */   
/* 112:    */   public abstract boolean isInsertable();
/* 113:    */   
/* 114:    */   public abstract boolean isUpdatable();
/* 115:    */   
/* 116:    */   public abstract PropertyGeneration getPropertyGeneration();
/* 117:    */   
/* 118:    */   private boolean checkOptimisticLockAnnotation()
/* 119:    */   {
/* 120:170 */     boolean triggersVersionIncrement = true;
/* 121:171 */     AnnotationInstance optimisticLockAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.OPTIMISTIC_LOCK);
/* 122:175 */     if (optimisticLockAnnotation != null)
/* 123:    */     {
/* 124:176 */       boolean exclude = optimisticLockAnnotation.value("excluded").asBoolean();
/* 125:177 */       triggersVersionIncrement = !exclude;
/* 126:    */     }
/* 127:179 */     return triggersVersionIncrement;
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.MappedAttribute
 * JD-Core Version:    0.7.0.1
 */