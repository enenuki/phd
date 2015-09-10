/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import jxl.biff.IntegerHelper;
/*   4:    */ import jxl.biff.Type;
/*   5:    */ import jxl.biff.WritableRecordData;
/*   6:    */ import jxl.common.Assert;
/*   7:    */ import jxl.common.Logger;
/*   8:    */ import jxl.read.biff.Record;
/*   9:    */ 
/*  10:    */ public class ObjRecord
/*  11:    */   extends WritableRecordData
/*  12:    */ {
/*  13: 39 */   private static final Logger logger = Logger.getLogger(ObjRecord.class);
/*  14:    */   private ObjType type;
/*  15:    */   private boolean read;
/*  16:    */   private int objectId;
/*  17:    */   
/*  18:    */   private static final class ObjType
/*  19:    */   {
/*  20:    */     public int value;
/*  21:    */     public String desc;
/*  22: 64 */     private static ObjType[] types = new ObjType[0];
/*  23:    */     
/*  24:    */     ObjType(int v, String d)
/*  25:    */     {
/*  26: 68 */       this.value = v;
/*  27: 69 */       this.desc = d;
/*  28:    */       
/*  29: 71 */       ObjType[] oldtypes = types;
/*  30: 72 */       types = new ObjType[types.length + 1];
/*  31: 73 */       System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
/*  32: 74 */       types[oldtypes.length] = this;
/*  33:    */     }
/*  34:    */     
/*  35:    */     public String toString()
/*  36:    */     {
/*  37: 79 */       return this.desc;
/*  38:    */     }
/*  39:    */     
/*  40:    */     public static ObjType getType(int val)
/*  41:    */     {
/*  42: 84 */       ObjType retval = ObjRecord.UNKNOWN;
/*  43: 85 */       for (int i = 0; (i < types.length) && (retval == ObjRecord.UNKNOWN); i++) {
/*  44: 87 */         if (types[i].value == val) {
/*  45: 89 */           retval = types[i];
/*  46:    */         }
/*  47:    */       }
/*  48: 92 */       return retval;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52: 97 */   public static final ObjType GROUP = new ObjType(0, "Group");
/*  53: 98 */   public static final ObjType LINE = new ObjType(1, "Line");
/*  54: 99 */   public static final ObjType RECTANGLE = new ObjType(2, "Rectangle");
/*  55:100 */   public static final ObjType OVAL = new ObjType(3, "Oval");
/*  56:101 */   public static final ObjType ARC = new ObjType(4, "Arc");
/*  57:102 */   public static final ObjType CHART = new ObjType(5, "Chart");
/*  58:103 */   public static final ObjType TEXT = new ObjType(6, "Text");
/*  59:104 */   public static final ObjType BUTTON = new ObjType(7, "Button");
/*  60:105 */   public static final ObjType PICTURE = new ObjType(8, "Picture");
/*  61:106 */   public static final ObjType POLYGON = new ObjType(9, "Polygon");
/*  62:107 */   public static final ObjType CHECKBOX = new ObjType(11, "Checkbox");
/*  63:108 */   public static final ObjType OPTION = new ObjType(12, "Option");
/*  64:109 */   public static final ObjType EDITBOX = new ObjType(13, "Edit Box");
/*  65:110 */   public static final ObjType LABEL = new ObjType(14, "Label");
/*  66:111 */   public static final ObjType DIALOGUEBOX = new ObjType(15, "Dialogue Box");
/*  67:112 */   public static final ObjType SPINBOX = new ObjType(16, "Spin Box");
/*  68:113 */   public static final ObjType SCROLLBAR = new ObjType(17, "Scrollbar");
/*  69:114 */   public static final ObjType LISTBOX = new ObjType(18, "List Box");
/*  70:115 */   public static final ObjType GROUPBOX = new ObjType(19, "Group Box");
/*  71:116 */   public static final ObjType COMBOBOX = new ObjType(20, "Combo Box");
/*  72:117 */   public static final ObjType MSOFFICEDRAWING = new ObjType(30, "MS Office Drawing");
/*  73:119 */   public static final ObjType FORMCONTROL = new ObjType(20, "Form Combo Box");
/*  74:121 */   public static final ObjType EXCELNOTE = new ObjType(25, "Excel Note");
/*  75:124 */   public static final ObjType UNKNOWN = new ObjType(255, "Unknown");
/*  76:    */   private static final int COMMON_DATA_LENGTH = 22;
/*  77:    */   private static final int CLIPBOARD_FORMAT_LENGTH = 6;
/*  78:    */   private static final int PICTURE_OPTION_LENGTH = 6;
/*  79:    */   private static final int NOTE_STRUCTURE_LENGTH = 26;
/*  80:    */   private static final int COMBOBOX_STRUCTURE_LENGTH = 44;
/*  81:    */   private static final int END_LENGTH = 4;
/*  82:    */   
/*  83:    */   public ObjRecord(Record t)
/*  84:    */   {
/*  85:141 */     super(t);
/*  86:142 */     byte[] data = t.getData();
/*  87:143 */     int objtype = IntegerHelper.getInt(data[4], data[5]);
/*  88:144 */     this.read = true;
/*  89:145 */     this.type = ObjType.getType(objtype);
/*  90:147 */     if (this.type == UNKNOWN) {
/*  91:149 */       logger.warn("unknown object type code " + objtype);
/*  92:    */     }
/*  93:152 */     this.objectId = IntegerHelper.getInt(data[6], data[7]);
/*  94:    */   }
/*  95:    */   
/*  96:    */   ObjRecord(int objId, ObjType t)
/*  97:    */   {
/*  98:163 */     super(Type.OBJ);
/*  99:164 */     this.objectId = objId;
/* 100:165 */     this.type = t;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public byte[] getData()
/* 104:    */   {
/* 105:175 */     if (this.read) {
/* 106:177 */       return getRecord().getData();
/* 107:    */     }
/* 108:180 */     if ((this.type == PICTURE) || (this.type == CHART)) {
/* 109:182 */       return getPictureData();
/* 110:    */     }
/* 111:184 */     if (this.type == EXCELNOTE) {
/* 112:186 */       return getNoteData();
/* 113:    */     }
/* 114:188 */     if (this.type == COMBOBOX) {
/* 115:190 */       return getComboBoxData();
/* 116:    */     }
/* 117:194 */     Assert.verify(false);
/* 118:    */     
/* 119:196 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private byte[] getPictureData()
/* 123:    */   {
/* 124:206 */     int dataLength = 38;
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:210 */     int pos = 0;
/* 129:211 */     byte[] data = new byte[dataLength];
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:215 */     IntegerHelper.getTwoBytes(21, data, pos);
/* 134:    */     
/* 135:    */ 
/* 136:218 */     IntegerHelper.getTwoBytes(18, data, pos + 2);
/* 137:    */     
/* 138:    */ 
/* 139:221 */     IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
/* 140:    */     
/* 141:    */ 
/* 142:224 */     IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
/* 143:    */     
/* 144:    */ 
/* 145:227 */     IntegerHelper.getTwoBytes(24593, data, pos + 8);
/* 146:228 */     pos += 22;
/* 147:    */     
/* 148:    */ 
/* 149:    */ 
/* 150:232 */     IntegerHelper.getTwoBytes(7, data, pos);
/* 151:    */     
/* 152:    */ 
/* 153:235 */     IntegerHelper.getTwoBytes(2, data, pos + 2);
/* 154:    */     
/* 155:    */ 
/* 156:238 */     IntegerHelper.getTwoBytes(65535, data, pos + 4);
/* 157:239 */     pos += 6;
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:243 */     IntegerHelper.getTwoBytes(8, data, pos);
/* 162:    */     
/* 163:    */ 
/* 164:246 */     IntegerHelper.getTwoBytes(2, data, pos + 2);
/* 165:    */     
/* 166:    */ 
/* 167:249 */     IntegerHelper.getTwoBytes(1, data, pos + 4);
/* 168:250 */     pos += 6;
/* 169:    */     
/* 170:    */ 
/* 171:253 */     IntegerHelper.getTwoBytes(0, data, pos);
/* 172:    */     
/* 173:    */ 
/* 174:256 */     IntegerHelper.getTwoBytes(0, data, pos + 2);
/* 175:    */     
/* 176:    */ 
/* 177:259 */     pos += 4;
/* 178:    */     
/* 179:261 */     return data;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private byte[] getNoteData()
/* 183:    */   {
/* 184:271 */     int dataLength = 52;
/* 185:    */     
/* 186:    */ 
/* 187:274 */     int pos = 0;
/* 188:275 */     byte[] data = new byte[dataLength];
/* 189:    */     
/* 190:    */ 
/* 191:    */ 
/* 192:279 */     IntegerHelper.getTwoBytes(21, data, pos);
/* 193:    */     
/* 194:    */ 
/* 195:282 */     IntegerHelper.getTwoBytes(18, data, pos + 2);
/* 196:    */     
/* 197:    */ 
/* 198:285 */     IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
/* 199:    */     
/* 200:    */ 
/* 201:288 */     IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
/* 202:    */     
/* 203:    */ 
/* 204:291 */     IntegerHelper.getTwoBytes(16401, data, pos + 8);
/* 205:292 */     pos += 22;
/* 206:    */     
/* 207:    */ 
/* 208:    */ 
/* 209:296 */     IntegerHelper.getTwoBytes(13, data, pos);
/* 210:    */     
/* 211:    */ 
/* 212:299 */     IntegerHelper.getTwoBytes(22, data, pos + 2);
/* 213:    */     
/* 214:    */ 
/* 215:302 */     pos += 26;
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:306 */     IntegerHelper.getTwoBytes(0, data, pos);
/* 220:    */     
/* 221:    */ 
/* 222:309 */     IntegerHelper.getTwoBytes(0, data, pos + 2);
/* 223:    */     
/* 224:    */ 
/* 225:312 */     pos += 4;
/* 226:    */     
/* 227:314 */     return data;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private byte[] getComboBoxData()
/* 231:    */   {
/* 232:324 */     int dataLength = 70;
/* 233:    */     
/* 234:    */ 
/* 235:327 */     int pos = 0;
/* 236:328 */     byte[] data = new byte[dataLength];
/* 237:    */     
/* 238:    */ 
/* 239:    */ 
/* 240:332 */     IntegerHelper.getTwoBytes(21, data, pos);
/* 241:    */     
/* 242:    */ 
/* 243:335 */     IntegerHelper.getTwoBytes(18, data, pos + 2);
/* 244:    */     
/* 245:    */ 
/* 246:338 */     IntegerHelper.getTwoBytes(this.type.value, data, pos + 4);
/* 247:    */     
/* 248:    */ 
/* 249:341 */     IntegerHelper.getTwoBytes(this.objectId, data, pos + 6);
/* 250:    */     
/* 251:    */ 
/* 252:344 */     IntegerHelper.getTwoBytes(0, data, pos + 8);
/* 253:345 */     pos += 22;
/* 254:    */     
/* 255:    */ 
/* 256:    */ 
/* 257:349 */     IntegerHelper.getTwoBytes(12, data, pos);
/* 258:    */     
/* 259:    */ 
/* 260:352 */     IntegerHelper.getTwoBytes(20, data, pos + 2);
/* 261:    */     
/* 262:    */ 
/* 263:355 */     data[(pos + 14)] = 1;
/* 264:356 */     data[(pos + 16)] = 4;
/* 265:357 */     data[(pos + 20)] = 16;
/* 266:358 */     data[(pos + 24)] = 19;
/* 267:359 */     data[(pos + 26)] = -18;
/* 268:360 */     data[(pos + 27)] = 31;
/* 269:361 */     data[(pos + 30)] = 4;
/* 270:362 */     data[(pos + 34)] = 1;
/* 271:363 */     data[(pos + 35)] = 6;
/* 272:364 */     data[(pos + 38)] = 2;
/* 273:365 */     data[(pos + 40)] = 8;
/* 274:366 */     data[(pos + 42)] = 64;
/* 275:    */     
/* 276:368 */     pos += 44;
/* 277:    */     
/* 278:    */ 
/* 279:    */ 
/* 280:372 */     IntegerHelper.getTwoBytes(0, data, pos);
/* 281:    */     
/* 282:    */ 
/* 283:375 */     IntegerHelper.getTwoBytes(0, data, pos + 2);
/* 284:    */     
/* 285:    */ 
/* 286:378 */     pos += 4;
/* 287:    */     
/* 288:380 */     return data;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Record getRecord()
/* 292:    */   {
/* 293:391 */     return super.getRecord();
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ObjType getType()
/* 297:    */   {
/* 298:401 */     return this.type;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int getObjectId()
/* 302:    */   {
/* 303:411 */     return this.objectId;
/* 304:    */   }
/* 305:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.ObjRecord
 * JD-Core Version:    0.7.0.1
 */