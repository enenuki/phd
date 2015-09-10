/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import org.apache.commons.collections.Transformer;
/*   7:    */ 
/*   8:    */ public class ChainedTransformer
/*   9:    */   implements Transformer, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 3514945074733160196L;
/*  12:    */   private final Transformer[] iTransformers;
/*  13:    */   
/*  14:    */   public static Transformer getInstance(Transformer[] transformers)
/*  15:    */   {
/*  16: 53 */     FunctorUtils.validate(transformers);
/*  17: 54 */     if (transformers.length == 0) {
/*  18: 55 */       return NOPTransformer.INSTANCE;
/*  19:    */     }
/*  20: 57 */     transformers = FunctorUtils.copy(transformers);
/*  21: 58 */     return new ChainedTransformer(transformers);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static Transformer getInstance(Collection transformers)
/*  25:    */   {
/*  26: 72 */     if (transformers == null) {
/*  27: 73 */       throw new IllegalArgumentException("Transformer collection must not be null");
/*  28:    */     }
/*  29: 75 */     if (transformers.size() == 0) {
/*  30: 76 */       return NOPTransformer.INSTANCE;
/*  31:    */     }
/*  32: 79 */     Transformer[] cmds = new Transformer[transformers.size()];
/*  33: 80 */     int i = 0;
/*  34: 81 */     for (Iterator it = transformers.iterator(); it.hasNext();) {
/*  35: 82 */       cmds[(i++)] = ((Transformer)it.next());
/*  36:    */     }
/*  37: 84 */     FunctorUtils.validate(cmds);
/*  38: 85 */     return new ChainedTransformer(cmds);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Transformer getInstance(Transformer transformer1, Transformer transformer2)
/*  42:    */   {
/*  43: 97 */     if ((transformer1 == null) || (transformer2 == null)) {
/*  44: 98 */       throw new IllegalArgumentException("Transformers must not be null");
/*  45:    */     }
/*  46:100 */     Transformer[] transformers = { transformer1, transformer2 };
/*  47:101 */     return new ChainedTransformer(transformers);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ChainedTransformer(Transformer[] transformers)
/*  51:    */   {
/*  52:112 */     this.iTransformers = transformers;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object transform(Object object)
/*  56:    */   {
/*  57:122 */     for (int i = 0; i < this.iTransformers.length; i++) {
/*  58:123 */       object = this.iTransformers[i].transform(object);
/*  59:    */     }
/*  60:125 */     return object;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Transformer[] getTransformers()
/*  64:    */   {
/*  65:134 */     return this.iTransformers;
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ChainedTransformer
 * JD-Core Version:    0.7.0.1
 */