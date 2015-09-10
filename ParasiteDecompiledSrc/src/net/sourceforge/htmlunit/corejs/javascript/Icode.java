/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ abstract class Icode
/*   4:    */ {
/*   5:    */   static final int Icode_DUP = -1;
/*   6:    */   static final int Icode_DUP2 = -2;
/*   7:    */   static final int Icode_SWAP = -3;
/*   8:    */   static final int Icode_POP = -4;
/*   9:    */   static final int Icode_POP_RESULT = -5;
/*  10:    */   static final int Icode_IFEQ_POP = -6;
/*  11:    */   static final int Icode_VAR_INC_DEC = -7;
/*  12:    */   static final int Icode_NAME_INC_DEC = -8;
/*  13:    */   static final int Icode_PROP_INC_DEC = -9;
/*  14:    */   static final int Icode_ELEM_INC_DEC = -10;
/*  15:    */   static final int Icode_REF_INC_DEC = -11;
/*  16:    */   static final int Icode_SCOPE_LOAD = -12;
/*  17:    */   static final int Icode_SCOPE_SAVE = -13;
/*  18:    */   static final int Icode_TYPEOFNAME = -14;
/*  19:    */   static final int Icode_NAME_AND_THIS = -15;
/*  20:    */   static final int Icode_PROP_AND_THIS = -16;
/*  21:    */   static final int Icode_ELEM_AND_THIS = -17;
/*  22:    */   static final int Icode_VALUE_AND_THIS = -18;
/*  23:    */   static final int Icode_CLOSURE_EXPR = -19;
/*  24:    */   static final int Icode_CLOSURE_STMT = -20;
/*  25:    */   static final int Icode_CALLSPECIAL = -21;
/*  26:    */   static final int Icode_RETUNDEF = -22;
/*  27:    */   static final int Icode_GOSUB = -23;
/*  28:    */   static final int Icode_STARTSUB = -24;
/*  29:    */   static final int Icode_RETSUB = -25;
/*  30:    */   static final int Icode_LINE = -26;
/*  31:    */   static final int Icode_SHORTNUMBER = -27;
/*  32:    */   static final int Icode_INTNUMBER = -28;
/*  33:    */   static final int Icode_LITERAL_NEW = -29;
/*  34:    */   static final int Icode_LITERAL_SET = -30;
/*  35:    */   static final int Icode_SPARE_ARRAYLIT = -31;
/*  36:    */   static final int Icode_REG_IND_C0 = -32;
/*  37:    */   static final int Icode_REG_IND_C1 = -33;
/*  38:    */   static final int Icode_REG_IND_C2 = -34;
/*  39:    */   static final int Icode_REG_IND_C3 = -35;
/*  40:    */   static final int Icode_REG_IND_C4 = -36;
/*  41:    */   static final int Icode_REG_IND_C5 = -37;
/*  42:    */   static final int Icode_REG_IND1 = -38;
/*  43:    */   static final int Icode_REG_IND2 = -39;
/*  44:    */   static final int Icode_REG_IND4 = -40;
/*  45:    */   static final int Icode_REG_STR_C0 = -41;
/*  46:    */   static final int Icode_REG_STR_C1 = -42;
/*  47:    */   static final int Icode_REG_STR_C2 = -43;
/*  48:    */   static final int Icode_REG_STR_C3 = -44;
/*  49:    */   static final int Icode_REG_STR1 = -45;
/*  50:    */   static final int Icode_REG_STR2 = -46;
/*  51:    */   static final int Icode_REG_STR4 = -47;
/*  52:    */   static final int Icode_GETVAR1 = -48;
/*  53:    */   static final int Icode_SETVAR1 = -49;
/*  54:    */   static final int Icode_UNDEF = -50;
/*  55:    */   static final int Icode_ZERO = -51;
/*  56:    */   static final int Icode_ONE = -52;
/*  57:    */   static final int Icode_ENTERDQ = -53;
/*  58:    */   static final int Icode_LEAVEDQ = -54;
/*  59:    */   static final int Icode_TAIL_CALL = -55;
/*  60:    */   static final int Icode_LOCAL_CLEAR = -56;
/*  61:    */   static final int Icode_LITERAL_GETTER = -57;
/*  62:    */   static final int Icode_LITERAL_SETTER = -58;
/*  63:    */   static final int Icode_SETCONST = -59;
/*  64:    */   static final int Icode_SETCONSTVAR = -60;
/*  65:    */   static final int Icode_SETCONSTVAR1 = -61;
/*  66:    */   static final int Icode_GENERATOR = -62;
/*  67:    */   static final int Icode_GENERATOR_END = -63;
/*  68:    */   static final int Icode_DEBUGGER = -64;
/*  69:    */   static final int MIN_ICODE = -64;
/*  70:    */   
/*  71:    */   static String bytecodeName(int bytecode)
/*  72:    */   {
/*  73:181 */     if (!validBytecode(bytecode)) {
/*  74:182 */       throw new IllegalArgumentException(String.valueOf(bytecode));
/*  75:    */     }
/*  76:186 */     return String.valueOf(bytecode);
/*  77:    */   }
/*  78:    */   
/*  79:    */   static boolean validIcode(int icode)
/*  80:    */   {
/*  81:266 */     return (-64 <= icode) && (icode <= -1);
/*  82:    */   }
/*  83:    */   
/*  84:    */   static boolean validTokenCode(int token)
/*  85:    */   {
/*  86:271 */     return (2 <= token) && (token <= 80);
/*  87:    */   }
/*  88:    */   
/*  89:    */   static boolean validBytecode(int bytecode)
/*  90:    */   {
/*  91:277 */     return (validIcode(bytecode)) || (validTokenCode(bytecode));
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Icode
 * JD-Core Version:    0.7.0.1
 */