/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.Instruction;
/*  4:   */ 
/*  5:   */ class OutlineableChunkEnd
/*  6:   */   extends MarkerInstruction
/*  7:   */ {
/*  8:36 */   public static final Instruction OUTLINEABLECHUNKEND = new OutlineableChunkEnd();
/*  9:   */   
/* 10:   */   public String getName()
/* 11:   */   {
/* 12:52 */     return OutlineableChunkEnd.class.getName();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:60 */     return getName();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString(boolean verbose)
/* 21:   */   {
/* 22:68 */     return getName();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.OutlineableChunkEnd
 * JD-Core Version:    0.7.0.1
 */