/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import org.apache.commons.logging.Log;
/*   4:    */ import org.apache.commons.logging.LogFactory;
/*   5:    */ import org.w3c.css.sac.AttributeCondition;
/*   6:    */ import org.w3c.css.sac.CombinatorCondition;
/*   7:    */ import org.w3c.css.sac.Condition;
/*   8:    */ import org.w3c.css.sac.ConditionalSelector;
/*   9:    */ import org.w3c.css.sac.DescendantSelector;
/*  10:    */ import org.w3c.css.sac.ElementSelector;
/*  11:    */ import org.w3c.css.sac.Selector;
/*  12:    */ import org.w3c.css.sac.SiblingSelector;
/*  13:    */ 
/*  14:    */ class SelectorSpecificity
/*  15:    */   implements Comparable<SelectorSpecificity>
/*  16:    */ {
/*  17: 35 */   private static final Log LOG = LogFactory.getLog(SelectorSpecificity.class);
/*  18: 39 */   public static final SelectorSpecificity FROM_STYLE_ATTRIBUTE = new SelectorSpecificity(1, 0, 0, 0);
/*  19:    */   private int fieldA_;
/*  20:    */   private int fieldB_;
/*  21:    */   private int fieldC_;
/*  22:    */   private int fieldD_;
/*  23:    */   
/*  24:    */   SelectorSpecificity(Selector selector)
/*  25:    */   {
/*  26: 47 */     readSelectorSpecificity(selector);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private SelectorSpecificity(int a, int b, int c, int d)
/*  30:    */   {
/*  31: 51 */     this.fieldA_ = a;
/*  32: 52 */     this.fieldB_ = b;
/*  33: 53 */     this.fieldC_ = c;
/*  34: 54 */     this.fieldD_ = d;
/*  35:    */   }
/*  36:    */   
/*  37:    */   void readSelectorSpecificity(Selector selector)
/*  38:    */   {
/*  39: 58 */     switch (selector.getSelectorType())
/*  40:    */     {
/*  41:    */     case 1: 
/*  42: 60 */       return;
/*  43:    */     case 10: 
/*  44: 62 */       DescendantSelector ds = (DescendantSelector)selector;
/*  45: 63 */       readSelectorSpecificity(ds.getAncestorSelector());
/*  46: 64 */       readSelectorSpecificity(ds.getSimpleSelector());
/*  47: 65 */       return;
/*  48:    */     case 11: 
/*  49: 67 */       DescendantSelector cs = (DescendantSelector)selector;
/*  50: 68 */       readSelectorSpecificity(cs.getAncestorSelector());
/*  51: 69 */       readSelectorSpecificity(cs.getSimpleSelector());
/*  52: 70 */       return;
/*  53:    */     case 0: 
/*  54: 72 */       ConditionalSelector conditional = (ConditionalSelector)selector;
/*  55: 73 */       Condition condition = conditional.getCondition();
/*  56: 74 */       readSelectorSpecificity(conditional.getSimpleSelector());
/*  57: 75 */       readSelectorSpecificity(condition);
/*  58: 76 */       return;
/*  59:    */     case 4: 
/*  60: 78 */       ElementSelector es = (ElementSelector)selector;
/*  61: 79 */       String esName = es.getLocalName();
/*  62: 80 */       if (esName != null) {
/*  63: 81 */         this.fieldD_ += 1;
/*  64:    */       }
/*  65: 83 */       return;
/*  66:    */     case 9: 
/*  67: 85 */       ElementSelector pes = (ElementSelector)selector;
/*  68: 86 */       String pesName = pes.getLocalName();
/*  69: 87 */       if (pesName != null) {
/*  70: 88 */         this.fieldD_ += 1;
/*  71:    */       }
/*  72: 90 */       return;
/*  73:    */     case 12: 
/*  74: 92 */       SiblingSelector ss = (SiblingSelector)selector;
/*  75: 93 */       readSelectorSpecificity(ss.getSelector());
/*  76: 94 */       readSelectorSpecificity(ss.getSiblingSelector());
/*  77: 95 */       return;
/*  78:    */     }
/*  79: 97 */     LOG.warn("Unhandled CSS selector type for specificity computation: '" + selector.getSelectorType() + "'.");
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void readSelectorSpecificity(Condition condition)
/*  83:    */   {
/*  84:104 */     switch (condition.getConditionType())
/*  85:    */     {
/*  86:    */     case 5: 
/*  87:106 */       this.fieldB_ += 1;
/*  88:107 */       return;
/*  89:    */     case 9: 
/*  90:109 */       this.fieldC_ += 1;
/*  91:110 */       return;
/*  92:    */     case 0: 
/*  93:112 */       CombinatorCondition cc1 = (CombinatorCondition)condition;
/*  94:113 */       readSelectorSpecificity(cc1.getFirstCondition());
/*  95:114 */       readSelectorSpecificity(cc1.getSecondCondition());
/*  96:115 */       return;
/*  97:    */     case 4: 
/*  98:117 */       AttributeCondition ac1 = (AttributeCondition)condition;
/*  99:118 */       if ("id".equalsIgnoreCase(ac1.getLocalName())) {
/* 100:119 */         this.fieldB_ += 1;
/* 101:    */       } else {
/* 102:122 */         this.fieldC_ += 1;
/* 103:    */       }
/* 104:124 */       return;
/* 105:    */     case 10: 
/* 106:126 */       this.fieldD_ += 1;
/* 107:127 */       return;
/* 108:    */     }
/* 109:129 */     LOG.warn("Unhandled CSS condition type for specifity computation: '" + condition.getConditionType() + "'.");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String toString()
/* 113:    */   {
/* 114:140 */     return this.fieldA_ + "," + this.fieldB_ + "," + this.fieldC_ + "," + this.fieldD_;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int compareTo(SelectorSpecificity other)
/* 118:    */   {
/* 119:147 */     if (this.fieldA_ != other.fieldA_) {
/* 120:148 */       return this.fieldA_ - other.fieldA_;
/* 121:    */     }
/* 122:150 */     if (this.fieldB_ != other.fieldB_) {
/* 123:151 */       return this.fieldB_ - other.fieldB_;
/* 124:    */     }
/* 125:153 */     if (this.fieldC_ != other.fieldC_) {
/* 126:154 */       return this.fieldC_ - other.fieldC_;
/* 127:    */     }
/* 128:156 */     if (this.fieldD_ != other.fieldD_) {
/* 129:157 */       return this.fieldD_ - other.fieldD_;
/* 130:    */     }
/* 131:159 */     return 0;
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.SelectorSpecificity
 * JD-Core Version:    0.7.0.1
 */