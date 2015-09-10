/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.collections.Closure;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public class IfClosure
/*   8:    */   implements Closure, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 3518477308466486130L;
/*  11:    */   private final Predicate iPredicate;
/*  12:    */   private final Closure iTrueClosure;
/*  13:    */   private final Closure iFalseClosure;
/*  14:    */   
/*  15:    */   public static Closure getInstance(Predicate predicate, Closure trueClosure)
/*  16:    */   {
/*  17: 59 */     return getInstance(predicate, trueClosure, NOPClosure.INSTANCE);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Closure getInstance(Predicate predicate, Closure trueClosure, Closure falseClosure)
/*  21:    */   {
/*  22: 72 */     if (predicate == null) {
/*  23: 73 */       throw new IllegalArgumentException("Predicate must not be null");
/*  24:    */     }
/*  25: 75 */     if ((trueClosure == null) || (falseClosure == null)) {
/*  26: 76 */       throw new IllegalArgumentException("Closures must not be null");
/*  27:    */     }
/*  28: 78 */     return new IfClosure(predicate, trueClosure, falseClosure);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IfClosure(Predicate predicate, Closure trueClosure)
/*  32:    */   {
/*  33: 93 */     this(predicate, trueClosure, NOPClosure.INSTANCE);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IfClosure(Predicate predicate, Closure trueClosure, Closure falseClosure)
/*  37:    */   {
/*  38:106 */     this.iPredicate = predicate;
/*  39:107 */     this.iTrueClosure = trueClosure;
/*  40:108 */     this.iFalseClosure = falseClosure;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void execute(Object input)
/*  44:    */   {
/*  45:117 */     if (this.iPredicate.evaluate(input) == true) {
/*  46:118 */       this.iTrueClosure.execute(input);
/*  47:    */     } else {
/*  48:120 */       this.iFalseClosure.execute(input);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Predicate getPredicate()
/*  53:    */   {
/*  54:131 */     return this.iPredicate;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Closure getTrueClosure()
/*  58:    */   {
/*  59:141 */     return this.iTrueClosure;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Closure getFalseClosure()
/*  63:    */   {
/*  64:151 */     return this.iFalseClosure;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.IfClosure
 * JD-Core Version:    0.7.0.1
 */