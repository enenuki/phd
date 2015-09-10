/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFormElement;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  7:   */ 
/*  8:   */ public class Enumerator
/*  9:   */   extends SimpleScriptable
/* 10:   */ {
/* 11:   */   private int index_;
/* 12:   */   private HTMLCollection collection_;
/* 13:   */   
/* 14:   */   public void jsConstructor(Object o)
/* 15:   */   {
/* 16:48 */     if ((o instanceof HTMLCollection)) {
/* 17:49 */       this.collection_ = ((HTMLCollection)o);
/* 18:51 */     } else if ((o instanceof HTMLFormElement)) {
/* 19:52 */       this.collection_ = ((HTMLFormElement)o).jsxGet_elements();
/* 20:   */     } else {
/* 21:55 */       throw new IllegalArgumentException(String.valueOf(o));
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean jsxFunction_atEnd()
/* 26:   */   {
/* 27:64 */     return this.index_ >= this.collection_.getLength();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object jsxFunction_item()
/* 31:   */   {
/* 32:72 */     if (!jsxFunction_atEnd())
/* 33:   */     {
/* 34:73 */       SimpleScriptable scriptable = (SimpleScriptable)this.collection_.get(this.index_, this.collection_);
/* 35:74 */       scriptable = scriptable.clone();
/* 36:75 */       scriptable.setCaseSensitive(false);
/* 37:76 */       return scriptable;
/* 38:   */     }
/* 39:78 */     return Undefined.instance;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void jsxFunction_moveFirst()
/* 43:   */   {
/* 44:85 */     this.index_ = 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void jsxFunction_moveNext()
/* 48:   */   {
/* 49:92 */     this.index_ += 1;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Enumerator
 * JD-Core Version:    0.7.0.1
 */