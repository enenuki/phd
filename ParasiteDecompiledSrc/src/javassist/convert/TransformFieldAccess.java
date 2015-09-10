/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CtClass;
/*  4:   */ import javassist.CtField;
/*  5:   */ import javassist.Modifier;
/*  6:   */ import javassist.bytecode.CodeAttribute;
/*  7:   */ import javassist.bytecode.CodeIterator;
/*  8:   */ import javassist.bytecode.ConstPool;
/*  9:   */ 
/* 10:   */ public final class TransformFieldAccess
/* 11:   */   extends Transformer
/* 12:   */ {
/* 13:   */   private String newClassname;
/* 14:   */   private String newFieldname;
/* 15:   */   private String fieldname;
/* 16:   */   private CtClass fieldClass;
/* 17:   */   private boolean isPrivate;
/* 18:   */   private int newIndex;
/* 19:   */   private ConstPool constPool;
/* 20:   */   
/* 21:   */   public TransformFieldAccess(Transformer next, CtField field, String newClassname, String newFieldname)
/* 22:   */   {
/* 23:36 */     super(next);
/* 24:37 */     this.fieldClass = field.getDeclaringClass();
/* 25:38 */     this.fieldname = field.getName();
/* 26:39 */     this.isPrivate = Modifier.isPrivate(field.getModifiers());
/* 27:40 */     this.newClassname = newClassname;
/* 28:41 */     this.newFieldname = newFieldname;
/* 29:42 */     this.constPool = null;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void initialize(ConstPool cp, CodeAttribute attr)
/* 33:   */   {
/* 34:46 */     if (this.constPool != cp) {
/* 35:47 */       this.newIndex = 0;
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp)
/* 40:   */   {
/* 41:59 */     int c = iterator.byteAt(pos);
/* 42:60 */     if ((c == 180) || (c == 178) || (c == 181) || (c == 179))
/* 43:   */     {
/* 44:62 */       int index = iterator.u16bitAt(pos + 1);
/* 45:63 */       String typedesc = TransformReadField.isField(clazz.getClassPool(), cp, this.fieldClass, this.fieldname, this.isPrivate, index);
/* 46:66 */       if (typedesc != null)
/* 47:   */       {
/* 48:67 */         if (this.newIndex == 0)
/* 49:   */         {
/* 50:68 */           int nt = cp.addNameAndTypeInfo(this.newFieldname, typedesc);
/* 51:   */           
/* 52:70 */           this.newIndex = cp.addFieldrefInfo(cp.addClassInfo(this.newClassname), nt);
/* 53:   */           
/* 54:72 */           this.constPool = cp;
/* 55:   */         }
/* 56:75 */         iterator.write16bit(this.newIndex, pos + 1);
/* 57:   */       }
/* 58:   */     }
/* 59:79 */     return pos;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformFieldAccess
 * JD-Core Version:    0.7.0.1
 */