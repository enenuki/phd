/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.ElementCollection;
/*   5:    */ import javax.persistence.JoinColumn;
/*   6:    */ import javax.persistence.JoinColumns;
/*   7:    */ import javax.persistence.JoinTable;
/*   8:    */ import javax.persistence.ManyToMany;
/*   9:    */ import javax.persistence.ManyToOne;
/*  10:    */ import javax.persistence.OneToMany;
/*  11:    */ import javax.persistence.OneToOne;
/*  12:    */ import org.hibernate.AnnotationException;
/*  13:    */ import org.hibernate.annotations.Any;
/*  14:    */ import org.hibernate.annotations.Columns;
/*  15:    */ import org.hibernate.annotations.Formula;
/*  16:    */ import org.hibernate.annotations.JoinColumnsOrFormulas;
/*  17:    */ import org.hibernate.annotations.JoinFormula;
/*  18:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  19:    */ import org.hibernate.cfg.annotations.EntityBinder;
/*  20:    */ import org.hibernate.cfg.annotations.Nullability;
/*  21:    */ import org.hibernate.internal.util.StringHelper;
/*  22:    */ 
/*  23:    */ class ColumnsBuilder
/*  24:    */ {
/*  25:    */   private PropertyHolder propertyHolder;
/*  26:    */   private Nullability nullability;
/*  27:    */   private XProperty property;
/*  28:    */   private PropertyData inferredData;
/*  29:    */   private EntityBinder entityBinder;
/*  30:    */   private Mappings mappings;
/*  31:    */   private Ejb3Column[] columns;
/*  32:    */   private Ejb3JoinColumn[] joinColumns;
/*  33:    */   
/*  34:    */   public ColumnsBuilder(PropertyHolder propertyHolder, Nullability nullability, XProperty property, PropertyData inferredData, EntityBinder entityBinder, Mappings mappings)
/*  35:    */   {
/*  36: 68 */     this.propertyHolder = propertyHolder;
/*  37: 69 */     this.nullability = nullability;
/*  38: 70 */     this.property = property;
/*  39: 71 */     this.inferredData = inferredData;
/*  40: 72 */     this.entityBinder = entityBinder;
/*  41: 73 */     this.mappings = mappings;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Ejb3Column[] getColumns()
/*  45:    */   {
/*  46: 77 */     return this.columns;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Ejb3JoinColumn[] getJoinColumns()
/*  50:    */   {
/*  51: 81 */     return this.joinColumns;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ColumnsBuilder extractMetadata()
/*  55:    */   {
/*  56: 85 */     this.columns = null;
/*  57: 86 */     this.joinColumns = buildExplicitJoinColumns(this.property, this.inferredData);
/*  58: 89 */     if ((this.property.isAnnotationPresent(Column.class)) || (this.property.isAnnotationPresent(Formula.class)))
/*  59:    */     {
/*  60: 90 */       Column ann = (Column)this.property.getAnnotation(Column.class);
/*  61: 91 */       Formula formulaAnn = (Formula)this.property.getAnnotation(Formula.class);
/*  62: 92 */       this.columns = Ejb3Column.buildColumnFromAnnotation(new Column[] { ann }, formulaAnn, this.nullability, this.propertyHolder, this.inferredData, this.entityBinder.getSecondaryTables(), this.mappings);
/*  63:    */     }
/*  64: 97 */     else if (this.property.isAnnotationPresent(Columns.class))
/*  65:    */     {
/*  66: 98 */       Columns anns = (Columns)this.property.getAnnotation(Columns.class);
/*  67: 99 */       this.columns = Ejb3Column.buildColumnFromAnnotation(anns.columns(), null, this.nullability, this.propertyHolder, this.inferredData, this.entityBinder.getSecondaryTables(), this.mappings);
/*  68:    */     }
/*  69:107 */     if ((this.joinColumns == null) && ((this.property.isAnnotationPresent(ManyToOne.class)) || (this.property.isAnnotationPresent(OneToOne.class))))
/*  70:    */     {
/*  71:111 */       this.joinColumns = buildDefaultJoinColumnsForXToOne(this.property, this.inferredData);
/*  72:    */     }
/*  73:113 */     else if ((this.joinColumns == null) && ((this.property.isAnnotationPresent(OneToMany.class)) || (this.property.isAnnotationPresent(ElementCollection.class))))
/*  74:    */     {
/*  75:117 */       OneToMany oneToMany = (OneToMany)this.property.getAnnotation(OneToMany.class);
/*  76:118 */       String mappedBy = oneToMany != null ? oneToMany.mappedBy() : "";
/*  77:    */       
/*  78:    */ 
/*  79:121 */       this.joinColumns = Ejb3JoinColumn.buildJoinColumns(null, mappedBy, this.entityBinder.getSecondaryTables(), this.propertyHolder, this.inferredData.getPropertyName(), this.mappings);
/*  80:    */     }
/*  81:127 */     else if ((this.joinColumns == null) && (this.property.isAnnotationPresent(Any.class)))
/*  82:    */     {
/*  83:128 */       throw new AnnotationException("@Any requires an explicit @JoinColumn(s): " + BinderHelper.getPath(this.propertyHolder, this.inferredData));
/*  84:    */     }
/*  85:131 */     if ((this.columns == null) && (!this.property.isAnnotationPresent(ManyToMany.class))) {
/*  86:133 */       this.columns = Ejb3Column.buildColumnFromAnnotation(null, null, this.nullability, this.propertyHolder, this.inferredData, this.entityBinder.getSecondaryTables(), this.mappings);
/*  87:    */     }
/*  88:139 */     if (this.nullability == Nullability.FORCED_NOT_NULL) {
/*  89:141 */       for (Ejb3Column col : this.columns) {
/*  90:142 */         col.forceNotNull();
/*  91:    */       }
/*  92:    */     }
/*  93:145 */     return this;
/*  94:    */   }
/*  95:    */   
/*  96:    */   Ejb3JoinColumn[] buildDefaultJoinColumnsForXToOne(XProperty property, PropertyData inferredData)
/*  97:    */   {
/*  98:150 */     JoinTable joinTableAnn = this.propertyHolder.getJoinTable(property);
/*  99:    */     Ejb3JoinColumn[] joinColumns;
/* 100:151 */     if (joinTableAnn != null)
/* 101:    */     {
/* 102:152 */       Ejb3JoinColumn[] joinColumns = Ejb3JoinColumn.buildJoinColumns(joinTableAnn.inverseJoinColumns(), null, this.entityBinder.getSecondaryTables(), this.propertyHolder, inferredData.getPropertyName(), this.mappings);
/* 103:156 */       if (StringHelper.isEmpty(joinTableAnn.name())) {
/* 104:157 */         throw new AnnotationException("JoinTable.name() on a @ToOne association has to be explicit: " + BinderHelper.getPath(this.propertyHolder, inferredData));
/* 105:    */       }
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:164 */       OneToOne oneToOneAnn = (OneToOne)property.getAnnotation(OneToOne.class);
/* 110:165 */       String mappedBy = oneToOneAnn != null ? oneToOneAnn.mappedBy() : null;
/* 111:    */       
/* 112:    */ 
/* 113:168 */       joinColumns = Ejb3JoinColumn.buildJoinColumns(null, mappedBy, this.entityBinder.getSecondaryTables(), this.propertyHolder, inferredData.getPropertyName(), this.mappings);
/* 114:    */     }
/* 115:174 */     return joinColumns;
/* 116:    */   }
/* 117:    */   
/* 118:    */   Ejb3JoinColumn[] buildExplicitJoinColumns(XProperty property, PropertyData inferredData)
/* 119:    */   {
/* 120:179 */     Ejb3JoinColumn[] joinColumns = null;
/* 121:    */     
/* 122:181 */     JoinColumn[] anns = null;
/* 123:183 */     if (property.isAnnotationPresent(JoinColumn.class))
/* 124:    */     {
/* 125:184 */       anns = new JoinColumn[] { (JoinColumn)property.getAnnotation(JoinColumn.class) };
/* 126:    */     }
/* 127:186 */     else if (property.isAnnotationPresent(JoinColumns.class))
/* 128:    */     {
/* 129:187 */       JoinColumns ann = (JoinColumns)property.getAnnotation(JoinColumns.class);
/* 130:188 */       anns = ann.value();
/* 131:189 */       int length = anns.length;
/* 132:190 */       if (length == 0) {
/* 133:191 */         throw new AnnotationException("Cannot bind an empty @JoinColumns");
/* 134:    */       }
/* 135:    */     }
/* 136:194 */     if (anns != null)
/* 137:    */     {
/* 138:195 */       joinColumns = Ejb3JoinColumn.buildJoinColumns(anns, null, this.entityBinder.getSecondaryTables(), this.propertyHolder, inferredData.getPropertyName(), this.mappings);
/* 139:    */     }
/* 140:200 */     else if (property.isAnnotationPresent(JoinColumnsOrFormulas.class))
/* 141:    */     {
/* 142:201 */       JoinColumnsOrFormulas ann = (JoinColumnsOrFormulas)property.getAnnotation(JoinColumnsOrFormulas.class);
/* 143:202 */       joinColumns = Ejb3JoinColumn.buildJoinColumnsOrFormulas(ann, null, this.entityBinder.getSecondaryTables(), this.propertyHolder, inferredData.getPropertyName(), this.mappings);
/* 144:    */     }
/* 145:207 */     else if (property.isAnnotationPresent(JoinFormula.class))
/* 146:    */     {
/* 147:208 */       JoinFormula ann = (JoinFormula)property.getAnnotation(JoinFormula.class);
/* 148:209 */       joinColumns = new Ejb3JoinColumn[1];
/* 149:210 */       joinColumns[0] = Ejb3JoinColumn.buildJoinFormula(ann, null, this.entityBinder.getSecondaryTables(), this.propertyHolder, inferredData.getPropertyName(), this.mappings);
/* 150:    */     }
/* 151:215 */     return joinColumns;
/* 152:    */   }
/* 153:    */   
/* 154:    */   Ejb3Column[] overrideColumnFromMapperOrMapsIdProperty(boolean isId)
/* 155:    */   {
/* 156:219 */     Ejb3Column[] result = this.columns;
/* 157:220 */     PropertyData overridingProperty = BinderHelper.getPropertyOverriddenByMapperOrMapsId(isId, this.propertyHolder, this.property.getName(), this.mappings);
/* 158:223 */     if (overridingProperty != null) {
/* 159:224 */       result = buildExcplicitOrDefaultJoinColumn(overridingProperty);
/* 160:    */     }
/* 161:226 */     return result;
/* 162:    */   }
/* 163:    */   
/* 164:    */   Ejb3Column[] buildExcplicitOrDefaultJoinColumn(PropertyData overridingProperty)
/* 165:    */   {
/* 166:234 */     Ejb3Column[] result = buildExplicitJoinColumns(overridingProperty.getProperty(), overridingProperty);
/* 167:235 */     if (result == null) {
/* 168:236 */       result = buildDefaultJoinColumnsForXToOne(overridingProperty.getProperty(), overridingProperty);
/* 169:    */     }
/* 170:238 */     return result;
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ColumnsBuilder
 * JD-Core Version:    0.7.0.1
 */