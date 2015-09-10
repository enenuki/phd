/*  1:   */ package org.hibernate.loader.entity;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.engine.spi.CascadingAction;
/*  5:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.internal.CoreMessageLogger;
/*  8:   */ import org.hibernate.loader.JoinWalker;
/*  9:   */ import org.hibernate.persister.entity.OuterJoinLoadable;
/* 10:   */ 
/* 11:   */ public class CascadeEntityLoader
/* 12:   */   extends AbstractEntityLoader
/* 13:   */ {
/* 14:   */   public CascadeEntityLoader(OuterJoinLoadable persister, CascadingAction action, SessionFactoryImplementor factory)
/* 15:   */     throws MappingException
/* 16:   */   {
/* 17:39 */     super(persister, persister.getIdentifierType(), factory, LoadQueryInfluencers.NONE);
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:   */ 
/* 23:   */ 
/* 24:46 */     JoinWalker walker = new CascadeEntityJoinWalker(persister, action, factory);
/* 25:   */     
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:51 */     initFromWalker(walker);
/* 30:   */     
/* 31:53 */     postInstantiate();
/* 32:55 */     if (LOG.isDebugEnabled()) {
/* 33:56 */       LOG.debugf("Static select for action %s on entity %s: %s", action, this.entityName, getSQLString());
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.CascadeEntityLoader
 * JD-Core Version:    0.7.0.1
 */