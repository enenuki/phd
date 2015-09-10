/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ public class ExpandedNameTable
/*   4:    */ {
/*   5:    */   private ExtendedType[] m_extendedTypes;
/*   6: 44 */   private static int m_initialSize = 128;
/*   7:    */   private int m_nextType;
/*   8:    */   public static final int ELEMENT = 1;
/*   9:    */   public static final int ATTRIBUTE = 2;
/*  10:    */   public static final int TEXT = 3;
/*  11:    */   public static final int CDATA_SECTION = 4;
/*  12:    */   public static final int ENTITY_REFERENCE = 5;
/*  13:    */   public static final int ENTITY = 6;
/*  14:    */   public static final int PROCESSING_INSTRUCTION = 7;
/*  15:    */   public static final int COMMENT = 8;
/*  16:    */   public static final int DOCUMENT = 9;
/*  17:    */   public static final int DOCUMENT_TYPE = 10;
/*  18:    */   public static final int DOCUMENT_FRAGMENT = 11;
/*  19:    */   public static final int NOTATION = 12;
/*  20:    */   public static final int NAMESPACE = 13;
/*  21: 68 */   ExtendedType hashET = new ExtendedType(-1, "", "");
/*  22:    */   private static ExtendedType[] m_defaultExtendedTypes;
/*  23: 77 */   private static float m_loadFactor = 0.75F;
/*  24: 83 */   private static int m_initialCapacity = 203;
/*  25:    */   private int m_capacity;
/*  26:    */   private int m_threshold;
/*  27:    */   private HashEntry[] m_table;
/*  28:    */   
/*  29:    */   static
/*  30:    */   {
/*  31:108 */     m_defaultExtendedTypes = new ExtendedType[14];
/*  32:110 */     for (int i = 0; i < 14; i++) {
/*  33:112 */       m_defaultExtendedTypes[i] = new ExtendedType(i, "", "");
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ExpandedNameTable()
/*  38:    */   {
/*  39:121 */     this.m_capacity = m_initialCapacity;
/*  40:122 */     this.m_threshold = ((int)(this.m_capacity * m_loadFactor));
/*  41:123 */     this.m_table = new HashEntry[this.m_capacity];
/*  42:    */     
/*  43:125 */     initExtendedTypes();
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void initExtendedTypes()
/*  47:    */   {
/*  48:135 */     this.m_extendedTypes = new ExtendedType[m_initialSize];
/*  49:136 */     for (int i = 0; i < 14; i++)
/*  50:    */     {
/*  51:137 */       this.m_extendedTypes[i] = m_defaultExtendedTypes[i];
/*  52:138 */       this.m_table[i] = new HashEntry(m_defaultExtendedTypes[i], i, i, null);
/*  53:    */     }
/*  54:141 */     this.m_nextType = 14;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getExpandedTypeID(String namespace, String localName, int type)
/*  58:    */   {
/*  59:158 */     return getExpandedTypeID(namespace, localName, type, false);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getExpandedTypeID(String namespace, String localName, int type, boolean searchOnly)
/*  63:    */   {
/*  64:181 */     if (null == namespace) {
/*  65:182 */       namespace = "";
/*  66:    */     }
/*  67:183 */     if (null == localName) {
/*  68:184 */       localName = "";
/*  69:    */     }
/*  70:187 */     int hash = type + namespace.hashCode() + localName.hashCode();
/*  71:    */     
/*  72:    */ 
/*  73:190 */     this.hashET.redefine(type, namespace, localName, hash);
/*  74:    */     
/*  75:    */ 
/*  76:193 */     int index = hash % this.m_capacity;
/*  77:194 */     if (index < 0) {
/*  78:195 */       index = -index;
/*  79:    */     }
/*  80:199 */     for (HashEntry e = this.m_table[index]; e != null; e = e.next) {
/*  81:201 */       if ((e.hash == hash) && (e.key.equals(this.hashET))) {
/*  82:202 */         return e.value;
/*  83:    */       }
/*  84:    */     }
/*  85:205 */     if (searchOnly) {
/*  86:207 */       return -1;
/*  87:    */     }
/*  88:211 */     if (this.m_nextType > this.m_threshold)
/*  89:    */     {
/*  90:212 */       rehash();
/*  91:213 */       index = hash % this.m_capacity;
/*  92:214 */       if (index < 0) {
/*  93:215 */         index = -index;
/*  94:    */       }
/*  95:    */     }
/*  96:219 */     ExtendedType newET = new ExtendedType(type, namespace, localName, hash);
/*  97:222 */     if (this.m_extendedTypes.length == this.m_nextType)
/*  98:    */     {
/*  99:223 */       ExtendedType[] newArray = new ExtendedType[this.m_extendedTypes.length * 2];
/* 100:224 */       System.arraycopy(this.m_extendedTypes, 0, newArray, 0, this.m_extendedTypes.length);
/* 101:    */       
/* 102:226 */       this.m_extendedTypes = newArray;
/* 103:    */     }
/* 104:229 */     this.m_extendedTypes[this.m_nextType] = newET;
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:233 */     HashEntry entry = new HashEntry(newET, this.m_nextType, hash, this.m_table[index]);
/* 109:234 */     this.m_table[index] = entry;
/* 110:    */     
/* 111:236 */     return this.m_nextType++;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void rehash()
/* 115:    */   {
/* 116:247 */     int oldCapacity = this.m_capacity;
/* 117:248 */     HashEntry[] oldTable = this.m_table;
/* 118:    */     
/* 119:250 */     int newCapacity = 2 * oldCapacity + 1;
/* 120:251 */     this.m_capacity = newCapacity;
/* 121:252 */     this.m_threshold = ((int)(newCapacity * m_loadFactor));
/* 122:    */     
/* 123:254 */     this.m_table = new HashEntry[newCapacity];
/* 124:255 */     for (int i = oldCapacity - 1; i >= 0; i--) {
/* 125:257 */       for (HashEntry old = oldTable[i]; old != null;)
/* 126:    */       {
/* 127:259 */         HashEntry e = old;
/* 128:260 */         old = old.next;
/* 129:    */         
/* 130:262 */         int newIndex = e.hash % newCapacity;
/* 131:263 */         if (newIndex < 0) {
/* 132:264 */           newIndex = -newIndex;
/* 133:    */         }
/* 134:266 */         e.next = this.m_table[newIndex];
/* 135:267 */         this.m_table[newIndex] = e;
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getExpandedTypeID(int type)
/* 141:    */   {
/* 142:280 */     return type;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getLocalName(int ExpandedNameID)
/* 146:    */   {
/* 147:291 */     return this.m_extendedTypes[ExpandedNameID].getLocalName();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final int getLocalNameID(int ExpandedNameID)
/* 151:    */   {
/* 152:303 */     if (this.m_extendedTypes[ExpandedNameID].getLocalName().equals("")) {
/* 153:304 */       return 0;
/* 154:    */     }
/* 155:306 */     return ExpandedNameID;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getNamespace(int ExpandedNameID)
/* 159:    */   {
/* 160:319 */     String namespace = this.m_extendedTypes[ExpandedNameID].getNamespace();
/* 161:320 */     return namespace.equals("") ? null : namespace;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public final int getNamespaceID(int ExpandedNameID)
/* 165:    */   {
/* 166:332 */     if (this.m_extendedTypes[ExpandedNameID].getNamespace().equals("")) {
/* 167:333 */       return 0;
/* 168:    */     }
/* 169:335 */     return ExpandedNameID;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public final short getType(int ExpandedNameID)
/* 173:    */   {
/* 174:347 */     return (short)this.m_extendedTypes[ExpandedNameID].getNodeType();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getSize()
/* 178:    */   {
/* 179:357 */     return this.m_nextType;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ExtendedType[] getExtendedTypes()
/* 183:    */   {
/* 184:367 */     return this.m_extendedTypes;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private static final class HashEntry
/* 188:    */   {
/* 189:    */     ExtendedType key;
/* 190:    */     int value;
/* 191:    */     int hash;
/* 192:    */     HashEntry next;
/* 193:    */     
/* 194:    */     protected HashEntry(ExtendedType key, int value, int hash, HashEntry next)
/* 195:    */     {
/* 196:384 */       this.key = key;
/* 197:385 */       this.value = value;
/* 198:386 */       this.hash = hash;
/* 199:387 */       this.next = next;
/* 200:    */     }
/* 201:    */   }
/* 202:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.ExpandedNameTable
 * JD-Core Version:    0.7.0.1
 */