/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.AccessType;
/*   7:    */ import org.hibernate.AssertionFailure;
/*   8:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*   9:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  10:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  11:    */ import org.jboss.jandex.ClassInfo;
/*  12:    */ import org.jboss.jandex.DotName;
/*  13:    */ import org.jboss.jandex.Index;
/*  14:    */ 
/*  15:    */ public class EmbeddableHierarchy
/*  16:    */   implements Iterable<EmbeddableClass>
/*  17:    */ {
/*  18:    */   private final AccessType defaultAccessType;
/*  19:    */   private final List<EmbeddableClass> embeddables;
/*  20:    */   
/*  21:    */   public static EmbeddableHierarchy createEmbeddableHierarchy(Class<?> embeddableClass, String propertyName, AccessType accessType, AnnotationBindingContext context)
/*  22:    */   {
/*  23: 60 */     ClassInfo embeddableClassInfo = context.getClassInfo(embeddableClass.getName());
/*  24: 61 */     if (embeddableClassInfo == null) {
/*  25: 62 */       throw new AssertionFailure(String.format("The specified class %s cannot be found in the annotation index", new Object[] { embeddableClass.getName() }));
/*  26:    */     }
/*  27: 70 */     if (JandexHelper.getSingleAnnotation(embeddableClassInfo, JPADotNames.EMBEDDABLE) == null) {
/*  28: 71 */       throw new AssertionFailure(String.format("The specified class %s is not annotated with @Embeddable even though it is as embeddable", new Object[] { embeddableClass.getName() }));
/*  29:    */     }
/*  30: 79 */     List<ClassInfo> classInfoList = new ArrayList();
/*  31:    */     
/*  32: 81 */     Class<?> clazz = embeddableClass;
/*  33: 82 */     while ((clazz != null) && (!clazz.equals(Object.class)))
/*  34:    */     {
/*  35: 83 */       ClassInfo tmpClassInfo = context.getIndex().getClassByName(DotName.createSimple(clazz.getName()));
/*  36: 84 */       clazz = clazz.getSuperclass();
/*  37: 85 */       if (tmpClassInfo != null) {
/*  38: 89 */         classInfoList.add(0, tmpClassInfo);
/*  39:    */       }
/*  40:    */     }
/*  41: 92 */     return new EmbeddableHierarchy(classInfoList, propertyName, context, accessType);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private EmbeddableHierarchy(List<ClassInfo> classInfoList, String propertyName, AnnotationBindingContext context, AccessType defaultAccessType)
/*  45:    */   {
/*  46:106 */     this.defaultAccessType = defaultAccessType;
/*  47:    */     
/*  48:    */ 
/*  49:109 */     context.resolveAllTypes(((ClassInfo)classInfoList.get(classInfoList.size() - 1)).name().toString());
/*  50:    */     
/*  51:111 */     this.embeddables = new ArrayList();
/*  52:112 */     ConfiguredClass parent = null;
/*  53:114 */     for (ClassInfo info : classInfoList)
/*  54:    */     {
/*  55:115 */       EmbeddableClass embeddable = new EmbeddableClass(info, propertyName, parent, defaultAccessType, context);
/*  56:    */       
/*  57:    */ 
/*  58:118 */       this.embeddables.add(embeddable);
/*  59:119 */       parent = embeddable;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public AccessType getDefaultAccessType()
/*  64:    */   {
/*  65:125 */     return this.defaultAccessType;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Iterator<EmbeddableClass> iterator()
/*  69:    */   {
/*  70:132 */     return this.embeddables.iterator();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public EmbeddableClass getLeaf()
/*  74:    */   {
/*  75:139 */     return (EmbeddableClass)this.embeddables.get(this.embeddables.size() - 1);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:144 */     StringBuilder sb = new StringBuilder();
/*  81:145 */     sb.append("EmbeddableHierarchy");
/*  82:146 */     sb.append("{defaultAccessType=").append(this.defaultAccessType);
/*  83:147 */     sb.append(", embeddables=").append(this.embeddables);
/*  84:148 */     sb.append('}');
/*  85:149 */     return sb.toString();
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.EmbeddableHierarchy
 * JD-Core Version:    0.7.0.1
 */