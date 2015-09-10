/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ 
/*   6:    */ class PrefixForUriEnumerator
/*   7:    */   implements Enumeration
/*   8:    */ {
/*   9:    */   private Enumeration allPrefixes;
/*  10:    */   private String uri;
/*  11:392 */   private String lookahead = null;
/*  12:    */   private NamespaceSupport2 nsup;
/*  13:    */   
/*  14:    */   PrefixForUriEnumerator(NamespaceSupport2 nsup, String uri, Enumeration allPrefixes)
/*  15:    */   {
/*  16:399 */     this.nsup = nsup;
/*  17:400 */     this.uri = uri;
/*  18:401 */     this.allPrefixes = allPrefixes;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean hasMoreElements()
/*  22:    */   {
/*  23:406 */     if (this.lookahead != null) {
/*  24:407 */       return true;
/*  25:    */     }
/*  26:409 */     while (this.allPrefixes.hasMoreElements())
/*  27:    */     {
/*  28:411 */       String prefix = (String)this.allPrefixes.nextElement();
/*  29:412 */       if (this.uri.equals(this.nsup.getURI(prefix)))
/*  30:    */       {
/*  31:414 */         this.lookahead = prefix;
/*  32:415 */         return true;
/*  33:    */       }
/*  34:    */     }
/*  35:418 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object nextElement()
/*  39:    */   {
/*  40:423 */     if (hasMoreElements())
/*  41:    */     {
/*  42:425 */       String tmp = this.lookahead;
/*  43:426 */       this.lookahead = null;
/*  44:427 */       return tmp;
/*  45:    */     }
/*  46:430 */     throw new NoSuchElementException();
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.PrefixForUriEnumerator
 * JD-Core Version:    0.7.0.1
 */