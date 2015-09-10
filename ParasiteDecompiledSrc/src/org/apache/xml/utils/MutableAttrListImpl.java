/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.xml.sax.Attributes;
/*   5:    */ import org.xml.sax.helpers.AttributesImpl;
/*   6:    */ 
/*   7:    */ public class MutableAttrListImpl
/*   8:    */   extends AttributesImpl
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 6289452013442934470L;
/*  12:    */   
/*  13:    */   public MutableAttrListImpl() {}
/*  14:    */   
/*  15:    */   public MutableAttrListImpl(Attributes atts)
/*  16:    */   {
/*  17: 56 */     super(atts);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void addAttribute(String uri, String localName, String qName, String type, String value)
/*  21:    */   {
/*  22: 80 */     if (null == uri) {
/*  23: 81 */       uri = "";
/*  24:    */     }
/*  25: 85 */     int index = getIndex(qName);
/*  26: 90 */     if (index >= 0) {
/*  27: 91 */       setAttribute(index, uri, localName, qName, type, value);
/*  28:    */     } else {
/*  29: 93 */       super.addAttribute(uri, localName, qName, type, value);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void addAttributes(Attributes atts)
/*  34:    */   {
/*  35:104 */     int nAtts = atts.getLength();
/*  36:106 */     for (int i = 0; i < nAtts; i++)
/*  37:    */     {
/*  38:108 */       String uri = atts.getURI(i);
/*  39:110 */       if (null == uri) {
/*  40:111 */         uri = "";
/*  41:    */       }
/*  42:113 */       String localName = atts.getLocalName(i);
/*  43:114 */       String qname = atts.getQName(i);
/*  44:115 */       int index = getIndex(uri, localName);
/*  45:117 */       if (index >= 0) {
/*  46:118 */         setAttribute(index, uri, localName, qname, atts.getType(i), atts.getValue(i));
/*  47:    */       } else {
/*  48:121 */         addAttribute(uri, localName, qname, atts.getType(i), atts.getValue(i));
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean contains(String name)
/*  54:    */   {
/*  55:135 */     return getValue(name) != null;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.MutableAttrListImpl
 * JD-Core Version:    0.7.0.1
 */