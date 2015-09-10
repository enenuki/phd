/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.EOFException;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import org.apache.commons.io.EndianUtils;
/*   8:    */ 
/*   9:    */ public class SwappedDataInputStream
/*  10:    */   extends ProxyInputStream
/*  11:    */   implements DataInput
/*  12:    */ {
/*  13:    */   public SwappedDataInputStream(InputStream input)
/*  14:    */   {
/*  15: 47 */     super(input);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean readBoolean()
/*  19:    */     throws IOException, EOFException
/*  20:    */   {
/*  21: 59 */     return 0 != readByte();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public byte readByte()
/*  25:    */     throws IOException, EOFException
/*  26:    */   {
/*  27: 71 */     return (byte)this.in.read();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public char readChar()
/*  31:    */     throws IOException, EOFException
/*  32:    */   {
/*  33: 83 */     return (char)readShort();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public double readDouble()
/*  37:    */     throws IOException, EOFException
/*  38:    */   {
/*  39: 95 */     return EndianUtils.readSwappedDouble(this.in);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public float readFloat()
/*  43:    */     throws IOException, EOFException
/*  44:    */   {
/*  45:107 */     return EndianUtils.readSwappedFloat(this.in);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void readFully(byte[] data)
/*  49:    */     throws IOException, EOFException
/*  50:    */   {
/*  51:120 */     readFully(data, 0, data.length);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void readFully(byte[] data, int offset, int length)
/*  55:    */     throws IOException, EOFException
/*  56:    */   {
/*  57:136 */     int remaining = length;
/*  58:138 */     while (remaining > 0)
/*  59:    */     {
/*  60:140 */       int location = offset + (length - remaining);
/*  61:141 */       int count = read(data, location, remaining);
/*  62:143 */       if (-1 == count) {
/*  63:145 */         throw new EOFException();
/*  64:    */       }
/*  65:148 */       remaining -= count;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int readInt()
/*  70:    */     throws IOException, EOFException
/*  71:    */   {
/*  72:161 */     return EndianUtils.readSwappedInteger(this.in);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String readLine()
/*  76:    */     throws IOException, EOFException
/*  77:    */   {
/*  78:173 */     throw new UnsupportedOperationException("Operation not supported: readLine()");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public long readLong()
/*  82:    */     throws IOException, EOFException
/*  83:    */   {
/*  84:186 */     return EndianUtils.readSwappedLong(this.in);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public short readShort()
/*  88:    */     throws IOException, EOFException
/*  89:    */   {
/*  90:198 */     return EndianUtils.readSwappedShort(this.in);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int readUnsignedByte()
/*  94:    */     throws IOException, EOFException
/*  95:    */   {
/*  96:210 */     return this.in.read();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int readUnsignedShort()
/* 100:    */     throws IOException, EOFException
/* 101:    */   {
/* 102:222 */     return EndianUtils.readSwappedUnsignedShort(this.in);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String readUTF()
/* 106:    */     throws IOException, EOFException
/* 107:    */   {
/* 108:234 */     throw new UnsupportedOperationException("Operation not supported: readUTF()");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int skipBytes(int count)
/* 112:    */     throws IOException, EOFException
/* 113:    */   {
/* 114:248 */     return (int)this.in.skip(count);
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.SwappedDataInputStream
 * JD-Core Version:    0.7.0.1
 */