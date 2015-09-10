/*  1:   */ package org.hibernate.bytecode.buildtime.internal;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.DataInputStream;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.util.Set;
/*  7:   */ import javassist.bytecode.ClassFile;
/*  8:   */ import org.hibernate.bytecode.buildtime.spi.AbstractInstrumenter;
/*  9:   */ import org.hibernate.bytecode.buildtime.spi.AbstractInstrumenter.CustomFieldFilter;
/* 10:   */ import org.hibernate.bytecode.buildtime.spi.BasicClassFilter;
/* 11:   */ import org.hibernate.bytecode.buildtime.spi.ClassDescriptor;
/* 12:   */ import org.hibernate.bytecode.buildtime.spi.Instrumenter.Options;
/* 13:   */ import org.hibernate.bytecode.buildtime.spi.Logger;
/* 14:   */ import org.hibernate.bytecode.internal.javassist.BytecodeProviderImpl;
/* 15:   */ import org.hibernate.bytecode.internal.javassist.FieldHandled;
/* 16:   */ import org.hibernate.bytecode.spi.ClassTransformer;
/* 17:   */ 
/* 18:   */ public class JavassistInstrumenter
/* 19:   */   extends AbstractInstrumenter
/* 20:   */ {
/* 21:50 */   private static final BasicClassFilter CLASS_FILTER = new BasicClassFilter();
/* 22:52 */   private final BytecodeProviderImpl provider = new BytecodeProviderImpl();
/* 23:   */   
/* 24:   */   public JavassistInstrumenter(Logger logger, Instrumenter.Options options)
/* 25:   */   {
/* 26:55 */     super(logger, options);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected ClassDescriptor getClassDescriptor(byte[] bytecode)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:60 */     return new CustomClassDescriptor(bytecode);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ClassTransformer getClassTransformer(ClassDescriptor descriptor, Set classNames)
/* 36:   */   {
/* 37:65 */     if (descriptor.isInstrumented())
/* 38:   */     {
/* 39:66 */       this.logger.debug("class [" + descriptor.getName() + "] already instrumented");
/* 40:67 */       return null;
/* 41:   */     }
/* 42:70 */     return this.provider.getTransformer(CLASS_FILTER, new AbstractInstrumenter.CustomFieldFilter(this, descriptor, classNames));
/* 43:   */   }
/* 44:   */   
/* 45:   */   private static class CustomClassDescriptor
/* 46:   */     implements ClassDescriptor
/* 47:   */   {
/* 48:   */     private final byte[] bytes;
/* 49:   */     private final ClassFile classFile;
/* 50:   */     
/* 51:   */     public CustomClassDescriptor(byte[] bytes)
/* 52:   */       throws IOException
/* 53:   */     {
/* 54:79 */       this.bytes = bytes;
/* 55:80 */       this.classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
/* 56:   */     }
/* 57:   */     
/* 58:   */     public String getName()
/* 59:   */     {
/* 60:84 */       return this.classFile.getName();
/* 61:   */     }
/* 62:   */     
/* 63:   */     public boolean isInstrumented()
/* 64:   */     {
/* 65:88 */       String[] interfaceNames = this.classFile.getInterfaces();
/* 66:89 */       for (String interfaceName : interfaceNames) {
/* 67:90 */         if (FieldHandled.class.getName().equals(interfaceName)) {
/* 68:91 */           return true;
/* 69:   */         }
/* 70:   */       }
/* 71:94 */       return false;
/* 72:   */     }
/* 73:   */     
/* 74:   */     public byte[] getBytes()
/* 75:   */     {
/* 76:98 */       return this.bytes;
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.internal.JavassistInstrumenter
 * JD-Core Version:    0.7.0.1
 */