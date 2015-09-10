/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ import javassist.bytecode.Descriptor;
/*   8:    */ 
/*   9:    */ public class ClassMemberValue
/*  10:    */   extends MemberValue
/*  11:    */ {
/*  12:    */   int valueIndex;
/*  13:    */   
/*  14:    */   public ClassMemberValue(int index, ConstPool cp)
/*  15:    */   {
/*  16: 40 */     super('c', cp);
/*  17: 41 */     this.valueIndex = index;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ClassMemberValue(String className, ConstPool cp)
/*  21:    */   {
/*  22: 50 */     super('c', cp);
/*  23: 51 */     setValue(className);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ClassMemberValue(ConstPool cp)
/*  27:    */   {
/*  28: 59 */     super('c', cp);
/*  29: 60 */     setValue("java.lang.Class");
/*  30:    */   }
/*  31:    */   
/*  32:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  33:    */     throws ClassNotFoundException
/*  34:    */   {
/*  35: 65 */     String classname = getValue();
/*  36: 66 */     if (classname.equals("void")) {
/*  37: 67 */       return Void.TYPE;
/*  38:    */     }
/*  39: 68 */     if (classname.equals("int")) {
/*  40: 69 */       return Integer.TYPE;
/*  41:    */     }
/*  42: 70 */     if (classname.equals("byte")) {
/*  43: 71 */       return Byte.TYPE;
/*  44:    */     }
/*  45: 72 */     if (classname.equals("long")) {
/*  46: 73 */       return Long.TYPE;
/*  47:    */     }
/*  48: 74 */     if (classname.equals("double")) {
/*  49: 75 */       return Double.TYPE;
/*  50:    */     }
/*  51: 76 */     if (classname.equals("float")) {
/*  52: 77 */       return Float.TYPE;
/*  53:    */     }
/*  54: 78 */     if (classname.equals("char")) {
/*  55: 79 */       return Character.TYPE;
/*  56:    */     }
/*  57: 80 */     if (classname.equals("short")) {
/*  58: 81 */       return Short.TYPE;
/*  59:    */     }
/*  60: 82 */     if (classname.equals("boolean")) {
/*  61: 83 */       return Boolean.TYPE;
/*  62:    */     }
/*  63: 85 */     return loadClass(cl, classname);
/*  64:    */   }
/*  65:    */   
/*  66:    */   Class getType(ClassLoader cl)
/*  67:    */     throws ClassNotFoundException
/*  68:    */   {
/*  69: 89 */     return loadClass(cl, "java.lang.Class");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getValue()
/*  73:    */   {
/*  74: 98 */     String v = this.cp.getUtf8Info(this.valueIndex);
/*  75: 99 */     return Descriptor.toClassName(v);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setValue(String newClassName)
/*  79:    */   {
/*  80:108 */     String setTo = Descriptor.of(newClassName);
/*  81:109 */     this.valueIndex = this.cp.addUtf8Info(setTo);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String toString()
/*  85:    */   {
/*  86:116 */     return "<" + getValue() + " class>";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void write(AnnotationsWriter writer)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:123 */     writer.classInfoIndex(this.cp.getUtf8Info(this.valueIndex));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void accept(MemberValueVisitor visitor)
/*  96:    */   {
/*  97:130 */     visitor.visitClassMemberValue(this);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.ClassMemberValue
 * JD-Core Version:    0.7.0.1
 */