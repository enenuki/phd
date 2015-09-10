/*   1:    */ package javassist.compiler.ast;
/*   2:    */ 
/*   3:    */ import javassist.compiler.CompileError;
/*   4:    */ 
/*   5:    */ public class IntConst
/*   6:    */   extends ASTree
/*   7:    */ {
/*   8:    */   protected long value;
/*   9:    */   protected int type;
/*  10:    */   
/*  11:    */   public IntConst(long v, int tokenId)
/*  12:    */   {
/*  13: 28 */     this.value = v;this.type = tokenId;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public long get()
/*  17:    */   {
/*  18: 30 */     return this.value;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void set(long v)
/*  22:    */   {
/*  23: 32 */     this.value = v;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getType()
/*  27:    */   {
/*  28: 36 */     return this.type;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String toString()
/*  32:    */   {
/*  33: 38 */     return Long.toString(this.value);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */     throws CompileError
/*  38:    */   {
/*  39: 41 */     v.atIntConst(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public ASTree compute(int op, ASTree right)
/*  43:    */   {
/*  44: 45 */     if ((right instanceof IntConst)) {
/*  45: 46 */       return compute0(op, (IntConst)right);
/*  46:    */     }
/*  47: 47 */     if ((right instanceof DoubleConst)) {
/*  48: 48 */       return compute0(op, (DoubleConst)right);
/*  49:    */     }
/*  50: 50 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private IntConst compute0(int op, IntConst right)
/*  54:    */   {
/*  55: 54 */     int type1 = this.type;
/*  56: 55 */     int type2 = right.type;
/*  57:    */     int newType;
/*  58:    */     int newType;
/*  59: 57 */     if ((type1 == 403) || (type2 == 403))
/*  60:    */     {
/*  61: 58 */       newType = 403;
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65:    */       int newType;
/*  66: 59 */       if ((type1 == 401) && (type2 == 401)) {
/*  67: 61 */         newType = 401;
/*  68:    */       } else {
/*  69: 63 */         newType = 402;
/*  70:    */       }
/*  71:    */     }
/*  72: 65 */     long value1 = this.value;
/*  73: 66 */     long value2 = right.value;
/*  74:    */     long newValue;
/*  75: 68 */     switch (op)
/*  76:    */     {
/*  77:    */     case 43: 
/*  78: 70 */       newValue = value1 + value2;
/*  79: 71 */       break;
/*  80:    */     case 45: 
/*  81: 73 */       newValue = value1 - value2;
/*  82: 74 */       break;
/*  83:    */     case 42: 
/*  84: 76 */       newValue = value1 * value2;
/*  85: 77 */       break;
/*  86:    */     case 47: 
/*  87: 79 */       newValue = value1 / value2;
/*  88: 80 */       break;
/*  89:    */     case 37: 
/*  90: 82 */       newValue = value1 % value2;
/*  91: 83 */       break;
/*  92:    */     case 124: 
/*  93: 85 */       newValue = value1 | value2;
/*  94: 86 */       break;
/*  95:    */     case 94: 
/*  96: 88 */       newValue = value1 ^ value2;
/*  97: 89 */       break;
/*  98:    */     case 38: 
/*  99: 91 */       newValue = value1 & value2;
/* 100: 92 */       break;
/* 101:    */     case 364: 
/* 102: 94 */       newValue = this.value << (int)value2;
/* 103: 95 */       newType = type1;
/* 104: 96 */       break;
/* 105:    */     case 366: 
/* 106: 98 */       newValue = this.value >> (int)value2;
/* 107: 99 */       newType = type1;
/* 108:100 */       break;
/* 109:    */     case 370: 
/* 110:102 */       newValue = this.value >>> (int)value2;
/* 111:103 */       newType = type1;
/* 112:104 */       break;
/* 113:    */     default: 
/* 114:106 */       return null;
/* 115:    */     }
/* 116:109 */     return new IntConst(newValue, newType);
/* 117:    */   }
/* 118:    */   
/* 119:    */   private DoubleConst compute0(int op, DoubleConst right)
/* 120:    */   {
/* 121:113 */     double value1 = this.value;
/* 122:114 */     double value2 = right.value;
/* 123:    */     double newValue;
/* 124:116 */     switch (op)
/* 125:    */     {
/* 126:    */     case 43: 
/* 127:118 */       newValue = value1 + value2;
/* 128:119 */       break;
/* 129:    */     case 45: 
/* 130:121 */       newValue = value1 - value2;
/* 131:122 */       break;
/* 132:    */     case 42: 
/* 133:124 */       newValue = value1 * value2;
/* 134:125 */       break;
/* 135:    */     case 47: 
/* 136:127 */       newValue = value1 / value2;
/* 137:128 */       break;
/* 138:    */     case 37: 
/* 139:130 */       newValue = value1 % value2;
/* 140:131 */       break;
/* 141:    */     case 38: 
/* 142:    */     case 39: 
/* 143:    */     case 40: 
/* 144:    */     case 41: 
/* 145:    */     case 44: 
/* 146:    */     case 46: 
/* 147:    */     default: 
/* 148:133 */       return null;
/* 149:    */     }
/* 150:136 */     return new DoubleConst(newValue, right.type);
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.IntConst
 * JD-Core Version:    0.7.0.1
 */