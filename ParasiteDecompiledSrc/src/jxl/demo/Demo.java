/*   1:    */ package jxl.demo;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import jxl.Cell;
/*   8:    */ import jxl.Range;
/*   9:    */ import jxl.Workbook;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ 
/*  12:    */ public class Demo
/*  13:    */ {
/*  14:    */   private static final int CSVFormat = 13;
/*  15:    */   private static final int XMLFormat = 14;
/*  16: 45 */   private static Logger logger = Logger.getLogger(Demo.class);
/*  17:    */   
/*  18:    */   private static void displayHelp()
/*  19:    */   {
/*  20: 52 */     System.err.println("Command format:  Demo [-unicode] [-csv] [-hide] excelfile");
/*  21:    */     
/*  22: 54 */     System.err.println("                 Demo -xml [-format]  excelfile");
/*  23: 55 */     System.err.println("                 Demo -readwrite|-rw excelfile output");
/*  24: 56 */     System.err.println("                 Demo -biffdump | -bd | -wa | -write | -formulas | -features | -escher | -escherdg excelfile");
/*  25: 57 */     System.err.println("                 Demo -ps excelfile [property] [output]");
/*  26: 58 */     System.err.println("                 Demo -version | -logtest | -h | -help");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void main(String[] args)
/*  30:    */   {
/*  31: 71 */     if (args.length == 0)
/*  32:    */     {
/*  33: 73 */       displayHelp();
/*  34: 74 */       System.exit(1);
/*  35:    */     }
/*  36: 77 */     if ((args[0].equals("-help")) || (args[0].equals("-h")))
/*  37:    */     {
/*  38: 79 */       displayHelp();
/*  39: 80 */       System.exit(1);
/*  40:    */     }
/*  41: 83 */     if (args[0].equals("-version"))
/*  42:    */     {
/*  43: 85 */       System.out.println("v" + Workbook.getVersion());
/*  44: 86 */       System.exit(0);
/*  45:    */     }
/*  46: 89 */     if (args[0].equals("-logtest"))
/*  47:    */     {
/*  48: 91 */       logger.debug("A sample \"debug\" message");
/*  49: 92 */       logger.info("A sample \"info\" message");
/*  50: 93 */       logger.warn("A sample \"warning\" message");
/*  51: 94 */       logger.error("A sample \"error\" message");
/*  52: 95 */       logger.fatal("A sample \"fatal\" message");
/*  53: 96 */       System.exit(0);
/*  54:    */     }
/*  55: 99 */     boolean write = false;
/*  56:100 */     boolean readwrite = false;
/*  57:101 */     boolean formulas = false;
/*  58:102 */     boolean biffdump = false;
/*  59:103 */     boolean jxlversion = false;
/*  60:104 */     boolean propertysets = false;
/*  61:105 */     boolean features = false;
/*  62:106 */     boolean escher = false;
/*  63:107 */     boolean escherdg = false;
/*  64:108 */     String file = args[0];
/*  65:109 */     String outputFile = null;
/*  66:110 */     String propertySet = null;
/*  67:112 */     if (args[0].equals("-write"))
/*  68:    */     {
/*  69:114 */       write = true;
/*  70:115 */       file = args[1];
/*  71:    */     }
/*  72:117 */     else if (args[0].equals("-formulas"))
/*  73:    */     {
/*  74:119 */       formulas = true;
/*  75:120 */       file = args[1];
/*  76:    */     }
/*  77:122 */     else if (args[0].equals("-features"))
/*  78:    */     {
/*  79:124 */       features = true;
/*  80:125 */       file = args[1];
/*  81:    */     }
/*  82:127 */     else if (args[0].equals("-escher"))
/*  83:    */     {
/*  84:129 */       escher = true;
/*  85:130 */       file = args[1];
/*  86:    */     }
/*  87:132 */     else if (args[0].equals("-escherdg"))
/*  88:    */     {
/*  89:134 */       escherdg = true;
/*  90:135 */       file = args[1];
/*  91:    */     }
/*  92:137 */     else if ((args[0].equals("-biffdump")) || (args[0].equals("-bd")))
/*  93:    */     {
/*  94:139 */       biffdump = true;
/*  95:140 */       file = args[1];
/*  96:    */     }
/*  97:142 */     else if (args[0].equals("-wa"))
/*  98:    */     {
/*  99:144 */       jxlversion = true;
/* 100:145 */       file = args[1];
/* 101:    */     }
/* 102:147 */     else if (args[0].equals("-ps"))
/* 103:    */     {
/* 104:149 */       propertysets = true;
/* 105:150 */       file = args[1];
/* 106:152 */       if (args.length > 2) {
/* 107:154 */         propertySet = args[2];
/* 108:    */       }
/* 109:157 */       if (args.length == 4) {
/* 110:159 */         outputFile = args[3];
/* 111:    */       }
/* 112:    */     }
/* 113:162 */     else if ((args[0].equals("-readwrite")) || (args[0].equals("-rw")))
/* 114:    */     {
/* 115:164 */       readwrite = true;
/* 116:165 */       file = args[1];
/* 117:166 */       outputFile = args[2];
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:170 */       file = args[(args.length - 1)];
/* 122:    */     }
/* 123:173 */     String encoding = "UTF8";
/* 124:174 */     int format = 13;
/* 125:175 */     boolean formatInfo = false;
/* 126:176 */     boolean hideCells = false;
/* 127:178 */     if ((!write) && (!readwrite) && (!formulas) && (!biffdump) && (!jxlversion) && (!propertysets) && (!features) && (!escher) && (!escherdg)) {
/* 128:188 */       for (int i = 0; i < args.length - 1; i++) {
/* 129:190 */         if (args[i].equals("-unicode"))
/* 130:    */         {
/* 131:192 */           encoding = "UnicodeBig";
/* 132:    */         }
/* 133:194 */         else if (args[i].equals("-xml"))
/* 134:    */         {
/* 135:196 */           format = 14;
/* 136:    */         }
/* 137:198 */         else if (args[i].equals("-csv"))
/* 138:    */         {
/* 139:200 */           format = 13;
/* 140:    */         }
/* 141:202 */         else if (args[i].equals("-format"))
/* 142:    */         {
/* 143:204 */           formatInfo = true;
/* 144:    */         }
/* 145:206 */         else if (args[i].equals("-hide"))
/* 146:    */         {
/* 147:208 */           hideCells = true;
/* 148:    */         }
/* 149:    */         else
/* 150:    */         {
/* 151:212 */           System.err.println("Command format:  CSV [-unicode] [-xml|-csv] excelfile");
/* 152:    */           
/* 153:214 */           System.exit(1);
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:    */     try
/* 158:    */     {
/* 159:221 */       if (write)
/* 160:    */       {
/* 161:223 */         Write w = new Write(file);
/* 162:224 */         w.write();
/* 163:    */       }
/* 164:226 */       else if (readwrite)
/* 165:    */       {
/* 166:228 */         ReadWrite rw = new ReadWrite(file, outputFile);
/* 167:229 */         rw.readWrite();
/* 168:    */       }
/* 169:231 */       else if (formulas)
/* 170:    */       {
/* 171:233 */         Workbook w = Workbook.getWorkbook(new File(file));
/* 172:234 */         Formulas f = new Formulas(w, System.out, encoding);
/* 173:235 */         w.close();
/* 174:    */       }
/* 175:237 */       else if (features)
/* 176:    */       {
/* 177:239 */         Workbook w = Workbook.getWorkbook(new File(file));
/* 178:240 */         Features f = new Features(w, System.out, encoding);
/* 179:241 */         w.close();
/* 180:    */       }
/* 181:243 */       else if (escher)
/* 182:    */       {
/* 183:245 */         Workbook w = Workbook.getWorkbook(new File(file));
/* 184:246 */         Escher f = new Escher(w, System.out, encoding);
/* 185:247 */         w.close();
/* 186:    */       }
/* 187:249 */       else if (escherdg)
/* 188:    */       {
/* 189:251 */         Workbook w = Workbook.getWorkbook(new File(file));
/* 190:252 */         EscherDrawingGroup f = new EscherDrawingGroup(w, System.out, encoding);
/* 191:253 */         w.close();
/* 192:    */       }
/* 193:    */       else
/* 194:    */       {
/* 195:    */         BiffDump bd;
/* 196:255 */         if (biffdump)
/* 197:    */         {
/* 198:257 */           bd = new BiffDump(new File(file), System.out);
/* 199:    */         }
/* 200:    */         else
/* 201:    */         {
/* 202:    */           WriteAccess bd;
/* 203:259 */           if (jxlversion)
/* 204:    */           {
/* 205:261 */             bd = new WriteAccess(new File(file));
/* 206:    */           }
/* 207:    */           else
/* 208:    */           {
/* 209:    */             PropertySetsReader psr;
/* 210:263 */             if (propertysets)
/* 211:    */             {
/* 212:265 */               OutputStream os = System.out;
/* 213:266 */               if (outputFile != null) {
/* 214:268 */                 os = new FileOutputStream(outputFile);
/* 215:    */               }
/* 216:270 */               psr = new PropertySetsReader(new File(file), propertySet, os);
/* 217:    */             }
/* 218:    */             else
/* 219:    */             {
/* 220:276 */               Workbook w = Workbook.getWorkbook(new File(file));
/* 221:    */               CSV csv;
/* 222:    */               XML xml;
/* 223:280 */               if (format == 13) {
/* 224:282 */                 csv = new CSV(w, System.out, encoding, hideCells);
/* 225:284 */               } else if (format == 14) {
/* 226:286 */                 xml = new XML(w, System.out, encoding, formatInfo);
/* 227:    */               }
/* 228:289 */               w.close();
/* 229:    */             }
/* 230:    */           }
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:    */     catch (Throwable t)
/* 235:    */     {
/* 236:294 */       System.out.println(t.toString());
/* 237:295 */       t.printStackTrace();
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private static void findTest(Workbook w)
/* 242:    */   {
/* 243:304 */     logger.info("Find test");
/* 244:    */     
/* 245:306 */     Cell c = w.findCellByName("named1");
/* 246:307 */     if (c != null) {
/* 247:309 */       logger.info("named1 contents:  " + c.getContents());
/* 248:    */     }
/* 249:312 */     c = w.findCellByName("named2");
/* 250:313 */     if (c != null) {
/* 251:315 */       logger.info("named2 contents:  " + c.getContents());
/* 252:    */     }
/* 253:318 */     c = w.findCellByName("namedrange");
/* 254:319 */     if (c != null) {
/* 255:321 */       logger.info("named2 contents:  " + c.getContents());
/* 256:    */     }
/* 257:324 */     Range[] range = w.findByName("namedrange");
/* 258:325 */     if (range != null)
/* 259:    */     {
/* 260:327 */       c = range[0].getTopLeft();
/* 261:328 */       logger.info("namedrange top left contents:  " + c.getContents());
/* 262:    */       
/* 263:330 */       c = range[0].getBottomRight();
/* 264:331 */       logger.info("namedrange bottom right contents:  " + c.getContents());
/* 265:    */     }
/* 266:334 */     range = w.findByName("nonadjacentrange");
/* 267:335 */     if (range != null) {
/* 268:337 */       for (int i = 0; i < range.length; i++)
/* 269:    */       {
/* 270:339 */         c = range[i].getTopLeft();
/* 271:340 */         logger.info("nonadjacent top left contents:  " + c.getContents());
/* 272:    */         
/* 273:342 */         c = range[i].getBottomRight();
/* 274:343 */         logger.info("nonadjacent bottom right contents:  " + c.getContents());
/* 275:    */       }
/* 276:    */     }
/* 277:347 */     range = w.findByName("horizontalnonadjacentrange");
/* 278:348 */     if (range != null) {
/* 279:350 */       for (int i = 0; i < range.length; i++)
/* 280:    */       {
/* 281:352 */         c = range[i].getTopLeft();
/* 282:353 */         logger.info("horizontalnonadjacent top left contents:  " + c.getContents());
/* 283:    */         
/* 284:    */ 
/* 285:356 */         c = range[i].getBottomRight();
/* 286:357 */         logger.info("horizontalnonadjacent bottom right contents:  " + c.getContents());
/* 287:    */       }
/* 288:    */     }
/* 289:    */   }
/* 290:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.demo.Demo
 * JD-Core Version:    0.7.0.1
 */