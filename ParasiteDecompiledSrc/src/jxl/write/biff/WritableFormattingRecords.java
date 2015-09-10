/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.Fonts;
/*   4:    */ import jxl.biff.FormattingRecords;
/*   5:    */ import jxl.biff.NumFormatRecordsException;
/*   6:    */ import jxl.common.Assert;
/*   7:    */ import jxl.write.NumberFormats;
/*   8:    */ import jxl.write.WritableCellFormat;
/*   9:    */ 
/*  10:    */ public class WritableFormattingRecords
/*  11:    */   extends FormattingRecords
/*  12:    */ {
/*  13:    */   public static WritableCellFormat normalStyle;
/*  14:    */   
/*  15:    */   public WritableFormattingRecords(Fonts f, Styles styles)
/*  16:    */   {
/*  17: 52 */     super(f);
/*  18:    */     try
/*  19:    */     {
/*  20: 57 */       StyleXFRecord sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  21:    */       
/*  22: 59 */       sxf.setLocked(true);
/*  23: 60 */       addStyle(sxf);
/*  24:    */       
/*  25: 62 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.DEFAULT);
/*  26: 63 */       sxf.setLocked(true);
/*  27: 64 */       sxf.setCellOptions(62464);
/*  28: 65 */       addStyle(sxf);
/*  29:    */       
/*  30: 67 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.DEFAULT);
/*  31: 68 */       sxf.setLocked(true);
/*  32: 69 */       sxf.setCellOptions(62464);
/*  33: 70 */       addStyle(sxf);
/*  34:    */       
/*  35: 72 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.DEFAULT);
/*  36: 73 */       sxf.setLocked(true);
/*  37: 74 */       sxf.setCellOptions(62464);
/*  38: 75 */       addStyle(sxf);
/*  39:    */       
/*  40: 77 */       sxf = new StyleXFRecord(getFonts().getFont(2), NumberFormats.DEFAULT);
/*  41: 78 */       sxf.setLocked(true);
/*  42: 79 */       sxf.setCellOptions(62464);
/*  43: 80 */       addStyle(sxf);
/*  44:    */       
/*  45: 82 */       sxf = new StyleXFRecord(getFonts().getFont(3), NumberFormats.DEFAULT);
/*  46: 83 */       sxf.setLocked(true);
/*  47: 84 */       sxf.setCellOptions(62464);
/*  48: 85 */       addStyle(sxf);
/*  49:    */       
/*  50: 87 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  51:    */       
/*  52: 89 */       sxf.setLocked(true);
/*  53: 90 */       sxf.setCellOptions(62464);
/*  54: 91 */       addStyle(sxf);
/*  55:    */       
/*  56: 93 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  57:    */       
/*  58: 95 */       sxf.setLocked(true);
/*  59: 96 */       sxf.setCellOptions(62464);
/*  60: 97 */       addStyle(sxf);
/*  61:    */       
/*  62: 99 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  63:    */       
/*  64:101 */       sxf.setLocked(true);
/*  65:102 */       sxf.setCellOptions(62464);
/*  66:103 */       addStyle(sxf);
/*  67:    */       
/*  68:105 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  69:    */       
/*  70:107 */       sxf.setLocked(true);
/*  71:108 */       sxf.setCellOptions(62464);
/*  72:109 */       addStyle(sxf);
/*  73:    */       
/*  74:111 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  75:    */       
/*  76:113 */       sxf.setLocked(true);
/*  77:114 */       sxf.setCellOptions(62464);
/*  78:115 */       addStyle(sxf);
/*  79:    */       
/*  80:117 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  81:    */       
/*  82:119 */       sxf.setLocked(true);
/*  83:120 */       sxf.setCellOptions(62464);
/*  84:121 */       addStyle(sxf);
/*  85:    */       
/*  86:123 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  87:    */       
/*  88:125 */       sxf.setLocked(true);
/*  89:126 */       sxf.setCellOptions(62464);
/*  90:127 */       addStyle(sxf);
/*  91:    */       
/*  92:129 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  93:    */       
/*  94:131 */       sxf.setLocked(true);
/*  95:132 */       sxf.setCellOptions(62464);
/*  96:133 */       addStyle(sxf);
/*  97:    */       
/*  98:135 */       sxf = new StyleXFRecord(styles.getArial10Pt(), NumberFormats.DEFAULT);
/*  99:    */       
/* 100:137 */       sxf.setLocked(true);
/* 101:138 */       sxf.setCellOptions(62464);
/* 102:139 */       addStyle(sxf);
/* 103:    */       
/* 104:    */ 
/* 105:    */ 
/* 106:143 */       addStyle(styles.getNormalStyle());
/* 107:    */       
/* 108:    */ 
/* 109:146 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.FORMAT7);
/* 110:    */       
/* 111:148 */       sxf.setLocked(true);
/* 112:149 */       sxf.setCellOptions(63488);
/* 113:150 */       addStyle(sxf);
/* 114:    */       
/* 115:152 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.FORMAT5);
/* 116:    */       
/* 117:154 */       sxf.setLocked(true);
/* 118:155 */       sxf.setCellOptions(63488);
/* 119:156 */       addStyle(sxf);
/* 120:    */       
/* 121:158 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.FORMAT8);
/* 122:    */       
/* 123:160 */       sxf.setLocked(true);
/* 124:161 */       sxf.setCellOptions(63488);
/* 125:162 */       addStyle(sxf);
/* 126:    */       
/* 127:164 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.FORMAT6);
/* 128:    */       
/* 129:166 */       sxf.setLocked(true);
/* 130:167 */       sxf.setCellOptions(63488);
/* 131:168 */       addStyle(sxf);
/* 132:    */       
/* 133:170 */       sxf = new StyleXFRecord(getFonts().getFont(1), NumberFormats.PERCENT_INTEGER);
/* 134:    */       
/* 135:172 */       sxf.setLocked(true);
/* 136:173 */       sxf.setCellOptions(63488);
/* 137:174 */       addStyle(sxf);
/* 138:    */     }
/* 139:    */     catch (NumFormatRecordsException e)
/* 140:    */     {
/* 141:219 */       Assert.verify(false, e.getMessage());
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.WritableFormattingRecords
 * JD-Core Version:    0.7.0.1
 */