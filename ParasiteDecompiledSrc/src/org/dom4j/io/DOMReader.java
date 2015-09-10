/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.dom4j.Branch;
/*   7:    */ import org.dom4j.DocumentFactory;
/*   8:    */ import org.dom4j.Element;
/*   9:    */ import org.dom4j.Namespace;
/*  10:    */ import org.dom4j.QName;
/*  11:    */ import org.dom4j.tree.NamespaceStack;
/*  12:    */ import org.w3c.dom.DocumentType;
/*  13:    */ import org.w3c.dom.NamedNodeMap;
/*  14:    */ import org.w3c.dom.Node;
/*  15:    */ import org.w3c.dom.NodeList;
/*  16:    */ 
/*  17:    */ public class DOMReader
/*  18:    */ {
/*  19:    */   private DocumentFactory factory;
/*  20:    */   private NamespaceStack namespaceStack;
/*  21:    */   
/*  22:    */   public DOMReader()
/*  23:    */   {
/*  24: 38 */     this.factory = DocumentFactory.getInstance();
/*  25: 39 */     this.namespaceStack = new NamespaceStack(this.factory);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public DOMReader(DocumentFactory factory)
/*  29:    */   {
/*  30: 43 */     this.factory = factory;
/*  31: 44 */     this.namespaceStack = new NamespaceStack(factory);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DocumentFactory getDocumentFactory()
/*  35:    */   {
/*  36: 54 */     return this.factory;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setDocumentFactory(DocumentFactory docFactory)
/*  40:    */   {
/*  41: 69 */     this.factory = docFactory;
/*  42: 70 */     this.namespaceStack.setDocumentFactory(this.factory);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public org.dom4j.Document read(org.w3c.dom.Document domDocument)
/*  46:    */   {
/*  47: 74 */     if ((domDocument instanceof org.dom4j.Document)) {
/*  48: 75 */       return (org.dom4j.Document)domDocument;
/*  49:    */     }
/*  50: 78 */     org.dom4j.Document document = createDocument();
/*  51:    */     
/*  52: 80 */     clearNamespaceStack();
/*  53:    */     
/*  54: 82 */     NodeList nodeList = domDocument.getChildNodes();
/*  55:    */     
/*  56: 84 */     int i = 0;
/*  57: 84 */     for (int size = nodeList.getLength(); i < size; i++) {
/*  58: 85 */       readTree(nodeList.item(i), document);
/*  59:    */     }
/*  60: 88 */     return document;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void readTree(Node node, Branch current)
/*  64:    */   {
/*  65: 93 */     Element element = null;
/*  66: 94 */     org.dom4j.Document document = null;
/*  67: 96 */     if ((current instanceof Element)) {
/*  68: 97 */       element = (Element)current;
/*  69:    */     } else {
/*  70: 99 */       document = (org.dom4j.Document)current;
/*  71:    */     }
/*  72:102 */     switch (node.getNodeType())
/*  73:    */     {
/*  74:    */     case 1: 
/*  75:104 */       readElement(node, current);
/*  76:    */       
/*  77:106 */       break;
/*  78:    */     case 7: 
/*  79:110 */       if ((current instanceof Element))
/*  80:    */       {
/*  81:111 */         Element currentEl = (Element)current;
/*  82:112 */         currentEl.addProcessingInstruction(node.getNodeName(), node.getNodeValue());
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:115 */         org.dom4j.Document currentDoc = (org.dom4j.Document)current;
/*  87:116 */         currentDoc.addProcessingInstruction(node.getNodeName(), node.getNodeValue());
/*  88:    */       }
/*  89:120 */       break;
/*  90:    */     case 8: 
/*  91:124 */       if ((current instanceof Element)) {
/*  92:125 */         ((Element)current).addComment(node.getNodeValue());
/*  93:    */       } else {
/*  94:127 */         ((org.dom4j.Document)current).addComment(node.getNodeValue());
/*  95:    */       }
/*  96:130 */       break;
/*  97:    */     case 10: 
/*  98:134 */       DocumentType domDocType = (DocumentType)node;
/*  99:    */       
/* 100:136 */       document.addDocType(domDocType.getName(), domDocType.getPublicId(), domDocType.getSystemId());
/* 101:    */       
/* 102:    */ 
/* 103:139 */       break;
/* 104:    */     case 3: 
/* 105:142 */       element.addText(node.getNodeValue());
/* 106:    */       
/* 107:144 */       break;
/* 108:    */     case 4: 
/* 109:147 */       element.addCDATA(node.getNodeValue());
/* 110:    */       
/* 111:149 */       break;
/* 112:    */     case 5: 
/* 113:154 */       Node firstChild = node.getFirstChild();
/* 114:156 */       if (firstChild != null) {
/* 115:157 */         element.addEntity(node.getNodeName(), firstChild.getNodeValue());
/* 116:    */       } else {
/* 117:160 */         element.addEntity(node.getNodeName(), "");
/* 118:    */       }
/* 119:163 */       break;
/* 120:    */     case 6: 
/* 121:166 */       element.addEntity(node.getNodeName(), node.getNodeValue());
/* 122:    */       
/* 123:168 */       break;
/* 124:    */     case 2: 
/* 125:    */     case 9: 
/* 126:    */     default: 
/* 127:171 */       System.out.println("WARNING: Unknown DOM node type: " + node.getNodeType());
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void readElement(Node node, Branch current)
/* 132:    */   {
/* 133:177 */     int previouslyDeclaredNamespaces = this.namespaceStack.size();
/* 134:    */     
/* 135:179 */     String namespaceUri = node.getNamespaceURI();
/* 136:180 */     String elementPrefix = node.getPrefix();
/* 137:182 */     if (elementPrefix == null) {
/* 138:183 */       elementPrefix = "";
/* 139:    */     }
/* 140:186 */     NamedNodeMap attributeList = node.getAttributes();
/* 141:188 */     if ((attributeList != null) && (namespaceUri == null))
/* 142:    */     {
/* 143:190 */       Node attribute = attributeList.getNamedItem("xmlns");
/* 144:192 */       if (attribute != null)
/* 145:    */       {
/* 146:193 */         namespaceUri = attribute.getNodeValue();
/* 147:194 */         elementPrefix = "";
/* 148:    */       }
/* 149:    */     }
/* 150:198 */     QName qName = this.namespaceStack.getQName(namespaceUri, node.getLocalName(), node.getNodeName());
/* 151:    */     
/* 152:200 */     Element element = current.addElement(qName);
/* 153:202 */     if (attributeList != null)
/* 154:    */     {
/* 155:203 */       int size = attributeList.getLength();
/* 156:204 */       List attributes = new ArrayList(size);
/* 157:206 */       for (int i = 0; i < size; i++)
/* 158:    */       {
/* 159:207 */         Node attribute = attributeList.item(i);
/* 160:    */         
/* 161:    */ 
/* 162:210 */         String name = attribute.getNodeName();
/* 163:212 */         if (name.startsWith("xmlns"))
/* 164:    */         {
/* 165:213 */           String prefix = getPrefix(name);
/* 166:214 */           String uri = attribute.getNodeValue();
/* 167:    */           
/* 168:216 */           Namespace namespace = this.namespaceStack.addNamespace(prefix, uri);
/* 169:    */           
/* 170:218 */           element.add(namespace);
/* 171:    */         }
/* 172:    */         else
/* 173:    */         {
/* 174:220 */           attributes.add(attribute);
/* 175:    */         }
/* 176:    */       }
/* 177:225 */       size = attributes.size();
/* 178:227 */       for (int i = 0; i < size; i++)
/* 179:    */       {
/* 180:228 */         Node attribute = (Node)attributes.get(i);
/* 181:    */         
/* 182:230 */         QName attributeQName = this.namespaceStack.getQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getNodeName());
/* 183:    */         
/* 184:    */ 
/* 185:233 */         element.addAttribute(attributeQName, attribute.getNodeValue());
/* 186:    */       }
/* 187:    */     }
/* 188:238 */     NodeList children = node.getChildNodes();
/* 189:    */     
/* 190:240 */     int i = 0;
/* 191:240 */     for (int size = children.getLength(); i < size; i++)
/* 192:    */     {
/* 193:241 */       Node child = children.item(i);
/* 194:242 */       readTree(child, element);
/* 195:    */     }
/* 196:246 */     while (this.namespaceStack.size() > previouslyDeclaredNamespaces) {
/* 197:247 */       this.namespaceStack.pop();
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected Namespace getNamespace(String prefix, String uri)
/* 202:    */   {
/* 203:252 */     return getDocumentFactory().createNamespace(prefix, uri);
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected org.dom4j.Document createDocument()
/* 207:    */   {
/* 208:256 */     return getDocumentFactory().createDocument();
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void clearNamespaceStack()
/* 212:    */   {
/* 213:260 */     this.namespaceStack.clear();
/* 214:262 */     if (!this.namespaceStack.contains(Namespace.XML_NAMESPACE)) {
/* 215:263 */       this.namespaceStack.push(Namespace.XML_NAMESPACE);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String getPrefix(String xmlnsDecl)
/* 220:    */   {
/* 221:268 */     int index = xmlnsDecl.indexOf(':', 5);
/* 222:270 */     if (index != -1) {
/* 223:271 */       return xmlnsDecl.substring(index + 1);
/* 224:    */     }
/* 225:273 */     return "";
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DOMReader
 * JD-Core Version:    0.7.0.1
 */