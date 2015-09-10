/*  1:   */ package javassist.runtime;
/*  2:   */ 
/*  3:   */ public class Cflow
/*  4:   */   extends ThreadLocal
/*  5:   */ {
/*  6:   */   private static class Depth
/*  7:   */   {
/*  8:28 */     private int depth = 0;
/*  9:   */     
/* 10:   */     int get()
/* 11:   */     {
/* 12:29 */       return this.depth;
/* 13:   */     }
/* 14:   */     
/* 15:   */     void inc()
/* 16:   */     {
/* 17:30 */       this.depth += 1;
/* 18:   */     }
/* 19:   */     
/* 20:   */     void dec()
/* 21:   */     {
/* 22:31 */       this.depth -= 1;
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected synchronized Object initialValue()
/* 27:   */   {
/* 28:35 */     return new Depth();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void enter()
/* 32:   */   {
/* 33:41 */     ((Depth)get()).inc();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void exit()
/* 37:   */   {
/* 38:46 */     ((Depth)get()).dec();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int value()
/* 42:   */   {
/* 43:51 */     return ((Depth)get()).get();
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.runtime.Cflow
 * JD-Core Version:    0.7.0.1
 */