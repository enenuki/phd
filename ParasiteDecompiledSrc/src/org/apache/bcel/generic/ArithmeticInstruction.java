/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public abstract class ArithmeticInstruction
/*   4:    */   extends Instruction
/*   5:    */   implements TypedInstruction, StackProducer, StackConsumer
/*   6:    */ {
/*   7:    */   ArithmeticInstruction() {}
/*   8:    */   
/*   9:    */   protected ArithmeticInstruction(short opcode)
/*  10:    */   {
/*  11: 75 */     super(opcode, (short)1);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Type getType(ConstantPoolGen cp)
/*  15:    */   {
/*  16: 81 */     switch (this.opcode)
/*  17:    */     {
/*  18:    */     case 99: 
/*  19:    */     case 103: 
/*  20:    */     case 107: 
/*  21:    */     case 111: 
/*  22:    */     case 115: 
/*  23:    */     case 119: 
/*  24: 84 */       return Type.DOUBLE;
/*  25:    */     case 98: 
/*  26:    */     case 102: 
/*  27:    */     case 106: 
/*  28:    */     case 110: 
/*  29:    */     case 114: 
/*  30:    */     case 118: 
/*  31: 88 */       return Type.FLOAT;
/*  32:    */     case 96: 
/*  33:    */     case 100: 
/*  34:    */     case 104: 
/*  35:    */     case 108: 
/*  36:    */     case 112: 
/*  37:    */     case 116: 
/*  38:    */     case 120: 
/*  39:    */     case 122: 
/*  40:    */     case 124: 
/*  41:    */     case 126: 
/*  42:    */     case 128: 
/*  43:    */     case 130: 
/*  44: 94 */       return Type.INT;
/*  45:    */     case 97: 
/*  46:    */     case 101: 
/*  47:    */     case 105: 
/*  48:    */     case 109: 
/*  49:    */     case 113: 
/*  50:    */     case 117: 
/*  51:    */     case 121: 
/*  52:    */     case 123: 
/*  53:    */     case 125: 
/*  54:    */     case 127: 
/*  55:    */     case 129: 
/*  56:    */     case 131: 
/*  57:100 */       return Type.LONG;
/*  58:    */     }
/*  59:103 */     throw new ClassGenException("Unknown type " + this.opcode);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ArithmeticInstruction
 * JD-Core Version:    0.7.0.1
 */