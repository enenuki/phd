/*  1:   */ package org.hibernate.bytecode.spi;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ 
/*  5:   */ public class InstrumentedClassLoader
/*  6:   */   extends ClassLoader
/*  7:   */ {
/*  8:   */   private ClassTransformer classTransformer;
/*  9:   */   
/* 10:   */   public InstrumentedClassLoader(ClassLoader parent, ClassTransformer classTransformer)
/* 11:   */   {
/* 12:40 */     super(parent);
/* 13:41 */     this.classTransformer = classTransformer;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Class loadClass(String name)
/* 17:   */     throws ClassNotFoundException
/* 18:   */   {
/* 19:45 */     if ((name.startsWith("java.")) || (this.classTransformer == null)) {
/* 20:46 */       return getParent().loadClass(name);
/* 21:   */     }
/* 22:49 */     Class c = findLoadedClass(name);
/* 23:50 */     if (c != null) {
/* 24:51 */       return c;
/* 25:   */     }
/* 26:54 */     InputStream is = getResourceAsStream(name.replace('.', '/') + ".class");
/* 27:55 */     if (is == null) {
/* 28:56 */       throw new ClassNotFoundException(name + " not found");
/* 29:   */     }
/* 30:   */     try
/* 31:   */     {
/* 32:60 */       byte[] originalBytecode = ByteCodeHelper.readByteCode(is);
/* 33:61 */       byte[] transformedBytecode = this.classTransformer.transform(getParent(), name, null, null, originalBytecode);
/* 34:62 */       if (originalBytecode == transformedBytecode) {
/* 35:65 */         return getParent().loadClass(name);
/* 36:   */       }
/* 37:68 */       return defineClass(name, transformedBytecode, 0, transformedBytecode.length);
/* 38:   */     }
/* 39:   */     catch (Throwable t)
/* 40:   */     {
/* 41:72 */       throw new ClassNotFoundException(name + " not found", t);
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.InstrumentedClassLoader
 * JD-Core Version:    0.7.0.1
 */