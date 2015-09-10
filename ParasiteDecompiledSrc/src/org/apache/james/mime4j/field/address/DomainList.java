/*  1:   */ package org.apache.james.mime4j.field.address;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.AbstractList;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.Collections;
/*  7:   */ import java.util.List;
/*  8:   */ 
/*  9:   */ public class DomainList
/* 10:   */   extends AbstractList<String>
/* 11:   */   implements Serializable
/* 12:   */ {
/* 13:   */   private static final long serialVersionUID = 1L;
/* 14:   */   private final List<String> domains;
/* 15:   */   
/* 16:   */   public DomainList(List<String> domains, boolean dontCopy)
/* 17:   */   {
/* 18:46 */     if (domains != null) {
/* 19:47 */       this.domains = (dontCopy ? domains : new ArrayList(domains));
/* 20:   */     } else {
/* 21:49 */       this.domains = Collections.emptyList();
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int size()
/* 26:   */   {
/* 27:57 */     return this.domains.size();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String get(int index)
/* 31:   */   {
/* 32:68 */     return (String)this.domains.get(index);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toRouteString()
/* 36:   */   {
/* 37:76 */     StringBuilder sb = new StringBuilder();
/* 38:78 */     for (String domain : this.domains)
/* 39:   */     {
/* 40:79 */       if (sb.length() > 0) {
/* 41:80 */         sb.append(',');
/* 42:   */       }
/* 43:83 */       sb.append("@");
/* 44:84 */       sb.append(domain);
/* 45:   */     }
/* 46:87 */     return sb.toString();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:92 */     return toRouteString();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.DomainList
 * JD-Core Version:    0.7.0.1
 */