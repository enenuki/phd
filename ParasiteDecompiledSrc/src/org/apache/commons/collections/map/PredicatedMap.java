/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.apache.commons.collections.Predicate;
/*  12:    */ 
/*  13:    */ public class PredicatedMap
/*  14:    */   extends AbstractInputCheckedMapDecorator
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 7412622456128415156L;
/*  18:    */   protected final Predicate keyPredicate;
/*  19:    */   protected final Predicate valuePredicate;
/*  20:    */   
/*  21:    */   public static Map decorate(Map map, Predicate keyPredicate, Predicate valuePredicate)
/*  22:    */   {
/*  23: 77 */     return new PredicatedMap(map, keyPredicate, valuePredicate);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected PredicatedMap(Map map, Predicate keyPredicate, Predicate valuePredicate)
/*  27:    */   {
/*  28: 90 */     super(map);
/*  29: 91 */     this.keyPredicate = keyPredicate;
/*  30: 92 */     this.valuePredicate = valuePredicate;
/*  31:    */     
/*  32: 94 */     Iterator it = map.entrySet().iterator();
/*  33: 95 */     while (it.hasNext())
/*  34:    */     {
/*  35: 96 */       Map.Entry entry = (Map.Entry)it.next();
/*  36: 97 */       Object key = entry.getKey();
/*  37: 98 */       Object value = entry.getValue();
/*  38: 99 */       validate(key, value);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void writeObject(ObjectOutputStream out)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45:112 */     out.defaultWriteObject();
/*  46:113 */     out.writeObject(this.map);
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void readObject(ObjectInputStream in)
/*  50:    */     throws IOException, ClassNotFoundException
/*  51:    */   {
/*  52:125 */     in.defaultReadObject();
/*  53:126 */     this.map = ((Map)in.readObject());
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected void validate(Object key, Object value)
/*  57:    */   {
/*  58:138 */     if ((this.keyPredicate != null) && (!this.keyPredicate.evaluate(key))) {
/*  59:139 */       throw new IllegalArgumentException("Cannot add key - Predicate rejected it");
/*  60:    */     }
/*  61:141 */     if ((this.valuePredicate != null) && (!this.valuePredicate.evaluate(value))) {
/*  62:142 */       throw new IllegalArgumentException("Cannot add value - Predicate rejected it");
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected Object checkSetValue(Object value)
/*  67:    */   {
/*  68:154 */     if (!this.valuePredicate.evaluate(value)) {
/*  69:155 */       throw new IllegalArgumentException("Cannot set value - Predicate rejected it");
/*  70:    */     }
/*  71:157 */     return value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected boolean isSetValueChecking()
/*  75:    */   {
/*  76:167 */     return this.valuePredicate != null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object put(Object key, Object value)
/*  80:    */   {
/*  81:172 */     validate(key, value);
/*  82:173 */     return this.map.put(key, value);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void putAll(Map mapToCopy)
/*  86:    */   {
/*  87:177 */     Iterator it = mapToCopy.entrySet().iterator();
/*  88:178 */     while (it.hasNext())
/*  89:    */     {
/*  90:179 */       Map.Entry entry = (Map.Entry)it.next();
/*  91:180 */       Object key = entry.getKey();
/*  92:181 */       Object value = entry.getValue();
/*  93:182 */       validate(key, value);
/*  94:    */     }
/*  95:184 */     this.map.putAll(mapToCopy);
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.PredicatedMap
 * JD-Core Version:    0.7.0.1
 */