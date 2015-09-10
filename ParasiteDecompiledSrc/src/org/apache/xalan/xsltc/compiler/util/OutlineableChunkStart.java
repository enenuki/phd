/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.Instruction;
/*  4:   */ 
/*  5:   */ class OutlineableChunkStart
/*  6:   */   extends MarkerInstruction
/*  7:   */ {
/*  8:45 */   public static final Instruction OUTLINEABLECHUNKSTART = new OutlineableChunkStart();
/*  9:   */   
/* 10:   */   public String getName()
/* 11:   */   {
/* 12:61 */     return OutlineableChunkStart.class.getName();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:69 */     return getName();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString(boolean verbose)
/* 21:   */   {
/* 22:77 */     return getName();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.OutlineableChunkStart
 * JD-Core Version:    0.7.0.1
 */