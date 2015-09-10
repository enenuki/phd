/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import jcifs.ntlmssp.Type1Message;
/*   5:    */ import jcifs.ntlmssp.Type2Message;
/*   6:    */ import jcifs.ntlmssp.Type3Message;
/*   7:    */ import jcifs.util.Encdec;
/*   8:    */ import jcifs.util.Hexdump;
/*   9:    */ import jcifs.util.LogStream;
/*  10:    */ 
/*  11:    */ public class NtlmContext
/*  12:    */ {
/*  13:    */   NtlmPasswordAuthentication auth;
/*  14:    */   int ntlmsspFlags;
/*  15:    */   String workstation;
/*  16: 38 */   boolean isEstablished = false;
/*  17: 39 */   byte[] serverChallenge = null;
/*  18: 40 */   byte[] signingKey = null;
/*  19: 41 */   String netbiosName = null;
/*  20: 42 */   int state = 1;
/*  21:    */   LogStream log;
/*  22:    */   
/*  23:    */   public NtlmContext(NtlmPasswordAuthentication auth, boolean doSigning)
/*  24:    */   {
/*  25: 46 */     this.auth = auth;
/*  26: 47 */     this.ntlmsspFlags = (this.ntlmsspFlags | 0x4 | 0x80000 | 0x20000000);
/*  27: 51 */     if (doSigning) {
/*  28: 52 */       this.ntlmsspFlags |= 0x40008010;
/*  29:    */     }
/*  30: 56 */     this.workstation = Type1Message.getDefaultWorkstation();
/*  31: 57 */     this.log = LogStream.getInstance();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString()
/*  35:    */   {
/*  36: 61 */     String ret = "NtlmContext[auth=" + this.auth + ",ntlmsspFlags=0x" + Hexdump.toHexString(this.ntlmsspFlags, 8) + ",workstation=" + this.workstation + ",isEstablished=" + this.isEstablished + ",state=" + this.state + ",serverChallenge=";
/*  37: 67 */     if (this.serverChallenge == null) {
/*  38: 68 */       ret = ret + "null";
/*  39:    */     } else {
/*  40: 70 */       ret = ret + Hexdump.toHexString(this.serverChallenge, 0, this.serverChallenge.length * 2);
/*  41:    */     }
/*  42: 72 */     ret = ret + ",signingKey=";
/*  43: 73 */     if (this.signingKey == null) {
/*  44: 74 */       ret = ret + "null";
/*  45:    */     } else {
/*  46: 76 */       ret = ret + Hexdump.toHexString(this.signingKey, 0, this.signingKey.length * 2);
/*  47:    */     }
/*  48: 78 */     ret = ret + "]";
/*  49: 79 */     return ret;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isEstablished()
/*  53:    */   {
/*  54: 83 */     return this.isEstablished;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public byte[] getServerChallenge()
/*  58:    */   {
/*  59: 87 */     return this.serverChallenge;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public byte[] getSigningKey()
/*  63:    */   {
/*  64: 91 */     return this.signingKey;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getNetbiosName()
/*  68:    */   {
/*  69: 95 */     return this.netbiosName;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private String getNtlmsspListItem(byte[] type2token, int id0)
/*  73:    */   {
/*  74:100 */     int ri = 58;
/*  75:    */     for (;;)
/*  76:    */     {
/*  77:103 */       int id = Encdec.dec_uint16le(type2token, ri);
/*  78:104 */       int len = Encdec.dec_uint16le(type2token, ri + 2);
/*  79:105 */       ri += 4;
/*  80:106 */       if ((id == 0) || (ri + len > type2token.length)) {
/*  81:    */         break;
/*  82:    */       }
/*  83:108 */       if (id == id0) {
/*  84:    */         try
/*  85:    */         {
/*  86:110 */           return new String(type2token, ri, len, "UTF-16LE");
/*  87:    */         }
/*  88:    */         catch (UnsupportedEncodingException uee) {}
/*  89:    */       }
/*  90:115 */       ri += len;
/*  91:    */     }
/*  92:118 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public byte[] initSecContext(byte[] token, int offset, int len)
/*  96:    */     throws SmbException
/*  97:    */   {
/*  98:121 */     switch (this.state)
/*  99:    */     {
/* 100:    */     case 1: 
/* 101:123 */       Type1Message msg1 = new Type1Message(this.ntlmsspFlags, this.auth.getDomain(), this.workstation);
/* 102:124 */       token = msg1.toByteArray();
/* 103:126 */       if (LogStream.level >= 4)
/* 104:    */       {
/* 105:127 */         this.log.println(msg1);
/* 106:128 */         if (LogStream.level >= 6) {
/* 107:129 */           Hexdump.hexdump(this.log, token, 0, token.length);
/* 108:    */         }
/* 109:    */       }
/* 110:132 */       this.state += 1;
/* 111:133 */       break;
/* 112:    */     case 2: 
/* 113:    */       try
/* 114:    */       {
/* 115:136 */         Type2Message msg2 = new Type2Message(token);
/* 116:138 */         if (LogStream.level >= 4)
/* 117:    */         {
/* 118:139 */           this.log.println(msg2);
/* 119:140 */           if (LogStream.level >= 6) {
/* 120:141 */             Hexdump.hexdump(this.log, token, 0, token.length);
/* 121:    */           }
/* 122:    */         }
/* 123:144 */         this.serverChallenge = msg2.getChallenge();
/* 124:145 */         this.ntlmsspFlags &= msg2.getFlags();
/* 125:    */         
/* 126:    */ 
/* 127:    */ 
/* 128:149 */         Type3Message msg3 = new Type3Message(msg2, this.auth.getPassword(), this.auth.getDomain(), this.auth.getUsername(), this.workstation, this.ntlmsspFlags);
/* 129:    */         
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:155 */         token = msg3.toByteArray();
/* 135:157 */         if (LogStream.level >= 4)
/* 136:    */         {
/* 137:158 */           this.log.println(msg3);
/* 138:159 */           if (LogStream.level >= 6) {
/* 139:160 */             Hexdump.hexdump(this.log, token, 0, token.length);
/* 140:    */           }
/* 141:    */         }
/* 142:163 */         if ((this.ntlmsspFlags & 0x10) != 0) {
/* 143:164 */           this.signingKey = msg3.getMasterKey();
/* 144:    */         }
/* 145:166 */         this.isEstablished = true;
/* 146:167 */         this.state += 1;
/* 147:    */       }
/* 148:    */       catch (Exception e)
/* 149:    */       {
/* 150:170 */         throw new SmbException(e.getMessage(), e);
/* 151:    */       }
/* 152:    */     default: 
/* 153:173 */       throw new SmbException("Invalid state");
/* 154:    */     }
/* 155:175 */     return token;
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtlmContext
 * JD-Core Version:    0.7.0.1
 */