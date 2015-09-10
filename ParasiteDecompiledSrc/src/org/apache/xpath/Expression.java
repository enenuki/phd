/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.ErrorListener;
/*   6:    */ import javax.xml.transform.SourceLocator;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xml.dtm.DTM;
/*   9:    */ import org.apache.xml.dtm.DTMIterator;
/*  10:    */ import org.apache.xml.utils.XMLString;
/*  11:    */ import org.apache.xpath.objects.XNodeSet;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ import org.apache.xpath.res.XPATHMessages;
/*  14:    */ import org.xml.sax.ContentHandler;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ 
/*  17:    */ public abstract class Expression
/*  18:    */   implements Serializable, ExpressionNode, XPathVisitable
/*  19:    */ {
/*  20:    */   static final long serialVersionUID = 565665869777906902L;
/*  21:    */   private ExpressionNode m_parent;
/*  22:    */   
/*  23:    */   public boolean canTraverseOutsideSubtree()
/*  24:    */   {
/*  25: 63 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public XObject execute(XPathContext xctxt, int currentNode)
/*  29:    */     throws TransformerException
/*  30:    */   {
/*  31: 96 */     return execute(xctxt);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public XObject execute(XPathContext xctxt, int currentNode, DTM dtm, int expType)
/*  35:    */     throws TransformerException
/*  36:    */   {
/*  37:120 */     return execute(xctxt);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public abstract XObject execute(XPathContext paramXPathContext)
/*  41:    */     throws TransformerException;
/*  42:    */   
/*  43:    */   public XObject execute(XPathContext xctxt, boolean destructiveOK)
/*  44:    */     throws TransformerException
/*  45:    */   {
/*  46:155 */     return execute(xctxt);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public double num(XPathContext xctxt)
/*  50:    */     throws TransformerException
/*  51:    */   {
/*  52:171 */     return execute(xctxt).num();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean bool(XPathContext xctxt)
/*  56:    */     throws TransformerException
/*  57:    */   {
/*  58:186 */     return execute(xctxt).bool();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public XMLString xstr(XPathContext xctxt)
/*  62:    */     throws TransformerException
/*  63:    */   {
/*  64:201 */     return execute(xctxt).xstr();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isNodesetExpr()
/*  68:    */   {
/*  69:211 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int asNode(XPathContext xctxt)
/*  73:    */     throws TransformerException
/*  74:    */   {
/*  75:225 */     DTMIterator iter = execute(xctxt).iter();
/*  76:226 */     return iter.nextNode();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public DTMIterator asIterator(XPathContext xctxt, int contextNode)
/*  80:    */     throws TransformerException
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:250 */       xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/*  85:    */       
/*  86:252 */       return execute(xctxt).iter();
/*  87:    */     }
/*  88:    */     finally
/*  89:    */     {
/*  90:256 */       xctxt.popCurrentNodeAndExpression();
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public DTMIterator asIteratorRaw(XPathContext xctxt, int contextNode)
/*  95:    */     throws TransformerException
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:281 */       xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);
/* 100:    */       
/* 101:283 */       XNodeSet nodeset = (XNodeSet)execute(xctxt);
/* 102:284 */       return nodeset.iterRaw();
/* 103:    */     }
/* 104:    */     finally
/* 105:    */     {
/* 106:288 */       xctxt.popCurrentNodeAndExpression();
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void executeCharsToContentHandler(XPathContext xctxt, ContentHandler handler)
/* 111:    */     throws TransformerException, SAXException
/* 112:    */   {
/* 113:313 */     XObject obj = execute(xctxt);
/* 114:    */     
/* 115:315 */     obj.dispatchCharactersEvents(handler);
/* 116:316 */     obj.detach();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isStableNumber()
/* 120:    */   {
/* 121:329 */     return false;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public abstract void fixupVariables(Vector paramVector, int paramInt);
/* 125:    */   
/* 126:    */   public abstract boolean deepEquals(Expression paramExpression);
/* 127:    */   
/* 128:    */   protected final boolean isSameClass(Expression expr)
/* 129:    */   {
/* 130:367 */     if (null == expr) {
/* 131:368 */       return false;
/* 132:    */     }
/* 133:370 */     return getClass() == expr.getClass();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void warn(XPathContext xctxt, String msg, Object[] args)
/* 137:    */     throws TransformerException
/* 138:    */   {
/* 139:392 */     String fmsg = XPATHMessages.createXPATHWarning(msg, args);
/* 140:394 */     if (null != xctxt)
/* 141:    */     {
/* 142:396 */       ErrorListener eh = xctxt.getErrorListener();
/* 143:    */       
/* 144:    */ 
/* 145:399 */       eh.warning(new TransformerException(fmsg, xctxt.getSAXLocator()));
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void assertion(boolean b, String msg)
/* 150:    */   {
/* 151:417 */     if (!b)
/* 152:    */     {
/* 153:419 */       String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:423 */       throw new RuntimeException(fMsg);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void error(XPathContext xctxt, String msg, Object[] args)
/* 162:    */     throws TransformerException
/* 163:    */   {
/* 164:447 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/* 165:449 */     if (null != xctxt)
/* 166:    */     {
/* 167:451 */       ErrorListener eh = xctxt.getErrorListener();
/* 168:452 */       TransformerException te = new TransformerException(fmsg, this);
/* 169:    */       
/* 170:454 */       eh.fatalError(te);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public ExpressionNode getExpressionOwner()
/* 175:    */   {
/* 176:464 */     ExpressionNode parent = exprGetParent();
/* 177:465 */     while ((null != parent) && ((parent instanceof Expression))) {
/* 178:466 */       parent = parent.exprGetParent();
/* 179:    */     }
/* 180:467 */     return parent;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void exprSetParent(ExpressionNode n)
/* 184:    */   {
/* 185:476 */     assertion(n != this, "Can not parent an expression to itself!");
/* 186:477 */     this.m_parent = n;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public ExpressionNode exprGetParent()
/* 190:    */   {
/* 191:482 */     return this.m_parent;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void exprAddChild(ExpressionNode n, int i)
/* 195:    */   {
/* 196:489 */     assertion(false, "exprAddChild method not implemented!");
/* 197:    */   }
/* 198:    */   
/* 199:    */   public ExpressionNode exprGetChild(int i)
/* 200:    */   {
/* 201:496 */     return null;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int exprGetNumChildren()
/* 205:    */   {
/* 206:502 */     return 0;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String getPublicId()
/* 210:    */   {
/* 211:520 */     if (null == this.m_parent) {
/* 212:521 */       return null;
/* 213:    */     }
/* 214:522 */     return this.m_parent.getPublicId();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getSystemId()
/* 218:    */   {
/* 219:541 */     if (null == this.m_parent) {
/* 220:542 */       return null;
/* 221:    */     }
/* 222:543 */     return this.m_parent.getSystemId();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getLineNumber()
/* 226:    */   {
/* 227:563 */     if (null == this.m_parent) {
/* 228:564 */       return 0;
/* 229:    */     }
/* 230:565 */     return this.m_parent.getLineNumber();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int getColumnNumber()
/* 234:    */   {
/* 235:585 */     if (null == this.m_parent) {
/* 236:586 */       return 0;
/* 237:    */     }
/* 238:587 */     return this.m_parent.getColumnNumber();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public abstract void callVisitors(ExpressionOwner paramExpressionOwner, XPathVisitor paramXPathVisitor);
/* 242:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.Expression
 * JD-Core Version:    0.7.0.1
 */