/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class ConversionInstruction
/*  4:   */   extends Instruction
/*  5:   */   implements TypedInstruction, StackProducer, StackConsumer
/*  6:   */ {
/*  7:   */   ConversionInstruction() {}
/*  8:   */   
/*  9:   */   protected ConversionInstruction(short opcode)
/* 10:   */   {
/* 11:75 */     super(opcode, (short)1);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Type getType(ConstantPoolGen cp)
/* 15:   */   {
/* 16:81 */     switch (this.opcode)
/* 17:   */     {
/* 18:   */     case 136: 
/* 19:   */     case 139: 
/* 20:   */     case 142: 
/* 21:83 */       return Type.INT;
/* 22:   */     case 134: 
/* 23:   */     case 137: 
/* 24:   */     case 144: 
/* 25:85 */       return Type.FLOAT;
/* 26:   */     case 133: 
/* 27:   */     case 140: 
/* 28:   */     case 143: 
/* 29:87 */       return Type.LONG;
/* 30:   */     case 135: 
/* 31:   */     case 138: 
/* 32:   */     case 141: 
/* 33:89 */       return Type.DOUBLE;
/* 34:   */     case 145: 
/* 35:91 */       return Type.BYTE;
/* 36:   */     case 146: 
/* 37:93 */       return Type.CHAR;
/* 38:   */     case 147: 
/* 39:95 */       return Type.SHORT;
/* 40:   */     }
/* 41:98 */     throw new ClassGenException("Unknown type " + this.opcode);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ConversionInstruction
 * JD-Core Version:    0.7.0.1
 */