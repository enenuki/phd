/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   7:    */ import org.hibernate.sql.Alias;
/*   8:    */ 
/*   9:    */ public class Join
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12: 37 */   private static final Alias PK_ALIAS = new Alias(15, "PK");
/*  13: 39 */   private ArrayList properties = new ArrayList();
/*  14: 40 */   private ArrayList declaredProperties = new ArrayList();
/*  15:    */   private Table table;
/*  16:    */   private KeyValue key;
/*  17:    */   private PersistentClass persistentClass;
/*  18:    */   private boolean sequentialSelect;
/*  19:    */   private boolean inverse;
/*  20:    */   private boolean optional;
/*  21:    */   private String customSQLInsert;
/*  22:    */   private boolean customInsertCallable;
/*  23:    */   private ExecuteUpdateResultCheckStyle insertCheckStyle;
/*  24:    */   private String customSQLUpdate;
/*  25:    */   private boolean customUpdateCallable;
/*  26:    */   private ExecuteUpdateResultCheckStyle updateCheckStyle;
/*  27:    */   private String customSQLDelete;
/*  28:    */   private boolean customDeleteCallable;
/*  29:    */   private ExecuteUpdateResultCheckStyle deleteCheckStyle;
/*  30:    */   
/*  31:    */   public void addProperty(Property prop)
/*  32:    */   {
/*  33: 60 */     this.properties.add(prop);
/*  34: 61 */     this.declaredProperties.add(prop);
/*  35: 62 */     prop.setPersistentClass(getPersistentClass());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void addMappedsuperclassProperty(Property prop)
/*  39:    */   {
/*  40: 65 */     this.properties.add(prop);
/*  41: 66 */     prop.setPersistentClass(getPersistentClass());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Iterator getDeclaredPropertyIterator()
/*  45:    */   {
/*  46: 70 */     return this.declaredProperties.iterator();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean containsProperty(Property prop)
/*  50:    */   {
/*  51: 74 */     return this.properties.contains(prop);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Iterator getPropertyIterator()
/*  55:    */   {
/*  56: 77 */     return this.properties.iterator();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Table getTable()
/*  60:    */   {
/*  61: 81 */     return this.table;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setTable(Table table)
/*  65:    */   {
/*  66: 84 */     this.table = table;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public KeyValue getKey()
/*  70:    */   {
/*  71: 88 */     return this.key;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setKey(KeyValue key)
/*  75:    */   {
/*  76: 91 */     this.key = key;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public PersistentClass getPersistentClass()
/*  80:    */   {
/*  81: 95 */     return this.persistentClass;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setPersistentClass(PersistentClass persistentClass)
/*  85:    */   {
/*  86: 99 */     this.persistentClass = persistentClass;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void createForeignKey()
/*  90:    */   {
/*  91:103 */     getKey().createForeignKeyOfEntity(this.persistentClass.getEntityName());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void createPrimaryKey()
/*  95:    */   {
/*  96:108 */     PrimaryKey pk = new PrimaryKey();
/*  97:109 */     pk.setTable(this.table);
/*  98:110 */     pk.setName(PK_ALIAS.toAliasString(this.table.getName()));
/*  99:111 */     this.table.setPrimaryKey(pk);
/* 100:    */     
/* 101:113 */     pk.addColumns(getKey().getColumnIterator());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getPropertySpan()
/* 105:    */   {
/* 106:117 */     return this.properties.size();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setCustomSQLInsert(String customSQLInsert, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 110:    */   {
/* 111:121 */     this.customSQLInsert = customSQLInsert;
/* 112:122 */     this.customInsertCallable = callable;
/* 113:123 */     this.insertCheckStyle = checkStyle;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCustomSQLInsert()
/* 117:    */   {
/* 118:127 */     return this.customSQLInsert;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isCustomInsertCallable()
/* 122:    */   {
/* 123:131 */     return this.customInsertCallable;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ExecuteUpdateResultCheckStyle getCustomSQLInsertCheckStyle()
/* 127:    */   {
/* 128:135 */     return this.insertCheckStyle;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setCustomSQLUpdate(String customSQLUpdate, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 132:    */   {
/* 133:139 */     this.customSQLUpdate = customSQLUpdate;
/* 134:140 */     this.customUpdateCallable = callable;
/* 135:141 */     this.updateCheckStyle = checkStyle;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getCustomSQLUpdate()
/* 139:    */   {
/* 140:145 */     return this.customSQLUpdate;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isCustomUpdateCallable()
/* 144:    */   {
/* 145:149 */     return this.customUpdateCallable;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ExecuteUpdateResultCheckStyle getCustomSQLUpdateCheckStyle()
/* 149:    */   {
/* 150:153 */     return this.updateCheckStyle;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setCustomSQLDelete(String customSQLDelete, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 154:    */   {
/* 155:157 */     this.customSQLDelete = customSQLDelete;
/* 156:158 */     this.customDeleteCallable = callable;
/* 157:159 */     this.deleteCheckStyle = checkStyle;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getCustomSQLDelete()
/* 161:    */   {
/* 162:163 */     return this.customSQLDelete;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isCustomDeleteCallable()
/* 166:    */   {
/* 167:167 */     return this.customDeleteCallable;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public ExecuteUpdateResultCheckStyle getCustomSQLDeleteCheckStyle()
/* 171:    */   {
/* 172:171 */     return this.deleteCheckStyle;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isSequentialSelect()
/* 176:    */   {
/* 177:175 */     return this.sequentialSelect;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setSequentialSelect(boolean deferred)
/* 181:    */   {
/* 182:178 */     this.sequentialSelect = deferred;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isInverse()
/* 186:    */   {
/* 187:182 */     return this.inverse;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setInverse(boolean leftJoin)
/* 191:    */   {
/* 192:186 */     this.inverse = leftJoin;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String toString()
/* 196:    */   {
/* 197:190 */     return getClass().getName() + '(' + this.table.toString() + ')';
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isLazy()
/* 201:    */   {
/* 202:194 */     Iterator iter = getPropertyIterator();
/* 203:195 */     while (iter.hasNext())
/* 204:    */     {
/* 205:196 */       Property prop = (Property)iter.next();
/* 206:197 */       if (!prop.isLazy()) {
/* 207:197 */         return false;
/* 208:    */       }
/* 209:    */     }
/* 210:199 */     return true;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean isOptional()
/* 214:    */   {
/* 215:203 */     return this.optional;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setOptional(boolean nullable)
/* 219:    */   {
/* 220:206 */     this.optional = nullable;
/* 221:    */   }
/* 222:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Join
 * JD-Core Version:    0.7.0.1
 */