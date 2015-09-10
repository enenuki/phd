/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public final class TargetLostException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private InstructionHandle[] targets;
/*  7:   */   
/*  8:   */   TargetLostException(InstructionHandle[] t, String mesg)
/*  9:   */   {
/* 10:92 */     super(mesg);
/* 11:93 */     this.targets = t;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public InstructionHandle[] getTargets()
/* 15:   */   {
/* 16:99 */     return this.targets;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.TargetLostException
 * JD-Core Version:    0.7.0.1
 */