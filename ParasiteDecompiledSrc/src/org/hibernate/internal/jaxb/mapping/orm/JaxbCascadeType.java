/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="cascade-type", propOrder={"cascadeAll", "cascadePersist", "cascadeMerge", "cascadeRemove", "cascadeRefresh", "cascadeDetach"})
/*  10:    */ public class JaxbCascadeType
/*  11:    */ {
/*  12:    */   @XmlElement(name="cascade-all")
/*  13:    */   protected JaxbEmptyType cascadeAll;
/*  14:    */   @XmlElement(name="cascade-persist")
/*  15:    */   protected JaxbEmptyType cascadePersist;
/*  16:    */   @XmlElement(name="cascade-merge")
/*  17:    */   protected JaxbEmptyType cascadeMerge;
/*  18:    */   @XmlElement(name="cascade-remove")
/*  19:    */   protected JaxbEmptyType cascadeRemove;
/*  20:    */   @XmlElement(name="cascade-refresh")
/*  21:    */   protected JaxbEmptyType cascadeRefresh;
/*  22:    */   @XmlElement(name="cascade-detach")
/*  23:    */   protected JaxbEmptyType cascadeDetach;
/*  24:    */   
/*  25:    */   public JaxbEmptyType getCascadeAll()
/*  26:    */   {
/*  27: 78 */     return this.cascadeAll;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setCascadeAll(JaxbEmptyType value)
/*  31:    */   {
/*  32: 90 */     this.cascadeAll = value;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public JaxbEmptyType getCascadePersist()
/*  36:    */   {
/*  37:102 */     return this.cascadePersist;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setCascadePersist(JaxbEmptyType value)
/*  41:    */   {
/*  42:114 */     this.cascadePersist = value;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public JaxbEmptyType getCascadeMerge()
/*  46:    */   {
/*  47:126 */     return this.cascadeMerge;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setCascadeMerge(JaxbEmptyType value)
/*  51:    */   {
/*  52:138 */     this.cascadeMerge = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JaxbEmptyType getCascadeRemove()
/*  56:    */   {
/*  57:150 */     return this.cascadeRemove;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setCascadeRemove(JaxbEmptyType value)
/*  61:    */   {
/*  62:162 */     this.cascadeRemove = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public JaxbEmptyType getCascadeRefresh()
/*  66:    */   {
/*  67:174 */     return this.cascadeRefresh;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setCascadeRefresh(JaxbEmptyType value)
/*  71:    */   {
/*  72:186 */     this.cascadeRefresh = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public JaxbEmptyType getCascadeDetach()
/*  76:    */   {
/*  77:198 */     return this.cascadeDetach;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setCascadeDetach(JaxbEmptyType value)
/*  81:    */   {
/*  82:210 */     this.cascadeDetach = value;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbCascadeType
 * JD-Core Version:    0.7.0.1
 */