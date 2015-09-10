/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import java.io.DataOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.Instruction;
/*  7:   */ import org.apache.bcel.generic.Visitor;
/*  8:   */ 
/*  9:   */ abstract class MarkerInstruction
/* 10:   */   extends Instruction
/* 11:   */ {
/* 12:   */   public MarkerInstruction()
/* 13:   */   {
/* 14:46 */     super((short)-1, (short)0);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v) {}
/* 18:   */   
/* 19:   */   public final int consumeStack(ConstantPoolGen cpg)
/* 20:   */   {
/* 21:66 */     return 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public final int produceStack(ConstantPoolGen cpg)
/* 25:   */   {
/* 26:77 */     return 0;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Instruction copy()
/* 30:   */   {
/* 31:87 */     return this;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public final void dump(DataOutputStream out)
/* 35:   */     throws IOException
/* 36:   */   {}
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.MarkerInstruction
 * JD-Core Version:    0.7.0.1
 */