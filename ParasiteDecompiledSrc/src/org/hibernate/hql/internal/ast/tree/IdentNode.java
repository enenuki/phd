/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.dialect.function.SQLFunction;
/*   8:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   9:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  10:    */ import org.hibernate.hql.internal.ast.util.LiteralProcessor;
/*  11:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  12:    */ import org.hibernate.internal.util.StringHelper;
/*  13:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  14:    */ import org.hibernate.persister.entity.Queryable;
/*  15:    */ import org.hibernate.sql.JoinType;
/*  16:    */ import org.hibernate.type.CollectionType;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ 
/*  19:    */ public class IdentNode
/*  20:    */   extends FromReferenceNode
/*  21:    */   implements SelectExpression
/*  22:    */ {
/*  23:    */   private static final int UNKNOWN = 0;
/*  24:    */   private static final int PROPERTY_REF = 1;
/*  25:    */   private static final int COMPONENT_REF = 2;
/*  26: 56 */   private boolean nakedPropertyRef = false;
/*  27:    */   
/*  28:    */   public void resolveIndex(AST parent)
/*  29:    */     throws SemanticException
/*  30:    */   {
/*  31: 66 */     if ((!isResolved()) || (!this.nakedPropertyRef)) {
/*  32: 67 */       throw new UnsupportedOperationException();
/*  33:    */     }
/*  34: 70 */     String propertyName = getOriginalText();
/*  35: 71 */     if (!getDataType().isCollectionType()) {
/*  36: 72 */       throw new SemanticException("Collection expected; [" + propertyName + "] does not refer to a collection property");
/*  37:    */     }
/*  38: 76 */     CollectionType type = (CollectionType)getDataType();
/*  39: 77 */     String role = type.getRole();
/*  40: 78 */     QueryableCollection queryableCollection = getSessionFactoryHelper().requireQueryableCollection(role);
/*  41:    */     
/*  42: 80 */     String alias = null;
/*  43: 81 */     String columnTableAlias = getFromElement().getTableAlias();
/*  44: 82 */     JoinType joinType = JoinType.INNER_JOIN;
/*  45: 83 */     boolean fetch = false;
/*  46:    */     
/*  47: 85 */     FromElementFactory factory = new FromElementFactory(getWalker().getCurrentFromClause(), getFromElement(), propertyName, alias, getFromElement().toColumns(columnTableAlias, propertyName, false), true);
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 93 */     FromElement elem = factory.createCollection(queryableCollection, role, joinType, fetch, true);
/*  56: 94 */     setFromElement(elem);
/*  57: 95 */     getWalker().addQuerySpaces(queryableCollection.getCollectionSpaces());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias, AST parent)
/*  61:    */   {
/*  62: 99 */     if (!isResolved())
/*  63:    */     {
/*  64:100 */       if (getWalker().getCurrentFromClause().isFromElementAlias(getText()))
/*  65:    */       {
/*  66:101 */         if (resolveAsAlias()) {
/*  67:102 */           setResolved();
/*  68:    */         }
/*  69:    */       }
/*  70:106 */       else if ((parent != null) && (parent.getType() == 15))
/*  71:    */       {
/*  72:107 */         DotNode dot = (DotNode)parent;
/*  73:108 */         if (parent.getFirstChild() == this)
/*  74:    */         {
/*  75:109 */           if (resolveAsNakedComponentPropertyRefLHS(dot)) {
/*  76:111 */             setResolved();
/*  77:    */           }
/*  78:    */         }
/*  79:115 */         else if (resolveAsNakedComponentPropertyRefRHS(dot)) {
/*  80:117 */           setResolved();
/*  81:    */         }
/*  82:    */       }
/*  83:    */       else
/*  84:    */       {
/*  85:122 */         int result = resolveAsNakedPropertyRef();
/*  86:123 */         if (result == 1) {
/*  87:125 */           setResolved();
/*  88:127 */         } else if (result == 2) {
/*  89:130 */           return;
/*  90:    */         }
/*  91:    */       }
/*  92:139 */       if (!isResolved()) {
/*  93:    */         try
/*  94:    */         {
/*  95:141 */           getWalker().getLiteralProcessor().processConstant(this, false);
/*  96:    */         }
/*  97:    */         catch (Throwable ignore) {}
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private boolean resolveAsAlias()
/* 103:    */   {
/* 104:152 */     FromElement element = getWalker().getCurrentFromClause().getFromElement(getText());
/* 105:153 */     if (element != null)
/* 106:    */     {
/* 107:154 */       setFromElement(element);
/* 108:155 */       setText(element.getIdentityColumn());
/* 109:156 */       setType(140);
/* 110:157 */       return true;
/* 111:    */     }
/* 112:159 */     return false;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private Type getNakedPropertyType(FromElement fromElement)
/* 116:    */   {
/* 117:164 */     if (fromElement == null) {
/* 118:165 */       return null;
/* 119:    */     }
/* 120:167 */     String property = getOriginalText();
/* 121:168 */     Type propertyType = null;
/* 122:    */     try
/* 123:    */     {
/* 124:170 */       propertyType = fromElement.getPropertyType(property, property);
/* 125:    */     }
/* 126:    */     catch (Throwable t) {}
/* 127:174 */     return propertyType;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private int resolveAsNakedPropertyRef()
/* 131:    */   {
/* 132:178 */     FromElement fromElement = locateSingleFromElement();
/* 133:179 */     if (fromElement == null) {
/* 134:180 */       return 0;
/* 135:    */     }
/* 136:182 */     Queryable persister = fromElement.getQueryable();
/* 137:183 */     if (persister == null) {
/* 138:184 */       return 0;
/* 139:    */     }
/* 140:186 */     Type propertyType = getNakedPropertyType(fromElement);
/* 141:187 */     if (propertyType == null) {
/* 142:189 */       return 0;
/* 143:    */     }
/* 144:192 */     if ((propertyType.isComponentType()) || (propertyType.isAssociationType())) {
/* 145:193 */       return 2;
/* 146:    */     }
/* 147:196 */     setFromElement(fromElement);
/* 148:197 */     String property = getText();
/* 149:198 */     String[] columns = getWalker().isSelectStatement() ? persister.toColumns(fromElement.getTableAlias(), property) : persister.toColumns(property);
/* 150:    */     
/* 151:    */ 
/* 152:201 */     String text = StringHelper.join(", ", columns);
/* 153:202 */     setText("(" + text + ")");
/* 154:203 */     setType(142);
/* 155:    */     
/* 156:    */ 
/* 157:206 */     super.setDataType(propertyType);
/* 158:207 */     this.nakedPropertyRef = true;
/* 159:    */     
/* 160:209 */     return 1;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private boolean resolveAsNakedComponentPropertyRefLHS(DotNode parent)
/* 164:    */   {
/* 165:213 */     FromElement fromElement = locateSingleFromElement();
/* 166:214 */     if (fromElement == null) {
/* 167:215 */       return false;
/* 168:    */     }
/* 169:218 */     Type componentType = getNakedPropertyType(fromElement);
/* 170:219 */     if (componentType == null) {
/* 171:220 */       throw new QueryException("Unable to resolve path [" + parent.getPath() + "], unexpected token [" + getOriginalText() + "]");
/* 172:    */     }
/* 173:222 */     if (!componentType.isComponentType()) {
/* 174:223 */       throw new QueryException("Property '" + getOriginalText() + "' is not a component.  Use an alias to reference associations or collections.");
/* 175:    */     }
/* 176:226 */     Type propertyType = null;
/* 177:227 */     String propertyPath = getText() + "." + getNextSibling().getText();
/* 178:    */     try
/* 179:    */     {
/* 180:231 */       propertyType = fromElement.getPropertyType(getText(), propertyPath);
/* 181:    */     }
/* 182:    */     catch (Throwable t)
/* 183:    */     {
/* 184:235 */       return false;
/* 185:    */     }
/* 186:238 */     setFromElement(fromElement);
/* 187:239 */     parent.setPropertyPath(propertyPath);
/* 188:240 */     parent.setDataType(propertyType);
/* 189:    */     
/* 190:242 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private boolean resolveAsNakedComponentPropertyRefRHS(DotNode parent)
/* 194:    */   {
/* 195:246 */     FromElement fromElement = locateSingleFromElement();
/* 196:247 */     if (fromElement == null) {
/* 197:248 */       return false;
/* 198:    */     }
/* 199:251 */     Type propertyType = null;
/* 200:252 */     String propertyPath = parent.getLhs().getText() + "." + getText();
/* 201:    */     try
/* 202:    */     {
/* 203:256 */       propertyType = fromElement.getPropertyType(getText(), propertyPath);
/* 204:    */     }
/* 205:    */     catch (Throwable t)
/* 206:    */     {
/* 207:260 */       return false;
/* 208:    */     }
/* 209:263 */     setFromElement(fromElement);
/* 210:    */     
/* 211:265 */     super.setDataType(propertyType);
/* 212:266 */     this.nakedPropertyRef = true;
/* 213:    */     
/* 214:268 */     return true;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private FromElement locateSingleFromElement()
/* 218:    */   {
/* 219:272 */     List fromElements = getWalker().getCurrentFromClause().getFromElements();
/* 220:273 */     if ((fromElements == null) || (fromElements.size() != 1)) {
/* 221:275 */       return null;
/* 222:    */     }
/* 223:277 */     FromElement element = (FromElement)fromElements.get(0);
/* 224:278 */     if (element.getClassAlias() != null) {
/* 225:280 */       return null;
/* 226:    */     }
/* 227:282 */     return element;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Type getDataType()
/* 231:    */   {
/* 232:287 */     Type type = super.getDataType();
/* 233:288 */     if (type != null) {
/* 234:289 */       return type;
/* 235:    */     }
/* 236:291 */     FromElement fe = getFromElement();
/* 237:292 */     if (fe != null) {
/* 238:293 */       return fe.getDataType();
/* 239:    */     }
/* 240:295 */     SQLFunction sf = getWalker().getSessionFactoryHelper().findSQLFunction(getText());
/* 241:296 */     if (sf != null) {
/* 242:297 */       return sf.getReturnType(null, getWalker().getSessionFactoryHelper().getFactory());
/* 243:    */     }
/* 244:299 */     return null;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setScalarColumnText(int i)
/* 248:    */     throws SemanticException
/* 249:    */   {
/* 250:303 */     if (this.nakedPropertyRef)
/* 251:    */     {
/* 252:306 */       ColumnHelper.generateSingleScalarColumn(this, i);
/* 253:    */     }
/* 254:    */     else
/* 255:    */     {
/* 256:309 */       FromElement fe = getFromElement();
/* 257:310 */       if (fe != null) {
/* 258:311 */         setText(fe.renderScalarIdentifierSelect(i));
/* 259:    */       } else {
/* 260:314 */         ColumnHelper.generateSingleScalarColumn(this, i);
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getDisplayText()
/* 266:    */   {
/* 267:321 */     StringBuffer buf = new StringBuffer();
/* 268:323 */     if (getType() == 140)
/* 269:    */     {
/* 270:324 */       buf.append("{alias=").append(getOriginalText());
/* 271:325 */       if (getFromElement() == null)
/* 272:    */       {
/* 273:326 */         buf.append(", no from element");
/* 274:    */       }
/* 275:    */       else
/* 276:    */       {
/* 277:329 */         buf.append(", className=").append(getFromElement().getClassName());
/* 278:330 */         buf.append(", tableAlias=").append(getFromElement().getTableAlias());
/* 279:    */       }
/* 280:332 */       buf.append("}");
/* 281:    */     }
/* 282:    */     else
/* 283:    */     {
/* 284:335 */       buf.append("{originalText=" + getOriginalText()).append("}");
/* 285:    */     }
/* 286:337 */     return buf.toString();
/* 287:    */   }
/* 288:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.IdentNode
 * JD-Core Version:    0.7.0.1
 */