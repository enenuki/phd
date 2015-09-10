/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import org.apache.commons.collections.functors.AllPredicate;
/*   5:    */ import org.apache.commons.collections.functors.AndPredicate;
/*   6:    */ import org.apache.commons.collections.functors.AnyPredicate;
/*   7:    */ import org.apache.commons.collections.functors.EqualPredicate;
/*   8:    */ import org.apache.commons.collections.functors.ExceptionPredicate;
/*   9:    */ import org.apache.commons.collections.functors.FalsePredicate;
/*  10:    */ import org.apache.commons.collections.functors.IdentityPredicate;
/*  11:    */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  12:    */ import org.apache.commons.collections.functors.InvokerTransformer;
/*  13:    */ import org.apache.commons.collections.functors.NonePredicate;
/*  14:    */ import org.apache.commons.collections.functors.NotNullPredicate;
/*  15:    */ import org.apache.commons.collections.functors.NotPredicate;
/*  16:    */ import org.apache.commons.collections.functors.NullIsExceptionPredicate;
/*  17:    */ import org.apache.commons.collections.functors.NullIsFalsePredicate;
/*  18:    */ import org.apache.commons.collections.functors.NullIsTruePredicate;
/*  19:    */ import org.apache.commons.collections.functors.NullPredicate;
/*  20:    */ import org.apache.commons.collections.functors.OnePredicate;
/*  21:    */ import org.apache.commons.collections.functors.OrPredicate;
/*  22:    */ import org.apache.commons.collections.functors.TransformedPredicate;
/*  23:    */ import org.apache.commons.collections.functors.TransformerPredicate;
/*  24:    */ import org.apache.commons.collections.functors.TruePredicate;
/*  25:    */ import org.apache.commons.collections.functors.UniquePredicate;
/*  26:    */ 
/*  27:    */ public class PredicateUtils
/*  28:    */ {
/*  29:    */   public static Predicate exceptionPredicate()
/*  30:    */   {
/*  31: 96 */     return ExceptionPredicate.INSTANCE;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Predicate truePredicate()
/*  35:    */   {
/*  36:107 */     return TruePredicate.INSTANCE;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Predicate falsePredicate()
/*  40:    */   {
/*  41:118 */     return FalsePredicate.INSTANCE;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Predicate nullPredicate()
/*  45:    */   {
/*  46:129 */     return NullPredicate.INSTANCE;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Predicate notNullPredicate()
/*  50:    */   {
/*  51:140 */     return NotNullPredicate.INSTANCE;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static Predicate equalPredicate(Object value)
/*  55:    */   {
/*  56:153 */     return EqualPredicate.getInstance(value);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Predicate identityPredicate(Object value)
/*  60:    */   {
/*  61:166 */     return IdentityPredicate.getInstance(value);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Predicate instanceofPredicate(Class type)
/*  65:    */   {
/*  66:181 */     return InstanceofPredicate.getInstance(type);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Predicate uniquePredicate()
/*  70:    */   {
/*  71:197 */     return UniquePredicate.getInstance();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static Predicate invokerPredicate(String methodName)
/*  75:    */   {
/*  76:219 */     return asPredicate(InvokerTransformer.getInstance(methodName));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static Predicate invokerPredicate(String methodName, Class[] paramTypes, Object[] args)
/*  80:    */   {
/*  81:244 */     return asPredicate(InvokerTransformer.getInstance(methodName, paramTypes, args));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static Predicate andPredicate(Predicate predicate1, Predicate predicate2)
/*  85:    */   {
/*  86:262 */     return AndPredicate.getInstance(predicate1, predicate2);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static Predicate allPredicate(Predicate[] predicates)
/*  90:    */   {
/*  91:278 */     return AllPredicate.getInstance(predicates);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static Predicate allPredicate(Collection predicates)
/*  95:    */   {
/*  96:294 */     return AllPredicate.getInstance(predicates);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Predicate orPredicate(Predicate predicate1, Predicate predicate2)
/* 100:    */   {
/* 101:309 */     return OrPredicate.getInstance(predicate1, predicate2);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static Predicate anyPredicate(Predicate[] predicates)
/* 105:    */   {
/* 106:325 */     return AnyPredicate.getInstance(predicates);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static Predicate anyPredicate(Collection predicates)
/* 110:    */   {
/* 111:341 */     return AnyPredicate.getInstance(predicates);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static Predicate eitherPredicate(Predicate predicate1, Predicate predicate2)
/* 115:    */   {
/* 116:356 */     return onePredicate(new Predicate[] { predicate1, predicate2 });
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static Predicate onePredicate(Predicate[] predicates)
/* 120:    */   {
/* 121:372 */     return OnePredicate.getInstance(predicates);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static Predicate onePredicate(Collection predicates)
/* 125:    */   {
/* 126:388 */     return OnePredicate.getInstance(predicates);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static Predicate neitherPredicate(Predicate predicate1, Predicate predicate2)
/* 130:    */   {
/* 131:403 */     return nonePredicate(new Predicate[] { predicate1, predicate2 });
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static Predicate nonePredicate(Predicate[] predicates)
/* 135:    */   {
/* 136:419 */     return NonePredicate.getInstance(predicates);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static Predicate nonePredicate(Collection predicates)
/* 140:    */   {
/* 141:435 */     return NonePredicate.getInstance(predicates);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static Predicate notPredicate(Predicate predicate)
/* 145:    */   {
/* 146:449 */     return NotPredicate.getInstance(predicate);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static Predicate asPredicate(Transformer transformer)
/* 150:    */   {
/* 151:467 */     return TransformerPredicate.getInstance(transformer);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static Predicate nullIsExceptionPredicate(Predicate predicate)
/* 155:    */   {
/* 156:485 */     return NullIsExceptionPredicate.getInstance(predicate);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static Predicate nullIsFalsePredicate(Predicate predicate)
/* 160:    */   {
/* 161:500 */     return NullIsFalsePredicate.getInstance(predicate);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static Predicate nullIsTruePredicate(Predicate predicate)
/* 165:    */   {
/* 166:515 */     return NullIsTruePredicate.getInstance(predicate);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static Predicate transformedPredicate(Transformer transformer, Predicate predicate)
/* 170:    */   {
/* 171:533 */     return TransformedPredicate.getInstance(transformer, predicate);
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.PredicateUtils
 * JD-Core Version:    0.7.0.1
 */