/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.james.mime4j.field.ContentDispositionField;
/*   8:    */ import org.apache.james.mime4j.field.ContentTransferEncodingField;
/*   9:    */ import org.apache.james.mime4j.field.ContentTypeField;
/*  10:    */ import org.apache.james.mime4j.field.Fields;
/*  11:    */ import org.apache.james.mime4j.parser.Field;
/*  12:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  13:    */ 
/*  14:    */ public abstract class Entity
/*  15:    */   implements Disposable
/*  16:    */ {
/*  17: 39 */   private Header header = null;
/*  18: 40 */   private Body body = null;
/*  19: 41 */   private Entity parent = null;
/*  20:    */   
/*  21:    */   protected Entity() {}
/*  22:    */   
/*  23:    */   protected Entity(Entity other)
/*  24:    */   {
/*  25: 68 */     if (other.header != null) {
/*  26: 69 */       this.header = new Header(other.header);
/*  27:    */     }
/*  28: 72 */     if (other.body != null)
/*  29:    */     {
/*  30: 73 */       Body bodyCopy = BodyCopier.copy(other.body);
/*  31: 74 */       setBody(bodyCopy);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Entity getParent()
/*  36:    */   {
/*  37: 85 */     return this.parent;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setParent(Entity parent)
/*  41:    */   {
/*  42: 95 */     this.parent = parent;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Header getHeader()
/*  46:    */   {
/*  47:104 */     return this.header;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setHeader(Header header)
/*  51:    */   {
/*  52:113 */     this.header = header;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Body getBody()
/*  56:    */   {
/*  57:122 */     return this.body;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setBody(Body body)
/*  61:    */   {
/*  62:132 */     if (this.body != null) {
/*  63:133 */       throw new IllegalStateException("body already set");
/*  64:    */     }
/*  65:135 */     this.body = body;
/*  66:136 */     body.setParent(this);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Body removeBody()
/*  70:    */   {
/*  71:147 */     if (this.body == null) {
/*  72:148 */       return null;
/*  73:    */     }
/*  74:150 */     Body body = this.body;
/*  75:151 */     this.body = null;
/*  76:152 */     body.setParent(null);
/*  77:    */     
/*  78:154 */     return body;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setMessage(Message message)
/*  82:    */   {
/*  83:166 */     setBody(message, "message/rfc822", null);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setMultipart(Multipart multipart)
/*  87:    */   {
/*  88:179 */     String mimeType = "multipart/" + multipart.getSubType();
/*  89:180 */     Map<String, String> parameters = Collections.singletonMap("boundary", MimeUtil.createUniqueBoundary());
/*  90:    */     
/*  91:    */ 
/*  92:183 */     setBody(multipart, mimeType, parameters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setMultipart(Multipart multipart, Map<String, String> parameters)
/*  96:    */   {
/*  97:198 */     String mimeType = "multipart/" + multipart.getSubType();
/*  98:199 */     if (!parameters.containsKey("boundary"))
/*  99:    */     {
/* 100:200 */       parameters = new HashMap(parameters);
/* 101:201 */       parameters.put("boundary", MimeUtil.createUniqueBoundary());
/* 102:    */     }
/* 103:204 */     setBody(multipart, mimeType, parameters);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setText(TextBody textBody)
/* 107:    */   {
/* 108:217 */     setText(textBody, "plain");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setText(TextBody textBody, String subtype)
/* 112:    */   {
/* 113:234 */     String mimeType = "text/" + subtype;
/* 114:    */     
/* 115:236 */     Map<String, String> parameters = null;
/* 116:237 */     String mimeCharset = textBody.getMimeCharset();
/* 117:238 */     if ((mimeCharset != null) && (!mimeCharset.equalsIgnoreCase("us-ascii"))) {
/* 118:239 */       parameters = Collections.singletonMap("charset", mimeCharset);
/* 119:    */     }
/* 120:242 */     setBody(textBody, mimeType, parameters);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setBody(Body body, String mimeType)
/* 124:    */   {
/* 125:257 */     setBody(body, mimeType, null);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setBody(Body body, String mimeType, Map<String, String> parameters)
/* 129:    */   {
/* 130:275 */     setBody(body);
/* 131:    */     
/* 132:277 */     Header header = obtainHeader();
/* 133:278 */     header.setField(Fields.contentType(mimeType, parameters));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getMimeType()
/* 137:    */   {
/* 138:289 */     ContentTypeField child = (ContentTypeField)getHeader().getField("Content-Type");
/* 139:    */     
/* 140:291 */     ContentTypeField parent = getParent() != null ? (ContentTypeField)getParent().getHeader().getField("Content-Type") : null;
/* 141:    */     
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:296 */     return ContentTypeField.getMimeType(child, parent);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getCharset()
/* 149:    */   {
/* 150:305 */     return ContentTypeField.getCharset((ContentTypeField)getHeader().getField("Content-Type"));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getContentTransferEncoding()
/* 154:    */   {
/* 155:315 */     ContentTransferEncodingField f = (ContentTransferEncodingField)getHeader().getField("Content-Transfer-Encoding");
/* 156:    */     
/* 157:    */ 
/* 158:318 */     return ContentTransferEncodingField.getEncoding(f);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setContentTransferEncoding(String contentTransferEncoding)
/* 162:    */   {
/* 163:329 */     Header header = obtainHeader();
/* 164:330 */     header.setField(Fields.contentTransferEncoding(contentTransferEncoding));
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getDispositionType()
/* 168:    */   {
/* 169:341 */     ContentDispositionField field = (ContentDispositionField)obtainField("Content-Disposition");
/* 170:342 */     if (field == null) {
/* 171:343 */       return null;
/* 172:    */     }
/* 173:345 */     return field.getDispositionType();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setContentDisposition(String dispositionType)
/* 177:    */   {
/* 178:358 */     Header header = obtainHeader();
/* 179:359 */     header.setField(Fields.contentDisposition(dispositionType, null, -1L, null, null, null));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setContentDisposition(String dispositionType, String filename)
/* 183:    */   {
/* 184:376 */     Header header = obtainHeader();
/* 185:377 */     header.setField(Fields.contentDisposition(dispositionType, filename, -1L, null, null, null));
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setContentDisposition(String dispositionType, String filename, long size)
/* 189:    */   {
/* 190:398 */     Header header = obtainHeader();
/* 191:399 */     header.setField(Fields.contentDisposition(dispositionType, filename, size, null, null, null));
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setContentDisposition(String dispositionType, String filename, long size, Date creationDate, Date modificationDate, Date readDate)
/* 195:    */   {
/* 196:428 */     Header header = obtainHeader();
/* 197:429 */     header.setField(Fields.contentDisposition(dispositionType, filename, size, creationDate, modificationDate, readDate));
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getFilename()
/* 201:    */   {
/* 202:441 */     ContentDispositionField field = (ContentDispositionField)obtainField("Content-Disposition");
/* 203:442 */     if (field == null) {
/* 204:443 */       return null;
/* 205:    */     }
/* 206:445 */     return field.getFilename();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setFilename(String filename)
/* 210:    */   {
/* 211:459 */     Header header = obtainHeader();
/* 212:460 */     ContentDispositionField field = (ContentDispositionField)header.getField("Content-Disposition");
/* 213:462 */     if (field == null)
/* 214:    */     {
/* 215:463 */       if (filename != null) {
/* 216:464 */         header.setField(Fields.contentDisposition("attachment", filename, -1L, null, null, null));
/* 217:    */       }
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:469 */       String dispositionType = field.getDispositionType();
/* 222:470 */       Map<String, String> parameters = new HashMap(field.getParameters());
/* 223:472 */       if (filename == null) {
/* 224:473 */         parameters.remove("filename");
/* 225:    */       } else {
/* 226:475 */         parameters.put("filename", filename);
/* 227:    */       }
/* 228:478 */       header.setField(Fields.contentDisposition(dispositionType, parameters));
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean isMimeType(String type)
/* 233:    */   {
/* 234:491 */     return getMimeType().equalsIgnoreCase(type);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean isMultipart()
/* 238:    */   {
/* 239:503 */     ContentTypeField f = (ContentTypeField)getHeader().getField("Content-Type");
/* 240:    */     
/* 241:505 */     return (f != null) && (f.getBoundary() != null) && (getMimeType().startsWith("multipart/"));
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void dispose()
/* 245:    */   {
/* 246:519 */     if (this.body != null) {
/* 247:520 */       this.body.dispose();
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   Header obtainHeader()
/* 252:    */   {
/* 253:531 */     if (this.header == null) {
/* 254:532 */       this.header = new Header();
/* 255:    */     }
/* 256:534 */     return this.header;
/* 257:    */   }
/* 258:    */   
/* 259:    */   <F extends Field> F obtainField(String fieldName)
/* 260:    */   {
/* 261:548 */     Header header = getHeader();
/* 262:549 */     if (header == null) {
/* 263:550 */       return null;
/* 264:    */     }
/* 265:553 */     F field = header.getField(fieldName);
/* 266:554 */     return field;
/* 267:    */   }
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.Entity
 * JD-Core Version:    0.7.0.1
 */