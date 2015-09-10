/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.dom4j.Branch;
/*   8:    */ import org.dom4j.Comment;
/*   9:    */ import org.dom4j.DocumentFactory;
/*  10:    */ import org.dom4j.Element;
/*  11:    */ import org.dom4j.IllegalAddException;
/*  12:    */ import org.dom4j.Namespace;
/*  13:    */ import org.dom4j.Node;
/*  14:    */ import org.dom4j.ProcessingInstruction;
/*  15:    */ import org.dom4j.QName;
/*  16:    */ 
/*  17:    */ public abstract class AbstractBranch
/*  18:    */   extends AbstractNode
/*  19:    */   implements Branch
/*  20:    */ {
/*  21:    */   protected static final int DEFAULT_CONTENT_LIST_SIZE = 5;
/*  22:    */   
/*  23:    */   public boolean isReadOnly()
/*  24:    */   {
/*  25: 40 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean hasContent()
/*  29:    */   {
/*  30: 44 */     return nodeCount() > 0;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List content()
/*  34:    */   {
/*  35: 48 */     List backingList = contentList();
/*  36:    */     
/*  37: 50 */     return new ContentListFacade(this, backingList);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getText()
/*  41:    */   {
/*  42: 54 */     List content = contentList();
/*  43: 56 */     if (content != null)
/*  44:    */     {
/*  45: 57 */       int size = content.size();
/*  46: 59 */       if (size >= 1)
/*  47:    */       {
/*  48: 60 */         Object first = content.get(0);
/*  49: 61 */         String firstText = getContentAsText(first);
/*  50: 63 */         if (size == 1) {
/*  51: 65 */           return firstText;
/*  52:    */         }
/*  53: 67 */         StringBuffer buffer = new StringBuffer(firstText);
/*  54: 69 */         for (int i = 1; i < size; i++)
/*  55:    */         {
/*  56: 70 */           Object node = content.get(i);
/*  57: 71 */           buffer.append(getContentAsText(node));
/*  58:    */         }
/*  59: 74 */         return buffer.toString();
/*  60:    */       }
/*  61:    */     }
/*  62: 79 */     return "";
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected String getContentAsText(Object content)
/*  66:    */   {
/*  67: 92 */     if ((content instanceof Node))
/*  68:    */     {
/*  69: 93 */       Node node = (Node)content;
/*  70: 95 */       switch (node.getNodeType())
/*  71:    */       {
/*  72:    */       case 3: 
/*  73:    */       case 4: 
/*  74:    */       case 5: 
/*  75:101 */         return node.getText();
/*  76:    */       }
/*  77:    */     }
/*  78:106 */     else if ((content instanceof String))
/*  79:    */     {
/*  80:107 */       return (String)content;
/*  81:    */     }
/*  82:110 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String getContentAsStringValue(Object content)
/*  86:    */   {
/*  87:122 */     if ((content instanceof Node))
/*  88:    */     {
/*  89:123 */       Node node = (Node)content;
/*  90:125 */       switch (node.getNodeType())
/*  91:    */       {
/*  92:    */       case 1: 
/*  93:    */       case 3: 
/*  94:    */       case 4: 
/*  95:    */       case 5: 
/*  96:132 */         return node.getStringValue();
/*  97:    */       }
/*  98:    */     }
/*  99:137 */     else if ((content instanceof String))
/* 100:    */     {
/* 101:138 */       return (String)content;
/* 102:    */     }
/* 103:141 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getTextTrim()
/* 107:    */   {
/* 108:145 */     String text = getText();
/* 109:    */     
/* 110:147 */     StringBuffer textContent = new StringBuffer();
/* 111:148 */     StringTokenizer tokenizer = new StringTokenizer(text);
/* 112:150 */     while (tokenizer.hasMoreTokens())
/* 113:    */     {
/* 114:151 */       String str = tokenizer.nextToken();
/* 115:152 */       textContent.append(str);
/* 116:154 */       if (tokenizer.hasMoreTokens()) {
/* 117:155 */         textContent.append(" ");
/* 118:    */       }
/* 119:    */     }
/* 120:159 */     return textContent.toString();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setProcessingInstructions(List listOfPIs)
/* 124:    */   {
/* 125:163 */     for (Iterator iter = listOfPIs.iterator(); iter.hasNext();)
/* 126:    */     {
/* 127:164 */       ProcessingInstruction pi = (ProcessingInstruction)iter.next();
/* 128:165 */       addNode(pi);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Element addElement(String name)
/* 133:    */   {
/* 134:170 */     Element node = getDocumentFactory().createElement(name);
/* 135:171 */     add(node);
/* 136:    */     
/* 137:173 */     return node;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Element addElement(String qualifiedName, String namespaceURI)
/* 141:    */   {
/* 142:177 */     Element node = getDocumentFactory().createElement(qualifiedName, namespaceURI);
/* 143:    */     
/* 144:179 */     add(node);
/* 145:    */     
/* 146:181 */     return node;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Element addElement(QName qname)
/* 150:    */   {
/* 151:185 */     Element node = getDocumentFactory().createElement(qname);
/* 152:186 */     add(node);
/* 153:    */     
/* 154:188 */     return node;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Element addElement(String name, String prefix, String uri)
/* 158:    */   {
/* 159:192 */     Namespace namespace = Namespace.get(prefix, uri);
/* 160:193 */     QName qName = getDocumentFactory().createQName(name, namespace);
/* 161:    */     
/* 162:195 */     return addElement(qName);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void add(Node node)
/* 166:    */   {
/* 167:200 */     switch (node.getNodeType())
/* 168:    */     {
/* 169:    */     case 1: 
/* 170:202 */       add((Element)node);
/* 171:    */       
/* 172:204 */       break;
/* 173:    */     case 8: 
/* 174:207 */       add((Comment)node);
/* 175:    */       
/* 176:209 */       break;
/* 177:    */     case 7: 
/* 178:212 */       add((ProcessingInstruction)node);
/* 179:    */       
/* 180:214 */       break;
/* 181:    */     default: 
/* 182:217 */       invalidNodeTypeAddException(node);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean remove(Node node)
/* 187:    */   {
/* 188:222 */     switch (node.getNodeType())
/* 189:    */     {
/* 190:    */     case 1: 
/* 191:224 */       return remove((Element)node);
/* 192:    */     case 8: 
/* 193:227 */       return remove((Comment)node);
/* 194:    */     case 7: 
/* 195:230 */       return remove((ProcessingInstruction)node);
/* 196:    */     }
/* 197:233 */     invalidNodeTypeAddException(node);
/* 198:    */     
/* 199:235 */     return false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void add(Comment comment)
/* 203:    */   {
/* 204:241 */     addNode(comment);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void add(Element element)
/* 208:    */   {
/* 209:245 */     addNode(element);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void add(ProcessingInstruction pi)
/* 213:    */   {
/* 214:249 */     addNode(pi);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean remove(Comment comment)
/* 218:    */   {
/* 219:253 */     return removeNode(comment);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean remove(Element element)
/* 223:    */   {
/* 224:257 */     return removeNode(element);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean remove(ProcessingInstruction pi)
/* 228:    */   {
/* 229:261 */     return removeNode(pi);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Element elementByID(String elementID)
/* 233:    */   {
/* 234:265 */     int i = 0;
/* 235:265 */     for (int size = nodeCount(); i < size; i++)
/* 236:    */     {
/* 237:266 */       Node node = node(i);
/* 238:268 */       if ((node instanceof Element))
/* 239:    */       {
/* 240:269 */         Element element = (Element)node;
/* 241:270 */         String id = elementID(element);
/* 242:272 */         if ((id != null) && (id.equals(elementID))) {
/* 243:273 */           return element;
/* 244:    */         }
/* 245:275 */         element = element.elementByID(elementID);
/* 246:277 */         if (element != null) {
/* 247:278 */           return element;
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:284 */     return null;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void appendContent(Branch branch)
/* 255:    */   {
/* 256:288 */     int i = 0;
/* 257:288 */     for (int size = branch.nodeCount(); i < size; i++)
/* 258:    */     {
/* 259:289 */       Node node = branch.node(i);
/* 260:290 */       add((Node)node.clone());
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public Node node(int index)
/* 265:    */   {
/* 266:295 */     Object object = contentList().get(index);
/* 267:297 */     if ((object instanceof Node)) {
/* 268:298 */       return (Node)object;
/* 269:    */     }
/* 270:301 */     if ((object instanceof String)) {
/* 271:302 */       return getDocumentFactory().createText(object.toString());
/* 272:    */     }
/* 273:305 */     return null;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public int nodeCount()
/* 277:    */   {
/* 278:309 */     return contentList().size();
/* 279:    */   }
/* 280:    */   
/* 281:    */   public int indexOf(Node node)
/* 282:    */   {
/* 283:313 */     return contentList().indexOf(node);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public Iterator nodeIterator()
/* 287:    */   {
/* 288:317 */     return contentList().iterator();
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected String elementID(Element element)
/* 292:    */   {
/* 293:333 */     return element.attributeValue("ID");
/* 294:    */   }
/* 295:    */   
/* 296:    */   protected abstract List contentList();
/* 297:    */   
/* 298:    */   protected List createContentList()
/* 299:    */   {
/* 300:350 */     return new ArrayList(5);
/* 301:    */   }
/* 302:    */   
/* 303:    */   protected List createContentList(int size)
/* 304:    */   {
/* 305:363 */     return new ArrayList(size);
/* 306:    */   }
/* 307:    */   
/* 308:    */   protected BackedList createResultList()
/* 309:    */   {
/* 310:373 */     return new BackedList(this, contentList());
/* 311:    */   }
/* 312:    */   
/* 313:    */   protected List createSingleResultList(Object result)
/* 314:    */   {
/* 315:386 */     BackedList list = new BackedList(this, contentList(), 1);
/* 316:387 */     list.addLocal(result);
/* 317:    */     
/* 318:389 */     return list;
/* 319:    */   }
/* 320:    */   
/* 321:    */   protected List createEmptyList()
/* 322:    */   {
/* 323:399 */     return new BackedList(this, contentList(), 0);
/* 324:    */   }
/* 325:    */   
/* 326:    */   protected abstract void addNode(Node paramNode);
/* 327:    */   
/* 328:    */   protected abstract void addNode(int paramInt, Node paramNode);
/* 329:    */   
/* 330:    */   protected abstract boolean removeNode(Node paramNode);
/* 331:    */   
/* 332:    */   protected abstract void childAdded(Node paramNode);
/* 333:    */   
/* 334:    */   protected abstract void childRemoved(Node paramNode);
/* 335:    */   
/* 336:    */   protected void contentRemoved()
/* 337:    */   {
/* 338:431 */     List content = contentList();
/* 339:    */     
/* 340:433 */     int i = 0;
/* 341:433 */     for (int size = content.size(); i < size; i++)
/* 342:    */     {
/* 343:434 */       Object object = content.get(i);
/* 344:436 */       if ((object instanceof Node)) {
/* 345:437 */         childRemoved((Node)object);
/* 346:    */       }
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   protected void invalidNodeTypeAddException(Node node)
/* 351:    */   {
/* 352:453 */     throw new IllegalAddException("Invalid node type. Cannot add node: " + node + " to this branch: " + this);
/* 353:    */   }
/* 354:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractBranch
 * JD-Core Version:    0.7.0.1
 */