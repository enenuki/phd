/*   1:    */ package org.dom4j.util;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.CDATA;
/*   6:    */ import org.dom4j.Comment;
/*   7:    */ import org.dom4j.Document;
/*   8:    */ import org.dom4j.DocumentFactory;
/*   9:    */ import org.dom4j.DocumentType;
/*  10:    */ import org.dom4j.Element;
/*  11:    */ import org.dom4j.Entity;
/*  12:    */ import org.dom4j.Namespace;
/*  13:    */ import org.dom4j.NodeFilter;
/*  14:    */ import org.dom4j.ProcessingInstruction;
/*  15:    */ import org.dom4j.QName;
/*  16:    */ import org.dom4j.Text;
/*  17:    */ import org.dom4j.XPath;
/*  18:    */ import org.dom4j.rule.Pattern;
/*  19:    */ import org.jaxen.VariableContext;
/*  20:    */ 
/*  21:    */ public abstract class ProxyDocumentFactory
/*  22:    */ {
/*  23:    */   private DocumentFactory proxy;
/*  24:    */   
/*  25:    */   public ProxyDocumentFactory()
/*  26:    */   {
/*  27: 47 */     this.proxy = DocumentFactory.getInstance();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ProxyDocumentFactory(DocumentFactory proxy)
/*  31:    */   {
/*  32: 51 */     this.proxy = proxy;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Document createDocument()
/*  36:    */   {
/*  37: 57 */     return this.proxy.createDocument();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Document createDocument(Element rootElement)
/*  41:    */   {
/*  42: 61 */     return this.proxy.createDocument(rootElement);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public DocumentType createDocType(String name, String publicId, String systemId)
/*  46:    */   {
/*  47: 66 */     return this.proxy.createDocType(name, publicId, systemId);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Element createElement(QName qname)
/*  51:    */   {
/*  52: 70 */     return this.proxy.createElement(qname);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Element createElement(String name)
/*  56:    */   {
/*  57: 74 */     return this.proxy.createElement(name);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Attribute createAttribute(Element owner, QName qname, String value)
/*  61:    */   {
/*  62: 78 */     return this.proxy.createAttribute(owner, qname, value);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Attribute createAttribute(Element owner, String name, String value)
/*  66:    */   {
/*  67: 82 */     return this.proxy.createAttribute(owner, name, value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public CDATA createCDATA(String text)
/*  71:    */   {
/*  72: 86 */     return this.proxy.createCDATA(text);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Comment createComment(String text)
/*  76:    */   {
/*  77: 90 */     return this.proxy.createComment(text);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Text createText(String text)
/*  81:    */   {
/*  82: 94 */     return this.proxy.createText(text);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Entity createEntity(String name, String text)
/*  86:    */   {
/*  87: 98 */     return this.proxy.createEntity(name, text);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Namespace createNamespace(String prefix, String uri)
/*  91:    */   {
/*  92:102 */     return this.proxy.createNamespace(prefix, uri);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/*  96:    */   {
/*  97:107 */     return this.proxy.createProcessingInstruction(target, data);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ProcessingInstruction createProcessingInstruction(String target, Map data)
/* 101:    */   {
/* 102:112 */     return this.proxy.createProcessingInstruction(target, data);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public QName createQName(String localName, Namespace namespace)
/* 106:    */   {
/* 107:116 */     return this.proxy.createQName(localName, namespace);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public QName createQName(String localName)
/* 111:    */   {
/* 112:120 */     return this.proxy.createQName(localName);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public QName createQName(String name, String prefix, String uri)
/* 116:    */   {
/* 117:124 */     return this.proxy.createQName(name, prefix, uri);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public QName createQName(String qualifiedName, String uri)
/* 121:    */   {
/* 122:128 */     return this.proxy.createQName(qualifiedName, uri);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public XPath createXPath(String xpathExpression)
/* 126:    */   {
/* 127:132 */     return this.proxy.createXPath(xpathExpression);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public XPath createXPath(String xpathExpression, VariableContext variableContext)
/* 131:    */   {
/* 132:137 */     return this.proxy.createXPath(xpathExpression, variableContext);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public NodeFilter createXPathFilter(String xpathFilterExpression, VariableContext variableContext)
/* 136:    */   {
/* 137:142 */     return this.proxy.createXPathFilter(xpathFilterExpression, variableContext);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public NodeFilter createXPathFilter(String xpathFilterExpression)
/* 141:    */   {
/* 142:146 */     return this.proxy.createXPathFilter(xpathFilterExpression);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Pattern createPattern(String xpathPattern)
/* 146:    */   {
/* 147:150 */     return this.proxy.createPattern(xpathPattern);
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected DocumentFactory getProxy()
/* 151:    */   {
/* 152:156 */     return this.proxy;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected void setProxy(DocumentFactory proxy)
/* 156:    */   {
/* 157:160 */     if (proxy == null) {
/* 158:162 */       proxy = DocumentFactory.getInstance();
/* 159:    */     }
/* 160:165 */     this.proxy = proxy;
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.ProxyDocumentFactory
 * JD-Core Version:    0.7.0.1
 */