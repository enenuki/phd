/*   1:    */ package org.cyberneko.html;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import org.apache.xerces.xni.Augmentations;
/*   6:    */ 
/*   7:    */ public class HTMLAugmentations
/*   8:    */   implements Augmentations
/*   9:    */ {
/*  10: 46 */   protected final Hashtable fItems = new Hashtable();
/*  11:    */   
/*  12:    */   public HTMLAugmentations() {}
/*  13:    */   
/*  14:    */   HTMLAugmentations(Augmentations augs)
/*  15:    */   {
/*  16: 60 */     for (Enumeration keys = augs.keys(); keys.hasMoreElements();)
/*  17:    */     {
/*  18: 61 */       String key = (String)keys.nextElement();
/*  19: 62 */       Object value = augs.getItem(key);
/*  20: 63 */       if ((value instanceof HTMLScanner.LocationItem)) {
/*  21: 64 */         value = new HTMLScanner.LocationItem((HTMLScanner.LocationItem)value);
/*  22:    */       }
/*  23: 66 */       this.fItems.put(key, value);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void removeAllItems()
/*  28:    */   {
/*  29: 74 */     this.fItems.clear();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void clear()
/*  33:    */   {
/*  34: 81 */     this.fItems.clear();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object putItem(String key, Object item)
/*  38:    */   {
/*  39: 99 */     return this.fItems.put(key, item);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object getItem(String key)
/*  43:    */   {
/*  44:113 */     return this.fItems.get(key);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object removeItem(String key)
/*  48:    */   {
/*  49:124 */     return this.fItems.remove(key);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Enumeration keys()
/*  53:    */   {
/*  54:131 */     return this.fItems.keys();
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLAugmentations
 * JD-Core Version:    0.7.0.1
 */