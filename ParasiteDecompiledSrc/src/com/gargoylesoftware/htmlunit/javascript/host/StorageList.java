/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  6:   */ import java.net.URL;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  8:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  9:   */ 
/* 10:   */ public class StorageList
/* 11:   */   extends SimpleScriptable
/* 12:   */ {
/* 13:   */   private Storage storage_;
/* 14:   */   
/* 15:   */   public Object get(String name, Scriptable start)
/* 16:   */   {
/* 17:39 */     if (name.equals(((HtmlPage)getWindow().getWebWindow().getEnclosedPage()).getUrl().getHost()))
/* 18:   */     {
/* 19:40 */       if (this.storage_ == null)
/* 20:   */       {
/* 21:41 */         this.storage_ = new Storage();
/* 22:42 */         this.storage_.setParentScope(getParentScope());
/* 23:43 */         this.storage_.setPrototype(getPrototype(this.storage_.getClass()));
/* 24:44 */         this.storage_.setType(Storage.Type.GLOBAL_STORAGE);
/* 25:   */       }
/* 26:46 */       return this.storage_;
/* 27:   */     }
/* 28:48 */     Context.reportError("Security error: can not access the specified host");
/* 29:49 */     return null;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.StorageList
 * JD-Core Version:    0.7.0.1
 */