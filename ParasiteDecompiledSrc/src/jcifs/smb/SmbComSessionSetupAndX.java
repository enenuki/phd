/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.Config;
/*   4:    */ 
/*   5:    */ class SmbComSessionSetupAndX
/*   6:    */   extends AndXServerMessageBlock
/*   7:    */ {
/*   8: 26 */   private static final int BATCH_LIMIT = Config.getInt("jcifs.smb.client.SessionSetupAndX.TreeConnectAndX", 1);
/*   9: 28 */   private static final boolean DISABLE_PLAIN_TEXT_PASSWORDS = Config.getBoolean("jcifs.smb.client.disablePlainTextPasswords", true);
/*  10:    */   private byte[] lmHash;
/*  11:    */   private byte[] ntHash;
/*  12: 31 */   private byte[] blob = null;
/*  13:    */   private int sessionKey;
/*  14:    */   private int capabilities;
/*  15:    */   private String accountName;
/*  16:    */   private String primaryDomain;
/*  17:    */   SmbSession session;
/*  18:    */   Object cred;
/*  19:    */   
/*  20:    */   SmbComSessionSetupAndX(SmbSession session, ServerMessageBlock andx, Object cred)
/*  21:    */     throws SmbException
/*  22:    */   {
/*  23: 39 */     super(andx);
/*  24: 40 */     this.command = 115;
/*  25: 41 */     this.session = session;
/*  26: 42 */     this.cred = cred;
/*  27:    */     
/*  28: 44 */     this.sessionKey = session.transport.sessionKey;
/*  29: 45 */     this.capabilities = session.transport.capabilities;
/*  30: 47 */     if (session.transport.server.security == 1)
/*  31:    */     {
/*  32: 48 */       if ((cred instanceof NtlmPasswordAuthentication))
/*  33:    */       {
/*  34: 49 */         NtlmPasswordAuthentication auth = (NtlmPasswordAuthentication)cred;
/*  35: 51 */         if (auth == NtlmPasswordAuthentication.ANONYMOUS)
/*  36:    */         {
/*  37: 52 */           this.lmHash = new byte[0];
/*  38: 53 */           this.ntHash = new byte[0];
/*  39: 54 */           this.capabilities &= 0x7FFFFFFF;
/*  40:    */         }
/*  41: 55 */         else if (session.transport.server.encryptedPasswords)
/*  42:    */         {
/*  43: 56 */           this.lmHash = auth.getAnsiHash(session.transport.server.encryptionKey);
/*  44: 57 */           this.ntHash = auth.getUnicodeHash(session.transport.server.encryptionKey);
/*  45: 59 */           if ((this.lmHash.length == 0) && (this.ntHash.length == 0)) {
/*  46: 60 */             throw new RuntimeException("Null setup prohibited.");
/*  47:    */           }
/*  48:    */         }
/*  49:    */         else
/*  50:    */         {
/*  51: 62 */           if (DISABLE_PLAIN_TEXT_PASSWORDS) {
/*  52: 63 */             throw new RuntimeException("Plain text passwords are disabled");
/*  53:    */           }
/*  54: 64 */           if (this.useUnicode)
/*  55:    */           {
/*  56: 66 */             String password = auth.getPassword();
/*  57: 67 */             this.lmHash = new byte[0];
/*  58: 68 */             this.ntHash = new byte[(password.length() + 1) * 2];
/*  59: 69 */             writeString(password, this.ntHash, 0);
/*  60:    */           }
/*  61:    */           else
/*  62:    */           {
/*  63: 72 */             String password = auth.getPassword();
/*  64: 73 */             this.lmHash = new byte[(password.length() + 1) * 2];
/*  65: 74 */             this.ntHash = new byte[0];
/*  66: 75 */             writeString(password, this.lmHash, 0);
/*  67:    */           }
/*  68:    */         }
/*  69: 77 */         this.accountName = auth.username;
/*  70: 78 */         if (this.useUnicode) {
/*  71: 79 */           this.accountName = this.accountName.toUpperCase();
/*  72:    */         }
/*  73: 80 */         this.primaryDomain = auth.domain.toUpperCase();
/*  74:    */       }
/*  75: 81 */       else if ((cred instanceof byte[]))
/*  76:    */       {
/*  77: 82 */         this.blob = ((byte[])cred);
/*  78:    */       }
/*  79:    */       else
/*  80:    */       {
/*  81: 84 */         throw new SmbException("Unsupported credential type");
/*  82:    */       }
/*  83:    */     }
/*  84: 86 */     else if (session.transport.server.security == 0)
/*  85:    */     {
/*  86: 87 */       if ((cred instanceof NtlmPasswordAuthentication))
/*  87:    */       {
/*  88: 88 */         NtlmPasswordAuthentication auth = (NtlmPasswordAuthentication)cred;
/*  89: 89 */         this.lmHash = new byte[0];
/*  90: 90 */         this.ntHash = new byte[0];
/*  91: 91 */         this.accountName = auth.username;
/*  92: 92 */         if (this.useUnicode) {
/*  93: 93 */           this.accountName = this.accountName.toUpperCase();
/*  94:    */         }
/*  95: 94 */         this.primaryDomain = auth.domain.toUpperCase();
/*  96:    */       }
/*  97:    */       else
/*  98:    */       {
/*  99: 96 */         throw new SmbException("Unsupported credential type");
/* 100:    */       }
/* 101:    */     }
/* 102:    */     else {
/* 103: 99 */       throw new SmbException("Unsupported");
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   int getBatchLimit(byte command)
/* 108:    */   {
/* 109:104 */     return command == 117 ? BATCH_LIMIT : 0;
/* 110:    */   }
/* 111:    */   
/* 112:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/* 113:    */   {
/* 114:107 */     int start = dstIndex;
/* 115:    */     
/* 116:109 */     writeInt2(this.session.transport.snd_buf_size, dst, dstIndex);
/* 117:110 */     dstIndex += 2;
/* 118:111 */     writeInt2(this.session.transport.maxMpxCount, dst, dstIndex);
/* 119:112 */     dstIndex += 2;
/* 120:113 */     writeInt2(1L, dst, dstIndex);
/* 121:114 */     dstIndex += 2;
/* 122:115 */     writeInt4(this.sessionKey, dst, dstIndex);
/* 123:116 */     dstIndex += 4;
/* 124:117 */     if (this.blob != null)
/* 125:    */     {
/* 126:118 */       writeInt2(this.blob.length, dst, dstIndex);
/* 127:119 */       dstIndex += 2;
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:121 */       writeInt2(this.lmHash.length, dst, dstIndex);
/* 132:122 */       dstIndex += 2;
/* 133:123 */       writeInt2(this.ntHash.length, dst, dstIndex);
/* 134:124 */       dstIndex += 2;
/* 135:    */     }
/* 136:126 */     dst[(dstIndex++)] = 0;
/* 137:127 */     dst[(dstIndex++)] = 0;
/* 138:128 */     dst[(dstIndex++)] = 0;
/* 139:129 */     dst[(dstIndex++)] = 0;
/* 140:130 */     writeInt4(this.capabilities, dst, dstIndex);
/* 141:131 */     dstIndex += 4;
/* 142:    */     
/* 143:133 */     return dstIndex - start;
/* 144:    */   }
/* 145:    */   
/* 146:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 147:    */   {
/* 148:136 */     int start = dstIndex;
/* 149:138 */     if (this.blob != null)
/* 150:    */     {
/* 151:139 */       System.arraycopy(this.blob, 0, dst, dstIndex, this.blob.length);
/* 152:140 */       dstIndex += this.blob.length;
/* 153:    */     }
/* 154:    */     else
/* 155:    */     {
/* 156:142 */       System.arraycopy(this.lmHash, 0, dst, dstIndex, this.lmHash.length);
/* 157:143 */       dstIndex += this.lmHash.length;
/* 158:144 */       System.arraycopy(this.ntHash, 0, dst, dstIndex, this.ntHash.length);
/* 159:145 */       dstIndex += this.ntHash.length;
/* 160:    */       
/* 161:147 */       dstIndex += writeString(this.accountName, dst, dstIndex);
/* 162:148 */       dstIndex += writeString(this.primaryDomain, dst, dstIndex);
/* 163:    */     }
/* 164:150 */     dstIndex += writeString(SmbConstants.NATIVE_OS, dst, dstIndex);
/* 165:151 */     dstIndex += writeString(SmbConstants.NATIVE_LANMAN, dst, dstIndex);
/* 166:    */     
/* 167:153 */     return dstIndex - start;
/* 168:    */   }
/* 169:    */   
/* 170:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 171:    */   {
/* 172:156 */     return 0;
/* 173:    */   }
/* 174:    */   
/* 175:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 176:    */   {
/* 177:159 */     return 0;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String toString()
/* 181:    */   {
/* 182:162 */     String result = new String("SmbComSessionSetupAndX[" + super.toString() + ",snd_buf_size=" + this.session.transport.snd_buf_size + ",maxMpxCount=" + this.session.transport.maxMpxCount + ",VC_NUMBER=" + 1 + ",sessionKey=" + this.sessionKey + ",lmHash.length=" + (this.lmHash == null ? 0 : this.lmHash.length) + ",ntHash.length=" + (this.ntHash == null ? 0 : this.ntHash.length) + ",capabilities=" + this.capabilities + ",accountName=" + this.accountName + ",primaryDomain=" + this.primaryDomain + ",NATIVE_OS=" + SmbConstants.NATIVE_OS + ",NATIVE_LANMAN=" + SmbConstants.NATIVE_LANMAN + "]");
/* 183:    */     
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:175 */     return result;
/* 196:    */   }
/* 197:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComSessionSetupAndX
 * JD-Core Version:    0.7.0.1
 */