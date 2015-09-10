/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.util.Hexdump;
/*   4:    */ import jcifs.util.LogStream;
/*   5:    */ 
/*   6:    */ class NetServerEnum2Response
/*   7:    */   extends SmbComTransactionResponse
/*   8:    */ {
/*   9:    */   private int converter;
/*  10:    */   private int totalAvailableEntries;
/*  11:    */   String lastName;
/*  12:    */   
/*  13:    */   class ServerInfo1
/*  14:    */     implements FileEntry
/*  15:    */   {
/*  16:    */     String name;
/*  17:    */     int versionMajor;
/*  18:    */     int versionMinor;
/*  19:    */     int type;
/*  20:    */     String commentOrMasterBrowser;
/*  21:    */     
/*  22:    */     ServerInfo1() {}
/*  23:    */     
/*  24:    */     public String getName()
/*  25:    */     {
/*  26: 36 */       return this.name;
/*  27:    */     }
/*  28:    */     
/*  29:    */     public int getType()
/*  30:    */     {
/*  31: 39 */       return (this.type & 0x80000000) != 0 ? 2 : 4;
/*  32:    */     }
/*  33:    */     
/*  34:    */     public int getAttributes()
/*  35:    */     {
/*  36: 42 */       return 17;
/*  37:    */     }
/*  38:    */     
/*  39:    */     public long createTime()
/*  40:    */     {
/*  41: 45 */       return 0L;
/*  42:    */     }
/*  43:    */     
/*  44:    */     public long lastModified()
/*  45:    */     {
/*  46: 48 */       return 0L;
/*  47:    */     }
/*  48:    */     
/*  49:    */     public long length()
/*  50:    */     {
/*  51: 51 */       return 0L;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public String toString()
/*  55:    */     {
/*  56: 55 */       return new String("ServerInfo1[name=" + this.name + ",versionMajor=" + this.versionMajor + ",versionMinor=" + this.versionMinor + ",type=0x" + Hexdump.toHexString(this.type, 8) + ",commentOrMasterBrowser=" + this.commentOrMasterBrowser + "]");
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  61:    */   {
/*  62: 72 */     return 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/*  66:    */   {
/*  67: 75 */     return 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/*  71:    */   {
/*  72: 78 */     return 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/*  76:    */   {
/*  77: 81 */     return 0;
/*  78:    */   }
/*  79:    */   
/*  80:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/*  81:    */   {
/*  82: 84 */     int start = bufferIndex;
/*  83:    */     
/*  84: 86 */     this.status = readInt2(buffer, bufferIndex);
/*  85: 87 */     bufferIndex += 2;
/*  86: 88 */     this.converter = readInt2(buffer, bufferIndex);
/*  87: 89 */     bufferIndex += 2;
/*  88: 90 */     this.numEntries = readInt2(buffer, bufferIndex);
/*  89: 91 */     bufferIndex += 2;
/*  90: 92 */     this.totalAvailableEntries = readInt2(buffer, bufferIndex);
/*  91: 93 */     bufferIndex += 2;
/*  92:    */     
/*  93: 95 */     return bufferIndex - start;
/*  94:    */   }
/*  95:    */   
/*  96:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/*  97:    */   {
/*  98: 98 */     int start = bufferIndex;
/*  99: 99 */     ServerInfo1 e = null;
/* 100:    */     
/* 101:101 */     this.results = new ServerInfo1[this.numEntries];
/* 102:102 */     for (int i = 0; i < this.numEntries; i++)
/* 103:    */     {
/* 104:103 */       void tmp43_40 = new ServerInfo1();e = tmp43_40;this.results[i] = tmp43_40;
/* 105:104 */       e.name = readString(buffer, bufferIndex, 16, false);
/* 106:105 */       bufferIndex += 16;
/* 107:106 */       e.versionMajor = (buffer[(bufferIndex++)] & 0xFF);
/* 108:107 */       e.versionMinor = (buffer[(bufferIndex++)] & 0xFF);
/* 109:108 */       e.type = readInt4(buffer, bufferIndex);
/* 110:109 */       bufferIndex += 4;
/* 111:110 */       int off = readInt4(buffer, bufferIndex);
/* 112:111 */       bufferIndex += 4;
/* 113:112 */       off = (off & 0xFFFF) - this.converter;
/* 114:113 */       off = start + off;
/* 115:114 */       e.commentOrMasterBrowser = readString(buffer, off, 48, false);
/* 116:116 */       if (LogStream.level >= 4) {
/* 117:117 */         log.println(e);
/* 118:    */       }
/* 119:    */     }
/* 120:119 */     this.lastName = (this.numEntries == 0 ? null : e.name);
/* 121:    */     
/* 122:121 */     return bufferIndex - start;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String toString()
/* 126:    */   {
/* 127:124 */     return new String("NetServerEnum2Response[" + super.toString() + ",status=" + this.status + ",converter=" + this.converter + ",entriesReturned=" + this.numEntries + ",totalAvailableEntries=" + this.totalAvailableEntries + ",lastName=" + this.lastName + "]");
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NetServerEnum2Response
 * JD-Core Version:    0.7.0.1
 */