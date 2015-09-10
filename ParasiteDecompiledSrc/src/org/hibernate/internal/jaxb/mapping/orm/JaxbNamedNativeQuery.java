/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="named-native-query", propOrder={"description", "query", "hint"})
/*  13:    */ public class JaxbNamedNativeQuery
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   @XmlElement(required=true)
/*  17:    */   protected String query;
/*  18:    */   protected List<JaxbQueryHint> hint;
/*  19:    */   @XmlAttribute(required=true)
/*  20:    */   protected String name;
/*  21:    */   @XmlAttribute(name="result-class")
/*  22:    */   protected String resultClass;
/*  23:    */   @XmlAttribute(name="result-set-mapping")
/*  24:    */   protected String resultSetMapping;
/*  25:    */   
/*  26:    */   public String getDescription()
/*  27:    */   {
/*  28: 80 */     return this.description;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setDescription(String value)
/*  32:    */   {
/*  33: 92 */     this.description = value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getQuery()
/*  37:    */   {
/*  38:104 */     return this.query;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setQuery(String value)
/*  42:    */   {
/*  43:116 */     this.query = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<JaxbQueryHint> getHint()
/*  47:    */   {
/*  48:142 */     if (this.hint == null) {
/*  49:143 */       this.hint = new ArrayList();
/*  50:    */     }
/*  51:145 */     return this.hint;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getName()
/*  55:    */   {
/*  56:157 */     return this.name;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setName(String value)
/*  60:    */   {
/*  61:169 */     this.name = value;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getResultClass()
/*  65:    */   {
/*  66:181 */     return this.resultClass;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setResultClass(String value)
/*  70:    */   {
/*  71:193 */     this.resultClass = value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getResultSetMapping()
/*  75:    */   {
/*  76:205 */     return this.resultSetMapping;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setResultSetMapping(String value)
/*  80:    */   {
/*  81:217 */     this.resultSetMapping = value;
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbNamedNativeQuery
 * JD-Core Version:    0.7.0.1
 */