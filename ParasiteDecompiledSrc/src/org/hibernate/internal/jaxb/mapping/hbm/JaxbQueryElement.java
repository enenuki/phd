/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.xml.bind.JAXBElement;
/*   7:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   8:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   9:    */ import javax.xml.bind.annotation.XmlAttribute;
/*  10:    */ import javax.xml.bind.annotation.XmlElementRef;
/*  11:    */ import javax.xml.bind.annotation.XmlMixed;
/*  12:    */ import javax.xml.bind.annotation.XmlType;
/*  13:    */ 
/*  14:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  15:    */ @XmlType(name="query-element", propOrder={"content"})
/*  16:    */ public class JaxbQueryElement
/*  17:    */ {
/*  18:    */   @XmlElementRef(name="query-param", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=JAXBElement.class)
/*  19:    */   @XmlMixed
/*  20:    */   protected List<Serializable> content;
/*  21:    */   @XmlAttribute(name="cache-mode")
/*  22:    */   protected JaxbCacheModeAttribute cacheMode;
/*  23:    */   @XmlAttribute(name="cache-region")
/*  24:    */   protected String cacheRegion;
/*  25:    */   @XmlAttribute
/*  26:    */   protected Boolean cacheable;
/*  27:    */   @XmlAttribute
/*  28:    */   protected String comment;
/*  29:    */   @XmlAttribute(name="fetch-size")
/*  30:    */   protected String fetchSize;
/*  31:    */   @XmlAttribute(name="flush-mode")
/*  32:    */   protected JaxbFlushModeAttribute flushMode;
/*  33:    */   @XmlAttribute(required=true)
/*  34:    */   protected String name;
/*  35:    */   @XmlAttribute(name="read-only")
/*  36:    */   protected Boolean readOnly;
/*  37:    */   @XmlAttribute
/*  38:    */   protected String timeout;
/*  39:    */   
/*  40:    */   public List<Serializable> getContent()
/*  41:    */   {
/*  42:103 */     if (this.content == null) {
/*  43:104 */       this.content = new ArrayList();
/*  44:    */     }
/*  45:106 */     return this.content;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public JaxbCacheModeAttribute getCacheMode()
/*  49:    */   {
/*  50:118 */     return this.cacheMode;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setCacheMode(JaxbCacheModeAttribute value)
/*  54:    */   {
/*  55:130 */     this.cacheMode = value;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getCacheRegion()
/*  59:    */   {
/*  60:142 */     return this.cacheRegion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setCacheRegion(String value)
/*  64:    */   {
/*  65:154 */     this.cacheRegion = value;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isCacheable()
/*  69:    */   {
/*  70:166 */     if (this.cacheable == null) {
/*  71:167 */       return false;
/*  72:    */     }
/*  73:169 */     return this.cacheable.booleanValue();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCacheable(Boolean value)
/*  77:    */   {
/*  78:182 */     this.cacheable = value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getComment()
/*  82:    */   {
/*  83:194 */     return this.comment;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setComment(String value)
/*  87:    */   {
/*  88:206 */     this.comment = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getFetchSize()
/*  92:    */   {
/*  93:218 */     return this.fetchSize;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setFetchSize(String value)
/*  97:    */   {
/*  98:230 */     this.fetchSize = value;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JaxbFlushModeAttribute getFlushMode()
/* 102:    */   {
/* 103:242 */     return this.flushMode;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setFlushMode(JaxbFlushModeAttribute value)
/* 107:    */   {
/* 108:254 */     this.flushMode = value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getName()
/* 112:    */   {
/* 113:266 */     return this.name;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setName(String value)
/* 117:    */   {
/* 118:278 */     this.name = value;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Boolean isReadOnly()
/* 122:    */   {
/* 123:290 */     return this.readOnly;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setReadOnly(Boolean value)
/* 127:    */   {
/* 128:302 */     this.readOnly = value;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getTimeout()
/* 132:    */   {
/* 133:314 */     return this.timeout;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setTimeout(String value)
/* 137:    */   {
/* 138:326 */     this.timeout = value;
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbQueryElement
 * JD-Core Version:    0.7.0.1
 */