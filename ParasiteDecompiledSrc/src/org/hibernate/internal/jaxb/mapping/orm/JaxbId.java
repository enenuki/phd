/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlElement;
/*   7:    */ import javax.xml.bind.annotation.XmlType;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="id", propOrder={"column", "generatedValue", "temporal", "tableGenerator", "sequenceGenerator"})
/*  11:    */ public class JaxbId
/*  12:    */ {
/*  13:    */   protected JaxbColumn column;
/*  14:    */   @XmlElement(name="generated-value")
/*  15:    */   protected JaxbGeneratedValue generatedValue;
/*  16:    */   protected JaxbTemporalType temporal;
/*  17:    */   @XmlElement(name="table-generator")
/*  18:    */   protected JaxbTableGenerator tableGenerator;
/*  19:    */   @XmlElement(name="sequence-generator")
/*  20:    */   protected JaxbSequenceGenerator sequenceGenerator;
/*  21:    */   @XmlAttribute(required=true)
/*  22:    */   protected String name;
/*  23:    */   @XmlAttribute
/*  24:    */   protected JaxbAccessType access;
/*  25:    */   
/*  26:    */   public JaxbColumn getColumn()
/*  27:    */   {
/*  28: 79 */     return this.column;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setColumn(JaxbColumn value)
/*  32:    */   {
/*  33: 91 */     this.column = value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public JaxbGeneratedValue getGeneratedValue()
/*  37:    */   {
/*  38:103 */     return this.generatedValue;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setGeneratedValue(JaxbGeneratedValue value)
/*  42:    */   {
/*  43:115 */     this.generatedValue = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public JaxbTemporalType getTemporal()
/*  47:    */   {
/*  48:127 */     return this.temporal;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setTemporal(JaxbTemporalType value)
/*  52:    */   {
/*  53:139 */     this.temporal = value;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public JaxbTableGenerator getTableGenerator()
/*  57:    */   {
/*  58:151 */     return this.tableGenerator;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setTableGenerator(JaxbTableGenerator value)
/*  62:    */   {
/*  63:163 */     this.tableGenerator = value;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public JaxbSequenceGenerator getSequenceGenerator()
/*  67:    */   {
/*  68:175 */     return this.sequenceGenerator;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setSequenceGenerator(JaxbSequenceGenerator value)
/*  72:    */   {
/*  73:187 */     this.sequenceGenerator = value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getName()
/*  77:    */   {
/*  78:199 */     return this.name;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setName(String value)
/*  82:    */   {
/*  83:211 */     this.name = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public JaxbAccessType getAccess()
/*  87:    */   {
/*  88:223 */     return this.access;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setAccess(JaxbAccessType value)
/*  92:    */   {
/*  93:235 */     this.access = value;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbId
 * JD-Core Version:    0.7.0.1
 */