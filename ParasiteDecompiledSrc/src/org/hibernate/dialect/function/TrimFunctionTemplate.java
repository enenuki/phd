/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.QueryException;
/*   5:    */ import org.hibernate.engine.spi.Mapping;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.type.StandardBasicTypes;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ 
/*  10:    */ public abstract class TrimFunctionTemplate
/*  11:    */   implements SQLFunction
/*  12:    */ {
/*  13:    */   public boolean hasArguments()
/*  14:    */   {
/*  15: 40 */     return true;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean hasParenthesesIfNoArguments()
/*  19:    */   {
/*  20: 44 */     return false;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Type getReturnType(Type firstArgument, Mapping mapping)
/*  24:    */     throws QueryException
/*  25:    */   {
/*  26: 48 */     return StandardBasicTypes.STRING;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String render(Type firstArgument, List args, SessionFactoryImplementor factory)
/*  30:    */     throws QueryException
/*  31:    */   {
/*  32: 52 */     Options options = new Options();
/*  33:    */     String trimSource;
/*  34:    */     String trimSource;
/*  35: 55 */     if (args.size() == 1)
/*  36:    */     {
/*  37: 57 */       trimSource = (String)args.get(0);
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41:    */       String trimSource;
/*  42: 59 */       if ("from".equalsIgnoreCase((String)args.get(0)))
/*  43:    */       {
/*  44: 62 */         trimSource = (String)args.get(1);
/*  45:    */       }
/*  46:    */       else
/*  47:    */       {
/*  48: 72 */         int potentialTrimCharacterArgIndex = 1;
/*  49: 73 */         String firstArg = (String)args.get(0);
/*  50: 74 */         if ("leading".equalsIgnoreCase(firstArg)) {
/*  51: 75 */           options.setTrimSpecification(Specification.LEADING);
/*  52: 77 */         } else if ("trailing".equalsIgnoreCase(firstArg)) {
/*  53: 78 */           options.setTrimSpecification(Specification.TRAILING);
/*  54: 80 */         } else if (!"both".equalsIgnoreCase(firstArg)) {
/*  55: 84 */           potentialTrimCharacterArgIndex = 0;
/*  56:    */         }
/*  57: 87 */         String potentialTrimCharacter = (String)args.get(potentialTrimCharacterArgIndex);
/*  58:    */         String trimSource;
/*  59: 88 */         if ("from".equalsIgnoreCase(potentialTrimCharacter))
/*  60:    */         {
/*  61: 89 */           trimSource = (String)args.get(potentialTrimCharacterArgIndex + 1);
/*  62:    */         }
/*  63:    */         else
/*  64:    */         {
/*  65:    */           String trimSource;
/*  66: 91 */           if (potentialTrimCharacterArgIndex + 1 >= args.size())
/*  67:    */           {
/*  68: 92 */             trimSource = potentialTrimCharacter;
/*  69:    */           }
/*  70:    */           else
/*  71:    */           {
/*  72: 95 */             options.setTrimCharacter(potentialTrimCharacter);
/*  73:    */             String trimSource;
/*  74: 96 */             if ("from".equalsIgnoreCase((String)args.get(potentialTrimCharacterArgIndex + 1))) {
/*  75: 97 */               trimSource = (String)args.get(potentialTrimCharacterArgIndex + 2);
/*  76:    */             } else {
/*  77:100 */               trimSource = (String)args.get(potentialTrimCharacterArgIndex + 1);
/*  78:    */             }
/*  79:    */           }
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:104 */     return render(options, trimSource, factory);
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected abstract String render(Options paramOptions, String paramString, SessionFactoryImplementor paramSessionFactoryImplementor);
/*  87:    */   
/*  88:    */   public static class Options
/*  89:    */   {
/*  90:    */     public static final String DEFAULT_TRIM_CHARACTER = "' '";
/*  91:112 */     private String trimCharacter = "' '";
/*  92:113 */     private TrimFunctionTemplate.Specification trimSpecification = TrimFunctionTemplate.Specification.BOTH;
/*  93:    */     
/*  94:    */     public String getTrimCharacter()
/*  95:    */     {
/*  96:116 */       return this.trimCharacter;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void setTrimCharacter(String trimCharacter)
/* 100:    */     {
/* 101:120 */       this.trimCharacter = trimCharacter;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public TrimFunctionTemplate.Specification getTrimSpecification()
/* 105:    */     {
/* 106:124 */       return this.trimSpecification;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public void setTrimSpecification(TrimFunctionTemplate.Specification trimSpecification)
/* 110:    */     {
/* 111:128 */       this.trimSpecification = trimSpecification;
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static class Specification
/* 116:    */   {
/* 117:133 */     public static final Specification LEADING = new Specification("leading");
/* 118:134 */     public static final Specification TRAILING = new Specification("trailing");
/* 119:135 */     public static final Specification BOTH = new Specification("both");
/* 120:    */     private final String name;
/* 121:    */     
/* 122:    */     private Specification(String name)
/* 123:    */     {
/* 124:140 */       this.name = name;
/* 125:    */     }
/* 126:    */     
/* 127:    */     public String getName()
/* 128:    */     {
/* 129:144 */       return this.name;
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.TrimFunctionTemplate
 * JD-Core Version:    0.7.0.1
 */