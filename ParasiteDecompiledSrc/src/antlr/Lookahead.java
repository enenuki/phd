/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ 
/*   6:    */ public class Lookahead
/*   7:    */   implements Cloneable
/*   8:    */ {
/*   9:    */   BitSet fset;
/*  10:    */   String cycle;
/*  11:    */   BitSet epsilonDepth;
/*  12: 79 */   boolean hasEpsilon = false;
/*  13:    */   
/*  14:    */   public Lookahead()
/*  15:    */   {
/*  16: 82 */     this.fset = new BitSet();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Lookahead(BitSet paramBitSet)
/*  20:    */   {
/*  21: 87 */     this.fset = paramBitSet;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Lookahead(String paramString)
/*  25:    */   {
/*  26: 92 */     this();
/*  27: 93 */     this.cycle = paramString;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object clone()
/*  31:    */   {
/*  32: 98 */     Lookahead localLookahead = null;
/*  33:    */     try
/*  34:    */     {
/*  35:100 */       localLookahead = (Lookahead)super.clone();
/*  36:101 */       localLookahead.fset = ((BitSet)this.fset.clone());
/*  37:102 */       localLookahead.cycle = this.cycle;
/*  38:103 */       if (this.epsilonDepth != null) {
/*  39:104 */         localLookahead.epsilonDepth = ((BitSet)this.epsilonDepth.clone());
/*  40:    */       }
/*  41:    */     }
/*  42:    */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*  43:    */     {
/*  44:108 */       throw new InternalError();
/*  45:    */     }
/*  46:110 */     return localLookahead;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void combineWith(Lookahead paramLookahead)
/*  50:    */   {
/*  51:114 */     if (this.cycle == null) {
/*  52:115 */       this.cycle = paramLookahead.cycle;
/*  53:    */     }
/*  54:118 */     if (paramLookahead.containsEpsilon()) {
/*  55:119 */       this.hasEpsilon = true;
/*  56:    */     }
/*  57:123 */     if (this.epsilonDepth != null)
/*  58:    */     {
/*  59:124 */       if (paramLookahead.epsilonDepth != null) {
/*  60:125 */         this.epsilonDepth.orInPlace(paramLookahead.epsilonDepth);
/*  61:    */       }
/*  62:    */     }
/*  63:128 */     else if (paramLookahead.epsilonDepth != null) {
/*  64:129 */       this.epsilonDepth = ((BitSet)paramLookahead.epsilonDepth.clone());
/*  65:    */     }
/*  66:131 */     this.fset.orInPlace(paramLookahead.fset);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean containsEpsilon()
/*  70:    */   {
/*  71:135 */     return this.hasEpsilon;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Lookahead intersection(Lookahead paramLookahead)
/*  75:    */   {
/*  76:142 */     Lookahead localLookahead = new Lookahead(this.fset.and(paramLookahead.fset));
/*  77:143 */     if ((this.hasEpsilon) && (paramLookahead.hasEpsilon)) {
/*  78:144 */       localLookahead.setEpsilon();
/*  79:    */     }
/*  80:146 */     return localLookahead;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean nil()
/*  84:    */   {
/*  85:150 */     return (this.fset.nil()) && (!this.hasEpsilon);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Lookahead of(int paramInt)
/*  89:    */   {
/*  90:154 */     Lookahead localLookahead = new Lookahead();
/*  91:155 */     localLookahead.fset.add(paramInt);
/*  92:156 */     return localLookahead;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void resetEpsilon()
/*  96:    */   {
/*  97:160 */     this.hasEpsilon = false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setEpsilon()
/* 101:    */   {
/* 102:164 */     this.hasEpsilon = true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String toString()
/* 106:    */   {
/* 107:168 */     String str1 = "";String str3 = "";String str4 = "";
/* 108:169 */     String str2 = this.fset.toString(",");
/* 109:170 */     if (containsEpsilon()) {
/* 110:171 */       str1 = "+<epsilon>";
/* 111:    */     }
/* 112:173 */     if (this.cycle != null) {
/* 113:174 */       str3 = "; FOLLOW(" + this.cycle + ")";
/* 114:    */     }
/* 115:176 */     if (this.epsilonDepth != null) {
/* 116:177 */       str4 = "; depths=" + this.epsilonDepth.toString(",");
/* 117:    */     }
/* 118:179 */     return str2 + str1 + str3 + str4;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String toString(String paramString, CharFormatter paramCharFormatter)
/* 122:    */   {
/* 123:184 */     String str1 = "";String str3 = "";String str4 = "";
/* 124:185 */     String str2 = this.fset.toString(paramString, paramCharFormatter);
/* 125:186 */     if (containsEpsilon()) {
/* 126:187 */       str1 = "+<epsilon>";
/* 127:    */     }
/* 128:189 */     if (this.cycle != null) {
/* 129:190 */       str3 = "; FOLLOW(" + this.cycle + ")";
/* 130:    */     }
/* 131:192 */     if (this.epsilonDepth != null) {
/* 132:193 */       str4 = "; depths=" + this.epsilonDepth.toString(",");
/* 133:    */     }
/* 134:195 */     return str2 + str1 + str3 + str4;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String toString(String paramString, CharFormatter paramCharFormatter, Grammar paramGrammar)
/* 138:    */   {
/* 139:199 */     if ((paramGrammar instanceof LexerGrammar)) {
/* 140:200 */       return toString(paramString, paramCharFormatter);
/* 141:    */     }
/* 142:203 */     return toString(paramString, paramGrammar.tokenManager.getVocabulary());
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString(String paramString, Vector paramVector)
/* 146:    */   {
/* 147:208 */     String str2 = "";String str3 = "";
/* 148:209 */     String str1 = this.fset.toString(paramString, paramVector);
/* 149:210 */     if (this.cycle != null) {
/* 150:211 */       str2 = "; FOLLOW(" + this.cycle + ")";
/* 151:    */     }
/* 152:213 */     if (this.epsilonDepth != null) {
/* 153:214 */       str3 = "; depths=" + this.epsilonDepth.toString(",");
/* 154:    */     }
/* 155:216 */     return str1 + str2 + str3;
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Lookahead
 * JD-Core Version:    0.7.0.1
 */