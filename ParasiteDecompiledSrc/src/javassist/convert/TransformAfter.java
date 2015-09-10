/*  1:   */ package javassist.convert;
/*  2:   */ 
/*  3:   */ import javassist.CtMethod;
/*  4:   */ import javassist.NotFoundException;
/*  5:   */ import javassist.bytecode.BadBytecode;
/*  6:   */ import javassist.bytecode.CodeIterator;
/*  7:   */ 
/*  8:   */ public class TransformAfter
/*  9:   */   extends TransformBefore
/* 10:   */ {
/* 11:   */   public TransformAfter(Transformer next, CtMethod origMethod, CtMethod afterMethod)
/* 12:   */     throws NotFoundException
/* 13:   */   {
/* 14:27 */     super(next, origMethod, afterMethod);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected int match2(int pos, CodeIterator iterator)
/* 18:   */     throws BadBytecode
/* 19:   */   {
/* 20:31 */     iterator.move(pos);
/* 21:32 */     iterator.insert(this.saveCode);
/* 22:33 */     iterator.insert(this.loadCode);
/* 23:34 */     int p = iterator.insertGap(3);
/* 24:35 */     iterator.setMark(p);
/* 25:36 */     iterator.insert(this.loadCode);
/* 26:37 */     pos = iterator.next();
/* 27:38 */     p = iterator.getMark();
/* 28:39 */     iterator.writeByte(iterator.byteAt(pos), p);
/* 29:40 */     iterator.write16bit(iterator.u16bitAt(pos + 1), p + 1);
/* 30:41 */     iterator.writeByte(184, pos);
/* 31:42 */     iterator.write16bit(this.newIndex, pos + 1);
/* 32:43 */     iterator.move(p);
/* 33:44 */     return iterator.next();
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformAfter
 * JD-Core Version:    0.7.0.1
 */