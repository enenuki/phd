/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import jxl.WorkbookSettings;
/*   7:    */ import jxl.biff.BaseCompoundFile;
/*   8:    */ import jxl.biff.IntegerHelper;
/*   9:    */ import jxl.biff.Type;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ 
/*  12:    */ public class File
/*  13:    */ {
/*  14: 42 */   private static Logger logger = Logger.getLogger(File.class);
/*  15:    */   private byte[] data;
/*  16:    */   private int filePos;
/*  17:    */   private int oldPos;
/*  18:    */   private int initialFileSize;
/*  19:    */   private int arrayGrowSize;
/*  20:    */   private CompoundFile compoundFile;
/*  21:    */   private WorkbookSettings workbookSettings;
/*  22:    */   
/*  23:    */   public File(InputStream is, WorkbookSettings ws)
/*  24:    */     throws IOException, BiffException
/*  25:    */   {
/*  26: 86 */     this.workbookSettings = ws;
/*  27: 87 */     this.initialFileSize = this.workbookSettings.getInitialFileSize();
/*  28: 88 */     this.arrayGrowSize = this.workbookSettings.getArrayGrowSize();
/*  29:    */     
/*  30: 90 */     byte[] d = new byte[this.initialFileSize];
/*  31: 91 */     int bytesRead = is.read(d);
/*  32: 92 */     int pos = bytesRead;
/*  33: 96 */     if (Thread.currentThread().isInterrupted()) {
/*  34: 98 */       throw new InterruptedIOException();
/*  35:    */     }
/*  36:101 */     while (bytesRead != -1)
/*  37:    */     {
/*  38:103 */       if (pos >= d.length)
/*  39:    */       {
/*  40:106 */         byte[] newArray = new byte[d.length + this.arrayGrowSize];
/*  41:107 */         System.arraycopy(d, 0, newArray, 0, d.length);
/*  42:108 */         d = newArray;
/*  43:    */       }
/*  44:110 */       bytesRead = is.read(d, pos, d.length - pos);
/*  45:111 */       pos += bytesRead;
/*  46:113 */       if (Thread.currentThread().isInterrupted()) {
/*  47:115 */         throw new InterruptedIOException();
/*  48:    */       }
/*  49:    */     }
/*  50:119 */     bytesRead = pos + 1;
/*  51:122 */     if (bytesRead == 0) {
/*  52:124 */       throw new BiffException(BiffException.excelFileNotFound);
/*  53:    */     }
/*  54:127 */     CompoundFile cf = new CompoundFile(d, ws);
/*  55:    */     try
/*  56:    */     {
/*  57:130 */       this.data = cf.getStream("workbook");
/*  58:    */     }
/*  59:    */     catch (BiffException e)
/*  60:    */     {
/*  61:135 */       this.data = cf.getStream("book");
/*  62:    */     }
/*  63:138 */     if ((!this.workbookSettings.getPropertySetsDisabled()) && (cf.getNumberOfPropertySets() > BaseCompoundFile.STANDARD_PROPERTY_SETS.length)) {
/*  64:142 */       this.compoundFile = cf;
/*  65:    */     }
/*  66:145 */     cf = null;
/*  67:147 */     if (!this.workbookSettings.getGCDisabled()) {
/*  68:149 */       System.gc();
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public File(byte[] d)
/*  73:    */   {
/*  74:171 */     this.data = d;
/*  75:    */   }
/*  76:    */   
/*  77:    */   Record next()
/*  78:    */   {
/*  79:181 */     Record r = new Record(this.data, this.filePos, this);
/*  80:182 */     return r;
/*  81:    */   }
/*  82:    */   
/*  83:    */   Record peek()
/*  84:    */   {
/*  85:192 */     int tempPos = this.filePos;
/*  86:193 */     Record r = new Record(this.data, this.filePos, this);
/*  87:194 */     this.filePos = tempPos;
/*  88:195 */     return r;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void skip(int bytes)
/*  92:    */   {
/*  93:205 */     this.filePos += bytes;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public byte[] read(int pos, int length)
/*  97:    */   {
/*  98:217 */     byte[] ret = new byte[length];
/*  99:    */     try
/* 100:    */     {
/* 101:220 */       System.arraycopy(this.data, pos, ret, 0, length);
/* 102:    */     }
/* 103:    */     catch (ArrayIndexOutOfBoundsException e)
/* 104:    */     {
/* 105:224 */       logger.error("Array index out of bounds at position " + pos + " record length " + length);
/* 106:    */       
/* 107:226 */       throw e;
/* 108:    */     }
/* 109:228 */     return ret;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getPos()
/* 113:    */   {
/* 114:238 */     return this.filePos;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setPos(int p)
/* 118:    */   {
/* 119:254 */     this.oldPos = this.filePos;
/* 120:255 */     this.filePos = p;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void restorePos()
/* 124:    */   {
/* 125:266 */     this.filePos = this.oldPos;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private void moveToFirstBof()
/* 129:    */   {
/* 130:274 */     boolean bofFound = false;
/* 131:275 */     while (!bofFound)
/* 132:    */     {
/* 133:277 */       int code = IntegerHelper.getInt(this.data[this.filePos], this.data[(this.filePos + 1)]);
/* 134:278 */       if (code == Type.BOF.value) {
/* 135:280 */         bofFound = true;
/* 136:    */       } else {
/* 137:284 */         skip(128);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   /**
/* 143:    */    * @deprecated
/* 144:    */    */
/* 145:    */   public void close() {}
/* 146:    */   
/* 147:    */   public void clear()
/* 148:    */   {
/* 149:303 */     this.data = null;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean hasNext()
/* 153:    */   {
/* 154:314 */     return this.filePos < this.data.length - 4;
/* 155:    */   }
/* 156:    */   
/* 157:    */   CompoundFile getCompoundFile()
/* 158:    */   {
/* 159:326 */     return this.compoundFile;
/* 160:    */   }
/* 161:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.File
 * JD-Core Version:    0.7.0.1
 */