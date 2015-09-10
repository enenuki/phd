/*   1:    */ package org.hibernate.sql.ordering.antlr;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.CommonAST;
/*   5:    */ import antlr.ParserSharedInputState;
/*   6:    */ import antlr.TokenStream;
/*   7:    */ import antlr.collections.AST;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.dialect.function.SQLFunction;
/*  11:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class OrderByFragmentParser
/*  17:    */   extends GeneratedOrderByFragmentParser
/*  18:    */ {
/*  19: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, OrderByFragmentParser.class.getName());
/*  20:    */   private final TranslationContext context;
/*  21:    */   
/*  22:    */   public OrderByFragmentParser(TokenStream lexer, TranslationContext context)
/*  23:    */   {
/*  24: 51 */     super(lexer);
/*  25: 52 */     super.setASTFactory(new Factory());
/*  26: 53 */     this.context = context;
/*  27:    */   }
/*  28:    */   
/*  29: 59 */   private int traceDepth = 0;
/*  30:    */   
/*  31:    */   public void traceIn(String ruleName)
/*  32:    */   {
/*  33: 64 */     if (this.inputState.guessing > 0) {
/*  34: 65 */       return;
/*  35:    */     }
/*  36: 67 */     String prefix = StringHelper.repeat('-', this.traceDepth++ * 2) + "-> ";
/*  37: 68 */     LOG.trace(prefix + ruleName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void traceOut(String ruleName)
/*  41:    */   {
/*  42: 73 */     if (this.inputState.guessing > 0) {
/*  43: 74 */       return;
/*  44:    */     }
/*  45: 76 */     String prefix = "<-" + StringHelper.repeat('-', --this.traceDepth * 2) + " ";
/*  46: 77 */     LOG.trace(prefix + ruleName);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void trace(String msg)
/*  50:    */   {
/*  51: 85 */     LOG.trace(msg);
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected AST quotedIdentifier(AST ident)
/*  55:    */   {
/*  56: 93 */     return getASTFactory().create(17, "$PlaceHolder$." + this.context.getDialect().quote(new StringBuilder().append('`').append(ident.getText()).append('`').toString()));
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected AST quotedString(AST ident)
/*  60:    */   {
/*  61:104 */     return getASTFactory().create(17, this.context.getDialect().quote(ident.getText()));
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected boolean isFunctionName(AST ast)
/*  65:    */   {
/*  66:112 */     AST child = ast.getFirstChild();
/*  67:114 */     if ((child != null) && ("{param list}".equals(child.getText()))) {
/*  68:115 */       return true;
/*  69:    */     }
/*  70:118 */     SQLFunction function = this.context.getSqlFunctionRegistry().findSQLFunction(ast.getText());
/*  71:119 */     if (function == null) {
/*  72:120 */       return false;
/*  73:    */     }
/*  74:125 */     return !function.hasParenthesesIfNoArguments();
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected AST resolveFunction(AST ast)
/*  78:    */   {
/*  79:133 */     AST child = ast.getFirstChild();
/*  80:134 */     if (child != null)
/*  81:    */     {
/*  82:135 */       assert ("{param list}".equals(child.getText()));
/*  83:136 */       child = child.getFirstChild();
/*  84:    */     }
/*  85:139 */     String functionName = ast.getText();
/*  86:140 */     SQLFunction function = this.context.getSqlFunctionRegistry().findSQLFunction(functionName);
/*  87:141 */     if (function == null)
/*  88:    */     {
/*  89:142 */       String text = functionName;
/*  90:143 */       if (child != null)
/*  91:    */       {
/*  92:144 */         text = text + '(';
/*  93:145 */         while (child != null)
/*  94:    */         {
/*  95:146 */           text = text + child.getText();
/*  96:147 */           child = child.getNextSibling();
/*  97:148 */           if (child != null) {
/*  98:149 */             text = text + ", ";
/*  99:    */           }
/* 100:    */         }
/* 101:152 */         text = text + ')';
/* 102:    */       }
/* 103:154 */       return getASTFactory().create(17, text);
/* 104:    */     }
/* 105:157 */     ArrayList expressions = new ArrayList();
/* 106:158 */     while (child != null)
/* 107:    */     {
/* 108:159 */       expressions.add(child.getText());
/* 109:160 */       child = child.getNextSibling();
/* 110:    */     }
/* 111:162 */     String text = function.render(null, expressions, this.context.getSessionFactory());
/* 112:163 */     return getASTFactory().create(17, text);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected AST resolveIdent(AST ident)
/* 116:    */   {
/* 117:172 */     String text = ident.getText();
/* 118:    */     String[] replacements;
/* 119:    */     try
/* 120:    */     {
/* 121:175 */       replacements = this.context.getColumnMapper().map(text);
/* 122:    */     }
/* 123:    */     catch (Throwable t)
/* 124:    */     {
/* 125:178 */       replacements = null;
/* 126:    */     }
/* 127:181 */     if ((replacements == null) || (replacements.length == 0)) {
/* 128:182 */       return getASTFactory().create(17, "$PlaceHolder$." + text);
/* 129:    */     }
/* 130:184 */     if (replacements.length == 1) {
/* 131:185 */       return getASTFactory().create(17, "$PlaceHolder$." + replacements[0]);
/* 132:    */     }
/* 133:188 */     AST root = getASTFactory().create(10, "{ident list}");
/* 134:189 */     for (int i = 0; i < replacements.length; i++)
/* 135:    */     {
/* 136:190 */       String identText = "$PlaceHolder$." + replacements[i];
/* 137:191 */       root.addChild(getASTFactory().create(17, identText));
/* 138:    */     }
/* 139:193 */     return root;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected AST postProcessSortSpecification(AST sortSpec)
/* 143:    */   {
/* 144:202 */     assert (5 == sortSpec.getType());
/* 145:203 */     SortSpecification sortSpecification = (SortSpecification)sortSpec;
/* 146:204 */     AST sortKey = sortSpecification.getSortKey();
/* 147:205 */     if (10 == sortKey.getFirstChild().getType())
/* 148:    */     {
/* 149:206 */       AST identList = sortKey.getFirstChild();
/* 150:207 */       AST ident = identList.getFirstChild();
/* 151:208 */       AST holder = new CommonAST();
/* 152:    */       do
/* 153:    */       {
/* 154:210 */         holder.addChild(createSortSpecification(ident, sortSpecification.getCollation(), sortSpecification.getOrdering()));
/* 155:    */         
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:217 */         ident = ident.getNextSibling();
/* 162:218 */       } while (ident != null);
/* 163:219 */       sortSpec = holder.getFirstChild();
/* 164:    */     }
/* 165:221 */     return sortSpec;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private SortSpecification createSortSpecification(AST ident, CollationSpecification collationSpecification, OrderingSpecification orderingSpecification)
/* 169:    */   {
/* 170:228 */     AST sortSpecification = getASTFactory().create(5, "{{sort specification}}");
/* 171:229 */     AST sortKey = getASTFactory().create(7, "{{sort key}}");
/* 172:230 */     AST newIdent = getASTFactory().create(ident.getType(), ident.getText());
/* 173:231 */     sortKey.setFirstChild(newIdent);
/* 174:232 */     sortSpecification.setFirstChild(sortKey);
/* 175:233 */     if (collationSpecification != null) {
/* 176:234 */       sortSpecification.addChild(collationSpecification);
/* 177:    */     }
/* 178:236 */     if (orderingSpecification != null) {
/* 179:237 */       sortSpecification.addChild(orderingSpecification);
/* 180:    */     }
/* 181:239 */     return (SortSpecification)sortSpecification;
/* 182:    */   }
/* 183:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.OrderByFragmentParser
 * JD-Core Version:    0.7.0.1
 */