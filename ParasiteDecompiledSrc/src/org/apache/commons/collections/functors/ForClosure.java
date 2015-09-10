/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.collections.Closure;
/*   5:    */ 
/*   6:    */ public class ForClosure
/*   7:    */   implements Closure, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -1190120533393621674L;
/*  10:    */   private final int iCount;
/*  11:    */   private final Closure iClosure;
/*  12:    */   
/*  13:    */   public static Closure getInstance(int count, Closure closure)
/*  14:    */   {
/*  15: 52 */     if ((count <= 0) || (closure == null)) {
/*  16: 53 */       return NOPClosure.INSTANCE;
/*  17:    */     }
/*  18: 55 */     if (count == 1) {
/*  19: 56 */       return closure;
/*  20:    */     }
/*  21: 58 */     return new ForClosure(count, closure);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ForClosure(int count, Closure closure)
/*  25:    */   {
/*  26: 70 */     this.iCount = count;
/*  27: 71 */     this.iClosure = closure;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void execute(Object input)
/*  31:    */   {
/*  32: 80 */     for (int i = 0; i < this.iCount; i++) {
/*  33: 81 */       this.iClosure.execute(input);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Closure getClosure()
/*  38:    */   {
/*  39: 92 */     return this.iClosure;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getCount()
/*  43:    */   {
/*  44:102 */     return this.iCount;
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ForClosure
 * JD-Core Version:    0.7.0.1
 */