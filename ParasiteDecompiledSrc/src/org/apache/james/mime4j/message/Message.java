/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.TimeZone;
/*  11:    */ import org.apache.james.mime4j.MimeException;
/*  12:    */ import org.apache.james.mime4j.MimeIOException;
/*  13:    */ import org.apache.james.mime4j.field.AddressListField;
/*  14:    */ import org.apache.james.mime4j.field.DateTimeField;
/*  15:    */ import org.apache.james.mime4j.field.Fields;
/*  16:    */ import org.apache.james.mime4j.field.MailboxField;
/*  17:    */ import org.apache.james.mime4j.field.MailboxListField;
/*  18:    */ import org.apache.james.mime4j.field.UnstructuredField;
/*  19:    */ import org.apache.james.mime4j.field.address.Address;
/*  20:    */ import org.apache.james.mime4j.field.address.AddressList;
/*  21:    */ import org.apache.james.mime4j.field.address.Mailbox;
/*  22:    */ import org.apache.james.mime4j.field.address.MailboxList;
/*  23:    */ import org.apache.james.mime4j.parser.Field;
/*  24:    */ import org.apache.james.mime4j.parser.MimeEntityConfig;
/*  25:    */ import org.apache.james.mime4j.parser.MimeStreamParser;
/*  26:    */ import org.apache.james.mime4j.storage.DefaultStorageProvider;
/*  27:    */ import org.apache.james.mime4j.storage.StorageProvider;
/*  28:    */ 
/*  29:    */ public class Message
/*  30:    */   extends Entity
/*  31:    */   implements Body
/*  32:    */ {
/*  33:    */   public Message() {}
/*  34:    */   
/*  35:    */   public Message(Message other)
/*  36:    */   {
/*  37: 85 */     super(other);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Message(InputStream is)
/*  41:    */     throws IOException, MimeIOException
/*  42:    */   {
/*  43:100 */     this(is, null, DefaultStorageProvider.getInstance());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Message(InputStream is, MimeEntityConfig config)
/*  47:    */     throws IOException, MimeIOException
/*  48:    */   {
/*  49:116 */     this(is, config, DefaultStorageProvider.getInstance());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Message(InputStream is, MimeEntityConfig config, StorageProvider storageProvider)
/*  53:    */     throws IOException, MimeIOException
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57:139 */       MimeStreamParser parser = new MimeStreamParser(config);
/*  58:140 */       parser.setContentHandler(new MessageBuilder(this, storageProvider));
/*  59:141 */       parser.parse(is);
/*  60:    */     }
/*  61:    */     catch (MimeException e)
/*  62:    */     {
/*  63:143 */       throw new MimeIOException(e);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void writeTo(OutputStream out)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:158 */     MessageWriter.DEFAULT.writeEntity(this, out);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getMessageId()
/*  74:    */   {
/*  75:168 */     Field field = obtainField("Message-ID");
/*  76:169 */     if (field == null) {
/*  77:170 */       return null;
/*  78:    */     }
/*  79:172 */     return field.getBody();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void createMessageId(String hostname)
/*  83:    */   {
/*  84:185 */     Header header = obtainHeader();
/*  85:    */     
/*  86:187 */     header.setField(Fields.messageId(hostname));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getSubject()
/*  90:    */   {
/*  91:197 */     UnstructuredField field = (UnstructuredField)obtainField("Subject");
/*  92:198 */     if (field == null) {
/*  93:199 */       return null;
/*  94:    */     }
/*  95:201 */     return field.getValue();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setSubject(String subject)
/*  99:    */   {
/* 100:215 */     Header header = obtainHeader();
/* 101:217 */     if (subject == null) {
/* 102:218 */       header.removeFields("Subject");
/* 103:    */     } else {
/* 104:220 */       header.setField(Fields.subject(subject));
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getDate()
/* 109:    */   {
/* 110:231 */     DateTimeField dateField = (DateTimeField)obtainField("Date");
/* 111:232 */     if (dateField == null) {
/* 112:233 */       return null;
/* 113:    */     }
/* 114:235 */     return dateField.getDate();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDate(Date date)
/* 118:    */   {
/* 119:248 */     setDate(date, null);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDate(Date date, TimeZone zone)
/* 123:    */   {
/* 124:263 */     Header header = obtainHeader();
/* 125:265 */     if (date == null) {
/* 126:266 */       header.removeFields("Date");
/* 127:    */     } else {
/* 128:268 */       header.setField(Fields.date("Date", date, zone));
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Mailbox getSender()
/* 133:    */   {
/* 134:280 */     return getMailbox("Sender");
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setSender(Mailbox sender)
/* 138:    */   {
/* 139:292 */     setMailbox("Sender", sender);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public MailboxList getFrom()
/* 143:    */   {
/* 144:303 */     return getMailboxList("From");
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setFrom(Mailbox from)
/* 148:    */   {
/* 149:315 */     setMailboxList("From", from);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setFrom(Mailbox... from)
/* 153:    */   {
/* 154:327 */     setMailboxList("From", from);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setFrom(Collection<Mailbox> from)
/* 158:    */   {
/* 159:339 */     setMailboxList("From", from);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public AddressList getTo()
/* 163:    */   {
/* 164:350 */     return getAddressList("To");
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setTo(Address to)
/* 168:    */   {
/* 169:362 */     setAddressList("To", to);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setTo(Address... to)
/* 173:    */   {
/* 174:374 */     setAddressList("To", to);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setTo(Collection<Address> to)
/* 178:    */   {
/* 179:386 */     setAddressList("To", to);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public AddressList getCc()
/* 183:    */   {
/* 184:397 */     return getAddressList("Cc");
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setCc(Address cc)
/* 188:    */   {
/* 189:409 */     setAddressList("Cc", cc);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setCc(Address... cc)
/* 193:    */   {
/* 194:421 */     setAddressList("Cc", cc);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setCc(Collection<Address> cc)
/* 198:    */   {
/* 199:433 */     setAddressList("Cc", cc);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public AddressList getBcc()
/* 203:    */   {
/* 204:444 */     return getAddressList("Bcc");
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setBcc(Address bcc)
/* 208:    */   {
/* 209:456 */     setAddressList("Bcc", bcc);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setBcc(Address... bcc)
/* 213:    */   {
/* 214:468 */     setAddressList("Bcc", bcc);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setBcc(Collection<Address> bcc)
/* 218:    */   {
/* 219:480 */     setAddressList("Bcc", bcc);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public AddressList getReplyTo()
/* 223:    */   {
/* 224:491 */     return getAddressList("Reply-To");
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setReplyTo(Address replyTo)
/* 228:    */   {
/* 229:503 */     setAddressList("Reply-To", replyTo);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setReplyTo(Address... replyTo)
/* 233:    */   {
/* 234:515 */     setAddressList("Reply-To", replyTo);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setReplyTo(Collection<Address> replyTo)
/* 238:    */   {
/* 239:527 */     setAddressList("Reply-To", replyTo);
/* 240:    */   }
/* 241:    */   
/* 242:    */   private Mailbox getMailbox(String fieldName)
/* 243:    */   {
/* 244:531 */     MailboxField field = (MailboxField)obtainField(fieldName);
/* 245:532 */     if (field == null) {
/* 246:533 */       return null;
/* 247:    */     }
/* 248:535 */     return field.getMailbox();
/* 249:    */   }
/* 250:    */   
/* 251:    */   private void setMailbox(String fieldName, Mailbox mailbox)
/* 252:    */   {
/* 253:539 */     Header header = obtainHeader();
/* 254:541 */     if (mailbox == null) {
/* 255:542 */       header.removeFields(fieldName);
/* 256:    */     } else {
/* 257:544 */       header.setField(Fields.mailbox(fieldName, mailbox));
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   private MailboxList getMailboxList(String fieldName)
/* 262:    */   {
/* 263:549 */     MailboxListField field = (MailboxListField)obtainField(fieldName);
/* 264:550 */     if (field == null) {
/* 265:551 */       return null;
/* 266:    */     }
/* 267:553 */     return field.getMailboxList();
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void setMailboxList(String fieldName, Mailbox mailbox)
/* 271:    */   {
/* 272:557 */     setMailboxList(fieldName, mailbox == null ? null : Collections.singleton(mailbox));
/* 273:    */   }
/* 274:    */   
/* 275:    */   private void setMailboxList(String fieldName, Mailbox... mailboxes)
/* 276:    */   {
/* 277:562 */     setMailboxList(fieldName, mailboxes == null ? null : Arrays.asList(mailboxes));
/* 278:    */   }
/* 279:    */   
/* 280:    */   private void setMailboxList(String fieldName, Collection<Mailbox> mailboxes)
/* 281:    */   {
/* 282:567 */     Header header = obtainHeader();
/* 283:569 */     if ((mailboxes == null) || (mailboxes.isEmpty())) {
/* 284:570 */       header.removeFields(fieldName);
/* 285:    */     } else {
/* 286:572 */       header.setField(Fields.mailboxList(fieldName, mailboxes));
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   private AddressList getAddressList(String fieldName)
/* 291:    */   {
/* 292:577 */     AddressListField field = (AddressListField)obtainField(fieldName);
/* 293:578 */     if (field == null) {
/* 294:579 */       return null;
/* 295:    */     }
/* 296:581 */     return field.getAddressList();
/* 297:    */   }
/* 298:    */   
/* 299:    */   private void setAddressList(String fieldName, Address address)
/* 300:    */   {
/* 301:585 */     setAddressList(fieldName, address == null ? null : Collections.singleton(address));
/* 302:    */   }
/* 303:    */   
/* 304:    */   private void setAddressList(String fieldName, Address... addresses)
/* 305:    */   {
/* 306:590 */     setAddressList(fieldName, addresses == null ? null : Arrays.asList(addresses));
/* 307:    */   }
/* 308:    */   
/* 309:    */   private void setAddressList(String fieldName, Collection<Address> addresses)
/* 310:    */   {
/* 311:595 */     Header header = obtainHeader();
/* 312:597 */     if ((addresses == null) || (addresses.isEmpty())) {
/* 313:598 */       header.removeFields(fieldName);
/* 314:    */     } else {
/* 315:600 */       header.setField(Fields.addressList(fieldName, addresses));
/* 316:    */     }
/* 317:    */   }
/* 318:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.Message
 * JD-Core Version:    0.7.0.1
 */