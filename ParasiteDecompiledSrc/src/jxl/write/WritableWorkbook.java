/*  1:   */ package jxl.write;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.IOException;
/*  5:   */ import jxl.Range;
/*  6:   */ import jxl.Sheet;
/*  7:   */ import jxl.Workbook;
/*  8:   */ import jxl.format.Colour;
/*  9:   */ import jxl.format.UnderlineStyle;
/* 10:   */ 
/* 11:   */ public abstract class WritableWorkbook
/* 12:   */ {
/* 13:40 */   public static final WritableFont ARIAL_10_PT = new WritableFont(WritableFont.ARIAL);
/* 14:46 */   public static final WritableFont HYPERLINK_FONT = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE, Colour.BLUE);
/* 15:57 */   public static final WritableCellFormat NORMAL_STYLE = new WritableCellFormat(ARIAL_10_PT, NumberFormats.DEFAULT);
/* 16:63 */   public static final WritableCellFormat HYPERLINK_STYLE = new WritableCellFormat(HYPERLINK_FONT);
/* 17:69 */   public static final WritableCellFormat HIDDEN_STYLE = new WritableCellFormat(new DateFormat(";;;"));
/* 18:   */   
/* 19:   */   public abstract WritableSheet[] getSheets();
/* 20:   */   
/* 21:   */   public abstract String[] getSheetNames();
/* 22:   */   
/* 23:   */   public abstract WritableSheet getSheet(int paramInt)
/* 24:   */     throws IndexOutOfBoundsException;
/* 25:   */   
/* 26:   */   public abstract WritableSheet getSheet(String paramString);
/* 27:   */   
/* 28:   */   public abstract WritableCell getWritableCell(String paramString);
/* 29:   */   
/* 30:   */   public abstract int getNumberOfSheets();
/* 31:   */   
/* 32:   */   public abstract void close()
/* 33:   */     throws IOException, WriteException;
/* 34:   */   
/* 35:   */   public abstract WritableSheet createSheet(String paramString, int paramInt);
/* 36:   */   
/* 37:   */   public abstract WritableSheet importSheet(String paramString, int paramInt, Sheet paramSheet);
/* 38:   */   
/* 39:   */   public abstract void copySheet(int paramInt1, String paramString, int paramInt2);
/* 40:   */   
/* 41:   */   public abstract void copySheet(String paramString1, String paramString2, int paramInt);
/* 42:   */   
/* 43:   */   public abstract void removeSheet(int paramInt);
/* 44:   */   
/* 45:   */   public abstract WritableSheet moveSheet(int paramInt1, int paramInt2);
/* 46:   */   
/* 47:   */   public abstract void write()
/* 48:   */     throws IOException;
/* 49:   */   
/* 50:   */   public abstract void setProtected(boolean paramBoolean);
/* 51:   */   
/* 52:   */   public abstract void setColourRGB(Colour paramColour, int paramInt1, int paramInt2, int paramInt3);
/* 53:   */   
/* 54:   */   /**
/* 55:   */    * @deprecated
/* 56:   */    */
/* 57:   */   public void copy(Workbook w) {}
/* 58:   */   
/* 59:   */   public abstract WritableCell findCellByName(String paramString);
/* 60:   */   
/* 61:   */   public abstract Range[] findByName(String paramString);
/* 62:   */   
/* 63:   */   public abstract String[] getRangeNames();
/* 64:   */   
/* 65:   */   public abstract void removeRangeName(String paramString);
/* 66:   */   
/* 67:   */   public abstract void addNameArea(String paramString, WritableSheet paramWritableSheet, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/* 68:   */   
/* 69:   */   public abstract void setOutputFile(File paramFile)
/* 70:   */     throws IOException;
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.WritableWorkbook
 * JD-Core Version:    0.7.0.1
 */