/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ 
/*   5:    */ public class DefaultDocumentType
/*   6:    */   extends AbstractDocumentType
/*   7:    */ {
/*   8:    */   protected String elementName;
/*   9:    */   private String publicID;
/*  10:    */   private String systemID;
/*  11:    */   private List internalDeclarations;
/*  12:    */   private List externalDeclarations;
/*  13:    */   
/*  14:    */   public DefaultDocumentType() {}
/*  15:    */   
/*  16:    */   public DefaultDocumentType(String elementName, String systemID)
/*  17:    */   {
/*  18: 52 */     this.elementName = elementName;
/*  19: 53 */     this.systemID = systemID;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DefaultDocumentType(String elementName, String publicID, String systemID)
/*  23:    */   {
/*  24: 71 */     this.elementName = elementName;
/*  25: 72 */     this.publicID = publicID;
/*  26: 73 */     this.systemID = systemID;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getElementName()
/*  30:    */   {
/*  31: 77 */     return this.elementName;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setElementName(String elementName)
/*  35:    */   {
/*  36: 81 */     this.elementName = elementName;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getPublicID()
/*  40:    */   {
/*  41: 90 */     return this.publicID;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setPublicID(String publicID)
/*  45:    */   {
/*  46:100 */     this.publicID = publicID;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getSystemID()
/*  50:    */   {
/*  51:109 */     return this.systemID;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setSystemID(String systemID)
/*  55:    */   {
/*  56:119 */     this.systemID = systemID;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List getInternalDeclarations()
/*  60:    */   {
/*  61:123 */     return this.internalDeclarations;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setInternalDeclarations(List internalDeclarations)
/*  65:    */   {
/*  66:127 */     this.internalDeclarations = internalDeclarations;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List getExternalDeclarations()
/*  70:    */   {
/*  71:131 */     return this.externalDeclarations;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setExternalDeclarations(List externalDeclarations)
/*  75:    */   {
/*  76:135 */     this.externalDeclarations = externalDeclarations;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultDocumentType
 * JD-Core Version:    0.7.0.1
 */