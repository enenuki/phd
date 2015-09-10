/*  1:   */ package org.apache.commons.collections;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.HashMap;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public class HashBag
/* 10:   */   extends DefaultMapBag
/* 11:   */   implements Bag
/* 12:   */ {
/* 13:   */   public HashBag()
/* 14:   */   {
/* 15:37 */     super(new HashMap());
/* 16:   */   }
/* 17:   */   
/* 18:   */   public HashBag(Collection coll)
/* 19:   */   {
/* 20:47 */     this();
/* 21:48 */     addAll(coll);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.HashBag
 * JD-Core Version:    0.7.0.1
 */