/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.ExceptionConstants;
/*  4:   */ 
/*  5:   */ public class MONITORENTER
/*  6:   */   extends Instruction
/*  7:   */   implements ExceptionThrower, StackConsumer
/*  8:   */ {
/*  9:   */   public MONITORENTER()
/* 10:   */   {
/* 11:67 */     super((short)194, (short)1);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class[] getExceptions()
/* 15:   */   {
/* 16:71 */     return new Class[] { ExceptionConstants.NULL_POINTER_EXCEPTION };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void accept(Visitor v)
/* 20:   */   {
/* 21:84 */     v.visitExceptionThrower(this);
/* 22:85 */     v.visitStackConsumer(this);
/* 23:86 */     v.visitMONITORENTER(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.MONITORENTER
 * JD-Core Version:    0.7.0.1
 */