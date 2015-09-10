/*   1:    */ package org.hibernate.pretty;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   5:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   6:    */ import org.hibernate.persister.entity.EntityPersister;
/*   7:    */ import org.hibernate.type.Type;
/*   8:    */ 
/*   9:    */ public final class MessageHelper
/*  10:    */ {
/*  11:    */   public static String infoString(String entityName, Serializable id)
/*  12:    */   {
/*  13: 57 */     StringBuffer s = new StringBuffer();
/*  14: 58 */     s.append('[');
/*  15: 59 */     if (entityName == null) {
/*  16: 60 */       s.append("<null entity name>");
/*  17:    */     } else {
/*  18: 63 */       s.append(entityName);
/*  19:    */     }
/*  20: 65 */     s.append('#');
/*  21: 67 */     if (id == null) {
/*  22: 68 */       s.append("<null>");
/*  23:    */     } else {
/*  24: 71 */       s.append(id);
/*  25:    */     }
/*  26: 73 */     s.append(']');
/*  27:    */     
/*  28: 75 */     return s.toString();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String infoString(EntityPersister persister, Object id, SessionFactoryImplementor factory)
/*  32:    */   {
/*  33: 90 */     StringBuffer s = new StringBuffer();
/*  34: 91 */     s.append('[');
/*  35:    */     Type idType;
/*  36:    */     Type idType;
/*  37: 93 */     if (persister == null)
/*  38:    */     {
/*  39: 94 */       s.append("<null EntityPersister>");
/*  40: 95 */       idType = null;
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 98 */       s.append(persister.getEntityName());
/*  45: 99 */       idType = persister.getIdentifierType();
/*  46:    */     }
/*  47:101 */     s.append('#');
/*  48:103 */     if (id == null) {
/*  49:104 */       s.append("<null>");
/*  50:107 */     } else if (idType == null) {
/*  51:108 */       s.append(id);
/*  52:    */     } else {
/*  53:111 */       s.append(idType.toLoggableString(id, factory));
/*  54:    */     }
/*  55:114 */     s.append(']');
/*  56:    */     
/*  57:116 */     return s.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String infoString(EntityPersister persister, Object id, Type identifierType, SessionFactoryImplementor factory)
/*  61:    */   {
/*  62:134 */     StringBuffer s = new StringBuffer();
/*  63:135 */     s.append('[');
/*  64:136 */     if (persister == null) {
/*  65:137 */       s.append("<null EntityPersister>");
/*  66:    */     } else {
/*  67:140 */       s.append(persister.getEntityName());
/*  68:    */     }
/*  69:142 */     s.append('#');
/*  70:144 */     if (id == null) {
/*  71:145 */       s.append("<null>");
/*  72:    */     } else {
/*  73:148 */       s.append(identifierType.toLoggableString(id, factory));
/*  74:    */     }
/*  75:150 */     s.append(']');
/*  76:    */     
/*  77:152 */     return s.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String infoString(EntityPersister persister, Serializable[] ids, SessionFactoryImplementor factory)
/*  81:    */   {
/*  82:167 */     StringBuffer s = new StringBuffer();
/*  83:168 */     s.append('[');
/*  84:169 */     if (persister == null)
/*  85:    */     {
/*  86:170 */       s.append("<null EntityPersister>");
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:173 */       s.append(persister.getEntityName());
/*  91:174 */       s.append("#<");
/*  92:175 */       for (int i = 0; i < ids.length; i++)
/*  93:    */       {
/*  94:176 */         s.append(persister.getIdentifierType().toLoggableString(ids[i], factory));
/*  95:177 */         if (i < ids.length - 1) {
/*  96:178 */           s.append(", ");
/*  97:    */         }
/*  98:    */       }
/*  99:181 */       s.append('>');
/* 100:    */     }
/* 101:183 */     s.append(']');
/* 102:    */     
/* 103:185 */     return s.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static String infoString(EntityPersister persister)
/* 107:    */   {
/* 108:196 */     StringBuffer s = new StringBuffer();
/* 109:197 */     s.append('[');
/* 110:198 */     if (persister == null) {
/* 111:199 */       s.append("<null EntityPersister>");
/* 112:    */     } else {
/* 113:202 */       s.append(persister.getEntityName());
/* 114:    */     }
/* 115:204 */     s.append(']');
/* 116:205 */     return s.toString();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static String infoString(String entityName, String propertyName, Object key)
/* 120:    */   {
/* 121:218 */     StringBuffer s = new StringBuffer().append('[').append(entityName).append('.').append(propertyName).append('#');
/* 122:225 */     if (key == null) {
/* 123:226 */       s.append("<null>");
/* 124:    */     } else {
/* 125:229 */       s.append(key);
/* 126:    */     }
/* 127:231 */     s.append(']');
/* 128:232 */     return s.toString();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String collectionInfoString(CollectionPersister persister, Serializable[] ids, SessionFactoryImplementor factory)
/* 132:    */   {
/* 133:252 */     StringBuffer s = new StringBuffer();
/* 134:253 */     s.append('[');
/* 135:254 */     if (persister == null)
/* 136:    */     {
/* 137:255 */       s.append("<unreferenced>");
/* 138:    */     }
/* 139:    */     else
/* 140:    */     {
/* 141:258 */       s.append(persister.getRole());
/* 142:259 */       s.append("#<");
/* 143:260 */       for (int i = 0; i < ids.length; i++)
/* 144:    */       {
/* 145:265 */         s.append(persister.getOwnerEntityPersister().getIdentifierType().toLoggableString(ids[i], factory));
/* 146:266 */         if (i < ids.length - 1) {
/* 147:267 */           s.append(", ");
/* 148:    */         }
/* 149:    */       }
/* 150:270 */       s.append('>');
/* 151:    */     }
/* 152:272 */     s.append(']');
/* 153:273 */     return s.toString();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static String collectionInfoString(CollectionPersister persister, Serializable id, SessionFactoryImplementor factory)
/* 157:    */   {
/* 158:289 */     StringBuffer s = new StringBuffer();
/* 159:290 */     s.append('[');
/* 160:291 */     if (persister == null)
/* 161:    */     {
/* 162:292 */       s.append("<unreferenced>");
/* 163:    */     }
/* 164:    */     else
/* 165:    */     {
/* 166:295 */       s.append(persister.getRole());
/* 167:296 */       s.append('#');
/* 168:298 */       if (id == null) {
/* 169:299 */         s.append("<null>");
/* 170:    */       } else {
/* 171:306 */         s.append(persister.getOwnerEntityPersister().getIdentifierType().toLoggableString(id, factory));
/* 172:    */       }
/* 173:    */     }
/* 174:309 */     s.append(']');
/* 175:    */     
/* 176:311 */     return s.toString();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static String collectionInfoString(String role, Serializable id)
/* 180:    */   {
/* 181:323 */     StringBuffer s = new StringBuffer();
/* 182:324 */     s.append('[');
/* 183:325 */     if (role == null)
/* 184:    */     {
/* 185:326 */       s.append("<unreferenced>");
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:329 */       s.append(role);
/* 190:330 */       s.append('#');
/* 191:332 */       if (id == null) {
/* 192:333 */         s.append("<null>");
/* 193:    */       } else {
/* 194:336 */         s.append(id);
/* 195:    */       }
/* 196:    */     }
/* 197:339 */     s.append(']');
/* 198:340 */     return s.toString();
/* 199:    */   }
/* 200:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.pretty.MessageHelper
 * JD-Core Version:    0.7.0.1
 */