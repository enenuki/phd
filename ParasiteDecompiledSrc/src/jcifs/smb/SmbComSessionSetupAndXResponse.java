/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ class SmbComSessionSetupAndXResponse
/*  4:   */   extends AndXServerMessageBlock
/*  5:   */ {
/*  6:25 */   private String nativeOs = "";
/*  7:26 */   private String nativeLanMan = "";
/*  8:27 */   private String primaryDomain = "";
/*  9:   */   boolean isLoggedInAsGuest;
/* 10:30 */   byte[] blob = null;
/* 11:   */   
/* 12:   */   SmbComSessionSetupAndXResponse(ServerMessageBlock andx)
/* 13:   */   {
/* 14:33 */     super(andx);
/* 15:   */   }
/* 16:   */   
/* 17:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 18:   */   {
/* 19:37 */     return 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 23:   */   {
/* 24:40 */     return 0;
/* 25:   */   }
/* 26:   */   
/* 27:   */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 28:   */   {
/* 29:43 */     int start = bufferIndex;
/* 30:44 */     this.isLoggedInAsGuest = ((buffer[bufferIndex] & 0x1) == 1);
/* 31:45 */     bufferIndex += 2;
/* 32:46 */     if (this.extendedSecurity)
/* 33:   */     {
/* 34:47 */       int blobLength = readInt2(buffer, bufferIndex);
/* 35:48 */       bufferIndex += 2;
/* 36:49 */       this.blob = new byte[blobLength];
/* 37:   */     }
/* 38:51 */     return bufferIndex - start;
/* 39:   */   }
/* 40:   */   
/* 41:   */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 42:   */   {
/* 43:54 */     int start = bufferIndex;
/* 44:56 */     if (this.extendedSecurity)
/* 45:   */     {
/* 46:57 */       System.arraycopy(buffer, bufferIndex, this.blob, 0, this.blob.length);
/* 47:58 */       bufferIndex += this.blob.length;
/* 48:   */     }
/* 49:60 */     this.nativeOs = readString(buffer, bufferIndex);
/* 50:61 */     bufferIndex += stringWireLength(this.nativeOs, bufferIndex);
/* 51:62 */     this.nativeLanMan = readString(buffer, bufferIndex, start + this.byteCount, 255, this.useUnicode);
/* 52:63 */     bufferIndex += stringWireLength(this.nativeLanMan, bufferIndex);
/* 53:64 */     if (!this.extendedSecurity)
/* 54:   */     {
/* 55:65 */       this.primaryDomain = readString(buffer, bufferIndex, start + this.byteCount, 255, this.useUnicode);
/* 56:66 */       bufferIndex += stringWireLength(this.primaryDomain, bufferIndex);
/* 57:   */     }
/* 58:69 */     return bufferIndex - start;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String toString()
/* 62:   */   {
/* 63:72 */     String result = new String("SmbComSessionSetupAndXResponse[" + super.toString() + ",isLoggedInAsGuest=" + this.isLoggedInAsGuest + ",nativeOs=" + this.nativeOs + ",nativeLanMan=" + this.nativeLanMan + ",primaryDomain=" + this.primaryDomain + "]");
/* 64:   */     
/* 65:   */ 
/* 66:   */ 
/* 67:   */ 
/* 68:   */ 
/* 69:78 */     return result;
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComSessionSetupAndXResponse
 * JD-Core Version:    0.7.0.1
 */