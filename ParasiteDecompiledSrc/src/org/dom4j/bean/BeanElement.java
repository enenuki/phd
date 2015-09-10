/*   1:    */ package org.dom4j.bean;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.DocumentFactory;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Namespace;
/*   8:    */ import org.dom4j.QName;
/*   9:    */ import org.dom4j.tree.DefaultElement;
/*  10:    */ import org.dom4j.tree.NamespaceStack;
/*  11:    */ import org.xml.sax.Attributes;
/*  12:    */ 
/*  13:    */ public class BeanElement
/*  14:    */   extends DefaultElement
/*  15:    */ {
/*  16: 32 */   private static final DocumentFactory DOCUMENT_FACTORY = ;
/*  17:    */   private Object bean;
/*  18:    */   
/*  19:    */   public BeanElement(String name, Object bean)
/*  20:    */   {
/*  21: 39 */     this(DOCUMENT_FACTORY.createQName(name), bean);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public BeanElement(String name, Namespace namespace, Object bean)
/*  25:    */   {
/*  26: 43 */     this(DOCUMENT_FACTORY.createQName(name, namespace), bean);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BeanElement(QName qname, Object bean)
/*  30:    */   {
/*  31: 47 */     super(qname);
/*  32: 48 */     this.bean = bean;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public BeanElement(QName qname)
/*  36:    */   {
/*  37: 52 */     super(qname);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object getData()
/*  41:    */   {
/*  42: 61 */     return this.bean;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setData(Object data)
/*  46:    */   {
/*  47: 65 */     this.bean = data;
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 70 */     setAttributeList(null);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Attribute attribute(String name)
/*  56:    */   {
/*  57: 74 */     return getBeanAttributeList().attribute(name);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Attribute attribute(QName qname)
/*  61:    */   {
/*  62: 78 */     return getBeanAttributeList().attribute(qname);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Element addAttribute(String name, String value)
/*  66:    */   {
/*  67: 82 */     Attribute attribute = attribute(name);
/*  68: 84 */     if (attribute != null) {
/*  69: 85 */       attribute.setValue(value);
/*  70:    */     }
/*  71: 88 */     return this;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Element addAttribute(QName qName, String value)
/*  75:    */   {
/*  76: 92 */     Attribute attribute = attribute(qName);
/*  77: 94 */     if (attribute != null) {
/*  78: 95 */       attribute.setValue(value);
/*  79:    */     }
/*  80: 98 */     return this;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setAttributes(List attributes)
/*  84:    */   {
/*  85:102 */     throw new UnsupportedOperationException("Not implemented yet.");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setAttributes(Attributes attributes, NamespaceStack namespaceStack, boolean noNamespaceAttributes)
/*  89:    */   {
/*  90:108 */     String className = attributes.getValue("class");
/*  91:110 */     if (className != null) {
/*  92:    */       try
/*  93:    */       {
/*  94:112 */         Class beanClass = Class.forName(className, true, BeanElement.class.getClassLoader());
/*  95:    */         
/*  96:114 */         setData(beanClass.newInstance());
/*  97:116 */         for (int i = 0; i < attributes.getLength(); i++)
/*  98:    */         {
/*  99:117 */           String attributeName = attributes.getLocalName(i);
/* 100:119 */           if (!"class".equalsIgnoreCase(attributeName)) {
/* 101:120 */             addAttribute(attributeName, attributes.getValue(i));
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */       catch (Exception ex)
/* 106:    */       {
/* 107:125 */         ((BeanDocumentFactory)getDocumentFactory()).handleException(ex);
/* 108:    */       }
/* 109:    */     } else {
/* 110:129 */       super.setAttributes(attributes, namespaceStack, noNamespaceAttributes);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected DocumentFactory getDocumentFactory()
/* 115:    */   {
/* 116:137 */     return DOCUMENT_FACTORY;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected BeanAttributeList getBeanAttributeList()
/* 120:    */   {
/* 121:141 */     return (BeanAttributeList)attributeList();
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected List createAttributeList()
/* 125:    */   {
/* 126:151 */     return new BeanAttributeList(this);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected List createAttributeList(int size)
/* 130:    */   {
/* 131:155 */     return new BeanAttributeList(this);
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.bean.BeanElement
 * JD-Core Version:    0.7.0.1
 */