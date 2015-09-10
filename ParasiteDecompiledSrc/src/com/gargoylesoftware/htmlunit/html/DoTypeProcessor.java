/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ abstract class DoTypeProcessor
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   void doType(String currentValue, int selectionStart, int selectionEnd, char c, boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  9:   */   {
/* 10:24 */     StringBuilder newValue = new StringBuilder(currentValue);
/* 11:25 */     int cursorPosition = selectionStart;
/* 12:26 */     if (c == '\b')
/* 13:   */     {
/* 14:27 */       if (selectionStart > 0)
/* 15:   */       {
/* 16:28 */         newValue.deleteCharAt(selectionStart - 1);
/* 17:29 */         cursorPosition = selectionStart - 1;
/* 18:   */       }
/* 19:   */     }
/* 20:32 */     else if ((c < 57344) || (c > 63743)) {
/* 21:36 */       if (acceptChar(c))
/* 22:   */       {
/* 23:37 */         if (selectionStart != currentValue.length()) {
/* 24:38 */           newValue.replace(selectionStart, selectionEnd, Character.toString(c));
/* 25:   */         } else {
/* 26:41 */           newValue.append(c);
/* 27:   */         }
/* 28:43 */         cursorPosition++;
/* 29:   */       }
/* 30:   */     }
/* 31:46 */     typeDone(newValue.toString(), cursorPosition);
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected boolean acceptChar(char c)
/* 35:   */   {
/* 36:55 */     return (c == ' ') || (!Character.isWhitespace(c));
/* 37:   */   }
/* 38:   */   
/* 39:   */   abstract void typeDone(String paramString, int paramInt);
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DoTypeProcessor
 * JD-Core Version:    0.7.0.1
 */