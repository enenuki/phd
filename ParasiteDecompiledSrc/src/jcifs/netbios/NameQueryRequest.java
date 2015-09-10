/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ class NameQueryRequest
/*  4:   */   extends NameServicePacket
/*  5:   */ {
/*  6:   */   NameQueryRequest(Name name)
/*  7:   */   {
/*  8:24 */     this.questionName = name;
/*  9:25 */     this.questionType = 32;
/* 10:   */   }
/* 11:   */   
/* 12:   */   int writeBodyWireFormat(byte[] dst, int dstIndex)
/* 13:   */   {
/* 14:29 */     return writeQuestionSectionWireFormat(dst, dstIndex);
/* 15:   */   }
/* 16:   */   
/* 17:   */   int readBodyWireFormat(byte[] src, int srcIndex)
/* 18:   */   {
/* 19:32 */     return readQuestionSectionWireFormat(src, srcIndex);
/* 20:   */   }
/* 21:   */   
/* 22:   */   int writeRDataWireFormat(byte[] dst, int dstIndex)
/* 23:   */   {
/* 24:35 */     return 0;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int readRDataWireFormat(byte[] src, int srcIndex)
/* 28:   */   {
/* 29:38 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:41 */     return new String("NameQueryRequest[" + super.toString() + "]");
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NameQueryRequest
 * JD-Core Version:    0.7.0.1
 */