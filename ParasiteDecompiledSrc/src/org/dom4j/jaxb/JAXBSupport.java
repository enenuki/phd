/*   1:    */ package org.dom4j.jaxb;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import javax.xml.bind.JAXBContext;
/*   5:    */ import javax.xml.bind.JAXBException;
/*   6:    */ import javax.xml.bind.Marshaller;
/*   7:    */ import javax.xml.bind.Unmarshaller;
/*   8:    */ import javax.xml.transform.Source;
/*   9:    */ import javax.xml.transform.stream.StreamSource;
/*  10:    */ import org.dom4j.dom.DOMDocument;
/*  11:    */ 
/*  12:    */ abstract class JAXBSupport
/*  13:    */ {
/*  14:    */   private String contextPath;
/*  15:    */   private ClassLoader classloader;
/*  16:    */   private JAXBContext jaxbContext;
/*  17:    */   private Marshaller marshaller;
/*  18:    */   private Unmarshaller unmarshaller;
/*  19:    */   
/*  20:    */   public JAXBSupport(String contextPath)
/*  21:    */   {
/*  22: 38 */     this.contextPath = contextPath;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public JAXBSupport(String contextPath, ClassLoader classloader)
/*  26:    */   {
/*  27: 42 */     this.contextPath = contextPath;
/*  28: 43 */     this.classloader = classloader;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected org.dom4j.Element marshal(javax.xml.bind.Element element)
/*  32:    */     throws JAXBException
/*  33:    */   {
/*  34: 60 */     DOMDocument doc = new DOMDocument();
/*  35: 61 */     getMarshaller().marshal(element, doc);
/*  36:    */     
/*  37: 63 */     return doc.getRootElement();
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected javax.xml.bind.Element unmarshal(org.dom4j.Element element)
/*  41:    */     throws JAXBException
/*  42:    */   {
/*  43: 80 */     Source source = new StreamSource(new StringReader(element.asXML()));
/*  44:    */     
/*  45: 82 */     return (javax.xml.bind.Element)getUnmarshaller().unmarshal(source);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private Marshaller getMarshaller()
/*  49:    */     throws JAXBException
/*  50:    */   {
/*  51: 86 */     if (this.marshaller == null) {
/*  52: 87 */       this.marshaller = getContext().createMarshaller();
/*  53:    */     }
/*  54: 90 */     return this.marshaller;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private Unmarshaller getUnmarshaller()
/*  58:    */     throws JAXBException
/*  59:    */   {
/*  60: 94 */     if (this.unmarshaller == null) {
/*  61: 95 */       this.unmarshaller = getContext().createUnmarshaller();
/*  62:    */     }
/*  63: 98 */     return this.unmarshaller;
/*  64:    */   }
/*  65:    */   
/*  66:    */   private JAXBContext getContext()
/*  67:    */     throws JAXBException
/*  68:    */   {
/*  69:102 */     if (this.jaxbContext == null) {
/*  70:103 */       if (this.classloader == null) {
/*  71:104 */         this.jaxbContext = JAXBContext.newInstance(this.contextPath);
/*  72:    */       } else {
/*  73:106 */         this.jaxbContext = JAXBContext.newInstance(this.contextPath, this.classloader);
/*  74:    */       }
/*  75:    */     }
/*  76:110 */     return this.jaxbContext;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.jaxb.JAXBSupport
 * JD-Core Version:    0.7.0.1
 */