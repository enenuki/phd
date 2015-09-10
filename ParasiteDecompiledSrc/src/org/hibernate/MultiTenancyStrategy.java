/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.internal.CoreMessageLogger;
/*  5:   */ import org.jboss.logging.Logger;
/*  6:   */ 
/*  7:   */ public enum MultiTenancyStrategy
/*  8:   */ {
/*  9:43 */   DISCRIMINATOR,  SCHEMA,  DATABASE,  NONE;
/* 10:   */   
/* 11:56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MultiTenancyStrategy.class.getName());
/* 12:   */   
/* 13:   */   private MultiTenancyStrategy() {}
/* 14:   */   
/* 15:   */   public static MultiTenancyStrategy determineMultiTenancyStrategy(Map properties)
/* 16:   */   {
/* 17:61 */     Object strategy = properties.get("hibernate.multiTenancy");
/* 18:62 */     if (strategy == null) {
/* 19:63 */       return NONE;
/* 20:   */     }
/* 21:66 */     if (MultiTenancyStrategy.class.isInstance(strategy)) {
/* 22:67 */       return (MultiTenancyStrategy)strategy;
/* 23:   */     }
/* 24:70 */     String strategyName = strategy.toString();
/* 25:   */     try
/* 26:   */     {
/* 27:72 */       return valueOf(strategyName.toUpperCase());
/* 28:   */     }
/* 29:   */     catch (RuntimeException e)
/* 30:   */     {
/* 31:75 */       LOG.warn("Unknown multi tenancy strategy [ " + strategyName + " ], using MultiTenancyStrategy.NONE.");
/* 32:   */     }
/* 33:76 */     return NONE;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.MultiTenancyStrategy
 * JD-Core Version:    0.7.0.1
 */