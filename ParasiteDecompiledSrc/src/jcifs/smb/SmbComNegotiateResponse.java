/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.Date;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ import jcifs.util.LogStream;
/*   7:    */ 
/*   8:    */ class SmbComNegotiateResponse
/*   9:    */   extends ServerMessageBlock
/*  10:    */ {
/*  11:    */   int dialectIndex;
/*  12:    */   SmbTransport.ServerData server;
/*  13:    */   
/*  14:    */   SmbComNegotiateResponse(SmbTransport.ServerData server)
/*  15:    */   {
/*  16: 31 */     this.server = server;
/*  17:    */   }
/*  18:    */   
/*  19:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  20:    */   {
/*  21: 35 */     return 0;
/*  22:    */   }
/*  23:    */   
/*  24:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/*  25:    */   {
/*  26: 38 */     return 0;
/*  27:    */   }
/*  28:    */   
/*  29:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/*  30:    */   {
/*  31: 42 */     int start = bufferIndex;
/*  32:    */     
/*  33: 44 */     this.dialectIndex = readInt2(buffer, bufferIndex);bufferIndex += 2;
/*  34: 45 */     if (this.dialectIndex > 10) {
/*  35: 46 */       return bufferIndex - start;
/*  36:    */     }
/*  37: 48 */     this.server.securityMode = (buffer[(bufferIndex++)] & 0xFF);
/*  38: 49 */     this.server.security = (this.server.securityMode & 0x1);
/*  39: 50 */     this.server.encryptedPasswords = ((this.server.securityMode & 0x2) == 2);
/*  40: 51 */     this.server.signaturesEnabled = ((this.server.securityMode & 0x4) == 4);
/*  41: 52 */     this.server.signaturesRequired = ((this.server.securityMode & 0x8) == 8);
/*  42: 53 */     this.server.maxMpxCount = readInt2(buffer, bufferIndex);bufferIndex += 2;
/*  43: 54 */     this.server.maxNumberVcs = readInt2(buffer, bufferIndex);bufferIndex += 2;
/*  44: 55 */     this.server.maxBufferSize = readInt4(buffer, bufferIndex);bufferIndex += 4;
/*  45: 56 */     this.server.maxRawSize = readInt4(buffer, bufferIndex);bufferIndex += 4;
/*  46: 57 */     this.server.sessionKey = readInt4(buffer, bufferIndex);bufferIndex += 4;
/*  47: 58 */     this.server.capabilities = readInt4(buffer, bufferIndex);bufferIndex += 4;
/*  48: 59 */     this.server.serverTime = readTime(buffer, bufferIndex);bufferIndex += 8;
/*  49: 60 */     this.server.serverTimeZone = readInt2(buffer, bufferIndex);bufferIndex += 2;
/*  50: 61 */     this.server.encryptionKeyLength = (buffer[(bufferIndex++)] & 0xFF);
/*  51:    */     
/*  52: 63 */     return bufferIndex - start;
/*  53:    */   }
/*  54:    */   
/*  55:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/*  56:    */   {
/*  57: 67 */     int start = bufferIndex;
/*  58: 69 */     if ((this.server.capabilities & 0x80000000) == 0)
/*  59:    */     {
/*  60: 70 */       this.server.encryptionKey = new byte[this.server.encryptionKeyLength];
/*  61: 71 */       System.arraycopy(buffer, bufferIndex, this.server.encryptionKey, 0, this.server.encryptionKeyLength);
/*  62:    */       
/*  63: 73 */       bufferIndex += this.server.encryptionKeyLength;
/*  64: 74 */       if (this.byteCount > this.server.encryptionKeyLength)
/*  65:    */       {
/*  66: 75 */         int len = 0;
/*  67:    */         try
/*  68:    */         {
/*  69: 78 */           if ((this.flags2 & 0x8000) == 32768)
/*  70:    */           {
/*  71: 80 */             while ((buffer[(bufferIndex + len)] != 0) || (buffer[(bufferIndex + len + 1)] != 0))
/*  72:    */             {
/*  73: 81 */               len += 2;
/*  74: 82 */               if (len > 256) {
/*  75: 83 */                 throw new RuntimeException("zero termination not found");
/*  76:    */               }
/*  77:    */             }
/*  78: 86 */             this.server.oemDomainName = new String(buffer, bufferIndex, len, "UTF-16LE");
/*  79:    */           }
/*  80:    */           else
/*  81:    */           {
/*  82: 89 */             while (buffer[(bufferIndex + len)] != 0)
/*  83:    */             {
/*  84: 90 */               len++;
/*  85: 91 */               if (len > 256) {
/*  86: 92 */                 throw new RuntimeException("zero termination not found");
/*  87:    */               }
/*  88:    */             }
/*  89: 95 */             this.server.oemDomainName = new String(buffer, bufferIndex, len, SmbConstants.OEM_ENCODING);
/*  90:    */           }
/*  91:    */         }
/*  92:    */         catch (UnsupportedEncodingException uee)
/*  93:    */         {
/*  94: 99 */           if (LogStream.level > 1) {
/*  95:100 */             uee.printStackTrace(log);
/*  96:    */           }
/*  97:    */         }
/*  98:102 */         bufferIndex += len;
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:104 */         this.server.oemDomainName = new String();
/* 103:    */       }
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:107 */       this.server.guid = new byte[16];
/* 108:108 */       System.arraycopy(buffer, bufferIndex, this.server.guid, 0, 16);
/* 109:109 */       this.server.oemDomainName = new String();
/* 110:    */     }
/* 111:113 */     return bufferIndex - start;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String toString()
/* 115:    */   {
/* 116:116 */     return new String("SmbComNegotiateResponse[" + super.toString() + ",wordCount=" + this.wordCount + ",dialectIndex=" + this.dialectIndex + ",securityMode=0x" + Hexdump.toHexString(this.server.securityMode, 1) + ",security=" + (this.server.security == 0 ? "share" : "user") + ",encryptedPasswords=" + this.server.encryptedPasswords + ",maxMpxCount=" + this.server.maxMpxCount + ",maxNumberVcs=" + this.server.maxNumberVcs + ",maxBufferSize=" + this.server.maxBufferSize + ",maxRawSize=" + this.server.maxRawSize + ",sessionKey=0x" + Hexdump.toHexString(this.server.sessionKey, 8) + ",capabilities=0x" + Hexdump.toHexString(this.server.capabilities, 8) + ",serverTime=" + new Date(this.server.serverTime) + ",serverTimeZone=" + this.server.serverTimeZone + ",encryptionKeyLength=" + this.server.encryptionKeyLength + ",byteCount=" + this.byteCount + ",oemDomainName=" + this.server.oemDomainName + "]");
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNegotiateResponse
 * JD-Core Version:    0.7.0.1
 */