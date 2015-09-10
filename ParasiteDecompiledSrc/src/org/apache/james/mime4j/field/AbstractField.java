/*   1:    */ package org.apache.james.mime4j.field;
/*   2:    */ 
/*   3:    */ import java.util.regex.Matcher;
/*   4:    */ import java.util.regex.Pattern;
/*   5:    */ import org.apache.james.mime4j.MimeException;
/*   6:    */ import org.apache.james.mime4j.util.ByteSequence;
/*   7:    */ import org.apache.james.mime4j.util.ContentUtil;
/*   8:    */ import org.apache.james.mime4j.util.MimeUtil;
/*   9:    */ 
/*  10:    */ public abstract class AbstractField
/*  11:    */   implements ParsedField
/*  12:    */ {
/*  13: 35 */   private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("^([\\x21-\\x39\\x3b-\\x7e]+):");
/*  14: 38 */   private static final DefaultFieldParser parser = new DefaultFieldParser();
/*  15:    */   private final String name;
/*  16:    */   private final String body;
/*  17:    */   private final ByteSequence raw;
/*  18:    */   
/*  19:    */   protected AbstractField(String name, String body, ByteSequence raw)
/*  20:    */   {
/*  21: 45 */     this.name = name;
/*  22: 46 */     this.body = body;
/*  23: 47 */     this.raw = raw;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static ParsedField parse(ByteSequence raw)
/*  27:    */     throws MimeException
/*  28:    */   {
/*  29: 62 */     String rawStr = ContentUtil.decode(raw);
/*  30: 63 */     return parse(raw, rawStr);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static ParsedField parse(String rawStr)
/*  34:    */     throws MimeException
/*  35:    */   {
/*  36: 89 */     ByteSequence raw = ContentUtil.encode(rawStr);
/*  37: 90 */     return parse(raw, rawStr);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static DefaultFieldParser getParser()
/*  41:    */   {
/*  42: 99 */     return parser;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getName()
/*  46:    */   {
/*  47:109 */     return this.name;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ByteSequence getRaw()
/*  51:    */   {
/*  52:118 */     return this.raw;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getBody()
/*  56:    */   {
/*  57:128 */     return this.body;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isValidField()
/*  61:    */   {
/*  62:135 */     return getParseException() == null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ParseException getParseException()
/*  66:    */   {
/*  67:142 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String toString()
/*  71:    */   {
/*  72:147 */     return this.name + ": " + this.body;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static ParsedField parse(ByteSequence raw, String rawStr)
/*  76:    */     throws MimeException
/*  77:    */   {
/*  78:155 */     String unfolded = MimeUtil.unfold(rawStr);
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:160 */     Matcher fieldMatcher = FIELD_NAME_PATTERN.matcher(unfolded);
/*  84:161 */     if (!fieldMatcher.find()) {
/*  85:162 */       throw new MimeException("Invalid field in string");
/*  86:    */     }
/*  87:164 */     String name = fieldMatcher.group(1);
/*  88:    */     
/*  89:166 */     String body = unfolded.substring(fieldMatcher.end());
/*  90:167 */     if ((body.length() > 0) && (body.charAt(0) == ' ')) {
/*  91:168 */       body = body.substring(1);
/*  92:    */     }
/*  93:171 */     return parser.parse(name, body, raw);
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.AbstractField
 * JD-Core Version:    0.7.0.1
 */