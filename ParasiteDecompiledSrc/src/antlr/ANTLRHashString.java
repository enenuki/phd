/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ public class ANTLRHashString
/*   4:    */ {
/*   5:    */   private String s;
/*   6:    */   private char[] buf;
/*   7:    */   private int len;
/*   8:    */   private CharScanner lexer;
/*   9:    */   private static final int prime = 151;
/*  10:    */   
/*  11:    */   public ANTLRHashString(char[] paramArrayOfChar, int paramInt, CharScanner paramCharScanner)
/*  12:    */   {
/*  13: 24 */     this.lexer = paramCharScanner;
/*  14: 25 */     setBuffer(paramArrayOfChar, paramInt);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ANTLRHashString(CharScanner paramCharScanner)
/*  18:    */   {
/*  19: 30 */     this.lexer = paramCharScanner;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ANTLRHashString(String paramString, CharScanner paramCharScanner)
/*  23:    */   {
/*  24: 34 */     this.lexer = paramCharScanner;
/*  25: 35 */     setString(paramString);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private final char charAt(int paramInt)
/*  29:    */   {
/*  30: 39 */     return this.s != null ? this.s.charAt(paramInt) : this.buf[paramInt];
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean equals(Object paramObject)
/*  34:    */   {
/*  35: 44 */     if ((!(paramObject instanceof ANTLRHashString)) && (!(paramObject instanceof String))) {
/*  36: 45 */       return false;
/*  37:    */     }
/*  38:    */     ANTLRHashString localANTLRHashString;
/*  39: 49 */     if ((paramObject instanceof String)) {
/*  40: 50 */       localANTLRHashString = new ANTLRHashString((String)paramObject, this.lexer);
/*  41:    */     } else {
/*  42: 53 */       localANTLRHashString = (ANTLRHashString)paramObject;
/*  43:    */     }
/*  44: 55 */     int i = length();
/*  45: 56 */     if (localANTLRHashString.length() != i) {
/*  46: 57 */       return false;
/*  47:    */     }
/*  48: 59 */     if (this.lexer.getCaseSensitiveLiterals()) {
/*  49: 60 */       for (j = 0; j < i; j++) {
/*  50: 61 */         if (charAt(j) != localANTLRHashString.charAt(j)) {
/*  51: 62 */           return false;
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55: 67 */     for (int j = 0; j < i; j++) {
/*  56: 68 */       if (this.lexer.toLower(charAt(j)) != this.lexer.toLower(localANTLRHashString.charAt(j))) {
/*  57: 69 */         return false;
/*  58:    */       }
/*  59:    */     }
/*  60: 73 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int hashCode()
/*  64:    */   {
/*  65: 77 */     int i = 0;
/*  66: 78 */     int j = length();
/*  67: 80 */     if (this.lexer.getCaseSensitiveLiterals()) {
/*  68: 81 */       for (k = 0; k < j; k++) {
/*  69: 82 */         i = i * 151 + charAt(k);
/*  70:    */       }
/*  71:    */     }
/*  72: 86 */     for (int k = 0; k < j; k++) {
/*  73: 87 */       i = i * 151 + this.lexer.toLower(charAt(k));
/*  74:    */     }
/*  75: 90 */     return i;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private final int length()
/*  79:    */   {
/*  80: 94 */     return this.s != null ? this.s.length() : this.len;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setBuffer(char[] paramArrayOfChar, int paramInt)
/*  84:    */   {
/*  85: 98 */     this.buf = paramArrayOfChar;
/*  86: 99 */     this.len = paramInt;
/*  87:100 */     this.s = null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setString(String paramString)
/*  91:    */   {
/*  92:104 */     this.s = paramString;
/*  93:105 */     this.buf = null;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRHashString
 * JD-Core Version:    0.7.0.1
 */