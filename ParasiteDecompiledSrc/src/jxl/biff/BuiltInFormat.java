/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.format.Format;
/*   4:    */ 
/*   5:    */ final class BuiltInFormat
/*   6:    */   implements Format, DisplayFormat
/*   7:    */ {
/*   8:    */   private String formatString;
/*   9:    */   private int formatIndex;
/*  10:    */   
/*  11:    */   private BuiltInFormat(String s, int i)
/*  12:    */   {
/*  13: 52 */     this.formatIndex = i;
/*  14: 53 */     this.formatString = s;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String getFormatString()
/*  18:    */   {
/*  19: 65 */     return this.formatString;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getFormatIndex()
/*  23:    */   {
/*  24: 75 */     return this.formatIndex;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isInitialized()
/*  28:    */   {
/*  29: 84 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void initialize(int pos) {}
/*  33:    */   
/*  34:    */   public boolean isBuiltIn()
/*  35:    */   {
/*  36:102 */     return true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean equals(Object o)
/*  40:    */   {
/*  41:112 */     if (o == this) {
/*  42:114 */       return true;
/*  43:    */     }
/*  44:117 */     if (!(o instanceof BuiltInFormat)) {
/*  45:119 */       return false;
/*  46:    */     }
/*  47:122 */     BuiltInFormat bif = (BuiltInFormat)o;
/*  48:123 */     return this.formatIndex == bif.formatIndex;
/*  49:    */   }
/*  50:    */   
/*  51:129 */   public static BuiltInFormat[] builtIns = new BuiltInFormat[50];
/*  52:    */   
/*  53:    */   static
/*  54:    */   {
/*  55:134 */     builtIns[0] = new BuiltInFormat("", 0);
/*  56:135 */     builtIns[1] = new BuiltInFormat("0", 1);
/*  57:136 */     builtIns[2] = new BuiltInFormat("0.00", 2);
/*  58:137 */     builtIns[3] = new BuiltInFormat("#,##0", 3);
/*  59:138 */     builtIns[4] = new BuiltInFormat("#,##0.00", 4);
/*  60:139 */     builtIns[5] = new BuiltInFormat("($#,##0_);($#,##0)", 5);
/*  61:140 */     builtIns[6] = new BuiltInFormat("($#,##0_);[Red]($#,##0)", 6);
/*  62:141 */     builtIns[7] = new BuiltInFormat("($#,##0_);[Red]($#,##0)", 7);
/*  63:142 */     builtIns[8] = new BuiltInFormat("($#,##0.00_);[Red]($#,##0.00)", 8);
/*  64:143 */     builtIns[9] = new BuiltInFormat("0%", 9);
/*  65:144 */     builtIns[10] = new BuiltInFormat("0.00%", 10);
/*  66:145 */     builtIns[11] = new BuiltInFormat("0.00E+00", 11);
/*  67:146 */     builtIns[12] = new BuiltInFormat("# ?/?", 12);
/*  68:147 */     builtIns[13] = new BuiltInFormat("# ??/??", 13);
/*  69:148 */     builtIns[14] = new BuiltInFormat("dd/mm/yyyy", 14);
/*  70:149 */     builtIns[15] = new BuiltInFormat("d-mmm-yy", 15);
/*  71:150 */     builtIns[16] = new BuiltInFormat("d-mmm", 16);
/*  72:151 */     builtIns[17] = new BuiltInFormat("mmm-yy", 17);
/*  73:152 */     builtIns[18] = new BuiltInFormat("h:mm AM/PM", 18);
/*  74:153 */     builtIns[19] = new BuiltInFormat("h:mm:ss AM/PM", 19);
/*  75:154 */     builtIns[20] = new BuiltInFormat("h:mm", 20);
/*  76:155 */     builtIns[21] = new BuiltInFormat("h:mm:ss", 21);
/*  77:156 */     builtIns[22] = new BuiltInFormat("m/d/yy h:mm", 22);
/*  78:157 */     builtIns[37] = new BuiltInFormat("(#,##0_);(#,##0)", 37);
/*  79:158 */     builtIns[38] = new BuiltInFormat("(#,##0_);[Red](#,##0)", 38);
/*  80:159 */     builtIns[39] = new BuiltInFormat("(#,##0.00_);(#,##0.00)", 39);
/*  81:160 */     builtIns[40] = new BuiltInFormat("(#,##0.00_);[Red](#,##0.00)", 40);
/*  82:161 */     builtIns[41] = new BuiltInFormat("_(*#,##0_);_(*(#,##0);_(*\"-\"_);(@_)", 41);
/*  83:    */     
/*  84:163 */     builtIns[42] = new BuiltInFormat("_($*#,##0_);_($*(#,##0);_($*\"-\"_);(@_)", 42);
/*  85:    */     
/*  86:165 */     builtIns[43] = new BuiltInFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);(@_)", 43);
/*  87:    */     
/*  88:167 */     builtIns[44] = new BuiltInFormat("_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);(@_)", 44);
/*  89:    */     
/*  90:169 */     builtIns[45] = new BuiltInFormat("mm:ss", 45);
/*  91:170 */     builtIns[46] = new BuiltInFormat("[h]mm:ss", 46);
/*  92:171 */     builtIns[47] = new BuiltInFormat("mm:ss.0", 47);
/*  93:172 */     builtIns[48] = new BuiltInFormat("##0.0E+0", 48);
/*  94:173 */     builtIns[49] = new BuiltInFormat("@", 49);
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.BuiltInFormat
 * JD-Core Version:    0.7.0.1
 */