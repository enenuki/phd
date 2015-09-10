/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.XFRecord;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ import jxl.write.DateFormat;
/*   6:    */ import jxl.write.DateFormats;
/*   7:    */ import jxl.write.NumberFormats;
/*   8:    */ import jxl.write.WritableCellFormat;
/*   9:    */ import jxl.write.WritableFont;
/*  10:    */ import jxl.write.WritableWorkbook;
/*  11:    */ 
/*  12:    */ class Styles
/*  13:    */ {
/*  14: 42 */   private static Logger logger = Logger.getLogger(Styles.class);
/*  15:    */   private WritableFont arial10pt;
/*  16:    */   private WritableFont hyperlinkFont;
/*  17:    */   private WritableCellFormat normalStyle;
/*  18:    */   private WritableCellFormat hyperlinkStyle;
/*  19:    */   private WritableCellFormat hiddenStyle;
/*  20:    */   private WritableCellFormat defaultDateFormat;
/*  21:    */   
/*  22:    */   public Styles()
/*  23:    */   {
/*  24: 79 */     this.arial10pt = null;
/*  25: 80 */     this.hyperlinkFont = null;
/*  26: 81 */     this.normalStyle = null;
/*  27: 82 */     this.hyperlinkStyle = null;
/*  28: 83 */     this.hiddenStyle = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   private synchronized void initNormalStyle()
/*  32:    */   {
/*  33: 88 */     this.normalStyle = new WritableCellFormat(getArial10Pt(), NumberFormats.DEFAULT);
/*  34:    */     
/*  35: 90 */     this.normalStyle.setFont(getArial10Pt());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public WritableCellFormat getNormalStyle()
/*  39:    */   {
/*  40: 95 */     if (this.normalStyle == null) {
/*  41: 97 */       initNormalStyle();
/*  42:    */     }
/*  43:100 */     return this.normalStyle;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private synchronized void initHiddenStyle()
/*  47:    */   {
/*  48:105 */     this.hiddenStyle = new WritableCellFormat(getArial10Pt(), new DateFormat(";;;"));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public WritableCellFormat getHiddenStyle()
/*  52:    */   {
/*  53:111 */     if (this.hiddenStyle == null) {
/*  54:113 */       initHiddenStyle();
/*  55:    */     }
/*  56:116 */     return this.hiddenStyle;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private synchronized void initHyperlinkStyle()
/*  60:    */   {
/*  61:121 */     this.hyperlinkStyle = new WritableCellFormat(getHyperlinkFont(), NumberFormats.DEFAULT);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public WritableCellFormat getHyperlinkStyle()
/*  65:    */   {
/*  66:127 */     if (this.hyperlinkStyle == null) {
/*  67:129 */       initHyperlinkStyle();
/*  68:    */     }
/*  69:132 */     return this.hyperlinkStyle;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private synchronized void initArial10Pt()
/*  73:    */   {
/*  74:137 */     this.arial10pt = new WritableFont(WritableWorkbook.ARIAL_10_PT);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public WritableFont getArial10Pt()
/*  78:    */   {
/*  79:142 */     if (this.arial10pt == null) {
/*  80:144 */       initArial10Pt();
/*  81:    */     }
/*  82:147 */     return this.arial10pt;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private synchronized void initHyperlinkFont()
/*  86:    */   {
/*  87:152 */     this.hyperlinkFont = new WritableFont(WritableWorkbook.HYPERLINK_FONT);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public WritableFont getHyperlinkFont()
/*  91:    */   {
/*  92:157 */     if (this.hyperlinkFont == null) {
/*  93:159 */       initHyperlinkFont();
/*  94:    */     }
/*  95:162 */     return this.hyperlinkFont;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private synchronized void initDefaultDateFormat()
/*  99:    */   {
/* 100:167 */     this.defaultDateFormat = new WritableCellFormat(DateFormats.DEFAULT);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public WritableCellFormat getDefaultDateFormat()
/* 104:    */   {
/* 105:172 */     if (this.defaultDateFormat == null) {
/* 106:174 */       initDefaultDateFormat();
/* 107:    */     }
/* 108:177 */     return this.defaultDateFormat;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public XFRecord getFormat(XFRecord wf)
/* 112:    */   {
/* 113:192 */     XFRecord format = wf;
/* 114:196 */     if (format == WritableWorkbook.NORMAL_STYLE) {
/* 115:198 */       format = getNormalStyle();
/* 116:200 */     } else if (format == WritableWorkbook.HYPERLINK_STYLE) {
/* 117:202 */       format = getHyperlinkStyle();
/* 118:204 */     } else if (format == WritableWorkbook.HIDDEN_STYLE) {
/* 119:206 */       format = getHiddenStyle();
/* 120:208 */     } else if (format == DateRecord.defaultDateFormat) {
/* 121:210 */       format = getDefaultDateFormat();
/* 122:    */     }
/* 123:214 */     if (format.getFont() == WritableWorkbook.ARIAL_10_PT) {
/* 124:216 */       format.setFont(getArial10Pt());
/* 125:218 */     } else if (format.getFont() == WritableWorkbook.HYPERLINK_FONT) {
/* 126:220 */       format.setFont(getHyperlinkFont());
/* 127:    */     }
/* 128:223 */     return format;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.Styles
 * JD-Core Version:    0.7.0.1
 */