/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.event.service.internal.EventListenerServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.SessionFactoryServiceInitiator;
/*  8:   */ import org.hibernate.stat.internal.StatisticsInitiator;
/*  9:   */ 
/* 10:   */ public class StandardSessionFactoryServiceInitiators
/* 11:   */ {
/* 12:41 */   public static List<SessionFactoryServiceInitiator> LIST = ;
/* 13:   */   
/* 14:   */   private static List<SessionFactoryServiceInitiator> buildStandardServiceInitiatorList()
/* 15:   */   {
/* 16:44 */     List<SessionFactoryServiceInitiator> serviceInitiators = new ArrayList();
/* 17:   */     
/* 18:46 */     serviceInitiators.add(EventListenerServiceInitiator.INSTANCE);
/* 19:47 */     serviceInitiators.add(StatisticsInitiator.INSTANCE);
/* 20:   */     
/* 21:49 */     return Collections.unmodifiableList(serviceInitiators);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.StandardSessionFactoryServiceInitiators
 * JD-Core Version:    0.7.0.1
 */