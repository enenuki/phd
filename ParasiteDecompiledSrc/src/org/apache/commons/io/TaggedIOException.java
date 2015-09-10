/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class TaggedIOException
/*   7:    */   extends IOExceptionWithCause
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -6994123481142850163L;
/*  10:    */   private final Serializable tag;
/*  11:    */   
/*  12:    */   public static boolean isTaggedWith(Throwable throwable, Object tag)
/*  13:    */   {
/*  14: 65 */     return (tag != null) && ((throwable instanceof TaggedIOException)) && (tag.equals(((TaggedIOException)throwable).tag));
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static void throwCauseIfTaggedWith(Throwable throwable, Object tag)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 94 */     if (isTaggedWith(throwable, tag)) {
/*  21: 95 */       throw ((TaggedIOException)throwable).getCause();
/*  22:    */     }
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TaggedIOException(IOException original, Serializable tag)
/*  26:    */   {
/*  27:111 */     super(original.getMessage(), original);
/*  28:112 */     this.tag = tag;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Serializable getTag()
/*  32:    */   {
/*  33:121 */     return this.tag;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IOException getCause()
/*  37:    */   {
/*  38:132 */     return (IOException)super.getCause();
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.TaggedIOException
 * JD-Core Version:    0.7.0.1
 */