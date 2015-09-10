/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.format.Colour;
/*   4:    */ import jxl.format.RGB;
/*   5:    */ import jxl.read.biff.Record;
/*   6:    */ 
/*   7:    */ public class PaletteRecord
/*   8:    */   extends WritableRecordData
/*   9:    */ {
/*  10: 34 */   private RGB[] rgbColours = new RGB[56];
/*  11:    */   private boolean dirty;
/*  12:    */   private boolean read;
/*  13:    */   private boolean initialized;
/*  14:    */   private static final int numColours = 56;
/*  15:    */   
/*  16:    */   public PaletteRecord(Record t)
/*  17:    */   {
/*  18: 64 */     super(t);
/*  19:    */     
/*  20: 66 */     this.initialized = false;
/*  21: 67 */     this.dirty = false;
/*  22: 68 */     this.read = true;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public PaletteRecord()
/*  26:    */   {
/*  27: 76 */     super(Type.PALETTE);
/*  28:    */     
/*  29: 78 */     this.initialized = true;
/*  30: 79 */     this.dirty = false;
/*  31: 80 */     this.read = false;
/*  32:    */     
/*  33:    */ 
/*  34: 83 */     Colour[] colours = Colour.getAllColours();
/*  35: 85 */     for (int i = 0; i < colours.length; i++)
/*  36:    */     {
/*  37: 87 */       Colour c = colours[i];
/*  38: 88 */       setColourRGB(c, c.getDefaultRGB().getRed(), c.getDefaultRGB().getGreen(), c.getDefaultRGB().getBlue());
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public byte[] getData()
/*  43:    */   {
/*  44:103 */     if ((this.read) && (!this.dirty)) {
/*  45:105 */       return getRecord().getData();
/*  46:    */     }
/*  47:108 */     byte[] data = new byte['â'];
/*  48:109 */     int pos = 0;
/*  49:    */     
/*  50:    */ 
/*  51:112 */     IntegerHelper.getTwoBytes(56, data, pos);
/*  52:115 */     for (int i = 0; i < 56; i++)
/*  53:    */     {
/*  54:117 */       pos = i * 4 + 2;
/*  55:118 */       data[pos] = ((byte)this.rgbColours[i].getRed());
/*  56:119 */       data[(pos + 1)] = ((byte)this.rgbColours[i].getGreen());
/*  57:120 */       data[(pos + 2)] = ((byte)this.rgbColours[i].getBlue());
/*  58:    */     }
/*  59:123 */     return data;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void initialize()
/*  63:    */   {
/*  64:131 */     byte[] data = getRecord().getData();
/*  65:    */     
/*  66:133 */     int numrecords = IntegerHelper.getInt(data[0], data[1]);
/*  67:135 */     for (int i = 0; i < numrecords; i++)
/*  68:    */     {
/*  69:137 */       int pos = i * 4 + 2;
/*  70:138 */       int red = IntegerHelper.getInt(data[pos], (byte)0);
/*  71:139 */       int green = IntegerHelper.getInt(data[(pos + 1)], (byte)0);
/*  72:140 */       int blue = IntegerHelper.getInt(data[(pos + 2)], (byte)0);
/*  73:141 */       this.rgbColours[i] = new RGB(red, green, blue);
/*  74:    */     }
/*  75:144 */     this.initialized = true;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isDirty()
/*  79:    */   {
/*  80:155 */     return this.dirty;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setColourRGB(Colour c, int r, int g, int b)
/*  84:    */   {
/*  85:169 */     int pos = c.getValue() - 8;
/*  86:170 */     if ((pos < 0) || (pos >= 56)) {
/*  87:172 */       return;
/*  88:    */     }
/*  89:175 */     if (!this.initialized) {
/*  90:177 */       initialize();
/*  91:    */     }
/*  92:181 */     r = setValueRange(r, 0, 255);
/*  93:182 */     g = setValueRange(g, 0, 255);
/*  94:183 */     b = setValueRange(b, 0, 255);
/*  95:    */     
/*  96:185 */     this.rgbColours[pos] = new RGB(r, g, b);
/*  97:    */     
/*  98:    */ 
/*  99:188 */     this.dirty = true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public RGB getColourRGB(Colour c)
/* 103:    */   {
/* 104:200 */     int pos = c.getValue() - 8;
/* 105:201 */     if ((pos < 0) || (pos >= 56)) {
/* 106:203 */       return c.getDefaultRGB();
/* 107:    */     }
/* 108:206 */     if (!this.initialized) {
/* 109:208 */       initialize();
/* 110:    */     }
/* 111:211 */     return this.rgbColours[pos];
/* 112:    */   }
/* 113:    */   
/* 114:    */   private int setValueRange(int val, int min, int max)
/* 115:    */   {
/* 116:224 */     val = Math.max(val, min);
/* 117:225 */     val = Math.min(val, max);
/* 118:226 */     return val;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.PaletteRecord
 * JD-Core Version:    0.7.0.1
 */