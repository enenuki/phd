/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   5:    */ import java.util.List;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   7:    */ 
/*   8:    */ public class XPathResult
/*   9:    */   extends SimpleScriptable
/*  10:    */ {
/*  11:    */   public static final int ANY_TYPE = 0;
/*  12:    */   public static final int NUMBER_TYPE = 1;
/*  13:    */   public static final int STRING_TYPE = 2;
/*  14:    */   public static final int BOOLEAN_TYPE = 3;
/*  15:    */   public static final int UNORDERED_NODE_ITERATOR_TYPE = 4;
/*  16:    */   public static final int ORDERED_NODE_ITERATOR_TYPE = 5;
/*  17:    */   public static final int UNORDERED_NODE_SNAPSHOT_TYPE = 6;
/*  18:    */   public static final int ORDERED_NODE_SNAPSHOT_TYPE = 7;
/*  19:    */   public static final int ANY_UNORDERED_NODE_TYPE = 8;
/*  20:    */   public static final int FIRST_ORDERED_NODE_TYPE = 9;
/*  21:    */   private List<? extends Object> result_;
/*  22:    */   private int resultType_;
/*  23:    */   private int iteratorIndex_;
/*  24:    */   
/*  25:    */   void init(List<? extends Object> result, int type)
/*  26:    */   {
/*  27:104 */     this.result_ = result;
/*  28:105 */     this.resultType_ = -1;
/*  29:106 */     if (this.result_.size() == 1)
/*  30:    */     {
/*  31:107 */       Object o = this.result_.get(0);
/*  32:108 */       if ((o instanceof Number)) {
/*  33:109 */         this.resultType_ = 1;
/*  34:111 */       } else if ((o instanceof String)) {
/*  35:112 */         this.resultType_ = 2;
/*  36:114 */       } else if ((o instanceof Boolean)) {
/*  37:115 */         this.resultType_ = 3;
/*  38:    */       }
/*  39:    */     }
/*  40:119 */     if (this.resultType_ == -1) {
/*  41:120 */       if (type != 0) {
/*  42:121 */         this.resultType_ = type;
/*  43:    */       } else {
/*  44:124 */         this.resultType_ = 4;
/*  45:    */       }
/*  46:    */     }
/*  47:127 */     this.iteratorIndex_ = 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int jsxGet_resultType()
/*  51:    */   {
/*  52:135 */     return this.resultType_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int jsxGet_snapshotLength()
/*  56:    */   {
/*  57:143 */     if ((this.resultType_ != 6) && (this.resultType_ != 7)) {
/*  58:144 */       throw Context.reportRuntimeError("Cannot get snapshotLength for type: " + this.resultType_);
/*  59:    */     }
/*  60:146 */     return this.result_.size();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Node jsxGet_singleNodeValue()
/*  64:    */   {
/*  65:154 */     if ((this.resultType_ != 8) && (this.resultType_ != 9)) {
/*  66:155 */       throw Context.reportRuntimeError("Cannot get singleNodeValue for type: " + this.resultType_);
/*  67:    */     }
/*  68:157 */     if (!this.result_.isEmpty()) {
/*  69:158 */       return (Node)((DomNode)this.result_.get(0)).getScriptObject();
/*  70:    */     }
/*  71:160 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Node jsxFunction_iterateNext()
/*  75:    */   {
/*  76:168 */     if ((this.resultType_ != 4) && (this.resultType_ != 5)) {
/*  77:169 */       throw Context.reportRuntimeError("Cannot get iterateNext for type: " + this.resultType_);
/*  78:    */     }
/*  79:171 */     if (this.iteratorIndex_ < this.result_.size()) {
/*  80:172 */       return (Node)((DomNode)this.result_.get(this.iteratorIndex_++)).getScriptObject();
/*  81:    */     }
/*  82:174 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Node jsxFunction_snapshotItem(int index)
/*  86:    */   {
/*  87:184 */     if ((this.resultType_ != 6) && (this.resultType_ != 7)) {
/*  88:185 */       throw Context.reportRuntimeError("Cannot get snapshotLength for type: " + this.resultType_);
/*  89:    */     }
/*  90:187 */     if ((index >= 0) && (index < this.result_.size())) {
/*  91:188 */       return (Node)((DomNode)this.result_.get(index)).getScriptObject();
/*  92:    */     }
/*  93:190 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public double jsxGet_numberValue()
/*  97:    */   {
/*  98:198 */     if (this.resultType_ != 1) {
/*  99:199 */       throw Context.reportRuntimeError("Cannot get numberValue for type: " + this.resultType_);
/* 100:    */     }
/* 101:201 */     return ((Number)this.result_.get(0)).doubleValue();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean jsxGet_booleanValue()
/* 105:    */   {
/* 106:209 */     if (this.resultType_ != 3) {
/* 107:210 */       throw Context.reportRuntimeError("Cannot get booleanValue for type: " + this.resultType_);
/* 108:    */     }
/* 109:212 */     return ((Boolean)this.result_.get(0)).booleanValue();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String jsxGet_stringValue()
/* 113:    */   {
/* 114:220 */     if (this.resultType_ != 2) {
/* 115:221 */       throw Context.reportRuntimeError("Cannot get stringValue for type: " + this.resultType_);
/* 116:    */     }
/* 117:223 */     return (String)this.result_.get(0);
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.XPathResult
 * JD-Core Version:    0.7.0.1
 */