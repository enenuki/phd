/*   1:    */ package org.apache.xpath.compiler;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.templates.FuncKey;
/*   6:    */ import org.apache.xpath.functions.FuncBoolean;
/*   7:    */ import org.apache.xpath.functions.FuncCeiling;
/*   8:    */ import org.apache.xpath.functions.FuncConcat;
/*   9:    */ import org.apache.xpath.functions.FuncContains;
/*  10:    */ import org.apache.xpath.functions.FuncCount;
/*  11:    */ import org.apache.xpath.functions.FuncCurrent;
/*  12:    */ import org.apache.xpath.functions.FuncDoclocation;
/*  13:    */ import org.apache.xpath.functions.FuncExtElementAvailable;
/*  14:    */ import org.apache.xpath.functions.FuncExtFunctionAvailable;
/*  15:    */ import org.apache.xpath.functions.FuncFalse;
/*  16:    */ import org.apache.xpath.functions.FuncFloor;
/*  17:    */ import org.apache.xpath.functions.FuncGenerateId;
/*  18:    */ import org.apache.xpath.functions.FuncId;
/*  19:    */ import org.apache.xpath.functions.FuncLang;
/*  20:    */ import org.apache.xpath.functions.FuncLast;
/*  21:    */ import org.apache.xpath.functions.FuncLocalPart;
/*  22:    */ import org.apache.xpath.functions.FuncNamespace;
/*  23:    */ import org.apache.xpath.functions.FuncNormalizeSpace;
/*  24:    */ import org.apache.xpath.functions.FuncNot;
/*  25:    */ import org.apache.xpath.functions.FuncNumber;
/*  26:    */ import org.apache.xpath.functions.FuncPosition;
/*  27:    */ import org.apache.xpath.functions.FuncQname;
/*  28:    */ import org.apache.xpath.functions.FuncRound;
/*  29:    */ import org.apache.xpath.functions.FuncStartsWith;
/*  30:    */ import org.apache.xpath.functions.FuncString;
/*  31:    */ import org.apache.xpath.functions.FuncStringLength;
/*  32:    */ import org.apache.xpath.functions.FuncSubstring;
/*  33:    */ import org.apache.xpath.functions.FuncSubstringAfter;
/*  34:    */ import org.apache.xpath.functions.FuncSubstringBefore;
/*  35:    */ import org.apache.xpath.functions.FuncSum;
/*  36:    */ import org.apache.xpath.functions.FuncSystemProperty;
/*  37:    */ import org.apache.xpath.functions.FuncTranslate;
/*  38:    */ import org.apache.xpath.functions.FuncTrue;
/*  39:    */ import org.apache.xpath.functions.FuncUnparsedEntityURI;
/*  40:    */ import org.apache.xpath.functions.Function;
/*  41:    */ 
/*  42:    */ public class FunctionTable
/*  43:    */ {
/*  44:    */   public static final int FUNC_CURRENT = 0;
/*  45:    */   public static final int FUNC_LAST = 1;
/*  46:    */   public static final int FUNC_POSITION = 2;
/*  47:    */   public static final int FUNC_COUNT = 3;
/*  48:    */   public static final int FUNC_ID = 4;
/*  49:    */   public static final int FUNC_KEY = 5;
/*  50:    */   public static final int FUNC_LOCAL_PART = 7;
/*  51:    */   public static final int FUNC_NAMESPACE = 8;
/*  52:    */   public static final int FUNC_QNAME = 9;
/*  53:    */   public static final int FUNC_GENERATE_ID = 10;
/*  54:    */   public static final int FUNC_NOT = 11;
/*  55:    */   public static final int FUNC_TRUE = 12;
/*  56:    */   public static final int FUNC_FALSE = 13;
/*  57:    */   public static final int FUNC_BOOLEAN = 14;
/*  58:    */   public static final int FUNC_NUMBER = 15;
/*  59:    */   public static final int FUNC_FLOOR = 16;
/*  60:    */   public static final int FUNC_CEILING = 17;
/*  61:    */   public static final int FUNC_ROUND = 18;
/*  62:    */   public static final int FUNC_SUM = 19;
/*  63:    */   public static final int FUNC_STRING = 20;
/*  64:    */   public static final int FUNC_STARTS_WITH = 21;
/*  65:    */   public static final int FUNC_CONTAINS = 22;
/*  66:    */   public static final int FUNC_SUBSTRING_BEFORE = 23;
/*  67:    */   public static final int FUNC_SUBSTRING_AFTER = 24;
/*  68:    */   public static final int FUNC_NORMALIZE_SPACE = 25;
/*  69:    */   public static final int FUNC_TRANSLATE = 26;
/*  70:    */   public static final int FUNC_CONCAT = 27;
/*  71:    */   public static final int FUNC_SUBSTRING = 29;
/*  72:    */   public static final int FUNC_STRING_LENGTH = 30;
/*  73:    */   public static final int FUNC_SYSTEM_PROPERTY = 31;
/*  74:    */   public static final int FUNC_LANG = 32;
/*  75:    */   public static final int FUNC_EXT_FUNCTION_AVAILABLE = 33;
/*  76:    */   public static final int FUNC_EXT_ELEM_AVAILABLE = 34;
/*  77:    */   public static final int FUNC_UNPARSED_ENTITY_URI = 36;
/*  78:    */   public static final int FUNC_DOCLOCATION = 35;
/*  79:    */   private static Class[] m_functions;
/*  80:147 */   private static HashMap m_functionID = new HashMap();
/*  81:152 */   private Class[] m_functions_customer = new Class[30];
/*  82:157 */   private HashMap m_functionID_customer = new HashMap();
/*  83:    */   private static final int NUM_BUILT_IN_FUNCS = 37;
/*  84:    */   private static final int NUM_ALLOWABLE_ADDINS = 30;
/*  85:173 */   private int m_funcNextFreeIndex = 37;
/*  86:    */   
/*  87:    */   static
/*  88:    */   {
/*  89:177 */     m_functions = new Class[37];
/*  90:178 */     m_functions[0] = FuncCurrent.class;
/*  91:179 */     m_functions[1] = FuncLast.class;
/*  92:180 */     m_functions[2] = FuncPosition.class;
/*  93:181 */     m_functions[3] = FuncCount.class;
/*  94:182 */     m_functions[4] = FuncId.class;
/*  95:183 */     m_functions[5] = FuncKey.class;
/*  96:    */     
/*  97:185 */     m_functions[7] = FuncLocalPart.class;
/*  98:    */     
/*  99:187 */     m_functions[8] = FuncNamespace.class;
/* 100:    */     
/* 101:189 */     m_functions[9] = FuncQname.class;
/* 102:190 */     m_functions[10] = FuncGenerateId.class;
/* 103:    */     
/* 104:192 */     m_functions[11] = FuncNot.class;
/* 105:193 */     m_functions[12] = FuncTrue.class;
/* 106:194 */     m_functions[13] = FuncFalse.class;
/* 107:195 */     m_functions[14] = FuncBoolean.class;
/* 108:196 */     m_functions[32] = FuncLang.class;
/* 109:197 */     m_functions[15] = FuncNumber.class;
/* 110:198 */     m_functions[16] = FuncFloor.class;
/* 111:199 */     m_functions[17] = FuncCeiling.class;
/* 112:200 */     m_functions[18] = FuncRound.class;
/* 113:201 */     m_functions[19] = FuncSum.class;
/* 114:202 */     m_functions[20] = FuncString.class;
/* 115:203 */     m_functions[21] = FuncStartsWith.class;
/* 116:    */     
/* 117:205 */     m_functions[22] = FuncContains.class;
/* 118:206 */     m_functions[23] = FuncSubstringBefore.class;
/* 119:    */     
/* 120:208 */     m_functions[24] = FuncSubstringAfter.class;
/* 121:    */     
/* 122:210 */     m_functions[25] = FuncNormalizeSpace.class;
/* 123:    */     
/* 124:212 */     m_functions[26] = FuncTranslate.class;
/* 125:    */     
/* 126:214 */     m_functions[27] = FuncConcat.class;
/* 127:215 */     m_functions[31] = FuncSystemProperty.class;
/* 128:    */     
/* 129:217 */     m_functions[33] = FuncExtFunctionAvailable.class;
/* 130:    */     
/* 131:219 */     m_functions[34] = FuncExtElementAvailable.class;
/* 132:    */     
/* 133:221 */     m_functions[29] = FuncSubstring.class;
/* 134:    */     
/* 135:223 */     m_functions[30] = FuncStringLength.class;
/* 136:    */     
/* 137:225 */     m_functions[35] = FuncDoclocation.class;
/* 138:    */     
/* 139:227 */     m_functions[36] = FuncUnparsedEntityURI.class;
/* 140:    */     
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:232 */     m_functionID.put("current", new Integer(0));
/* 145:    */     
/* 146:234 */     m_functionID.put("last", new Integer(1));
/* 147:    */     
/* 148:236 */     m_functionID.put("position", new Integer(2));
/* 149:    */     
/* 150:238 */     m_functionID.put("count", new Integer(3));
/* 151:    */     
/* 152:240 */     m_functionID.put("id", new Integer(4));
/* 153:    */     
/* 154:242 */     m_functionID.put("key", new Integer(5));
/* 155:    */     
/* 156:244 */     m_functionID.put("local-name", new Integer(7));
/* 157:    */     
/* 158:246 */     m_functionID.put("namespace-uri", new Integer(8));
/* 159:    */     
/* 160:248 */     m_functionID.put("name", new Integer(9));
/* 161:    */     
/* 162:250 */     m_functionID.put("generate-id", new Integer(10));
/* 163:    */     
/* 164:252 */     m_functionID.put("not", new Integer(11));
/* 165:    */     
/* 166:254 */     m_functionID.put("true", new Integer(12));
/* 167:    */     
/* 168:256 */     m_functionID.put("false", new Integer(13));
/* 169:    */     
/* 170:258 */     m_functionID.put("boolean", new Integer(14));
/* 171:    */     
/* 172:260 */     m_functionID.put("lang", new Integer(32));
/* 173:    */     
/* 174:262 */     m_functionID.put("number", new Integer(15));
/* 175:    */     
/* 176:264 */     m_functionID.put("floor", new Integer(16));
/* 177:    */     
/* 178:266 */     m_functionID.put("ceiling", new Integer(17));
/* 179:    */     
/* 180:268 */     m_functionID.put("round", new Integer(18));
/* 181:    */     
/* 182:270 */     m_functionID.put("sum", new Integer(19));
/* 183:    */     
/* 184:272 */     m_functionID.put("string", new Integer(20));
/* 185:    */     
/* 186:274 */     m_functionID.put("starts-with", new Integer(21));
/* 187:    */     
/* 188:276 */     m_functionID.put("contains", new Integer(22));
/* 189:    */     
/* 190:278 */     m_functionID.put("substring-before", new Integer(23));
/* 191:    */     
/* 192:280 */     m_functionID.put("substring-after", new Integer(24));
/* 193:    */     
/* 194:282 */     m_functionID.put("normalize-space", new Integer(25));
/* 195:    */     
/* 196:284 */     m_functionID.put("translate", new Integer(26));
/* 197:    */     
/* 198:286 */     m_functionID.put("concat", new Integer(27));
/* 199:    */     
/* 200:288 */     m_functionID.put("system-property", new Integer(31));
/* 201:    */     
/* 202:290 */     m_functionID.put("function-available", new Integer(33));
/* 203:    */     
/* 204:292 */     m_functionID.put("element-available", new Integer(34));
/* 205:    */     
/* 206:294 */     m_functionID.put("substring", new Integer(29));
/* 207:    */     
/* 208:296 */     m_functionID.put("string-length", new Integer(30));
/* 209:    */     
/* 210:298 */     m_functionID.put("unparsed-entity-uri", new Integer(36));
/* 211:    */     
/* 212:300 */     m_functionID.put("document-location", new Integer(35));
/* 213:    */   }
/* 214:    */   
/* 215:    */   String getFunctionName(int funcID)
/* 216:    */   {
/* 217:312 */     if (funcID < 37) {
/* 218:312 */       return m_functions[funcID].getName();
/* 219:    */     }
/* 220:313 */     return this.m_functions_customer[(funcID - 37)].getName();
/* 221:    */   }
/* 222:    */   
/* 223:    */   Function getFunction(int which)
/* 224:    */     throws TransformerException
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:332 */       if (which < 37) {
/* 229:333 */         return (Function)m_functions[which].newInstance();
/* 230:    */       }
/* 231:335 */       return (Function)this.m_functions_customer[(which - 37)].newInstance();
/* 232:    */     }
/* 233:    */     catch (IllegalAccessException ex)
/* 234:    */     {
/* 235:338 */       throw new TransformerException(ex.getMessage());
/* 236:    */     }
/* 237:    */     catch (InstantiationException ex)
/* 238:    */     {
/* 239:340 */       throw new TransformerException(ex.getMessage());
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   Object getFunctionID(String key)
/* 244:    */   {
/* 245:352 */     Object id = this.m_functionID_customer.get(key);
/* 246:353 */     if (null == id) {
/* 247:353 */       id = m_functionID.get(key);
/* 248:    */     }
/* 249:354 */     return id;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public int installFunction(String name, Class func)
/* 253:    */   {
/* 254:367 */     Object funcIndexObj = getFunctionID(name);
/* 255:    */     int funcIndex;
/* 256:369 */     if (null != funcIndexObj)
/* 257:    */     {
/* 258:371 */       funcIndex = ((Integer)funcIndexObj).intValue();
/* 259:373 */       if (funcIndex < 37)
/* 260:    */       {
/* 261:374 */         funcIndex = this.m_funcNextFreeIndex++;
/* 262:375 */         this.m_functionID_customer.put(name, new Integer(funcIndex));
/* 263:    */       }
/* 264:377 */       this.m_functions_customer[(funcIndex - 37)] = func;
/* 265:    */     }
/* 266:    */     else
/* 267:    */     {
/* 268:381 */       funcIndex = this.m_funcNextFreeIndex++;
/* 269:    */       
/* 270:383 */       this.m_functions_customer[(funcIndex - 37)] = func;
/* 271:    */       
/* 272:385 */       this.m_functionID_customer.put(name, new Integer(funcIndex));
/* 273:    */     }
/* 274:388 */     return funcIndex;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean functionAvailable(String methName)
/* 278:    */   {
/* 279:400 */     Object tblEntry = m_functionID.get(methName);
/* 280:401 */     if (null != tblEntry) {
/* 281:401 */       return true;
/* 282:    */     }
/* 283:403 */     tblEntry = this.m_functionID_customer.get(methName);
/* 284:404 */     return null != tblEntry;
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.FunctionTable
 * JD-Core Version:    0.7.0.1
 */