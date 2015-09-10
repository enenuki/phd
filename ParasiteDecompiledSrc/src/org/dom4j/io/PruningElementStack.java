/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.ElementHandler;
/*   5:    */ 
/*   6:    */ class PruningElementStack
/*   7:    */   extends ElementStack
/*   8:    */ {
/*   9:    */   private ElementHandler elementHandler;
/*  10:    */   private String[] path;
/*  11:    */   private int matchingElementIndex;
/*  12:    */   
/*  13:    */   public PruningElementStack(String[] path, ElementHandler elementHandler)
/*  14:    */   {
/*  15: 44 */     this.path = path;
/*  16: 45 */     this.elementHandler = elementHandler;
/*  17: 46 */     checkPath();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public PruningElementStack(String[] path, ElementHandler elementHandler, int defaultCapacity)
/*  21:    */   {
/*  22: 51 */     super(defaultCapacity);
/*  23: 52 */     this.path = path;
/*  24: 53 */     this.elementHandler = elementHandler;
/*  25: 54 */     checkPath();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Element popElement()
/*  29:    */   {
/*  30: 58 */     Element answer = super.popElement();
/*  31: 60 */     if ((this.lastElementIndex == this.matchingElementIndex) && (this.lastElementIndex >= 0)) {
/*  32: 67 */       if (validElement(answer, this.lastElementIndex + 1))
/*  33:    */       {
/*  34: 68 */         Element parent = null;
/*  35: 70 */         for (int i = 0; i <= this.lastElementIndex; i++)
/*  36:    */         {
/*  37: 71 */           parent = this.stack[i];
/*  38: 73 */           if (!validElement(parent, i))
/*  39:    */           {
/*  40: 74 */             parent = null;
/*  41:    */             
/*  42: 76 */             break;
/*  43:    */           }
/*  44:    */         }
/*  45: 80 */         if (parent != null) {
/*  46: 81 */           pathMatches(parent, answer);
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50: 86 */     return answer;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void pathMatches(Element parent, Element selectedNode)
/*  54:    */   {
/*  55: 90 */     this.elementHandler.onEnd(this);
/*  56: 91 */     parent.remove(selectedNode);
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected boolean validElement(Element element, int index)
/*  60:    */   {
/*  61: 95 */     String requiredName = this.path[index];
/*  62: 96 */     String name = element.getName();
/*  63: 98 */     if (requiredName == name) {
/*  64: 99 */       return true;
/*  65:    */     }
/*  66:102 */     if ((requiredName != null) && (name != null)) {
/*  67:103 */       return requiredName.equals(name);
/*  68:    */     }
/*  69:106 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void checkPath()
/*  73:    */   {
/*  74:110 */     if (this.path.length < 2) {
/*  75:111 */       throw new RuntimeException("Invalid path of length: " + this.path.length + " it must be greater than 2");
/*  76:    */     }
/*  77:115 */     this.matchingElementIndex = (this.path.length - 2);
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.PruningElementStack
 * JD-Core Version:    0.7.0.1
 */