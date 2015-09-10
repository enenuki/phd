/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.functors.ChainedClosure;
/*   9:    */ import org.apache.commons.collections.functors.EqualPredicate;
/*  10:    */ import org.apache.commons.collections.functors.ExceptionClosure;
/*  11:    */ import org.apache.commons.collections.functors.ForClosure;
/*  12:    */ import org.apache.commons.collections.functors.IfClosure;
/*  13:    */ import org.apache.commons.collections.functors.InvokerTransformer;
/*  14:    */ import org.apache.commons.collections.functors.NOPClosure;
/*  15:    */ import org.apache.commons.collections.functors.SwitchClosure;
/*  16:    */ import org.apache.commons.collections.functors.TransformerClosure;
/*  17:    */ import org.apache.commons.collections.functors.WhileClosure;
/*  18:    */ 
/*  19:    */ public class ClosureUtils
/*  20:    */ {
/*  21:    */   public static Closure exceptionClosure()
/*  22:    */   {
/*  23: 75 */     return ExceptionClosure.INSTANCE;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Closure nopClosure()
/*  27:    */   {
/*  28: 87 */     return NOPClosure.INSTANCE;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Closure asClosure(Transformer transformer)
/*  32:    */   {
/*  33:101 */     return TransformerClosure.getInstance(transformer);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Closure forClosure(int count, Closure closure)
/*  37:    */   {
/*  38:116 */     return ForClosure.getInstance(count, closure);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Closure whileClosure(Predicate predicate, Closure closure)
/*  42:    */   {
/*  43:131 */     return WhileClosure.getInstance(predicate, closure, false);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Closure doWhileClosure(Closure closure, Predicate predicate)
/*  47:    */   {
/*  48:146 */     return WhileClosure.getInstance(predicate, closure, true);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static Closure invokerClosure(String methodName)
/*  52:    */   {
/*  53:162 */     return asClosure(InvokerTransformer.getInstance(methodName));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static Closure invokerClosure(String methodName, Class[] paramTypes, Object[] args)
/*  57:    */   {
/*  58:181 */     return asClosure(InvokerTransformer.getInstance(methodName, paramTypes, args));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static Closure chainedClosure(Closure closure1, Closure closure2)
/*  62:    */   {
/*  63:196 */     return ChainedClosure.getInstance(closure1, closure2);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Closure chainedClosure(Closure[] closures)
/*  67:    */   {
/*  68:211 */     return ChainedClosure.getInstance(closures);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static Closure chainedClosure(Collection closures)
/*  72:    */   {
/*  73:228 */     return ChainedClosure.getInstance(closures);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static Closure ifClosure(Predicate predicate, Closure trueClosure)
/*  77:    */   {
/*  78:245 */     return IfClosure.getInstance(predicate, trueClosure);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static Closure ifClosure(Predicate predicate, Closure trueClosure, Closure falseClosure)
/*  82:    */   {
/*  83:262 */     return IfClosure.getInstance(predicate, trueClosure, falseClosure);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static Closure switchClosure(Predicate[] predicates, Closure[] closures)
/*  87:    */   {
/*  88:283 */     return SwitchClosure.getInstance(predicates, closures, null);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Closure switchClosure(Predicate[] predicates, Closure[] closures, Closure defaultClosure)
/*  92:    */   {
/*  93:306 */     return SwitchClosure.getInstance(predicates, closures, defaultClosure);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static Closure switchClosure(Map predicatesAndClosures)
/*  97:    */   {
/*  98:330 */     return SwitchClosure.getInstance(predicatesAndClosures);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static Closure switchMapClosure(Map objectsAndClosures)
/* 102:    */   {
/* 103:351 */     Closure[] trs = null;
/* 104:352 */     Predicate[] preds = null;
/* 105:353 */     if (objectsAndClosures == null) {
/* 106:354 */       throw new IllegalArgumentException("The object and closure map must not be null");
/* 107:    */     }
/* 108:356 */     Closure def = (Closure)objectsAndClosures.remove(null);
/* 109:357 */     int size = objectsAndClosures.size();
/* 110:358 */     trs = new Closure[size];
/* 111:359 */     preds = new Predicate[size];
/* 112:360 */     int i = 0;
/* 113:361 */     for (Iterator it = objectsAndClosures.entrySet().iterator(); it.hasNext();)
/* 114:    */     {
/* 115:362 */       Map.Entry entry = (Map.Entry)it.next();
/* 116:363 */       preds[i] = EqualPredicate.getInstance(entry.getKey());
/* 117:364 */       trs[i] = ((Closure)entry.getValue());
/* 118:365 */       i++;
/* 119:    */     }
/* 120:367 */     return switchClosure(preds, trs, def);
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ClosureUtils
 * JD-Core Version:    0.7.0.1
 */