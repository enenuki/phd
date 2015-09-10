/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ class ExceptionTableEntry
/*  4:   */ {
/*  5:   */   int startPc;
/*  6:   */   int endPc;
/*  7:   */   int handlerPc;
/*  8:   */   int catchType;
/*  9:   */   
/* 10:   */   ExceptionTableEntry(int start, int end, int handle, int type)
/* 11:   */   {
/* 12:31 */     this.startPc = start;
/* 13:32 */     this.endPc = end;
/* 14:33 */     this.handlerPc = handle;
/* 15:34 */     this.catchType = type;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ExceptionTableEntry
 * JD-Core Version:    0.7.0.1
 */