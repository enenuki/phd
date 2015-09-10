/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.codec.DecoderUtil;
/*  4:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  5:   */ 
/*  6:   */ public class UnstructuredField
/*  7:   */   extends AbstractField
/*  8:   */ {
/*  9:29 */   private boolean parsed = false;
/* 10:   */   private String value;
/* 11:   */   
/* 12:   */   UnstructuredField(String name, String body, ByteSequence raw)
/* 13:   */   {
/* 14:34 */     super(name, body, raw);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getValue()
/* 18:   */   {
/* 19:38 */     if (!this.parsed) {
/* 20:39 */       parse();
/* 21:   */     }
/* 22:41 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   private void parse()
/* 26:   */   {
/* 27:45 */     String body = getBody();
/* 28:   */     
/* 29:47 */     this.value = DecoderUtil.decodeEncodedWords(body);
/* 30:   */     
/* 31:49 */     this.parsed = true;
/* 32:   */   }
/* 33:   */   
/* 34:52 */   static final FieldParser PARSER = new FieldParser()
/* 35:   */   {
/* 36:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 37:   */     {
/* 38:55 */       return new UnstructuredField(name, body, raw);
/* 39:   */     }
/* 40:   */   };
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.UnstructuredField
 * JD-Core Version:    0.7.0.1
 */