/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   9:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  10:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  11:    */ import org.hibernate.metamodel.source.annotations.xml.PseudoJpaDotNames;
/*  12:    */ import org.jboss.jandex.AnnotationInstance;
/*  13:    */ import org.jboss.jandex.AnnotationTarget;
/*  14:    */ import org.jboss.jandex.ClassInfo;
/*  15:    */ import org.jboss.jandex.DotName;
/*  16:    */ import org.jboss.jandex.MethodInfo;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ class AccessHelper
/*  20:    */   implements JPADotNames
/*  21:    */ {
/*  22: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AccessHelper.class.getName());
/*  23:    */   
/*  24:    */   static JaxbAccessType getAccessFromDefault(IndexBuilder indexBuilder)
/*  25:    */   {
/*  26: 54 */     AnnotationInstance annotationInstance = JandexHelper.getSingleAnnotation(indexBuilder.getAnnotations(), PseudoJpaDotNames.DEFAULT_ACCESS);
/*  27: 58 */     if (annotationInstance == null) {
/*  28: 59 */       return null;
/*  29:    */     }
/*  30: 62 */     return (JaxbAccessType)JandexHelper.getEnumValue(annotationInstance, "value", JaxbAccessType.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   static JaxbAccessType getAccessFromIdPosition(DotName className, IndexBuilder indexBuilder)
/*  34:    */   {
/*  35: 68 */     Map<DotName, List<AnnotationInstance>> indexedAnnotations = indexBuilder.getIndexedAnnotations(className);
/*  36: 69 */     Map<DotName, List<AnnotationInstance>> ormAnnotations = indexBuilder.getClassInfoAnnotationsMap(className);
/*  37: 70 */     JaxbAccessType accessType = getAccessFromIdPosition(ormAnnotations);
/*  38: 71 */     if (accessType == null) {
/*  39: 72 */       accessType = getAccessFromIdPosition(indexedAnnotations);
/*  40:    */     }
/*  41: 74 */     if (accessType == null)
/*  42:    */     {
/*  43: 75 */       ClassInfo parent = indexBuilder.getClassInfo(className);
/*  44: 76 */       if (parent == null) {
/*  45: 77 */         parent = indexBuilder.getIndexedClassInfo(className);
/*  46:    */       }
/*  47: 79 */       if (parent != null)
/*  48:    */       {
/*  49: 80 */         DotName parentClassName = parent.superName();
/*  50: 81 */         accessType = getAccessFromIdPosition(parentClassName, indexBuilder);
/*  51:    */       }
/*  52:    */     }
/*  53: 86 */     return accessType;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static JaxbAccessType getAccessFromIdPosition(Map<DotName, List<AnnotationInstance>> annotations)
/*  57:    */   {
/*  58: 90 */     if ((annotations == null) || (annotations.isEmpty()) || (!annotations.containsKey(ID))) {
/*  59: 91 */       return null;
/*  60:    */     }
/*  61: 93 */     List<AnnotationInstance> idAnnotationInstances = (List)annotations.get(ID);
/*  62: 94 */     if (MockHelper.isNotEmpty(idAnnotationInstances)) {
/*  63: 95 */       return processIdAnnotations(idAnnotationInstances);
/*  64:    */     }
/*  65: 97 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static JaxbAccessType processIdAnnotations(List<AnnotationInstance> idAnnotations)
/*  69:    */   {
/*  70:101 */     JaxbAccessType accessType = null;
/*  71:102 */     for (AnnotationInstance annotation : idAnnotations)
/*  72:    */     {
/*  73:103 */       AnnotationTarget tmpTarget = annotation.target();
/*  74:104 */       if (tmpTarget == null) {
/*  75:105 */         throw new AssertionFailure("@Id has no AnnotationTarget, this is mostly a internal error.");
/*  76:    */       }
/*  77:107 */       if (accessType == null) {
/*  78:108 */         accessType = annotationTargetToAccessType(tmpTarget);
/*  79:111 */       } else if (!accessType.equals(annotationTargetToAccessType(tmpTarget))) {
/*  80:112 */         throw new MappingException("Inconsistent placement of @Id annotation within hierarchy ");
/*  81:    */       }
/*  82:    */     }
/*  83:116 */     return accessType;
/*  84:    */   }
/*  85:    */   
/*  86:    */   static JaxbAccessType annotationTargetToAccessType(AnnotationTarget target)
/*  87:    */   {
/*  88:120 */     return (target instanceof MethodInfo) ? JaxbAccessType.PROPERTY : JaxbAccessType.FIELD;
/*  89:    */   }
/*  90:    */   
/*  91:    */   static JaxbAccessType getEntityAccess(DotName className, IndexBuilder indexBuilder)
/*  92:    */   {
/*  93:124 */     Map<DotName, List<AnnotationInstance>> indexedAnnotations = indexBuilder.getIndexedAnnotations(className);
/*  94:125 */     Map<DotName, List<AnnotationInstance>> ormAnnotations = indexBuilder.getClassInfoAnnotationsMap(className);
/*  95:126 */     JaxbAccessType accessType = getAccess(ormAnnotations);
/*  96:127 */     if (accessType == null) {
/*  97:128 */       accessType = getAccess(indexedAnnotations);
/*  98:    */     }
/*  99:130 */     if (accessType == null)
/* 100:    */     {
/* 101:131 */       ClassInfo parent = indexBuilder.getClassInfo(className);
/* 102:132 */       if (parent == null) {
/* 103:133 */         parent = indexBuilder.getIndexedClassInfo(className);
/* 104:    */       }
/* 105:135 */       if (parent != null)
/* 106:    */       {
/* 107:136 */         DotName parentClassName = parent.superName();
/* 108:137 */         accessType = getEntityAccess(parentClassName, indexBuilder);
/* 109:    */       }
/* 110:    */     }
/* 111:140 */     return accessType;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static JaxbAccessType getAccess(Map<DotName, List<AnnotationInstance>> annotations)
/* 115:    */   {
/* 116:145 */     if ((annotations == null) || (annotations.isEmpty()) || (!isEntityObject(annotations))) {
/* 117:146 */       return null;
/* 118:    */     }
/* 119:148 */     List<AnnotationInstance> accessAnnotationInstances = (List)annotations.get(JPADotNames.ACCESS);
/* 120:149 */     if (MockHelper.isNotEmpty(accessAnnotationInstances)) {
/* 121:150 */       for (AnnotationInstance annotationInstance : accessAnnotationInstances) {
/* 122:151 */         if ((annotationInstance.target() != null) && ((annotationInstance.target() instanceof ClassInfo))) {
/* 123:152 */           return (JaxbAccessType)JandexHelper.getEnumValue(annotationInstance, "value", JaxbAccessType.class);
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:160 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static boolean isEntityObject(Map<DotName, List<AnnotationInstance>> annotations)
/* 131:    */   {
/* 132:164 */     return (annotations.containsKey(ENTITY)) || (annotations.containsKey(MAPPED_SUPERCLASS)) || (annotations.containsKey(EMBEDDABLE));
/* 133:    */   }
/* 134:    */   
/* 135:    */   static JaxbAccessType getAccessFromAttributeAnnotation(DotName className, String attributeName, IndexBuilder indexBuilder)
/* 136:    */   {
/* 137:172 */     Map<DotName, List<AnnotationInstance>> indexedAnnotations = indexBuilder.getIndexedAnnotations(className);
/* 138:173 */     if ((indexedAnnotations != null) && (indexedAnnotations.containsKey(ACCESS)))
/* 139:    */     {
/* 140:174 */       List<AnnotationInstance> annotationInstances = (List)indexedAnnotations.get(ACCESS);
/* 141:175 */       if (MockHelper.isNotEmpty(annotationInstances)) {
/* 142:176 */         for (AnnotationInstance annotationInstance : annotationInstances)
/* 143:    */         {
/* 144:177 */           AnnotationTarget indexedPropertyTarget = annotationInstance.target();
/* 145:178 */           if (indexedPropertyTarget != null) {
/* 146:181 */             if (JandexHelper.getPropertyName(indexedPropertyTarget).equals(attributeName))
/* 147:    */             {
/* 148:182 */               JaxbAccessType accessType = (JaxbAccessType)JandexHelper.getEnumValue(annotationInstance, "value", JaxbAccessType.class);
/* 149:    */               
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:190 */               JaxbAccessType targetAccessType = annotationTargetToAccessType(indexedPropertyTarget);
/* 157:191 */               if (accessType.equals(targetAccessType)) {
/* 158:192 */                 return targetAccessType;
/* 159:    */               }
/* 160:195 */               LOG.warn(String.format("%s.%s has @Access on %s, but it tries to assign the access type to %s, this is not allowed by JPA spec, and will be ignored.", new Object[] { className, attributeName, targetAccessType, accessType }));
/* 161:    */             }
/* 162:    */           }
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:209 */     return null;
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AccessHelper
 * JD-Core Version:    0.7.0.1
 */