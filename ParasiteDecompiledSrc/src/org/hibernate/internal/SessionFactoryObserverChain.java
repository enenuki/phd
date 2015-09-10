/*  1:   */ package org.hibernate.internal;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.SessionFactory;
/*  6:   */ import org.hibernate.SessionFactoryObserver;
/*  7:   */ 
/*  8:   */ public class SessionFactoryObserverChain
/*  9:   */   implements SessionFactoryObserver
/* 10:   */ {
/* 11:   */   private List<SessionFactoryObserver> observers;
/* 12:   */   
/* 13:   */   public void addObserver(SessionFactoryObserver observer)
/* 14:   */   {
/* 15:39 */     if (this.observers == null) {
/* 16:40 */       this.observers = new ArrayList();
/* 17:   */     }
/* 18:42 */     this.observers.add(observer);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void sessionFactoryCreated(SessionFactory factory)
/* 22:   */   {
/* 23:47 */     if (this.observers == null) {
/* 24:48 */       return;
/* 25:   */     }
/* 26:51 */     for (SessionFactoryObserver observer : this.observers) {
/* 27:52 */       observer.sessionFactoryCreated(factory);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void sessionFactoryClosed(SessionFactory factory)
/* 32:   */   {
/* 33:58 */     if (this.observers == null) {
/* 34:59 */       return;
/* 35:   */     }
/* 36:62 */     for (SessionFactoryObserver observer : this.observers) {
/* 37:63 */       observer.sessionFactoryClosed(factory);
/* 38:   */     }
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.SessionFactoryObserverChain
 * JD-Core Version:    0.7.0.1
 */