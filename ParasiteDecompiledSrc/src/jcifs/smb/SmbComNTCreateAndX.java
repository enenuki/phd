/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.util.Hexdump;
/*   4:    */ 
/*   5:    */ class SmbComNTCreateAndX
/*   6:    */   extends AndXServerMessageBlock
/*   7:    */ {
/*   8:    */   static final int FILE_SUPERSEDE = 0;
/*   9:    */   static final int FILE_OPEN = 1;
/*  10:    */   static final int FILE_CREATE = 2;
/*  11:    */   static final int FILE_OPEN_IF = 3;
/*  12:    */   static final int FILE_OVERWRITE = 4;
/*  13:    */   static final int FILE_OVERWRITE_IF = 5;
/*  14:    */   static final int FILE_WRITE_THROUGH = 2;
/*  15:    */   static final int FILE_SEQUENTIAL_ONLY = 4;
/*  16:    */   static final int FILE_SYNCHRONOUS_IO_ALERT = 16;
/*  17:    */   static final int FILE_SYNCHRONOUS_IO_NONALERT = 32;
/*  18:    */   static final int SECURITY_CONTEXT_TRACKING = 1;
/*  19:    */   static final int SECURITY_EFFECTIVE_ONLY = 2;
/*  20:    */   private int rootDirectoryFid;
/*  21:    */   private int extFileAttributes;
/*  22:    */   private int shareAccess;
/*  23:    */   private int createDisposition;
/*  24:    */   private int createOptions;
/*  25:    */   private int impersonationLevel;
/*  26:    */   private long allocationSize;
/*  27:    */   private byte securityFlags;
/*  28:    */   private int namelen_index;
/*  29:    */   int flags0;
/*  30:    */   int desiredAccess;
/*  31:    */   
/*  32:    */   SmbComNTCreateAndX(String name, int flags, int access, int shareAccess, int extFileAttributes, int createOptions, ServerMessageBlock andx)
/*  33:    */   {
/*  34: 93 */     super(andx);
/*  35: 94 */     this.path = name;
/*  36: 95 */     this.command = -94;
/*  37:    */     
/*  38: 97 */     this.desiredAccess = access;
/*  39: 98 */     this.desiredAccess |= 0x89;
/*  40:    */     
/*  41:    */ 
/*  42:101 */     this.extFileAttributes = extFileAttributes;
/*  43:    */     
/*  44:    */ 
/*  45:104 */     this.shareAccess = shareAccess;
/*  46:107 */     if ((flags & 0x40) == 64)
/*  47:    */     {
/*  48:109 */       if ((flags & 0x10) == 16) {
/*  49:111 */         this.createDisposition = 5;
/*  50:    */       } else {
/*  51:113 */         this.createDisposition = 4;
/*  52:    */       }
/*  53:    */     }
/*  54:117 */     else if ((flags & 0x10) == 16)
/*  55:    */     {
/*  56:119 */       if ((flags & 0x20) == 32) {
/*  57:121 */         this.createDisposition = 2;
/*  58:    */       } else {
/*  59:123 */         this.createDisposition = 3;
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else {
/*  63:126 */       this.createDisposition = 1;
/*  64:    */     }
/*  65:130 */     if ((createOptions & 0x1) == 0) {
/*  66:131 */       this.createOptions = (createOptions | 0x40);
/*  67:    */     } else {
/*  68:133 */       this.createOptions = createOptions;
/*  69:    */     }
/*  70:135 */     this.impersonationLevel = 2;
/*  71:136 */     this.securityFlags = 3;
/*  72:    */   }
/*  73:    */   
/*  74:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  75:    */   {
/*  76:140 */     int start = dstIndex;
/*  77:    */     
/*  78:142 */     dst[(dstIndex++)] = 0;
/*  79:    */     
/*  80:144 */     this.namelen_index = dstIndex;
/*  81:145 */     dstIndex += 2;
/*  82:146 */     writeInt4(this.flags0, dst, dstIndex);
/*  83:147 */     dstIndex += 4;
/*  84:148 */     writeInt4(this.rootDirectoryFid, dst, dstIndex);
/*  85:149 */     dstIndex += 4;
/*  86:150 */     writeInt4(this.desiredAccess, dst, dstIndex);
/*  87:151 */     dstIndex += 4;
/*  88:152 */     writeInt8(this.allocationSize, dst, dstIndex);
/*  89:153 */     dstIndex += 8;
/*  90:154 */     writeInt4(this.extFileAttributes, dst, dstIndex);
/*  91:155 */     dstIndex += 4;
/*  92:156 */     writeInt4(this.shareAccess, dst, dstIndex);
/*  93:157 */     dstIndex += 4;
/*  94:158 */     writeInt4(this.createDisposition, dst, dstIndex);
/*  95:159 */     dstIndex += 4;
/*  96:160 */     writeInt4(this.createOptions, dst, dstIndex);
/*  97:161 */     dstIndex += 4;
/*  98:162 */     writeInt4(this.impersonationLevel, dst, dstIndex);
/*  99:163 */     dstIndex += 4;
/* 100:164 */     dst[(dstIndex++)] = this.securityFlags;
/* 101:    */     
/* 102:166 */     return dstIndex - start;
/* 103:    */   }
/* 104:    */   
/* 105:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 106:    */   {
/* 107:170 */     int n = writeString(this.path, dst, dstIndex);
/* 108:171 */     writeInt2(this.useUnicode ? this.path.length() * 2 : n, dst, this.namelen_index);
/* 109:172 */     return n;
/* 110:    */   }
/* 111:    */   
/* 112:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 113:    */   {
/* 114:175 */     return 0;
/* 115:    */   }
/* 116:    */   
/* 117:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 118:    */   {
/* 119:178 */     return 0;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String toString()
/* 123:    */   {
/* 124:181 */     return new String("SmbComNTCreateAndX[" + super.toString() + ",flags=0x" + Hexdump.toHexString(this.flags0, 2) + ",rootDirectoryFid=" + this.rootDirectoryFid + ",desiredAccess=0x" + Hexdump.toHexString(this.desiredAccess, 4) + ",allocationSize=" + this.allocationSize + ",extFileAttributes=0x" + Hexdump.toHexString(this.extFileAttributes, 4) + ",shareAccess=0x" + Hexdump.toHexString(this.shareAccess, 4) + ",createDisposition=0x" + Hexdump.toHexString(this.createDisposition, 4) + ",createOptions=0x" + Hexdump.toHexString(this.createOptions, 8) + ",impersonationLevel=0x" + Hexdump.toHexString(this.impersonationLevel, 4) + ",securityFlags=0x" + Hexdump.toHexString(this.securityFlags, 2) + ",name=" + this.path + "]");
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComNTCreateAndX
 * JD-Core Version:    0.7.0.1
 */