/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ class NameQueryResponse
/*  4:   */   extends NameServicePacket
/*  5:   */ {
/*  6:   */   NameQueryResponse()
/*  7:   */   {
/*  8:24 */     this.recordName = new Name();
/*  9:   */   }
/* 10:   */   
/* 11:   */   int writeBodyWireFormat(byte[] dst, int dstIndex)
/* 12:   */   {
/* 13:28 */     return 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int readBodyWireFormat(byte[] src, int srcIndex)
/* 17:   */   {
/* 18:31 */     return readResourceRecordWireFormat(src, srcIndex);
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeRDataWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:34 */     return 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   int readRDataWireFormat(byte[] src, int srcIndex)
/* 27:   */   {
/* 28:37 */     if ((this.resultCode != 0) || (this.opCode != 0)) {
/* 29:38 */       return 0;
/* 30:   */     }
/* 31:40 */     boolean groupName = (src[srcIndex] & 0x80) == 128;
/* 32:41 */     int nodeType = (src[srcIndex] & 0x60) >> 5;
/* 33:42 */     srcIndex += 2;
/* 34:43 */     int address = readInt4(src, srcIndex);
/* 35:44 */     if (address != 0) {
/* 36:45 */       this.addrEntry[this.addrIndex] = new NbtAddress(this.recordName, address, groupName, nodeType);
/* 37:   */     } else {
/* 38:47 */       this.addrEntry[this.addrIndex] = null;
/* 39:   */     }
/* 40:50 */     return 6;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:53 */     return new String("NameQueryResponse[" + super.toString() + ",addrEntry=" + this.addrEntry + "]");
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NameQueryResponse
 * JD-Core Version:    0.7.0.1
 */