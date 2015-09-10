/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public final class ScriptStackElement
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   public final String fileName;
/*  9:   */   public final String functionName;
/* 10:   */   public final int lineNumber;
/* 11:   */   
/* 12:   */   public ScriptStackElement(String fileName, String functionName, int lineNumber)
/* 13:   */   {
/* 14:18 */     this.fileName = fileName;
/* 15:19 */     this.functionName = functionName;
/* 16:20 */     this.lineNumber = lineNumber;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:24 */     StringBuilder sb = new StringBuilder();
/* 22:25 */     renderMozillaStyle(sb);
/* 23:26 */     return sb.toString();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void renderJavaStyle(StringBuilder sb)
/* 27:   */   {
/* 28:35 */     sb.append("\tat ").append(this.fileName);
/* 29:36 */     if (this.lineNumber > -1) {
/* 30:37 */       sb.append(':').append(this.lineNumber);
/* 31:   */     }
/* 32:39 */     if (this.functionName != null) {
/* 33:40 */       sb.append(" (").append(this.functionName).append(')');
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void renderMozillaStyle(StringBuilder sb)
/* 38:   */   {
/* 39:50 */     if (this.functionName != null) {
/* 40:51 */       sb.append(this.functionName).append("()");
/* 41:   */     }
/* 42:53 */     sb.append('@').append(this.fileName);
/* 43:54 */     if (this.lineNumber > -1) {
/* 44:55 */       sb.append(':').append(this.lineNumber);
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ScriptStackElement
 * JD-Core Version:    0.7.0.1
 */