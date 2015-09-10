/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.commons.collections.Closure;
/*   6:    */ import org.apache.commons.collections.Predicate;
/*   7:    */ import org.apache.commons.collections.Transformer;
/*   8:    */ 
/*   9:    */ class FunctorUtils
/*  10:    */ {
/*  11:    */   static Predicate[] copy(Predicate[] predicates)
/*  12:    */   {
/*  13: 51 */     if (predicates == null) {
/*  14: 52 */       return null;
/*  15:    */     }
/*  16: 54 */     return (Predicate[])predicates.clone();
/*  17:    */   }
/*  18:    */   
/*  19:    */   static void validate(Predicate[] predicates)
/*  20:    */   {
/*  21: 63 */     if (predicates == null) {
/*  22: 64 */       throw new IllegalArgumentException("The predicate array must not be null");
/*  23:    */     }
/*  24: 66 */     for (int i = 0; i < predicates.length; i++) {
/*  25: 67 */       if (predicates[i] == null) {
/*  26: 68 */         throw new IllegalArgumentException("The predicate array must not contain a null predicate, index " + i + " was null");
/*  27:    */       }
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   static Predicate[] validate(Collection predicates)
/*  32:    */   {
/*  33: 80 */     if (predicates == null) {
/*  34: 81 */       throw new IllegalArgumentException("The predicate collection must not be null");
/*  35:    */     }
/*  36: 84 */     Predicate[] preds = new Predicate[predicates.size()];
/*  37: 85 */     int i = 0;
/*  38: 86 */     for (Iterator it = predicates.iterator(); it.hasNext();)
/*  39:    */     {
/*  40: 87 */       preds[i] = ((Predicate)it.next());
/*  41: 88 */       if (preds[i] == null) {
/*  42: 89 */         throw new IllegalArgumentException("The predicate collection must not contain a null predicate, index " + i + " was null");
/*  43:    */       }
/*  44: 91 */       i++;
/*  45:    */     }
/*  46: 93 */     return preds;
/*  47:    */   }
/*  48:    */   
/*  49:    */   static Closure[] copy(Closure[] closures)
/*  50:    */   {
/*  51:103 */     if (closures == null) {
/*  52:104 */       return null;
/*  53:    */     }
/*  54:106 */     return (Closure[])closures.clone();
/*  55:    */   }
/*  56:    */   
/*  57:    */   static void validate(Closure[] closures)
/*  58:    */   {
/*  59:115 */     if (closures == null) {
/*  60:116 */       throw new IllegalArgumentException("The closure array must not be null");
/*  61:    */     }
/*  62:118 */     for (int i = 0; i < closures.length; i++) {
/*  63:119 */       if (closures[i] == null) {
/*  64:120 */         throw new IllegalArgumentException("The closure array must not contain a null closure, index " + i + " was null");
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   static Transformer[] copy(Transformer[] transformers)
/*  70:    */   {
/*  71:132 */     if (transformers == null) {
/*  72:133 */       return null;
/*  73:    */     }
/*  74:135 */     return (Transformer[])transformers.clone();
/*  75:    */   }
/*  76:    */   
/*  77:    */   static void validate(Transformer[] transformers)
/*  78:    */   {
/*  79:144 */     if (transformers == null) {
/*  80:145 */       throw new IllegalArgumentException("The transformer array must not be null");
/*  81:    */     }
/*  82:147 */     for (int i = 0; i < transformers.length; i++) {
/*  83:148 */       if (transformers[i] == null) {
/*  84:149 */         throw new IllegalArgumentException("The transformer array must not contain a null transformer, index " + i + " was null");
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.FunctorUtils
 * JD-Core Version:    0.7.0.1
 */