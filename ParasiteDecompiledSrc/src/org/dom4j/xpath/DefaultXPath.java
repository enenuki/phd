/*   1:    */ package org.dom4j.xpath;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.dom4j.InvalidXPathException;
/*  12:    */ import org.dom4j.Node;
/*  13:    */ import org.dom4j.NodeFilter;
/*  14:    */ import org.dom4j.XPathException;
/*  15:    */ import org.jaxen.FunctionContext;
/*  16:    */ import org.jaxen.JaxenException;
/*  17:    */ import org.jaxen.NamespaceContext;
/*  18:    */ import org.jaxen.SimpleNamespaceContext;
/*  19:    */ import org.jaxen.VariableContext;
/*  20:    */ import org.jaxen.dom4j.Dom4jXPath;
/*  21:    */ 
/*  22:    */ public class DefaultXPath
/*  23:    */   implements org.dom4j.XPath, NodeFilter, Serializable
/*  24:    */ {
/*  25:    */   private String text;
/*  26:    */   private org.jaxen.XPath xpath;
/*  27:    */   private NamespaceContext namespaceContext;
/*  28:    */   
/*  29:    */   public DefaultXPath(String text)
/*  30:    */     throws InvalidXPathException
/*  31:    */   {
/*  32: 58 */     this.text = text;
/*  33: 59 */     this.xpath = parse(text);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String toString()
/*  37:    */   {
/*  38: 63 */     return "[XPath: " + this.xpath + "]";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getText()
/*  42:    */   {
/*  43: 74 */     return this.text;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public FunctionContext getFunctionContext()
/*  47:    */   {
/*  48: 78 */     return this.xpath.getFunctionContext();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setFunctionContext(FunctionContext functionContext)
/*  52:    */   {
/*  53: 82 */     this.xpath.setFunctionContext(functionContext);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public NamespaceContext getNamespaceContext()
/*  57:    */   {
/*  58: 86 */     return this.namespaceContext;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setNamespaceURIs(Map map)
/*  62:    */   {
/*  63: 90 */     setNamespaceContext(new SimpleNamespaceContext(map));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setNamespaceContext(NamespaceContext namespaceContext)
/*  67:    */   {
/*  68: 94 */     this.namespaceContext = namespaceContext;
/*  69: 95 */     this.xpath.setNamespaceContext(namespaceContext);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public VariableContext getVariableContext()
/*  73:    */   {
/*  74: 99 */     return this.xpath.getVariableContext();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setVariableContext(VariableContext variableContext)
/*  78:    */   {
/*  79:103 */     this.xpath.setVariableContext(variableContext);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object evaluate(Object context)
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:108 */       setNSContext(context);
/*  87:    */       
/*  88:110 */       List answer = this.xpath.selectNodes(context);
/*  89:112 */       if ((answer != null) && (answer.size() == 1)) {
/*  90:113 */         return answer.get(0);
/*  91:    */       }
/*  92:116 */       return answer;
/*  93:    */     }
/*  94:    */     catch (JaxenException e)
/*  95:    */     {
/*  96:118 */       handleJaxenException(e);
/*  97:    */     }
/*  98:120 */     return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Object selectObject(Object context)
/* 102:    */   {
/* 103:125 */     return evaluate(context);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List selectNodes(Object context)
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:130 */       setNSContext(context);
/* 111:    */       
/* 112:132 */       return this.xpath.selectNodes(context);
/* 113:    */     }
/* 114:    */     catch (JaxenException e)
/* 115:    */     {
/* 116:134 */       handleJaxenException(e);
/* 117:    */     }
/* 118:136 */     return Collections.EMPTY_LIST;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List selectNodes(Object context, org.dom4j.XPath sortXPath)
/* 122:    */   {
/* 123:141 */     List answer = selectNodes(context);
/* 124:142 */     sortXPath.sort(answer);
/* 125:    */     
/* 126:144 */     return answer;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List selectNodes(Object context, org.dom4j.XPath sortXPath, boolean distinct)
/* 130:    */   {
/* 131:149 */     List answer = selectNodes(context);
/* 132:150 */     sortXPath.sort(answer, distinct);
/* 133:    */     
/* 134:152 */     return answer;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Node selectSingleNode(Object context)
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:157 */       setNSContext(context);
/* 142:    */       
/* 143:159 */       Object answer = this.xpath.selectSingleNode(context);
/* 144:161 */       if ((answer instanceof Node)) {
/* 145:162 */         return (Node)answer;
/* 146:    */       }
/* 147:165 */       if (answer == null) {
/* 148:166 */         return null;
/* 149:    */       }
/* 150:169 */       throw new XPathException("The result of the XPath expression is not a Node. It was: " + answer + " of type: " + answer.getClass().getName());
/* 151:    */     }
/* 152:    */     catch (JaxenException e)
/* 153:    */     {
/* 154:173 */       handleJaxenException(e);
/* 155:    */     }
/* 156:175 */     return null;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String valueOf(Object context)
/* 160:    */   {
/* 161:    */     try
/* 162:    */     {
/* 163:181 */       setNSContext(context);
/* 164:    */       
/* 165:183 */       return this.xpath.stringValueOf(context);
/* 166:    */     }
/* 167:    */     catch (JaxenException e)
/* 168:    */     {
/* 169:185 */       handleJaxenException(e);
/* 170:    */     }
/* 171:187 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Number numberValueOf(Object context)
/* 175:    */   {
/* 176:    */     try
/* 177:    */     {
/* 178:193 */       setNSContext(context);
/* 179:    */       
/* 180:195 */       return this.xpath.numberValueOf(context);
/* 181:    */     }
/* 182:    */     catch (JaxenException e)
/* 183:    */     {
/* 184:197 */       handleJaxenException(e);
/* 185:    */     }
/* 186:199 */     return null;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean booleanValueOf(Object context)
/* 190:    */   {
/* 191:    */     try
/* 192:    */     {
/* 193:205 */       setNSContext(context);
/* 194:    */       
/* 195:207 */       return this.xpath.booleanValueOf(context);
/* 196:    */     }
/* 197:    */     catch (JaxenException e)
/* 198:    */     {
/* 199:209 */       handleJaxenException(e);
/* 200:    */     }
/* 201:211 */     return false;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void sort(List list)
/* 205:    */   {
/* 206:225 */     sort(list, false);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void sort(List list, boolean distinct)
/* 210:    */   {
/* 211:241 */     if ((list != null) && (!list.isEmpty()))
/* 212:    */     {
/* 213:242 */       int size = list.size();
/* 214:243 */       HashMap sortValues = new HashMap(size);
/* 215:245 */       for (int i = 0; i < size; i++)
/* 216:    */       {
/* 217:246 */         Object object = list.get(i);
/* 218:248 */         if ((object instanceof Node))
/* 219:    */         {
/* 220:249 */           Node node = (Node)object;
/* 221:250 */           Object expression = getCompareValue(node);
/* 222:251 */           sortValues.put(node, expression);
/* 223:    */         }
/* 224:    */       }
/* 225:255 */       sort(list, sortValues);
/* 226:257 */       if (distinct) {
/* 227:258 */         removeDuplicates(list, sortValues);
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean matches(Node node)
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:265 */       setNSContext(node);
/* 237:    */       
/* 238:267 */       List answer = this.xpath.selectNodes(node);
/* 239:269 */       if ((answer != null) && (answer.size() > 0))
/* 240:    */       {
/* 241:270 */         Object item = answer.get(0);
/* 242:272 */         if ((item instanceof Boolean)) {
/* 243:273 */           return ((Boolean)item).booleanValue();
/* 244:    */         }
/* 245:276 */         return answer.contains(node);
/* 246:    */       }
/* 247:279 */       return false;
/* 248:    */     }
/* 249:    */     catch (JaxenException e)
/* 250:    */     {
/* 251:281 */       handleJaxenException(e);
/* 252:    */     }
/* 253:283 */     return false;
/* 254:    */   }
/* 255:    */   
/* 256:    */   protected void sort(List list, Map sortValues)
/* 257:    */   {
/* 258:296 */     Collections.sort(list, new Comparator()
/* 259:    */     {
/* 260:    */       private final Map val$sortValues;
/* 261:    */       
/* 262:    */       public int compare(Object o1, Object o2)
/* 263:    */       {
/* 264:298 */         o1 = this.val$sortValues.get(o1);
/* 265:299 */         o2 = this.val$sortValues.get(o2);
/* 266:301 */         if (o1 == o2) {
/* 267:302 */           return 0;
/* 268:    */         }
/* 269:303 */         if ((o1 instanceof Comparable))
/* 270:    */         {
/* 271:304 */           Comparable c1 = (Comparable)o1;
/* 272:    */           
/* 273:306 */           return c1.compareTo(o2);
/* 274:    */         }
/* 275:307 */         if (o1 == null) {
/* 276:308 */           return 1;
/* 277:    */         }
/* 278:309 */         if (o2 == null) {
/* 279:310 */           return -1;
/* 280:    */         }
/* 281:312 */         return o1.equals(o2) ? 0 : -1;
/* 282:    */       }
/* 283:    */     });
/* 284:    */   }
/* 285:    */   
/* 286:    */   protected void removeDuplicates(List list, Map sortValues)
/* 287:    */   {
/* 288:330 */     HashSet distinctValues = new HashSet();
/* 289:332 */     for (Iterator iter = list.iterator(); iter.hasNext();)
/* 290:    */     {
/* 291:333 */       Object node = iter.next();
/* 292:334 */       Object value = sortValues.get(node);
/* 293:336 */       if (distinctValues.contains(value)) {
/* 294:337 */         iter.remove();
/* 295:    */       } else {
/* 296:339 */         distinctValues.add(value);
/* 297:    */       }
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   protected Object getCompareValue(Node node)
/* 302:    */   {
/* 303:353 */     return valueOf(node);
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected static org.jaxen.XPath parse(String text)
/* 307:    */   {
/* 308:    */     try
/* 309:    */     {
/* 310:358 */       return new Dom4jXPath(text);
/* 311:    */     }
/* 312:    */     catch (JaxenException e)
/* 313:    */     {
/* 314:360 */       throw new InvalidXPathException(text, e.getMessage());
/* 315:    */     }
/* 316:    */     catch (Throwable t)
/* 317:    */     {
/* 318:362 */       throw new InvalidXPathException(text, t);
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   protected void setNSContext(Object context)
/* 323:    */   {
/* 324:367 */     if (this.namespaceContext == null) {
/* 325:368 */       this.xpath.setNamespaceContext(DefaultNamespaceContext.create(context));
/* 326:    */     }
/* 327:    */   }
/* 328:    */   
/* 329:    */   protected void handleJaxenException(JaxenException exception)
/* 330:    */     throws XPathException
/* 331:    */   {
/* 332:374 */     throw new XPathException(this.text, exception);
/* 333:    */   }
/* 334:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.xpath.DefaultXPath
 * JD-Core Version:    0.7.0.1
 */