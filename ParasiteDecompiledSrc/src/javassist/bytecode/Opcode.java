/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ public abstract interface Opcode
/*   4:    */ {
/*   5:    */   public static final int AALOAD = 50;
/*   6:    */   public static final int AASTORE = 83;
/*   7:    */   public static final int ACONST_NULL = 1;
/*   8:    */   public static final int ALOAD = 25;
/*   9:    */   public static final int ALOAD_0 = 42;
/*  10:    */   public static final int ALOAD_1 = 43;
/*  11:    */   public static final int ALOAD_2 = 44;
/*  12:    */   public static final int ALOAD_3 = 45;
/*  13:    */   public static final int ANEWARRAY = 189;
/*  14:    */   public static final int ARETURN = 176;
/*  15:    */   public static final int ARRAYLENGTH = 190;
/*  16:    */   public static final int ASTORE = 58;
/*  17:    */   public static final int ASTORE_0 = 75;
/*  18:    */   public static final int ASTORE_1 = 76;
/*  19:    */   public static final int ASTORE_2 = 77;
/*  20:    */   public static final int ASTORE_3 = 78;
/*  21:    */   public static final int ATHROW = 191;
/*  22:    */   public static final int BALOAD = 51;
/*  23:    */   public static final int BASTORE = 84;
/*  24:    */   public static final int BIPUSH = 16;
/*  25:    */   public static final int CALOAD = 52;
/*  26:    */   public static final int CASTORE = 85;
/*  27:    */   public static final int CHECKCAST = 192;
/*  28:    */   public static final int D2F = 144;
/*  29:    */   public static final int D2I = 142;
/*  30:    */   public static final int D2L = 143;
/*  31:    */   public static final int DADD = 99;
/*  32:    */   public static final int DALOAD = 49;
/*  33:    */   public static final int DASTORE = 82;
/*  34:    */   public static final int DCMPG = 152;
/*  35:    */   public static final int DCMPL = 151;
/*  36:    */   public static final int DCONST_0 = 14;
/*  37:    */   public static final int DCONST_1 = 15;
/*  38:    */   public static final int DDIV = 111;
/*  39:    */   public static final int DLOAD = 24;
/*  40:    */   public static final int DLOAD_0 = 38;
/*  41:    */   public static final int DLOAD_1 = 39;
/*  42:    */   public static final int DLOAD_2 = 40;
/*  43:    */   public static final int DLOAD_3 = 41;
/*  44:    */   public static final int DMUL = 107;
/*  45:    */   public static final int DNEG = 119;
/*  46:    */   public static final int DREM = 115;
/*  47:    */   public static final int DRETURN = 175;
/*  48:    */   public static final int DSTORE = 57;
/*  49:    */   public static final int DSTORE_0 = 71;
/*  50:    */   public static final int DSTORE_1 = 72;
/*  51:    */   public static final int DSTORE_2 = 73;
/*  52:    */   public static final int DSTORE_3 = 74;
/*  53:    */   public static final int DSUB = 103;
/*  54:    */   public static final int DUP = 89;
/*  55:    */   public static final int DUP2 = 92;
/*  56:    */   public static final int DUP2_X1 = 93;
/*  57:    */   public static final int DUP2_X2 = 94;
/*  58:    */   public static final int DUP_X1 = 90;
/*  59:    */   public static final int DUP_X2 = 91;
/*  60:    */   public static final int F2D = 141;
/*  61:    */   public static final int F2I = 139;
/*  62:    */   public static final int F2L = 140;
/*  63:    */   public static final int FADD = 98;
/*  64:    */   public static final int FALOAD = 48;
/*  65:    */   public static final int FASTORE = 81;
/*  66:    */   public static final int FCMPG = 150;
/*  67:    */   public static final int FCMPL = 149;
/*  68:    */   public static final int FCONST_0 = 11;
/*  69:    */   public static final int FCONST_1 = 12;
/*  70:    */   public static final int FCONST_2 = 13;
/*  71:    */   public static final int FDIV = 110;
/*  72:    */   public static final int FLOAD = 23;
/*  73:    */   public static final int FLOAD_0 = 34;
/*  74:    */   public static final int FLOAD_1 = 35;
/*  75:    */   public static final int FLOAD_2 = 36;
/*  76:    */   public static final int FLOAD_3 = 37;
/*  77:    */   public static final int FMUL = 106;
/*  78:    */   public static final int FNEG = 118;
/*  79:    */   public static final int FREM = 114;
/*  80:    */   public static final int FRETURN = 174;
/*  81:    */   public static final int FSTORE = 56;
/*  82:    */   public static final int FSTORE_0 = 67;
/*  83:    */   public static final int FSTORE_1 = 68;
/*  84:    */   public static final int FSTORE_2 = 69;
/*  85:    */   public static final int FSTORE_3 = 70;
/*  86:    */   public static final int FSUB = 102;
/*  87:    */   public static final int GETFIELD = 180;
/*  88:    */   public static final int GETSTATIC = 178;
/*  89:    */   public static final int GOTO = 167;
/*  90:    */   public static final int GOTO_W = 200;
/*  91:    */   public static final int I2B = 145;
/*  92:    */   public static final int I2C = 146;
/*  93:    */   public static final int I2D = 135;
/*  94:    */   public static final int I2F = 134;
/*  95:    */   public static final int I2L = 133;
/*  96:    */   public static final int I2S = 147;
/*  97:    */   public static final int IADD = 96;
/*  98:    */   public static final int IALOAD = 46;
/*  99:    */   public static final int IAND = 126;
/* 100:    */   public static final int IASTORE = 79;
/* 101:    */   public static final int ICONST_0 = 3;
/* 102:    */   public static final int ICONST_1 = 4;
/* 103:    */   public static final int ICONST_2 = 5;
/* 104:    */   public static final int ICONST_3 = 6;
/* 105:    */   public static final int ICONST_4 = 7;
/* 106:    */   public static final int ICONST_5 = 8;
/* 107:    */   public static final int ICONST_M1 = 2;
/* 108:    */   public static final int IDIV = 108;
/* 109:    */   public static final int IFEQ = 153;
/* 110:    */   public static final int IFGE = 156;
/* 111:    */   public static final int IFGT = 157;
/* 112:    */   public static final int IFLE = 158;
/* 113:    */   public static final int IFLT = 155;
/* 114:    */   public static final int IFNE = 154;
/* 115:    */   public static final int IFNONNULL = 199;
/* 116:    */   public static final int IFNULL = 198;
/* 117:    */   public static final int IF_ACMPEQ = 165;
/* 118:    */   public static final int IF_ACMPNE = 166;
/* 119:    */   public static final int IF_ICMPEQ = 159;
/* 120:    */   public static final int IF_ICMPGE = 162;
/* 121:    */   public static final int IF_ICMPGT = 163;
/* 122:    */   public static final int IF_ICMPLE = 164;
/* 123:    */   public static final int IF_ICMPLT = 161;
/* 124:    */   public static final int IF_ICMPNE = 160;
/* 125:    */   public static final int IINC = 132;
/* 126:    */   public static final int ILOAD = 21;
/* 127:    */   public static final int ILOAD_0 = 26;
/* 128:    */   public static final int ILOAD_1 = 27;
/* 129:    */   public static final int ILOAD_2 = 28;
/* 130:    */   public static final int ILOAD_3 = 29;
/* 131:    */   public static final int IMUL = 104;
/* 132:    */   public static final int INEG = 116;
/* 133:    */   public static final int INSTANCEOF = 193;
/* 134:    */   public static final int INVOKEINTERFACE = 185;
/* 135:    */   public static final int INVOKESPECIAL = 183;
/* 136:    */   public static final int INVOKESTATIC = 184;
/* 137:    */   public static final int INVOKEVIRTUAL = 182;
/* 138:    */   public static final int IOR = 128;
/* 139:    */   public static final int IREM = 112;
/* 140:    */   public static final int IRETURN = 172;
/* 141:    */   public static final int ISHL = 120;
/* 142:    */   public static final int ISHR = 122;
/* 143:    */   public static final int ISTORE = 54;
/* 144:    */   public static final int ISTORE_0 = 59;
/* 145:    */   public static final int ISTORE_1 = 60;
/* 146:    */   public static final int ISTORE_2 = 61;
/* 147:    */   public static final int ISTORE_3 = 62;
/* 148:    */   public static final int ISUB = 100;
/* 149:    */   public static final int IUSHR = 124;
/* 150:    */   public static final int IXOR = 130;
/* 151:    */   public static final int JSR = 168;
/* 152:    */   public static final int JSR_W = 201;
/* 153:    */   public static final int L2D = 138;
/* 154:    */   public static final int L2F = 137;
/* 155:    */   public static final int L2I = 136;
/* 156:    */   public static final int LADD = 97;
/* 157:    */   public static final int LALOAD = 47;
/* 158:    */   public static final int LAND = 127;
/* 159:    */   public static final int LASTORE = 80;
/* 160:    */   public static final int LCMP = 148;
/* 161:    */   public static final int LCONST_0 = 9;
/* 162:    */   public static final int LCONST_1 = 10;
/* 163:    */   public static final int LDC = 18;
/* 164:    */   public static final int LDC2_W = 20;
/* 165:    */   public static final int LDC_W = 19;
/* 166:    */   public static final int LDIV = 109;
/* 167:    */   public static final int LLOAD = 22;
/* 168:    */   public static final int LLOAD_0 = 30;
/* 169:    */   public static final int LLOAD_1 = 31;
/* 170:    */   public static final int LLOAD_2 = 32;
/* 171:    */   public static final int LLOAD_3 = 33;
/* 172:    */   public static final int LMUL = 105;
/* 173:    */   public static final int LNEG = 117;
/* 174:    */   public static final int LOOKUPSWITCH = 171;
/* 175:    */   public static final int LOR = 129;
/* 176:    */   public static final int LREM = 113;
/* 177:    */   public static final int LRETURN = 173;
/* 178:    */   public static final int LSHL = 121;
/* 179:    */   public static final int LSHR = 123;
/* 180:    */   public static final int LSTORE = 55;
/* 181:    */   public static final int LSTORE_0 = 63;
/* 182:    */   public static final int LSTORE_1 = 64;
/* 183:    */   public static final int LSTORE_2 = 65;
/* 184:    */   public static final int LSTORE_3 = 66;
/* 185:    */   public static final int LSUB = 101;
/* 186:    */   public static final int LUSHR = 125;
/* 187:    */   public static final int LXOR = 131;
/* 188:    */   public static final int MONITORENTER = 194;
/* 189:    */   public static final int MONITOREXIT = 195;
/* 190:    */   public static final int MULTIANEWARRAY = 197;
/* 191:    */   public static final int NEW = 187;
/* 192:    */   public static final int NEWARRAY = 188;
/* 193:    */   public static final int NOP = 0;
/* 194:    */   public static final int POP = 87;
/* 195:    */   public static final int POP2 = 88;
/* 196:    */   public static final int PUTFIELD = 181;
/* 197:    */   public static final int PUTSTATIC = 179;
/* 198:    */   public static final int RET = 169;
/* 199:    */   public static final int RETURN = 177;
/* 200:    */   public static final int SALOAD = 53;
/* 201:    */   public static final int SASTORE = 86;
/* 202:    */   public static final int SIPUSH = 17;
/* 203:    */   public static final int SWAP = 95;
/* 204:    */   public static final int TABLESWITCH = 170;
/* 205:    */   public static final int WIDE = 196;
/* 206:    */   public static final int T_BOOLEAN = 4;
/* 207:    */   public static final int T_CHAR = 5;
/* 208:    */   public static final int T_FLOAT = 6;
/* 209:    */   public static final int T_DOUBLE = 7;
/* 210:    */   public static final int T_BYTE = 8;
/* 211:    */   public static final int T_SHORT = 9;
/* 212:    */   public static final int T_INT = 10;
/* 213:    */   public static final int T_LONG = 11;
/* 214:243 */   public static final int[] STACK_GROW = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2, -1, -1, -1, -1, -1, -2, -2, -2, -2, -1, -1, -1, -1, -2, -2, -2, -2, -1, -1, -1, -1, -3, -4, -3, -4, -3, -3, -3, -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1, -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, -1, -1, 0, 1 };
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.Opcode
 * JD-Core Version:    0.7.0.1
 */