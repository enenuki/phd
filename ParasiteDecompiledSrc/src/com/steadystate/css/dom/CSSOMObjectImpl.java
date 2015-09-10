/*  1:   */ package com.steadystate.css.dom;
/*  2:   */ 
/*  3:   */ import java.util.Hashtable;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class CSSOMObjectImpl
/*  7:   */   implements CSSOMObject
/*  8:   */ {
/*  9:   */   private Map userDataMap;
/* 10:   */   
/* 11:   */   public Map getUserDataMap()
/* 12:   */   {
/* 13:22 */     if (this.userDataMap == null) {
/* 14:24 */       this.userDataMap = new Hashtable();
/* 15:   */     }
/* 16:26 */     return this.userDataMap;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setUserDataMap(Map userDataMap)
/* 20:   */   {
/* 21:31 */     this.userDataMap = userDataMap;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object getUserData(String key)
/* 25:   */   {
/* 26:43 */     return getUserDataMap().get(key);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object setUserData(String key, Object data)
/* 30:   */   {
/* 31:48 */     return getUserDataMap().put(key, data);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSOMObjectImpl
 * JD-Core Version:    0.7.0.1
 */