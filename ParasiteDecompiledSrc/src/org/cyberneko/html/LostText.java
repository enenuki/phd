/*  1:   */ package org.cyberneko.html;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.apache.xerces.xni.Augmentations;
/*  7:   */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  8:   */ import org.apache.xerces.xni.XMLString;
/*  9:   */ 
/* 10:   */ class LostText
/* 11:   */ {
/* 12:   */   static class Entry
/* 13:   */   {
/* 14:   */     private XMLString text_;
/* 15:   */     private Augmentations augs_;
/* 16:   */     
/* 17:   */     public Entry(XMLString text, Augmentations augs)
/* 18:   */     {
/* 19:45 */       char[] chars = new char[text.length];
/* 20:46 */       System.arraycopy(text.ch, text.offset, chars, 0, text.length);
/* 21:47 */       this.text_ = new XMLString(chars, 0, chars.length);
/* 22:48 */       if (augs != null) {
/* 23:49 */         this.augs_ = new HTMLAugmentations(augs);
/* 24:   */       }
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:52 */   private final List entries = new ArrayList();
/* 29:   */   
/* 30:   */   public void add(XMLString text, Augmentations augs)
/* 31:   */   {
/* 32:59 */     if ((!this.entries.isEmpty()) || (text.toString().trim().length() > 0)) {
/* 33:60 */       this.entries.add(new Entry(text, augs));
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void refeed(XMLDocumentHandler tagBalancer)
/* 38:   */   {
/* 39:68 */     for (Iterator iter = this.entries.iterator(); iter.hasNext();)
/* 40:   */     {
/* 41:69 */       Entry entry = (Entry)iter.next();
/* 42:70 */       tagBalancer.characters(entry.text_, entry.augs_);
/* 43:   */     }
/* 44:73 */     this.entries.clear();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean isEmpty()
/* 48:   */   {
/* 49:81 */     return this.entries.isEmpty();
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.LostText
 * JD-Core Version:    0.7.0.1
 */