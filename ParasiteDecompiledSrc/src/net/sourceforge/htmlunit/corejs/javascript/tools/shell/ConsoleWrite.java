/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*  2:   */ 
/*  3:   */ class ConsoleWrite
/*  4:   */   implements Runnable
/*  5:   */ {
/*  6:   */   private ConsoleTextArea textArea;
/*  7:   */   private String str;
/*  8:   */   
/*  9:   */   public ConsoleWrite(ConsoleTextArea textArea, String str)
/* 10:   */   {
/* 11:52 */     this.textArea = textArea;
/* 12:53 */     this.str = str;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void run()
/* 16:   */   {
/* 17:57 */     this.textArea.write(this.str);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleWrite
 * JD-Core Version:    0.7.0.1
 */