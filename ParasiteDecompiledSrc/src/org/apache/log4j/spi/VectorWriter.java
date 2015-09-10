/*  1:   */ package org.apache.log4j.spi;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.util.Vector;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ class VectorWriter
/* 10:   */   extends PrintWriter
/* 11:   */ {
/* 12:   */   private Vector v;
/* 13:   */   
/* 14:   */   /**
/* 15:   */    * @deprecated
/* 16:   */    */
/* 17:   */   VectorWriter()
/* 18:   */   {
/* 19:36 */     super(new NullWriter());
/* 20:37 */     this.v = new Vector();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void print(Object o)
/* 24:   */   {
/* 25:41 */     this.v.addElement(String.valueOf(o));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void print(char[] chars)
/* 29:   */   {
/* 30:45 */     this.v.addElement(new String(chars));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void print(String s)
/* 34:   */   {
/* 35:49 */     this.v.addElement(s);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void println(Object o)
/* 39:   */   {
/* 40:53 */     this.v.addElement(String.valueOf(o));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void println(char[] chars)
/* 44:   */   {
/* 45:60 */     this.v.addElement(new String(chars));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void println(String s)
/* 49:   */   {
/* 50:65 */     this.v.addElement(s);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void write(char[] chars)
/* 54:   */   {
/* 55:69 */     this.v.addElement(new String(chars));
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void write(char[] chars, int off, int len)
/* 59:   */   {
/* 60:73 */     this.v.addElement(new String(chars, off, len));
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void write(String s, int off, int len)
/* 64:   */   {
/* 65:77 */     this.v.addElement(s.substring(off, off + len));
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void write(String s)
/* 69:   */   {
/* 70:81 */     this.v.addElement(s);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String[] toStringArray()
/* 74:   */   {
/* 75:85 */     int len = this.v.size();
/* 76:86 */     String[] sa = new String[len];
/* 77:87 */     for (int i = 0; i < len; i++) {
/* 78:88 */       sa[i] = ((String)this.v.elementAt(i));
/* 79:   */     }
/* 80:90 */     return sa;
/* 81:   */   }
/* 82:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.VectorWriter
 * JD-Core Version:    0.7.0.1
 */