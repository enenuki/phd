/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectionDelegate;
/*   6:    */ import java.util.Map;
/*   7:    */ 
/*   8:    */ public class HtmlPasswordInput
/*   9:    */   extends HtmlInput
/*  10:    */   implements SelectableTextInput
/*  11:    */ {
/*  12:    */   private String valueAtFocus_;
/*  13: 37 */   private final SelectionDelegate selectionDelegate_ = new SelectionDelegate(this);
/*  14: 39 */   private final DoTypeProcessor doTypeProcessor_ = new DoTypeProcessor()
/*  15:    */   {
/*  16:    */     void typeDone(String newValue, int newCursorPosition)
/*  17:    */     {
/*  18: 42 */       if (newValue.length() > HtmlPasswordInput.this.getMaxLength()) {
/*  19: 43 */         return;
/*  20:    */       }
/*  21: 45 */       HtmlPasswordInput.this.setAttribute("value", newValue);
/*  22: 46 */       HtmlPasswordInput.this.setSelectionStart(newCursorPosition);
/*  23: 47 */       HtmlPasswordInput.this.setSelectionEnd(newCursorPosition);
/*  24:    */     }
/*  25:    */   };
/*  26:    */   
/*  27:    */   HtmlPasswordInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  28:    */   {
/*  29: 60 */     super(namespaceURI, qualifiedName, page, attributes);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected boolean isSubmittableByEnter()
/*  33:    */   {
/*  34: 68 */     return true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void select()
/*  38:    */   {
/*  39: 75 */     this.selectionDelegate_.select();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getSelectedText()
/*  43:    */   {
/*  44: 82 */     return this.selectionDelegate_.getSelectedText();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getText()
/*  48:    */   {
/*  49: 89 */     return getValueAttribute();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setText(String text)
/*  53:    */   {
/*  54: 96 */     setValueAttribute(text);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getSelectionStart()
/*  58:    */   {
/*  59:103 */     return this.selectionDelegate_.getSelectionStart();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setSelectionStart(int selectionStart)
/*  63:    */   {
/*  64:110 */     this.selectionDelegate_.setSelectionStart(selectionStart);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getSelectionEnd()
/*  68:    */   {
/*  69:117 */     return this.selectionDelegate_.getSelectionEnd();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setSelectionEnd(int selectionEnd)
/*  73:    */   {
/*  74:124 */     this.selectionDelegate_.setSelectionEnd(selectionEnd);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void doType(char c, boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  78:    */   {
/*  79:132 */     this.doTypeProcessor_.doType(getValueAttribute(), getSelectionStart(), getSelectionEnd(), c, shiftKey, ctrlKey, altKey);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void focus()
/*  83:    */   {
/*  84:141 */     super.focus();
/*  85:    */     
/*  86:143 */     this.valueAtFocus_ = getValueAttribute();
/*  87:    */   }
/*  88:    */   
/*  89:    */   void removeFocus()
/*  90:    */   {
/*  91:151 */     super.removeFocus();
/*  92:152 */     if (!this.valueAtFocus_.equals(getValueAttribute())) {
/*  93:153 */       executeOnChangeHandlerIfAppropriate(this);
/*  94:    */     }
/*  95:155 */     this.valueAtFocus_ = null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected Object clone()
/*  99:    */     throws CloneNotSupportedException
/* 100:    */   {
/* 101:163 */     return new HtmlPasswordInput(getNamespaceURI(), getQualifiedName(), getPage(), getAttributesMap());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 105:    */   {
/* 106:171 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 107:172 */     if (("value".equals(qualifiedName)) && ((getPage() instanceof HtmlPage)))
/* 108:    */     {
/* 109:173 */       setSelectionStart(attributeValue.length());
/* 110:174 */       setSelectionEnd(attributeValue.length());
/* 111:    */     }
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlPasswordInput
 * JD-Core Version:    0.7.0.1
 */