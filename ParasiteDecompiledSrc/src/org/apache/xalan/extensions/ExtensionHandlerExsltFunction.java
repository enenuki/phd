/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ import org.apache.xalan.templates.ElemExsltFuncResult;
/*   8:    */ import org.apache.xalan.templates.ElemExsltFunction;
/*   9:    */ import org.apache.xalan.templates.ElemTemplate;
/*  10:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  11:    */ import org.apache.xalan.templates.Stylesheet;
/*  12:    */ import org.apache.xalan.templates.StylesheetRoot;
/*  13:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  14:    */ import org.apache.xml.utils.QName;
/*  15:    */ import org.apache.xpath.Expression;
/*  16:    */ import org.apache.xpath.ExpressionNode;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  19:    */ import org.apache.xpath.objects.XObject;
/*  20:    */ import org.apache.xpath.objects.XString;
/*  21:    */ 
/*  22:    */ public class ExtensionHandlerExsltFunction
/*  23:    */   extends ExtensionHandler
/*  24:    */ {
/*  25:    */   private String m_namespace;
/*  26:    */   private StylesheetRoot m_stylesheet;
/*  27: 53 */   private static final QName RESULTQNAME = new QName("http://exslt.org/functions", "result");
/*  28:    */   
/*  29:    */   public ExtensionHandlerExsltFunction(String ns, StylesheetRoot stylesheet)
/*  30:    */   {
/*  31: 61 */     super(ns, "xml");
/*  32: 62 */     this.m_namespace = ns;
/*  33: 63 */     this.m_stylesheet = stylesheet;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void processElement(String localPart, ElemTemplateElement element, TransformerImpl transformer, Stylesheet stylesheetTree, Object methodKey)
/*  37:    */     throws TransformerException, IOException
/*  38:    */   {}
/*  39:    */   
/*  40:    */   public ElemExsltFunction getFunction(String funcName)
/*  41:    */   {
/*  42: 84 */     QName qname = new QName(this.m_namespace, funcName);
/*  43: 85 */     ElemTemplate templ = this.m_stylesheet.getTemplateComposed(qname);
/*  44: 86 */     if ((templ != null) && ((templ instanceof ElemExsltFunction))) {
/*  45: 87 */       return (ElemExsltFunction)templ;
/*  46:    */     }
/*  47: 89 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isFunctionAvailable(String funcName)
/*  51:    */   {
/*  52:101 */     return getFunction(funcName) != null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isElementAvailable(String elemName)
/*  56:    */   {
/*  57:115 */     if (!new QName(this.m_namespace, elemName).equals(RESULTQNAME)) {
/*  58:117 */       return false;
/*  59:    */     }
/*  60:121 */     ElemTemplateElement elem = this.m_stylesheet.getFirstChildElem();
/*  61:122 */     while ((elem != null) && (elem != this.m_stylesheet))
/*  62:    */     {
/*  63:124 */       if (((elem instanceof ElemExsltFuncResult)) && (ancestorIsFunction(elem))) {
/*  64:125 */         return true;
/*  65:    */       }
/*  66:126 */       ElemTemplateElement nextElem = elem.getFirstChildElem();
/*  67:127 */       if (nextElem == null) {
/*  68:128 */         nextElem = elem.getNextSiblingElem();
/*  69:    */       }
/*  70:129 */       if (nextElem == null) {
/*  71:130 */         nextElem = elem.getParentElem();
/*  72:    */       }
/*  73:131 */       elem = nextElem;
/*  74:    */     }
/*  75:134 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private boolean ancestorIsFunction(ElemTemplateElement child)
/*  79:    */   {
/*  80:144 */     while ((child.getParentElem() != null) && (!(child.getParentElem() instanceof StylesheetRoot)))
/*  81:    */     {
/*  82:146 */       if ((child.getParentElem() instanceof ElemExsltFunction)) {
/*  83:147 */         return true;
/*  84:    */       }
/*  85:148 */       child = child.getParentElem();
/*  86:    */     }
/*  87:150 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object callFunction(String funcName, Vector args, Object methodKey, ExpressionContext exprContext)
/*  91:    */     throws TransformerException
/*  92:    */   {
/*  93:167 */     throw new TransformerException("This method should not be called.");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Object callFunction(FuncExtFunction extFunction, Vector args, ExpressionContext exprContext)
/*  97:    */     throws TransformerException
/*  98:    */   {
/*  99:185 */     ExpressionNode parent = extFunction.exprGetParent();
/* 100:186 */     while ((parent != null) && (!(parent instanceof ElemTemplate))) {
/* 101:188 */       parent = parent.exprGetParent();
/* 102:    */     }
/* 103:191 */     ElemTemplate callerTemplate = parent != null ? (ElemTemplate)parent : null;
/* 104:    */     
/* 105:    */ 
/* 106:194 */     XObject[] methodArgs = new XObject[args.size()];
/* 107:    */     try
/* 108:    */     {
/* 109:197 */       for (int i = 0; i < methodArgs.length; i++) {
/* 110:199 */         methodArgs[i] = XObject.create(args.get(i));
/* 111:    */       }
/* 112:202 */       ElemExsltFunction elemFunc = getFunction(extFunction.getFunctionName());
/* 113:204 */       if (null != elemFunc)
/* 114:    */       {
/* 115:205 */         XPathContext context = exprContext.getXPathContext();
/* 116:206 */         TransformerImpl transformer = (TransformerImpl)context.getOwnerObject();
/* 117:207 */         transformer.pushCurrentFuncResult(null);
/* 118:    */         
/* 119:209 */         elemFunc.execute(transformer, methodArgs);
/* 120:    */         
/* 121:211 */         XObject val = (XObject)transformer.popCurrentFuncResult();
/* 122:212 */         return val == null ? new XString("") : val;
/* 123:    */       }
/* 124:216 */       throw new TransformerException(XSLMessages.createMessage("ER_FUNCTION_NOT_FOUND", new Object[] { extFunction.getFunctionName() }));
/* 125:    */     }
/* 126:    */     catch (TransformerException e)
/* 127:    */     {
/* 128:221 */       throw e;
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:225 */       throw new TransformerException(e);
/* 133:    */     }
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandlerExsltFunction
 * JD-Core Version:    0.7.0.1
 */