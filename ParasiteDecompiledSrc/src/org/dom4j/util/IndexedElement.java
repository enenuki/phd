/*   1:    */ package org.dom4j.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.dom4j.Attribute;
/*   9:    */ import org.dom4j.Element;
/*  10:    */ import org.dom4j.Node;
/*  11:    */ import org.dom4j.QName;
/*  12:    */ import org.dom4j.tree.BackedList;
/*  13:    */ import org.dom4j.tree.DefaultElement;
/*  14:    */ 
/*  15:    */ public class IndexedElement
/*  16:    */   extends DefaultElement
/*  17:    */ {
/*  18:    */   private Map elementIndex;
/*  19:    */   private Map attributeIndex;
/*  20:    */   
/*  21:    */   public IndexedElement(String name)
/*  22:    */   {
/*  23: 41 */     super(name);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public IndexedElement(QName qname)
/*  27:    */   {
/*  28: 45 */     super(qname);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IndexedElement(QName qname, int attributeCount)
/*  32:    */   {
/*  33: 49 */     super(qname, attributeCount);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Attribute attribute(String name)
/*  37:    */   {
/*  38: 53 */     return (Attribute)attributeIndex().get(name);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Attribute attribute(QName qName)
/*  42:    */   {
/*  43: 57 */     return (Attribute)attributeIndex().get(qName);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Element element(String name)
/*  47:    */   {
/*  48: 61 */     return asElement(elementIndex().get(name));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Element element(QName qName)
/*  52:    */   {
/*  53: 65 */     return asElement(elementIndex().get(qName));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List elements(String name)
/*  57:    */   {
/*  58: 69 */     return asElementList(elementIndex().get(name));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List elements(QName qName)
/*  62:    */   {
/*  63: 73 */     return asElementList(elementIndex().get(qName));
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected Element asElement(Object object)
/*  67:    */   {
/*  68: 79 */     if ((object instanceof Element)) {
/*  69: 80 */       return (Element)object;
/*  70:    */     }
/*  71: 81 */     if (object != null)
/*  72:    */     {
/*  73: 82 */       List list = (List)object;
/*  74: 84 */       if (list.size() >= 1) {
/*  75: 85 */         return (Element)list.get(0);
/*  76:    */       }
/*  77:    */     }
/*  78: 89 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected List asElementList(Object object)
/*  82:    */   {
/*  83: 93 */     if ((object instanceof Element)) {
/*  84: 94 */       return createSingleResultList(object);
/*  85:    */     }
/*  86: 95 */     if (object != null)
/*  87:    */     {
/*  88: 96 */       List list = (List)object;
/*  89: 97 */       BackedList answer = createResultList();
/*  90:    */       
/*  91: 99 */       int i = 0;
/*  92: 99 */       for (int size = list.size(); i < size; i++) {
/*  93:100 */         answer.addLocal(list.get(i));
/*  94:    */       }
/*  95:103 */       return answer;
/*  96:    */     }
/*  97:106 */     return createEmptyList();
/*  98:    */   }
/*  99:    */   
/* 100:    */   /**
/* 101:    */    * @deprecated
/* 102:    */    */
/* 103:    */   protected Iterator asElementIterator(Object object)
/* 104:    */   {
/* 105:120 */     return asElementList(object).iterator();
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void addNode(Node node)
/* 109:    */   {
/* 110:125 */     super.addNode(node);
/* 111:127 */     if ((this.elementIndex != null) && ((node instanceof Element))) {
/* 112:128 */       addToElementIndex((Element)node);
/* 113:129 */     } else if ((this.attributeIndex != null) && ((node instanceof Attribute))) {
/* 114:130 */       addToAttributeIndex((Attribute)node);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected boolean removeNode(Node node)
/* 119:    */   {
/* 120:135 */     if (super.removeNode(node))
/* 121:    */     {
/* 122:136 */       if ((this.elementIndex != null) && ((node instanceof Element))) {
/* 123:137 */         removeFromElementIndex((Element)node);
/* 124:138 */       } else if ((this.attributeIndex != null) && ((node instanceof Attribute))) {
/* 125:139 */         removeFromAttributeIndex((Attribute)node);
/* 126:    */       }
/* 127:142 */       return true;
/* 128:    */     }
/* 129:145 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Map attributeIndex()
/* 133:    */   {
/* 134:    */     Iterator iter;
/* 135:149 */     if (this.attributeIndex == null)
/* 136:    */     {
/* 137:150 */       this.attributeIndex = createAttributeIndex();
/* 138:152 */       for (iter = attributeIterator(); iter.hasNext();) {
/* 139:153 */         addToAttributeIndex((Attribute)iter.next());
/* 140:    */       }
/* 141:    */     }
/* 142:157 */     return this.attributeIndex;
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected Map elementIndex()
/* 146:    */   {
/* 147:    */     Iterator iter;
/* 148:161 */     if (this.elementIndex == null)
/* 149:    */     {
/* 150:162 */       this.elementIndex = createElementIndex();
/* 151:164 */       for (iter = elementIterator(); iter.hasNext();) {
/* 152:165 */         addToElementIndex((Element)iter.next());
/* 153:    */       }
/* 154:    */     }
/* 155:169 */     return this.elementIndex;
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Map createAttributeIndex()
/* 159:    */   {
/* 160:178 */     Map answer = createIndex();
/* 161:    */     
/* 162:180 */     return answer;
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected Map createElementIndex()
/* 166:    */   {
/* 167:189 */     Map answer = createIndex();
/* 168:    */     
/* 169:191 */     return answer;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void addToElementIndex(Element element)
/* 173:    */   {
/* 174:195 */     QName qName = element.getQName();
/* 175:196 */     String name = qName.getName();
/* 176:197 */     addToElementIndex(qName, element);
/* 177:198 */     addToElementIndex(name, element);
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected void addToElementIndex(Object key, Element value)
/* 181:    */   {
/* 182:202 */     Object oldValue = this.elementIndex.get(key);
/* 183:204 */     if (oldValue == null)
/* 184:    */     {
/* 185:205 */       this.elementIndex.put(key, value);
/* 186:    */     }
/* 187:207 */     else if ((oldValue instanceof List))
/* 188:    */     {
/* 189:208 */       List list = (List)oldValue;
/* 190:209 */       list.add(value);
/* 191:    */     }
/* 192:    */     else
/* 193:    */     {
/* 194:211 */       List list = createList();
/* 195:212 */       list.add(oldValue);
/* 196:213 */       list.add(value);
/* 197:214 */       this.elementIndex.put(key, list);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected void removeFromElementIndex(Element element)
/* 202:    */   {
/* 203:220 */     QName qName = element.getQName();
/* 204:221 */     String name = qName.getName();
/* 205:222 */     removeFromElementIndex(qName, element);
/* 206:223 */     removeFromElementIndex(name, element);
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void removeFromElementIndex(Object key, Element value)
/* 210:    */   {
/* 211:227 */     Object oldValue = this.elementIndex.get(key);
/* 212:229 */     if ((oldValue instanceof List))
/* 213:    */     {
/* 214:230 */       List list = (List)oldValue;
/* 215:231 */       list.remove(value);
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:233 */       this.elementIndex.remove(key);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   protected void addToAttributeIndex(Attribute attribute)
/* 224:    */   {
/* 225:238 */     QName qName = attribute.getQName();
/* 226:239 */     String name = qName.getName();
/* 227:240 */     addToAttributeIndex(qName, attribute);
/* 228:241 */     addToAttributeIndex(name, attribute);
/* 229:    */   }
/* 230:    */   
/* 231:    */   protected void addToAttributeIndex(Object key, Attribute value)
/* 232:    */   {
/* 233:245 */     Object oldValue = this.attributeIndex.get(key);
/* 234:247 */     if (oldValue != null) {
/* 235:248 */       this.attributeIndex.put(key, value);
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected void removeFromAttributeIndex(Attribute attribute)
/* 240:    */   {
/* 241:253 */     QName qName = attribute.getQName();
/* 242:254 */     String name = qName.getName();
/* 243:255 */     removeFromAttributeIndex(qName, attribute);
/* 244:256 */     removeFromAttributeIndex(name, attribute);
/* 245:    */   }
/* 246:    */   
/* 247:    */   protected void removeFromAttributeIndex(Object key, Attribute value)
/* 248:    */   {
/* 249:260 */     Object oldValue = this.attributeIndex.get(key);
/* 250:262 */     if ((oldValue != null) && (oldValue.equals(value))) {
/* 251:263 */       this.attributeIndex.remove(key);
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected Map createIndex()
/* 256:    */   {
/* 257:273 */     return new HashMap();
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected List createList()
/* 261:    */   {
/* 262:282 */     return new ArrayList();
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.IndexedElement
 * JD-Core Version:    0.7.0.1
 */