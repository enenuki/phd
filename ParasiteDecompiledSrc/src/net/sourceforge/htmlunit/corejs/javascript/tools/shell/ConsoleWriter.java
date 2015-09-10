/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ import javax.swing.SwingUtilities;
/*   5:    */ 
/*   6:    */ class ConsoleWriter
/*   7:    */   extends OutputStream
/*   8:    */ {
/*   9:    */   private ConsoleTextArea textArea;
/*  10:    */   private StringBuffer buffer;
/*  11:    */   
/*  12:    */   public ConsoleWriter(ConsoleTextArea textArea)
/*  13:    */   {
/*  14: 67 */     this.textArea = textArea;
/*  15: 68 */     this.buffer = new StringBuffer();
/*  16:    */   }
/*  17:    */   
/*  18:    */   public synchronized void write(int ch)
/*  19:    */   {
/*  20: 73 */     this.buffer.append((char)ch);
/*  21: 74 */     if (ch == 10) {
/*  22: 75 */       flushBuffer();
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public synchronized void write(char[] data, int off, int len)
/*  27:    */   {
/*  28: 80 */     for (int i = off; i < len; i++)
/*  29:    */     {
/*  30: 81 */       this.buffer.append(data[i]);
/*  31: 82 */       if (data[i] == '\n') {
/*  32: 83 */         flushBuffer();
/*  33:    */       }
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public synchronized void flush()
/*  38:    */   {
/*  39: 90 */     if (this.buffer.length() > 0) {
/*  40: 91 */       flushBuffer();
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void close()
/*  45:    */   {
/*  46: 97 */     flush();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void flushBuffer()
/*  50:    */   {
/*  51:101 */     String str = this.buffer.toString();
/*  52:102 */     this.buffer.setLength(0);
/*  53:103 */     SwingUtilities.invokeLater(new ConsoleWrite(this.textArea, str));
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.ConsoleWriter
 * JD-Core Version:    0.7.0.1
 */