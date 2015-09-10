/*  1:   */ package org.hibernate.bytecode.spi;
/*  2:   */ 
/*  3:   */ import java.security.ProtectionDomain;
/*  4:   */ import org.hibernate.bytecode.buildtime.spi.ClassFilter;
/*  5:   */ import org.hibernate.bytecode.buildtime.spi.FieldFilter;
/*  6:   */ 
/*  7:   */ public abstract class AbstractClassTransformerImpl
/*  8:   */   implements ClassTransformer
/*  9:   */ {
/* 10:   */   protected final ClassFilter classFilter;
/* 11:   */   protected final FieldFilter fieldFilter;
/* 12:   */   
/* 13:   */   protected AbstractClassTransformerImpl(ClassFilter classFilter, FieldFilter fieldFilter)
/* 14:   */   {
/* 15:43 */     this.classFilter = classFilter;
/* 16:44 */     this.fieldFilter = fieldFilter;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
/* 20:   */   {
/* 21:54 */     className = className.replace('/', '.');
/* 22:55 */     if (this.classFilter.shouldInstrumentClass(className)) {
/* 23:56 */       return doTransform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
/* 24:   */     }
/* 25:59 */     return classfileBuffer;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected abstract byte[] doTransform(ClassLoader paramClassLoader, String paramString, Class paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte);
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.AbstractClassTransformerImpl
 * JD-Core Version:    0.7.0.1
 */