/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ 
/*  6:   */ public class NOPTransformer
/*  7:   */   implements Transformer, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 2133891748318574490L;
/* 10:37 */   public static final Transformer INSTANCE = new NOPTransformer();
/* 11:   */   
/* 12:   */   public static Transformer getInstance()
/* 13:   */   {
/* 14:46 */     return INSTANCE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object transform(Object input)
/* 18:   */   {
/* 19:63 */     return input;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NOPTransformer
 * JD-Core Version:    0.7.0.1
 */