/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.NoSuchElementException;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HeaderIterator;
/*   6:    */ 
/*   7:    */ public class BasicHeaderIterator
/*   8:    */   implements HeaderIterator
/*   9:    */ {
/*  10:    */   protected final Header[] allHeaders;
/*  11:    */   protected int currentIndex;
/*  12:    */   protected String headerName;
/*  13:    */   
/*  14:    */   public BasicHeaderIterator(Header[] headers, String name)
/*  15:    */   {
/*  16: 74 */     if (headers == null) {
/*  17: 75 */       throw new IllegalArgumentException("Header array must not be null.");
/*  18:    */     }
/*  19: 79 */     this.allHeaders = headers;
/*  20: 80 */     this.headerName = name;
/*  21: 81 */     this.currentIndex = findNext(-1);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected int findNext(int from)
/*  25:    */   {
/*  26: 95 */     if (from < -1) {
/*  27: 96 */       return -1;
/*  28:    */     }
/*  29: 98 */     int to = this.allHeaders.length - 1;
/*  30: 99 */     boolean found = false;
/*  31:100 */     while ((!found) && (from < to))
/*  32:    */     {
/*  33:101 */       from++;
/*  34:102 */       found = filterHeader(from);
/*  35:    */     }
/*  36:104 */     return found ? from : -1;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected boolean filterHeader(int index)
/*  40:    */   {
/*  41:117 */     return (this.headerName == null) || (this.headerName.equalsIgnoreCase(this.allHeaders[index].getName()));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean hasNext()
/*  45:    */   {
/*  46:124 */     return this.currentIndex >= 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Header nextHeader()
/*  50:    */     throws NoSuchElementException
/*  51:    */   {
/*  52:138 */     int current = this.currentIndex;
/*  53:139 */     if (current < 0) {
/*  54:140 */       throw new NoSuchElementException("Iteration already finished.");
/*  55:    */     }
/*  56:143 */     this.currentIndex = findNext(current);
/*  57:    */     
/*  58:145 */     return this.allHeaders[current];
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final Object next()
/*  62:    */     throws NoSuchElementException
/*  63:    */   {
/*  64:159 */     return nextHeader();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void remove()
/*  68:    */     throws UnsupportedOperationException
/*  69:    */   {
/*  70:171 */     throw new UnsupportedOperationException("Removing headers is not supported.");
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeaderIterator
 * JD-Core Version:    0.7.0.1
 */