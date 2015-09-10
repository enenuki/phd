/*   1:    */ package org.hibernate.transform;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   7:    */ import org.hibernate.type.Type;
/*   8:    */ 
/*   9:    */ public class CacheableResultTransformer
/*  10:    */   implements ResultTransformer
/*  11:    */ {
/*  12: 45 */   private static final PassThroughResultTransformer ACTUAL_TRANSFORMER = PassThroughResultTransformer.INSTANCE;
/*  13:    */   private final int tupleLength;
/*  14:    */   private final int tupleSubsetLength;
/*  15:    */   private final boolean[] includeInTuple;
/*  16:    */   private final int[] includeInTransformIndex;
/*  17:    */   
/*  18:    */   public static CacheableResultTransformer create(ResultTransformer transformer, String[] aliases, boolean[] includeInTuple)
/*  19:    */   {
/*  20: 83 */     return (transformer instanceof TupleSubsetResultTransformer) ? create((TupleSubsetResultTransformer)transformer, aliases, includeInTuple) : create(includeInTuple);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private static CacheableResultTransformer create(TupleSubsetResultTransformer transformer, String[] aliases, boolean[] includeInTuple)
/*  24:    */   {
/*  25:109 */     if (transformer == null) {
/*  26:110 */       throw new IllegalArgumentException("transformer cannot be null");
/*  27:    */     }
/*  28:112 */     int tupleLength = ArrayHelper.countTrue(includeInTuple);
/*  29:113 */     if ((aliases != null) && (aliases.length != tupleLength)) {
/*  30:114 */       throw new IllegalArgumentException("if aliases is not null, then the length of aliases[] must equal the number of true elements in includeInTuple; aliases.length=" + aliases.length + "tupleLength=" + tupleLength);
/*  31:    */     }
/*  32:119 */     return new CacheableResultTransformer(includeInTuple, transformer.includeInTransform(aliases, tupleLength));
/*  33:    */   }
/*  34:    */   
/*  35:    */   private static CacheableResultTransformer create(boolean[] includeInTuple)
/*  36:    */   {
/*  37:138 */     return new CacheableResultTransformer(includeInTuple, null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private CacheableResultTransformer(boolean[] includeInTuple, boolean[] includeInTransform)
/*  41:    */   {
/*  42:142 */     if (includeInTuple == null) {
/*  43:143 */       throw new IllegalArgumentException("includeInTuple cannot be null");
/*  44:    */     }
/*  45:145 */     this.includeInTuple = includeInTuple;
/*  46:146 */     this.tupleLength = ArrayHelper.countTrue(includeInTuple);
/*  47:147 */     this.tupleSubsetLength = (includeInTransform == null ? this.tupleLength : ArrayHelper.countTrue(includeInTransform));
/*  48:152 */     if (this.tupleSubsetLength == this.tupleLength)
/*  49:    */     {
/*  50:153 */       this.includeInTransformIndex = null;
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54:156 */       this.includeInTransformIndex = new int[this.tupleSubsetLength];
/*  55:157 */       int i = 0;
/*  56:157 */       for (int j = 0; i < includeInTransform.length; i++) {
/*  57:158 */         if (includeInTransform[i] != 0)
/*  58:    */         {
/*  59:159 */           this.includeInTransformIndex[j] = i;
/*  60:160 */           j++;
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object transformTuple(Object[] tuple, String[] aliases)
/*  67:    */   {
/*  68:170 */     if ((aliases != null) && (aliases.length != this.tupleLength)) {
/*  69:171 */       throw new IllegalStateException("aliases expected length is " + this.tupleLength + "; actual length is " + aliases.length);
/*  70:    */     }
/*  71:178 */     return ACTUAL_TRANSFORMER.transformTuple(index(tuple.getClass(), tuple), null);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List retransformResults(List transformedResults, String[] aliases, ResultTransformer transformer, boolean[] includeInTuple)
/*  75:    */   {
/*  76:200 */     if (transformer == null) {
/*  77:201 */       throw new IllegalArgumentException("transformer cannot be null");
/*  78:    */     }
/*  79:203 */     if (!equals(create(transformer, aliases, includeInTuple))) {
/*  80:204 */       throw new IllegalStateException("this CacheableResultTransformer is inconsistent with specified arguments; cannot re-transform");
/*  81:    */     }
/*  82:208 */     boolean requiresRetransform = true;
/*  83:209 */     String[] aliasesToUse = aliases == null ? null : (String[])index(aliases.getClass(), aliases);
/*  84:210 */     if (transformer == ACTUAL_TRANSFORMER) {
/*  85:211 */       requiresRetransform = false;
/*  86:213 */     } else if ((transformer instanceof TupleSubsetResultTransformer)) {
/*  87:214 */       requiresRetransform = !((TupleSubsetResultTransformer)transformer).isTransformedValueATupleElement(aliasesToUse, this.tupleLength);
/*  88:    */     }
/*  89:219 */     if (requiresRetransform) {
/*  90:220 */       for (int i = 0; i < transformedResults.size(); i++)
/*  91:    */       {
/*  92:221 */         Object[] tuple = ACTUAL_TRANSFORMER.untransformToTuple(transformedResults.get(i), this.tupleSubsetLength == 1);
/*  93:    */         
/*  94:    */ 
/*  95:    */ 
/*  96:225 */         transformedResults.set(i, transformer.transformTuple(tuple, aliasesToUse));
/*  97:    */       }
/*  98:    */     }
/*  99:228 */     return transformedResults;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List untransformToTuples(List results)
/* 103:    */   {
/* 104:248 */     if (this.includeInTransformIndex == null) {
/* 105:249 */       results = ACTUAL_TRANSFORMER.untransformToTuples(results, this.tupleSubsetLength == 1);
/* 106:    */     } else {
/* 107:255 */       for (int i = 0; i < results.size(); i++)
/* 108:    */       {
/* 109:256 */         Object[] tuple = ACTUAL_TRANSFORMER.untransformToTuple(results.get(i), this.tupleSubsetLength == 1);
/* 110:    */         
/* 111:    */ 
/* 112:    */ 
/* 113:260 */         results.set(i, unindex(tuple.getClass(), tuple));
/* 114:    */       }
/* 115:    */     }
/* 116:264 */     return results;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Type[] getCachedResultTypes(Type[] tupleResultTypes)
/* 120:    */   {
/* 121:273 */     return this.tupleLength != this.tupleSubsetLength ? (Type[])index(tupleResultTypes.getClass(), tupleResultTypes) : tupleResultTypes;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List transformList(List list)
/* 125:    */   {
/* 126:283 */     return list;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private <T> T[] index(Class<? extends T[]> clazz, T[] objects)
/* 130:    */   {
/* 131:287 */     T[] objectsIndexed = objects;
/* 132:288 */     if ((objects != null) && (this.includeInTransformIndex != null) && (objects.length != this.tupleSubsetLength))
/* 133:    */     {
/* 134:291 */       objectsIndexed = (Object[])clazz.cast(Array.newInstance(clazz.getComponentType(), this.tupleSubsetLength));
/* 135:292 */       for (int i = 0; i < this.tupleSubsetLength; i++) {
/* 136:293 */         objectsIndexed[i] = objects[this.includeInTransformIndex[i]];
/* 137:    */       }
/* 138:    */     }
/* 139:296 */     return objectsIndexed;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private <T> T[] unindex(Class<? extends T[]> clazz, T[] objects)
/* 143:    */   {
/* 144:300 */     T[] objectsUnindexed = objects;
/* 145:301 */     if ((objects != null) && (this.includeInTransformIndex != null) && (objects.length != this.tupleLength))
/* 146:    */     {
/* 147:304 */       objectsUnindexed = (Object[])clazz.cast(Array.newInstance(clazz.getComponentType(), this.tupleLength));
/* 148:305 */       for (int i = 0; i < this.tupleSubsetLength; i++) {
/* 149:306 */         objectsUnindexed[this.includeInTransformIndex[i]] = objects[i];
/* 150:    */       }
/* 151:    */     }
/* 152:309 */     return objectsUnindexed;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean equals(Object o)
/* 156:    */   {
/* 157:314 */     if (this == o) {
/* 158:315 */       return true;
/* 159:    */     }
/* 160:317 */     if ((o == null) || (getClass() != o.getClass())) {
/* 161:318 */       return false;
/* 162:    */     }
/* 163:321 */     CacheableResultTransformer that = (CacheableResultTransformer)o;
/* 164:323 */     if (this.tupleLength != that.tupleLength) {
/* 165:324 */       return false;
/* 166:    */     }
/* 167:326 */     if (this.tupleSubsetLength != that.tupleSubsetLength) {
/* 168:327 */       return false;
/* 169:    */     }
/* 170:329 */     if (!Arrays.equals(this.includeInTuple, that.includeInTuple)) {
/* 171:330 */       return false;
/* 172:    */     }
/* 173:332 */     if (!Arrays.equals(this.includeInTransformIndex, that.includeInTransformIndex)) {
/* 174:333 */       return false;
/* 175:    */     }
/* 176:336 */     return true;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int hashCode()
/* 180:    */   {
/* 181:341 */     int result = this.tupleLength;
/* 182:342 */     result = 31 * result + this.tupleSubsetLength;
/* 183:343 */     result = 31 * result + (this.includeInTuple != null ? Arrays.hashCode(this.includeInTuple) : 0);
/* 184:344 */     result = 31 * result + (this.includeInTransformIndex != null ? Arrays.hashCode(this.includeInTransformIndex) : 0);
/* 185:345 */     return result;
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.CacheableResultTransformer
 * JD-Core Version:    0.7.0.1
 */