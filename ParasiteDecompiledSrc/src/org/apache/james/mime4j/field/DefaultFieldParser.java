/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ public class DefaultFieldParser
/*  4:   */   extends DelegatingFieldParser
/*  5:   */ {
/*  6:   */   public DefaultFieldParser()
/*  7:   */   {
/*  8:25 */     setFieldParser("Content-Transfer-Encoding", ContentTransferEncodingField.PARSER);
/*  9:   */     
/* 10:27 */     setFieldParser("Content-Type", ContentTypeField.PARSER);
/* 11:28 */     setFieldParser("Content-Disposition", ContentDispositionField.PARSER);
/* 12:   */     
/* 13:   */ 
/* 14:31 */     FieldParser dateTimeParser = DateTimeField.PARSER;
/* 15:32 */     setFieldParser("Date", dateTimeParser);
/* 16:33 */     setFieldParser("Resent-Date", dateTimeParser);
/* 17:   */     
/* 18:35 */     FieldParser mailboxListParser = MailboxListField.PARSER;
/* 19:36 */     setFieldParser("From", mailboxListParser);
/* 20:37 */     setFieldParser("Resent-From", mailboxListParser);
/* 21:   */     
/* 22:39 */     FieldParser mailboxParser = MailboxField.PARSER;
/* 23:40 */     setFieldParser("Sender", mailboxParser);
/* 24:41 */     setFieldParser("Resent-Sender", mailboxParser);
/* 25:   */     
/* 26:43 */     FieldParser addressListParser = AddressListField.PARSER;
/* 27:44 */     setFieldParser("To", addressListParser);
/* 28:45 */     setFieldParser("Resent-To", addressListParser);
/* 29:46 */     setFieldParser("Cc", addressListParser);
/* 30:47 */     setFieldParser("Resent-Cc", addressListParser);
/* 31:48 */     setFieldParser("Bcc", addressListParser);
/* 32:49 */     setFieldParser("Resent-Bcc", addressListParser);
/* 33:50 */     setFieldParser("Reply-To", addressListParser);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.DefaultFieldParser
 * JD-Core Version:    0.7.0.1
 */