/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import java.io.StringReader;
/*  4:   */ import java.util.Date;
/*  5:   */ import org.apache.commons.logging.Log;
/*  6:   */ import org.apache.commons.logging.LogFactory;
/*  7:   */ import org.apache.james.mime4j.field.datetime.DateTime;
/*  8:   */ import org.apache.james.mime4j.field.datetime.parser.DateTimeParser;
/*  9:   */ import org.apache.james.mime4j.field.datetime.parser.ParseException;
/* 10:   */ import org.apache.james.mime4j.field.datetime.parser.TokenMgrError;
/* 11:   */ import org.apache.james.mime4j.util.ByteSequence;
/* 12:   */ 
/* 13:   */ public class DateTimeField
/* 14:   */   extends AbstractField
/* 15:   */ {
/* 16:36 */   private static Log log = LogFactory.getLog(DateTimeField.class);
/* 17:38 */   private boolean parsed = false;
/* 18:   */   private Date date;
/* 19:   */   private ParseException parseException;
/* 20:   */   
/* 21:   */   DateTimeField(String name, String body, ByteSequence raw)
/* 22:   */   {
/* 23:44 */     super(name, body, raw);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Date getDate()
/* 27:   */   {
/* 28:48 */     if (!this.parsed) {
/* 29:49 */       parse();
/* 30:   */     }
/* 31:51 */     return this.date;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ParseException getParseException()
/* 35:   */   {
/* 36:56 */     if (!this.parsed) {
/* 37:57 */       parse();
/* 38:   */     }
/* 39:59 */     return this.parseException;
/* 40:   */   }
/* 41:   */   
/* 42:   */   private void parse()
/* 43:   */   {
/* 44:63 */     String body = getBody();
/* 45:   */     try
/* 46:   */     {
/* 47:66 */       this.date = new DateTimeParser(new StringReader(body)).parseAll().getDate();
/* 48:   */     }
/* 49:   */     catch (ParseException e)
/* 50:   */     {
/* 51:69 */       if (log.isDebugEnabled()) {
/* 52:70 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 53:   */       }
/* 54:72 */       this.parseException = e;
/* 55:   */     }
/* 56:   */     catch (TokenMgrError e)
/* 57:   */     {
/* 58:74 */       if (log.isDebugEnabled()) {
/* 59:75 */         log.debug("Parsing value '" + body + "': " + e.getMessage());
/* 60:   */       }
/* 61:77 */       this.parseException = new ParseException(e.getMessage());
/* 62:   */     }
/* 63:80 */     this.parsed = true;
/* 64:   */   }
/* 65:   */   
/* 66:83 */   static final FieldParser PARSER = new FieldParser()
/* 67:   */   {
/* 68:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 69:   */     {
/* 70:86 */       return new DateTimeField(name, body, raw);
/* 71:   */     }
/* 72:   */   };
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.DateTimeField
 * JD-Core Version:    0.7.0.1
 */