/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ class HashtableEntry
/*  4:   */ {
/*  5:   */   int hash;
/*  6:   */   Object key;
/*  7:   */   Object value;
/*  8:   */   HashtableEntry next;
/*  9:   */   
/* 10:   */   protected Object clone()
/* 11:   */   {
/* 12:44 */     HashtableEntry entry = new HashtableEntry();
/* 13:45 */     entry.hash = this.hash;
/* 14:46 */     entry.key = this.key;
/* 15:47 */     entry.value = this.value;
/* 16:48 */     entry.next = (this.next != null ? (HashtableEntry)this.next.clone() : null);
/* 17:49 */     return entry;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.HashtableEntry
 * JD-Core Version:    0.7.0.1
 */