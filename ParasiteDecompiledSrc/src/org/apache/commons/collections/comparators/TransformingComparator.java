/*  1:   */ package org.apache.commons.collections.comparators;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ 
/*  6:   */ public class TransformingComparator
/*  7:   */   implements Comparator
/*  8:   */ {
/*  9:   */   protected Comparator decorated;
/* 10:   */   protected Transformer transformer;
/* 11:   */   
/* 12:   */   public TransformingComparator(Transformer transformer)
/* 13:   */   {
/* 14:49 */     this(transformer, new ComparableComparator());
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TransformingComparator(Transformer transformer, Comparator decorated)
/* 18:   */   {
/* 19:59 */     this.decorated = decorated;
/* 20:60 */     this.transformer = transformer;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int compare(Object obj1, Object obj2)
/* 24:   */   {
/* 25:72 */     Object value1 = this.transformer.transform(obj1);
/* 26:73 */     Object value2 = this.transformer.transform(obj2);
/* 27:74 */     return this.decorated.compare(value1, value2);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.TransformingComparator
 * JD-Core Version:    0.7.0.1
 */