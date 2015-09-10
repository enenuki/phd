/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import org.w3c.dom.CharacterData;
/*   5:    */ 
/*   6:    */ public abstract class DomCharacterData
/*   7:    */   extends DomNode
/*   8:    */   implements CharacterData
/*   9:    */ {
/*  10:    */   private String data_;
/*  11:    */   
/*  12:    */   public DomCharacterData(SgmlPage page, String data)
/*  13:    */   {
/*  14: 42 */     super(page);
/*  15: 43 */     this.data_ = data;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getData()
/*  19:    */   {
/*  20: 51 */     return this.data_;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setData(String data)
/*  24:    */   {
/*  25: 59 */     this.data_ = data;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setNodeValue(String newValue)
/*  29:    */   {
/*  30: 68 */     this.data_ = newValue;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setTextContent(String textContent)
/*  34:    */   {
/*  35: 76 */     this.data_ = textContent;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getLength()
/*  39:    */   {
/*  40: 84 */     return this.data_.length();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void appendData(String newData)
/*  44:    */   {
/*  45: 92 */     this.data_ += newData;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void deleteData(int offset, int count)
/*  49:    */   {
/*  50:101 */     if ((offset < 0) || (count < 0)) {
/*  51:102 */       throw new IllegalArgumentException("offset: " + offset + " count: " + count);
/*  52:    */     }
/*  53:105 */     int tailLength = Math.max(this.data_.length() - count - offset, 0);
/*  54:106 */     if (tailLength > 0) {
/*  55:107 */       this.data_ = (this.data_.substring(0, offset) + this.data_.substring(offset + count, offset + count + tailLength));
/*  56:    */     } else {
/*  57:110 */       this.data_ = "";
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void insertData(int offset, String arg)
/*  62:    */   {
/*  63:120 */     this.data_ = new StringBuilder(this.data_).insert(offset, arg).toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void replaceData(int offset, int count, String arg)
/*  67:    */   {
/*  68:130 */     deleteData(offset, count);
/*  69:131 */     insertData(offset, arg);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String substringData(int offset, int count)
/*  73:    */   {
/*  74:142 */     int length = this.data_.length();
/*  75:143 */     if ((count < 0) || (offset < 0) || (offset > length - 1)) {
/*  76:144 */       throw new IllegalArgumentException("offset: " + offset + " count: " + count);
/*  77:    */     }
/*  78:147 */     int tailIndex = Math.min(offset + count, length);
/*  79:148 */     return this.data_.substring(offset, tailIndex);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getNodeValue()
/*  83:    */   {
/*  84:157 */     return this.data_;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCanonicalXPath()
/*  88:    */   {
/*  89:165 */     return getParentNode().getCanonicalXPath() + '/' + getXPathToken();
/*  90:    */   }
/*  91:    */   
/*  92:    */   private String getXPathToken()
/*  93:    */   {
/*  94:172 */     DomNode parent = getParentNode();
/*  95:    */     
/*  96:    */ 
/*  97:    */ 
/*  98:176 */     int siblingsOfSameType = 0;
/*  99:177 */     int nodeIndex = 0;
/* 100:178 */     for (DomNode child : parent.getChildren()) {
/* 101:179 */       if (child == this)
/* 102:    */       {
/* 103:180 */         siblingsOfSameType++;nodeIndex = siblingsOfSameType;
/* 104:181 */         if (nodeIndex > 1) {
/* 105:    */           break;
/* 106:    */         }
/* 107:    */       }
/* 108:187 */       else if (child.getNodeType() == getNodeType())
/* 109:    */       {
/* 110:188 */         siblingsOfSameType++;
/* 111:189 */         if (nodeIndex > 0) {
/* 112:    */           break;
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:197 */     String nodeName = getNodeName().substring(1) + "()";
/* 117:198 */     if (siblingsOfSameType == 1) {
/* 118:199 */       return nodeName;
/* 119:    */     }
/* 120:201 */     return nodeName + '[' + nodeIndex + ']';
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomCharacterData
 * JD-Core Version:    0.7.0.1
 */