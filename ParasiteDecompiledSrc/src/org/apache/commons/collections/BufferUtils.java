/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import org.apache.commons.collections.buffer.BlockingBuffer;
/*   4:    */ import org.apache.commons.collections.buffer.BoundedBuffer;
/*   5:    */ import org.apache.commons.collections.buffer.PredicatedBuffer;
/*   6:    */ import org.apache.commons.collections.buffer.SynchronizedBuffer;
/*   7:    */ import org.apache.commons.collections.buffer.TransformedBuffer;
/*   8:    */ import org.apache.commons.collections.buffer.TypedBuffer;
/*   9:    */ import org.apache.commons.collections.buffer.UnmodifiableBuffer;
/*  10:    */ 
/*  11:    */ public class BufferUtils
/*  12:    */ {
/*  13: 41 */   public static final Buffer EMPTY_BUFFER = UnmodifiableBuffer.decorate(new ArrayStack(1));
/*  14:    */   
/*  15:    */   public static Buffer synchronizedBuffer(Buffer buffer)
/*  16:    */   {
/*  17: 71 */     return SynchronizedBuffer.decorate(buffer);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Buffer blockingBuffer(Buffer buffer)
/*  21:    */   {
/*  22: 87 */     return BlockingBuffer.decorate(buffer);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Buffer blockingBuffer(Buffer buffer, long timeoutMillis)
/*  26:    */   {
/*  27:105 */     return BlockingBuffer.decorate(buffer, timeoutMillis);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Buffer boundedBuffer(Buffer buffer, int maximumSize)
/*  31:    */   {
/*  32:122 */     return BoundedBuffer.decorate(buffer, maximumSize);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Buffer boundedBuffer(Buffer buffer, int maximumSize, long timeoutMillis)
/*  36:    */   {
/*  37:140 */     return BoundedBuffer.decorate(buffer, maximumSize, timeoutMillis);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Buffer unmodifiableBuffer(Buffer buffer)
/*  41:    */   {
/*  42:151 */     return UnmodifiableBuffer.decorate(buffer);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Buffer predicatedBuffer(Buffer buffer, Predicate predicate)
/*  46:    */   {
/*  47:168 */     return PredicatedBuffer.decorate(buffer, predicate);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Buffer typedBuffer(Buffer buffer, Class type)
/*  51:    */   {
/*  52:182 */     return TypedBuffer.decorate(buffer, type);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Buffer transformedBuffer(Buffer buffer, Transformer transformer)
/*  56:    */   {
/*  57:198 */     return TransformedBuffer.decorate(buffer, transformer);
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BufferUtils
 * JD-Core Version:    0.7.0.1
 */