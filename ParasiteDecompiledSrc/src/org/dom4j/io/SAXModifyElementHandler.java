/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.dom4j.Document;
/*   5:    */ import org.dom4j.Element;
/*   6:    */ import org.dom4j.ElementHandler;
/*   7:    */ import org.dom4j.ElementPath;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ 
/*  10:    */ class SAXModifyElementHandler
/*  11:    */   implements ElementHandler
/*  12:    */ {
/*  13:    */   private ElementModifier elemModifier;
/*  14:    */   private Element modifiedElement;
/*  15:    */   
/*  16:    */   public SAXModifyElementHandler(ElementModifier elemModifier)
/*  17:    */   {
/*  18: 34 */     this.elemModifier = elemModifier;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void onStart(ElementPath elementPath)
/*  22:    */   {
/*  23: 38 */     this.modifiedElement = elementPath.getCurrent();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void onEnd(ElementPath elementPath)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 43 */       Element origElement = elementPath.getCurrent();
/*  31: 44 */       Element currentParent = origElement.getParent();
/*  32: 46 */       if (currentParent != null)
/*  33:    */       {
/*  34: 48 */         Element clonedElem = (Element)origElement.clone();
/*  35:    */         
/*  36:    */ 
/*  37: 51 */         this.modifiedElement = this.elemModifier.modifyElement(clonedElem);
/*  38: 53 */         if (this.modifiedElement != null)
/*  39:    */         {
/*  40: 55 */           this.modifiedElement.setParent(origElement.getParent());
/*  41: 56 */           this.modifiedElement.setDocument(origElement.getDocument());
/*  42:    */           
/*  43:    */ 
/*  44: 59 */           int contentIndex = currentParent.indexOf(origElement);
/*  45: 60 */           currentParent.content().set(contentIndex, this.modifiedElement);
/*  46:    */         }
/*  47: 64 */         origElement.detach();
/*  48:    */       }
/*  49: 66 */       else if (origElement.isRootElement())
/*  50:    */       {
/*  51: 68 */         Element clonedElem = (Element)origElement.clone();
/*  52:    */         
/*  53:    */ 
/*  54: 71 */         this.modifiedElement = this.elemModifier.modifyElement(clonedElem);
/*  55: 73 */         if (this.modifiedElement != null)
/*  56:    */         {
/*  57: 75 */           this.modifiedElement.setDocument(origElement.getDocument());
/*  58:    */           
/*  59:    */ 
/*  60: 78 */           Document doc = origElement.getDocument();
/*  61: 79 */           doc.setRootElement(this.modifiedElement);
/*  62:    */         }
/*  63: 83 */         origElement.detach();
/*  64:    */       }
/*  65: 89 */       if ((elementPath instanceof ElementStack))
/*  66:    */       {
/*  67: 90 */         ElementStack elementStack = (ElementStack)elementPath;
/*  68: 91 */         elementStack.popElement();
/*  69: 92 */         elementStack.pushElement(this.modifiedElement);
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (Exception ex)
/*  73:    */     {
/*  74: 95 */       throw new SAXModifyException(ex);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected Element getModifiedElement()
/*  79:    */   {
/*  80:105 */     return this.modifiedElement;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXModifyElementHandler
 * JD-Core Version:    0.7.0.1
 */