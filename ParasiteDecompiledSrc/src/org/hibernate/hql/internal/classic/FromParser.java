/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.QueryException;
/*   6:    */ import org.hibernate.persister.entity.Queryable;
/*   7:    */ import org.hibernate.sql.JoinType;
/*   8:    */ 
/*   9:    */ public class FromParser
/*  10:    */   implements Parser
/*  11:    */ {
/*  12: 41 */   private final PathExpressionParser peParser = new FromPathExpressionParser();
/*  13:    */   private String entityName;
/*  14:    */   private String alias;
/*  15:    */   private boolean afterIn;
/*  16:    */   private boolean afterAs;
/*  17:    */   private boolean afterClass;
/*  18:    */   private boolean expectingJoin;
/*  19:    */   private boolean expectingIn;
/*  20:    */   private boolean expectingAs;
/*  21:    */   private boolean afterJoinType;
/*  22: 51 */   private JoinType joinType = JoinType.INNER_JOIN;
/*  23:    */   private boolean afterFetch;
/*  24:    */   private boolean memberDeclarations;
/*  25:    */   private boolean expectingPathExpression;
/*  26:    */   private boolean afterMemberDeclarations;
/*  27:    */   private String collectionName;
/*  28: 61 */   private static final Map<String, JoinType> JOIN_TYPES = new HashMap();
/*  29:    */   
/*  30:    */   static
/*  31:    */   {
/*  32: 64 */     JOIN_TYPES.put("left", JoinType.LEFT_OUTER_JOIN);
/*  33: 65 */     JOIN_TYPES.put("right", JoinType.RIGHT_OUTER_JOIN);
/*  34: 66 */     JOIN_TYPES.put("full", JoinType.FULL_JOIN);
/*  35: 67 */     JOIN_TYPES.put("inner", JoinType.INNER_JOIN);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void token(String token, QueryTranslatorImpl q)
/*  39:    */     throws QueryException
/*  40:    */   {
/*  41: 73 */     String lcToken = token.toLowerCase();
/*  42: 74 */     if (lcToken.equals(","))
/*  43:    */     {
/*  44: 75 */       if (!(this.expectingJoin | this.expectingAs)) {
/*  45: 75 */         throw new QueryException("unexpected token: ,");
/*  46:    */       }
/*  47: 76 */       this.expectingJoin = false;
/*  48: 77 */       this.expectingAs = false;
/*  49:    */     }
/*  50: 79 */     else if (lcToken.equals("join"))
/*  51:    */     {
/*  52: 80 */       if (!this.afterJoinType)
/*  53:    */       {
/*  54: 81 */         if (!(this.expectingJoin | this.expectingAs)) {
/*  55: 81 */           throw new QueryException("unexpected token: join");
/*  56:    */         }
/*  57: 83 */         this.joinType = JoinType.INNER_JOIN;
/*  58: 84 */         this.expectingJoin = false;
/*  59: 85 */         this.expectingAs = false;
/*  60:    */       }
/*  61:    */       else
/*  62:    */       {
/*  63: 88 */         this.afterJoinType = false;
/*  64:    */       }
/*  65:    */     }
/*  66: 91 */     else if (lcToken.equals("fetch"))
/*  67:    */     {
/*  68: 92 */       if (q.isShallowQuery()) {
/*  69: 92 */         throw new QueryException("fetch may not be used with scroll() or iterate()");
/*  70:    */       }
/*  71: 93 */       if (this.joinType == JoinType.NONE) {
/*  72: 93 */         throw new QueryException("unexpected token: fetch");
/*  73:    */       }
/*  74: 94 */       if ((this.joinType == JoinType.FULL_JOIN) || (this.joinType == JoinType.RIGHT_OUTER_JOIN)) {
/*  75: 95 */         throw new QueryException("fetch may only be used with inner join or left outer join");
/*  76:    */       }
/*  77: 97 */       this.afterFetch = true;
/*  78:    */     }
/*  79: 99 */     else if (lcToken.equals("outer"))
/*  80:    */     {
/*  81:101 */       if ((!this.afterJoinType) || ((this.joinType != JoinType.LEFT_OUTER_JOIN) && (this.joinType != JoinType.RIGHT_OUTER_JOIN))) {
/*  82:104 */         throw new QueryException("unexpected token: outer");
/*  83:    */       }
/*  84:    */     }
/*  85:107 */     else if (JOIN_TYPES.containsKey(lcToken))
/*  86:    */     {
/*  87:108 */       if (!(this.expectingJoin | this.expectingAs)) {
/*  88:108 */         throw new QueryException("unexpected token: " + token);
/*  89:    */       }
/*  90:109 */       this.joinType = ((JoinType)JOIN_TYPES.get(lcToken));
/*  91:110 */       this.afterJoinType = true;
/*  92:111 */       this.expectingJoin = false;
/*  93:112 */       this.expectingAs = false;
/*  94:    */     }
/*  95:114 */     else if (lcToken.equals("class"))
/*  96:    */     {
/*  97:115 */       if (!this.afterIn) {
/*  98:115 */         throw new QueryException("unexpected token: class");
/*  99:    */       }
/* 100:116 */       if (this.joinType != JoinType.NONE) {
/* 101:116 */         throw new QueryException("outer or full join must be followed by path expression");
/* 102:    */       }
/* 103:117 */       this.afterClass = true;
/* 104:    */     }
/* 105:119 */     else if (lcToken.equals("in"))
/* 106:    */     {
/* 107:120 */       if (this.alias == null)
/* 108:    */       {
/* 109:121 */         this.memberDeclarations = true;
/* 110:122 */         this.afterMemberDeclarations = false;
/* 111:    */       }
/* 112:    */       else
/* 113:    */       {
/* 114:124 */         if (!this.expectingIn) {
/* 115:125 */           throw new QueryException("unexpected token: in");
/* 116:    */         }
/* 117:127 */         this.afterIn = true;
/* 118:128 */         this.expectingIn = false;
/* 119:    */       }
/* 120:    */     }
/* 121:131 */     else if (lcToken.equals("as"))
/* 122:    */     {
/* 123:132 */       if (!this.expectingAs) {
/* 124:132 */         throw new QueryException("unexpected token: as");
/* 125:    */       }
/* 126:133 */       this.afterAs = true;
/* 127:134 */       this.expectingAs = false;
/* 128:    */     }
/* 129:136 */     else if ("(".equals(token))
/* 130:    */     {
/* 131:137 */       if (!this.memberDeclarations) {
/* 132:137 */         throw new QueryException("unexpected token: (");
/* 133:    */       }
/* 134:139 */       this.expectingPathExpression = true;
/* 135:    */     }
/* 136:142 */     else if (")".equals(token))
/* 137:    */     {
/* 138:145 */       this.afterMemberDeclarations = true;
/* 139:    */     }
/* 140:    */     else
/* 141:    */     {
/* 142:149 */       if (this.afterJoinType) {
/* 143:149 */         throw new QueryException("join expected: " + token);
/* 144:    */       }
/* 145:150 */       if (this.expectingJoin) {
/* 146:150 */         throw new QueryException("unexpected token: " + token);
/* 147:    */       }
/* 148:151 */       if (this.expectingIn) {
/* 149:151 */         throw new QueryException("in expected: " + token);
/* 150:    */       }
/* 151:155 */       if ((this.afterAs) || (this.expectingAs))
/* 152:    */       {
/* 153:163 */         if (this.entityName != null) {
/* 154:164 */           q.setAliasName(token, this.entityName);
/* 155:166 */         } else if (this.collectionName != null) {
/* 156:167 */           q.setAliasName(token, this.collectionName);
/* 157:    */         } else {
/* 158:170 */           throw new QueryException("unexpected: as " + token);
/* 159:    */         }
/* 160:172 */         this.afterAs = false;
/* 161:173 */         this.expectingJoin = true;
/* 162:174 */         this.expectingAs = false;
/* 163:175 */         this.entityName = null;
/* 164:176 */         this.collectionName = null;
/* 165:177 */         this.memberDeclarations = false;
/* 166:178 */         this.expectingPathExpression = false;
/* 167:179 */         this.afterMemberDeclarations = false;
/* 168:    */       }
/* 169:182 */       else if (this.afterIn)
/* 170:    */       {
/* 171:187 */         if (this.alias == null) {
/* 172:187 */           throw new QueryException("alias not specified for: " + token);
/* 173:    */         }
/* 174:189 */         if (this.joinType != JoinType.NONE) {
/* 175:189 */           throw new QueryException("outer or full join must be followed by path expression");
/* 176:    */         }
/* 177:191 */         if (this.afterClass)
/* 178:    */         {
/* 179:193 */           Queryable p = q.getEntityPersisterUsingImports(token);
/* 180:194 */           if (p == null) {
/* 181:194 */             throw new QueryException("persister not found: " + token);
/* 182:    */           }
/* 183:195 */           q.addFromClass(this.alias, p);
/* 184:    */         }
/* 185:    */         else
/* 186:    */         {
/* 187:199 */           this.peParser.setJoinType(JoinType.INNER_JOIN);
/* 188:200 */           this.peParser.setUseThetaStyleJoin(true);
/* 189:201 */           ParserHelper.parse(this.peParser, q.unalias(token), ".", q);
/* 190:202 */           if (!this.peParser.isCollectionValued()) {
/* 191:202 */             throw new QueryException("path expression did not resolve to collection: " + token);
/* 192:    */           }
/* 193:203 */           String nm = this.peParser.addFromCollection(q);
/* 194:204 */           q.setAliasName(this.alias, nm);
/* 195:    */         }
/* 196:207 */         this.alias = null;
/* 197:208 */         this.afterIn = false;
/* 198:209 */         this.afterClass = false;
/* 199:210 */         this.expectingJoin = true;
/* 200:    */       }
/* 201:212 */       else if ((this.memberDeclarations) && (this.expectingPathExpression))
/* 202:    */       {
/* 203:213 */         this.expectingAs = true;
/* 204:214 */         this.peParser.setJoinType(JoinType.INNER_JOIN);
/* 205:215 */         this.peParser.setUseThetaStyleJoin(false);
/* 206:216 */         ParserHelper.parse(this.peParser, q.unalias(token), ".", q);
/* 207:217 */         if (!this.peParser.isCollectionValued()) {
/* 208:217 */           throw new QueryException("path expression did not resolve to collection: " + token);
/* 209:    */         }
/* 210:218 */         this.collectionName = this.peParser.addFromCollection(q);
/* 211:219 */         this.expectingPathExpression = false;
/* 212:220 */         this.memberDeclarations = false;
/* 213:    */       }
/* 214:    */       else
/* 215:    */       {
/* 216:229 */         Queryable p = q.getEntityPersisterUsingImports(token);
/* 217:230 */         if (p != null)
/* 218:    */         {
/* 219:232 */           if (this.joinType != JoinType.NONE) {
/* 220:232 */             throw new QueryException("outer or full join must be followed by path expression");
/* 221:    */           }
/* 222:233 */           this.entityName = q.createNameFor(p.getEntityName());
/* 223:234 */           q.addFromClass(this.entityName, p);
/* 224:235 */           this.expectingAs = true;
/* 225:    */         }
/* 226:237 */         else if (token.indexOf('.') < 0)
/* 227:    */         {
/* 228:240 */           this.alias = token;
/* 229:241 */           this.expectingIn = true;
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:251 */           if (this.joinType != JoinType.NONE) {
/* 234:252 */             this.peParser.setJoinType(this.joinType);
/* 235:    */           } else {
/* 236:255 */             this.peParser.setJoinType(JoinType.INNER_JOIN);
/* 237:    */           }
/* 238:257 */           this.peParser.setUseThetaStyleJoin(q.isSubquery());
/* 239:    */           
/* 240:259 */           ParserHelper.parse(this.peParser, q.unalias(token), ".", q);
/* 241:260 */           this.entityName = this.peParser.addFromAssociation(q);
/* 242:    */           
/* 243:262 */           this.joinType = JoinType.NONE;
/* 244:263 */           this.peParser.setJoinType(JoinType.INNER_JOIN);
/* 245:265 */           if (this.afterFetch)
/* 246:    */           {
/* 247:266 */             this.peParser.fetch(q, this.entityName);
/* 248:267 */             this.afterFetch = false;
/* 249:    */           }
/* 250:270 */           this.expectingAs = true;
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void start(QueryTranslatorImpl q)
/* 257:    */   {
/* 258:279 */     this.entityName = null;
/* 259:280 */     this.collectionName = null;
/* 260:281 */     this.alias = null;
/* 261:282 */     this.afterIn = false;
/* 262:283 */     this.afterAs = false;
/* 263:284 */     this.afterClass = false;
/* 264:285 */     this.expectingJoin = false;
/* 265:286 */     this.expectingIn = false;
/* 266:287 */     this.expectingAs = false;
/* 267:288 */     this.memberDeclarations = false;
/* 268:289 */     this.expectingPathExpression = false;
/* 269:290 */     this.afterMemberDeclarations = false;
/* 270:291 */     this.joinType = JoinType.NONE;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void end(QueryTranslatorImpl q)
/* 274:    */   {
/* 275:295 */     if (this.afterMemberDeclarations) {
/* 276:298 */       throw new QueryException("alias not specified for IN");
/* 277:    */     }
/* 278:    */   }
/* 279:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.FromParser
 * JD-Core Version:    0.7.0.1
 */