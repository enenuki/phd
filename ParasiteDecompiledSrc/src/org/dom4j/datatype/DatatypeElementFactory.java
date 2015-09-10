/*   1:    */ package org.dom4j.datatype;
/*   2:    */ 
/*   3:    */ import com.sun.msv.datatype.xsd.XSDatatype;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.dom4j.Attribute;
/*   7:    */ import org.dom4j.DocumentFactory;
/*   8:    */ import org.dom4j.Element;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ 
/*  11:    */ public class DatatypeElementFactory
/*  12:    */   extends DocumentFactory
/*  13:    */ {
/*  14:    */   private QName elementQName;
/*  15: 37 */   private Map attributeXSDatatypes = new HashMap();
/*  16: 43 */   private Map childrenXSDatatypes = new HashMap();
/*  17:    */   
/*  18:    */   public DatatypeElementFactory(QName elementQName)
/*  19:    */   {
/*  20: 46 */     this.elementQName = elementQName;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public QName getQName()
/*  24:    */   {
/*  25: 55 */     return this.elementQName;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public XSDatatype getAttributeXSDatatype(QName attributeQName)
/*  29:    */   {
/*  30: 68 */     return (XSDatatype)this.attributeXSDatatypes.get(attributeQName);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setAttributeXSDatatype(QName attributeQName, XSDatatype type)
/*  34:    */   {
/*  35: 81 */     this.attributeXSDatatypes.put(attributeQName, type);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public XSDatatype getChildElementXSDatatype(QName qname)
/*  39:    */   {
/*  40: 94 */     return (XSDatatype)this.childrenXSDatatypes.get(qname);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setChildElementXSDatatype(QName qname, XSDatatype dataType)
/*  44:    */   {
/*  45: 98 */     this.childrenXSDatatypes.put(qname, dataType);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Element createElement(QName qname)
/*  49:    */   {
/*  50:106 */     XSDatatype dataType = getChildElementXSDatatype(qname);
/*  51:108 */     if (dataType != null) {
/*  52:109 */       return new DatatypeElement(qname, dataType);
/*  53:    */     }
/*  54:112 */     DocumentFactory factory = qname.getDocumentFactory();
/*  55:114 */     if ((factory instanceof DatatypeElementFactory))
/*  56:    */     {
/*  57:115 */       DatatypeElementFactory dtFactory = (DatatypeElementFactory)factory;
/*  58:116 */       dataType = dtFactory.getChildElementXSDatatype(qname);
/*  59:118 */       if (dataType != null) {
/*  60:119 */         return new DatatypeElement(qname, dataType);
/*  61:    */       }
/*  62:    */     }
/*  63:123 */     return super.createElement(qname);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Attribute createAttribute(Element owner, QName qname, String value)
/*  67:    */   {
/*  68:127 */     XSDatatype dataType = getAttributeXSDatatype(qname);
/*  69:129 */     if (dataType == null) {
/*  70:130 */       return super.createAttribute(owner, qname, value);
/*  71:    */     }
/*  72:132 */     return new DatatypeAttribute(qname, dataType, value);
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.DatatypeElementFactory
 * JD-Core Version:    0.7.0.1
 */