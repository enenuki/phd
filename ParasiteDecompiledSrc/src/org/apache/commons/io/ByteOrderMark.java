/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class ByteOrderMark
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9: 35 */   public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", new int[] { 239, 187, 191 });
/*  10: 37 */   public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] { 254, 255 });
/*  11: 39 */   public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] { 255, 254 });
/*  12:    */   private final String charsetName;
/*  13:    */   private final int[] bytes;
/*  14:    */   
/*  15:    */   public ByteOrderMark(String charsetName, int... bytes)
/*  16:    */   {
/*  17: 55 */     if ((charsetName == null) || (charsetName.length() == 0)) {
/*  18: 56 */       throw new IllegalArgumentException("No charsetName specified");
/*  19:    */     }
/*  20: 58 */     if ((bytes == null) || (bytes.length == 0)) {
/*  21: 59 */       throw new IllegalArgumentException("No bytes specified");
/*  22:    */     }
/*  23: 61 */     this.charsetName = charsetName;
/*  24: 62 */     this.bytes = new int[bytes.length];
/*  25: 63 */     System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getCharsetName()
/*  29:    */   {
/*  30: 72 */     return this.charsetName;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int length()
/*  34:    */   {
/*  35: 81 */     return this.bytes.length;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int get(int pos)
/*  39:    */   {
/*  40: 91 */     return this.bytes[pos];
/*  41:    */   }
/*  42:    */   
/*  43:    */   public byte[] getBytes()
/*  44:    */   {
/*  45:100 */     byte[] copy = new byte[this.bytes.length];
/*  46:101 */     for (int i = 0; i < this.bytes.length; i++) {
/*  47:102 */       copy[i] = ((byte)this.bytes[i]);
/*  48:    */     }
/*  49:104 */     return copy;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean equals(Object obj)
/*  53:    */   {
/*  54:116 */     if (!(obj instanceof ByteOrderMark)) {
/*  55:117 */       return false;
/*  56:    */     }
/*  57:119 */     ByteOrderMark bom = (ByteOrderMark)obj;
/*  58:120 */     if (this.bytes.length != bom.length()) {
/*  59:121 */       return false;
/*  60:    */     }
/*  61:123 */     for (int i = 0; i < this.bytes.length; i++) {
/*  62:124 */       if (this.bytes[i] != bom.get(i)) {
/*  63:125 */         return false;
/*  64:    */       }
/*  65:    */     }
/*  66:128 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int hashCode()
/*  70:    */   {
/*  71:139 */     int hashCode = getClass().hashCode();
/*  72:140 */     for (int b : this.bytes) {
/*  73:141 */       hashCode += b;
/*  74:    */     }
/*  75:143 */     return hashCode;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:153 */     StringBuilder builder = new StringBuilder();
/*  81:154 */     builder.append(getClass().getSimpleName());
/*  82:155 */     builder.append('[');
/*  83:156 */     builder.append(this.charsetName);
/*  84:157 */     builder.append(": ");
/*  85:158 */     for (int i = 0; i < this.bytes.length; i++)
/*  86:    */     {
/*  87:159 */       if (i > 0) {
/*  88:160 */         builder.append(",");
/*  89:    */       }
/*  90:162 */       builder.append("0x");
/*  91:163 */       builder.append(Integer.toHexString(0xFF & this.bytes[i]).toUpperCase());
/*  92:    */     }
/*  93:165 */     builder.append(']');
/*  94:166 */     return builder.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.ByteOrderMark
 * JD-Core Version:    0.7.0.1
 */