/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.IntegerHelper;
/*   4:    */ import jxl.biff.Type;
/*   5:    */ import jxl.biff.WritableRecordData;
/*   6:    */ 
/*   7:    */ class ExtendedSSTRecord
/*   8:    */   extends WritableRecordData
/*   9:    */ {
/*  10:    */   private static final int infoRecordSize = 8;
/*  11:    */   private int numberOfStrings;
/*  12:    */   private int[] absoluteStreamPositions;
/*  13:    */   private int[] relativeStreamPositions;
/*  14: 39 */   private int currentStringIndex = 0;
/*  15:    */   
/*  16:    */   public ExtendedSSTRecord(int newNumberOfStrings)
/*  17:    */   {
/*  18: 50 */     super(Type.EXTSST);
/*  19: 51 */     this.numberOfStrings = newNumberOfStrings;
/*  20: 52 */     int numberOfBuckets = getNumberOfBuckets();
/*  21: 53 */     this.absoluteStreamPositions = new int[numberOfBuckets];
/*  22: 54 */     this.relativeStreamPositions = new int[numberOfBuckets];
/*  23: 55 */     this.currentStringIndex = 0;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getNumberOfBuckets()
/*  27:    */   {
/*  28: 60 */     int numberOfStringsPerBucket = getNumberOfStringsPerBucket();
/*  29: 61 */     return numberOfStringsPerBucket != 0 ? (this.numberOfStrings + numberOfStringsPerBucket - 1) / numberOfStringsPerBucket : 0;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getNumberOfStringsPerBucket()
/*  33:    */   {
/*  34: 73 */     int bucketLimit = 128;
/*  35: 74 */     return (this.numberOfStrings + 128 - 1) / 128;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void addString(int absoluteStreamPosition, int relativeStreamPosition)
/*  39:    */   {
/*  40: 80 */     this.absoluteStreamPositions[this.currentStringIndex] = (absoluteStreamPosition + relativeStreamPosition);
/*  41:    */     
/*  42: 82 */     this.relativeStreamPositions[this.currentStringIndex] = relativeStreamPosition;
/*  43: 83 */     this.currentStringIndex += 1;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public byte[] getData()
/*  47:    */   {
/*  48: 93 */     int numberOfBuckets = getNumberOfBuckets();
/*  49: 94 */     byte[] data = new byte[2 + 8 * numberOfBuckets];
/*  50:    */     
/*  51: 96 */     IntegerHelper.getTwoBytes(getNumberOfStringsPerBucket(), data, 0);
/*  52: 98 */     for (int i = 0; i < numberOfBuckets; i++)
/*  53:    */     {
/*  54:101 */       IntegerHelper.getFourBytes(this.absoluteStreamPositions[i], data, 2 + i * 8);
/*  55:    */       
/*  56:    */ 
/*  57:    */ 
/*  58:105 */       IntegerHelper.getTwoBytes(this.relativeStreamPositions[i], data, 6 + i * 8);
/*  59:    */     }
/*  60:112 */     return data;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ExtendedSSTRecord
 * JD-Core Version:    0.7.0.1
 */