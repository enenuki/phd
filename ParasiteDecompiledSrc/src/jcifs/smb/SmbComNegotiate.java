/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.UnsupportedEncodingException;
/*  4:   */ 
/*  5:   */ class SmbComNegotiate
/*  6:   */   extends ServerMessageBlock
/*  7:   */ {
/*  8:   */   private static final String DIALECTS = "";
/*  9:   */   
/* 10:   */   SmbComNegotiate()
/* 11:   */   {
/* 12:28 */     this.command = 114;
/* 13:29 */     this.flags2 = SmbConstants.DEFAULT_FLAGS2;
/* 14:   */   }
/* 15:   */   
/* 16:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 17:   */   {
/* 18:33 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 22:   */   {
/* 23:   */     try
/* 24:   */     {
/* 25:38 */       dialects = "".getBytes("ASCII");
/* 26:   */     }
/* 27:   */     catch (UnsupportedEncodingException uee)
/* 28:   */     {
/* 29:   */       byte[] dialects;
/* 30:40 */       return 0;
/* 31:   */     }
/* 32:   */     byte[] dialects;
/* 33:42 */     System.arraycopy(dialects, 0, dst, dstIndex, dialects.length);
/* 34:43 */     return dialects.length;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 38:   */   {
/* 39:46 */     return 0;
/* 40:   */   }
/* 41:   */   
/* 42:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 43:   */   {
/* 44:49 */     return 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:52 */     return new String("SmbComNegotiate[" + super.toString() + ",wordCount=" + this.wordCount + ",dialects=NT LM 0.12]");
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNegotiate
 * JD-Core Version:    0.7.0.1
 */