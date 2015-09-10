/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.QueryException;
/*   6:    */ import org.hibernate.engine.spi.Mapping;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.type.StandardBasicTypes;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public abstract class AbstractAnsiTrimEmulationFunction
/*  12:    */   implements SQLFunction
/*  13:    */ {
/*  14:    */   public final boolean hasArguments()
/*  15:    */   {
/*  16: 48 */     return true;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public final boolean hasParenthesesIfNoArguments()
/*  20:    */   {
/*  21: 53 */     return false;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final Type getReturnType(Type argumentType, Mapping mapping)
/*  25:    */     throws QueryException
/*  26:    */   {
/*  27: 58 */     return StandardBasicTypes.STRING;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final String render(Type argumentType, List args, SessionFactoryImplementor factory)
/*  31:    */     throws QueryException
/*  32:    */   {
/*  33: 79 */     if (args.size() == 1) {
/*  34: 82 */       return resolveBothSpaceTrimFunction().render(argumentType, args, factory);
/*  35:    */     }
/*  36: 84 */     if ("from".equalsIgnoreCase((String)args.get(0))) {
/*  37: 87 */       return resolveBothSpaceTrimFromFunction().render(argumentType, args, factory);
/*  38:    */     }
/*  39: 93 */     boolean leading = true;
/*  40: 94 */     boolean trailing = true;
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:101 */     int potentialTrimCharacterArgIndex = 1;
/*  48:102 */     String firstArg = (String)args.get(0);
/*  49:103 */     if ("leading".equalsIgnoreCase(firstArg)) {
/*  50:104 */       trailing = false;
/*  51:106 */     } else if ("trailing".equalsIgnoreCase(firstArg)) {
/*  52:107 */       leading = false;
/*  53:109 */     } else if (!"both".equalsIgnoreCase(firstArg)) {
/*  54:112 */       potentialTrimCharacterArgIndex = 0;
/*  55:    */     }
/*  56:115 */     String potentialTrimCharacter = (String)args.get(potentialTrimCharacterArgIndex);
/*  57:    */     String trimSource;
/*  58:    */     String trimCharacter;
/*  59:    */     String trimSource;
/*  60:116 */     if ("from".equalsIgnoreCase(potentialTrimCharacter))
/*  61:    */     {
/*  62:117 */       String trimCharacter = "' '";
/*  63:118 */       trimSource = (String)args.get(potentialTrimCharacterArgIndex + 1);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:    */       String trimSource;
/*  68:120 */       if (potentialTrimCharacterArgIndex + 1 >= args.size())
/*  69:    */       {
/*  70:121 */         String trimCharacter = "' '";
/*  71:122 */         trimSource = potentialTrimCharacter;
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75:125 */         trimCharacter = potentialTrimCharacter;
/*  76:    */         String trimSource;
/*  77:126 */         if ("from".equalsIgnoreCase((String)args.get(potentialTrimCharacterArgIndex + 1))) {
/*  78:127 */           trimSource = (String)args.get(potentialTrimCharacterArgIndex + 2);
/*  79:    */         } else {
/*  80:130 */           trimSource = (String)args.get(potentialTrimCharacterArgIndex + 1);
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:134 */     List<String> argsToUse = new ArrayList();
/*  85:135 */     argsToUse.add(trimSource);
/*  86:136 */     argsToUse.add(trimCharacter);
/*  87:138 */     if (trimCharacter.equals("' '"))
/*  88:    */     {
/*  89:139 */       if ((leading) && (trailing)) {
/*  90:140 */         return resolveBothSpaceTrimFunction().render(argumentType, argsToUse, factory);
/*  91:    */       }
/*  92:142 */       if (leading) {
/*  93:143 */         return resolveLeadingSpaceTrimFunction().render(argumentType, argsToUse, factory);
/*  94:    */       }
/*  95:146 */       return resolveTrailingSpaceTrimFunction().render(argumentType, argsToUse, factory);
/*  96:    */     }
/*  97:150 */     if ((leading) && (trailing)) {
/*  98:151 */       return resolveBothTrimFunction().render(argumentType, argsToUse, factory);
/*  99:    */     }
/* 100:153 */     if (leading) {
/* 101:154 */       return resolveLeadingTrimFunction().render(argumentType, argsToUse, factory);
/* 102:    */     }
/* 103:157 */     return resolveTrailingTrimFunction().render(argumentType, argsToUse, factory);
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected abstract SQLFunction resolveBothSpaceTrimFunction();
/* 107:    */   
/* 108:    */   protected abstract SQLFunction resolveBothSpaceTrimFromFunction();
/* 109:    */   
/* 110:    */   protected abstract SQLFunction resolveLeadingSpaceTrimFunction();
/* 111:    */   
/* 112:    */   protected abstract SQLFunction resolveTrailingSpaceTrimFunction();
/* 113:    */   
/* 114:    */   protected abstract SQLFunction resolveBothTrimFunction();
/* 115:    */   
/* 116:    */   protected abstract SQLFunction resolveLeadingTrimFunction();
/* 117:    */   
/* 118:    */   protected abstract SQLFunction resolveTrailingTrimFunction();
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.AbstractAnsiTrimEmulationFunction
 * JD-Core Version:    0.7.0.1
 */