/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ public class GOTO
/*   7:    */   extends GotoInstruction
/*   8:    */   implements VariableLengthInstruction
/*   9:    */ {
/*  10:    */   GOTO() {}
/*  11:    */   
/*  12:    */   public GOTO(InstructionHandle target)
/*  13:    */   {
/*  14: 72 */     super((short)167, target);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void dump(DataOutputStream out)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 80 */     this.index = getTargetOffset();
/*  21: 81 */     if (this.opcode == 167)
/*  22:    */     {
/*  23: 82 */       super.dump(out);
/*  24:    */     }
/*  25:    */     else
/*  26:    */     {
/*  27: 84 */       this.index = getTargetOffset();
/*  28: 85 */       out.writeByte(this.opcode);
/*  29: 86 */       out.writeInt(this.index);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected int updatePosition(int offset, int max_offset)
/*  34:    */   {
/*  35: 94 */     int i = getTargetOffset();
/*  36:    */     
/*  37: 96 */     this.position += offset;
/*  38: 98 */     if (Math.abs(i) >= 32767 - max_offset)
/*  39:    */     {
/*  40: 99 */       this.opcode = 200;
/*  41:100 */       this.length = 5;
/*  42:101 */       return 2;
/*  43:    */     }
/*  44:104 */     return 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void accept(Visitor v)
/*  48:    */   {
/*  49:116 */     v.visitVariableLengthInstruction(this);
/*  50:117 */     v.visitUnconditionalBranch(this);
/*  51:118 */     v.visitBranchInstruction(this);
/*  52:119 */     v.visitGotoInstruction(this);
/*  53:120 */     v.visitGOTO(this);
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.GOTO
 * JD-Core Version:    0.7.0.1
 */