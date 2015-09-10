/*   1:    */ package org.apache.xalan.trace;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javax.xml.transform.SourceLocator;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xalan.templates.ElemTemplate;
/*   9:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  10:    */ import org.apache.xalan.templates.ElemTextLiteral;
/*  11:    */ import org.apache.xalan.templates.StylesheetRoot;
/*  12:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  13:    */ import org.apache.xml.dtm.DTM;
/*  14:    */ import org.apache.xml.dtm.DTMIterator;
/*  15:    */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*  16:    */ import org.apache.xpath.XPath;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.objects.XObject;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ 
/*  21:    */ public class PrintTraceListener
/*  22:    */   implements TraceListenerEx3
/*  23:    */ {
/*  24:    */   PrintWriter m_pw;
/*  25:    */   
/*  26:    */   public PrintTraceListener(PrintWriter pw)
/*  27:    */   {
/*  28: 55 */     this.m_pw = pw;
/*  29:    */   }
/*  30:    */   
/*  31: 66 */   public boolean m_traceTemplates = false;
/*  32: 71 */   public boolean m_traceElements = false;
/*  33: 76 */   public boolean m_traceGeneration = false;
/*  34: 81 */   public boolean m_traceSelection = false;
/*  35: 86 */   public boolean m_traceExtension = false;
/*  36:    */   
/*  37:    */   public void _trace(TracerEvent ev)
/*  38:    */   {
/*  39: 96 */     switch (ev.m_styleNode.getXSLToken())
/*  40:    */     {
/*  41:    */     case 78: 
/*  42: 99 */       if (this.m_traceElements)
/*  43:    */       {
/*  44:101 */         this.m_pw.print(ev.m_styleNode.getSystemId() + " Line #" + ev.m_styleNode.getLineNumber() + ", " + "Column #" + ev.m_styleNode.getColumnNumber() + " -- " + ev.m_styleNode.getNodeName() + ": ");
/*  45:    */         
/*  46:    */ 
/*  47:    */ 
/*  48:105 */         ElemTextLiteral etl = (ElemTextLiteral)ev.m_styleNode;
/*  49:106 */         String chars = new String(etl.getChars(), 0, etl.getChars().length);
/*  50:    */         
/*  51:108 */         this.m_pw.println("    " + chars.trim());
/*  52:    */       }
/*  53:    */       break;
/*  54:    */     case 19: 
/*  55:112 */       if ((this.m_traceTemplates) || (this.m_traceElements))
/*  56:    */       {
/*  57:114 */         ElemTemplate et = (ElemTemplate)ev.m_styleNode;
/*  58:    */         
/*  59:116 */         this.m_pw.print(et.getSystemId() + " Line #" + et.getLineNumber() + ", " + "Column #" + et.getColumnNumber() + ": " + et.getNodeName() + " ");
/*  60:119 */         if (null != et.getMatch()) {
/*  61:121 */           this.m_pw.print("match='" + et.getMatch().getPatternString() + "' ");
/*  62:    */         }
/*  63:124 */         if (null != et.getName()) {
/*  64:126 */           this.m_pw.print("name='" + et.getName() + "' ");
/*  65:    */         }
/*  66:129 */         this.m_pw.println();
/*  67:    */       }
/*  68:    */       break;
/*  69:    */     default: 
/*  70:133 */       if (this.m_traceElements) {
/*  71:135 */         this.m_pw.println(ev.m_styleNode.getSystemId() + " Line #" + ev.m_styleNode.getLineNumber() + ", " + "Column #" + ev.m_styleNode.getColumnNumber() + ": " + ev.m_styleNode.getNodeName());
/*  72:    */       }
/*  73:    */       break;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:142 */   int m_indent = 0;
/*  78:    */   
/*  79:    */   public void trace(TracerEvent ev)
/*  80:    */   {
/*  81:158 */     _trace(ev);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void traceEnd(TracerEvent ev) {}
/*  85:    */   
/*  86:    */   public void selected(SelectionEvent ev)
/*  87:    */     throws TransformerException
/*  88:    */   {
/*  89:190 */     if (this.m_traceSelection)
/*  90:    */     {
/*  91:191 */       ElemTemplateElement ete = ev.m_styleNode;
/*  92:192 */       Node sourceNode = ev.m_sourceNode;
/*  93:    */       
/*  94:194 */       SourceLocator locator = null;
/*  95:195 */       if ((sourceNode instanceof DTMNodeProxy))
/*  96:    */       {
/*  97:196 */         int nodeHandler = ((DTMNodeProxy)sourceNode).getDTMNodeNumber();
/*  98:197 */         locator = ((DTMNodeProxy)sourceNode).getDTM().getSourceLocatorFor(nodeHandler);
/*  99:    */       }
/* 100:202 */       if (locator != null) {
/* 101:203 */         this.m_pw.println("Selected source node '" + sourceNode.getNodeName() + "', at " + locator);
/* 102:    */       } else {
/* 103:209 */         this.m_pw.println("Selected source node '" + sourceNode.getNodeName() + "'");
/* 104:    */       }
/* 105:212 */       if (ev.m_styleNode.getLineNumber() == 0)
/* 106:    */       {
/* 107:216 */         ElemTemplateElement parent = ete.getParentElem();
/* 108:219 */         if (parent == ete.getStylesheetRoot().getDefaultRootRule()) {
/* 109:220 */           this.m_pw.print("(default root rule) ");
/* 110:221 */         } else if (parent == ete.getStylesheetRoot().getDefaultTextRule()) {
/* 111:223 */           this.m_pw.print("(default text rule) ");
/* 112:224 */         } else if (parent == ete.getStylesheetRoot().getDefaultRule()) {
/* 113:225 */           this.m_pw.print("(default rule) ");
/* 114:    */         }
/* 115:228 */         this.m_pw.print(ete.getNodeName() + ", " + ev.m_attributeName + "='" + ev.m_xpath.getPatternString() + "': ");
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:236 */         this.m_pw.print(ev.m_styleNode.getSystemId() + " Line #" + ev.m_styleNode.getLineNumber() + ", " + "Column #" + ev.m_styleNode.getColumnNumber() + ": " + ete.getNodeName() + ", " + ev.m_attributeName + "='" + ev.m_xpath.getPatternString() + "': ");
/* 120:    */       }
/* 121:252 */       if (ev.m_selection.getType() == 4)
/* 122:    */       {
/* 123:253 */         this.m_pw.println();
/* 124:    */         
/* 125:255 */         DTMIterator nl = ev.m_selection.iter();
/* 126:    */         
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:261 */         int currentPos = -1;
/* 132:262 */         currentPos = nl.getCurrentPos();
/* 133:263 */         nl.setShouldCacheNodes(true);
/* 134:264 */         DTMIterator clone = null;
/* 135:    */         try
/* 136:    */         {
/* 137:268 */           clone = nl.cloneWithReset();
/* 138:    */         }
/* 139:    */         catch (CloneNotSupportedException cnse)
/* 140:    */         {
/* 141:270 */           this.m_pw.println("     [Can't trace nodelist because it it threw a CloneNotSupportedException]");
/* 142:    */           
/* 143:272 */           return;
/* 144:    */         }
/* 145:274 */         int pos = clone.nextNode();
/* 146:276 */         if (-1 == pos) {
/* 147:277 */           this.m_pw.println("     [empty node list]");
/* 148:    */         } else {
/* 149:279 */           while (-1 != pos)
/* 150:    */           {
/* 151:281 */             DTM dtm = ev.m_processor.getXPathContext().getDTM(pos);
/* 152:282 */             this.m_pw.print("     ");
/* 153:283 */             this.m_pw.print(Integer.toHexString(pos));
/* 154:284 */             this.m_pw.print(": ");
/* 155:285 */             this.m_pw.println(dtm.getNodeName(pos));
/* 156:286 */             pos = clone.nextNode();
/* 157:    */           }
/* 158:    */         }
/* 159:291 */         nl.runTo(-1);
/* 160:292 */         nl.setCurrentPos(currentPos);
/* 161:    */       }
/* 162:    */       else
/* 163:    */       {
/* 164:296 */         this.m_pw.println(ev.m_selection.str());
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void selectEnd(EndSelectionEvent ev)
/* 170:    */     throws TransformerException
/* 171:    */   {}
/* 172:    */   
/* 173:    */   public void generated(GenerateEvent ev)
/* 174:    */   {
/* 175:323 */     if (this.m_traceGeneration) {
/* 176:325 */       switch (ev.m_eventtype)
/* 177:    */       {
/* 178:    */       case 1: 
/* 179:328 */         this.m_pw.println("STARTDOCUMENT");
/* 180:329 */         break;
/* 181:    */       case 2: 
/* 182:331 */         this.m_pw.println("ENDDOCUMENT");
/* 183:332 */         break;
/* 184:    */       case 3: 
/* 185:334 */         this.m_pw.println("STARTELEMENT: " + ev.m_name);
/* 186:335 */         break;
/* 187:    */       case 4: 
/* 188:337 */         this.m_pw.println("ENDELEMENT: " + ev.m_name);
/* 189:338 */         break;
/* 190:    */       case 5: 
/* 191:341 */         String chars = new String(ev.m_characters, ev.m_start, ev.m_length);
/* 192:    */         
/* 193:343 */         this.m_pw.println("CHARACTERS: " + chars);
/* 194:    */         
/* 195:345 */         break;
/* 196:    */       case 10: 
/* 197:348 */         String chars = new String(ev.m_characters, ev.m_start, ev.m_length);
/* 198:    */         
/* 199:350 */         this.m_pw.println("CDATA: " + chars);
/* 200:    */         
/* 201:352 */         break;
/* 202:    */       case 8: 
/* 203:354 */         this.m_pw.println("COMMENT: " + ev.m_data);
/* 204:355 */         break;
/* 205:    */       case 7: 
/* 206:357 */         this.m_pw.println("PI: " + ev.m_name + ", " + ev.m_data);
/* 207:358 */         break;
/* 208:    */       case 9: 
/* 209:360 */         this.m_pw.println("ENTITYREF: " + ev.m_name);
/* 210:361 */         break;
/* 211:    */       case 6: 
/* 212:363 */         this.m_pw.println("IGNORABLEWHITESPACE");
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void extension(ExtensionEvent ev)
/* 218:    */   {
/* 219:375 */     if (this.m_traceExtension) {
/* 220:376 */       switch (ev.m_callType)
/* 221:    */       {
/* 222:    */       case 0: 
/* 223:378 */         this.m_pw.println("EXTENSION: " + ((Class)ev.m_method).getName() + "#<init>");
/* 224:379 */         break;
/* 225:    */       case 1: 
/* 226:381 */         this.m_pw.println("EXTENSION: " + ((Method)ev.m_method).getDeclaringClass().getName() + "#" + ((Method)ev.m_method).getName());
/* 227:382 */         break;
/* 228:    */       case 2: 
/* 229:384 */         this.m_pw.println("EXTENSION: " + ((Constructor)ev.m_method).getDeclaringClass().getName() + "#<init>");
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void extensionEnd(ExtensionEvent ev) {}
/* 235:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.PrintTraceListener
 * JD-Core Version:    0.7.0.1
 */