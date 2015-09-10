/*   1:    */ package jcifs.ntlmssp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.UnknownHostException;
/*   5:    */ import jcifs.Config;
/*   6:    */ import jcifs.netbios.NbtAddress;
/*   7:    */ import jcifs.util.Hexdump;
/*   8:    */ 
/*   9:    */ public class Type1Message
/*  10:    */   extends NtlmMessage
/*  11:    */ {
/*  12: 46 */   private static final int DEFAULT_FLAGS = 0x200 | (Config.getBoolean("jcifs.smb.client.useUnicode", true) ? 1 : 2);
/*  13: 49 */   private static final String DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", null);
/*  14:    */   private static final String DEFAULT_WORKSTATION;
/*  15:    */   private String suppliedDomain;
/*  16:    */   private String suppliedWorkstation;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 50 */     String defaultWorkstation = null;
/*  21:    */     try
/*  22:    */     {
/*  23: 52 */       defaultWorkstation = NbtAddress.getLocalHost().getHostName();
/*  24:    */     }
/*  25:    */     catch (UnknownHostException ex) {}
/*  26: 54 */     DEFAULT_WORKSTATION = defaultWorkstation;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Type1Message()
/*  30:    */   {
/*  31: 62 */     this(getDefaultFlags(), getDefaultDomain(), getDefaultWorkstation());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Type1Message(int flags, String suppliedDomain, String suppliedWorkstation)
/*  35:    */   {
/*  36: 74 */     setFlags(getDefaultFlags() | flags);
/*  37: 75 */     setSuppliedDomain(suppliedDomain);
/*  38: 76 */     if (suppliedWorkstation == null) {
/*  39: 77 */       suppliedWorkstation = getDefaultWorkstation();
/*  40:    */     }
/*  41: 78 */     setSuppliedWorkstation(suppliedWorkstation);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Type1Message(byte[] material)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 88 */     parse(material);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getSuppliedDomain()
/*  51:    */   {
/*  52: 97 */     return this.suppliedDomain;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setSuppliedDomain(String suppliedDomain)
/*  56:    */   {
/*  57:106 */     this.suppliedDomain = suppliedDomain;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getSuppliedWorkstation()
/*  61:    */   {
/*  62:115 */     return this.suppliedWorkstation;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setSuppliedWorkstation(String suppliedWorkstation)
/*  66:    */   {
/*  67:124 */     this.suppliedWorkstation = suppliedWorkstation;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public byte[] toByteArray()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:129 */       String suppliedDomain = getSuppliedDomain();
/*  75:130 */       String suppliedWorkstation = getSuppliedWorkstation();
/*  76:131 */       int flags = getFlags();
/*  77:132 */       boolean hostInfo = false;
/*  78:133 */       byte[] domain = new byte[0];
/*  79:134 */       if ((suppliedDomain != null) && (suppliedDomain.length() != 0))
/*  80:    */       {
/*  81:135 */         hostInfo = true;
/*  82:136 */         flags |= 0x1000;
/*  83:137 */         domain = suppliedDomain.toUpperCase().getBytes(getOEMEncoding());
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87:140 */         flags &= 0xFFFFEFFF;
/*  88:    */       }
/*  89:142 */       byte[] workstation = new byte[0];
/*  90:143 */       if ((suppliedWorkstation != null) && (suppliedWorkstation.length() != 0))
/*  91:    */       {
/*  92:145 */         hostInfo = true;
/*  93:146 */         flags |= 0x2000;
/*  94:147 */         workstation = suppliedWorkstation.toUpperCase().getBytes(getOEMEncoding());
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98:151 */         flags &= 0xFFFFDFFF;
/*  99:    */       }
/* 100:154 */       byte[] type1 = new byte[hostInfo ? 32 + domain.length + workstation.length : 16];
/* 101:    */       
/* 102:156 */       System.arraycopy(NTLMSSP_SIGNATURE, 0, type1, 0, 8);
/* 103:157 */       writeULong(type1, 8, 1);
/* 104:158 */       writeULong(type1, 12, flags);
/* 105:159 */       if (hostInfo)
/* 106:    */       {
/* 107:160 */         writeSecurityBuffer(type1, 16, 32, domain);
/* 108:161 */         writeSecurityBuffer(type1, 24, 32 + domain.length, workstation);
/* 109:    */       }
/* 110:163 */       return type1;
/* 111:    */     }
/* 112:    */     catch (IOException ex)
/* 113:    */     {
/* 114:165 */       throw new IllegalStateException(ex.getMessage());
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString()
/* 119:    */   {
/* 120:170 */     String suppliedDomain = getSuppliedDomain();
/* 121:171 */     String suppliedWorkstation = getSuppliedWorkstation();
/* 122:172 */     return "Type1Message[suppliedDomain=" + (suppliedDomain == null ? "null" : suppliedDomain) + ",suppliedWorkstation=" + (suppliedWorkstation == null ? "null" : suppliedWorkstation) + ",flags=0x" + Hexdump.toHexString(getFlags(), 8) + "]";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static int getDefaultFlags()
/* 126:    */   {
/* 127:184 */     return DEFAULT_FLAGS;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static String getDefaultDomain()
/* 131:    */   {
/* 132:193 */     return DEFAULT_DOMAIN;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static String getDefaultWorkstation()
/* 136:    */   {
/* 137:202 */     return DEFAULT_WORKSTATION;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private void parse(byte[] material)
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:206 */     for (int i = 0; i < 8; i++) {
/* 144:207 */       if (material[i] != NTLMSSP_SIGNATURE[i]) {
/* 145:208 */         throw new IOException("Not an NTLMSSP message.");
/* 146:    */       }
/* 147:    */     }
/* 148:211 */     if (readULong(material, 8) != 1) {
/* 149:212 */       throw new IOException("Not a Type 1 message.");
/* 150:    */     }
/* 151:214 */     int flags = readULong(material, 12);
/* 152:215 */     String suppliedDomain = null;
/* 153:216 */     if ((flags & 0x1000) != 0)
/* 154:    */     {
/* 155:217 */       byte[] domain = readSecurityBuffer(material, 16);
/* 156:218 */       suppliedDomain = new String(domain, getOEMEncoding());
/* 157:    */     }
/* 158:220 */     String suppliedWorkstation = null;
/* 159:221 */     if ((flags & 0x2000) != 0)
/* 160:    */     {
/* 161:222 */       byte[] workstation = readSecurityBuffer(material, 24);
/* 162:223 */       suppliedWorkstation = new String(workstation, getOEMEncoding());
/* 163:    */     }
/* 164:225 */     setFlags(flags);
/* 165:226 */     setSuppliedDomain(suppliedDomain);
/* 166:227 */     setSuppliedWorkstation(suppliedWorkstation);
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.ntlmssp.Type1Message
 * JD-Core Version:    0.7.0.1
 */