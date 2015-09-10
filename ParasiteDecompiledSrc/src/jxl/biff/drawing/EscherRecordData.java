/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import jxl.biff.IntegerHelper;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ final class EscherRecordData
/*   7:    */ {
/*   8: 36 */   private static Logger logger = Logger.getLogger(EscherRecordData.class);
/*   9:    */   private int pos;
/*  10:    */   private int instance;
/*  11:    */   private int version;
/*  12:    */   private int recordId;
/*  13:    */   private int length;
/*  14:    */   private int streamLength;
/*  15:    */   private boolean container;
/*  16:    */   private EscherRecordType type;
/*  17:    */   private EscherStream escherStream;
/*  18:    */   
/*  19:    */   public EscherRecordData(EscherStream dg, int p)
/*  20:    */   {
/*  21: 92 */     this.escherStream = dg;
/*  22: 93 */     this.pos = p;
/*  23: 94 */     byte[] data = this.escherStream.getData();
/*  24:    */     
/*  25: 96 */     this.streamLength = data.length;
/*  26:    */     
/*  27:    */ 
/*  28: 99 */     int value = IntegerHelper.getInt(data[this.pos], data[(this.pos + 1)]);
/*  29:    */     
/*  30:    */ 
/*  31:102 */     this.instance = ((value & 0xFFF0) >> 4);
/*  32:    */     
/*  33:    */ 
/*  34:105 */     this.version = (value & 0xF);
/*  35:    */     
/*  36:    */ 
/*  37:108 */     this.recordId = IntegerHelper.getInt(data[(this.pos + 2)], data[(this.pos + 3)]);
/*  38:    */     
/*  39:    */ 
/*  40:111 */     this.length = IntegerHelper.getInt(data[(this.pos + 4)], data[(this.pos + 5)], data[(this.pos + 6)], data[(this.pos + 7)]);
/*  41:114 */     if (this.version == 15) {
/*  42:116 */       this.container = true;
/*  43:    */     } else {
/*  44:120 */       this.container = false;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public EscherRecordData(EscherRecordType t)
/*  49:    */   {
/*  50:131 */     this.type = t;
/*  51:132 */     this.recordId = this.type.getValue();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isContainer()
/*  55:    */   {
/*  56:142 */     return this.container;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getLength()
/*  60:    */   {
/*  61:152 */     return this.length;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getRecordId()
/*  65:    */   {
/*  66:162 */     return this.recordId;
/*  67:    */   }
/*  68:    */   
/*  69:    */   EscherStream getDrawingGroup()
/*  70:    */   {
/*  71:172 */     return this.escherStream;
/*  72:    */   }
/*  73:    */   
/*  74:    */   int getPos()
/*  75:    */   {
/*  76:182 */     return this.pos;
/*  77:    */   }
/*  78:    */   
/*  79:    */   EscherRecordType getType()
/*  80:    */   {
/*  81:192 */     if (this.type == null) {
/*  82:194 */       this.type = EscherRecordType.getType(this.recordId);
/*  83:    */     }
/*  84:197 */     return this.type;
/*  85:    */   }
/*  86:    */   
/*  87:    */   int getInstance()
/*  88:    */   {
/*  89:207 */     return this.instance;
/*  90:    */   }
/*  91:    */   
/*  92:    */   void setContainer(boolean c)
/*  93:    */   {
/*  94:218 */     this.container = c;
/*  95:    */   }
/*  96:    */   
/*  97:    */   void setInstance(int inst)
/*  98:    */   {
/*  99:228 */     this.instance = inst;
/* 100:    */   }
/* 101:    */   
/* 102:    */   void setLength(int l)
/* 103:    */   {
/* 104:238 */     this.length = l;
/* 105:    */   }
/* 106:    */   
/* 107:    */   void setVersion(int v)
/* 108:    */   {
/* 109:248 */     this.version = v;
/* 110:    */   }
/* 111:    */   
/* 112:    */   byte[] setHeaderData(byte[] d)
/* 113:    */   {
/* 114:260 */     byte[] data = new byte[d.length + 8];
/* 115:261 */     System.arraycopy(d, 0, data, 8, d.length);
/* 116:263 */     if (this.container) {
/* 117:265 */       this.version = 15;
/* 118:    */     }
/* 119:269 */     int value = this.instance << 4;
/* 120:270 */     value |= this.version;
/* 121:271 */     IntegerHelper.getTwoBytes(value, data, 0);
/* 122:    */     
/* 123:    */ 
/* 124:274 */     IntegerHelper.getTwoBytes(this.recordId, data, 2);
/* 125:    */     
/* 126:    */ 
/* 127:277 */     IntegerHelper.getFourBytes(d.length, data, 4);
/* 128:    */     
/* 129:279 */     return data;
/* 130:    */   }
/* 131:    */   
/* 132:    */   EscherStream getEscherStream()
/* 133:    */   {
/* 134:289 */     return this.escherStream;
/* 135:    */   }
/* 136:    */   
/* 137:    */   byte[] getBytes()
/* 138:    */   {
/* 139:299 */     byte[] d = new byte[this.length];
/* 140:300 */     System.arraycopy(this.escherStream.getData(), this.pos + 8, d, 0, this.length);
/* 141:301 */     return d;
/* 142:    */   }
/* 143:    */   
/* 144:    */   int getStreamLength()
/* 145:    */   {
/* 146:311 */     return this.streamLength;
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.EscherRecordData
 * JD-Core Version:    0.7.0.1
 */