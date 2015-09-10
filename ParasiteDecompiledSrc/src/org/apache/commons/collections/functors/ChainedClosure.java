/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import org.apache.commons.collections.Closure;
/*   7:    */ 
/*   8:    */ public class ChainedClosure
/*   9:    */   implements Closure, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -3520677225766901240L;
/*  12:    */   private final Closure[] iClosures;
/*  13:    */   
/*  14:    */   public static Closure getInstance(Closure[] closures)
/*  15:    */   {
/*  16: 50 */     FunctorUtils.validate(closures);
/*  17: 51 */     if (closures.length == 0) {
/*  18: 52 */       return NOPClosure.INSTANCE;
/*  19:    */     }
/*  20: 54 */     closures = FunctorUtils.copy(closures);
/*  21: 55 */     return new ChainedClosure(closures);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static Closure getInstance(Collection closures)
/*  25:    */   {
/*  26: 69 */     if (closures == null) {
/*  27: 70 */       throw new IllegalArgumentException("Closure collection must not be null");
/*  28:    */     }
/*  29: 72 */     if (closures.size() == 0) {
/*  30: 73 */       return NOPClosure.INSTANCE;
/*  31:    */     }
/*  32: 76 */     Closure[] cmds = new Closure[closures.size()];
/*  33: 77 */     int i = 0;
/*  34: 78 */     for (Iterator it = closures.iterator(); it.hasNext();) {
/*  35: 79 */       cmds[(i++)] = ((Closure)it.next());
/*  36:    */     }
/*  37: 81 */     FunctorUtils.validate(cmds);
/*  38: 82 */     return new ChainedClosure(cmds);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Closure getInstance(Closure closure1, Closure closure2)
/*  42:    */   {
/*  43: 94 */     if ((closure1 == null) || (closure2 == null)) {
/*  44: 95 */       throw new IllegalArgumentException("Closures must not be null");
/*  45:    */     }
/*  46: 97 */     Closure[] closures = { closure1, closure2 };
/*  47: 98 */     return new ChainedClosure(closures);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ChainedClosure(Closure[] closures)
/*  51:    */   {
/*  52:109 */     this.iClosures = closures;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void execute(Object input)
/*  56:    */   {
/*  57:118 */     for (int i = 0; i < this.iClosures.length; i++) {
/*  58:119 */       this.iClosures[i].execute(input);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Closure[] getClosures()
/*  63:    */   {
/*  64:129 */     return this.iClosures;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ChainedClosure
 * JD-Core Version:    0.7.0.1
 */