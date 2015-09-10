/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.hql.internal.CollectionProperties;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ 
/*  10:    */ public class PreprocessingParser
/*  11:    */   implements Parser
/*  12:    */ {
/*  13: 43 */   private static final Set HQL_OPERATORS = new HashSet();
/*  14:    */   private Map replacements;
/*  15:    */   private boolean quoted;
/*  16:    */   private StringBuffer quotedString;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 44 */     HQL_OPERATORS.add("<=");
/*  21: 45 */     HQL_OPERATORS.add(">=");
/*  22: 46 */     HQL_OPERATORS.add("=>");
/*  23: 47 */     HQL_OPERATORS.add("=<");
/*  24: 48 */     HQL_OPERATORS.add("!=");
/*  25: 49 */     HQL_OPERATORS.add("<>");
/*  26: 50 */     HQL_OPERATORS.add("!#");
/*  27: 51 */     HQL_OPERATORS.add("!~");
/*  28: 52 */     HQL_OPERATORS.add("!<");
/*  29: 53 */     HQL_OPERATORS.add("!>");
/*  30: 54 */     HQL_OPERATORS.add("is not");
/*  31: 55 */     HQL_OPERATORS.add("not like");
/*  32: 56 */     HQL_OPERATORS.add("not in");
/*  33: 57 */     HQL_OPERATORS.add("not between");
/*  34: 58 */     HQL_OPERATORS.add("not exists");
/*  35:    */   }
/*  36:    */   
/*  37: 64 */   private ClauseParser parser = new ClauseParser();
/*  38:    */   private String lastToken;
/*  39:    */   private String currentCollectionProp;
/*  40:    */   
/*  41:    */   public PreprocessingParser(Map replacements)
/*  42:    */   {
/*  43: 69 */     this.replacements = replacements;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void token(String token, QueryTranslatorImpl q)
/*  47:    */     throws QueryException
/*  48:    */   {
/*  49: 75 */     if (this.quoted) {
/*  50: 76 */       this.quotedString.append(token);
/*  51:    */     }
/*  52: 78 */     if ("'".equals(token))
/*  53:    */     {
/*  54: 79 */       if (this.quoted) {
/*  55: 80 */         token = this.quotedString.toString();
/*  56:    */       } else {
/*  57: 83 */         this.quotedString = new StringBuffer(20).append(token);
/*  58:    */       }
/*  59: 85 */       this.quoted = (!this.quoted);
/*  60:    */     }
/*  61: 87 */     if (this.quoted) {
/*  62: 87 */       return;
/*  63:    */     }
/*  64: 90 */     if (ParserHelper.isWhitespace(token)) {
/*  65: 90 */       return;
/*  66:    */     }
/*  67: 93 */     String substoken = (String)this.replacements.get(token);
/*  68: 94 */     token = substoken == null ? token : substoken;
/*  69: 97 */     if (this.currentCollectionProp != null)
/*  70:    */     {
/*  71: 98 */       if ("(".equals(token)) {
/*  72: 99 */         return;
/*  73:    */       }
/*  74:101 */       if (")".equals(token))
/*  75:    */       {
/*  76:102 */         this.currentCollectionProp = null;
/*  77:103 */         return;
/*  78:    */       }
/*  79:106 */       token = StringHelper.qualify(token, this.currentCollectionProp);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:110 */       String prop = CollectionProperties.getNormalizedPropertyName(token.toLowerCase());
/*  84:111 */       if (prop != null)
/*  85:    */       {
/*  86:112 */         this.currentCollectionProp = prop;
/*  87:113 */         return;
/*  88:    */       }
/*  89:    */     }
/*  90:119 */     if (this.lastToken == null)
/*  91:    */     {
/*  92:120 */       this.lastToken = token;
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:123 */       String doubleToken = this.lastToken + token;
/*  97:126 */       if (HQL_OPERATORS.contains(doubleToken.toLowerCase()))
/*  98:    */       {
/*  99:127 */         this.parser.token(doubleToken, q);
/* 100:128 */         this.lastToken = null;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:131 */         this.parser.token(this.lastToken, q);
/* 105:132 */         this.lastToken = token;
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void start(QueryTranslatorImpl q)
/* 111:    */     throws QueryException
/* 112:    */   {
/* 113:139 */     this.quoted = false;
/* 114:140 */     this.parser.start(q);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void end(QueryTranslatorImpl q)
/* 118:    */     throws QueryException
/* 119:    */   {
/* 120:144 */     if (this.lastToken != null) {
/* 121:144 */       this.parser.token(this.lastToken, q);
/* 122:    */     }
/* 123:145 */     this.parser.end(q);
/* 124:146 */     this.lastToken = null;
/* 125:147 */     this.currentCollectionProp = null;
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.PreprocessingParser
 * JD-Core Version:    0.7.0.1
 */