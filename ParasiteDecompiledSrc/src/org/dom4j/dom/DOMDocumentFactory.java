/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.CDATA;
/*   6:    */ import org.dom4j.Comment;
/*   7:    */ import org.dom4j.DocumentFactory;
/*   8:    */ import org.dom4j.Element;
/*   9:    */ import org.dom4j.Entity;
/*  10:    */ import org.dom4j.Namespace;
/*  11:    */ import org.dom4j.ProcessingInstruction;
/*  12:    */ import org.dom4j.QName;
/*  13:    */ import org.dom4j.Text;
/*  14:    */ import org.dom4j.util.SingletonStrategy;
/*  15:    */ import org.w3c.dom.DOMException;
/*  16:    */ import org.w3c.dom.DOMImplementation;
/*  17:    */ 
/*  18:    */ public class DOMDocumentFactory
/*  19:    */   extends DocumentFactory
/*  20:    */   implements DOMImplementation
/*  21:    */ {
/*  22: 40 */   private static SingletonStrategy singleton = null;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26:    */     try
/*  27:    */     {
/*  28: 44 */       String defaultSingletonClass = "org.dom4j.util.SimpleSingleton";
/*  29: 45 */       Class clazz = null;
/*  30:    */       try
/*  31:    */       {
/*  32: 47 */         String singletonClass = defaultSingletonClass;
/*  33: 48 */         singletonClass = System.getProperty("org.dom4j.dom.DOMDocumentFactory.singleton.strategy", singletonClass);
/*  34:    */         
/*  35:    */ 
/*  36: 51 */         clazz = Class.forName(singletonClass);
/*  37:    */       }
/*  38:    */       catch (Exception exc1)
/*  39:    */       {
/*  40:    */         try
/*  41:    */         {
/*  42: 54 */           String singletonClass = defaultSingletonClass;
/*  43: 55 */           clazz = Class.forName(singletonClass);
/*  44:    */         }
/*  45:    */         catch (Exception exc2) {}
/*  46:    */       }
/*  47: 59 */       singleton = (SingletonStrategy)clazz.newInstance();
/*  48: 60 */       singleton.setSingletonClassName(DOMDocumentFactory.class.getName());
/*  49:    */     }
/*  50:    */     catch (Exception exc3) {}
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static DocumentFactory getInstance()
/*  54:    */   {
/*  55: 73 */     DOMDocumentFactory fact = (DOMDocumentFactory)singleton.instance();
/*  56: 74 */     return fact;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public org.dom4j.Document createDocument()
/*  60:    */   {
/*  61: 79 */     DOMDocument answer = new DOMDocument();
/*  62: 80 */     answer.setDocumentFactory(this);
/*  63:    */     
/*  64: 82 */     return answer;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public org.dom4j.DocumentType createDocType(String name, String publicId, String systemId)
/*  68:    */   {
/*  69: 87 */     return new DOMDocumentType(name, publicId, systemId);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Element createElement(QName qname)
/*  73:    */   {
/*  74: 91 */     return new DOMElement(qname);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Element createElement(QName qname, int attributeCount)
/*  78:    */   {
/*  79: 95 */     return new DOMElement(qname, attributeCount);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Attribute createAttribute(Element owner, QName qname, String value)
/*  83:    */   {
/*  84: 99 */     return new DOMAttribute(qname, value);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public CDATA createCDATA(String text)
/*  88:    */   {
/*  89:103 */     return new DOMCDATA(text);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Comment createComment(String text)
/*  93:    */   {
/*  94:107 */     return new DOMComment(text);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Text createText(String text)
/*  98:    */   {
/*  99:111 */     return new DOMText(text);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Entity createEntity(String name)
/* 103:    */   {
/* 104:115 */     return new DOMEntityReference(name);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Entity createEntity(String name, String text)
/* 108:    */   {
/* 109:119 */     return new DOMEntityReference(name, text);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Namespace createNamespace(String prefix, String uri)
/* 113:    */   {
/* 114:123 */     return new DOMNamespace(prefix, uri);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/* 118:    */   {
/* 119:128 */     return new DOMProcessingInstruction(target, data);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public ProcessingInstruction createProcessingInstruction(String target, Map data)
/* 123:    */   {
/* 124:133 */     return new DOMProcessingInstruction(target, data);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean hasFeature(String feat, String version)
/* 128:    */   {
/* 129:138 */     if (("XML".equalsIgnoreCase(feat)) || ("Core".equalsIgnoreCase(feat))) {
/* 130:139 */       return (version == null) || (version.length() == 0) || ("1.0".equals(version)) || ("2.0".equals(version));
/* 131:    */     }
/* 132:143 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public org.w3c.dom.DocumentType createDocumentType(String qualifiedName, String publicId, String systemId)
/* 136:    */     throws DOMException
/* 137:    */   {
/* 138:148 */     return new DOMDocumentType(qualifiedName, publicId, systemId);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public org.w3c.dom.Document createDocument(String namespaceURI, String qualifiedName, org.w3c.dom.DocumentType docType)
/* 142:    */     throws DOMException
/* 143:    */   {
/* 144:    */     DOMDocument document;
/* 145:    */     DOMDocument document;
/* 146:156 */     if (docType != null)
/* 147:    */     {
/* 148:157 */       DOMDocumentType documentType = asDocumentType(docType);
/* 149:158 */       document = new DOMDocument(documentType);
/* 150:    */     }
/* 151:    */     else
/* 152:    */     {
/* 153:160 */       document = new DOMDocument();
/* 154:    */     }
/* 155:163 */     document.addElement(createQName(qualifiedName, namespaceURI));
/* 156:    */     
/* 157:165 */     return document;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected DOMDocumentType asDocumentType(org.w3c.dom.DocumentType docType)
/* 161:    */   {
/* 162:170 */     if ((docType instanceof DOMDocumentType)) {
/* 163:171 */       return (DOMDocumentType)docType;
/* 164:    */     }
/* 165:173 */     return new DOMDocumentType(docType.getName(), docType.getPublicId(), docType.getSystemId());
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMDocumentFactory
 * JD-Core Version:    0.7.0.1
 */