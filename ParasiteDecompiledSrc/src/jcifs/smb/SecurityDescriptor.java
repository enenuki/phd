/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class SecurityDescriptor
/*  6:   */ {
/*  7:   */   public int type;
/*  8:   */   public ACE[] aces;
/*  9:   */   
/* 10:   */   public SecurityDescriptor() {}
/* 11:   */   
/* 12:   */   public SecurityDescriptor(byte[] buffer, int bufferIndex, int len)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:31 */     decode(buffer, bufferIndex, len);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int decode(byte[] buffer, int bufferIndex, int len)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:34 */     int start = bufferIndex;
/* 22:   */     
/* 23:36 */     bufferIndex++;
/* 24:37 */     bufferIndex++;
/* 25:38 */     this.type = ServerMessageBlock.readInt2(buffer, bufferIndex);
/* 26:39 */     bufferIndex += 2;
/* 27:40 */     ServerMessageBlock.readInt4(buffer, bufferIndex);
/* 28:41 */     bufferIndex += 4;
/* 29:42 */     ServerMessageBlock.readInt4(buffer, bufferIndex);
/* 30:43 */     bufferIndex += 4;
/* 31:44 */     ServerMessageBlock.readInt4(buffer, bufferIndex);
/* 32:45 */     bufferIndex += 4;
/* 33:46 */     int daclOffset = ServerMessageBlock.readInt4(buffer, bufferIndex);
/* 34:   */     
/* 35:48 */     bufferIndex = start + daclOffset;
/* 36:   */     
/* 37:50 */     bufferIndex++;
/* 38:51 */     bufferIndex++;
/* 39:52 */     int size = ServerMessageBlock.readInt2(buffer, bufferIndex);
/* 40:53 */     bufferIndex += 2;
/* 41:54 */     int numAces = ServerMessageBlock.readInt4(buffer, bufferIndex);
/* 42:55 */     bufferIndex += 4;
/* 43:57 */     if (numAces > 4096) {
/* 44:58 */       throw new IOException("Invalid SecurityDescriptor");
/* 45:   */     }
/* 46:60 */     if (daclOffset != 0)
/* 47:   */     {
/* 48:61 */       this.aces = new ACE[numAces];
/* 49:62 */       for (int i = 0; i < numAces; i++)
/* 50:   */       {
/* 51:63 */         this.aces[i] = new ACE();
/* 52:64 */         bufferIndex += this.aces[i].decode(buffer, bufferIndex);
/* 53:   */       }
/* 54:   */     }
/* 55:   */     else
/* 56:   */     {
/* 57:67 */       this.aces = null;
/* 58:   */     }
/* 59:70 */     return bufferIndex - start;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String toString()
/* 63:   */   {
/* 64:73 */     String ret = "SecurityDescriptor:\n";
/* 65:74 */     if (this.aces != null) {
/* 66:75 */       for (int ai = 0; ai < this.aces.length; ai++) {
/* 67:76 */         ret = ret + this.aces[ai].toString() + "\n";
/* 68:   */       }
/* 69:   */     } else {
/* 70:79 */       ret = ret + "NULL";
/* 71:   */     }
/* 72:81 */     return ret;
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SecurityDescriptor
 * JD-Core Version:    0.7.0.1
 */