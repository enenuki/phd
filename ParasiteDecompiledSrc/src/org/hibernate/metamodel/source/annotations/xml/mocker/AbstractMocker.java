/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbUniqueConstraint;
/*   7:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*   8:    */ import org.jboss.jandex.AnnotationInstance;
/*   9:    */ import org.jboss.jandex.AnnotationTarget;
/*  10:    */ import org.jboss.jandex.AnnotationValue;
/*  11:    */ import org.jboss.jandex.DotName;
/*  12:    */ 
/*  13:    */ abstract class AbstractMocker
/*  14:    */   implements JPADotNames
/*  15:    */ {
/*  16:    */   protected final IndexBuilder indexBuilder;
/*  17:    */   
/*  18:    */   AbstractMocker(IndexBuilder indexBuilder)
/*  19:    */   {
/*  20: 47 */     this.indexBuilder = indexBuilder;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected abstract AnnotationInstance push(AnnotationInstance paramAnnotationInstance);
/*  24:    */   
/*  25:    */   protected AnnotationInstance create(DotName name, AnnotationTarget target)
/*  26:    */   {
/*  27: 55 */     return create(name, target, MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected AnnotationInstance create(DotName name, AnnotationTarget target, List<AnnotationValue> annotationValueList)
/*  31:    */   {
/*  32: 60 */     return create(name, target, MockHelper.toArray(annotationValueList));
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected AnnotationInstance create(DotName name, AnnotationTarget target, AnnotationValue[] annotationValues)
/*  36:    */   {
/*  37: 64 */     AnnotationInstance annotationInstance = MockHelper.create(name, target, annotationValues);
/*  38: 65 */     push(annotationInstance);
/*  39: 66 */     return annotationInstance;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected AnnotationInstance parserAccessType(JaxbAccessType accessType, AnnotationTarget target)
/*  43:    */   {
/*  44: 72 */     if (accessType == null) {
/*  45: 73 */       return null;
/*  46:    */     }
/*  47: 75 */     return create(ACCESS, target, MockHelper.enumValueArray("value", ACCESS_TYPE, accessType));
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void nestedUniqueConstraintList(String name, List<JaxbUniqueConstraint> constraints, List<AnnotationValue> annotationValueList)
/*  51:    */   {
/*  52: 79 */     if (MockHelper.isNotEmpty(constraints))
/*  53:    */     {
/*  54: 80 */       AnnotationValue[] values = new AnnotationValue[constraints.size()];
/*  55: 81 */       for (int i = 0; i < constraints.size(); i++)
/*  56:    */       {
/*  57: 82 */         AnnotationInstance annotationInstance = parserUniqueConstraint((JaxbUniqueConstraint)constraints.get(i), null);
/*  58: 83 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/*  59:    */       }
/*  60: 87 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected AnnotationInstance parserUniqueConstraint(JaxbUniqueConstraint uniqueConstraint, AnnotationTarget target)
/*  65:    */   {
/*  66: 96 */     if (uniqueConstraint == null) {
/*  67: 97 */       return null;
/*  68:    */     }
/*  69: 99 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  70:100 */     MockHelper.stringValue("name", uniqueConstraint.getName(), annotationValueList);
/*  71:101 */     MockHelper.stringArrayValue("columnNames", uniqueConstraint.getColumnName(), annotationValueList);
/*  72:102 */     return create(UNIQUE_CONSTRAINT, target, annotationValueList);
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AbstractMocker
 * JD-Core Version:    0.7.0.1
 */