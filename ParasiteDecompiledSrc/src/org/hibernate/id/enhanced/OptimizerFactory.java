/*   1:    */ package org.hibernate.id.enhanced;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.id.IntegralDataTypeHolder;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.ReflectHelper;
/*   9:    */ import org.jboss.logging.Logger;
/*  10:    */ 
/*  11:    */ public class OptimizerFactory
/*  12:    */ {
/*  13: 43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, OptimizerFactory.class.getName());
/*  14:    */   public static final String NONE = "none";
/*  15:    */   public static final String HILO = "hilo";
/*  16:    */   public static final String LEGACY_HILO = "legacy-hilo";
/*  17:    */   public static final String POOL = "pooled";
/*  18:    */   public static final String POOL_LO = "pooled-lo";
/*  19: 51 */   private static Class[] CTOR_SIG = { Class.class, Integer.TYPE };
/*  20:    */   
/*  21:    */   @Deprecated
/*  22:    */   public static Optimizer buildOptimizer(String type, Class returnClass, int incrementSize)
/*  23:    */   {
/*  24:    */     String optimizerClassName;
/*  25:    */     String optimizerClassName;
/*  26: 86 */     if ("none".equals(type))
/*  27:    */     {
/*  28: 87 */       optimizerClassName = NoopOptimizer.class.getName();
/*  29:    */     }
/*  30:    */     else
/*  31:    */     {
/*  32:    */       String optimizerClassName;
/*  33: 89 */       if ("hilo".equals(type))
/*  34:    */       {
/*  35: 90 */         optimizerClassName = HiLoOptimizer.class.getName();
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39:    */         String optimizerClassName;
/*  40: 92 */         if ("legacy-hilo".equals(type))
/*  41:    */         {
/*  42: 93 */           optimizerClassName = LegacyHiLoAlgorithmOptimizer.class.getName();
/*  43:    */         }
/*  44:    */         else
/*  45:    */         {
/*  46:    */           String optimizerClassName;
/*  47: 95 */           if ("pooled".equals(type))
/*  48:    */           {
/*  49: 96 */             optimizerClassName = PooledOptimizer.class.getName();
/*  50:    */           }
/*  51:    */           else
/*  52:    */           {
/*  53:    */             String optimizerClassName;
/*  54: 98 */             if ("pooled-lo".equals(type)) {
/*  55: 99 */               optimizerClassName = PooledLoOptimizer.class.getName();
/*  56:    */             } else {
/*  57:102 */               optimizerClassName = type;
/*  58:    */             }
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65:106 */       Class optimizerClass = ReflectHelper.classForName(optimizerClassName);
/*  66:107 */       Constructor ctor = optimizerClass.getConstructor(CTOR_SIG);
/*  67:108 */       return (Optimizer)ctor.newInstance(new Object[] { returnClass, Integer.valueOf(incrementSize) });
/*  68:    */     }
/*  69:    */     catch (Throwable ignore)
/*  70:    */     {
/*  71:111 */       LOG.unableToInstantiateOptimizer(type);
/*  72:    */     }
/*  73:115 */     return new NoopOptimizer(returnClass, incrementSize);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static Optimizer buildOptimizer(String type, Class returnClass, int incrementSize, long explicitInitialValue)
/*  77:    */   {
/*  78:131 */     Optimizer optimizer = buildOptimizer(type, returnClass, incrementSize);
/*  79:132 */     if (InitialValueAwareOptimizer.class.isInstance(optimizer)) {
/*  80:133 */       ((InitialValueAwareOptimizer)optimizer).injectInitialValue(explicitInitialValue);
/*  81:    */     }
/*  82:135 */     return optimizer;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static abstract interface InitialValueAwareOptimizer
/*  86:    */   {
/*  87:    */     public abstract void injectInitialValue(long paramLong);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static abstract class OptimizerSupport
/*  91:    */     implements Optimizer
/*  92:    */   {
/*  93:    */     protected final Class returnClass;
/*  94:    */     protected final int incrementSize;
/*  95:    */     
/*  96:    */     protected OptimizerSupport(Class returnClass, int incrementSize)
/*  97:    */     {
/*  98:152 */       if (returnClass == null) {
/*  99:153 */         throw new HibernateException("return class is required");
/* 100:    */       }
/* 101:155 */       this.returnClass = returnClass;
/* 102:156 */       this.incrementSize = incrementSize;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public final Class getReturnClass()
/* 106:    */     {
/* 107:166 */       return this.returnClass;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public final int getIncrementSize()
/* 111:    */     {
/* 112:173 */       return this.incrementSize;
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static class NoopOptimizer
/* 117:    */     extends OptimizerFactory.OptimizerSupport
/* 118:    */   {
/* 119:    */     private IntegralDataTypeHolder lastSourceValue;
/* 120:    */     
/* 121:    */     public NoopOptimizer(Class returnClass, int incrementSize)
/* 122:    */     {
/* 123:185 */       super(incrementSize);
/* 124:    */     }
/* 125:    */     
/* 126:    */     public Serializable generate(AccessCallback callback)
/* 127:    */     {
/* 128:194 */       IntegralDataTypeHolder value = null;
/* 129:195 */       while ((value == null) || (value.lt(1L))) {
/* 130:196 */         value = callback.getNextValue();
/* 131:    */       }
/* 132:198 */       this.lastSourceValue = value;
/* 133:199 */       return value.makeValue();
/* 134:    */     }
/* 135:    */     
/* 136:    */     public IntegralDataTypeHolder getLastSourceValue()
/* 137:    */     {
/* 138:206 */       return this.lastSourceValue;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public boolean applyIncrementSizeToSourceValues()
/* 142:    */     {
/* 143:213 */       return false;
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static class HiLoOptimizer
/* 148:    */     extends OptimizerFactory.OptimizerSupport
/* 149:    */   {
/* 150:    */     private IntegralDataTypeHolder lastSourceValue;
/* 151:    */     private IntegralDataTypeHolder upperLimit;
/* 152:    */     private IntegralDataTypeHolder value;
/* 153:    */     
/* 154:    */     public HiLoOptimizer(Class returnClass, int incrementSize)
/* 155:    */     {
/* 156:256 */       super(incrementSize);
/* 157:257 */       if (incrementSize < 1) {
/* 158:258 */         throw new HibernateException("increment size cannot be less than 1");
/* 159:    */       }
/* 160:259 */       if (OptimizerFactory.LOG.isTraceEnabled()) {
/* 161:260 */         OptimizerFactory.LOG.tracev("Creating hilo optimizer with [incrementSize={0}; returnClass={1}]", Integer.valueOf(incrementSize), returnClass.getName());
/* 162:    */       }
/* 163:    */     }
/* 164:    */     
/* 165:    */     public synchronized Serializable generate(AccessCallback callback)
/* 166:    */     {
/* 167:268 */       if (this.lastSourceValue == null)
/* 168:    */       {
/* 169:271 */         this.lastSourceValue = callback.getNextValue();
/* 170:272 */         while (this.lastSourceValue.lt(1L)) {
/* 171:273 */           this.lastSourceValue = callback.getNextValue();
/* 172:    */         }
/* 173:276 */         this.upperLimit = this.lastSourceValue.copy().multiplyBy(this.incrementSize).increment();
/* 174:    */         
/* 175:278 */         this.value = this.upperLimit.copy().subtract(this.incrementSize);
/* 176:    */       }
/* 177:280 */       else if (!this.upperLimit.gt(this.value))
/* 178:    */       {
/* 179:281 */         this.lastSourceValue = callback.getNextValue();
/* 180:282 */         this.upperLimit = this.lastSourceValue.copy().multiplyBy(this.incrementSize).increment();
/* 181:    */       }
/* 182:284 */       return this.value.makeValueThenIncrement();
/* 183:    */     }
/* 184:    */     
/* 185:    */     public IntegralDataTypeHolder getLastSourceValue()
/* 186:    */     {
/* 187:292 */       return this.lastSourceValue;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public boolean applyIncrementSizeToSourceValues()
/* 191:    */     {
/* 192:299 */       return false;
/* 193:    */     }
/* 194:    */     
/* 195:    */     public IntegralDataTypeHolder getLastValue()
/* 196:    */     {
/* 197:310 */       return this.value.copy().decrement();
/* 198:    */     }
/* 199:    */     
/* 200:    */     public IntegralDataTypeHolder getHiValue()
/* 201:    */     {
/* 202:321 */       return this.upperLimit;
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static class LegacyHiLoAlgorithmOptimizer
/* 207:    */     extends OptimizerFactory.OptimizerSupport
/* 208:    */   {
/* 209:    */     private long maxLo;
/* 210:    */     private long lo;
/* 211:    */     private IntegralDataTypeHolder hi;
/* 212:    */     private IntegralDataTypeHolder lastSourceValue;
/* 213:    */     private IntegralDataTypeHolder value;
/* 214:    */     
/* 215:    */     public LegacyHiLoAlgorithmOptimizer(Class returnClass, int incrementSize)
/* 216:    */     {
/* 217:335 */       super(incrementSize);
/* 218:336 */       if (incrementSize < 1) {
/* 219:337 */         throw new HibernateException("increment size cannot be less than 1");
/* 220:    */       }
/* 221:338 */       if (OptimizerFactory.LOG.isTraceEnabled()) {
/* 222:339 */         OptimizerFactory.LOG.tracev("Creating hilo optimizer (legacy) with [incrementSize={0}; returnClass={1}]", Integer.valueOf(incrementSize), returnClass.getName());
/* 223:    */       }
/* 224:341 */       this.maxLo = incrementSize;
/* 225:342 */       this.lo = (this.maxLo + 1L);
/* 226:    */     }
/* 227:    */     
/* 228:    */     public synchronized Serializable generate(AccessCallback callback)
/* 229:    */     {
/* 230:349 */       if (this.lo > this.maxLo)
/* 231:    */       {
/* 232:350 */         this.lastSourceValue = callback.getNextValue();
/* 233:351 */         this.lo = (this.lastSourceValue.eq(0L) ? 1L : 0L);
/* 234:352 */         this.hi = this.lastSourceValue.copy().multiplyBy(this.maxLo + 1L);
/* 235:    */       }
/* 236:354 */       this.value = this.hi.copy().add(this.lo++);
/* 237:355 */       return this.value.makeValue();
/* 238:    */     }
/* 239:    */     
/* 240:    */     public IntegralDataTypeHolder getLastSourceValue()
/* 241:    */     {
/* 242:362 */       return this.lastSourceValue.copy();
/* 243:    */     }
/* 244:    */     
/* 245:    */     public boolean applyIncrementSizeToSourceValues()
/* 246:    */     {
/* 247:369 */       return false;
/* 248:    */     }
/* 249:    */     
/* 250:    */     public IntegralDataTypeHolder getLastValue()
/* 251:    */     {
/* 252:380 */       return this.value;
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static class PooledOptimizer
/* 257:    */     extends OptimizerFactory.OptimizerSupport
/* 258:    */     implements OptimizerFactory.InitialValueAwareOptimizer
/* 259:    */   {
/* 260:    */     private IntegralDataTypeHolder hiValue;
/* 261:    */     private IntegralDataTypeHolder value;
/* 262:398 */     private long initialValue = -1L;
/* 263:    */     
/* 264:    */     public PooledOptimizer(Class returnClass, int incrementSize)
/* 265:    */     {
/* 266:401 */       super(incrementSize);
/* 267:402 */       if (incrementSize < 1) {
/* 268:403 */         throw new HibernateException("increment size cannot be less than 1");
/* 269:    */       }
/* 270:405 */       if (OptimizerFactory.LOG.isTraceEnabled()) {
/* 271:406 */         OptimizerFactory.LOG.tracev("Creating pooled optimizer with [incrementSize={0}; returnClass={1}]", Integer.valueOf(incrementSize), returnClass.getName());
/* 272:    */       }
/* 273:    */     }
/* 274:    */     
/* 275:    */     public synchronized Serializable generate(AccessCallback callback)
/* 276:    */     {
/* 277:414 */       if (this.hiValue == null)
/* 278:    */       {
/* 279:415 */         this.value = callback.getNextValue();
/* 280:420 */         if (this.value.lt(1L)) {
/* 281:420 */           OptimizerFactory.LOG.pooledOptimizerReportedInitialValue(this.value);
/* 282:    */         }
/* 283:422 */         if (((this.initialValue == -1L) && (this.value.lt(this.incrementSize))) || (this.value.eq(this.initialValue)))
/* 284:    */         {
/* 285:422 */           this.hiValue = callback.getNextValue();
/* 286:    */         }
/* 287:    */         else
/* 288:    */         {
/* 289:424 */           this.hiValue = this.value;
/* 290:425 */           this.value = this.hiValue.copy().subtract(this.incrementSize);
/* 291:    */         }
/* 292:    */       }
/* 293:428 */       else if (!this.hiValue.gt(this.value))
/* 294:    */       {
/* 295:429 */         this.hiValue = callback.getNextValue();
/* 296:430 */         this.value = this.hiValue.copy().subtract(this.incrementSize);
/* 297:    */       }
/* 298:432 */       return this.value.makeValueThenIncrement();
/* 299:    */     }
/* 300:    */     
/* 301:    */     public IntegralDataTypeHolder getLastSourceValue()
/* 302:    */     {
/* 303:439 */       return this.hiValue;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public boolean applyIncrementSizeToSourceValues()
/* 307:    */     {
/* 308:446 */       return true;
/* 309:    */     }
/* 310:    */     
/* 311:    */     public IntegralDataTypeHolder getLastValue()
/* 312:    */     {
/* 313:457 */       return this.value.copy().decrement();
/* 314:    */     }
/* 315:    */     
/* 316:    */     public void injectInitialValue(long initialValue)
/* 317:    */     {
/* 318:464 */       this.initialValue = initialValue;
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   public static class PooledLoOptimizer
/* 323:    */     extends OptimizerFactory.OptimizerSupport
/* 324:    */   {
/* 325:    */     private IntegralDataTypeHolder lastSourceValue;
/* 326:    */     private IntegralDataTypeHolder value;
/* 327:    */     
/* 328:    */     public PooledLoOptimizer(Class returnClass, int incrementSize)
/* 329:    */     {
/* 330:473 */       super(incrementSize);
/* 331:474 */       if (incrementSize < 1) {
/* 332:475 */         throw new HibernateException("increment size cannot be less than 1");
/* 333:    */       }
/* 334:477 */       if (OptimizerFactory.LOG.isTraceEnabled()) {
/* 335:478 */         OptimizerFactory.LOG.tracev("Creating pooled optimizer (lo) with [incrementSize={0}; returnClass=]", Integer.valueOf(incrementSize), returnClass.getName());
/* 336:    */       }
/* 337:    */     }
/* 338:    */     
/* 339:    */     public Serializable generate(AccessCallback callback)
/* 340:    */     {
/* 341:483 */       if ((this.lastSourceValue == null) || (!this.value.lt(this.lastSourceValue.copy().add(this.incrementSize))))
/* 342:    */       {
/* 343:484 */         this.lastSourceValue = callback.getNextValue();
/* 344:485 */         this.value = this.lastSourceValue.copy();
/* 345:487 */         while (this.value.lt(1L)) {
/* 346:488 */           this.value.increment();
/* 347:    */         }
/* 348:    */       }
/* 349:491 */       return this.value.makeValueThenIncrement();
/* 350:    */     }
/* 351:    */     
/* 352:    */     public IntegralDataTypeHolder getLastSourceValue()
/* 353:    */     {
/* 354:495 */       return this.lastSourceValue;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public boolean applyIncrementSizeToSourceValues()
/* 358:    */     {
/* 359:499 */       return true;
/* 360:    */     }
/* 361:    */   }
/* 362:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.OptimizerFactory
 * JD-Core Version:    0.7.0.1
 */