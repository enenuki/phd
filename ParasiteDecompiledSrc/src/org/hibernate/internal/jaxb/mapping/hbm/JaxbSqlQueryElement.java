/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   7:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   8:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   9:    */ import javax.xml.bind.annotation.XmlElementRefs;
/*  10:    */ import javax.xml.bind.annotation.XmlMixed;
/*  11:    */ import javax.xml.bind.annotation.XmlType;
/*  12:    */ 
/*  13:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  14:    */ @XmlType(name="sql-query-element", propOrder={"content"})
/*  15:    */ public class JaxbSqlQueryElement
/*  16:    */ {
/*  17:    */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="load-collection", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="synchronize", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="return", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="query-param", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="return-join", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="return-scalar", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=javax.xml.bind.JAXBElement.class)})
/*  18:    */   @XmlMixed
/*  19:    */   protected List<Serializable> content;
/*  20:    */   @XmlAttribute(name="cache-mode")
/*  21:    */   protected JaxbCacheModeAttribute cacheMode;
/*  22:    */   @XmlAttribute(name="cache-region")
/*  23:    */   protected String cacheRegion;
/*  24:    */   @XmlAttribute
/*  25:    */   protected Boolean cacheable;
/*  26:    */   @XmlAttribute
/*  27:    */   protected Boolean callable;
/*  28:    */   @XmlAttribute
/*  29:    */   protected String comment;
/*  30:    */   @XmlAttribute(name="fetch-size")
/*  31:    */   protected String fetchSize;
/*  32:    */   @XmlAttribute(name="flush-mode")
/*  33:    */   protected JaxbFlushModeAttribute flushMode;
/*  34:    */   @XmlAttribute(required=true)
/*  35:    */   protected String name;
/*  36:    */   @XmlAttribute(name="read-only")
/*  37:    */   protected Boolean readOnly;
/*  38:    */   @XmlAttribute(name="resultset-ref")
/*  39:    */   protected String resultsetRef;
/*  40:    */   @XmlAttribute
/*  41:    */   protected String timeout;
/*  42:    */   
/*  43:    */   public List<Serializable> getContent()
/*  44:    */   {
/*  45:127 */     if (this.content == null) {
/*  46:128 */       this.content = new ArrayList();
/*  47:    */     }
/*  48:130 */     return this.content;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public JaxbCacheModeAttribute getCacheMode()
/*  52:    */   {
/*  53:142 */     return this.cacheMode;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setCacheMode(JaxbCacheModeAttribute value)
/*  57:    */   {
/*  58:154 */     this.cacheMode = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getCacheRegion()
/*  62:    */   {
/*  63:166 */     return this.cacheRegion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setCacheRegion(String value)
/*  67:    */   {
/*  68:178 */     this.cacheRegion = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isCacheable()
/*  72:    */   {
/*  73:190 */     if (this.cacheable == null) {
/*  74:191 */       return false;
/*  75:    */     }
/*  76:193 */     return this.cacheable.booleanValue();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setCacheable(Boolean value)
/*  80:    */   {
/*  81:206 */     this.cacheable = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isCallable()
/*  85:    */   {
/*  86:218 */     if (this.callable == null) {
/*  87:219 */       return false;
/*  88:    */     }
/*  89:221 */     return this.callable.booleanValue();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCallable(Boolean value)
/*  93:    */   {
/*  94:234 */     this.callable = value;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getComment()
/*  98:    */   {
/*  99:246 */     return this.comment;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setComment(String value)
/* 103:    */   {
/* 104:258 */     this.comment = value;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getFetchSize()
/* 108:    */   {
/* 109:270 */     return this.fetchSize;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setFetchSize(String value)
/* 113:    */   {
/* 114:282 */     this.fetchSize = value;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public JaxbFlushModeAttribute getFlushMode()
/* 118:    */   {
/* 119:294 */     return this.flushMode;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFlushMode(JaxbFlushModeAttribute value)
/* 123:    */   {
/* 124:306 */     this.flushMode = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getName()
/* 128:    */   {
/* 129:318 */     return this.name;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setName(String value)
/* 133:    */   {
/* 134:330 */     this.name = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Boolean isReadOnly()
/* 138:    */   {
/* 139:342 */     return this.readOnly;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setReadOnly(Boolean value)
/* 143:    */   {
/* 144:354 */     this.readOnly = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getResultsetRef()
/* 148:    */   {
/* 149:366 */     return this.resultsetRef;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setResultsetRef(String value)
/* 153:    */   {
/* 154:378 */     this.resultsetRef = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getTimeout()
/* 158:    */   {
/* 159:390 */     return this.timeout;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setTimeout(String value)
/* 163:    */   {
/* 164:402 */     this.timeout = value;
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbSqlQueryElement
 * JD-Core Version:    0.7.0.1
 */