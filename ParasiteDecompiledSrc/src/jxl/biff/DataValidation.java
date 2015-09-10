/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import jxl.WorkbookSettings;
/*   7:    */ import jxl.biff.formula.ExternalSheet;
/*   8:    */ import jxl.common.Assert;
/*   9:    */ import jxl.common.Logger;
/*  10:    */ import jxl.write.biff.File;
/*  11:    */ 
/*  12:    */ public class DataValidation
/*  13:    */ {
/*  14: 44 */   private static Logger logger = Logger.getLogger(DataValidation.class);
/*  15:    */   private DataValidityListRecord validityList;
/*  16:    */   private ArrayList validitySettings;
/*  17:    */   private WorkbookMethods workbook;
/*  18:    */   private ExternalSheet externalSheet;
/*  19:    */   private WorkbookSettings workbookSettings;
/*  20:    */   private int comboBoxObjectId;
/*  21:    */   private boolean copied;
/*  22:    */   public static final int DEFAULT_OBJECT_ID = -1;
/*  23:    */   private static final int MAX_NO_OF_VALIDITY_SETTINGS = 65533;
/*  24:    */   
/*  25:    */   public DataValidation(DataValidityListRecord dvlr)
/*  26:    */   {
/*  27: 90 */     this.validityList = dvlr;
/*  28: 91 */     this.validitySettings = new ArrayList(this.validityList.getNumberOfSettings());
/*  29: 92 */     this.copied = false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public DataValidation(int objId, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws)
/*  33:    */   {
/*  34:103 */     this.workbook = wm;
/*  35:104 */     this.externalSheet = es;
/*  36:105 */     this.workbookSettings = ws;
/*  37:106 */     this.validitySettings = new ArrayList();
/*  38:107 */     this.comboBoxObjectId = objId;
/*  39:108 */     this.copied = false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public DataValidation(DataValidation dv, ExternalSheet es, WorkbookMethods wm, WorkbookSettings ws)
/*  43:    */   {
/*  44:119 */     this.workbook = wm;
/*  45:120 */     this.externalSheet = es;
/*  46:121 */     this.workbookSettings = ws;
/*  47:122 */     this.copied = true;
/*  48:123 */     this.validityList = new DataValidityListRecord(dv.getDataValidityList());
/*  49:    */     
/*  50:125 */     this.validitySettings = new ArrayList();
/*  51:126 */     DataValiditySettingsRecord[] settings = dv.getDataValiditySettings();
/*  52:128 */     for (int i = 0; i < settings.length; i++) {
/*  53:130 */       this.validitySettings.add(new DataValiditySettingsRecord(settings[i], this.externalSheet, this.workbook, this.workbookSettings));
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void add(DataValiditySettingsRecord dvsr)
/*  58:    */   {
/*  59:142 */     this.validitySettings.add(dvsr);
/*  60:143 */     dvsr.setDataValidation(this);
/*  61:145 */     if (this.copied)
/*  62:    */     {
/*  63:148 */       Assert.verify(this.validityList != null);
/*  64:149 */       this.validityList.dvAdded();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public DataValidityListRecord getDataValidityList()
/*  69:    */   {
/*  70:158 */     return this.validityList;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public DataValiditySettingsRecord[] getDataValiditySettings()
/*  74:    */   {
/*  75:166 */     DataValiditySettingsRecord[] dvlr = new DataValiditySettingsRecord[0];
/*  76:167 */     return (DataValiditySettingsRecord[])this.validitySettings.toArray(dvlr);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void write(File outputFile)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:178 */     if (this.validitySettings.size() > 65533)
/*  83:    */     {
/*  84:180 */       logger.warn("Maximum number of data validations exceeded - truncating...");
/*  85:    */       
/*  86:182 */       this.validitySettings = new ArrayList(this.validitySettings.subList(0, 65532));
/*  87:    */       
/*  88:184 */       Assert.verify(this.validitySettings.size() <= 65533);
/*  89:    */     }
/*  90:187 */     if (this.validityList == null)
/*  91:    */     {
/*  92:189 */       DValParser dvp = new DValParser(this.comboBoxObjectId, this.validitySettings.size());
/*  93:    */       
/*  94:191 */       this.validityList = new DataValidityListRecord(dvp);
/*  95:    */     }
/*  96:194 */     if (!this.validityList.hasDVRecords()) {
/*  97:196 */       return;
/*  98:    */     }
/*  99:199 */     outputFile.write(this.validityList);
/* 100:201 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 101:    */     {
/* 102:203 */       DataValiditySettingsRecord dvsr = (DataValiditySettingsRecord)i.next();
/* 103:204 */       outputFile.write(dvsr);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void insertRow(int row)
/* 108:    */   {
/* 109:215 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 110:    */     {
/* 111:217 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 112:218 */       dv.insertRow(row);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void removeRow(int row)
/* 117:    */   {
/* 118:229 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 119:    */     {
/* 120:231 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 121:233 */       if ((dv.getFirstRow() == row) && (dv.getLastRow() == row))
/* 122:    */       {
/* 123:235 */         i.remove();
/* 124:236 */         this.validityList.dvRemoved();
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:240 */         dv.removeRow(row);
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void insertColumn(int col)
/* 134:    */   {
/* 135:252 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 136:    */     {
/* 137:254 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 138:255 */       dv.insertColumn(col);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void removeColumn(int col)
/* 143:    */   {
/* 144:266 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 145:    */     {
/* 146:268 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 147:270 */       if ((dv.getFirstColumn() == col) && (dv.getLastColumn() == col))
/* 148:    */       {
/* 149:272 */         i.remove();
/* 150:273 */         this.validityList.dvRemoved();
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:277 */         dv.removeColumn(col);
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void removeDataValidation(int col, int row)
/* 160:    */   {
/* 161:290 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 162:    */     {
/* 163:292 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 164:294 */       if ((dv.getFirstColumn() == col) && (dv.getLastColumn() == col) && (dv.getFirstRow() == row) && (dv.getLastRow() == row))
/* 165:    */       {
/* 166:297 */         i.remove();
/* 167:298 */         this.validityList.dvRemoved();
/* 168:299 */         break;
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void removeSharedDataValidation(int col1, int row1, int col2, int row2)
/* 174:    */   {
/* 175:313 */     for (Iterator i = this.validitySettings.iterator(); i.hasNext();)
/* 176:    */     {
/* 177:315 */       DataValiditySettingsRecord dv = (DataValiditySettingsRecord)i.next();
/* 178:317 */       if ((dv.getFirstColumn() == col1) && (dv.getLastColumn() == col2) && (dv.getFirstRow() == row1) && (dv.getLastRow() == row2))
/* 179:    */       {
/* 180:320 */         i.remove();
/* 181:321 */         this.validityList.dvRemoved();
/* 182:322 */         break;
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public DataValiditySettingsRecord getDataValiditySettings(int col, int row)
/* 188:    */   {
/* 189:333 */     boolean found = false;
/* 190:334 */     DataValiditySettingsRecord foundRecord = null;
/* 191:335 */     for (Iterator i = this.validitySettings.iterator(); (i.hasNext()) && (!found);)
/* 192:    */     {
/* 193:337 */       DataValiditySettingsRecord dvsr = (DataValiditySettingsRecord)i.next();
/* 194:338 */       if ((dvsr.getFirstColumn() == col) && (dvsr.getFirstRow() == row))
/* 195:    */       {
/* 196:340 */         found = true;
/* 197:341 */         foundRecord = dvsr;
/* 198:    */       }
/* 199:    */     }
/* 200:345 */     return foundRecord;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public int getComboBoxObjectId()
/* 204:    */   {
/* 205:353 */     return this.comboBoxObjectId;
/* 206:    */   }
/* 207:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.DataValidation
 * JD-Core Version:    0.7.0.1
 */