/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   7:    */ import java.io.IOException;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  10:    */ 
/*  11:    */ public class History
/*  12:    */   extends SimpleScriptable
/*  13:    */ {
/*  14:    */   public Object[] getIds()
/*  15:    */   {
/*  16: 48 */     Object[] ids = super.getIds();
/*  17: 49 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_156))
/*  18:    */     {
/*  19: 50 */       int len = getWindow().getWebWindow().getHistory().getLength();
/*  20: 51 */       if (len > 0)
/*  21:    */       {
/*  22: 52 */         Object[] allIds = new Object[ids.length + len];
/*  23: 53 */         System.arraycopy(ids, 0, allIds, 0, ids.length);
/*  24: 54 */         for (int i = 0; i < len; i++) {
/*  25: 55 */           allIds[(ids.length + i)] = Integer.valueOf(i);
/*  26:    */         }
/*  27: 57 */         ids = allIds;
/*  28:    */       }
/*  29:    */     }
/*  30: 60 */     return ids;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean has(int index, Scriptable start)
/*  34:    */   {
/*  35: 68 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_157))
/*  36:    */     {
/*  37: 69 */       History h = (History)start;
/*  38: 70 */       if ((index >= 0) && (index < h.jsxGet_length())) {
/*  39: 71 */         return true;
/*  40:    */       }
/*  41:    */     }
/*  42: 74 */     return super.has(index, start);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Object get(int index, Scriptable start)
/*  46:    */   {
/*  47: 82 */     History h = (History)start;
/*  48: 83 */     if ((index < 0) || (index >= h.jsxGet_length())) {
/*  49: 84 */       return NOT_FOUND;
/*  50:    */     }
/*  51: 86 */     return jsxFunction_item(index);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int jsxGet_length()
/*  55:    */   {
/*  56: 94 */     WebWindow w = getWindow().getWebWindow();
/*  57: 95 */     return w.getHistory().getLength();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void jsxFunction_back()
/*  61:    */   {
/*  62:102 */     WebWindow w = getWindow().getWebWindow();
/*  63:    */     try
/*  64:    */     {
/*  65:104 */       w.getHistory().back();
/*  66:    */     }
/*  67:    */     catch (IOException e)
/*  68:    */     {
/*  69:107 */       Context.throwAsScriptRuntimeEx(e);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void jsxFunction_forward()
/*  74:    */   {
/*  75:115 */     WebWindow w = getWindow().getWebWindow();
/*  76:    */     try
/*  77:    */     {
/*  78:117 */       w.getHistory().forward();
/*  79:    */     }
/*  80:    */     catch (IOException e)
/*  81:    */     {
/*  82:120 */       Context.throwAsScriptRuntimeEx(e);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void jsxFunction_go(int relativeIndex)
/*  87:    */   {
/*  88:129 */     WebWindow w = getWindow().getWebWindow();
/*  89:    */     try
/*  90:    */     {
/*  91:131 */       w.getHistory().go(relativeIndex);
/*  92:    */     }
/*  93:    */     catch (IOException e)
/*  94:    */     {
/*  95:134 */       Context.throwAsScriptRuntimeEx(e);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String jsxGet_current()
/* 100:    */   {
/* 101:143 */     throw Context.reportRuntimeError("Permission denied to get property History.current");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String jsxGet_previous()
/* 105:    */   {
/* 106:151 */     throw Context.reportRuntimeError("Permission denied to get property History.previous");
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String jsxGet_next()
/* 110:    */   {
/* 111:159 */     throw Context.reportRuntimeError("Permission denied to get property History.next");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String jsxFunction_item(int index)
/* 115:    */   {
/* 116:168 */     throw Context.reportRuntimeError("Permission denied to call method History.item");
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.History
 * JD-Core Version:    0.7.0.1
 */