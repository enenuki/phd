/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.biff.RecordData;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ public class ExternalSheetRecord
/*   9:    */   extends RecordData
/*  10:    */ {
/*  11: 37 */   private static Logger logger = Logger.getLogger(ExternalSheetRecord.class);
/*  12: 43 */   public static Biff7 biff7 = new Biff7(null);
/*  13:    */   private XTI[] xtiArray;
/*  14:    */   
/*  15:    */   private static class XTI
/*  16:    */   {
/*  17:    */     int supbookIndex;
/*  18:    */     int firstTab;
/*  19:    */     int lastTab;
/*  20:    */     
/*  21:    */     XTI(int s, int f, int l)
/*  22:    */     {
/*  23: 72 */       this.supbookIndex = s;
/*  24: 73 */       this.firstTab = f;
/*  25: 74 */       this.lastTab = l;
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   ExternalSheetRecord(Record t, WorkbookSettings ws)
/*  30:    */   {
/*  31: 91 */     super(t);
/*  32: 92 */     byte[] data = getRecord().getData();
/*  33:    */     
/*  34: 94 */     int numxtis = IntegerHelper.getInt(data[0], data[1]);
/*  35: 96 */     if (data.length < numxtis * 6 + 2)
/*  36:    */     {
/*  37: 98 */       this.xtiArray = new XTI[0];
/*  38: 99 */       logger.warn("Could not process external sheets.  Formulas may be compromised.");
/*  39:    */       
/*  40:101 */       return;
/*  41:    */     }
/*  42:104 */     this.xtiArray = new XTI[numxtis];
/*  43:    */     
/*  44:106 */     int pos = 2;
/*  45:107 */     for (int i = 0; i < numxtis; i++)
/*  46:    */     {
/*  47:109 */       int s = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  48:110 */       int f = IntegerHelper.getInt(data[(pos + 2)], data[(pos + 3)]);
/*  49:111 */       int l = IntegerHelper.getInt(data[(pos + 4)], data[(pos + 5)]);
/*  50:112 */       this.xtiArray[i] = new XTI(s, f, l);
/*  51:113 */       pos += 6;
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   ExternalSheetRecord(Record t, WorkbookSettings settings, Biff7 dummy)
/*  56:    */   {
/*  57:127 */     super(t);
/*  58:    */     
/*  59:129 */     logger.warn("External sheet record for Biff 7 not supported");
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getNumRecords()
/*  63:    */   {
/*  64:138 */     return this.xtiArray != null ? this.xtiArray.length : 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getSupbookIndex(int index)
/*  68:    */   {
/*  69:148 */     return this.xtiArray[index].supbookIndex;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getFirstTabIndex(int index)
/*  73:    */   {
/*  74:159 */     return this.xtiArray[index].firstTab;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getLastTabIndex(int index)
/*  78:    */   {
/*  79:170 */     return this.xtiArray[index].lastTab;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public byte[] getData()
/*  83:    */   {
/*  84:180 */     return getRecord().getData();
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static class Biff7 {}
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.ExternalSheetRecord
 * JD-Core Version:    0.7.0.1
 */