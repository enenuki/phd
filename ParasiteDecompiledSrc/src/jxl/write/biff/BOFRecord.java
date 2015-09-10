/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.Type;
/*   4:    */ import jxl.biff.WritableRecordData;
/*   5:    */ 
/*   6:    */ class BOFRecord
/*   7:    */   extends WritableRecordData
/*   8:    */ {
/*   9:    */   private byte[] data;
/*  10: 40 */   public static final WorkbookGlobalsBOF workbookGlobals = new WorkbookGlobalsBOF(null);
/*  11: 42 */   public static final SheetBOF sheet = new SheetBOF(null);
/*  12:    */   
/*  13:    */   public BOFRecord(WorkbookGlobalsBOF dummy)
/*  14:    */   {
/*  15: 51 */     super(Type.BOF);
/*  16:    */     
/*  17:    */ 
/*  18:    */ 
/*  19: 55 */     this.data = new byte[] { 0, 6, 5, 0, -14, 21, -52, 7, 0, 0, 0, 0, 6, 0, 0, 0 };
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BOFRecord(SheetBOF dummy)
/*  23:    */   {
/*  24: 82 */     super(Type.BOF);
/*  25:    */     
/*  26:    */ 
/*  27:    */ 
/*  28: 86 */     this.data = new byte[] { 0, 6, 16, 0, -14, 21, -52, 7, 0, 0, 0, 0, 6, 0, 0, 0 };
/*  29:    */   }
/*  30:    */   
/*  31:    */   public byte[] getData()
/*  32:    */   {
/*  33:113 */     return this.data;
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static class SheetBOF {}
/*  37:    */   
/*  38:    */   private static class WorkbookGlobalsBOF {}
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.BOFRecord
 * JD-Core Version:    0.7.0.1
 */