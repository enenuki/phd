/*  1:   */ package org.dom4j.bean;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.dom4j.Attribute;
/*  5:   */ import org.dom4j.DocumentFactory;
/*  6:   */ import org.dom4j.Element;
/*  7:   */ import org.dom4j.QName;
/*  8:   */ import org.dom4j.tree.DefaultAttribute;
/*  9:   */ import org.xml.sax.Attributes;
/* 10:   */ 
/* 11:   */ public class BeanDocumentFactory
/* 12:   */   extends DocumentFactory
/* 13:   */ {
/* 14:33 */   private static BeanDocumentFactory singleton = new BeanDocumentFactory();
/* 15:   */   
/* 16:   */   public static DocumentFactory getInstance()
/* 17:   */   {
/* 18:43 */     return singleton;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Element createElement(QName qname)
/* 22:   */   {
/* 23:48 */     Object bean = createBean(qname);
/* 24:50 */     if (bean == null) {
/* 25:51 */       return new BeanElement(qname);
/* 26:   */     }
/* 27:53 */     return new BeanElement(qname, bean);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Element createElement(QName qname, Attributes attributes)
/* 31:   */   {
/* 32:58 */     Object bean = createBean(qname, attributes);
/* 33:60 */     if (bean == null) {
/* 34:61 */       return new BeanElement(qname);
/* 35:   */     }
/* 36:63 */     return new BeanElement(qname, bean);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Attribute createAttribute(Element owner, QName qname, String value)
/* 40:   */   {
/* 41:68 */     return new DefaultAttribute(qname, value);
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected Object createBean(QName qname)
/* 45:   */   {
/* 46:73 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected Object createBean(QName qname, Attributes attributes)
/* 50:   */   {
/* 51:77 */     String value = attributes.getValue("class");
/* 52:79 */     if (value != null) {
/* 53:   */       try
/* 54:   */       {
/* 55:81 */         Class beanClass = Class.forName(value, true, BeanDocumentFactory.class.getClassLoader());
/* 56:   */         
/* 57:   */ 
/* 58:84 */         return beanClass.newInstance();
/* 59:   */       }
/* 60:   */       catch (Exception e)
/* 61:   */       {
/* 62:86 */         handleException(e);
/* 63:   */       }
/* 64:   */     }
/* 65:90 */     return null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   protected void handleException(Exception e)
/* 69:   */   {
/* 70:95 */     System.out.println("#### Warning: couldn't create bean: " + e);
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.bean.BeanDocumentFactory
 * JD-Core Version:    0.7.0.1
 */