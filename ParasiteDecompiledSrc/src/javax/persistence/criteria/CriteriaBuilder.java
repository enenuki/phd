/*    1:     */ package javax.persistence.criteria;
/*    2:     */ 
/*    3:     */ import java.math.BigDecimal;
/*    4:     */ import java.math.BigInteger;
/*    5:     */ import java.sql.Date;
/*    6:     */ import java.sql.Time;
/*    7:     */ import java.sql.Timestamp;
/*    8:     */ import java.util.Collection;
/*    9:     */ import java.util.Map;
/*   10:     */ import java.util.Set;
/*   11:     */ import javax.persistence.Tuple;
/*   12:     */ 
/*   13:     */ public abstract interface CriteriaBuilder
/*   14:     */ {
/*   15:     */   public abstract CriteriaQuery<Object> createQuery();
/*   16:     */   
/*   17:     */   public abstract <T> CriteriaQuery<T> createQuery(Class<T> paramClass);
/*   18:     */   
/*   19:     */   public abstract CriteriaQuery<Tuple> createTupleQuery();
/*   20:     */   
/*   21:     */   public abstract <Y> CompoundSelection<Y> construct(Class<Y> paramClass, Selection<?>... paramVarArgs);
/*   22:     */   
/*   23:     */   public abstract CompoundSelection<Tuple> tuple(Selection<?>... paramVarArgs);
/*   24:     */   
/*   25:     */   public abstract CompoundSelection<Object[]> array(Selection<?>... paramVarArgs);
/*   26:     */   
/*   27:     */   public abstract Order asc(Expression<?> paramExpression);
/*   28:     */   
/*   29:     */   public abstract Order desc(Expression<?> paramExpression);
/*   30:     */   
/*   31:     */   public abstract <N extends Number> Expression<Double> avg(Expression<N> paramExpression);
/*   32:     */   
/*   33:     */   public abstract <N extends Number> Expression<N> sum(Expression<N> paramExpression);
/*   34:     */   
/*   35:     */   public abstract Expression<Long> sumAsLong(Expression<Integer> paramExpression);
/*   36:     */   
/*   37:     */   public abstract Expression<Double> sumAsDouble(Expression<Float> paramExpression);
/*   38:     */   
/*   39:     */   public abstract <N extends Number> Expression<N> max(Expression<N> paramExpression);
/*   40:     */   
/*   41:     */   public abstract <N extends Number> Expression<N> min(Expression<N> paramExpression);
/*   42:     */   
/*   43:     */   public abstract <X extends Comparable<? super X>> Expression<X> greatest(Expression<X> paramExpression);
/*   44:     */   
/*   45:     */   public abstract <X extends Comparable<? super X>> Expression<X> least(Expression<X> paramExpression);
/*   46:     */   
/*   47:     */   public abstract Expression<Long> count(Expression<?> paramExpression);
/*   48:     */   
/*   49:     */   public abstract Expression<Long> countDistinct(Expression<?> paramExpression);
/*   50:     */   
/*   51:     */   public abstract Predicate exists(Subquery<?> paramSubquery);
/*   52:     */   
/*   53:     */   public abstract <Y> Expression<Y> all(Subquery<Y> paramSubquery);
/*   54:     */   
/*   55:     */   public abstract <Y> Expression<Y> some(Subquery<Y> paramSubquery);
/*   56:     */   
/*   57:     */   public abstract <Y> Expression<Y> any(Subquery<Y> paramSubquery);
/*   58:     */   
/*   59:     */   public abstract Predicate and(Expression<Boolean> paramExpression1, Expression<Boolean> paramExpression2);
/*   60:     */   
/*   61:     */   public abstract Predicate and(Predicate... paramVarArgs);
/*   62:     */   
/*   63:     */   public abstract Predicate or(Expression<Boolean> paramExpression1, Expression<Boolean> paramExpression2);
/*   64:     */   
/*   65:     */   public abstract Predicate or(Predicate... paramVarArgs);
/*   66:     */   
/*   67:     */   public abstract Predicate not(Expression<Boolean> paramExpression);
/*   68:     */   
/*   69:     */   public abstract Predicate conjunction();
/*   70:     */   
/*   71:     */   public abstract Predicate disjunction();
/*   72:     */   
/*   73:     */   public abstract Predicate isTrue(Expression<Boolean> paramExpression);
/*   74:     */   
/*   75:     */   public abstract Predicate isFalse(Expression<Boolean> paramExpression);
/*   76:     */   
/*   77:     */   public abstract Predicate isNull(Expression<?> paramExpression);
/*   78:     */   
/*   79:     */   public abstract Predicate isNotNull(Expression<?> paramExpression);
/*   80:     */   
/*   81:     */   public abstract Predicate equal(Expression<?> paramExpression1, Expression<?> paramExpression2);
/*   82:     */   
/*   83:     */   public abstract Predicate equal(Expression<?> paramExpression, Object paramObject);
/*   84:     */   
/*   85:     */   public abstract Predicate notEqual(Expression<?> paramExpression1, Expression<?> paramExpression2);
/*   86:     */   
/*   87:     */   public abstract Predicate notEqual(Expression<?> paramExpression, Object paramObject);
/*   88:     */   
/*   89:     */   public abstract <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2);
/*   90:     */   
/*   91:     */   public abstract <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> paramExpression, Y paramY);
/*   92:     */   
/*   93:     */   public abstract <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2);
/*   94:     */   
/*   95:     */   public abstract <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> paramExpression, Y paramY);
/*   96:     */   
/*   97:     */   public abstract <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2);
/*   98:     */   
/*   99:     */   public abstract <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> paramExpression, Y paramY);
/*  100:     */   
/*  101:     */   public abstract <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2);
/*  102:     */   
/*  103:     */   public abstract <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> paramExpression, Y paramY);
/*  104:     */   
/*  105:     */   public abstract <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2, Expression<? extends Y> paramExpression3);
/*  106:     */   
/*  107:     */   public abstract <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> paramExpression, Y paramY1, Y paramY2);
/*  108:     */   
/*  109:     */   public abstract Predicate gt(Expression<? extends Number> paramExpression1, Expression<? extends Number> paramExpression2);
/*  110:     */   
/*  111:     */   public abstract Predicate gt(Expression<? extends Number> paramExpression, Number paramNumber);
/*  112:     */   
/*  113:     */   public abstract Predicate ge(Expression<? extends Number> paramExpression1, Expression<? extends Number> paramExpression2);
/*  114:     */   
/*  115:     */   public abstract Predicate ge(Expression<? extends Number> paramExpression, Number paramNumber);
/*  116:     */   
/*  117:     */   public abstract Predicate lt(Expression<? extends Number> paramExpression1, Expression<? extends Number> paramExpression2);
/*  118:     */   
/*  119:     */   public abstract Predicate lt(Expression<? extends Number> paramExpression, Number paramNumber);
/*  120:     */   
/*  121:     */   public abstract Predicate le(Expression<? extends Number> paramExpression1, Expression<? extends Number> paramExpression2);
/*  122:     */   
/*  123:     */   public abstract Predicate le(Expression<? extends Number> paramExpression, Number paramNumber);
/*  124:     */   
/*  125:     */   public abstract <N extends Number> Expression<N> neg(Expression<N> paramExpression);
/*  126:     */   
/*  127:     */   public abstract <N extends Number> Expression<N> abs(Expression<N> paramExpression);
/*  128:     */   
/*  129:     */   public abstract <N extends Number> Expression<N> sum(Expression<? extends N> paramExpression1, Expression<? extends N> paramExpression2);
/*  130:     */   
/*  131:     */   public abstract <N extends Number> Expression<N> sum(Expression<? extends N> paramExpression, N paramN);
/*  132:     */   
/*  133:     */   public abstract <N extends Number> Expression<N> sum(N paramN, Expression<? extends N> paramExpression);
/*  134:     */   
/*  135:     */   public abstract <N extends Number> Expression<N> prod(Expression<? extends N> paramExpression1, Expression<? extends N> paramExpression2);
/*  136:     */   
/*  137:     */   public abstract <N extends Number> Expression<N> prod(Expression<? extends N> paramExpression, N paramN);
/*  138:     */   
/*  139:     */   public abstract <N extends Number> Expression<N> prod(N paramN, Expression<? extends N> paramExpression);
/*  140:     */   
/*  141:     */   public abstract <N extends Number> Expression<N> diff(Expression<? extends N> paramExpression1, Expression<? extends N> paramExpression2);
/*  142:     */   
/*  143:     */   public abstract <N extends Number> Expression<N> diff(Expression<? extends N> paramExpression, N paramN);
/*  144:     */   
/*  145:     */   public abstract <N extends Number> Expression<N> diff(N paramN, Expression<? extends N> paramExpression);
/*  146:     */   
/*  147:     */   public abstract Expression<Number> quot(Expression<? extends Number> paramExpression1, Expression<? extends Number> paramExpression2);
/*  148:     */   
/*  149:     */   public abstract Expression<Number> quot(Expression<? extends Number> paramExpression, Number paramNumber);
/*  150:     */   
/*  151:     */   public abstract Expression<Number> quot(Number paramNumber, Expression<? extends Number> paramExpression);
/*  152:     */   
/*  153:     */   public abstract Expression<Integer> mod(Expression<Integer> paramExpression1, Expression<Integer> paramExpression2);
/*  154:     */   
/*  155:     */   public abstract Expression<Integer> mod(Expression<Integer> paramExpression, Integer paramInteger);
/*  156:     */   
/*  157:     */   public abstract Expression<Integer> mod(Integer paramInteger, Expression<Integer> paramExpression);
/*  158:     */   
/*  159:     */   public abstract Expression<Double> sqrt(Expression<? extends Number> paramExpression);
/*  160:     */   
/*  161:     */   public abstract Expression<Long> toLong(Expression<? extends Number> paramExpression);
/*  162:     */   
/*  163:     */   public abstract Expression<Integer> toInteger(Expression<? extends Number> paramExpression);
/*  164:     */   
/*  165:     */   public abstract Expression<Float> toFloat(Expression<? extends Number> paramExpression);
/*  166:     */   
/*  167:     */   public abstract Expression<Double> toDouble(Expression<? extends Number> paramExpression);
/*  168:     */   
/*  169:     */   public abstract Expression<BigDecimal> toBigDecimal(Expression<? extends Number> paramExpression);
/*  170:     */   
/*  171:     */   public abstract Expression<BigInteger> toBigInteger(Expression<? extends Number> paramExpression);
/*  172:     */   
/*  173:     */   public abstract Expression<String> toString(Expression<Character> paramExpression);
/*  174:     */   
/*  175:     */   public abstract <T> Expression<T> literal(T paramT);
/*  176:     */   
/*  177:     */   public abstract <T> Expression<T> nullLiteral(Class<T> paramClass);
/*  178:     */   
/*  179:     */   public abstract <T> ParameterExpression<T> parameter(Class<T> paramClass);
/*  180:     */   
/*  181:     */   public abstract <T> ParameterExpression<T> parameter(Class<T> paramClass, String paramString);
/*  182:     */   
/*  183:     */   public abstract <C extends Collection<?>> Predicate isEmpty(Expression<C> paramExpression);
/*  184:     */   
/*  185:     */   public abstract <C extends Collection<?>> Predicate isNotEmpty(Expression<C> paramExpression);
/*  186:     */   
/*  187:     */   public abstract <C extends Collection<?>> Expression<Integer> size(Expression<C> paramExpression);
/*  188:     */   
/*  189:     */   public abstract <C extends Collection<?>> Expression<Integer> size(C paramC);
/*  190:     */   
/*  191:     */   public abstract <E, C extends Collection<E>> Predicate isMember(Expression<E> paramExpression, Expression<C> paramExpression1);
/*  192:     */   
/*  193:     */   public abstract <E, C extends Collection<E>> Predicate isMember(E paramE, Expression<C> paramExpression);
/*  194:     */   
/*  195:     */   public abstract <E, C extends Collection<E>> Predicate isNotMember(Expression<E> paramExpression, Expression<C> paramExpression1);
/*  196:     */   
/*  197:     */   public abstract <E, C extends Collection<E>> Predicate isNotMember(E paramE, Expression<C> paramExpression);
/*  198:     */   
/*  199:     */   public abstract <V, M extends Map<?, V>> Expression<Collection<V>> values(M paramM);
/*  200:     */   
/*  201:     */   public abstract <K, M extends Map<K, ?>> Expression<Set<K>> keys(M paramM);
/*  202:     */   
/*  203:     */   public abstract Predicate like(Expression<String> paramExpression1, Expression<String> paramExpression2);
/*  204:     */   
/*  205:     */   public abstract Predicate like(Expression<String> paramExpression, String paramString);
/*  206:     */   
/*  207:     */   public abstract Predicate like(Expression<String> paramExpression1, Expression<String> paramExpression2, Expression<Character> paramExpression);
/*  208:     */   
/*  209:     */   public abstract Predicate like(Expression<String> paramExpression1, Expression<String> paramExpression2, char paramChar);
/*  210:     */   
/*  211:     */   public abstract Predicate like(Expression<String> paramExpression, String paramString, Expression<Character> paramExpression1);
/*  212:     */   
/*  213:     */   public abstract Predicate like(Expression<String> paramExpression, String paramString, char paramChar);
/*  214:     */   
/*  215:     */   public abstract Predicate notLike(Expression<String> paramExpression1, Expression<String> paramExpression2);
/*  216:     */   
/*  217:     */   public abstract Predicate notLike(Expression<String> paramExpression, String paramString);
/*  218:     */   
/*  219:     */   public abstract Predicate notLike(Expression<String> paramExpression1, Expression<String> paramExpression2, Expression<Character> paramExpression);
/*  220:     */   
/*  221:     */   public abstract Predicate notLike(Expression<String> paramExpression1, Expression<String> paramExpression2, char paramChar);
/*  222:     */   
/*  223:     */   public abstract Predicate notLike(Expression<String> paramExpression, String paramString, Expression<Character> paramExpression1);
/*  224:     */   
/*  225:     */   public abstract Predicate notLike(Expression<String> paramExpression, String paramString, char paramChar);
/*  226:     */   
/*  227:     */   public abstract Expression<String> concat(Expression<String> paramExpression1, Expression<String> paramExpression2);
/*  228:     */   
/*  229:     */   public abstract Expression<String> concat(Expression<String> paramExpression, String paramString);
/*  230:     */   
/*  231:     */   public abstract Expression<String> concat(String paramString, Expression<String> paramExpression);
/*  232:     */   
/*  233:     */   public abstract Expression<String> substring(Expression<String> paramExpression, Expression<Integer> paramExpression1);
/*  234:     */   
/*  235:     */   public abstract Expression<String> substring(Expression<String> paramExpression, int paramInt);
/*  236:     */   
/*  237:     */   public abstract Expression<String> substring(Expression<String> paramExpression, Expression<Integer> paramExpression1, Expression<Integer> paramExpression2);
/*  238:     */   
/*  239:     */   public abstract Expression<String> substring(Expression<String> paramExpression, int paramInt1, int paramInt2);
/*  240:     */   
/*  241:     */   public abstract Expression<String> trim(Expression<String> paramExpression);
/*  242:     */   
/*  243:     */   public abstract Expression<String> trim(Trimspec paramTrimspec, Expression<String> paramExpression);
/*  244:     */   
/*  245:     */   public abstract Expression<String> trim(Expression<Character> paramExpression, Expression<String> paramExpression1);
/*  246:     */   
/*  247:     */   public abstract Expression<String> trim(Trimspec paramTrimspec, Expression<Character> paramExpression, Expression<String> paramExpression1);
/*  248:     */   
/*  249:     */   public abstract Expression<String> trim(char paramChar, Expression<String> paramExpression);
/*  250:     */   
/*  251:     */   public abstract Expression<String> trim(Trimspec paramTrimspec, char paramChar, Expression<String> paramExpression);
/*  252:     */   
/*  253:     */   public abstract Expression<String> lower(Expression<String> paramExpression);
/*  254:     */   
/*  255:     */   public abstract Expression<String> upper(Expression<String> paramExpression);
/*  256:     */   
/*  257:     */   public abstract Expression<Integer> length(Expression<String> paramExpression);
/*  258:     */   
/*  259:     */   public abstract Expression<Integer> locate(Expression<String> paramExpression1, Expression<String> paramExpression2);
/*  260:     */   
/*  261:     */   public abstract Expression<Integer> locate(Expression<String> paramExpression, String paramString);
/*  262:     */   
/*  263:     */   public abstract Expression<Integer> locate(Expression<String> paramExpression1, Expression<String> paramExpression2, Expression<Integer> paramExpression);
/*  264:     */   
/*  265:     */   public abstract Expression<Integer> locate(Expression<String> paramExpression, String paramString, int paramInt);
/*  266:     */   
/*  267:     */   public abstract Expression<Date> currentDate();
/*  268:     */   
/*  269:     */   public abstract Expression<Timestamp> currentTimestamp();
/*  270:     */   
/*  271:     */   public abstract Expression<Time> currentTime();
/*  272:     */   
/*  273:     */   public abstract <T> In<T> in(Expression<? extends T> paramExpression);
/*  274:     */   
/*  275:     */   public abstract <Y> Expression<Y> coalesce(Expression<? extends Y> paramExpression1, Expression<? extends Y> paramExpression2);
/*  276:     */   
/*  277:     */   public abstract <Y> Expression<Y> coalesce(Expression<? extends Y> paramExpression, Y paramY);
/*  278:     */   
/*  279:     */   public abstract <Y> Expression<Y> nullif(Expression<Y> paramExpression, Expression<?> paramExpression1);
/*  280:     */   
/*  281:     */   public abstract <Y> Expression<Y> nullif(Expression<Y> paramExpression, Y paramY);
/*  282:     */   
/*  283:     */   public abstract <T> Coalesce<T> coalesce();
/*  284:     */   
/*  285:     */   public abstract <C, R> SimpleCase<C, R> selectCase(Expression<? extends C> paramExpression);
/*  286:     */   
/*  287:     */   public abstract <R> Case<R> selectCase();
/*  288:     */   
/*  289:     */   public abstract <T> Expression<T> function(String paramString, Class<T> paramClass, Expression<?>... paramVarArgs);
/*  290:     */   
/*  291:     */   public static abstract interface Case<R>
/*  292:     */     extends Expression<R>
/*  293:     */   {
/*  294:     */     public abstract Case<R> when(Expression<Boolean> paramExpression, R paramR);
/*  295:     */     
/*  296:     */     public abstract Case<R> when(Expression<Boolean> paramExpression, Expression<? extends R> paramExpression1);
/*  297:     */     
/*  298:     */     public abstract Expression<R> otherwise(R paramR);
/*  299:     */     
/*  300:     */     public abstract Expression<R> otherwise(Expression<? extends R> paramExpression);
/*  301:     */   }
/*  302:     */   
/*  303:     */   public static abstract interface SimpleCase<C, R>
/*  304:     */     extends Expression<R>
/*  305:     */   {
/*  306:     */     public abstract Expression<C> getExpression();
/*  307:     */     
/*  308:     */     public abstract SimpleCase<C, R> when(C paramC, R paramR);
/*  309:     */     
/*  310:     */     public abstract SimpleCase<C, R> when(C paramC, Expression<? extends R> paramExpression);
/*  311:     */     
/*  312:     */     public abstract Expression<R> otherwise(R paramR);
/*  313:     */     
/*  314:     */     public abstract Expression<R> otherwise(Expression<? extends R> paramExpression);
/*  315:     */   }
/*  316:     */   
/*  317:     */   public static abstract interface Coalesce<T>
/*  318:     */     extends Expression<T>
/*  319:     */   {
/*  320:     */     public abstract Coalesce<T> value(T paramT);
/*  321:     */     
/*  322:     */     public abstract Coalesce<T> value(Expression<? extends T> paramExpression);
/*  323:     */   }
/*  324:     */   
/*  325:     */   public static abstract interface In<T>
/*  326:     */     extends Predicate
/*  327:     */   {
/*  328:     */     public abstract Expression<T> getExpression();
/*  329:     */     
/*  330:     */     public abstract In<T> value(T paramT);
/*  331:     */     
/*  332:     */     public abstract In<T> value(Expression<? extends T> paramExpression);
/*  333:     */   }
/*  334:     */   
/*  335:     */   public static enum Trimspec
/*  336:     */   {
/*  337:1287 */     LEADING,  TRAILING,  BOTH;
/*  338:     */     
/*  339:     */     private Trimspec() {}
/*  340:     */   }
/*  341:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.CriteriaBuilder
 * JD-Core Version:    0.7.0.1
 */