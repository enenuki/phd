/*  1:   */ package org.apache.xalan.xsltc.runtime.output;
/*  2:   */ 
/*  3:   */ class StringOutputBuffer
/*  4:   */   implements OutputBuffer
/*  5:   */ {
/*  6:   */   private StringBuffer _buffer;
/*  7:   */   
/*  8:   */   public StringOutputBuffer()
/*  9:   */   {
/* 10:32 */     this._buffer = new StringBuffer();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String close()
/* 14:   */   {
/* 15:36 */     return this._buffer.toString();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public OutputBuffer append(String s)
/* 19:   */   {
/* 20:40 */     this._buffer.append(s);
/* 21:41 */     return this;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public OutputBuffer append(char[] s, int from, int to)
/* 25:   */   {
/* 26:45 */     this._buffer.append(s, from, to);
/* 27:46 */     return this;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public OutputBuffer append(char ch)
/* 31:   */   {
/* 32:50 */     this._buffer.append(ch);
/* 33:51 */     return this;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.output.StringOutputBuffer
 * JD-Core Version:    0.7.0.1
 */