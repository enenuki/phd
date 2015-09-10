/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.UUID;
/*   7:    */ import org.apache.commons.io.TaggedIOException;
/*   8:    */ 
/*   9:    */ public class TaggedInputStream
/*  10:    */   extends ProxyInputStream
/*  11:    */ {
/*  12: 69 */   private final Serializable tag = UUID.randomUUID();
/*  13:    */   
/*  14:    */   public TaggedInputStream(InputStream proxy)
/*  15:    */   {
/*  16: 77 */     super(proxy);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean isCauseOf(Throwable exception)
/*  20:    */   {
/*  21: 88 */     return TaggedIOException.isTaggedWith(exception, this.tag);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void throwIfCauseOf(Throwable throwable)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:102 */     TaggedIOException.throwCauseIfTaggedWith(throwable, this.tag);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void handleIOException(IOException e)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33:113 */     throw new TaggedIOException(e, this.tag);
/*  34:    */   }
/*  35:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.TaggedInputStream
 * JD-Core Version:    0.7.0.1
 */