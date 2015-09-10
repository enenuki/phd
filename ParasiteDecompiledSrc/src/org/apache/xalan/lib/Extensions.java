/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import javax.xml.parsers.DocumentBuilder;
/*   7:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   8:    */ import javax.xml.parsers.ParserConfigurationException;
/*   9:    */ import org.apache.xalan.extensions.ExpressionContext;
/*  10:    */ import org.apache.xalan.xslt.EnvironmentCheck;
/*  11:    */ import org.apache.xml.utils.Hashtree2Node;
/*  12:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  13:    */ import org.apache.xpath.NodeSet;
/*  14:    */ import org.apache.xpath.objects.XBoolean;
/*  15:    */ import org.apache.xpath.objects.XNumber;
/*  16:    */ import org.apache.xpath.objects.XObject;
/*  17:    */ import org.w3c.dom.Document;
/*  18:    */ import org.w3c.dom.DocumentFragment;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ import org.w3c.dom.NodeList;
/*  21:    */ import org.w3c.dom.Text;
/*  22:    */ import org.w3c.dom.traversal.NodeIterator;
/*  23:    */ import org.xml.sax.SAXNotSupportedException;
/*  24:    */ 
/*  25:    */ public class Extensions
/*  26:    */ {
/*  27:    */   public static NodeSet nodeset(ExpressionContext myProcessor, Object rtf)
/*  28:    */   {
/*  29: 89 */     if ((rtf instanceof NodeIterator)) {
/*  30: 91 */       return new NodeSet((NodeIterator)rtf);
/*  31:    */     }
/*  32:    */     String textNodeValue;
/*  33: 95 */     if ((rtf instanceof String)) {
/*  34: 97 */       textNodeValue = (String)rtf;
/*  35: 99 */     } else if ((rtf instanceof Boolean)) {
/*  36:101 */       textNodeValue = new XBoolean(((Boolean)rtf).booleanValue()).str();
/*  37:103 */     } else if ((rtf instanceof Double)) {
/*  38:105 */       textNodeValue = new XNumber(((Double)rtf).doubleValue()).str();
/*  39:    */     } else {
/*  40:109 */       textNodeValue = rtf.toString();
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44:116 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  45:117 */       DocumentBuilder db = dbf.newDocumentBuilder();
/*  46:118 */       Document myDoc = db.newDocument();
/*  47:    */       
/*  48:120 */       Text textNode = myDoc.createTextNode(textNodeValue);
/*  49:121 */       DocumentFragment docFrag = myDoc.createDocumentFragment();
/*  50:    */       
/*  51:123 */       docFrag.appendChild(textNode);
/*  52:    */       
/*  53:125 */       return new NodeSet(docFrag);
/*  54:    */     }
/*  55:    */     catch (ParserConfigurationException pce)
/*  56:    */     {
/*  57:129 */       throw new WrappedRuntimeException(pce);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static NodeList intersection(NodeList nl1, NodeList nl2)
/*  62:    */   {
/*  63:147 */     return ExsltSets.intersection(nl1, nl2);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static NodeList difference(NodeList nl1, NodeList nl2)
/*  67:    */   {
/*  68:163 */     return ExsltSets.difference(nl1, nl2);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static NodeList distinct(NodeList nl)
/*  72:    */   {
/*  73:180 */     return ExsltSets.distinct(nl);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static boolean hasSameNodes(NodeList nl1, NodeList nl2)
/*  77:    */   {
/*  78:193 */     NodeSet ns1 = new NodeSet(nl1);
/*  79:194 */     NodeSet ns2 = new NodeSet(nl2);
/*  80:196 */     if (ns1.getLength() != ns2.getLength()) {
/*  81:197 */       return false;
/*  82:    */     }
/*  83:199 */     for (int i = 0; i < ns1.getLength(); i++)
/*  84:    */     {
/*  85:201 */       Node n = ns1.elementAt(i);
/*  86:203 */       if (!ns2.contains(n)) {
/*  87:204 */         return false;
/*  88:    */       }
/*  89:    */     }
/*  90:207 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static XObject evaluate(ExpressionContext myContext, String xpathExpr)
/*  94:    */     throws SAXNotSupportedException
/*  95:    */   {
/*  96:230 */     return ExsltDynamic.evaluate(myContext, xpathExpr);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static NodeList tokenize(String toTokenize, String delims)
/* 100:    */   {
/* 101:249 */     Document doc = DocumentHolder.m_doc;
/* 102:    */     
/* 103:    */ 
/* 104:252 */     StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);
/* 105:253 */     NodeSet resultSet = new NodeSet();
/* 106:255 */     synchronized (doc)
/* 107:    */     {
/* 108:257 */       while (lTokenizer.hasMoreTokens()) {
/* 109:259 */         resultSet.addNode(doc.createTextNode(lTokenizer.nextToken()));
/* 110:    */       }
/* 111:    */     }
/* 112:263 */     return resultSet;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static NodeList tokenize(String toTokenize)
/* 116:    */   {
/* 117:281 */     return tokenize(toTokenize, " \t\n\r");
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static Node checkEnvironment(ExpressionContext myContext)
/* 121:    */   {
/* 122:    */     Document factoryDocument;
/* 123:    */     try
/* 124:    */     {
/* 125:311 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 126:312 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 127:313 */       factoryDocument = db.newDocument();
/* 128:    */     }
/* 129:    */     catch (ParserConfigurationException pce)
/* 130:    */     {
/* 131:317 */       throw new WrappedRuntimeException(pce);
/* 132:    */     }
/* 133:320 */     Node resultNode = null;
/* 134:    */     try
/* 135:    */     {
/* 136:325 */       resultNode = checkEnvironmentUsingWhich(myContext, factoryDocument);
/* 137:327 */       if (null != resultNode) {
/* 138:328 */         return resultNode;
/* 139:    */       }
/* 140:331 */       EnvironmentCheck envChecker = new EnvironmentCheck();
/* 141:332 */       Hashtable h = envChecker.getEnvironmentHash();
/* 142:333 */       resultNode = factoryDocument.createElement("checkEnvironmentExtension");
/* 143:334 */       envChecker.appendEnvironmentReport(resultNode, factoryDocument, h);
/* 144:335 */       envChecker = null;
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:339 */       throw new WrappedRuntimeException(e);
/* 149:    */     }
/* 150:342 */     return resultNode;
/* 151:    */   }
/* 152:    */   
/* 153:    */   private static Node checkEnvironmentUsingWhich(ExpressionContext myContext, Document factoryDocument)
/* 154:    */   {
/* 155:356 */     String WHICH_CLASSNAME = "org.apache.env.Which";
/* 156:357 */     String WHICH_METHODNAME = "which";
/* 157:358 */     Class[] WHICH_METHOD_ARGS = { Hashtable.class, String.class, String.class };
/* 158:    */     try
/* 159:    */     {
/* 160:364 */       Class clazz = ObjectFactory.findProviderClass("org.apache.env.Which", ObjectFactory.findClassLoader(), true);
/* 161:366 */       if (null == clazz) {
/* 162:367 */         return null;
/* 163:    */       }
/* 164:370 */       Method method = clazz.getMethod("which", WHICH_METHOD_ARGS);
/* 165:371 */       Hashtable report = new Hashtable();
/* 166:    */       
/* 167:    */ 
/* 168:374 */       Object[] methodArgs = { report, "XmlCommons;Xalan;Xerces;Crimson;Ant", "" };
/* 169:375 */       Object returnValue = method.invoke(null, methodArgs);
/* 170:    */       
/* 171:    */ 
/* 172:378 */       Node resultNode = factoryDocument.createElement("checkEnvironmentExtension");
/* 173:379 */       Hashtree2Node.appendHashToNode(report, "whichReport", resultNode, factoryDocument);
/* 174:    */       
/* 175:    */ 
/* 176:382 */       return resultNode;
/* 177:    */     }
/* 178:    */     catch (Throwable t) {}
/* 179:387 */     return null;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private static class DocumentHolder
/* 183:    */   {
/* 184:    */     private static final Document m_doc;
/* 185:    */     
/* 186:    */     static
/* 187:    */     {
/* 188:    */       try
/* 189:    */       {
/* 190:408 */         m_doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/* 191:    */       }
/* 192:    */       catch (ParserConfigurationException pce)
/* 193:    */       {
/* 194:413 */         throw new WrappedRuntimeException(pce);
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.Extensions
 * JD-Core Version:    0.7.0.1
 */