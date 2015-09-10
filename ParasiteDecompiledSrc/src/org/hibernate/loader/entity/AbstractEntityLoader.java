/*   1:    */ package org.hibernate.loader.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.LockOptions;
/*   9:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.loader.OuterJoinLoader;
/*  13:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  14:    */ import org.hibernate.transform.ResultTransformer;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public abstract class AbstractEntityLoader
/*  18:    */   extends OuterJoinLoader
/*  19:    */   implements UniqueEntityLoader
/*  20:    */ {
/*  21:    */   protected final OuterJoinLoadable persister;
/*  22:    */   protected final Type uniqueKeyType;
/*  23:    */   protected final String entityName;
/*  24:    */   
/*  25:    */   public AbstractEntityLoader(OuterJoinLoadable persister, Type uniqueKeyType, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  26:    */   {
/*  27: 53 */     super(factory, loadQueryInfluencers);
/*  28: 54 */     this.uniqueKeyType = uniqueKeyType;
/*  29: 55 */     this.entityName = persister.getEntityName();
/*  30: 56 */     this.persister = persister;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object load(Serializable id, Object optionalObject, SessionImplementor session)
/*  34:    */   {
/*  35: 65 */     return load(id, optionalObject, session, LockOptions.NONE);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object load(Serializable id, Object optionalObject, SessionImplementor session, LockOptions lockOptions)
/*  39:    */   {
/*  40: 72 */     return load(session, id, optionalObject, id, lockOptions);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected Object load(SessionImplementor session, Object id, Object optionalObject, Serializable optionalId, LockOptions lockOptions)
/*  44:    */   {
/*  45: 82 */     List list = loadEntity(session, id, this.uniqueKeyType, optionalObject, this.entityName, optionalId, this.persister, lockOptions);
/*  46: 93 */     if (list.size() == 1) {
/*  47: 94 */       return list.get(0);
/*  48:    */     }
/*  49: 96 */     if (list.size() == 0) {
/*  50: 97 */       return null;
/*  51:    */     }
/*  52:100 */     if (getCollectionOwners() != null) {
/*  53:101 */       return list.get(0);
/*  54:    */     }
/*  55:104 */     throw new HibernateException("More than one row with the given identifier was found: " + id + ", for class: " + this.persister.getEntityName());
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/*  59:    */     throws SQLException, HibernateException
/*  60:    */   {
/*  61:118 */     return row[(row.length - 1)];
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected boolean isSingleRowLoader()
/*  65:    */   {
/*  66:123 */     return true;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.AbstractEntityLoader
 * JD-Core Version:    0.7.0.1
 */