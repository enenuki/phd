/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ class Trans2GetDfsReferralResponse
/*   4:    */   extends SmbComTransactionResponse
/*   5:    */ {
/*   6:    */   int pathConsumed;
/*   7:    */   int numReferrals;
/*   8:    */   int flags;
/*   9:    */   Referral[] referrals;
/*  10:    */   
/*  11:    */   class Referral
/*  12:    */   {
/*  13:    */     private int version;
/*  14:    */     private int size;
/*  15:    */     private int serverType;
/*  16:    */     private int flags;
/*  17:    */     private int proximity;
/*  18:    */     private int pathOffset;
/*  19:    */     private int altPathOffset;
/*  20:    */     private int nodeOffset;
/*  21:    */     private String altPath;
/*  22:    */     int ttl;
/*  23: 37 */     String path = null;
/*  24: 38 */     String node = null;
/*  25:    */     
/*  26:    */     Referral() {}
/*  27:    */     
/*  28:    */     int readWireFormat(byte[] buffer, int bufferIndex, int len)
/*  29:    */     {
/*  30: 41 */       int start = bufferIndex;
/*  31:    */       
/*  32: 43 */       this.version = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  33: 44 */       if ((this.version != 3) && (this.version != 1)) {
/*  34: 45 */         throw new RuntimeException("Version " + this.version + " referral not supported. Please report this to jcifs at samba dot org.");
/*  35:    */       }
/*  36: 47 */       bufferIndex += 2;
/*  37: 48 */       this.size = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  38: 49 */       bufferIndex += 2;
/*  39: 50 */       this.serverType = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  40: 51 */       bufferIndex += 2;
/*  41: 52 */       this.flags = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  42: 53 */       bufferIndex += 2;
/*  43: 54 */       if (this.version == 3)
/*  44:    */       {
/*  45: 55 */         this.proximity = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  46: 56 */         bufferIndex += 2;
/*  47: 57 */         this.ttl = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  48: 58 */         bufferIndex += 2;
/*  49: 59 */         this.pathOffset = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  50: 60 */         bufferIndex += 2;
/*  51: 61 */         this.altPathOffset = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  52: 62 */         bufferIndex += 2;
/*  53: 63 */         this.nodeOffset = ServerMessageBlock.readInt2(buffer, bufferIndex);
/*  54: 64 */         bufferIndex += 2;
/*  55:    */         
/*  56: 66 */         this.path = Trans2GetDfsReferralResponse.this.readString(buffer, start + this.pathOffset, len, (Trans2GetDfsReferralResponse.this.flags2 & 0x8000) != 0);
/*  57: 67 */         if (this.nodeOffset > 0) {
/*  58: 68 */           this.node = Trans2GetDfsReferralResponse.this.readString(buffer, start + this.nodeOffset, len, (Trans2GetDfsReferralResponse.this.flags2 & 0x8000) != 0);
/*  59:    */         }
/*  60:    */       }
/*  61: 69 */       else if (this.version == 1)
/*  62:    */       {
/*  63: 70 */         this.node = Trans2GetDfsReferralResponse.this.readString(buffer, bufferIndex, len, (Trans2GetDfsReferralResponse.this.flags2 & 0x8000) != 0);
/*  64:    */       }
/*  65: 73 */       return this.size;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public String toString()
/*  69:    */     {
/*  70: 77 */       return new String("Referral[version=" + this.version + ",size=" + this.size + ",serverType=" + this.serverType + ",flags=" + this.flags + ",proximity=" + this.proximity + ",ttl=" + this.ttl + ",pathOffset=" + this.pathOffset + ",altPathOffset=" + this.altPathOffset + ",nodeOffset=" + this.nodeOffset + ",path=" + this.path + ",altPath=" + this.altPath + ",node=" + this.node + "]");
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   Trans2GetDfsReferralResponse()
/*  75:    */   {
/*  76: 93 */     this.subCommand = 16;
/*  77:    */   }
/*  78:    */   
/*  79:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  80:    */   {
/*  81: 97 */     return 0;
/*  82:    */   }
/*  83:    */   
/*  84:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/*  85:    */   {
/*  86:100 */     return 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/*  90:    */   {
/*  91:103 */     return 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/*  95:    */   {
/*  96:106 */     return 0;
/*  97:    */   }
/*  98:    */   
/*  99:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/* 100:    */   {
/* 101:109 */     return 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/* 105:    */   {
/* 106:112 */     int start = bufferIndex;
/* 107:    */     
/* 108:114 */     this.pathConsumed = readInt2(buffer, bufferIndex);
/* 109:115 */     bufferIndex += 2;
/* 110:120 */     if ((this.flags2 & 0x8000) != 0) {
/* 111:121 */       this.pathConsumed /= 2;
/* 112:    */     }
/* 113:123 */     this.numReferrals = readInt2(buffer, bufferIndex);
/* 114:124 */     bufferIndex += 2;
/* 115:125 */     this.flags = readInt2(buffer, bufferIndex);
/* 116:126 */     bufferIndex += 4;
/* 117:    */     
/* 118:128 */     this.referrals = new Referral[this.numReferrals];
/* 119:129 */     for (int ri = 0; ri < this.numReferrals; ri++)
/* 120:    */     {
/* 121:130 */       this.referrals[ri] = new Referral();
/* 122:131 */       bufferIndex += this.referrals[ri].readWireFormat(buffer, bufferIndex, len);
/* 123:    */     }
/* 124:134 */     return bufferIndex - start;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:137 */     return new String("Trans2GetDfsReferralResponse[" + super.toString() + ",pathConsumed=" + this.pathConsumed + ",numReferrals=" + this.numReferrals + ",flags=" + this.flags + "]");
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2GetDfsReferralResponse
 * JD-Core Version:    0.7.0.1
 */