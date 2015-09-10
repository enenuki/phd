/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.WeakHashMap;
/*  11:    */ import org.dom4j.DocumentFactory;
/*  12:    */ import org.dom4j.Namespace;
/*  13:    */ import org.dom4j.QName;
/*  14:    */ 
/*  15:    */ public class QNameCache
/*  16:    */ {
/*  17: 36 */   protected Map noNamespaceCache = Collections.synchronizedMap(new WeakHashMap());
/*  18: 43 */   protected Map namespaceCache = Collections.synchronizedMap(new WeakHashMap());
/*  19:    */   private DocumentFactory documentFactory;
/*  20:    */   
/*  21:    */   public QNameCache() {}
/*  22:    */   
/*  23:    */   public QNameCache(DocumentFactory documentFactory)
/*  24:    */   {
/*  25: 56 */     this.documentFactory = documentFactory;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List getQNames()
/*  29:    */   {
/*  30: 65 */     List answer = new ArrayList();
/*  31: 66 */     answer.addAll(this.noNamespaceCache.values());
/*  32: 68 */     for (Iterator it = this.namespaceCache.values().iterator(); it.hasNext();)
/*  33:    */     {
/*  34: 69 */       Map map = (Map)it.next();
/*  35: 70 */       answer.addAll(map.values());
/*  36:    */     }
/*  37: 73 */     return answer;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public QName get(String name)
/*  41:    */   {
/*  42: 85 */     QName answer = null;
/*  43: 87 */     if (name != null) {
/*  44: 88 */       answer = (QName)this.noNamespaceCache.get(name);
/*  45:    */     } else {
/*  46: 90 */       name = "";
/*  47:    */     }
/*  48: 93 */     if (answer == null)
/*  49:    */     {
/*  50: 94 */       answer = createQName(name);
/*  51: 95 */       answer.setDocumentFactory(this.documentFactory);
/*  52: 96 */       this.noNamespaceCache.put(name, answer);
/*  53:    */     }
/*  54: 99 */     return answer;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public QName get(String name, Namespace namespace)
/*  58:    */   {
/*  59:113 */     Map cache = getNamespaceCache(namespace);
/*  60:114 */     QName answer = null;
/*  61:116 */     if (name != null) {
/*  62:117 */       answer = (QName)cache.get(name);
/*  63:    */     } else {
/*  64:119 */       name = "";
/*  65:    */     }
/*  66:122 */     if (answer == null)
/*  67:    */     {
/*  68:123 */       answer = createQName(name, namespace);
/*  69:124 */       answer.setDocumentFactory(this.documentFactory);
/*  70:125 */       cache.put(name, answer);
/*  71:    */     }
/*  72:128 */     return answer;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public QName get(String localName, Namespace namespace, String qName)
/*  76:    */   {
/*  77:144 */     Map cache = getNamespaceCache(namespace);
/*  78:145 */     QName answer = null;
/*  79:147 */     if (localName != null) {
/*  80:148 */       answer = (QName)cache.get(localName);
/*  81:    */     } else {
/*  82:150 */       localName = "";
/*  83:    */     }
/*  84:153 */     if (answer == null)
/*  85:    */     {
/*  86:154 */       answer = createQName(localName, namespace, qName);
/*  87:155 */       answer.setDocumentFactory(this.documentFactory);
/*  88:156 */       cache.put(localName, answer);
/*  89:    */     }
/*  90:159 */     return answer;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public QName get(String qualifiedName, String uri)
/*  94:    */   {
/*  95:163 */     int index = qualifiedName.indexOf(':');
/*  96:165 */     if (index < 0) {
/*  97:166 */       return get(qualifiedName, Namespace.get(uri));
/*  98:    */     }
/*  99:168 */     String name = qualifiedName.substring(index + 1);
/* 100:169 */     String prefix = qualifiedName.substring(0, index);
/* 101:    */     
/* 102:171 */     return get(name, Namespace.get(prefix, uri));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public QName intern(QName qname)
/* 106:    */   {
/* 107:185 */     return get(qname.getName(), qname.getNamespace(), qname.getQualifiedName());
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected Map getNamespaceCache(Namespace namespace)
/* 111:    */   {
/* 112:199 */     if (namespace == Namespace.NO_NAMESPACE) {
/* 113:200 */       return this.noNamespaceCache;
/* 114:    */     }
/* 115:203 */     Map answer = null;
/* 116:205 */     if (namespace != null) {
/* 117:206 */       answer = (Map)this.namespaceCache.get(namespace);
/* 118:    */     }
/* 119:209 */     if (answer == null)
/* 120:    */     {
/* 121:210 */       answer = createMap();
/* 122:211 */       this.namespaceCache.put(namespace, answer);
/* 123:    */     }
/* 124:214 */     return answer;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected Map createMap()
/* 128:    */   {
/* 129:223 */     return Collections.synchronizedMap(new HashMap());
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected QName createQName(String name)
/* 133:    */   {
/* 134:236 */     return new QName(name);
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected QName createQName(String name, Namespace namespace)
/* 138:    */   {
/* 139:251 */     return new QName(name, namespace);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected QName createQName(String name, Namespace namespace, String qualifiedName)
/* 143:    */   {
/* 144:269 */     return new QName(name, namespace, qualifiedName);
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.QNameCache
 * JD-Core Version:    0.7.0.1
 */