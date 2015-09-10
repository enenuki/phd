/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.collections.Closure;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class WhileClosure
/*   8:    */   implements Closure, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -3110538116913760108L;
/*  11:    */   private final Predicate iPredicate;
/*  12:    */   private final Closure iClosure;
/*  13:    */   private final boolean iDoLoop;
/*  14:    */   
/*  15:    */   public static Closure getInstance(Predicate predicate, Closure closure, boolean doLoop)
/*  16:    */   {
/*  17: 55 */     if (predicate == null) {
/*  18: 56 */       throw new IllegalArgumentException("Predicate must not be null");
/*  19:    */     }
/*  20: 58 */     if (closure == null) {
/*  21: 59 */       throw new IllegalArgumentException("Closure must not be null");
/*  22:    */     }
/*  23: 61 */     return new WhileClosure(predicate, closure, doLoop);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public WhileClosure(Predicate predicate, Closure closure, boolean doLoop)
/*  27:    */   {
/*  28: 74 */     this.iPredicate = predicate;
/*  29: 75 */     this.iClosure = closure;
/*  30: 76 */     this.iDoLoop = doLoop;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void execute(Object input)
/*  34:    */   {
/*  35: 85 */     if (this.iDoLoop) {
/*  36: 86 */       this.iClosure.execute(input);
/*  37:    */     }
/*  38: 88 */     while (this.iPredicate.evaluate(input)) {
/*  39: 89 */       this.iClosure.execute(input);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Predicate getPredicate()
/*  44:    */   {
/*  45:100 */     return this.iPredicate;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Closure getClosure()
/*  49:    */   {
/*  50:110 */     return this.iClosure;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isDoLoop()
/*  54:    */   {
/*  55:120 */     return this.iDoLoop;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.WhileClosure
 * JD-Core Version:    0.7.0.1
 */