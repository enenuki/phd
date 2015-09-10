/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import org.dom4j.Element;
/*  11:    */ import org.hibernate.AssertionFailure;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  16:    */ import org.hibernate.loader.CollectionAliases;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class PersistentElementHolder
/*  21:    */   extends AbstractPersistentCollection
/*  22:    */ {
/*  23:    */   protected Element element;
/*  24:    */   
/*  25:    */   public PersistentElementHolder(SessionImplementor session, Element element)
/*  26:    */   {
/*  27: 53 */     super(session);
/*  28: 54 */     this.element = element;
/*  29: 55 */     setInitialized();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  33:    */     throws HibernateException
/*  34:    */   {
/*  35: 61 */     Type elementType = persister.getElementType();
/*  36: 62 */     List elements = this.element.elements(persister.getElementNodeName());
/*  37: 63 */     ArrayList snapshot = new ArrayList(elements.size());
/*  38: 64 */     for (int i = 0; i < elements.size(); i++)
/*  39:    */     {
/*  40: 65 */       Element elem = (Element)elements.get(i);
/*  41: 66 */       Object value = elementType.fromXMLNode(elem, persister.getFactory());
/*  42: 67 */       Object copy = elementType.deepCopy(value, persister.getFactory());
/*  43: 68 */       snapshot.add(copy);
/*  44:    */     }
/*  45: 70 */     return snapshot;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  49:    */     throws HibernateException
/*  50:    */   {
/*  51: 78 */     return CollectionHelper.EMPTY_COLLECTION;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public PersistentElementHolder(SessionImplementor session, CollectionPersister persister, Serializable key)
/*  55:    */     throws HibernateException
/*  56:    */   {
/*  57: 83 */     super(session);
/*  58: 84 */     Element owner = (Element)session.getPersistenceContext().getCollectionOwner(key, persister);
/*  59: 85 */     if (owner == null) {
/*  60: 85 */       throw new AssertionFailure("null owner");
/*  61:    */     }
/*  62: 87 */     String nodeName = persister.getNodeName();
/*  63: 88 */     if (".".equals(nodeName))
/*  64:    */     {
/*  65: 89 */       this.element = owner;
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69: 92 */       this.element = owner.element(nodeName);
/*  70: 93 */       if (this.element == null) {
/*  71: 93 */         this.element = owner.addElement(nodeName);
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isWrapper(Object collection)
/*  77:    */   {
/*  78: 98 */     return this.element == collection;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  82:    */     throws HibernateException
/*  83:    */   {
/*  84:102 */     Type elementType = persister.getElementType();
/*  85:    */     
/*  86:104 */     ArrayList snapshot = (ArrayList)getSnapshot();
/*  87:105 */     List elements = this.element.elements(persister.getElementNodeName());
/*  88:106 */     if (snapshot.size() != elements.size()) {
/*  89:106 */       return false;
/*  90:    */     }
/*  91:107 */     for (int i = 0; i < snapshot.size(); i++)
/*  92:    */     {
/*  93:108 */       Object old = snapshot.get(i);
/*  94:109 */       Element elem = (Element)elements.get(i);
/*  95:110 */       Object current = elementType.fromXMLNode(elem, persister.getFactory());
/*  96:111 */       if (elementType.isDirty(old, current, getSession())) {
/*  97:111 */         return false;
/*  98:    */       }
/*  99:    */     }
/* 100:113 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/* 104:    */   {
/* 105:117 */     return ((Collection)snapshot).isEmpty();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean empty()
/* 109:    */   {
/* 110:122 */     return !this.element.elementIterator().hasNext();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 114:    */     throws HibernateException, SQLException
/* 115:    */   {
/* 116:127 */     Object object = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 117:128 */     Type elementType = persister.getElementType();
/* 118:129 */     Element subelement = this.element.addElement(persister.getElementNodeName());
/* 119:130 */     elementType.setToXMLNode(subelement, object, persister.getFactory());
/* 120:131 */     return object;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Iterator entries(CollectionPersister persister)
/* 124:    */   {
/* 125:136 */     Type elementType = persister.getElementType();
/* 126:137 */     List elements = this.element.elements(persister.getElementNodeName());
/* 127:138 */     int length = elements.size();
/* 128:139 */     List result = new ArrayList(length);
/* 129:140 */     for (int i = 0; i < length; i++)
/* 130:    */     {
/* 131:141 */       Element elem = (Element)elements.get(i);
/* 132:142 */       Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 133:143 */       result.add(object);
/* 134:    */     }
/* 135:145 */     return result.iterator();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize) {}
/* 139:    */   
/* 140:    */   public boolean isDirectlyAccessible()
/* 141:    */   {
/* 142:152 */     return true;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 146:    */     throws HibernateException
/* 147:    */   {
/* 148:158 */     Type elementType = persister.getElementType();
/* 149:159 */     Serializable[] cached = (Serializable[])disassembled;
/* 150:160 */     for (int i = 0; i < cached.length; i++)
/* 151:    */     {
/* 152:161 */       Object object = elementType.assemble(cached[i], getSession(), owner);
/* 153:162 */       Element subelement = this.element.addElement(persister.getElementNodeName());
/* 154:163 */       elementType.setToXMLNode(subelement, object, persister.getFactory());
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Serializable disassemble(CollectionPersister persister)
/* 159:    */     throws HibernateException
/* 160:    */   {
/* 161:170 */     Type elementType = persister.getElementType();
/* 162:171 */     List elements = this.element.elements(persister.getElementNodeName());
/* 163:172 */     int length = elements.size();
/* 164:173 */     Serializable[] result = new Serializable[length];
/* 165:174 */     for (int i = 0; i < length; i++)
/* 166:    */     {
/* 167:175 */       Element elem = (Element)elements.get(i);
/* 168:176 */       Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 169:177 */       result[i] = elementType.disassemble(object, getSession(), null);
/* 170:    */     }
/* 171:179 */     return result;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Object getValue()
/* 175:    */   {
/* 176:184 */     return this.element;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 180:    */     throws HibernateException
/* 181:    */   {
/* 182:190 */     Type elementType = persister.getElementType();
/* 183:191 */     ArrayList snapshot = (ArrayList)getSnapshot();
/* 184:192 */     List elements = this.element.elements(persister.getElementNodeName());
/* 185:193 */     ArrayList result = new ArrayList();
/* 186:194 */     for (int i = 0; i < snapshot.size(); i++)
/* 187:    */     {
/* 188:195 */       Object old = snapshot.get(i);
/* 189:196 */       if (i >= elements.size())
/* 190:    */       {
/* 191:197 */         result.add(old);
/* 192:    */       }
/* 193:    */       else
/* 194:    */       {
/* 195:200 */         Element elem = (Element)elements.get(i);
/* 196:201 */         Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 197:202 */         if (elementType.isDirty(old, object, getSession())) {
/* 198:202 */           result.add(old);
/* 199:    */         }
/* 200:    */       }
/* 201:    */     }
/* 202:205 */     return result.iterator();
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean needsInserting(Object entry, int i, Type elementType)
/* 206:    */     throws HibernateException
/* 207:    */   {
/* 208:211 */     ArrayList snapshot = (ArrayList)getSnapshot();
/* 209:212 */     return (i >= snapshot.size()) || (elementType.isDirty(snapshot.get(i), entry, getSession()));
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean needsUpdating(Object entry, int i, Type elementType)
/* 213:    */     throws HibernateException
/* 214:    */   {
/* 215:217 */     return false;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 219:    */   {
/* 220:221 */     throw new UnsupportedOperationException();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Object getElement(Object entry)
/* 224:    */   {
/* 225:225 */     return entry;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Object getSnapshotElement(Object entry, int i)
/* 229:    */   {
/* 230:229 */     throw new UnsupportedOperationException();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean entryExists(Object entry, int i)
/* 234:    */   {
/* 235:233 */     return entry != null;
/* 236:    */   }
/* 237:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentElementHolder
 * JD-Core Version:    0.7.0.1
 */