/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.http.message.BasicNameValuePair;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ public class NameValuePair
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private final String name_;
/*  12:    */   private final String value_;
/*  13:    */   
/*  14:    */   public NameValuePair(String name, String value)
/*  15:    */   {
/*  16: 44 */     this.name_ = name;
/*  17: 45 */     this.value_ = value;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getName()
/*  21:    */   {
/*  22: 53 */     return this.name_;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getValue()
/*  26:    */   {
/*  27: 61 */     return this.value_;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean equals(Object object)
/*  31:    */   {
/*  32: 69 */     if (!(object instanceof NameValuePair)) {
/*  33: 70 */       return false;
/*  34:    */     }
/*  35: 72 */     NameValuePair other = (NameValuePair)object;
/*  36: 73 */     return (LangUtils.equals(this.name_, other.name_)) && (LangUtils.equals(this.value_, other.value_));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int hashCode()
/*  40:    */   {
/*  41: 81 */     int hash = 17;
/*  42: 82 */     hash = LangUtils.hashCode(hash, this.name_);
/*  43: 83 */     hash = LangUtils.hashCode(hash, this.value_);
/*  44: 84 */     return hash;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String toString()
/*  48:    */   {
/*  49: 92 */     return this.name_ + "=" + this.value_;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static org.apache.http.NameValuePair[] toHttpClient(NameValuePair[] pairs)
/*  53:    */   {
/*  54:101 */     org.apache.http.NameValuePair[] pairs2 = new org.apache.http.NameValuePair[pairs.length];
/*  55:103 */     for (int i = 0; i < pairs.length; i++)
/*  56:    */     {
/*  57:104 */       NameValuePair pair = pairs[i];
/*  58:105 */       pairs2[i] = new BasicNameValuePair(pair.getName(), pair.getValue());
/*  59:    */     }
/*  60:107 */     return pairs2;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static org.apache.http.NameValuePair[] toHttpClient(List<NameValuePair> pairs)
/*  64:    */   {
/*  65:116 */     org.apache.http.NameValuePair[] pairs2 = new org.apache.http.NameValuePair[pairs.size()];
/*  66:117 */     for (int i = 0; i < pairs.size(); i++)
/*  67:    */     {
/*  68:118 */       NameValuePair pair = (NameValuePair)pairs.get(i);
/*  69:119 */       pairs2[i] = new BasicNameValuePair(pair.getName(), pair.getValue());
/*  70:    */     }
/*  71:121 */     return pairs2;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.NameValuePair
 * JD-Core Version:    0.7.0.1
 */