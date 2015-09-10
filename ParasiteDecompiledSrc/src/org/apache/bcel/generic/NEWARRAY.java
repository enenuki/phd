/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.ExceptionConstants;
/*   7:    */ import org.apache.bcel.util.ByteSequence;
/*   8:    */ 
/*   9:    */ public class NEWARRAY
/*  10:    */   extends Instruction
/*  11:    */   implements AllocationInstruction, ExceptionThrower, StackProducer
/*  12:    */ {
/*  13:    */   private byte type;
/*  14:    */   
/*  15:    */   NEWARRAY() {}
/*  16:    */   
/*  17:    */   public NEWARRAY(byte type)
/*  18:    */   {
/*  19: 78 */     super((short)188, (short)2);
/*  20: 79 */     this.type = type;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public NEWARRAY(BasicType type)
/*  24:    */   {
/*  25: 83 */     this(type.getType());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void dump(DataOutputStream out)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 91 */     out.writeByte(this.opcode);
/*  32: 92 */     out.writeByte(this.type);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final byte getTypecode()
/*  36:    */   {
/*  37: 98 */     return this.type;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final Type getType()
/*  41:    */   {
/*  42:104 */     return new ArrayType(BasicType.getType(this.type), 1);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String toString(boolean verbose)
/*  46:    */   {
/*  47:111 */     return super.toString(verbose) + " " + org.apache.bcel.Constants.TYPE_NAMES[this.type];
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:118 */     this.type = bytes.readByte();
/*  54:119 */     this.length = 2;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Class[] getExceptions()
/*  58:    */   {
/*  59:123 */     return new Class[] { ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION };
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void accept(Visitor v)
/*  63:    */   {
/*  64:136 */     v.visitAllocationInstruction(this);
/*  65:137 */     v.visitExceptionThrower(this);
/*  66:138 */     v.visitStackProducer(this);
/*  67:139 */     v.visitNEWARRAY(this);
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.NEWARRAY
 * JD-Core Version:    0.7.0.1
 */