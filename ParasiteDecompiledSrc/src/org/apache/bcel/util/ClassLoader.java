/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import org.apache.bcel.Repository;
/*   6:    */ import org.apache.bcel.classfile.ClassParser;
/*   7:    */ import org.apache.bcel.classfile.ConstantClass;
/*   8:    */ import org.apache.bcel.classfile.ConstantPool;
/*   9:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*  10:    */ import org.apache.bcel.classfile.JavaClass;
/*  11:    */ import org.apache.bcel.classfile.Utility;
/*  12:    */ 
/*  13:    */ public class ClassLoader
/*  14:    */   extends java.lang.ClassLoader
/*  15:    */ {
/*  16: 88 */   private Hashtable classes = new Hashtable();
/*  17: 89 */   private String[] ignored_packages = { "java.", "javax.", "sun." };
/*  18:    */   
/*  19:    */   public ClassLoader() {}
/*  20:    */   
/*  21:    */   public ClassLoader(String[] ignored_packages)
/*  22:    */   {
/*  23:100 */     String[] new_p = new String[ignored_packages.length + this.ignored_packages.length];
/*  24:    */     
/*  25:102 */     System.arraycopy(this.ignored_packages, 0, new_p, 0, this.ignored_packages.length);
/*  26:103 */     System.arraycopy(ignored_packages, 0, new_p, this.ignored_packages.length, ignored_packages.length);
/*  27:    */     
/*  28:    */ 
/*  29:106 */     this.ignored_packages = new_p;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected Class loadClass(String class_name, boolean resolve)
/*  33:    */     throws ClassNotFoundException
/*  34:    */   {
/*  35:112 */     Class cl = null;
/*  36:116 */     if ((cl = (Class)this.classes.get(class_name)) == null)
/*  37:    */     {
/*  38:120 */       for (int i = 0; i < this.ignored_packages.length; i++) {
/*  39:121 */         if (class_name.startsWith(this.ignored_packages[i]))
/*  40:    */         {
/*  41:122 */           cl = Class.forName(class_name);
/*  42:123 */           break;
/*  43:    */         }
/*  44:    */       }
/*  45:127 */       if (cl == null)
/*  46:    */       {
/*  47:128 */         JavaClass clazz = null;
/*  48:132 */         if (class_name.indexOf("$$BCEL$$") >= 0) {
/*  49:133 */           clazz = createClass(class_name);
/*  50:135 */         } else if ((clazz = Repository.lookupClass(class_name)) != null) {
/*  51:136 */           clazz = modifyClass(clazz);
/*  52:    */         } else {
/*  53:138 */           throw new ClassNotFoundException(class_name);
/*  54:    */         }
/*  55:141 */         if (clazz != null)
/*  56:    */         {
/*  57:142 */           byte[] bytes = clazz.getBytes();
/*  58:143 */           cl = defineClass(class_name, bytes, 0, bytes.length);
/*  59:    */         }
/*  60:    */         else
/*  61:    */         {
/*  62:145 */           cl = Class.forName(class_name);
/*  63:    */         }
/*  64:    */       }
/*  65:148 */       if (resolve) {
/*  66:149 */         resolveClass(cl);
/*  67:    */       }
/*  68:    */     }
/*  69:152 */     this.classes.put(class_name, cl);
/*  70:    */     
/*  71:154 */     return cl;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected JavaClass modifyClass(JavaClass clazz)
/*  75:    */   {
/*  76:161 */     return clazz;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected JavaClass createClass(String class_name)
/*  80:    */   {
/*  81:179 */     int index = class_name.indexOf("$$BCEL$$");
/*  82:180 */     String real_name = class_name.substring(index + 8);
/*  83:    */     
/*  84:182 */     JavaClass clazz = null;
/*  85:    */     try
/*  86:    */     {
/*  87:184 */       byte[] bytes = Utility.decode(real_name, true);
/*  88:185 */       ClassParser parser = new ClassParser(new ByteArrayInputStream(bytes), "foo");
/*  89:    */       
/*  90:187 */       clazz = parser.parse();
/*  91:    */     }
/*  92:    */     catch (Throwable e)
/*  93:    */     {
/*  94:189 */       e.printStackTrace();
/*  95:190 */       return null;
/*  96:    */     }
/*  97:194 */     ConstantPool cp = clazz.getConstantPool();
/*  98:    */     
/*  99:196 */     ConstantClass cl = (ConstantClass)cp.getConstant(clazz.getClassNameIndex(), (byte)7);
/* 100:    */     
/* 101:198 */     ConstantUtf8 name = (ConstantUtf8)cp.getConstant(cl.getNameIndex(), (byte)1);
/* 102:    */     
/* 103:200 */     name.setBytes(class_name.replace('.', '/'));
/* 104:    */     
/* 105:202 */     return clazz;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassLoader
 * JD-Core Version:    0.7.0.1
 */