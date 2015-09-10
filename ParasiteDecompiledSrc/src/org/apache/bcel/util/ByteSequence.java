/*  1:   */ package org.apache.bcel.util;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.DataInputStream;
/*  5:   */ import java.io.FilterInputStream;
/*  6:   */ 
/*  7:   */ public final class ByteSequence
/*  8:   */   extends DataInputStream
/*  9:   */ {
/* 10:   */   private ByteArrayStream byte_stream;
/* 11:   */   
/* 12:   */   public ByteSequence(byte[] bytes)
/* 13:   */   {
/* 14:70 */     super(new ByteArrayStream(bytes));
/* 15:71 */     this.byte_stream = ((ByteArrayStream)this.in);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public final int getIndex()
/* 19:   */   {
/* 20:74 */     return this.byte_stream.getPosition();
/* 21:   */   }
/* 22:   */   
/* 23:   */   final void unreadByte()
/* 24:   */   {
/* 25:75 */     this.byte_stream.unreadByte();
/* 26:   */   }
/* 27:   */   
/* 28:   */   private static final class ByteArrayStream
/* 29:   */     extends ByteArrayInputStream
/* 30:   */   {
/* 31:   */     ByteArrayStream(byte[] bytes)
/* 32:   */     {
/* 33:78 */       super();
/* 34:   */     }
/* 35:   */     
/* 36:   */     final int getPosition()
/* 37:   */     {
/* 38:79 */       return this.pos;
/* 39:   */     }
/* 40:   */     
/* 41:   */     final void unreadByte()
/* 42:   */     {
/* 43:80 */       if (this.pos > 0) {
/* 44:80 */         this.pos -= 1;
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ByteSequence
 * JD-Core Version:    0.7.0.1
 */