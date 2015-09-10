/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import java.util.AbstractSequentialList;
/*   4:    */ import java.util.ListIterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.w3c.dom.Node;
/*   7:    */ 
/*   8:    */ class SiblingDomNodeList
/*   9:    */   extends AbstractSequentialList<DomNode>
/*  10:    */   implements DomNodeList<DomNode>
/*  11:    */ {
/*  12:    */   private DomNode parent_;
/*  13:    */   
/*  14:    */   public SiblingDomNodeList(DomNode parent)
/*  15:    */   {
/*  16: 34 */     this.parent_ = parent;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public int getLength()
/*  20:    */   {
/*  21: 41 */     int length = 0;
/*  22: 42 */     for (DomNode node = this.parent_.getFirstChild(); node != null; node = node.getNextSibling()) {
/*  23: 43 */       length++;
/*  24:    */     }
/*  25: 45 */     return length;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int size()
/*  29:    */   {
/*  30: 53 */     return getLength();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Node item(int index)
/*  34:    */   {
/*  35: 60 */     return get(index);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public DomNode get(int index)
/*  39:    */   {
/*  40: 68 */     int i = 0;
/*  41: 69 */     for (DomNode node = this.parent_.getFirstChild(); node != null; node = node.getNextSibling())
/*  42:    */     {
/*  43: 70 */       if (i == index) {
/*  44: 71 */         return node;
/*  45:    */       }
/*  46: 73 */       i++;
/*  47:    */     }
/*  48: 75 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ListIterator<DomNode> listIterator(int index)
/*  52:    */   {
/*  53: 83 */     return new SiblingListIterator(index);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toString()
/*  57:    */   {
/*  58: 91 */     return "SiblingDomNodeList[" + this.parent_ + "]";
/*  59:    */   }
/*  60:    */   
/*  61:    */   private class SiblingListIterator
/*  62:    */     implements ListIterator<DomNode>
/*  63:    */   {
/*  64:    */     private DomNode prev_;
/*  65:    */     private DomNode next_;
/*  66:    */     private int nextIndex_;
/*  67:    */     
/*  68:    */     public SiblingListIterator(int index)
/*  69:    */     {
/*  70:100 */       this.next_ = SiblingDomNodeList.this.parent_.getFirstChild();
/*  71:101 */       this.nextIndex_ = 0;
/*  72:102 */       for (int i = 0; i < index; i++) {
/*  73:103 */         next();
/*  74:    */       }
/*  75:    */     }
/*  76:    */     
/*  77:    */     public boolean hasNext()
/*  78:    */     {
/*  79:111 */       return this.next_ != null;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public DomNode next()
/*  83:    */     {
/*  84:118 */       if (!hasNext()) {
/*  85:119 */         throw new NoSuchElementException();
/*  86:    */       }
/*  87:121 */       this.prev_ = this.next_;
/*  88:122 */       this.next_ = this.next_.getNextSibling();
/*  89:123 */       this.nextIndex_ += 1;
/*  90:124 */       return this.prev_;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public boolean hasPrevious()
/*  94:    */     {
/*  95:131 */       return this.prev_ != null;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public DomNode previous()
/*  99:    */     {
/* 100:135 */       if (!hasPrevious()) {
/* 101:136 */         throw new NoSuchElementException();
/* 102:    */       }
/* 103:138 */       this.next_ = this.prev_;
/* 104:139 */       this.prev_ = this.prev_.getPreviousSibling();
/* 105:140 */       this.nextIndex_ -= 1;
/* 106:141 */       return this.next_;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public int nextIndex()
/* 110:    */     {
/* 111:145 */       return this.nextIndex_;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public int previousIndex()
/* 115:    */     {
/* 116:149 */       return this.nextIndex_ - 1;
/* 117:    */     }
/* 118:    */     
/* 119:    */     public void add(DomNode e)
/* 120:    */     {
/* 121:153 */       throw new UnsupportedOperationException();
/* 122:    */     }
/* 123:    */     
/* 124:    */     public void remove()
/* 125:    */     {
/* 126:157 */       throw new UnsupportedOperationException();
/* 127:    */     }
/* 128:    */     
/* 129:    */     public void set(DomNode e)
/* 130:    */     {
/* 131:161 */       throw new UnsupportedOperationException();
/* 132:    */     }
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.SiblingDomNodeList
 * JD-Core Version:    0.7.0.1
 */