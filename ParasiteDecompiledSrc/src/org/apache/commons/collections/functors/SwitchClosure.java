/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.Closure;
/*   9:    */ import org.apache.commons.collections.Predicate;
/*  10:    */ 
/*  11:    */ public class SwitchClosure
/*  12:    */   implements Closure, Serializable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 3518477308466486130L;
/*  15:    */   private final Predicate[] iPredicates;
/*  16:    */   private final Closure[] iClosures;
/*  17:    */   private final Closure iDefault;
/*  18:    */   
/*  19:    */   public static Closure getInstance(Predicate[] predicates, Closure[] closures, Closure defaultClosure)
/*  20:    */   {
/*  21: 58 */     FunctorUtils.validate(predicates);
/*  22: 59 */     FunctorUtils.validate(closures);
/*  23: 60 */     if (predicates.length != closures.length) {
/*  24: 61 */       throw new IllegalArgumentException("The predicate and closure arrays must be the same size");
/*  25:    */     }
/*  26: 63 */     if (predicates.length == 0) {
/*  27: 64 */       return defaultClosure == null ? NOPClosure.INSTANCE : defaultClosure;
/*  28:    */     }
/*  29: 66 */     predicates = FunctorUtils.copy(predicates);
/*  30: 67 */     closures = FunctorUtils.copy(closures);
/*  31: 68 */     return new SwitchClosure(predicates, closures, defaultClosure);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Closure getInstance(Map predicatesAndClosures)
/*  35:    */   {
/*  36: 89 */     Closure[] closures = null;
/*  37: 90 */     Predicate[] preds = null;
/*  38: 91 */     if (predicatesAndClosures == null) {
/*  39: 92 */       throw new IllegalArgumentException("The predicate and closure map must not be null");
/*  40:    */     }
/*  41: 94 */     if (predicatesAndClosures.size() == 0) {
/*  42: 95 */       return NOPClosure.INSTANCE;
/*  43:    */     }
/*  44: 98 */     Closure defaultClosure = (Closure)predicatesAndClosures.remove(null);
/*  45: 99 */     int size = predicatesAndClosures.size();
/*  46:100 */     if (size == 0) {
/*  47:101 */       return defaultClosure == null ? NOPClosure.INSTANCE : defaultClosure;
/*  48:    */     }
/*  49:103 */     closures = new Closure[size];
/*  50:104 */     preds = new Predicate[size];
/*  51:105 */     int i = 0;
/*  52:106 */     for (Iterator it = predicatesAndClosures.entrySet().iterator(); it.hasNext();)
/*  53:    */     {
/*  54:107 */       Map.Entry entry = (Map.Entry)it.next();
/*  55:108 */       preds[i] = ((Predicate)entry.getKey());
/*  56:109 */       closures[i] = ((Closure)entry.getValue());
/*  57:110 */       i++;
/*  58:    */     }
/*  59:112 */     return new SwitchClosure(preds, closures, defaultClosure);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public SwitchClosure(Predicate[] predicates, Closure[] closures, Closure defaultClosure)
/*  63:    */   {
/*  64:125 */     this.iPredicates = predicates;
/*  65:126 */     this.iClosures = closures;
/*  66:127 */     this.iDefault = (defaultClosure == null ? NOPClosure.INSTANCE : defaultClosure);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void execute(Object input)
/*  70:    */   {
/*  71:136 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  72:137 */       if (this.iPredicates[i].evaluate(input) == true)
/*  73:    */       {
/*  74:138 */         this.iClosures[i].execute(input);
/*  75:139 */         return;
/*  76:    */       }
/*  77:    */     }
/*  78:142 */     this.iDefault.execute(input);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Predicate[] getPredicates()
/*  82:    */   {
/*  83:152 */     return this.iPredicates;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Closure[] getClosures()
/*  87:    */   {
/*  88:162 */     return this.iClosures;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Closure getDefaultClosure()
/*  92:    */   {
/*  93:172 */     return this.iDefault;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.SwitchClosure
 * JD-Core Version:    0.7.0.1
 */