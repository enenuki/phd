/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*   6:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   7:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*   8:    */ import org.hibernate.hql.internal.ast.tree.Node;
/*   9:    */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*  10:    */ import org.hibernate.hql.internal.ast.tree.RestrictableStatement;
/*  11:    */ import org.hibernate.hql.internal.ast.tree.SqlFragment;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.hibernate.param.CollectionFilterKeyParameterSpecification;
/*  15:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  16:    */ import org.hibernate.persister.entity.Queryable;
/*  17:    */ import org.hibernate.sql.JoinFragment;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class SyntheticAndFactory
/*  22:    */   implements HqlSqlTokenTypes
/*  23:    */ {
/*  24: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SyntheticAndFactory.class.getName());
/*  25:    */   private HqlSqlWalker hqlSqlWalker;
/*  26:    */   private AST thetaJoins;
/*  27:    */   private AST filters;
/*  28:    */   
/*  29:    */   public SyntheticAndFactory(HqlSqlWalker hqlSqlWalker)
/*  30:    */   {
/*  31: 60 */     this.hqlSqlWalker = hqlSqlWalker;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private Node create(int tokenType, String text)
/*  35:    */   {
/*  36: 64 */     return (Node)ASTUtil.create(this.hqlSqlWalker.getASTFactory(), tokenType, text);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addWhereFragment(JoinFragment joinFragment, String whereFragment, QueryNode query, FromElement fromElement, HqlSqlWalker hqlSqlWalker)
/*  40:    */   {
/*  41: 73 */     if (whereFragment == null) {
/*  42: 74 */       return;
/*  43:    */     }
/*  44: 77 */     if ((!fromElement.useWhereFragment()) && (!joinFragment.hasThetaJoins())) {
/*  45: 78 */       return;
/*  46:    */     }
/*  47: 81 */     whereFragment = whereFragment.trim();
/*  48: 82 */     if (StringHelper.isEmpty(whereFragment)) {
/*  49: 83 */       return;
/*  50:    */     }
/*  51: 88 */     if (whereFragment.startsWith("and")) {
/*  52: 89 */       whereFragment = whereFragment.substring(4);
/*  53:    */     }
/*  54: 92 */     LOG.debugf("Using unprocessed WHERE-fragment [%s]", whereFragment);
/*  55:    */     
/*  56: 94 */     SqlFragment fragment = (SqlFragment)create(142, whereFragment);
/*  57: 95 */     fragment.setJoinFragment(joinFragment);
/*  58: 96 */     fragment.setFromElement(fromElement);
/*  59: 98 */     if (fromElement.getIndexCollectionSelectorParamSpec() != null)
/*  60:    */     {
/*  61: 99 */       fragment.addEmbeddedParameter(fromElement.getIndexCollectionSelectorParamSpec());
/*  62:100 */       fromElement.setIndexCollectionSelectorParamSpec(null);
/*  63:    */     }
/*  64:103 */     if ((hqlSqlWalker.isFilter()) && 
/*  65:104 */       (whereFragment.indexOf('?') >= 0))
/*  66:    */     {
/*  67:105 */       Type collectionFilterKeyType = hqlSqlWalker.getSessionFactoryHelper().requireQueryableCollection(hqlSqlWalker.getCollectionFilterRole()).getKeyType();
/*  68:    */       
/*  69:    */ 
/*  70:108 */       CollectionFilterKeyParameterSpecification paramSpec = new CollectionFilterKeyParameterSpecification(hqlSqlWalker.getCollectionFilterRole(), collectionFilterKeyType, 0);
/*  71:    */       
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:113 */       fragment.addEmbeddedParameter(paramSpec);
/*  76:    */     }
/*  77:117 */     JoinProcessor.processDynamicFilterParameters(whereFragment, fragment, hqlSqlWalker);
/*  78:123 */     if (LOG.isDebugEnabled()) {
/*  79:124 */       LOG.debugf("Using processed WHERE-fragment [%s]", fragment.getText());
/*  80:    */     }
/*  81:130 */     if ((fragment.getFromElement().isFilter()) || (fragment.hasFilterCondition()))
/*  82:    */     {
/*  83:131 */       if (this.filters == null)
/*  84:    */       {
/*  85:133 */         AST where = query.getWhereClause();
/*  86:    */         
/*  87:135 */         this.filters = create(146, "{filter conditions}");
/*  88:    */         
/*  89:137 */         ASTUtil.insertChild(where, this.filters);
/*  90:    */       }
/*  91:141 */       this.filters.addChild(fragment);
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95:144 */       if (this.thetaJoins == null)
/*  96:    */       {
/*  97:146 */         AST where = query.getWhereClause();
/*  98:    */         
/*  99:148 */         this.thetaJoins = create(145, "{theta joins}");
/* 100:150 */         if (this.filters == null) {
/* 101:151 */           ASTUtil.insertChild(where, this.thetaJoins);
/* 102:    */         } else {
/* 103:154 */           ASTUtil.insertSibling(this.thetaJoins, this.filters);
/* 104:    */         }
/* 105:    */       }
/* 106:159 */       this.thetaJoins.addChild(fragment);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addDiscriminatorWhereFragment(RestrictableStatement statement, Queryable persister, Map enabledFilters, String alias)
/* 111:    */   {
/* 112:169 */     String whereFragment = persister.filterFragment(alias, enabledFilters).trim();
/* 113:170 */     if ("".equals(whereFragment)) {
/* 114:171 */       return;
/* 115:    */     }
/* 116:173 */     if (whereFragment.startsWith("and")) {
/* 117:174 */       whereFragment = whereFragment.substring(4);
/* 118:    */     }
/* 119:179 */     whereFragment = StringHelper.replace(whereFragment, persister.generateFilterConditionAlias(alias) + ".", "");
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:188 */     SqlFragment discrimNode = (SqlFragment)create(142, whereFragment);
/* 129:    */     
/* 130:190 */     JoinProcessor.processDynamicFilterParameters(whereFragment, discrimNode, this.hqlSqlWalker);
/* 131:196 */     if (statement.getWhereClause().getNumberOfChildren() == 0)
/* 132:    */     {
/* 133:197 */       statement.getWhereClause().setFirstChild(discrimNode);
/* 134:    */     }
/* 135:    */     else
/* 136:    */     {
/* 137:200 */       AST and = create(6, "{and}");
/* 138:201 */       AST currentFirstChild = statement.getWhereClause().getFirstChild();
/* 139:202 */       and.setFirstChild(discrimNode);
/* 140:203 */       and.addChild(currentFirstChild);
/* 141:204 */       statement.getWhereClause().setFirstChild(and);
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.SyntheticAndFactory
 * JD-Core Version:    0.7.0.1
 */