/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xalan.serialize.SerializerUtils;
/*   8:    */ import org.apache.xalan.templates.Stylesheet;
/*   9:    */ import org.apache.xalan.transformer.ClonerToResultTree;
/*  10:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  11:    */ import org.apache.xml.dtm.DTM;
/*  12:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*  13:    */ import org.apache.xml.dtm.DTMIterator;
/*  14:    */ import org.apache.xml.serializer.SerializationHandler;
/*  15:    */ import org.apache.xml.utils.QName;
/*  16:    */ import org.apache.xpath.NodeSetDTM;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.axes.DescendantIterator;
/*  19:    */ import org.apache.xpath.axes.OneStepIterator;
/*  20:    */ import org.apache.xpath.objects.XBoolean;
/*  21:    */ import org.apache.xpath.objects.XNodeSet;
/*  22:    */ import org.apache.xpath.objects.XNumber;
/*  23:    */ import org.apache.xpath.objects.XObject;
/*  24:    */ import org.apache.xpath.objects.XRTreeFrag;
/*  25:    */ import org.apache.xpath.objects.XString;
/*  26:    */ import org.w3c.dom.DocumentFragment;
/*  27:    */ import org.w3c.dom.Node;
/*  28:    */ import org.w3c.dom.traversal.NodeIterator;
/*  29:    */ import org.xml.sax.ContentHandler;
/*  30:    */ import org.xml.sax.SAXException;
/*  31:    */ 
/*  32:    */ public class XSLProcessorContext
/*  33:    */ {
/*  34:    */   private TransformerImpl transformer;
/*  35:    */   private Stylesheet stylesheetTree;
/*  36:    */   private DTM sourceTree;
/*  37:    */   private int sourceNode;
/*  38:    */   private QName mode;
/*  39:    */   
/*  40:    */   public XSLProcessorContext(TransformerImpl transformer, Stylesheet stylesheetTree)
/*  41:    */   {
/*  42: 68 */     this.transformer = transformer;
/*  43: 69 */     this.stylesheetTree = stylesheetTree;
/*  44:    */     
/*  45: 71 */     XPathContext xctxt = transformer.getXPathContext();
/*  46: 72 */     this.mode = transformer.getMode();
/*  47: 73 */     this.sourceNode = xctxt.getCurrentNode();
/*  48: 74 */     this.sourceTree = xctxt.getDTM(this.sourceNode);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TransformerImpl getTransformer()
/*  52:    */   {
/*  53: 87 */     return this.transformer;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Stylesheet getStylesheet()
/*  57:    */   {
/*  58:100 */     return this.stylesheetTree;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Node getSourceTree()
/*  62:    */   {
/*  63:113 */     return this.sourceTree.getNode(this.sourceTree.getDocumentRoot(this.sourceNode));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Node getContextNode()
/*  67:    */   {
/*  68:126 */     return this.sourceTree.getNode(this.sourceNode);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public QName getMode()
/*  72:    */   {
/*  73:139 */     return this.mode;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void outputToResultTree(Stylesheet stylesheetTree, Object obj)
/*  77:    */     throws TransformerException, MalformedURLException, FileNotFoundException, IOException
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:163 */       SerializationHandler rtreeHandler = this.transformer.getResultTreeHandler();
/*  82:164 */       XPathContext xctxt = this.transformer.getXPathContext();
/*  83:    */       XObject value;
/*  84:171 */       if ((obj instanceof XObject))
/*  85:    */       {
/*  86:173 */         value = (XObject)obj;
/*  87:    */       }
/*  88:175 */       else if ((obj instanceof String))
/*  89:    */       {
/*  90:177 */         value = new XString((String)obj);
/*  91:    */       }
/*  92:179 */       else if ((obj instanceof Boolean))
/*  93:    */       {
/*  94:181 */         value = new XBoolean(((Boolean)obj).booleanValue());
/*  95:    */       }
/*  96:183 */       else if ((obj instanceof Double))
/*  97:    */       {
/*  98:185 */         value = new XNumber(((Double)obj).doubleValue());
/*  99:    */       }
/* 100:187 */       else if ((obj instanceof DocumentFragment))
/* 101:    */       {
/* 102:189 */         int handle = xctxt.getDTMHandleFromNode((DocumentFragment)obj);
/* 103:    */         
/* 104:191 */         value = new XRTreeFrag(handle, xctxt);
/* 105:    */       }
/* 106:193 */       else if ((obj instanceof DTM))
/* 107:    */       {
/* 108:195 */         DTM dtm = (DTM)obj;
/* 109:196 */         DTMIterator iterator = new DescendantIterator();
/* 110:    */         
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:202 */         iterator.setRoot(dtm.getDocument(), xctxt);
/* 116:203 */         value = new XNodeSet(iterator);
/* 117:    */       }
/* 118:205 */       else if ((obj instanceof DTMAxisIterator))
/* 119:    */       {
/* 120:207 */         DTMAxisIterator iter = (DTMAxisIterator)obj;
/* 121:208 */         DTMIterator iterator = new OneStepIterator(iter, -1);
/* 122:209 */         value = new XNodeSet(iterator);
/* 123:    */       }
/* 124:211 */       else if ((obj instanceof DTMIterator))
/* 125:    */       {
/* 126:213 */         value = new XNodeSet((DTMIterator)obj);
/* 127:    */       }
/* 128:215 */       else if ((obj instanceof NodeIterator))
/* 129:    */       {
/* 130:217 */         value = new XNodeSet(new NodeSetDTM((NodeIterator)obj, xctxt));
/* 131:    */       }
/* 132:219 */       else if ((obj instanceof Node))
/* 133:    */       {
/* 134:221 */         value = new XNodeSet(xctxt.getDTMHandleFromNode((Node)obj), xctxt.getDTMManager());
/* 135:    */       }
/* 136:    */       else
/* 137:    */       {
/* 138:227 */         value = new XString(obj.toString());
/* 139:    */       }
/* 140:230 */       int type = value.getType();
/* 141:233 */       switch (type)
/* 142:    */       {
/* 143:    */       case 1: 
/* 144:    */       case 2: 
/* 145:    */       case 3: 
/* 146:238 */         String s = value.str();
/* 147:    */         
/* 148:240 */         rtreeHandler.characters(s.toCharArray(), 0, s.length());
/* 149:241 */         break;
/* 150:    */       case 4: 
/* 151:244 */         DTMIterator nl = value.iter();
/* 152:    */         int pos;
/* 153:248 */         for (; -1 != (pos = nl.nextNode()); -1 != pos)
/* 154:    */         {
/* 155:    */           int i;
/* 156:250 */           DTM dtm = nl.getDTM(i);
/* 157:251 */           int top = i;
/* 158:    */           
/* 159:253 */           continue;
/* 160:    */           
/* 161:255 */           rtreeHandler.flushPending();
/* 162:256 */           ClonerToResultTree.cloneToResultTree(i, dtm.getNodeType(i), dtm, rtreeHandler, true);
/* 163:    */           
/* 164:    */ 
/* 165:259 */           int nextNode = dtm.getFirstChild(i);
/* 166:261 */           while (-1 == nextNode)
/* 167:    */           {
/* 168:263 */             if (1 == dtm.getNodeType(i)) {
/* 169:265 */               rtreeHandler.endElement("", "", dtm.getNodeName(i));
/* 170:    */             }
/* 171:268 */             if (top == i) {
/* 172:    */               break;
/* 173:    */             }
/* 174:271 */             nextNode = dtm.getNextSibling(i);
/* 175:273 */             if (-1 == nextNode)
/* 176:    */             {
/* 177:275 */               pos = dtm.getParent(i);
/* 178:277 */               if (top == pos)
/* 179:    */               {
/* 180:279 */                 if (1 == dtm.getNodeType(pos)) {
/* 181:281 */                   rtreeHandler.endElement("", "", dtm.getNodeName(pos));
/* 182:    */                 }
/* 183:284 */                 nextNode = -1;
/* 184:    */                 
/* 185:286 */                 break;
/* 186:    */               }
/* 187:    */             }
/* 188:    */           }
/* 189:291 */           pos = nextNode;
/* 190:    */         }
/* 191:294 */         break;
/* 192:    */       case 5: 
/* 193:296 */         SerializerUtils.outputResultTreeFragment(rtreeHandler, value, this.transformer.getXPathContext());
/* 194:    */       }
/* 195:    */     }
/* 196:    */     catch (SAXException se)
/* 197:    */     {
/* 198:305 */       throw new TransformerException(se);
/* 199:    */     }
/* 200:    */   }
/* 201:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.XSLProcessorContext
 * JD-Core Version:    0.7.0.1
 */