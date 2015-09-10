/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  8:   */ 
/*  9:   */ public class StaticNodeList
/* 10:   */   extends SimpleScriptable
/* 11:   */ {
/* 12:   */   private final List<Node> elements_;
/* 13:   */   
/* 14:   */   public StaticNodeList()
/* 15:   */   {
/* 16:39 */     this.elements_ = new ArrayList();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public StaticNodeList(List<Node> elements, ScriptableObject parentScope)
/* 20:   */   {
/* 21:48 */     this.elements_ = elements;
/* 22:49 */     setParentScope(parentScope);
/* 23:50 */     setPrototype(getPrototype(getClass()));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object get(int index, Scriptable start)
/* 27:   */   {
/* 28:55 */     StaticNodeList staticNodeList = (StaticNodeList)start;
/* 29:56 */     Object result = staticNodeList.jsxFunction_item(index);
/* 30:57 */     if (null == result) {
/* 31:58 */       return NOT_FOUND;
/* 32:   */     }
/* 33:60 */     return result;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Node jsxFunction_item(int index)
/* 37:   */   {
/* 38:69 */     if ((index < 0) || (index >= jsxGet_length())) {
/* 39:70 */       return null;
/* 40:   */     }
/* 41:72 */     return (Node)this.elements_.get(index);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int jsxGet_length()
/* 45:   */   {
/* 46:80 */     return this.elements_.size();
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.StaticNodeList
 * JD-Core Version:    0.7.0.1
 */