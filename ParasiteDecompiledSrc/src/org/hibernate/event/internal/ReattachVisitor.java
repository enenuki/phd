/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.action.internal.CollectionRemoveAction;
/*   6:    */ import org.hibernate.engine.spi.ActionQueue;
/*   7:    */ import org.hibernate.event.spi.EventSource;
/*   8:    */ import org.hibernate.internal.CoreMessageLogger;
/*   9:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  10:    */ import org.hibernate.persister.entity.EntityPersister;
/*  11:    */ import org.hibernate.pretty.MessageHelper;
/*  12:    */ import org.hibernate.type.CollectionType;
/*  13:    */ import org.hibernate.type.CompositeType;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public abstract class ReattachVisitor
/*  18:    */   extends ProxyVisitor
/*  19:    */ {
/*  20: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ReattachVisitor.class.getName());
/*  21:    */   private final Serializable ownerIdentifier;
/*  22:    */   private final Object owner;
/*  23:    */   
/*  24:    */   public ReattachVisitor(EventSource session, Serializable ownerIdentifier, Object owner)
/*  25:    */   {
/*  26: 52 */     super(session);
/*  27: 53 */     this.ownerIdentifier = ownerIdentifier;
/*  28: 54 */     this.owner = owner;
/*  29:    */   }
/*  30:    */   
/*  31:    */   final Serializable getOwnerIdentifier()
/*  32:    */   {
/*  33: 63 */     return this.ownerIdentifier;
/*  34:    */   }
/*  35:    */   
/*  36:    */   final Object getOwner()
/*  37:    */   {
/*  38: 72 */     return this.owner;
/*  39:    */   }
/*  40:    */   
/*  41:    */   Object processComponent(Object component, CompositeType componentType)
/*  42:    */     throws HibernateException
/*  43:    */   {
/*  44: 80 */     Type[] types = componentType.getSubtypes();
/*  45: 81 */     if (component == null) {
/*  46: 82 */       processValues(new Object[types.length], types);
/*  47:    */     } else {
/*  48: 85 */       super.processComponent(component, componentType);
/*  49:    */     }
/*  50: 88 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   void removeCollection(CollectionPersister role, Serializable collectionKey, EventSource source)
/*  54:    */     throws HibernateException
/*  55:    */   {
/*  56:100 */     if (LOG.isTraceEnabled()) {
/*  57:101 */       LOG.tracev("Collection dereferenced while transient {0}", MessageHelper.collectionInfoString(role, this.ownerIdentifier, source.getFactory()));
/*  58:    */     }
/*  59:104 */     source.getActionQueue().addAction(new CollectionRemoveAction(this.owner, role, collectionKey, false, source));
/*  60:    */   }
/*  61:    */   
/*  62:    */   final Serializable extractCollectionKeyFromOwner(CollectionPersister role)
/*  63:    */   {
/*  64:117 */     if (role.getCollectionType().useLHSPrimaryKey()) {
/*  65:118 */       return this.ownerIdentifier;
/*  66:    */     }
/*  67:120 */     return (Serializable)role.getOwnerEntityPersister().getPropertyValue(this.owner, role.getCollectionType().getLHSPropertyName());
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.ReattachVisitor
 * JD-Core Version:    0.7.0.1
 */