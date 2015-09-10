/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.functors.ChainedTransformer;
/*   9:    */ import org.apache.commons.collections.functors.CloneTransformer;
/*  10:    */ import org.apache.commons.collections.functors.ClosureTransformer;
/*  11:    */ import org.apache.commons.collections.functors.ConstantTransformer;
/*  12:    */ import org.apache.commons.collections.functors.EqualPredicate;
/*  13:    */ import org.apache.commons.collections.functors.ExceptionTransformer;
/*  14:    */ import org.apache.commons.collections.functors.FactoryTransformer;
/*  15:    */ import org.apache.commons.collections.functors.InstantiateTransformer;
/*  16:    */ import org.apache.commons.collections.functors.InvokerTransformer;
/*  17:    */ import org.apache.commons.collections.functors.MapTransformer;
/*  18:    */ import org.apache.commons.collections.functors.NOPTransformer;
/*  19:    */ import org.apache.commons.collections.functors.PredicateTransformer;
/*  20:    */ import org.apache.commons.collections.functors.StringValueTransformer;
/*  21:    */ import org.apache.commons.collections.functors.SwitchTransformer;
/*  22:    */ 
/*  23:    */ public class TransformerUtils
/*  24:    */ {
/*  25:    */   public static Transformer exceptionTransformer()
/*  26:    */   {
/*  27: 84 */     return ExceptionTransformer.INSTANCE;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Transformer nullTransformer()
/*  31:    */   {
/*  32: 95 */     return ConstantTransformer.NULL_INSTANCE;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Transformer nopTransformer()
/*  36:    */   {
/*  37:108 */     return NOPTransformer.INSTANCE;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Transformer cloneTransformer()
/*  41:    */   {
/*  42:126 */     return CloneTransformer.INSTANCE;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Transformer constantTransformer(Object constantToReturn)
/*  46:    */   {
/*  47:139 */     return ConstantTransformer.getInstance(constantToReturn);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Transformer asTransformer(Closure closure)
/*  51:    */   {
/*  52:153 */     return ClosureTransformer.getInstance(closure);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Transformer asTransformer(Predicate predicate)
/*  56:    */   {
/*  57:167 */     return PredicateTransformer.getInstance(predicate);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Transformer asTransformer(Factory factory)
/*  61:    */   {
/*  62:181 */     return FactoryTransformer.getInstance(factory);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Transformer chainedTransformer(Transformer transformer1, Transformer transformer2)
/*  66:    */   {
/*  67:196 */     return ChainedTransformer.getInstance(transformer1, transformer2);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Transformer chainedTransformer(Transformer[] transformers)
/*  71:    */   {
/*  72:211 */     return ChainedTransformer.getInstance(transformers);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Transformer chainedTransformer(Collection transformers)
/*  76:    */   {
/*  77:227 */     return ChainedTransformer.getInstance(transformers);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static Transformer switchTransformer(Predicate predicate, Transformer trueTransformer, Transformer falseTransformer)
/*  81:    */   {
/*  82:244 */     return SwitchTransformer.getInstance(new Predicate[] { predicate }, new Transformer[] { trueTransformer }, falseTransformer);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static Transformer switchTransformer(Predicate[] predicates, Transformer[] transformers)
/*  86:    */   {
/*  87:264 */     return SwitchTransformer.getInstance(predicates, transformers, null);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Transformer switchTransformer(Predicate[] predicates, Transformer[] transformers, Transformer defaultTransformer)
/*  91:    */   {
/*  92:286 */     return SwitchTransformer.getInstance(predicates, transformers, defaultTransformer);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static Transformer switchTransformer(Map predicatesAndTransformers)
/*  96:    */   {
/*  97:311 */     return SwitchTransformer.getInstance(predicatesAndTransformers);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static Transformer switchMapTransformer(Map objectsAndTransformers)
/* 101:    */   {
/* 102:332 */     Transformer[] trs = null;
/* 103:333 */     Predicate[] preds = null;
/* 104:334 */     if (objectsAndTransformers == null) {
/* 105:335 */       throw new IllegalArgumentException("The object and transformer map must not be null");
/* 106:    */     }
/* 107:337 */     Transformer def = (Transformer)objectsAndTransformers.remove(null);
/* 108:338 */     int size = objectsAndTransformers.size();
/* 109:339 */     trs = new Transformer[size];
/* 110:340 */     preds = new Predicate[size];
/* 111:341 */     int i = 0;
/* 112:342 */     for (Iterator it = objectsAndTransformers.entrySet().iterator(); it.hasNext();)
/* 113:    */     {
/* 114:343 */       Map.Entry entry = (Map.Entry)it.next();
/* 115:344 */       preds[i] = EqualPredicate.getInstance(entry.getKey());
/* 116:345 */       trs[i] = ((Transformer)entry.getValue());
/* 117:346 */       i++;
/* 118:    */     }
/* 119:348 */     return switchTransformer(preds, trs, def);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static Transformer instantiateTransformer()
/* 123:    */   {
/* 124:359 */     return InstantiateTransformer.NO_ARG_INSTANCE;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Transformer instantiateTransformer(Class[] paramTypes, Object[] args)
/* 128:    */   {
/* 129:375 */     return InstantiateTransformer.getInstance(paramTypes, args);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static Transformer mapTransformer(Map map)
/* 133:    */   {
/* 134:389 */     return MapTransformer.getInstance(map);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static Transformer invokerTransformer(String methodName)
/* 138:    */   {
/* 139:408 */     return InvokerTransformer.getInstance(methodName, null, null);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Transformer invokerTransformer(String methodName, Class[] paramTypes, Object[] args)
/* 143:    */   {
/* 144:426 */     return InvokerTransformer.getInstance(methodName, paramTypes, args);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static Transformer stringValueTransformer()
/* 148:    */   {
/* 149:439 */     return StringValueTransformer.INSTANCE;
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.TransformerUtils
 * JD-Core Version:    0.7.0.1
 */