/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ import java.security.NoSuchAlgorithmException;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ import jcifs.util.LogStream;
/*   7:    */ 
/*   8:    */ public class SigningDigest
/*   9:    */   implements SmbConstants
/*  10:    */ {
/*  11: 16 */   static LogStream log = ;
/*  12:    */   private MessageDigest digest;
/*  13:    */   private byte[] macSigningKey;
/*  14: 20 */   private boolean bypass = false;
/*  15:    */   private int updates;
/*  16:    */   private int signSequence;
/*  17:    */   
/*  18:    */   public SigningDigest(byte[] macSigningKey, boolean bypass)
/*  19:    */     throws SmbException
/*  20:    */   {
/*  21:    */     try
/*  22:    */     {
/*  23: 26 */       this.digest = MessageDigest.getInstance("MD5");
/*  24:    */     }
/*  25:    */     catch (NoSuchAlgorithmException ex)
/*  26:    */     {
/*  27: 28 */       if (LogStream.level > 0) {
/*  28: 29 */         ex.printStackTrace(log);
/*  29:    */       }
/*  30: 30 */       throw new SmbException("MD5", ex);
/*  31:    */     }
/*  32: 33 */     this.macSigningKey = macSigningKey;
/*  33: 34 */     this.bypass = bypass;
/*  34: 35 */     this.updates = 0;
/*  35: 36 */     this.signSequence = 0;
/*  36: 38 */     if (LogStream.level >= 5)
/*  37:    */     {
/*  38: 39 */       log.println("macSigningKey:");
/*  39: 40 */       Hexdump.hexdump(log, macSigningKey, 0, macSigningKey.length);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SigningDigest(SmbTransport transport, NtlmPasswordAuthentication auth)
/*  44:    */     throws SmbException
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 47 */       this.digest = MessageDigest.getInstance("MD5");
/*  49:    */     }
/*  50:    */     catch (NoSuchAlgorithmException ex)
/*  51:    */     {
/*  52: 49 */       if (LogStream.level > 0) {
/*  53: 50 */         ex.printStackTrace(log);
/*  54:    */       }
/*  55: 51 */       throw new SmbException("MD5", ex);
/*  56:    */     }
/*  57:    */     try
/*  58:    */     {
/*  59: 55 */       switch (SmbConstants.LM_COMPATIBILITY)
/*  60:    */       {
/*  61:    */       case 0: 
/*  62:    */       case 1: 
/*  63:    */       case 2: 
/*  64: 59 */         this.macSigningKey = new byte[40];
/*  65: 60 */         auth.getUserSessionKey(transport.server.encryptionKey, this.macSigningKey, 0);
/*  66: 61 */         System.arraycopy(auth.getUnicodeHash(transport.server.encryptionKey), 0, this.macSigningKey, 16, 24);
/*  67:    */         
/*  68: 63 */         break;
/*  69:    */       case 3: 
/*  70:    */       case 4: 
/*  71:    */       case 5: 
/*  72: 67 */         this.macSigningKey = new byte[16];
/*  73: 68 */         auth.getUserSessionKey(transport.server.encryptionKey, this.macSigningKey, 0);
/*  74: 69 */         break;
/*  75:    */       default: 
/*  76: 71 */         this.macSigningKey = new byte[40];
/*  77: 72 */         auth.getUserSessionKey(transport.server.encryptionKey, this.macSigningKey, 0);
/*  78: 73 */         System.arraycopy(auth.getUnicodeHash(transport.server.encryptionKey), 0, this.macSigningKey, 16, 24);
/*  79:    */       }
/*  80:    */     }
/*  81:    */     catch (Exception ex)
/*  82:    */     {
/*  83: 78 */       throw new SmbException("", ex);
/*  84:    */     }
/*  85: 80 */     if (LogStream.level >= 5)
/*  86:    */     {
/*  87: 81 */       log.println("LM_COMPATIBILITY=" + SmbConstants.LM_COMPATIBILITY);
/*  88: 82 */       Hexdump.hexdump(log, this.macSigningKey, 0, this.macSigningKey.length);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void update(byte[] input, int offset, int len)
/*  93:    */   {
/*  94: 87 */     if (LogStream.level >= 5)
/*  95:    */     {
/*  96: 88 */       log.println("update: " + this.updates + " " + offset + ":" + len);
/*  97: 89 */       Hexdump.hexdump(log, input, offset, Math.min(len, 256));
/*  98: 90 */       log.flush();
/*  99:    */     }
/* 100: 92 */     if (len == 0) {
/* 101: 93 */       return;
/* 102:    */     }
/* 103: 95 */     this.digest.update(input, offset, len);
/* 104: 96 */     this.updates += 1;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public byte[] digest()
/* 108:    */   {
/* 109:101 */     byte[] b = this.digest.digest();
/* 110:103 */     if (LogStream.level >= 5)
/* 111:    */     {
/* 112:104 */       log.println("digest: ");
/* 113:105 */       Hexdump.hexdump(log, b, 0, b.length);
/* 114:106 */       log.flush();
/* 115:    */     }
/* 116:108 */     this.updates = 0;
/* 117:    */     
/* 118:110 */     return b;
/* 119:    */   }
/* 120:    */   
/* 121:    */   void sign(byte[] data, int offset, int length, ServerMessageBlock request, ServerMessageBlock response)
/* 122:    */   {
/* 123:125 */     request.signSeq = this.signSequence;
/* 124:126 */     if (response != null)
/* 125:    */     {
/* 126:127 */       response.signSeq = (this.signSequence + 1);
/* 127:128 */       response.verifyFailed = false;
/* 128:    */     }
/* 129:    */     try
/* 130:    */     {
/* 131:132 */       update(this.macSigningKey, 0, this.macSigningKey.length);
/* 132:133 */       int index = offset + 14;
/* 133:134 */       for (int i = 0; i < 8; i++) {
/* 134:134 */         data[(index + i)] = 0;
/* 135:    */       }
/* 136:135 */       ServerMessageBlock.writeInt4(this.signSequence, data, index);
/* 137:136 */       update(data, offset, length);
/* 138:137 */       System.arraycopy(digest(), 0, data, index, 8);
/* 139:138 */       if (this.bypass)
/* 140:    */       {
/* 141:139 */         this.bypass = false;
/* 142:140 */         System.arraycopy("BSRSPYL ".getBytes(), 0, data, index, 8);
/* 143:    */       }
/* 144:    */     }
/* 145:    */     catch (Exception ex)
/* 146:    */     {
/* 147:143 */       if (LogStream.level > 0) {
/* 148:144 */         ex.printStackTrace(log);
/* 149:    */       }
/* 150:    */     }
/* 151:    */     finally
/* 152:    */     {
/* 153:146 */       this.signSequence += 2;
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   boolean verify(byte[] data, int offset, ServerMessageBlock response)
/* 158:    */   {
/* 159:159 */     update(this.macSigningKey, 0, this.macSigningKey.length);
/* 160:160 */     int index = offset;
/* 161:161 */     update(data, index, 14);
/* 162:162 */     index += 14;
/* 163:163 */     byte[] sequence = new byte[8];
/* 164:164 */     ServerMessageBlock.writeInt4(response.signSeq, sequence, 0);
/* 165:165 */     update(sequence, 0, sequence.length);
/* 166:166 */     index += 8;
/* 167:167 */     if (response.command == 46)
/* 168:    */     {
/* 169:170 */       SmbComReadAndXResponse raxr = (SmbComReadAndXResponse)response;
/* 170:171 */       int length = response.length - raxr.dataLength;
/* 171:172 */       update(data, index, length - 14 - 8);
/* 172:173 */       update(raxr.b, raxr.off, raxr.dataLength);
/* 173:    */     }
/* 174:    */     else
/* 175:    */     {
/* 176:175 */       update(data, index, response.length - 14 - 8);
/* 177:    */     }
/* 178:177 */     byte[] signature = digest();
/* 179:178 */     for (int i = 0; i < 8; i++) {
/* 180:179 */       if (signature[i] != data[(offset + 14 + i)])
/* 181:    */       {
/* 182:180 */         if (LogStream.level >= 2)
/* 183:    */         {
/* 184:181 */           log.println("signature verification failure");
/* 185:182 */           Hexdump.hexdump(log, signature, 0, 8);
/* 186:183 */           Hexdump.hexdump(log, data, offset + 14, 8);
/* 187:    */         }
/* 188:186 */         return response.verifyFailed = 1;
/* 189:    */       }
/* 190:    */     }
/* 191:190 */     return response.verifyFailed = 0;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String toString()
/* 195:    */   {
/* 196:193 */     return "LM_COMPATIBILITY=" + SmbConstants.LM_COMPATIBILITY + " MacSigningKey=" + Hexdump.toHexString(this.macSigningKey, 0, this.macSigningKey.length);
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SigningDigest
 * JD-Core Version:    0.7.0.1
 */