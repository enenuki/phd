/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public abstract class ReturnInstruction
/*   6:    */   extends Instruction
/*   7:    */   implements ExceptionThrower, TypedInstruction, StackConsumer
/*   8:    */ {
/*   9:    */   ReturnInstruction() {}
/*  10:    */   
/*  11:    */   protected ReturnInstruction(short opcode)
/*  12:    */   {
/*  13: 77 */     super(opcode, (short)1);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Type getType()
/*  17:    */   {
/*  18: 81 */     switch (this.opcode)
/*  19:    */     {
/*  20:    */     case 172: 
/*  21: 82 */       return Type.INT;
/*  22:    */     case 173: 
/*  23: 83 */       return Type.LONG;
/*  24:    */     case 174: 
/*  25: 84 */       return Type.FLOAT;
/*  26:    */     case 175: 
/*  27: 85 */       return Type.DOUBLE;
/*  28:    */     case 176: 
/*  29: 86 */       return Type.OBJECT;
/*  30:    */     case 177: 
/*  31: 87 */       return Type.VOID;
/*  32:    */     }
/*  33: 90 */     throw new ClassGenException("Unknown type " + this.opcode);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Class[] getExceptions()
/*  37:    */   {
/*  38: 95 */     return new Class[] { ExceptionConstants.ILLEGAL_MONITOR_STATE };
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Type getType(ConstantPoolGen cp)
/*  42:    */   {
/*  43:101 */     return getType();
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ReturnInstruction
 * JD-Core Version:    0.7.0.1
 */