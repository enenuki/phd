/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.ExceptionConstants;
/*  4:   */ 
/*  5:   */ public class ARRAYLENGTH
/*  6:   */   extends Instruction
/*  7:   */   implements ExceptionThrower, StackProducer
/*  8:   */ {
/*  9:   */   public ARRAYLENGTH()
/* 10:   */   {
/* 11:69 */     super((short)190, (short)1);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class[] getExceptions()
/* 15:   */   {
/* 16:75 */     return new Class[] { ExceptionConstants.NULL_POINTER_EXCEPTION };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void accept(Visitor v)
/* 20:   */   {
/* 21:88 */     v.visitExceptionThrower(this);
/* 22:89 */     v.visitStackProducer(this);
/* 23:90 */     v.visitARRAYLENGTH(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ARRAYLENGTH
 * JD-Core Version:    0.7.0.1
 */