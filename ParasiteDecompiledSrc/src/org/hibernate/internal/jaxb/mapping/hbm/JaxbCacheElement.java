/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*   8:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*   9:    */ 
/*  10:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  11:    */ @XmlType(name="cache-element")
/*  12:    */ public class JaxbCacheElement
/*  13:    */ {
/*  14:    */   @XmlAttribute
/*  15:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  16:    */   protected String include;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String region;
/*  19:    */   @XmlAttribute(required=true)
/*  20:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  21:    */   protected String usage;
/*  22:    */   
/*  23:    */   public String getInclude()
/*  24:    */   {
/*  25: 76 */     if (this.include == null) {
/*  26: 77 */       return "all";
/*  27:    */     }
/*  28: 79 */     return this.include;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setInclude(String value)
/*  32:    */   {
/*  33: 92 */     this.include = value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getRegion()
/*  37:    */   {
/*  38:104 */     return this.region;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setRegion(String value)
/*  42:    */   {
/*  43:116 */     this.region = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getUsage()
/*  47:    */   {
/*  48:128 */     return this.usage;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setUsage(String value)
/*  52:    */   {
/*  53:140 */     this.usage = value;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbCacheElement
 * JD-Core Version:    0.7.0.1
 */