/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.math.BigInteger;
/*   7:    */ import java.text.DecimalFormat;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.QueryException;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*  15:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  16:    */ import org.hibernate.hql.internal.ast.InvalidPathException;
/*  17:    */ import org.hibernate.hql.internal.ast.tree.DotNode;
/*  18:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.IdentNode;
/*  20:    */ import org.hibernate.internal.CoreMessageLogger;
/*  21:    */ import org.hibernate.internal.util.ReflectHelper;
/*  22:    */ import org.hibernate.persister.entity.Queryable;
/*  23:    */ import org.hibernate.type.LiteralType;
/*  24:    */ import org.hibernate.type.Type;
/*  25:    */ import org.hibernate.type.TypeResolver;
/*  26:    */ import org.jboss.logging.Logger;
/*  27:    */ 
/*  28:    */ public class LiteralProcessor
/*  29:    */   implements HqlSqlTokenTypes
/*  30:    */ {
/*  31:    */   public static final int EXACT = 0;
/*  32:    */   public static final int APPROXIMATE = 1;
/*  33: 76 */   public static int DECIMAL_LITERAL_FORMAT = 0;
/*  34: 78 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, LiteralProcessor.class.getName());
/*  35:    */   private HqlSqlWalker walker;
/*  36:    */   
/*  37:    */   public LiteralProcessor(HqlSqlWalker hqlSqlWalker)
/*  38:    */   {
/*  39: 83 */     this.walker = hqlSqlWalker;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isAlias(String alias)
/*  43:    */   {
/*  44: 87 */     FromClause from = this.walker.getCurrentFromClause();
/*  45: 88 */     while (from.isSubQuery())
/*  46:    */     {
/*  47: 89 */       if (from.containsClassAlias(alias)) {
/*  48: 90 */         return true;
/*  49:    */       }
/*  50: 92 */       from = from.getParentFromClause();
/*  51:    */     }
/*  52: 94 */     return from.containsClassAlias(alias);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void processConstant(AST constant, boolean resolveIdent)
/*  56:    */     throws SemanticException
/*  57:    */   {
/*  58: 99 */     boolean isIdent = (constant.getType() == 126) || (constant.getType() == 93);
/*  59:100 */     if ((resolveIdent) && (isIdent) && (isAlias(constant.getText())))
/*  60:    */     {
/*  61:101 */       IdentNode ident = (IdentNode)constant;
/*  62:    */       
/*  63:103 */       ident.resolve(false, true);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:106 */       Queryable queryable = this.walker.getSessionFactoryHelper().findQueryableUsingImports(constant.getText());
/*  68:107 */       if ((isIdent) && (queryable != null)) {
/*  69:108 */         constant.setText(queryable.getDiscriminatorSQLValue());
/*  70:    */       } else {
/*  71:112 */         processLiteral(constant);
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void lookupConstant(DotNode node)
/*  77:    */     throws SemanticException
/*  78:    */   {
/*  79:118 */     String text = ASTUtil.getPathText(node);
/*  80:119 */     Queryable persister = this.walker.getSessionFactoryHelper().findQueryableUsingImports(text);
/*  81:120 */     if (persister != null)
/*  82:    */     {
/*  83:122 */       String discrim = persister.getDiscriminatorSQLValue();
/*  84:123 */       node.setDataType(persister.getDiscriminatorType());
/*  85:124 */       if (("null".equals(discrim)) || ("not null".equals(discrim))) {
/*  86:124 */         throw new InvalidPathException("subclass test not allowed for null or not null discriminator: '" + text + "'");
/*  87:    */       }
/*  88:127 */       setSQLValue(node, text, discrim);
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:130 */       Object value = ReflectHelper.getConstantValue(text);
/*  93:131 */       if (value == null) {
/*  94:131 */         throw new InvalidPathException("Invalid path: '" + text + "'");
/*  95:    */       }
/*  96:132 */       setConstantValue(node, text, value);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void setSQLValue(DotNode node, String text, String value)
/* 101:    */   {
/* 102:137 */     LOG.debugf("setSQLValue() %s -> %s", text, value);
/* 103:138 */     node.setFirstChild(null);
/* 104:139 */     node.setType(142);
/* 105:140 */     node.setText(value);
/* 106:141 */     node.setResolvedConstant(text);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private void setConstantValue(DotNode node, String text, Object value)
/* 110:    */   {
/* 111:145 */     if (LOG.isDebugEnabled()) {
/* 112:146 */       LOG.debugf("setConstantValue() %s -> %s %s", text, value, value.getClass().getName());
/* 113:    */     }
/* 114:148 */     node.setFirstChild(null);
/* 115:149 */     if ((value instanceof String)) {
/* 116:150 */       node.setType(125);
/* 117:152 */     } else if ((value instanceof Character)) {
/* 118:153 */       node.setType(125);
/* 119:155 */     } else if ((value instanceof Byte)) {
/* 120:156 */       node.setType(124);
/* 121:158 */     } else if ((value instanceof Short)) {
/* 122:159 */       node.setType(124);
/* 123:161 */     } else if ((value instanceof Integer)) {
/* 124:162 */       node.setType(124);
/* 125:164 */     } else if ((value instanceof Long)) {
/* 126:165 */       node.setType(97);
/* 127:167 */     } else if ((value instanceof Double)) {
/* 128:168 */       node.setType(95);
/* 129:170 */     } else if ((value instanceof Float)) {
/* 130:171 */       node.setType(96);
/* 131:    */     } else {
/* 132:174 */       node.setType(94);
/* 133:    */     }
/* 134:    */     Type type;
/* 135:    */     try
/* 136:    */     {
/* 137:178 */       type = this.walker.getSessionFactoryHelper().getFactory().getTypeResolver().heuristicType(value.getClass().getName());
/* 138:    */     }
/* 139:    */     catch (MappingException me)
/* 140:    */     {
/* 141:181 */       throw new QueryException(me);
/* 142:    */     }
/* 143:183 */     if (type == null) {
/* 144:184 */       throw new QueryException("Could not determine type of: " + node.getText());
/* 145:    */     }
/* 146:    */     try
/* 147:    */     {
/* 148:187 */       LiteralType literalType = (LiteralType)type;
/* 149:188 */       Dialect dialect = this.walker.getSessionFactoryHelper().getFactory().getDialect();
/* 150:189 */       node.setText(literalType.objectToSQLString(value, dialect));
/* 151:    */     }
/* 152:    */     catch (Exception e)
/* 153:    */     {
/* 154:192 */       throw new QueryException("Could not format constant value to SQL literal: " + node.getText(), e);
/* 155:    */     }
/* 156:194 */     node.setDataType(type);
/* 157:195 */     node.setResolvedConstant(text);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void processBoolean(AST constant)
/* 161:    */   {
/* 162:201 */     String replacement = (String)this.walker.getTokenReplacements().get(constant.getText());
/* 163:202 */     if (replacement != null)
/* 164:    */     {
/* 165:203 */       constant.setText(replacement);
/* 166:    */     }
/* 167:    */     else
/* 168:    */     {
/* 169:206 */       boolean bool = "true".equals(constant.getText().toLowerCase());
/* 170:207 */       Dialect dialect = this.walker.getSessionFactoryHelper().getFactory().getDialect();
/* 171:208 */       constant.setText(dialect.toBooleanValueString(bool));
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private void processLiteral(AST constant)
/* 176:    */   {
/* 177:213 */     String replacement = (String)this.walker.getTokenReplacements().get(constant.getText());
/* 178:214 */     if (replacement != null)
/* 179:    */     {
/* 180:215 */       if (LOG.isDebugEnabled()) {
/* 181:216 */         LOG.debugf("processConstant() : Replacing '%s' with '%s'", constant.getText(), replacement);
/* 182:    */       }
/* 183:218 */       constant.setText(replacement);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void processNumeric(AST literal)
/* 188:    */   {
/* 189:223 */     if ((literal.getType() == 124) || (literal.getType() == 97) || (literal.getType() == 98)) {
/* 190:226 */       literal.setText(determineIntegerRepresentation(literal.getText(), literal.getType()));
/* 191:227 */     } else if ((literal.getType() == 96) || (literal.getType() == 95) || (literal.getType() == 99)) {
/* 192:230 */       literal.setText(determineDecimalRepresentation(literal.getText(), literal.getType()));
/* 193:    */     } else {
/* 194:231 */       LOG.unexpectedLiteralTokenType(literal.getType());
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   private String determineIntegerRepresentation(String text, int type)
/* 199:    */   {
/* 200:    */     try
/* 201:    */     {
/* 202:236 */       if (type == 98)
/* 203:    */       {
/* 204:237 */         String literalValue = text;
/* 205:238 */         if ((literalValue.endsWith("bi")) || (literalValue.endsWith("BI"))) {
/* 206:239 */           literalValue = literalValue.substring(0, literalValue.length() - 2);
/* 207:    */         }
/* 208:241 */         return new BigInteger(literalValue).toString();
/* 209:    */       }
/* 210:243 */       if (type == 124) {
/* 211:    */         try
/* 212:    */         {
/* 213:245 */           return Integer.valueOf(text).toString();
/* 214:    */         }
/* 215:    */         catch (NumberFormatException e)
/* 216:    */         {
/* 217:248 */           LOG.tracev("Could not format incoming text [{0}] as a NUM_INT; assuming numeric overflow and attempting as NUM_LONG", text);
/* 218:    */         }
/* 219:    */       }
/* 220:253 */       String literalValue = text;
/* 221:254 */       if ((literalValue.endsWith("l")) || (literalValue.endsWith("L"))) {
/* 222:255 */         literalValue = literalValue.substring(0, literalValue.length() - 1);
/* 223:    */       }
/* 224:257 */       return Long.valueOf(literalValue).toString();
/* 225:    */     }
/* 226:    */     catch (Throwable t)
/* 227:    */     {
/* 228:260 */       throw new HibernateException("Could not parse literal [" + text + "] as integer", t);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String determineDecimalRepresentation(String text, int type)
/* 233:    */   {
/* 234:265 */     String literalValue = text;
/* 235:266 */     if (type == 96)
/* 236:    */     {
/* 237:267 */       if ((literalValue.endsWith("f")) || (literalValue.endsWith("F"))) {
/* 238:268 */         literalValue = literalValue.substring(0, literalValue.length() - 1);
/* 239:    */       }
/* 240:    */     }
/* 241:271 */     else if (type == 95)
/* 242:    */     {
/* 243:272 */       if ((literalValue.endsWith("d")) || (literalValue.endsWith("D"))) {
/* 244:273 */         literalValue = literalValue.substring(0, literalValue.length() - 1);
/* 245:    */       }
/* 246:    */     }
/* 247:276 */     else if ((type == 99) && (
/* 248:277 */       (literalValue.endsWith("bd")) || (literalValue.endsWith("BD")))) {
/* 249:278 */       literalValue = literalValue.substring(0, literalValue.length() - 2);
/* 250:    */     }
/* 251:282 */     BigDecimal number = null;
/* 252:    */     try
/* 253:    */     {
/* 254:284 */       number = new BigDecimal(literalValue);
/* 255:    */     }
/* 256:    */     catch (Throwable t)
/* 257:    */     {
/* 258:287 */       throw new HibernateException("Could not parse literal [" + text + "] as big-decimal", t);
/* 259:    */     }
/* 260:290 */     return formatters[DECIMAL_LITERAL_FORMAT].format(number);
/* 261:    */   }
/* 262:    */   
/* 263:    */   private static abstract interface DecimalFormatter
/* 264:    */   {
/* 265:    */     public abstract String format(BigDecimal paramBigDecimal);
/* 266:    */   }
/* 267:    */   
/* 268:    */   private static class ExactDecimalFormatter
/* 269:    */     implements LiteralProcessor.DecimalFormatter
/* 270:    */   {
/* 271:    */     public String format(BigDecimal number)
/* 272:    */     {
/* 273:300 */       return number.toString();
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private static class ApproximateDecimalFormatter
/* 278:    */     implements LiteralProcessor.DecimalFormatter
/* 279:    */   {
/* 280:    */     private static final String FORMAT_STRING = "#0.0E0";
/* 281:    */     
/* 282:    */     public String format(BigDecimal number)
/* 283:    */     {
/* 284:    */       try
/* 285:    */       {
/* 286:311 */         DecimalFormat jdkFormatter = new DecimalFormat("#0.0E0");
/* 287:312 */         jdkFormatter.setMinimumIntegerDigits(1);
/* 288:313 */         jdkFormatter.setMaximumFractionDigits(2147483647);
/* 289:314 */         return jdkFormatter.format(number);
/* 290:    */       }
/* 291:    */       catch (Throwable t)
/* 292:    */       {
/* 293:317 */         throw new HibernateException("Unable to format decimal literal in approximate format [" + number.toString() + "]", t);
/* 294:    */       }
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:322 */   private static final DecimalFormatter[] formatters = { new ExactDecimalFormatter(null), new ApproximateDecimalFormatter(null) };
/* 299:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.LiteralProcessor
 * JD-Core Version:    0.7.0.1
 */