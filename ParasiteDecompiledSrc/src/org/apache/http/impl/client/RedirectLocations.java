/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Set;
/*  9:   */ import org.apache.http.annotation.NotThreadSafe;
/* 10:   */ 
/* 11:   */ @NotThreadSafe
/* 12:   */ public class RedirectLocations
/* 13:   */ {
/* 14:   */   private final Set<URI> unique;
/* 15:   */   private final List<URI> all;
/* 16:   */   
/* 17:   */   public RedirectLocations()
/* 18:   */   {
/* 19:52 */     this.unique = new HashSet();
/* 20:53 */     this.all = new ArrayList();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean contains(URI uri)
/* 24:   */   {
/* 25:60 */     return this.unique.contains(uri);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void add(URI uri)
/* 29:   */   {
/* 30:67 */     this.unique.add(uri);
/* 31:68 */     this.all.add(uri);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean remove(URI uri)
/* 35:   */   {
/* 36:75 */     boolean removed = this.unique.remove(uri);
/* 37:76 */     if (removed)
/* 38:   */     {
/* 39:77 */       Iterator<URI> it = this.all.iterator();
/* 40:78 */       while (it.hasNext())
/* 41:   */       {
/* 42:79 */         URI current = (URI)it.next();
/* 43:80 */         if (current.equals(uri)) {
/* 44:81 */           it.remove();
/* 45:   */         }
/* 46:   */       }
/* 47:   */     }
/* 48:85 */     return removed;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public List<URI> getAll()
/* 52:   */   {
/* 53:96 */     return new ArrayList(this.all);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.RedirectLocations
 * JD-Core Version:    0.7.0.1
 */