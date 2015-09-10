/*   1:    */ package org.apache.james.mime4j.field;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.james.mime4j.field.contenttype.parser.ContentTypeParser;
/*  11:    */ import org.apache.james.mime4j.field.contenttype.parser.ParseException;
/*  12:    */ import org.apache.james.mime4j.field.contenttype.parser.TokenMgrError;
/*  13:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  14:    */ 
/*  15:    */ public class ContentTypeField
/*  16:    */   extends AbstractField
/*  17:    */ {
/*  18: 39 */   private static Log log = LogFactory.getLog(ContentTypeField.class);
/*  19:    */   public static final String TYPE_MULTIPART_PREFIX = "multipart/";
/*  20:    */   public static final String TYPE_MULTIPART_DIGEST = "multipart/digest";
/*  21:    */   public static final String TYPE_TEXT_PLAIN = "text/plain";
/*  22:    */   public static final String TYPE_MESSAGE_RFC822 = "message/rfc822";
/*  23:    */   public static final String PARAM_BOUNDARY = "boundary";
/*  24:    */   public static final String PARAM_CHARSET = "charset";
/*  25: 59 */   private boolean parsed = false;
/*  26: 61 */   private String mimeType = "";
/*  27: 62 */   private Map<String, String> parameters = new HashMap();
/*  28:    */   private ParseException parseException;
/*  29:    */   
/*  30:    */   ContentTypeField(String name, String body, ByteSequence raw)
/*  31:    */   {
/*  32: 66 */     super(name, body, raw);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ParseException getParseException()
/*  36:    */   {
/*  37: 75 */     if (!this.parsed) {
/*  38: 76 */       parse();
/*  39:    */     }
/*  40: 78 */     return this.parseException;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getMimeType()
/*  44:    */   {
/*  45: 87 */     if (!this.parsed) {
/*  46: 88 */       parse();
/*  47:    */     }
/*  48: 90 */     return this.mimeType;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getParameter(String name)
/*  52:    */   {
/*  53:101 */     if (!this.parsed) {
/*  54:102 */       parse();
/*  55:    */     }
/*  56:104 */     return (String)this.parameters.get(name.toLowerCase());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Map<String, String> getParameters()
/*  60:    */   {
/*  61:113 */     if (!this.parsed) {
/*  62:114 */       parse();
/*  63:    */     }
/*  64:116 */     return Collections.unmodifiableMap(this.parameters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isMimeType(String mimeType)
/*  68:    */   {
/*  69:128 */     if (!this.parsed) {
/*  70:129 */       parse();
/*  71:    */     }
/*  72:131 */     return this.mimeType.equalsIgnoreCase(mimeType);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isMultipart()
/*  76:    */   {
/*  77:142 */     if (!this.parsed) {
/*  78:143 */       parse();
/*  79:    */     }
/*  80:145 */     return this.mimeType.startsWith("multipart/");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getBoundary()
/*  84:    */   {
/*  85:155 */     return getParameter("boundary");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCharset()
/*  89:    */   {
/*  90:165 */     return getParameter("charset");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static String getMimeType(ContentTypeField child, ContentTypeField parent)
/*  94:    */   {
/*  95:182 */     if ((child == null) || (child.getMimeType().length() == 0) || ((child.isMultipart()) && (child.getBoundary() == null)))
/*  96:    */     {
/*  97:185 */       if ((parent != null) && (parent.isMimeType("multipart/digest"))) {
/*  98:186 */         return "message/rfc822";
/*  99:    */       }
/* 100:188 */       return "text/plain";
/* 101:    */     }
/* 102:192 */     return child.getMimeType();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static String getCharset(ContentTypeField f)
/* 106:    */   {
/* 107:203 */     if (f != null)
/* 108:    */     {
/* 109:204 */       String charset = f.getCharset();
/* 110:205 */       if ((charset != null) && (charset.length() > 0)) {
/* 111:206 */         return charset;
/* 112:    */       }
/* 113:    */     }
/* 114:209 */     return "us-ascii";
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void parse()
/* 118:    */   {
/* 119:213 */     String body = getBody();
/* 120:    */     
/* 121:215 */     ContentTypeParser parser = new ContentTypeParser(new StringReader(body));
/* 122:    */     try
/* 123:    */     {
/* 124:217 */       parser.parseAll();
/* 125:    */     }
/* 126:    */     catch (ParseException e)
/* 127:    */     {
/* 128:219 */       if (log.isDebugEnabled()) {
/* 129:220 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 130:    */       }
/* 131:222 */       this.parseException = e;
/* 132:    */     }
/* 133:    */     catch (TokenMgrError e)
/* 134:    */     {
/* 135:224 */       if (log.isDebugEnabled()) {
/* 136:225 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 137:    */       }
/* 138:227 */       this.parseException = new ParseException(e.getMessage());
/* 139:    */     }
/* 140:230 */     String type = parser.getType();
/* 141:231 */     String subType = parser.getSubType();
/* 142:233 */     if ((type != null) && (subType != null))
/* 143:    */     {
/* 144:234 */       this.mimeType = (type + "/" + subType).toLowerCase();
/* 145:    */       
/* 146:236 */       List<String> paramNames = parser.getParamNames();
/* 147:237 */       List<String> paramValues = parser.getParamValues();
/* 148:239 */       if ((paramNames != null) && (paramValues != null))
/* 149:    */       {
/* 150:240 */         int len = Math.min(paramNames.size(), paramValues.size());
/* 151:241 */         for (int i = 0; i < len; i++)
/* 152:    */         {
/* 153:242 */           String paramName = ((String)paramNames.get(i)).toLowerCase();
/* 154:243 */           String paramValue = (String)paramValues.get(i);
/* 155:244 */           this.parameters.put(paramName, paramValue);
/* 156:    */         }
/* 157:    */       }
/* 158:    */     }
/* 159:249 */     this.parsed = true;
/* 160:    */   }
/* 161:    */   
/* 162:252 */   static final FieldParser PARSER = new FieldParser()
/* 163:    */   {
/* 164:    */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 165:    */     {
/* 166:255 */       return new ContentTypeField(name, body, raw);
/* 167:    */     }
/* 168:    */   };
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.ContentTypeField
 * JD-Core Version:    0.7.0.1
 */