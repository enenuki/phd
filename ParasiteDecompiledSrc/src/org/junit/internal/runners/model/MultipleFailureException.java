/*  1:   */ package org.junit.internal.runners.model;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ @Deprecated
/*  6:   */ public class MultipleFailureException
/*  7:   */   extends org.junit.runners.model.MultipleFailureException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   
/* 11:   */   public MultipleFailureException(List<Throwable> errors)
/* 12:   */   {
/* 13:10 */     super(errors);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.model.MultipleFailureException
 * JD-Core Version:    0.7.0.1
 */