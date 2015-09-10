/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.dom4j.Element;
/*  14:    */ import org.hibernate.AssertionFailure;
/*  15:    */ import org.hibernate.HibernateException;
/*  16:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  20:    */ import org.hibernate.loader.CollectionAliases;
/*  21:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  22:    */ import org.hibernate.type.Type;
/*  23:    */ import org.hibernate.type.XmlRepresentableType;
/*  24:    */ 
/*  25:    */ public abstract class PersistentIndexedElementHolder
/*  26:    */   extends AbstractPersistentCollection
/*  27:    */ {
/*  28:    */   protected Element element;
/*  29:    */   
/*  30:    */   public PersistentIndexedElementHolder(SessionImplementor session, Element element)
/*  31:    */   {
/*  32: 57 */     super(session);
/*  33: 58 */     this.element = element;
/*  34: 59 */     setInitialized();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static final class IndexedValue
/*  38:    */   {
/*  39:    */     String index;
/*  40:    */     Object value;
/*  41:    */     
/*  42:    */     IndexedValue(String index, Object value)
/*  43:    */     {
/*  44: 66 */       this.index = index;
/*  45: 67 */       this.value = value;
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected static String getIndex(Element element, String indexNodeName, int i)
/*  50:    */   {
/*  51: 72 */     if (indexNodeName != null) {
/*  52: 73 */       return element.attributeValue(indexNodeName);
/*  53:    */     }
/*  54: 76 */     return Integer.toString(i);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected static void setIndex(Element element, String indexNodeName, String index)
/*  58:    */   {
/*  59: 81 */     if (indexNodeName != null) {
/*  60: 81 */       element.addAttribute(indexNodeName, index);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected static String getIndexAttributeName(CollectionPersister persister)
/*  65:    */   {
/*  66: 85 */     String node = persister.getIndexNodeName();
/*  67: 86 */     return node == null ? null : node.substring(1);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  71:    */     throws HibernateException
/*  72:    */   {
/*  73: 92 */     Type elementType = persister.getElementType();
/*  74: 93 */     String indexNode = getIndexAttributeName(persister);
/*  75: 94 */     List elements = this.element.elements(persister.getElementNodeName());
/*  76: 95 */     HashMap snapshot = new HashMap(elements.size());
/*  77: 96 */     for (int i = 0; i < elements.size(); i++)
/*  78:    */     {
/*  79: 97 */       Element elem = (Element)elements.get(i);
/*  80: 98 */       Object value = elementType.fromXMLNode(elem, persister.getFactory());
/*  81: 99 */       Object copy = elementType.deepCopy(value, persister.getFactory());
/*  82:100 */       snapshot.put(getIndex(elem, indexNode, i), copy);
/*  83:    */     }
/*  84:102 */     return snapshot;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  88:    */     throws HibernateException
/*  89:    */   {
/*  90:109 */     return CollectionHelper.EMPTY_COLLECTION;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public PersistentIndexedElementHolder(SessionImplementor session, CollectionPersister persister, Serializable key)
/*  94:    */     throws HibernateException
/*  95:    */   {
/*  96:114 */     super(session);
/*  97:115 */     Element owner = (Element)session.getPersistenceContext().getCollectionOwner(key, persister);
/*  98:116 */     if (owner == null) {
/*  99:116 */       throw new AssertionFailure("null owner");
/* 100:    */     }
/* 101:118 */     String nodeName = persister.getNodeName();
/* 102:119 */     if (".".equals(nodeName))
/* 103:    */     {
/* 104:120 */       this.element = owner;
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:123 */       this.element = owner.element(nodeName);
/* 109:124 */       if (this.element == null) {
/* 110:124 */         this.element = owner.addElement(nodeName);
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isWrapper(Object collection)
/* 116:    */   {
/* 117:129 */     return this.element == collection;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean equalsSnapshot(CollectionPersister persister)
/* 121:    */     throws HibernateException
/* 122:    */   {
/* 123:133 */     Type elementType = persister.getElementType();
/* 124:134 */     String indexNode = getIndexAttributeName(persister);
/* 125:135 */     HashMap snapshot = (HashMap)getSnapshot();
/* 126:136 */     List elements = this.element.elements(persister.getElementNodeName());
/* 127:137 */     if (snapshot.size() != elements.size()) {
/* 128:137 */       return false;
/* 129:    */     }
/* 130:138 */     for (int i = 0; i < snapshot.size(); i++)
/* 131:    */     {
/* 132:139 */       Element elem = (Element)elements.get(i);
/* 133:140 */       Object old = snapshot.get(getIndex(elem, indexNode, i));
/* 134:141 */       Object current = elementType.fromXMLNode(elem, persister.getFactory());
/* 135:142 */       if (elementType.isDirty(old, current, getSession())) {
/* 136:142 */         return false;
/* 137:    */       }
/* 138:    */     }
/* 139:144 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/* 143:    */   {
/* 144:148 */     return ((HashMap)snapshot).isEmpty();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean empty()
/* 148:    */   {
/* 149:152 */     return !this.element.elementIterator().hasNext();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 153:    */     throws HibernateException, SQLException
/* 154:    */   {
/* 155:157 */     Object object = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 156:158 */     Type elementType = persister.getElementType();
/* 157:159 */     SessionFactoryImplementor factory = persister.getFactory();
/* 158:160 */     String indexNode = getIndexAttributeName(persister);
/* 159:    */     
/* 160:162 */     Element elem = this.element.addElement(persister.getElementNodeName());
/* 161:163 */     elementType.setToXMLNode(elem, object, factory);
/* 162:    */     
/* 163:165 */     Type indexType = persister.getIndexType();
/* 164:166 */     Object indexValue = persister.readIndex(rs, descriptor.getSuffixedIndexAliases(), getSession());
/* 165:167 */     String index = ((XmlRepresentableType)indexType).toXMLString(indexValue, factory);
/* 166:168 */     setIndex(elem, indexNode, index);
/* 167:169 */     return object;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Iterator entries(CollectionPersister persister)
/* 171:    */   {
/* 172:174 */     Type elementType = persister.getElementType();
/* 173:175 */     String indexNode = getIndexAttributeName(persister);
/* 174:176 */     List elements = this.element.elements(persister.getElementNodeName());
/* 175:177 */     int length = elements.size();
/* 176:178 */     List result = new ArrayList(length);
/* 177:179 */     for (int i = 0; i < length; i++)
/* 178:    */     {
/* 179:180 */       Element elem = (Element)elements.get(i);
/* 180:181 */       Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 181:182 */       result.add(new IndexedValue(getIndex(elem, indexNode, i), object));
/* 182:    */     }
/* 183:184 */     return result.iterator();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize) {}
/* 187:    */   
/* 188:    */   public boolean isDirectlyAccessible()
/* 189:    */   {
/* 190:190 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Object getValue()
/* 194:    */   {
/* 195:194 */     return this.element;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 199:    */     throws HibernateException
/* 200:    */   {
/* 201:200 */     Type indexType = persister.getIndexType();
/* 202:201 */     HashMap snapshot = (HashMap)getSnapshot();
/* 203:202 */     HashMap deletes = (HashMap)snapshot.clone();
/* 204:203 */     deletes.keySet().removeAll(((HashMap)getSnapshot(persister)).keySet());
/* 205:204 */     ArrayList deleteList = new ArrayList(deletes.size());
/* 206:205 */     for (Object o : deletes.entrySet())
/* 207:    */     {
/* 208:206 */       Map.Entry me = (Map.Entry)o;
/* 209:207 */       Object object = indexIsFormula ? me.getValue() : ((XmlRepresentableType)indexType).fromXMLString((String)me.getKey(), persister.getFactory());
/* 210:210 */       if (object != null) {
/* 211:211 */         deleteList.add(object);
/* 212:    */       }
/* 213:    */     }
/* 214:215 */     return deleteList.iterator();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean needsInserting(Object entry, int i, Type elementType)
/* 218:    */     throws HibernateException
/* 219:    */   {
/* 220:221 */     HashMap snapshot = (HashMap)getSnapshot();
/* 221:222 */     IndexedValue iv = (IndexedValue)entry;
/* 222:223 */     return (iv.value != null) && (snapshot.get(iv.index) == null);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean needsUpdating(Object entry, int i, Type elementType)
/* 226:    */     throws HibernateException
/* 227:    */   {
/* 228:228 */     HashMap snapshot = (HashMap)getSnapshot();
/* 229:229 */     IndexedValue iv = (IndexedValue)entry;
/* 230:230 */     Object old = snapshot.get(iv.index);
/* 231:231 */     return (old != null) && (elementType.isDirty(old, iv.value, getSession()));
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 235:    */   {
/* 236:235 */     String index = ((IndexedValue)entry).index;
/* 237:236 */     Type indexType = persister.getIndexType();
/* 238:237 */     return ((XmlRepresentableType)indexType).fromXMLString(index, persister.getFactory());
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Object getElement(Object entry)
/* 242:    */   {
/* 243:241 */     return ((IndexedValue)entry).value;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Object getSnapshotElement(Object entry, int i)
/* 247:    */   {
/* 248:245 */     return ((HashMap)getSnapshot()).get(((IndexedValue)entry).index);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean entryExists(Object entry, int i)
/* 252:    */   {
/* 253:249 */     return entry != null;
/* 254:    */   }
/* 255:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentIndexedElementHolder
 * JD-Core Version:    0.7.0.1
 */