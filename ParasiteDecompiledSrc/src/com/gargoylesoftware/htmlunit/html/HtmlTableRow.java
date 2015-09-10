/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.NoSuchElementException;
/*  10:    */ 
/*  11:    */ public class HtmlTableRow
/*  12:    */   extends HtmlElement
/*  13:    */ {
/*  14:    */   public static final String TAG_NAME = "tr";
/*  15:    */   
/*  16:    */   HtmlTableRow(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  17:    */   {
/*  18: 50 */     super(namespaceURI, qualifiedName, page, attributes);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public CellIterator getCellIterator()
/*  22:    */   {
/*  23: 57 */     return new CellIterator();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<HtmlTableCell> getCells()
/*  27:    */   {
/*  28: 65 */     List<HtmlTableCell> result = new ArrayList();
/*  29: 66 */     for (CellIterator iterator = getCellIterator(); iterator.hasNext();) {
/*  30: 67 */       result.add(iterator.next());
/*  31:    */     }
/*  32: 69 */     return Collections.unmodifiableList(result);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public HtmlTableCell getCell(int index)
/*  36:    */     throws IndexOutOfBoundsException
/*  37:    */   {
/*  38: 78 */     int count = 0;
/*  39: 79 */     for (CellIterator iterator = getCellIterator(); iterator.hasNext(); count++)
/*  40:    */     {
/*  41: 80 */       HtmlTableCell next = iterator.nextCell();
/*  42: 81 */       if (count == index) {
/*  43: 82 */         return next;
/*  44:    */       }
/*  45:    */     }
/*  46: 85 */     throw new IndexOutOfBoundsException();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String getAlignAttribute()
/*  50:    */   {
/*  51: 97 */     return getAttribute("align");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final String getCharAttribute()
/*  55:    */   {
/*  56:109 */     return getAttribute("char");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final String getCharoffAttribute()
/*  60:    */   {
/*  61:121 */     return getAttribute("charoff");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String getValignAttribute()
/*  65:    */   {
/*  66:133 */     return getAttribute("valign");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public HtmlTable getEnclosingTable()
/*  70:    */   {
/*  71:141 */     return (HtmlTable)getEnclosingElement("table");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String getBgcolorAttribute()
/*  75:    */   {
/*  76:153 */     return getAttribute("bgcolor");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public class CellIterator
/*  80:    */     implements Iterator<HtmlTableCell>
/*  81:    */   {
/*  82:    */     private HtmlTableCell nextCell_;
/*  83:    */     private HtmlForm currentForm_;
/*  84:    */     
/*  85:    */     public CellIterator()
/*  86:    */     {
/*  87:166 */       setNextCell(HtmlTableRow.this.getFirstChild());
/*  88:    */     }
/*  89:    */     
/*  90:    */     public boolean hasNext()
/*  91:    */     {
/*  92:171 */       return this.nextCell_ != null;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public HtmlTableCell next()
/*  96:    */       throws NoSuchElementException
/*  97:    */     {
/*  98:179 */       return nextCell();
/*  99:    */     }
/* 100:    */     
/* 101:    */     public void remove()
/* 102:    */       throws IllegalStateException
/* 103:    */     {
/* 104:187 */       if (this.nextCell_ == null) {
/* 105:188 */         throw new IllegalStateException();
/* 106:    */       }
/* 107:190 */       DomNode sibling = this.nextCell_.getPreviousSibling();
/* 108:191 */       if (sibling != null) {
/* 109:192 */         sibling.remove();
/* 110:    */       }
/* 111:    */     }
/* 112:    */     
/* 113:    */     public HtmlTableCell nextCell()
/* 114:    */       throws NoSuchElementException
/* 115:    */     {
/* 116:201 */       if (this.nextCell_ != null)
/* 117:    */       {
/* 118:202 */         HtmlTableCell result = this.nextCell_;
/* 119:203 */         setNextCell(this.nextCell_.getNextSibling());
/* 120:204 */         return result;
/* 121:    */       }
/* 122:206 */       throw new NoSuchElementException();
/* 123:    */     }
/* 124:    */     
/* 125:    */     private void setNextCell(DomNode node)
/* 126:    */     {
/* 127:215 */       this.nextCell_ = null;
/* 128:216 */       for (DomNode next = node; next != null; next = next.getNextSibling())
/* 129:    */       {
/* 130:217 */         if ((next instanceof HtmlTableCell))
/* 131:    */         {
/* 132:218 */           this.nextCell_ = ((HtmlTableCell)next);
/* 133:219 */           return;
/* 134:    */         }
/* 135:221 */         if ((this.currentForm_ == null) && ((next instanceof HtmlForm)))
/* 136:    */         {
/* 137:223 */           this.currentForm_ = ((HtmlForm)next);
/* 138:224 */           setNextCell(next.getFirstChild());
/* 139:225 */           return;
/* 140:    */         }
/* 141:    */       }
/* 142:228 */       if (this.currentForm_ != null)
/* 143:    */       {
/* 144:229 */         DomNode form = this.currentForm_;
/* 145:230 */         this.currentForm_ = null;
/* 146:231 */         setNextCell(form.getNextSibling());
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTableRow
 * JD-Core Version:    0.7.0.1
 */