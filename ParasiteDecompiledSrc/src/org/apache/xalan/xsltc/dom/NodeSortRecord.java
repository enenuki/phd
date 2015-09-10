/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.text.Collator;
/*   4:    */ import java.util.Locale;
/*   5:    */ import org.apache.xalan.xsltc.CollatorFactory;
/*   6:    */ import org.apache.xalan.xsltc.DOM;
/*   7:    */ import org.apache.xalan.xsltc.TransletException;
/*   8:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   9:    */ import org.apache.xml.utils.StringComparable;
/*  10:    */ 
/*  11:    */ public abstract class NodeSortRecord
/*  12:    */ {
/*  13:    */   public static final int COMPARE_STRING = 0;
/*  14:    */   public static final int COMPARE_NUMERIC = 1;
/*  15:    */   public static final int COMPARE_ASCENDING = 0;
/*  16:    */   public static final int COMPARE_DESCENDING = 1;
/*  17:    */   /**
/*  18:    */    * @deprecated
/*  19:    */    */
/*  20: 50 */   private static final Collator DEFAULT_COLLATOR = ;
/*  21:    */   /**
/*  22:    */    * @deprecated
/*  23:    */    */
/*  24: 57 */   protected Collator _collator = DEFAULT_COLLATOR;
/*  25:    */   protected Collator[] _collators;
/*  26:    */   /**
/*  27:    */    * @deprecated
/*  28:    */    */
/*  29:    */   protected Locale _locale;
/*  30:    */   protected CollatorFactory _collatorFactory;
/*  31:    */   protected SortSettings _settings;
/*  32: 71 */   private DOM _dom = null;
/*  33:    */   private int _node;
/*  34: 73 */   private int _last = 0;
/*  35: 74 */   private int _scanned = 0;
/*  36:    */   private Object[] _values;
/*  37:    */   
/*  38:    */   public NodeSortRecord(int node)
/*  39:    */   {
/*  40: 85 */     this._node = node;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public NodeSortRecord()
/*  44:    */   {
/*  45: 89 */     this(0);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void initialize(int node, int last, DOM dom, SortSettings settings)
/*  49:    */     throws TransletException
/*  50:    */   {
/*  51:100 */     this._dom = dom;
/*  52:101 */     this._node = node;
/*  53:102 */     this._last = last;
/*  54:103 */     this._settings = settings;
/*  55:    */     
/*  56:105 */     int levels = settings.getSortOrders().length;
/*  57:106 */     this._values = new Object[levels];
/*  58:    */     
/*  59:    */ 
/*  60:109 */     String colFactClassname = System.getProperty("org.apache.xalan.xsltc.COLLATOR_FACTORY");
/*  61:112 */     if (colFactClassname != null)
/*  62:    */     {
/*  63:    */       try
/*  64:    */       {
/*  65:114 */         Object candObj = ObjectFactory.findProviderClass(colFactClassname, ObjectFactory.findClassLoader(), true);
/*  66:    */         
/*  67:116 */         this._collatorFactory = ((CollatorFactory)candObj);
/*  68:    */       }
/*  69:    */       catch (ClassNotFoundException e)
/*  70:    */       {
/*  71:118 */         throw new TransletException(e);
/*  72:    */       }
/*  73:120 */       Locale[] locales = settings.getLocales();
/*  74:121 */       this._collators = new Collator[levels];
/*  75:122 */       for (int i = 0; i < levels; i++) {
/*  76:123 */         this._collators[i] = this._collatorFactory.getCollator(locales[i]);
/*  77:    */       }
/*  78:125 */       this._collator = this._collators[0];
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:127 */       this._collators = settings.getCollators();
/*  83:128 */       this._collator = this._collators[0];
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final int getNode()
/*  88:    */   {
/*  89:136 */     return this._node;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final int compareDocOrder(NodeSortRecord other)
/*  93:    */   {
/*  94:143 */     return this._node - other._node;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private final Comparable stringValue(int level)
/*  98:    */   {
/*  99:153 */     if (this._scanned <= level)
/* 100:    */     {
/* 101:154 */       AbstractTranslet translet = this._settings.getTranslet();
/* 102:155 */       Locale[] locales = this._settings.getLocales();
/* 103:156 */       String[] caseOrder = this._settings.getCaseOrders();
/* 104:    */       
/* 105:    */ 
/* 106:159 */       String str = extractValueFromDOM(this._dom, this._node, level, translet, this._last);
/* 107:    */       
/* 108:161 */       Comparable key = StringComparable.getComparator(str, locales[level], this._collators[level], caseOrder[level]);
/* 109:    */       
/* 110:    */ 
/* 111:    */ 
/* 112:165 */       this._values[(this._scanned++)] = key;
/* 113:166 */       return key;
/* 114:    */     }
/* 115:168 */     return (Comparable)this._values[level];
/* 116:    */   }
/* 117:    */   
/* 118:    */   private final Double numericValue(int level)
/* 119:    */   {
/* 120:173 */     if (this._scanned <= level)
/* 121:    */     {
/* 122:174 */       AbstractTranslet translet = this._settings.getTranslet();
/* 123:    */       
/* 124:    */ 
/* 125:177 */       String str = extractValueFromDOM(this._dom, this._node, level, translet, this._last);
/* 126:    */       Double num;
/* 127:    */       try
/* 128:    */       {
/* 129:181 */         num = new Double(str);
/* 130:    */       }
/* 131:    */       catch (NumberFormatException e)
/* 132:    */       {
/* 133:185 */         num = new Double((-1.0D / 0.0D));
/* 134:    */       }
/* 135:187 */       this._values[(this._scanned++)] = num;
/* 136:188 */       return num;
/* 137:    */     }
/* 138:190 */     return (Double)this._values[level];
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int compareTo(NodeSortRecord other)
/* 142:    */   {
/* 143:202 */     int[] sortOrder = this._settings.getSortOrders();
/* 144:203 */     int levels = this._settings.getSortOrders().length;
/* 145:204 */     int[] compareTypes = this._settings.getTypes();
/* 146:206 */     for (int level = 0; level < levels; level++)
/* 147:    */     {
/* 148:    */       int cmp;
/* 149:208 */       if (compareTypes[level] == 1)
/* 150:    */       {
/* 151:209 */         Double our = numericValue(level);
/* 152:210 */         Double their = other.numericValue(level);
/* 153:211 */         cmp = our.compareTo(their);
/* 154:    */       }
/* 155:    */       else
/* 156:    */       {
/* 157:214 */         Comparable our = stringValue(level);
/* 158:215 */         Comparable their = other.stringValue(level);
/* 159:216 */         cmp = our.compareTo(their);
/* 160:    */       }
/* 161:220 */       if (cmp != 0) {
/* 162:221 */         return sortOrder[level] == 1 ? 0 - cmp : cmp;
/* 163:    */       }
/* 164:    */     }
/* 165:225 */     return this._node - other._node;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Collator[] getCollator()
/* 169:    */   {
/* 170:233 */     return this._collators;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public abstract String extractValueFromDOM(DOM paramDOM, int paramInt1, int paramInt2, AbstractTranslet paramAbstractTranslet, int paramInt3);
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.NodeSortRecord
 * JD-Core Version:    0.7.0.1
 */