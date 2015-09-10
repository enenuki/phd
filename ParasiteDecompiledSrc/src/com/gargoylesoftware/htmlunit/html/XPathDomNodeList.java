/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.xpath.XPathUtils;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.AbstractList;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.collections.Transformer;
/*   9:    */ import org.apache.commons.collections.functors.NOPTransformer;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ 
/*  12:    */ class XPathDomNodeList<E extends DomNode>
/*  13:    */   extends AbstractList<E>
/*  14:    */   implements DomNodeList<E>, Serializable
/*  15:    */ {
/*  16:    */   private String xpath_;
/*  17:    */   private DomNode node_;
/*  18:    */   private Transformer transformer_;
/*  19:    */   private List<Object> cachedElements_;
/*  20:    */   
/*  21:    */   public XPathDomNodeList(DomNode node, String xpath)
/*  22:    */   {
/*  23: 56 */     this(node, xpath, NOPTransformer.INSTANCE);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public XPathDomNodeList(DomNode node, String xpath, Transformer transformer)
/*  27:    */   {
/*  28: 67 */     if (node != null)
/*  29:    */     {
/*  30: 68 */       this.node_ = node;
/*  31: 69 */       this.xpath_ = xpath;
/*  32: 70 */       this.transformer_ = transformer;
/*  33: 71 */       XPathDomNodeList<E>.DomHtmlAttributeChangeListenerImpl listener = new DomHtmlAttributeChangeListenerImpl(null);
/*  34: 72 */       this.node_.addDomChangeListener(listener);
/*  35: 73 */       if ((this.node_ instanceof HtmlElement))
/*  36:    */       {
/*  37: 74 */         ((HtmlElement)this.node_).addHtmlAttributeChangeListener(listener);
/*  38: 75 */         this.cachedElements_ = null;
/*  39:    */       }
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   private List<Object> getNodes()
/*  44:    */   {
/*  45: 85 */     if (this.cachedElements_ == null) {
/*  46: 86 */       if (this.node_ != null) {
/*  47: 87 */         this.cachedElements_ = XPathUtils.getByXPath(this.node_, this.xpath_);
/*  48:    */       } else {
/*  49: 90 */         this.cachedElements_ = new ArrayList();
/*  50:    */       }
/*  51:    */     }
/*  52: 93 */     return this.cachedElements_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int size()
/*  56:    */   {
/*  57:101 */     return getLength();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getLength()
/*  61:    */   {
/*  62:108 */     return getNodes().size();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Node item(int index)
/*  66:    */   {
/*  67:115 */     return (DomNode)this.transformer_.transform(getNodes().get(index));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public E get(int index)
/*  71:    */   {
/*  72:124 */     return (DomNode)getNodes().get(index);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String toString()
/*  76:    */   {
/*  77:132 */     return "XPathDomNodeList[" + this.xpath_ + "]";
/*  78:    */   }
/*  79:    */   
/*  80:    */   private class DomHtmlAttributeChangeListenerImpl
/*  81:    */     implements DomChangeListener, HtmlAttributeChangeListener
/*  82:    */   {
/*  83:    */     private DomHtmlAttributeChangeListenerImpl() {}
/*  84:    */     
/*  85:    */     public void nodeAdded(DomChangeEvent event)
/*  86:    */     {
/*  87:144 */       XPathDomNodeList.this.cachedElements_ = null;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void nodeDeleted(DomChangeEvent event)
/*  91:    */     {
/*  92:151 */       XPathDomNodeList.this.cachedElements_ = null;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public void attributeAdded(HtmlAttributeChangeEvent event)
/*  96:    */     {
/*  97:158 */       XPathDomNodeList.this.cachedElements_ = null;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void attributeRemoved(HtmlAttributeChangeEvent event)
/* 101:    */     {
/* 102:165 */       XPathDomNodeList.this.cachedElements_ = null;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public void attributeReplaced(HtmlAttributeChangeEvent event)
/* 106:    */     {
/* 107:172 */       XPathDomNodeList.this.cachedElements_ = null;
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.XPathDomNodeList
 * JD-Core Version:    0.7.0.1
 */