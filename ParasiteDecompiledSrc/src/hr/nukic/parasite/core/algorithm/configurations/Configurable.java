/*   1:    */ package hr.nukic.parasite.core.algorithm.configurations;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.ParasiteManager;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import nukic.parasite.utils.MainLogger;
/*  13:    */ import nukic.parasite.utils.ParasiteUtils;
/*  14:    */ 
/*  15:    */ public class Configurable
/*  16:    */ {
/*  17: 21 */   public static float POSITIVE_INFINITY = 3.4028235E+38F;
/*  18: 22 */   public static float NEGATIVE_INFINITY = 1.4E-45F;
/*  19: 23 */   public static Map<String, Configurable> dictionary = new HashMap();
/*  20:    */   
/*  21:    */   static
/*  22:    */   {
/*  23: 26 */     loadDictionary();
/*  24:    */   }
/*  25:    */   
/*  26: 29 */   public int ordinal = -1;
/*  27:    */   public String name;
/*  28: 31 */   public String description = "";
/*  29:    */   public float value;
/*  30:    */   public float defaultValue;
/*  31:    */   public float step;
/*  32:    */   public float minValue;
/*  33:    */   public float maxValue;
/*  34:    */   
/*  35:    */   private static void loadDictionary()
/*  36:    */   {
/*  37: 39 */     MainLogger.info("Loading configurable dictionary...");
/*  38:    */     try
/*  39:    */     {
/*  40: 41 */       List<String> lineList = new ArrayList(3);
/*  41:    */       
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 46 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
/*  46: 47 */         ParasiteManager.getInstance().configDictFilePath);
/*  47: 48 */       BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*  48:    */       String strLine;
/*  49: 53 */       while ((strLine = br.readLine()) != null)
/*  50:    */       {
/*  51:    */         String strLine;
/*  52: 54 */         lineList.add(strLine);
/*  53:    */         
/*  54: 56 */         MainLogger.info("Adding configurable to dictionary...");
/*  55: 57 */         Configurable c = new Configurable(strLine);
/*  56: 58 */         MainLogger.info(c.toString());
/*  57: 59 */         dictionary.put(c.name, c);
/*  58:    */       }
/*  59: 62 */       is.close();
/*  60:    */     }
/*  61:    */     catch (Exception e)
/*  62:    */     {
/*  63: 64 */       MainLogger.error(e);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static Configurable getConfigurable(String name)
/*  68:    */   {
/*  69: 70 */     return (Configurable)dictionary.get(name);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Configurable(String name, float value, float step, float minValue, float maxValue)
/*  73:    */   {
/*  74: 75 */     this.name = name;
/*  75: 76 */     this.value = value;
/*  76: 77 */     this.step = step;
/*  77: 78 */     this.minValue = minValue;
/*  78: 79 */     this.maxValue = maxValue;
/*  79: 81 */     if (!performSanityCheck()) {
/*  80: 82 */       return;
/*  81:    */     }
/*  82: 85 */     if (value == minValue) {
/*  83: 86 */       this.ordinal = 1;
/*  84:    */     } else {
/*  85: 88 */       calculateOrdinal();
/*  86:    */     }
/*  87: 91 */     this.defaultValue = value;
/*  88: 92 */     this.description = "<NO_DESCRIPTION>";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Configurable(String name, float value, float defaultValue, float step, float minValue, float maxValue)
/*  92:    */   {
/*  93: 97 */     this.name = name;
/*  94: 98 */     this.value = value;
/*  95: 99 */     this.step = step;
/*  96:100 */     this.minValue = minValue;
/*  97:101 */     this.maxValue = maxValue;
/*  98:102 */     this.defaultValue = defaultValue;
/*  99:104 */     if (!performSanityCheck()) {
/* 100:105 */       return;
/* 101:    */     }
/* 102:108 */     if (value == minValue) {
/* 103:109 */       this.ordinal = 1;
/* 104:    */     } else {
/* 105:111 */       calculateOrdinal();
/* 106:    */     }
/* 107:114 */     this.description = "<NO_DESCRIPTION>";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Configurable(String name, float value)
/* 111:    */   {
/* 112:120 */     this.name = name;
/* 113:121 */     this.value = value;
/* 114:122 */     this.step = 0.0F;
/* 115:123 */     this.minValue = value;
/* 116:124 */     this.maxValue = value;
/* 117:125 */     this.defaultValue = value;
/* 118:127 */     if (!performSanityCheck()) {
/* 119:128 */       return;
/* 120:    */     }
/* 121:131 */     this.ordinal = 1;
/* 122:132 */     this.description = "<NO_DESCRIPTION>";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Configurable(String csvString)
/* 126:    */   {
/* 127:137 */     parseFromCsvString(csvString);
/* 128:138 */     performSanityCheck();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Configurable(Configurable copy)
/* 132:    */   {
/* 133:142 */     this.ordinal = copy.ordinal;
/* 134:143 */     this.name = copy.name;
/* 135:144 */     this.description = copy.description;
/* 136:145 */     this.value = copy.value;
/* 137:146 */     this.defaultValue = copy.defaultValue;
/* 138:147 */     this.step = copy.step;
/* 139:148 */     this.minValue = copy.minValue;
/* 140:149 */     this.maxValue = copy.maxValue;
/* 141:150 */     performSanityCheck();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean increaseValue()
/* 145:    */   {
/* 146:154 */     if (this.value + this.step < this.maxValue)
/* 147:    */     {
/* 148:155 */       this.value += this.step;
/* 149:156 */       this.ordinal += 1;
/* 150:157 */       return true;
/* 151:    */     }
/* 152:159 */     MainLogger.info("Could not increase value of configurable. Max value reached.");
/* 153:160 */     return false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Configurable createNext()
/* 157:    */   {
/* 158:165 */     if (this.value + this.step < this.maxValue)
/* 159:    */     {
/* 160:166 */       Configurable next = new Configurable(this);
/* 161:167 */       this.value += this.step;
/* 162:168 */       next.value = ParasiteUtils.round(next.value, 3);
/* 163:169 */       next.ordinal += 1;
/* 164:170 */       return next;
/* 165:    */     }
/* 166:172 */     return this;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Configurable getInstanceWithMinValue()
/* 170:    */   {
/* 171:177 */     Configurable c = new Configurable(this);
/* 172:178 */     c.value = this.minValue;
/* 173:179 */     c.ordinal = 1;
/* 174:180 */     return c;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String toCsvString()
/* 178:    */   {
/* 179:184 */     return 
/* 180:185 */       this.ordinal + "," + this.name + "," + this.value + "," + this.defaultValue + "," + this.minValue + "," + this.maxValue + "," + this.step;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void parseFromCsvString(String csvString)
/* 184:    */   {
/* 185:189 */     String[] tokens = csvString.split(",");
/* 186:190 */     if (tokens.length != 7) {
/* 187:191 */       MainLogger.error("Configurable csv string is not correctly formatted: " + csvString);
/* 188:    */     }
/* 189:194 */     this.name = tokens[1];
/* 190:195 */     this.value = Float.parseFloat(tokens[2]);
/* 191:196 */     this.defaultValue = Float.parseFloat(tokens[3]);
/* 192:197 */     this.minValue = Float.parseFloat(tokens[4]);
/* 193:198 */     this.maxValue = Float.parseFloat(tokens[5]);
/* 194:199 */     this.step = Float.parseFloat(tokens[6]);
/* 195:    */     
/* 196:201 */     calculateOrdinal();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String toString()
/* 200:    */   {
/* 201:206 */     return 
/* 202:207 */       "Configurable [ordinal=" + this.ordinal + ", name=" + this.name + ", value=" + this.value + ", defaultValue=" + this.defaultValue + ", step=" + this.step + ", minValue=" + this.minValue + ", maxValue=" + this.maxValue + "]\n";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void calculateOrdinal()
/* 206:    */   {
/* 207:211 */     List<Float> values = getPossibleValues();
/* 208:212 */     this.ordinal = 1;
/* 209:213 */     boolean found = false;
/* 210:214 */     Iterator<Float> i = values.iterator();
/* 211:215 */     while (i.hasNext())
/* 212:    */     {
/* 213:216 */       float val = ((Float)i.next()).floatValue();
/* 214:217 */       if (val == this.value)
/* 215:    */       {
/* 216:219 */         found = true;
/* 217:220 */         break;
/* 218:    */       }
/* 219:222 */       this.ordinal += 1;
/* 220:    */     }
/* 221:225 */     if (!found)
/* 222:    */     {
/* 223:226 */       MainLogger.error(
/* 224:227 */         "ERROR: Cannot calculate configurable ordinal for configurable " + toString() + ". None of the possible configurable value matches current value! Setting ordinal to -1!");
/* 225:    */       
/* 226:229 */       this.ordinal = -1;
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<Float> getPossibleValues()
/* 231:    */   {
/* 232:235 */     List<Float> possibleValues = new ArrayList();
/* 233:236 */     if (this.minValue != this.maxValue)
/* 234:    */     {
/* 235:237 */       float value = this.minValue;
/* 236:238 */       while (value <= this.maxValue)
/* 237:    */       {
/* 238:239 */         possibleValues.add(new Float(value));
/* 239:    */         
/* 240:241 */         value += this.step;
/* 241:242 */         value = ParasiteUtils.round(value, 3);
/* 242:    */       }
/* 243:    */     }
/* 244:    */     else
/* 245:    */     {
/* 246:245 */       possibleValues.add(new Float(this.minValue));
/* 247:    */     }
/* 248:247 */     return possibleValues;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean performSanityCheck()
/* 252:    */   {
/* 253:251 */     boolean sane = true;
/* 254:253 */     if (this.minValue > this.maxValue)
/* 255:    */     {
/* 256:255 */       MainLogger.error("ERROR: Configurable sanity check failed! Min value must be smaller or equal to max value.");
/* 257:256 */       sane = false;
/* 258:    */     }
/* 259:257 */     else if (this.value > this.maxValue)
/* 260:    */     {
/* 261:258 */       MainLogger.error("ERROR: Configurable sanity check failed! Current value is larger than max value.");
/* 262:259 */       sane = false;
/* 263:    */     }
/* 264:260 */     else if (this.value < this.minValue)
/* 265:    */     {
/* 266:261 */       MainLogger.error("ERROR: Configurable sanity check failed! Current value is smaller than min value.");
/* 267:262 */       sane = false;
/* 268:    */     }
/* 269:263 */     else if (this.step < 0.0F)
/* 270:    */     {
/* 271:264 */       MainLogger.error("ERROR: Configurable sanity check failed! Step value cannot be negative!");
/* 272:265 */       sane = false;
/* 273:    */     }
/* 274:266 */     else if ((this.step == 0.0F) && (this.minValue != this.maxValue))
/* 275:    */     {
/* 276:268 */       MainLogger.error("ERROR: Configurable sanity check failed! Step value can be zero ONLY if min and max values are same!");
/* 277:269 */       sane = false;
/* 278:    */     }
/* 279:270 */     else if (this.step > this.maxValue - this.minValue)
/* 280:    */     {
/* 281:272 */       MainLogger.error("ERROR: Configurable sanity check failed! Step value cannot be larger than difference between max and min values");
/* 282:273 */       sane = false;
/* 283:    */     }
/* 284:275 */     return sane;
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.Configurable
 * JD-Core Version:    0.7.0.1
 */