/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ final class Function
/*   7:    */ {
/*   8: 34 */   private static Logger logger = Logger.getLogger(Function.class);
/*   9:    */   private final int code;
/*  10:    */   private final String name;
/*  11:    */   private final int numArgs;
/*  12: 56 */   private static Function[] functions = new Function[0];
/*  13:    */   
/*  14:    */   private Function(int v, String s, int a)
/*  15:    */   {
/*  16: 68 */     this.code = v;
/*  17: 69 */     this.name = s;
/*  18: 70 */     this.numArgs = a;
/*  19:    */     
/*  20:    */ 
/*  21: 73 */     Function[] newarray = new Function[functions.length + 1];
/*  22: 74 */     System.arraycopy(functions, 0, newarray, 0, functions.length);
/*  23: 75 */     newarray[functions.length] = this;
/*  24: 76 */     functions = newarray;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int hashCode()
/*  28:    */   {
/*  29: 86 */     return this.code;
/*  30:    */   }
/*  31:    */   
/*  32:    */   int getCode()
/*  33:    */   {
/*  34: 96 */     return this.code;
/*  35:    */   }
/*  36:    */   
/*  37:    */   String getPropertyName()
/*  38:    */   {
/*  39:107 */     return this.name;
/*  40:    */   }
/*  41:    */   
/*  42:    */   String getName(WorkbookSettings ws)
/*  43:    */   {
/*  44:117 */     FunctionNames fn = ws.getFunctionNames();
/*  45:118 */     return fn.getName(this);
/*  46:    */   }
/*  47:    */   
/*  48:    */   int getNumArgs()
/*  49:    */   {
/*  50:128 */     return this.numArgs;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Function getFunction(int v)
/*  54:    */   {
/*  55:139 */     Function f = null;
/*  56:141 */     for (int i = 0; i < functions.length; i++) {
/*  57:143 */       if (functions[i].code == v)
/*  58:    */       {
/*  59:145 */         f = functions[i];
/*  60:146 */         break;
/*  61:    */       }
/*  62:    */     }
/*  63:150 */     return f != null ? f : UNKNOWN;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Function getFunction(String v, WorkbookSettings ws)
/*  67:    */   {
/*  68:162 */     FunctionNames fn = ws.getFunctionNames();
/*  69:163 */     Function f = fn.getFunction(v);
/*  70:164 */     return f != null ? f : UNKNOWN;
/*  71:    */   }
/*  72:    */   
/*  73:    */   static Function[] getFunctions()
/*  74:    */   {
/*  75:175 */     return functions;
/*  76:    */   }
/*  77:    */   
/*  78:180 */   public static final Function COUNT = new Function(0, "count", 255);
/*  79:182 */   public static final Function ATTRIBUTE = new Function(1, "", 255);
/*  80:183 */   public static final Function ISNA = new Function(2, "isna", 1);
/*  81:185 */   public static final Function ISERROR = new Function(3, "iserror", 1);
/*  82:187 */   public static final Function SUM = new Function(4, "sum", 255);
/*  83:189 */   public static final Function AVERAGE = new Function(5, "average", 255);
/*  84:191 */   public static final Function MIN = new Function(6, "min", 255);
/*  85:193 */   public static final Function MAX = new Function(7, "max", 255);
/*  86:195 */   public static final Function ROW = new Function(8, "row", 255);
/*  87:197 */   public static final Function COLUMN = new Function(9, "column", 255);
/*  88:199 */   public static final Function NA = new Function(10, "na", 0);
/*  89:201 */   public static final Function NPV = new Function(11, "npv", 255);
/*  90:203 */   public static final Function STDEV = new Function(12, "stdev", 255);
/*  91:205 */   public static final Function DOLLAR = new Function(13, "dollar", 2);
/*  92:207 */   public static final Function FIXED = new Function(14, "fixed", 255);
/*  93:209 */   public static final Function SIN = new Function(15, "sin", 1);
/*  94:211 */   public static final Function COS = new Function(16, "cos", 1);
/*  95:213 */   public static final Function TAN = new Function(17, "tan", 1);
/*  96:215 */   public static final Function ATAN = new Function(18, "atan", 1);
/*  97:217 */   public static final Function PI = new Function(19, "pi", 0);
/*  98:219 */   public static final Function SQRT = new Function(20, "sqrt", 1);
/*  99:221 */   public static final Function EXP = new Function(21, "exp", 1);
/* 100:223 */   public static final Function LN = new Function(22, "ln", 1);
/* 101:225 */   public static final Function LOG10 = new Function(23, "log10", 1);
/* 102:227 */   public static final Function ABS = new Function(24, "abs", 1);
/* 103:229 */   public static final Function INT = new Function(25, "int", 1);
/* 104:231 */   public static final Function SIGN = new Function(26, "sign", 1);
/* 105:233 */   public static final Function ROUND = new Function(27, "round", 2);
/* 106:235 */   public static final Function LOOKUP = new Function(28, "lookup", 2);
/* 107:237 */   public static final Function INDEX = new Function(29, "index", 3);
/* 108:239 */   public static final Function REPT = new Function(30, "rept", 2);
/* 109:240 */   public static final Function MID = new Function(31, "mid", 3);
/* 110:242 */   public static final Function LEN = new Function(32, "len", 1);
/* 111:244 */   public static final Function VALUE = new Function(33, "value", 1);
/* 112:246 */   public static final Function TRUE = new Function(34, "true", 0);
/* 113:248 */   public static final Function FALSE = new Function(35, "false", 0);
/* 114:250 */   public static final Function AND = new Function(36, "and", 255);
/* 115:252 */   public static final Function OR = new Function(37, "or", 255);
/* 116:254 */   public static final Function NOT = new Function(38, "not", 1);
/* 117:256 */   public static final Function MOD = new Function(39, "mod", 2);
/* 118:258 */   public static final Function DCOUNT = new Function(40, "dcount", 3);
/* 119:260 */   public static final Function DSUM = new Function(41, "dsum", 3);
/* 120:262 */   public static final Function DAVERAGE = new Function(42, "daverage", 3);
/* 121:264 */   public static final Function DMIN = new Function(43, "dmin", 3);
/* 122:266 */   public static final Function DMAX = new Function(44, "dmax", 3);
/* 123:268 */   public static final Function DSTDEV = new Function(45, "dstdev", 3);
/* 124:270 */   public static final Function VAR = new Function(46, "var", 255);
/* 125:272 */   public static final Function DVAR = new Function(47, "dvar", 3);
/* 126:274 */   public static final Function TEXT = new Function(48, "text", 2);
/* 127:276 */   public static final Function LINEST = new Function(49, "linest", 255);
/* 128:278 */   public static final Function TREND = new Function(50, "trend", 255);
/* 129:280 */   public static final Function LOGEST = new Function(51, "logest", 255);
/* 130:282 */   public static final Function GROWTH = new Function(52, "growth", 255);
/* 131:286 */   public static final Function PV = new Function(56, "pv", 255);
/* 132:288 */   public static final Function FV = new Function(57, "fv", 255);
/* 133:290 */   public static final Function NPER = new Function(58, "nper", 255);
/* 134:292 */   public static final Function PMT = new Function(59, "pmt", 255);
/* 135:294 */   public static final Function RATE = new Function(60, "rate", 255);
/* 136:298 */   public static final Function RAND = new Function(63, "rand", 0);
/* 137:300 */   public static final Function MATCH = new Function(64, "match", 3);
/* 138:302 */   public static final Function DATE = new Function(65, "date", 3);
/* 139:304 */   public static final Function TIME = new Function(66, "time", 3);
/* 140:306 */   public static final Function DAY = new Function(67, "day", 1);
/* 141:308 */   public static final Function MONTH = new Function(68, "month", 1);
/* 142:310 */   public static final Function YEAR = new Function(69, "year", 1);
/* 143:312 */   public static final Function WEEKDAY = new Function(70, "weekday", 2);
/* 144:314 */   public static final Function HOUR = new Function(71, "hour", 1);
/* 145:316 */   public static final Function MINUTE = new Function(72, "minute", 1);
/* 146:318 */   public static final Function SECOND = new Function(73, "second", 1);
/* 147:320 */   public static final Function NOW = new Function(74, "now", 0);
/* 148:322 */   public static final Function AREAS = new Function(75, "areas", 255);
/* 149:324 */   public static final Function ROWS = new Function(76, "rows", 1);
/* 150:326 */   public static final Function COLUMNS = new Function(77, "columns", 255);
/* 151:328 */   public static final Function OFFSET = new Function(78, "offset", 255);
/* 152:333 */   public static final Function SEARCH = new Function(82, "search", 255);
/* 153:334 */   public static final Function TRANSPOSE = new Function(83, "transpose", 255);
/* 154:336 */   public static final Function ERROR = new Function(84, "error", 1);
/* 155:339 */   public static final Function TYPE = new Function(86, "type", 1);
/* 156:351 */   public static final Function ATAN2 = new Function(97, "atan2", 1);
/* 157:353 */   public static final Function ASIN = new Function(98, "asin", 1);
/* 158:355 */   public static final Function ACOS = new Function(99, "acos", 1);
/* 159:357 */   public static final Function CHOOSE = new Function(100, "choose", 255);
/* 160:359 */   public static final Function HLOOKUP = new Function(101, "hlookup", 255);
/* 161:361 */   public static final Function VLOOKUP = new Function(102, "vlookup", 255);
/* 162:365 */   public static final Function ISREF = new Function(105, "isref", 1);
/* 163:370 */   public static final Function LOG = new Function(109, "log", 255);
/* 164:373 */   public static final Function CHAR = new Function(111, "char", 1);
/* 165:375 */   public static final Function LOWER = new Function(112, "lower", 1);
/* 166:377 */   public static final Function UPPER = new Function(113, "upper", 1);
/* 167:379 */   public static final Function PROPER = new Function(114, "proper", 1);
/* 168:381 */   public static final Function LEFT = new Function(115, "left", 255);
/* 169:383 */   public static final Function RIGHT = new Function(116, "right", 255);
/* 170:385 */   public static final Function EXACT = new Function(117, "exact", 2);
/* 171:387 */   public static final Function TRIM = new Function(118, "trim", 1);
/* 172:389 */   public static final Function REPLACE = new Function(119, "replace", 4);
/* 173:391 */   public static final Function SUBSTITUTE = new Function(120, "substitute", 255);
/* 174:393 */   public static final Function CODE = new Function(121, "code", 1);
/* 175:397 */   public static final Function FIND = new Function(124, "find", 255);
/* 176:399 */   public static final Function CELL = new Function(125, "cell", 2);
/* 177:401 */   public static final Function ISERR = new Function(126, "iserr", 1);
/* 178:403 */   public static final Function ISTEXT = new Function(127, "istext", 1);
/* 179:405 */   public static final Function ISNUMBER = new Function(128, "isnumber", 1);
/* 180:407 */   public static final Function ISBLANK = new Function(129, "isblank", 1);
/* 181:409 */   public static final Function T = new Function(130, "t", 1);
/* 182:411 */   public static final Function N = new Function(131, "n", 1);
/* 183:421 */   public static final Function DATEVALUE = new Function(140, "datevalue", 1);
/* 184:423 */   public static final Function TIMEVALUE = new Function(141, "timevalue", 1);
/* 185:425 */   public static final Function SLN = new Function(142, "sln", 3);
/* 186:427 */   public static final Function SYD = new Function(143, "syd", 3);
/* 187:429 */   public static final Function DDB = new Function(144, "ddb", 255);
/* 188:434 */   public static final Function INDIRECT = new Function(148, "indirect", 255);
/* 189:456 */   public static final Function CLEAN = new Function(162, "clean", 1);
/* 190:458 */   public static final Function MDETERM = new Function(163, "mdeterm", 255);
/* 191:460 */   public static final Function MINVERSE = new Function(164, "minverse", 255);
/* 192:462 */   public static final Function MMULT = new Function(165, "mmult", 255);
/* 193:466 */   public static final Function IPMT = new Function(167, "ipmt", 255);
/* 194:468 */   public static final Function PPMT = new Function(168, "ppmt", 255);
/* 195:470 */   public static final Function COUNTA = new Function(169, "counta", 255);
/* 196:472 */   public static final Function PRODUCT = new Function(183, "product", 255);
/* 197:474 */   public static final Function FACT = new Function(184, "fact", 1);
/* 198:482 */   public static final Function DPRODUCT = new Function(189, "dproduct", 3);
/* 199:484 */   public static final Function ISNONTEXT = new Function(190, "isnontext", 1);
/* 200:488 */   public static final Function STDEVP = new Function(193, "stdevp", 255);
/* 201:490 */   public static final Function VARP = new Function(194, "varp", 255);
/* 202:492 */   public static final Function DSTDEVP = new Function(195, "dstdevp", 255);
/* 203:494 */   public static final Function DVARP = new Function(196, "dvarp", 255);
/* 204:496 */   public static final Function TRUNC = new Function(197, "trunc", 255);
/* 205:498 */   public static final Function ISLOGICAL = new Function(198, "islogical", 1);
/* 206:500 */   public static final Function DCOUNTA = new Function(199, "dcounta", 255);
/* 207:502 */   public static final Function FINDB = new Function(205, "findb", 255);
/* 208:504 */   public static final Function SEARCHB = new Function(206, "searchb", 3);
/* 209:506 */   public static final Function REPLACEB = new Function(207, "replaceb", 4);
/* 210:508 */   public static final Function LEFTB = new Function(208, "leftb", 255);
/* 211:510 */   public static final Function RIGHTB = new Function(209, "rightb", 255);
/* 212:512 */   public static final Function MIDB = new Function(210, "midb", 3);
/* 213:514 */   public static final Function LENB = new Function(211, "lenb", 1);
/* 214:516 */   public static final Function ROUNDUP = new Function(212, "roundup", 2);
/* 215:518 */   public static final Function ROUNDDOWN = new Function(213, "rounddown", 2);
/* 216:520 */   public static final Function RANK = new Function(216, "rank", 255);
/* 217:522 */   public static final Function ADDRESS = new Function(219, "address", 255);
/* 218:524 */   public static final Function AYS360 = new Function(220, "days360", 255);
/* 219:526 */   public static final Function ODAY = new Function(221, "today", 0);
/* 220:528 */   public static final Function VDB = new Function(222, "vdb", 255);
/* 221:530 */   public static final Function MEDIAN = new Function(227, "median", 255);
/* 222:532 */   public static final Function SUMPRODUCT = new Function(228, "sumproduct", 255);
/* 223:534 */   public static final Function SINH = new Function(229, "sinh", 1);
/* 224:536 */   public static final Function COSH = new Function(230, "cosh", 1);
/* 225:538 */   public static final Function TANH = new Function(231, "tanh", 1);
/* 226:540 */   public static final Function ASINH = new Function(232, "asinh", 1);
/* 227:542 */   public static final Function ACOSH = new Function(233, "acosh", 1);
/* 228:544 */   public static final Function ATANH = new Function(234, "atanh", 1);
/* 229:546 */   public static final Function INFO = new Function(244, "info", 1);
/* 230:548 */   public static final Function AVEDEV = new Function(269, "avedev", 255);
/* 231:550 */   public static final Function BETADIST = new Function(270, "betadist", 255);
/* 232:552 */   public static final Function GAMMALN = new Function(271, "gammaln", 1);
/* 233:554 */   public static final Function BETAINV = new Function(272, "betainv", 255);
/* 234:556 */   public static final Function BINOMDIST = new Function(273, "binomdist", 4);
/* 235:558 */   public static final Function CHIDIST = new Function(274, "chidist", 2);
/* 236:560 */   public static final Function CHIINV = new Function(275, "chiinv", 2);
/* 237:562 */   public static final Function COMBIN = new Function(276, "combin", 2);
/* 238:564 */   public static final Function CONFIDENCE = new Function(277, "confidence", 3);
/* 239:566 */   public static final Function CRITBINOM = new Function(278, "critbinom", 3);
/* 240:568 */   public static final Function EVEN = new Function(279, "even", 1);
/* 241:570 */   public static final Function EXPONDIST = new Function(280, "expondist", 3);
/* 242:572 */   public static final Function FDIST = new Function(281, "fdist", 3);
/* 243:574 */   public static final Function FINV = new Function(282, "finv", 3);
/* 244:576 */   public static final Function FISHER = new Function(283, "fisher", 1);
/* 245:578 */   public static final Function FISHERINV = new Function(284, "fisherinv", 1);
/* 246:580 */   public static final Function FLOOR = new Function(285, "floor", 2);
/* 247:582 */   public static final Function GAMMADIST = new Function(286, "gammadist", 4);
/* 248:584 */   public static final Function GAMMAINV = new Function(287, "gammainv", 3);
/* 249:586 */   public static final Function CEILING = new Function(288, "ceiling", 2);
/* 250:588 */   public static final Function HYPGEOMDIST = new Function(289, "hypgeomdist", 4);
/* 251:590 */   public static final Function LOGNORMDIST = new Function(290, "lognormdist", 3);
/* 252:592 */   public static final Function LOGINV = new Function(291, "loginv", 3);
/* 253:594 */   public static final Function NEGBINOMDIST = new Function(292, "negbinomdist", 3);
/* 254:596 */   public static final Function NORMDIST = new Function(293, "normdist", 4);
/* 255:598 */   public static final Function NORMSDIST = new Function(294, "normsdist", 1);
/* 256:600 */   public static final Function NORMINV = new Function(295, "norminv", 3);
/* 257:602 */   public static final Function NORMSINV = new Function(296, "normsinv", 1);
/* 258:604 */   public static final Function STANDARDIZE = new Function(297, "standardize", 3);
/* 259:606 */   public static final Function ODD = new Function(298, "odd", 1);
/* 260:608 */   public static final Function PERMUT = new Function(299, "permut", 2);
/* 261:610 */   public static final Function POISSON = new Function(300, "poisson", 3);
/* 262:612 */   public static final Function TDIST = new Function(301, "tdist", 3);
/* 263:614 */   public static final Function WEIBULL = new Function(302, "weibull", 4);
/* 264:616 */   public static final Function SUMXMY2 = new Function(303, "sumxmy2", 255);
/* 265:618 */   public static final Function SUMX2MY2 = new Function(304, "sumx2my2", 255);
/* 266:620 */   public static final Function SUMX2PY2 = new Function(305, "sumx2py2", 255);
/* 267:622 */   public static final Function CHITEST = new Function(306, "chitest", 255);
/* 268:624 */   public static final Function CORREL = new Function(307, "correl", 255);
/* 269:626 */   public static final Function COVAR = new Function(308, "covar", 255);
/* 270:628 */   public static final Function FORECAST = new Function(309, "forecast", 255);
/* 271:630 */   public static final Function FTEST = new Function(310, "ftest", 255);
/* 272:632 */   public static final Function INTERCEPT = new Function(311, "intercept", 255);
/* 273:634 */   public static final Function PEARSON = new Function(312, "pearson", 255);
/* 274:636 */   public static final Function RSQ = new Function(313, "rsq", 255);
/* 275:638 */   public static final Function STEYX = new Function(314, "steyx", 255);
/* 276:640 */   public static final Function SLOPE = new Function(315, "slope", 2);
/* 277:642 */   public static final Function TTEST = new Function(316, "ttest", 255);
/* 278:644 */   public static final Function PROB = new Function(317, "prob", 255);
/* 279:646 */   public static final Function DEVSQ = new Function(318, "devsq", 255);
/* 280:648 */   public static final Function GEOMEAN = new Function(319, "geomean", 255);
/* 281:650 */   public static final Function HARMEAN = new Function(320, "harmean", 255);
/* 282:652 */   public static final Function SUMSQ = new Function(321, "sumsq", 255);
/* 283:654 */   public static final Function KURT = new Function(322, "kurt", 255);
/* 284:656 */   public static final Function SKEW = new Function(323, "skew", 255);
/* 285:658 */   public static final Function ZTEST = new Function(324, "ztest", 255);
/* 286:660 */   public static final Function LARGE = new Function(325, "large", 255);
/* 287:662 */   public static final Function SMALL = new Function(326, "small", 255);
/* 288:664 */   public static final Function QUARTILE = new Function(327, "quartile", 255);
/* 289:666 */   public static final Function PERCENTILE = new Function(328, "percentile", 255);
/* 290:668 */   public static final Function PERCENTRANK = new Function(329, "percentrank", 255);
/* 291:670 */   public static final Function MODE = new Function(330, "mode", 255);
/* 292:672 */   public static final Function TRIMMEAN = new Function(331, "trimmean", 255);
/* 293:674 */   public static final Function TINV = new Function(332, "tinv", 2);
/* 294:676 */   public static final Function CONCATENATE = new Function(336, "concatenate", 255);
/* 295:678 */   public static final Function POWER = new Function(337, "power", 2);
/* 296:680 */   public static final Function RADIANS = new Function(342, "radians", 1);
/* 297:682 */   public static final Function DEGREES = new Function(343, "degrees", 1);
/* 298:684 */   public static final Function SUBTOTAL = new Function(344, "subtotal", 255);
/* 299:686 */   public static final Function SUMIF = new Function(345, "sumif", 255);
/* 300:688 */   public static final Function COUNTIF = new Function(346, "countif", 2);
/* 301:690 */   public static final Function COUNTBLANK = new Function(347, "countblank", 1);
/* 302:692 */   public static final Function HYPERLINK = new Function(359, "hyperlink", 2);
/* 303:694 */   public static final Function AVERAGEA = new Function(361, "averagea", 255);
/* 304:696 */   public static final Function MAXA = new Function(362, "maxa", 255);
/* 305:698 */   public static final Function MINA = new Function(363, "mina", 255);
/* 306:700 */   public static final Function STDEVPA = new Function(364, "stdevpa", 255);
/* 307:702 */   public static final Function VARPA = new Function(365, "varpa", 255);
/* 308:704 */   public static final Function STDEVA = new Function(366, "stdeva", 255);
/* 309:706 */   public static final Function VARA = new Function(367, "vara", 255);
/* 310:711 */   public static final Function IF = new Function(65534, "if", 255);
/* 311:715 */   public static final Function UNKNOWN = new Function(65535, "", 0);
/* 312:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Function
 * JD-Core Version:    0.7.0.1
 */