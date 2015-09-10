/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.QueryException;
/*   6:    */ import org.hibernate.engine.spi.Mapping;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.type.StandardBasicTypes;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class DerbyConcatFunction
/*  12:    */   implements SQLFunction
/*  13:    */ {
/*  14:    */   public boolean hasArguments()
/*  15:    */   {
/*  16: 55 */     return true;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean hasParenthesesIfNoArguments()
/*  20:    */   {
/*  21: 64 */     return true;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Type getReturnType(Type argumentType, Mapping mapping)
/*  25:    */     throws QueryException
/*  26:    */   {
/*  27: 73 */     return StandardBasicTypes.STRING;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String render(Type argumentType, List args, SessionFactoryImplementor factory)
/*  31:    */     throws QueryException
/*  32:    */   {
/*  33: 87 */     boolean areAllArgsParams = true;
/*  34: 88 */     Iterator itr = args.iterator();
/*  35: 89 */     while (itr.hasNext())
/*  36:    */     {
/*  37: 90 */       String arg = (String)itr.next();
/*  38: 91 */       if (!"?".equals(arg))
/*  39:    */       {
/*  40: 92 */         areAllArgsParams = false;
/*  41: 93 */         break;
/*  42:    */       }
/*  43:    */     }
/*  44: 97 */     if (areAllArgsParams) {
/*  45: 98 */       join(args.iterator(), new StringTransformer()
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49:102 */         new StringJoinTemplate
/*  50:    */         {
/*  51:    */           public String transform(String string)
/*  52:    */           {
/*  53:102 */             return "cast( ? as varchar(32672) )";
/*  54:    */           }
/*  55:102 */         }, new StringJoinTemplate()
/*  56:    */         {
/*  57:    */           public String getBeginning()
/*  58:    */           {
/*  59:107 */             return "varchar( ";
/*  60:    */           }
/*  61:    */           
/*  62:    */           public String getSeparator()
/*  63:    */           {
/*  64:110 */             return " || ";
/*  65:    */           }
/*  66:    */           
/*  67:    */           public String getEnding()
/*  68:    */           {
/*  69:113 */             return " )";
/*  70:    */           }
/*  71:    */         });
/*  72:    */     }
/*  73:119 */     join(args.iterator(), new StringTransformer()
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:123 */       new StringJoinTemplate
/*  78:    */       {
/*  79:    */         public String transform(String string)
/*  80:    */         {
/*  81:123 */           return string;
/*  82:    */         }
/*  83:123 */       }, new StringJoinTemplate()
/*  84:    */       {
/*  85:    */         public String getBeginning()
/*  86:    */         {
/*  87:128 */           return "(";
/*  88:    */         }
/*  89:    */         
/*  90:    */         public String getSeparator()
/*  91:    */         {
/*  92:131 */           return "||";
/*  93:    */         }
/*  94:    */         
/*  95:    */         public String getEnding()
/*  96:    */         {
/*  97:134 */           return ")";
/*  98:    */         }
/*  99:    */       });
/* 100:    */   }
/* 101:    */   
/* 102:    */   private static String join(Iterator elements, StringTransformer elementTransformer, StringJoinTemplate template)
/* 103:    */   {
/* 104:168 */     StringBuffer buffer = new StringBuffer(template.getBeginning());
/* 105:169 */     while (elements.hasNext())
/* 106:    */     {
/* 107:170 */       String element = (String)elements.next();
/* 108:171 */       buffer.append(elementTransformer.transform(element));
/* 109:172 */       if (elements.hasNext()) {
/* 110:173 */         buffer.append(template.getSeparator());
/* 111:    */       }
/* 112:    */     }
/* 113:176 */     return template.getEnding();
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static abstract interface StringJoinTemplate
/* 117:    */   {
/* 118:    */     public abstract String getBeginning();
/* 119:    */     
/* 120:    */     public abstract String getSeparator();
/* 121:    */     
/* 122:    */     public abstract String getEnding();
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static abstract interface StringTransformer
/* 126:    */   {
/* 127:    */     public abstract String transform(String paramString);
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.DerbyConcatFunction
 * JD-Core Version:    0.7.0.1
 */