/*   1:    */ package org.hibernate.hql.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
/*   5:    */ import org.hibernate.transform.ResultTransformer;
/*   6:    */ import org.hibernate.transform.Transformers;
/*   7:    */ 
/*   8:    */ public final class HolderInstantiator
/*   9:    */ {
/*  10: 37 */   public static final HolderInstantiator NOOP_INSTANTIATOR = new HolderInstantiator(null, null);
/*  11:    */   private final ResultTransformer transformer;
/*  12:    */   private final String[] queryReturnAliases;
/*  13:    */   
/*  14:    */   public static HolderInstantiator getHolderInstantiator(ResultTransformer selectNewTransformer, ResultTransformer customTransformer, String[] queryReturnAliases)
/*  15:    */   {
/*  16: 43 */     return new HolderInstantiator(resolveResultTransformer(selectNewTransformer, customTransformer), queryReturnAliases);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static ResultTransformer resolveResultTransformer(ResultTransformer selectNewTransformer, ResultTransformer customTransformer)
/*  20:    */   {
/*  21: 50 */     return selectNewTransformer != null ? selectNewTransformer : customTransformer;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static ResultTransformer createSelectNewTransformer(Constructor constructor, boolean returnMaps, boolean returnLists)
/*  25:    */   {
/*  26: 54 */     if (constructor != null) {
/*  27: 55 */       return new AliasToBeanConstructorResultTransformer(constructor);
/*  28:    */     }
/*  29: 57 */     if (returnMaps) {
/*  30: 58 */       return Transformers.ALIAS_TO_ENTITY_MAP;
/*  31:    */     }
/*  32: 60 */     if (returnLists) {
/*  33: 61 */       return Transformers.TO_LIST;
/*  34:    */     }
/*  35: 64 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static HolderInstantiator createClassicHolderInstantiator(Constructor constructor, ResultTransformer transformer)
/*  39:    */   {
/*  40: 70 */     return new HolderInstantiator(resolveClassicResultTransformer(constructor, transformer), null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static ResultTransformer resolveClassicResultTransformer(Constructor constructor, ResultTransformer transformer)
/*  44:    */   {
/*  45: 76 */     return constructor != null ? new AliasToBeanConstructorResultTransformer(constructor) : transformer;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public HolderInstantiator(ResultTransformer transformer, String[] queryReturnAliases)
/*  49:    */   {
/*  50: 83 */     this.transformer = transformer;
/*  51: 84 */     this.queryReturnAliases = queryReturnAliases;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isRequired()
/*  55:    */   {
/*  56: 88 */     return this.transformer != null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Object instantiate(Object[] row)
/*  60:    */   {
/*  61: 92 */     if (this.transformer == null) {
/*  62: 93 */       return row;
/*  63:    */     }
/*  64: 95 */     return this.transformer.transformTuple(row, this.queryReturnAliases);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String[] getQueryReturnAliases()
/*  68:    */   {
/*  69:100 */     return this.queryReturnAliases;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ResultTransformer getResultTransformer()
/*  73:    */   {
/*  74:104 */     return this.transformer;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.HolderInstantiator
 * JD-Core Version:    0.7.0.1
 */