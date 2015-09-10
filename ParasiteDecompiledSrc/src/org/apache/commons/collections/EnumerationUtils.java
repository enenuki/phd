/*  1:   */ package org.apache.commons.collections;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.List;
/*  5:   */ import org.apache.commons.collections.iterators.EnumerationIterator;
/*  6:   */ 
/*  7:   */ public class EnumerationUtils
/*  8:   */ {
/*  9:   */   public static List toList(Enumeration enumeration)
/* 10:   */   {
/* 11:51 */     return IteratorUtils.toList(new EnumerationIterator(enumeration));
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.EnumerationUtils
 * JD-Core Version:    0.7.0.1
 */