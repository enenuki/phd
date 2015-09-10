/*  1:   */ package org.apache.http.message;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.http.Header;
/*  5:   */ import org.apache.http.HeaderElement;
/*  6:   */ import org.apache.http.ParseException;
/*  7:   */ import org.apache.http.util.CharArrayBuffer;
/*  8:   */ 
/*  9:   */ public class BasicHeader
/* 10:   */   implements Header, Cloneable, Serializable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = -5427236326487562174L;
/* 13:   */   private final String name;
/* 14:   */   private final String value;
/* 15:   */   
/* 16:   */   public BasicHeader(String name, String value)
/* 17:   */   {
/* 18:56 */     if (name == null) {
/* 19:57 */       throw new IllegalArgumentException("Name may not be null");
/* 20:   */     }
/* 21:59 */     this.name = name;
/* 22:60 */     this.value = value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getName()
/* 26:   */   {
/* 27:64 */     return this.name;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getValue()
/* 31:   */   {
/* 32:68 */     return this.value;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:73 */     return BasicLineFormatter.DEFAULT.formatHeader(null, this).toString();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public HeaderElement[] getElements()
/* 41:   */     throws ParseException
/* 42:   */   {
/* 43:77 */     if (this.value != null) {
/* 44:79 */       return BasicHeaderValueParser.parseElements(this.value, null);
/* 45:   */     }
/* 46:81 */     return new HeaderElement[0];
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Object clone()
/* 50:   */     throws CloneNotSupportedException
/* 51:   */   {
/* 52:86 */     return super.clone();
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeader
 * JD-Core Version:    0.7.0.1
 */