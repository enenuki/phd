/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.Interceptor;
/*  4:   */ import org.hibernate.event.spi.EventSource;
/*  5:   */ import org.hibernate.event.spi.PreLoadEvent;
/*  6:   */ import org.hibernate.event.spi.PreLoadEventListener;
/*  7:   */ import org.hibernate.persister.entity.EntityPersister;
/*  8:   */ 
/*  9:   */ public class DefaultPreLoadEventListener
/* 10:   */   implements PreLoadEventListener
/* 11:   */ {
/* 12:   */   public void onPreLoad(PreLoadEvent event)
/* 13:   */   {
/* 14:39 */     EntityPersister persister = event.getPersister();
/* 15:40 */     event.getSession().getInterceptor().onLoad(event.getEntity(), event.getId(), event.getState(), persister.getPropertyNames(), persister.getPropertyTypes());
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultPreLoadEventListener
 * JD-Core Version:    0.7.0.1
 */