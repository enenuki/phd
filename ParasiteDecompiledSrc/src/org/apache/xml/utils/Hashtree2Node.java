/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.w3c.dom.Document;
/*   9:    */ import org.w3c.dom.Element;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ 
/*  12:    */ public abstract class Hashtree2Node
/*  13:    */ {
/*  14:    */   public static void appendHashToNode(Hashtable hash, String name, Node container, Document factory)
/*  15:    */   {
/*  16: 69 */     if ((null == container) || (null == factory) || (null == hash)) {
/*  17: 71 */       return;
/*  18:    */     }
/*  19: 75 */     String elemName = null;
/*  20: 76 */     if ((null == name) || ("".equals(name))) {
/*  21: 77 */       elemName = "appendHashToNode";
/*  22:    */     } else {
/*  23: 79 */       elemName = name;
/*  24:    */     }
/*  25:    */     try
/*  26:    */     {
/*  27: 83 */       Element hashNode = factory.createElement(elemName);
/*  28: 84 */       container.appendChild(hashNode);
/*  29:    */       
/*  30: 86 */       Enumeration keys = hash.keys();
/*  31: 87 */       List v = new ArrayList();
/*  32: 89 */       while (keys.hasMoreElements())
/*  33:    */       {
/*  34: 91 */         Object key = keys.nextElement();
/*  35: 92 */         String keyStr = key.toString();
/*  36: 93 */         Object item = hash.get(key);
/*  37: 95 */         if ((item instanceof Hashtable))
/*  38:    */         {
/*  39:100 */           v.add(keyStr);
/*  40:101 */           v.add((Hashtable)item);
/*  41:    */         }
/*  42:    */         else
/*  43:    */         {
/*  44:    */           try
/*  45:    */           {
/*  46:108 */             Element node = factory.createElement("item");
/*  47:109 */             node.setAttribute("key", keyStr);
/*  48:110 */             node.appendChild(factory.createTextNode((String)item));
/*  49:111 */             hashNode.appendChild(node);
/*  50:    */           }
/*  51:    */           catch (Exception e)
/*  52:    */           {
/*  53:115 */             Element node = factory.createElement("item");
/*  54:116 */             node.setAttribute("key", keyStr);
/*  55:117 */             node.appendChild(factory.createTextNode("ERROR: Reading " + key + " threw: " + e.toString()));
/*  56:118 */             hashNode.appendChild(node);
/*  57:    */           }
/*  58:    */         }
/*  59:    */       }
/*  60:124 */       Iterator it = v.iterator();
/*  61:125 */       while (it.hasNext())
/*  62:    */       {
/*  63:128 */         String n = (String)it.next();
/*  64:129 */         Hashtable h = (Hashtable)it.next();
/*  65:    */         
/*  66:131 */         appendHashToNode(h, n, hashNode, factory);
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (Exception e2)
/*  70:    */     {
/*  71:138 */       e2.printStackTrace();
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.Hashtree2Node
 * JD-Core Version:    0.7.0.1
 */