/*  1:   */ package org.hibernate.internal.util;
/*  2:   */ 
/*  3:   */ public class Value<T>
/*  4:   */ {
/*  5:   */   private final DeferredInitializer<T> valueInitializer;
/*  6:   */   private T value;
/*  7:   */   
/*  8:   */   public Value(DeferredInitializer<T> valueInitializer)
/*  9:   */   {
/* 10:59 */     this.valueInitializer = valueInitializer;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Value(T value)
/* 14:   */   {
/* 15:64 */     this(NO_DEFERRED_INITIALIZER);
/* 16:65 */     this.value = value;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public T getValue()
/* 20:   */   {
/* 21:69 */     if (this.value == null) {
/* 22:70 */       this.value = this.valueInitializer.initialize();
/* 23:   */     }
/* 24:72 */     return this.value;
/* 25:   */   }
/* 26:   */   
/* 27:75 */   private static final DeferredInitializer NO_DEFERRED_INITIALIZER = new DeferredInitializer()
/* 28:   */   {
/* 29:   */     public Void initialize()
/* 30:   */     {
/* 31:78 */       return null;
/* 32:   */     }
/* 33:   */   };
/* 34:   */   
/* 35:   */   public static abstract interface DeferredInitializer<T>
/* 36:   */   {
/* 37:   */     public abstract T initialize();
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.Value
 * JD-Core Version:    0.7.0.1
 */