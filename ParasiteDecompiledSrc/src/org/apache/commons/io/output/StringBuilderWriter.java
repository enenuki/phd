/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.io.Writer;
/*   5:    */ 
/*   6:    */ public class StringBuilderWriter
/*   7:    */   extends Writer
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private final StringBuilder builder;
/*  11:    */   
/*  12:    */   public StringBuilderWriter()
/*  13:    */   {
/*  14: 42 */     this.builder = new StringBuilder();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public StringBuilderWriter(int capacity)
/*  18:    */   {
/*  19: 51 */     this.builder = new StringBuilder(capacity);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public StringBuilderWriter(StringBuilder builder)
/*  23:    */   {
/*  24: 60 */     this.builder = (builder != null ? builder : new StringBuilder());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Writer append(char value)
/*  28:    */   {
/*  29: 71 */     this.builder.append(value);
/*  30: 72 */     return this;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Writer append(CharSequence value)
/*  34:    */   {
/*  35: 83 */     this.builder.append(value);
/*  36: 84 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Writer append(CharSequence value, int start, int end)
/*  40:    */   {
/*  41: 97 */     this.builder.append(value, start, end);
/*  42: 98 */     return this;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void close() {}
/*  46:    */   
/*  47:    */   public void flush() {}
/*  48:    */   
/*  49:    */   public void write(String value)
/*  50:    */   {
/*  51:123 */     if (value != null) {
/*  52:124 */       this.builder.append(value);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void write(char[] value, int offset, int length)
/*  57:    */   {
/*  58:137 */     if (value != null) {
/*  59:138 */       this.builder.append(value, offset, length);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public StringBuilder getBuilder()
/*  64:    */   {
/*  65:148 */     return this.builder;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String toString()
/*  69:    */   {
/*  70:158 */     return this.builder.toString();
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.StringBuilderWriter
 * JD-Core Version:    0.7.0.1
 */