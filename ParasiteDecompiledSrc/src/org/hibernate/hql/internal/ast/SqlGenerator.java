/*   1:    */ package org.hibernate.hql.internal.ast;
/*   2:    */ 
/*   3:    */ import antlr.RecognitionException;
/*   4:    */ import antlr.TreeParserSharedInputState;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import org.hibernate.QueryException;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.dialect.function.SQLFunction;
/*  13:    */ import org.hibernate.engine.internal.JoinSequence;
/*  14:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  15:    */ import org.hibernate.hql.internal.antlr.SqlGeneratorBase;
/*  16:    */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*  17:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  18:    */ import org.hibernate.hql.internal.ast.tree.FunctionNode;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.Node;
/*  20:    */ import org.hibernate.hql.internal.ast.tree.ParameterContainer;
/*  21:    */ import org.hibernate.hql.internal.ast.tree.ParameterNode;
/*  22:    */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  23:    */ import org.hibernate.internal.CoreMessageLogger;
/*  24:    */ import org.hibernate.internal.util.StringHelper;
/*  25:    */ import org.hibernate.param.ParameterSpecification;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class SqlGenerator
/*  30:    */   extends SqlGeneratorBase
/*  31:    */   implements ErrorReporter
/*  32:    */ {
/*  33: 61 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SqlGenerator.class.getName());
/*  34: 63 */   public static boolean REGRESSION_STYLE_CROSS_JOINS = false;
/*  35: 72 */   private SqlWriter writer = new DefaultWriter();
/*  36:    */   private ParseErrorHandler parseErrorHandler;
/*  37:    */   private SessionFactoryImplementor sessionFactory;
/*  38: 76 */   private LinkedList<SqlWriter> outputStack = new LinkedList();
/*  39: 77 */   private final ASTPrinter printer = new ASTPrinter(SqlTokenTypes.class);
/*  40: 78 */   private List collectedParameters = new ArrayList();
/*  41: 83 */   private int traceDepth = 0;
/*  42:    */   
/*  43:    */   public void traceIn(String ruleName, AST tree)
/*  44:    */   {
/*  45: 87 */     if (!LOG.isTraceEnabled()) {
/*  46: 87 */       return;
/*  47:    */     }
/*  48: 88 */     if (this.inputState.guessing > 0) {
/*  49: 88 */       return;
/*  50:    */     }
/*  51: 89 */     String prefix = StringHelper.repeat('-', this.traceDepth++ * 2) + "-> ";
/*  52: 90 */     String traceText = ruleName + " (" + buildTraceNodeName(tree) + ")";
/*  53: 91 */     LOG.trace(prefix + traceText);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private String buildTraceNodeName(AST tree)
/*  57:    */   {
/*  58: 95 */     return tree.getText() + " [" + this.printer.getTokenTypeName(tree.getType()) + "]";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void traceOut(String ruleName, AST tree)
/*  62:    */   {
/*  63:102 */     if (!LOG.isTraceEnabled()) {
/*  64:102 */       return;
/*  65:    */     }
/*  66:103 */     if (this.inputState.guessing > 0) {
/*  67:103 */       return;
/*  68:    */     }
/*  69:104 */     String prefix = "<-" + StringHelper.repeat('-', --this.traceDepth * 2) + " ";
/*  70:105 */     LOG.trace(prefix + ruleName);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List getCollectedParameters()
/*  74:    */   {
/*  75:109 */     return this.collectedParameters;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void out(String s)
/*  79:    */   {
/*  80:114 */     this.writer.clause(s);
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void out(AST n)
/*  84:    */   {
/*  85:119 */     if ((n instanceof Node)) {
/*  86:120 */       out(((Node)n).getRenderText(this.sessionFactory));
/*  87:    */     } else {
/*  88:123 */       super.out(n);
/*  89:    */     }
/*  90:126 */     if ((n instanceof ParameterNode))
/*  91:    */     {
/*  92:127 */       this.collectedParameters.add(((ParameterNode)n).getHqlParameterSpecification());
/*  93:    */     }
/*  94:129 */     else if (((n instanceof ParameterContainer)) && 
/*  95:130 */       (((ParameterContainer)n).hasEmbeddedParameters()))
/*  96:    */     {
/*  97:131 */       ParameterSpecification[] specifications = ((ParameterContainer)n).getEmbeddedParameters();
/*  98:132 */       if (specifications != null) {
/*  99:133 */         this.collectedParameters.addAll(Arrays.asList(specifications));
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void commaBetweenParameters(String comma)
/* 105:    */   {
/* 106:141 */     this.writer.commaBetweenParameters(comma);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void reportError(RecognitionException e)
/* 110:    */   {
/* 111:146 */     this.parseErrorHandler.reportError(e);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void reportError(String s)
/* 115:    */   {
/* 116:151 */     this.parseErrorHandler.reportError(s);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void reportWarning(String s)
/* 120:    */   {
/* 121:156 */     this.parseErrorHandler.reportWarning(s);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public ParseErrorHandler getParseErrorHandler()
/* 125:    */   {
/* 126:160 */     return this.parseErrorHandler;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public SqlGenerator(SessionFactoryImplementor sfi)
/* 130:    */   {
/* 131:165 */     this.parseErrorHandler = new ErrorCounter();
/* 132:166 */     this.sessionFactory = sfi;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getSQL()
/* 136:    */   {
/* 137:170 */     return getStringBuffer().toString();
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected void optionalSpace()
/* 141:    */   {
/* 142:175 */     int c = getLastChar();
/* 143:176 */     switch (c)
/* 144:    */     {
/* 145:    */     case -1: 
/* 146:178 */       return;
/* 147:    */     case 32: 
/* 148:180 */       return;
/* 149:    */     case 41: 
/* 150:182 */       return;
/* 151:    */     case 40: 
/* 152:184 */       return;
/* 153:    */     }
/* 154:186 */     out(" ");
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void beginFunctionTemplate(AST node, AST nameNode)
/* 158:    */   {
/* 159:194 */     FunctionNode functionNode = (FunctionNode)node;
/* 160:195 */     SQLFunction sqlFunction = functionNode.getSQLFunction();
/* 161:196 */     if (sqlFunction == null)
/* 162:    */     {
/* 163:198 */       super.beginFunctionTemplate(node, nameNode);
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:202 */       this.outputStack.addFirst(this.writer);
/* 168:203 */       this.writer = new FunctionArguments();
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void endFunctionTemplate(AST node)
/* 173:    */   {
/* 174:209 */     FunctionNode functionNode = (FunctionNode)node;
/* 175:210 */     SQLFunction sqlFunction = functionNode.getSQLFunction();
/* 176:211 */     if (sqlFunction == null)
/* 177:    */     {
/* 178:212 */       super.endFunctionTemplate(node);
/* 179:    */     }
/* 180:    */     else
/* 181:    */     {
/* 182:215 */       Type functionType = functionNode.getFirstArgumentType();
/* 183:    */       
/* 184:217 */       FunctionArguments functionArguments = (FunctionArguments)this.writer;
/* 185:218 */       this.writer = ((SqlWriter)this.outputStack.removeFirst());
/* 186:219 */       out(sqlFunction.render(functionType, functionArguments.getArgs(), this.sessionFactory));
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   static abstract interface SqlWriter
/* 191:    */   {
/* 192:    */     public abstract void clause(String paramString);
/* 193:    */     
/* 194:    */     public abstract void commaBetweenParameters(String paramString);
/* 195:    */   }
/* 196:    */   
/* 197:    */   class FunctionArguments
/* 198:    */     implements SqlGenerator.SqlWriter
/* 199:    */   {
/* 200:    */     private int argInd;
/* 201:247 */     private final List<String> args = new ArrayList(3);
/* 202:    */     
/* 203:    */     FunctionArguments() {}
/* 204:    */     
/* 205:    */     public void clause(String clause)
/* 206:    */     {
/* 207:250 */       if (this.argInd == this.args.size()) {
/* 208:251 */         this.args.add(clause);
/* 209:    */       } else {
/* 210:254 */         this.args.set(this.argInd, (String)this.args.get(this.argInd) + clause);
/* 211:    */       }
/* 212:    */     }
/* 213:    */     
/* 214:    */     public void commaBetweenParameters(String comma)
/* 215:    */     {
/* 216:259 */       this.argInd += 1;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public List getArgs()
/* 220:    */     {
/* 221:263 */       return this.args;
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   class DefaultWriter
/* 226:    */     implements SqlGenerator.SqlWriter
/* 227:    */   {
/* 228:    */     DefaultWriter() {}
/* 229:    */     
/* 230:    */     public void clause(String clause)
/* 231:    */     {
/* 232:272 */       SqlGenerator.this.getStringBuffer().append(clause);
/* 233:    */     }
/* 234:    */     
/* 235:    */     public void commaBetweenParameters(String comma)
/* 236:    */     {
/* 237:276 */       SqlGenerator.this.getStringBuffer().append(comma);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public static void panic()
/* 242:    */   {
/* 243:281 */     throw new QueryException("TreeWalker: panic");
/* 244:    */   }
/* 245:    */   
/* 246:    */   protected void fromFragmentSeparator(AST a)
/* 247:    */   {
/* 248:287 */     AST next = a.getNextSibling();
/* 249:288 */     if ((next == null) || (!hasText(a))) {
/* 250:289 */       return;
/* 251:    */     }
/* 252:292 */     FromElement left = (FromElement)a;
/* 253:293 */     FromElement right = (FromElement)next;
/* 254:304 */     while ((right != null) && (!hasText(right))) {
/* 255:305 */       right = (FromElement)right.getNextSibling();
/* 256:    */     }
/* 257:307 */     if (right == null) {
/* 258:308 */       return;
/* 259:    */     }
/* 260:312 */     if (!hasText(right)) {
/* 261:313 */       return;
/* 262:    */     }
/* 263:316 */     if ((right.getRealOrigin() == left) || ((right.getRealOrigin() != null) && (right.getRealOrigin() == left.getRealOrigin())))
/* 264:    */     {
/* 265:320 */       if ((right.getJoinSequence() != null) && (right.getJoinSequence().isThetaStyle())) {
/* 266:321 */         writeCrossJoinSeparator();
/* 267:    */       } else {
/* 268:324 */         out(" ");
/* 269:    */       }
/* 270:    */     }
/* 271:    */     else {
/* 272:329 */       writeCrossJoinSeparator();
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   private void writeCrossJoinSeparator()
/* 277:    */   {
/* 278:334 */     if (REGRESSION_STYLE_CROSS_JOINS) {
/* 279:335 */       out(", ");
/* 280:    */     } else {
/* 281:338 */       out(this.sessionFactory.getDialect().getCrossJoinSeparator());
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   protected void nestedFromFragment(AST d, AST parent)
/* 286:    */   {
/* 287:346 */     if ((d != null) && (hasText(d)))
/* 288:    */     {
/* 289:347 */       if ((parent != null) && (hasText(parent)))
/* 290:    */       {
/* 291:349 */         FromElement left = (FromElement)parent;
/* 292:350 */         FromElement right = (FromElement)d;
/* 293:351 */         if (right.getRealOrigin() == left)
/* 294:    */         {
/* 295:353 */           if ((right.getJoinSequence() != null) && (right.getJoinSequence().isThetaStyle())) {
/* 296:354 */             out(", ");
/* 297:    */           } else {
/* 298:357 */             out(" ");
/* 299:    */           }
/* 300:    */         }
/* 301:    */         else {
/* 302:363 */           out(", ");
/* 303:    */         }
/* 304:    */       }
/* 305:366 */       out(d);
/* 306:    */     }
/* 307:    */   }
/* 308:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.SqlGenerator
 * JD-Core Version:    0.7.0.1
 */