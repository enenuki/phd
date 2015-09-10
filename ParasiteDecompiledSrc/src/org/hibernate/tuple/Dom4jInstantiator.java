/*  1:   */ package org.hibernate.tuple;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import org.dom4j.Element;
/*  7:   */ import org.hibernate.internal.util.xml.XMLHelper;
/*  8:   */ import org.hibernate.mapping.Component;
/*  9:   */ import org.hibernate.mapping.PersistentClass;
/* 10:   */ 
/* 11:   */ public class Dom4jInstantiator
/* 12:   */   implements Instantiator
/* 13:   */ {
/* 14:   */   private final String nodeName;
/* 15:41 */   private final HashSet isInstanceNodeNames = new HashSet();
/* 16:   */   
/* 17:   */   public Dom4jInstantiator(Component component)
/* 18:   */   {
/* 19:44 */     this.nodeName = component.getNodeName();
/* 20:45 */     this.isInstanceNodeNames.add(this.nodeName);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Dom4jInstantiator(PersistentClass mappingInfo)
/* 24:   */   {
/* 25:49 */     this.nodeName = mappingInfo.getNodeName();
/* 26:50 */     this.isInstanceNodeNames.add(this.nodeName);
/* 27:52 */     if (mappingInfo.hasSubclasses())
/* 28:   */     {
/* 29:53 */       Iterator itr = mappingInfo.getSubclassClosureIterator();
/* 30:54 */       while (itr.hasNext())
/* 31:   */       {
/* 32:55 */         PersistentClass subclassInfo = (PersistentClass)itr.next();
/* 33:56 */         this.isInstanceNodeNames.add(subclassInfo.getNodeName());
/* 34:   */       }
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object instantiate(Serializable id)
/* 39:   */   {
/* 40:62 */     return instantiate();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Object instantiate()
/* 44:   */   {
/* 45:66 */     return XMLHelper.generateDom4jElement(this.nodeName);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean isInstance(Object object)
/* 49:   */   {
/* 50:70 */     if ((object instanceof Element)) {
/* 51:71 */       return this.isInstanceNodeNames.contains(((Element)object).getName());
/* 52:   */     }
/* 53:74 */     return false;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.Dom4jInstantiator
 * JD-Core Version:    0.7.0.1
 */