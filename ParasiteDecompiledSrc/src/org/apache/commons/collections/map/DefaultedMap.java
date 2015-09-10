/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.commons.collections.Factory;
/*  10:    */ import org.apache.commons.collections.Transformer;
/*  11:    */ import org.apache.commons.collections.functors.ConstantTransformer;
/*  12:    */ import org.apache.commons.collections.functors.FactoryTransformer;
/*  13:    */ 
/*  14:    */ public class DefaultedMap
/*  15:    */   extends AbstractMapDecorator
/*  16:    */   implements Map, Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 19698628745827L;
/*  19:    */   protected final Object value;
/*  20:    */   
/*  21:    */   public static Map decorate(Map map, Object defaultValue)
/*  22:    */   {
/*  23: 87 */     if ((defaultValue instanceof Transformer)) {
/*  24: 88 */       defaultValue = ConstantTransformer.getInstance(defaultValue);
/*  25:    */     }
/*  26: 90 */     return new DefaultedMap(map, defaultValue);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Map decorate(Map map, Factory factory)
/*  30:    */   {
/*  31:104 */     if (factory == null) {
/*  32:105 */       throw new IllegalArgumentException("Factory must not be null");
/*  33:    */     }
/*  34:107 */     return new DefaultedMap(map, FactoryTransformer.getInstance(factory));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static Map decorate(Map map, Transformer factory)
/*  38:    */   {
/*  39:122 */     if (factory == null) {
/*  40:123 */       throw new IllegalArgumentException("Transformer must not be null");
/*  41:    */     }
/*  42:125 */     return new DefaultedMap(map, factory);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public DefaultedMap(Object defaultValue)
/*  46:    */   {
/*  47:139 */     super(new HashMap());
/*  48:140 */     if ((defaultValue instanceof Transformer)) {
/*  49:141 */       defaultValue = ConstantTransformer.getInstance(defaultValue);
/*  50:    */     }
/*  51:143 */     this.value = defaultValue;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected DefaultedMap(Map map, Object value)
/*  55:    */   {
/*  56:154 */     super(map);
/*  57:155 */     this.value = value;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void writeObject(ObjectOutputStream out)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:166 */     out.defaultWriteObject();
/*  64:167 */     out.writeObject(this.map);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void readObject(ObjectInputStream in)
/*  68:    */     throws IOException, ClassNotFoundException
/*  69:    */   {
/*  70:178 */     in.defaultReadObject();
/*  71:179 */     this.map = ((Map)in.readObject());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object get(Object key)
/*  75:    */   {
/*  76:185 */     if (!this.map.containsKey(key))
/*  77:    */     {
/*  78:186 */       if ((this.value instanceof Transformer)) {
/*  79:187 */         return ((Transformer)this.value).transform(key);
/*  80:    */       }
/*  81:189 */       return this.value;
/*  82:    */     }
/*  83:191 */     return this.map.get(key);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.DefaultedMap
 * JD-Core Version:    0.7.0.1
 */