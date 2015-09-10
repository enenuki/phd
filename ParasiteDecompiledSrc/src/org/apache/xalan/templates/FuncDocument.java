/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ import javax.xml.transform.ErrorListener;
/*   8:    */ import javax.xml.transform.Source;
/*   9:    */ import javax.xml.transform.SourceLocator;
/*  10:    */ import javax.xml.transform.TransformerException;
/*  11:    */ import org.apache.xalan.res.XSLMessages;
/*  12:    */ import org.apache.xml.dtm.DTM;
/*  13:    */ import org.apache.xml.dtm.DTMIterator;
/*  14:    */ import org.apache.xml.utils.PrefixResolver;
/*  15:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  16:    */ import org.apache.xml.utils.XMLString;
/*  17:    */ import org.apache.xpath.Expression;
/*  18:    */ import org.apache.xpath.NodeSetDTM;
/*  19:    */ import org.apache.xpath.SourceTreeManager;
/*  20:    */ import org.apache.xpath.XPathContext;
/*  21:    */ import org.apache.xpath.functions.Function2Args;
/*  22:    */ import org.apache.xpath.functions.FunctionOneArg;
/*  23:    */ import org.apache.xpath.functions.WrongNumberArgsException;
/*  24:    */ import org.apache.xpath.objects.XNodeSet;
/*  25:    */ import org.apache.xpath.objects.XObject;
/*  26:    */ 
/*  27:    */ public class FuncDocument
/*  28:    */   extends Function2Args
/*  29:    */ {
/*  30:    */   static final long serialVersionUID = 2483304325971281424L;
/*  31:    */   
/*  32:    */   public XObject execute(XPathContext xctxt)
/*  33:    */     throws TransformerException
/*  34:    */   {
/*  35: 75 */     int context = xctxt.getCurrentNode();
/*  36: 76 */     DTM dtm = xctxt.getDTM(context);
/*  37:    */     
/*  38: 78 */     int docContext = dtm.getDocumentRoot(context);
/*  39: 79 */     XObject arg = getArg0().execute(xctxt);
/*  40:    */     
/*  41: 81 */     String base = "";
/*  42: 82 */     Expression arg1Expr = getArg1();
/*  43: 84 */     if (null != arg1Expr)
/*  44:    */     {
/*  45: 91 */       XObject arg2 = arg1Expr.execute(xctxt);
/*  46: 93 */       if (4 == arg2.getType())
/*  47:    */       {
/*  48: 95 */         int baseNode = arg2.iter().nextNode();
/*  49: 97 */         if (baseNode == -1)
/*  50:    */         {
/*  51:102 */           warn(xctxt, "WG_EMPTY_SECOND_ARG", null);
/*  52:103 */           XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
/*  53:104 */           return nodes;
/*  54:    */         }
/*  55:106 */         DTM baseDTM = xctxt.getDTM(baseNode);
/*  56:107 */         base = baseDTM.getDocumentBaseURI();
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60:125 */         arg2.iter();
/*  61:    */       }
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65:140 */       assertion(null != xctxt.getNamespaceContext(), "Namespace context can not be null!");
/*  66:141 */       base = xctxt.getNamespaceContext().getBaseIdentifier();
/*  67:    */     }
/*  68:144 */     XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
/*  69:145 */     NodeSetDTM mnl = nodes.mutableNodeset();
/*  70:146 */     DTMIterator iterator = 4 == arg.getType() ? arg.iter() : null;
/*  71:    */     
/*  72:148 */     int pos = -1;
/*  73:150 */     while ((null == iterator) || (-1 != (pos = iterator.nextNode())))
/*  74:    */     {
/*  75:152 */       XMLString ref = null != iterator ? xctxt.getDTM(pos).getStringValue(pos) : arg.xstr();
/*  76:163 */       if ((null == arg1Expr) && (-1 != pos))
/*  77:    */       {
/*  78:165 */         DTM baseDTM = xctxt.getDTM(pos);
/*  79:166 */         base = baseDTM.getDocumentBaseURI();
/*  80:    */       }
/*  81:169 */       if (null != ref)
/*  82:    */       {
/*  83:172 */         if (-1 == docContext) {
/*  84:174 */           error(xctxt, "ER_NO_CONTEXT_OWNERDOC", null);
/*  85:    */         }
/*  86:183 */         int indexOfColon = ref.indexOf(58);
/*  87:184 */         int indexOfSlash = ref.indexOf(47);
/*  88:186 */         if ((indexOfColon != -1) && (indexOfSlash != -1) && (indexOfColon < indexOfSlash)) {
/*  89:191 */           base = null;
/*  90:    */         }
/*  91:194 */         int newDoc = getDoc(xctxt, context, ref.toString(), base);
/*  92:197 */         if (-1 != newDoc) {
/*  93:200 */           if (!mnl.contains(newDoc)) {
/*  94:202 */             mnl.addElement(newDoc);
/*  95:    */           }
/*  96:    */         }
/*  97:206 */         if ((null == iterator) || (newDoc == -1)) {
/*  98:    */           break;
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:210 */     return nodes;
/* 103:    */   }
/* 104:    */   
/* 105:    */   int getDoc(XPathContext xctxt, int context, String uri, String base)
/* 106:    */     throws TransformerException
/* 107:    */   {
/* 108:231 */     SourceTreeManager treeMgr = xctxt.getSourceTreeManager();
/* 109:    */     Source source;
/* 110:    */     int newDoc;
/* 111:    */     try
/* 112:    */     {
/* 113:237 */       source = treeMgr.resolveURI(base, uri, xctxt.getSAXLocator());
/* 114:238 */       newDoc = treeMgr.getNode(source);
/* 115:    */     }
/* 116:    */     catch (IOException ioe)
/* 117:    */     {
/* 118:242 */       throw new TransformerException(ioe.getMessage(), xctxt.getSAXLocator(), ioe);
/* 119:    */     }
/* 120:    */     catch (TransformerException te)
/* 121:    */     {
/* 122:247 */       throw new TransformerException(te);
/* 123:    */     }
/* 124:250 */     if (-1 != newDoc) {
/* 125:251 */       return newDoc;
/* 126:    */     }
/* 127:254 */     if (uri.length() == 0)
/* 128:    */     {
/* 129:257 */       uri = xctxt.getNamespaceContext().getBaseIdentifier();
/* 130:    */       try
/* 131:    */       {
/* 132:260 */         source = treeMgr.resolveURI(base, uri, xctxt.getSAXLocator());
/* 133:    */       }
/* 134:    */       catch (IOException ioe)
/* 135:    */       {
/* 136:264 */         throw new TransformerException(ioe.getMessage(), xctxt.getSAXLocator(), ioe);
/* 137:    */       }
/* 138:    */     }
/* 139:269 */     String diagnosticsString = null;
/* 140:    */     try
/* 141:    */     {
/* 142:273 */       if ((null != uri) && (uri.length() > 0)) {
/* 143:275 */         newDoc = treeMgr.getSourceTree(source, xctxt.getSAXLocator(), xctxt);
/* 144:    */       } else {
/* 145:280 */         warn(xctxt, "WG_CANNOT_MAKE_URL_FROM", new Object[] { (base == null ? "" : base) + uri });
/* 146:    */       }
/* 147:    */     }
/* 148:    */     catch (Throwable throwable)
/* 149:    */     {
/* 150:287 */       newDoc = -1;
/* 151:291 */       while ((throwable instanceof WrappedRuntimeException)) {
/* 152:293 */         throwable = ((WrappedRuntimeException)throwable).getException();
/* 153:    */       }
/* 154:297 */       if (((throwable instanceof NullPointerException)) || ((throwable instanceof ClassCastException))) {
/* 155:300 */         throw new WrappedRuntimeException((Exception)throwable);
/* 156:    */       }
/* 157:304 */       StringWriter sw = new StringWriter();
/* 158:305 */       PrintWriter diagnosticsWriter = new PrintWriter(sw);
/* 159:307 */       if ((throwable instanceof TransformerException))
/* 160:    */       {
/* 161:309 */         TransformerException spe = (TransformerException)throwable;
/* 162:    */         
/* 163:    */ 
/* 164:312 */         Throwable e = spe;
/* 165:314 */         while (null != e)
/* 166:    */         {
/* 167:316 */           if (null != e.getMessage()) {
/* 168:318 */             diagnosticsWriter.println(" (" + e.getClass().getName() + "): " + e.getMessage());
/* 169:    */           }
/* 170:322 */           if ((e instanceof TransformerException))
/* 171:    */           {
/* 172:324 */             TransformerException spe2 = (TransformerException)e;
/* 173:    */             
/* 174:326 */             SourceLocator locator = spe2.getLocator();
/* 175:327 */             if ((null != locator) && (null != locator.getSystemId())) {
/* 176:328 */               diagnosticsWriter.println("   ID: " + locator.getSystemId() + " Line #" + locator.getLineNumber() + " Column #" + locator.getColumnNumber());
/* 177:    */             }
/* 178:333 */             e = spe2.getException();
/* 179:335 */             if ((e instanceof WrappedRuntimeException)) {
/* 180:336 */               e = ((WrappedRuntimeException)e).getException();
/* 181:    */             }
/* 182:    */           }
/* 183:    */           else
/* 184:    */           {
/* 185:339 */             e = null;
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:    */       else
/* 190:    */       {
/* 191:345 */         diagnosticsWriter.println(" (" + throwable.getClass().getName() + "): " + throwable.getMessage());
/* 192:    */       }
/* 193:349 */       diagnosticsString = throwable.getMessage();
/* 194:    */     }
/* 195:352 */     if (-1 == newDoc) {
/* 196:356 */       if (null != diagnosticsString) {
/* 197:358 */         warn(xctxt, "WG_CANNOT_LOAD_REQUESTED_DOC", new Object[] { diagnosticsString });
/* 198:    */       } else {
/* 199:362 */         warn(xctxt, "WG_CANNOT_LOAD_REQUESTED_DOC", new Object[] { uri == null ? (base == null ? "" : base) + uri : uri.toString() });
/* 200:    */       }
/* 201:    */     }
/* 202:374 */     return newDoc;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void error(XPathContext xctxt, String msg, Object[] args)
/* 206:    */     throws TransformerException
/* 207:    */   {
/* 208:393 */     String formattedMsg = XSLMessages.createMessage(msg, args);
/* 209:394 */     ErrorListener errHandler = xctxt.getErrorListener();
/* 210:395 */     TransformerException spe = new TransformerException(formattedMsg, xctxt.getSAXLocator());
/* 211:398 */     if (null != errHandler) {
/* 212:399 */       errHandler.error(spe);
/* 213:    */     } else {
/* 214:401 */       System.out.println(formattedMsg);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void warn(XPathContext xctxt, String msg, Object[] args)
/* 219:    */     throws TransformerException
/* 220:    */   {
/* 221:419 */     String formattedMsg = XSLMessages.createWarning(msg, args);
/* 222:420 */     ErrorListener errHandler = xctxt.getErrorListener();
/* 223:421 */     TransformerException spe = new TransformerException(formattedMsg, xctxt.getSAXLocator());
/* 224:424 */     if (null != errHandler) {
/* 225:425 */       errHandler.warning(spe);
/* 226:    */     } else {
/* 227:427 */       System.out.println(formattedMsg);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void checkNumberArgs(int argNum)
/* 232:    */     throws WrongNumberArgsException
/* 233:    */   {
/* 234:440 */     if ((argNum < 1) || (argNum > 2)) {
/* 235:441 */       reportWrongNumberArgs();
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected void reportWrongNumberArgs()
/* 240:    */     throws WrongNumberArgsException
/* 241:    */   {
/* 242:451 */     throw new WrongNumberArgsException(XSLMessages.createMessage("ER_ONE_OR_TWO", null));
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isNodesetExpr()
/* 246:    */   {
/* 247:460 */     return true;
/* 248:    */   }
/* 249:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.FuncDocument
 * JD-Core Version:    0.7.0.1
 */