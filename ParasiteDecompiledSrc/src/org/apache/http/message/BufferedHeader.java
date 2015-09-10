/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.http.FormattedHeader;
/*   5:    */ import org.apache.http.HeaderElement;
/*   6:    */ import org.apache.http.ParseException;
/*   7:    */ import org.apache.http.util.CharArrayBuffer;
/*   8:    */ 
/*   9:    */ public class BufferedHeader
/*  10:    */   implements FormattedHeader, Cloneable, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -2768352615787625448L;
/*  13:    */   private final String name;
/*  14:    */   private final CharArrayBuffer buffer;
/*  15:    */   private final int valuePos;
/*  16:    */   
/*  17:    */   public BufferedHeader(CharArrayBuffer buffer)
/*  18:    */     throws ParseException
/*  19:    */   {
/*  20: 76 */     if (buffer == null) {
/*  21: 77 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*  22:    */     }
/*  23: 80 */     int colon = buffer.indexOf(58);
/*  24: 81 */     if (colon == -1) {
/*  25: 82 */       throw new ParseException("Invalid header: " + buffer.toString());
/*  26:    */     }
/*  27: 85 */     String s = buffer.substringTrimmed(0, colon);
/*  28: 86 */     if (s.length() == 0) {
/*  29: 87 */       throw new ParseException("Invalid header: " + buffer.toString());
/*  30:    */     }
/*  31: 90 */     this.buffer = buffer;
/*  32: 91 */     this.name = s;
/*  33: 92 */     this.valuePos = (colon + 1);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getName()
/*  37:    */   {
/*  38: 97 */     return this.name;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getValue()
/*  42:    */   {
/*  43:101 */     return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public HeaderElement[] getElements()
/*  47:    */     throws ParseException
/*  48:    */   {
/*  49:105 */     ParserCursor cursor = new ParserCursor(0, this.buffer.length());
/*  50:106 */     cursor.updatePos(this.valuePos);
/*  51:107 */     return BasicHeaderValueParser.DEFAULT.parseElements(this.buffer, cursor);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getValuePos()
/*  55:    */   {
/*  56:112 */     return this.valuePos;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CharArrayBuffer getBuffer()
/*  60:    */   {
/*  61:116 */     return this.buffer;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String toString()
/*  65:    */   {
/*  66:120 */     return this.buffer.toString();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object clone()
/*  70:    */     throws CloneNotSupportedException
/*  71:    */   {
/*  72:126 */     return super.clone();
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BufferedHeader
 * JD-Core Version:    0.7.0.1
 */