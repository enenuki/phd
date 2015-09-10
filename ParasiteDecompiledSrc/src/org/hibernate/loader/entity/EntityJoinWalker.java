/*   1:    */ package org.hibernate.loader.entity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.hibernate.FetchMode;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.LockOptions;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  12:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.loader.AbstractEntityJoinWalker;
/*  15:    */ import org.hibernate.loader.JoinWalker.AssociationInitCallback;
/*  16:    */ import org.hibernate.loader.OuterJoinableAssociation;
/*  17:    */ import org.hibernate.loader.PropertyPath;
/*  18:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  19:    */ import org.hibernate.persister.entity.EntityPersister;
/*  20:    */ import org.hibernate.persister.entity.Loadable;
/*  21:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  22:    */ import org.hibernate.sql.JoinType;
/*  23:    */ import org.hibernate.tuple.IdentifierProperty;
/*  24:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  25:    */ import org.hibernate.type.AssociationType;
/*  26:    */ import org.hibernate.type.CompositeType;
/*  27:    */ import org.hibernate.type.EntityType;
/*  28:    */ import org.hibernate.type.Type;
/*  29:    */ 
/*  30:    */ public class EntityJoinWalker
/*  31:    */   extends AbstractEntityJoinWalker
/*  32:    */ {
/*  33: 56 */   private final LockOptions lockOptions = new LockOptions();
/*  34:    */   private final int[][] compositeKeyManyToOneTargetIndices;
/*  35:    */   
/*  36:    */   public EntityJoinWalker(OuterJoinLoadable persister, String[] uniqueKey, int batchSize, LockMode lockMode, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  37:    */     throws MappingException
/*  38:    */   {
/*  39: 66 */     super(persister, factory, loadQueryInfluencers);
/*  40:    */     
/*  41: 68 */     this.lockOptions.setLockMode(lockMode);
/*  42:    */     
/*  43: 70 */     StringBuffer whereCondition = whereString(getAlias(), uniqueKey, batchSize).append(persister.filterFragment(getAlias(), Collections.EMPTY_MAP));
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47: 74 */     AssociationInitCallbackImpl callback = new AssociationInitCallbackImpl(factory);
/*  48: 75 */     initAll(whereCondition.toString(), "", this.lockOptions, callback);
/*  49: 76 */     this.compositeKeyManyToOneTargetIndices = callback.resolve();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public EntityJoinWalker(OuterJoinLoadable persister, String[] uniqueKey, int batchSize, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  53:    */     throws MappingException
/*  54:    */   {
/*  55: 86 */     super(persister, factory, loadQueryInfluencers);
/*  56: 87 */     LockOptions.copy(lockOptions, this.lockOptions);
/*  57:    */     
/*  58: 89 */     StringBuffer whereCondition = whereString(getAlias(), uniqueKey, batchSize).append(persister.filterFragment(getAlias(), Collections.EMPTY_MAP));
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62: 93 */     AssociationInitCallbackImpl callback = new AssociationInitCallbackImpl(factory);
/*  63: 94 */     initAll(whereCondition.toString(), "", lockOptions, callback);
/*  64: 95 */     this.compositeKeyManyToOneTargetIndices = callback.resolve();
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected JoinType getJoinType(OuterJoinLoadable persister, PropertyPath path, int propertyNumber, AssociationType associationType, FetchMode metadataFetchMode, CascadeStyle metadataCascadeStyle, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth)
/*  68:    */     throws MappingException
/*  69:    */   {
/*  70:112 */     if (this.lockOptions.getLockMode().greaterThan(LockMode.READ)) {
/*  71:113 */       return JoinType.NONE;
/*  72:    */     }
/*  73:115 */     if ((isTooDeep(currentDepth)) || ((associationType.isCollectionType()) && (isTooManyCollections()))) {
/*  74:117 */       return JoinType.NONE;
/*  75:    */     }
/*  76:119 */     if ((!isJoinedFetchEnabledInMapping(metadataFetchMode, associationType)) && (!isJoinFetchEnabledByProfile(persister, path, propertyNumber))) {
/*  77:121 */       return JoinType.NONE;
/*  78:    */     }
/*  79:123 */     if (isDuplicateAssociation(lhsTable, lhsColumns, associationType)) {
/*  80:124 */       return JoinType.NONE;
/*  81:    */     }
/*  82:126 */     return getJoinType(nullable, currentDepth);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getComment()
/*  86:    */   {
/*  87:130 */     return "load " + getPersister().getEntityName();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int[][] getCompositeKeyManyToOneTargetIndices()
/*  91:    */   {
/*  92:134 */     return this.compositeKeyManyToOneTargetIndices;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private static class AssociationInitCallbackImpl
/*  96:    */     implements JoinWalker.AssociationInitCallback
/*  97:    */   {
/*  98:    */     private final SessionFactoryImplementor factory;
/*  99:139 */     private final HashMap<String, OuterJoinableAssociation> associationsByAlias = new HashMap();
/* 100:141 */     private final HashMap<String, Integer> positionsByAlias = new HashMap();
/* 101:142 */     private final ArrayList<String> aliasesForAssociationsWithCompositesIds = new ArrayList();
/* 102:    */     
/* 103:    */     public AssociationInitCallbackImpl(SessionFactoryImplementor factory)
/* 104:    */     {
/* 105:146 */       this.factory = factory;
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void associationProcessed(OuterJoinableAssociation oja, int position)
/* 109:    */     {
/* 110:150 */       this.associationsByAlias.put(oja.getRhsAlias(), oja);
/* 111:151 */       this.positionsByAlias.put(oja.getRhsAlias(), Integer.valueOf(position));
/* 112:152 */       EntityPersister entityPersister = null;
/* 113:153 */       if (oja.getJoinableType().isCollectionType()) {
/* 114:154 */         entityPersister = ((QueryableCollection)oja.getJoinable()).getElementPersister();
/* 115:156 */       } else if (oja.getJoinableType().isEntityType()) {
/* 116:157 */         entityPersister = (EntityPersister)oja.getJoinable();
/* 117:    */       }
/* 118:159 */       if ((entityPersister != null) && (entityPersister.getIdentifierType().isComponentType()) && (!entityPersister.getEntityMetamodel().getIdentifierProperty().isEmbedded()) && (hasAssociation((CompositeType)entityPersister.getIdentifierType()))) {
/* 119:163 */         this.aliasesForAssociationsWithCompositesIds.add(oja.getRhsAlias());
/* 120:    */       }
/* 121:    */     }
/* 122:    */     
/* 123:    */     private boolean hasAssociation(CompositeType componentType)
/* 124:    */     {
/* 125:168 */       for (Type subType : componentType.getSubtypes())
/* 126:    */       {
/* 127:169 */         if (subType.isEntityType()) {
/* 128:170 */           return true;
/* 129:    */         }
/* 130:172 */         if ((subType.isComponentType()) && (hasAssociation((CompositeType)subType))) {
/* 131:173 */           return true;
/* 132:    */         }
/* 133:    */       }
/* 134:176 */       return false;
/* 135:    */     }
/* 136:    */     
/* 137:    */     public int[][] resolve()
/* 138:    */     {
/* 139:180 */       int[][] compositeKeyManyToOneTargetIndices = (int[][])null;
/* 140:181 */       for (String aliasWithCompositeId : this.aliasesForAssociationsWithCompositesIds)
/* 141:    */       {
/* 142:182 */         OuterJoinableAssociation joinWithCompositeId = (OuterJoinableAssociation)this.associationsByAlias.get(aliasWithCompositeId);
/* 143:183 */         ArrayList<Integer> keyManyToOneTargetIndices = new ArrayList();
/* 144:    */         
/* 145:    */ 
/* 146:186 */         EntityPersister entityPersister = null;
/* 147:187 */         if (joinWithCompositeId.getJoinableType().isCollectionType()) {
/* 148:188 */           entityPersister = ((QueryableCollection)joinWithCompositeId.getJoinable()).getElementPersister();
/* 149:190 */         } else if (joinWithCompositeId.getJoinableType().isEntityType()) {
/* 150:191 */           entityPersister = (EntityPersister)joinWithCompositeId.getJoinable();
/* 151:    */         }
/* 152:194 */         findKeyManyToOneTargetIndices(keyManyToOneTargetIndices, joinWithCompositeId, (CompositeType)entityPersister.getIdentifierType());
/* 153:200 */         if (!keyManyToOneTargetIndices.isEmpty())
/* 154:    */         {
/* 155:201 */           if (compositeKeyManyToOneTargetIndices == null) {
/* 156:202 */             compositeKeyManyToOneTargetIndices = new int[this.associationsByAlias.size()][];
/* 157:    */           }
/* 158:204 */           position = ((Integer)this.positionsByAlias.get(aliasWithCompositeId)).intValue();
/* 159:205 */           compositeKeyManyToOneTargetIndices[position] = new int[keyManyToOneTargetIndices.size()];
/* 160:206 */           i = 0;
/* 161:207 */           for (i$ = keyManyToOneTargetIndices.iterator(); i$.hasNext();)
/* 162:    */           {
/* 163:207 */             int index = ((Integer)i$.next()).intValue();
/* 164:208 */             compositeKeyManyToOneTargetIndices[position][i] = index;
/* 165:209 */             i++;
/* 166:    */           }
/* 167:    */         }
/* 168:    */       }
/* 169:    */       int position;
/* 170:    */       int i;
/* 171:    */       Iterator i$;
/* 172:213 */       return compositeKeyManyToOneTargetIndices;
/* 173:    */     }
/* 174:    */     
/* 175:    */     private void findKeyManyToOneTargetIndices(ArrayList<Integer> keyManyToOneTargetIndices, OuterJoinableAssociation joinWithCompositeId, CompositeType componentType)
/* 176:    */     {
/* 177:220 */       for (Type subType : componentType.getSubtypes()) {
/* 178:221 */         if (subType.isEntityType())
/* 179:    */         {
/* 180:222 */           Integer index = locateKeyManyToOneTargetIndex(joinWithCompositeId, (EntityType)subType);
/* 181:223 */           if (index != null) {
/* 182:224 */             keyManyToOneTargetIndices.add(index);
/* 183:    */           }
/* 184:    */         }
/* 185:227 */         else if (subType.isComponentType())
/* 186:    */         {
/* 187:228 */           findKeyManyToOneTargetIndices(keyManyToOneTargetIndices, joinWithCompositeId, (CompositeType)subType);
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */     
/* 192:    */     private Integer locateKeyManyToOneTargetIndex(OuterJoinableAssociation joinWithCompositeId, EntityType keyManyToOneType)
/* 193:    */     {
/* 194:239 */       if (joinWithCompositeId.getLhsAlias() != null)
/* 195:    */       {
/* 196:240 */         OuterJoinableAssociation lhs = (OuterJoinableAssociation)this.associationsByAlias.get(joinWithCompositeId.getLhsAlias());
/* 197:241 */         if (keyManyToOneType.getAssociatedEntityName(this.factory).equals(lhs.getJoinableType().getAssociatedEntityName(this.factory))) {
/* 198:242 */           return (Integer)this.positionsByAlias.get(lhs.getRhsAlias());
/* 199:    */         }
/* 200:    */       }
/* 201:247 */       for (OuterJoinableAssociation oja : this.associationsByAlias.values()) {
/* 202:248 */         if ((oja.getLhsAlias() != null) && (oja.getLhsAlias().equals(joinWithCompositeId.getRhsAlias())) && 
/* 203:249 */           (keyManyToOneType.equals(oja.getJoinableType()))) {
/* 204:250 */           return (Integer)this.positionsByAlias.get(oja.getLhsAlias());
/* 205:    */         }
/* 206:    */       }
/* 207:254 */       return null;
/* 208:    */     }
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.EntityJoinWalker
 * JD-Core Version:    0.7.0.1
 */