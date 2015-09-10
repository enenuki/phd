/*  1:   */ package org.hibernate.engine.loading.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  6:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  7:   */ import org.hibernate.pretty.MessageHelper;
/*  8:   */ 
/*  9:   */ public class LoadingCollectionEntry
/* 10:   */ {
/* 11:   */   private final ResultSet resultSet;
/* 12:   */   private final CollectionPersister persister;
/* 13:   */   private final Serializable key;
/* 14:   */   private final PersistentCollection collection;
/* 15:   */   
/* 16:   */   public LoadingCollectionEntry(ResultSet resultSet, CollectionPersister persister, Serializable key, PersistentCollection collection)
/* 17:   */   {
/* 18:49 */     this.resultSet = resultSet;
/* 19:50 */     this.persister = persister;
/* 20:51 */     this.key = key;
/* 21:52 */     this.collection = collection;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ResultSet getResultSet()
/* 25:   */   {
/* 26:56 */     return this.resultSet;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CollectionPersister getPersister()
/* 30:   */   {
/* 31:60 */     return this.persister;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Serializable getKey()
/* 35:   */   {
/* 36:64 */     return this.key;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public PersistentCollection getCollection()
/* 40:   */   {
/* 41:68 */     return this.collection;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String toString()
/* 45:   */   {
/* 46:72 */     return getClass().getName() + "<rs=" + this.resultSet + ", coll=" + MessageHelper.collectionInfoString(this.persister.getRole(), this.key) + ">@" + Integer.toHexString(hashCode());
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.loading.internal.LoadingCollectionEntry
 * JD-Core Version:    0.7.0.1
 */