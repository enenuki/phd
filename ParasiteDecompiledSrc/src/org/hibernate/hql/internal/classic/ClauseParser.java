/*   1:    */ package org.hibernate.hql.internal.classic;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ 
/*   8:    */ public class ClauseParser
/*   9:    */   implements Parser
/*  10:    */ {
/*  11:    */   private Parser child;
/*  12:    */   private List selectTokens;
/*  13: 39 */   private boolean cacheSelectTokens = false;
/*  14: 40 */   private boolean byExpected = false;
/*  15: 41 */   private int parenCount = 0;
/*  16:    */   
/*  17:    */   public void token(String token, QueryTranslatorImpl q)
/*  18:    */     throws QueryException
/*  19:    */   {
/*  20: 44 */     String lcToken = token.toLowerCase();
/*  21: 46 */     if ("(".equals(token)) {
/*  22: 47 */       this.parenCount += 1;
/*  23: 49 */     } else if (")".equals(token)) {
/*  24: 50 */       this.parenCount -= 1;
/*  25:    */     }
/*  26: 53 */     if ((this.byExpected) && (!lcToken.equals("by"))) {
/*  27: 54 */       throw new QueryException("BY expected after GROUP or ORDER: " + token);
/*  28:    */     }
/*  29: 57 */     boolean isClauseStart = this.parenCount == 0;
/*  30: 59 */     if (isClauseStart) {
/*  31: 60 */       if (lcToken.equals("select"))
/*  32:    */       {
/*  33: 61 */         this.selectTokens = new ArrayList();
/*  34: 62 */         this.cacheSelectTokens = true;
/*  35:    */       }
/*  36: 64 */       else if (lcToken.equals("from"))
/*  37:    */       {
/*  38: 65 */         this.child = new FromParser();
/*  39: 66 */         this.child.start(q);
/*  40: 67 */         this.cacheSelectTokens = false;
/*  41:    */       }
/*  42: 69 */       else if (lcToken.equals("where"))
/*  43:    */       {
/*  44: 70 */         endChild(q);
/*  45: 71 */         this.child = new WhereParser();
/*  46: 72 */         this.child.start(q);
/*  47:    */       }
/*  48: 74 */       else if (lcToken.equals("order"))
/*  49:    */       {
/*  50: 75 */         endChild(q);
/*  51: 76 */         this.child = new OrderByParser();
/*  52: 77 */         this.byExpected = true;
/*  53:    */       }
/*  54: 79 */       else if (lcToken.equals("having"))
/*  55:    */       {
/*  56: 80 */         endChild(q);
/*  57: 81 */         this.child = new HavingParser();
/*  58: 82 */         this.child.start(q);
/*  59:    */       }
/*  60: 84 */       else if (lcToken.equals("group"))
/*  61:    */       {
/*  62: 85 */         endChild(q);
/*  63: 86 */         this.child = new GroupByParser();
/*  64: 87 */         this.byExpected = true;
/*  65:    */       }
/*  66: 89 */       else if (lcToken.equals("by"))
/*  67:    */       {
/*  68: 90 */         if (!this.byExpected) {
/*  69: 90 */           throw new QueryException("GROUP or ORDER expected before BY");
/*  70:    */         }
/*  71: 91 */         this.child.start(q);
/*  72: 92 */         this.byExpected = false;
/*  73:    */       }
/*  74:    */       else
/*  75:    */       {
/*  76: 95 */         isClauseStart = false;
/*  77:    */       }
/*  78:    */     }
/*  79: 99 */     if (!isClauseStart) {
/*  80:100 */       if (this.cacheSelectTokens)
/*  81:    */       {
/*  82:101 */         this.selectTokens.add(token);
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:104 */         if (this.child == null) {
/*  87:105 */           throw new QueryException("query must begin with SELECT or FROM: " + token);
/*  88:    */         }
/*  89:108 */         this.child.token(token, q);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void endChild(QueryTranslatorImpl q)
/*  95:    */     throws QueryException
/*  96:    */   {
/*  97:116 */     if (this.child == null) {
/*  98:118 */       this.cacheSelectTokens = false;
/*  99:    */     } else {
/* 100:121 */       this.child.end(q);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void start(QueryTranslatorImpl q) {}
/* 105:    */   
/* 106:    */   public void end(QueryTranslatorImpl q)
/* 107:    */     throws QueryException
/* 108:    */   {
/* 109:129 */     endChild(q);
/* 110:130 */     if (this.selectTokens != null)
/* 111:    */     {
/* 112:131 */       this.child = new SelectParser();
/* 113:132 */       this.child.start(q);
/* 114:133 */       Iterator iter = this.selectTokens.iterator();
/* 115:134 */       while (iter.hasNext()) {
/* 116:135 */         token((String)iter.next(), q);
/* 117:    */       }
/* 118:137 */       this.child.end(q);
/* 119:    */     }
/* 120:139 */     this.byExpected = false;
/* 121:140 */     this.parenCount = 0;
/* 122:141 */     this.cacheSelectTokens = false;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.ClauseParser
 * JD-Core Version:    0.7.0.1
 */