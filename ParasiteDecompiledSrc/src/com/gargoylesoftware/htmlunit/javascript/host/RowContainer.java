/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  12:    */ 
/*  13:    */ public class RowContainer
/*  14:    */   extends HTMLElement
/*  15:    */ {
/*  16:    */   private HTMLCollection rows_;
/*  17:    */   
/*  18:    */   public Object jsxGet_rows()
/*  19:    */   {
/*  20: 54 */     if (this.rows_ == null) {
/*  21: 55 */       this.rows_ = new HTMLCollection(getDomNodeOrDie(), false, "rows")
/*  22:    */       {
/*  23:    */         protected boolean isMatching(DomNode node)
/*  24:    */         {
/*  25: 58 */           return ((node instanceof HtmlTableRow)) && (RowContainer.this.isContainedRow((HtmlTableRow)node));
/*  26:    */         }
/*  27:    */       };
/*  28:    */     }
/*  29: 63 */     return this.rows_;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected boolean isContainedRow(HtmlTableRow row)
/*  33:    */   {
/*  34: 72 */     return row.getParentNode() == getDomNodeOrDie();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void jsxFunction_deleteRow(int rowIndex)
/*  38:    */   {
/*  39: 81 */     HTMLCollection rows = (HTMLCollection)jsxGet_rows();
/*  40: 82 */     int rowCount = rows.jsxGet_length();
/*  41: 83 */     if (rowIndex == -1) {
/*  42: 84 */       rowIndex = rowCount - 1;
/*  43:    */     }
/*  44: 86 */     boolean rowIndexValid = (rowIndex >= 0) && (rowIndex < rowCount);
/*  45: 87 */     if (rowIndexValid)
/*  46:    */     {
/*  47: 88 */       SimpleScriptable row = (SimpleScriptable)rows.jsxFunction_item(Integer.valueOf(rowIndex));
/*  48: 89 */       row.getDomNodeOrDie().remove();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object jsxFunction_insertRow(Object index)
/*  53:    */   {
/*  54:103 */     int rowIndex = -1;
/*  55:104 */     if (index != Undefined.instance) {
/*  56:105 */       rowIndex = (int)Context.toNumber(index);
/*  57:    */     }
/*  58:107 */     HTMLCollection rows = (HTMLCollection)jsxGet_rows();
/*  59:108 */     int rowCount = rows.jsxGet_length();
/*  60:    */     int r;
/*  61:    */     int r;
/*  62:110 */     if ((rowIndex == -1) || (rowIndex == rowCount)) {
/*  63:111 */       r = Math.max(0, rowCount - 1);
/*  64:    */     } else {
/*  65:114 */       r = rowIndex;
/*  66:    */     }
/*  67:117 */     if ((r < 0) || (r > rowCount)) {
/*  68:118 */       throw Context.reportRuntimeError("Index or size is negative or greater than the allowed amount (index: " + rowIndex + ", " + rowCount + " rows)");
/*  69:    */     }
/*  70:122 */     return insertRow(r);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object insertRow(int index)
/*  74:    */   {
/*  75:131 */     HTMLCollection rows = (HTMLCollection)jsxGet_rows();
/*  76:132 */     int rowCount = rows.jsxGet_length();
/*  77:133 */     HtmlElement newRow = ((HtmlPage)getDomNodeOrDie().getPage()).createElement("tr");
/*  78:134 */     if (rowCount == 0)
/*  79:    */     {
/*  80:135 */       getDomNodeOrDie().appendChild(newRow);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:138 */       SimpleScriptable row = (SimpleScriptable)rows.jsxFunction_item(Integer.valueOf(index));
/*  85:140 */       if (index >= rowCount - 1) {
/*  86:141 */         row.getDomNodeOrDie().getParentNode().appendChild(newRow);
/*  87:    */       } else {
/*  88:144 */         row.getDomNodeOrDie().insertBefore(newRow);
/*  89:    */       }
/*  90:    */     }
/*  91:147 */     return getScriptableFor(newRow);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object jsxFunction_moveRow(int sourceIndex, int targetIndex)
/*  95:    */   {
/*  96:158 */     HTMLCollection rows = (HTMLCollection)jsxGet_rows();
/*  97:159 */     int rowCount = rows.jsxGet_length();
/*  98:160 */     boolean sourceIndexValid = (sourceIndex >= 0) && (sourceIndex < rowCount);
/*  99:161 */     boolean targetIndexValid = (targetIndex >= 0) && (targetIndex < rowCount);
/* 100:162 */     if ((sourceIndexValid) && (targetIndexValid))
/* 101:    */     {
/* 102:163 */       SimpleScriptable sourceRow = (SimpleScriptable)rows.jsxFunction_item(Integer.valueOf(sourceIndex));
/* 103:164 */       SimpleScriptable targetRow = (SimpleScriptable)rows.jsxFunction_item(Integer.valueOf(targetIndex));
/* 104:165 */       targetRow.getDomNodeOrDie().insertBefore(sourceRow.getDomNodeOrDie());
/* 105:166 */       return sourceRow;
/* 106:    */     }
/* 107:168 */     return null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String jsxGet_align()
/* 111:    */   {
/* 112:176 */     return getAlign(true);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void jsxSet_align(String align)
/* 116:    */   {
/* 117:184 */     setAlign(align, false);
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.RowContainer
 * JD-Core Version:    0.7.0.1
 */