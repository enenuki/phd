/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.apache.commons.collections.CollectionUtils;
/*   7:    */ import org.apache.commons.collections.collection.CompositeCollection;
/*   8:    */ import org.apache.commons.collections.collection.CompositeCollection.CollectionMutator;
/*   9:    */ 
/*  10:    */ public class CompositeSet
/*  11:    */   extends CompositeCollection
/*  12:    */   implements Set
/*  13:    */ {
/*  14:    */   public CompositeSet() {}
/*  15:    */   
/*  16:    */   public CompositeSet(Set set)
/*  17:    */   {
/*  18: 51 */     super(set);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public CompositeSet(Set[] sets)
/*  22:    */   {
/*  23: 58 */     super(sets);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public synchronized void addComposited(Collection c)
/*  27:    */   {
/*  28: 73 */     if (!(c instanceof Set)) {
/*  29: 74 */       throw new IllegalArgumentException("Collections added must implement java.util.Set");
/*  30:    */     }
/*  31: 77 */     for (Iterator i = getCollections().iterator(); i.hasNext();)
/*  32:    */     {
/*  33: 78 */       Set set = (Set)i.next();
/*  34: 79 */       Collection intersects = CollectionUtils.intersection(set, c);
/*  35: 80 */       if (intersects.size() > 0)
/*  36:    */       {
/*  37: 81 */         if (this.mutator == null) {
/*  38: 82 */           throw new UnsupportedOperationException("Collision adding composited collection with no SetMutator set");
/*  39:    */         }
/*  40: 85 */         if (!(this.mutator instanceof SetMutator)) {
/*  41: 86 */           throw new UnsupportedOperationException("Collision adding composited collection to a CompositeSet with a CollectionMutator instead of a SetMutator");
/*  42:    */         }
/*  43: 89 */         ((SetMutator)this.mutator).resolveCollision(this, set, (Set)c, intersects);
/*  44: 90 */         if (CollectionUtils.intersection(set, c).size() > 0) {
/*  45: 91 */           throw new IllegalArgumentException("Attempt to add illegal entry unresolved by SetMutator.resolveCollision()");
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49: 96 */     super.addComposited(new Collection[] { c });
/*  50:    */   }
/*  51:    */   
/*  52:    */   public synchronized void addComposited(Collection c, Collection d)
/*  53:    */   {
/*  54:105 */     if (!(c instanceof Set)) {
/*  55:105 */       throw new IllegalArgumentException("Argument must implement java.util.Set");
/*  56:    */     }
/*  57:106 */     if (!(d instanceof Set)) {
/*  58:106 */       throw new IllegalArgumentException("Argument must implement java.util.Set");
/*  59:    */     }
/*  60:107 */     addComposited(new Set[] { (Set)c, (Set)d });
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized void addComposited(Collection[] comps)
/*  64:    */   {
/*  65:116 */     for (int i = comps.length - 1; i >= 0; i--) {
/*  66:117 */       addComposited(comps[i]);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setMutator(CompositeCollection.CollectionMutator mutator)
/*  71:    */   {
/*  72:129 */     super.setMutator(mutator);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean remove(Object obj)
/*  76:    */   {
/*  77:142 */     for (Iterator i = getCollections().iterator(); i.hasNext();)
/*  78:    */     {
/*  79:143 */       Set set = (Set)i.next();
/*  80:144 */       if (set.contains(obj)) {
/*  81:144 */         return set.remove(obj);
/*  82:    */       }
/*  83:    */     }
/*  84:146 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean equals(Object obj)
/*  88:    */   {
/*  89:154 */     if ((obj instanceof Set))
/*  90:    */     {
/*  91:155 */       Set set = (Set)obj;
/*  92:156 */       if ((set.containsAll(this)) && (set.size() == size())) {
/*  93:157 */         return true;
/*  94:    */       }
/*  95:    */     }
/*  96:160 */     return false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int hashCode()
/* 100:    */   {
/* 101:167 */     int code = 0;
/* 102:168 */     for (Iterator i = iterator(); i.hasNext();)
/* 103:    */     {
/* 104:169 */       Object next = i.next();
/* 105:170 */       code += (next != null ? next.hashCode() : 0);
/* 106:    */     }
/* 107:172 */     return code;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static abstract interface SetMutator
/* 111:    */     extends CompositeCollection.CollectionMutator
/* 112:    */   {
/* 113:    */     public abstract void resolveCollision(CompositeSet paramCompositeSet, Set paramSet1, Set paramSet2, Collection paramCollection);
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.CompositeSet
 * JD-Core Version:    0.7.0.1
 */