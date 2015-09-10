/*   1:    */ package org.apache.james.mime4j.storage;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ 
/*   6:    */ public abstract class StorageOutputStream
/*   7:    */   extends OutputStream
/*   8:    */ {
/*   9:    */   private byte[] singleByte;
/*  10:    */   private boolean closed;
/*  11:    */   private boolean usedUp;
/*  12:    */   
/*  13:    */   public final Storage toStorage()
/*  14:    */     throws IOException
/*  15:    */   {
/*  16: 67 */     if (this.usedUp) {
/*  17: 68 */       throw new IllegalStateException("toStorage may be invoked only once");
/*  18:    */     }
/*  19: 71 */     if (!this.closed) {
/*  20: 72 */       close();
/*  21:    */     }
/*  22: 74 */     this.usedUp = true;
/*  23: 75 */     return toStorage0();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public final void write(int b)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 80 */     if (this.closed) {
/*  30: 81 */       throw new IOException("StorageOutputStream has been closed");
/*  31:    */     }
/*  32: 83 */     if (this.singleByte == null) {
/*  33: 84 */       this.singleByte = new byte[1];
/*  34:    */     }
/*  35: 86 */     this.singleByte[0] = ((byte)b);
/*  36: 87 */     write0(this.singleByte, 0, 1);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final void write(byte[] buffer)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 92 */     if (this.closed) {
/*  43: 93 */       throw new IOException("StorageOutputStream has been closed");
/*  44:    */     }
/*  45: 95 */     if (buffer == null) {
/*  46: 96 */       throw new NullPointerException();
/*  47:    */     }
/*  48: 98 */     if (buffer.length == 0) {
/*  49: 99 */       return;
/*  50:    */     }
/*  51:101 */     write0(buffer, 0, buffer.length);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final void write(byte[] buffer, int offset, int length)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:107 */     if (this.closed) {
/*  58:108 */       throw new IOException("StorageOutputStream has been closed");
/*  59:    */     }
/*  60:110 */     if (buffer == null) {
/*  61:111 */       throw new NullPointerException();
/*  62:    */     }
/*  63:113 */     if ((offset < 0) || (length < 0) || (offset + length > buffer.length)) {
/*  64:114 */       throw new IndexOutOfBoundsException();
/*  65:    */     }
/*  66:116 */     if (length == 0) {
/*  67:117 */       return;
/*  68:    */     }
/*  69:119 */     write0(buffer, offset, length);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void close()
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:134 */     this.closed = true;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected abstract void write0(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  79:    */     throws IOException;
/*  80:    */   
/*  81:    */   protected abstract Storage toStorage0()
/*  82:    */     throws IOException;
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.StorageOutputStream
 * JD-Core Version:    0.7.0.1
 */