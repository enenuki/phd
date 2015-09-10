/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.commons.logging.Log;
/*  4:   */ import org.apache.commons.logging.LogFactory;
/*  5:   */ import org.apache.james.mime4j.field.address.AddressList;
/*  6:   */ import org.apache.james.mime4j.field.address.Mailbox;
/*  7:   */ import org.apache.james.mime4j.field.address.MailboxList;
/*  8:   */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*  9:   */ import org.apache.james.mime4j.util.ByteSequence;
/* 10:   */ 
/* 11:   */ public class MailboxField
/* 12:   */   extends AbstractField
/* 13:   */ {
/* 14:34 */   private static Log log = LogFactory.getLog(MailboxField.class);
/* 15:36 */   private boolean parsed = false;
/* 16:   */   private Mailbox mailbox;
/* 17:   */   private ParseException parseException;
/* 18:   */   
/* 19:   */   MailboxField(String name, String body, ByteSequence raw)
/* 20:   */   {
/* 21:42 */     super(name, body, raw);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Mailbox getMailbox()
/* 25:   */   {
/* 26:46 */     if (!this.parsed) {
/* 27:47 */       parse();
/* 28:   */     }
/* 29:49 */     return this.mailbox;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ParseException getParseException()
/* 33:   */   {
/* 34:54 */     if (!this.parsed) {
/* 35:55 */       parse();
/* 36:   */     }
/* 37:57 */     return this.parseException;
/* 38:   */   }
/* 39:   */   
/* 40:   */   private void parse()
/* 41:   */   {
/* 42:61 */     String body = getBody();
/* 43:   */     try
/* 44:   */     {
/* 45:64 */       MailboxList mailboxList = AddressList.parse(body).flatten();
/* 46:65 */       if (mailboxList.size() > 0) {
/* 47:66 */         this.mailbox = mailboxList.get(0);
/* 48:   */       }
/* 49:   */     }
/* 50:   */     catch (ParseException e)
/* 51:   */     {
/* 52:69 */       if (log.isDebugEnabled()) {
/* 53:70 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 54:   */       }
/* 55:72 */       this.parseException = e;
/* 56:   */     }
/* 57:75 */     this.parsed = true;
/* 58:   */   }
/* 59:   */   
/* 60:78 */   static final FieldParser PARSER = new FieldParser()
/* 61:   */   {
/* 62:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 63:   */     {
/* 64:81 */       return new MailboxField(name, body, raw);
/* 65:   */     }
/* 66:   */   };
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.MailboxField
 * JD-Core Version:    0.7.0.1
 */