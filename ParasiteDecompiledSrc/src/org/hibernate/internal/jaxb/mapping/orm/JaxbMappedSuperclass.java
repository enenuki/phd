/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlElement;
/*   7:    */ import javax.xml.bind.annotation.XmlType;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="mapped-superclass", propOrder={"description", "idClass", "excludeDefaultListeners", "excludeSuperclassListeners", "entityListeners", "prePersist", "postPersist", "preRemove", "postRemove", "preUpdate", "postUpdate", "postLoad", "attributes"})
/*  11:    */ public class JaxbMappedSuperclass
/*  12:    */ {
/*  13:    */   protected String description;
/*  14:    */   @XmlElement(name="id-class")
/*  15:    */   protected JaxbIdClass idClass;
/*  16:    */   @XmlElement(name="exclude-default-listeners")
/*  17:    */   protected JaxbEmptyType excludeDefaultListeners;
/*  18:    */   @XmlElement(name="exclude-superclass-listeners")
/*  19:    */   protected JaxbEmptyType excludeSuperclassListeners;
/*  20:    */   @XmlElement(name="entity-listeners")
/*  21:    */   protected JaxbEntityListeners entityListeners;
/*  22:    */   @XmlElement(name="pre-persist")
/*  23:    */   protected JaxbPrePersist prePersist;
/*  24:    */   @XmlElement(name="post-persist")
/*  25:    */   protected JaxbPostPersist postPersist;
/*  26:    */   @XmlElement(name="pre-remove")
/*  27:    */   protected JaxbPreRemove preRemove;
/*  28:    */   @XmlElement(name="post-remove")
/*  29:    */   protected JaxbPostRemove postRemove;
/*  30:    */   @XmlElement(name="pre-update")
/*  31:    */   protected JaxbPreUpdate preUpdate;
/*  32:    */   @XmlElement(name="post-update")
/*  33:    */   protected JaxbPostUpdate postUpdate;
/*  34:    */   @XmlElement(name="post-load")
/*  35:    */   protected JaxbPostLoad postLoad;
/*  36:    */   protected JaxbAttributes attributes;
/*  37:    */   @XmlAttribute(name="class", required=true)
/*  38:    */   protected String clazz;
/*  39:    */   @XmlAttribute
/*  40:    */   protected JaxbAccessType access;
/*  41:    */   @XmlAttribute(name="metadata-complete")
/*  42:    */   protected Boolean metadataComplete;
/*  43:    */   
/*  44:    */   public String getDescription()
/*  45:    */   {
/*  46:120 */     return this.description;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setDescription(String value)
/*  50:    */   {
/*  51:132 */     this.description = value;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public JaxbIdClass getIdClass()
/*  55:    */   {
/*  56:144 */     return this.idClass;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdClass(JaxbIdClass value)
/*  60:    */   {
/*  61:156 */     this.idClass = value;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public JaxbEmptyType getExcludeDefaultListeners()
/*  65:    */   {
/*  66:168 */     return this.excludeDefaultListeners;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setExcludeDefaultListeners(JaxbEmptyType value)
/*  70:    */   {
/*  71:180 */     this.excludeDefaultListeners = value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public JaxbEmptyType getExcludeSuperclassListeners()
/*  75:    */   {
/*  76:192 */     return this.excludeSuperclassListeners;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setExcludeSuperclassListeners(JaxbEmptyType value)
/*  80:    */   {
/*  81:204 */     this.excludeSuperclassListeners = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public JaxbEntityListeners getEntityListeners()
/*  85:    */   {
/*  86:216 */     return this.entityListeners;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setEntityListeners(JaxbEntityListeners value)
/*  90:    */   {
/*  91:228 */     this.entityListeners = value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public JaxbPrePersist getPrePersist()
/*  95:    */   {
/*  96:240 */     return this.prePersist;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setPrePersist(JaxbPrePersist value)
/* 100:    */   {
/* 101:252 */     this.prePersist = value;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public JaxbPostPersist getPostPersist()
/* 105:    */   {
/* 106:264 */     return this.postPersist;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setPostPersist(JaxbPostPersist value)
/* 110:    */   {
/* 111:276 */     this.postPersist = value;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public JaxbPreRemove getPreRemove()
/* 115:    */   {
/* 116:288 */     return this.preRemove;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setPreRemove(JaxbPreRemove value)
/* 120:    */   {
/* 121:300 */     this.preRemove = value;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public JaxbPostRemove getPostRemove()
/* 125:    */   {
/* 126:312 */     return this.postRemove;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPostRemove(JaxbPostRemove value)
/* 130:    */   {
/* 131:324 */     this.postRemove = value;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public JaxbPreUpdate getPreUpdate()
/* 135:    */   {
/* 136:336 */     return this.preUpdate;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setPreUpdate(JaxbPreUpdate value)
/* 140:    */   {
/* 141:348 */     this.preUpdate = value;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public JaxbPostUpdate getPostUpdate()
/* 145:    */   {
/* 146:360 */     return this.postUpdate;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setPostUpdate(JaxbPostUpdate value)
/* 150:    */   {
/* 151:372 */     this.postUpdate = value;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public JaxbPostLoad getPostLoad()
/* 155:    */   {
/* 156:384 */     return this.postLoad;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setPostLoad(JaxbPostLoad value)
/* 160:    */   {
/* 161:396 */     this.postLoad = value;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public JaxbAttributes getAttributes()
/* 165:    */   {
/* 166:408 */     return this.attributes;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setAttributes(JaxbAttributes value)
/* 170:    */   {
/* 171:420 */     this.attributes = value;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getClazz()
/* 175:    */   {
/* 176:432 */     return this.clazz;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setClazz(String value)
/* 180:    */   {
/* 181:444 */     this.clazz = value;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public JaxbAccessType getAccess()
/* 185:    */   {
/* 186:456 */     return this.access;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setAccess(JaxbAccessType value)
/* 190:    */   {
/* 191:468 */     this.access = value;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Boolean isMetadataComplete()
/* 195:    */   {
/* 196:480 */     return this.metadataComplete;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setMetadataComplete(Boolean value)
/* 200:    */   {
/* 201:492 */     this.metadataComplete = value;
/* 202:    */   }
/* 203:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbMappedSuperclass
 * JD-Core Version:    0.7.0.1
 */