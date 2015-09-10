/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ class SessionRetargetResponsePacket
/*  7:   */   extends SessionServicePacket
/*  8:   */ {
/*  9:   */   private NbtAddress retargetAddress;
/* 10:   */   private int retargetPort;
/* 11:   */   
/* 12:   */   SessionRetargetResponsePacket()
/* 13:   */   {
/* 14:30 */     this.type = 132;
/* 15:31 */     this.length = 6;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int writeTrailerWireFormat(byte[] dst, int dstIndex)
/* 19:   */   {
/* 20:35 */     return 0;
/* 21:   */   }
/* 22:   */   
/* 23:   */   int readTrailerWireFormat(InputStream in, byte[] buffer, int bufferIndex)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:41 */     if (in.read(buffer, bufferIndex, this.length) != this.length) {
/* 27:42 */       throw new IOException("unexpected EOF reading netbios retarget session response");
/* 28:   */     }
/* 29:44 */     int addr = readInt4(buffer, bufferIndex);
/* 30:45 */     bufferIndex += 4;
/* 31:46 */     this.retargetAddress = new NbtAddress(null, addr, false, 0);
/* 32:47 */     this.retargetPort = readInt2(buffer, bufferIndex);
/* 33:48 */     return this.length;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.SessionRetargetResponsePacket
 * JD-Core Version:    0.7.0.1
 */