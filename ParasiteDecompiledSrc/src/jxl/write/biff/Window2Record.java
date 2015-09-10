/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.SheetSettings;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.biff.Type;
/*   6:    */ import jxl.biff.WritableRecordData;
/*   7:    */ 
/*   8:    */ class Window2Record
/*   9:    */   extends WritableRecordData
/*  10:    */ {
/*  11:    */   private byte[] data;
/*  12:    */   
/*  13:    */   public Window2Record(SheetSettings settings)
/*  14:    */   {
/*  15: 42 */     super(Type.WINDOW2);
/*  16:    */     
/*  17: 44 */     int options = 0;
/*  18:    */     
/*  19: 46 */     options |= 0x0;
/*  20: 48 */     if (settings.getShowGridLines()) {
/*  21: 50 */       options |= 0x2;
/*  22:    */     }
/*  23: 53 */     options |= 0x4;
/*  24:    */     
/*  25: 55 */     options |= 0x0;
/*  26: 57 */     if (settings.getDisplayZeroValues()) {
/*  27: 59 */       options |= 0x10;
/*  28:    */     }
/*  29: 62 */     options |= 0x20;
/*  30:    */     
/*  31: 64 */     options |= 0x80;
/*  32: 67 */     if ((settings.getHorizontalFreeze() != 0) || (settings.getVerticalFreeze() != 0))
/*  33:    */     {
/*  34: 70 */       options |= 0x8;
/*  35: 71 */       options |= 0x100;
/*  36:    */     }
/*  37: 75 */     if (settings.isSelected()) {
/*  38: 77 */       options |= 0x600;
/*  39:    */     }
/*  40: 81 */     if (settings.getPageBreakPreviewMode()) {
/*  41: 83 */       options |= 0x800;
/*  42:    */     }
/*  43: 87 */     this.data = new byte[18];
/*  44: 88 */     IntegerHelper.getTwoBytes(options, this.data, 0);
/*  45: 89 */     IntegerHelper.getTwoBytes(64, this.data, 6);
/*  46: 90 */     IntegerHelper.getTwoBytes(settings.getPageBreakPreviewMagnification(), this.data, 10);
/*  47:    */     
/*  48: 92 */     IntegerHelper.getTwoBytes(settings.getNormalMagnification(), this.data, 12);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public byte[] getData()
/*  52:    */   {
/*  53:104 */     return this.data;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.Window2Record
 * JD-Core Version:    0.7.0.1
 */