/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  6:   */ 
/*  7:   */ public class DelegatingFieldParser
/*  8:   */   implements FieldParser
/*  9:   */ {
/* 10:29 */   private Map<String, FieldParser> parsers = new HashMap();
/* 11:30 */   private FieldParser defaultParser = UnstructuredField.PARSER;
/* 12:   */   
/* 13:   */   public void setFieldParser(String name, FieldParser parser)
/* 14:   */   {
/* 15:38 */     this.parsers.put(name.toLowerCase(), parser);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public FieldParser getParser(String name)
/* 19:   */   {
/* 20:42 */     FieldParser field = (FieldParser)this.parsers.get(name.toLowerCase());
/* 21:43 */     if (field == null) {
/* 22:44 */       return this.defaultParser;
/* 23:   */     }
/* 24:46 */     return field;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ParsedField parse(String name, String body, ByteSequence raw)
/* 28:   */   {
/* 29:50 */     FieldParser parser = getParser(name);
/* 30:51 */     return parser.parse(name, body, raw);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.DelegatingFieldParser
 * JD-Core Version:    0.7.0.1
 */