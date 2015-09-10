/*  1:   */ package org.hibernate.internal;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.ObjectOutputStream;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import org.hibernate.engine.internal.StatefulPersistenceContext;
/*  8:   */ import org.hibernate.engine.spi.ActionQueue;
/*  9:   */ import org.hibernate.engine.spi.NonFlushedChanges;
/* 10:   */ import org.hibernate.event.spi.EventSource;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public final class NonFlushedChangesImpl
/* 14:   */   implements NonFlushedChanges, Serializable
/* 15:   */ {
/* 16:38 */   private static final Logger LOG = Logger.getLogger(NonFlushedChangesImpl.class.getName());
/* 17:   */   private transient ActionQueue actionQueue;
/* 18:   */   private transient StatefulPersistenceContext persistenceContext;
/* 19:   */   
/* 20:   */   public NonFlushedChangesImpl(EventSource session)
/* 21:   */   {
/* 22:44 */     this.actionQueue = session.getActionQueue();
/* 23:45 */     this.persistenceContext = ((StatefulPersistenceContext)session.getPersistenceContext());
/* 24:   */   }
/* 25:   */   
/* 26:   */   ActionQueue getActionQueue()
/* 27:   */   {
/* 28:50 */     return this.actionQueue;
/* 29:   */   }
/* 30:   */   
/* 31:   */   StatefulPersistenceContext getPersistenceContext()
/* 32:   */   {
/* 33:55 */     return this.persistenceContext;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void clear() {}
/* 37:   */   
/* 38:   */   private void readObject(ObjectInputStream ois)
/* 39:   */     throws IOException, ClassNotFoundException
/* 40:   */   {
/* 41:62 */     LOG.trace("Deserializing NonFlushedChangesImpl");
/* 42:63 */     ois.defaultReadObject();
/* 43:64 */     this.persistenceContext = StatefulPersistenceContext.deserialize(ois, null);
/* 44:65 */     this.actionQueue = ActionQueue.deserialize(ois, null);
/* 45:   */   }
/* 46:   */   
/* 47:   */   private void writeObject(ObjectOutputStream oos)
/* 48:   */     throws IOException
/* 49:   */   {
/* 50:69 */     LOG.trace("Serializing NonFlushedChangesImpl");
/* 51:70 */     oos.defaultWriteObject();
/* 52:71 */     this.persistenceContext.serialize(oos);
/* 53:72 */     this.actionQueue.serialize(oos);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.NonFlushedChangesImpl
 * JD-Core Version:    0.7.0.1
 */