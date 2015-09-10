/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ class NodeStatusRequest
/*  4:   */   extends NameServicePacket
/*  5:   */ {
/*  6:   */   NodeStatusRequest(Name name)
/*  7:   */   {
/*  8:24 */     this.questionName = name;
/*  9:25 */     this.questionType = 33;
/* 10:26 */     this.isRecurDesired = false;
/* 11:27 */     this.isBroadcast = false;
/* 12:   */   }
/* 13:   */   
/* 14:   */   int writeBodyWireFormat(byte[] dst, int dstIndex)
/* 15:   */   {
/* 16:31 */     int tmp = this.questionName.hexCode;
/* 17:32 */     this.questionName.hexCode = 0;
/* 18:33 */     int result = writeQuestionSectionWireFormat(dst, dstIndex);
/* 19:34 */     this.questionName.hexCode = tmp;
/* 20:35 */     return result;
/* 21:   */   }
/* 22:   */   
/* 23:   */   int readBodyWireFormat(byte[] src, int srcIndex)
/* 24:   */   {
/* 25:38 */     return 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   int writeRDataWireFormat(byte[] dst, int dstIndex)
/* 29:   */   {
/* 30:41 */     return 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   int readRDataWireFormat(byte[] src, int srcIndex)
/* 34:   */   {
/* 35:44 */     return 0;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:47 */     return new String("NodeStatusRequest[" + super.toString() + "]");
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NodeStatusRequest
 * JD-Core Version:    0.7.0.1
 */