/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ 
/*   7:    */ class WriterToASCI
/*   8:    */   extends Writer
/*   9:    */   implements WriterChain
/*  10:    */ {
/*  11:    */   private final OutputStream m_os;
/*  12:    */   
/*  13:    */   public WriterToASCI(OutputStream os)
/*  14:    */   {
/*  15: 53 */     this.m_os = os;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void write(char[] chars, int start, int length)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 71 */     int n = length + start;
/*  22: 73 */     for (int i = start; i < n; i++) {
/*  23: 75 */       this.m_os.write(chars[i]);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void write(int c)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 92 */     this.m_os.write(c);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void write(String s)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:104 */     int n = s.length();
/*  37:105 */     for (int i = 0; i < n; i++) {
/*  38:107 */       this.m_os.write(s.charAt(i));
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void flush()
/*  43:    */     throws IOException
/*  44:    */   {
/*  45:122 */     this.m_os.flush();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void close()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:134 */     this.m_os.close();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public OutputStream getOutputStream()
/*  55:    */   {
/*  56:145 */     return this.m_os;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Writer getWriter()
/*  60:    */   {
/*  61:153 */     return null;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.WriterToASCI
 * JD-Core Version:    0.7.0.1
 */