/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.util.Arrays;
/*   6:    */ 
/*   7:    */ public class PNGReader
/*   8:    */ {
/*   9:    */   private byte[] pngData;
/*  10:    */   private Chunk ihdr;
/*  11:    */   private Chunk phys;
/*  12:    */   private int pixelWidth;
/*  13:    */   private int pixelHeight;
/*  14:    */   private int verticalResolution;
/*  15:    */   private int horizontalResolution;
/*  16:    */   private int resolutionUnit;
/*  17: 39 */   private static byte[] PNG_MAGIC_NUMBER = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*  18:    */   
/*  19:    */   public PNGReader(byte[] data)
/*  20:    */   {
/*  21: 45 */     this.pngData = data;
/*  22:    */   }
/*  23:    */   
/*  24:    */   void read()
/*  25:    */   {
/*  26: 51 */     byte[] header = new byte[PNG_MAGIC_NUMBER.length];
/*  27: 52 */     System.arraycopy(this.pngData, 0, header, 0, header.length);
/*  28: 53 */     boolean pngFile = Arrays.equals(PNG_MAGIC_NUMBER, header);
/*  29: 54 */     if (!pngFile) {
/*  30: 56 */       return;
/*  31:    */     }
/*  32: 59 */     int pos = 8;
/*  33: 60 */     while (pos < this.pngData.length)
/*  34:    */     {
/*  35: 62 */       int length = getInt(this.pngData[pos], this.pngData[(pos + 1)], this.pngData[(pos + 2)], this.pngData[(pos + 3)]);
/*  36:    */       
/*  37:    */ 
/*  38:    */ 
/*  39: 66 */       ChunkType chunkType = ChunkType.getChunkType(this.pngData[(pos + 4)], this.pngData[(pos + 5)], this.pngData[(pos + 6)], this.pngData[(pos + 7)]);
/*  40: 71 */       if (chunkType == ChunkType.IHDR) {
/*  41: 73 */         this.ihdr = new Chunk(pos + 8, length, chunkType, this.pngData);
/*  42: 75 */       } else if (chunkType == ChunkType.PHYS) {
/*  43: 77 */         this.phys = new Chunk(pos + 8, length, chunkType, this.pngData);
/*  44:    */       }
/*  45: 80 */       pos += length + 12;
/*  46:    */     }
/*  47: 84 */     byte[] ihdrData = this.ihdr.getData();
/*  48: 85 */     this.pixelWidth = getInt(ihdrData[0], ihdrData[1], ihdrData[2], ihdrData[3]);
/*  49: 86 */     this.pixelHeight = getInt(ihdrData[4], ihdrData[5], ihdrData[6], ihdrData[7]);
/*  50: 88 */     if (this.phys != null)
/*  51:    */     {
/*  52: 90 */       byte[] physData = this.phys.getData();
/*  53: 91 */       this.resolutionUnit = physData[8];
/*  54: 92 */       this.horizontalResolution = getInt(physData[0], physData[1], physData[2], physData[3]);
/*  55:    */       
/*  56: 94 */       this.verticalResolution = getInt(physData[4], physData[5], physData[6], physData[7]);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   private int getInt(byte d1, byte d2, byte d3, byte d4)
/*  61:    */   {
/*  62:102 */     int i1 = d1 & 0xFF;
/*  63:103 */     int i2 = d2 & 0xFF;
/*  64:104 */     int i3 = d3 & 0xFF;
/*  65:105 */     int i4 = d4 & 0xFF;
/*  66:    */     
/*  67:107 */     int val = i1 << 24 | i2 << 16 | i3 << 8 | i4;
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:112 */     return val;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getHeight()
/*  76:    */   {
/*  77:117 */     return this.pixelHeight;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getWidth()
/*  81:    */   {
/*  82:122 */     return this.pixelWidth;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getHorizontalResolution()
/*  86:    */   {
/*  87:128 */     return this.resolutionUnit == 1 ? this.horizontalResolution : 0;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getVerticalResolution()
/*  91:    */   {
/*  92:134 */     return this.resolutionUnit == 1 ? this.verticalResolution : 0;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void main(String[] args)
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:141 */       File f = new File(args[0]);
/* 100:142 */       int size = (int)f.length();
/* 101:    */       
/* 102:144 */       byte[] data = new byte[size];
/* 103:    */       
/* 104:146 */       FileInputStream fis = new FileInputStream(f);
/* 105:147 */       fis.read(data);
/* 106:148 */       fis.close();
/* 107:149 */       PNGReader reader = new PNGReader(data);
/* 108:150 */       reader.read();
/* 109:    */     }
/* 110:    */     catch (Throwable t)
/* 111:    */     {
/* 112:154 */       t.printStackTrace();
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.PNGReader
 * JD-Core Version:    0.7.0.1
 */