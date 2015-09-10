/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class GOTO_W
/*   9:    */   extends GotoInstruction
/*  10:    */ {
/*  11:    */   GOTO_W() {}
/*  12:    */   
/*  13:    */   public GOTO_W(InstructionHandle target)
/*  14:    */   {
/*  15: 73 */     super((short)200, target);
/*  16: 74 */     this.length = 5;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void dump(DataOutputStream out)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 82 */     this.index = getTargetOffset();
/*  23: 83 */     out.writeByte(this.opcode);
/*  24: 84 */     out.writeInt(this.index);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 92 */     this.index = bytes.readInt();
/*  31: 93 */     this.length = 5;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void accept(Visitor v)
/*  35:    */   {
/*  36:105 */     v.visitUnconditionalBranch(this);
/*  37:106 */     v.visitBranchInstruction(this);
/*  38:107 */     v.visitGotoInstruction(this);
/*  39:108 */     v.visitGOTO_W(this);
/*  40:    */   }
/*  41:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.GOTO_W
 * JD-Core Version:    0.7.0.1
 */