/*  1:   */ package org.hamcrest.internal;
/*  2:   */ 
/*  3:   */ import org.hamcrest.Description;
/*  4:   */ import org.hamcrest.SelfDescribing;
/*  5:   */ 
/*  6:   */ public class SelfDescribingValue<T>
/*  7:   */   implements SelfDescribing
/*  8:   */ {
/*  9:   */   private T value;
/* 10:   */   
/* 11:   */   public SelfDescribingValue(T value)
/* 12:   */   {
/* 13:10 */     this.value = value;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void describeTo(Description description)
/* 17:   */   {
/* 18:14 */     description.appendValue(this.value);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.internal.SelfDescribingValue
 * JD-Core Version:    0.7.0.1
 */