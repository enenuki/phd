/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.Predicate;
/*   9:    */ import org.apache.commons.collections.Transformer;
/*  10:    */ 
/*  11:    */ public class SwitchTransformer
/*  12:    */   implements Transformer, Serializable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = -6404460890903469332L;
/*  15:    */   private final Predicate[] iPredicates;
/*  16:    */   private final Transformer[] iTransformers;
/*  17:    */   private final Transformer iDefault;
/*  18:    */   
/*  19:    */   public static Transformer getInstance(Predicate[] predicates, Transformer[] transformers, Transformer defaultTransformer)
/*  20:    */   {
/*  21: 58 */     FunctorUtils.validate(predicates);
/*  22: 59 */     FunctorUtils.validate(transformers);
/*  23: 60 */     if (predicates.length != transformers.length) {
/*  24: 61 */       throw new IllegalArgumentException("The predicate and transformer arrays must be the same size");
/*  25:    */     }
/*  26: 63 */     if (predicates.length == 0) {
/*  27: 64 */       return defaultTransformer == null ? ConstantTransformer.NULL_INSTANCE : defaultTransformer;
/*  28:    */     }
/*  29: 66 */     predicates = FunctorUtils.copy(predicates);
/*  30: 67 */     transformers = FunctorUtils.copy(transformers);
/*  31: 68 */     return new SwitchTransformer(predicates, transformers, defaultTransformer);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Transformer getInstance(Map predicatesAndTransformers)
/*  35:    */   {
/*  36: 89 */     Transformer[] transformers = null;
/*  37: 90 */     Predicate[] preds = null;
/*  38: 91 */     if (predicatesAndTransformers == null) {
/*  39: 92 */       throw new IllegalArgumentException("The predicate and transformer map must not be null");
/*  40:    */     }
/*  41: 94 */     if (predicatesAndTransformers.size() == 0) {
/*  42: 95 */       return ConstantTransformer.NULL_INSTANCE;
/*  43:    */     }
/*  44: 98 */     Transformer defaultTransformer = (Transformer)predicatesAndTransformers.remove(null);
/*  45: 99 */     int size = predicatesAndTransformers.size();
/*  46:100 */     if (size == 0) {
/*  47:101 */       return defaultTransformer == null ? ConstantTransformer.NULL_INSTANCE : defaultTransformer;
/*  48:    */     }
/*  49:103 */     transformers = new Transformer[size];
/*  50:104 */     preds = new Predicate[size];
/*  51:105 */     int i = 0;
/*  52:106 */     for (Iterator it = predicatesAndTransformers.entrySet().iterator(); it.hasNext();)
/*  53:    */     {
/*  54:107 */       Map.Entry entry = (Map.Entry)it.next();
/*  55:108 */       preds[i] = ((Predicate)entry.getKey());
/*  56:109 */       transformers[i] = ((Transformer)entry.getValue());
/*  57:110 */       i++;
/*  58:    */     }
/*  59:112 */     return new SwitchTransformer(preds, transformers, defaultTransformer);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public SwitchTransformer(Predicate[] predicates, Transformer[] transformers, Transformer defaultTransformer)
/*  63:    */   {
/*  64:125 */     this.iPredicates = predicates;
/*  65:126 */     this.iTransformers = transformers;
/*  66:127 */     this.iDefault = (defaultTransformer == null ? ConstantTransformer.NULL_INSTANCE : defaultTransformer);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object transform(Object input)
/*  70:    */   {
/*  71:138 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  72:139 */       if (this.iPredicates[i].evaluate(input) == true) {
/*  73:140 */         return this.iTransformers[i].transform(input);
/*  74:    */       }
/*  75:    */     }
/*  76:143 */     return this.iDefault.transform(input);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Predicate[] getPredicates()
/*  80:    */   {
/*  81:153 */     return this.iPredicates;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Transformer[] getTransformers()
/*  85:    */   {
/*  86:163 */     return this.iTransformers;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Transformer getDefaultTransformer()
/*  90:    */   {
/*  91:173 */     return this.iDefault;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.SwitchTransformer
 * JD-Core Version:    0.7.0.1
 */