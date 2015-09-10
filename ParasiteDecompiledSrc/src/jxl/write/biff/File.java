/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import jxl.WorkbookSettings;
/*   6:    */ import jxl.biff.ByteData;
/*   7:    */ import jxl.common.Logger;
/*   8:    */ 
/*   9:    */ public final class File
/*  10:    */ {
/*  11: 41 */   private static Logger logger = Logger.getLogger(File.class);
/*  12:    */   private ExcelDataOutput data;
/*  13:    */   private int pos;
/*  14:    */   private OutputStream outputStream;
/*  15:    */   private int initialFileSize;
/*  16:    */   private int arrayGrowSize;
/*  17:    */   private WorkbookSettings workbookSettings;
/*  18:    */   jxl.read.biff.CompoundFile readCompoundFile;
/*  19:    */   
/*  20:    */   File(OutputStream os, WorkbookSettings ws, jxl.read.biff.CompoundFile rcf)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 83 */     this.outputStream = os;
/*  24: 84 */     this.workbookSettings = ws;
/*  25: 85 */     this.readCompoundFile = rcf;
/*  26: 86 */     createDataOutput();
/*  27:    */   }
/*  28:    */   
/*  29:    */   private void createDataOutput()
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 91 */     if (this.workbookSettings.getUseTemporaryFileDuringWrite())
/*  33:    */     {
/*  34: 93 */       this.data = new FileDataOutput(this.workbookSettings.getTemporaryFileDuringWriteDirectory());
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 98 */       this.initialFileSize = this.workbookSettings.getInitialFileSize();
/*  39: 99 */       this.arrayGrowSize = this.workbookSettings.getArrayGrowSize();
/*  40:    */       
/*  41:101 */       this.data = new MemoryDataOutput(this.initialFileSize, this.arrayGrowSize);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   void close(boolean cs)
/*  46:    */     throws IOException, JxlWriteException
/*  47:    */   {
/*  48:116 */     CompoundFile cf = new CompoundFile(this.data, this.data.getPosition(), this.outputStream, this.readCompoundFile);
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:120 */     cf.write();
/*  53:    */     
/*  54:122 */     this.outputStream.flush();
/*  55:123 */     this.data.close();
/*  56:125 */     if (cs) {
/*  57:127 */       this.outputStream.close();
/*  58:    */     }
/*  59:131 */     this.data = null;
/*  60:133 */     if (!this.workbookSettings.getGCDisabled()) {
/*  61:135 */       System.gc();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void write(ByteData record)
/*  66:    */     throws IOException
/*  67:    */   {
/*  68:147 */     byte[] bytes = record.getBytes();
/*  69:    */     
/*  70:149 */     this.data.write(bytes);
/*  71:    */   }
/*  72:    */   
/*  73:    */   int getPos()
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:159 */     return this.data.getPosition();
/*  77:    */   }
/*  78:    */   
/*  79:    */   void setData(byte[] newdata, int pos)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:171 */     this.data.setData(newdata, pos);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setOutputFile(OutputStream os)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:183 */     if (this.data != null) {
/*  89:185 */       logger.warn("Rewriting a workbook with non-empty data");
/*  90:    */     }
/*  91:188 */     this.outputStream = os;
/*  92:189 */     createDataOutput();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.File
 * JD-Core Version:    0.7.0.1
 */