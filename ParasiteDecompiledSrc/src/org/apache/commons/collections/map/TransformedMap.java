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
/*  11:    */ import org.apache.commons.collections.Transformer;
/*  12:    */ 
/*  13:    */ public class TransformedMap
/*  14:    */   extends AbstractInputCheckedMapDecorator
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 7023152376788900464L;
/*  18:    */   protected final Transformer keyTransformer;
/*  19:    */   protected final Transformer valueTransformer;
/*  20:    */   
/*  21:    */   public static Map decorate(Map map, Transformer keyTransformer, Transformer valueTransformer)
/*  22:    */   {
/*  23: 74 */     return new TransformedMap(map, keyTransformer, valueTransformer);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Map decorateTransform(Map map, Transformer keyTransformer, Transformer valueTransformer)
/*  27:    */   {
/*  28: 92 */     TransformedMap decorated = new TransformedMap(map, keyTransformer, valueTransformer);
/*  29: 93 */     if (map.size() > 0)
/*  30:    */     {
/*  31: 94 */       Map transformed = decorated.transformMap(map);
/*  32: 95 */       decorated.clear();
/*  33: 96 */       decorated.getMap().putAll(transformed);
/*  34:    */     }
/*  35: 98 */     return decorated;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected TransformedMap(Map map, Transformer keyTransformer, Transformer valueTransformer)
/*  39:    */   {
/*  40:114 */     super(map);
/*  41:115 */     this.keyTransformer = keyTransformer;
/*  42:116 */     this.valueTransformer = valueTransformer;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void writeObject(ObjectOutputStream out)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:128 */     out.defaultWriteObject();
/*  49:129 */     out.writeObject(this.map);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void readObject(ObjectInputStream in)
/*  53:    */     throws IOException, ClassNotFoundException
/*  54:    */   {
/*  55:141 */     in.defaultReadObject();
/*  56:142 */     this.map = ((Map)in.readObject());
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Object transformKey(Object object)
/*  60:    */   {
/*  61:155 */     if (this.keyTransformer == null) {
/*  62:156 */       return object;
/*  63:    */     }
/*  64:158 */     return this.keyTransformer.transform(object);
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Object transformValue(Object object)
/*  68:    */   {
/*  69:170 */     if (this.valueTransformer == null) {
/*  70:171 */       return object;
/*  71:    */     }
/*  72:173 */     return this.valueTransformer.transform(object);
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected Map transformMap(Map map)
/*  76:    */   {
/*  77:185 */     if (map.isEmpty()) {
/*  78:186 */       return map;
/*  79:    */     }
/*  80:188 */     Map result = new LinkedMap(map.size());
/*  81:189 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/*  82:    */     {
/*  83:190 */       Map.Entry entry = (Map.Entry)it.next();
/*  84:191 */       result.put(transformKey(entry.getKey()), transformValue(entry.getValue()));
/*  85:    */     }
/*  86:193 */     return result;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Object checkSetValue(Object value)
/*  90:    */   {
/*  91:204 */     return this.valueTransformer.transform(value);
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected boolean isSetValueChecking()
/*  95:    */   {
/*  96:214 */     return this.valueTransformer != null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Object put(Object key, Object value)
/* 100:    */   {
/* 101:219 */     key = transformKey(key);
/* 102:220 */     value = transformValue(value);
/* 103:221 */     return getMap().put(key, value);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void putAll(Map mapToCopy)
/* 107:    */   {
/* 108:225 */     mapToCopy = transformMap(mapToCopy);
/* 109:226 */     getMap().putAll(mapToCopy);
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.TransformedMap
 * JD-Core Version:    0.7.0.1
 */