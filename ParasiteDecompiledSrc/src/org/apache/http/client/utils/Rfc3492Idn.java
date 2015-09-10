/*   1:    */ package org.apache.http.client.utils;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ 
/*   6:    */ @Immutable
/*   7:    */ public class Rfc3492Idn
/*   8:    */   implements Idn
/*   9:    */ {
/*  10:    */   private static final int base = 36;
/*  11:    */   private static final int tmin = 1;
/*  12:    */   private static final int tmax = 26;
/*  13:    */   private static final int skew = 38;
/*  14:    */   private static final int damp = 700;
/*  15:    */   private static final int initial_bias = 72;
/*  16:    */   private static final int initial_n = 128;
/*  17:    */   private static final char delimiter = '-';
/*  18:    */   private static final String ACE_PREFIX = "xn--";
/*  19:    */   
/*  20:    */   private int adapt(int delta, int numpoints, boolean firsttime)
/*  21:    */   {
/*  22: 55 */     if (firsttime) {
/*  23: 55 */       delta /= 700;
/*  24:    */     } else {
/*  25: 56 */       delta /= 2;
/*  26:    */     }
/*  27: 57 */     delta += delta / numpoints;
/*  28: 58 */     int k = 0;
/*  29: 59 */     while (delta > 455)
/*  30:    */     {
/*  31: 60 */       delta /= 35;
/*  32: 61 */       k += 36;
/*  33:    */     }
/*  34: 63 */     return k + 36 * delta / (delta + 38);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private int digit(char c)
/*  38:    */   {
/*  39: 67 */     if ((c >= 'A') && (c <= 'Z')) {
/*  40: 67 */       return c - 'A';
/*  41:    */     }
/*  42: 68 */     if ((c >= 'a') && (c <= 'z')) {
/*  43: 68 */       return c - 'a';
/*  44:    */     }
/*  45: 69 */     if ((c >= '0') && (c <= '9')) {
/*  46: 69 */       return c - '0' + 26;
/*  47:    */     }
/*  48: 70 */     throw new IllegalArgumentException("illegal digit: " + c);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toUnicode(String punycode)
/*  52:    */   {
/*  53: 74 */     StringBuilder unicode = new StringBuilder(punycode.length());
/*  54: 75 */     StringTokenizer tok = new StringTokenizer(punycode, ".");
/*  55: 76 */     while (tok.hasMoreTokens())
/*  56:    */     {
/*  57: 77 */       String t = tok.nextToken();
/*  58: 78 */       if (unicode.length() > 0) {
/*  59: 78 */         unicode.append('.');
/*  60:    */       }
/*  61: 79 */       if (t.startsWith("xn--")) {
/*  62: 79 */         t = decode(t.substring(4));
/*  63:    */       }
/*  64: 80 */       unicode.append(t);
/*  65:    */     }
/*  66: 82 */     return unicode.toString();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String decode(String input)
/*  70:    */   {
/*  71: 86 */     int n = 128;
/*  72: 87 */     int i = 0;
/*  73: 88 */     int bias = 72;
/*  74: 89 */     StringBuilder output = new StringBuilder(input.length());
/*  75: 90 */     int lastdelim = input.lastIndexOf('-');
/*  76: 91 */     if (lastdelim != -1)
/*  77:    */     {
/*  78: 92 */       output.append(input.subSequence(0, lastdelim));
/*  79: 93 */       input = input.substring(lastdelim + 1);
/*  80:    */     }
/*  81: 96 */     while (input.length() > 0)
/*  82:    */     {
/*  83: 97 */       int oldi = i;
/*  84: 98 */       int w = 1;
/*  85: 99 */       for (int k = 36; input.length() != 0; k += 36)
/*  86:    */       {
/*  87:101 */         char c = input.charAt(0);
/*  88:102 */         input = input.substring(1);
/*  89:103 */         int digit = digit(c);
/*  90:104 */         i += digit * w;
/*  91:    */         int t;
/*  92:    */         int t;
/*  93:106 */         if (k <= bias + 1)
/*  94:    */         {
/*  95:107 */           t = 1;
/*  96:    */         }
/*  97:    */         else
/*  98:    */         {
/*  99:    */           int t;
/* 100:108 */           if (k >= bias + 26) {
/* 101:109 */             t = 26;
/* 102:    */           } else {
/* 103:111 */             t = k - bias;
/* 104:    */           }
/* 105:    */         }
/* 106:113 */         if (digit < t) {
/* 107:    */           break;
/* 108:    */         }
/* 109:114 */         w *= (36 - t);
/* 110:    */       }
/* 111:116 */       bias = adapt(i - oldi, output.length() + 1, oldi == 0);
/* 112:117 */       n += i / (output.length() + 1);
/* 113:118 */       i %= (output.length() + 1);
/* 114:    */       
/* 115:120 */       output.insert(i, (char)n);
/* 116:121 */       i++;
/* 117:    */     }
/* 118:123 */     return output.toString();
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.Rfc3492Idn
 * JD-Core Version:    0.7.0.1
 */