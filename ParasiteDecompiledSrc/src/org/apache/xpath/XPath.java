/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Vector;
/*   6:    */ import javax.xml.transform.ErrorListener;
/*   7:    */ import javax.xml.transform.SourceLocator;
/*   8:    */ import javax.xml.transform.TransformerException;
/*   9:    */ import org.apache.xml.utils.DefaultErrorHandler;
/*  10:    */ import org.apache.xml.utils.PrefixResolver;
/*  11:    */ import org.apache.xml.utils.SAXSourceLocator;
/*  12:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  13:    */ import org.apache.xpath.compiler.Compiler;
/*  14:    */ import org.apache.xpath.compiler.FunctionTable;
/*  15:    */ import org.apache.xpath.compiler.XPathParser;
/*  16:    */ import org.apache.xpath.objects.XObject;
/*  17:    */ import org.apache.xpath.res.XPATHMessages;
/*  18:    */ import org.w3c.dom.Node;
/*  19:    */ 
/*  20:    */ public class XPath
/*  21:    */   implements Serializable, ExpressionOwner
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = 3976493477939110553L;
/*  24:    */   private Expression m_mainExp;
/*  25: 56 */   private transient FunctionTable m_funcTable = null;
/*  26:    */   String m_patternString;
/*  27:    */   public static final int SELECT = 0;
/*  28:    */   public static final int MATCH = 1;
/*  29:    */   private static final boolean DEBUG_MATCHES = false;
/*  30:    */   public static final double MATCH_SCORE_NONE = (-1.0D / 0.0D);
/*  31:    */   public static final double MATCH_SCORE_QNAME = 0.0D;
/*  32:    */   public static final double MATCH_SCORE_NSWILD = -0.25D;
/*  33:    */   public static final double MATCH_SCORE_NODETEST = -0.5D;
/*  34:    */   public static final double MATCH_SCORE_OTHER = 0.5D;
/*  35:    */   
/*  36:    */   private void initFunctionTable()
/*  37:    */   {
/*  38: 62 */     this.m_funcTable = new FunctionTable();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Expression getExpression()
/*  42:    */   {
/*  43: 73 */     return this.m_mainExp;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  47:    */   {
/*  48: 88 */     this.m_mainExp.fixupVariables(vars, globalsSize);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setExpression(Expression exp)
/*  52:    */   {
/*  53: 99 */     if (null != this.m_mainExp) {
/*  54:100 */       exp.exprSetParent(this.m_mainExp.exprGetParent());
/*  55:    */     }
/*  56:101 */     this.m_mainExp = exp;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public SourceLocator getLocator()
/*  60:    */   {
/*  61:112 */     return this.m_mainExp;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getPatternString()
/*  65:    */   {
/*  66:140 */     return this.m_patternString;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type, ErrorListener errorListener)
/*  70:    */     throws TransformerException
/*  71:    */   {
/*  72:168 */     initFunctionTable();
/*  73:169 */     if (null == errorListener) {
/*  74:170 */       errorListener = new DefaultErrorHandler();
/*  75:    */     }
/*  76:172 */     this.m_patternString = exprString;
/*  77:    */     
/*  78:174 */     XPathParser parser = new XPathParser(errorListener, locator);
/*  79:175 */     Compiler compiler = new Compiler(errorListener, locator, this.m_funcTable);
/*  80:177 */     if (0 == type) {
/*  81:178 */       parser.initXPath(compiler, exprString, prefixResolver);
/*  82:179 */     } else if (1 == type) {
/*  83:180 */       parser.initMatchPattern(compiler, exprString, prefixResolver);
/*  84:    */     } else {
/*  85:182 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[] { Integer.toString(type) }));
/*  86:    */     }
/*  87:185 */     Expression expr = compiler.compile(0);
/*  88:    */     
/*  89:    */ 
/*  90:188 */     setExpression(expr);
/*  91:190 */     if ((null != locator) && ((locator instanceof ExpressionNode))) {
/*  92:192 */       expr.exprSetParent((ExpressionNode)locator);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type, ErrorListener errorListener, FunctionTable aTable)
/*  97:    */     throws TransformerException
/*  98:    */   {
/*  99:217 */     this.m_funcTable = aTable;
/* 100:218 */     if (null == errorListener) {
/* 101:219 */       errorListener = new DefaultErrorHandler();
/* 102:    */     }
/* 103:221 */     this.m_patternString = exprString;
/* 104:    */     
/* 105:223 */     XPathParser parser = new XPathParser(errorListener, locator);
/* 106:224 */     Compiler compiler = new Compiler(errorListener, locator, this.m_funcTable);
/* 107:226 */     if (0 == type) {
/* 108:227 */       parser.initXPath(compiler, exprString, prefixResolver);
/* 109:228 */     } else if (1 == type) {
/* 110:229 */       parser.initMatchPattern(compiler, exprString, prefixResolver);
/* 111:    */     } else {
/* 112:231 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[] { Integer.toString(type) }));
/* 113:    */     }
/* 114:237 */     Expression expr = compiler.compile(0);
/* 115:    */     
/* 116:    */ 
/* 117:240 */     setExpression(expr);
/* 118:242 */     if ((null != locator) && ((locator instanceof ExpressionNode))) {
/* 119:244 */       expr.exprSetParent((ExpressionNode)locator);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public XPath(String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type)
/* 124:    */     throws TransformerException
/* 125:    */   {
/* 126:266 */     this(exprString, locator, prefixResolver, type, null);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public XPath(Expression expr)
/* 130:    */   {
/* 131:278 */     setExpression(expr);
/* 132:279 */     initFunctionTable();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public XObject execute(XPathContext xctxt, Node contextNode, PrefixResolver namespaceContext)
/* 136:    */     throws TransformerException
/* 137:    */   {
/* 138:303 */     return execute(xctxt, xctxt.getDTMHandleFromNode(contextNode), namespaceContext);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public XObject execute(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
/* 142:    */     throws TransformerException
/* 143:    */   {
/* 144:329 */     xctxt.pushNamespaceContext(namespaceContext);
/* 145:    */     
/* 146:331 */     xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/* 147:    */     
/* 148:333 */     XObject xobj = null;
/* 149:    */     try
/* 150:    */     {
/* 151:337 */       xobj = this.m_mainExp.execute(xctxt);
/* 152:    */     }
/* 153:    */     catch (TransformerException te)
/* 154:    */     {
/* 155:341 */       te.setLocator(getLocator());
/* 156:342 */       ErrorListener el = xctxt.getErrorListener();
/* 157:343 */       if (null != el) {
/* 158:345 */         el.error(te);
/* 159:    */       } else {
/* 160:348 */         throw te;
/* 161:    */       }
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:352 */       while ((e instanceof WrappedRuntimeException)) {
/* 166:354 */         e = ((WrappedRuntimeException)e).getException();
/* 167:    */       }
/* 168:358 */       String msg = e.getMessage();
/* 169:360 */       if ((msg == null) || (msg.length() == 0)) {
/* 170:361 */         msg = XPATHMessages.createXPATHMessage("ER_XPATH_ERROR", null);
/* 171:    */       }
/* 172:365 */       TransformerException te = new TransformerException(msg, getLocator(), e);
/* 173:    */       
/* 174:367 */       ErrorListener el = xctxt.getErrorListener();
/* 175:369 */       if (null != el) {
/* 176:371 */         el.fatalError(te);
/* 177:    */       } else {
/* 178:374 */         throw te;
/* 179:    */       }
/* 180:    */     }
/* 181:    */     finally
/* 182:    */     {
/* 183:378 */       xctxt.popNamespaceContext();
/* 184:    */       
/* 185:380 */       xctxt.popCurrentNodeAndExpression();
/* 186:    */     }
/* 187:383 */     return xobj;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean bool(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
/* 191:    */     throws TransformerException
/* 192:    */   {
/* 193:406 */     xctxt.pushNamespaceContext(namespaceContext);
/* 194:    */     
/* 195:408 */     xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/* 196:    */     try
/* 197:    */     {
/* 198:412 */       return this.m_mainExp.bool(xctxt);
/* 199:    */     }
/* 200:    */     catch (TransformerException te)
/* 201:    */     {
/* 202:416 */       te.setLocator(getLocator());
/* 203:417 */       ErrorListener el = xctxt.getErrorListener();
/* 204:418 */       if (null != el) {
/* 205:420 */         el.error(te);
/* 206:    */       } else {
/* 207:423 */         throw te;
/* 208:    */       }
/* 209:    */     }
/* 210:    */     catch (Exception e)
/* 211:    */     {
/* 212:427 */       while ((e instanceof WrappedRuntimeException)) {
/* 213:429 */         e = ((WrappedRuntimeException)e).getException();
/* 214:    */       }
/* 215:433 */       String msg = e.getMessage();
/* 216:435 */       if ((msg == null) || (msg.length() == 0)) {
/* 217:436 */         msg = XPATHMessages.createXPATHMessage("ER_XPATH_ERROR", null);
/* 218:    */       }
/* 219:441 */       TransformerException te = new TransformerException(msg, getLocator(), e);
/* 220:    */       
/* 221:443 */       ErrorListener el = xctxt.getErrorListener();
/* 222:445 */       if (null != el) {
/* 223:447 */         el.fatalError(te);
/* 224:    */       } else {
/* 225:450 */         throw te;
/* 226:    */       }
/* 227:    */     }
/* 228:    */     finally
/* 229:    */     {
/* 230:454 */       xctxt.popNamespaceContext();
/* 231:    */       
/* 232:456 */       xctxt.popCurrentNodeAndExpression();
/* 233:    */     }
/* 234:459 */     return false;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public double getMatchScore(XPathContext xctxt, int context)
/* 238:    */     throws TransformerException
/* 239:    */   {
/* 240:482 */     xctxt.pushCurrentNode(context);
/* 241:483 */     xctxt.pushCurrentExpressionNode(context);
/* 242:    */     try
/* 243:    */     {
/* 244:487 */       XObject score = this.m_mainExp.execute(xctxt);
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:497 */       return score.num();
/* 255:    */     }
/* 256:    */     finally
/* 257:    */     {
/* 258:501 */       xctxt.popCurrentNode();
/* 259:502 */       xctxt.popCurrentExpressionNode();
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void warn(XPathContext xctxt, int sourceNode, String msg, Object[] args)
/* 264:    */     throws TransformerException
/* 265:    */   {
/* 266:528 */     String fmsg = XPATHMessages.createXPATHWarning(msg, args);
/* 267:529 */     ErrorListener ehandler = xctxt.getErrorListener();
/* 268:531 */     if (null != ehandler) {
/* 269:535 */       ehandler.warning(new TransformerException(fmsg, (SAXSourceLocator)xctxt.getSAXLocator()));
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void assertion(boolean b, String msg)
/* 274:    */   {
/* 275:551 */     if (!b)
/* 276:    */     {
/* 277:553 */       String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/* 278:    */       
/* 279:    */ 
/* 280:    */ 
/* 281:557 */       throw new RuntimeException(fMsg);
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void error(XPathContext xctxt, int sourceNode, String msg, Object[] args)
/* 286:    */     throws TransformerException
/* 287:    */   {
/* 288:581 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/* 289:582 */     ErrorListener ehandler = xctxt.getErrorListener();
/* 290:584 */     if (null != ehandler)
/* 291:    */     {
/* 292:586 */       ehandler.fatalError(new TransformerException(fmsg, (SAXSourceLocator)xctxt.getSAXLocator()));
/* 293:    */     }
/* 294:    */     else
/* 295:    */     {
/* 296:591 */       SourceLocator slocator = xctxt.getSAXLocator();
/* 297:592 */       System.out.println(fmsg + "; file " + slocator.getSystemId() + "; line " + slocator.getLineNumber() + "; column " + slocator.getColumnNumber());
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 302:    */   {
/* 303:609 */     this.m_mainExp.callVisitors(this, visitor);
/* 304:    */   }
/* 305:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPath
 * JD-Core Version:    0.7.0.1
 */