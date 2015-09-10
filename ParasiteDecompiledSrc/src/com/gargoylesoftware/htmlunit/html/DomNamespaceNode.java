/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.xpath.XPathUtils;
/*   6:    */ 
/*   7:    */ public abstract class DomNamespaceNode
/*   8:    */   extends DomNode
/*   9:    */ {
/*  10:    */   private final String namespaceURI_;
/*  11:    */   private String qualifiedName_;
/*  12:    */   private final String localName_;
/*  13:    */   private String prefix_;
/*  14:    */   
/*  15:    */   protected DomNamespaceNode(String namespaceURI, String qualifiedName, SgmlPage page)
/*  16:    */   {
/*  17: 43 */     super(page);
/*  18: 44 */     WebAssert.notNull("qualifiedName", qualifiedName);
/*  19: 45 */     this.qualifiedName_ = qualifiedName;
/*  20: 47 */     if (qualifiedName.indexOf(':') != -1)
/*  21:    */     {
/*  22: 48 */       this.namespaceURI_ = namespaceURI;
/*  23: 49 */       int colonPosition = this.qualifiedName_.indexOf(':');
/*  24: 50 */       this.localName_ = this.qualifiedName_.substring(colonPosition + 1);
/*  25: 51 */       this.prefix_ = this.qualifiedName_.substring(0, colonPosition);
/*  26:    */     }
/*  27:    */     else
/*  28:    */     {
/*  29: 54 */       this.namespaceURI_ = namespaceURI;
/*  30: 55 */       this.localName_ = this.qualifiedName_;
/*  31: 56 */       this.prefix_ = null;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getNamespaceURI()
/*  36:    */   {
/*  37: 65 */     return this.namespaceURI_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getLocalName()
/*  41:    */   {
/*  42: 73 */     boolean caseSensitive = getPage().hasCaseSensitiveTagNames();
/*  43: 74 */     if ((!caseSensitive) && (XPathUtils.isProcessingXPath())) {
/*  44: 75 */       return this.localName_.toLowerCase();
/*  45:    */     }
/*  46: 77 */     return this.localName_;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getPrefix()
/*  50:    */   {
/*  51: 85 */     return this.prefix_;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setPrefix(String prefix)
/*  55:    */   {
/*  56: 93 */     this.prefix_ = prefix;
/*  57: 94 */     if ((this.prefix_ != null) && (this.localName_ != null)) {
/*  58: 95 */       this.qualifiedName_ = (this.prefix_ + ":" + this.localName_);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getQualifiedName()
/*  63:    */   {
/*  64:104 */     return this.qualifiedName_;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomNamespaceNode
 * JD-Core Version:    0.7.0.1
 */