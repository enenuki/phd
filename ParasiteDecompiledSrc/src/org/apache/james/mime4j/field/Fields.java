/*   1:    */ package org.apache.james.mime4j.field;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.TimeZone;
/*  10:    */ import java.util.regex.Matcher;
/*  11:    */ import java.util.regex.Pattern;
/*  12:    */ import org.apache.james.mime4j.codec.EncoderUtil;
/*  13:    */ import org.apache.james.mime4j.codec.EncoderUtil.Usage;
/*  14:    */ import org.apache.james.mime4j.field.address.Address;
/*  15:    */ import org.apache.james.mime4j.field.address.Mailbox;
/*  16:    */ import org.apache.james.mime4j.parser.Field;
/*  17:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  18:    */ import org.apache.james.mime4j.util.ContentUtil;
/*  19:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  20:    */ 
/*  21:    */ public class Fields
/*  22:    */ {
/*  23: 43 */   private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("[\\x21-\\x39\\x3b-\\x7e]+");
/*  24:    */   
/*  25:    */   public static ContentTypeField contentType(String contentType)
/*  26:    */   {
/*  27: 60 */     return (ContentTypeField)parse(ContentTypeField.PARSER, "Content-Type", contentType);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static ContentTypeField contentType(String mimeType, Map<String, String> parameters)
/*  31:    */   {
/*  32: 78 */     if (!isValidMimeType(mimeType)) {
/*  33: 79 */       throw new IllegalArgumentException();
/*  34:    */     }
/*  35: 81 */     if ((parameters == null) || (parameters.isEmpty())) {
/*  36: 82 */       return (ContentTypeField)parse(ContentTypeField.PARSER, "Content-Type", mimeType);
/*  37:    */     }
/*  38: 85 */     StringBuilder sb = new StringBuilder(mimeType);
/*  39: 86 */     for (Map.Entry<String, String> entry : parameters.entrySet())
/*  40:    */     {
/*  41: 87 */       sb.append("; ");
/*  42: 88 */       sb.append(EncoderUtil.encodeHeaderParameter((String)entry.getKey(), (String)entry.getValue()));
/*  43:    */     }
/*  44: 91 */     String contentType = sb.toString();
/*  45: 92 */     return contentType(contentType);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static ContentTransferEncodingField contentTransferEncoding(String contentTransferEncoding)
/*  49:    */   {
/*  50:107 */     return (ContentTransferEncodingField)parse(ContentTransferEncodingField.PARSER, "Content-Transfer-Encoding", contentTransferEncoding);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static ContentDispositionField contentDisposition(String contentDisposition)
/*  54:    */   {
/*  55:123 */     return (ContentDispositionField)parse(ContentDispositionField.PARSER, "Content-Disposition", contentDisposition);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static ContentDispositionField contentDisposition(String dispositionType, Map<String, String> parameters)
/*  59:    */   {
/*  60:141 */     if (!isValidDispositionType(dispositionType)) {
/*  61:142 */       throw new IllegalArgumentException();
/*  62:    */     }
/*  63:144 */     if ((parameters == null) || (parameters.isEmpty())) {
/*  64:145 */       return (ContentDispositionField)parse(ContentDispositionField.PARSER, "Content-Disposition", dispositionType);
/*  65:    */     }
/*  66:148 */     StringBuilder sb = new StringBuilder(dispositionType);
/*  67:149 */     for (Map.Entry<String, String> entry : parameters.entrySet())
/*  68:    */     {
/*  69:150 */       sb.append("; ");
/*  70:151 */       sb.append(EncoderUtil.encodeHeaderParameter((String)entry.getKey(), (String)entry.getValue()));
/*  71:    */     }
/*  72:154 */     String contentDisposition = sb.toString();
/*  73:155 */     return contentDisposition(contentDisposition);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static ContentDispositionField contentDisposition(String dispositionType, String filename)
/*  77:    */   {
/*  78:173 */     return contentDisposition(dispositionType, filename, -1L, null, null, null);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static ContentDispositionField contentDisposition(String dispositionType, String filename, long size)
/*  82:    */   {
/*  83:193 */     return contentDisposition(dispositionType, filename, size, null, null, null);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static ContentDispositionField contentDisposition(String dispositionType, String filename, long size, Date creationDate, Date modificationDate, Date readDate)
/*  87:    */   {
/*  88:223 */     Map<String, String> parameters = new HashMap();
/*  89:224 */     if (filename != null) {
/*  90:225 */       parameters.put("filename", filename);
/*  91:    */     }
/*  92:227 */     if (size >= 0L) {
/*  93:228 */       parameters.put("size", Long.toString(size));
/*  94:    */     }
/*  95:231 */     if (creationDate != null) {
/*  96:232 */       parameters.put("creation-date", MimeUtil.formatDate(creationDate, null));
/*  97:    */     }
/*  98:235 */     if (modificationDate != null) {
/*  99:236 */       parameters.put("modification-date", MimeUtil.formatDate(modificationDate, null));
/* 100:    */     }
/* 101:239 */     if (readDate != null) {
/* 102:240 */       parameters.put("read-date", MimeUtil.formatDate(readDate, null));
/* 103:    */     }
/* 104:243 */     return contentDisposition(dispositionType, parameters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static DateTimeField date(Date date)
/* 108:    */   {
/* 109:255 */     return date0("Date", date, null);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static DateTimeField date(String fieldName, Date date)
/* 113:    */   {
/* 114:270 */     checkValidFieldName(fieldName);
/* 115:271 */     return date0(fieldName, date, null);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static DateTimeField date(String fieldName, Date date, TimeZone zone)
/* 119:    */   {
/* 120:288 */     checkValidFieldName(fieldName);
/* 121:289 */     return date0(fieldName, date, zone);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static Field messageId(String hostname)
/* 125:    */   {
/* 126:301 */     String fieldValue = MimeUtil.createUniqueMessageId(hostname);
/* 127:302 */     return parse(UnstructuredField.PARSER, "Message-ID", fieldValue);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static UnstructuredField subject(String subject)
/* 131:    */   {
/* 132:314 */     int usedCharacters = "Subject".length() + 2;
/* 133:315 */     String fieldValue = EncoderUtil.encodeIfNecessary(subject, EncoderUtil.Usage.TEXT_TOKEN, usedCharacters);
/* 134:    */     
/* 135:    */ 
/* 136:318 */     return (UnstructuredField)parse(UnstructuredField.PARSER, "Subject", fieldValue);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static MailboxField sender(Mailbox mailbox)
/* 140:    */   {
/* 141:329 */     return mailbox0("Sender", mailbox);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static MailboxListField from(Mailbox mailbox)
/* 145:    */   {
/* 146:340 */     return mailboxList0("From", Collections.singleton(mailbox));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static MailboxListField from(Mailbox... mailboxes)
/* 150:    */   {
/* 151:351 */     return mailboxList0("From", Arrays.asList(mailboxes));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static MailboxListField from(Iterable<Mailbox> mailboxes)
/* 155:    */   {
/* 156:362 */     return mailboxList0("From", mailboxes);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static AddressListField to(Address address)
/* 160:    */   {
/* 161:373 */     return addressList0("To", Collections.singleton(address));
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static AddressListField to(Address... addresses)
/* 165:    */   {
/* 166:384 */     return addressList0("To", Arrays.asList(addresses));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static AddressListField to(Iterable<Address> addresses)
/* 170:    */   {
/* 171:395 */     return addressList0("To", addresses);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static AddressListField cc(Address address)
/* 175:    */   {
/* 176:406 */     return addressList0("Cc", Collections.singleton(address));
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static AddressListField cc(Address... addresses)
/* 180:    */   {
/* 181:417 */     return addressList0("Cc", Arrays.asList(addresses));
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static AddressListField cc(Iterable<Address> addresses)
/* 185:    */   {
/* 186:428 */     return addressList0("Cc", addresses);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static AddressListField bcc(Address address)
/* 190:    */   {
/* 191:439 */     return addressList0("Bcc", Collections.singleton(address));
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static AddressListField bcc(Address... addresses)
/* 195:    */   {
/* 196:450 */     return addressList0("Bcc", Arrays.asList(addresses));
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static AddressListField bcc(Iterable<Address> addresses)
/* 200:    */   {
/* 201:461 */     return addressList0("Bcc", addresses);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static AddressListField replyTo(Address address)
/* 205:    */   {
/* 206:473 */     return addressList0("Reply-To", Collections.singleton(address));
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static AddressListField replyTo(Address... addresses)
/* 210:    */   {
/* 211:485 */     return addressList0("Reply-To", Arrays.asList(addresses));
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static AddressListField replyTo(Iterable<Address> addresses)
/* 215:    */   {
/* 216:497 */     return addressList0("Reply-To", addresses);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static MailboxField mailbox(String fieldName, Mailbox mailbox)
/* 220:    */   {
/* 221:513 */     checkValidFieldName(fieldName);
/* 222:514 */     return mailbox0(fieldName, mailbox);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static MailboxListField mailboxList(String fieldName, Iterable<Mailbox> mailboxes)
/* 226:    */   {
/* 227:531 */     checkValidFieldName(fieldName);
/* 228:532 */     return mailboxList0(fieldName, mailboxes);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public static AddressListField addressList(String fieldName, Iterable<Address> addresses)
/* 232:    */   {
/* 233:553 */     checkValidFieldName(fieldName);
/* 234:554 */     return addressList0(fieldName, addresses);
/* 235:    */   }
/* 236:    */   
/* 237:    */   private static DateTimeField date0(String fieldName, Date date, TimeZone zone)
/* 238:    */   {
/* 239:559 */     String formattedDate = MimeUtil.formatDate(date, zone);
/* 240:560 */     return (DateTimeField)parse(DateTimeField.PARSER, fieldName, formattedDate);
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static MailboxField mailbox0(String fieldName, Mailbox mailbox)
/* 244:    */   {
/* 245:564 */     String fieldValue = encodeAddresses(Collections.singleton(mailbox));
/* 246:565 */     return (MailboxField)parse(MailboxField.PARSER, fieldName, fieldValue);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private static MailboxListField mailboxList0(String fieldName, Iterable<Mailbox> mailboxes)
/* 250:    */   {
/* 251:570 */     String fieldValue = encodeAddresses(mailboxes);
/* 252:571 */     return (MailboxListField)parse(MailboxListField.PARSER, fieldName, fieldValue);
/* 253:    */   }
/* 254:    */   
/* 255:    */   private static AddressListField addressList0(String fieldName, Iterable<Address> addresses)
/* 256:    */   {
/* 257:576 */     String fieldValue = encodeAddresses(addresses);
/* 258:577 */     return (AddressListField)parse(AddressListField.PARSER, fieldName, fieldValue);
/* 259:    */   }
/* 260:    */   
/* 261:    */   private static void checkValidFieldName(String fieldName)
/* 262:    */   {
/* 263:581 */     if (!FIELD_NAME_PATTERN.matcher(fieldName).matches()) {
/* 264:582 */       throw new IllegalArgumentException("Invalid field name");
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   private static boolean isValidMimeType(String mimeType)
/* 269:    */   {
/* 270:586 */     if (mimeType == null) {
/* 271:587 */       return false;
/* 272:    */     }
/* 273:589 */     int idx = mimeType.indexOf('/');
/* 274:590 */     if (idx == -1) {
/* 275:591 */       return false;
/* 276:    */     }
/* 277:593 */     String type = mimeType.substring(0, idx);
/* 278:594 */     String subType = mimeType.substring(idx + 1);
/* 279:595 */     return (EncoderUtil.isToken(type)) && (EncoderUtil.isToken(subType));
/* 280:    */   }
/* 281:    */   
/* 282:    */   private static boolean isValidDispositionType(String dispositionType)
/* 283:    */   {
/* 284:599 */     if (dispositionType == null) {
/* 285:600 */       return false;
/* 286:    */     }
/* 287:602 */     return EncoderUtil.isToken(dispositionType);
/* 288:    */   }
/* 289:    */   
/* 290:    */   private static <F extends Field> F parse(FieldParser parser, String fieldName, String fieldBody)
/* 291:    */   {
/* 292:607 */     String rawStr = MimeUtil.fold(fieldName + ": " + fieldBody, 0);
/* 293:608 */     ByteSequence raw = ContentUtil.encode(rawStr);
/* 294:    */     
/* 295:610 */     Field field = parser.parse(fieldName, fieldBody, raw);
/* 296:    */     
/* 297:    */ 
/* 298:613 */     F f = field;
/* 299:614 */     return f;
/* 300:    */   }
/* 301:    */   
/* 302:    */   private static String encodeAddresses(Iterable<? extends Address> addresses)
/* 303:    */   {
/* 304:618 */     StringBuilder sb = new StringBuilder();
/* 305:620 */     for (Address address : addresses)
/* 306:    */     {
/* 307:621 */       if (sb.length() > 0) {
/* 308:622 */         sb.append(", ");
/* 309:    */       }
/* 310:624 */       sb.append(address.getEncodedString());
/* 311:    */     }
/* 312:627 */     return sb.toString();
/* 313:    */   }
/* 314:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.Fields
 * JD-Core Version:    0.7.0.1
 */