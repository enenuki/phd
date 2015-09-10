/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import org.w3c.dom.Attr;
/*   5:    */ import org.w3c.dom.TypeInfo;
/*   6:    */ 
/*   7:    */ public class DomAttr
/*   8:    */   extends DomNamespaceNode
/*   9:    */   implements Attr
/*  10:    */ {
/*  11:    */   private String value_;
/*  12:    */   private boolean specified_;
/*  13:    */   
/*  14:    */   public DomAttr(SgmlPage page, String namespaceURI, String qualifiedName, String value, boolean specified)
/*  15:    */   {
/*  16: 48 */     super(namespaceURI, qualifiedName, page);
/*  17: 49 */     this.value_ = value;
/*  18: 50 */     this.specified_ = specified;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public short getNodeType()
/*  22:    */   {
/*  23: 58 */     return 2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getNodeName()
/*  27:    */   {
/*  28: 66 */     return getName();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getNodeValue()
/*  32:    */   {
/*  33: 74 */     return getValue();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getName()
/*  37:    */   {
/*  38: 81 */     return getQualifiedName();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getValue()
/*  42:    */   {
/*  43: 88 */     return this.value_;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setNodeValue(String value)
/*  47:    */   {
/*  48: 96 */     setValue(value);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setValue(String value)
/*  52:    */   {
/*  53:103 */     this.value_ = value;
/*  54:104 */     this.specified_ = true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public DomElement getOwnerElement()
/*  58:    */   {
/*  59:111 */     return (DomElement)getParentNode();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean getSpecified()
/*  63:    */   {
/*  64:118 */     return this.specified_;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public TypeInfo getSchemaTypeInfo()
/*  68:    */   {
/*  69:126 */     throw new UnsupportedOperationException("DomAttr.getSchemaTypeInfo is not yet implemented.");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isId()
/*  73:    */   {
/*  74:133 */     return "id".equals(getNodeName());
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:141 */     return getClass().getSimpleName() + "[name=" + getNodeName() + " value=" + getNodeValue() + "]";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getCanonicalXPath()
/*  83:    */   {
/*  84:149 */     return getParentNode().getCanonicalXPath() + "/@" + getName();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getTextContent()
/*  88:    */   {
/*  89:157 */     return getNodeValue();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTextContent(String textContent)
/*  93:    */   {
/*  94:165 */     boolean mappedElement = HtmlPage.isMappedElement(getOwnerDocument(), getName());
/*  95:166 */     if (mappedElement) {
/*  96:167 */       ((HtmlPage)getPage()).removeMappedElement((HtmlElement)getOwnerElement());
/*  97:    */     }
/*  98:169 */     setValue(textContent);
/*  99:170 */     if (mappedElement) {
/* 100:171 */       ((HtmlPage)getPage()).addMappedElement((HtmlElement)getOwnerElement());
/* 101:    */     }
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomAttr
 * JD-Core Version:    0.7.0.1
 */