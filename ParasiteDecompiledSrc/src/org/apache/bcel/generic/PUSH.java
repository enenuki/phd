/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public final class PUSH
/*   4:    */   implements CompoundInstruction, VariableLengthInstruction, InstructionConstants
/*   5:    */ {
/*   6:    */   private Instruction instruction;
/*   7:    */   
/*   8:    */   public PUSH(ConstantPoolGen cp, int value)
/*   9:    */   {
/*  10: 78 */     if ((value >= -1) && (value <= 5)) {
/*  11: 79 */       this.instruction = InstructionConstants.INSTRUCTIONS[(3 + value)];
/*  12: 80 */     } else if ((value >= -128) && (value <= 127)) {
/*  13: 81 */       this.instruction = new BIPUSH((byte)value);
/*  14: 82 */     } else if ((value >= -32768) && (value <= 32767)) {
/*  15: 83 */       this.instruction = new SIPUSH((short)value);
/*  16:    */     } else {
/*  17: 85 */       this.instruction = new LDC(cp.addInteger(value));
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public PUSH(ConstantPoolGen cp, boolean value)
/*  22:    */   {
/*  23: 93 */     this.instruction = InstructionConstants.INSTRUCTIONS[(3 + 0)];
/*  24:    */   }
/*  25:    */   
/*  26:    */   public PUSH(ConstantPoolGen cp, float value)
/*  27:    */   {
/*  28:101 */     if (value == 0.0D) {
/*  29:102 */       this.instruction = InstructionConstants.FCONST_0;
/*  30:103 */     } else if (value == 1.0D) {
/*  31:104 */       this.instruction = InstructionConstants.FCONST_1;
/*  32:105 */     } else if (value == 2.0D) {
/*  33:106 */       this.instruction = InstructionConstants.FCONST_2;
/*  34:    */     } else {
/*  35:108 */       this.instruction = new LDC(cp.addFloat(value));
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PUSH(ConstantPoolGen cp, long value)
/*  40:    */   {
/*  41:116 */     if (value == 0L) {
/*  42:117 */       this.instruction = InstructionConstants.LCONST_0;
/*  43:118 */     } else if (value == 1L) {
/*  44:119 */       this.instruction = InstructionConstants.LCONST_1;
/*  45:    */     } else {
/*  46:121 */       this.instruction = new LDC2_W(cp.addLong(value));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PUSH(ConstantPoolGen cp, double value)
/*  51:    */   {
/*  52:129 */     if (value == 0.0D) {
/*  53:130 */       this.instruction = InstructionConstants.DCONST_0;
/*  54:131 */     } else if (value == 1.0D) {
/*  55:132 */       this.instruction = InstructionConstants.DCONST_1;
/*  56:    */     } else {
/*  57:134 */       this.instruction = new LDC2_W(cp.addDouble(value));
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public PUSH(ConstantPoolGen cp, String value)
/*  62:    */   {
/*  63:142 */     if (value == null) {
/*  64:143 */       this.instruction = InstructionConstants.ACONST_NULL;
/*  65:    */     } else {
/*  66:145 */       this.instruction = new LDC(cp.addString(value));
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public PUSH(ConstantPoolGen cp, Number value)
/*  71:    */   {
/*  72:153 */     if (((value instanceof Integer)) || ((value instanceof Short)) || ((value instanceof Byte))) {
/*  73:154 */       this.instruction = new PUSH(cp, value.intValue()).instruction;
/*  74:155 */     } else if ((value instanceof Double)) {
/*  75:156 */       this.instruction = new PUSH(cp, value.doubleValue()).instruction;
/*  76:157 */     } else if ((value instanceof Float)) {
/*  77:158 */       this.instruction = new PUSH(cp, value.floatValue()).instruction;
/*  78:159 */     } else if ((value instanceof Long)) {
/*  79:160 */       this.instruction = new PUSH(cp, value.longValue()).instruction;
/*  80:    */     } else {
/*  81:162 */       throw new ClassGenException("What's this: " + value);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public PUSH(ConstantPoolGen cp, Character value)
/*  86:    */   {
/*  87:170 */     this(cp, value.charValue());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public PUSH(ConstantPoolGen cp, Boolean value)
/*  91:    */   {
/*  92:178 */     this(cp, value.booleanValue());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final InstructionList getInstructionList()
/*  96:    */   {
/*  97:182 */     return new InstructionList(this.instruction);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final Instruction getInstruction()
/* 101:    */   {
/* 102:186 */     return this.instruction;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String toString()
/* 106:    */   {
/* 107:193 */     return this.instruction.toString() + " (PUSH)";
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.PUSH
 * JD-Core Version:    0.7.0.1
 */