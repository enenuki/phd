/*   1:    */ package org.hibernate.hql.internal;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.hql.internal.classic.ParserHelper;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ import org.jboss.logging.Logger;
/*  12:    */ 
/*  13:    */ public final class QuerySplitter
/*  14:    */ {
/*  15: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QuerySplitter.class.getName());
/*  16: 50 */   private static final Set BEFORE_CLASS_TOKENS = new HashSet();
/*  17: 51 */   private static final Set NOT_AFTER_CLASS_TOKENS = new HashSet();
/*  18:    */   
/*  19:    */   static
/*  20:    */   {
/*  21: 54 */     BEFORE_CLASS_TOKENS.add("from");
/*  22: 55 */     BEFORE_CLASS_TOKENS.add("delete");
/*  23: 56 */     BEFORE_CLASS_TOKENS.add("update");
/*  24:    */     
/*  25: 58 */     BEFORE_CLASS_TOKENS.add(",");
/*  26: 59 */     NOT_AFTER_CLASS_TOKENS.add("in");
/*  27:    */     
/*  28: 61 */     NOT_AFTER_CLASS_TOKENS.add("from");
/*  29: 62 */     NOT_AFTER_CLASS_TOKENS.add(")");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static String[] concreteQueries(String query, SessionFactoryImplementor factory)
/*  33:    */     throws MappingException
/*  34:    */   {
/*  35: 84 */     String[] tokens = StringHelper.split(" \n\r\f\t(),", query, true);
/*  36: 85 */     if (tokens.length == 0) {
/*  37: 85 */       return new String[] { query };
/*  38:    */     }
/*  39: 86 */     ArrayList placeholders = new ArrayList();
/*  40: 87 */     ArrayList replacements = new ArrayList();
/*  41: 88 */     StringBuffer templateQuery = new StringBuffer(40);
/*  42:    */     
/*  43: 90 */     int start = getStartingPositionFor(tokens, templateQuery);
/*  44: 91 */     int count = 0;
/*  45: 92 */     String next = null;
/*  46: 93 */     String last = tokens[(start - 1)].toLowerCase();
/*  47: 95 */     for (int i = start; i < tokens.length; i++)
/*  48:    */     {
/*  49: 97 */       String token = tokens[i];
/*  50: 99 */       if (ParserHelper.isWhitespace(token))
/*  51:    */       {
/*  52:100 */         templateQuery.append(token);
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56:104 */         next = nextNonWhite(tokens, i).toLowerCase();
/*  57:    */         
/*  58:106 */         boolean process = (isJavaIdentifier(token)) && (isPossiblyClassName(last, next));
/*  59:    */         
/*  60:    */ 
/*  61:109 */         last = token.toLowerCase();
/*  62:111 */         if (process)
/*  63:    */         {
/*  64:112 */           String importedClassName = getImportedClass(token, factory);
/*  65:113 */           if (importedClassName != null)
/*  66:    */           {
/*  67:114 */             String[] implementors = factory.getImplementors(importedClassName);
/*  68:115 */             token = "$clazz" + count++ + "$";
/*  69:116 */             if (implementors != null)
/*  70:    */             {
/*  71:117 */               placeholders.add(token);
/*  72:118 */               replacements.add(implementors);
/*  73:    */             }
/*  74:    */           }
/*  75:    */         }
/*  76:123 */         templateQuery.append(token);
/*  77:    */       }
/*  78:    */     }
/*  79:126 */     String[] results = StringHelper.multiply(templateQuery.toString(), placeholders.iterator(), replacements.iterator());
/*  80:127 */     if (results.length == 0) {
/*  81:128 */       LOG.noPersistentClassesFound(query);
/*  82:    */     }
/*  83:130 */     return results;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private static String nextNonWhite(String[] tokens, int start)
/*  87:    */   {
/*  88:134 */     for (int i = start + 1; i < tokens.length; i++) {
/*  89:135 */       if (!ParserHelper.isWhitespace(tokens[i])) {
/*  90:135 */         return tokens[i];
/*  91:    */       }
/*  92:    */     }
/*  93:137 */     return tokens[(tokens.length - 1)];
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static int getStartingPositionFor(String[] tokens, StringBuffer templateQuery)
/*  97:    */   {
/*  98:141 */     templateQuery.append(tokens[0]);
/*  99:142 */     if (!"select".equals(tokens[0].toLowerCase())) {
/* 100:142 */       return 1;
/* 101:    */     }
/* 102:145 */     for (int i = 1; i < tokens.length; i++)
/* 103:    */     {
/* 104:146 */       if ("from".equals(tokens[i].toLowerCase())) {
/* 105:146 */         return i;
/* 106:    */       }
/* 107:147 */       templateQuery.append(tokens[i]);
/* 108:    */     }
/* 109:149 */     return tokens.length;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static boolean isPossiblyClassName(String last, String next)
/* 113:    */   {
/* 114:153 */     return ("class".equals(last)) || ((BEFORE_CLASS_TOKENS.contains(last)) && (!NOT_AFTER_CLASS_TOKENS.contains(next)));
/* 115:    */   }
/* 116:    */   
/* 117:    */   private static boolean isJavaIdentifier(String token)
/* 118:    */   {
/* 119:160 */     return Character.isJavaIdentifierStart(token.charAt(0));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static String getImportedClass(String name, SessionFactoryImplementor factory)
/* 123:    */   {
/* 124:164 */     return factory.getImportedClassName(name);
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.QuerySplitter
 * JD-Core Version:    0.7.0.1
 */