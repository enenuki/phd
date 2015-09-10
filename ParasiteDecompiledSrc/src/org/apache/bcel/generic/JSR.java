/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ public class JSR
/*   7:    */   extends JsrInstruction
/*   8:    */   implements VariableLengthInstruction
/*   9:    */ {
/*  10:    */   JSR() {}
/*  11:    */   
/*  12:    */   public JSR(InstructionHandle target)
/*  13:    */   {
/*  14: 72 */     super((short)168, target);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void dump(DataOutputStream out)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 80 */     this.index = getTargetOffset();
/*  21: 81 */     if (this.opcode == 168)
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
/*  35: 91 */     int i = getTargetOffset();
/*  36:    */     
/*  37: 93 */     this.position += offset;
/*  38: 95 */     if (Math.abs(i) >= 32767 - max_offset)
/*  39:    */     {
/*  40: 96 */       this.opcode = 201;
/*  41: 97 */       this.length = 5;
/*  42: 98 */       return 2;
/*  43:    */     }
/*  44:101 */     return 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void accept(Visitor v)
/*  48:    */   {
/*  49:113 */     v.visitStackProducer(this);
/*  50:114 */     v.visitVariableLengthInstruction(this);
/*  51:115 */     v.visitBranchInstruction(this);
/*  52:116 */     v.visitJsrInstruction(this);
/*  53:117 */     v.visitJSR(this);
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.JSR
 * JD-Core Version:    0.7.0.1
 */