/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.Config;
/*   4:    */ import jcifs.util.Hexdump;
/*   5:    */ 
/*   6:    */ class Trans2FindFirst2
/*   7:    */   extends SmbComTransaction
/*   8:    */ {
/*   9:    */   private static final int FLAGS_CLOSE_AFTER_THIS_REQUEST = 1;
/*  10:    */   private static final int FLAGS_CLOSE_IF_END_REACHED = 2;
/*  11:    */   private static final int FLAGS_RETURN_RESUME_KEYS = 4;
/*  12:    */   private static final int FLAGS_RESUME_FROM_PREVIOUS_END = 8;
/*  13:    */   private static final int FLAGS_FIND_WITH_BACKUP_INTENT = 16;
/*  14:    */   private static final int DEFAULT_LIST_SIZE = 65535;
/*  15:    */   private static final int DEFAULT_LIST_COUNT = 200;
/*  16:    */   private int searchAttributes;
/*  17:    */   private int flags;
/*  18:    */   private int informationLevel;
/*  19: 40 */   private int searchStorageType = 0;
/*  20:    */   private String wildcard;
/*  21:    */   static final int SMB_INFO_STANDARD = 1;
/*  22:    */   static final int SMB_INFO_QUERY_EA_SIZE = 2;
/*  23:    */   static final int SMB_INFO_QUERY_EAS_FROM_LIST = 3;
/*  24:    */   static final int SMB_FIND_FILE_DIRECTORY_INFO = 257;
/*  25:    */   static final int SMB_FIND_FILE_FULL_DIRECTORY_INFO = 258;
/*  26:    */   static final int SMB_FILE_NAMES_INFO = 259;
/*  27:    */   static final int SMB_FILE_BOTH_DIRECTORY_INFO = 260;
/*  28: 53 */   static final int LIST_SIZE = Config.getInt("jcifs.smb.client.listSize", 65535);
/*  29: 54 */   static final int LIST_COUNT = Config.getInt("jcifs.smb.client.listCount", 200);
/*  30:    */   
/*  31:    */   Trans2FindFirst2(String filename, String wildcard, int searchAttributes)
/*  32:    */   {
/*  33: 57 */     if (filename.equals("\\")) {
/*  34: 58 */       this.path = filename;
/*  35:    */     } else {
/*  36: 60 */       this.path = (filename + "\\");
/*  37:    */     }
/*  38: 62 */     this.wildcard = wildcard;
/*  39: 63 */     this.searchAttributes = (searchAttributes & 0x37);
/*  40: 64 */     this.command = 50;
/*  41: 65 */     this.subCommand = 1;
/*  42:    */     
/*  43: 67 */     this.flags = 0;
/*  44: 68 */     this.informationLevel = 260;
/*  45:    */     
/*  46: 70 */     this.totalDataCount = 0;
/*  47: 71 */     this.maxParameterCount = 10;
/*  48: 72 */     this.maxDataCount = LIST_SIZE;
/*  49: 73 */     this.maxSetupCount = 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  53:    */   {
/*  54: 77 */     dst[(dstIndex++)] = this.subCommand;
/*  55: 78 */     dst[(dstIndex++)] = 0;
/*  56: 79 */     return 2;
/*  57:    */   }
/*  58:    */   
/*  59:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/*  60:    */   {
/*  61: 82 */     int start = dstIndex;
/*  62:    */     
/*  63: 84 */     writeInt2(this.searchAttributes, dst, dstIndex);
/*  64: 85 */     dstIndex += 2;
/*  65: 86 */     writeInt2(LIST_COUNT, dst, dstIndex);
/*  66: 87 */     dstIndex += 2;
/*  67: 88 */     writeInt2(this.flags, dst, dstIndex);
/*  68: 89 */     dstIndex += 2;
/*  69: 90 */     writeInt2(this.informationLevel, dst, dstIndex);
/*  70: 91 */     dstIndex += 2;
/*  71: 92 */     writeInt4(this.searchStorageType, dst, dstIndex);
/*  72: 93 */     dstIndex += 4;
/*  73: 94 */     dstIndex += writeString(this.path + this.wildcard, dst, dstIndex);
/*  74:    */     
/*  75: 96 */     return dstIndex - start;
/*  76:    */   }
/*  77:    */   
/*  78:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/*  79:    */   {
/*  80: 99 */     return 0;
/*  81:    */   }
/*  82:    */   
/*  83:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/*  84:    */   {
/*  85:102 */     return 0;
/*  86:    */   }
/*  87:    */   
/*  88:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/*  89:    */   {
/*  90:105 */     return 0;
/*  91:    */   }
/*  92:    */   
/*  93:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/*  94:    */   {
/*  95:108 */     return 0;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toString()
/*  99:    */   {
/* 100:111 */     return new String("Trans2FindFirst2[" + super.toString() + ",searchAttributes=0x" + Hexdump.toHexString(this.searchAttributes, 2) + ",searchCount=" + LIST_COUNT + ",flags=0x" + Hexdump.toHexString(this.flags, 2) + ",informationLevel=0x" + Hexdump.toHexString(this.informationLevel, 3) + ",searchStorageType=" + this.searchStorageType + ",filename=" + this.path + "]");
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Trans2FindFirst2
 * JD-Core Version:    0.7.0.1
 */