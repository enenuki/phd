/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ class ByteVector
/*  4:   */   implements Cloneable
/*  5:   */ {
/*  6:   */   private byte[] buffer;
/*  7:   */   private int size;
/*  8:   */   
/*  9:   */   public ByteVector()
/* 10:   */   {
/* 11:26 */     this.buffer = new byte[64];
/* 12:27 */     this.size = 0;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Object clone()
/* 16:   */     throws CloneNotSupportedException
/* 17:   */   {
/* 18:31 */     ByteVector bv = (ByteVector)super.clone();
/* 19:32 */     bv.buffer = ((byte[])this.buffer.clone());
/* 20:33 */     return bv;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public final int getSize()
/* 24:   */   {
/* 25:36 */     return this.size;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public final byte[] copy()
/* 29:   */   {
/* 30:39 */     byte[] b = new byte[this.size];
/* 31:40 */     System.arraycopy(this.buffer, 0, b, 0, this.size);
/* 32:41 */     return b;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int read(int offset)
/* 36:   */   {
/* 37:45 */     if ((offset < 0) || (this.size <= offset)) {
/* 38:46 */       throw new ArrayIndexOutOfBoundsException(offset);
/* 39:   */     }
/* 40:48 */     return this.buffer[offset];
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void write(int offset, int value)
/* 44:   */   {
/* 45:52 */     if ((offset < 0) || (this.size <= offset)) {
/* 46:53 */       throw new ArrayIndexOutOfBoundsException(offset);
/* 47:   */     }
/* 48:55 */     this.buffer[offset] = ((byte)value);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void add(int code)
/* 52:   */   {
/* 53:59 */     addGap(1);
/* 54:60 */     this.buffer[(this.size - 1)] = ((byte)code);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void add(int b1, int b2)
/* 58:   */   {
/* 59:64 */     addGap(2);
/* 60:65 */     this.buffer[(this.size - 2)] = ((byte)b1);
/* 61:66 */     this.buffer[(this.size - 1)] = ((byte)b2);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void add(int b1, int b2, int b3, int b4)
/* 65:   */   {
/* 66:70 */     addGap(4);
/* 67:71 */     this.buffer[(this.size - 4)] = ((byte)b1);
/* 68:72 */     this.buffer[(this.size - 3)] = ((byte)b2);
/* 69:73 */     this.buffer[(this.size - 2)] = ((byte)b3);
/* 70:74 */     this.buffer[(this.size - 1)] = ((byte)b4);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void addGap(int length)
/* 74:   */   {
/* 75:78 */     if (this.size + length > this.buffer.length)
/* 76:   */     {
/* 77:79 */       int newSize = this.size << 1;
/* 78:80 */       if (newSize < this.size + length) {
/* 79:81 */         newSize = this.size + length;
/* 80:   */       }
/* 81:83 */       byte[] newBuf = new byte[newSize];
/* 82:84 */       System.arraycopy(this.buffer, 0, newBuf, 0, this.size);
/* 83:85 */       this.buffer = newBuf;
/* 84:   */     }
/* 85:88 */     this.size += length;
/* 86:   */   }
/* 87:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ByteVector
 * JD-Core Version:    0.7.0.1
 */