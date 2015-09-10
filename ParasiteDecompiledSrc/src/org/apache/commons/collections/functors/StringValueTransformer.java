/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ 
/*  6:   */ public final class StringValueTransformer
/*  7:   */   implements Transformer, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 7511110693171758606L;
/* 10:38 */   public static final Transformer INSTANCE = new StringValueTransformer();
/* 11:   */   
/* 12:   */   public static Transformer getInstance()
/* 13:   */   {
/* 14:47 */     return INSTANCE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object transform(Object input)
/* 18:   */   {
/* 19:64 */     return String.valueOf(input);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.StringValueTransformer
 * JD-Core Version:    0.7.0.1
 */