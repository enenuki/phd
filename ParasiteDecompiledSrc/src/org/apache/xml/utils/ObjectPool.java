/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.apache.xml.res.XMLMessages;
/*   6:    */ 
/*   7:    */ public class ObjectPool
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   static final long serialVersionUID = -8519013691660936643L;
/*  11:    */   private final Class objectType;
/*  12:    */   private final ArrayList freeStack;
/*  13:    */   
/*  14:    */   public ObjectPool(Class type)
/*  15:    */   {
/*  16: 52 */     this.objectType = type;
/*  17: 53 */     this.freeStack = new ArrayList();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ObjectPool(String className)
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 65 */       this.objectType = ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
/*  25:    */     }
/*  26:    */     catch (ClassNotFoundException cnfe)
/*  27:    */     {
/*  28: 70 */       throw new WrappedRuntimeException(cnfe);
/*  29:    */     }
/*  30: 72 */     this.freeStack = new ArrayList();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ObjectPool(Class type, int size)
/*  34:    */   {
/*  35: 85 */     this.objectType = type;
/*  36: 86 */     this.freeStack = new ArrayList(size);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ObjectPool()
/*  40:    */   {
/*  41: 95 */     this.objectType = null;
/*  42: 96 */     this.freeStack = new ArrayList();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public synchronized Object getInstanceIfFree()
/*  46:    */   {
/*  47:109 */     if (!this.freeStack.isEmpty())
/*  48:    */     {
/*  49:113 */       Object result = this.freeStack.remove(this.freeStack.size() - 1);
/*  50:114 */       return result;
/*  51:    */     }
/*  52:117 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public synchronized Object getInstance()
/*  56:    */   {
/*  57:130 */     if (this.freeStack.isEmpty())
/*  58:    */     {
/*  59:    */       try
/*  60:    */       {
/*  61:136 */         return this.objectType.newInstance();
/*  62:    */       }
/*  63:    */       catch (InstantiationException ex) {}catch (IllegalAccessException ex) {}
/*  64:142 */       throw new RuntimeException(XMLMessages.createXMLMessage("ER_EXCEPTION_CREATING_POOL", null));
/*  65:    */     }
/*  66:148 */     Object result = this.freeStack.remove(this.freeStack.size() - 1);
/*  67:    */     
/*  68:150 */     return result;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized void freeInstance(Object obj)
/*  72:    */   {
/*  73:167 */     this.freeStack.add(obj);
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.ObjectPool
 * JD-Core Version:    0.7.0.1
 */