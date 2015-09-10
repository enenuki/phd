/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlTable;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  15:    */ 
/*  16:    */ public class HTMLTableRowElement
/*  17:    */   extends HTMLTableComponent
/*  18:    */ {
/*  19:    */   private HTMLCollection cells_;
/*  20:    */   
/*  21:    */   public int jsxGet_rowIndex()
/*  22:    */   {
/*  23: 55 */     HtmlTableRow row = (HtmlTableRow)getDomNodeOrDie();
/*  24: 56 */     HtmlTable table = row.getEnclosingTable();
/*  25: 57 */     if (table == null) {
/*  26: 58 */       return -1;
/*  27:    */     }
/*  28: 60 */     return table.getRows().indexOf(row);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int jsxGet_sectionRowIndex()
/*  32:    */   {
/*  33: 71 */     DomNode row = getDomNodeOrDie();
/*  34: 72 */     HtmlTable table = ((HtmlTableRow)row).getEnclosingTable();
/*  35: 73 */     if (table == null)
/*  36:    */     {
/*  37: 74 */       if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_108)) {
/*  38: 75 */         return -1;
/*  39:    */       }
/*  40: 79 */       return 5461640;
/*  41:    */     }
/*  42: 81 */     int index = -1;
/*  43: 82 */     while (row != null)
/*  44:    */     {
/*  45: 83 */       if ((row instanceof HtmlTableRow)) {
/*  46: 84 */         index++;
/*  47:    */       }
/*  48: 86 */       row = row.getPreviousSibling();
/*  49:    */     }
/*  50: 88 */     return index;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object jsxGet_cells()
/*  54:    */   {
/*  55: 96 */     if (this.cells_ == null)
/*  56:    */     {
/*  57: 97 */       final HtmlTableRow row = (HtmlTableRow)getDomNodeOrDie();
/*  58: 98 */       this.cells_ = new HTMLCollection(row, false, "cells")
/*  59:    */       {
/*  60:    */         protected List<Object> computeElements()
/*  61:    */         {
/*  62:101 */           return new ArrayList(row.getCells());
/*  63:    */         }
/*  64:    */       };
/*  65:    */     }
/*  66:105 */     return this.cells_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String jsxGet_bgColor()
/*  70:    */   {
/*  71:114 */     return getDomNodeOrDie().getAttribute("bgColor");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsxSet_bgColor(String bgColor)
/*  75:    */   {
/*  76:123 */     setColorAttribute("bgColor", bgColor);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object jsxFunction_insertCell(Object index)
/*  80:    */   {
/*  81:136 */     int position = -1;
/*  82:137 */     if (index != Undefined.instance) {
/*  83:138 */       position = (int)Context.toNumber(index);
/*  84:    */     }
/*  85:140 */     HtmlTableRow htmlRow = (HtmlTableRow)getDomNodeOrDie();
/*  86:    */     
/*  87:142 */     boolean indexValid = (position >= -1) && (position <= htmlRow.getCells().size());
/*  88:143 */     if (indexValid)
/*  89:    */     {
/*  90:144 */       HtmlElement newCell = ((HtmlPage)htmlRow.getPage()).createElement("td");
/*  91:145 */       if ((position == -1) || (position == htmlRow.getCells().size())) {
/*  92:146 */         htmlRow.appendChild(newCell);
/*  93:    */       } else {
/*  94:149 */         htmlRow.getCell(position).insertBefore(newCell);
/*  95:    */       }
/*  96:151 */       return getScriptableFor(newCell);
/*  97:    */     }
/*  98:153 */     throw Context.reportRuntimeError("Index or size is negative or greater than the allowed amount");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void jsxFunction_deleteCell(Object index)
/* 102:    */   {
/* 103:164 */     int position = -1;
/* 104:165 */     if (index != Undefined.instance) {
/* 105:166 */       position = (int)Context.toNumber(index);
/* 106:168 */     } else if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_172)) {
/* 107:169 */       throw Context.reportRuntimeError("No enough arguments");
/* 108:    */     }
/* 109:172 */     HtmlTableRow htmlRow = (HtmlTableRow)getDomNodeOrDie();
/* 110:174 */     if (position == -1) {
/* 111:175 */       position = htmlRow.getCells().size() - 1;
/* 112:    */     }
/* 113:177 */     boolean indexValid = (position >= -1) && (position <= htmlRow.getCells().size());
/* 114:178 */     if (!indexValid) {
/* 115:179 */       throw Context.reportRuntimeError("Index or size is negative or greater than the allowed amount");
/* 116:    */     }
/* 117:182 */     htmlRow.getCell(position).remove();
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableRowElement
 * JD-Core Version:    0.7.0.1
 */