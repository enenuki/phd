/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.common.Logger;
/*   4:    */ 
/*   5:    */ public class DValParser
/*   6:    */ {
/*   7: 35 */   private static Logger logger = Logger.getLogger(DValParser.class);
/*   8: 38 */   private static int PROMPT_BOX_VISIBLE_MASK = 1;
/*   9: 39 */   private static int PROMPT_BOX_AT_CELL_MASK = 2;
/*  10: 40 */   private static int VALIDITY_DATA_CACHED_MASK = 4;
/*  11:    */   private boolean promptBoxVisible;
/*  12:    */   private boolean promptBoxAtCell;
/*  13:    */   private boolean validityDataCached;
/*  14:    */   private int numDVRecords;
/*  15:    */   private int objectId;
/*  16:    */   
/*  17:    */   public DValParser(byte[] data)
/*  18:    */   {
/*  19: 72 */     int options = IntegerHelper.getInt(data[0], data[1]);
/*  20:    */     
/*  21: 74 */     this.promptBoxVisible = ((options & PROMPT_BOX_VISIBLE_MASK) != 0);
/*  22: 75 */     this.promptBoxAtCell = ((options & PROMPT_BOX_AT_CELL_MASK) != 0);
/*  23: 76 */     this.validityDataCached = ((options & VALIDITY_DATA_CACHED_MASK) != 0);
/*  24:    */     
/*  25: 78 */     this.objectId = IntegerHelper.getInt(data[10], data[11], data[12], data[13]);
/*  26: 79 */     this.numDVRecords = IntegerHelper.getInt(data[14], data[15], data[16], data[17]);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DValParser(int objid, int num)
/*  30:    */   {
/*  31: 88 */     this.objectId = objid;
/*  32: 89 */     this.numDVRecords = num;
/*  33: 90 */     this.validityDataCached = true;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public byte[] getData()
/*  37:    */   {
/*  38: 98 */     byte[] data = new byte[18];
/*  39:    */     
/*  40:100 */     int options = 0;
/*  41:102 */     if (this.promptBoxVisible) {
/*  42:104 */       options |= PROMPT_BOX_VISIBLE_MASK;
/*  43:    */     }
/*  44:107 */     if (this.promptBoxAtCell) {
/*  45:109 */       options |= PROMPT_BOX_AT_CELL_MASK;
/*  46:    */     }
/*  47:112 */     if (this.validityDataCached) {
/*  48:114 */       options |= VALIDITY_DATA_CACHED_MASK;
/*  49:    */     }
/*  50:117 */     IntegerHelper.getTwoBytes(options, data, 0);
/*  51:    */     
/*  52:119 */     IntegerHelper.getFourBytes(this.objectId, data, 10);
/*  53:    */     
/*  54:121 */     IntegerHelper.getFourBytes(this.numDVRecords, data, 14);
/*  55:    */     
/*  56:123 */     return data;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void dvRemoved()
/*  60:    */   {
/*  61:132 */     this.numDVRecords -= 1;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getNumberOfDVRecords()
/*  65:    */   {
/*  66:142 */     return this.numDVRecords;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getObjectId()
/*  70:    */   {
/*  71:152 */     return this.objectId;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void dvAdded()
/*  75:    */   {
/*  76:160 */     this.numDVRecords += 1;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.DValParser
 * JD-Core Version:    0.7.0.1
 */