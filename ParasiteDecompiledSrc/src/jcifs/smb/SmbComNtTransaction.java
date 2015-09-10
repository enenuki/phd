/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ abstract class SmbComNtTransaction
/*  4:   */   extends SmbComTransaction
/*  5:   */ {
/*  6:   */   private static final int NTT_PRIMARY_SETUP_OFFSET = 69;
/*  7:   */   private static final int NTT_SECONDARY_PARAMETER_OFFSET = 51;
/*  8:   */   static final int NT_TRANSACT_QUERY_SECURITY_DESC = 6;
/*  9:   */   int function;
/* 10:   */   
/* 11:   */   SmbComNtTransaction()
/* 12:   */   {
/* 13:33 */     this.primarySetupOffset = 69;
/* 14:34 */     this.secondaryParameterOffset = 51;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 18:   */   {
/* 19:38 */     int start = dstIndex;
/* 20:40 */     if (this.command != -95) {
/* 21:41 */       dst[(dstIndex++)] = this.maxSetupCount;
/* 22:   */     } else {
/* 23:43 */       dst[(dstIndex++)] = 0;
/* 24:   */     }
/* 25:45 */     dst[(dstIndex++)] = 0;
/* 26:46 */     dst[(dstIndex++)] = 0;
/* 27:47 */     writeInt4(this.totalParameterCount, dst, dstIndex);
/* 28:48 */     dstIndex += 4;
/* 29:49 */     writeInt4(this.totalDataCount, dst, dstIndex);
/* 30:50 */     dstIndex += 4;
/* 31:51 */     if (this.command != -95)
/* 32:   */     {
/* 33:52 */       writeInt4(this.maxParameterCount, dst, dstIndex);
/* 34:53 */       dstIndex += 4;
/* 35:54 */       writeInt4(this.maxDataCount, dst, dstIndex);
/* 36:55 */       dstIndex += 4;
/* 37:   */     }
/* 38:57 */     writeInt4(this.parameterCount, dst, dstIndex);
/* 39:58 */     dstIndex += 4;
/* 40:59 */     writeInt4(this.parameterCount == 0 ? 0 : this.parameterOffset, dst, dstIndex);
/* 41:60 */     dstIndex += 4;
/* 42:61 */     if (this.command == -95)
/* 43:   */     {
/* 44:62 */       writeInt4(this.parameterDisplacement, dst, dstIndex);
/* 45:63 */       dstIndex += 4;
/* 46:   */     }
/* 47:65 */     writeInt4(this.dataCount, dst, dstIndex);
/* 48:66 */     dstIndex += 4;
/* 49:67 */     writeInt4(this.dataCount == 0 ? 0 : this.dataOffset, dst, dstIndex);
/* 50:68 */     dstIndex += 4;
/* 51:69 */     if (this.command == -95)
/* 52:   */     {
/* 53:70 */       writeInt4(this.dataDisplacement, dst, dstIndex);
/* 54:71 */       dstIndex += 4;
/* 55:72 */       dst[(dstIndex++)] = 0;
/* 56:   */     }
/* 57:   */     else
/* 58:   */     {
/* 59:74 */       dst[(dstIndex++)] = ((byte)this.setupCount);
/* 60:75 */       writeInt2(this.function, dst, dstIndex);
/* 61:76 */       dstIndex += 2;
/* 62:77 */       dstIndex += writeSetupWireFormat(dst, dstIndex);
/* 63:   */     }
/* 64:80 */     return dstIndex - start;
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNtTransaction
 * JD-Core Version:    0.7.0.1
 */