/*  1:   */ package org.apache.commons.io.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.ObjectInputStream;
/*  6:   */ import java.io.ObjectStreamClass;
/*  7:   */ import java.io.StreamCorruptedException;
/*  8:   */ 
/*  9:   */ public class ClassLoaderObjectInputStream
/* 10:   */   extends ObjectInputStream
/* 11:   */ {
/* 12:   */   private final ClassLoader classLoader;
/* 13:   */   
/* 14:   */   public ClassLoaderObjectInputStream(ClassLoader classLoader, InputStream inputStream)
/* 15:   */     throws IOException, StreamCorruptedException
/* 16:   */   {
/* 17:51 */     super(inputStream);
/* 18:52 */     this.classLoader = classLoader;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected Class<?> resolveClass(ObjectStreamClass objectStreamClass)
/* 22:   */     throws IOException, ClassNotFoundException
/* 23:   */   {
/* 24:68 */     Class<?> clazz = Class.forName(objectStreamClass.getName(), false, this.classLoader);
/* 25:70 */     if (clazz != null) {
/* 26:72 */       return clazz;
/* 27:   */     }
/* 28:75 */     return super.resolveClass(objectStreamClass);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.ClassLoaderObjectInputStream
 * JD-Core Version:    0.7.0.1
 */