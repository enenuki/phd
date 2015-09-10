/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.Document;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.DocumentFragment;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.host.Element;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.host.Node;
/*  14:    */ import java.util.Arrays;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.Locale;
/*  17:    */ import java.util.Set;
/*  18:    */ import org.w3c.dom.NamedNodeMap;
/*  19:    */ 
/*  20:    */ public class XMLSerializer
/*  21:    */   extends SimpleScriptable
/*  22:    */ {
/*  23:145 */   private static final Set<String> NON_EMPTY_TAGS = new HashSet(Arrays.asList(new String[] { "abbr", "acronym", "a", "applet", "address", "audio", "bgsound", "bdo", "big", "blink", "blockquote", "body", "b", "button", "canvas", "caption", "center", "cite", "code", "dfn", "dd", "del", "dir", "div", "dl", "dt", "embed", "em", "fieldset", "font", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "html", "iframe", "ins", "isindex", "i", "kbd", "label", "legend", "listing", "li", "map", "marquee", "menu", "multicol", "nobr", "noembed", "noframes", "noscript", "object", "ol", "optgroup", "option", "p", "plaintext", "pre", "q", "s", "samp", "script", "select", "small", "source", "spacer", "span", "strike", "strong", "style", "sub", "sup", "title", "table", "col", "colgroup", "tbody", "td", "th", "tr", "textarea", "tfoot", "thead", "tt", "u", "ul", "var", "video", "wbr", "xmp" }));
/*  24:    */   
/*  25:    */   public void jsConstructor() {}
/*  26:    */   
/*  27:    */   public String jsxFunction_serializeToString(Node root)
/*  28:    */   {
/*  29:200 */     if (root == null) {
/*  30:201 */       return "";
/*  31:    */     }
/*  32:203 */     if ((root instanceof Document)) {
/*  33:204 */       root = ((Document)root).jsxGet_documentElement();
/*  34:206 */     } else if ((root instanceof DocumentFragment)) {
/*  35:207 */       root = root.jsxGet_firstChild();
/*  36:    */     }
/*  37:209 */     if ((root instanceof Element))
/*  38:    */     {
/*  39:210 */       StringBuilder buffer = new StringBuilder();
/*  40:211 */       DomNode node = root.getDomNodeOrDie();
/*  41:212 */       boolean nodeNameAsUpperCase = (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_XML_SERIALIZER_NODE_AS_UPPERCASE)) && ((node.getPage() instanceof HtmlPage));
/*  42:    */       
/*  43:    */ 
/*  44:215 */       boolean appendCrlf = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_XML_SERIALIZER_APPENDS_CRLF);
/*  45:    */       
/*  46:217 */       boolean addXhtmlNamespace = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_XML_SERIALIZER_ADD_XHTML_NAMESPACE);
/*  47:    */       
/*  48:    */ 
/*  49:220 */       String forcedNamespace = null;
/*  50:221 */       if ((addXhtmlNamespace) && 
/*  51:222 */         ((node.getPage() instanceof HtmlPage))) {
/*  52:223 */         forcedNamespace = "http://www.w3.org/1999/xhtml";
/*  53:    */       }
/*  54:226 */       toXml(1, node, buffer, forcedNamespace, nodeNameAsUpperCase, appendCrlf);
/*  55:228 */       if (appendCrlf) {
/*  56:229 */         buffer.append("\r\n");
/*  57:    */       }
/*  58:231 */       return buffer.toString();
/*  59:    */     }
/*  60:233 */     return root.getDomNodeOrDie().asXml();
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void toXml(int indent, DomNode node, StringBuilder buffer, String foredNamespace, boolean nodeNameAsUpperCase, boolean appendCrLf)
/*  64:    */   {
/*  65:239 */     String nodeName = node.getNodeName();
/*  66:240 */     if (nodeNameAsUpperCase) {
/*  67:241 */       nodeName = nodeName.toUpperCase();
/*  68:    */     }
/*  69:243 */     buffer.append('<').append(nodeName);
/*  70:    */     
/*  71:245 */     String optionalPrefix = "";
/*  72:246 */     String namespaceURI = node.getNamespaceURI();
/*  73:247 */     String prefix = node.getPrefix();
/*  74:248 */     if ((namespaceURI != null) && (prefix != null))
/*  75:    */     {
/*  76:249 */       boolean sameNamespace = false;
/*  77:250 */       for (DomNode parentNode = node.getParentNode(); (parentNode instanceof DomElement); parentNode = parentNode.getParentNode()) {
/*  78:252 */         if (namespaceURI.equals(parentNode.getNamespaceURI())) {
/*  79:253 */           sameNamespace = true;
/*  80:    */         }
/*  81:    */       }
/*  82:256 */       if ((node.getParentNode() == null) || (!sameNamespace)) {
/*  83:257 */         ((DomElement)node).setAttribute("xmlns:" + prefix, namespaceURI);
/*  84:    */       }
/*  85:    */     }
/*  86:260 */     else if (foredNamespace != null)
/*  87:    */     {
/*  88:261 */       buffer.append(" xmlns=\"").append(foredNamespace).append('"');
/*  89:262 */       optionalPrefix = " ";
/*  90:    */     }
/*  91:265 */     NamedNodeMap attributesMap = node.getAttributes();
/*  92:266 */     for (int i = 0; i < attributesMap.getLength(); i++)
/*  93:    */     {
/*  94:267 */       DomAttr attrib = (DomAttr)attributesMap.item(i);
/*  95:268 */       buffer.append(' ').append(attrib.getQualifiedName()).append('=').append('"').append(attrib.getValue()).append('"');
/*  96:    */     }
/*  97:271 */     boolean startTagClosed = false;
/*  98:272 */     for (DomNode child : node.getChildren())
/*  99:    */     {
/* 100:273 */       if (!startTagClosed)
/* 101:    */       {
/* 102:274 */         buffer.append(optionalPrefix).append('>');
/* 103:275 */         startTagClosed = true;
/* 104:    */       }
/* 105:277 */       switch (child.getNodeType())
/* 106:    */       {
/* 107:    */       case 1: 
/* 108:279 */         toXml(indent + 1, child, buffer, null, nodeNameAsUpperCase, appendCrLf);
/* 109:280 */         break;
/* 110:    */       case 3: 
/* 111:283 */         String value = child.getNodeValue();
/* 112:284 */         value = com.gargoylesoftware.htmlunit.util.StringUtils.escapeXmlChars(value);
/* 113:285 */         if ((appendCrLf) && (org.apache.commons.lang.StringUtils.isBlank(value)))
/* 114:    */         {
/* 115:286 */           buffer.append("\r\n");
/* 116:287 */           DomNode sibling = child.getNextSibling();
/* 117:288 */           if ((sibling != null) && (sibling.getNodeType() == 1)) {
/* 118:289 */             for (int i = 0; i < indent; i++) {
/* 119:290 */               buffer.append('\t');
/* 120:    */             }
/* 121:    */           }
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:295 */           buffer.append(value);
/* 126:    */         }
/* 127:297 */         break;
/* 128:    */       case 4: 
/* 129:    */       case 8: 
/* 130:301 */         buffer.append(child.asXml());
/* 131:    */       }
/* 132:    */     }
/* 133:308 */     if (!startTagClosed)
/* 134:    */     {
/* 135:309 */       String tagName = nodeName.toLowerCase(Locale.ENGLISH);
/* 136:310 */       boolean nonEmptyTagsSupported = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_XML_SERIALIZER_NON_EMPTY_TAGS);
/* 137:312 */       if ((nonEmptyTagsSupported) && (NON_EMPTY_TAGS.contains(tagName)))
/* 138:    */       {
/* 139:313 */         buffer.append('>');
/* 140:314 */         buffer.append("</").append(nodeName).append('>');
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:317 */         buffer.append(optionalPrefix).append("/>");
/* 145:    */       }
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:321 */       buffer.append('<').append('/').append(nodeName).append('>');
/* 150:    */     }
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.xml.XMLSerializer
 * JD-Core Version:    0.7.0.1
 */