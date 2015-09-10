/*   1:    */ package org.apache.james.mime4j.field;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Locale;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ import org.apache.james.mime4j.field.contentdisposition.parser.ContentDispositionParser;
/*  13:    */ import org.apache.james.mime4j.field.datetime.DateTime;
/*  14:    */ import org.apache.james.mime4j.field.datetime.parser.DateTimeParser;
/*  15:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  16:    */ 
/*  17:    */ public class ContentDispositionField
/*  18:    */   extends AbstractField
/*  19:    */ {
/*  20: 41 */   private static Log log = LogFactory.getLog(ContentDispositionField.class);
/*  21:    */   public static final String DISPOSITION_TYPE_INLINE = "inline";
/*  22:    */   public static final String DISPOSITION_TYPE_ATTACHMENT = "attachment";
/*  23:    */   public static final String PARAM_FILENAME = "filename";
/*  24:    */   public static final String PARAM_CREATION_DATE = "creation-date";
/*  25:    */   public static final String PARAM_MODIFICATION_DATE = "modification-date";
/*  26:    */   public static final String PARAM_READ_DATE = "read-date";
/*  27:    */   public static final String PARAM_SIZE = "size";
/*  28: 64 */   private boolean parsed = false;
/*  29: 66 */   private String dispositionType = "";
/*  30: 67 */   private Map<String, String> parameters = new HashMap();
/*  31:    */   private ParseException parseException;
/*  32:    */   private boolean creationDateParsed;
/*  33:    */   private Date creationDate;
/*  34:    */   private boolean modificationDateParsed;
/*  35:    */   private Date modificationDate;
/*  36:    */   private boolean readDateParsed;
/*  37:    */   private Date readDate;
/*  38:    */   
/*  39:    */   ContentDispositionField(String name, String body, ByteSequence raw)
/*  40:    */   {
/*  41: 80 */     super(name, body, raw);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ParseException getParseException()
/*  45:    */   {
/*  46: 89 */     if (!this.parsed) {
/*  47: 90 */       parse();
/*  48:    */     }
/*  49: 92 */     return this.parseException;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getDispositionType()
/*  53:    */   {
/*  54:101 */     if (!this.parsed) {
/*  55:102 */       parse();
/*  56:    */     }
/*  57:104 */     return this.dispositionType;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getParameter(String name)
/*  61:    */   {
/*  62:115 */     if (!this.parsed) {
/*  63:116 */       parse();
/*  64:    */     }
/*  65:118 */     return (String)this.parameters.get(name.toLowerCase());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Map<String, String> getParameters()
/*  69:    */   {
/*  70:127 */     if (!this.parsed) {
/*  71:128 */       parse();
/*  72:    */     }
/*  73:130 */     return Collections.unmodifiableMap(this.parameters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isDispositionType(String dispositionType)
/*  77:    */   {
/*  78:142 */     if (!this.parsed) {
/*  79:143 */       parse();
/*  80:    */     }
/*  81:145 */     return this.dispositionType.equalsIgnoreCase(dispositionType);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isInline()
/*  85:    */   {
/*  86:156 */     if (!this.parsed) {
/*  87:157 */       parse();
/*  88:    */     }
/*  89:159 */     return this.dispositionType.equals("inline");
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isAttachment()
/*  93:    */   {
/*  94:170 */     if (!this.parsed) {
/*  95:171 */       parse();
/*  96:    */     }
/*  97:173 */     return this.dispositionType.equals("attachment");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getFilename()
/* 101:    */   {
/* 102:183 */     return getParameter("filename");
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Date getCreationDate()
/* 106:    */   {
/* 107:194 */     if (!this.creationDateParsed)
/* 108:    */     {
/* 109:195 */       this.creationDate = parseDate("creation-date");
/* 110:196 */       this.creationDateParsed = true;
/* 111:    */     }
/* 112:199 */     return this.creationDate;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getModificationDate()
/* 116:    */   {
/* 117:210 */     if (!this.modificationDateParsed)
/* 118:    */     {
/* 119:211 */       this.modificationDate = parseDate("modification-date");
/* 120:212 */       this.modificationDateParsed = true;
/* 121:    */     }
/* 122:215 */     return this.modificationDate;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getReadDate()
/* 126:    */   {
/* 127:226 */     if (!this.readDateParsed)
/* 128:    */     {
/* 129:227 */       this.readDate = parseDate("read-date");
/* 130:228 */       this.readDateParsed = true;
/* 131:    */     }
/* 132:231 */     return this.readDate;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public long getSize()
/* 136:    */   {
/* 137:241 */     String value = getParameter("size");
/* 138:242 */     if (value == null) {
/* 139:243 */       return -1L;
/* 140:    */     }
/* 141:    */     try
/* 142:    */     {
/* 143:246 */       long size = Long.parseLong(value);
/* 144:247 */       return size < 0L ? -1L : size;
/* 145:    */     }
/* 146:    */     catch (NumberFormatException e) {}
/* 147:249 */     return -1L;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private Date parseDate(String paramName)
/* 151:    */   {
/* 152:254 */     String value = getParameter(paramName);
/* 153:255 */     if (value == null)
/* 154:    */     {
/* 155:256 */       if (log.isDebugEnabled()) {
/* 156:257 */         log.debug("Parsing " + paramName + " null");
/* 157:    */       }
/* 158:259 */       return null;
/* 159:    */     }
/* 160:    */     try
/* 161:    */     {
/* 162:263 */       return new DateTimeParser(new StringReader(value)).parseAll().getDate();
/* 163:    */     }
/* 164:    */     catch (ParseException e)
/* 165:    */     {
/* 166:266 */       if (log.isDebugEnabled()) {
/* 167:267 */         log.debug("Parsing " + paramName + " '" + value + "': " + e.getMessage());
/* 168:    */       }
/* 169:270 */       return null;
/* 170:    */     }
/* 171:    */     catch (org.apache.james.mime4j.field.datetime.parser.TokenMgrError e)
/* 172:    */     {
/* 173:272 */       if (log.isDebugEnabled()) {
/* 174:273 */         log.debug("Parsing " + paramName + " '" + value + "': " + e.getMessage());
/* 175:    */       }
/* 176:    */     }
/* 177:276 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void parse()
/* 181:    */   {
/* 182:281 */     String body = getBody();
/* 183:    */     
/* 184:283 */     ContentDispositionParser parser = new ContentDispositionParser(new StringReader(body));
/* 185:    */     try
/* 186:    */     {
/* 187:286 */       parser.parseAll();
/* 188:    */     }
/* 189:    */     catch (ParseException e)
/* 190:    */     {
/* 191:288 */       if (log.isDebugEnabled()) {
/* 192:289 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 193:    */       }
/* 194:291 */       this.parseException = e;
/* 195:    */     }
/* 196:    */     catch (org.apache.james.mime4j.field.contentdisposition.parser.TokenMgrError e)
/* 197:    */     {
/* 198:293 */       if (log.isDebugEnabled()) {
/* 199:294 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 200:    */       }
/* 201:296 */       this.parseException = new ParseException(e.getMessage());
/* 202:    */     }
/* 203:299 */     String dispositionType = parser.getDispositionType();
/* 204:301 */     if (dispositionType != null)
/* 205:    */     {
/* 206:302 */       this.dispositionType = dispositionType.toLowerCase(Locale.US);
/* 207:    */       
/* 208:304 */       List<String> paramNames = parser.getParamNames();
/* 209:305 */       List<String> paramValues = parser.getParamValues();
/* 210:307 */       if ((paramNames != null) && (paramValues != null))
/* 211:    */       {
/* 212:308 */         int len = Math.min(paramNames.size(), paramValues.size());
/* 213:309 */         for (int i = 0; i < len; i++)
/* 214:    */         {
/* 215:310 */           String paramName = ((String)paramNames.get(i)).toLowerCase(Locale.US);
/* 216:311 */           String paramValue = (String)paramValues.get(i);
/* 217:312 */           this.parameters.put(paramName, paramValue);
/* 218:    */         }
/* 219:    */       }
/* 220:    */     }
/* 221:317 */     this.parsed = true;
/* 222:    */   }
/* 223:    */   
/* 224:320 */   static final FieldParser PARSER = new FieldParser()
/* 225:    */   {
/* 226:    */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 227:    */     {
/* 228:323 */       return new ContentDispositionField(name, body, raw);
/* 229:    */     }
/* 230:    */   };
/* 231:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.ContentDispositionField
 * JD-Core Version:    0.7.0.1
 */