/*  1:   */ package org.apache.xpath.jaxp;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import javax.xml.xpath.XPathVariableResolver;
/*  5:   */ import org.apache.xpath.VariableStack;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ import org.apache.xpath.res.XPATHMessages;
/*  9:   */ 
/* 10:   */ public class JAXPVariableStack
/* 11:   */   extends VariableStack
/* 12:   */ {
/* 13:   */   private final XPathVariableResolver resolver;
/* 14:   */   
/* 15:   */   public JAXPVariableStack(XPathVariableResolver resolver)
/* 16:   */   {
/* 17:45 */     super(2);
/* 18:46 */     this.resolver = resolver;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public XObject getVariableOrParam(XPathContext xctxt, org.apache.xml.utils.QName qname)
/* 22:   */     throws TransformerException, IllegalArgumentException
/* 23:   */   {
/* 24:51 */     if (qname == null)
/* 25:   */     {
/* 26:54 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Variable qname" });
/* 27:   */       
/* 28:   */ 
/* 29:57 */       throw new IllegalArgumentException(fmsg);
/* 30:   */     }
/* 31:59 */     javax.xml.namespace.QName name = new javax.xml.namespace.QName(qname.getNamespace(), qname.getLocalPart());
/* 32:   */     
/* 33:   */ 
/* 34:   */ 
/* 35:63 */     Object varValue = this.resolver.resolveVariable(name);
/* 36:64 */     if (varValue == null)
/* 37:   */     {
/* 38:65 */       String fmsg = XPATHMessages.createXPATHMessage("ER_RESOLVE_VARIABLE_RETURNS_NULL", new Object[] { name.toString() });
/* 39:   */       
/* 40:   */ 
/* 41:68 */       throw new TransformerException(fmsg);
/* 42:   */     }
/* 43:70 */     return XObject.create(varValue, xctxt);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.JAXPVariableStack
 * JD-Core Version:    0.7.0.1
 */