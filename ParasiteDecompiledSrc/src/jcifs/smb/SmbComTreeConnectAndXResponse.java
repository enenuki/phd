/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.UnsupportedEncodingException;
/*  4:   */ 
/*  5:   */ class SmbComTreeConnectAndXResponse
/*  6:   */   extends AndXServerMessageBlock
/*  7:   */ {
/*  8:   */   private static final int SMB_SUPPORT_SEARCH_BITS = 1;
/*  9:   */   private static final int SMB_SHARE_IS_IN_DFS = 2;
/* 10:   */   boolean supportSearchBits;
/* 11:   */   boolean shareIsInDfs;
/* 12:   */   String service;
/* 13:29 */   String nativeFileSystem = "";
/* 14:   */   
/* 15:   */   SmbComTreeConnectAndXResponse(ServerMessageBlock andx)
/* 16:   */   {
/* 17:32 */     super(andx);
/* 18:   */   }
/* 19:   */   
/* 20:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 21:   */   {
/* 22:36 */     return 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 26:   */   {
/* 27:39 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 31:   */   {
/* 32:42 */     this.supportSearchBits = ((buffer[bufferIndex] & 0x1) == 1);
/* 33:43 */     this.shareIsInDfs = ((buffer[bufferIndex] & 0x2) == 2);
/* 34:44 */     return 2;
/* 35:   */   }
/* 36:   */   
/* 37:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 38:   */   {
/* 39:47 */     int start = bufferIndex;
/* 40:   */     
/* 41:49 */     int len = readStringLength(buffer, bufferIndex, 32);
/* 42:   */     try
/* 43:   */     {
/* 44:51 */       this.service = new String(buffer, bufferIndex, len, "ASCII");
/* 45:   */     }
/* 46:   */     catch (UnsupportedEncodingException uee)
/* 47:   */     {
/* 48:53 */       return 0;
/* 49:   */     }
/* 50:55 */     bufferIndex += len + 1;
/* 51:   */     
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:65 */     return bufferIndex - start;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String toString()
/* 64:   */   {
/* 65:68 */     String result = new String("SmbComTreeConnectAndXResponse[" + super.toString() + ",supportSearchBits=" + this.supportSearchBits + ",shareIsInDfs=" + this.shareIsInDfs + ",service=" + this.service + ",nativeFileSystem=" + this.nativeFileSystem + "]");
/* 66:   */     
/* 67:   */ 
/* 68:   */ 
/* 69:   */ 
/* 70:   */ 
/* 71:74 */     return result;
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComTreeConnectAndXResponse
 * JD-Core Version:    0.7.0.1
 */