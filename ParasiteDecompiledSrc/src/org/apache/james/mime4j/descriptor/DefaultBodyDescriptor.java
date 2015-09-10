/*   1:    */ package org.apache.james.mime4j.descriptor;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.james.mime4j.parser.Field;
/*   8:    */ import org.apache.james.mime4j.util.MimeUtil;
/*   9:    */ 
/*  10:    */ public class DefaultBodyDescriptor
/*  11:    */   implements MutableBodyDescriptor
/*  12:    */ {
/*  13:    */   private static final String US_ASCII = "us-ascii";
/*  14:    */   private static final String SUB_TYPE_EMAIL = "rfc822";
/*  15:    */   private static final String MEDIA_TYPE_TEXT = "text";
/*  16:    */   private static final String MEDIA_TYPE_MESSAGE = "message";
/*  17:    */   private static final String EMAIL_MESSAGE_MIME_TYPE = "message/rfc822";
/*  18:    */   private static final String DEFAULT_SUB_TYPE = "plain";
/*  19:    */   private static final String DEFAULT_MEDIA_TYPE = "text";
/*  20:    */   private static final String DEFAULT_MIME_TYPE = "text/plain";
/*  21: 51 */   private static Log log = LogFactory.getLog(DefaultBodyDescriptor.class);
/*  22: 53 */   private String mediaType = "text";
/*  23: 54 */   private String subType = "plain";
/*  24: 55 */   private String mimeType = "text/plain";
/*  25: 56 */   private String boundary = null;
/*  26: 57 */   private String charset = "us-ascii";
/*  27: 58 */   private String transferEncoding = "7bit";
/*  28: 59 */   private Map<String, String> parameters = new HashMap();
/*  29:    */   private boolean contentTypeSet;
/*  30:    */   private boolean contentTransferEncSet;
/*  31: 62 */   private long contentLength = -1L;
/*  32:    */   
/*  33:    */   public DefaultBodyDescriptor()
/*  34:    */   {
/*  35: 68 */     this(null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public DefaultBodyDescriptor(BodyDescriptor parent)
/*  39:    */   {
/*  40: 78 */     if ((parent != null) && (MimeUtil.isSameMimeType("multipart/digest", parent.getMimeType())))
/*  41:    */     {
/*  42: 79 */       this.mimeType = "message/rfc822";
/*  43: 80 */       this.subType = "rfc822";
/*  44: 81 */       this.mediaType = "message";
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 83 */       this.mimeType = "text/plain";
/*  49: 84 */       this.subType = "plain";
/*  50: 85 */       this.mediaType = "text";
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addField(Field field)
/*  55:    */   {
/*  56: 96 */     String name = field.getName();
/*  57: 97 */     String value = field.getBody();
/*  58:    */     
/*  59: 99 */     name = name.trim().toLowerCase();
/*  60:101 */     if ((name.equals("content-transfer-encoding")) && (!this.contentTransferEncSet))
/*  61:    */     {
/*  62:102 */       this.contentTransferEncSet = true;
/*  63:    */       
/*  64:104 */       value = value.trim().toLowerCase();
/*  65:105 */       if (value.length() > 0) {
/*  66:106 */         this.transferEncoding = value;
/*  67:    */       }
/*  68:    */     }
/*  69:109 */     else if ((name.equals("content-length")) && (this.contentLength == -1L))
/*  70:    */     {
/*  71:    */       try
/*  72:    */       {
/*  73:111 */         this.contentLength = Long.parseLong(value.trim());
/*  74:    */       }
/*  75:    */       catch (NumberFormatException e)
/*  76:    */       {
/*  77:113 */         log.error("Invalid content-length: " + value);
/*  78:    */       }
/*  79:    */     }
/*  80:115 */     else if ((name.equals("content-type")) && (!this.contentTypeSet))
/*  81:    */     {
/*  82:116 */       parseContentType(value);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void parseContentType(String value)
/*  87:    */   {
/*  88:121 */     this.contentTypeSet = true;
/*  89:    */     
/*  90:123 */     Map<String, String> params = MimeUtil.getHeaderParams(value);
/*  91:    */     
/*  92:125 */     String main = (String)params.get("");
/*  93:126 */     String type = null;
/*  94:127 */     String subtype = null;
/*  95:128 */     if (main != null)
/*  96:    */     {
/*  97:129 */       main = main.toLowerCase().trim();
/*  98:130 */       int index = main.indexOf('/');
/*  99:131 */       boolean valid = false;
/* 100:132 */       if (index != -1)
/* 101:    */       {
/* 102:133 */         type = main.substring(0, index).trim();
/* 103:134 */         subtype = main.substring(index + 1).trim();
/* 104:135 */         if ((type.length() > 0) && (subtype.length() > 0))
/* 105:    */         {
/* 106:136 */           main = type + "/" + subtype;
/* 107:137 */           valid = true;
/* 108:    */         }
/* 109:    */       }
/* 110:141 */       if (!valid)
/* 111:    */       {
/* 112:142 */         main = null;
/* 113:143 */         type = null;
/* 114:144 */         subtype = null;
/* 115:    */       }
/* 116:    */     }
/* 117:147 */     String b = (String)params.get("boundary");
/* 118:149 */     if ((main != null) && (((main.startsWith("multipart/")) && (b != null)) || (!main.startsWith("multipart/"))))
/* 119:    */     {
/* 120:153 */       this.mimeType = main;
/* 121:154 */       this.subType = subtype;
/* 122:155 */       this.mediaType = type;
/* 123:    */     }
/* 124:158 */     if (MimeUtil.isMultipart(this.mimeType)) {
/* 125:159 */       this.boundary = b;
/* 126:    */     }
/* 127:162 */     String c = (String)params.get("charset");
/* 128:163 */     this.charset = null;
/* 129:164 */     if (c != null)
/* 130:    */     {
/* 131:165 */       c = c.trim();
/* 132:166 */       if (c.length() > 0) {
/* 133:167 */         this.charset = c.toLowerCase();
/* 134:    */       }
/* 135:    */     }
/* 136:170 */     if ((this.charset == null) && ("text".equals(this.mediaType))) {
/* 137:171 */       this.charset = "us-ascii";
/* 138:    */     }
/* 139:177 */     this.parameters.putAll(params);
/* 140:178 */     this.parameters.remove("");
/* 141:179 */     this.parameters.remove("boundary");
/* 142:180 */     this.parameters.remove("charset");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getMimeType()
/* 146:    */   {
/* 147:189 */     return this.mimeType;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getBoundary()
/* 151:    */   {
/* 152:198 */     return this.boundary;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getCharset()
/* 156:    */   {
/* 157:207 */     return this.charset;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Map<String, String> getContentTypeParameters()
/* 161:    */   {
/* 162:216 */     return this.parameters;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getTransferEncoding()
/* 166:    */   {
/* 167:225 */     return this.transferEncoding;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String toString()
/* 171:    */   {
/* 172:230 */     return this.mimeType;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public long getContentLength()
/* 176:    */   {
/* 177:234 */     return this.contentLength;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getMediaType()
/* 181:    */   {
/* 182:238 */     return this.mediaType;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getSubType()
/* 186:    */   {
/* 187:242 */     return this.subType;
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.descriptor.DefaultBodyDescriptor
 * JD-Core Version:    0.7.0.1
 */