/*  1:   */ package javassist.bytecode.annotation;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import javassist.ClassPool;
/*  6:   */ import javassist.bytecode.ConstPool;
/*  7:   */ 
/*  8:   */ public class AnnotationMemberValue
/*  9:   */   extends MemberValue
/* 10:   */ {
/* 11:   */   Annotation value;
/* 12:   */   
/* 13:   */   public AnnotationMemberValue(ConstPool cp)
/* 14:   */   {
/* 15:35 */     this(null, cp);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AnnotationMemberValue(Annotation a, ConstPool cp)
/* 19:   */   {
/* 20:43 */     super('@', cp);
/* 21:44 */     this.value = a;
/* 22:   */   }
/* 23:   */   
/* 24:   */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/* 25:   */     throws ClassNotFoundException
/* 26:   */   {
/* 27:50 */     return AnnotationImpl.make(cl, getType(cl), cp, this.value);
/* 28:   */   }
/* 29:   */   
/* 30:   */   Class getType(ClassLoader cl)
/* 31:   */     throws ClassNotFoundException
/* 32:   */   {
/* 33:54 */     if (this.value == null) {
/* 34:55 */       throw new ClassNotFoundException("no type specified");
/* 35:   */     }
/* 36:57 */     return loadClass(cl, this.value.getTypeName());
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Annotation getValue()
/* 40:   */   {
/* 41:64 */     return this.value;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setValue(Annotation newValue)
/* 45:   */   {
/* 46:71 */     this.value = newValue;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:78 */     return this.value.toString();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void write(AnnotationsWriter writer)
/* 55:   */     throws IOException
/* 56:   */   {
/* 57:85 */     writer.annotationValue();
/* 58:86 */     this.value.write(writer);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void accept(MemberValueVisitor visitor)
/* 62:   */   {
/* 63:93 */     visitor.visitAnnotationMemberValue(this);
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.AnnotationMemberValue
 * JD-Core Version:    0.7.0.1
 */