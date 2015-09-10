/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.commons.logging.Log;
/*  4:   */ import org.apache.commons.logging.LogFactory;
/*  5:   */ import org.apache.james.mime4j.field.address.AddressList;
/*  6:   */ import org.apache.james.mime4j.field.address.MailboxList;
/*  7:   */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*  8:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  9:   */ 
/* 10:   */ public class MailboxListField
/* 11:   */   extends AbstractField
/* 12:   */ {
/* 13:33 */   private static Log log = LogFactory.getLog(MailboxListField.class);
/* 14:35 */   private boolean parsed = false;
/* 15:   */   private MailboxList mailboxList;
/* 16:   */   private ParseException parseException;
/* 17:   */   
/* 18:   */   MailboxListField(String name, String body, ByteSequence raw)
/* 19:   */   {
/* 20:41 */     super(name, body, raw);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MailboxList getMailboxList()
/* 24:   */   {
/* 25:45 */     if (!this.parsed) {
/* 26:46 */       parse();
/* 27:   */     }
/* 28:48 */     return this.mailboxList;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ParseException getParseException()
/* 32:   */   {
/* 33:53 */     if (!this.parsed) {
/* 34:54 */       parse();
/* 35:   */     }
/* 36:56 */     return this.parseException;
/* 37:   */   }
/* 38:   */   
/* 39:   */   private void parse()
/* 40:   */   {
/* 41:60 */     String body = getBody();
/* 42:   */     try
/* 43:   */     {
/* 44:63 */       this.mailboxList = AddressList.parse(body).flatten();
/* 45:   */     }
/* 46:   */     catch (ParseException e)
/* 47:   */     {
/* 48:65 */       if (log.isDebugEnabled()) {
/* 49:66 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 50:   */       }
/* 51:68 */       this.parseException = e;
/* 52:   */     }
/* 53:71 */     this.parsed = true;
/* 54:   */   }
/* 55:   */   
/* 56:74 */   static final FieldParser PARSER = new FieldParser()
/* 57:   */   {
/* 58:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 59:   */     {
/* 60:77 */       return new MailboxListField(name, body, raw);
/* 61:   */     }
/* 62:   */   };
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.MailboxListField
 * JD-Core Version:    0.7.0.1
 */