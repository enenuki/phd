/*  1:   */ package org.junit.internal.requests;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.junit.runner.Description;
/*  5:   */ import org.junit.runner.Request;
/*  6:   */ import org.junit.runner.Runner;
/*  7:   */ import org.junit.runner.manipulation.Sorter;
/*  8:   */ 
/*  9:   */ public class SortingRequest
/* 10:   */   extends Request
/* 11:   */ {
/* 12:   */   private final Request fRequest;
/* 13:   */   private final Comparator<Description> fComparator;
/* 14:   */   
/* 15:   */   public SortingRequest(Request request, Comparator<Description> comparator)
/* 16:   */   {
/* 17:15 */     this.fRequest = request;
/* 18:16 */     this.fComparator = comparator;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Runner getRunner()
/* 22:   */   {
/* 23:21 */     Runner runner = this.fRequest.getRunner();
/* 24:22 */     new Sorter(this.fComparator).apply(runner);
/* 25:23 */     return runner;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.requests.SortingRequest
 * JD-Core Version:    0.7.0.1
 */