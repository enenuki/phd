/*  1:   */ package org.hibernate.service.instrumentation.internal;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.ConcurrentHashMap;
/*  4:   */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  5:   */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  6:   */ 
/*  7:   */ public class CachingInstrumentationService
/*  8:   */   implements InstrumentationService
/*  9:   */ {
/* 10:13 */   private final ConcurrentHashMap<Class<?>, Boolean> isInstrumentedCache = new ConcurrentHashMap();
/* 11:   */   
/* 12:   */   public boolean isInstrumented(Class<?> entityType)
/* 13:   */   {
/* 14:17 */     Boolean isInstrumented = (Boolean)this.isInstrumentedCache.get(entityType);
/* 15:18 */     if (isInstrumented == null)
/* 16:   */     {
/* 17:19 */       isInstrumented = Boolean.valueOf(FieldInterceptionHelper.isInstrumented(entityType));
/* 18:20 */       this.isInstrumentedCache.put(entityType, isInstrumented);
/* 19:   */     }
/* 20:23 */     return isInstrumented.booleanValue();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isInstrumented(Object entity)
/* 24:   */   {
/* 25:28 */     return (entity != null) && (isInstrumented(entity.getClass()));
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.instrumentation.internal.CachingInstrumentationService
 * JD-Core Version:    0.7.0.1
 */