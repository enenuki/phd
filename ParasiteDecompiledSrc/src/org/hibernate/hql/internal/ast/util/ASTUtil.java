/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import antlr.collections.impl.ASTArray;
/*   6:    */ import java.lang.reflect.Field;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ 
/*  13:    */ public final class ASTUtil
/*  14:    */ {
/*  15:    */   /**
/*  16:    */    * @deprecated
/*  17:    */    */
/*  18:    */   public static AST create(ASTFactory astFactory, int type, String text)
/*  19:    */   {
/*  20: 66 */     return astFactory.create(type, text);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static AST createSibling(ASTFactory astFactory, int type, String text, AST prevSibling)
/*  24:    */   {
/*  25: 82 */     AST node = astFactory.create(type, text);
/*  26: 83 */     return insertSibling(node, prevSibling);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static AST insertSibling(AST node, AST prevSibling)
/*  30:    */   {
/*  31: 98 */     node.setNextSibling(prevSibling.getNextSibling());
/*  32: 99 */     prevSibling.setNextSibling(node);
/*  33:100 */     return node;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static AST createBinarySubtree(ASTFactory factory, int parentType, String parentText, AST child1, AST child2)
/*  37:    */   {
/*  38:116 */     ASTArray array = createAstArray(factory, 3, parentType, parentText, child1);
/*  39:117 */     array.add(child2);
/*  40:118 */     return factory.make(array);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static AST createParent(ASTFactory factory, int parentType, String parentText, AST child)
/*  44:    */   {
/*  45:133 */     ASTArray array = createAstArray(factory, 2, parentType, parentText, child);
/*  46:134 */     return factory.make(array);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static AST createTree(ASTFactory factory, AST[] nestedChildren)
/*  50:    */   {
/*  51:138 */     AST[] array = new AST[2];
/*  52:139 */     int limit = nestedChildren.length - 1;
/*  53:140 */     for (int i = limit; i >= 0; i--) {
/*  54:141 */       if (i != limit)
/*  55:    */       {
/*  56:142 */         array[1] = nestedChildren[(i + 1)];
/*  57:143 */         array[0] = nestedChildren[i];
/*  58:144 */         factory.make(array);
/*  59:    */       }
/*  60:    */     }
/*  61:147 */     return array[0];
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static boolean isSubtreeChild(AST fixture, AST test)
/*  65:    */   {
/*  66:159 */     AST n = fixture.getFirstChild();
/*  67:160 */     while (n != null)
/*  68:    */     {
/*  69:161 */       if (n == test) {
/*  70:162 */         return true;
/*  71:    */       }
/*  72:164 */       if ((n.getFirstChild() != null) && (isSubtreeChild(n, test))) {
/*  73:165 */         return true;
/*  74:    */       }
/*  75:167 */       n = n.getNextSibling();
/*  76:    */     }
/*  77:169 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static AST findTypeInChildren(AST parent, int type)
/*  81:    */   {
/*  82:181 */     AST n = parent.getFirstChild();
/*  83:182 */     while ((n != null) && (n.getType() != type)) {
/*  84:183 */       n = n.getNextSibling();
/*  85:    */     }
/*  86:185 */     return n;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static AST getLastChild(AST n)
/*  90:    */   {
/*  91:196 */     return getLastSibling(n.getFirstChild());
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static AST getLastSibling(AST a)
/*  95:    */   {
/*  96:207 */     AST last = null;
/*  97:208 */     while (a != null)
/*  98:    */     {
/*  99:209 */       last = a;
/* 100:210 */       a = a.getNextSibling();
/* 101:    */     }
/* 102:212 */     return last;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static String getDebugString(AST n)
/* 106:    */   {
/* 107:223 */     StringBuffer buf = new StringBuffer();
/* 108:224 */     buf.append("[ ");
/* 109:225 */     buf.append(n == null ? "{null}" : n.toStringTree());
/* 110:226 */     buf.append(" ]");
/* 111:227 */     return buf.toString();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static AST findPreviousSibling(AST parent, AST child)
/* 115:    */   {
/* 116:239 */     AST prev = null;
/* 117:240 */     AST n = parent.getFirstChild();
/* 118:241 */     while (n != null)
/* 119:    */     {
/* 120:242 */       if (n == child) {
/* 121:243 */         return prev;
/* 122:    */       }
/* 123:245 */       prev = n;
/* 124:246 */       n = n.getNextSibling();
/* 125:    */     }
/* 126:248 */     throw new IllegalArgumentException("Child not found in parent!");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void makeSiblingOfParent(AST parent, AST child)
/* 130:    */   {
/* 131:258 */     AST prev = findPreviousSibling(parent, child);
/* 132:259 */     if (prev != null) {
/* 133:260 */       prev.setNextSibling(child.getNextSibling());
/* 134:    */     } else {
/* 135:263 */       parent.setFirstChild(child.getNextSibling());
/* 136:    */     }
/* 137:265 */     child.setNextSibling(parent.getNextSibling());
/* 138:266 */     parent.setNextSibling(child);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String getPathText(AST n)
/* 142:    */   {
/* 143:270 */     StringBuffer buf = new StringBuffer();
/* 144:271 */     getPathText(buf, n);
/* 145:272 */     return buf.toString();
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static void getPathText(StringBuffer buf, AST n)
/* 149:    */   {
/* 150:276 */     AST firstChild = n.getFirstChild();
/* 151:278 */     if (firstChild != null) {
/* 152:279 */       getPathText(buf, firstChild);
/* 153:    */     }
/* 154:282 */     buf.append(n.getText());
/* 155:284 */     if ((firstChild != null) && (firstChild.getNextSibling() != null)) {
/* 156:285 */       getPathText(buf, firstChild.getNextSibling());
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static boolean hasExactlyOneChild(AST n)
/* 161:    */   {
/* 162:290 */     return (n != null) && (n.getFirstChild() != null) && (n.getFirstChild().getNextSibling() == null);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static void appendSibling(AST n, AST s)
/* 166:    */   {
/* 167:294 */     while (n.getNextSibling() != null) {
/* 168:295 */       n = n.getNextSibling();
/* 169:    */     }
/* 170:297 */     n.setNextSibling(s);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void insertChild(AST parent, AST child)
/* 174:    */   {
/* 175:307 */     if (parent.getFirstChild() == null)
/* 176:    */     {
/* 177:308 */       parent.setFirstChild(child);
/* 178:    */     }
/* 179:    */     else
/* 180:    */     {
/* 181:311 */       AST n = parent.getFirstChild();
/* 182:312 */       parent.setFirstChild(child);
/* 183:313 */       child.setNextSibling(n);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private static ASTArray createAstArray(ASTFactory factory, int size, int parentType, String parentText, AST child1)
/* 188:    */   {
/* 189:318 */     ASTArray array = new ASTArray(size);
/* 190:319 */     array.add(factory.create(parentType, parentText));
/* 191:320 */     array.add(child1);
/* 192:321 */     return array;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static abstract interface FilterPredicate
/* 196:    */   {
/* 197:    */     public abstract boolean exclude(AST paramAST);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static abstract class IncludePredicate
/* 201:    */     implements ASTUtil.FilterPredicate
/* 202:    */   {
/* 203:    */     public final boolean exclude(AST node)
/* 204:    */     {
/* 205:343 */       return !include(node);
/* 206:    */     }
/* 207:    */     
/* 208:    */     public abstract boolean include(AST paramAST);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static List collectChildren(AST root, FilterPredicate predicate)
/* 212:    */   {
/* 213:350 */     return new CollectingNodeVisitor(predicate).collect(root);
/* 214:    */   }
/* 215:    */   
/* 216:    */   private static class CollectingNodeVisitor
/* 217:    */     implements NodeTraverser.VisitationStrategy
/* 218:    */   {
/* 219:    */     private final ASTUtil.FilterPredicate predicate;
/* 220:355 */     private final List collectedNodes = new ArrayList();
/* 221:    */     
/* 222:    */     public CollectingNodeVisitor(ASTUtil.FilterPredicate predicate)
/* 223:    */     {
/* 224:358 */       this.predicate = predicate;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void visit(AST node)
/* 228:    */     {
/* 229:362 */       if ((this.predicate == null) || (!this.predicate.exclude(node))) {
/* 230:363 */         this.collectedNodes.add(node);
/* 231:    */       }
/* 232:    */     }
/* 233:    */     
/* 234:    */     public List getCollectedNodes()
/* 235:    */     {
/* 236:368 */       return this.collectedNodes;
/* 237:    */     }
/* 238:    */     
/* 239:    */     public List collect(AST root)
/* 240:    */     {
/* 241:372 */       NodeTraverser traverser = new NodeTraverser(this);
/* 242:373 */       traverser.traverseDepthFirst(root);
/* 243:374 */       return this.collectedNodes;
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static Map generateTokenNameCache(Class tokenTypeInterface)
/* 248:    */   {
/* 249:385 */     Field[] fields = tokenTypeInterface.getFields();
/* 250:386 */     Map cache = new HashMap((int)(fields.length * 0.75D) + 1);
/* 251:387 */     for (int i = 0; i < fields.length; i++)
/* 252:    */     {
/* 253:388 */       Field field = fields[i];
/* 254:389 */       if (Modifier.isStatic(field.getModifiers())) {
/* 255:    */         try
/* 256:    */         {
/* 257:391 */           cache.put(field.get(null), field.getName());
/* 258:    */         }
/* 259:    */         catch (Throwable ignore) {}
/* 260:    */       }
/* 261:    */     }
/* 262:397 */     return cache;
/* 263:    */   }
/* 264:    */   
/* 265:    */   /**
/* 266:    */    * @deprecated
/* 267:    */    */
/* 268:    */   public static String getConstantName(Class owner, int value)
/* 269:    */   {
/* 270:414 */     return getTokenTypeName(owner, value);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static String getTokenTypeName(Class tokenTypeInterface, int tokenType)
/* 274:    */   {
/* 275:429 */     String tokenTypeName = Integer.toString(tokenType);
/* 276:430 */     if (tokenTypeInterface != null)
/* 277:    */     {
/* 278:431 */       Field[] fields = tokenTypeInterface.getFields();
/* 279:432 */       for (int i = 0; i < fields.length; i++)
/* 280:    */       {
/* 281:433 */         Integer fieldValue = extractIntegerValue(fields[i]);
/* 282:434 */         if ((fieldValue != null) && (fieldValue.intValue() == tokenType))
/* 283:    */         {
/* 284:435 */           tokenTypeName = fields[i].getName();
/* 285:436 */           break;
/* 286:    */         }
/* 287:    */       }
/* 288:    */     }
/* 289:440 */     return tokenTypeName;
/* 290:    */   }
/* 291:    */   
/* 292:    */   private static Integer extractIntegerValue(Field field)
/* 293:    */   {
/* 294:444 */     Integer rtn = null;
/* 295:    */     try
/* 296:    */     {
/* 297:446 */       Object value = field.get(null);
/* 298:447 */       if ((value instanceof Integer)) {
/* 299:448 */         rtn = (Integer)value;
/* 300:450 */       } else if ((value instanceof Short)) {
/* 301:451 */         rtn = Integer.valueOf(((Short)value).intValue());
/* 302:453 */       } else if (((value instanceof Long)) && 
/* 303:454 */         (((Long)value).longValue() <= 2147483647L)) {
/* 304:455 */         rtn = Integer.valueOf(((Long)value).intValue());
/* 305:    */       }
/* 306:    */     }
/* 307:    */     catch (IllegalAccessException ignore) {}
/* 308:461 */     return rtn;
/* 309:    */   }
/* 310:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ASTUtil
 * JD-Core Version:    0.7.0.1
 */