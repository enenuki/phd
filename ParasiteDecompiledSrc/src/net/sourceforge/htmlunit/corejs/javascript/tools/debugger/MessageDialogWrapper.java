/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Component;
/*    4:     */ import javax.swing.JOptionPane;
/*    5:     */ 
/*    6:     */ class MessageDialogWrapper
/*    7:     */ {
/*    8:     */   public static void showMessageDialog(Component parent, String msg, String title, int flags)
/*    9:     */   {
/*   10: 988 */     if (msg.length() > 60)
/*   11:     */     {
/*   12: 989 */       StringBuffer buf = new StringBuffer();
/*   13: 990 */       int len = msg.length();
/*   14: 991 */       int j = 0;
/*   15: 993 */       for (int i = 0; i < len; j++)
/*   16:     */       {
/*   17: 994 */         char c = msg.charAt(i);
/*   18: 995 */         buf.append(c);
/*   19: 996 */         if (Character.isWhitespace(c))
/*   20:     */         {
/*   21: 998 */           for (int k = i + 1; k < len; k++) {
/*   22: 999 */             if (Character.isWhitespace(msg.charAt(k))) {
/*   23:     */               break;
/*   24:     */             }
/*   25:     */           }
/*   26:1003 */           if (k < len)
/*   27:     */           {
/*   28:1004 */             int nextWordLen = k - i;
/*   29:1005 */             if (j + nextWordLen > 60)
/*   30:     */             {
/*   31:1006 */               buf.append('\n');
/*   32:1007 */               j = 0;
/*   33:     */             }
/*   34:     */           }
/*   35:     */         }
/*   36: 993 */         i++;
/*   37:     */       }
/*   38:1012 */       msg = buf.toString();
/*   39:     */     }
/*   40:1014 */     JOptionPane.showMessageDialog(parent, msg, title, flags);
/*   41:     */   }
/*   42:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.MessageDialogWrapper
 * JD-Core Version:    0.7.0.1
 */