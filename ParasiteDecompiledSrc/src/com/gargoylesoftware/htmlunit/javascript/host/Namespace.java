/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  5:   */ 
/*  6:   */ public class Namespace
/*  7:   */   extends SimpleScriptable
/*  8:   */ {
/*  9:   */   private String name_;
/* 10:   */   private String urn_;
/* 11:   */   
/* 12:   */   @Deprecated
/* 13:   */   public Namespace() {}
/* 14:   */   
/* 15:   */   public Namespace(ScriptableObject parentScope, String name, String urn)
/* 16:   */   {
/* 17:50 */     setParentScope(parentScope);
/* 18:51 */     setPrototype(getPrototype(getClass()));
/* 19:52 */     this.name_ = name;
/* 20:53 */     this.urn_ = urn;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String jsxGet_name()
/* 24:   */   {
/* 25:61 */     return this.name_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String jsxGet_urn()
/* 29:   */   {
/* 30:69 */     return this.urn_;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void jsxSet_urn(String urn)
/* 34:   */   {
/* 35:77 */     this.urn_ = urn;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Namespace
 * JD-Core Version:    0.7.0.1
 */