/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import javassist.bytecode.ClassFile;
/*  4:   */ import javassist.bytecode.InnerClassesAttribute;
/*  5:   */ 
/*  6:   */ class CtNewNestedClass
/*  7:   */   extends CtNewClass
/*  8:   */ {
/*  9:   */   CtNewNestedClass(String realName, ClassPool cp, boolean isInterface, CtClass superclass)
/* 10:   */   {
/* 11:28 */     super(realName, cp, isInterface, superclass);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void setModifiers(int mod)
/* 15:   */   {
/* 16:35 */     mod &= 0xFFFFFFF7;
/* 17:36 */     super.setModifiers(mod);
/* 18:37 */     updateInnerEntry(mod, getName(), this, true);
/* 19:   */   }
/* 20:   */   
/* 21:   */   private static void updateInnerEntry(int mod, String name, CtClass clazz, boolean outer)
/* 22:   */   {
/* 23:41 */     ClassFile cf = clazz.getClassFile2();
/* 24:42 */     InnerClassesAttribute ica = (InnerClassesAttribute)cf.getAttribute("InnerClasses");
/* 25:44 */     if (ica == null) {
/* 26:45 */       return;
/* 27:   */     }
/* 28:47 */     int n = ica.tableLength();
/* 29:48 */     for (int i = 0; i < n; i++) {
/* 30:49 */       if (name.equals(ica.innerClass(i)))
/* 31:   */       {
/* 32:50 */         int acc = ica.accessFlags(i) & 0x8;
/* 33:51 */         ica.setAccessFlags(i, mod | acc);
/* 34:52 */         String outName = ica.outerClass(i);
/* 35:53 */         if ((outName == null) || (!outer)) {
/* 36:   */           break;
/* 37:   */         }
/* 38:   */         try
/* 39:   */         {
/* 40:55 */           CtClass parent = clazz.getClassPool().get(outName);
/* 41:56 */           updateInnerEntry(mod, name, parent, false);
/* 42:   */         }
/* 43:   */         catch (NotFoundException e)
/* 44:   */         {
/* 45:59 */           throw new RuntimeException("cannot find the declaring class: " + outName);
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewNestedClass
 * JD-Core Version:    0.7.0.1
 */