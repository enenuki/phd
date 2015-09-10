/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ public class SessionRequestPacket
/*  7:   */   extends SessionServicePacket
/*  8:   */ {
/*  9:   */   private Name calledName;
/* 10:   */   private Name callingName;
/* 11:   */   
/* 12:   */   SessionRequestPacket()
/* 13:   */   {
/* 14:29 */     this.calledName = new Name();
/* 15:30 */     this.callingName = new Name();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public SessionRequestPacket(Name calledName, Name callingName)
/* 19:   */   {
/* 20:33 */     this.type = 129;
/* 21:34 */     this.calledName = calledName;
/* 22:35 */     this.callingName = callingName;
/* 23:   */   }
/* 24:   */   
/* 25:   */   int writeTrailerWireFormat(byte[] dst, int dstIndex)
/* 26:   */   {
/* 27:38 */     int start = dstIndex;
/* 28:39 */     dstIndex += this.calledName.writeWireFormat(dst, dstIndex);
/* 29:40 */     dstIndex += this.callingName.writeWireFormat(dst, dstIndex);
/* 30:41 */     return dstIndex - start;
/* 31:   */   }
/* 32:   */   
/* 33:   */   int readTrailerWireFormat(InputStream in, byte[] buffer, int bufferIndex)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:47 */     int start = bufferIndex;
/* 37:48 */     if (in.read(buffer, bufferIndex, this.length) != this.length) {
/* 38:49 */       throw new IOException("invalid session request wire format");
/* 39:   */     }
/* 40:51 */     bufferIndex += this.calledName.readWireFormat(buffer, bufferIndex);
/* 41:52 */     bufferIndex += this.callingName.readWireFormat(buffer, bufferIndex);
/* 42:53 */     return bufferIndex - start;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.SessionRequestPacket
 * JD-Core Version:    0.7.0.1
 */