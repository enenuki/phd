/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.DataInputStream;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.security.ProtectionDomain;
/*   9:    */ import javassist.bytecode.ClassFile;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.bytecode.buildtime.spi.ClassFilter;
/*  12:    */ import org.hibernate.bytecode.spi.AbstractClassTransformerImpl;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class JavassistClassTransformer
/*  17:    */   extends AbstractClassTransformerImpl
/*  18:    */ {
/*  19: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JavassistClassTransformer.class.getName());
/*  20:    */   
/*  21:    */   public JavassistClassTransformer(ClassFilter classFilter, org.hibernate.bytecode.buildtime.spi.FieldFilter fieldFilter)
/*  22:    */   {
/*  23: 54 */     super(classFilter, fieldFilter);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected byte[] doTransform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
/*  27:    */   {
/*  28:    */     ClassFile classfile;
/*  29:    */     try
/*  30:    */     {
/*  31: 67 */       classfile = new ClassFile(new DataInputStream(new ByteArrayInputStream(classfileBuffer)));
/*  32:    */     }
/*  33:    */     catch (IOException e)
/*  34:    */     {
/*  35: 70 */       LOG.unableToBuildEnhancementMetamodel(className);
/*  36: 71 */       return classfileBuffer;
/*  37:    */     }
/*  38: 73 */     FieldTransformer transformer = getFieldTransformer(classfile);
/*  39: 74 */     if (transformer != null)
/*  40:    */     {
/*  41: 75 */       LOG.debugf("Enhancing %s", className);
/*  42: 76 */       DataOutputStream out = null;
/*  43:    */       try
/*  44:    */       {
/*  45: 78 */         transformer.transform(classfile);
/*  46: 79 */         ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
/*  47: 80 */         out = new DataOutputStream(byteStream);
/*  48: 81 */         classfile.write(out);
/*  49: 82 */         return byteStream.toByteArray();
/*  50:    */       }
/*  51:    */       catch (Exception e)
/*  52:    */       {
/*  53: 85 */         LOG.unableToTransformClass(e.getMessage());
/*  54: 86 */         throw new HibernateException("Unable to transform class: " + e.getMessage());
/*  55:    */       }
/*  56:    */       finally
/*  57:    */       {
/*  58:    */         try
/*  59:    */         {
/*  60: 90 */           if (out != null) {
/*  61: 90 */             out.close();
/*  62:    */           }
/*  63:    */         }
/*  64:    */         catch (IOException e) {}
/*  65:    */       }
/*  66:    */     }
/*  67: 97 */     return classfileBuffer;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected FieldTransformer getFieldTransformer(final ClassFile classfile)
/*  71:    */   {
/*  72:101 */     if (alreadyInstrumented(classfile)) {
/*  73:102 */       return null;
/*  74:    */     }
/*  75:104 */     new FieldTransformer(new FieldFilter()
/*  76:    */     {
/*  77:    */       public boolean handleRead(String desc, String name)
/*  78:    */       {
/*  79:107 */         return JavassistClassTransformer.this.fieldFilter.shouldInstrumentField(classfile.getName(), name);
/*  80:    */       }
/*  81:    */       
/*  82:    */       public boolean handleWrite(String desc, String name)
/*  83:    */       {
/*  84:111 */         return JavassistClassTransformer.this.fieldFilter.shouldInstrumentField(classfile.getName(), name);
/*  85:    */       }
/*  86:    */       
/*  87:    */       public boolean handleReadAccess(String fieldOwnerClassName, String fieldName)
/*  88:    */       {
/*  89:115 */         return JavassistClassTransformer.this.fieldFilter.shouldTransformFieldAccess(classfile.getName(), fieldOwnerClassName, fieldName);
/*  90:    */       }
/*  91:    */       
/*  92:    */       public boolean handleWriteAccess(String fieldOwnerClassName, String fieldName)
/*  93:    */       {
/*  94:119 */         return JavassistClassTransformer.this.fieldFilter.shouldTransformFieldAccess(classfile.getName(), fieldOwnerClassName, fieldName);
/*  95:    */       }
/*  96:    */     });
/*  97:    */   }
/*  98:    */   
/*  99:    */   private boolean alreadyInstrumented(ClassFile classfile)
/* 100:    */   {
/* 101:126 */     String[] intfs = classfile.getInterfaces();
/* 102:127 */     for (int i = 0; i < intfs.length; i++) {
/* 103:128 */       if (FieldHandled.class.getName().equals(intfs[i])) {
/* 104:129 */         return true;
/* 105:    */       }
/* 106:    */     }
/* 107:132 */     return false;
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.JavassistClassTransformer
 * JD-Core Version:    0.7.0.1
 */