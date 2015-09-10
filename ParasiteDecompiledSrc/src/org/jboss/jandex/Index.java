/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ 
/*  10:    */ public final class Index
/*  11:    */ {
/*  12: 45 */   private static final List<AnnotationInstance> EMPTY_ANNOTATION_LIST = ;
/*  13: 46 */   private static final List<ClassInfo> EMPTY_CLASSINFO_LIST = Collections.emptyList();
/*  14:    */   final Map<DotName, List<AnnotationInstance>> annotations;
/*  15:    */   final Map<DotName, List<ClassInfo>> subclasses;
/*  16:    */   final Map<DotName, List<ClassInfo>> implementors;
/*  17:    */   final Map<DotName, ClassInfo> classes;
/*  18:    */   
/*  19:    */   Index(Map<DotName, List<AnnotationInstance>> annotations, Map<DotName, List<ClassInfo>> subclasses, Map<DotName, List<ClassInfo>> implementors, Map<DotName, ClassInfo> classes)
/*  20:    */   {
/*  21: 54 */     this.annotations = Collections.unmodifiableMap(annotations);
/*  22: 55 */     this.classes = Collections.unmodifiableMap(classes);
/*  23: 56 */     this.subclasses = Collections.unmodifiableMap(subclasses);
/*  24: 57 */     this.implementors = Collections.unmodifiableMap(implementors);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static Index create(Map<DotName, List<AnnotationInstance>> annotations, Map<DotName, List<ClassInfo>> subclasses, Map<DotName, List<ClassInfo>> implementors, Map<DotName, ClassInfo> classes)
/*  28:    */   {
/*  29: 74 */     return new Index(annotations, subclasses, implementors, classes);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<AnnotationInstance> getAnnotations(DotName annotationName)
/*  33:    */   {
/*  34: 87 */     List<AnnotationInstance> list = (List)this.annotations.get(annotationName);
/*  35: 88 */     return list == null ? EMPTY_ANNOTATION_LIST : Collections.unmodifiableList(list);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<ClassInfo> getKnownDirectSubclasses(DotName className)
/*  39:    */   {
/*  40:105 */     List<ClassInfo> list = (List)this.subclasses.get(className);
/*  41:106 */     return list == null ? EMPTY_CLASSINFO_LIST : Collections.unmodifiableList(list);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<ClassInfo> getKnownDirectImplementors(DotName className)
/*  45:    */   {
/*  46:125 */     List<ClassInfo> list = (List)this.implementors.get(className);
/*  47:126 */     return list == null ? EMPTY_CLASSINFO_LIST : Collections.unmodifiableList(list);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ClassInfo getClassByName(DotName className)
/*  51:    */   {
/*  52:137 */     return (ClassInfo)this.classes.get(className);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Collection<ClassInfo> getKnownClasses()
/*  56:    */   {
/*  57:146 */     return this.classes.values();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void printAnnotations()
/*  61:    */   {
/*  62:154 */     System.out.println("Annotations:");
/*  63:155 */     for (Map.Entry<DotName, List<AnnotationInstance>> e : this.annotations.entrySet())
/*  64:    */     {
/*  65:156 */       System.out.println(e.getKey() + ":");
/*  66:157 */       for (AnnotationInstance instance : (List)e.getValue())
/*  67:    */       {
/*  68:158 */         AnnotationTarget target = instance.target();
/*  69:161 */         if ((target instanceof ClassInfo)) {
/*  70:162 */           System.out.println("    Class: " + target);
/*  71:163 */         } else if ((target instanceof FieldInfo)) {
/*  72:164 */           System.out.println("    Field: " + target);
/*  73:165 */         } else if ((target instanceof MethodInfo)) {
/*  74:166 */           System.out.println("    Method: " + target);
/*  75:167 */         } else if ((target instanceof MethodParameterInfo)) {
/*  76:168 */           System.out.println("    Parameter: " + target);
/*  77:    */         }
/*  78:171 */         List<AnnotationValue> values = instance.values();
/*  79:172 */         if (values.size() >= 1)
/*  80:    */         {
/*  81:175 */           StringBuilder builder = new StringBuilder("        (");
/*  82:177 */           for (int i = 0; i < values.size(); i++)
/*  83:    */           {
/*  84:178 */             builder.append(values.get(i));
/*  85:179 */             if (i < values.size() - 1) {
/*  86:180 */               builder.append(", ");
/*  87:    */             }
/*  88:    */           }
/*  89:182 */           builder.append(')');
/*  90:183 */           System.out.println(builder.toString());
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void printSubclasses()
/*  97:    */   {
/*  98:193 */     System.out.println("Subclasses:");
/*  99:194 */     for (Map.Entry<DotName, List<ClassInfo>> entry : this.subclasses.entrySet())
/* 100:    */     {
/* 101:195 */       System.out.println(entry.getKey() + ":");
/* 102:196 */       for (ClassInfo clazz : (List)entry.getValue()) {
/* 103:197 */         System.out.println("    " + clazz.name());
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.Index
 * JD-Core Version:    0.7.0.1
 */