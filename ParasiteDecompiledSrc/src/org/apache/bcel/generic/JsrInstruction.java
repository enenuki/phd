/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public abstract class JsrInstruction
/*   4:    */   extends BranchInstruction
/*   5:    */   implements UnconditionalBranch, TypedInstruction, StackProducer
/*   6:    */ {
/*   7:    */   JsrInstruction(short opcode, InstructionHandle target)
/*   8:    */   {
/*   9: 67 */     super(opcode, target);
/*  10:    */   }
/*  11:    */   
/*  12:    */   JsrInstruction() {}
/*  13:    */   
/*  14:    */   public Type getType(ConstantPoolGen cp)
/*  15:    */   {
/*  16: 79 */     return new ReturnaddressType(physicalSuccessor());
/*  17:    */   }
/*  18:    */   
/*  19:    */   public InstructionHandle physicalSuccessor()
/*  20:    */   {
/*  21: 95 */     InstructionHandle ih = this.target;
/*  22: 98 */     while (ih.getPrev() != null) {
/*  23: 99 */       ih = ih.getPrev();
/*  24:    */     }
/*  25:102 */     while (ih.getInstruction() != this) {
/*  26:103 */       ih = ih.getNext();
/*  27:    */     }
/*  28:105 */     InstructionHandle toThis = ih;
/*  29:107 */     while (ih != null)
/*  30:    */     {
/*  31:108 */       ih = ih.getNext();
/*  32:109 */       if ((ih != null) && (ih.getInstruction() == this)) {
/*  33:110 */         throw new RuntimeException("physicalSuccessor() called on a shared JsrInstruction.");
/*  34:    */       }
/*  35:    */     }
/*  36:114 */     return toThis.getNext();
/*  37:    */   }
/*  38:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.JsrInstruction
 * JD-Core Version:    0.7.0.1
 */