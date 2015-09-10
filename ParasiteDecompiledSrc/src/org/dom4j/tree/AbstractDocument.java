/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.Comment;
/*  10:    */ import org.dom4j.Document;
/*  11:    */ import org.dom4j.DocumentFactory;
/*  12:    */ import org.dom4j.DocumentType;
/*  13:    */ import org.dom4j.Element;
/*  14:    */ import org.dom4j.IllegalAddException;
/*  15:    */ import org.dom4j.Node;
/*  16:    */ import org.dom4j.ProcessingInstruction;
/*  17:    */ import org.dom4j.QName;
/*  18:    */ import org.dom4j.Text;
/*  19:    */ import org.dom4j.Visitor;
/*  20:    */ import org.dom4j.io.OutputFormat;
/*  21:    */ import org.dom4j.io.XMLWriter;
/*  22:    */ 
/*  23:    */ public abstract class AbstractDocument
/*  24:    */   extends AbstractBranch
/*  25:    */   implements Document
/*  26:    */ {
/*  27:    */   protected String encoding;
/*  28:    */   
/*  29:    */   public short getNodeType()
/*  30:    */   {
/*  31: 49 */     return 9;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getPath(Element context)
/*  35:    */   {
/*  36: 53 */     return "/";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getUniquePath(Element context)
/*  40:    */   {
/*  41: 57 */     return "/";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Document getDocument()
/*  45:    */   {
/*  46: 61 */     return this;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getXMLEncoding()
/*  50:    */   {
/*  51: 65 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getStringValue()
/*  55:    */   {
/*  56: 69 */     Element root = getRootElement();
/*  57:    */     
/*  58: 71 */     return root != null ? root.getStringValue() : "";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String asXML()
/*  62:    */   {
/*  63: 75 */     OutputFormat format = new OutputFormat();
/*  64: 76 */     format.setEncoding(this.encoding);
/*  65:    */     try
/*  66:    */     {
/*  67: 79 */       StringWriter out = new StringWriter();
/*  68: 80 */       XMLWriter writer = new XMLWriter(out, format);
/*  69: 81 */       writer.write(this);
/*  70: 82 */       writer.flush();
/*  71:    */       
/*  72: 84 */       return out.toString();
/*  73:    */     }
/*  74:    */     catch (IOException e)
/*  75:    */     {
/*  76: 86 */       throw new RuntimeException("IOException while generating textual representation: " + e.getMessage());
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void write(Writer out)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83: 92 */     OutputFormat format = new OutputFormat();
/*  84: 93 */     format.setEncoding(this.encoding);
/*  85:    */     
/*  86: 95 */     XMLWriter writer = new XMLWriter(out, format);
/*  87: 96 */     writer.write(this);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void accept(Visitor visitor)
/*  91:    */   {
/*  92:109 */     visitor.visit(this);
/*  93:    */     
/*  94:111 */     DocumentType docType = getDocType();
/*  95:113 */     if (docType != null) {
/*  96:114 */       visitor.visit(docType);
/*  97:    */     }
/*  98:118 */     List content = content();
/*  99:    */     Iterator iter;
/* 100:120 */     if (content != null) {
/* 101:121 */       for (iter = content.iterator(); iter.hasNext();)
/* 102:    */       {
/* 103:122 */         Object object = iter.next();
/* 104:124 */         if ((object instanceof String))
/* 105:    */         {
/* 106:125 */           Text text = getDocumentFactory().createText((String)object);
/* 107:    */           
/* 108:127 */           visitor.visit(text);
/* 109:    */         }
/* 110:    */         else
/* 111:    */         {
/* 112:129 */           Node node = (Node)object;
/* 113:130 */           node.accept(visitor);
/* 114:    */         }
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String toString()
/* 120:    */   {
/* 121:137 */     return super.toString() + " [Document: name " + getName() + "]";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void normalize()
/* 125:    */   {
/* 126:141 */     Element element = getRootElement();
/* 127:143 */     if (element != null) {
/* 128:144 */       element.normalize();
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Document addComment(String comment)
/* 133:    */   {
/* 134:149 */     Comment node = getDocumentFactory().createComment(comment);
/* 135:150 */     add(node);
/* 136:    */     
/* 137:152 */     return this;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Document addProcessingInstruction(String target, String data)
/* 141:    */   {
/* 142:156 */     ProcessingInstruction node = getDocumentFactory().createProcessingInstruction(target, data);
/* 143:    */     
/* 144:158 */     add(node);
/* 145:    */     
/* 146:160 */     return this;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Document addProcessingInstruction(String target, Map data)
/* 150:    */   {
/* 151:164 */     ProcessingInstruction node = getDocumentFactory().createProcessingInstruction(target, data);
/* 152:    */     
/* 153:166 */     add(node);
/* 154:    */     
/* 155:168 */     return this;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Element addElement(String name)
/* 159:    */   {
/* 160:172 */     Element element = getDocumentFactory().createElement(name);
/* 161:173 */     add(element);
/* 162:    */     
/* 163:175 */     return element;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Element addElement(String qualifiedName, String namespaceURI)
/* 167:    */   {
/* 168:179 */     Element element = getDocumentFactory().createElement(qualifiedName, namespaceURI);
/* 169:    */     
/* 170:181 */     add(element);
/* 171:    */     
/* 172:183 */     return element;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Element addElement(QName qName)
/* 176:    */   {
/* 177:187 */     Element element = getDocumentFactory().createElement(qName);
/* 178:188 */     add(element);
/* 179:    */     
/* 180:190 */     return element;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setRootElement(Element rootElement)
/* 184:    */   {
/* 185:194 */     clearContent();
/* 186:196 */     if (rootElement != null)
/* 187:    */     {
/* 188:197 */       super.add(rootElement);
/* 189:198 */       rootElementAdded(rootElement);
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void add(Element element)
/* 194:    */   {
/* 195:203 */     checkAddElementAllowed(element);
/* 196:204 */     super.add(element);
/* 197:205 */     rootElementAdded(element);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean remove(Element element)
/* 201:    */   {
/* 202:209 */     boolean answer = super.remove(element);
/* 203:210 */     Element root = getRootElement();
/* 204:212 */     if ((root != null) && (answer)) {
/* 205:213 */       setRootElement(null);
/* 206:    */     }
/* 207:216 */     element.setDocument(null);
/* 208:    */     
/* 209:218 */     return answer;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Node asXPathResult(Element parent)
/* 213:    */   {
/* 214:222 */     return this;
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected void childAdded(Node node)
/* 218:    */   {
/* 219:226 */     if (node != null) {
/* 220:227 */       node.setDocument(this);
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected void childRemoved(Node node)
/* 225:    */   {
/* 226:232 */     if (node != null) {
/* 227:233 */       node.setDocument(null);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   protected void checkAddElementAllowed(Element element)
/* 232:    */   {
/* 233:238 */     Element root = getRootElement();
/* 234:240 */     if (root != null) {
/* 235:241 */       throw new IllegalAddException(this, element, "Cannot add another element to this Document as it already has a root element of: " + root.getQualifiedName());
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected abstract void rootElementAdded(Element paramElement);
/* 240:    */   
/* 241:    */   public void setXMLEncoding(String enc)
/* 242:    */   {
/* 243:257 */     this.encoding = enc;
/* 244:    */   }
/* 245:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractDocument
 * JD-Core Version:    0.7.0.1
 */