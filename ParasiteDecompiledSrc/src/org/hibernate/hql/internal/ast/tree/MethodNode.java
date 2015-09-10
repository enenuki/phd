/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import org.hibernate.dialect.function.SQLFunction;
/*   7:    */ import org.hibernate.hql.internal.CollectionProperties;
/*   8:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   9:    */ import org.hibernate.hql.internal.ast.TypeDiscriminatorMetadata;
/*  10:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  11:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  12:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  15:    */ import org.hibernate.persister.entity.EntityPersister;
/*  16:    */ import org.hibernate.persister.entity.Queryable;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class MethodNode
/*  21:    */   extends AbstractSelectExpression
/*  22:    */   implements FunctionNode
/*  23:    */ {
/*  24: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MethodNode.class.getName());
/*  25:    */   private String methodName;
/*  26:    */   private FromElement fromElement;
/*  27:    */   private String[] selectColumns;
/*  28:    */   private SQLFunction function;
/*  29:    */   private boolean inSelect;
/*  30:    */   
/*  31:    */   public void resolve(boolean inSelect)
/*  32:    */     throws SemanticException
/*  33:    */   {
/*  34: 60 */     AST name = getFirstChild();
/*  35: 61 */     initializeMethodNode(name, inSelect);
/*  36: 62 */     AST exprList = name.getNextSibling();
/*  37: 65 */     if (ASTUtil.hasExactlyOneChild(exprList))
/*  38:    */     {
/*  39: 66 */       if ("type".equals(this.methodName))
/*  40:    */       {
/*  41: 67 */         typeDiscriminator(exprList.getFirstChild());
/*  42: 68 */         return;
/*  43:    */       }
/*  44: 70 */       if (isCollectionPropertyMethod())
/*  45:    */       {
/*  46: 71 */         collectionProperty(exprList.getFirstChild(), name);
/*  47: 72 */         return;
/*  48:    */       }
/*  49:    */     }
/*  50: 76 */     dialectFunction(exprList);
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void typeDiscriminator(AST path)
/*  54:    */     throws SemanticException
/*  55:    */   {
/*  56: 80 */     if (path == null) {
/*  57: 81 */       throw new SemanticException("type() discriminator reference has no path!");
/*  58:    */     }
/*  59: 84 */     FromReferenceNode pathAsFromReferenceNode = (FromReferenceNode)path;
/*  60: 85 */     FromElement fromElement = pathAsFromReferenceNode.getFromElement();
/*  61: 86 */     TypeDiscriminatorMetadata typeDiscriminatorMetadata = fromElement.getTypeDiscriminatorMetadata();
/*  62:    */     
/*  63: 88 */     setDataType(typeDiscriminatorMetadata.getResolutionType());
/*  64: 89 */     setText(typeDiscriminatorMetadata.getSqlFragment());
/*  65: 90 */     setType(142);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public SQLFunction getSQLFunction()
/*  69:    */   {
/*  70: 94 */     return this.function;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Type getFirstArgumentType()
/*  74:    */   {
/*  75: 98 */     AST argument = getFirstChild();
/*  76: 99 */     while (argument != null) {
/*  77:100 */       if ((argument instanceof SqlNode))
/*  78:    */       {
/*  79:101 */         Type type = ((SqlNode)argument).getDataType();
/*  80:102 */         if (type != null) {
/*  81:103 */           return type;
/*  82:    */         }
/*  83:105 */         argument = argument.getNextSibling();
/*  84:    */       }
/*  85:    */     }
/*  86:108 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void dialectFunction(AST exprList)
/*  90:    */   {
/*  91:112 */     this.function = getSessionFactoryHelper().findSQLFunction(this.methodName);
/*  92:113 */     if (this.function != null)
/*  93:    */     {
/*  94:114 */       AST firstChild = exprList != null ? exprList.getFirstChild() : null;
/*  95:115 */       Type functionReturnType = getSessionFactoryHelper().findFunctionReturnType(this.methodName, firstChild);
/*  96:    */       
/*  97:117 */       setDataType(functionReturnType);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isCollectionPropertyMethod()
/* 102:    */   {
/* 103:126 */     return CollectionProperties.isAnyCollectionProperty(this.methodName);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void initializeMethodNode(AST name, boolean inSelect)
/* 107:    */   {
/* 108:130 */     name.setType(147);
/* 109:131 */     String text = name.getText();
/* 110:132 */     this.methodName = text.toLowerCase();
/* 111:133 */     this.inSelect = inSelect;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private String getMethodName()
/* 115:    */   {
/* 116:137 */     return this.methodName;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void collectionProperty(AST path, AST name)
/* 120:    */     throws SemanticException
/* 121:    */   {
/* 122:141 */     if (path == null) {
/* 123:142 */       throw new SemanticException("Collection function " + name.getText() + " has no path!");
/* 124:    */     }
/* 125:145 */     SqlNode expr = (SqlNode)path;
/* 126:146 */     Type type = expr.getDataType();
/* 127:147 */     LOG.debugf("collectionProperty() :  name=%s type=%s", name, type);
/* 128:    */     
/* 129:149 */     resolveCollectionProperty(expr);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isScalar()
/* 133:    */     throws SemanticException
/* 134:    */   {
/* 135:155 */     return true;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void resolveCollectionProperty(AST expr)
/* 139:    */     throws SemanticException
/* 140:    */   {
/* 141:159 */     String propertyName = CollectionProperties.getNormalizedPropertyName(getMethodName());
/* 142:160 */     if ((expr instanceof FromReferenceNode))
/* 143:    */     {
/* 144:161 */       FromReferenceNode collectionNode = (FromReferenceNode)expr;
/* 145:163 */       if ("elements".equals(propertyName))
/* 146:    */       {
/* 147:164 */         handleElements(collectionNode, propertyName);
/* 148:    */       }
/* 149:    */       else
/* 150:    */       {
/* 151:168 */         this.fromElement = collectionNode.getFromElement();
/* 152:169 */         setDataType(this.fromElement.getPropertyType(propertyName, propertyName));
/* 153:170 */         this.selectColumns = this.fromElement.toColumns(this.fromElement.getTableAlias(), propertyName, this.inSelect);
/* 154:    */       }
/* 155:172 */       if ((collectionNode instanceof DotNode)) {
/* 156:173 */         prepareAnyImplicitJoins((DotNode)collectionNode);
/* 157:    */       }
/* 158:175 */       if (!this.inSelect)
/* 159:    */       {
/* 160:176 */         this.fromElement.setText("");
/* 161:177 */         this.fromElement.setUseWhereFragment(false);
/* 162:    */       }
/* 163:179 */       prepareSelectColumns(this.selectColumns);
/* 164:180 */       setText(this.selectColumns[0]);
/* 165:181 */       setType(142);
/* 166:    */     }
/* 167:    */     else
/* 168:    */     {
/* 169:184 */       throw new SemanticException("Unexpected expression " + expr + " found for collection function " + propertyName);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   private void prepareAnyImplicitJoins(DotNode dotNode)
/* 174:    */     throws SemanticException
/* 175:    */   {
/* 176:192 */     if ((dotNode.getLhs() instanceof DotNode))
/* 177:    */     {
/* 178:193 */       DotNode lhs = (DotNode)dotNode.getLhs();
/* 179:194 */       FromElement lhsOrigin = lhs.getFromElement();
/* 180:195 */       if ((lhsOrigin != null) && ("".equals(lhsOrigin.getText())))
/* 181:    */       {
/* 182:196 */         String lhsOriginText = lhsOrigin.getQueryable().getTableName() + " " + lhsOrigin.getTableAlias();
/* 183:    */         
/* 184:198 */         lhsOrigin.setText(lhsOriginText);
/* 185:    */       }
/* 186:200 */       prepareAnyImplicitJoins(lhs);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private void handleElements(FromReferenceNode collectionNode, String propertyName)
/* 191:    */   {
/* 192:205 */     FromElement collectionFromElement = collectionNode.getFromElement();
/* 193:206 */     QueryableCollection queryableCollection = collectionFromElement.getQueryableCollection();
/* 194:    */     
/* 195:208 */     String path = collectionNode.getPath() + "[]." + propertyName;
/* 196:209 */     LOG.debugf("Creating elements for %s", path);
/* 197:    */     
/* 198:211 */     this.fromElement = collectionFromElement;
/* 199:212 */     if (!collectionFromElement.isCollectionOfValuesOrComponents()) {
/* 200:213 */       getWalker().addQuerySpaces(queryableCollection.getElementPersister().getQuerySpaces());
/* 201:    */     }
/* 202:216 */     setDataType(queryableCollection.getElementType());
/* 203:217 */     this.selectColumns = collectionFromElement.toColumns(this.fromElement.getTableAlias(), propertyName, this.inSelect);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setScalarColumnText(int i)
/* 207:    */     throws SemanticException
/* 208:    */   {
/* 209:221 */     if (this.selectColumns == null) {
/* 210:222 */       ColumnHelper.generateSingleScalarColumn(this, i);
/* 211:    */     } else {
/* 212:225 */       ColumnHelper.generateScalarColumns(this, this.selectColumns, i);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected void prepareSelectColumns(String[] columns) {}
/* 217:    */   
/* 218:    */   public FromElement getFromElement()
/* 219:    */   {
/* 220:234 */     return this.fromElement;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getDisplayText()
/* 224:    */   {
/* 225:238 */     return "{method=" + getMethodName() + ",selectColumns=" + (this.selectColumns == null ? null : Arrays.asList(this.selectColumns)) + ",fromElement=" + this.fromElement.getTableAlias() + "}";
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.MethodNode
 * JD-Core Version:    0.7.0.1
 */