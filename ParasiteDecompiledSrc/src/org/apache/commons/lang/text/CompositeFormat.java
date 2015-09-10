/*   1:    */ package org.apache.commons.lang.text;
/*   2:    */ 
/*   3:    */ import java.text.FieldPosition;
/*   4:    */ import java.text.Format;
/*   5:    */ import java.text.ParseException;
/*   6:    */ import java.text.ParsePosition;
/*   7:    */ 
/*   8:    */ public class CompositeFormat
/*   9:    */   extends Format
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -4329119827877627683L;
/*  12:    */   private final Format parser;
/*  13:    */   private final Format formatter;
/*  14:    */   
/*  15:    */   public CompositeFormat(Format parser, Format formatter)
/*  16:    */   {
/*  17: 55 */     this.parser = parser;
/*  18: 56 */     this.formatter = formatter;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
/*  22:    */   {
/*  23: 70 */     return this.formatter.format(obj, toAppendTo, pos);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object parseObject(String source, ParsePosition pos)
/*  27:    */   {
/*  28: 84 */     return this.parser.parseObject(source, pos);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Format getParser()
/*  32:    */   {
/*  33: 93 */     return this.parser;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Format getFormatter()
/*  37:    */   {
/*  38:102 */     return this.formatter;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String reformat(String input)
/*  42:    */     throws ParseException
/*  43:    */   {
/*  44:113 */     return format(parseObject(input));
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.CompositeFormat
 * JD-Core Version:    0.7.0.1
 */