/*  1:   */ package org.jboss.jandex;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public final class ClassInfo
/*  8:   */   implements AnnotationTarget
/*  9:   */ {
/* 10:   */   private final DotName name;
/* 11:   */   private short flags;
/* 12:   */   private final DotName superName;
/* 13:   */   private final DotName[] interfaces;
/* 14:   */   private final Map<DotName, List<AnnotationInstance>> annotations;
/* 15:   */   
/* 16:   */   ClassInfo(DotName name, DotName superName, short flags, DotName[] interfaces, Map<DotName, List<AnnotationInstance>> annotations)
/* 17:   */   {
/* 18:56 */     this.name = name;
/* 19:57 */     this.superName = superName;
/* 20:58 */     this.flags = flags;
/* 21:59 */     this.interfaces = interfaces;
/* 22:60 */     this.annotations = Collections.unmodifiableMap(annotations);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static final ClassInfo create(DotName name, DotName superName, short flags, DotName[] interfaces, Map<DotName, List<AnnotationInstance>> annotations)
/* 26:   */   {
/* 27:75 */     return new ClassInfo(name, superName, flags, interfaces, annotations);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:79 */     return this.name.toString();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public final DotName name()
/* 36:   */   {
/* 37:83 */     return this.name;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public final short flags()
/* 41:   */   {
/* 42:87 */     return this.flags;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public final DotName superName()
/* 46:   */   {
/* 47:91 */     return this.superName;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public final DotName[] interfaces()
/* 51:   */   {
/* 52:95 */     return this.interfaces;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public final Map<DotName, List<AnnotationInstance>> annotations()
/* 56:   */   {
/* 57:99 */     return this.annotations;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.ClassInfo
 * JD-Core Version:    0.7.0.1
 */