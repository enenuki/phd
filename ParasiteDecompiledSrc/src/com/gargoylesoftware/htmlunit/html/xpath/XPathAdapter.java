/*   1:    */ package com.gargoylesoftware.htmlunit.html.xpath;
/*   2:    */ 
/*   3:    */ import java.util.regex.Matcher;
/*   4:    */ import java.util.regex.Pattern;
/*   5:    */ import javax.xml.transform.ErrorListener;
/*   6:    */ import javax.xml.transform.SourceLocator;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xml.utils.DefaultErrorHandler;
/*   9:    */ import org.apache.xml.utils.PrefixResolver;
/*  10:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.ExpressionNode;
/*  13:    */ import org.apache.xpath.XPathContext;
/*  14:    */ import org.apache.xpath.compiler.Compiler;
/*  15:    */ import org.apache.xpath.compiler.FunctionTable;
/*  16:    */ import org.apache.xpath.compiler.XPathParser;
/*  17:    */ import org.apache.xpath.objects.XObject;
/*  18:    */ import org.apache.xpath.res.XPATHMessages;
/*  19:    */ 
/*  20:    */ class XPathAdapter
/*  21:    */ {
/*  22: 44 */   private static final Pattern PREPROCESS_XPATH_PATTERN = Pattern.compile("(@[a-zA-Z]+)");
/*  23:    */   private Expression mainExp_;
/*  24:    */   private FunctionTable funcTable_;
/*  25:    */   
/*  26:    */   private void initFunctionTable()
/*  27:    */   {
/*  28: 53 */     this.funcTable_ = new FunctionTable();
/*  29: 54 */     this.funcTable_.installFunction("lower-case", LowerCaseFunction.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   XPathAdapter(String exprString, SourceLocator locator, PrefixResolver prefixResolver, ErrorListener errorListener, boolean caseSensitive)
/*  33:    */     throws TransformerException
/*  34:    */   {
/*  35: 69 */     initFunctionTable();
/*  36: 71 */     if (errorListener == null) {
/*  37: 72 */       errorListener = new DefaultErrorHandler();
/*  38:    */     }
/*  39: 75 */     if (!caseSensitive) {
/*  40: 76 */       exprString = preProcessXPath(exprString);
/*  41:    */     }
/*  42: 79 */     XPathParser parser = new XPathParser(errorListener, locator);
/*  43: 80 */     Compiler compiler = new Compiler(errorListener, locator, this.funcTable_);
/*  44:    */     
/*  45: 82 */     parser.initXPath(compiler, exprString, prefixResolver);
/*  46:    */     
/*  47: 84 */     Expression expr = compiler.compile(0);
/*  48:    */     
/*  49: 86 */     this.mainExp_ = expr;
/*  50: 88 */     if ((locator != null) && ((locator instanceof ExpressionNode))) {
/*  51: 89 */       expr.exprSetParent((ExpressionNode)locator);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static String preProcessXPath(String xpath)
/*  56:    */   {
/*  57:101 */     char[] charArray = xpath.toCharArray();
/*  58:102 */     processOutsideBrackets(charArray);
/*  59:103 */     xpath = new String(charArray);
/*  60:    */     
/*  61:105 */     Matcher matcher = PREPROCESS_XPATH_PATTERN.matcher(xpath);
/*  62:106 */     while (matcher.find())
/*  63:    */     {
/*  64:107 */       String attribute = matcher.group(1);
/*  65:108 */       xpath = xpath.replace(attribute, attribute.toLowerCase());
/*  66:    */     }
/*  67:110 */     return xpath;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static void processOutsideBrackets(char[] array)
/*  71:    */   {
/*  72:118 */     int length = array.length;
/*  73:119 */     int insideBrackets = 0;
/*  74:120 */     for (int i = 0; i < length; i++)
/*  75:    */     {
/*  76:121 */       char ch = array[i];
/*  77:122 */       switch (ch)
/*  78:    */       {
/*  79:    */       case '(': 
/*  80:    */       case '[': 
/*  81:125 */         insideBrackets++;
/*  82:126 */         break;
/*  83:    */       case ')': 
/*  84:    */       case ']': 
/*  85:130 */         insideBrackets--;
/*  86:131 */         break;
/*  87:    */       default: 
/*  88:134 */         if (insideBrackets == 0) {
/*  89:135 */           array[i] = Character.toLowerCase(ch);
/*  90:    */         }
/*  91:    */         break;
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   XObject execute(XPathContext xpathContext, int contextNode, PrefixResolver namespaceContext)
/*  97:    */     throws TransformerException
/*  98:    */   {
/*  99:152 */     xpathContext.pushNamespaceContext(namespaceContext);
/* 100:    */     
/* 101:154 */     xpathContext.pushCurrentNodeAndExpression(contextNode, contextNode);
/* 102:    */     
/* 103:156 */     XObject xobj = null;
/* 104:    */     try
/* 105:    */     {
/* 106:159 */       xobj = this.mainExp_.execute(xpathContext);
/* 107:    */     }
/* 108:    */     catch (TransformerException te)
/* 109:    */     {
/* 110:162 */       te.setLocator(this.mainExp_);
/* 111:163 */       ErrorListener el = xpathContext.getErrorListener();
/* 112:164 */       if (null != el) {
/* 113:165 */         el.error(te);
/* 114:    */       } else {
/* 115:168 */         throw te;
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:172 */       while ((e instanceof WrappedRuntimeException)) {
/* 121:173 */         e = ((WrappedRuntimeException)e).getException();
/* 122:    */       }
/* 123:175 */       String msg = e.getMessage();
/* 124:177 */       if ((msg == null) || (msg.length() == 0)) {
/* 125:178 */         msg = XPATHMessages.createXPATHMessage("ER_XPATH_ERROR", null);
/* 126:    */       }
/* 127:180 */       TransformerException te = new TransformerException(msg, this.mainExp_, e);
/* 128:181 */       ErrorListener el = xpathContext.getErrorListener();
/* 129:182 */       if (null != el) {
/* 130:183 */         el.fatalError(te);
/* 131:    */       } else {
/* 132:186 */         throw te;
/* 133:    */       }
/* 134:    */     }
/* 135:    */     finally
/* 136:    */     {
/* 137:190 */       xpathContext.popNamespaceContext();
/* 138:191 */       xpathContext.popCurrentNodeAndExpression();
/* 139:    */     }
/* 140:194 */     return xobj;
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.xpath.XPathAdapter
 * JD-Core Version:    0.7.0.1
 */