/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*  14:    */ import org.hibernate.hql.internal.ast.util.ASTIterator;
/*  15:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  16:    */ import org.hibernate.hql.internal.ast.util.ASTUtil.FilterPredicate;
/*  17:    */ import org.hibernate.hql.internal.ast.util.ASTUtil.IncludePredicate;
/*  18:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  19:    */ import org.hibernate.internal.CoreMessageLogger;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ public class FromClause
/*  23:    */   extends HqlSqlWalkerNode
/*  24:    */   implements HqlSqlTokenTypes, DisplayableNode
/*  25:    */ {
/*  26: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FromClause.class.getName());
/*  27:    */   public static final int ROOT_LEVEL = 1;
/*  28: 53 */   private int level = 1;
/*  29: 54 */   private Set fromElements = new HashSet();
/*  30: 55 */   private Map fromElementByClassAlias = new HashMap();
/*  31: 56 */   private Map fromElementByTableAlias = new HashMap();
/*  32: 57 */   private Map fromElementsByPath = new HashMap();
/*  33: 63 */   private Map collectionJoinFromElementsByPath = new HashMap();
/*  34:    */   private FromClause parentFromClause;
/*  35:    */   private Set childFromClauses;
/*  36: 75 */   private int fromElementCounter = 0;
/*  37: 79 */   private List impliedElements = new LinkedList();
/*  38:    */   
/*  39:    */   public FromElement addFromElement(String path, AST alias)
/*  40:    */     throws SemanticException
/*  41:    */   {
/*  42: 90 */     String classAlias = alias == null ? null : alias.getText();
/*  43: 91 */     checkForDuplicateClassAlias(classAlias);
/*  44: 92 */     FromElementFactory factory = new FromElementFactory(this, null, path, classAlias, null, false);
/*  45: 93 */     return factory.addFromElement();
/*  46:    */   }
/*  47:    */   
/*  48:    */   void registerFromElement(FromElement element)
/*  49:    */   {
/*  50: 97 */     this.fromElements.add(element);
/*  51: 98 */     String classAlias = element.getClassAlias();
/*  52: 99 */     if (classAlias != null) {
/*  53:101 */       this.fromElementByClassAlias.put(classAlias, element);
/*  54:    */     }
/*  55:104 */     String tableAlias = element.getTableAlias();
/*  56:105 */     if (tableAlias != null) {
/*  57:106 */       this.fromElementByTableAlias.put(tableAlias, element);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   void addDuplicateAlias(String alias, FromElement element)
/*  62:    */   {
/*  63:111 */     if (alias != null) {
/*  64:112 */       this.fromElementByClassAlias.put(alias, element);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void checkForDuplicateClassAlias(String classAlias)
/*  69:    */     throws SemanticException
/*  70:    */   {
/*  71:117 */     if ((classAlias != null) && (this.fromElementByClassAlias.containsKey(classAlias))) {
/*  72:118 */       throw new SemanticException("Duplicate definition of alias '" + classAlias + "'");
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public FromElement getFromElement(String aliasOrClassName)
/*  77:    */   {
/*  78:129 */     FromElement fromElement = (FromElement)this.fromElementByClassAlias.get(aliasOrClassName);
/*  79:130 */     if ((fromElement == null) && (getSessionFactoryHelper().isStrictJPAQLComplianceEnabled())) {
/*  80:131 */       fromElement = findIntendedAliasedFromElementBasedOnCrazyJPARequirements(aliasOrClassName);
/*  81:    */     }
/*  82:133 */     if ((fromElement == null) && (this.parentFromClause != null)) {
/*  83:134 */       fromElement = this.parentFromClause.getFromElement(aliasOrClassName);
/*  84:    */     }
/*  85:136 */     return fromElement;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public FromElement findFromElementBySqlAlias(String sqlAlias)
/*  89:    */   {
/*  90:140 */     FromElement fromElement = (FromElement)this.fromElementByTableAlias.get(sqlAlias);
/*  91:141 */     if ((fromElement == null) && (this.parentFromClause != null)) {
/*  92:142 */       fromElement = this.parentFromClause.getFromElement(sqlAlias);
/*  93:    */     }
/*  94:144 */     return fromElement;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public FromElement findFromElementByUserOrSqlAlias(String userAlias, String sqlAlias)
/*  98:    */   {
/*  99:148 */     FromElement fromElement = null;
/* 100:149 */     if (userAlias != null) {
/* 101:150 */       fromElement = getFromElement(userAlias);
/* 102:    */     }
/* 103:153 */     if (fromElement == null) {
/* 104:154 */       fromElement = findFromElementBySqlAlias(sqlAlias);
/* 105:    */     }
/* 106:157 */     return fromElement;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private FromElement findIntendedAliasedFromElementBasedOnCrazyJPARequirements(String specifiedAlias)
/* 110:    */   {
/* 111:161 */     Iterator itr = this.fromElementByClassAlias.entrySet().iterator();
/* 112:162 */     while (itr.hasNext())
/* 113:    */     {
/* 114:163 */       Map.Entry entry = (Map.Entry)itr.next();
/* 115:164 */       String alias = (String)entry.getKey();
/* 116:165 */       if (alias.equalsIgnoreCase(specifiedAlias)) {
/* 117:166 */         return (FromElement)entry.getValue();
/* 118:    */       }
/* 119:    */     }
/* 120:169 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isFromElementAlias(String possibleAlias)
/* 124:    */   {
/* 125:180 */     boolean isAlias = containsClassAlias(possibleAlias);
/* 126:181 */     if ((!isAlias) && (this.parentFromClause != null)) {
/* 127:183 */       isAlias = this.parentFromClause.isFromElementAlias(possibleAlias);
/* 128:    */     }
/* 129:185 */     return isAlias;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List getFromElements()
/* 133:    */   {
/* 134:194 */     return ASTUtil.collectChildren(this, fromElementPredicate);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public FromElement getFromElement()
/* 138:    */   {
/* 139:203 */     return (FromElement)getFromElements().get(0);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List getProjectionList()
/* 143:    */   {
/* 144:212 */     return ASTUtil.collectChildren(this, projectionListPredicate);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List getCollectionFetches()
/* 148:    */   {
/* 149:216 */     return ASTUtil.collectChildren(this, collectionFetchPredicate);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean hasCollectionFecthes()
/* 153:    */   {
/* 154:220 */     return getCollectionFetches().size() > 0;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List getExplicitFromElements()
/* 158:    */   {
/* 159:224 */     return ASTUtil.collectChildren(this, explicitFromPredicate);
/* 160:    */   }
/* 161:    */   
/* 162:227 */   private static ASTUtil.FilterPredicate fromElementPredicate = new ASTUtil.IncludePredicate()
/* 163:    */   {
/* 164:    */     public boolean include(AST node)
/* 165:    */     {
/* 166:230 */       FromElement fromElement = (FromElement)node;
/* 167:231 */       return fromElement.isFromOrJoinFragment();
/* 168:    */     }
/* 169:    */   };
/* 170:235 */   private static ASTUtil.FilterPredicate projectionListPredicate = new ASTUtil.IncludePredicate()
/* 171:    */   {
/* 172:    */     public boolean include(AST node)
/* 173:    */     {
/* 174:238 */       FromElement fromElement = (FromElement)node;
/* 175:239 */       return fromElement.inProjectionList();
/* 176:    */     }
/* 177:    */   };
/* 178:243 */   private static ASTUtil.FilterPredicate collectionFetchPredicate = new ASTUtil.IncludePredicate()
/* 179:    */   {
/* 180:    */     public boolean include(AST node)
/* 181:    */     {
/* 182:246 */       FromElement fromElement = (FromElement)node;
/* 183:247 */       return (fromElement.isFetch()) && (fromElement.getQueryableCollection() != null);
/* 184:    */     }
/* 185:    */   };
/* 186:251 */   private static ASTUtil.FilterPredicate explicitFromPredicate = new ASTUtil.IncludePredicate()
/* 187:    */   {
/* 188:    */     public boolean include(AST node)
/* 189:    */     {
/* 190:254 */       FromElement fromElement = (FromElement)node;
/* 191:255 */       return !fromElement.isImplied();
/* 192:    */     }
/* 193:    */   };
/* 194:    */   
/* 195:    */   FromElement findCollectionJoin(String path)
/* 196:    */   {
/* 197:260 */     return (FromElement)this.collectionJoinFromElementsByPath.get(path);
/* 198:    */   }
/* 199:    */   
/* 200:    */   FromElement findJoinByPath(String path)
/* 201:    */   {
/* 202:268 */     FromElement elem = findJoinByPathLocal(path);
/* 203:269 */     if ((elem == null) && (this.parentFromClause != null)) {
/* 204:270 */       elem = this.parentFromClause.findJoinByPath(path);
/* 205:    */     }
/* 206:272 */     return elem;
/* 207:    */   }
/* 208:    */   
/* 209:    */   FromElement findJoinByPathLocal(String path)
/* 210:    */   {
/* 211:276 */     Map joinsByPath = this.fromElementsByPath;
/* 212:277 */     return (FromElement)joinsByPath.get(path);
/* 213:    */   }
/* 214:    */   
/* 215:    */   void addJoinByPathMap(String path, FromElement destination)
/* 216:    */   {
/* 217:281 */     if (LOG.isDebugEnabled()) {
/* 218:282 */       LOG.debugf("addJoinByPathMap() : %s -> %s", path, destination.getDisplayText());
/* 219:    */     }
/* 220:284 */     this.fromElementsByPath.put(path, destination);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean containsClassAlias(String alias)
/* 224:    */   {
/* 225:294 */     boolean isAlias = this.fromElementByClassAlias.containsKey(alias);
/* 226:295 */     if ((!isAlias) && (getSessionFactoryHelper().isStrictJPAQLComplianceEnabled())) {
/* 227:296 */       isAlias = findIntendedAliasedFromElementBasedOnCrazyJPARequirements(alias) != null;
/* 228:    */     }
/* 229:298 */     return isAlias;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean containsTableAlias(String alias)
/* 233:    */   {
/* 234:308 */     return this.fromElementByTableAlias.keySet().contains(alias);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getDisplayText()
/* 238:    */   {
/* 239:312 */     return "FromClause{level=" + this.level + ", fromElementCounter=" + this.fromElementCounter + ", fromElements=" + this.fromElements.size() + ", fromElementByClassAlias=" + this.fromElementByClassAlias.keySet() + ", fromElementByTableAlias=" + this.fromElementByTableAlias.keySet() + ", fromElementsByPath=" + this.fromElementsByPath.keySet() + ", collectionJoinFromElementsByPath=" + this.collectionJoinFromElementsByPath.keySet() + ", impliedElements=" + this.impliedElements + "}";
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setParentFromClause(FromClause parentFromClause)
/* 243:    */   {
/* 244:325 */     this.parentFromClause = parentFromClause;
/* 245:326 */     if (parentFromClause != null)
/* 246:    */     {
/* 247:327 */       this.level = (parentFromClause.getLevel() + 1);
/* 248:328 */       parentFromClause.addChild(this);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   private void addChild(FromClause fromClause)
/* 253:    */   {
/* 254:333 */     if (this.childFromClauses == null) {
/* 255:334 */       this.childFromClauses = new HashSet();
/* 256:    */     }
/* 257:336 */     this.childFromClauses.add(fromClause);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public FromClause locateChildFromClauseWithJoinByPath(String path)
/* 261:    */   {
/* 262:340 */     if ((this.childFromClauses != null) && (!this.childFromClauses.isEmpty()))
/* 263:    */     {
/* 264:341 */       Iterator children = this.childFromClauses.iterator();
/* 265:342 */       while (children.hasNext())
/* 266:    */       {
/* 267:343 */         FromClause child = (FromClause)children.next();
/* 268:344 */         if (child.findJoinByPathLocal(path) != null) {
/* 269:345 */           return child;
/* 270:    */         }
/* 271:    */       }
/* 272:    */     }
/* 273:349 */     return null;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void promoteJoin(FromElement elem)
/* 277:    */   {
/* 278:353 */     LOG.debugf("Promoting [%s] to [%s]", elem, this);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean isSubQuery()
/* 282:    */   {
/* 283:365 */     return this.parentFromClause != null;
/* 284:    */   }
/* 285:    */   
/* 286:    */   void addCollectionJoinFromElementByPath(String path, FromElement destination)
/* 287:    */   {
/* 288:369 */     LOG.debugf("addCollectionJoinFromElementByPath() : %s -> %s", path, destination);
/* 289:370 */     this.collectionJoinFromElementsByPath.put(path, destination);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public FromClause getParentFromClause()
/* 293:    */   {
/* 294:374 */     return this.parentFromClause;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public int getLevel()
/* 298:    */   {
/* 299:378 */     return this.level;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public int nextFromElementCounter()
/* 303:    */   {
/* 304:382 */     return this.fromElementCounter++;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void resolve()
/* 308:    */   {
/* 309:387 */     ASTIterator iter = new ASTIterator(getFirstChild());
/* 310:388 */     Set childrenInTree = new HashSet();
/* 311:389 */     while (iter.hasNext()) {
/* 312:390 */       childrenInTree.add(iter.next());
/* 313:    */     }
/* 314:392 */     for (Iterator iterator = this.fromElements.iterator(); iterator.hasNext();)
/* 315:    */     {
/* 316:393 */       FromElement fromElement = (FromElement)iterator.next();
/* 317:394 */       if (!childrenInTree.contains(fromElement)) {
/* 318:395 */         throw new IllegalStateException("Element not in AST: " + fromElement);
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void addImpliedFromElement(FromElement element)
/* 324:    */   {
/* 325:401 */     this.impliedElements.add(element);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String toString()
/* 329:    */   {
/* 330:406 */     return "FromClause{level=" + this.level + "}";
/* 331:    */   }
/* 332:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FromClause
 * JD-Core Version:    0.7.0.1
 */