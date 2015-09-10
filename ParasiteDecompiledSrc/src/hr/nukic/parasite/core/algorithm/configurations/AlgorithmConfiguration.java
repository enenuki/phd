/*   1:    */ package hr.nukic.parasite.core.algorithm.configurations;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.ParasiteManager;
/*   4:    */ import hr.nukic.parasite.core.algorithm.TradingAlgorithm;
/*   5:    */ import java.io.BufferedWriter;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileWriter;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import nukic.parasite.utils.MainLogger;
/*  12:    */ 
/*  13:    */ public class AlgorithmConfiguration
/*  14:    */ {
/*  15:    */   long id;
/*  16: 31 */   String fullName = "";
/*  17: 32 */   String version = "";
/*  18:    */   TradingAlgorithm itsAlgorithm;
/*  19: 34 */   public List<Configurable> configurables = new ArrayList();
/*  20:    */   private ConfigurationEvaluationSuite itsEvaluationSuite;
/*  21:    */   
/*  22:    */   public static AlgorithmConfiguration createAllMinValuesConfig(AlgorithmConfiguration template)
/*  23:    */   {
/*  24: 41 */     AlgorithmConfiguration allMinValuesConfig = new AlgorithmConfiguration(template);
/*  25: 42 */     Iterator<Configurable> i = allMinValuesConfig.configurables.iterator();
/*  26: 43 */     while (i.hasNext())
/*  27:    */     {
/*  28: 44 */       Configurable c = (Configurable)i.next();
/*  29: 45 */       c.value = c.minValue;
/*  30: 46 */       c.ordinal = 1;
/*  31:    */     }
/*  32: 48 */     allMinValuesConfig.fullName = allMinValuesConfig.generateFullName();
/*  33: 49 */     allMinValuesConfig.version = allMinValuesConfig.generateVersionString();
/*  34: 50 */     allMinValuesConfig.id = allMinValuesConfig.generateUniqueId();
/*  35:    */     
/*  36: 52 */     return allMinValuesConfig;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static AlgorithmConfiguration createAllDefaultValuesConfig(AlgorithmConfiguration template)
/*  40:    */   {
/*  41: 56 */     AlgorithmConfiguration allDefaultValuesConfig = new AlgorithmConfiguration(template);
/*  42: 57 */     Iterator<Configurable> i = allDefaultValuesConfig.configurables.iterator();
/*  43: 58 */     while (i.hasNext())
/*  44:    */     {
/*  45: 59 */       Configurable c = (Configurable)i.next();
/*  46: 60 */       c.value = c.defaultValue;
/*  47: 61 */       c.calculateOrdinal();
/*  48:    */     }
/*  49: 65 */     allDefaultValuesConfig.fullName = allDefaultValuesConfig.generateFullName();
/*  50: 66 */     allDefaultValuesConfig.version = allDefaultValuesConfig.generateVersionString();
/*  51: 67 */     allDefaultValuesConfig.id = allDefaultValuesConfig.generateUniqueId();
/*  52:    */     
/*  53: 69 */     return allDefaultValuesConfig;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public AlgorithmConfiguration(List<Configurable> configurables)
/*  57:    */   {
/*  58: 75 */     this.configurables = configurables;
/*  59: 76 */     this.fullName = generateFullName();
/*  60: 77 */     this.version = generateVersionString();
/*  61: 78 */     this.id = generateUniqueId();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AlgorithmConfiguration(AlgorithmConfiguration configTemplate)
/*  65:    */   {
/*  66: 82 */     Iterator<Configurable> i = configTemplate.configurables.iterator();
/*  67: 83 */     while (i.hasNext()) {
/*  68: 84 */       this.configurables.add(new Configurable((Configurable)i.next()));
/*  69:    */     }
/*  70: 87 */     this.fullName = generateFullName();
/*  71: 88 */     this.version = generateVersionString();
/*  72: 89 */     this.id = generateUniqueId();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isAllDefaultConfig()
/*  76:    */   {
/*  77: 93 */     boolean is = true;
/*  78: 94 */     Iterator<Configurable> i = this.configurables.iterator();
/*  79: 95 */     while (i.hasNext())
/*  80:    */     {
/*  81: 96 */       Configurable c = (Configurable)i.next();
/*  82: 97 */       if (c.value != c.defaultValue)
/*  83:    */       {
/*  84: 98 */         is = false;
/*  85: 99 */         break;
/*  86:    */       }
/*  87:    */     }
/*  88:103 */     return is;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void overwriteConfigurableWithSameName(Configurable configurable)
/*  92:    */   {
/*  93:108 */     boolean found = false;
/*  94:109 */     int index = 0;
/*  95:110 */     Iterator<Configurable> i = this.configurables.iterator();
/*  96:111 */     while (i.hasNext())
/*  97:    */     {
/*  98:112 */       Configurable c = (Configurable)i.next();
/*  99:113 */       if (configurable.name.equals(c.name))
/* 100:    */       {
/* 101:114 */         found = true;
/* 102:115 */         break;
/* 103:    */       }
/* 104:117 */       index++;
/* 105:    */     }
/* 106:119 */     if (found)
/* 107:    */     {
/* 108:120 */       configurable.calculateOrdinal();
/* 109:121 */       this.configurables.set(index, configurable);
/* 110:122 */       generateVersionString();
/* 111:123 */       this.id = generateUniqueId();
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:125 */       MainLogger.error("Could not find configurable with same name!");
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String toString()
/* 120:    */   {
/* 121:131 */     String str = "AlgorithmConfiguration id: " + this.id;
/* 122:132 */     str = str + "\nFull name: " + this.fullName;
/* 123:133 */     str = str + "\nVersion: " + this.version + "\n";
/* 124:134 */     Iterator<Configurable> i = this.configurables.iterator();
/* 125:135 */     while (i.hasNext()) {
/* 126:136 */       str = str + ((Configurable)i.next()).toString();
/* 127:    */     }
/* 128:139 */     return str;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void writeToCsvFile()
/* 132:    */   {
/* 133:145 */     String str = toCsvString();
/* 134:    */     try
/* 135:    */     {
/* 136:149 */       String outDirPath = ParasiteManager.getInstance().mainDir + "\\algorithm_configs\\";
/* 137:150 */       File outFolder = new File(outDirPath);
/* 138:151 */       if (!outFolder.exists())
/* 139:    */       {
/* 140:152 */         boolean success = outFolder.mkdir();
/* 141:153 */         if (!success) {
/* 142:154 */           MainLogger.error("ERROR while creating directory: " + outDirPath);
/* 143:    */         }
/* 144:    */       }
/* 145:157 */       FileWriter fstream = new FileWriter(outDirPath + "AC_" + this.id + ".alconf", true);
/* 146:    */       
/* 147:159 */       BufferedWriter out = new BufferedWriter(fstream);
/* 148:160 */       MainLogger.info("Writing to CSV file algorithm config with id: " + this.id);
/* 149:161 */       out.write(str);
/* 150:162 */       out.close();
/* 151:    */     }
/* 152:    */     catch (Exception e)
/* 153:    */     {
/* 154:164 */       MainLogger.error(e);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String toCsvString()
/* 159:    */   {
/* 160:170 */     String str = this.id + "," + this.fullName + "," + this.version + ",";
/* 161:171 */     Iterator<Configurable> i = this.configurables.iterator();
/* 162:172 */     while (i.hasNext())
/* 163:    */     {
/* 164:173 */       str = str + ((Configurable)i.next()).toCsvString();
/* 165:174 */       str = str + ",";
/* 166:    */     }
/* 167:176 */     str = str.substring(0, str.lastIndexOf(","));
/* 168:177 */     return str;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String generateFullName()
/* 172:    */   {
/* 173:181 */     String str = "AC";
/* 174:182 */     Iterator<Configurable> i = this.configurables.iterator();
/* 175:183 */     while (i.hasNext())
/* 176:    */     {
/* 177:184 */       Configurable c = (Configurable)i.next();
/* 178:185 */       str = str + "_" + c.name;
/* 179:    */     }
/* 180:187 */     this.fullName = str;
/* 181:188 */     return str;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String generateVersionString()
/* 185:    */   {
/* 186:192 */     String str = "v";
/* 187:193 */     Iterator<Configurable> i = this.configurables.iterator();
/* 188:194 */     while (i.hasNext())
/* 189:    */     {
/* 190:195 */       Configurable c = (Configurable)i.next();
/* 191:196 */       str = str + c.ordinal + ".";
/* 192:    */     }
/* 193:198 */     if (!this.configurables.isEmpty()) {
/* 194:199 */       str = str.substring(0, str.lastIndexOf("."));
/* 195:    */     }
/* 196:201 */     this.version = str;
/* 197:202 */     return str;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Configurable getConfigurableByName(String name)
/* 201:    */   {
/* 202:206 */     Iterator<Configurable> i = this.configurables.iterator();
/* 203:207 */     while (i.hasNext())
/* 204:    */     {
/* 205:208 */       Configurable item = (Configurable)i.next();
/* 206:209 */       if (item.name.equals(name)) {
/* 207:210 */         return item;
/* 208:    */       }
/* 209:    */     }
/* 210:213 */     return null;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setConfigurableValue(String name, float value)
/* 214:    */   {
/* 215:217 */     Iterator<Configurable> i = this.configurables.iterator();
/* 216:218 */     while (i.hasNext())
/* 217:    */     {
/* 218:219 */       Configurable item = (Configurable)i.next();
/* 219:220 */       if (item.name.equals(name)) {
/* 220:221 */         item.value = value;
/* 221:    */       }
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setItsAlgorithm(TradingAlgorithm itsAlgorithm)
/* 226:    */   {
/* 227:227 */     this.itsAlgorithm = itsAlgorithm;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public long generateUniqueId()
/* 231:    */   {
/* 232:231 */     return toCsvString().hashCode();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public ConfigurationEvaluationSuite getEvaluationSuite()
/* 236:    */   {
/* 237:235 */     return this.itsEvaluationSuite;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setEvaluationSuite(ConfigurationEvaluationSuite evaluationSuite)
/* 241:    */   {
/* 242:239 */     this.itsEvaluationSuite = evaluationSuite;
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfiguration
 * JD-Core Version:    0.7.0.1
 */