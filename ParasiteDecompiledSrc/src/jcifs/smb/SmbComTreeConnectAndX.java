/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import jcifs.Config;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ 
/*   7:    */ class SmbComTreeConnectAndX
/*   8:    */   extends AndXServerMessageBlock
/*   9:    */ {
/*  10: 27 */   private static final boolean DISABLE_PLAIN_TEXT_PASSWORDS = Config.getBoolean("jcifs.smb.client.disablePlainTextPasswords", true);
/*  11:    */   private SmbSession session;
/*  12: 31 */   private boolean disconnectTid = false;
/*  13:    */   private String service;
/*  14:    */   private byte[] password;
/*  15:    */   private int passwordLength;
/*  16:    */   String path;
/*  17: 52 */   private static byte[] batchLimits = { 1, 1, 1, 1, 1, 1, 1, 1, 0 };
/*  18:    */   
/*  19:    */   static
/*  20:    */   {
/*  21:    */     String s;
/*  22: 59 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.CheckDirectory")) != null) {
/*  23: 60 */       batchLimits[0] = Byte.parseByte(s);
/*  24:    */     }
/*  25: 62 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.CreateDirectory")) != null) {
/*  26: 63 */       batchLimits[2] = Byte.parseByte(s);
/*  27:    */     }
/*  28: 65 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.Delete")) != null) {
/*  29: 66 */       batchLimits[3] = Byte.parseByte(s);
/*  30:    */     }
/*  31: 68 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.DeleteDirectory")) != null) {
/*  32: 69 */       batchLimits[4] = Byte.parseByte(s);
/*  33:    */     }
/*  34: 71 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.OpenAndX")) != null) {
/*  35: 72 */       batchLimits[5] = Byte.parseByte(s);
/*  36:    */     }
/*  37: 74 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.Rename")) != null) {
/*  38: 75 */       batchLimits[6] = Byte.parseByte(s);
/*  39:    */     }
/*  40: 77 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.Transaction")) != null) {
/*  41: 78 */       batchLimits[7] = Byte.parseByte(s);
/*  42:    */     }
/*  43: 80 */     if ((s = Config.getProperty("jcifs.smb.client.TreeConnectAndX.QueryInformation")) != null) {
/*  44: 81 */       batchLimits[8] = Byte.parseByte(s);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   SmbComTreeConnectAndX(SmbSession session, String path, String service, ServerMessageBlock andx)
/*  49:    */   {
/*  50: 87 */     super(andx);
/*  51: 88 */     this.session = session;
/*  52: 89 */     this.path = path;
/*  53: 90 */     this.service = service;
/*  54: 91 */     this.command = 117;
/*  55:    */   }
/*  56:    */   
/*  57:    */   int getBatchLimit(byte command)
/*  58:    */   {
/*  59: 95 */     int c = command & 0xFF;
/*  60: 97 */     switch (c)
/*  61:    */     {
/*  62:    */     case 16: 
/*  63: 99 */       return batchLimits[0];
/*  64:    */     case 0: 
/*  65:101 */       return batchLimits[2];
/*  66:    */     case 6: 
/*  67:103 */       return batchLimits[3];
/*  68:    */     case 1: 
/*  69:105 */       return batchLimits[4];
/*  70:    */     case 45: 
/*  71:107 */       return batchLimits[5];
/*  72:    */     case 7: 
/*  73:109 */       return batchLimits[6];
/*  74:    */     case 37: 
/*  75:111 */       return batchLimits[7];
/*  76:    */     case 8: 
/*  77:113 */       return batchLimits[8];
/*  78:    */     }
/*  79:115 */     return 0;
/*  80:    */   }
/*  81:    */   
/*  82:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  83:    */   {
/*  84:120 */     if ((this.session.transport.server.security == 0) && ((this.session.auth.hashesExternal) || (this.session.auth.password.length() > 0)))
/*  85:    */     {
/*  86:124 */       if (this.session.transport.server.encryptedPasswords)
/*  87:    */       {
/*  88:126 */         this.password = this.session.auth.getAnsiHash(this.session.transport.server.encryptionKey);
/*  89:127 */         this.passwordLength = this.password.length;
/*  90:    */       }
/*  91:    */       else
/*  92:    */       {
/*  93:128 */         if (DISABLE_PLAIN_TEXT_PASSWORDS) {
/*  94:129 */           throw new RuntimeException("Plain text passwords are disabled");
/*  95:    */         }
/*  96:132 */         this.password = new byte[(this.session.auth.password.length() + 1) * 2];
/*  97:133 */         this.passwordLength = writeString(this.session.auth.password, this.password, 0);
/*  98:    */       }
/*  99:    */     }
/* 100:    */     else {
/* 101:137 */       this.passwordLength = 1;
/* 102:    */     }
/* 103:140 */     dst[(dstIndex++)] = (this.disconnectTid ? 1 : 0);
/* 104:141 */     dst[(dstIndex++)] = 0;
/* 105:142 */     writeInt2(this.passwordLength, dst, dstIndex);
/* 106:143 */     return 4;
/* 107:    */   }
/* 108:    */   
/* 109:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 110:    */   {
/* 111:146 */     int start = dstIndex;
/* 112:148 */     if ((this.session.transport.server.security == 0) && ((this.session.auth.hashesExternal) || (this.session.auth.password.length() > 0)))
/* 113:    */     {
/* 114:151 */       System.arraycopy(this.password, 0, dst, dstIndex, this.passwordLength);
/* 115:152 */       dstIndex += this.passwordLength;
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:155 */       dst[(dstIndex++)] = 0;
/* 120:    */     }
/* 121:157 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 122:    */     try
/* 123:    */     {
/* 124:159 */       System.arraycopy(this.service.getBytes("ASCII"), 0, dst, dstIndex, this.service.length());
/* 125:    */     }
/* 126:    */     catch (UnsupportedEncodingException uee)
/* 127:    */     {
/* 128:161 */       return 0;
/* 129:    */     }
/* 130:163 */     dstIndex += this.service.length();
/* 131:164 */     dst[(dstIndex++)] = 0;
/* 132:    */     
/* 133:166 */     return dstIndex - start;
/* 134:    */   }
/* 135:    */   
/* 136:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 137:    */   {
/* 138:169 */     return 0;
/* 139:    */   }
/* 140:    */   
/* 141:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 142:    */   {
/* 143:172 */     return 0;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String toString()
/* 147:    */   {
/* 148:175 */     String result = new String("SmbComTreeConnectAndX[" + super.toString() + ",disconnectTid=" + this.disconnectTid + ",passwordLength=" + this.passwordLength + ",password=" + Hexdump.toHexString(this.password, this.passwordLength, 0) + ",path=" + this.path + ",service=" + this.service + "]");
/* 149:    */     
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:182 */     return result;
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComTreeConnectAndX
 * JD-Core Version:    0.7.0.1
 */