/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlElement;
/*   7:    */ import javax.xml.bind.annotation.XmlType;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="entity-listener", propOrder={"description", "prePersist", "postPersist", "preRemove", "postRemove", "preUpdate", "postUpdate", "postLoad"})
/*  11:    */ public class JaxbEntityListener
/*  12:    */ {
/*  13:    */   protected String description;
/*  14:    */   @XmlElement(name="pre-persist")
/*  15:    */   protected JaxbPrePersist prePersist;
/*  16:    */   @XmlElement(name="post-persist")
/*  17:    */   protected JaxbPostPersist postPersist;
/*  18:    */   @XmlElement(name="pre-remove")
/*  19:    */   protected JaxbPreRemove preRemove;
/*  20:    */   @XmlElement(name="post-remove")
/*  21:    */   protected JaxbPostRemove postRemove;
/*  22:    */   @XmlElement(name="pre-update")
/*  23:    */   protected JaxbPreUpdate preUpdate;
/*  24:    */   @XmlElement(name="post-update")
/*  25:    */   protected JaxbPostUpdate postUpdate;
/*  26:    */   @XmlElement(name="post-load")
/*  27:    */   protected JaxbPostLoad postLoad;
/*  28:    */   @XmlAttribute(name="class", required=true)
/*  29:    */   protected String clazz;
/*  30:    */   
/*  31:    */   public String getDescription()
/*  32:    */   {
/*  33: 89 */     return this.description;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setDescription(String value)
/*  37:    */   {
/*  38:101 */     this.description = value;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public JaxbPrePersist getPrePersist()
/*  42:    */   {
/*  43:113 */     return this.prePersist;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setPrePersist(JaxbPrePersist value)
/*  47:    */   {
/*  48:125 */     this.prePersist = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public JaxbPostPersist getPostPersist()
/*  52:    */   {
/*  53:137 */     return this.postPersist;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setPostPersist(JaxbPostPersist value)
/*  57:    */   {
/*  58:149 */     this.postPersist = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public JaxbPreRemove getPreRemove()
/*  62:    */   {
/*  63:161 */     return this.preRemove;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setPreRemove(JaxbPreRemove value)
/*  67:    */   {
/*  68:173 */     this.preRemove = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public JaxbPostRemove getPostRemove()
/*  72:    */   {
/*  73:185 */     return this.postRemove;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setPostRemove(JaxbPostRemove value)
/*  77:    */   {
/*  78:197 */     this.postRemove = value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public JaxbPreUpdate getPreUpdate()
/*  82:    */   {
/*  83:209 */     return this.preUpdate;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setPreUpdate(JaxbPreUpdate value)
/*  87:    */   {
/*  88:221 */     this.preUpdate = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public JaxbPostUpdate getPostUpdate()
/*  92:    */   {
/*  93:233 */     return this.postUpdate;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setPostUpdate(JaxbPostUpdate value)
/*  97:    */   {
/*  98:245 */     this.postUpdate = value;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JaxbPostLoad getPostLoad()
/* 102:    */   {
/* 103:257 */     return this.postLoad;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setPostLoad(JaxbPostLoad value)
/* 107:    */   {
/* 108:269 */     this.postLoad = value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getClazz()
/* 112:    */   {
/* 113:281 */     return this.clazz;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setClazz(String value)
/* 117:    */   {
/* 118:293 */     this.clazz = value;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListener
 * JD-Core Version:    0.7.0.1
 */