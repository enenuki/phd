/*  1:   */ package org.hibernate.loader.entity;
/*  2:   */ 
/*  3:   */ import org.hibernate.FetchMode;
/*  4:   */ import org.hibernate.LockOptions;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.engine.spi.CascadeStyle;
/*  7:   */ import org.hibernate.engine.spi.CascadingAction;
/*  8:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  9:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 10:   */ import org.hibernate.internal.util.collections.CollectionHelper;
/* 11:   */ import org.hibernate.loader.AbstractEntityJoinWalker;
/* 12:   */ import org.hibernate.persister.entity.Loadable;
/* 13:   */ import org.hibernate.persister.entity.OuterJoinLoadable;
/* 14:   */ import org.hibernate.type.AssociationType;
/* 15:   */ 
/* 16:   */ public class CascadeEntityJoinWalker
/* 17:   */   extends AbstractEntityJoinWalker
/* 18:   */ {
/* 19:   */   private final CascadingAction cascadeAction;
/* 20:   */   
/* 21:   */   public CascadeEntityJoinWalker(OuterJoinLoadable persister, CascadingAction action, SessionFactoryImplementor factory)
/* 22:   */     throws MappingException
/* 23:   */   {
/* 24:44 */     super(persister, factory, LoadQueryInfluencers.NONE);
/* 25:45 */     this.cascadeAction = action;
/* 26:46 */     StringBuffer whereCondition = whereString(getAlias(), persister.getIdentifierColumnNames(), 1).append(persister.filterFragment(getAlias(), CollectionHelper.EMPTY_MAP));
/* 27:   */     
/* 28:   */ 
/* 29:   */ 
/* 30:50 */     initAll(whereCondition.toString(), "", LockOptions.READ);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected boolean isJoinedFetchEnabled(AssociationType type, FetchMode config, CascadeStyle cascadeStyle)
/* 34:   */   {
/* 35:55 */     return ((type.isEntityType()) || (type.isCollectionType())) && ((cascadeStyle == null) || (cascadeStyle.doCascade(this.cascadeAction)));
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected boolean isTooManyCollections()
/* 39:   */   {
/* 40:61 */     return countCollectionPersisters(this.associations) > 0;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getComment()
/* 44:   */   {
/* 45:66 */     return "load " + getPersister().getEntityName();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.CascadeEntityJoinWalker
 * JD-Core Version:    0.7.0.1
 */