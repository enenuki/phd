/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import org.apache.http.Header;
/*   6:    */ import org.apache.http.HeaderIterator;
/*   7:    */ 
/*   8:    */ public class BasicListHeaderIterator
/*   9:    */   implements HeaderIterator
/*  10:    */ {
/*  11:    */   protected final List allHeaders;
/*  12:    */   protected int currentIndex;
/*  13:    */   protected int lastIndex;
/*  14:    */   protected String headerName;
/*  15:    */   
/*  16:    */   public BasicListHeaderIterator(List headers, String name)
/*  17:    */   {
/*  18: 81 */     if (headers == null) {
/*  19: 82 */       throw new IllegalArgumentException("Header list must not be null.");
/*  20:    */     }
/*  21: 86 */     this.allHeaders = headers;
/*  22: 87 */     this.headerName = name;
/*  23: 88 */     this.currentIndex = findNext(-1);
/*  24: 89 */     this.lastIndex = -1;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected int findNext(int from)
/*  28:    */   {
/*  29:103 */     if (from < -1) {
/*  30:104 */       return -1;
/*  31:    */     }
/*  32:106 */     int to = this.allHeaders.size() - 1;
/*  33:107 */     boolean found = false;
/*  34:108 */     while ((!found) && (from < to))
/*  35:    */     {
/*  36:109 */       from++;
/*  37:110 */       found = filterHeader(from);
/*  38:    */     }
/*  39:112 */     return found ? from : -1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected boolean filterHeader(int index)
/*  43:    */   {
/*  44:125 */     if (this.headerName == null) {
/*  45:126 */       return true;
/*  46:    */     }
/*  47:129 */     String name = ((Header)this.allHeaders.get(index)).getName();
/*  48:    */     
/*  49:131 */     return this.headerName.equalsIgnoreCase(name);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean hasNext()
/*  53:    */   {
/*  54:137 */     return this.currentIndex >= 0;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Header nextHeader()
/*  58:    */     throws NoSuchElementException
/*  59:    */   {
/*  60:151 */     int current = this.currentIndex;
/*  61:152 */     if (current < 0) {
/*  62:153 */       throw new NoSuchElementException("Iteration already finished.");
/*  63:    */     }
/*  64:156 */     this.lastIndex = current;
/*  65:157 */     this.currentIndex = findNext(current);
/*  66:    */     
/*  67:159 */     return (Header)this.allHeaders.get(current);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final Object next()
/*  71:    */     throws NoSuchElementException
/*  72:    */   {
/*  73:173 */     return nextHeader();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void remove()
/*  77:    */     throws UnsupportedOperationException
/*  78:    */   {
/*  79:183 */     if (this.lastIndex < 0) {
/*  80:184 */       throw new IllegalStateException("No header to remove.");
/*  81:    */     }
/*  82:186 */     this.allHeaders.remove(this.lastIndex);
/*  83:187 */     this.lastIndex = -1;
/*  84:188 */     this.currentIndex -= 1;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicListHeaderIterator
 * JD-Core Version:    0.7.0.1
 */