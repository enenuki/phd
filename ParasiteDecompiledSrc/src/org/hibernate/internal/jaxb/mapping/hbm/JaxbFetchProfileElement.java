/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlType;
/*   9:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*  10:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*  11:    */ 
/*  12:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  13:    */ @XmlType(name="fetch-profile-element", propOrder={"fetch"})
/*  14:    */ public class JaxbFetchProfileElement
/*  15:    */ {
/*  16:    */   protected List<JaxbFetch> fetch;
/*  17:    */   @XmlAttribute(required=true)
/*  18:    */   protected String name;
/*  19:    */   
/*  20:    */   public List<JaxbFetch> getFetch()
/*  21:    */   {
/*  22: 91 */     if (this.fetch == null) {
/*  23: 92 */       this.fetch = new ArrayList();
/*  24:    */     }
/*  25: 94 */     return this.fetch;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30:106 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setName(String value)
/*  34:    */   {
/*  35:118 */     this.name = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   @XmlAccessorType(XmlAccessType.FIELD)
/*  39:    */   @XmlType(name="")
/*  40:    */   public static class JaxbFetch
/*  41:    */   {
/*  42:    */     @XmlAttribute(required=true)
/*  43:    */     protected String association;
/*  44:    */     @XmlAttribute
/*  45:    */     protected String entity;
/*  46:    */     @XmlAttribute
/*  47:    */     @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  48:    */     protected String style;
/*  49:    */     
/*  50:    */     public String getAssociation()
/*  51:    */     {
/*  52:169 */       return this.association;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public void setAssociation(String value)
/*  56:    */     {
/*  57:181 */       this.association = value;
/*  58:    */     }
/*  59:    */     
/*  60:    */     public String getEntity()
/*  61:    */     {
/*  62:193 */       return this.entity;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public void setEntity(String value)
/*  66:    */     {
/*  67:205 */       this.entity = value;
/*  68:    */     }
/*  69:    */     
/*  70:    */     public String getStyle()
/*  71:    */     {
/*  72:217 */       if (this.style == null) {
/*  73:218 */         return "join";
/*  74:    */       }
/*  75:220 */       return this.style;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public void setStyle(String value)
/*  79:    */     {
/*  80:233 */       this.style = value;
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement
 * JD-Core Version:    0.7.0.1
 */