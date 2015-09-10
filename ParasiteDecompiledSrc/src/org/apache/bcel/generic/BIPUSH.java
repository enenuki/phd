/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class BIPUSH
/*   9:    */   extends Instruction
/*  10:    */   implements ConstantPushInstruction
/*  11:    */ {
/*  12:    */   private byte b;
/*  13:    */   
/*  14:    */   BIPUSH() {}
/*  15:    */   
/*  16:    */   public BIPUSH(byte b)
/*  17:    */   {
/*  18: 80 */     super((short)16, (short)2);
/*  19: 81 */     this.b = b;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void dump(DataOutputStream out)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 88 */     super.dump(out);
/*  26: 89 */     out.writeByte(this.b);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String toString(boolean verbose)
/*  30:    */   {
/*  31: 96 */     return super.toString(verbose) + " " + this.b;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:104 */     this.length = 2;
/*  38:105 */     this.b = bytes.readByte();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Number getValue()
/*  42:    */   {
/*  43:108 */     return new Integer(this.b);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type getType(ConstantPoolGen cp)
/*  47:    */   {
/*  48:113 */     return Type.BYTE;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void accept(Visitor v)
/*  52:    */   {
/*  53:126 */     v.visitPushInstruction(this);
/*  54:127 */     v.visitStackProducer(this);
/*  55:128 */     v.visitTypedInstruction(this);
/*  56:129 */     v.visitConstantPushInstruction(this);
/*  57:130 */     v.visitBIPUSH(this);
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.BIPUSH
 * JD-Core Version:    0.7.0.1
 */