/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.UUID;
/*   7:    */ import org.apache.commons.io.TaggedIOException;
/*   8:    */ 
/*   9:    */ public class TaggedOutputStream
/*  10:    */   extends ProxyOutputStream
/*  11:    */ {
/*  12: 69 */   private final Serializable tag = UUID.randomUUID();
/*  13:    */   
/*  14:    */   public TaggedOutputStream(OutputStream proxy)
/*  15:    */   {
/*  16: 77 */     super(proxy);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean isCauseOf(Exception exception)
/*  20:    */   {
/*  21: 88 */     return TaggedIOException.isTaggedWith(exception, this.tag);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void throwIfCauseOf(Exception exception)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:102 */     TaggedIOException.throwCauseIfTaggedWith(exception, this.tag);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void handleIOException(IOException e)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33:113 */     throw new TaggedIOException(e, this.tag);
/*  34:    */   }
/*  35:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.TaggedOutputStream
 * JD-Core Version:    0.7.0.1
 */