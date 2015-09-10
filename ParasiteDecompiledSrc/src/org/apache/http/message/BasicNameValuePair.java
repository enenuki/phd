/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.http.NameValuePair;
/*   5:    */ import org.apache.http.util.CharArrayBuffer;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ public class BasicNameValuePair
/*   9:    */   implements NameValuePair, Cloneable, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -6437800749411518984L;
/*  12:    */   private final String name;
/*  13:    */   private final String value;
/*  14:    */   
/*  15:    */   public BasicNameValuePair(String name, String value)
/*  16:    */   {
/*  17: 56 */     if (name == null) {
/*  18: 57 */       throw new IllegalArgumentException("Name may not be null");
/*  19:    */     }
/*  20: 59 */     this.name = name;
/*  21: 60 */     this.value = value;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getName()
/*  25:    */   {
/*  26: 64 */     return this.name;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getValue()
/*  30:    */   {
/*  31: 68 */     return this.value;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString()
/*  35:    */   {
/*  36: 74 */     if (this.value == null) {
/*  37: 75 */       return this.name;
/*  38:    */     }
/*  39: 77 */     int len = this.name.length() + 1 + this.value.length();
/*  40: 78 */     CharArrayBuffer buffer = new CharArrayBuffer(len);
/*  41: 79 */     buffer.append(this.name);
/*  42: 80 */     buffer.append("=");
/*  43: 81 */     buffer.append(this.value);
/*  44: 82 */     return buffer.toString();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean equals(Object object)
/*  48:    */   {
/*  49: 87 */     if (this == object) {
/*  50: 87 */       return true;
/*  51:    */     }
/*  52: 88 */     if ((object instanceof NameValuePair))
/*  53:    */     {
/*  54: 89 */       BasicNameValuePair that = (BasicNameValuePair)object;
/*  55: 90 */       return (this.name.equals(that.name)) && (LangUtils.equals(this.value, that.value));
/*  56:    */     }
/*  57: 93 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int hashCode()
/*  61:    */   {
/*  62: 98 */     int hash = 17;
/*  63: 99 */     hash = LangUtils.hashCode(hash, this.name);
/*  64:100 */     hash = LangUtils.hashCode(hash, this.value);
/*  65:101 */     return hash;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object clone()
/*  69:    */     throws CloneNotSupportedException
/*  70:    */   {
/*  71:105 */     return super.clone();
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicNameValuePair
 * JD-Core Version:    0.7.0.1
 */