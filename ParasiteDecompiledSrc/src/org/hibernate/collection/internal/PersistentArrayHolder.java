/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.loader.CollectionAliases;
/*  15:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class PersistentArrayHolder
/*  20:    */   extends AbstractPersistentCollection
/*  21:    */ {
/*  22:    */   protected Object array;
/*  23: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PersistentArrayHolder.class.getName());
/*  24:    */   private transient Class elementClass;
/*  25:    */   private transient List tempList;
/*  26:    */   
/*  27:    */   public PersistentArrayHolder(SessionImplementor session, Object array)
/*  28:    */   {
/*  29: 60 */     super(session);
/*  30: 61 */     this.array = array;
/*  31: 62 */     setInitialized();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 66 */     int length = Array.getLength(this.array);
/*  38: 67 */     Serializable result = (Serializable)Array.newInstance(persister.getElementClass(), length);
/*  39: 68 */     for (int i = 0; i < length; i++)
/*  40:    */     {
/*  41: 69 */       Object elt = Array.get(this.array, i);
/*  42:    */       try
/*  43:    */       {
/*  44: 71 */         Array.set(result, i, persister.getElementType().deepCopy(elt, persister.getFactory()));
/*  45:    */       }
/*  46:    */       catch (IllegalArgumentException iae)
/*  47:    */       {
/*  48: 74 */         LOG.invalidArrayElementType(iae.getMessage());
/*  49: 75 */         throw new HibernateException("Array element type error", iae);
/*  50:    */       }
/*  51:    */     }
/*  52: 78 */     return result;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/*  56:    */   {
/*  57: 82 */     return Array.getLength(snapshot) == 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 87 */     Object[] sn = (Object[])snapshot;
/*  64: 88 */     Object[] arr = (Object[])this.array;
/*  65: 89 */     ArrayList result = new ArrayList();
/*  66: 90 */     for (int i = 0; i < sn.length; i++) {
/*  67: 90 */       result.add(sn[i]);
/*  68:    */     }
/*  69: 91 */     for (int i = 0; i < sn.length; i++) {
/*  70: 91 */       identityRemove(result, arr[i], entityName, getSession());
/*  71:    */     }
/*  72: 92 */     return result;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public PersistentArrayHolder(SessionImplementor session, CollectionPersister persister)
/*  76:    */     throws HibernateException
/*  77:    */   {
/*  78: 96 */     super(session);
/*  79: 97 */     this.elementClass = persister.getElementClass();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object getArray()
/*  83:    */   {
/*  84:101 */     return this.array;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isWrapper(Object collection)
/*  88:    */   {
/*  89:105 */     return this.array == collection;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  93:    */     throws HibernateException
/*  94:    */   {
/*  95:109 */     Type elementType = persister.getElementType();
/*  96:110 */     Serializable snapshot = getSnapshot();
/*  97:111 */     int xlen = Array.getLength(snapshot);
/*  98:112 */     if (xlen != Array.getLength(this.array)) {
/*  99:112 */       return false;
/* 100:    */     }
/* 101:113 */     for (int i = 0; i < xlen; i++) {
/* 102:114 */       if (elementType.isDirty(Array.get(snapshot, i), Array.get(this.array, i), getSession())) {
/* 103:114 */         return false;
/* 104:    */       }
/* 105:    */     }
/* 106:116 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Iterator elements()
/* 110:    */   {
/* 111:121 */     int length = Array.getLength(this.array);
/* 112:122 */     List list = new ArrayList(length);
/* 113:123 */     for (int i = 0; i < length; i++) {
/* 114:124 */       list.add(Array.get(this.array, i));
/* 115:    */     }
/* 116:126 */     return list.iterator();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean empty()
/* 120:    */   {
/* 121:130 */     return false;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 125:    */     throws HibernateException, SQLException
/* 126:    */   {
/* 127:136 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 128:137 */     int index = ((Integer)persister.readIndex(rs, descriptor.getSuffixedIndexAliases(), getSession())).intValue();
/* 129:138 */     for (int i = this.tempList.size(); i <= index; i++) {
/* 130:139 */       this.tempList.add(i, null);
/* 131:    */     }
/* 132:141 */     this.tempList.set(index, element);
/* 133:142 */     return element;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Iterator entries(CollectionPersister persister)
/* 137:    */   {
/* 138:146 */     return elements();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void beginRead()
/* 142:    */   {
/* 143:151 */     super.beginRead();
/* 144:152 */     this.tempList = new ArrayList();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean endRead()
/* 148:    */   {
/* 149:156 */     setInitialized();
/* 150:157 */     this.array = Array.newInstance(this.elementClass, this.tempList.size());
/* 151:158 */     for (int i = 0; i < this.tempList.size(); i++) {
/* 152:159 */       Array.set(this.array, i, this.tempList.get(i));
/* 153:    */     }
/* 154:161 */     this.tempList = null;
/* 155:162 */     return true;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize) {}
/* 159:    */   
/* 160:    */   public boolean isDirectlyAccessible()
/* 161:    */   {
/* 162:171 */     return true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:176 */     Serializable[] cached = (Serializable[])disassembled;
/* 169:    */     
/* 170:178 */     this.array = Array.newInstance(persister.getElementClass(), cached.length);
/* 171:180 */     for (int i = 0; i < cached.length; i++) {
/* 172:181 */       Array.set(this.array, i, persister.getElementType().assemble(cached[i], getSession(), owner));
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Serializable disassemble(CollectionPersister persister)
/* 177:    */     throws HibernateException
/* 178:    */   {
/* 179:186 */     int length = Array.getLength(this.array);
/* 180:187 */     Serializable[] result = new Serializable[length];
/* 181:188 */     for (int i = 0; i < length; i++) {
/* 182:189 */       result[i] = persister.getElementType().disassemble(Array.get(this.array, i), getSession(), null);
/* 183:    */     }
/* 184:198 */     return result;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Object getValue()
/* 188:    */   {
/* 189:204 */     return this.array;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 193:    */     throws HibernateException
/* 194:    */   {
/* 195:208 */     List deletes = new ArrayList();
/* 196:209 */     Serializable sn = getSnapshot();
/* 197:210 */     int snSize = Array.getLength(sn);
/* 198:211 */     int arraySize = Array.getLength(this.array);
/* 199:    */     int end;
/* 200:    */     int end;
/* 201:213 */     if (snSize > arraySize)
/* 202:    */     {
/* 203:214 */       for (int i = arraySize; i < snSize; i++) {
/* 204:214 */         deletes.add(Integer.valueOf(i));
/* 205:    */       }
/* 206:215 */       end = arraySize;
/* 207:    */     }
/* 208:    */     else
/* 209:    */     {
/* 210:218 */       end = snSize;
/* 211:    */     }
/* 212:220 */     for (int i = 0; i < end; i++) {
/* 213:221 */       if ((Array.get(this.array, i) == null) && (Array.get(sn, i) != null)) {
/* 214:221 */         deletes.add(Integer.valueOf(i));
/* 215:    */       }
/* 216:    */     }
/* 217:223 */     return deletes.iterator();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 221:    */     throws HibernateException
/* 222:    */   {
/* 223:227 */     Serializable sn = getSnapshot();
/* 224:228 */     return (Array.get(this.array, i) != null) && ((i >= Array.getLength(sn)) || (Array.get(sn, i) == null));
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 228:    */     throws HibernateException
/* 229:    */   {
/* 230:232 */     Serializable sn = getSnapshot();
/* 231:233 */     return (i < Array.getLength(sn)) && (Array.get(sn, i) != null) && (Array.get(this.array, i) != null) && (elemType.isDirty(Array.get(this.array, i), Array.get(sn, i), getSession()));
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 235:    */   {
/* 236:240 */     return Integer.valueOf(i);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Object getElement(Object entry)
/* 240:    */   {
/* 241:244 */     return entry;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Object getSnapshotElement(Object entry, int i)
/* 245:    */   {
/* 246:248 */     Serializable sn = getSnapshot();
/* 247:249 */     return Array.get(sn, i);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean entryExists(Object entry, int i)
/* 251:    */   {
/* 252:253 */     return entry != null;
/* 253:    */   }
/* 254:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentArrayHolder
 * JD-Core Version:    0.7.0.1
 */