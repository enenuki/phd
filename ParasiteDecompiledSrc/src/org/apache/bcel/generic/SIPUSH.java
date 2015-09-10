/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class SIPUSH
/*   9:    */   extends Instruction
/*  10:    */   implements ConstantPushInstruction
/*  11:    */ {
/*  12:    */   private short b;
/*  13:    */   
/*  14:    */   SIPUSH() {}
/*  15:    */   
/*  16:    */   public SIPUSH(short b)
/*  17:    */   {
/*  18: 77 */     super((short)17, (short)3);
/*  19: 78 */     this.b = b;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void dump(DataOutputStream out)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 85 */     super.dump(out);
/*  26: 86 */     out.writeShort(this.b);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String toString(boolean verbose)
/*  30:    */   {
/*  31: 93 */     return super.toString(verbose) + " " + this.b;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:101 */     this.length = 3;
/*  38:102 */     this.b = bytes.readShort();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Number getValue()
/*  42:    */   {
/*  43:105 */     return new Integer(this.b);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type getType(ConstantPoolGen cp)
/*  47:    */   {
/*  48:110 */     return Type.SHORT;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void accept(Visitor v)
/*  52:    */   {
/*  53:123 */     v.visitPushInstruction(this);
/*  54:124 */     v.visitStackProducer(this);
/*  55:125 */     v.visitTypedInstruction(this);
/*  56:126 */     v.visitConstantPushInstruction(this);
/*  57:127 */     v.visitSIPUSH(this);
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.SIPUSH
 * JD-Core Version:    0.7.0.1
 */