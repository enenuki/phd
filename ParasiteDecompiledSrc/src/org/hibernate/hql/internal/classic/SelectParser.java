/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.QueryException;
/*   8:    */ import org.hibernate.dialect.function.SQLFunction;
/*   9:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.hql.internal.QuerySplitter;
/*  12:    */ import org.hibernate.internal.util.ReflectHelper;
/*  13:    */ import org.hibernate.type.StandardBasicTypes;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ 
/*  16:    */ public class SelectParser
/*  17:    */   implements Parser
/*  18:    */ {
/*  19: 47 */   private static final Set COUNT_MODIFIERS = new HashSet();
/*  20:    */   private LinkedList aggregateFuncTokenList;
/*  21:    */   private boolean ready;
/*  22:    */   private boolean aggregate;
/*  23:    */   private boolean first;
/*  24:    */   private boolean afterNew;
/*  25:    */   private boolean insideNew;
/*  26:    */   private boolean aggregateAddSelectScalar;
/*  27:    */   private Class holderClass;
/*  28:    */   private final SelectPathExpressionParser pathExpressionParser;
/*  29:    */   private final PathExpressionParser aggregatePathExpressionParser;
/*  30:    */   
/*  31:    */   static
/*  32:    */   {
/*  33: 50 */     COUNT_MODIFIERS.add("distinct");
/*  34: 51 */     COUNT_MODIFIERS.add("all");
/*  35: 52 */     COUNT_MODIFIERS.add("*");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SelectParser()
/*  39:    */   {
/*  40: 55 */     this.aggregateFuncTokenList = new LinkedList();
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 69 */     this.pathExpressionParser = new SelectPathExpressionParser();
/*  55: 70 */     this.aggregatePathExpressionParser = new PathExpressionParser();
/*  56:    */     
/*  57: 72 */     this.pathExpressionParser.setUseThetaStyleJoin(true);
/*  58: 73 */     this.aggregatePathExpressionParser.setUseThetaStyleJoin(true);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void token(String token, QueryTranslatorImpl q)
/*  62:    */     throws QueryException
/*  63:    */   {
/*  64: 78 */     String lctoken = token.toLowerCase();
/*  65: 80 */     if (this.first)
/*  66:    */     {
/*  67: 81 */       this.first = false;
/*  68: 82 */       if ("distinct".equals(lctoken))
/*  69:    */       {
/*  70: 83 */         q.setDistinct(true);
/*  71: 84 */         return;
/*  72:    */       }
/*  73: 86 */       if ("all".equals(lctoken))
/*  74:    */       {
/*  75: 87 */         q.setDistinct(false);
/*  76: 88 */         return;
/*  77:    */       }
/*  78:    */     }
/*  79: 92 */     if (this.afterNew)
/*  80:    */     {
/*  81: 93 */       this.afterNew = false;
/*  82:    */       try
/*  83:    */       {
/*  84: 95 */         this.holderClass = ReflectHelper.classForName(QuerySplitter.getImportedClass(token, q.getFactory()));
/*  85:    */       }
/*  86:    */       catch (ClassNotFoundException cnfe)
/*  87:    */       {
/*  88: 98 */         throw new QueryException(cnfe);
/*  89:    */       }
/*  90:100 */       if (this.holderClass == null) {
/*  91:100 */         throw new QueryException("class not found: " + token);
/*  92:    */       }
/*  93:101 */       q.setHolderClass(this.holderClass);
/*  94:102 */       this.insideNew = true;
/*  95:    */     }
/*  96:104 */     else if (token.equals(","))
/*  97:    */     {
/*  98:105 */       if ((!this.aggregate) && (this.ready)) {
/*  99:105 */         throw new QueryException("alias or expression expected in SELECT");
/* 100:    */       }
/* 101:106 */       q.appendScalarSelectToken(", ");
/* 102:107 */       this.ready = true;
/* 103:    */     }
/* 104:109 */     else if ("new".equals(lctoken))
/* 105:    */     {
/* 106:110 */       this.afterNew = true;
/* 107:111 */       this.ready = false;
/* 108:    */     }
/* 109:113 */     else if ("(".equals(token))
/* 110:    */     {
/* 111:114 */       if ((this.insideNew) && (!this.aggregate) && (!this.ready)) {
/* 112:116 */         this.ready = true;
/* 113:118 */       } else if (this.aggregate) {
/* 114:119 */         q.appendScalarSelectToken(token);
/* 115:    */       } else {
/* 116:122 */         throw new QueryException("aggregate function expected before ( in SELECT");
/* 117:    */       }
/* 118:124 */       this.ready = true;
/* 119:    */     }
/* 120:126 */     else if (")".equals(token))
/* 121:    */     {
/* 122:127 */       if ((this.insideNew) && (!this.aggregate) && (!this.ready))
/* 123:    */       {
/* 124:129 */         this.insideNew = false;
/* 125:    */       }
/* 126:131 */       else if ((this.aggregate) && (this.ready))
/* 127:    */       {
/* 128:132 */         q.appendScalarSelectToken(token);
/* 129:133 */         this.aggregateFuncTokenList.removeLast();
/* 130:134 */         if (this.aggregateFuncTokenList.size() < 1)
/* 131:    */         {
/* 132:135 */           this.aggregate = false;
/* 133:136 */           this.ready = false;
/* 134:    */         }
/* 135:    */       }
/* 136:    */       else
/* 137:    */       {
/* 138:140 */         throw new QueryException("( expected before ) in select");
/* 139:    */       }
/* 140:    */     }
/* 141:143 */     else if (COUNT_MODIFIERS.contains(lctoken))
/* 142:    */     {
/* 143:144 */       if ((!this.ready) || (!this.aggregate)) {
/* 144:145 */         throw new QueryException(token + " only allowed inside aggregate function in SELECT");
/* 145:    */       }
/* 146:147 */       q.appendScalarSelectToken(token);
/* 147:148 */       if ("*".equals(token)) {
/* 148:150 */         q.addSelectScalar(getFunction("count", q).getReturnType(StandardBasicTypes.LONG, q.getFactory()));
/* 149:    */       }
/* 150:    */     }
/* 151:153 */     else if ((getFunction(lctoken, q) != null) && (token.equals(q.unalias(token))))
/* 152:    */     {
/* 153:155 */       if (!this.ready) {
/* 154:155 */         throw new QueryException(", expected before aggregate function in SELECT: " + token);
/* 155:    */       }
/* 156:156 */       this.aggregate = true;
/* 157:157 */       this.aggregateAddSelectScalar = true;
/* 158:158 */       this.aggregateFuncTokenList.add(lctoken);
/* 159:159 */       this.ready = false;
/* 160:160 */       q.appendScalarSelectToken(token);
/* 161:161 */       if (!aggregateHasArgs(lctoken, q))
/* 162:    */       {
/* 163:162 */         q.addSelectScalar(aggregateType(this.aggregateFuncTokenList, null, q));
/* 164:163 */         if (!aggregateFuncNoArgsHasParenthesis(lctoken, q))
/* 165:    */         {
/* 166:164 */           this.aggregateFuncTokenList.removeLast();
/* 167:165 */           if (this.aggregateFuncTokenList.size() < 1)
/* 168:    */           {
/* 169:166 */             this.aggregate = false;
/* 170:167 */             this.ready = false;
/* 171:    */           }
/* 172:    */           else
/* 173:    */           {
/* 174:170 */             this.ready = true;
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:175 */     else if (this.aggregate)
/* 180:    */     {
/* 181:176 */       boolean constantToken = false;
/* 182:177 */       if (!this.ready) {
/* 183:177 */         throw new QueryException("( expected after aggregate function in SELECT");
/* 184:    */       }
/* 185:    */       try
/* 186:    */       {
/* 187:179 */         ParserHelper.parse(this.aggregatePathExpressionParser, q.unalias(token), ".", q);
/* 188:    */       }
/* 189:    */       catch (QueryException qex)
/* 190:    */       {
/* 191:182 */         constantToken = true;
/* 192:    */       }
/* 193:185 */       if (constantToken)
/* 194:    */       {
/* 195:186 */         q.appendScalarSelectToken(token);
/* 196:    */       }
/* 197:    */       else
/* 198:    */       {
/* 199:189 */         if (this.aggregatePathExpressionParser.isCollectionValued()) {
/* 200:190 */           q.addCollection(this.aggregatePathExpressionParser.getCollectionName(), this.aggregatePathExpressionParser.getCollectionRole());
/* 201:    */         }
/* 202:193 */         q.appendScalarSelectToken(this.aggregatePathExpressionParser.getWhereColumn());
/* 203:194 */         if (this.aggregateAddSelectScalar)
/* 204:    */         {
/* 205:195 */           q.addSelectScalar(aggregateType(this.aggregateFuncTokenList, this.aggregatePathExpressionParser.getWhereColumnType(), q));
/* 206:196 */           this.aggregateAddSelectScalar = false;
/* 207:    */         }
/* 208:198 */         this.aggregatePathExpressionParser.addAssociation(q);
/* 209:    */       }
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:202 */       if (!this.ready) {
/* 214:202 */         throw new QueryException(", expected in SELECT");
/* 215:    */       }
/* 216:203 */       ParserHelper.parse(this.pathExpressionParser, q.unalias(token), ".", q);
/* 217:204 */       if (this.pathExpressionParser.isCollectionValued()) {
/* 218:205 */         q.addCollection(this.pathExpressionParser.getCollectionName(), this.pathExpressionParser.getCollectionRole());
/* 219:208 */       } else if (this.pathExpressionParser.getWhereColumnType().isEntityType()) {
/* 220:209 */         q.addSelectClass(this.pathExpressionParser.getSelectName());
/* 221:    */       }
/* 222:211 */       q.appendScalarSelectTokens(this.pathExpressionParser.getWhereColumns());
/* 223:212 */       q.addSelectScalar(this.pathExpressionParser.getWhereColumnType());
/* 224:213 */       this.pathExpressionParser.addAssociation(q);
/* 225:    */       
/* 226:215 */       this.ready = false;
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean aggregateHasArgs(String funcToken, QueryTranslatorImpl q)
/* 231:    */   {
/* 232:220 */     return getFunction(funcToken, q).hasArguments();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public boolean aggregateFuncNoArgsHasParenthesis(String funcToken, QueryTranslatorImpl q)
/* 236:    */   {
/* 237:224 */     return getFunction(funcToken, q).hasParenthesesIfNoArguments();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Type aggregateType(List funcTokenList, Type type, QueryTranslatorImpl q)
/* 241:    */     throws QueryException
/* 242:    */   {
/* 243:228 */     Type retType = type;
/* 244:230 */     for (int i = funcTokenList.size() - 1; i >= 0; i--)
/* 245:    */     {
/* 246:231 */       Type argType = retType;
/* 247:232 */       String funcToken = (String)funcTokenList.get(i);
/* 248:233 */       retType = getFunction(funcToken, q).getReturnType(argType, q.getFactory());
/* 249:    */     }
/* 250:235 */     return retType;
/* 251:    */   }
/* 252:    */   
/* 253:    */   private SQLFunction getFunction(String name, QueryTranslatorImpl q)
/* 254:    */   {
/* 255:239 */     return q.getFactory().getSqlFunctionRegistry().findSQLFunction(name);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void start(QueryTranslatorImpl q)
/* 259:    */   {
/* 260:243 */     this.ready = true;
/* 261:244 */     this.first = true;
/* 262:245 */     this.aggregate = false;
/* 263:246 */     this.afterNew = false;
/* 264:247 */     this.insideNew = false;
/* 265:248 */     this.holderClass = null;
/* 266:249 */     this.aggregateFuncTokenList.clear();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void end(QueryTranslatorImpl q) {}
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.SelectParser
 * JD-Core Version:    0.7.0.1
 */