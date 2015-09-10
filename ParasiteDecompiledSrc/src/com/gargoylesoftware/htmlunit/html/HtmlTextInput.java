/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectionDelegate;
/*   6:    */ import java.util.Map;
/*   7:    */ 
/*   8:    */ public class HtmlTextInput
/*   9:    */   extends HtmlInput
/*  10:    */   implements SelectableTextInput
/*  11:    */ {
/*  12:    */   private String valueAtFocus_;
/*  13: 38 */   private final SelectionDelegate selectionDelegate_ = new SelectionDelegate(this);
/*  14: 40 */   private final DoTypeProcessor doTypeProcessor_ = new DoTypeProcessor()
/*  15:    */   {
/*  16:    */     void typeDone(String newValue, int newCursorPosition)
/*  17:    */     {
/*  18: 43 */       if (newValue.length() > HtmlTextInput.this.getMaxLength()) {
/*  19: 44 */         return;
/*  20:    */       }
/*  21: 46 */       HtmlTextInput.this.setAttribute("value", newValue);
/*  22: 47 */       HtmlTextInput.this.setSelectionStart(newCursorPosition);
/*  23: 48 */       HtmlTextInput.this.setSelectionEnd(newCursorPosition);
/*  24:    */     }
/*  25:    */   };
/*  26:    */   
/*  27:    */   HtmlTextInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  28:    */   {
/*  29: 62 */     super(namespaceURI, qualifiedName, page, attributes);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void doType(char c, boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  33:    */   {
/*  34: 70 */     this.doTypeProcessor_.doType(getValueAttribute(), getSelectionStart(), getSelectionEnd(), c, shiftKey, ctrlKey, altKey);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected boolean isSubmittableByEnter()
/*  38:    */   {
/*  39: 79 */     return true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void select()
/*  43:    */   {
/*  44: 86 */     this.selectionDelegate_.select();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getSelectedText()
/*  48:    */   {
/*  49: 93 */     return this.selectionDelegate_.getSelectedText();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getText()
/*  53:    */   {
/*  54:100 */     return getValueAttribute();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setText(String text)
/*  58:    */   {
/*  59:107 */     setValueAttribute(text);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getSelectionStart()
/*  63:    */   {
/*  64:114 */     return this.selectionDelegate_.getSelectionStart();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setSelectionStart(int selectionStart)
/*  68:    */   {
/*  69:121 */     this.selectionDelegate_.setSelectionStart(selectionStart);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getSelectionEnd()
/*  73:    */   {
/*  74:128 */     return this.selectionDelegate_.getSelectionEnd();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setSelectionEnd(int selectionEnd)
/*  78:    */   {
/*  79:135 */     this.selectionDelegate_.setSelectionEnd(selectionEnd);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/*  83:    */   {
/*  84:143 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/*  85:144 */     if (("value".equals(qualifiedName)) && ((getPage() instanceof HtmlPage)))
/*  86:    */     {
/*  87:145 */       setSelectionStart(attributeValue.length());
/*  88:146 */       setSelectionEnd(attributeValue.length());
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void focus()
/*  93:    */   {
/*  94:155 */     super.focus();
/*  95:    */     
/*  96:157 */     this.valueAtFocus_ = getValueAttribute();
/*  97:    */   }
/*  98:    */   
/*  99:    */   void removeFocus()
/* 100:    */   {
/* 101:165 */     super.removeFocus();
/* 102:166 */     if (!this.valueAtFocus_.equals(getValueAttribute())) {
/* 103:167 */       executeOnChangeHandlerIfAppropriate(this);
/* 104:    */     }
/* 105:169 */     this.valueAtFocus_ = null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Object clone()
/* 109:    */     throws CloneNotSupportedException
/* 110:    */   {
/* 111:177 */     return new HtmlTextInput(getNamespaceURI(), getQualifiedName(), getPage(), getAttributesMap());
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTextInput
 * JD-Core Version:    0.7.0.1
 */