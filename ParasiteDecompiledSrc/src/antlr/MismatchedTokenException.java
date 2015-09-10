/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.impl.BitSet;
/*   5:    */ 
/*   6:    */ public class MismatchedTokenException
/*   7:    */   extends RecognitionException
/*   8:    */ {
/*   9:    */   String[] tokenNames;
/*  10:    */   public Token token;
/*  11:    */   public AST node;
/*  12: 21 */   String tokenText = null;
/*  13:    */   public static final int TOKEN = 1;
/*  14:    */   public static final int NOT_TOKEN = 2;
/*  15:    */   public static final int RANGE = 3;
/*  16:    */   public static final int NOT_RANGE = 4;
/*  17:    */   public static final int SET = 5;
/*  18:    */   public static final int NOT_SET = 6;
/*  19:    */   public int mismatchType;
/*  20:    */   public int expecting;
/*  21:    */   public int upper;
/*  22:    */   public BitSet set;
/*  23:    */   
/*  24:    */   public MismatchedTokenException()
/*  25:    */   {
/*  26: 44 */     super("Mismatched Token: expecting any AST node", "<AST>", -1, -1);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, int paramInt1, int paramInt2, boolean paramBoolean)
/*  30:    */   {
/*  31: 49 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  32: 50 */     this.tokenNames = paramArrayOfString;
/*  33: 51 */     this.node = paramAST;
/*  34: 52 */     if (paramAST == null) {
/*  35: 53 */       this.tokenText = "<empty tree>";
/*  36:    */     } else {
/*  37: 56 */       this.tokenText = paramAST.toString();
/*  38:    */     }
/*  39: 58 */     this.mismatchType = (paramBoolean ? 4 : 3);
/*  40: 59 */     this.expecting = paramInt1;
/*  41: 60 */     this.upper = paramInt2;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, int paramInt, boolean paramBoolean)
/*  45:    */   {
/*  46: 65 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  47: 66 */     this.tokenNames = paramArrayOfString;
/*  48: 67 */     this.node = paramAST;
/*  49: 68 */     if (paramAST == null) {
/*  50: 69 */       this.tokenText = "<empty tree>";
/*  51:    */     } else {
/*  52: 72 */       this.tokenText = paramAST.toString();
/*  53:    */     }
/*  54: 74 */     this.mismatchType = (paramBoolean ? 2 : 1);
/*  55: 75 */     this.expecting = paramInt;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, BitSet paramBitSet, boolean paramBoolean)
/*  59:    */   {
/*  60: 80 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  61: 81 */     this.tokenNames = paramArrayOfString;
/*  62: 82 */     this.node = paramAST;
/*  63: 83 */     if (paramAST == null) {
/*  64: 84 */       this.tokenText = "<empty tree>";
/*  65:    */     } else {
/*  66: 87 */       this.tokenText = paramAST.toString();
/*  67:    */     }
/*  68: 89 */     this.mismatchType = (paramBoolean ? 6 : 5);
/*  69: 90 */     this.set = paramBitSet;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
/*  73:    */   {
/*  74: 95 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/*  75: 96 */     this.tokenNames = paramArrayOfString;
/*  76: 97 */     this.token = paramToken;
/*  77: 98 */     this.tokenText = paramToken.getText();
/*  78: 99 */     this.mismatchType = (paramBoolean ? 4 : 3);
/*  79:100 */     this.expecting = paramInt1;
/*  80:101 */     this.upper = paramInt2;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, int paramInt, boolean paramBoolean, String paramString)
/*  84:    */   {
/*  85:106 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/*  86:107 */     this.tokenNames = paramArrayOfString;
/*  87:108 */     this.token = paramToken;
/*  88:109 */     this.tokenText = paramToken.getText();
/*  89:110 */     this.mismatchType = (paramBoolean ? 2 : 1);
/*  90:111 */     this.expecting = paramInt;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, BitSet paramBitSet, boolean paramBoolean, String paramString)
/*  94:    */   {
/*  95:116 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/*  96:117 */     this.tokenNames = paramArrayOfString;
/*  97:118 */     this.token = paramToken;
/*  98:119 */     this.tokenText = paramToken.getText();
/*  99:120 */     this.mismatchType = (paramBoolean ? 6 : 5);
/* 100:121 */     this.set = paramBitSet;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getMessage()
/* 104:    */   {
/* 105:128 */     StringBuffer localStringBuffer = new StringBuffer();
/* 106:130 */     switch (this.mismatchType)
/* 107:    */     {
/* 108:    */     case 1: 
/* 109:132 */       localStringBuffer.append("expecting " + tokenName(this.expecting) + ", found '" + this.tokenText + "'");
/* 110:133 */       break;
/* 111:    */     case 2: 
/* 112:135 */       localStringBuffer.append("expecting anything but " + tokenName(this.expecting) + "; got it anyway");
/* 113:136 */       break;
/* 114:    */     case 3: 
/* 115:138 */       localStringBuffer.append("expecting token in range: " + tokenName(this.expecting) + ".." + tokenName(this.upper) + ", found '" + this.tokenText + "'");
/* 116:139 */       break;
/* 117:    */     case 4: 
/* 118:141 */       localStringBuffer.append("expecting token NOT in range: " + tokenName(this.expecting) + ".." + tokenName(this.upper) + ", found '" + this.tokenText + "'");
/* 119:142 */       break;
/* 120:    */     case 5: 
/* 121:    */     case 6: 
/* 122:145 */       localStringBuffer.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
/* 123:146 */       int[] arrayOfInt = this.set.toArray();
/* 124:147 */       for (int i = 0; i < arrayOfInt.length; i++)
/* 125:    */       {
/* 126:148 */         localStringBuffer.append(" ");
/* 127:149 */         localStringBuffer.append(tokenName(arrayOfInt[i]));
/* 128:    */       }
/* 129:151 */       localStringBuffer.append("), found '" + this.tokenText + "'");
/* 130:152 */       break;
/* 131:    */     default: 
/* 132:154 */       localStringBuffer.append(super.getMessage());
/* 133:    */     }
/* 134:158 */     return localStringBuffer.toString();
/* 135:    */   }
/* 136:    */   
/* 137:    */   private String tokenName(int paramInt)
/* 138:    */   {
/* 139:162 */     if (paramInt == 0) {
/* 140:163 */       return "<Set of tokens>";
/* 141:    */     }
/* 142:165 */     if ((paramInt < 0) || (paramInt >= this.tokenNames.length)) {
/* 143:166 */       return "<" + String.valueOf(paramInt) + ">";
/* 144:    */     }
/* 145:169 */     return this.tokenNames[paramInt];
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.MismatchedTokenException
 * JD-Core Version:    0.7.0.1
 */