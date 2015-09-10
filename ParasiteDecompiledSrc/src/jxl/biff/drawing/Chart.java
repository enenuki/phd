/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.biff.ByteData;
/*   5:    */ import jxl.biff.IndexMapping;
/*   6:    */ import jxl.biff.IntegerHelper;
/*   7:    */ import jxl.biff.Type;
/*   8:    */ import jxl.common.Assert;
/*   9:    */ import jxl.common.Logger;
/*  10:    */ import jxl.read.biff.File;
/*  11:    */ import jxl.read.biff.Record;
/*  12:    */ 
/*  13:    */ public class Chart
/*  14:    */   implements ByteData, EscherStream
/*  15:    */ {
/*  16: 41 */   private static final Logger logger = Logger.getLogger(Chart.class);
/*  17:    */   private MsoDrawingRecord msoDrawingRecord;
/*  18:    */   private ObjRecord objRecord;
/*  19:    */   private int startpos;
/*  20:    */   private int endpos;
/*  21:    */   private File file;
/*  22:    */   private DrawingData drawingData;
/*  23:    */   private int drawingNumber;
/*  24:    */   private byte[] data;
/*  25:    */   private boolean initialized;
/*  26:    */   private WorkbookSettings workbookSettings;
/*  27:    */   
/*  28:    */   public Chart(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, int sp, int ep, File f, WorkbookSettings ws)
/*  29:    */   {
/*  30:109 */     this.msoDrawingRecord = mso;
/*  31:110 */     this.objRecord = obj;
/*  32:111 */     this.startpos = sp;
/*  33:112 */     this.endpos = ep;
/*  34:113 */     this.file = f;
/*  35:114 */     this.workbookSettings = ws;
/*  36:119 */     if (this.msoDrawingRecord != null)
/*  37:    */     {
/*  38:121 */       this.drawingData = dd;
/*  39:122 */       this.drawingData.addData(this.msoDrawingRecord.getRecord().getData());
/*  40:123 */       this.drawingNumber = (this.drawingData.getNumDrawings() - 1);
/*  41:    */     }
/*  42:126 */     this.initialized = false;
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:131 */     Assert.verify(((mso != null) && (obj != null)) || ((mso == null) && (obj == null)));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public byte[] getBytes()
/*  51:    */   {
/*  52:142 */     if (!this.initialized) {
/*  53:144 */       initialize();
/*  54:    */     }
/*  55:147 */     return this.data;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public byte[] getData()
/*  59:    */   {
/*  60:157 */     return this.msoDrawingRecord.getRecord().getData();
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void initialize()
/*  64:    */   {
/*  65:165 */     this.data = this.file.read(this.startpos, this.endpos - this.startpos);
/*  66:166 */     this.initialized = true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping)
/*  70:    */   {
/*  71:179 */     if (!this.initialized) {
/*  72:181 */       initialize();
/*  73:    */     }
/*  74:187 */     int pos = 0;
/*  75:188 */     int code = 0;
/*  76:189 */     int length = 0;
/*  77:190 */     Type type = null;
/*  78:191 */     while (pos < this.data.length)
/*  79:    */     {
/*  80:193 */       code = IntegerHelper.getInt(this.data[pos], this.data[(pos + 1)]);
/*  81:194 */       length = IntegerHelper.getInt(this.data[(pos + 2)], this.data[(pos + 3)]);
/*  82:    */       
/*  83:196 */       type = Type.getType(code);
/*  84:198 */       if (type == Type.FONTX)
/*  85:    */       {
/*  86:200 */         int fontind = IntegerHelper.getInt(this.data[(pos + 4)], this.data[(pos + 5)]);
/*  87:201 */         IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind), this.data, pos + 4);
/*  88:    */       }
/*  89:204 */       else if (type == Type.FBI)
/*  90:    */       {
/*  91:206 */         int fontind = IntegerHelper.getInt(this.data[(pos + 12)], this.data[(pos + 13)]);
/*  92:207 */         IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind), this.data, pos + 12);
/*  93:    */       }
/*  94:210 */       else if (type == Type.IFMT)
/*  95:    */       {
/*  96:212 */         int formind = IntegerHelper.getInt(this.data[(pos + 4)], this.data[(pos + 5)]);
/*  97:213 */         IntegerHelper.getTwoBytes(formatMapping.getNewIndex(formind), this.data, pos + 4);
/*  98:    */       }
/*  99:216 */       else if (type == Type.ALRUNS)
/* 100:    */       {
/* 101:218 */         int numRuns = IntegerHelper.getInt(this.data[(pos + 4)], this.data[(pos + 5)]);
/* 102:219 */         int fontPos = pos + 6;
/* 103:220 */         for (int i = 0; i < numRuns; i++)
/* 104:    */         {
/* 105:222 */           int fontind = IntegerHelper.getInt(this.data[(fontPos + 2)], this.data[(fontPos + 3)]);
/* 106:    */           
/* 107:224 */           IntegerHelper.getTwoBytes(fontMapping.getNewIndex(fontind), this.data, fontPos + 2);
/* 108:    */           
/* 109:226 */           fontPos += 4;
/* 110:    */         }
/* 111:    */       }
/* 112:230 */       pos += length + 4;
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   EscherContainer getSpContainer()
/* 117:    */   {
/* 118:241 */     EscherContainer spContainer = this.drawingData.getSpContainer(this.drawingNumber);
/* 119:    */     
/* 120:243 */     return spContainer;
/* 121:    */   }
/* 122:    */   
/* 123:    */   MsoDrawingRecord getMsoDrawingRecord()
/* 124:    */   {
/* 125:253 */     return this.msoDrawingRecord;
/* 126:    */   }
/* 127:    */   
/* 128:    */   ObjRecord getObjRecord()
/* 129:    */   {
/* 130:263 */     return this.objRecord;
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.Chart
 * JD-Core Version:    0.7.0.1
 */