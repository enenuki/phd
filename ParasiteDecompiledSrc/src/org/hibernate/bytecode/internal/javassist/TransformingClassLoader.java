/*  1:   */ package org.hibernate.bytecode.internal.javassist;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import javassist.CannotCompileException;
/*  5:   */ import javassist.ClassPool;
/*  6:   */ import javassist.CtClass;
/*  7:   */ import javassist.NotFoundException;
/*  8:   */ import org.hibernate.HibernateException;
/*  9:   */ 
/* 10:   */ public class TransformingClassLoader
/* 11:   */   extends ClassLoader
/* 12:   */ {
/* 13:   */   private ClassLoader parent;
/* 14:   */   private ClassPool classPool;
/* 15:   */   
/* 16:   */   TransformingClassLoader(ClassLoader parent, String[] classpath)
/* 17:   */   {
/* 18:43 */     this.parent = parent;
/* 19:44 */     this.classPool = new ClassPool(true);
/* 20:45 */     for (int i = 0; i < classpath.length; i++) {
/* 21:   */       try
/* 22:   */       {
/* 23:47 */         this.classPool.appendClassPath(classpath[i]);
/* 24:   */       }
/* 25:   */       catch (NotFoundException e)
/* 26:   */       {
/* 27:50 */         throw new HibernateException("Unable to resolve requested classpath for transformation [" + classpath[i] + "] : " + e.getMessage());
/* 28:   */       }
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected Class findClass(String name)
/* 33:   */     throws ClassNotFoundException
/* 34:   */   {
/* 35:   */     try
/* 36:   */     {
/* 37:60 */       CtClass cc = this.classPool.get(name);
/* 38:   */       
/* 39:62 */       byte[] b = cc.toBytecode();
/* 40:63 */       return defineClass(name, b, 0, b.length);
/* 41:   */     }
/* 42:   */     catch (NotFoundException e)
/* 43:   */     {
/* 44:66 */       throw new ClassNotFoundException();
/* 45:   */     }
/* 46:   */     catch (IOException e)
/* 47:   */     {
/* 48:69 */       throw new ClassNotFoundException();
/* 49:   */     }
/* 50:   */     catch (CannotCompileException e)
/* 51:   */     {
/* 52:72 */       throw new ClassNotFoundException();
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void release()
/* 57:   */   {
/* 58:77 */     this.classPool = null;
/* 59:78 */     this.parent = null;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.TransformingClassLoader
 * JD-Core Version:    0.7.0.1
 */