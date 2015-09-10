/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.xml.transform.SourceLocator;
/*   5:    */ import org.xml.sax.Locator;
/*   6:    */ import org.xml.sax.SAXParseException;
/*   7:    */ import org.xml.sax.helpers.LocatorImpl;
/*   8:    */ 
/*   9:    */ public class SAXSourceLocator
/*  10:    */   extends LocatorImpl
/*  11:    */   implements SourceLocator, Serializable
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 3181680946321164112L;
/*  14:    */   Locator m_locator;
/*  15:    */   
/*  16:    */   public SAXSourceLocator() {}
/*  17:    */   
/*  18:    */   public SAXSourceLocator(Locator locator)
/*  19:    */   {
/*  20: 59 */     this.m_locator = locator;
/*  21: 60 */     setColumnNumber(locator.getColumnNumber());
/*  22: 61 */     setLineNumber(locator.getLineNumber());
/*  23: 62 */     setPublicId(locator.getPublicId());
/*  24: 63 */     setSystemId(locator.getSystemId());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public SAXSourceLocator(SourceLocator locator)
/*  28:    */   {
/*  29: 74 */     this.m_locator = null;
/*  30: 75 */     setColumnNumber(locator.getColumnNumber());
/*  31: 76 */     setLineNumber(locator.getLineNumber());
/*  32: 77 */     setPublicId(locator.getPublicId());
/*  33: 78 */     setSystemId(locator.getSystemId());
/*  34:    */   }
/*  35:    */   
/*  36:    */   public SAXSourceLocator(SAXParseException spe)
/*  37:    */   {
/*  38: 90 */     setLineNumber(spe.getLineNumber());
/*  39: 91 */     setColumnNumber(spe.getColumnNumber());
/*  40: 92 */     setPublicId(spe.getPublicId());
/*  41: 93 */     setSystemId(spe.getSystemId());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getPublicId()
/*  45:    */   {
/*  46:109 */     return null == this.m_locator ? super.getPublicId() : this.m_locator.getPublicId();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getSystemId()
/*  50:    */   {
/*  51:128 */     return null == this.m_locator ? super.getSystemId() : this.m_locator.getSystemId();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getLineNumber()
/*  55:    */   {
/*  56:148 */     return null == this.m_locator ? super.getLineNumber() : this.m_locator.getLineNumber();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getColumnNumber()
/*  60:    */   {
/*  61:168 */     return null == this.m_locator ? super.getColumnNumber() : this.m_locator.getColumnNumber();
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.SAXSourceLocator
 * JD-Core Version:    0.7.0.1
 */