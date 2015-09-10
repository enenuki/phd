/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import org.hibernate.type.StandardBasicTypes;
/*   4:    */ 
/*   5:    */ public class AnsiTrimEmulationFunction
/*   6:    */   extends AbstractAnsiTrimEmulationFunction
/*   7:    */ {
/*   8:    */   public static final String LTRIM = "ltrim";
/*   9:    */   public static final String RTRIM = "rtrim";
/*  10:    */   public static final String REPLACE = "replace";
/*  11:    */   public static final String SPACE_PLACEHOLDER = "${space}$";
/*  12:    */   public static final String LEADING_SPACE_TRIM_TEMPLATE = "ltrim(?1)";
/*  13:    */   public static final String TRAILING_SPACE_TRIM_TEMPLATE = "rtrim(?1)";
/*  14:    */   public static final String BOTH_SPACE_TRIM_TEMPLATE = "ltrim(rtrim(?1))";
/*  15:    */   public static final String BOTH_SPACE_TRIM_FROM_TEMPLATE = "ltrim(rtrim(?2))";
/*  16:    */   public static final String LEADING_TRIM_TEMPLATE = "replace(replace(ltrim(replace(replace(?1,' ','${space}$'),?2,' ')),' ',?2),'${space}$',' ')";
/*  17:    */   public static final String TRAILING_TRIM_TEMPLATE = "replace(replace(rtrim(replace(replace(?1,' ','${space}$'),?2,' ')),' ',?2),'${space}$',' ')";
/*  18:    */   public static final String BOTH_TRIM_TEMPLATE = "replace(replace(ltrim(rtrim(replace(replace(?1,' ','${space}$'),?2,' '))),' ',?2),'${space}$',' ')";
/*  19:    */   private final SQLFunction leadingSpaceTrim;
/*  20:    */   private final SQLFunction trailingSpaceTrim;
/*  21:    */   private final SQLFunction bothSpaceTrim;
/*  22:    */   private final SQLFunction bothSpaceTrimFrom;
/*  23:    */   private final SQLFunction leadingTrim;
/*  24:    */   private final SQLFunction trailingTrim;
/*  25:    */   private final SQLFunction bothTrim;
/*  26:    */   
/*  27:    */   public AnsiTrimEmulationFunction()
/*  28:    */   {
/*  29:151 */     this("ltrim", "rtrim", "replace");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public AnsiTrimEmulationFunction(String ltrimFunctionName, String rtrimFunctionName, String replaceFunctionName)
/*  33:    */   {
/*  34:162 */     this.leadingSpaceTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1)".replaceAll("ltrim", ltrimFunctionName));
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:167 */     this.trailingSpaceTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1)".replaceAll("rtrim", rtrimFunctionName));
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:172 */     this.bothSpaceTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(rtrim(?1))".replaceAll("ltrim", ltrimFunctionName).replaceAll("rtrim", rtrimFunctionName));
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:178 */     this.bothSpaceTrimFrom = new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(rtrim(?2))".replaceAll("ltrim", ltrimFunctionName).replaceAll("rtrim", rtrimFunctionName));
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:184 */     this.leadingTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "replace(replace(ltrim(replace(replace(?1,' ','${space}$'),?2,' ')),' ',?2),'${space}$',' ')".replaceAll("ltrim", ltrimFunctionName).replaceAll("rtrim", rtrimFunctionName).replaceAll("replace", replaceFunctionName));
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:191 */     this.trailingTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "replace(replace(rtrim(replace(replace(?1,' ','${space}$'),?2,' ')),' ',?2),'${space}$',' ')".replaceAll("ltrim", ltrimFunctionName).replaceAll("rtrim", rtrimFunctionName).replaceAll("replace", replaceFunctionName));
/*  64:    */     
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:198 */     this.bothTrim = new SQLFunctionTemplate(StandardBasicTypes.STRING, "replace(replace(ltrim(rtrim(replace(replace(?1,' ','${space}$'),?2,' '))),' ',?2),'${space}$',' ')".replaceAll("ltrim", ltrimFunctionName).replaceAll("rtrim", rtrimFunctionName).replaceAll("replace", replaceFunctionName));
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected SQLFunction resolveBothSpaceTrimFunction()
/*  74:    */   {
/*  75:210 */     return this.bothSpaceTrim;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected SQLFunction resolveBothSpaceTrimFromFunction()
/*  79:    */   {
/*  80:217 */     return this.bothSpaceTrimFrom;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected SQLFunction resolveLeadingSpaceTrimFunction()
/*  84:    */   {
/*  85:224 */     return this.leadingSpaceTrim;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected SQLFunction resolveTrailingSpaceTrimFunction()
/*  89:    */   {
/*  90:231 */     return this.trailingSpaceTrim;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected SQLFunction resolveBothTrimFunction()
/*  94:    */   {
/*  95:238 */     return this.bothTrim;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected SQLFunction resolveLeadingTrimFunction()
/*  99:    */   {
/* 100:245 */     return this.leadingTrim;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected SQLFunction resolveTrailingTrimFunction()
/* 104:    */   {
/* 105:252 */     return this.trailingTrim;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.AnsiTrimEmulationFunction
 * JD-Core Version:    0.7.0.1
 */