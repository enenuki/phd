/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public abstract class SessionServicePacket
/*   7:    */ {
/*   8:    */   static final int SESSION_MESSAGE = 0;
/*   9:    */   static final int SESSION_REQUEST = 129;
/*  10:    */   public static final int POSITIVE_SESSION_RESPONSE = 130;
/*  11:    */   public static final int NEGATIVE_SESSION_RESPONSE = 131;
/*  12:    */   static final int SESSION_RETARGET_RESPONSE = 132;
/*  13:    */   static final int SESSION_KEEP_ALIVE = 133;
/*  14:    */   static final int MAX_MESSAGE_SIZE = 131071;
/*  15:    */   static final int HEADER_LENGTH = 4;
/*  16:    */   int type;
/*  17:    */   int length;
/*  18:    */   
/*  19:    */   static void writeInt2(int val, byte[] dst, int dstIndex)
/*  20:    */   {
/*  21: 38 */     dst[(dstIndex++)] = ((byte)(val >> 8 & 0xFF));
/*  22: 39 */     dst[dstIndex] = ((byte)(val & 0xFF));
/*  23:    */   }
/*  24:    */   
/*  25:    */   static void writeInt4(int val, byte[] dst, int dstIndex)
/*  26:    */   {
/*  27: 42 */     dst[(dstIndex++)] = ((byte)(val >> 24 & 0xFF));
/*  28: 43 */     dst[(dstIndex++)] = ((byte)(val >> 16 & 0xFF));
/*  29: 44 */     dst[(dstIndex++)] = ((byte)(val >> 8 & 0xFF));
/*  30: 45 */     dst[dstIndex] = ((byte)(val & 0xFF));
/*  31:    */   }
/*  32:    */   
/*  33:    */   static int readInt2(byte[] src, int srcIndex)
/*  34:    */   {
/*  35: 48 */     return ((src[srcIndex] & 0xFF) << 8) + (src[(srcIndex + 1)] & 0xFF);
/*  36:    */   }
/*  37:    */   
/*  38:    */   static int readInt4(byte[] src, int srcIndex)
/*  39:    */   {
/*  40: 52 */     return ((src[srcIndex] & 0xFF) << 24) + ((src[(srcIndex + 1)] & 0xFF) << 16) + ((src[(srcIndex + 2)] & 0xFF) << 8) + (src[(srcIndex + 3)] & 0xFF);
/*  41:    */   }
/*  42:    */   
/*  43:    */   static int readLength(byte[] src, int srcIndex)
/*  44:    */   {
/*  45: 58 */     srcIndex++;
/*  46: 59 */     return ((src[(srcIndex++)] & 0x1) << 16) + ((src[(srcIndex++)] & 0xFF) << 8) + (src[(srcIndex++)] & 0xFF);
/*  47:    */   }
/*  48:    */   
/*  49:    */   static int readn(InputStream in, byte[] b, int off, int len)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52: 67 */     int i = 0;
/*  53: 69 */     while (i < len)
/*  54:    */     {
/*  55: 70 */       int n = in.read(b, off + i, len - i);
/*  56: 71 */       if (n <= 0) {
/*  57:    */         break;
/*  58:    */       }
/*  59: 74 */       i += n;
/*  60:    */     }
/*  61: 77 */     return i;
/*  62:    */   }
/*  63:    */   
/*  64:    */   static int readPacketType(InputStream in, byte[] buffer, int bufferIndex)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:    */     int n;
/*  68: 84 */     if ((n = readn(in, buffer, bufferIndex, 4)) != 4)
/*  69:    */     {
/*  70: 85 */       if (n == -1) {
/*  71: 86 */         return -1;
/*  72:    */       }
/*  73: 88 */       throw new IOException("unexpected EOF reading netbios session header");
/*  74:    */     }
/*  75: 90 */     int t = buffer[bufferIndex] & 0xFF;
/*  76: 91 */     return t;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int writeWireFormat(byte[] dst, int dstIndex)
/*  80:    */   {
/*  81: 97 */     this.length = writeTrailerWireFormat(dst, dstIndex + 4);
/*  82: 98 */     writeHeaderWireFormat(dst, dstIndex);
/*  83: 99 */     return 4 + this.length;
/*  84:    */   }
/*  85:    */   
/*  86:    */   int readWireFormat(InputStream in, byte[] buffer, int bufferIndex)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:102 */     readHeaderWireFormat(in, buffer, bufferIndex);
/*  90:103 */     return 4 + readTrailerWireFormat(in, buffer, bufferIndex);
/*  91:    */   }
/*  92:    */   
/*  93:    */   int writeHeaderWireFormat(byte[] dst, int dstIndex)
/*  94:    */   {
/*  95:106 */     dst[(dstIndex++)] = ((byte)this.type);
/*  96:107 */     if (this.length > 65535) {
/*  97:108 */       dst[dstIndex] = 1;
/*  98:    */     }
/*  99:110 */     dstIndex++;
/* 100:111 */     writeInt2(this.length, dst, dstIndex);
/* 101:112 */     return 4;
/* 102:    */   }
/* 103:    */   
/* 104:    */   int readHeaderWireFormat(InputStream in, byte[] buffer, int bufferIndex)
/* 105:    */     throws IOException
/* 106:    */   {
/* 107:118 */     this.type = (buffer[(bufferIndex++)] & 0xFF);
/* 108:119 */     this.length = (((buffer[bufferIndex] & 0x1) << 16) + readInt2(buffer, bufferIndex + 1));
/* 109:120 */     return 4;
/* 110:    */   }
/* 111:    */   
/* 112:    */   abstract int writeTrailerWireFormat(byte[] paramArrayOfByte, int paramInt);
/* 113:    */   
/* 114:    */   abstract int readTrailerWireFormat(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
/* 115:    */     throws IOException;
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.SessionServicePacket
 * JD-Core Version:    0.7.0.1
 */