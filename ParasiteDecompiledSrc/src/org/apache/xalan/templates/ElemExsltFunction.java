/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.extensions.ExtensionNamespaceSupport;
/*   5:    */ import org.apache.xalan.extensions.ExtensionNamespacesManager;
/*   6:    */ import org.apache.xalan.trace.TraceManager;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.utils.QName;
/*   9:    */ import org.apache.xpath.VariableStack;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ import org.w3c.dom.Node;
/*  13:    */ import org.w3c.dom.NodeList;
/*  14:    */ 
/*  15:    */ public class ElemExsltFunction
/*  16:    */   extends ElemTemplate
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = 272154954793534771L;
/*  19:    */   
/*  20:    */   public int getXSLToken()
/*  21:    */   {
/*  22: 51 */     return 88;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getNodeName()
/*  26:    */   {
/*  27: 63 */     return "function";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void execute(TransformerImpl transformer, XObject[] args)
/*  31:    */     throws TransformerException
/*  32:    */   {
/*  33: 69 */     XPathContext xctxt = transformer.getXPathContext();
/*  34: 70 */     VariableStack vars = xctxt.getVarStack();
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38: 74 */     int thisFrame = vars.getStackFrame();
/*  39: 75 */     int nextFrame = vars.link(this.m_frameSize);
/*  40: 77 */     if (this.m_inArgsSize < args.length) {
/*  41: 78 */       throw new TransformerException("function called with too many args");
/*  42:    */     }
/*  43: 83 */     if (this.m_inArgsSize > 0)
/*  44:    */     {
/*  45: 84 */       vars.clearLocalSlots(0, this.m_inArgsSize);
/*  46: 86 */       if (args.length > 0)
/*  47:    */       {
/*  48: 87 */         vars.setStackFrame(thisFrame);
/*  49: 88 */         NodeList children = getChildNodes();
/*  50: 90 */         for (int i = 0; i < args.length; i++)
/*  51:    */         {
/*  52: 91 */           Node child = children.item(i);
/*  53: 92 */           if ((children.item(i) instanceof ElemParam))
/*  54:    */           {
/*  55: 93 */             ElemParam param = (ElemParam)children.item(i);
/*  56: 94 */             vars.setLocalVariable(param.getIndex(), args[i], nextFrame);
/*  57:    */           }
/*  58:    */         }
/*  59: 98 */         vars.setStackFrame(nextFrame);
/*  60:    */       }
/*  61:    */     }
/*  62:107 */     if (transformer.getDebug()) {
/*  63:108 */       transformer.getTraceManager().fireTraceEvent(this);
/*  64:    */     }
/*  65:110 */     vars.setStackFrame(nextFrame);
/*  66:111 */     transformer.executeChildTemplates(this, true);
/*  67:    */     
/*  68:    */ 
/*  69:114 */     vars.unlink(thisFrame);
/*  70:116 */     if (transformer.getDebug()) {
/*  71:117 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void compose(StylesheetRoot sroot)
/*  76:    */     throws TransformerException
/*  77:    */   {
/*  78:132 */     super.compose(sroot);
/*  79:    */     
/*  80:    */ 
/*  81:135 */     String namespace = getName().getNamespace();
/*  82:136 */     String handlerClass = sroot.getExtensionHandlerClass();
/*  83:137 */     Object[] args = { namespace, sroot };
/*  84:138 */     ExtensionNamespaceSupport extNsSpt = new ExtensionNamespaceSupport(namespace, handlerClass, args);
/*  85:    */     
/*  86:140 */     sroot.getExtensionNamespacesManager().registerExtension(extNsSpt);
/*  87:143 */     if (!namespace.equals("http://exslt.org/functions"))
/*  88:    */     {
/*  89:145 */       namespace = "http://exslt.org/functions";
/*  90:146 */       args = new Object[] { namespace, sroot };
/*  91:147 */       extNsSpt = new ExtensionNamespaceSupport(namespace, handlerClass, args);
/*  92:148 */       sroot.getExtensionNamespacesManager().registerExtension(extNsSpt);
/*  93:    */     }
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemExsltFunction
 * JD-Core Version:    0.7.0.1
 */