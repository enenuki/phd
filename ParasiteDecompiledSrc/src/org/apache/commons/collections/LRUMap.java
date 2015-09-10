/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.io.Externalizable;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.ObjectInput;
/*   6:    */ import java.io.ObjectOutput;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Set;
/*   9:    */ 
/*  10:    */ /**
/*  11:    */  * @deprecated
/*  12:    */  */
/*  13:    */ public class LRUMap
/*  14:    */   extends SequencedHashMap
/*  15:    */   implements Externalizable
/*  16:    */ {
/*  17: 55 */   private int maximumSize = 0;
/*  18:    */   private static final long serialVersionUID = 2197433140769957051L;
/*  19:    */   
/*  20:    */   public LRUMap()
/*  21:    */   {
/*  22: 64 */     this(100);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public LRUMap(int i)
/*  26:    */   {
/*  27: 75 */     super(i);
/*  28: 76 */     this.maximumSize = i;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object get(Object key)
/*  32:    */   {
/*  33: 92 */     if (!containsKey(key)) {
/*  34: 92 */       return null;
/*  35:    */     }
/*  36: 94 */     Object value = remove(key);
/*  37: 95 */     super.put(key, value);
/*  38: 96 */     return value;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object put(Object key, Object value)
/*  42:    */   {
/*  43:113 */     int mapSize = size();
/*  44:114 */     Object retval = null;
/*  45:116 */     if (mapSize >= this.maximumSize) {
/*  46:120 */       if (!containsKey(key)) {
/*  47:122 */         removeLRU();
/*  48:    */       }
/*  49:    */     }
/*  50:126 */     retval = super.put(key, value);
/*  51:    */     
/*  52:128 */     return retval;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void removeLRU()
/*  56:    */   {
/*  57:136 */     Object key = getFirstKey();
/*  58:    */     
/*  59:    */ 
/*  60:139 */     Object value = super.get(key);
/*  61:    */     
/*  62:141 */     remove(key);
/*  63:    */     
/*  64:143 */     processRemovedLRU(key, value);
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void processRemovedLRU(Object key, Object value) {}
/*  68:    */   
/*  69:    */   public void readExternal(ObjectInput in)
/*  70:    */     throws IOException, ClassNotFoundException
/*  71:    */   {
/*  72:161 */     this.maximumSize = in.readInt();
/*  73:162 */     int size = in.readInt();
/*  74:164 */     for (int i = 0; i < size; i++)
/*  75:    */     {
/*  76:165 */       Object key = in.readObject();
/*  77:166 */       Object value = in.readObject();
/*  78:167 */       put(key, value);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void writeExternal(ObjectOutput out)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:172 */     out.writeInt(this.maximumSize);
/*  86:173 */     out.writeInt(size());
/*  87:174 */     for (Iterator iterator = keySet().iterator(); iterator.hasNext();)
/*  88:    */     {
/*  89:175 */       Object key = iterator.next();
/*  90:176 */       out.writeObject(key);
/*  91:    */       
/*  92:    */ 
/*  93:179 */       Object value = super.get(key);
/*  94:180 */       out.writeObject(value);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getMaximumSize()
/*  99:    */   {
/* 100:191 */     return this.maximumSize;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setMaximumSize(int maximumSize)
/* 104:    */   {
/* 105:197 */     this.maximumSize = maximumSize;
/* 106:198 */     while (size() > maximumSize) {
/* 107:199 */       removeLRU();
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.LRUMap
 * JD-Core Version:    0.7.0.1
 */