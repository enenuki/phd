/*   1:    */ package org.apache.xml.serializer.dom3;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.DOMError;
/*   4:    */ import org.w3c.dom.DOMLocator;
/*   5:    */ 
/*   6:    */ final class DOMErrorImpl
/*   7:    */   implements DOMError
/*   8:    */ {
/*   9: 40 */   private short fSeverity = 1;
/*  10: 43 */   private String fMessage = null;
/*  11:    */   private String fType;
/*  12: 49 */   private Exception fException = null;
/*  13:    */   private Object fRelatedData;
/*  14: 55 */   private DOMLocatorImpl fLocation = new DOMLocatorImpl();
/*  15:    */   
/*  16:    */   DOMErrorImpl() {}
/*  17:    */   
/*  18:    */   DOMErrorImpl(short severity, String message, String type)
/*  19:    */   {
/*  20: 74 */     this.fSeverity = severity;
/*  21: 75 */     this.fMessage = message;
/*  22: 76 */     this.fType = type;
/*  23:    */   }
/*  24:    */   
/*  25:    */   DOMErrorImpl(short severity, String message, String type, Exception exception)
/*  26:    */   {
/*  27: 87 */     this.fSeverity = severity;
/*  28: 88 */     this.fMessage = message;
/*  29: 89 */     this.fType = type;
/*  30: 90 */     this.fException = exception;
/*  31:    */   }
/*  32:    */   
/*  33:    */   DOMErrorImpl(short severity, String message, String type, Exception exception, Object relatedData, DOMLocatorImpl location)
/*  34:    */   {
/*  35:103 */     this.fSeverity = severity;
/*  36:104 */     this.fMessage = message;
/*  37:105 */     this.fType = type;
/*  38:106 */     this.fException = exception;
/*  39:107 */     this.fRelatedData = relatedData;
/*  40:108 */     this.fLocation = location;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public short getSeverity()
/*  44:    */   {
/*  45:119 */     return this.fSeverity;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getMessage()
/*  49:    */   {
/*  50:128 */     return this.fMessage;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public DOMLocator getLocation()
/*  54:    */   {
/*  55:137 */     return this.fLocation;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object getRelatedException()
/*  59:    */   {
/*  60:146 */     return this.fException;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getType()
/*  64:    */   {
/*  65:155 */     return this.fType;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object getRelatedData()
/*  69:    */   {
/*  70:164 */     return this.fRelatedData;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void reset()
/*  74:    */   {
/*  75:168 */     this.fSeverity = 1;
/*  76:169 */     this.fException = null;
/*  77:170 */     this.fMessage = null;
/*  78:171 */     this.fType = null;
/*  79:172 */     this.fRelatedData = null;
/*  80:173 */     this.fLocation = null;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOMErrorImpl
 * JD-Core Version:    0.7.0.1
 */