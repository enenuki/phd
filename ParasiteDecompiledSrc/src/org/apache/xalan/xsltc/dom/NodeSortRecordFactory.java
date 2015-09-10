/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.text.Collator;
/*   4:    */ import java.util.Locale;
/*   5:    */ import org.apache.xalan.xsltc.DOM;
/*   6:    */ import org.apache.xalan.xsltc.Translet;
/*   7:    */ import org.apache.xalan.xsltc.TransletException;
/*   8:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   9:    */ import org.apache.xml.utils.LocaleUtility;
/*  10:    */ 
/*  11:    */ public class NodeSortRecordFactory
/*  12:    */ {
/*  13: 34 */   private static int DESCENDING = "descending".length();
/*  14: 35 */   private static int NUMBER = "number".length();
/*  15:    */   private final DOM _dom;
/*  16:    */   private final String _className;
/*  17:    */   private Class _class;
/*  18:    */   private SortSettings _sortSettings;
/*  19:    */   protected Collator _collator;
/*  20:    */   
/*  21:    */   /**
/*  22:    */    * @deprecated
/*  23:    */    */
/*  24:    */   public NodeSortRecordFactory(DOM dom, String className, Translet translet, String[] order, String[] type)
/*  25:    */     throws TransletException
/*  26:    */   {
/*  27: 61 */     this(dom, className, translet, order, type, null, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public NodeSortRecordFactory(DOM dom, String className, Translet translet, String[] order, String[] type, String[] lang, String[] caseOrder)
/*  31:    */     throws TransletException
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 77 */       this._dom = dom;
/*  36: 78 */       this._className = className;
/*  37:    */       
/*  38: 80 */       this._class = translet.getAuxiliaryClass(className);
/*  39: 82 */       if (this._class == null) {
/*  40: 83 */         this._class = ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
/*  41:    */       }
/*  42: 87 */       int levels = order.length;
/*  43: 88 */       int[] iOrder = new int[levels];
/*  44: 89 */       int[] iType = new int[levels];
/*  45: 90 */       for (int i = 0; i < levels; i++)
/*  46:    */       {
/*  47: 91 */         if (order[i].length() == DESCENDING) {
/*  48: 92 */           iOrder[i] = 1;
/*  49:    */         }
/*  50: 94 */         if (type[i].length() == NUMBER) {
/*  51: 95 */           iType[i] = 1;
/*  52:    */         }
/*  53:    */       }
/*  54:102 */       String[] emptyStringArray = null;
/*  55:103 */       if ((lang == null) || (caseOrder == null))
/*  56:    */       {
/*  57:104 */         int numSortKeys = order.length;
/*  58:105 */         emptyStringArray = new String[numSortKeys];
/*  59:109 */         for (int i = 0; i < numSortKeys; i++) {
/*  60:110 */           emptyStringArray[i] = "";
/*  61:    */         }
/*  62:    */       }
/*  63:114 */       if (lang == null) {
/*  64:115 */         lang = emptyStringArray;
/*  65:    */       }
/*  66:117 */       if (caseOrder == null) {
/*  67:118 */         caseOrder = emptyStringArray;
/*  68:    */       }
/*  69:121 */       int length = lang.length;
/*  70:122 */       Locale[] locales = new Locale[length];
/*  71:123 */       Collator[] collators = new Collator[length];
/*  72:124 */       for (int i = 0; i < length; i++)
/*  73:    */       {
/*  74:125 */         locales[i] = LocaleUtility.langToLocale(lang[i]);
/*  75:126 */         collators[i] = Collator.getInstance(locales[i]);
/*  76:    */       }
/*  77:129 */       this._sortSettings = new SortSettings((AbstractTranslet)translet, iOrder, iType, locales, collators, caseOrder);
/*  78:    */     }
/*  79:    */     catch (ClassNotFoundException e)
/*  80:    */     {
/*  81:133 */       throw new TransletException(e);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public NodeSortRecord makeNodeSortRecord(int node, int last)
/*  86:    */     throws ExceptionInInitializerError, LinkageError, IllegalAccessException, InstantiationException, SecurityException, TransletException
/*  87:    */   {
/*  88:151 */     NodeSortRecord sortRecord = (NodeSortRecord)this._class.newInstance();
/*  89:    */     
/*  90:153 */     sortRecord.initialize(node, last, this._dom, this._sortSettings);
/*  91:154 */     return sortRecord;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getClassName()
/*  95:    */   {
/*  96:158 */     return this._className;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private final void setLang(String[] lang) {}
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.NodeSortRecordFactory
 * JD-Core Version:    0.7.0.1
 */