/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.common.Assert;
/*   6:    */ import jxl.common.Logger;
/*   7:    */ 
/*   8:    */ class BlipStoreEntry
/*   9:    */   extends EscherAtom
/*  10:    */ {
/*  11: 37 */   private static Logger logger = Logger.getLogger(BlipStoreEntry.class);
/*  12:    */   private BlipType type;
/*  13:    */   private byte[] data;
/*  14:    */   private int imageDataLength;
/*  15:    */   private int referenceCount;
/*  16:    */   private boolean write;
/*  17:    */   private static final int IMAGE_DATA_OFFSET = 61;
/*  18:    */   
/*  19:    */   public BlipStoreEntry(EscherRecordData erd)
/*  20:    */   {
/*  21: 77 */     super(erd);
/*  22: 78 */     this.type = BlipType.getType(getInstance());
/*  23: 79 */     this.write = false;
/*  24: 80 */     byte[] bytes = getBytes();
/*  25: 81 */     this.referenceCount = IntegerHelper.getInt(bytes[24], bytes[25], bytes[26], bytes[27]);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public BlipStoreEntry(Drawing d)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 93 */     super(EscherRecordType.BSE);
/*  32: 94 */     this.type = BlipType.PNG;
/*  33: 95 */     setVersion(2);
/*  34: 96 */     setInstance(this.type.getValue());
/*  35:    */     
/*  36: 98 */     byte[] imageData = d.getImageBytes();
/*  37: 99 */     this.imageDataLength = imageData.length;
/*  38:100 */     this.data = new byte[this.imageDataLength + 61];
/*  39:101 */     System.arraycopy(imageData, 0, this.data, 61, this.imageDataLength);
/*  40:102 */     this.referenceCount = d.getReferenceCount();
/*  41:103 */     this.write = true;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public BlipType getBlipType()
/*  45:    */   {
/*  46:113 */     return this.type;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public byte[] getData()
/*  50:    */   {
/*  51:123 */     if (this.write)
/*  52:    */     {
/*  53:128 */       this.data[0] = ((byte)this.type.getValue());
/*  54:    */       
/*  55:    */ 
/*  56:131 */       this.data[1] = ((byte)this.type.getValue());
/*  57:    */       
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:140 */       IntegerHelper.getFourBytes(this.imageDataLength + 8 + 17, this.data, 20);
/*  66:    */       
/*  67:    */ 
/*  68:143 */       IntegerHelper.getFourBytes(this.referenceCount, this.data, 24);
/*  69:    */       
/*  70:    */ 
/*  71:146 */       IntegerHelper.getFourBytes(0, this.data, 28);
/*  72:    */       
/*  73:    */ 
/*  74:149 */       this.data[32] = 0;
/*  75:    */       
/*  76:    */ 
/*  77:152 */       this.data[33] = 0;
/*  78:    */       
/*  79:    */ 
/*  80:155 */       this.data[34] = 126;
/*  81:156 */       this.data[35] = 1;
/*  82:    */       
/*  83:    */ 
/*  84:159 */       this.data[36] = 0;
/*  85:160 */       this.data[37] = 110;
/*  86:    */       
/*  87:    */ 
/*  88:163 */       IntegerHelper.getTwoBytes(61470, this.data, 38);
/*  89:    */       
/*  90:    */ 
/*  91:    */ 
/*  92:167 */       IntegerHelper.getFourBytes(this.imageDataLength + 17, this.data, 40);
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:175 */       this.data = getBytes();
/*  97:    */     }
/*  98:178 */     return setHeaderData(this.data);
/*  99:    */   }
/* 100:    */   
/* 101:    */   void dereference()
/* 102:    */   {
/* 103:187 */     this.referenceCount -= 1;
/* 104:188 */     Assert.verify(this.referenceCount >= 0);
/* 105:    */   }
/* 106:    */   
/* 107:    */   int getReferenceCount()
/* 108:    */   {
/* 109:198 */     return this.referenceCount;
/* 110:    */   }
/* 111:    */   
/* 112:    */   byte[] getImageData()
/* 113:    */   {
/* 114:208 */     byte[] allData = getBytes();
/* 115:209 */     byte[] imageData = new byte[allData.length - 61];
/* 116:210 */     System.arraycopy(allData, 61, imageData, 0, imageData.length);
/* 117:    */     
/* 118:212 */     return imageData;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.BlipStoreEntry
 * JD-Core Version:    0.7.0.1
 */