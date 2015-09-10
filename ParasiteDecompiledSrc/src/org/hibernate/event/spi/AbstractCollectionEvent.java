/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   5:    */ import org.hibernate.engine.spi.CollectionEntry;
/*   6:    */ import org.hibernate.engine.spi.EntityEntry;
/*   7:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   8:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   9:    */ import org.hibernate.persister.entity.EntityPersister;
/*  10:    */ 
/*  11:    */ public abstract class AbstractCollectionEvent
/*  12:    */   extends AbstractEvent
/*  13:    */ {
/*  14:    */   private final PersistentCollection collection;
/*  15:    */   private final Object affectedOwner;
/*  16:    */   private final Serializable affectedOwnerId;
/*  17:    */   private final String affectedOwnerEntityName;
/*  18:    */   
/*  19:    */   public AbstractCollectionEvent(CollectionPersister collectionPersister, PersistentCollection collection, EventSource source, Object affectedOwner, Serializable affectedOwnerId)
/*  20:    */   {
/*  21: 61 */     super(source);
/*  22: 62 */     this.collection = collection;
/*  23: 63 */     this.affectedOwner = affectedOwner;
/*  24: 64 */     this.affectedOwnerId = affectedOwnerId;
/*  25: 65 */     this.affectedOwnerEntityName = getAffectedOwnerEntityName(collectionPersister, affectedOwner, source);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected static CollectionPersister getLoadedCollectionPersister(PersistentCollection collection, EventSource source)
/*  29:    */   {
/*  30: 70 */     CollectionEntry ce = source.getPersistenceContext().getCollectionEntry(collection);
/*  31: 71 */     return ce == null ? null : ce.getLoadedPersister();
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected static Object getLoadedOwnerOrNull(PersistentCollection collection, EventSource source)
/*  35:    */   {
/*  36: 75 */     return source.getPersistenceContext().getLoadedCollectionOwnerOrNull(collection);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected static Serializable getLoadedOwnerIdOrNull(PersistentCollection collection, EventSource source)
/*  40:    */   {
/*  41: 79 */     return source.getPersistenceContext().getLoadedCollectionOwnerIdOrNull(collection);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected static Serializable getOwnerIdOrNull(Object owner, EventSource source)
/*  45:    */   {
/*  46: 83 */     EntityEntry ownerEntry = source.getPersistenceContext().getEntry(owner);
/*  47: 84 */     return ownerEntry == null ? null : ownerEntry.getId();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected static String getAffectedOwnerEntityName(CollectionPersister collectionPersister, Object affectedOwner, EventSource source)
/*  51:    */   {
/*  52: 91 */     String entityName = collectionPersister == null ? null : collectionPersister.getOwnerEntityPersister().getEntityName();
/*  53: 93 */     if (affectedOwner != null)
/*  54:    */     {
/*  55: 94 */       EntityEntry ee = source.getPersistenceContext().getEntry(affectedOwner);
/*  56: 95 */       if ((ee != null) && (ee.getEntityName() != null)) {
/*  57: 96 */         entityName = ee.getEntityName();
/*  58:    */       }
/*  59:    */     }
/*  60: 99 */     return entityName;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public PersistentCollection getCollection()
/*  64:    */   {
/*  65:103 */     return this.collection;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object getAffectedOwnerOrNull()
/*  69:    */   {
/*  70:113 */     return this.affectedOwner;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Serializable getAffectedOwnerIdOrNull()
/*  74:    */   {
/*  75:124 */     return this.affectedOwnerId;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getAffectedOwnerEntityName()
/*  79:    */   {
/*  80:134 */     return this.affectedOwnerEntityName;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.AbstractCollectionEvent
 * JD-Core Version:    0.7.0.1
 */