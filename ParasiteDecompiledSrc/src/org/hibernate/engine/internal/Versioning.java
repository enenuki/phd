/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   4:    */ import org.hibernate.internal.CoreMessageLogger;
/*   5:    */ import org.hibernate.persister.entity.EntityPersister;
/*   6:    */ import org.hibernate.type.VersionType;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ 
/*   9:    */ public final class Versioning
/*  10:    */ {
/*  11:    */   public static final int OPTIMISTIC_LOCK_NONE = -1;
/*  12:    */   public static final int OPTIMISTIC_LOCK_VERSION = 0;
/*  13:    */   public static final int OPTIMISTIC_LOCK_ALL = 2;
/*  14:    */   public static final int OPTIMISTIC_LOCK_DIRTY = 1;
/*  15: 65 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Versioning.class.getName());
/*  16:    */   
/*  17:    */   private static Object seed(VersionType versionType, SessionImplementor session)
/*  18:    */   {
/*  19: 81 */     Object seed = versionType.seed(session);
/*  20: 82 */     LOG.tracev("Seeding: {0}", seed);
/*  21: 83 */     return seed;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static boolean seedVersion(Object[] fields, int versionProperty, VersionType versionType, SessionImplementor session)
/*  25:    */   {
/*  26:103 */     Object initialVersion = fields[versionProperty];
/*  27:104 */     if ((initialVersion == null) || (((initialVersion instanceof Number)) && (((Number)initialVersion).longValue() < 0L)))
/*  28:    */     {
/*  29:112 */       fields[versionProperty] = seed(versionType, session);
/*  30:113 */       return true;
/*  31:    */     }
/*  32:115 */     LOG.tracev("Using initial version: {0}", initialVersion);
/*  33:116 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Object increment(Object version, VersionType versionType, SessionImplementor session)
/*  37:    */   {
/*  38:130 */     Object next = versionType.next(version, session);
/*  39:131 */     if (LOG.isTraceEnabled()) {
/*  40:132 */       LOG.tracev("Incrementing: {0} to {1}", versionType.toLoggableString(version, session.getFactory()), versionType.toLoggableString(next, session.getFactory()));
/*  41:    */     }
/*  42:135 */     return next;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static void setVersion(Object[] fields, Object version, EntityPersister persister)
/*  46:    */   {
/*  47:146 */     if (!persister.isVersioned()) {
/*  48:147 */       return;
/*  49:    */     }
/*  50:149 */     fields[persister.getVersionProperty()] = version;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Object getVersion(Object[] fields, EntityPersister persister)
/*  54:    */   {
/*  55:160 */     if (!persister.isVersioned()) {
/*  56:161 */       return null;
/*  57:    */     }
/*  58:163 */     return fields[persister.getVersionProperty()];
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static boolean isVersionIncrementRequired(int[] dirtyProperties, boolean hasDirtyCollections, boolean[] propertyVersionability)
/*  62:    */   {
/*  63:178 */     if (hasDirtyCollections) {
/*  64:179 */       return true;
/*  65:    */     }
/*  66:181 */     for (int i = 0; i < dirtyProperties.length; i++) {
/*  67:182 */       if (propertyVersionability[dirtyProperties[i]] != 0) {
/*  68:183 */         return true;
/*  69:    */       }
/*  70:    */     }
/*  71:186 */     return false;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.Versioning
 * JD-Core Version:    0.7.0.1
 */