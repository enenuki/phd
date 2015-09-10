/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  4:   */ 
/*  5:   */ public class ContentTransferEncodingField
/*  6:   */   extends AbstractField
/*  7:   */ {
/*  8:   */   private String encoding;
/*  9:   */   
/* 10:   */   ContentTransferEncodingField(String name, String body, ByteSequence raw)
/* 11:   */   {
/* 12:32 */     super(name, body, raw);
/* 13:33 */     this.encoding = body.trim().toLowerCase();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getEncoding()
/* 17:   */   {
/* 18:42 */     return this.encoding;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static String getEncoding(ContentTransferEncodingField f)
/* 22:   */   {
/* 23:53 */     if ((f != null) && (f.getEncoding().length() != 0)) {
/* 24:54 */       return f.getEncoding();
/* 25:   */     }
/* 26:56 */     return "7bit";
/* 27:   */   }
/* 28:   */   
/* 29:59 */   static final FieldParser PARSER = new FieldParser()
/* 30:   */   {
/* 31:   */     public ParsedField parse(String name, String body, ByteSequence raw)
/* 32:   */     {
/* 33:62 */       return new ContentTransferEncodingField(name, body, raw);
/* 34:   */     }
/* 35:   */   };
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.ContentTransferEncodingField
 * JD-Core Version:    0.7.0.1
 */