/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.util.Stack;
/*    4:     */ import java.util.Vector;
/*    5:     */ import java_cup.runtime.Symbol;
/*    6:     */ import java_cup.runtime.lr_parser;
/*    7:     */ 
/*    8:     */ class CUP$XPathParser$actions
/*    9:     */ {
/*   10:     */   private final XPathParser parser;
/*   11:     */   
/*   12:     */   CUP$XPathParser$actions(XPathParser parser)
/*   13:     */   {
/*   14:1119 */     this.parser = parser;
/*   15:     */   }
/*   16:     */   
/*   17:     */   public final Symbol CUP$XPathParser$do_action(int CUP$XPathParser$act_num, lr_parser CUP$XPathParser$parser, Stack CUP$XPathParser$stack, int CUP$XPathParser$top)
/*   18:     */     throws Exception
/*   19:     */   {
/*   20:     */     Symbol CUP$XPathParser$result;
/*   21:1134 */     switch (CUP$XPathParser$act_num)
/*   22:     */     {
/*   23:     */     case 140: 
/*   24:1139 */       QName RESULT = null;
/*   25:1140 */       RESULT = this.parser.getQNameIgnoreDefaultNs("id");
/*   26:1141 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   27:     */       
/*   28:1143 */       return CUP$XPathParser$result;
/*   29:     */     case 139: 
/*   30:1148 */       QName RESULT = null;
/*   31:1149 */       RESULT = this.parser.getQNameIgnoreDefaultNs("self");
/*   32:1150 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   33:     */       
/*   34:1152 */       return CUP$XPathParser$result;
/*   35:     */     case 138: 
/*   36:1157 */       QName RESULT = null;
/*   37:1158 */       RESULT = this.parser.getQNameIgnoreDefaultNs("preceding-sibling");
/*   38:1159 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   39:     */       
/*   40:1161 */       return CUP$XPathParser$result;
/*   41:     */     case 137: 
/*   42:1166 */       QName RESULT = null;
/*   43:1167 */       RESULT = this.parser.getQNameIgnoreDefaultNs("preceding");
/*   44:1168 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   45:     */       
/*   46:1170 */       return CUP$XPathParser$result;
/*   47:     */     case 136: 
/*   48:1175 */       QName RESULT = null;
/*   49:1176 */       RESULT = this.parser.getQNameIgnoreDefaultNs("parent");
/*   50:1177 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   51:     */       
/*   52:1179 */       return CUP$XPathParser$result;
/*   53:     */     case 135: 
/*   54:1184 */       QName RESULT = null;
/*   55:1185 */       RESULT = this.parser.getQNameIgnoreDefaultNs("namespace");
/*   56:1186 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   57:     */       
/*   58:1188 */       return CUP$XPathParser$result;
/*   59:     */     case 134: 
/*   60:1193 */       QName RESULT = null;
/*   61:1194 */       RESULT = this.parser.getQNameIgnoreDefaultNs("following-sibling");
/*   62:1195 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   63:     */       
/*   64:1197 */       return CUP$XPathParser$result;
/*   65:     */     case 133: 
/*   66:1202 */       QName RESULT = null;
/*   67:1203 */       RESULT = this.parser.getQNameIgnoreDefaultNs("following");
/*   68:1204 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   69:     */       
/*   70:1206 */       return CUP$XPathParser$result;
/*   71:     */     case 132: 
/*   72:1211 */       QName RESULT = null;
/*   73:1212 */       RESULT = this.parser.getQNameIgnoreDefaultNs("decendant-or-self");
/*   74:1213 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   75:     */       
/*   76:1215 */       return CUP$XPathParser$result;
/*   77:     */     case 131: 
/*   78:1220 */       QName RESULT = null;
/*   79:1221 */       RESULT = this.parser.getQNameIgnoreDefaultNs("decendant");
/*   80:1222 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   81:     */       
/*   82:1224 */       return CUP$XPathParser$result;
/*   83:     */     case 130: 
/*   84:1229 */       QName RESULT = null;
/*   85:1230 */       RESULT = this.parser.getQNameIgnoreDefaultNs("child");
/*   86:1231 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   87:     */       
/*   88:1233 */       return CUP$XPathParser$result;
/*   89:     */     case 129: 
/*   90:1238 */       QName RESULT = null;
/*   91:1239 */       RESULT = this.parser.getQNameIgnoreDefaultNs("attribute");
/*   92:1240 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   93:     */       
/*   94:1242 */       return CUP$XPathParser$result;
/*   95:     */     case 128: 
/*   96:1247 */       QName RESULT = null;
/*   97:1248 */       RESULT = this.parser.getQNameIgnoreDefaultNs("ancestor-or-self");
/*   98:1249 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*   99:     */       
/*  100:1251 */       return CUP$XPathParser$result;
/*  101:     */     case 127: 
/*  102:1256 */       QName RESULT = null;
/*  103:1257 */       RESULT = this.parser.getQNameIgnoreDefaultNs("child");
/*  104:1258 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  105:     */       
/*  106:1260 */       return CUP$XPathParser$result;
/*  107:     */     case 126: 
/*  108:1265 */       QName RESULT = null;
/*  109:1266 */       RESULT = this.parser.getQNameIgnoreDefaultNs("key");
/*  110:1267 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  111:     */       
/*  112:1269 */       return CUP$XPathParser$result;
/*  113:     */     case 125: 
/*  114:1274 */       QName RESULT = null;
/*  115:1275 */       RESULT = this.parser.getQNameIgnoreDefaultNs("mod");
/*  116:1276 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  117:     */       
/*  118:1278 */       return CUP$XPathParser$result;
/*  119:     */     case 124: 
/*  120:1283 */       QName RESULT = null;
/*  121:1284 */       RESULT = this.parser.getQNameIgnoreDefaultNs("div");
/*  122:1285 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  123:     */       
/*  124:1287 */       return CUP$XPathParser$result;
/*  125:     */     case 123: 
/*  126:1292 */       QName RESULT = null;
/*  127:1293 */       int qnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  128:1294 */       int qnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  129:1295 */       String qname = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  130:1296 */       RESULT = this.parser.getQNameIgnoreDefaultNs(qname);
/*  131:1297 */       CUP$XPathParser$result = new Symbol(37, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  132:     */       
/*  133:1299 */       return CUP$XPathParser$result;
/*  134:     */     case 122: 
/*  135:1304 */       Object RESULT = null;
/*  136:1305 */       int qnleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  137:1306 */       int qnright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  138:1307 */       QName qn = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  139:1308 */       RESULT = qn;
/*  140:1309 */       CUP$XPathParser$result = new Symbol(26, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  141:     */       
/*  142:1311 */       return CUP$XPathParser$result;
/*  143:     */     case 121: 
/*  144:1316 */       Object RESULT = null;
/*  145:1317 */       RESULT = null;
/*  146:1318 */       CUP$XPathParser$result = new Symbol(26, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  147:     */       
/*  148:1320 */       return CUP$XPathParser$result;
/*  149:     */     case 120: 
/*  150:1325 */       Object RESULT = null;
/*  151:1326 */       RESULT = new Integer(7);
/*  152:1327 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  153:     */       
/*  154:1329 */       return CUP$XPathParser$result;
/*  155:     */     case 119: 
/*  156:1334 */       Object RESULT = null;
/*  157:1335 */       int lleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  158:1336 */       int lright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  159:1337 */       String l = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  160:     */       
/*  161:1339 */       QName name = this.parser.getQNameIgnoreDefaultNs("name");
/*  162:1340 */       Expression exp = new EqualityExpr(0, new NameCall(name), new LiteralExpr(l));
/*  163:     */       
/*  164:     */ 
/*  165:1343 */       Vector predicates = new Vector();
/*  166:1344 */       predicates.addElement(new Predicate(exp));
/*  167:1345 */       RESULT = new Step(3, 7, predicates);
/*  168:     */       
/*  169:1347 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  170:     */       
/*  171:1349 */       return CUP$XPathParser$result;
/*  172:     */     case 118: 
/*  173:1354 */       Object RESULT = null;
/*  174:1355 */       RESULT = new Integer(8);
/*  175:1356 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  176:     */       
/*  177:1358 */       return CUP$XPathParser$result;
/*  178:     */     case 117: 
/*  179:1363 */       Object RESULT = null;
/*  180:1364 */       RESULT = new Integer(3);
/*  181:1365 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  182:     */       
/*  183:1367 */       return CUP$XPathParser$result;
/*  184:     */     case 116: 
/*  185:1372 */       Object RESULT = null;
/*  186:1373 */       RESULT = new Integer(-1);
/*  187:1374 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  188:     */       
/*  189:1376 */       return CUP$XPathParser$result;
/*  190:     */     case 115: 
/*  191:1381 */       Object RESULT = null;
/*  192:1382 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  193:1383 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  194:1384 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  195:1385 */       RESULT = nt;
/*  196:1386 */       CUP$XPathParser$result = new Symbol(25, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  197:     */       
/*  198:1388 */       return CUP$XPathParser$result;
/*  199:     */     case 114: 
/*  200:1393 */       Expression RESULT = null;
/*  201:1394 */       int exleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  202:1395 */       int exright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  203:1396 */       Expression ex = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  204:1397 */       RESULT = ex;
/*  205:1398 */       CUP$XPathParser$result = new Symbol(3, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  206:     */       
/*  207:1400 */       return CUP$XPathParser$result;
/*  208:     */     case 113: 
/*  209:1405 */       QName RESULT = null;
/*  210:1406 */       int vnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  211:1407 */       int vnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  212:1408 */       QName vname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  213:     */       
/*  214:1410 */       RESULT = vname;
/*  215:     */       
/*  216:1412 */       CUP$XPathParser$result = new Symbol(39, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  217:     */       
/*  218:1414 */       return CUP$XPathParser$result;
/*  219:     */     case 112: 
/*  220:1419 */       QName RESULT = null;
/*  221:1420 */       int fnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  222:1421 */       int fnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  223:1422 */       QName fname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  224:     */       
/*  225:1424 */       RESULT = fname;
/*  226:     */       
/*  227:1426 */       CUP$XPathParser$result = new Symbol(38, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  228:     */       
/*  229:1428 */       return CUP$XPathParser$result;
/*  230:     */     case 111: 
/*  231:1433 */       Vector RESULT = null;
/*  232:1434 */       int argleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  233:1435 */       int argright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  234:1436 */       Expression arg = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  235:1437 */       int arglleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  236:1438 */       int arglright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  237:1439 */       Vector argl = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  238:1440 */       argl.insertElementAt(arg, 0);RESULT = argl;
/*  239:1441 */       CUP$XPathParser$result = new Symbol(36, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  240:     */       
/*  241:1443 */       return CUP$XPathParser$result;
/*  242:     */     case 110: 
/*  243:1448 */       Vector RESULT = null;
/*  244:1449 */       int argleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  245:1450 */       int argright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  246:1451 */       Expression arg = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  247:     */       
/*  248:1453 */       Vector temp = new Vector();
/*  249:1454 */       temp.addElement(arg);
/*  250:1455 */       RESULT = temp;
/*  251:     */       
/*  252:1457 */       CUP$XPathParser$result = new Symbol(36, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  253:     */       
/*  254:1459 */       return CUP$XPathParser$result;
/*  255:     */     case 109: 
/*  256:1464 */       Expression RESULT = null;
/*  257:1465 */       int fnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left;
/*  258:1466 */       int fnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).right;
/*  259:1467 */       QName fname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).value;
/*  260:1468 */       int arglleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  261:1469 */       int arglright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  262:1470 */       Vector argl = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  263:1472 */       if (fname == this.parser.getQNameIgnoreDefaultNs("concat"))
/*  264:     */       {
/*  265:1473 */         RESULT = new ConcatCall(fname, argl);
/*  266:     */       }
/*  267:1475 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("number"))
/*  268:     */       {
/*  269:1476 */         RESULT = new NumberCall(fname, argl);
/*  270:     */       }
/*  271:1478 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("document"))
/*  272:     */       {
/*  273:1479 */         this.parser.setMultiDocument(true);
/*  274:1480 */         RESULT = new DocumentCall(fname, argl);
/*  275:     */       }
/*  276:1482 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("string"))
/*  277:     */       {
/*  278:1483 */         RESULT = new StringCall(fname, argl);
/*  279:     */       }
/*  280:1485 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("boolean"))
/*  281:     */       {
/*  282:1486 */         RESULT = new BooleanCall(fname, argl);
/*  283:     */       }
/*  284:1488 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("name"))
/*  285:     */       {
/*  286:1489 */         RESULT = new NameCall(fname, argl);
/*  287:     */       }
/*  288:1491 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("generate-id"))
/*  289:     */       {
/*  290:1492 */         RESULT = new GenerateIdCall(fname, argl);
/*  291:     */       }
/*  292:1494 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("not"))
/*  293:     */       {
/*  294:1495 */         RESULT = new NotCall(fname, argl);
/*  295:     */       }
/*  296:1497 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("format-number"))
/*  297:     */       {
/*  298:1498 */         RESULT = new FormatNumberCall(fname, argl);
/*  299:     */       }
/*  300:1500 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("unparsed-entity-uri"))
/*  301:     */       {
/*  302:1501 */         RESULT = new UnparsedEntityUriCall(fname, argl);
/*  303:     */       }
/*  304:1503 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("key"))
/*  305:     */       {
/*  306:1504 */         RESULT = new KeyCall(fname, argl);
/*  307:     */       }
/*  308:1506 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("id"))
/*  309:     */       {
/*  310:1507 */         RESULT = new KeyCall(fname, argl);
/*  311:1508 */         this.parser.setHasIdCall(true);
/*  312:     */       }
/*  313:1510 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("ceiling"))
/*  314:     */       {
/*  315:1511 */         RESULT = new CeilingCall(fname, argl);
/*  316:     */       }
/*  317:1513 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("round"))
/*  318:     */       {
/*  319:1514 */         RESULT = new RoundCall(fname, argl);
/*  320:     */       }
/*  321:1516 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("floor"))
/*  322:     */       {
/*  323:1517 */         RESULT = new FloorCall(fname, argl);
/*  324:     */       }
/*  325:1519 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("contains"))
/*  326:     */       {
/*  327:1520 */         RESULT = new ContainsCall(fname, argl);
/*  328:     */       }
/*  329:1522 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("string-length"))
/*  330:     */       {
/*  331:1523 */         RESULT = new StringLengthCall(fname, argl);
/*  332:     */       }
/*  333:1525 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("starts-with"))
/*  334:     */       {
/*  335:1526 */         RESULT = new StartsWithCall(fname, argl);
/*  336:     */       }
/*  337:1528 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("function-available"))
/*  338:     */       {
/*  339:1529 */         RESULT = new FunctionAvailableCall(fname, argl);
/*  340:     */       }
/*  341:1531 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("element-available"))
/*  342:     */       {
/*  343:1532 */         RESULT = new ElementAvailableCall(fname, argl);
/*  344:     */       }
/*  345:1534 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("local-name"))
/*  346:     */       {
/*  347:1535 */         RESULT = new LocalNameCall(fname, argl);
/*  348:     */       }
/*  349:1537 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("lang"))
/*  350:     */       {
/*  351:1538 */         RESULT = new LangCall(fname, argl);
/*  352:     */       }
/*  353:1540 */       else if (fname == this.parser.getQNameIgnoreDefaultNs("namespace-uri"))
/*  354:     */       {
/*  355:1541 */         RESULT = new NamespaceUriCall(fname, argl);
/*  356:     */       }
/*  357:1543 */       else if (fname == this.parser.getQName("http://xml.apache.org/xalan/xsltc", "xsltc", "cast"))
/*  358:     */       {
/*  359:1544 */         RESULT = new CastCall(fname, argl);
/*  360:     */       }
/*  361:1547 */       else if ((fname.getLocalPart().equals("nodeset")) || (fname.getLocalPart().equals("node-set")))
/*  362:     */       {
/*  363:1548 */         this.parser.setCallsNodeset(true);
/*  364:1549 */         RESULT = new FunctionCall(fname, argl);
/*  365:     */       }
/*  366:     */       else
/*  367:     */       {
/*  368:1552 */         RESULT = new FunctionCall(fname, argl);
/*  369:     */       }
/*  370:1555 */       CUP$XPathParser$result = new Symbol(16, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  371:     */       
/*  372:1557 */       return CUP$XPathParser$result;
/*  373:     */     case 108: 
/*  374:1562 */       Expression RESULT = null;
/*  375:1563 */       int fnameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  376:1564 */       int fnameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  377:1565 */       QName fname = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  378:1568 */       if (fname == this.parser.getQNameIgnoreDefaultNs("current")) {
/*  379:1569 */         RESULT = new CurrentCall(fname);
/*  380:1571 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("number")) {
/*  381:1572 */         RESULT = new NumberCall(fname, XPathParser.EmptyArgs);
/*  382:1574 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("string")) {
/*  383:1575 */         RESULT = new StringCall(fname, XPathParser.EmptyArgs);
/*  384:1577 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("concat")) {
/*  385:1578 */         RESULT = new ConcatCall(fname, XPathParser.EmptyArgs);
/*  386:1580 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("true")) {
/*  387:1581 */         RESULT = new BooleanExpr(true);
/*  388:1583 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("false")) {
/*  389:1584 */         RESULT = new BooleanExpr(false);
/*  390:1586 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("name")) {
/*  391:1587 */         RESULT = new NameCall(fname);
/*  392:1589 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("generate-id")) {
/*  393:1590 */         RESULT = new GenerateIdCall(fname, XPathParser.EmptyArgs);
/*  394:1592 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("string-length")) {
/*  395:1593 */         RESULT = new StringLengthCall(fname, XPathParser.EmptyArgs);
/*  396:1595 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("position")) {
/*  397:1596 */         RESULT = new PositionCall(fname);
/*  398:1598 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("last")) {
/*  399:1599 */         RESULT = new LastCall(fname);
/*  400:1601 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("local-name")) {
/*  401:1602 */         RESULT = new LocalNameCall(fname);
/*  402:1604 */       } else if (fname == this.parser.getQNameIgnoreDefaultNs("namespace-uri")) {
/*  403:1605 */         RESULT = new NamespaceUriCall(fname);
/*  404:     */       } else {
/*  405:1608 */         RESULT = new FunctionCall(fname, XPathParser.EmptyArgs);
/*  406:     */       }
/*  407:1611 */       CUP$XPathParser$result = new Symbol(16, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  408:     */       
/*  409:1613 */       return CUP$XPathParser$result;
/*  410:     */     case 107: 
/*  411:1618 */       Expression RESULT = null;
/*  412:1619 */       int varNameleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  413:1620 */       int varNameright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  414:1621 */       QName varName = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  415:     */       
/*  416:     */ 
/*  417:     */ 
/*  418:1625 */       SyntaxTreeNode node = this.parser.lookupName(varName);
/*  419:1627 */       if (node != null) {
/*  420:1628 */         if ((node instanceof Variable)) {
/*  421:1629 */           RESULT = new VariableRef((Variable)node);
/*  422:1631 */         } else if ((node instanceof Param)) {
/*  423:1632 */           RESULT = new ParameterRef((Param)node);
/*  424:     */         } else {
/*  425:1635 */           RESULT = new UnresolvedRef(varName);
/*  426:     */         }
/*  427:     */       }
/*  428:1639 */       if (node == null) {
/*  429:1640 */         RESULT = new UnresolvedRef(varName);
/*  430:     */       }
/*  431:1643 */       CUP$XPathParser$result = new Symbol(15, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  432:     */       
/*  433:1645 */       return CUP$XPathParser$result;
/*  434:     */     case 106: 
/*  435:1650 */       Expression RESULT = null;
/*  436:1651 */       int fcleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  437:1652 */       int fcright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  438:1653 */       Expression fc = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  439:1654 */       RESULT = fc;
/*  440:1655 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  441:     */       
/*  442:1657 */       return CUP$XPathParser$result;
/*  443:     */     case 105: 
/*  444:1662 */       Expression RESULT = null;
/*  445:1663 */       int numleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  446:1664 */       int numright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  447:1665 */       Double num = (Double)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  448:1666 */       RESULT = new RealExpr(num.doubleValue());
/*  449:1667 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  450:     */       
/*  451:1669 */       return CUP$XPathParser$result;
/*  452:     */     case 104: 
/*  453:1674 */       Expression RESULT = null;
/*  454:1675 */       int numleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  455:1676 */       int numright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  456:1677 */       Long num = (Long)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  457:     */       
/*  458:1679 */       long value = num.longValue();
/*  459:1680 */       if ((value < -2147483648L) || (value > 2147483647L)) {
/*  460:1681 */         RESULT = new RealExpr(value);
/*  461:1684 */       } else if (num.doubleValue() == 0.0D) {
/*  462:1685 */         RESULT = new RealExpr(num.doubleValue());
/*  463:1686 */       } else if (num.intValue() == 0) {
/*  464:1687 */         RESULT = new IntExpr(num.intValue());
/*  465:1688 */       } else if (num.doubleValue() == 0.0D) {
/*  466:1689 */         RESULT = new RealExpr(num.doubleValue());
/*  467:     */       } else {
/*  468:1691 */         RESULT = new IntExpr(num.intValue());
/*  469:     */       }
/*  470:1694 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  471:     */       
/*  472:1696 */       return CUP$XPathParser$result;
/*  473:     */     case 103: 
/*  474:1701 */       Expression RESULT = null;
/*  475:1702 */       int stringleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  476:1703 */       int stringright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  477:1704 */       String string = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  478:     */       
/*  479:     */ 
/*  480:     */ 
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485:1712 */       String namespace = null;
/*  486:1713 */       int index = string.lastIndexOf(':');
/*  487:1715 */       if (index > 0)
/*  488:     */       {
/*  489:1716 */         String prefix = string.substring(0, index);
/*  490:1717 */         namespace = this.parser._symbolTable.lookupNamespace(prefix);
/*  491:     */       }
/*  492:1719 */       RESULT = namespace == null ? new LiteralExpr(string) : new LiteralExpr(string, namespace);
/*  493:     */       
/*  494:     */ 
/*  495:1722 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  496:     */       
/*  497:1724 */       return CUP$XPathParser$result;
/*  498:     */     case 102: 
/*  499:1729 */       Expression RESULT = null;
/*  500:1730 */       int exleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  501:1731 */       int exright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  502:1732 */       Expression ex = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  503:1733 */       RESULT = ex;
/*  504:1734 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  505:     */       
/*  506:1736 */       return CUP$XPathParser$result;
/*  507:     */     case 101: 
/*  508:1741 */       Expression RESULT = null;
/*  509:1742 */       int vrleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  510:1743 */       int vrright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  511:1744 */       Expression vr = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  512:1745 */       RESULT = vr;
/*  513:1746 */       CUP$XPathParser$result = new Symbol(17, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  514:     */       
/*  515:1748 */       return CUP$XPathParser$result;
/*  516:     */     case 100: 
/*  517:1753 */       Expression RESULT = null;
/*  518:1754 */       int primaryleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  519:1755 */       int primaryright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  520:1756 */       Expression primary = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  521:1757 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  522:1758 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  523:1759 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  524:1760 */       RESULT = new FilterExpr(primary, pp);
/*  525:1761 */       CUP$XPathParser$result = new Symbol(6, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  526:     */       
/*  527:1763 */       return CUP$XPathParser$result;
/*  528:     */     case 99: 
/*  529:1768 */       Expression RESULT = null;
/*  530:1769 */       int primaryleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  531:1770 */       int primaryright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  532:1771 */       Expression primary = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  533:1772 */       RESULT = primary;
/*  534:1773 */       CUP$XPathParser$result = new Symbol(6, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  535:     */       
/*  536:1775 */       return CUP$XPathParser$result;
/*  537:     */     case 98: 
/*  538:1780 */       Expression RESULT = null;
/*  539:1781 */       RESULT = new Step(10, -1, null);
/*  540:1782 */       CUP$XPathParser$result = new Symbol(20, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  541:     */       
/*  542:1784 */       return CUP$XPathParser$result;
/*  543:     */     case 97: 
/*  544:1789 */       Expression RESULT = null;
/*  545:1790 */       RESULT = new Step(13, -1, null);
/*  546:1791 */       CUP$XPathParser$result = new Symbol(20, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  547:     */       
/*  548:1793 */       return CUP$XPathParser$result;
/*  549:     */     case 96: 
/*  550:1798 */       Integer RESULT = null;
/*  551:1799 */       RESULT = new Integer(13);
/*  552:1800 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  553:     */       
/*  554:1802 */       return CUP$XPathParser$result;
/*  555:     */     case 95: 
/*  556:1807 */       Integer RESULT = null;
/*  557:1808 */       RESULT = new Integer(12);
/*  558:1809 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  559:     */       
/*  560:1811 */       return CUP$XPathParser$result;
/*  561:     */     case 94: 
/*  562:1816 */       Integer RESULT = null;
/*  563:1817 */       RESULT = new Integer(11);
/*  564:1818 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  565:     */       
/*  566:1820 */       return CUP$XPathParser$result;
/*  567:     */     case 93: 
/*  568:1825 */       Integer RESULT = null;
/*  569:1826 */       RESULT = new Integer(10);
/*  570:1827 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  571:     */       
/*  572:1829 */       return CUP$XPathParser$result;
/*  573:     */     case 92: 
/*  574:1834 */       Integer RESULT = null;
/*  575:1835 */       RESULT = new Integer(9);
/*  576:1836 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  577:     */       
/*  578:1838 */       return CUP$XPathParser$result;
/*  579:     */     case 91: 
/*  580:1843 */       Integer RESULT = null;
/*  581:1844 */       RESULT = new Integer(7);
/*  582:1845 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  583:     */       
/*  584:1847 */       return CUP$XPathParser$result;
/*  585:     */     case 90: 
/*  586:1852 */       Integer RESULT = null;
/*  587:1853 */       RESULT = new Integer(6);
/*  588:1854 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  589:     */       
/*  590:1856 */       return CUP$XPathParser$result;
/*  591:     */     case 89: 
/*  592:1861 */       Integer RESULT = null;
/*  593:1862 */       RESULT = new Integer(5);
/*  594:1863 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  595:     */       
/*  596:1865 */       return CUP$XPathParser$result;
/*  597:     */     case 88: 
/*  598:1870 */       Integer RESULT = null;
/*  599:1871 */       RESULT = new Integer(4);
/*  600:1872 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  601:     */       
/*  602:1874 */       return CUP$XPathParser$result;
/*  603:     */     case 87: 
/*  604:1879 */       Integer RESULT = null;
/*  605:1880 */       RESULT = new Integer(3);
/*  606:1881 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  607:     */       
/*  608:1883 */       return CUP$XPathParser$result;
/*  609:     */     case 86: 
/*  610:1888 */       Integer RESULT = null;
/*  611:1889 */       RESULT = new Integer(2);
/*  612:1890 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  613:     */       
/*  614:1892 */       return CUP$XPathParser$result;
/*  615:     */     case 85: 
/*  616:1897 */       Integer RESULT = null;
/*  617:1898 */       RESULT = new Integer(1);
/*  618:1899 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  619:     */       
/*  620:1901 */       return CUP$XPathParser$result;
/*  621:     */     case 84: 
/*  622:1906 */       Integer RESULT = null;
/*  623:1907 */       RESULT = new Integer(0);
/*  624:1908 */       CUP$XPathParser$result = new Symbol(40, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  625:     */       
/*  626:1910 */       return CUP$XPathParser$result;
/*  627:     */     case 83: 
/*  628:1915 */       Integer RESULT = null;
/*  629:1916 */       RESULT = new Integer(2);
/*  630:1917 */       CUP$XPathParser$result = new Symbol(41, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  631:     */       
/*  632:1919 */       return CUP$XPathParser$result;
/*  633:     */     case 82: 
/*  634:1924 */       Integer RESULT = null;
/*  635:1925 */       int anleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  636:1926 */       int anright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  637:1927 */       Integer an = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  638:1928 */       RESULT = an;
/*  639:1929 */       CUP$XPathParser$result = new Symbol(41, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  640:     */       
/*  641:1931 */       return CUP$XPathParser$result;
/*  642:     */     case 81: 
/*  643:1936 */       Expression RESULT = null;
/*  644:1937 */       int abbrevleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  645:1938 */       int abbrevright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  646:1939 */       Expression abbrev = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  647:1940 */       RESULT = abbrev;
/*  648:1941 */       CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  649:     */       
/*  650:1943 */       return CUP$XPathParser$result;
/*  651:     */     case 80: 
/*  652:1948 */       Expression RESULT = null;
/*  653:1949 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  654:1950 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  655:1951 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  656:1952 */       int ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  657:1953 */       int ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  658:1954 */       Object ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  659:1955 */       RESULT = new Step(axis.intValue(), this.parser.findNodeType(axis.intValue(), ntest), null);
/*  660:     */       
/*  661:     */ 
/*  662:     */ 
/*  663:1959 */       CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  664:     */       
/*  665:1961 */       return CUP$XPathParser$result;
/*  666:     */     case 79: 
/*  667:1966 */       Expression RESULT = null;
/*  668:1967 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  669:1968 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  670:1969 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  671:1970 */       int ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  672:1971 */       int ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  673:1972 */       Object ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  674:1973 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  675:1974 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  676:1975 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  677:1976 */       RESULT = new Step(axis.intValue(), this.parser.findNodeType(axis.intValue(), ntest), pp);
/*  678:     */       
/*  679:     */ 
/*  680:     */ 
/*  681:1980 */       CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  682:     */       
/*  683:1982 */       return CUP$XPathParser$result;
/*  684:     */     case 78: 
/*  685:1987 */       Expression RESULT = null;
/*  686:1988 */       int ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/*  687:1989 */       int ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/*  688:1990 */       Object ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/*  689:1991 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  690:1992 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  691:1993 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  692:1995 */       if ((ntest instanceof Step))
/*  693:     */       {
/*  694:1996 */         Step step = (Step)ntest;
/*  695:1997 */         step.addPredicates(pp);
/*  696:1998 */         RESULT = (Step)ntest;
/*  697:     */       }
/*  698:     */       else
/*  699:     */       {
/*  700:2001 */         RESULT = new Step(3, this.parser.findNodeType(3, ntest), pp);
/*  701:     */       }
/*  702:2005 */       CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  703:     */       
/*  704:2007 */       return CUP$XPathParser$result;
/*  705:     */     case 77: 
/*  706:2012 */       Expression RESULT = null;
/*  707:2013 */       int ntestleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  708:2014 */       int ntestright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  709:2015 */       Object ntest = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  710:2017 */       if ((ntest instanceof Step)) {
/*  711:2018 */         RESULT = (Step)ntest;
/*  712:     */       } else {
/*  713:2021 */         RESULT = new Step(3, this.parser.findNodeType(3, ntest), null);
/*  714:     */       }
/*  715:2026 */       CUP$XPathParser$result = new Symbol(7, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  716:     */       
/*  717:2028 */       return CUP$XPathParser$result;
/*  718:     */     case 76: 
/*  719:2033 */       Expression RESULT = null;
/*  720:2034 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  721:2035 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  722:2036 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  723:     */       
/*  724:     */ 
/*  725:     */ 
/*  726:     */ 
/*  727:     */ 
/*  728:2042 */       int nodeType = -1;
/*  729:2043 */       if (((rlp instanceof Step)) && (this.parser.isElementAxis(((Step)rlp).getAxis()))) {
/*  730:2046 */         nodeType = 1;
/*  731:     */       }
/*  732:2048 */       Step step = new Step(5, nodeType, null);
/*  733:2049 */       RESULT = new AbsoluteLocationPath(this.parser.insertStep(step, (RelativeLocationPath)rlp));
/*  734:     */       
/*  735:     */ 
/*  736:2052 */       CUP$XPathParser$result = new Symbol(24, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  737:     */       
/*  738:2054 */       return CUP$XPathParser$result;
/*  739:     */     case 75: 
/*  740:2059 */       Expression RESULT = null;
/*  741:2060 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  742:2061 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  743:2062 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  744:2063 */       int stepleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  745:2064 */       int stepright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  746:2065 */       Expression step = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  747:     */       
/*  748:2067 */       Step right = (Step)step;
/*  749:2068 */       int axis = right.getAxis();
/*  750:2069 */       int type = right.getNodeType();
/*  751:2070 */       Vector predicates = right.getPredicates();
/*  752:2072 */       if ((axis == 3) && (type != 2))
/*  753:     */       {
/*  754:2074 */         if (predicates == null)
/*  755:     */         {
/*  756:2075 */           right.setAxis(4);
/*  757:2076 */           if (((rlp instanceof Step)) && (((Step)rlp).isAbbreviatedDot()))
/*  758:     */           {
/*  759:2077 */             RESULT = right;
/*  760:     */           }
/*  761:     */           else
/*  762:     */           {
/*  763:2081 */             RelativeLocationPath left = (RelativeLocationPath)rlp;
/*  764:2082 */             RESULT = new ParentLocationPath(left, right);
/*  765:     */           }
/*  766:     */         }
/*  767:2087 */         else if (((rlp instanceof Step)) && (((Step)rlp).isAbbreviatedDot()))
/*  768:     */         {
/*  769:2088 */           Step left = new Step(5, 1, null);
/*  770:     */           
/*  771:2090 */           RESULT = new ParentLocationPath(left, right);
/*  772:     */         }
/*  773:     */         else
/*  774:     */         {
/*  775:2094 */           RelativeLocationPath left = (RelativeLocationPath)rlp;
/*  776:2095 */           Step mid = new Step(5, 1, null);
/*  777:     */           
/*  778:2097 */           ParentLocationPath ppl = new ParentLocationPath(mid, right);
/*  779:2098 */           RESULT = new ParentLocationPath(left, ppl);
/*  780:     */         }
/*  781:     */       }
/*  782:2102 */       else if ((axis == 2) || (type == 2))
/*  783:     */       {
/*  784:2104 */         RelativeLocationPath left = (RelativeLocationPath)rlp;
/*  785:2105 */         Step middle = new Step(5, 1, null);
/*  786:     */         
/*  787:2107 */         ParentLocationPath ppl = new ParentLocationPath(middle, right);
/*  788:2108 */         RESULT = new ParentLocationPath(left, ppl);
/*  789:     */       }
/*  790:     */       else
/*  791:     */       {
/*  792:2112 */         RelativeLocationPath left = (RelativeLocationPath)rlp;
/*  793:2113 */         Step middle = new Step(5, -1, null);
/*  794:     */         
/*  795:2115 */         ParentLocationPath ppl = new ParentLocationPath(middle, right);
/*  796:2116 */         RESULT = new ParentLocationPath(left, ppl);
/*  797:     */       }
/*  798:2119 */       CUP$XPathParser$result = new Symbol(22, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  799:     */       
/*  800:2121 */       return CUP$XPathParser$result;
/*  801:     */     case 74: 
/*  802:2126 */       Expression RESULT = null;
/*  803:2127 */       int aalpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  804:2128 */       int aalpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  805:2129 */       Expression aalp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  806:2130 */       RESULT = aalp;
/*  807:2131 */       CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  808:     */       
/*  809:2133 */       return CUP$XPathParser$result;
/*  810:     */     case 73: 
/*  811:2138 */       Expression RESULT = null;
/*  812:2139 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  813:2140 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  814:2141 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  815:2142 */       RESULT = new AbsoluteLocationPath(rlp);
/*  816:2143 */       CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  817:     */       
/*  818:2145 */       return CUP$XPathParser$result;
/*  819:     */     case 72: 
/*  820:2150 */       Expression RESULT = null;
/*  821:2151 */       RESULT = new AbsoluteLocationPath();
/*  822:2152 */       CUP$XPathParser$result = new Symbol(23, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  823:     */       
/*  824:2154 */       return CUP$XPathParser$result;
/*  825:     */     case 71: 
/*  826:2159 */       Expression RESULT = null;
/*  827:2160 */       int arlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  828:2161 */       int arlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  829:2162 */       Expression arlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  830:2163 */       RESULT = arlp;
/*  831:2164 */       CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  832:     */       
/*  833:2166 */       return CUP$XPathParser$result;
/*  834:     */     case 70: 
/*  835:2171 */       Expression RESULT = null;
/*  836:2172 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  837:2173 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  838:2174 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  839:2175 */       int stepleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  840:2176 */       int stepright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  841:2177 */       Expression step = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  842:2179 */       if (((rlp instanceof Step)) && (((Step)rlp).isAbbreviatedDot())) {
/*  843:2180 */         RESULT = step;
/*  844:2182 */       } else if (((Step)step).isAbbreviatedDot()) {
/*  845:2183 */         RESULT = rlp;
/*  846:     */       } else {
/*  847:2186 */         RESULT = new ParentLocationPath((RelativeLocationPath)rlp, step);
/*  848:     */       }
/*  849:2190 */       CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  850:     */       
/*  851:2192 */       return CUP$XPathParser$result;
/*  852:     */     case 69: 
/*  853:2197 */       Expression RESULT = null;
/*  854:2198 */       int stepleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  855:2199 */       int stepright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  856:2200 */       Expression step = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  857:2201 */       RESULT = step;
/*  858:2202 */       CUP$XPathParser$result = new Symbol(21, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  859:     */       
/*  860:2204 */       return CUP$XPathParser$result;
/*  861:     */     case 68: 
/*  862:2209 */       Expression RESULT = null;
/*  863:2210 */       int alpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  864:2211 */       int alpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  865:2212 */       Expression alp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  866:2213 */       RESULT = alp;
/*  867:2214 */       CUP$XPathParser$result = new Symbol(4, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  868:     */       
/*  869:2216 */       return CUP$XPathParser$result;
/*  870:     */     case 67: 
/*  871:2221 */       Expression RESULT = null;
/*  872:2222 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  873:2223 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  874:2224 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  875:2225 */       RESULT = rlp;
/*  876:2226 */       CUP$XPathParser$result = new Symbol(4, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  877:     */       
/*  878:2228 */       return CUP$XPathParser$result;
/*  879:     */     case 66: 
/*  880:2233 */       Expression RESULT = null;
/*  881:2234 */       int fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  882:2235 */       int fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  883:2236 */       Expression fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  884:2237 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  885:2238 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  886:2239 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  887:     */       
/*  888:     */ 
/*  889:     */ 
/*  890:     */ 
/*  891:     */ 
/*  892:2245 */       int nodeType = -1;
/*  893:2246 */       if (((rlp instanceof Step)) && (this.parser.isElementAxis(((Step)rlp).getAxis()))) {
/*  894:2249 */         nodeType = 1;
/*  895:     */       }
/*  896:2251 */       Step step = new Step(5, nodeType, null);
/*  897:2252 */       FilterParentPath fpp = new FilterParentPath(fexp, step);
/*  898:2253 */       fpp = new FilterParentPath(fpp, rlp);
/*  899:2254 */       if (!(fexp instanceof KeyCall)) {
/*  900:2255 */         fpp.setDescendantAxis();
/*  901:     */       }
/*  902:2257 */       RESULT = fpp;
/*  903:     */       
/*  904:2259 */       CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  905:     */       
/*  906:2261 */       return CUP$XPathParser$result;
/*  907:     */     case 65: 
/*  908:2266 */       Expression RESULT = null;
/*  909:2267 */       int fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  910:2268 */       int fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  911:2269 */       Expression fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  912:2270 */       int rlpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  913:2271 */       int rlpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  914:2272 */       Expression rlp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  915:2273 */       RESULT = new FilterParentPath(fexp, rlp);
/*  916:2274 */       CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  917:     */       
/*  918:2276 */       return CUP$XPathParser$result;
/*  919:     */     case 64: 
/*  920:2281 */       Expression RESULT = null;
/*  921:2282 */       int fexpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  922:2283 */       int fexpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  923:2284 */       Expression fexp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  924:2285 */       RESULT = fexp;
/*  925:2286 */       CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  926:     */       
/*  927:2288 */       return CUP$XPathParser$result;
/*  928:     */     case 63: 
/*  929:2293 */       Expression RESULT = null;
/*  930:2294 */       int lpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  931:2295 */       int lpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  932:2296 */       Expression lp = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  933:2297 */       RESULT = lp;
/*  934:2298 */       CUP$XPathParser$result = new Symbol(19, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  935:     */       
/*  936:2300 */       return CUP$XPathParser$result;
/*  937:     */     case 62: 
/*  938:2305 */       Expression RESULT = null;
/*  939:2306 */       int peleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  940:2307 */       int peright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  941:2308 */       Expression pe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  942:2309 */       int restleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  943:2310 */       int restright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  944:2311 */       Expression rest = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  945:2312 */       RESULT = new UnionPathExpr(pe, rest);
/*  946:2313 */       CUP$XPathParser$result = new Symbol(18, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  947:     */       
/*  948:2315 */       return CUP$XPathParser$result;
/*  949:     */     case 61: 
/*  950:2320 */       Expression RESULT = null;
/*  951:2321 */       int peleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  952:2322 */       int peright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  953:2323 */       Expression pe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  954:2324 */       RESULT = pe;
/*  955:2325 */       CUP$XPathParser$result = new Symbol(18, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  956:     */       
/*  957:2327 */       return CUP$XPathParser$result;
/*  958:     */     case 60: 
/*  959:2332 */       Expression RESULT = null;
/*  960:2333 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  961:2334 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  962:2335 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  963:2336 */       RESULT = new UnaryOpExpr(ue);
/*  964:2337 */       CUP$XPathParser$result = new Symbol(14, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  965:     */       
/*  966:2339 */       return CUP$XPathParser$result;
/*  967:     */     case 59: 
/*  968:2344 */       Expression RESULT = null;
/*  969:2345 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  970:2346 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  971:2347 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  972:2348 */       RESULT = ue;
/*  973:2349 */       CUP$XPathParser$result = new Symbol(14, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  974:     */       
/*  975:2351 */       return CUP$XPathParser$result;
/*  976:     */     case 58: 
/*  977:2356 */       Expression RESULT = null;
/*  978:2357 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  979:2358 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  980:2359 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  981:2360 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  982:2361 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  983:2362 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  984:2363 */       RESULT = new BinOpExpr(4, me, ue);
/*  985:2364 */       CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  986:     */       
/*  987:2366 */       return CUP$XPathParser$result;
/*  988:     */     case 57: 
/*  989:2371 */       Expression RESULT = null;
/*  990:2372 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/*  991:2373 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/*  992:2374 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/*  993:2375 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/*  994:2376 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/*  995:2377 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/*  996:2378 */       RESULT = new BinOpExpr(3, me, ue);
/*  997:2379 */       CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/*  998:     */       
/*  999:2381 */       return CUP$XPathParser$result;
/* 1000:     */     case 56: 
/* 1001:2386 */       Expression RESULT = null;
/* 1002:2387 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1003:2388 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1004:2389 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1005:2390 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1006:2391 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1007:2392 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1008:2393 */       RESULT = new BinOpExpr(2, me, ue);
/* 1009:2394 */       CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1010:     */       
/* 1011:2396 */       return CUP$XPathParser$result;
/* 1012:     */     case 55: 
/* 1013:2401 */       Expression RESULT = null;
/* 1014:2402 */       int ueleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1015:2403 */       int ueright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1016:2404 */       Expression ue = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1017:2405 */       RESULT = ue;
/* 1018:2406 */       CUP$XPathParser$result = new Symbol(13, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1019:     */       
/* 1020:2408 */       return CUP$XPathParser$result;
/* 1021:     */     case 54: 
/* 1022:2413 */       Expression RESULT = null;
/* 1023:2414 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1024:2415 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1025:2416 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1026:2417 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1027:2418 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1028:2419 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1029:2420 */       RESULT = new BinOpExpr(1, ae, me);
/* 1030:2421 */       CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1031:     */       
/* 1032:2423 */       return CUP$XPathParser$result;
/* 1033:     */     case 53: 
/* 1034:2428 */       Expression RESULT = null;
/* 1035:2429 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1036:2430 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1037:2431 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1038:2432 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1039:2433 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1040:2434 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1041:2435 */       RESULT = new BinOpExpr(0, ae, me);
/* 1042:2436 */       CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1043:     */       
/* 1044:2438 */       return CUP$XPathParser$result;
/* 1045:     */     case 52: 
/* 1046:2443 */       Expression RESULT = null;
/* 1047:2444 */       int meleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1048:2445 */       int meright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1049:2446 */       Expression me = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1050:2447 */       RESULT = me;
/* 1051:2448 */       CUP$XPathParser$result = new Symbol(12, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1052:     */       
/* 1053:2450 */       return CUP$XPathParser$result;
/* 1054:     */     case 51: 
/* 1055:2455 */       Expression RESULT = null;
/* 1056:2456 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1057:2457 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1058:2458 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1059:2459 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1060:2460 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1061:2461 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1062:2462 */       RESULT = new RelationalExpr(4, re, ae);
/* 1063:2463 */       CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1064:     */       
/* 1065:2465 */       return CUP$XPathParser$result;
/* 1066:     */     case 50: 
/* 1067:2470 */       Expression RESULT = null;
/* 1068:2471 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1069:2472 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1070:2473 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1071:2474 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1072:2475 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1073:2476 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1074:2477 */       RESULT = new RelationalExpr(5, re, ae);
/* 1075:2478 */       CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1076:     */       
/* 1077:2480 */       return CUP$XPathParser$result;
/* 1078:     */     case 49: 
/* 1079:2485 */       Expression RESULT = null;
/* 1080:2486 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1081:2487 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1082:2488 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1083:2489 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1084:2490 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1085:2491 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1086:2492 */       RESULT = new RelationalExpr(2, re, ae);
/* 1087:2493 */       CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1088:     */       
/* 1089:2495 */       return CUP$XPathParser$result;
/* 1090:     */     case 48: 
/* 1091:2500 */       Expression RESULT = null;
/* 1092:2501 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1093:2502 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1094:2503 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1095:2504 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1096:2505 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1097:2506 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1098:2507 */       RESULT = new RelationalExpr(3, re, ae);
/* 1099:2508 */       CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1100:     */       
/* 1101:2510 */       return CUP$XPathParser$result;
/* 1102:     */     case 47: 
/* 1103:2515 */       Expression RESULT = null;
/* 1104:2516 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1105:2517 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1106:2518 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1107:2519 */       RESULT = ae;
/* 1108:2520 */       CUP$XPathParser$result = new Symbol(11, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1109:     */       
/* 1110:2522 */       return CUP$XPathParser$result;
/* 1111:     */     case 46: 
/* 1112:2527 */       Expression RESULT = null;
/* 1113:2528 */       int eeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1114:2529 */       int eeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1115:2530 */       Expression ee = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1116:2531 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1117:2532 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1118:2533 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1119:2534 */       RESULT = new EqualityExpr(1, ee, re);
/* 1120:2535 */       CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1121:     */       
/* 1122:2537 */       return CUP$XPathParser$result;
/* 1123:     */     case 45: 
/* 1124:2542 */       Expression RESULT = null;
/* 1125:2543 */       int eeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1126:2544 */       int eeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1127:2545 */       Expression ee = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1128:2546 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1129:2547 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1130:2548 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1131:2549 */       RESULT = new EqualityExpr(0, ee, re);
/* 1132:2550 */       CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1133:     */       
/* 1134:2552 */       return CUP$XPathParser$result;
/* 1135:     */     case 44: 
/* 1136:2557 */       Expression RESULT = null;
/* 1137:2558 */       int releft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1138:2559 */       int reright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1139:2560 */       Expression re = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1140:2561 */       RESULT = re;
/* 1141:2562 */       CUP$XPathParser$result = new Symbol(10, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1142:     */       
/* 1143:2564 */       return CUP$XPathParser$result;
/* 1144:     */     case 43: 
/* 1145:2569 */       Expression RESULT = null;
/* 1146:2570 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1147:2571 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1148:2572 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1149:2573 */       int eeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1150:2574 */       int eeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1151:2575 */       Expression ee = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1152:2576 */       RESULT = new LogicalExpr(1, ae, ee);
/* 1153:2577 */       CUP$XPathParser$result = new Symbol(9, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1154:     */       
/* 1155:2579 */       return CUP$XPathParser$result;
/* 1156:     */     case 42: 
/* 1157:2584 */       Expression RESULT = null;
/* 1158:2585 */       int eleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1159:2586 */       int eright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1160:2587 */       Expression e = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1161:2588 */       RESULT = e;
/* 1162:2589 */       CUP$XPathParser$result = new Symbol(9, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1163:     */       
/* 1164:2591 */       return CUP$XPathParser$result;
/* 1165:     */     case 41: 
/* 1166:2596 */       Expression RESULT = null;
/* 1167:2597 */       int oeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1168:2598 */       int oeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1169:2599 */       Expression oe = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1170:2600 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1171:2601 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1172:2602 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1173:2603 */       RESULT = new LogicalExpr(0, oe, ae);
/* 1174:2604 */       CUP$XPathParser$result = new Symbol(8, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1175:     */       
/* 1176:2606 */       return CUP$XPathParser$result;
/* 1177:     */     case 40: 
/* 1178:2611 */       Expression RESULT = null;
/* 1179:2612 */       int aeleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1180:2613 */       int aeright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1181:2614 */       Expression ae = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1182:2615 */       RESULT = ae;
/* 1183:2616 */       CUP$XPathParser$result = new Symbol(8, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1184:     */       
/* 1185:2618 */       return CUP$XPathParser$result;
/* 1186:     */     case 39: 
/* 1187:2623 */       Expression RESULT = null;
/* 1188:2624 */       int exleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1189:2625 */       int exright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1190:2626 */       Expression ex = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1191:2627 */       RESULT = ex;
/* 1192:2628 */       CUP$XPathParser$result = new Symbol(2, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1193:     */       
/* 1194:2630 */       return CUP$XPathParser$result;
/* 1195:     */     case 38: 
/* 1196:2635 */       Expression RESULT = null;
/* 1197:2636 */       int eleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1198:2637 */       int eright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1199:2638 */       Expression e = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1200:     */       
/* 1201:2640 */       RESULT = new Predicate(e);
/* 1202:     */       
/* 1203:2642 */       CUP$XPathParser$result = new Symbol(5, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1204:     */       
/* 1205:2644 */       return CUP$XPathParser$result;
/* 1206:     */     case 37: 
/* 1207:2649 */       Vector RESULT = null;
/* 1208:2650 */       int pleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1209:2651 */       int pright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1210:2652 */       Expression p = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1211:2653 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1212:2654 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1213:2655 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1214:2656 */       pp.insertElementAt(p, 0);RESULT = pp;
/* 1215:2657 */       CUP$XPathParser$result = new Symbol(35, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1216:     */       
/* 1217:2659 */       return CUP$XPathParser$result;
/* 1218:     */     case 36: 
/* 1219:2664 */       Vector RESULT = null;
/* 1220:2665 */       int pleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1221:2666 */       int pright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1222:2667 */       Expression p = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1223:     */       
/* 1224:2669 */       Vector temp = new Vector();
/* 1225:2670 */       temp.addElement(p);
/* 1226:2671 */       RESULT = temp;
/* 1227:     */       
/* 1228:2673 */       CUP$XPathParser$result = new Symbol(35, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1229:     */       
/* 1230:2675 */       return CUP$XPathParser$result;
/* 1231:     */     case 35: 
/* 1232:2680 */       Integer RESULT = null;
/* 1233:2681 */       RESULT = new Integer(2);
/* 1234:2682 */       CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1235:     */       
/* 1236:2684 */       return CUP$XPathParser$result;
/* 1237:     */     case 34: 
/* 1238:2689 */       Integer RESULT = null;
/* 1239:2690 */       RESULT = new Integer(3);
/* 1240:2691 */       CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1241:     */       
/* 1242:2693 */       return CUP$XPathParser$result;
/* 1243:     */     case 33: 
/* 1244:2698 */       Integer RESULT = null;
/* 1245:2699 */       RESULT = new Integer(2);
/* 1246:2700 */       CUP$XPathParser$result = new Symbol(42, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1247:     */       
/* 1248:2702 */       return CUP$XPathParser$result;
/* 1249:     */     case 32: 
/* 1250:2707 */       Object RESULT = null;
/* 1251:2708 */       int qnleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1252:2709 */       int qnright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1253:2710 */       QName qn = (QName)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1254:2711 */       RESULT = qn;
/* 1255:2712 */       CUP$XPathParser$result = new Symbol(34, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1256:     */       
/* 1257:2714 */       return CUP$XPathParser$result;
/* 1258:     */     case 31: 
/* 1259:2719 */       Object RESULT = null;
/* 1260:2720 */       RESULT = null;
/* 1261:2721 */       CUP$XPathParser$result = new Symbol(34, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1262:     */       
/* 1263:2723 */       return CUP$XPathParser$result;
/* 1264:     */     case 30: 
/* 1265:2728 */       Object RESULT = null;
/* 1266:2729 */       RESULT = new Integer(7);
/* 1267:2730 */       CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1268:     */       
/* 1269:2732 */       return CUP$XPathParser$result;
/* 1270:     */     case 29: 
/* 1271:2737 */       Object RESULT = null;
/* 1272:2738 */       RESULT = new Integer(8);
/* 1273:2739 */       CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1274:     */       
/* 1275:2741 */       return CUP$XPathParser$result;
/* 1276:     */     case 28: 
/* 1277:2746 */       Object RESULT = null;
/* 1278:2747 */       RESULT = new Integer(3);
/* 1279:2748 */       CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1280:     */       
/* 1281:2750 */       return CUP$XPathParser$result;
/* 1282:     */     case 27: 
/* 1283:2755 */       Object RESULT = null;
/* 1284:2756 */       RESULT = new Integer(-1);
/* 1285:2757 */       CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1286:     */       
/* 1287:2759 */       return CUP$XPathParser$result;
/* 1288:     */     case 26: 
/* 1289:2764 */       Object RESULT = null;
/* 1290:2765 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1291:2766 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1292:2767 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1293:2768 */       RESULT = nt;
/* 1294:2769 */       CUP$XPathParser$result = new Symbol(33, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1295:     */       
/* 1296:2771 */       return CUP$XPathParser$result;
/* 1297:     */     case 25: 
/* 1298:2776 */       StepPattern RESULT = null;
/* 1299:2777 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1300:2778 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1301:2779 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1302:2780 */       int pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1303:2781 */       int pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1304:2782 */       StepPattern pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1305:2783 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1306:2784 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1307:2785 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1308:     */       
/* 1309:     */ 
/* 1310:2788 */       RESULT = (ProcessingInstructionPattern)pip.setPredicates(pp);
/* 1311:     */       
/* 1312:2790 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1313:     */       
/* 1314:2792 */       return CUP$XPathParser$result;
/* 1315:     */     case 24: 
/* 1316:2797 */       StepPattern RESULT = null;
/* 1317:2798 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1318:2799 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1319:2800 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1320:2801 */       int pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1321:2802 */       int pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1322:2803 */       StepPattern pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1323:     */       
/* 1324:2805 */       RESULT = pip;
/* 1325:     */       
/* 1326:2807 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1327:     */       
/* 1328:2809 */       return CUP$XPathParser$result;
/* 1329:     */     case 23: 
/* 1330:2814 */       StepPattern RESULT = null;
/* 1331:2815 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1332:2816 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1333:2817 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1334:2818 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1335:2819 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1336:2820 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1337:2821 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1338:2822 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1339:2823 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1340:     */       
/* 1341:2825 */       RESULT = this.parser.createStepPattern(axis.intValue(), nt, pp);
/* 1342:     */       
/* 1343:2827 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1344:     */       
/* 1345:2829 */       return CUP$XPathParser$result;
/* 1346:     */     case 22: 
/* 1347:2834 */       StepPattern RESULT = null;
/* 1348:2835 */       int axisleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1349:2836 */       int axisright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1350:2837 */       Integer axis = (Integer)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1351:2838 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1352:2839 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1353:2840 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1354:     */       
/* 1355:2842 */       RESULT = this.parser.createStepPattern(axis.intValue(), nt, null);
/* 1356:     */       
/* 1357:2844 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1358:     */       
/* 1359:2846 */       return CUP$XPathParser$result;
/* 1360:     */     case 21: 
/* 1361:2851 */       StepPattern RESULT = null;
/* 1362:2852 */       int pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1363:2853 */       int pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1364:2854 */       StepPattern pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1365:2855 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1366:2856 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1367:2857 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1368:2858 */       RESULT = (ProcessingInstructionPattern)pip.setPredicates(pp);
/* 1369:2859 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1370:     */       
/* 1371:2861 */       return CUP$XPathParser$result;
/* 1372:     */     case 20: 
/* 1373:2866 */       StepPattern RESULT = null;
/* 1374:2867 */       int pipleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1375:2868 */       int pipright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1376:2869 */       StepPattern pip = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1377:2870 */       RESULT = pip;
/* 1378:2871 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1379:     */       
/* 1380:2873 */       return CUP$XPathParser$result;
/* 1381:     */     case 19: 
/* 1382:2878 */       StepPattern RESULT = null;
/* 1383:2879 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1384:2880 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1385:2881 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1386:2882 */       int ppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1387:2883 */       int ppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1388:2884 */       Vector pp = (Vector)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1389:     */       
/* 1390:2886 */       RESULT = this.parser.createStepPattern(3, nt, pp);
/* 1391:     */       
/* 1392:2888 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1393:     */       
/* 1394:2890 */       return CUP$XPathParser$result;
/* 1395:     */     case 18: 
/* 1396:2895 */       StepPattern RESULT = null;
/* 1397:2896 */       int ntleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1398:2897 */       int ntright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1399:2898 */       Object nt = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1400:     */       
/* 1401:2900 */       RESULT = this.parser.createStepPattern(3, nt, null);
/* 1402:     */       
/* 1403:2902 */       CUP$XPathParser$result = new Symbol(32, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1404:     */       
/* 1405:2904 */       return CUP$XPathParser$result;
/* 1406:     */     case 17: 
/* 1407:2909 */       RelativePathPattern RESULT = null;
/* 1408:2910 */       int spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1409:2911 */       int spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1410:2912 */       StepPattern sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1411:2913 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1412:2914 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1413:2915 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1414:2916 */       RESULT = new AncestorPattern(sp, rpp);
/* 1415:2917 */       CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1416:     */       
/* 1417:2919 */       return CUP$XPathParser$result;
/* 1418:     */     case 16: 
/* 1419:2924 */       RelativePathPattern RESULT = null;
/* 1420:2925 */       int spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1421:2926 */       int spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1422:2927 */       StepPattern sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1423:2928 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1424:2929 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1425:2930 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1426:2931 */       RESULT = new ParentPattern(sp, rpp);
/* 1427:2932 */       CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1428:     */       
/* 1429:2934 */       return CUP$XPathParser$result;
/* 1430:     */     case 15: 
/* 1431:2939 */       RelativePathPattern RESULT = null;
/* 1432:2940 */       int spleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1433:2941 */       int spright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1434:2942 */       StepPattern sp = (StepPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1435:2943 */       RESULT = sp;
/* 1436:2944 */       CUP$XPathParser$result = new Symbol(31, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1437:     */       
/* 1438:2946 */       return CUP$XPathParser$result;
/* 1439:     */     case 14: 
/* 1440:2951 */       StepPattern RESULT = null;
/* 1441:2952 */       int lleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1442:2953 */       int lright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1443:2954 */       String l = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1444:2955 */       RESULT = new ProcessingInstructionPattern(l);
/* 1445:2956 */       CUP$XPathParser$result = new Symbol(30, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1446:     */       
/* 1447:2958 */       return CUP$XPathParser$result;
/* 1448:     */     case 13: 
/* 1449:2963 */       IdKeyPattern RESULT = null;
/* 1450:2964 */       int l1left = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left;
/* 1451:2965 */       int l1right = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).right;
/* 1452:2966 */       String l1 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).value;
/* 1453:2967 */       int l2left = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1454:2968 */       int l2right = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1455:2969 */       String l2 = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1456:2970 */       RESULT = new KeyPattern(l1, l2);
/* 1457:2971 */       CUP$XPathParser$result = new Symbol(27, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 5)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1458:     */       
/* 1459:2973 */       return CUP$XPathParser$result;
/* 1460:     */     case 12: 
/* 1461:2978 */       IdKeyPattern RESULT = null;
/* 1462:2979 */       int lleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1463:2980 */       int lright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1464:2981 */       String l = (String)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1465:2982 */       RESULT = new IdPattern(l);
/* 1466:2983 */       this.parser.setHasIdCall(true);
/* 1467:     */       
/* 1468:2985 */       CUP$XPathParser$result = new Symbol(27, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 3)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1469:     */       
/* 1470:2987 */       return CUP$XPathParser$result;
/* 1471:     */     case 11: 
/* 1472:2992 */       Pattern RESULT = null;
/* 1473:2993 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1474:2994 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1475:2995 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1476:2996 */       RESULT = rpp;
/* 1477:2997 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1478:     */       
/* 1479:2999 */       return CUP$XPathParser$result;
/* 1480:     */     case 10: 
/* 1481:3004 */       Pattern RESULT = null;
/* 1482:3005 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1483:3006 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1484:3007 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1485:3008 */       RESULT = new AncestorPattern(rpp);
/* 1486:3009 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1487:     */       
/* 1488:3011 */       return CUP$XPathParser$result;
/* 1489:     */     case 9: 
/* 1490:3016 */       Pattern RESULT = null;
/* 1491:3017 */       int ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1492:3018 */       int ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1493:3019 */       IdKeyPattern ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1494:3020 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1495:3021 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1496:3022 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1497:3023 */       RESULT = new AncestorPattern(ikp, rpp);
/* 1498:3024 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1499:     */       
/* 1500:3026 */       return CUP$XPathParser$result;
/* 1501:     */     case 8: 
/* 1502:3031 */       Pattern RESULT = null;
/* 1503:3032 */       int ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1504:3033 */       int ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1505:3034 */       IdKeyPattern ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1506:3035 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1507:3036 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1508:3037 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1509:3038 */       RESULT = new ParentPattern(ikp, rpp);
/* 1510:3039 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1511:     */       
/* 1512:3041 */       return CUP$XPathParser$result;
/* 1513:     */     case 7: 
/* 1514:3046 */       Pattern RESULT = null;
/* 1515:3047 */       int ikpleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1516:3048 */       int ikpright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1517:3049 */       IdKeyPattern ikp = (IdKeyPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1518:3050 */       RESULT = ikp;
/* 1519:3051 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1520:     */       
/* 1521:3053 */       return CUP$XPathParser$result;
/* 1522:     */     case 6: 
/* 1523:3058 */       Pattern RESULT = null;
/* 1524:3059 */       int rppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1525:3060 */       int rppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1526:3061 */       RelativePathPattern rpp = (RelativePathPattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1527:3062 */       RESULT = new AbsolutePathPattern(rpp);
/* 1528:3063 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1529:     */       
/* 1530:3065 */       return CUP$XPathParser$result;
/* 1531:     */     case 5: 
/* 1532:3070 */       Pattern RESULT = null;
/* 1533:3071 */       RESULT = new AbsolutePathPattern(null);
/* 1534:3072 */       CUP$XPathParser$result = new Symbol(29, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1535:     */       
/* 1536:3074 */       return CUP$XPathParser$result;
/* 1537:     */     case 4: 
/* 1538:3079 */       Pattern RESULT = null;
/* 1539:3080 */       int lppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left;
/* 1540:3081 */       int lppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).right;
/* 1541:3082 */       Pattern lpp = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).value;
/* 1542:3083 */       int pleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1543:3084 */       int pright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1544:3085 */       Pattern p = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1545:3086 */       RESULT = new AlternativePattern(lpp, p);
/* 1546:3087 */       CUP$XPathParser$result = new Symbol(28, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 2)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1547:     */       
/* 1548:3089 */       return CUP$XPathParser$result;
/* 1549:     */     case 3: 
/* 1550:3094 */       Pattern RESULT = null;
/* 1551:3095 */       int lppleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1552:3096 */       int lppright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1553:3097 */       Pattern lpp = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1554:3098 */       RESULT = lpp;
/* 1555:3099 */       CUP$XPathParser$result = new Symbol(28, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1556:     */       
/* 1557:3101 */       return CUP$XPathParser$result;
/* 1558:     */     case 2: 
/* 1559:3106 */       SyntaxTreeNode RESULT = null;
/* 1560:3107 */       int exprleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1561:3108 */       int exprright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1562:3109 */       Expression expr = (Expression)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1563:3110 */       RESULT = expr;
/* 1564:3111 */       CUP$XPathParser$result = new Symbol(1, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1565:     */       
/* 1566:3113 */       return CUP$XPathParser$result;
/* 1567:     */     case 1: 
/* 1568:3118 */       SyntaxTreeNode RESULT = null;
/* 1569:3119 */       int patternleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).left;
/* 1570:3120 */       int patternright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right;
/* 1571:3121 */       Pattern pattern = (Pattern)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).value;
/* 1572:3122 */       RESULT = pattern;
/* 1573:3123 */       CUP$XPathParser$result = new Symbol(1, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1574:     */       
/* 1575:3125 */       return CUP$XPathParser$result;
/* 1576:     */     case 0: 
/* 1577:3130 */       Object RESULT = null;
/* 1578:3131 */       int start_valleft = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left;
/* 1579:3132 */       int start_valright = ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).right;
/* 1580:3133 */       SyntaxTreeNode start_val = (SyntaxTreeNode)((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).value;
/* 1581:3134 */       RESULT = start_val;
/* 1582:3135 */       CUP$XPathParser$result = new Symbol(0, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 1)).left, ((Symbol)CUP$XPathParser$stack.elementAt(CUP$XPathParser$top - 0)).right, RESULT);
/* 1583:     */       
/* 1584:     */ 
/* 1585:3138 */       CUP$XPathParser$parser.done_parsing();
/* 1586:3139 */       return CUP$XPathParser$result;
/* 1587:     */     }
/* 1588:3143 */     throw new Exception("Invalid action number found in internal parse table");
/* 1589:     */   }
/* 1590:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CUP.XPathParser.actions
 * JD-Core Version:    0.7.0.1
 */