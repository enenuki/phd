/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.ClassPool;
/*  4:   */ import javassist.CtClass;
/*  5:   */ import javassist.CtField;
/*  6:   */ import javassist.Modifier;
/*  7:   */ import javassist.NotFoundException;
/*  8:   */ import javassist.bytecode.BadBytecode;
/*  9:   */ import javassist.bytecode.CodeIterator;
/* 10:   */ import javassist.bytecode.ConstPool;
/* 11:   */ 
/* 12:   */ public class TransformReadField
/* 13:   */   extends Transformer
/* 14:   */ {
/* 15:   */   protected String fieldname;
/* 16:   */   protected CtClass fieldClass;
/* 17:   */   protected boolean isPrivate;
/* 18:   */   protected String methodClassname;
/* 19:   */   protected String methodName;
/* 20:   */   
/* 21:   */   public TransformReadField(Transformer next, CtField field, String methodClassname, String methodName)
/* 22:   */   {
/* 23:34 */     super(next);
/* 24:35 */     this.fieldClass = field.getDeclaringClass();
/* 25:36 */     this.fieldname = field.getName();
/* 26:37 */     this.methodClassname = methodClassname;
/* 27:38 */     this.methodName = methodName;
/* 28:39 */     this.isPrivate = Modifier.isPrivate(field.getModifiers());
/* 29:   */   }
/* 30:   */   
/* 31:   */   static String isField(ClassPool pool, ConstPool cp, CtClass fclass, String fname, boolean is_private, int index)
/* 32:   */   {
/* 33:44 */     if (!cp.getFieldrefName(index).equals(fname)) {
/* 34:45 */       return null;
/* 35:   */     }
/* 36:   */     try
/* 37:   */     {
/* 38:48 */       CtClass c = pool.get(cp.getFieldrefClassName(index));
/* 39:49 */       if ((c == fclass) || ((!is_private) && (isFieldInSuper(c, fclass, fname)))) {
/* 40:50 */         return cp.getFieldrefType(index);
/* 41:   */       }
/* 42:   */     }
/* 43:   */     catch (NotFoundException e) {}
/* 44:53 */     return null;
/* 45:   */   }
/* 46:   */   
/* 47:   */   static boolean isFieldInSuper(CtClass clazz, CtClass fclass, String fname)
/* 48:   */   {
/* 49:57 */     if (!clazz.subclassOf(fclass)) {
/* 50:58 */       return false;
/* 51:   */     }
/* 52:   */     try
/* 53:   */     {
/* 54:61 */       CtField f = clazz.getField(fname);
/* 55:62 */       return f.getDeclaringClass() == fclass;
/* 56:   */     }
/* 57:   */     catch (NotFoundException e) {}
/* 58:65 */     return false;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int transform(CtClass tclazz, int pos, CodeIterator iterator, ConstPool cp)
/* 62:   */     throws BadBytecode
/* 63:   */   {
/* 64:71 */     int c = iterator.byteAt(pos);
/* 65:72 */     if ((c == 180) || (c == 178))
/* 66:   */     {
/* 67:73 */       int index = iterator.u16bitAt(pos + 1);
/* 68:74 */       String typedesc = isField(tclazz.getClassPool(), cp, this.fieldClass, this.fieldname, this.isPrivate, index);
/* 69:76 */       if (typedesc != null)
/* 70:   */       {
/* 71:77 */         if (c == 178)
/* 72:   */         {
/* 73:78 */           iterator.move(pos);
/* 74:79 */           pos = iterator.insertGap(1);
/* 75:80 */           iterator.writeByte(1, pos);
/* 76:81 */           pos = iterator.next();
/* 77:   */         }
/* 78:84 */         String type = "(Ljava/lang/Object;)" + typedesc;
/* 79:85 */         int mi = cp.addClassInfo(this.methodClassname);
/* 80:86 */         int methodref = cp.addMethodrefInfo(mi, this.methodName, type);
/* 81:87 */         iterator.writeByte(184, pos);
/* 82:88 */         iterator.write16bit(methodref, pos + 1);
/* 83:89 */         return pos;
/* 84:   */       }
/* 85:   */     }
/* 86:93 */     return pos;
/* 87:   */   }
/* 88:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformReadField
 * JD-Core Version:    0.7.0.1
 */