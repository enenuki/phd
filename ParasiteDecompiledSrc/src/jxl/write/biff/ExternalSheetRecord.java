/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import jxl.biff.IntegerHelper;
/*   6:    */ import jxl.biff.Type;
/*   7:    */ import jxl.biff.WritableRecordData;
/*   8:    */ 
/*   9:    */ class ExternalSheetRecord
/*  10:    */   extends WritableRecordData
/*  11:    */ {
/*  12:    */   private byte[] data;
/*  13:    */   private ArrayList xtis;
/*  14:    */   
/*  15:    */   private static class XTI
/*  16:    */   {
/*  17:    */     int supbookIndex;
/*  18:    */     int firstTab;
/*  19:    */     int lastTab;
/*  20:    */     
/*  21:    */     XTI(int s, int f, int l)
/*  22:    */     {
/*  23: 56 */       this.supbookIndex = s;
/*  24: 57 */       this.firstTab = f;
/*  25: 58 */       this.lastTab = l;
/*  26:    */     }
/*  27:    */     
/*  28:    */     void sheetInserted(int index)
/*  29:    */     {
/*  30: 63 */       if (this.firstTab >= index) {
/*  31: 65 */         this.firstTab += 1;
/*  32:    */       }
/*  33: 68 */       if (this.lastTab >= index) {
/*  34: 70 */         this.lastTab += 1;
/*  35:    */       }
/*  36:    */     }
/*  37:    */     
/*  38:    */     void sheetRemoved(int index)
/*  39:    */     {
/*  40: 76 */       if (this.firstTab == index) {
/*  41: 78 */         this.firstTab = 0;
/*  42:    */       }
/*  43: 81 */       if (this.lastTab == index) {
/*  44: 83 */         this.lastTab = 0;
/*  45:    */       }
/*  46: 86 */       if (this.firstTab > index) {
/*  47: 88 */         this.firstTab -= 1;
/*  48:    */       }
/*  49: 91 */       if (this.lastTab > index) {
/*  50: 93 */         this.lastTab -= 1;
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ExternalSheetRecord(jxl.read.biff.ExternalSheetRecord esf)
/*  56:    */   {
/*  57:105 */     super(Type.EXTERNSHEET);
/*  58:    */     
/*  59:107 */     this.xtis = new ArrayList(esf.getNumRecords());
/*  60:108 */     XTI xti = null;
/*  61:109 */     for (int i = 0; i < esf.getNumRecords(); i++)
/*  62:    */     {
/*  63:111 */       xti = new XTI(esf.getSupbookIndex(i), esf.getFirstTabIndex(i), esf.getLastTabIndex(i));
/*  64:    */       
/*  65:    */ 
/*  66:114 */       this.xtis.add(xti);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ExternalSheetRecord()
/*  71:    */   {
/*  72:123 */     super(Type.EXTERNSHEET);
/*  73:124 */     this.xtis = new ArrayList();
/*  74:    */   }
/*  75:    */   
/*  76:    */   int getIndex(int supbookind, int sheetind)
/*  77:    */   {
/*  78:135 */     Iterator i = this.xtis.iterator();
/*  79:136 */     XTI xti = null;
/*  80:137 */     boolean found = false;
/*  81:138 */     int pos = 0;
/*  82:139 */     while ((i.hasNext()) && (!found))
/*  83:    */     {
/*  84:141 */       xti = (XTI)i.next();
/*  85:143 */       if ((xti.supbookIndex == supbookind) && (xti.firstTab == sheetind)) {
/*  86:146 */         found = true;
/*  87:    */       } else {
/*  88:150 */         pos++;
/*  89:    */       }
/*  90:    */     }
/*  91:154 */     if (!found)
/*  92:    */     {
/*  93:156 */       xti = new XTI(supbookind, sheetind, sheetind);
/*  94:157 */       this.xtis.add(xti);
/*  95:158 */       pos = this.xtis.size() - 1;
/*  96:    */     }
/*  97:161 */     return pos;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public byte[] getData()
/* 101:    */   {
/* 102:171 */     byte[] data = new byte[2 + this.xtis.size() * 6];
/* 103:    */     
/* 104:173 */     int pos = 0;
/* 105:174 */     IntegerHelper.getTwoBytes(this.xtis.size(), data, 0);
/* 106:175 */     pos += 2;
/* 107:    */     
/* 108:177 */     Iterator i = this.xtis.iterator();
/* 109:178 */     XTI xti = null;
/* 110:179 */     while (i.hasNext())
/* 111:    */     {
/* 112:181 */       xti = (XTI)i.next();
/* 113:182 */       IntegerHelper.getTwoBytes(xti.supbookIndex, data, pos);
/* 114:183 */       IntegerHelper.getTwoBytes(xti.firstTab, data, pos + 2);
/* 115:184 */       IntegerHelper.getTwoBytes(xti.lastTab, data, pos + 4);
/* 116:185 */       pos += 6;
/* 117:    */     }
/* 118:188 */     return data;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getSupbookIndex(int index)
/* 122:    */   {
/* 123:199 */     return ((XTI)this.xtis.get(index)).supbookIndex;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getFirstTabIndex(int index)
/* 127:    */   {
/* 128:210 */     return ((XTI)this.xtis.get(index)).firstTab;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getLastTabIndex(int index)
/* 132:    */   {
/* 133:221 */     return ((XTI)this.xtis.get(index)).lastTab;
/* 134:    */   }
/* 135:    */   
/* 136:    */   void sheetInserted(int index)
/* 137:    */   {
/* 138:230 */     XTI xti = null;
/* 139:231 */     for (Iterator i = this.xtis.iterator(); i.hasNext();)
/* 140:    */     {
/* 141:233 */       xti = (XTI)i.next();
/* 142:234 */       xti.sheetInserted(index);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   void sheetRemoved(int index)
/* 147:    */   {
/* 148:244 */     XTI xti = null;
/* 149:245 */     for (Iterator i = this.xtis.iterator(); i.hasNext();)
/* 150:    */     {
/* 151:247 */       xti = (XTI)i.next();
/* 152:248 */       xti.sheetRemoved(index);
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.ExternalSheetRecord
 * JD-Core Version:    0.7.0.1
 */