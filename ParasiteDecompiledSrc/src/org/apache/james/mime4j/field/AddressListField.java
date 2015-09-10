/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.commons.logging.Log;
/*  4:   */ import org.apache.commons.logging.LogFactory;
/*  5:   */ import org.apache.james.mime4j.field.address.AddressList;
/*  6:   */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*  7:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  8:   */ 
/*  9:   */ public class AddressListField
/* 10:   */   extends AbstractField
/* 11:   */ {
/* 12:32 */   private static Log log = LogFactory.getLog(AddressListField.class);
/* 13:34 */   private boolean parsed = false;
/* 14:   */   private AddressList addressList;
/* 15:   */   private ParseException parseException;
/* 16:   */   
/* 17:   */   AddressListField(String name, String body, ByteSequence raw)
/* 18:   */   {
/* 19:40 */     super(name, body, raw);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public AddressList getAddressList()
/* 23:   */   {
/* 24:44 */     if (!this.parsed) {
/* 25:45 */       parse();
/* 26:   */     }
/* 27:47 */     return this.addressList;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ParseException getParseException()
/* 31:   */   {
/* 32:52 */     if (!this.parsed) {
/* 33:53 */       parse();
/* 34:   */     }
/* 35:55 */     return this.parseException;
/* 36:   */   }
/* 37:   */   
/* 38:   */   private void parse()
/* 39:   */   {
/* 40:59 */     String body = getBody();
/* 41:   */     try
/* 42:   */     {
/* 43:62 */       this.addressList = AddressList.parse(body);
/* 44:   */     }
/* 45:   */     catch (ParseException e)
/* 46:   */     {
/* 47:64 */       if (log.isDebugEnabled()) {
/* 48:65 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 49:   */       }
/* 50:67 */       this.parseException = e;
/* 51:   */     }
/* 52:70 */     this.parsed = true;
/* 53:   */   }
/* 54:   */   
/* 55:73 */   static final FieldParser PARSER = new FieldParser()
/* 56:   */   {
/* 57:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 58:   */     {
/* 59:76 */       return new AddressListField(name, body, raw);
/* 60:   */     }
/* 61:   */   };
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.AddressListField
 * JD-Core Version:    0.7.0.1
 */