/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.dom4j.Document;
/*   7:    */ import org.dom4j.DocumentFactory;
/*   8:    */ import org.dom4j.DocumentType;
/*   9:    */ import org.dom4j.Element;
/*  10:    */ import org.dom4j.IllegalAddException;
/*  11:    */ import org.dom4j.Node;
/*  12:    */ import org.dom4j.ProcessingInstruction;
/*  13:    */ import org.xml.sax.EntityResolver;
/*  14:    */ 
/*  15:    */ public class DefaultDocument
/*  16:    */   extends AbstractDocument
/*  17:    */ {
/*  18: 34 */   protected static final List EMPTY_LIST = Collections.EMPTY_LIST;
/*  19: 36 */   protected static final Iterator EMPTY_ITERATOR = EMPTY_LIST.iterator();
/*  20:    */   private String name;
/*  21:    */   private Element rootElement;
/*  22:    */   private List content;
/*  23:    */   private DocumentType docType;
/*  24: 53 */   private DocumentFactory documentFactory = DocumentFactory.getInstance();
/*  25:    */   private transient EntityResolver entityResolver;
/*  26:    */   
/*  27:    */   public DefaultDocument() {}
/*  28:    */   
/*  29:    */   public DefaultDocument(String name)
/*  30:    */   {
/*  31: 62 */     this.name = name;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DefaultDocument(Element rootElement)
/*  35:    */   {
/*  36: 66 */     this.rootElement = rootElement;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public DefaultDocument(DocumentType docType)
/*  40:    */   {
/*  41: 70 */     this.docType = docType;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public DefaultDocument(Element rootElement, DocumentType docType)
/*  45:    */   {
/*  46: 74 */     this.rootElement = rootElement;
/*  47: 75 */     this.docType = docType;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public DefaultDocument(String name, Element rootElement, DocumentType docType)
/*  51:    */   {
/*  52: 80 */     this.name = name;
/*  53: 81 */     this.rootElement = rootElement;
/*  54: 82 */     this.docType = docType;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getName()
/*  58:    */   {
/*  59: 86 */     return this.name;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setName(String name)
/*  63:    */   {
/*  64: 90 */     this.name = name;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Element getRootElement()
/*  68:    */   {
/*  69: 94 */     return this.rootElement;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public DocumentType getDocType()
/*  73:    */   {
/*  74: 98 */     return this.docType;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setDocType(DocumentType docType)
/*  78:    */   {
/*  79:102 */     this.docType = docType;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Document addDocType(String docTypeName, String publicId, String systemId)
/*  83:    */   {
/*  84:107 */     setDocType(getDocumentFactory().createDocType(docTypeName, publicId, systemId));
/*  85:    */     
/*  86:    */ 
/*  87:110 */     return this;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getXMLEncoding()
/*  91:    */   {
/*  92:114 */     return this.encoding;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public EntityResolver getEntityResolver()
/*  96:    */   {
/*  97:118 */     return this.entityResolver;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setEntityResolver(EntityResolver entityResolver)
/* 101:    */   {
/* 102:122 */     this.entityResolver = entityResolver;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object clone()
/* 106:    */   {
/* 107:126 */     DefaultDocument document = (DefaultDocument)super.clone();
/* 108:127 */     document.rootElement = null;
/* 109:128 */     document.content = null;
/* 110:129 */     document.appendContent(this);
/* 111:    */     
/* 112:131 */     return document;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List processingInstructions()
/* 116:    */   {
/* 117:135 */     List source = contentList();
/* 118:136 */     List answer = createResultList();
/* 119:137 */     int size = source.size();
/* 120:139 */     for (int i = 0; i < size; i++)
/* 121:    */     {
/* 122:140 */       Object object = source.get(i);
/* 123:142 */       if ((object instanceof ProcessingInstruction)) {
/* 124:143 */         answer.add(object);
/* 125:    */       }
/* 126:    */     }
/* 127:147 */     return answer;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List processingInstructions(String target)
/* 131:    */   {
/* 132:151 */     List source = contentList();
/* 133:152 */     List answer = createResultList();
/* 134:153 */     int size = source.size();
/* 135:155 */     for (int i = 0; i < size; i++)
/* 136:    */     {
/* 137:156 */       Object object = source.get(i);
/* 138:158 */       if ((object instanceof ProcessingInstruction))
/* 139:    */       {
/* 140:159 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/* 141:161 */         if (target.equals(pi.getName())) {
/* 142:162 */           answer.add(pi);
/* 143:    */         }
/* 144:    */       }
/* 145:    */     }
/* 146:167 */     return answer;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public ProcessingInstruction processingInstruction(String target)
/* 150:    */   {
/* 151:171 */     List source = contentList();
/* 152:172 */     int size = source.size();
/* 153:174 */     for (int i = 0; i < size; i++)
/* 154:    */     {
/* 155:175 */       Object object = source.get(i);
/* 156:177 */       if ((object instanceof ProcessingInstruction))
/* 157:    */       {
/* 158:178 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/* 159:180 */         if (target.equals(pi.getName())) {
/* 160:181 */           return pi;
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:186 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean removeProcessingInstruction(String target)
/* 168:    */   {
/* 169:190 */     List source = contentList();
/* 170:192 */     for (Iterator iter = source.iterator(); iter.hasNext();)
/* 171:    */     {
/* 172:193 */       Object object = iter.next();
/* 173:195 */       if ((object instanceof ProcessingInstruction))
/* 174:    */       {
/* 175:196 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/* 176:198 */         if (target.equals(pi.getName()))
/* 177:    */         {
/* 178:199 */           iter.remove();
/* 179:    */           
/* 180:201 */           return true;
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:206 */     return false;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setContent(List content)
/* 188:    */   {
/* 189:210 */     this.rootElement = null;
/* 190:211 */     contentRemoved();
/* 191:213 */     if ((content instanceof ContentListFacade)) {
/* 192:214 */       content = ((ContentListFacade)content).getBackingList();
/* 193:    */     }
/* 194:217 */     if (content == null)
/* 195:    */     {
/* 196:218 */       this.content = null;
/* 197:    */     }
/* 198:    */     else
/* 199:    */     {
/* 200:220 */       int size = content.size();
/* 201:221 */       List newContent = createContentList(size);
/* 202:223 */       for (int i = 0; i < size; i++)
/* 203:    */       {
/* 204:224 */         Object object = content.get(i);
/* 205:226 */         if ((object instanceof Node))
/* 206:    */         {
/* 207:227 */           Node node = (Node)object;
/* 208:228 */           Document doc = node.getDocument();
/* 209:230 */           if ((doc != null) && (doc != this)) {
/* 210:231 */             node = (Node)node.clone();
/* 211:    */           }
/* 212:234 */           if ((node instanceof Element)) {
/* 213:235 */             if (this.rootElement == null) {
/* 214:236 */               this.rootElement = ((Element)node);
/* 215:    */             } else {
/* 216:238 */               throw new IllegalAddException("A document may only contain one root element: " + content);
/* 217:    */             }
/* 218:    */           }
/* 219:245 */           newContent.add(node);
/* 220:246 */           childAdded(node);
/* 221:    */         }
/* 222:    */       }
/* 223:250 */       this.content = newContent;
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void clearContent()
/* 228:    */   {
/* 229:255 */     contentRemoved();
/* 230:256 */     this.content = null;
/* 231:257 */     this.rootElement = null;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 235:    */   {
/* 236:261 */     this.documentFactory = documentFactory;
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected List contentList()
/* 240:    */   {
/* 241:267 */     if (this.content == null)
/* 242:    */     {
/* 243:268 */       this.content = createContentList();
/* 244:270 */       if (this.rootElement != null) {
/* 245:271 */         this.content.add(this.rootElement);
/* 246:    */       }
/* 247:    */     }
/* 248:275 */     return this.content;
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected void addNode(Node node)
/* 252:    */   {
/* 253:279 */     if (node != null)
/* 254:    */     {
/* 255:280 */       Document document = node.getDocument();
/* 256:282 */       if ((document != null) && (document != this))
/* 257:    */       {
/* 258:284 */         String message = "The Node already has an existing document: " + document;
/* 259:    */         
/* 260:286 */         throw new IllegalAddException(this, node, message);
/* 261:    */       }
/* 262:289 */       contentList().add(node);
/* 263:290 */       childAdded(node);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected void addNode(int index, Node node)
/* 268:    */   {
/* 269:295 */     if (node != null)
/* 270:    */     {
/* 271:296 */       Document document = node.getDocument();
/* 272:298 */       if ((document != null) && (document != this))
/* 273:    */       {
/* 274:300 */         String message = "The Node already has an existing document: " + document;
/* 275:    */         
/* 276:302 */         throw new IllegalAddException(this, node, message);
/* 277:    */       }
/* 278:305 */       contentList().add(index, node);
/* 279:306 */       childAdded(node);
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   protected boolean removeNode(Node node)
/* 284:    */   {
/* 285:311 */     if (node == this.rootElement) {
/* 286:312 */       this.rootElement = null;
/* 287:    */     }
/* 288:315 */     if (contentList().remove(node))
/* 289:    */     {
/* 290:316 */       childRemoved(node);
/* 291:    */       
/* 292:318 */       return true;
/* 293:    */     }
/* 294:321 */     return false;
/* 295:    */   }
/* 296:    */   
/* 297:    */   protected void rootElementAdded(Element element)
/* 298:    */   {
/* 299:325 */     this.rootElement = element;
/* 300:326 */     element.setDocument(this);
/* 301:    */   }
/* 302:    */   
/* 303:    */   protected DocumentFactory getDocumentFactory()
/* 304:    */   {
/* 305:330 */     return this.documentFactory;
/* 306:    */   }
/* 307:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultDocument
 * JD-Core Version:    0.7.0.1
 */