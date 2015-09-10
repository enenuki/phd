/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import jxl.Cell;
/*   7:    */ import jxl.CellType;
/*   8:    */ import jxl.Range;
/*   9:    */ import jxl.WorkbookSettings;
/*  10:    */ import jxl.biff.SheetRangeImpl;
/*  11:    */ import jxl.common.Assert;
/*  12:    */ import jxl.common.Logger;
/*  13:    */ import jxl.write.Blank;
/*  14:    */ import jxl.write.WritableSheet;
/*  15:    */ import jxl.write.WriteException;
/*  16:    */ 
/*  17:    */ class MergedCells
/*  18:    */ {
/*  19: 47 */   private static Logger logger = Logger.getLogger(MergedCells.class);
/*  20:    */   private ArrayList ranges;
/*  21:    */   private WritableSheet sheet;
/*  22:    */   private static final int maxRangesPerSheet = 1020;
/*  23:    */   
/*  24:    */   public MergedCells(WritableSheet ws)
/*  25:    */   {
/*  26: 69 */     this.ranges = new ArrayList();
/*  27: 70 */     this.sheet = ws;
/*  28:    */   }
/*  29:    */   
/*  30:    */   void add(Range r)
/*  31:    */   {
/*  32: 81 */     this.ranges.add(r);
/*  33:    */   }
/*  34:    */   
/*  35:    */   void insertRow(int row)
/*  36:    */   {
/*  37: 90 */     SheetRangeImpl sr = null;
/*  38: 91 */     Iterator i = this.ranges.iterator();
/*  39: 92 */     while (i.hasNext())
/*  40:    */     {
/*  41: 94 */       sr = (SheetRangeImpl)i.next();
/*  42: 95 */       sr.insertRow(row);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   void insertColumn(int col)
/*  47:    */   {
/*  48:104 */     SheetRangeImpl sr = null;
/*  49:105 */     Iterator i = this.ranges.iterator();
/*  50:106 */     while (i.hasNext())
/*  51:    */     {
/*  52:108 */       sr = (SheetRangeImpl)i.next();
/*  53:109 */       sr.insertColumn(col);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   void removeColumn(int col)
/*  58:    */   {
/*  59:118 */     SheetRangeImpl sr = null;
/*  60:119 */     Iterator i = this.ranges.iterator();
/*  61:120 */     while (i.hasNext())
/*  62:    */     {
/*  63:122 */       sr = (SheetRangeImpl)i.next();
/*  64:123 */       if ((sr.getTopLeft().getColumn() == col) && (sr.getBottomRight().getColumn() == col)) {
/*  65:128 */         i.remove();
/*  66:    */       } else {
/*  67:132 */         sr.removeColumn(col);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   void removeRow(int row)
/*  73:    */   {
/*  74:142 */     SheetRangeImpl sr = null;
/*  75:143 */     Iterator i = this.ranges.iterator();
/*  76:144 */     while (i.hasNext())
/*  77:    */     {
/*  78:146 */       sr = (SheetRangeImpl)i.next();
/*  79:147 */       if ((sr.getTopLeft().getRow() == row) && (sr.getBottomRight().getRow() == row)) {
/*  80:152 */         i.remove();
/*  81:    */       } else {
/*  82:156 */         sr.removeRow(row);
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   Range[] getMergedCells()
/*  88:    */   {
/*  89:168 */     Range[] cells = new Range[this.ranges.size()];
/*  90:170 */     for (int i = 0; i < cells.length; i++) {
/*  91:172 */       cells[i] = ((Range)this.ranges.get(i));
/*  92:    */     }
/*  93:175 */     return cells;
/*  94:    */   }
/*  95:    */   
/*  96:    */   void unmergeCells(Range r)
/*  97:    */   {
/*  98:186 */     int index = this.ranges.indexOf(r);
/*  99:188 */     if (index != -1) {
/* 100:190 */       this.ranges.remove(index);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void checkIntersections()
/* 105:    */   {
/* 106:199 */     ArrayList newcells = new ArrayList(this.ranges.size());
/* 107:201 */     for (Iterator mci = this.ranges.iterator(); mci.hasNext();)
/* 108:    */     {
/* 109:203 */       SheetRangeImpl r = (SheetRangeImpl)mci.next();
/* 110:    */       
/* 111:    */ 
/* 112:206 */       Iterator i = newcells.iterator();
/* 113:207 */       SheetRangeImpl range = null;
/* 114:208 */       boolean intersects = false;
/* 115:209 */       while ((i.hasNext()) && (!intersects))
/* 116:    */       {
/* 117:211 */         range = (SheetRangeImpl)i.next();
/* 118:213 */         if (range.intersects(r))
/* 119:    */         {
/* 120:215 */           logger.warn("Could not merge cells " + r + " as they clash with an existing set of merged cells.");
/* 121:    */           
/* 122:    */ 
/* 123:218 */           intersects = true;
/* 124:    */         }
/* 125:    */       }
/* 126:222 */       if (!intersects) {
/* 127:224 */         newcells.add(r);
/* 128:    */       }
/* 129:    */     }
/* 130:228 */     this.ranges = newcells;
/* 131:    */   }
/* 132:    */   
/* 133:    */   private void checkRanges()
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:239 */       SheetRangeImpl range = null;
/* 138:242 */       for (int i = 0; i < this.ranges.size(); i++)
/* 139:    */       {
/* 140:244 */         range = (SheetRangeImpl)this.ranges.get(i);
/* 141:    */         
/* 142:    */ 
/* 143:247 */         Cell tl = range.getTopLeft();
/* 144:248 */         Cell br = range.getBottomRight();
/* 145:249 */         boolean found = false;
/* 146:251 */         for (int c = tl.getColumn(); c <= br.getColumn(); c++) {
/* 147:253 */           for (int r = tl.getRow(); r <= br.getRow(); r++)
/* 148:    */           {
/* 149:255 */             Cell cell = this.sheet.getCell(c, r);
/* 150:256 */             if (cell.getType() != CellType.EMPTY) {
/* 151:258 */               if (!found)
/* 152:    */               {
/* 153:260 */                 found = true;
/* 154:    */               }
/* 155:    */               else
/* 156:    */               {
/* 157:264 */                 logger.warn("Range " + range + " contains more than one data cell.  " + "Setting the other cells to blank.");
/* 158:    */                 
/* 159:    */ 
/* 160:267 */                 Blank b = new Blank(c, r);
/* 161:268 */                 this.sheet.addCell(b);
/* 162:    */               }
/* 163:    */             }
/* 164:    */           }
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:    */     catch (WriteException e)
/* 169:    */     {
/* 170:278 */       Assert.verify(false);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   void write(File outputFile)
/* 175:    */     throws IOException
/* 176:    */   {
/* 177:284 */     if (this.ranges.size() == 0) {
/* 178:286 */       return;
/* 179:    */     }
/* 180:289 */     WorkbookSettings ws = ((WritableSheetImpl)this.sheet).getWorkbookSettings();
/* 181:292 */     if (!ws.getMergedCellCheckingDisabled())
/* 182:    */     {
/* 183:294 */       checkIntersections();
/* 184:295 */       checkRanges();
/* 185:    */     }
/* 186:300 */     if (this.ranges.size() < 1020)
/* 187:    */     {
/* 188:302 */       MergedCellsRecord mcr = new MergedCellsRecord(this.ranges);
/* 189:303 */       outputFile.write(mcr);
/* 190:304 */       return;
/* 191:    */     }
/* 192:307 */     int numRecordsRequired = this.ranges.size() / 1020 + 1;
/* 193:308 */     int pos = 0;
/* 194:310 */     for (int i = 0; i < numRecordsRequired; i++)
/* 195:    */     {
/* 196:312 */       int numranges = Math.min(1020, this.ranges.size() - pos);
/* 197:    */       
/* 198:314 */       ArrayList cells = new ArrayList(numranges);
/* 199:315 */       for (int j = 0; j < numranges; j++) {
/* 200:317 */         cells.add(this.ranges.get(pos + j));
/* 201:    */       }
/* 202:320 */       MergedCellsRecord mcr = new MergedCellsRecord(cells);
/* 203:321 */       outputFile.write(mcr);
/* 204:    */       
/* 205:323 */       pos += numranges;
/* 206:    */     }
/* 207:    */   }
/* 208:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.MergedCells
 * JD-Core Version:    0.7.0.1
 */