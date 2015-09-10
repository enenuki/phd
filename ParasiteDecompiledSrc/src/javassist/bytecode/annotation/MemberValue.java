/*  1:   */ package javassist.bytecode.annotation;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import javassist.ClassPool;
/*  6:   */ import javassist.bytecode.ConstPool;
/*  7:   */ import javassist.bytecode.Descriptor;
/*  8:   */ 
/*  9:   */ public abstract class MemberValue
/* 10:   */ {
/* 11:   */   ConstPool cp;
/* 12:   */   char tag;
/* 13:   */   
/* 14:   */   MemberValue(char tag, ConstPool cp)
/* 15:   */   {
/* 16:38 */     this.cp = cp;
/* 17:39 */     this.tag = tag;
/* 18:   */   }
/* 19:   */   
/* 20:   */   abstract Object getValue(ClassLoader paramClassLoader, ClassPool paramClassPool, Method paramMethod)
/* 21:   */     throws ClassNotFoundException;
/* 22:   */   
/* 23:   */   abstract Class getType(ClassLoader paramClassLoader)
/* 24:   */     throws ClassNotFoundException;
/* 25:   */   
/* 26:   */   static Class loadClass(ClassLoader cl, String classname)
/* 27:   */     throws ClassNotFoundException, NoSuchClassError
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:55 */       return Class.forName(convertFromArray(classname), true, cl);
/* 32:   */     }
/* 33:   */     catch (LinkageError e)
/* 34:   */     {
/* 35:58 */       throw new NoSuchClassError(classname, e);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   private static String convertFromArray(String classname)
/* 40:   */   {
/* 41:64 */     int index = classname.indexOf("[]");
/* 42:65 */     if (index != -1)
/* 43:   */     {
/* 44:66 */       String rawType = classname.substring(0, index);
/* 45:67 */       StringBuffer sb = new StringBuffer(Descriptor.of(rawType));
/* 46:68 */       while (index != -1)
/* 47:   */       {
/* 48:69 */         sb.insert(0, "[");
/* 49:70 */         index = classname.indexOf("[]", index + 1);
/* 50:   */       }
/* 51:72 */       return sb.toString().replace('/', '.');
/* 52:   */     }
/* 53:74 */     return classname;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public abstract void accept(MemberValueVisitor paramMemberValueVisitor);
/* 57:   */   
/* 58:   */   public abstract void write(AnnotationsWriter paramAnnotationsWriter)
/* 59:   */     throws IOException;
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.MemberValue
 * JD-Core Version:    0.7.0.1
 */