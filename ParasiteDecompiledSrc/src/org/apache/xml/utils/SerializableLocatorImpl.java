/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.xml.sax.Locator;
/*   5:    */ 
/*   6:    */ public class SerializableLocatorImpl
/*   7:    */   implements Locator, Serializable
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = -2660312888446371460L;
/*  10:    */   private String publicId;
/*  11:    */   private String systemId;
/*  12:    */   private int lineNumber;
/*  13:    */   private int columnNumber;
/*  14:    */   
/*  15:    */   public SerializableLocatorImpl() {}
/*  16:    */   
/*  17:    */   public SerializableLocatorImpl(Locator locator)
/*  18:    */   {
/*  19: 75 */     setPublicId(locator.getPublicId());
/*  20: 76 */     setSystemId(locator.getSystemId());
/*  21: 77 */     setLineNumber(locator.getLineNumber());
/*  22: 78 */     setColumnNumber(locator.getColumnNumber());
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getPublicId()
/*  26:    */   {
/*  27: 97 */     return this.publicId;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getSystemId()
/*  31:    */   {
/*  32:111 */     return this.systemId;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getLineNumber()
/*  36:    */   {
/*  37:124 */     return this.lineNumber;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getColumnNumber()
/*  41:    */   {
/*  42:137 */     return this.columnNumber;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setPublicId(String publicId)
/*  46:    */   {
/*  47:155 */     this.publicId = publicId;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setSystemId(String systemId)
/*  51:    */   {
/*  52:168 */     this.systemId = systemId;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setLineNumber(int lineNumber)
/*  56:    */   {
/*  57:180 */     this.lineNumber = lineNumber;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setColumnNumber(int columnNumber)
/*  61:    */   {
/*  62:192 */     this.columnNumber = columnNumber;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.SerializableLocatorImpl
 * JD-Core Version:    0.7.0.1
 */