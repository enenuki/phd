/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import org.hibernate.type.descriptor.BinaryStream;
/*  6:   */ 
/*  7:   */ public class BinaryStreamImpl
/*  8:   */   extends ByteArrayInputStream
/*  9:   */   implements BinaryStream
/* 10:   */ {
/* 11:   */   private final int length;
/* 12:   */   
/* 13:   */   public BinaryStreamImpl(byte[] bytes)
/* 14:   */   {
/* 15:40 */     super(bytes);
/* 16:41 */     this.length = bytes.length;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public InputStream getInputStream()
/* 20:   */   {
/* 21:45 */     return this;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public byte[] getBytes()
/* 25:   */   {
/* 26:50 */     return this.buf;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getLength()
/* 30:   */   {
/* 31:54 */     return this.length;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.BinaryStreamImpl
 * JD-Core Version:    0.7.0.1
 */