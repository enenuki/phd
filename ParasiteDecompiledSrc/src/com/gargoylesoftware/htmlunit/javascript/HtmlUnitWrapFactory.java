/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.WrapFactory;
/*  7:   */ import org.w3c.dom.NamedNodeMap;
/*  8:   */ import org.w3c.dom.NodeList;
/*  9:   */ 
/* 10:   */ public class HtmlUnitWrapFactory
/* 11:   */   extends WrapFactory
/* 12:   */   implements Serializable
/* 13:   */ {
/* 14:   */   public HtmlUnitWrapFactory()
/* 15:   */   {
/* 16:37 */     setJavaPrimitiveWrap(false);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Scriptable wrapAsJavaObject(Context context, Scriptable scope, Object javaObject, Class<?> staticType)
/* 20:   */   {
/* 21:   */     Scriptable resp;
/* 22:   */     Scriptable resp;
/* 23:51 */     if ((NodeList.class.equals(staticType)) || (NamedNodeMap.class.equals(staticType))) {
/* 24:53 */       resp = new ScriptableWrapper(scope, javaObject, staticType);
/* 25:   */     } else {
/* 26:56 */       resp = super.wrapAsJavaObject(context, scope, javaObject, staticType);
/* 27:   */     }
/* 28:59 */     return resp;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.HtmlUnitWrapFactory
 * JD-Core Version:    0.7.0.1
 */