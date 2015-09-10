/*  1:   */ package org.apache.xalan.xsltc.runtime.output;
/*  2:   */ 
/*  3:   */ import java.io.BufferedWriter;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.Writer;
/*  6:   */ 
/*  7:   */ class WriterOutputBuffer
/*  8:   */   implements OutputBuffer
/*  9:   */ {
/* 10:   */   private static final int KB = 1024;
/* 11:33 */   private static int BUFFER_SIZE = 4096;
/* 12:   */   private Writer _writer;
/* 13:   */   
/* 14:   */   static
/* 15:   */   {
/* 16:37 */     String osName = System.getProperty("os.name");
/* 17:38 */     if (osName.equalsIgnoreCase("solaris")) {
/* 18:39 */       BUFFER_SIZE = 32768;
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public WriterOutputBuffer(Writer writer)
/* 23:   */   {
/* 24:52 */     this._writer = new BufferedWriter(writer, BUFFER_SIZE);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String close()
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:57 */       this._writer.flush();
/* 32:   */     }
/* 33:   */     catch (IOException e)
/* 34:   */     {
/* 35:60 */       throw new RuntimeException(e.toString());
/* 36:   */     }
/* 37:62 */     return "";
/* 38:   */   }
/* 39:   */   
/* 40:   */   public OutputBuffer append(String s)
/* 41:   */   {
/* 42:   */     try
/* 43:   */     {
/* 44:67 */       this._writer.write(s);
/* 45:   */     }
/* 46:   */     catch (IOException e)
/* 47:   */     {
/* 48:70 */       throw new RuntimeException(e.toString());
/* 49:   */     }
/* 50:72 */     return this;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public OutputBuffer append(char[] s, int from, int to)
/* 54:   */   {
/* 55:   */     try
/* 56:   */     {
/* 57:77 */       this._writer.write(s, from, to);
/* 58:   */     }
/* 59:   */     catch (IOException e)
/* 60:   */     {
/* 61:80 */       throw new RuntimeException(e.toString());
/* 62:   */     }
/* 63:82 */     return this;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public OutputBuffer append(char ch)
/* 67:   */   {
/* 68:   */     try
/* 69:   */     {
/* 70:87 */       this._writer.write(ch);
/* 71:   */     }
/* 72:   */     catch (IOException e)
/* 73:   */     {
/* 74:90 */       throw new RuntimeException(e.toString());
/* 75:   */     }
/* 76:92 */     return this;
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.output.WriterOutputBuffer
 * JD-Core Version:    0.7.0.1
 */