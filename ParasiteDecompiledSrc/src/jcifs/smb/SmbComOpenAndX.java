/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import jcifs.Config;
/*   5:    */ import jcifs.util.Hexdump;
/*   6:    */ 
/*   7:    */ class SmbComOpenAndX
/*   8:    */   extends AndXServerMessageBlock
/*   9:    */ {
/*  10:    */   private static final int FLAGS_RETURN_ADDITIONAL_INFO = 1;
/*  11:    */   private static final int FLAGS_REQUEST_OPLOCK = 2;
/*  12:    */   private static final int FLAGS_REQUEST_BATCH_OPLOCK = 4;
/*  13:    */   private static final int SHARING_COMPATIBILITY = 0;
/*  14:    */   private static final int SHARING_DENY_READ_WRITE_EXECUTE = 16;
/*  15:    */   private static final int SHARING_DENY_WRITE = 32;
/*  16:    */   private static final int SHARING_DENY_READ_EXECUTE = 48;
/*  17:    */   private static final int SHARING_DENY_NONE = 64;
/*  18:    */   private static final int DO_NOT_CACHE = 4096;
/*  19:    */   private static final int WRITE_THROUGH = 16384;
/*  20:    */   private static final int OPEN_FN_CREATE = 16;
/*  21:    */   private static final int OPEN_FN_FAIL_IF_EXISTS = 0;
/*  22:    */   private static final int OPEN_FN_OPEN = 1;
/*  23:    */   private static final int OPEN_FN_TRUNC = 2;
/*  24: 47 */   private static final int BATCH_LIMIT = Config.getInt("jcifs.smb.client.OpenAndX.ReadAndX", 1);
/*  25:    */   int flags;
/*  26:    */   int desiredAccess;
/*  27:    */   int searchAttributes;
/*  28:    */   int fileAttributes;
/*  29:    */   int creationTime;
/*  30:    */   int openFunction;
/*  31:    */   int allocationSize;
/*  32:    */   
/*  33:    */   SmbComOpenAndX(String fileName, int access, int flags, ServerMessageBlock andx)
/*  34:    */   {
/*  35: 60 */     super(andx);
/*  36: 61 */     this.path = fileName;
/*  37: 62 */     this.command = 45;
/*  38:    */     
/*  39: 64 */     this.desiredAccess = (access & 0x3);
/*  40: 65 */     if (this.desiredAccess == 3) {
/*  41: 66 */       this.desiredAccess = 2;
/*  42:    */     }
/*  43: 68 */     this.desiredAccess |= 0x40;
/*  44: 69 */     this.desiredAccess &= 0xFFFFFFFE;
/*  45:    */     
/*  46:    */ 
/*  47: 72 */     this.searchAttributes = 22;
/*  48:    */     
/*  49:    */ 
/*  50: 75 */     this.fileAttributes = 0;
/*  51: 78 */     if ((flags & 0x40) == 64)
/*  52:    */     {
/*  53: 80 */       if ((flags & 0x10) == 16) {
/*  54: 82 */         this.openFunction = 18;
/*  55:    */       } else {
/*  56: 84 */         this.openFunction = 2;
/*  57:    */       }
/*  58:    */     }
/*  59: 88 */     else if ((flags & 0x10) == 16)
/*  60:    */     {
/*  61: 90 */       if ((flags & 0x20) == 32) {
/*  62: 92 */         this.openFunction = 16;
/*  63:    */       } else {
/*  64: 94 */         this.openFunction = 17;
/*  65:    */       }
/*  66:    */     }
/*  67:    */     else {
/*  68: 97 */       this.openFunction = 1;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   int getBatchLimit(byte command)
/*  73:    */   {
/*  74:103 */     return command == 46 ? BATCH_LIMIT : 0;
/*  75:    */   }
/*  76:    */   
/*  77:    */   int writeParameterWordsWireFormat(byte[] dst, int dstIndex)
/*  78:    */   {
/*  79:106 */     int start = dstIndex;
/*  80:    */     
/*  81:108 */     writeInt2(this.flags, dst, dstIndex);
/*  82:109 */     dstIndex += 2;
/*  83:110 */     writeInt2(this.desiredAccess, dst, dstIndex);
/*  84:111 */     dstIndex += 2;
/*  85:112 */     writeInt2(this.searchAttributes, dst, dstIndex);
/*  86:113 */     dstIndex += 2;
/*  87:114 */     writeInt2(this.fileAttributes, dst, dstIndex);
/*  88:115 */     dstIndex += 2;
/*  89:116 */     this.creationTime = 0;
/*  90:117 */     writeInt4(this.creationTime, dst, dstIndex);
/*  91:118 */     dstIndex += 4;
/*  92:119 */     writeInt2(this.openFunction, dst, dstIndex);
/*  93:120 */     dstIndex += 2;
/*  94:121 */     writeInt4(this.allocationSize, dst, dstIndex);
/*  95:122 */     dstIndex += 4;
/*  96:123 */     for (int i = 0; i < 8; i++) {
/*  97:124 */       dst[(dstIndex++)] = 0;
/*  98:    */     }
/*  99:127 */     return dstIndex - start;
/* 100:    */   }
/* 101:    */   
/* 102:    */   int writeBytesWireFormat(byte[] dst, int dstIndex)
/* 103:    */   {
/* 104:130 */     int start = dstIndex;
/* 105:132 */     if (this.useUnicode) {
/* 106:133 */       dst[(dstIndex++)] = 0;
/* 107:    */     }
/* 108:135 */     dstIndex += writeString(this.path, dst, dstIndex);
/* 109:    */     
/* 110:137 */     return dstIndex - start;
/* 111:    */   }
/* 112:    */   
/* 113:    */   int readParameterWordsWireFormat(byte[] buffer, int bufferIndex)
/* 114:    */   {
/* 115:140 */     return 0;
/* 116:    */   }
/* 117:    */   
/* 118:    */   int readBytesWireFormat(byte[] buffer, int bufferIndex)
/* 119:    */   {
/* 120:143 */     return 0;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String toString()
/* 124:    */   {
/* 125:146 */     return new String("SmbComOpenAndX[" + super.toString() + ",flags=0x" + Hexdump.toHexString(this.flags, 2) + ",desiredAccess=0x" + Hexdump.toHexString(this.desiredAccess, 4) + ",searchAttributes=0x" + Hexdump.toHexString(this.searchAttributes, 4) + ",fileAttributes=0x" + Hexdump.toHexString(this.fileAttributes, 4) + ",creationTime=" + new Date(this.creationTime) + ",openFunction=0x" + Hexdump.toHexString(this.openFunction, 2) + ",allocationSize=" + this.allocationSize + ",fileName=" + this.path + "]");
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbComOpenAndX
 * JD-Core Version:    0.7.0.1
 */