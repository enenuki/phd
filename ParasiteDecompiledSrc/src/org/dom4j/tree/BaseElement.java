/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.dom4j.Branch;
/*   5:    */ import org.dom4j.Document;
/*   6:    */ import org.dom4j.DocumentFactory;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.Namespace;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ 
/*  11:    */ public class BaseElement
/*  12:    */   extends AbstractElement
/*  13:    */ {
/*  14:    */   private QName qname;
/*  15:    */   private Branch parentBranch;
/*  16:    */   protected List content;
/*  17:    */   protected List attributes;
/*  18:    */   
/*  19:    */   public BaseElement(String name)
/*  20:    */   {
/*  21: 46 */     this.qname = getDocumentFactory().createQName(name);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public BaseElement(QName qname)
/*  25:    */   {
/*  26: 50 */     this.qname = qname;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BaseElement(String name, Namespace namespace)
/*  30:    */   {
/*  31: 54 */     this.qname = getDocumentFactory().createQName(name, namespace);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Element getParent()
/*  35:    */   {
/*  36: 58 */     Element result = null;
/*  37: 60 */     if ((this.parentBranch instanceof Element)) {
/*  38: 61 */       result = (Element)this.parentBranch;
/*  39:    */     }
/*  40: 64 */     return result;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setParent(Element parent)
/*  44:    */   {
/*  45: 68 */     if (((this.parentBranch instanceof Element)) || (parent != null)) {
/*  46: 69 */       this.parentBranch = parent;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Document getDocument()
/*  51:    */   {
/*  52: 74 */     if ((this.parentBranch instanceof Document)) {
/*  53: 75 */       return (Document)this.parentBranch;
/*  54:    */     }
/*  55: 76 */     if ((this.parentBranch instanceof Element))
/*  56:    */     {
/*  57: 77 */       Element parent = (Element)this.parentBranch;
/*  58:    */       
/*  59: 79 */       return parent.getDocument();
/*  60:    */     }
/*  61: 82 */     return null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setDocument(Document document)
/*  65:    */   {
/*  66: 86 */     if (((this.parentBranch instanceof Document)) || (document != null)) {
/*  67: 87 */       this.parentBranch = document;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean supportsParent()
/*  72:    */   {
/*  73: 92 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public QName getQName()
/*  77:    */   {
/*  78: 96 */     return this.qname;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setQName(QName name)
/*  82:    */   {
/*  83:100 */     this.qname = name;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void clearContent()
/*  87:    */   {
/*  88:104 */     contentList().clear();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setContent(List content)
/*  92:    */   {
/*  93:108 */     this.content = content;
/*  94:110 */     if ((content instanceof ContentListFacade)) {
/*  95:111 */       this.content = ((ContentListFacade)content).getBackingList();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setAttributes(List attributes)
/* 100:    */   {
/* 101:116 */     this.attributes = attributes;
/* 102:118 */     if ((attributes instanceof ContentListFacade)) {
/* 103:119 */       this.attributes = ((ContentListFacade)attributes).getBackingList();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected List contentList()
/* 108:    */   {
/* 109:126 */     if (this.content == null) {
/* 110:127 */       this.content = createContentList();
/* 111:    */     }
/* 112:130 */     return this.content;
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected List attributeList()
/* 116:    */   {
/* 117:134 */     if (this.attributes == null) {
/* 118:135 */       this.attributes = createAttributeList();
/* 119:    */     }
/* 120:138 */     return this.attributes;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected List attributeList(int size)
/* 124:    */   {
/* 125:142 */     if (this.attributes == null) {
/* 126:143 */       this.attributes = createAttributeList(size);
/* 127:    */     }
/* 128:146 */     return this.attributes;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void setAttributeList(List attributeList)
/* 132:    */   {
/* 133:150 */     this.attributes = attributeList;
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.BaseElement
 * JD-Core Version:    0.7.0.1
 */