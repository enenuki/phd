/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CtClass;
/*  4:   */ import javassist.CtField;
/*  5:   */ import javassist.bytecode.BadBytecode;
/*  6:   */ import javassist.bytecode.CodeAttribute;
/*  7:   */ import javassist.bytecode.CodeIterator;
/*  8:   */ import javassist.bytecode.ConstPool;
/*  9:   */ 
/* 10:   */ public final class TransformWriteField
/* 11:   */   extends TransformReadField
/* 12:   */ {
/* 13:   */   public TransformWriteField(Transformer next, CtField field, String methodClassname, String methodName)
/* 14:   */   {
/* 15:26 */     super(next, field, methodClassname, methodName);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int transform(CtClass tclazz, int pos, CodeIterator iterator, ConstPool cp)
/* 19:   */     throws BadBytecode
/* 20:   */   {
/* 21:32 */     int c = iterator.byteAt(pos);
/* 22:33 */     if ((c == 181) || (c == 179))
/* 23:   */     {
/* 24:34 */       int index = iterator.u16bitAt(pos + 1);
/* 25:35 */       String typedesc = isField(tclazz.getClassPool(), cp, this.fieldClass, this.fieldname, this.isPrivate, index);
/* 26:37 */       if (typedesc != null)
/* 27:   */       {
/* 28:38 */         if (c == 179)
/* 29:   */         {
/* 30:39 */           CodeAttribute ca = iterator.get();
/* 31:40 */           iterator.move(pos);
/* 32:41 */           char c0 = typedesc.charAt(0);
/* 33:42 */           if ((c0 == 'J') || (c0 == 'D'))
/* 34:   */           {
/* 35:44 */             pos = iterator.insertGap(3);
/* 36:45 */             iterator.writeByte(1, pos);
/* 37:46 */             iterator.writeByte(91, pos + 1);
/* 38:47 */             iterator.writeByte(87, pos + 2);
/* 39:48 */             ca.setMaxStack(ca.getMaxStack() + 2);
/* 40:   */           }
/* 41:   */           else
/* 42:   */           {
/* 43:52 */             pos = iterator.insertGap(2);
/* 44:53 */             iterator.writeByte(1, pos);
/* 45:54 */             iterator.writeByte(95, pos + 1);
/* 46:55 */             ca.setMaxStack(ca.getMaxStack() + 1);
/* 47:   */           }
/* 48:58 */           pos = iterator.next();
/* 49:   */         }
/* 50:61 */         int mi = cp.addClassInfo(this.methodClassname);
/* 51:62 */         String type = "(Ljava/lang/Object;" + typedesc + ")V";
/* 52:63 */         int methodref = cp.addMethodrefInfo(mi, this.methodName, type);
/* 53:64 */         iterator.writeByte(184, pos);
/* 54:65 */         iterator.write16bit(methodref, pos + 1);
/* 55:   */       }
/* 56:   */     }
/* 57:69 */     return pos;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformWriteField
 * JD-Core Version:    0.7.0.1
 */