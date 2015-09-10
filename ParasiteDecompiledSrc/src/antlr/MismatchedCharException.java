/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ 
/*   5:    */ public class MismatchedCharException
/*   6:    */   extends RecognitionException
/*   7:    */ {
/*   8:    */   public static final int CHAR = 1;
/*   9:    */   public static final int NOT_CHAR = 2;
/*  10:    */   public static final int RANGE = 3;
/*  11:    */   public static final int NOT_RANGE = 4;
/*  12:    */   public static final int SET = 5;
/*  13:    */   public static final int NOT_SET = 6;
/*  14:    */   public int mismatchType;
/*  15:    */   public int foundChar;
/*  16:    */   public int expecting;
/*  17:    */   public int upper;
/*  18:    */   public BitSet set;
/*  19:    */   public CharScanner scanner;
/*  20:    */   
/*  21:    */   public MismatchedCharException()
/*  22:    */   {
/*  23: 43 */     super("Mismatched char");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public MismatchedCharException(char paramChar1, char paramChar2, char paramChar3, boolean paramBoolean, CharScanner paramCharScanner)
/*  27:    */   {
/*  28: 48 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  29: 49 */     this.mismatchType = (paramBoolean ? 4 : 3);
/*  30: 50 */     this.foundChar = paramChar1;
/*  31: 51 */     this.expecting = paramChar2;
/*  32: 52 */     this.upper = paramChar3;
/*  33: 53 */     this.scanner = paramCharScanner;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public MismatchedCharException(char paramChar1, char paramChar2, boolean paramBoolean, CharScanner paramCharScanner)
/*  37:    */   {
/*  38: 58 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  39: 59 */     this.mismatchType = (paramBoolean ? 2 : 1);
/*  40: 60 */     this.foundChar = paramChar1;
/*  41: 61 */     this.expecting = paramChar2;
/*  42: 62 */     this.scanner = paramCharScanner;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MismatchedCharException(char paramChar, BitSet paramBitSet, boolean paramBoolean, CharScanner paramCharScanner)
/*  46:    */   {
/*  47: 67 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  48: 68 */     this.mismatchType = (paramBoolean ? 6 : 5);
/*  49: 69 */     this.foundChar = paramChar;
/*  50: 70 */     this.set = paramBitSet;
/*  51: 71 */     this.scanner = paramCharScanner;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getMessage()
/*  55:    */   {
/*  56: 78 */     StringBuffer localStringBuffer = new StringBuffer();
/*  57: 80 */     switch (this.mismatchType)
/*  58:    */     {
/*  59:    */     case 1: 
/*  60: 82 */       localStringBuffer.append("expecting ");appendCharName(localStringBuffer, this.expecting);
/*  61: 83 */       localStringBuffer.append(", found ");appendCharName(localStringBuffer, this.foundChar);
/*  62: 84 */       break;
/*  63:    */     case 2: 
/*  64: 86 */       localStringBuffer.append("expecting anything but '");
/*  65: 87 */       appendCharName(localStringBuffer, this.expecting);
/*  66: 88 */       localStringBuffer.append("'; got it anyway");
/*  67: 89 */       break;
/*  68:    */     case 3: 
/*  69:    */     case 4: 
/*  70: 92 */       localStringBuffer.append("expecting token ");
/*  71: 93 */       if (this.mismatchType == 4) {
/*  72: 94 */         localStringBuffer.append("NOT ");
/*  73:    */       }
/*  74: 95 */       localStringBuffer.append("in range: ");
/*  75: 96 */       appendCharName(localStringBuffer, this.expecting);
/*  76: 97 */       localStringBuffer.append("..");
/*  77: 98 */       appendCharName(localStringBuffer, this.upper);
/*  78: 99 */       localStringBuffer.append(", found ");
/*  79:100 */       appendCharName(localStringBuffer, this.foundChar);
/*  80:101 */       break;
/*  81:    */     case 5: 
/*  82:    */     case 6: 
/*  83:104 */       localStringBuffer.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
/*  84:105 */       int[] arrayOfInt = this.set.toArray();
/*  85:106 */       for (int i = 0; i < arrayOfInt.length; i++) {
/*  86:107 */         appendCharName(localStringBuffer, arrayOfInt[i]);
/*  87:    */       }
/*  88:109 */       localStringBuffer.append("), found ");
/*  89:110 */       appendCharName(localStringBuffer, this.foundChar);
/*  90:111 */       break;
/*  91:    */     default: 
/*  92:113 */       localStringBuffer.append(super.getMessage());
/*  93:    */     }
/*  94:117 */     return localStringBuffer.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void appendCharName(StringBuffer paramStringBuffer, int paramInt)
/*  98:    */   {
/*  99:124 */     switch (paramInt)
/* 100:    */     {
/* 101:    */     case 65535: 
/* 102:127 */       paramStringBuffer.append("'<EOF>'");
/* 103:128 */       break;
/* 104:    */     case 10: 
/* 105:130 */       paramStringBuffer.append("'\\n'");
/* 106:131 */       break;
/* 107:    */     case 13: 
/* 108:133 */       paramStringBuffer.append("'\\r'");
/* 109:134 */       break;
/* 110:    */     case 9: 
/* 111:136 */       paramStringBuffer.append("'\\t'");
/* 112:137 */       break;
/* 113:    */     default: 
/* 114:139 */       paramStringBuffer.append('\'');
/* 115:140 */       paramStringBuffer.append((char)paramInt);
/* 116:141 */       paramStringBuffer.append('\'');
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.MismatchedCharException
 * JD-Core Version:    0.7.0.1
 */