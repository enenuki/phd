/*  1:   */ package org.junit.runner.manipulation;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ 
/*  5:   */ public abstract class Filter
/*  6:   */ {
/*  7:19 */   public static Filter ALL = new Filter()
/*  8:   */   {
/*  9:   */     public boolean shouldRun(Description description)
/* 10:   */     {
/* 11:22 */       return true;
/* 12:   */     }
/* 13:   */     
/* 14:   */     public String describe()
/* 15:   */     {
/* 16:27 */       return "all tests";
/* 17:   */     }
/* 18:   */   };
/* 19:   */   
/* 20:   */   public static Filter matchMethodDescription(Description desiredDescription)
/* 21:   */   {
/* 22:36 */     new Filter()
/* 23:   */     {
/* 24:   */       public boolean shouldRun(Description description)
/* 25:   */       {
/* 26:39 */         if (description.isTest()) {
/* 27:40 */           return this.val$desiredDescription.equals(description);
/* 28:   */         }
/* 29:43 */         for (Description each : description.getChildren()) {
/* 30:44 */           if (shouldRun(each)) {
/* 31:45 */             return true;
/* 32:   */           }
/* 33:   */         }
/* 34:46 */         return false;
/* 35:   */       }
/* 36:   */       
/* 37:   */       public String describe()
/* 38:   */       {
/* 39:51 */         return String.format("Method %s", new Object[] { this.val$desiredDescription.getDisplayName() });
/* 40:   */       }
/* 41:   */     };
/* 42:   */   }
/* 43:   */   
/* 44:   */   public abstract boolean shouldRun(Description paramDescription);
/* 45:   */   
/* 46:   */   public abstract String describe();
/* 47:   */   
/* 48:   */   public void apply(Object child)
/* 49:   */     throws NoTestsRemainException
/* 50:   */   {
/* 51:76 */     if (!(child instanceof Filterable)) {
/* 52:77 */       return;
/* 53:   */     }
/* 54:78 */     Filterable filterable = (Filterable)child;
/* 55:79 */     filterable.filter(this);
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.manipulation.Filter
 * JD-Core Version:    0.7.0.1
 */