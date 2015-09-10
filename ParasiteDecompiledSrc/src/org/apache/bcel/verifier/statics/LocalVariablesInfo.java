/*   1:    */ package org.apache.bcel.verifier.statics;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.Type;
/*   4:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*   5:    */ import org.apache.bcel.verifier.exc.LocalVariableInfoInconsistentException;
/*   6:    */ 
/*   7:    */ public class LocalVariablesInfo
/*   8:    */ {
/*   9:    */   private LocalVariableInfo[] localVariableInfos;
/*  10: 78 */   private IntList instruction_offsets = new IntList();
/*  11:    */   
/*  12:    */   LocalVariablesInfo(int max_locals)
/*  13:    */   {
/*  14: 82 */     this.localVariableInfos = new LocalVariableInfo[max_locals];
/*  15: 83 */     for (int i = 0; i < max_locals; i++) {
/*  16: 84 */       this.localVariableInfos[i] = new LocalVariableInfo();
/*  17:    */     }
/*  18:    */   }
/*  19:    */   
/*  20:    */   public LocalVariableInfo getLocalVariableInfo(int slot)
/*  21:    */   {
/*  22: 90 */     if ((slot < 0) || (slot >= this.localVariableInfos.length)) {
/*  23: 91 */       throw new AssertionViolatedException("Slot number for local variable information out of range.");
/*  24:    */     }
/*  25: 93 */     return this.localVariableInfos[slot];
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void add(int slot, String name, int startpc, int length, Type t)
/*  29:    */     throws LocalVariableInfoInconsistentException
/*  30:    */   {
/*  31:105 */     if ((slot < 0) || (slot >= this.localVariableInfos.length)) {
/*  32:106 */       throw new AssertionViolatedException("Slot number for local variable information out of range.");
/*  33:    */     }
/*  34:109 */     this.localVariableInfos[slot].add(name, startpc, length, t);
/*  35:110 */     if (t == Type.LONG) {
/*  36:110 */       this.localVariableInfos[(slot + 1)].add(name, startpc, length, LONG_Upper.theInstance());
/*  37:    */     }
/*  38:111 */     if (t == Type.DOUBLE) {
/*  39:111 */       this.localVariableInfos[(slot + 1)].add(name, startpc, length, DOUBLE_Upper.theInstance());
/*  40:    */     }
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.LocalVariablesInfo
 * JD-Core Version:    0.7.0.1
 */