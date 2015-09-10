/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.RecognitionException;
/*   4:    */ import antlr.SemanticException;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import org.hibernate.QueryException;
/*  11:    */ import org.hibernate.engine.internal.JoinSequence;
/*  12:    */ import org.hibernate.engine.spi.QueryParameters;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.hql.internal.ast.SqlGenerator;
/*  15:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  16:    */ import org.hibernate.internal.CoreMessageLogger;
/*  17:    */ import org.hibernate.param.ParameterSpecification;
/*  18:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  19:    */ import org.hibernate.type.CollectionType;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public class IndexNode
/*  24:    */   extends FromReferenceNode
/*  25:    */ {
/*  26: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IndexNode.class.getName());
/*  27:    */   
/*  28:    */   public void setScalarColumnText(int i)
/*  29:    */     throws SemanticException
/*  30:    */   {
/*  31: 58 */     throw new UnsupportedOperationException("An IndexNode cannot generate column text!");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void prepareForDot(String propertyName)
/*  35:    */     throws SemanticException
/*  36:    */   {
/*  37: 63 */     FromElement fromElement = getFromElement();
/*  38: 64 */     if (fromElement == null) {
/*  39: 65 */       throw new IllegalStateException("No FROM element for index operator!");
/*  40:    */     }
/*  41: 67 */     QueryableCollection queryableCollection = fromElement.getQueryableCollection();
/*  42: 68 */     if ((queryableCollection != null) && (!queryableCollection.isOneToMany()))
/*  43:    */     {
/*  44: 70 */       FromReferenceNode collectionNode = (FromReferenceNode)getFirstChild();
/*  45: 71 */       String path = collectionNode.getPath() + "[]." + propertyName;
/*  46: 72 */       LOG.debugf("Creating join for many-to-many elements for %s", path);
/*  47: 73 */       FromElementFactory factory = new FromElementFactory(fromElement.getFromClause(), fromElement, path);
/*  48:    */       
/*  49: 75 */       FromElement elementJoin = factory.createElementJoin(queryableCollection);
/*  50: 76 */       setFromElement(elementJoin);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void resolveIndex(AST parent)
/*  55:    */     throws SemanticException
/*  56:    */   {
/*  57: 81 */     throw new UnsupportedOperationException();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias, AST parent)
/*  61:    */     throws SemanticException
/*  62:    */   {
/*  63: 86 */     if (isResolved()) {
/*  64: 87 */       return;
/*  65:    */     }
/*  66: 89 */     FromReferenceNode collectionNode = (FromReferenceNode)getFirstChild();
/*  67: 90 */     SessionFactoryHelper sessionFactoryHelper = getSessionFactoryHelper();
/*  68: 91 */     collectionNode.resolveIndex(this);
/*  69:    */     
/*  70: 93 */     Type type = collectionNode.getDataType();
/*  71: 94 */     if (!type.isCollectionType()) {
/*  72: 95 */       throw new SemanticException("The [] operator cannot be applied to type " + type.toString());
/*  73:    */     }
/*  74: 97 */     String collectionRole = ((CollectionType)type).getRole();
/*  75: 98 */     QueryableCollection queryableCollection = sessionFactoryHelper.requireQueryableCollection(collectionRole);
/*  76: 99 */     if (!queryableCollection.hasIndex()) {
/*  77:100 */       throw new QueryException("unindexed fromElement before []: " + collectionNode.getPath());
/*  78:    */     }
/*  79:104 */     FromElement fromElement = collectionNode.getFromElement();
/*  80:105 */     String elementTable = fromElement.getTableAlias();
/*  81:106 */     FromClause fromClause = fromElement.getFromClause();
/*  82:107 */     String path = collectionNode.getPath();
/*  83:    */     
/*  84:109 */     FromElement elem = fromClause.findCollectionJoin(path);
/*  85:110 */     if (elem == null)
/*  86:    */     {
/*  87:111 */       FromElementFactory factory = new FromElementFactory(fromClause, fromElement, path);
/*  88:112 */       elem = factory.createCollectionElementsJoin(queryableCollection, elementTable);
/*  89:113 */       LOG.debugf("No FROM element found for the elements of collection join path %s, created %s", path, elem);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:116 */       LOG.debugf("FROM element found for collection join path %s", path);
/*  94:    */     }
/*  95:120 */     setFromElement(fromElement);
/*  96:    */     
/*  97:    */ 
/*  98:123 */     AST selector = collectionNode.getNextSibling();
/*  99:124 */     if (selector == null) {
/* 100:125 */       throw new QueryException("No index value!");
/* 101:    */     }
/* 102:129 */     String collectionTableAlias = elementTable;
/* 103:130 */     if (elem.getCollectionTableAlias() != null) {
/* 104:131 */       collectionTableAlias = elem.getCollectionTableAlias();
/* 105:    */     }
/* 106:136 */     JoinSequence joinSequence = fromElement.getJoinSequence();
/* 107:137 */     String[] indexCols = queryableCollection.getIndexColumnNames();
/* 108:138 */     if (indexCols.length != 1) {
/* 109:139 */       throw new QueryException("composite-index appears in []: " + collectionNode.getPath());
/* 110:    */     }
/* 111:141 */     SqlGenerator gen = new SqlGenerator(getSessionFactoryHelper().getFactory());
/* 112:    */     try
/* 113:    */     {
/* 114:143 */       gen.simpleExpr(selector);
/* 115:    */     }
/* 116:    */     catch (RecognitionException e)
/* 117:    */     {
/* 118:146 */       throw new QueryException(e.getMessage(), e);
/* 119:    */     }
/* 120:148 */     String selectorExpression = gen.getSQL();
/* 121:149 */     joinSequence.addCondition(collectionTableAlias + '.' + indexCols[0] + " = " + selectorExpression);
/* 122:150 */     List paramSpecs = gen.getCollectedParameters();
/* 123:151 */     if (paramSpecs != null) {
/* 124:152 */       switch (paramSpecs.size())
/* 125:    */       {
/* 126:    */       case 0: 
/* 127:    */         break;
/* 128:    */       case 1: 
/* 129:157 */         ParameterSpecification paramSpec = (ParameterSpecification)paramSpecs.get(0);
/* 130:158 */         paramSpec.setExpectedType(queryableCollection.getIndexType());
/* 131:159 */         fromElement.setIndexCollectionSelectorParamSpec(paramSpec);
/* 132:160 */         break;
/* 133:    */       default: 
/* 134:162 */         fromElement.setIndexCollectionSelectorParamSpec(new AggregatedIndexCollectionSelectorParameterSpecifications(paramSpecs));
/* 135:    */       }
/* 136:    */     }
/* 137:170 */     String[] elementColumns = queryableCollection.getElementColumnNames(elementTable);
/* 138:171 */     setText(elementColumns[0]);
/* 139:172 */     setResolved();
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static class AggregatedIndexCollectionSelectorParameterSpecifications
/* 143:    */     implements ParameterSpecification
/* 144:    */   {
/* 145:    */     private final List paramSpecs;
/* 146:    */     
/* 147:    */     public AggregatedIndexCollectionSelectorParameterSpecifications(List paramSpecs)
/* 148:    */     {
/* 149:182 */       this.paramSpecs = paramSpecs;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public int bind(PreparedStatement statement, QueryParameters qp, SessionImplementor session, int position)
/* 153:    */       throws SQLException
/* 154:    */     {
/* 155:187 */       int bindCount = 0;
/* 156:188 */       Iterator itr = this.paramSpecs.iterator();
/* 157:189 */       while (itr.hasNext())
/* 158:    */       {
/* 159:190 */         ParameterSpecification paramSpec = (ParameterSpecification)itr.next();
/* 160:191 */         bindCount += paramSpec.bind(statement, qp, session, position + bindCount);
/* 161:    */       }
/* 162:193 */       return bindCount;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public Type getExpectedType()
/* 166:    */     {
/* 167:197 */       return null;
/* 168:    */     }
/* 169:    */     
/* 170:    */     public void setExpectedType(Type expectedType) {}
/* 171:    */     
/* 172:    */     public String renderDisplayInfo()
/* 173:    */     {
/* 174:204 */       return "index-selector [" + collectDisplayInfo() + "]";
/* 175:    */     }
/* 176:    */     
/* 177:    */     private String collectDisplayInfo()
/* 178:    */     {
/* 179:208 */       StringBuffer buffer = new StringBuffer();
/* 180:209 */       Iterator itr = this.paramSpecs.iterator();
/* 181:210 */       while (itr.hasNext()) {
/* 182:211 */         buffer.append(((ParameterSpecification)itr.next()).renderDisplayInfo());
/* 183:    */       }
/* 184:213 */       return buffer.toString();
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.IndexNode
 * JD-Core Version:    0.7.0.1
 */