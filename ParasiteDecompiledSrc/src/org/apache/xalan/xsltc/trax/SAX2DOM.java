/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.parsers.DocumentBuilder;
/*   6:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   7:    */ import javax.xml.parsers.ParserConfigurationException;
/*   8:    */ import org.apache.xalan.xsltc.runtime.Constants;
/*   9:    */ import org.w3c.dom.CharacterData;
/*  10:    */ import org.w3c.dom.Comment;
/*  11:    */ import org.w3c.dom.Document;
/*  12:    */ import org.w3c.dom.Element;
/*  13:    */ import org.w3c.dom.Node;
/*  14:    */ import org.w3c.dom.ProcessingInstruction;
/*  15:    */ import org.w3c.dom.Text;
/*  16:    */ import org.xml.sax.Attributes;
/*  17:    */ import org.xml.sax.ContentHandler;
/*  18:    */ import org.xml.sax.Locator;
/*  19:    */ import org.xml.sax.SAXException;
/*  20:    */ import org.xml.sax.ext.LexicalHandler;
/*  21:    */ 
/*  22:    */ public class SAX2DOM
/*  23:    */   implements ContentHandler, LexicalHandler, Constants
/*  24:    */ {
/*  25: 51 */   private Node _root = null;
/*  26: 52 */   private Document _document = null;
/*  27: 53 */   private Node _nextSibling = null;
/*  28: 54 */   private Stack _nodeStk = new Stack();
/*  29: 55 */   private Vector _namespaceDecls = null;
/*  30: 56 */   private Node _lastSibling = null;
/*  31:    */   
/*  32:    */   public SAX2DOM()
/*  33:    */     throws ParserConfigurationException
/*  34:    */   {
/*  35: 59 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  36:    */     
/*  37: 61 */     this._document = factory.newDocumentBuilder().newDocument();
/*  38: 62 */     this._root = this._document;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public SAX2DOM(Node root, Node nextSibling)
/*  42:    */     throws ParserConfigurationException
/*  43:    */   {
/*  44: 66 */     this._root = root;
/*  45: 67 */     if ((root instanceof Document))
/*  46:    */     {
/*  47: 68 */       this._document = ((Document)root);
/*  48:    */     }
/*  49: 70 */     else if (root != null)
/*  50:    */     {
/*  51: 71 */       this._document = root.getOwnerDocument();
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 74 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  56:    */       
/*  57: 76 */       this._document = factory.newDocumentBuilder().newDocument();
/*  58: 77 */       this._root = this._document;
/*  59:    */     }
/*  60: 80 */     this._nextSibling = nextSibling;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public SAX2DOM(Node root)
/*  64:    */     throws ParserConfigurationException
/*  65:    */   {
/*  66: 84 */     this(root, null);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Node getDOM()
/*  70:    */   {
/*  71: 88 */     return this._root;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void characters(char[] ch, int start, int length)
/*  75:    */   {
/*  76: 92 */     Node last = (Node)this._nodeStk.peek();
/*  77: 95 */     if (last != this._document)
/*  78:    */     {
/*  79: 96 */       String text = new String(ch, start, length);
/*  80: 97 */       if ((this._lastSibling != null) && (this._lastSibling.getNodeType() == 3)) {
/*  81: 98 */         ((Text)this._lastSibling).appendData(text);
/*  82:100 */       } else if ((last == this._root) && (this._nextSibling != null)) {
/*  83:101 */         this._lastSibling = last.insertBefore(this._document.createTextNode(text), this._nextSibling);
/*  84:    */       } else {
/*  85:104 */         this._lastSibling = last.appendChild(this._document.createTextNode(text));
/*  86:    */       }
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void startDocument()
/*  91:    */   {
/*  92:111 */     this._nodeStk.push(this._root);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void endDocument()
/*  96:    */   {
/*  97:115 */     this._nodeStk.pop();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void startElement(String namespace, String localName, String qName, Attributes attrs)
/* 101:    */   {
/* 102:121 */     Element tmp = this._document.createElementNS(namespace, qName);
/* 103:124 */     if (this._namespaceDecls != null)
/* 104:    */     {
/* 105:125 */       int nDecls = this._namespaceDecls.size();
/* 106:126 */       for (int i = 0; i < nDecls; i++)
/* 107:    */       {
/* 108:127 */         String prefix = (String)this._namespaceDecls.elementAt(i++);
/* 109:129 */         if ((prefix == null) || (prefix.equals(""))) {
/* 110:130 */           tmp.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", (String)this._namespaceDecls.elementAt(i));
/* 111:    */         } else {
/* 112:134 */           tmp.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, (String)this._namespaceDecls.elementAt(i));
/* 113:    */         }
/* 114:    */       }
/* 115:138 */       this._namespaceDecls.clear();
/* 116:    */     }
/* 117:142 */     int nattrs = attrs.getLength();
/* 118:143 */     for (int i = 0; i < nattrs; i++) {
/* 119:144 */       if (attrs.getLocalName(i) == null) {
/* 120:145 */         tmp.setAttribute(attrs.getQName(i), attrs.getValue(i));
/* 121:    */       } else {
/* 122:148 */         tmp.setAttributeNS(attrs.getURI(i), attrs.getQName(i), attrs.getValue(i));
/* 123:    */       }
/* 124:    */     }
/* 125:154 */     Node last = (Node)this._nodeStk.peek();
/* 126:158 */     if ((last == this._root) && (this._nextSibling != null)) {
/* 127:159 */       last.insertBefore(tmp, this._nextSibling);
/* 128:    */     } else {
/* 129:161 */       last.appendChild(tmp);
/* 130:    */     }
/* 131:164 */     this._nodeStk.push(tmp);
/* 132:165 */     this._lastSibling = null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void endElement(String namespace, String localName, String qName)
/* 136:    */   {
/* 137:169 */     this._nodeStk.pop();
/* 138:170 */     this._lastSibling = null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void startPrefixMapping(String prefix, String uri)
/* 142:    */   {
/* 143:174 */     if (this._namespaceDecls == null) {
/* 144:175 */       this._namespaceDecls = new Vector(2);
/* 145:    */     }
/* 146:177 */     this._namespaceDecls.addElement(prefix);
/* 147:178 */     this._namespaceDecls.addElement(uri);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void endPrefixMapping(String prefix) {}
/* 151:    */   
/* 152:    */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/* 153:    */   
/* 154:    */   public void processingInstruction(String target, String data)
/* 155:    */   {
/* 156:196 */     Node last = (Node)this._nodeStk.peek();
/* 157:197 */     ProcessingInstruction pi = this._document.createProcessingInstruction(target, data);
/* 158:199 */     if (pi != null)
/* 159:    */     {
/* 160:200 */       if ((last == this._root) && (this._nextSibling != null)) {
/* 161:201 */         last.insertBefore(pi, this._nextSibling);
/* 162:    */       } else {
/* 163:203 */         last.appendChild(pi);
/* 164:    */       }
/* 165:205 */       this._lastSibling = pi;
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setDocumentLocator(Locator locator) {}
/* 170:    */   
/* 171:    */   public void skippedEntity(String name) {}
/* 172:    */   
/* 173:    */   public void comment(char[] ch, int start, int length)
/* 174:    */   {
/* 175:228 */     Node last = (Node)this._nodeStk.peek();
/* 176:229 */     Comment comment = this._document.createComment(new String(ch, start, length));
/* 177:230 */     if (comment != null)
/* 178:    */     {
/* 179:231 */       if ((last == this._root) && (this._nextSibling != null)) {
/* 180:232 */         last.insertBefore(comment, this._nextSibling);
/* 181:    */       } else {
/* 182:234 */         last.appendChild(comment);
/* 183:    */       }
/* 184:236 */       this._lastSibling = comment;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void startCDATA() {}
/* 189:    */   
/* 190:    */   public void endCDATA() {}
/* 191:    */   
/* 192:    */   public void startEntity(String name) {}
/* 193:    */   
/* 194:    */   public void endDTD() {}
/* 195:    */   
/* 196:    */   public void endEntity(String name) {}
/* 197:    */   
/* 198:    */   public void startDTD(String name, String publicId, String systemId)
/* 199:    */     throws SAXException
/* 200:    */   {}
/* 201:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.SAX2DOM
 * JD-Core Version:    0.7.0.1
 */