/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ import org.apache.commons.collections.collection.TransformedCollection;
/*  6:   */ 
/*  7:   */ public class TransformedSet
/*  8:   */   extends TransformedCollection
/*  9:   */   implements Set
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 306127383500410386L;
/* 12:   */   
/* 13:   */   public static Set decorate(Set set, Transformer transformer)
/* 14:   */   {
/* 15:55 */     return new TransformedSet(set, transformer);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected TransformedSet(Set set, Transformer transformer)
/* 19:   */   {
/* 20:70 */     super(set, transformer);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.TransformedSet
 * JD-Core Version:    0.7.0.1
 */