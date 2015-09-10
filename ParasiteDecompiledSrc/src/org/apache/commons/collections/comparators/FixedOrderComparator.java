/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class FixedOrderComparator
/*  10:    */   implements Comparator
/*  11:    */ {
/*  12:    */   public static final int UNKNOWN_BEFORE = 0;
/*  13:    */   public static final int UNKNOWN_AFTER = 1;
/*  14:    */   public static final int UNKNOWN_THROW_EXCEPTION = 2;
/*  15: 73 */   private final Map map = new HashMap();
/*  16: 75 */   private int counter = 0;
/*  17: 77 */   private boolean isLocked = false;
/*  18: 79 */   private int unknownObjectBehavior = 2;
/*  19:    */   
/*  20:    */   public FixedOrderComparator() {}
/*  21:    */   
/*  22:    */   public FixedOrderComparator(Object[] items)
/*  23:    */   {
/*  24:101 */     if (items == null) {
/*  25:102 */       throw new IllegalArgumentException("The list of items must not be null");
/*  26:    */     }
/*  27:104 */     for (int i = 0; i < items.length; i++) {
/*  28:105 */       add(items[i]);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FixedOrderComparator(List items)
/*  33:    */   {
/*  34:120 */     if (items == null) {
/*  35:121 */       throw new IllegalArgumentException("The list of items must not be null");
/*  36:    */     }
/*  37:123 */     for (Iterator it = items.iterator(); it.hasNext();) {
/*  38:124 */       add(it.next());
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isLocked()
/*  43:    */   {
/*  44:138 */     return this.isLocked;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void checkLocked()
/*  48:    */   {
/*  49:147 */     if (isLocked()) {
/*  50:148 */       throw new UnsupportedOperationException("Cannot modify a FixedOrderComparator after a comparison");
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getUnknownObjectBehavior()
/*  55:    */   {
/*  56:159 */     return this.unknownObjectBehavior;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setUnknownObjectBehavior(int unknownObjectBehavior)
/*  60:    */   {
/*  61:171 */     checkLocked();
/*  62:172 */     if ((unknownObjectBehavior != 1) && (unknownObjectBehavior != 0) && (unknownObjectBehavior != 2)) {
/*  63:175 */       throw new IllegalArgumentException("Unrecognised value for unknown behaviour flag");
/*  64:    */     }
/*  65:177 */     this.unknownObjectBehavior = unknownObjectBehavior;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean add(Object obj)
/*  69:    */   {
/*  70:193 */     checkLocked();
/*  71:194 */     Object position = this.map.put(obj, new Integer(this.counter++));
/*  72:195 */     return position == null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean addAsEqual(Object existingObj, Object newObj)
/*  76:    */   {
/*  77:212 */     checkLocked();
/*  78:213 */     Integer position = (Integer)this.map.get(existingObj);
/*  79:214 */     if (position == null) {
/*  80:215 */       throw new IllegalArgumentException(existingObj + " not known to " + this);
/*  81:    */     }
/*  82:217 */     Object result = this.map.put(newObj, position);
/*  83:218 */     return result == null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int compare(Object obj1, Object obj2)
/*  87:    */   {
/*  88:238 */     this.isLocked = true;
/*  89:239 */     Integer position1 = (Integer)this.map.get(obj1);
/*  90:240 */     Integer position2 = (Integer)this.map.get(obj2);
/*  91:241 */     if ((position1 == null) || (position2 == null))
/*  92:    */     {
/*  93:242 */       switch (this.unknownObjectBehavior)
/*  94:    */       {
/*  95:    */       case 0: 
/*  96:244 */         if (position1 == null) {
/*  97:245 */           return position2 == null ? 0 : -1;
/*  98:    */         }
/*  99:247 */         return 1;
/* 100:    */       case 1: 
/* 101:250 */         if (position1 == null) {
/* 102:251 */           return position2 == null ? 0 : 1;
/* 103:    */         }
/* 104:253 */         return -1;
/* 105:    */       case 2: 
/* 106:256 */         Object unknownObj = position1 == null ? obj1 : obj2;
/* 107:257 */         throw new IllegalArgumentException("Attempting to compare unknown object " + unknownObj);
/* 108:    */       }
/* 109:259 */       throw new UnsupportedOperationException("Unknown unknownObjectBehavior: " + this.unknownObjectBehavior);
/* 110:    */     }
/* 111:262 */     return position1.compareTo(position2);
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.FixedOrderComparator
 * JD-Core Version:    0.7.0.1
 */