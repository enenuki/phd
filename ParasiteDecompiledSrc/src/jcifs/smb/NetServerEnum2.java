/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ 
/*   5:    */ class NetServerEnum2
/*   6:    */   extends SmbComTransaction
/*   7:    */ {
/*   8:    */   static final int SV_TYPE_ALL = -1;
/*   9:    */   static final int SV_TYPE_DOMAIN_ENUM = -2147483648;
/*  10: 29 */   static final String[] DESCR = { "", "" };
/*  11:    */   String domain;
/*  12: 34 */   String lastName = null;
/*  13:    */   int serverTypes;
/*  14:    */   
/*  15:    */   NetServerEnum2(String domain, int serverTypes)
/*  16:    */   {
/*  17: 38 */     this.domain = domain;
/*  18: 39 */     this.serverTypes = serverTypes;
/*  19: 40 */     this.command = 37;
/*  20: 41 */     this.subCommand = 104;
/*  21: 42 */     this.name = "\\PIPE\\LANMAN";
/*  22:    */     
/*  23: 44 */     this.maxParameterCount = 8;
/*  24:    */     
/*  25: 46 */     this.maxDataCount = 16384;
/*  26: 47 */     this.maxSetupCount = 0;
/*  27: 48 */     this.setupCount = 0;
/*  28: 49 */     this.timeout = 5000;
/*  29:    */   }
/*  30:    */   
/*  31:    */   void reset(int key, String lastName)
/*  32:    */   {
/*  33: 53 */     super.reset();
/*  34: 54 */     this.lastName = lastName;
/*  35:    */   }
/*  36:    */   
/*  37:    */   int writeSetupWireFormat(byte[] dst, int dstIndex)
/*  38:    */   {
/*  39: 58 */     return 0;
/*  40:    */   }
/*  41:    */   
/*  42:    */   int writeParametersWireFormat(byte[] dst, int dstIndex)
/*  43:    */   {
/*  44: 61 */     int start = dstIndex;
/*  45:    */     
/*  46: 63 */     int which = this.subCommand == 104 ? 0 : 1;
/*  47:    */     try
/*  48:    */     {
/*  49: 66 */       descr = DESCR[which].getBytes("ASCII");
/*  50:    */     }
/*  51:    */     catch (UnsupportedEncodingException uee)
/*  52:    */     {
/*  53:    */       byte[] descr;
/*  54: 68 */       return 0;
/*  55:    */     }
/*  56:    */     byte[] descr;
/*  57: 71 */     writeInt2(this.subCommand & 0xFF, dst, dstIndex);
/*  58: 72 */     dstIndex += 2;
/*  59: 73 */     System.arraycopy(descr, 0, dst, dstIndex, descr.length);
/*  60: 74 */     dstIndex += descr.length;
/*  61: 75 */     writeInt2(1L, dst, dstIndex);
/*  62: 76 */     dstIndex += 2;
/*  63: 77 */     writeInt2(this.maxDataCount, dst, dstIndex);
/*  64: 78 */     dstIndex += 2;
/*  65: 79 */     writeInt4(this.serverTypes, dst, dstIndex);
/*  66: 80 */     dstIndex += 4;
/*  67: 81 */     dstIndex += writeString(this.domain.toUpperCase(), dst, dstIndex, false);
/*  68: 82 */     if (which == 1) {
/*  69: 83 */       dstIndex += writeString(this.lastName.toUpperCase(), dst, dstIndex, false);
/*  70:    */     }
/*  71: 86 */     return dstIndex - start;
/*  72:    */   }
/*  73:    */   
/*  74:    */   int writeDataWireFormat(byte[] dst, int dstIndex)
/*  75:    */   {
/*  76: 89 */     return 0;
/*  77:    */   }
/*  78:    */   
/*  79:    */   int readSetupWireFormat(byte[] buffer, int bufferIndex, int len)
/*  80:    */   {
/*  81: 92 */     return 0;
/*  82:    */   }
/*  83:    */   
/*  84:    */   int readParametersWireFormat(byte[] buffer, int bufferIndex, int len)
/*  85:    */   {
/*  86: 95 */     return 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   int readDataWireFormat(byte[] buffer, int bufferIndex, int len)
/*  90:    */   {
/*  91: 98 */     return 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String toString()
/*  95:    */   {
/*  96:101 */     return new String("NetServerEnum2[" + super.toString() + ",name=" + this.name + ",serverTypes=" + (this.serverTypes == -1 ? "SV_TYPE_ALL" : "SV_TYPE_DOMAIN_ENUM") + "]");
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NetServerEnum2
 * JD-Core Version:    0.7.0.1
 */