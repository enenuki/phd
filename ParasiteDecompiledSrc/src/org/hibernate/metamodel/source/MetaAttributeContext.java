/*  1:   */ package org.hibernate.metamodel.source;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.Set;
/*  5:   */ import java.util.concurrent.ConcurrentHashMap;
/*  6:   */ import org.hibernate.metamodel.binding.MetaAttribute;
/*  7:   */ 
/*  8:   */ public class MetaAttributeContext
/*  9:   */ {
/* 10:   */   private final MetaAttributeContext parentContext;
/* 11:37 */   private final ConcurrentHashMap<String, MetaAttribute> metaAttributeMap = new ConcurrentHashMap();
/* 12:   */   
/* 13:   */   public MetaAttributeContext()
/* 14:   */   {
/* 15:40 */     this(null);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MetaAttributeContext(MetaAttributeContext parentContext)
/* 19:   */   {
/* 20:44 */     this.parentContext = parentContext;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Iterable<String> getKeys()
/* 24:   */   {
/* 25:51 */     HashSet<String> keys = new HashSet();
/* 26:52 */     addKeys(keys);
/* 27:53 */     return keys;
/* 28:   */   }
/* 29:   */   
/* 30:   */   private void addKeys(Set<String> keys)
/* 31:   */   {
/* 32:57 */     keys.addAll(this.metaAttributeMap.keySet());
/* 33:58 */     if (this.parentContext != null) {
/* 34:60 */       this.parentContext.addKeys(keys);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Iterable<String> getLocalKeys()
/* 39:   */   {
/* 40:65 */     return this.metaAttributeMap.keySet();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public MetaAttribute getMetaAttribute(String key)
/* 44:   */   {
/* 45:69 */     MetaAttribute value = getLocalMetaAttribute(key);
/* 46:70 */     if (value == null) {
/* 47:72 */       value = this.parentContext.getMetaAttribute(key);
/* 48:   */     }
/* 49:74 */     return value;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public MetaAttribute getLocalMetaAttribute(String key)
/* 53:   */   {
/* 54:78 */     return (MetaAttribute)this.metaAttributeMap.get(key);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void add(MetaAttribute metaAttribute)
/* 58:   */   {
/* 59:85 */     this.metaAttributeMap.put(metaAttribute.getName(), metaAttribute);
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MetaAttributeContext
 * JD-Core Version:    0.7.0.1
 */