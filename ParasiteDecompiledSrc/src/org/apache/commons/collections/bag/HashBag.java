/*  1:   */ package org.apache.commons.collections.bag;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.ObjectOutputStream;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import org.apache.commons.collections.Bag;
/* 10:   */ 
/* 11:   */ public class HashBag
/* 12:   */   extends AbstractMapBag
/* 13:   */   implements Bag, Serializable
/* 14:   */ {
/* 15:   */   private static final long serialVersionUID = -6561115435802554013L;
/* 16:   */   
/* 17:   */   public HashBag()
/* 18:   */   {
/* 19:54 */     super(new HashMap());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public HashBag(Collection coll)
/* 23:   */   {
/* 24:63 */     this();
/* 25:64 */     addAll(coll);
/* 26:   */   }
/* 27:   */   
/* 28:   */   private void writeObject(ObjectOutputStream out)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:72 */     out.defaultWriteObject();
/* 32:73 */     super.doWriteObject(out);
/* 33:   */   }
/* 34:   */   
/* 35:   */   private void readObject(ObjectInputStream in)
/* 36:   */     throws IOException, ClassNotFoundException
/* 37:   */   {
/* 38:80 */     in.defaultReadObject();
/* 39:81 */     super.doReadObject(new HashMap(), in);
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.HashBag
 * JD-Core Version:    0.7.0.1
 */