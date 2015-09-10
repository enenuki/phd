/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  6:   */ import org.hibernate.engine.spi.CollectionEntry;
/*  7:   */ import org.hibernate.engine.spi.CollectionKey;
/*  8:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  9:   */ import org.hibernate.event.spi.EventSource;
/* 10:   */ import org.hibernate.internal.CoreMessageLogger;
/* 11:   */ import org.hibernate.pretty.MessageHelper;
/* 12:   */ import org.hibernate.type.CollectionType;
/* 13:   */ import org.jboss.logging.Logger;
/* 14:   */ 
/* 15:   */ public class EvictVisitor
/* 16:   */   extends AbstractVisitor
/* 17:   */ {
/* 18:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EvictVisitor.class.getName());
/* 19:   */   
/* 20:   */   EvictVisitor(EventSource session)
/* 21:   */   {
/* 22:49 */     super(session);
/* 23:   */   }
/* 24:   */   
/* 25:   */   Object processCollection(Object collection, CollectionType type)
/* 26:   */     throws HibernateException
/* 27:   */   {
/* 28:56 */     if (collection != null) {
/* 29:56 */       evictCollection(collection, type);
/* 30:   */     }
/* 31:58 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void evictCollection(Object value, CollectionType type)
/* 35:   */   {
/* 36:   */     Object pc;
/* 37:63 */     if (type.hasHolder())
/* 38:   */     {
/* 39:64 */       pc = getSession().getPersistenceContext().removeCollectionHolder(value);
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:   */       Object pc;
/* 44:66 */       if ((value instanceof PersistentCollection)) {
/* 45:67 */         pc = value;
/* 46:   */       } else {
/* 47:   */         return;
/* 48:   */       }
/* 49:   */     }
/* 50:   */     Object pc;
/* 51:73 */     PersistentCollection collection = (PersistentCollection)pc;
/* 52:74 */     if (collection.unsetSession(getSession())) {
/* 53:74 */       evictCollection(collection);
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   private void evictCollection(PersistentCollection collection)
/* 58:   */   {
/* 59:78 */     CollectionEntry ce = (CollectionEntry)getSession().getPersistenceContext().getCollectionEntries().remove(collection);
/* 60:79 */     if (LOG.isDebugEnabled()) {
/* 61:80 */       LOG.debugf("Evicting collection: %s", MessageHelper.collectionInfoString(ce.getLoadedPersister(), ce.getLoadedKey(), getSession().getFactory()));
/* 62:   */     }
/* 63:85 */     if ((ce.getLoadedPersister() != null) && (ce.getLoadedKey() != null)) {
/* 64:87 */       getSession().getPersistenceContext().getCollectionsByKey().remove(new CollectionKey(ce.getLoadedPersister(), ce.getLoadedKey()));
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.EvictVisitor
 * JD-Core Version:    0.7.0.1
 */