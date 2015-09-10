/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.MappingException;
/*  7:   */ import org.hibernate.internal.CoreMessageLogger;
/*  8:   */ import org.hibernate.mapping.Collection;
/*  9:   */ import org.hibernate.mapping.IndexedCollection;
/* 10:   */ import org.hibernate.mapping.OneToMany;
/* 11:   */ import org.hibernate.mapping.Selectable;
/* 12:   */ import org.hibernate.mapping.Value;
/* 13:   */ import org.jboss.logging.Logger;
/* 14:   */ 
/* 15:   */ public abstract class CollectionSecondPass
/* 16:   */   implements SecondPass
/* 17:   */ {
/* 18:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CollectionSecondPass.class.getName());
/* 19:   */   Mappings mappings;
/* 20:   */   Collection collection;
/* 21:   */   private Map localInheritedMetas;
/* 22:   */   
/* 23:   */   public CollectionSecondPass(Mappings mappings, Collection collection, Map inheritedMetas)
/* 24:   */   {
/* 25:53 */     this.collection = collection;
/* 26:54 */     this.mappings = mappings;
/* 27:55 */     this.localInheritedMetas = inheritedMetas;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public CollectionSecondPass(Mappings mappings, Collection collection)
/* 31:   */   {
/* 32:59 */     this(mappings, collection, Collections.EMPTY_MAP);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void doSecondPass(Map persistentClasses)
/* 36:   */     throws MappingException
/* 37:   */   {
/* 38:64 */     LOG.debugf("Second pass for collection: %s", this.collection.getRole());
/* 39:   */     
/* 40:66 */     secondPass(persistentClasses, this.localInheritedMetas);
/* 41:67 */     this.collection.createAllKeys();
/* 42:69 */     if (LOG.isDebugEnabled())
/* 43:   */     {
/* 44:70 */       String msg = "Mapped collection key: " + columns(this.collection.getKey());
/* 45:71 */       if (this.collection.isIndexed()) {
/* 46:72 */         msg = msg + ", index: " + columns(((IndexedCollection)this.collection).getIndex());
/* 47:   */       }
/* 48:73 */       if (this.collection.isOneToMany()) {
/* 49:74 */         msg = msg + ", one-to-many: " + ((OneToMany)this.collection.getElement()).getReferencedEntityName();
/* 50:   */       } else {
/* 51:78 */         msg = msg + ", element: " + columns(this.collection.getElement());
/* 52:   */       }
/* 53:80 */       LOG.debug(msg);
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public abstract void secondPass(Map paramMap1, Map paramMap2)
/* 58:   */     throws MappingException;
/* 59:   */   
/* 60:   */   private static String columns(Value val)
/* 61:   */   {
/* 62:88 */     StringBuffer columns = new StringBuffer();
/* 63:89 */     Iterator iter = val.getColumnIterator();
/* 64:90 */     while (iter.hasNext())
/* 65:   */     {
/* 66:91 */       columns.append(((Selectable)iter.next()).getText());
/* 67:92 */       if (iter.hasNext()) {
/* 68:92 */         columns.append(", ");
/* 69:   */       }
/* 70:   */     }
/* 71:94 */     return columns.toString();
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.CollectionSecondPass
 * JD-Core Version:    0.7.0.1
 */