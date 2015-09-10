/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public abstract class ArrayInstruction
/*   6:    */   extends Instruction
/*   7:    */   implements ExceptionThrower, TypedInstruction
/*   8:    */ {
/*   9:    */   ArrayInstruction() {}
/*  10:    */   
/*  11:    */   protected ArrayInstruction(short opcode)
/*  12:    */   {
/*  13: 75 */     super(opcode, (short)1);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Class[] getExceptions()
/*  17:    */   {
/*  18: 79 */     return ExceptionConstants.EXCS_ARRAY_EXCEPTION;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Type getType(ConstantPoolGen cp)
/*  22:    */   {
/*  23: 85 */     switch (this.opcode)
/*  24:    */     {
/*  25:    */     case 46: 
/*  26:    */     case 79: 
/*  27: 87 */       return Type.INT;
/*  28:    */     case 52: 
/*  29:    */     case 85: 
/*  30: 89 */       return Type.CHAR;
/*  31:    */     case 51: 
/*  32:    */     case 84: 
/*  33: 91 */       return Type.BYTE;
/*  34:    */     case 53: 
/*  35:    */     case 86: 
/*  36: 93 */       return Type.SHORT;
/*  37:    */     case 47: 
/*  38:    */     case 80: 
/*  39: 95 */       return Type.LONG;
/*  40:    */     case 49: 
/*  41:    */     case 82: 
/*  42: 97 */       return Type.DOUBLE;
/*  43:    */     case 48: 
/*  44:    */     case 81: 
/*  45: 99 */       return Type.FLOAT;
/*  46:    */     case 50: 
/*  47:    */     case 83: 
/*  48:101 */       return Type.OBJECT;
/*  49:    */     }
/*  50:103 */     throw new ClassGenException("Oops: unknown case in switch" + this.opcode);
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ArrayInstruction
 * JD-Core Version:    0.7.0.1
 */