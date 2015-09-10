/*  1:   */ package org.dom4j.datatype;
/*  2:   */ 
/*  3:   */ import com.sun.msv.datatype.xsd.XSDatatype;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Set;
/*  8:   */ import org.dom4j.DocumentFactory;
/*  9:   */ import org.dom4j.Element;
/* 10:   */ import org.dom4j.QName;
/* 11:   */ 
/* 12:   */ class NamedTypeResolver
/* 13:   */ {
/* 14:29 */   protected Map complexTypeMap = new HashMap();
/* 15:31 */   protected Map simpleTypeMap = new HashMap();
/* 16:33 */   protected Map typedElementMap = new HashMap();
/* 17:35 */   protected Map elementFactoryMap = new HashMap();
/* 18:   */   protected DocumentFactory documentFactory;
/* 19:   */   
/* 20:   */   NamedTypeResolver(DocumentFactory documentFactory)
/* 21:   */   {
/* 22:40 */     this.documentFactory = documentFactory;
/* 23:   */   }
/* 24:   */   
/* 25:   */   void registerComplexType(QName type, DocumentFactory factory)
/* 26:   */   {
/* 27:44 */     this.complexTypeMap.put(type, factory);
/* 28:   */   }
/* 29:   */   
/* 30:   */   void registerSimpleType(QName type, XSDatatype datatype)
/* 31:   */   {
/* 32:48 */     this.simpleTypeMap.put(type, datatype);
/* 33:   */   }
/* 34:   */   
/* 35:   */   void registerTypedElement(Element element, QName type, DocumentFactory parentFactory)
/* 36:   */   {
/* 37:53 */     this.typedElementMap.put(element, type);
/* 38:54 */     this.elementFactoryMap.put(element, parentFactory);
/* 39:   */   }
/* 40:   */   
/* 41:   */   void resolveElementTypes()
/* 42:   */   {
/* 43:58 */     Iterator iterator = this.typedElementMap.keySet().iterator();
/* 44:60 */     while (iterator.hasNext())
/* 45:   */     {
/* 46:61 */       Element element = (Element)iterator.next();
/* 47:62 */       QName elementQName = getQNameOfSchemaElement(element);
/* 48:63 */       QName type = (QName)this.typedElementMap.get(element);
/* 49:65 */       if (this.complexTypeMap.containsKey(type))
/* 50:   */       {
/* 51:66 */         DocumentFactory factory = (DocumentFactory)this.complexTypeMap.get(type);
/* 52:   */         
/* 53:68 */         elementQName.setDocumentFactory(factory);
/* 54:   */       }
/* 55:69 */       else if (this.simpleTypeMap.containsKey(type))
/* 56:   */       {
/* 57:70 */         XSDatatype datatype = (XSDatatype)this.simpleTypeMap.get(type);
/* 58:71 */         DocumentFactory factory = (DocumentFactory)this.elementFactoryMap.get(element);
/* 59:74 */         if ((factory instanceof DatatypeElementFactory)) {
/* 60:75 */           ((DatatypeElementFactory)factory).setChildElementXSDatatype(elementQName, datatype);
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   void resolveNamedTypes()
/* 67:   */   {
/* 68:83 */     resolveElementTypes();
/* 69:   */   }
/* 70:   */   
/* 71:   */   private QName getQNameOfSchemaElement(Element element)
/* 72:   */   {
/* 73:87 */     String name = element.attributeValue("name");
/* 74:   */     
/* 75:89 */     return getQName(name);
/* 76:   */   }
/* 77:   */   
/* 78:   */   private QName getQName(String name)
/* 79:   */   {
/* 80:93 */     return this.documentFactory.createQName(name);
/* 81:   */   }
/* 82:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.NamedTypeResolver
 * JD-Core Version:    0.7.0.1
 */