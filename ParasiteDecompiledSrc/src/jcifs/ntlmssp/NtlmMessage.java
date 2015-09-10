/*   1:    */ package jcifs.ntlmssp;
/*   2:    */ 
/*   3:    */ import jcifs.Config;
/*   4:    */ 
/*   5:    */ public abstract class NtlmMessage
/*   6:    */   implements NtlmFlags
/*   7:    */ {
/*   8: 32 */   protected static final byte[] NTLMSSP_SIGNATURE = { 78, 84, 76, 77, 83, 83, 80, 0 };
/*   9: 37 */   private static final String OEM_ENCODING = Config.DEFAULT_OEM_ENCODING;
/*  10:    */   protected static final String UNI_ENCODING = "UTF-16LE";
/*  11:    */   private int flags;
/*  12:    */   
/*  13:    */   public int getFlags()
/*  14:    */   {
/*  15: 49 */     return this.flags;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setFlags(int flags)
/*  19:    */   {
/*  20: 58 */     this.flags = flags;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean getFlag(int flag)
/*  24:    */   {
/*  25: 68 */     return (getFlags() & flag) != 0;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setFlag(int flag, boolean value)
/*  29:    */   {
/*  30: 80 */     setFlags(value ? getFlags() | flag : getFlags() & (0xFFFFFFFF ^ flag));
/*  31:    */   }
/*  32:    */   
/*  33:    */   static int readULong(byte[] src, int index)
/*  34:    */   {
/*  35: 85 */     return src[index] & 0xFF | (src[(index + 1)] & 0xFF) << 8 | (src[(index + 2)] & 0xFF) << 16 | (src[(index + 3)] & 0xFF) << 24;
/*  36:    */   }
/*  37:    */   
/*  38:    */   static int readUShort(byte[] src, int index)
/*  39:    */   {
/*  40: 92 */     return src[index] & 0xFF | (src[(index + 1)] & 0xFF) << 8;
/*  41:    */   }
/*  42:    */   
/*  43:    */   static byte[] readSecurityBuffer(byte[] src, int index)
/*  44:    */   {
/*  45: 96 */     int length = readUShort(src, index);
/*  46: 97 */     int offset = readULong(src, index + 4);
/*  47: 98 */     byte[] buffer = new byte[length];
/*  48: 99 */     System.arraycopy(src, offset, buffer, 0, length);
/*  49:100 */     return buffer;
/*  50:    */   }
/*  51:    */   
/*  52:    */   static void writeULong(byte[] dest, int offset, int ulong)
/*  53:    */   {
/*  54:104 */     dest[offset] = ((byte)(ulong & 0xFF));
/*  55:105 */     dest[(offset + 1)] = ((byte)(ulong >> 8 & 0xFF));
/*  56:106 */     dest[(offset + 2)] = ((byte)(ulong >> 16 & 0xFF));
/*  57:107 */     dest[(offset + 3)] = ((byte)(ulong >> 24 & 0xFF));
/*  58:    */   }
/*  59:    */   
/*  60:    */   static void writeUShort(byte[] dest, int offset, int ushort)
/*  61:    */   {
/*  62:111 */     dest[offset] = ((byte)(ushort & 0xFF));
/*  63:112 */     dest[(offset + 1)] = ((byte)(ushort >> 8 & 0xFF));
/*  64:    */   }
/*  65:    */   
/*  66:    */   static void writeSecurityBuffer(byte[] dest, int offset, int bodyOffset, byte[] src)
/*  67:    */   {
/*  68:117 */     int length = src != null ? src.length : 0;
/*  69:118 */     if (length == 0) {
/*  70:118 */       return;
/*  71:    */     }
/*  72:119 */     writeUShort(dest, offset, length);
/*  73:120 */     writeUShort(dest, offset + 2, length);
/*  74:121 */     writeULong(dest, offset + 4, bodyOffset);
/*  75:122 */     System.arraycopy(src, 0, dest, bodyOffset, length);
/*  76:    */   }
/*  77:    */   
/*  78:    */   static String getOEMEncoding()
/*  79:    */   {
/*  80:126 */     return OEM_ENCODING;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public abstract byte[] toByteArray();
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.ntlmssp.NtlmMessage
 * JD-Core Version:    0.7.0.1
 */