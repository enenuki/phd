/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.function.AnsiTrimEmulationFunction;
/*   4:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   7:    */ import org.hibernate.type.StandardBasicTypes;
/*   8:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*   9:    */ import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
/*  10:    */ 
/*  11:    */ public class SybaseASE15Dialect
/*  12:    */   extends AbstractTransactSQLDialect
/*  13:    */ {
/*  14:    */   public SybaseASE15Dialect()
/*  15:    */   {
/*  16: 48 */     registerColumnType(-4, "image");
/*  17: 49 */     registerColumnType(-1, "text");
/*  18: 50 */     registerColumnType(-5, "bigint");
/*  19: 51 */     registerColumnType(91, "date");
/*  20: 52 */     registerColumnType(3, "numeric($p,$s)");
/*  21: 53 */     registerColumnType(92, "time");
/*  22: 54 */     registerColumnType(7, "real");
/*  23: 55 */     registerColumnType(16, "tinyint");
/*  24:    */     
/*  25: 57 */     registerFunction("second", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(second, ?1)"));
/*  26: 58 */     registerFunction("minute", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(minute, ?1)"));
/*  27: 59 */     registerFunction("hour", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(hour, ?1)"));
/*  28: 60 */     registerFunction("extract", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(?1, ?3)"));
/*  29: 61 */     registerFunction("mod", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2"));
/*  30: 62 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datalength(?1) * 8"));
/*  31: 63 */     registerFunction("trim", new AnsiTrimEmulationFunction("ltrim", "rtrim", "str_replace"));
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 69 */     registerFunction("atan2", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "atn2(?1, ?2"));
/*  38: 70 */     registerFunction("atn2", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "atn2(?1, ?2"));
/*  39:    */     
/*  40: 72 */     registerFunction("biginttohex", new SQLFunctionTemplate(StandardBasicTypes.STRING, "biginttohext(?1)"));
/*  41: 73 */     registerFunction("char_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "char_length(?1)"));
/*  42: 74 */     registerFunction("charindex", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "charindex(?1, ?2)"));
/*  43: 75 */     registerFunction("coalesce", new VarArgsSQLFunction("coalesce(", ",", ")"));
/*  44: 76 */     registerFunction("col_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "col_length(?1, ?2)"));
/*  45: 77 */     registerFunction("col_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "col_name(?1, ?2)"));
/*  46:    */     
/*  47: 79 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME));
/*  48: 80 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE));
/*  49:    */     
/*  50:    */ 
/*  51: 83 */     registerFunction("data_pages", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "data_pages(?1, ?2)"));
/*  52: 84 */     registerFunction("data_pages", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "data_pages(?1, ?2, ?3)"));
/*  53:    */     
/*  54:    */ 
/*  55: 87 */     registerFunction("data_pages", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "data_pages(?1, ?2, ?3, ?4)"));
/*  56:    */     
/*  57:    */ 
/*  58: 90 */     registerFunction("datalength", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datalength(?1)"));
/*  59: 91 */     registerFunction("dateadd", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "dateadd"));
/*  60: 92 */     registerFunction("datediff", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datediff"));
/*  61: 93 */     registerFunction("datepart", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart"));
/*  62: 94 */     registerFunction("datetime", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "datetime"));
/*  63: 95 */     registerFunction("db_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "db_id(?1)"));
/*  64: 96 */     registerFunction("difference", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "difference(?1,?2)"));
/*  65: 97 */     registerFunction("db_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "db_name(?1)"));
/*  66: 98 */     registerFunction("has_role", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "has_role(?1, ?2)"));
/*  67: 99 */     registerFunction("hextobigint", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "hextobigint(?1)"));
/*  68:100 */     registerFunction("hextoint", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "hextoint(?1)"));
/*  69:101 */     registerFunction("host_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "host_id"));
/*  70:102 */     registerFunction("host_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "host_name"));
/*  71:103 */     registerFunction("inttohex", new SQLFunctionTemplate(StandardBasicTypes.STRING, "inttohex(?1)"));
/*  72:104 */     registerFunction("is_quiesced", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "is_quiesced(?1)"));
/*  73:105 */     registerFunction("is_sec_service_on", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "is_sec_service_on(?1)"));
/*  74:    */     
/*  75:    */ 
/*  76:108 */     registerFunction("object_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "object_id(?1)"));
/*  77:109 */     registerFunction("object_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "object_name(?1)"));
/*  78:110 */     registerFunction("pagesize", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "pagesize(?1)"));
/*  79:111 */     registerFunction("pagesize", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "pagesize(?1, ?2)"));
/*  80:112 */     registerFunction("pagesize", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "pagesize(?1, ?2, ?3)"));
/*  81:113 */     registerFunction("partition_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "partition_id(?1, ?2)"));
/*  82:    */     
/*  83:    */ 
/*  84:116 */     registerFunction("partition_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "partition_id(?1, ?2, ?3)"));
/*  85:    */     
/*  86:    */ 
/*  87:119 */     registerFunction("partition_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "partition_name(?1, ?2)"));
/*  88:    */     
/*  89:    */ 
/*  90:122 */     registerFunction("partition_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "partition_name(?1, ?2, ?3)"));
/*  91:    */     
/*  92:    */ 
/*  93:125 */     registerFunction("patindex", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "patindex"));
/*  94:126 */     registerFunction("proc_role", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "proc_role"));
/*  95:127 */     registerFunction("role_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "role_name"));
/*  96:    */     
/*  97:129 */     registerFunction("row_count", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "row_count"));
/*  98:130 */     registerFunction("rand2", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "rand2(?1)"));
/*  99:131 */     registerFunction("rand2", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "rand2"));
/* 100:132 */     registerFunction("replicate", new SQLFunctionTemplate(StandardBasicTypes.STRING, "replicate(?1,?2)"));
/* 101:133 */     registerFunction("role_contain", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "role_contain"));
/* 102:134 */     registerFunction("role_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "role_id"));
/* 103:135 */     registerFunction("reserved_pages", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "reserved_pages"));
/* 104:136 */     registerFunction("right", new SQLFunctionTemplate(StandardBasicTypes.STRING, "right"));
/* 105:137 */     registerFunction("show_role", new SQLFunctionTemplate(StandardBasicTypes.STRING, "show_role"));
/* 106:138 */     registerFunction("show_sec_services", new SQLFunctionTemplate(StandardBasicTypes.STRING, "show_sec_services"));
/* 107:    */     
/* 108:    */ 
/* 109:141 */     registerFunction("sortkey", new VarArgsSQLFunction(StandardBasicTypes.BINARY, "sortkey(", ",", ")"));
/* 110:142 */     registerFunction("soundex", new SQLFunctionTemplate(StandardBasicTypes.STRING, "sounded"));
/* 111:143 */     registerFunction("stddev", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "stddev"));
/* 112:144 */     registerFunction("stddev_pop", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "stddev_pop"));
/* 113:145 */     registerFunction("stddev_samp", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "stddev_samp"));
/* 114:146 */     registerFunction("stuff", new SQLFunctionTemplate(StandardBasicTypes.STRING, "stuff"));
/* 115:147 */     registerFunction("substring", new VarArgsSQLFunction(StandardBasicTypes.STRING, "substring(", ",", ")"));
/* 116:148 */     registerFunction("suser_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "suser_id"));
/* 117:149 */     registerFunction("suser_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "suser_name"));
/* 118:150 */     registerFunction("tempdb_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "tempdb_id"));
/* 119:151 */     registerFunction("textvalid", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "textvalid"));
/* 120:152 */     registerFunction("to_unichar", new SQLFunctionTemplate(StandardBasicTypes.STRING, "to_unichar(?1)"));
/* 121:153 */     registerFunction("tran_dumptable_status", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "ran_dumptable_status(?1)"));
/* 122:    */     
/* 123:    */ 
/* 124:    */ 
/* 125:157 */     registerFunction("uhighsurr", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "uhighsurr"));
/* 126:158 */     registerFunction("ulowsurr", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "ulowsurr"));
/* 127:159 */     registerFunction("uscalar", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "uscalar"));
/* 128:160 */     registerFunction("used_pages", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "used_pages"));
/* 129:161 */     registerFunction("user_id", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "user_id"));
/* 130:162 */     registerFunction("user_name", new SQLFunctionTemplate(StandardBasicTypes.STRING, "user_name"));
/* 131:163 */     registerFunction("valid_name", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "valid_name"));
/* 132:164 */     registerFunction("valid_user", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "valid_user"));
/* 133:165 */     registerFunction("variance", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "variance"));
/* 134:166 */     registerFunction("var_pop", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "var_pop"));
/* 135:167 */     registerFunction("var_samp", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "var_samp"));
/* 136:168 */     registerFunction("sysdate", new NoArgSQLFunction("getdate", StandardBasicTypes.TIMESTAMP));
/* 137:    */     
/* 138:170 */     registerSybaseKeywords();
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void registerSybaseKeywords()
/* 142:    */   {
/* 143:174 */     registerKeyword("add");
/* 144:175 */     registerKeyword("all");
/* 145:176 */     registerKeyword("alter");
/* 146:177 */     registerKeyword("and");
/* 147:178 */     registerKeyword("any");
/* 148:179 */     registerKeyword("arith_overflow");
/* 149:180 */     registerKeyword("as");
/* 150:181 */     registerKeyword("asc");
/* 151:182 */     registerKeyword("at");
/* 152:183 */     registerKeyword("authorization");
/* 153:184 */     registerKeyword("avg");
/* 154:185 */     registerKeyword("begin");
/* 155:186 */     registerKeyword("between");
/* 156:187 */     registerKeyword("break");
/* 157:188 */     registerKeyword("browse");
/* 158:189 */     registerKeyword("bulk");
/* 159:190 */     registerKeyword("by");
/* 160:191 */     registerKeyword("cascade");
/* 161:192 */     registerKeyword("case");
/* 162:193 */     registerKeyword("char_convert");
/* 163:194 */     registerKeyword("check");
/* 164:195 */     registerKeyword("checkpoint");
/* 165:196 */     registerKeyword("close");
/* 166:197 */     registerKeyword("clustered");
/* 167:198 */     registerKeyword("coalesce");
/* 168:199 */     registerKeyword("commit");
/* 169:200 */     registerKeyword("compute");
/* 170:201 */     registerKeyword("confirm");
/* 171:202 */     registerKeyword("connect");
/* 172:203 */     registerKeyword("constraint");
/* 173:204 */     registerKeyword("continue");
/* 174:205 */     registerKeyword("controlrow");
/* 175:206 */     registerKeyword("convert");
/* 176:207 */     registerKeyword("count");
/* 177:208 */     registerKeyword("count_big");
/* 178:209 */     registerKeyword("create");
/* 179:210 */     registerKeyword("current");
/* 180:211 */     registerKeyword("cursor");
/* 181:212 */     registerKeyword("database");
/* 182:213 */     registerKeyword("dbcc");
/* 183:214 */     registerKeyword("deallocate");
/* 184:215 */     registerKeyword("declare");
/* 185:216 */     registerKeyword("decrypt");
/* 186:217 */     registerKeyword("default");
/* 187:218 */     registerKeyword("delete");
/* 188:219 */     registerKeyword("desc");
/* 189:220 */     registerKeyword("determnistic");
/* 190:221 */     registerKeyword("disk");
/* 191:222 */     registerKeyword("distinct");
/* 192:223 */     registerKeyword("drop");
/* 193:224 */     registerKeyword("dummy");
/* 194:225 */     registerKeyword("dump");
/* 195:226 */     registerKeyword("else");
/* 196:227 */     registerKeyword("encrypt");
/* 197:228 */     registerKeyword("end");
/* 198:229 */     registerKeyword("endtran");
/* 199:230 */     registerKeyword("errlvl");
/* 200:231 */     registerKeyword("errordata");
/* 201:232 */     registerKeyword("errorexit");
/* 202:233 */     registerKeyword("escape");
/* 203:234 */     registerKeyword("except");
/* 204:235 */     registerKeyword("exclusive");
/* 205:236 */     registerKeyword("exec");
/* 206:237 */     registerKeyword("execute");
/* 207:238 */     registerKeyword("exist");
/* 208:239 */     registerKeyword("exit");
/* 209:240 */     registerKeyword("exp_row_size");
/* 210:241 */     registerKeyword("external");
/* 211:242 */     registerKeyword("fetch");
/* 212:243 */     registerKeyword("fillfactor");
/* 213:244 */     registerKeyword("for");
/* 214:245 */     registerKeyword("foreign");
/* 215:246 */     registerKeyword("from");
/* 216:247 */     registerKeyword("goto");
/* 217:248 */     registerKeyword("grant");
/* 218:249 */     registerKeyword("group");
/* 219:250 */     registerKeyword("having");
/* 220:251 */     registerKeyword("holdlock");
/* 221:252 */     registerKeyword("identity");
/* 222:253 */     registerKeyword("identity_gap");
/* 223:254 */     registerKeyword("identity_start");
/* 224:255 */     registerKeyword("if");
/* 225:256 */     registerKeyword("in");
/* 226:257 */     registerKeyword("index");
/* 227:258 */     registerKeyword("inout");
/* 228:259 */     registerKeyword("insensitive");
/* 229:260 */     registerKeyword("insert");
/* 230:261 */     registerKeyword("install");
/* 231:262 */     registerKeyword("intersect");
/* 232:263 */     registerKeyword("into");
/* 233:264 */     registerKeyword("is");
/* 234:265 */     registerKeyword("isolation");
/* 235:266 */     registerKeyword("jar");
/* 236:267 */     registerKeyword("join");
/* 237:268 */     registerKeyword("key");
/* 238:269 */     registerKeyword("kill");
/* 239:270 */     registerKeyword("level");
/* 240:271 */     registerKeyword("like");
/* 241:272 */     registerKeyword("lineno");
/* 242:273 */     registerKeyword("load");
/* 243:274 */     registerKeyword("lock");
/* 244:275 */     registerKeyword("materialized");
/* 245:276 */     registerKeyword("max");
/* 246:277 */     registerKeyword("max_rows_per_page");
/* 247:278 */     registerKeyword("min");
/* 248:279 */     registerKeyword("mirror");
/* 249:280 */     registerKeyword("mirrorexit");
/* 250:281 */     registerKeyword("modify");
/* 251:282 */     registerKeyword("national");
/* 252:283 */     registerKeyword("new");
/* 253:284 */     registerKeyword("noholdlock");
/* 254:285 */     registerKeyword("nonclustered");
/* 255:286 */     registerKeyword("nonscrollable");
/* 256:287 */     registerKeyword("non_sensitive");
/* 257:288 */     registerKeyword("not");
/* 258:289 */     registerKeyword("null");
/* 259:290 */     registerKeyword("nullif");
/* 260:291 */     registerKeyword("numeric_truncation");
/* 261:292 */     registerKeyword("of");
/* 262:293 */     registerKeyword("off");
/* 263:294 */     registerKeyword("offsets");
/* 264:295 */     registerKeyword("on");
/* 265:296 */     registerKeyword("once");
/* 266:297 */     registerKeyword("online");
/* 267:298 */     registerKeyword("only");
/* 268:299 */     registerKeyword("open");
/* 269:300 */     registerKeyword("option");
/* 270:301 */     registerKeyword("or");
/* 271:302 */     registerKeyword("order");
/* 272:303 */     registerKeyword("out");
/* 273:304 */     registerKeyword("output");
/* 274:305 */     registerKeyword("over");
/* 275:306 */     registerKeyword("artition");
/* 276:307 */     registerKeyword("perm");
/* 277:308 */     registerKeyword("permanent");
/* 278:309 */     registerKeyword("plan");
/* 279:310 */     registerKeyword("prepare");
/* 280:311 */     registerKeyword("primary");
/* 281:312 */     registerKeyword("print");
/* 282:313 */     registerKeyword("privileges");
/* 283:314 */     registerKeyword("proc");
/* 284:315 */     registerKeyword("procedure");
/* 285:316 */     registerKeyword("processexit");
/* 286:317 */     registerKeyword("proxy_table");
/* 287:318 */     registerKeyword("public");
/* 288:319 */     registerKeyword("quiesce");
/* 289:320 */     registerKeyword("raiserror");
/* 290:321 */     registerKeyword("read");
/* 291:322 */     registerKeyword("readpast");
/* 292:323 */     registerKeyword("readtext");
/* 293:324 */     registerKeyword("reconfigure");
/* 294:325 */     registerKeyword("references");
/* 295:326 */     registerKeyword("remove");
/* 296:327 */     registerKeyword("reorg");
/* 297:328 */     registerKeyword("replace");
/* 298:329 */     registerKeyword("replication");
/* 299:330 */     registerKeyword("reservepagegap");
/* 300:331 */     registerKeyword("return");
/* 301:332 */     registerKeyword("returns");
/* 302:333 */     registerKeyword("revoke");
/* 303:334 */     registerKeyword("role");
/* 304:335 */     registerKeyword("rollback");
/* 305:336 */     registerKeyword("rowcount");
/* 306:337 */     registerKeyword("rows");
/* 307:338 */     registerKeyword("rule");
/* 308:339 */     registerKeyword("save");
/* 309:340 */     registerKeyword("schema");
/* 310:341 */     registerKeyword("scroll");
/* 311:342 */     registerKeyword("scrollable");
/* 312:343 */     registerKeyword("select");
/* 313:344 */     registerKeyword("semi_sensitive");
/* 314:345 */     registerKeyword("set");
/* 315:346 */     registerKeyword("setuser");
/* 316:347 */     registerKeyword("shared");
/* 317:348 */     registerKeyword("shutdown");
/* 318:349 */     registerKeyword("some");
/* 319:350 */     registerKeyword("statistics");
/* 320:351 */     registerKeyword("stringsize");
/* 321:352 */     registerKeyword("stripe");
/* 322:353 */     registerKeyword("sum");
/* 323:354 */     registerKeyword("syb_identity");
/* 324:355 */     registerKeyword("syb_restree");
/* 325:356 */     registerKeyword("syb_terminate");
/* 326:357 */     registerKeyword("top");
/* 327:358 */     registerKeyword("table");
/* 328:359 */     registerKeyword("temp");
/* 329:360 */     registerKeyword("temporary");
/* 330:361 */     registerKeyword("textsize");
/* 331:362 */     registerKeyword("to");
/* 332:363 */     registerKeyword("tracefile");
/* 333:364 */     registerKeyword("tran");
/* 334:365 */     registerKeyword("transaction");
/* 335:366 */     registerKeyword("trigger");
/* 336:367 */     registerKeyword("truncate");
/* 337:368 */     registerKeyword("tsequal");
/* 338:369 */     registerKeyword("union");
/* 339:370 */     registerKeyword("unique");
/* 340:371 */     registerKeyword("unpartition");
/* 341:372 */     registerKeyword("update");
/* 342:373 */     registerKeyword("use");
/* 343:374 */     registerKeyword("user");
/* 344:375 */     registerKeyword("user_option");
/* 345:376 */     registerKeyword("using");
/* 346:377 */     registerKeyword("values");
/* 347:378 */     registerKeyword("varying");
/* 348:379 */     registerKeyword("view");
/* 349:380 */     registerKeyword("waitfor");
/* 350:381 */     registerKeyword("when");
/* 351:382 */     registerKeyword("where");
/* 352:383 */     registerKeyword("while");
/* 353:384 */     registerKeyword("with");
/* 354:385 */     registerKeyword("work");
/* 355:386 */     registerKeyword("writetext");
/* 356:387 */     registerKeyword("xmlextract");
/* 357:388 */     registerKeyword("xmlparse");
/* 358:389 */     registerKeyword("xmltest");
/* 359:390 */     registerKeyword("xmlvalidate");
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean supportsCascadeDelete()
/* 363:    */   {
/* 364:396 */     return false;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public int getMaxAliasLength()
/* 368:    */   {
/* 369:400 */     return 30;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public boolean areStringComparisonsCaseInsensitive()
/* 373:    */   {
/* 374:410 */     return true;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public String getCurrentTimestampSQLFunctionName()
/* 378:    */   {
/* 379:414 */     return "getdate()";
/* 380:    */   }
/* 381:    */   
/* 382:    */   public boolean supportsExpectedLobUsagePattern()
/* 383:    */   {
/* 384:423 */     return false;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public boolean supportsUniqueConstraintInCreateAlterTable()
/* 388:    */   {
/* 389:427 */     return false;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public String getCrossJoinSeparator()
/* 393:    */   {
/* 394:431 */     return ", ";
/* 395:    */   }
/* 396:    */   
/* 397:    */   protected SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
/* 398:    */   {
/* 399:436 */     return sqlCode == 16 ? TinyIntTypeDescriptor.INSTANCE : super.getSqlTypeDescriptorOverride(sqlCode);
/* 400:    */   }
/* 401:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SybaseASE15Dialect
 * JD-Core Version:    0.7.0.1
 */