/*   1:    */ package org.apache.xalan.trace;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.TooManyListenersException;
/*   5:    */ import java.util.Vector;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   8:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   9:    */ import org.apache.xml.dtm.DTM;
/*  10:    */ import org.apache.xpath.XPath;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ import org.w3c.dom.Node;
/*  14:    */ 
/*  15:    */ public class TraceManager
/*  16:    */ {
/*  17:    */   private TransformerImpl m_transformer;
/*  18:    */   
/*  19:    */   public TraceManager(TransformerImpl transformer)
/*  20:    */   {
/*  21: 50 */     this.m_transformer = transformer;
/*  22:    */   }
/*  23:    */   
/*  24: 57 */   private Vector m_traceListeners = null;
/*  25:    */   
/*  26:    */   public void addTraceListener(TraceListener tl)
/*  27:    */     throws TooManyListenersException
/*  28:    */   {
/*  29: 69 */     this.m_transformer.setDebug(true);
/*  30: 71 */     if (null == this.m_traceListeners) {
/*  31: 72 */       this.m_traceListeners = new Vector();
/*  32:    */     }
/*  33: 74 */     this.m_traceListeners.addElement(tl);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void removeTraceListener(TraceListener tl)
/*  37:    */   {
/*  38: 84 */     if (null != this.m_traceListeners)
/*  39:    */     {
/*  40: 86 */       this.m_traceListeners.removeElement(tl);
/*  41: 91 */       if (0 == this.m_traceListeners.size()) {
/*  42: 91 */         this.m_traceListeners = null;
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void fireGenerateEvent(GenerateEvent te)
/*  48:    */   {
/*  49:103 */     if (null != this.m_traceListeners)
/*  50:    */     {
/*  51:105 */       int nListeners = this.m_traceListeners.size();
/*  52:107 */       for (int i = 0; i < nListeners; i++)
/*  53:    */       {
/*  54:109 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/*  55:    */         
/*  56:111 */         tl.generated(te);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean hasTraceListeners()
/*  62:    */   {
/*  63:123 */     return null != this.m_traceListeners;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void fireTraceEvent(ElemTemplateElement styleNode)
/*  67:    */   {
/*  68:134 */     if (hasTraceListeners())
/*  69:    */     {
/*  70:136 */       int sourceNode = this.m_transformer.getXPathContext().getCurrentNode();
/*  71:137 */       Node source = getDOMNodeFromDTM(sourceNode);
/*  72:    */       
/*  73:139 */       fireTraceEvent(new TracerEvent(this.m_transformer, source, this.m_transformer.getMode(), styleNode));
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void fireTraceEndEvent(ElemTemplateElement styleNode)
/*  78:    */   {
/*  79:154 */     if (hasTraceListeners())
/*  80:    */     {
/*  81:156 */       int sourceNode = this.m_transformer.getXPathContext().getCurrentNode();
/*  82:157 */       Node source = getDOMNodeFromDTM(sourceNode);
/*  83:    */       
/*  84:159 */       fireTraceEndEvent(new TracerEvent(this.m_transformer, source, this.m_transformer.getMode(), styleNode));
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void fireTraceEndEvent(TracerEvent te)
/*  89:    */   {
/*  90:173 */     if (hasTraceListeners())
/*  91:    */     {
/*  92:175 */       int nListeners = this.m_traceListeners.size();
/*  93:177 */       for (int i = 0; i < nListeners; i++)
/*  94:    */       {
/*  95:179 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/*  96:180 */         if ((tl instanceof TraceListenerEx2)) {
/*  97:182 */           ((TraceListenerEx2)tl).traceEnd(te);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void fireTraceEvent(TracerEvent te)
/* 104:    */   {
/* 105:198 */     if (hasTraceListeners())
/* 106:    */     {
/* 107:200 */       int nListeners = this.m_traceListeners.size();
/* 108:202 */       for (int i = 0; i < nListeners; i++)
/* 109:    */       {
/* 110:204 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 111:    */         
/* 112:206 */         tl.trace(te);
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void fireSelectedEvent(int sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection)
/* 118:    */     throws TransformerException
/* 119:    */   {
/* 120:228 */     if (hasTraceListeners())
/* 121:    */     {
/* 122:230 */       Node source = getDOMNodeFromDTM(sourceNode);
/* 123:    */       
/* 124:232 */       fireSelectedEvent(new SelectionEvent(this.m_transformer, source, styleNode, attributeName, xpath, selection));
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void fireSelectedEndEvent(int sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection)
/* 129:    */     throws TransformerException
/* 130:    */   {
/* 131:254 */     if (hasTraceListeners())
/* 132:    */     {
/* 133:256 */       Node source = getDOMNodeFromDTM(sourceNode);
/* 134:    */       
/* 135:258 */       fireSelectedEndEvent(new EndSelectionEvent(this.m_transformer, source, styleNode, attributeName, xpath, selection));
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void fireSelectedEndEvent(EndSelectionEvent se)
/* 140:    */     throws TransformerException
/* 141:    */   {
/* 142:274 */     if (hasTraceListeners())
/* 143:    */     {
/* 144:276 */       int nListeners = this.m_traceListeners.size();
/* 145:278 */       for (int i = 0; i < nListeners; i++)
/* 146:    */       {
/* 147:280 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 148:282 */         if ((tl instanceof TraceListenerEx)) {
/* 149:283 */           ((TraceListenerEx)tl).selectEnd(se);
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void fireSelectedEvent(SelectionEvent se)
/* 156:    */     throws TransformerException
/* 157:    */   {
/* 158:299 */     if (hasTraceListeners())
/* 159:    */     {
/* 160:301 */       int nListeners = this.m_traceListeners.size();
/* 161:303 */       for (int i = 0; i < nListeners; i++)
/* 162:    */       {
/* 163:305 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 164:    */         
/* 165:307 */         tl.selected(se);
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void fireExtensionEndEvent(Method method, Object instance, Object[] arguments)
/* 171:    */   {
/* 172:324 */     ExtensionEvent ee = new ExtensionEvent(this.m_transformer, method, instance, arguments);
/* 173:326 */     if (hasTraceListeners())
/* 174:    */     {
/* 175:328 */       int nListeners = this.m_traceListeners.size();
/* 176:330 */       for (int i = 0; i < nListeners; i++)
/* 177:    */       {
/* 178:332 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 179:333 */         if ((tl instanceof TraceListenerEx3)) {
/* 180:335 */           ((TraceListenerEx3)tl).extensionEnd(ee);
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void fireExtensionEvent(Method method, Object instance, Object[] arguments)
/* 187:    */   {
/* 188:352 */     ExtensionEvent ee = new ExtensionEvent(this.m_transformer, method, instance, arguments);
/* 189:354 */     if (hasTraceListeners())
/* 190:    */     {
/* 191:356 */       int nListeners = this.m_traceListeners.size();
/* 192:358 */       for (int i = 0; i < nListeners; i++)
/* 193:    */       {
/* 194:360 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 195:361 */         if ((tl instanceof TraceListenerEx3)) {
/* 196:363 */           ((TraceListenerEx3)tl).extension(ee);
/* 197:    */         }
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void fireExtensionEndEvent(ExtensionEvent ee)
/* 203:    */   {
/* 204:378 */     if (hasTraceListeners())
/* 205:    */     {
/* 206:380 */       int nListeners = this.m_traceListeners.size();
/* 207:382 */       for (int i = 0; i < nListeners; i++)
/* 208:    */       {
/* 209:384 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 210:385 */         if ((tl instanceof TraceListenerEx3)) {
/* 211:387 */           ((TraceListenerEx3)tl).extensionEnd(ee);
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void fireExtensionEvent(ExtensionEvent ee)
/* 218:    */   {
/* 219:403 */     if (hasTraceListeners())
/* 220:    */     {
/* 221:405 */       int nListeners = this.m_traceListeners.size();
/* 222:407 */       for (int i = 0; i < nListeners; i++)
/* 223:    */       {
/* 224:409 */         TraceListener tl = (TraceListener)this.m_traceListeners.elementAt(i);
/* 225:410 */         if ((tl instanceof TraceListenerEx3)) {
/* 226:412 */           ((TraceListenerEx3)tl).extension(ee);
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   private Node getDOMNodeFromDTM(int sourceNode)
/* 233:    */   {
/* 234:423 */     DTM dtm = this.m_transformer.getXPathContext().getDTM(sourceNode);
/* 235:424 */     Node source = dtm == null ? null : dtm.getNode(sourceNode);
/* 236:425 */     return source;
/* 237:    */   }
/* 238:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.TraceManager
 * JD-Core Version:    0.7.0.1
 */