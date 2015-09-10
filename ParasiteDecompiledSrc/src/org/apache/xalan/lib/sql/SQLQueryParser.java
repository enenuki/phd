/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.xalan.extensions.ExpressionContext;
/*   8:    */ import org.apache.xml.utils.QName;
/*   9:    */ import org.apache.xpath.objects.XObject;
/*  10:    */ 
/*  11:    */ public class SQLQueryParser
/*  12:    */ {
/*  13: 46 */   private boolean m_InlineVariables = false;
/*  14: 51 */   private boolean m_IsCallable = false;
/*  15: 56 */   private String m_OrigQuery = null;
/*  16: 61 */   private StringBuffer m_ParsedQuery = null;
/*  17: 66 */   private Vector m_Parameters = null;
/*  18: 71 */   private boolean m_hasOutput = false;
/*  19:    */   private boolean m_HasParameters;
/*  20:    */   public static final int NO_OVERRIDE = 0;
/*  21:    */   public static final int NO_INLINE_PARSER = 1;
/*  22:    */   public static final int INLINE_PARSER = 2;
/*  23:    */   
/*  24:    */   public SQLQueryParser()
/*  25:    */   {
/*  26: 90 */     init();
/*  27:    */   }
/*  28:    */   
/*  29:    */   private SQLQueryParser(String query)
/*  30:    */   {
/*  31: 98 */     this.m_OrigQuery = query;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void init() {}
/*  35:    */   
/*  36:    */   public SQLQueryParser parse(XConnection xconn, String query, int override)
/*  37:    */   {
/*  38:125 */     SQLQueryParser parser = new SQLQueryParser(query);
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:129 */     parser.parse(xconn, override);
/*  43:    */     
/*  44:131 */     return parser;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void parse(XConnection xconn, int override)
/*  48:    */   {
/*  49:154 */     this.m_InlineVariables = "true".equals(xconn.getFeature("inline-variables"));
/*  50:155 */     if (override == 1) {
/*  51:155 */       this.m_InlineVariables = false;
/*  52:156 */     } else if (override == 2) {
/*  53:156 */       this.m_InlineVariables = true;
/*  54:    */     }
/*  55:158 */     if (this.m_InlineVariables) {
/*  56:158 */       inlineParser();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean hasParameters()
/*  61:    */   {
/*  62:169 */     return this.m_HasParameters;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isCallable()
/*  66:    */   {
/*  67:184 */     return this.m_IsCallable;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Vector getParameters()
/*  71:    */   {
/*  72:193 */     return this.m_Parameters;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setParameters(Vector p)
/*  76:    */   {
/*  77:203 */     this.m_HasParameters = true;
/*  78:204 */     this.m_Parameters = p;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getSQLQuery()
/*  82:    */   {
/*  83:214 */     if (this.m_InlineVariables) {
/*  84:214 */       return this.m_ParsedQuery.toString();
/*  85:    */     }
/*  86:215 */     return this.m_OrigQuery;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void populateStatement(PreparedStatement stmt, ExpressionContext ctx)
/*  90:    */   {
/*  91:232 */     for (int indx = 0; indx < this.m_Parameters.size(); indx++)
/*  92:    */     {
/*  93:234 */       QueryParameter parm = (QueryParameter)this.m_Parameters.elementAt(indx);
/*  94:    */       try
/*  95:    */       {
/*  96:239 */         if (this.m_InlineVariables)
/*  97:    */         {
/*  98:241 */           XObject value = ctx.getVariableOrParam(new QName(parm.getName()));
/*  99:243 */           if (value != null) {
/* 100:245 */             stmt.setObject(indx + 1, value.object(), parm.getType(), 4);
/* 101:    */           } else {
/* 102:252 */             stmt.setNull(indx + 1, parm.getType());
/* 103:    */           }
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:257 */           String value = parm.getValue();
/* 108:259 */           if (value != null) {
/* 109:261 */             stmt.setObject(indx + 1, value, parm.getType(), 4);
/* 110:    */           } else {
/* 111:268 */             stmt.setNull(indx + 1, parm.getType());
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115:    */       catch (Exception tx) {}
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void registerOutputParameters(CallableStatement cstmt)
/* 120:    */     throws SQLException
/* 121:    */   {
/* 122:283 */     if ((this.m_IsCallable) && (this.m_hasOutput)) {
/* 123:285 */       for (int indx = 0; indx < this.m_Parameters.size(); indx++)
/* 124:    */       {
/* 125:287 */         QueryParameter parm = (QueryParameter)this.m_Parameters.elementAt(indx);
/* 126:288 */         if (parm.isOutput()) {
/* 127:291 */           cstmt.registerOutParameter(indx + 1, parm.getType());
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected void inlineParser()
/* 134:    */   {
/* 135:302 */     QueryParameter curParm = null;
/* 136:303 */     int state = 0;
/* 137:304 */     StringBuffer tok = new StringBuffer();
/* 138:305 */     boolean firstword = true;
/* 139:307 */     if (this.m_Parameters == null) {
/* 140:307 */       this.m_Parameters = new Vector();
/* 141:    */     }
/* 142:309 */     if (this.m_ParsedQuery == null) {
/* 143:309 */       this.m_ParsedQuery = new StringBuffer();
/* 144:    */     }
/* 145:311 */     for (int idx = 0; idx < this.m_OrigQuery.length(); idx++)
/* 146:    */     {
/* 147:313 */       char ch = this.m_OrigQuery.charAt(idx);
/* 148:314 */       switch (state)
/* 149:    */       {
/* 150:    */       case 0: 
/* 151:318 */         if (ch == '\'')
/* 152:    */         {
/* 153:318 */           state = 1;
/* 154:    */         }
/* 155:319 */         else if (ch == '?')
/* 156:    */         {
/* 157:319 */           state = 4;
/* 158:    */         }
/* 159:320 */         else if ((firstword) && ((Character.isLetterOrDigit(ch)) || (ch == '#')))
/* 160:    */         {
/* 161:322 */           tok.append(ch);
/* 162:323 */           state = 3;
/* 163:    */         }
/* 164:325 */         this.m_ParsedQuery.append(ch);
/* 165:326 */         break;
/* 166:    */       case 1: 
/* 167:329 */         if (ch == '\'') {
/* 168:329 */           state = 0;
/* 169:330 */         } else if (ch == '\\') {
/* 170:330 */           state = 2;
/* 171:    */         }
/* 172:331 */         this.m_ParsedQuery.append(ch);
/* 173:332 */         break;
/* 174:    */       case 2: 
/* 175:335 */         state = 1;
/* 176:336 */         this.m_ParsedQuery.append(ch);
/* 177:337 */         break;
/* 178:    */       case 3: 
/* 179:340 */         if ((Character.isLetterOrDigit(ch)) || (ch == '#') || (ch == '_'))
/* 180:    */         {
/* 181:340 */           tok.append(ch);
/* 182:    */         }
/* 183:    */         else
/* 184:    */         {
/* 185:343 */           if (tok.toString().equalsIgnoreCase("call"))
/* 186:    */           {
/* 187:345 */             this.m_IsCallable = true;
/* 188:346 */             if (curParm != null) {
/* 189:349 */               curParm.setIsOutput(true);
/* 190:    */             }
/* 191:    */           }
/* 192:353 */           firstword = false;
/* 193:354 */           tok = new StringBuffer();
/* 194:355 */           if (ch == '\'') {
/* 195:355 */             state = 1;
/* 196:356 */           } else if (ch == '?') {
/* 197:356 */             state = 4;
/* 198:    */           } else {
/* 199:357 */             state = 0;
/* 200:    */           }
/* 201:    */         }
/* 202:360 */         this.m_ParsedQuery.append(ch);
/* 203:361 */         break;
/* 204:    */       case 4: 
/* 205:364 */         if (ch == '[') {
/* 206:364 */           state = 5;
/* 207:    */         }
/* 208:    */         break;
/* 209:    */       case 5: 
/* 210:368 */         if ((!Character.isWhitespace(ch)) && (ch != '='))
/* 211:    */         {
/* 212:370 */           tok.append(Character.toUpperCase(ch));
/* 213:    */         }
/* 214:372 */         else if (tok.length() > 0)
/* 215:    */         {
/* 216:375 */           this.m_HasParameters = true;
/* 217:    */           
/* 218:377 */           curParm = new QueryParameter();
/* 219:    */           
/* 220:379 */           curParm.setTypeName(tok.toString());
/* 221:    */           
/* 222:381 */           this.m_Parameters.addElement(curParm);
/* 223:382 */           tok = new StringBuffer();
/* 224:383 */           if (ch == '=') {
/* 225:383 */             state = 7;
/* 226:    */           } else {
/* 227:384 */             state = 6;
/* 228:    */           }
/* 229:    */         }
/* 230:    */         break;
/* 231:    */       case 6: 
/* 232:389 */         if (ch == '=') {
/* 233:389 */           state = 7;
/* 234:    */         }
/* 235:    */         break;
/* 236:    */       case 7: 
/* 237:393 */         if ((!Character.isWhitespace(ch)) && (ch != ']'))
/* 238:    */         {
/* 239:393 */           tok.append(ch);
/* 240:    */         }
/* 241:394 */         else if (tok.length() > 0)
/* 242:    */         {
/* 243:396 */           curParm.setName(tok.toString());
/* 244:397 */           tok = new StringBuffer();
/* 245:398 */           if (ch == ']') {
/* 246:401 */             state = 0;
/* 247:    */           } else {
/* 248:403 */             state = 8;
/* 249:    */           }
/* 250:    */         }
/* 251:    */         break;
/* 252:    */       case 8: 
/* 253:408 */         if ((!Character.isWhitespace(ch)) && (ch != ']'))
/* 254:    */         {
/* 255:410 */           tok.append(ch);
/* 256:    */         }
/* 257:412 */         else if (tok.length() > 0)
/* 258:    */         {
/* 259:414 */           tok.setLength(3);
/* 260:415 */           if (tok.toString().equalsIgnoreCase("OUT"))
/* 261:    */           {
/* 262:417 */             curParm.setIsOutput(true);
/* 263:418 */             this.m_hasOutput = true;
/* 264:    */           }
/* 265:421 */           tok = new StringBuffer();
/* 266:422 */           if (ch == ']') {
/* 267:424 */             state = 0;
/* 268:    */           }
/* 269:    */         }
/* 270:    */         break;
/* 271:    */       }
/* 272:    */     }
/* 273:433 */     if (this.m_IsCallable)
/* 274:    */     {
/* 275:435 */       this.m_ParsedQuery.insert(0, '{');
/* 276:436 */       this.m_ParsedQuery.append('}');
/* 277:    */     }
/* 278:    */   }
/* 279:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.SQLQueryParser
 * JD-Core Version:    0.7.0.1
 */