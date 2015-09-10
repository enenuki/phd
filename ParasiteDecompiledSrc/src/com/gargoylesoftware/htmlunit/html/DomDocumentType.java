/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import org.w3c.dom.DocumentType;
/*   5:    */ import org.w3c.dom.NamedNodeMap;
/*   6:    */ 
/*   7:    */ public class DomDocumentType
/*   8:    */   extends DomNode
/*   9:    */   implements DocumentType
/*  10:    */ {
/*  11:    */   private final String name_;
/*  12:    */   private final String publicId_;
/*  13:    */   private final String systemId_;
/*  14:    */   
/*  15:    */   public DomDocumentType(SgmlPage page, String name, String publicId, String systemId)
/*  16:    */   {
/*  17: 42 */     super(page);
/*  18: 43 */     this.name_ = name;
/*  19: 44 */     this.publicId_ = publicId;
/*  20: 45 */     this.systemId_ = systemId;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getNodeName()
/*  24:    */   {
/*  25: 53 */     return this.name_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public short getNodeType()
/*  29:    */   {
/*  30: 61 */     return 10;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public NamedNodeMap getEntities()
/*  34:    */   {
/*  35: 68 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getInternalSubset()
/*  39:    */   {
/*  40: 75 */     return "";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getName()
/*  44:    */   {
/*  45: 82 */     return this.name_;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public NamedNodeMap getNotations()
/*  49:    */   {
/*  50: 89 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getPublicId()
/*  54:    */   {
/*  55: 96 */     return this.publicId_;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getSystemId()
/*  59:    */   {
/*  60:103 */     return this.systemId_;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomDocumentType
 * JD-Core Version:    0.7.0.1
 */