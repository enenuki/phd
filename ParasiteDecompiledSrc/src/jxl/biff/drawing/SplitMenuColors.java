/*  1:   */ package jxl.biff.drawing;
/*  2:   */ 
/*  3:   */ class SplitMenuColors
/*  4:   */   extends EscherAtom
/*  5:   */ {
/*  6:   */   private byte[] data;
/*  7:   */   
/*  8:   */   public SplitMenuColors(EscherRecordData erd)
/*  9:   */   {
/* 10:39 */     super(erd);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public SplitMenuColors()
/* 14:   */   {
/* 15:47 */     super(EscherRecordType.SPLIT_MENU_COLORS);
/* 16:48 */     setVersion(0);
/* 17:49 */     setInstance(4);
/* 18:   */     
/* 19:51 */     this.data = new byte[] { 13, 0, 0, 8, 12, 0, 0, 8, 23, 0, 0, 8, -9, 0, 0, 16 };
/* 20:   */   }
/* 21:   */   
/* 22:   */   byte[] getData()
/* 23:   */   {
/* 24:65 */     return setHeaderData(this.data);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.SplitMenuColors
 * JD-Core Version:    0.7.0.1
 */