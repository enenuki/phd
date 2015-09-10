/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import org.apache.http.annotation.Immutable;
/*   9:    */ 
/*  10:    */ @Immutable
/*  11:    */ public class PublicSuffixListParser
/*  12:    */ {
/*  13:    */   private static final int MAX_LINE_LEN = 256;
/*  14:    */   private final PublicSuffixFilter filter;
/*  15:    */   
/*  16:    */   PublicSuffixListParser(PublicSuffixFilter filter)
/*  17:    */   {
/*  18: 54 */     this.filter = filter;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void parse(Reader list)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 66 */     Collection<String> rules = new ArrayList();
/*  25: 67 */     Collection<String> exceptions = new ArrayList();
/*  26: 68 */     BufferedReader r = new BufferedReader(list);
/*  27: 69 */     StringBuilder sb = new StringBuilder(256);
/*  28: 70 */     boolean more = true;
/*  29: 71 */     while (more)
/*  30:    */     {
/*  31: 72 */       more = readLine(r, sb);
/*  32: 73 */       String line = sb.toString();
/*  33: 74 */       if ((line.length() != 0) && 
/*  34: 75 */         (!line.startsWith("//")))
/*  35:    */       {
/*  36: 76 */         if (line.startsWith(".")) {
/*  37: 76 */           line = line.substring(1);
/*  38:    */         }
/*  39: 78 */         boolean isException = line.startsWith("!");
/*  40: 79 */         if (isException) {
/*  41: 79 */           line = line.substring(1);
/*  42:    */         }
/*  43: 81 */         if (isException) {
/*  44: 82 */           exceptions.add(line);
/*  45:    */         } else {
/*  46: 84 */           rules.add(line);
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50: 88 */     this.filter.setPublicSuffixes(rules);
/*  51: 89 */     this.filter.setExceptions(exceptions);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private boolean readLine(Reader r, StringBuilder sb)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:100 */     sb.setLength(0);
/*  58:    */     
/*  59:102 */     boolean hitWhitespace = false;
/*  60:    */     int b;
/*  61:103 */     while ((b = r.read()) != -1)
/*  62:    */     {
/*  63:104 */       char c = (char)b;
/*  64:105 */       if (c == '\n') {
/*  65:    */         break;
/*  66:    */       }
/*  67:107 */       if (Character.isWhitespace(c)) {
/*  68:107 */         hitWhitespace = true;
/*  69:    */       }
/*  70:108 */       if (!hitWhitespace) {
/*  71:108 */         sb.append(c);
/*  72:    */       }
/*  73:109 */       if (sb.length() > 256) {
/*  74:109 */         throw new IOException("Line too long");
/*  75:    */       }
/*  76:    */     }
/*  77:111 */     return b != -1;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.PublicSuffixListParser
 * JD-Core Version:    0.7.0.1
 */