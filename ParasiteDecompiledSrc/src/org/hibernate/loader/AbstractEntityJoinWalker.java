/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.FetchMode;
/*   9:    */ import org.hibernate.LockOptions;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.cfg.Settings;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ import org.hibernate.engine.profile.Fetch;
/*  14:    */ import org.hibernate.engine.profile.Fetch.Style;
/*  15:    */ import org.hibernate.engine.profile.FetchProfile;
/*  16:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  17:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.persister.entity.Loadable;
/*  20:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  21:    */ import org.hibernate.sql.JoinFragment;
/*  22:    */ import org.hibernate.sql.Select;
/*  23:    */ import org.hibernate.type.AssociationType;
/*  24:    */ 
/*  25:    */ public abstract class AbstractEntityJoinWalker
/*  26:    */   extends JoinWalker
/*  27:    */ {
/*  28:    */   private final OuterJoinLoadable persister;
/*  29:    */   private final String alias;
/*  30:    */   
/*  31:    */   public AbstractEntityJoinWalker(OuterJoinLoadable persister, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  32:    */   {
/*  33: 60 */     this(persister, factory, loadQueryInfluencers, null);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AbstractEntityJoinWalker(OuterJoinLoadable persister, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers, String alias)
/*  37:    */   {
/*  38: 68 */     super(factory, loadQueryInfluencers);
/*  39: 69 */     this.persister = persister;
/*  40: 70 */     this.alias = (alias == null ? generateRootAlias(persister.getEntityName()) : alias);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected final void initAll(String whereString, String orderByString, LockOptions lockOptions)
/*  44:    */     throws MappingException
/*  45:    */   {
/*  46: 77 */     initAll(whereString, orderByString, lockOptions, JoinWalker.AssociationInitCallback.NO_CALLBACK);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected final void initAll(String whereString, String orderByString, LockOptions lockOptions, JoinWalker.AssociationInitCallback callback)
/*  50:    */     throws MappingException
/*  51:    */   {
/*  52: 85 */     walkEntityTree(this.persister, getAlias());
/*  53: 86 */     List allAssociations = new ArrayList();
/*  54: 87 */     allAssociations.addAll(this.associations);
/*  55: 88 */     allAssociations.add(OuterJoinableAssociation.createRoot(this.persister.getEntityType(), this.alias, getFactory()));
/*  56: 89 */     initPersisters(allAssociations, lockOptions, callback);
/*  57: 90 */     initStatementString(whereString, orderByString, lockOptions);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected final void initProjection(String projectionString, String whereString, String orderByString, String groupByString, LockOptions lockOptions)
/*  61:    */     throws MappingException
/*  62:    */   {
/*  63: 99 */     walkEntityTree(this.persister, getAlias());
/*  64:100 */     this.persisters = new Loadable[0];
/*  65:101 */     initStatementString(projectionString, whereString, orderByString, groupByString, lockOptions);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void initStatementString(String condition, String orderBy, LockOptions lockOptions)
/*  69:    */     throws MappingException
/*  70:    */   {
/*  71:108 */     initStatementString(null, condition, orderBy, "", lockOptions);
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void initStatementString(String projection, String condition, String orderBy, String groupBy, LockOptions lockOptions)
/*  75:    */     throws MappingException
/*  76:    */   {
/*  77:118 */     int joins = countEntityPersisters(this.associations);
/*  78:119 */     this.suffixes = BasicLoader.generateSuffixes(joins + 1);
/*  79:    */     
/*  80:121 */     JoinFragment ojf = mergeOuterJoins(this.associations);
/*  81:    */     
/*  82:123 */     Select select = new Select(getDialect()).setLockOptions(lockOptions).setSelectClause(projection == null ? this.persister.selectFragment(this.alias, this.suffixes[joins]) + selectString(this.associations) : projection).setFromClause(getDialect().appendLockHint(lockOptions.getLockMode(), this.persister.fromTableFragment(this.alias)) + this.persister.fromJoinFragment(this.alias, true, true)).setWhereClause(condition).setOuterJoins(ojf.toFromFragmentString(), ojf.toWhereFragmentString() + getWhereFragment()).setOrderByClause(orderBy(this.associations, orderBy)).setGroupByClause(groupBy);
/*  83:142 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  84:143 */       select.setComment(getComment());
/*  85:    */     }
/*  86:145 */     this.sql = select.toStatementString();
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected String getWhereFragment()
/*  90:    */     throws MappingException
/*  91:    */   {
/*  92:150 */     return this.persister.whereJoinFragment(this.alias, true, true);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected boolean isJoinedFetchEnabled(AssociationType type, FetchMode config, CascadeStyle cascadeStyle)
/*  96:    */   {
/*  97:157 */     return isJoinedFetchEnabledInMapping(config, type);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected final boolean isJoinFetchEnabledByProfile(OuterJoinLoadable persister, PropertyPath path, int propertyNumber)
/* 101:    */   {
/* 102:161 */     if (!getLoadQueryInfluencers().hasEnabledFetchProfiles()) {
/* 103:163 */       return false;
/* 104:    */     }
/* 105:167 */     String fullPath = path.getFullPath();
/* 106:168 */     String rootPropertyName = persister.getSubclassPropertyName(propertyNumber);
/* 107:169 */     int pos = fullPath.lastIndexOf(rootPropertyName);
/* 108:170 */     String relativePropertyPath = pos >= 0 ? fullPath.substring(pos) : rootPropertyName;
/* 109:    */     
/* 110:    */ 
/* 111:173 */     String fetchRole = persister.getEntityName() + "." + relativePropertyPath;
/* 112:    */     
/* 113:175 */     Iterator profiles = getLoadQueryInfluencers().getEnabledFetchProfileNames().iterator();
/* 114:176 */     while (profiles.hasNext())
/* 115:    */     {
/* 116:177 */       String profileName = (String)profiles.next();
/* 117:178 */       FetchProfile profile = getFactory().getFetchProfile(profileName);
/* 118:179 */       Fetch fetch = profile.getFetchByRole(fetchRole);
/* 119:180 */       if ((fetch != null) && (Fetch.Style.JOIN == fetch.getStyle())) {
/* 120:181 */         return true;
/* 121:    */       }
/* 122:    */     }
/* 123:184 */     return false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public abstract String getComment();
/* 127:    */   
/* 128:    */   protected boolean isDuplicateAssociation(String foreignKeyTable, String[] foreignKeyColumns)
/* 129:    */   {
/* 130:195 */     boolean isSameJoin = (this.persister.getTableName().equals(foreignKeyTable)) && (Arrays.equals(foreignKeyColumns, this.persister.getKeyColumnNames()));
/* 131:    */     
/* 132:    */ 
/* 133:198 */     return (isSameJoin) || (super.isDuplicateAssociation(foreignKeyTable, foreignKeyColumns));
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected final Loadable getPersister()
/* 137:    */   {
/* 138:205 */     return this.persister;
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected final String getAlias()
/* 142:    */   {
/* 143:209 */     return this.alias;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String toString()
/* 147:    */   {
/* 148:213 */     return getClass().getName() + '(' + getPersister().getEntityName() + ')';
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.AbstractEntityJoinWalker
 * JD-Core Version:    0.7.0.1
 */