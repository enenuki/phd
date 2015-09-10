/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ public class Parameter
/*  4:   */ {
/*  5:   */   public String _name;
/*  6:   */   public Object _value;
/*  7:   */   public boolean _isDefault;
/*  8:   */   
/*  9:   */   public Parameter(String name, Object value)
/* 10:   */   {
/* 11:36 */     this._name = name;
/* 12:37 */     this._value = value;
/* 13:38 */     this._isDefault = true;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Parameter(String name, Object value, boolean isDefault)
/* 17:   */   {
/* 18:42 */     this._name = name;
/* 19:43 */     this._value = value;
/* 20:44 */     this._isDefault = isDefault;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.Parameter
 * JD-Core Version:    0.7.0.1
 */