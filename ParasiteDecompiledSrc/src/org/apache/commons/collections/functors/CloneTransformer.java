/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Factory;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class CloneTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -8188742709499652567L;
/* 11:39 */   public static final Transformer INSTANCE = new CloneTransformer();
/* 12:   */   
/* 13:   */   public static Transformer getInstance()
/* 14:   */   {
/* 15:48 */     return INSTANCE;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object transform(Object input)
/* 19:   */   {
/* 20:65 */     if (input == null) {
/* 21:66 */       return null;
/* 22:   */     }
/* 23:68 */     return PrototypeFactory.getInstance(input).create();
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.CloneTransformer
 * JD-Core Version:    0.7.0.1
 */