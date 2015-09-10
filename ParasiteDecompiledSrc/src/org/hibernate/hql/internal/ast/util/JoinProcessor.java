/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.ListIterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ import org.hibernate.AssertionFailure;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.engine.internal.JoinSequence;
/*  13:    */ import org.hibernate.engine.internal.JoinSequence.Selector;
/*  14:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  15:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*  18:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.DotNode;
/*  20:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  21:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  22:    */ import org.hibernate.hql.internal.ast.tree.ParameterContainer;
/*  23:    */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.FilterImpl;
/*  26:    */ import org.hibernate.internal.util.StringHelper;
/*  27:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  28:    */ import org.hibernate.param.DynamicFilterParameterSpecification;
/*  29:    */ import org.hibernate.sql.JoinFragment;
/*  30:    */ import org.hibernate.sql.JoinType;
/*  31:    */ import org.hibernate.type.Type;
/*  32:    */ import org.jboss.logging.Logger;
/*  33:    */ 
/*  34:    */ public class JoinProcessor
/*  35:    */   implements SqlTokenTypes
/*  36:    */ {
/*  37: 66 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JoinProcessor.class.getName());
/*  38:    */   private final HqlSqlWalker walker;
/*  39:    */   private final SyntheticAndFactory syntheticAndFactory;
/*  40:    */   
/*  41:    */   public JoinProcessor(HqlSqlWalker walker)
/*  42:    */   {
/*  43: 77 */     this.walker = walker;
/*  44: 78 */     this.syntheticAndFactory = new SyntheticAndFactory(walker);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static JoinType toHibernateJoinType(int astJoinType)
/*  48:    */   {
/*  49: 90 */     switch (astJoinType)
/*  50:    */     {
/*  51:    */     case 138: 
/*  52: 92 */       return JoinType.LEFT_OUTER_JOIN;
/*  53:    */     case 28: 
/*  54: 94 */       return JoinType.INNER_JOIN;
/*  55:    */     case 139: 
/*  56: 96 */       return JoinType.RIGHT_OUTER_JOIN;
/*  57:    */     }
/*  58: 98 */     throw new AssertionFailure("undefined join type " + astJoinType);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void processJoins(QueryNode query)
/*  62:    */   {
/*  63:103 */     final FromClause fromClause = query.getFromClause();
/*  64:    */     List fromElements;
/*  65:106 */     if (DotNode.useThetaStyleImplicitJoins)
/*  66:    */     {
/*  67:113 */       List fromElements = new ArrayList();
/*  68:114 */       ListIterator liter = fromClause.getFromElements().listIterator(fromClause.getFromElements().size());
/*  69:115 */       while (liter.hasPrevious()) {
/*  70:116 */         fromElements.add(liter.previous());
/*  71:    */       }
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:120 */       fromElements = fromClause.getFromElements();
/*  76:    */     }
/*  77:124 */     Iterator iter = fromElements.iterator();
/*  78:125 */     while (iter.hasNext())
/*  79:    */     {
/*  80:126 */       final FromElement fromElement = (FromElement)iter.next();
/*  81:127 */       JoinSequence join = fromElement.getJoinSequence();
/*  82:128 */       join.setSelector(new JoinSequence.Selector()
/*  83:    */       {
/*  84:    */         public boolean includeSubclasses(String alias)
/*  85:    */         {
/*  86:134 */           boolean containsTableAlias = fromClause.containsTableAlias(alias);
/*  87:135 */           if (fromElement.isDereferencedBySubclassProperty())
/*  88:    */           {
/*  89:137 */             JoinProcessor.LOG.tracev("Forcing inclusion of extra joins [alias={0}, containsTableAlias={1}]", alias, Boolean.valueOf(containsTableAlias));
/*  90:138 */             return true;
/*  91:    */           }
/*  92:140 */           boolean shallowQuery = JoinProcessor.this.walker.isShallowQuery();
/*  93:141 */           boolean includeSubclasses = fromElement.isIncludeSubclasses();
/*  94:142 */           boolean subQuery = fromClause.isSubQuery();
/*  95:143 */           return (includeSubclasses) && (containsTableAlias) && (!subQuery) && (!shallowQuery);
/*  96:    */         }
/*  97:146 */       });
/*  98:147 */       addJoinNodes(query, join, fromElement);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void addJoinNodes(QueryNode query, JoinSequence join, FromElement fromElement)
/* 103:    */   {
/* 104:153 */     JoinFragment joinFragment = join.toJoinFragment(this.walker.getEnabledFilters(), (fromElement.useFromFragment()) || (fromElement.isDereferencedBySuperclassOrSubclassProperty()), fromElement.getWithClauseFragment(), fromElement.getWithClauseJoinAlias());
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:160 */     String frag = joinFragment.toFromFragmentString();
/* 112:161 */     String whereFrag = joinFragment.toWhereFragmentString();
/* 113:166 */     if ((fromElement.getType() == 136) && ((join.isThetaStyle()) || (StringHelper.isNotEmpty(whereFrag))))
/* 114:    */     {
/* 115:168 */       fromElement.setType(134);
/* 116:169 */       fromElement.getJoinSequence().setUseThetaStyle(true);
/* 117:    */     }
/* 118:173 */     if (fromElement.useFromFragment())
/* 119:    */     {
/* 120:174 */       String fromFragment = processFromFragment(frag, join).trim();
/* 121:175 */       LOG.debugf("Using FROM fragment [%s]", fromFragment);
/* 122:176 */       processDynamicFilterParameters(fromFragment, fromElement, this.walker);
/* 123:    */     }
/* 124:183 */     this.syntheticAndFactory.addWhereFragment(joinFragment, whereFrag, query, fromElement, this.walker);
/* 125:    */   }
/* 126:    */   
/* 127:    */   private String processFromFragment(String frag, JoinSequence join)
/* 128:    */   {
/* 129:193 */     String fromFragment = frag.trim();
/* 130:195 */     if (fromFragment.startsWith(", ")) {
/* 131:196 */       fromFragment = fromFragment.substring(2);
/* 132:    */     }
/* 133:198 */     return fromFragment;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void processDynamicFilterParameters(String sqlFragment, ParameterContainer container, HqlSqlWalker walker)
/* 137:    */   {
/* 138:205 */     if ((walker.getEnabledFilters().isEmpty()) && (!hasDynamicFilterParam(sqlFragment)) && (!hasCollectionFilterParam(sqlFragment))) {
/* 139:208 */       return;
/* 140:    */     }
/* 141:211 */     Dialect dialect = walker.getSessionFactoryHelper().getFactory().getDialect();
/* 142:212 */     String symbols = " \n\r\f\t,()=<>&|+-=/*'^![]#~\\" + dialect.openQuote() + dialect.closeQuote();
/* 143:    */     
/* 144:    */ 
/* 145:    */ 
/* 146:216 */     StringTokenizer tokens = new StringTokenizer(sqlFragment, symbols, true);
/* 147:217 */     StringBuffer result = new StringBuffer();
/* 148:219 */     while (tokens.hasMoreTokens())
/* 149:    */     {
/* 150:220 */       String token = tokens.nextToken();
/* 151:221 */       if (token.startsWith(":"))
/* 152:    */       {
/* 153:222 */         String filterParameterName = token.substring(1);
/* 154:223 */         String[] parts = LoadQueryInfluencers.parseFilterParameterName(filterParameterName);
/* 155:224 */         FilterImpl filter = (FilterImpl)walker.getEnabledFilters().get(parts[0]);
/* 156:225 */         Object value = filter.getParameter(parts[1]);
/* 157:226 */         Type type = filter.getFilterDefinition().getParameterType(parts[1]);
/* 158:227 */         String typeBindFragment = StringHelper.join(",", ArrayHelper.fillArray("?", type.getColumnSpan(walker.getSessionFactoryHelper().getFactory())));
/* 159:    */         
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:235 */         String bindFragment = (value != null) && (Collection.class.isInstance(value)) ? StringHelper.join(",", ArrayHelper.fillArray(typeBindFragment, ((Collection)value).size())) : typeBindFragment;
/* 167:    */         
/* 168:    */ 
/* 169:238 */         result.append(bindFragment);
/* 170:239 */         container.addEmbeddedParameter(new DynamicFilterParameterSpecification(parts[0], parts[1], type));
/* 171:    */       }
/* 172:    */       else
/* 173:    */       {
/* 174:242 */         result.append(token);
/* 175:    */       }
/* 176:    */     }
/* 177:246 */     container.setText(result.toString());
/* 178:    */   }
/* 179:    */   
/* 180:    */   private static boolean hasDynamicFilterParam(String sqlFragment)
/* 181:    */   {
/* 182:250 */     return sqlFragment.indexOf(":") < 0;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private static boolean hasCollectionFilterParam(String sqlFragment)
/* 186:    */   {
/* 187:254 */     return sqlFragment.indexOf("?") < 0;
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.JoinProcessor
 * JD-Core Version:    0.7.0.1
 */