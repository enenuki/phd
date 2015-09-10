/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.Range;
/*   5:    */ import jxl.Sheet;
/*   6:    */ 
/*   7:    */ public class SheetRangeImpl
/*   8:    */   implements Range
/*   9:    */ {
/*  10:    */   private Sheet sheet;
/*  11:    */   private int column1;
/*  12:    */   private int row1;
/*  13:    */   private int column2;
/*  14:    */   private int row2;
/*  15:    */   
/*  16:    */   public SheetRangeImpl(Sheet s, int c1, int r1, int c2, int r2)
/*  17:    */   {
/*  18: 70 */     this.sheet = s;
/*  19: 71 */     this.row1 = r1;
/*  20: 72 */     this.row2 = r2;
/*  21: 73 */     this.column1 = c1;
/*  22: 74 */     this.column2 = c2;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public SheetRangeImpl(SheetRangeImpl c, Sheet s)
/*  26:    */   {
/*  27: 85 */     this.sheet = s;
/*  28: 86 */     this.row1 = c.row1;
/*  29: 87 */     this.row2 = c.row2;
/*  30: 88 */     this.column1 = c.column1;
/*  31: 89 */     this.column2 = c.column2;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Cell getTopLeft()
/*  35:    */   {
/*  36:101 */     if ((this.column1 >= this.sheet.getColumns()) || (this.row1 >= this.sheet.getRows())) {
/*  37:104 */       return new EmptyCell(this.column1, this.row1);
/*  38:    */     }
/*  39:107 */     return this.sheet.getCell(this.column1, this.row1);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Cell getBottomRight()
/*  43:    */   {
/*  44:119 */     if ((this.column2 >= this.sheet.getColumns()) || (this.row2 >= this.sheet.getRows())) {
/*  45:122 */       return new EmptyCell(this.column2, this.row2);
/*  46:    */     }
/*  47:125 */     return this.sheet.getCell(this.column2, this.row2);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getFirstSheetIndex()
/*  51:    */   {
/*  52:136 */     return -1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getLastSheetIndex()
/*  56:    */   {
/*  57:147 */     return -1;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean intersects(SheetRangeImpl range)
/*  61:    */   {
/*  62:161 */     if (range == this) {
/*  63:163 */       return true;
/*  64:    */     }
/*  65:166 */     if ((this.row2 < range.row1) || (this.row1 > range.row2) || (this.column2 < range.column1) || (this.column1 > range.column2)) {
/*  66:171 */       return false;
/*  67:    */     }
/*  68:174 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String toString()
/*  72:    */   {
/*  73:184 */     StringBuffer sb = new StringBuffer();
/*  74:185 */     CellReferenceHelper.getCellReference(this.column1, this.row1, sb);
/*  75:186 */     sb.append('-');
/*  76:187 */     CellReferenceHelper.getCellReference(this.column2, this.row2, sb);
/*  77:188 */     return sb.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void insertRow(int r)
/*  81:    */   {
/*  82:198 */     if (r > this.row2) {
/*  83:200 */       return;
/*  84:    */     }
/*  85:203 */     if (r <= this.row1) {
/*  86:205 */       this.row1 += 1;
/*  87:    */     }
/*  88:208 */     if (r <= this.row2) {
/*  89:210 */       this.row2 += 1;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void insertColumn(int c)
/*  94:    */   {
/*  95:221 */     if (c > this.column2) {
/*  96:223 */       return;
/*  97:    */     }
/*  98:226 */     if (c <= this.column1) {
/*  99:228 */       this.column1 += 1;
/* 100:    */     }
/* 101:231 */     if (c <= this.column2) {
/* 102:233 */       this.column2 += 1;
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void removeRow(int r)
/* 107:    */   {
/* 108:244 */     if (r > this.row2) {
/* 109:246 */       return;
/* 110:    */     }
/* 111:249 */     if (r < this.row1) {
/* 112:251 */       this.row1 -= 1;
/* 113:    */     }
/* 114:254 */     if (r < this.row2) {
/* 115:256 */       this.row2 -= 1;
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void removeColumn(int c)
/* 120:    */   {
/* 121:267 */     if (c > this.column2) {
/* 122:269 */       return;
/* 123:    */     }
/* 124:272 */     if (c < this.column1) {
/* 125:274 */       this.column1 -= 1;
/* 126:    */     }
/* 127:277 */     if (c < this.column2) {
/* 128:279 */       this.column2 -= 1;
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int hashCode()
/* 133:    */   {
/* 134:290 */     return 0xFFFF ^ this.row1 ^ this.row2 ^ this.column1 ^ this.column2;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean equals(Object o)
/* 138:    */   {
/* 139:301 */     if (o == this) {
/* 140:303 */       return true;
/* 141:    */     }
/* 142:306 */     if (!(o instanceof SheetRangeImpl)) {
/* 143:308 */       return false;
/* 144:    */     }
/* 145:311 */     SheetRangeImpl compare = (SheetRangeImpl)o;
/* 146:    */     
/* 147:313 */     return (this.column1 == compare.column1) && (this.column2 == compare.column2) && (this.row1 == compare.row1) && (this.row2 == compare.row2);
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.SheetRangeImpl
 * JD-Core Version:    0.7.0.1
 */