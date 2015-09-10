/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlApplet;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   8:    */ import java.applet.Applet;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  15:    */ 
/*  16:    */ public class HTMLAppletElement
/*  17:    */   extends HTMLElement
/*  18:    */ {
/*  19:    */   public void setDomNode(DomNode domNode)
/*  20:    */   {
/*  21: 51 */     super.setDomNode(domNode);
/*  22: 53 */     if (domNode.getPage().getWebClient().isAppletEnabled()) {
/*  23:    */       try
/*  24:    */       {
/*  25: 55 */         createAppletMethodAndProperties();
/*  26:    */       }
/*  27:    */       catch (Exception e)
/*  28:    */       {
/*  29: 58 */         throw new RuntimeException(e);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void createAppletMethodAndProperties()
/*  35:    */     throws Exception
/*  36:    */   {
/*  37: 64 */     HtmlApplet appletNode = (HtmlApplet)getDomNodeOrDie();
/*  38: 65 */     final Applet applet = appletNode.getApplet();
/*  39: 66 */     if (applet == null) {
/*  40: 67 */       return;
/*  41:    */     }
/*  42: 71 */     for (final Method method : applet.getClass().getMethods())
/*  43:    */     {
/*  44: 72 */       Function f = new BaseFunction()
/*  45:    */       {
/*  46:    */         public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  47:    */         {
/*  48: 77 */           Object[] realArgs = new Object[method.getParameterTypes().length];
/*  49: 78 */           for (int i = 0; i < realArgs.length; i++)
/*  50:    */           {
/*  51:    */             Object arg;
/*  52:    */             Object arg;
/*  53: 80 */             if (i > args.length) {
/*  54: 81 */               arg = null;
/*  55:    */             } else {
/*  56: 84 */               arg = Context.jsToJava(args[i], method.getParameterTypes()[i]);
/*  57:    */             }
/*  58: 86 */             realArgs[i] = arg;
/*  59:    */           }
/*  60:    */           try
/*  61:    */           {
/*  62: 89 */             return method.invoke(applet, realArgs);
/*  63:    */           }
/*  64:    */           catch (Exception e)
/*  65:    */           {
/*  66: 92 */             throw Context.throwAsScriptRuntimeEx(e);
/*  67:    */           }
/*  68:    */         }
/*  69: 95 */       };
/*  70: 96 */       ScriptableObject.defineProperty(this, method.getName(), f, 1);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String jsxGet_alt()
/*  75:    */   {
/*  76:105 */     String alt = getDomNodeOrDie().getAttribute("alt");
/*  77:106 */     return alt;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void jsxSet_alt(String alt)
/*  81:    */   {
/*  82:114 */     getDomNodeOrDie().setAttribute("alt", alt);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String jsxGet_border()
/*  86:    */   {
/*  87:122 */     String border = getDomNodeOrDie().getAttribute("border");
/*  88:123 */     return border;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void jsxSet_border(String border)
/*  92:    */   {
/*  93:131 */     getDomNodeOrDie().setAttribute("border", border);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String jsxGet_align()
/*  97:    */   {
/*  98:139 */     return getAlign(true);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void jsxSet_align(String align)
/* 102:    */   {
/* 103:147 */     setAlign(align, false);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAppletElement
 * JD-Core Version:    0.7.0.1
 */