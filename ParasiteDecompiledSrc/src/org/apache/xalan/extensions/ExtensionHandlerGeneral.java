/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.net.URLConnection;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import java.util.Vector;
/*  11:    */ import javax.xml.transform.TransformerException;
/*  12:    */ import org.apache.xalan.res.XSLMessages;
/*  13:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  14:    */ import org.apache.xalan.templates.Stylesheet;
/*  15:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  16:    */ import org.apache.xml.dtm.DTMIterator;
/*  17:    */ import org.apache.xml.dtm.ref.DTMNodeList;
/*  18:    */ import org.apache.xml.utils.StringVector;
/*  19:    */ import org.apache.xml.utils.SystemIDResolver;
/*  20:    */ import org.apache.xpath.XPathContext;
/*  21:    */ import org.apache.xpath.XPathException;
/*  22:    */ import org.apache.xpath.XPathProcessorException;
/*  23:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  24:    */ import org.apache.xpath.objects.XObject;
/*  25:    */ 
/*  26:    */ public class ExtensionHandlerGeneral
/*  27:    */   extends ExtensionHandler
/*  28:    */ {
/*  29:    */   private String m_scriptSrc;
/*  30:    */   private String m_scriptSrcURL;
/*  31: 66 */   private Hashtable m_functions = new Hashtable();
/*  32: 69 */   private Hashtable m_elements = new Hashtable();
/*  33:    */   private Object m_engine;
/*  34: 78 */   private Method m_engineCall = null;
/*  35:    */   private static String BSF_MANAGER;
/*  36:    */   private static final String DEFAULT_BSF_MANAGER = "org.apache.bsf.BSFManager";
/*  37:    */   private static final String propName = "org.apache.xalan.extensions.bsf.BSFManager";
/*  38: 92 */   private static final Integer ZEROINT = new Integer(0);
/*  39:    */   
/*  40:    */   static
/*  41:    */   {
/*  42: 95 */     BSF_MANAGER = ObjectFactory.lookUpFactoryClassName("org.apache.xalan.extensions.bsf.BSFManager", null, null);
/*  43: 97 */     if (BSF_MANAGER == null) {
/*  44: 98 */       BSF_MANAGER = "org.apache.bsf.BSFManager";
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ExtensionHandlerGeneral(String namespaceUri, StringVector elemNames, StringVector funcNames, String scriptLang, String scriptSrcURL, String scriptSrc, String systemId)
/*  49:    */     throws TransformerException
/*  50:    */   {
/*  51:121 */     super(namespaceUri, scriptLang);
/*  52:123 */     if (elemNames != null)
/*  53:    */     {
/*  54:125 */       Object junk = new Object();
/*  55:126 */       int n = elemNames.size();
/*  56:128 */       for (int i = 0; i < n; i++)
/*  57:    */       {
/*  58:130 */         String tok = elemNames.elementAt(i);
/*  59:    */         
/*  60:132 */         this.m_elements.put(tok, junk);
/*  61:    */       }
/*  62:    */     }
/*  63:136 */     if (funcNames != null)
/*  64:    */     {
/*  65:138 */       Object junk = new Object();
/*  66:139 */       int n = funcNames.size();
/*  67:141 */       for (int i = 0; i < n; i++)
/*  68:    */       {
/*  69:143 */         String tok = funcNames.elementAt(i);
/*  70:    */         
/*  71:145 */         this.m_functions.put(tok, junk);
/*  72:    */       }
/*  73:    */     }
/*  74:149 */     this.m_scriptSrcURL = scriptSrcURL;
/*  75:150 */     this.m_scriptSrc = scriptSrc;
/*  76:152 */     if (this.m_scriptSrcURL != null)
/*  77:    */     {
/*  78:154 */       URL url = null;
/*  79:    */       try
/*  80:    */       {
/*  81:156 */         url = new URL(this.m_scriptSrcURL);
/*  82:    */       }
/*  83:    */       catch (MalformedURLException mue)
/*  84:    */       {
/*  85:160 */         int indexOfColon = this.m_scriptSrcURL.indexOf(':');
/*  86:161 */         int indexOfSlash = this.m_scriptSrcURL.indexOf('/');
/*  87:163 */         if ((indexOfColon != -1) && (indexOfSlash != -1) && (indexOfColon < indexOfSlash))
/*  88:    */         {
/*  89:167 */           url = null;
/*  90:168 */           throw new TransformerException(XSLMessages.createMessage("ER_COULD_NOT_FIND_EXTERN_SCRIPT", new Object[] { this.m_scriptSrcURL }), mue);
/*  91:    */         }
/*  92:    */         try
/*  93:    */         {
/*  94:174 */           url = new URL(new URL(SystemIDResolver.getAbsoluteURI(systemId)), this.m_scriptSrcURL);
/*  95:    */         }
/*  96:    */         catch (MalformedURLException mue2)
/*  97:    */         {
/*  98:178 */           throw new TransformerException(XSLMessages.createMessage("ER_COULD_NOT_FIND_EXTERN_SCRIPT", new Object[] { this.m_scriptSrcURL }), mue2);
/*  99:    */         }
/* 100:    */       }
/* 101:183 */       if (url != null) {
/* 102:    */         try
/* 103:    */         {
/* 104:187 */           URLConnection uc = url.openConnection();
/* 105:188 */           InputStream is = uc.getInputStream();
/* 106:189 */           byte[] bArray = new byte[uc.getContentLength()];
/* 107:190 */           is.read(bArray);
/* 108:191 */           this.m_scriptSrc = new String(bArray);
/* 109:    */         }
/* 110:    */         catch (IOException ioe)
/* 111:    */         {
/* 112:196 */           throw new TransformerException(XSLMessages.createMessage("ER_COULD_NOT_FIND_EXTERN_SCRIPT", new Object[] { this.m_scriptSrcURL }), ioe);
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:203 */     Object manager = null;
/* 117:    */     try
/* 118:    */     {
/* 119:206 */       manager = ObjectFactory.newInstance(BSF_MANAGER, ObjectFactory.findClassLoader(), true);
/* 120:    */     }
/* 121:    */     catch (ObjectFactory.ConfigurationError e)
/* 122:    */     {
/* 123:211 */       e.printStackTrace();
/* 124:    */     }
/* 125:214 */     if (manager == null) {
/* 126:216 */       throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_INIT_BSFMGR", null));
/* 127:    */     }
/* 128:    */     try
/* 129:    */     {
/* 130:221 */       Method loadScriptingEngine = manager.getClass().getMethod("loadScriptingEngine", new Class[] { String.class });
/* 131:    */       
/* 132:    */ 
/* 133:224 */       this.m_engine = loadScriptingEngine.invoke(manager, new Object[] { scriptLang });
/* 134:    */       
/* 135:    */ 
/* 136:227 */       Method engineExec = this.m_engine.getClass().getMethod("exec", new Class[] { String.class, Integer.TYPE, Integer.TYPE, Object.class });
/* 137:    */       
/* 138:    */ 
/* 139:    */ 
/* 140:231 */       engineExec.invoke(this.m_engine, new Object[] { "XalanScript", ZEROINT, ZEROINT, this.m_scriptSrc });
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:236 */       e.printStackTrace();
/* 145:    */       
/* 146:238 */       throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_CMPL_EXTENSN", null), e);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isFunctionAvailable(String function)
/* 151:    */   {
/* 152:249 */     return this.m_functions.get(function) != null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isElementAvailable(String element)
/* 156:    */   {
/* 157:259 */     return this.m_elements.get(element) != null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Object callFunction(String funcName, Vector args, Object methodKey, ExpressionContext exprContext)
/* 161:    */     throws TransformerException
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:283 */       Object[] argArray = new Object[args.size()];
/* 166:285 */       for (int i = 0; i < argArray.length; i++)
/* 167:    */       {
/* 168:287 */         Object o = args.get(i);
/* 169:    */         
/* 170:289 */         argArray[i] = ((o instanceof XObject) ? ((XObject)o).object() : o);
/* 171:290 */         o = argArray[i];
/* 172:291 */         if ((null != o) && ((o instanceof DTMIterator))) {
/* 173:293 */           argArray[i] = new DTMNodeList((DTMIterator)o);
/* 174:    */         }
/* 175:    */       }
/* 176:297 */       if (this.m_engineCall == null) {
/* 177:298 */         this.m_engineCall = this.m_engine.getClass().getMethod("call", new Class[] { Object.class, String.class, new Object[0].getClass() });
/* 178:    */       }
/* 179:302 */       return this.m_engineCall.invoke(this.m_engine, new Object[] { null, funcName, argArray });
/* 180:    */     }
/* 181:    */     catch (Exception e)
/* 182:    */     {
/* 183:307 */       e.printStackTrace();
/* 184:    */       
/* 185:309 */       String msg = e.getMessage();
/* 186:311 */       if (null != msg)
/* 187:    */       {
/* 188:313 */         if (msg.startsWith("Stopping after fatal error:")) {
/* 189:315 */           msg = msg.substring("Stopping after fatal error:".length());
/* 190:    */         }
/* 191:319 */         throw new TransformerException(e);
/* 192:    */       }
/* 193:325 */       throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_CREATE_EXTENSN", new Object[] { funcName, e }));
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Object callFunction(FuncExtFunction extFunction, Vector args, ExpressionContext exprContext)
/* 198:    */     throws TransformerException
/* 199:    */   {
/* 200:345 */     return callFunction(extFunction.getFunctionName(), args, extFunction.getMethodKey(), exprContext);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void processElement(String localPart, ElemTemplateElement element, TransformerImpl transformer, Stylesheet stylesheetTree, Object methodKey)
/* 204:    */     throws TransformerException, IOException
/* 205:    */   {
/* 206:372 */     Object result = null;
/* 207:373 */     XSLProcessorContext xpc = new XSLProcessorContext(transformer, stylesheetTree);
/* 208:    */     try
/* 209:    */     {
/* 210:377 */       Vector argv = new Vector(2);
/* 211:    */       
/* 212:379 */       argv.add(xpc);
/* 213:380 */       argv.add(element);
/* 214:    */       
/* 215:382 */       result = callFunction(localPart, argv, methodKey, transformer.getXPathContext().getExpressionContext());
/* 216:    */     }
/* 217:    */     catch (XPathProcessorException e)
/* 218:    */     {
/* 219:389 */       throw new TransformerException(e.getMessage(), e);
/* 220:    */     }
/* 221:392 */     if (result != null) {
/* 222:394 */       xpc.outputToResultTree(stylesheetTree, result);
/* 223:    */     }
/* 224:    */   }
/* 225:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandlerGeneral
 * JD-Core Version:    0.7.0.1
 */