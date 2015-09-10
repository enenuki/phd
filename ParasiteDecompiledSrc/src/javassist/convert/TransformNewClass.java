/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CannotCompileException;
/*  4:   */ import javassist.CtClass;
/*  5:   */ import javassist.bytecode.CodeAttribute;
/*  6:   */ import javassist.bytecode.CodeIterator;
/*  7:   */ import javassist.bytecode.ConstPool;
/*  8:   */ 
/*  9:   */ public final class TransformNewClass
/* 10:   */   extends Transformer
/* 11:   */ {
/* 12:   */   private int nested;
/* 13:   */   private String classname;
/* 14:   */   private String newClassName;
/* 15:   */   private int newClassIndex;
/* 16:   */   private int newMethodNTIndex;
/* 17:   */   private int newMethodIndex;
/* 18:   */   
/* 19:   */   public TransformNewClass(Transformer next, String classname, String newClassName)
/* 20:   */   {
/* 21:29 */     super(next);
/* 22:30 */     this.classname = classname;
/* 23:31 */     this.newClassName = newClassName;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void initialize(ConstPool cp, CodeAttribute attr)
/* 27:   */   {
/* 28:35 */     this.nested = 0;
/* 29:36 */     this.newClassIndex = (this.newMethodNTIndex = this.newMethodIndex = 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp)
/* 33:   */     throws CannotCompileException
/* 34:   */   {
/* 35:50 */     int c = iterator.byteAt(pos);
/* 36:51 */     if (c == 187)
/* 37:   */     {
/* 38:52 */       int index = iterator.u16bitAt(pos + 1);
/* 39:53 */       if (cp.getClassInfo(index).equals(this.classname))
/* 40:   */       {
/* 41:54 */         if (iterator.byteAt(pos + 3) != 89) {
/* 42:55 */           throw new CannotCompileException("NEW followed by no DUP was found");
/* 43:   */         }
/* 44:58 */         if (this.newClassIndex == 0) {
/* 45:59 */           this.newClassIndex = cp.addClassInfo(this.newClassName);
/* 46:   */         }
/* 47:61 */         iterator.write16bit(this.newClassIndex, pos + 1);
/* 48:62 */         this.nested += 1;
/* 49:   */       }
/* 50:   */     }
/* 51:65 */     else if (c == 183)
/* 52:   */     {
/* 53:66 */       int index = iterator.u16bitAt(pos + 1);
/* 54:67 */       int typedesc = cp.isConstructor(this.classname, index);
/* 55:68 */       if ((typedesc != 0) && (this.nested > 0))
/* 56:   */       {
/* 57:69 */         int nt = cp.getMethodrefNameAndType(index);
/* 58:70 */         if (this.newMethodNTIndex != nt)
/* 59:   */         {
/* 60:71 */           this.newMethodNTIndex = nt;
/* 61:72 */           this.newMethodIndex = cp.addMethodrefInfo(this.newClassIndex, nt);
/* 62:   */         }
/* 63:75 */         iterator.write16bit(this.newMethodIndex, pos + 1);
/* 64:76 */         this.nested -= 1;
/* 65:   */       }
/* 66:   */     }
/* 67:80 */     return pos;
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformNewClass
 * JD-Core Version:    0.7.0.1
 */