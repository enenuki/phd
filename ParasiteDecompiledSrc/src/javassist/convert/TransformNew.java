/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CannotCompileException;
/*  4:   */ import javassist.CtClass;
/*  5:   */ import javassist.bytecode.CodeAttribute;
/*  6:   */ import javassist.bytecode.CodeIterator;
/*  7:   */ import javassist.bytecode.ConstPool;
/*  8:   */ import javassist.bytecode.Descriptor;
/*  9:   */ import javassist.bytecode.StackMap;
/* 10:   */ import javassist.bytecode.StackMapTable;
/* 11:   */ 
/* 12:   */ public final class TransformNew
/* 13:   */   extends Transformer
/* 14:   */ {
/* 15:   */   private int nested;
/* 16:   */   private String classname;
/* 17:   */   private String trapClass;
/* 18:   */   private String trapMethod;
/* 19:   */   
/* 20:   */   public TransformNew(Transformer next, String classname, String trapClass, String trapMethod)
/* 21:   */   {
/* 22:28 */     super(next);
/* 23:29 */     this.classname = classname;
/* 24:30 */     this.trapClass = trapClass;
/* 25:31 */     this.trapMethod = trapMethod;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void initialize(ConstPool cp, CodeAttribute attr)
/* 29:   */   {
/* 30:35 */     this.nested = 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp)
/* 34:   */     throws CannotCompileException
/* 35:   */   {
/* 36:54 */     int c = iterator.byteAt(pos);
/* 37:55 */     if (c == 187)
/* 38:   */     {
/* 39:56 */       int index = iterator.u16bitAt(pos + 1);
/* 40:57 */       if (cp.getClassInfo(index).equals(this.classname))
/* 41:   */       {
/* 42:58 */         if (iterator.byteAt(pos + 3) != 89) {
/* 43:59 */           throw new CannotCompileException("NEW followed by no DUP was found");
/* 44:   */         }
/* 45:62 */         iterator.writeByte(0, pos);
/* 46:63 */         iterator.writeByte(0, pos + 1);
/* 47:64 */         iterator.writeByte(0, pos + 2);
/* 48:65 */         iterator.writeByte(0, pos + 3);
/* 49:66 */         this.nested += 1;
/* 50:   */         
/* 51:68 */         StackMapTable smt = (StackMapTable)iterator.get().getAttribute("StackMapTable");
/* 52:70 */         if (smt != null) {
/* 53:71 */           smt.removeNew(pos);
/* 54:   */         }
/* 55:73 */         StackMap sm = (StackMap)iterator.get().getAttribute("StackMap");
/* 56:75 */         if (sm != null) {
/* 57:76 */           sm.removeNew(pos);
/* 58:   */         }
/* 59:   */       }
/* 60:   */     }
/* 61:79 */     else if (c == 183)
/* 62:   */     {
/* 63:80 */       int index = iterator.u16bitAt(pos + 1);
/* 64:81 */       int typedesc = cp.isConstructor(this.classname, index);
/* 65:82 */       if ((typedesc != 0) && (this.nested > 0))
/* 66:   */       {
/* 67:83 */         int methodref = computeMethodref(typedesc, cp);
/* 68:84 */         iterator.writeByte(184, pos);
/* 69:85 */         iterator.write16bit(methodref, pos + 1);
/* 70:86 */         this.nested -= 1;
/* 71:   */       }
/* 72:   */     }
/* 73:90 */     return pos;
/* 74:   */   }
/* 75:   */   
/* 76:   */   private int computeMethodref(int typedesc, ConstPool cp)
/* 77:   */   {
/* 78:94 */     int classIndex = cp.addClassInfo(this.trapClass);
/* 79:95 */     int mnameIndex = cp.addUtf8Info(this.trapMethod);
/* 80:96 */     typedesc = cp.addUtf8Info(Descriptor.changeReturnType(this.classname, cp.getUtf8Info(typedesc)));
/* 81:   */     
/* 82:   */ 
/* 83:99 */     return cp.addMethodrefInfo(classIndex, cp.addNameAndTypeInfo(mnameIndex, typedesc));
/* 84:   */   }
/* 85:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformNew
 * JD-Core Version:    0.7.0.1
 */