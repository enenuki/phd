/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import org.dom4j.Element;
/*   6:    */ import org.dom4j.ElementHandler;
/*   7:    */ import org.dom4j.ElementPath;
/*   8:    */ 
/*   9:    */ class DispatchHandler
/*  10:    */   implements ElementHandler
/*  11:    */ {
/*  12:    */   private boolean atRoot;
/*  13:    */   private String path;
/*  14:    */   private ArrayList pathStack;
/*  15:    */   private ArrayList handlerStack;
/*  16:    */   private HashMap handlers;
/*  17:    */   private ElementHandler defaultHandler;
/*  18:    */   
/*  19:    */   public DispatchHandler()
/*  20:    */   {
/*  21: 58 */     this.atRoot = true;
/*  22: 59 */     this.path = "/";
/*  23: 60 */     this.pathStack = new ArrayList();
/*  24: 61 */     this.handlerStack = new ArrayList();
/*  25: 62 */     this.handlers = new HashMap();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void addHandler(String handlerPath, ElementHandler handler)
/*  29:    */   {
/*  30: 76 */     this.handlers.put(handlerPath, handler);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ElementHandler removeHandler(String handlerPath)
/*  34:    */   {
/*  35: 89 */     return (ElementHandler)this.handlers.remove(handlerPath);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean containsHandler(String handlerPath)
/*  39:    */   {
/*  40:102 */     return this.handlers.containsKey(handlerPath);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ElementHandler getHandler(String handlerPath)
/*  44:    */   {
/*  45:114 */     return (ElementHandler)this.handlers.get(handlerPath);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getActiveHandlerCount()
/*  49:    */   {
/*  50:124 */     return this.handlerStack.size();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setDefaultHandler(ElementHandler handler)
/*  54:    */   {
/*  55:137 */     this.defaultHandler = handler;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void resetHandlers()
/*  59:    */   {
/*  60:145 */     this.atRoot = true;
/*  61:146 */     this.path = "/";
/*  62:147 */     this.pathStack.clear();
/*  63:148 */     this.handlerStack.clear();
/*  64:149 */     this.handlers.clear();
/*  65:150 */     this.defaultHandler = null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getPath()
/*  69:    */   {
/*  70:159 */     return this.path;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void onStart(ElementPath elementPath)
/*  74:    */   {
/*  75:164 */     Element element = elementPath.getCurrent();
/*  76:    */     
/*  77:    */ 
/*  78:167 */     this.pathStack.add(this.path);
/*  79:170 */     if (this.atRoot)
/*  80:    */     {
/*  81:171 */       this.path += element.getName();
/*  82:172 */       this.atRoot = false;
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:174 */       this.path = (this.path + "/" + element.getName());
/*  87:    */     }
/*  88:177 */     if ((this.handlers != null) && (this.handlers.containsKey(this.path)))
/*  89:    */     {
/*  90:180 */       ElementHandler handler = (ElementHandler)this.handlers.get(this.path);
/*  91:181 */       this.handlerStack.add(handler);
/*  92:    */       
/*  93:    */ 
/*  94:184 */       handler.onStart(elementPath);
/*  95:    */     }
/*  96:188 */     else if ((this.handlerStack.isEmpty()) && (this.defaultHandler != null))
/*  97:    */     {
/*  98:189 */       this.defaultHandler.onStart(elementPath);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void onEnd(ElementPath elementPath)
/* 103:    */   {
/* 104:195 */     if ((this.handlers != null) && (this.handlers.containsKey(this.path)))
/* 105:    */     {
/* 106:198 */       ElementHandler handler = (ElementHandler)this.handlers.get(this.path);
/* 107:199 */       this.handlerStack.remove(this.handlerStack.size() - 1);
/* 108:    */       
/* 109:    */ 
/* 110:202 */       handler.onEnd(elementPath);
/* 111:    */     }
/* 112:206 */     else if ((this.handlerStack.isEmpty()) && (this.defaultHandler != null))
/* 113:    */     {
/* 114:207 */       this.defaultHandler.onEnd(elementPath);
/* 115:    */     }
/* 116:212 */     this.path = ((String)this.pathStack.remove(this.pathStack.size() - 1));
/* 117:214 */     if (this.pathStack.size() == 0) {
/* 118:215 */       this.atRoot = true;
/* 119:    */     }
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DispatchHandler
 * JD-Core Version:    0.7.0.1
 */