/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.text.Collator;
/*   4:    */ import java.util.Locale;
/*   5:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   6:    */ 
/*   7:    */ final class SortSettings
/*   8:    */ {
/*   9:    */   private AbstractTranslet _translet;
/*  10:    */   private int[] _sortOrders;
/*  11:    */   private int[] _types;
/*  12:    */   private Locale[] _locales;
/*  13:    */   private Collator[] _collators;
/*  14:    */   private String[] _caseOrders;
/*  15:    */   
/*  16:    */   SortSettings(AbstractTranslet translet, int[] sortOrders, int[] types, Locale[] locales, Collator[] collators, String[] caseOrders)
/*  17:    */   {
/*  18: 84 */     this._translet = translet;
/*  19: 85 */     this._sortOrders = sortOrders;
/*  20: 86 */     this._types = types;
/*  21: 87 */     this._locales = locales;
/*  22: 88 */     this._collators = collators;
/*  23: 89 */     this._caseOrders = caseOrders;
/*  24:    */   }
/*  25:    */   
/*  26:    */   AbstractTranslet getTranslet()
/*  27:    */   {
/*  28: 96 */     return this._translet;
/*  29:    */   }
/*  30:    */   
/*  31:    */   int[] getSortOrders()
/*  32:    */   {
/*  33:104 */     return this._sortOrders;
/*  34:    */   }
/*  35:    */   
/*  36:    */   int[] getTypes()
/*  37:    */   {
/*  38:112 */     return this._types;
/*  39:    */   }
/*  40:    */   
/*  41:    */   Locale[] getLocales()
/*  42:    */   {
/*  43:120 */     return this._locales;
/*  44:    */   }
/*  45:    */   
/*  46:    */   Collator[] getCollators()
/*  47:    */   {
/*  48:128 */     return this._collators;
/*  49:    */   }
/*  50:    */   
/*  51:    */   String[] getCaseOrders()
/*  52:    */   {
/*  53:136 */     return this._caseOrders;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SortSettings
 * JD-Core Version:    0.7.0.1
 */