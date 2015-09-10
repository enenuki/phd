/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEnumType;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMapKey;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyClass;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyColumn;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyJoinColumn;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTemporalType;
/*  13:    */ import org.jboss.jandex.AnnotationInstance;
/*  14:    */ import org.jboss.jandex.AnnotationTarget;
/*  15:    */ import org.jboss.jandex.AnnotationValue;
/*  16:    */ import org.jboss.jandex.ClassInfo;
/*  17:    */ import org.jboss.jandex.DotName;
/*  18:    */ 
/*  19:    */ abstract class PropertyMocker
/*  20:    */   extends AnnotationMocker
/*  21:    */ {
/*  22:    */   protected ClassInfo classInfo;
/*  23:    */   private AnnotationTarget target;
/*  24:    */   
/*  25:    */   PropertyMocker(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults)
/*  26:    */   {
/*  27: 52 */     super(indexBuilder, defaults);
/*  28: 53 */     this.classInfo = classInfo;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected abstract void processExtra();
/*  32:    */   
/*  33:    */   protected abstract String getFieldName();
/*  34:    */   
/*  35:    */   protected abstract JaxbAccessType getAccessType();
/*  36:    */   
/*  37:    */   protected abstract void setAccessType(JaxbAccessType paramJaxbAccessType);
/*  38:    */   
/*  39:    */   protected DotName getTargetName()
/*  40:    */   {
/*  41: 66 */     return this.classInfo.name();
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void resolveTarget()
/*  45:    */   {
/*  46: 71 */     JaxbAccessType accessType = getAccessType();
/*  47: 72 */     if (accessType == null)
/*  48:    */     {
/*  49: 74 */       accessType = AccessHelper.getAccessFromAttributeAnnotation(getTargetName(), getFieldName(), this.indexBuilder);
/*  50: 75 */       if (accessType == null) {
/*  51: 76 */         accessType = AccessHelper.getEntityAccess(getTargetName(), this.indexBuilder);
/*  52:    */       }
/*  53: 78 */       if (accessType == null) {
/*  54: 79 */         accessType = AccessHelper.getAccessFromIdPosition(getTargetName(), this.indexBuilder);
/*  55:    */       }
/*  56: 81 */       if (accessType == null) {
/*  57: 83 */         accessType = AccessHelper.getAccessFromDefault(this.indexBuilder);
/*  58:    */       }
/*  59: 85 */       if (accessType == null) {
/*  60: 86 */         accessType = JaxbAccessType.PROPERTY;
/*  61:    */       }
/*  62: 89 */       setAccessType(accessType);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected AnnotationTarget getTarget()
/*  67:    */   {
/*  68: 96 */     if (this.target == null) {
/*  69: 97 */       this.target = getTargetFromAttributeAccessType(getAccessType());
/*  70:    */     }
/*  71: 99 */     return this.target;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected AnnotationTarget getTargetFromAttributeAccessType(JaxbAccessType accessType)
/*  75:    */   {
/*  76:103 */     if (accessType == null) {
/*  77:104 */       throw new IllegalArgumentException("access type can't be null.");
/*  78:    */     }
/*  79:106 */     switch (1.$SwitchMap$org$hibernate$internal$jaxb$mapping$orm$JaxbAccessType[accessType.ordinal()])
/*  80:    */     {
/*  81:    */     case 1: 
/*  82:108 */       return MockHelper.getTarget(this.indexBuilder.getServiceRegistry(), this.classInfo, getFieldName(), MockHelper.TargetType.FIELD);
/*  83:    */     case 2: 
/*  84:115 */       return MockHelper.getTarget(this.indexBuilder.getServiceRegistry(), this.classInfo, getFieldName(), MockHelper.TargetType.PROPERTY);
/*  85:    */     }
/*  86:122 */     throw new HibernateException("can't determin access type [" + accessType + "]");
/*  87:    */   }
/*  88:    */   
/*  89:    */   final void process()
/*  90:    */   {
/*  91:129 */     resolveTarget();
/*  92:130 */     processExtra();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected AnnotationInstance parserMapKeyColumn(JaxbMapKeyColumn mapKeyColumn, AnnotationTarget target)
/*  96:    */   {
/*  97:134 */     if (mapKeyColumn == null) {
/*  98:135 */       return null;
/*  99:    */     }
/* 100:137 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 101:138 */     MockHelper.stringValue("name", mapKeyColumn.getName(), annotationValueList);
/* 102:139 */     MockHelper.stringValue("columnDefinition", mapKeyColumn.getColumnDefinition(), annotationValueList);
/* 103:140 */     MockHelper.stringValue("table", mapKeyColumn.getTable(), annotationValueList);
/* 104:141 */     MockHelper.booleanValue("nullable", mapKeyColumn.isNullable(), annotationValueList);
/* 105:142 */     MockHelper.booleanValue("insertable", mapKeyColumn.isInsertable(), annotationValueList);
/* 106:143 */     MockHelper.booleanValue("updatable", mapKeyColumn.isUpdatable(), annotationValueList);
/* 107:144 */     MockHelper.booleanValue("unique", mapKeyColumn.isUnique(), annotationValueList);
/* 108:145 */     MockHelper.integerValue("length", mapKeyColumn.getLength(), annotationValueList);
/* 109:146 */     MockHelper.integerValue("precision", mapKeyColumn.getPrecision(), annotationValueList);
/* 110:147 */     MockHelper.integerValue("scale", mapKeyColumn.getScale(), annotationValueList);
/* 111:148 */     return create(MAP_KEY_COLUMN, target, annotationValueList);
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected AnnotationInstance parserMapKeyClass(JaxbMapKeyClass mapKeyClass, AnnotationTarget target)
/* 115:    */   {
/* 116:152 */     if (mapKeyClass == null) {
/* 117:153 */       return null;
/* 118:    */     }
/* 119:155 */     return create(MAP_KEY_CLASS, target, MockHelper.classValueArray("value", mapKeyClass.getClazz(), this.indexBuilder.getServiceRegistry()));
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected AnnotationInstance parserMapKeyTemporal(JaxbTemporalType temporalType, AnnotationTarget target)
/* 123:    */   {
/* 124:163 */     if (temporalType == null) {
/* 125:164 */       return null;
/* 126:    */     }
/* 127:166 */     return create(MAP_KEY_TEMPORAL, target, MockHelper.enumValueArray("value", TEMPORAL_TYPE, temporalType));
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected AnnotationInstance parserMapKeyEnumerated(JaxbEnumType enumType, AnnotationTarget target)
/* 131:    */   {
/* 132:173 */     if (enumType == null) {
/* 133:174 */       return null;
/* 134:    */     }
/* 135:176 */     return create(MAP_KEY_ENUMERATED, target, MockHelper.enumValueArray("value", ENUM_TYPE, enumType));
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected AnnotationInstance parserMapKey(JaxbMapKey mapKey, AnnotationTarget target)
/* 139:    */   {
/* 140:183 */     if (mapKey == null) {
/* 141:184 */       return null;
/* 142:    */     }
/* 143:186 */     return create(MAP_KEY, target, MockHelper.stringValueArray("name", mapKey.getName()));
/* 144:    */   }
/* 145:    */   
/* 146:    */   private AnnotationValue[] nestedMapKeyJoinColumnList(String name, List<JaxbMapKeyJoinColumn> columns, List<AnnotationValue> annotationValueList)
/* 147:    */   {
/* 148:190 */     if (MockHelper.isNotEmpty(columns))
/* 149:    */     {
/* 150:191 */       AnnotationValue[] values = new AnnotationValue[columns.size()];
/* 151:192 */       for (int i = 0; i < columns.size(); i++)
/* 152:    */       {
/* 153:193 */         AnnotationInstance annotationInstance = parserMapKeyJoinColumn((JaxbMapKeyJoinColumn)columns.get(i), null);
/* 154:194 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 155:    */       }
/* 156:198 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 157:    */       
/* 158:    */ 
/* 159:201 */       return values;
/* 160:    */     }
/* 161:203 */     return MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY;
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected AnnotationInstance parserMapKeyJoinColumnList(List<JaxbMapKeyJoinColumn> joinColumnList, AnnotationTarget target)
/* 165:    */   {
/* 166:207 */     if (MockHelper.isNotEmpty(joinColumnList))
/* 167:    */     {
/* 168:208 */       if (joinColumnList.size() == 1) {
/* 169:209 */         return parserMapKeyJoinColumn((JaxbMapKeyJoinColumn)joinColumnList.get(0), target);
/* 170:    */       }
/* 171:212 */       AnnotationValue[] values = nestedMapKeyJoinColumnList("value", joinColumnList, null);
/* 172:213 */       return create(MAP_KEY_JOIN_COLUMNS, target, values);
/* 173:    */     }
/* 174:220 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private AnnotationInstance parserMapKeyJoinColumn(JaxbMapKeyJoinColumn column, AnnotationTarget target)
/* 178:    */   {
/* 179:226 */     if (column == null) {
/* 180:227 */       return null;
/* 181:    */     }
/* 182:229 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 183:230 */     MockHelper.stringValue("name", column.getName(), annotationValueList);
/* 184:231 */     MockHelper.stringValue("columnDefinition", column.getColumnDefinition(), annotationValueList);
/* 185:232 */     MockHelper.stringValue("table", column.getTable(), annotationValueList);
/* 186:233 */     MockHelper.stringValue("referencedColumnName", column.getReferencedColumnName(), annotationValueList);
/* 187:    */     
/* 188:    */ 
/* 189:236 */     MockHelper.booleanValue("unique", column.isUnique(), annotationValueList);
/* 190:237 */     MockHelper.booleanValue("nullable", column.isNullable(), annotationValueList);
/* 191:238 */     MockHelper.booleanValue("insertable", column.isInsertable(), annotationValueList);
/* 192:239 */     MockHelper.booleanValue("updatable", column.isUpdatable(), annotationValueList);
/* 193:240 */     return create(MAP_KEY_JOIN_COLUMN, target, annotationValueList);
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.PropertyMocker
 * JD-Core Version:    0.7.0.1
 */