/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ 
/*   5:    */ class NodeStatusResponse
/*   6:    */   extends NameServicePacket
/*   7:    */ {
/*   8:    */   private NbtAddress queryAddress;
/*   9:    */   private int numberOfNames;
/*  10:    */   private byte[] macAddress;
/*  11:    */   private byte[] stats;
/*  12:    */   NbtAddress[] addressArray;
/*  13:    */   
/*  14:    */   NodeStatusResponse(NbtAddress queryAddress)
/*  15:    */   {
/*  16: 42 */     this.queryAddress = queryAddress;
/*  17: 43 */     this.recordName = new Name();
/*  18: 44 */     this.macAddress = new byte[6];
/*  19:    */   }
/*  20:    */   
/*  21:    */   int writeBodyWireFormat(byte[] dst, int dstIndex)
/*  22:    */   {
/*  23: 48 */     return 0;
/*  24:    */   }
/*  25:    */   
/*  26:    */   int readBodyWireFormat(byte[] src, int srcIndex)
/*  27:    */   {
/*  28: 51 */     return readResourceRecordWireFormat(src, srcIndex);
/*  29:    */   }
/*  30:    */   
/*  31:    */   int writeRDataWireFormat(byte[] dst, int dstIndex)
/*  32:    */   {
/*  33: 54 */     return 0;
/*  34:    */   }
/*  35:    */   
/*  36:    */   int readRDataWireFormat(byte[] src, int srcIndex)
/*  37:    */   {
/*  38: 57 */     int start = srcIndex;
/*  39: 58 */     this.numberOfNames = (src[srcIndex] & 0xFF);
/*  40: 59 */     int namesLength = this.numberOfNames * 18;
/*  41: 60 */     int statsLength = this.rDataLength - namesLength - 1;
/*  42: 61 */     this.numberOfNames = (src[(srcIndex++)] & 0xFF);
/*  43:    */     
/*  44: 63 */     System.arraycopy(src, srcIndex + namesLength, this.macAddress, 0, 6);
/*  45: 64 */     srcIndex += readNodeNameArray(src, srcIndex);
/*  46: 65 */     this.stats = new byte[statsLength];
/*  47: 66 */     System.arraycopy(src, srcIndex, this.stats, 0, statsLength);
/*  48: 67 */     srcIndex += statsLength;
/*  49: 68 */     return srcIndex - start;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private int readNodeNameArray(byte[] src, int srcIndex)
/*  53:    */   {
/*  54: 71 */     int start = srcIndex;
/*  55:    */     
/*  56: 73 */     this.addressArray = new NbtAddress[this.numberOfNames];
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60: 77 */     String scope = this.queryAddress.hostName.scope;
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68: 85 */     boolean addrFound = false;
/*  69:    */     try
/*  70:    */     {
/*  71: 88 */       for (int i = 0; i < this.numberOfNames; i++)
/*  72:    */       {
/*  73: 89 */         for (int j = srcIndex + 14; src[j] == 32; j--) {}
/*  74: 91 */         String n = new String(src, srcIndex, j - srcIndex + 1, Name.OEM_ENCODING);
/*  75: 92 */         int hexCode = src[(srcIndex + 15)] & 0xFF;
/*  76: 93 */         boolean groupName = (src[(srcIndex + 16)] & 0x80) == 128;
/*  77: 94 */         int ownerNodeType = (src[(srcIndex + 16)] & 0x60) >> 5;
/*  78: 95 */         boolean isBeingDeleted = (src[(srcIndex + 16)] & 0x10) == 16;
/*  79: 96 */         boolean isInConflict = (src[(srcIndex + 16)] & 0x8) == 8;
/*  80: 97 */         boolean isActive = (src[(srcIndex + 16)] & 0x4) == 4;
/*  81: 98 */         boolean isPermanent = (src[(srcIndex + 16)] & 0x2) == 2;
/*  82:106 */         if ((!addrFound) && (this.queryAddress.hostName.hexCode == hexCode) && ((this.queryAddress.hostName == NbtAddress.UNKNOWN_NAME) || (this.queryAddress.hostName.name.equals(n))))
/*  83:    */         {
/*  84:110 */           if (this.queryAddress.hostName == NbtAddress.UNKNOWN_NAME) {
/*  85:111 */             this.queryAddress.hostName = new Name(n, hexCode, scope);
/*  86:    */           }
/*  87:113 */           this.queryAddress.groupName = groupName;
/*  88:114 */           this.queryAddress.nodeType = ownerNodeType;
/*  89:115 */           this.queryAddress.isBeingDeleted = isBeingDeleted;
/*  90:116 */           this.queryAddress.isInConflict = isInConflict;
/*  91:117 */           this.queryAddress.isActive = isActive;
/*  92:118 */           this.queryAddress.isPermanent = isPermanent;
/*  93:119 */           this.queryAddress.macAddress = this.macAddress;
/*  94:120 */           this.queryAddress.isDataFromNodeStatus = true;
/*  95:121 */           addrFound = true;
/*  96:122 */           this.addressArray[i] = this.queryAddress;
/*  97:    */         }
/*  98:    */         else
/*  99:    */         {
/* 100:124 */           this.addressArray[i] = new NbtAddress(new Name(n, hexCode, scope), this.queryAddress.address, groupName, ownerNodeType, isBeingDeleted, isInConflict, isActive, isPermanent, this.macAddress);
/* 101:    */         }
/* 102: 88 */         srcIndex += 18;
/* 103:    */       }
/* 104:    */     }
/* 105:    */     catch (UnsupportedEncodingException uee) {}
/* 106:137 */     return srcIndex - start;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String toString()
/* 110:    */   {
/* 111:140 */     return new String("NodeStatusResponse[" + super.toString() + "]");
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NodeStatusResponse
 * JD-Core Version:    0.7.0.1
 */