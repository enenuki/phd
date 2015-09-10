/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.NoSuchElementException;
/*  11:    */ 
/*  12:    */ public class HtmlTable
/*  13:    */   extends HtmlElement
/*  14:    */ {
/*  15:    */   public static final String TAG_NAME = "table";
/*  16:    */   
/*  17:    */   HtmlTable(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  18:    */   {
/*  19: 51 */     super(namespaceURI, qualifiedName, page, attributes);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public final HtmlTableCell getCellAt(int rowIndex, int columnIndex)
/*  23:    */   {
/*  24: 62 */     RowIterator rowIterator = getRowIterator();
/*  25: 63 */     for (int rowNo = 0; rowIterator.hasNext(); rowNo++)
/*  26:    */     {
/*  27: 64 */       HtmlTableRow row = rowIterator.nextRow();
/*  28: 65 */       HtmlTableRow.CellIterator cellIterator = row.getCellIterator();
/*  29: 66 */       for (int colNo = 0; cellIterator.hasNext(); colNo++)
/*  30:    */       {
/*  31: 67 */         HtmlTableCell cell = cellIterator.nextCell();
/*  32: 68 */         if ((rowNo <= rowIndex) && (rowNo + cell.getRowSpan() > rowIndex) && 
/*  33: 69 */           (colNo <= columnIndex) && (colNo + cell.getColumnSpan() > columnIndex)) {
/*  34: 70 */           return cell;
/*  35:    */         }
/*  36:    */       }
/*  37:    */     }
/*  38: 75 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private RowIterator getRowIterator()
/*  42:    */   {
/*  43: 82 */     return new RowIterator();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<HtmlTableRow> getRows()
/*  47:    */   {
/*  48: 90 */     List<HtmlTableRow> result = new ArrayList();
/*  49: 91 */     for (RowIterator iterator = getRowIterator(); iterator.hasNext();) {
/*  50: 92 */       result.add(iterator.next());
/*  51:    */     }
/*  52: 94 */     return Collections.unmodifiableList(result);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public HtmlTableRow getRow(int index)
/*  56:    */     throws IndexOutOfBoundsException
/*  57:    */   {
/*  58:104 */     int count = 0;
/*  59:105 */     for (RowIterator iterator = getRowIterator(); iterator.hasNext(); count++)
/*  60:    */     {
/*  61:106 */       HtmlTableRow next = iterator.nextRow();
/*  62:107 */       if (count == index) {
/*  63:108 */         return next;
/*  64:    */       }
/*  65:    */     }
/*  66:111 */     throw new IndexOutOfBoundsException();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final int getRowCount()
/*  70:    */   {
/*  71:121 */     int count = 0;
/*  72:122 */     for (RowIterator iterator = getRowIterator(); iterator.hasNext(); iterator.next()) {
/*  73:123 */       count++;
/*  74:    */     }
/*  75:125 */     return count;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final HtmlTableRow getRowById(String id)
/*  79:    */     throws ElementNotFoundException
/*  80:    */   {
/*  81:136 */     RowIterator iterator = new RowIterator();
/*  82:137 */     while (iterator.hasNext())
/*  83:    */     {
/*  84:138 */       HtmlTableRow row = iterator.next();
/*  85:139 */       if (row.getAttribute("id").equals(id)) {
/*  86:140 */         return row;
/*  87:    */       }
/*  88:    */     }
/*  89:143 */     throw new ElementNotFoundException("tr", "id", id);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getCaptionText()
/*  93:    */   {
/*  94:152 */     for (HtmlElement element : getChildElements()) {
/*  95:153 */       if ((element instanceof HtmlCaption)) {
/*  96:154 */         return element.asText();
/*  97:    */       }
/*  98:    */     }
/*  99:157 */     return null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public HtmlTableHeader getHeader()
/* 103:    */   {
/* 104:166 */     for (HtmlElement element : getChildElements()) {
/* 105:167 */       if ((element instanceof HtmlTableHeader)) {
/* 106:168 */         return (HtmlTableHeader)element;
/* 107:    */       }
/* 108:    */     }
/* 109:171 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public HtmlTableFooter getFooter()
/* 113:    */   {
/* 114:180 */     for (HtmlElement element : getChildElements()) {
/* 115:181 */       if ((element instanceof HtmlTableFooter)) {
/* 116:182 */         return (HtmlTableFooter)element;
/* 117:    */       }
/* 118:    */     }
/* 119:185 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public List<HtmlTableBody> getBodies()
/* 123:    */   {
/* 124:195 */     List<HtmlTableBody> bodies = new ArrayList();
/* 125:196 */     for (HtmlElement element : getChildElements()) {
/* 126:197 */       if ((element instanceof HtmlTableBody)) {
/* 127:198 */         bodies.add((HtmlTableBody)element);
/* 128:    */       }
/* 129:    */     }
/* 130:201 */     return bodies;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public final String getSummaryAttribute()
/* 134:    */   {
/* 135:213 */     return getAttribute("summary");
/* 136:    */   }
/* 137:    */   
/* 138:    */   public final String getWidthAttribute()
/* 139:    */   {
/* 140:225 */     return getAttribute("width");
/* 141:    */   }
/* 142:    */   
/* 143:    */   public final String getBorderAttribute()
/* 144:    */   {
/* 145:237 */     return getAttribute("border");
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final String getFrameAttribute()
/* 149:    */   {
/* 150:249 */     return getAttribute("frame");
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final String getRulesAttribute()
/* 154:    */   {
/* 155:261 */     return getAttribute("rules");
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final String getCellSpacingAttribute()
/* 159:    */   {
/* 160:273 */     return getAttribute("cellspacing");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final String getCellPaddingAttribute()
/* 164:    */   {
/* 165:285 */     return getAttribute("cellpadding");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public final String getAlignAttribute()
/* 169:    */   {
/* 170:297 */     return getAttribute("align");
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final String getBgcolorAttribute()
/* 174:    */   {
/* 175:309 */     return getAttribute("bgcolor");
/* 176:    */   }
/* 177:    */   
/* 178:    */   private class RowIterator
/* 179:    */     implements Iterator<HtmlTableRow>, Iterable<HtmlTableRow>
/* 180:    */   {
/* 181:    */     private HtmlTableRow nextRow_;
/* 182:    */     private TableRowGroup currentGroup_;
/* 183:    */     
/* 184:    */     public RowIterator()
/* 185:    */     {
/* 186:322 */       setNextRow(HtmlTable.this.getFirstChild());
/* 187:    */     }
/* 188:    */     
/* 189:    */     public boolean hasNext()
/* 190:    */     {
/* 191:329 */       return this.nextRow_ != null;
/* 192:    */     }
/* 193:    */     
/* 194:    */     public HtmlTableRow next()
/* 195:    */       throws NoSuchElementException
/* 196:    */     {
/* 197:337 */       return nextRow();
/* 198:    */     }
/* 199:    */     
/* 200:    */     public void remove()
/* 201:    */       throws IllegalStateException
/* 202:    */     {
/* 203:345 */       if (this.nextRow_ == null) {
/* 204:346 */         throw new IllegalStateException();
/* 205:    */       }
/* 206:348 */       DomNode sibling = this.nextRow_.getPreviousSibling();
/* 207:349 */       if (sibling != null) {
/* 208:350 */         sibling.remove();
/* 209:    */       }
/* 210:    */     }
/* 211:    */     
/* 212:    */     public HtmlTableRow nextRow()
/* 213:    */       throws NoSuchElementException
/* 214:    */     {
/* 215:359 */       if (this.nextRow_ != null)
/* 216:    */       {
/* 217:360 */         HtmlTableRow result = this.nextRow_;
/* 218:361 */         setNextRow(this.nextRow_.getNextSibling());
/* 219:362 */         return result;
/* 220:    */       }
/* 221:364 */       throw new NoSuchElementException();
/* 222:    */     }
/* 223:    */     
/* 224:    */     private void setNextRow(DomNode node)
/* 225:    */     {
/* 226:373 */       this.nextRow_ = null;
/* 227:374 */       for (DomNode next = node; next != null; next = next.getNextSibling())
/* 228:    */       {
/* 229:375 */         if ((next instanceof HtmlTableRow))
/* 230:    */         {
/* 231:376 */           this.nextRow_ = ((HtmlTableRow)next);
/* 232:377 */           return;
/* 233:    */         }
/* 234:379 */         if ((this.currentGroup_ == null) && ((next instanceof TableRowGroup)))
/* 235:    */         {
/* 236:380 */           this.currentGroup_ = ((TableRowGroup)next);
/* 237:381 */           setNextRow(next.getFirstChild());
/* 238:382 */           return;
/* 239:    */         }
/* 240:    */       }
/* 241:385 */       if (this.currentGroup_ != null)
/* 242:    */       {
/* 243:386 */         DomNode group = this.currentGroup_;
/* 244:387 */         this.currentGroup_ = null;
/* 245:388 */         setNextRow(group.getNextSibling());
/* 246:    */       }
/* 247:    */     }
/* 248:    */     
/* 249:    */     public Iterator<HtmlTableRow> iterator()
/* 250:    */     {
/* 251:393 */       return this;
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected boolean isBlock()
/* 256:    */   {
/* 257:402 */     return true;
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected boolean isEmptyXmlTagExpanded()
/* 261:    */   {
/* 262:411 */     return true;
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTable
 * JD-Core Version:    0.7.0.1
 */