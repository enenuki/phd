/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomCharacterData;
/*   4:    */ 
/*   5:    */ public class CharacterDataImpl
/*   6:    */   extends Node
/*   7:    */ {
/*   8:    */   public Object jsxGet_data()
/*   9:    */   {
/*  10: 39 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  11: 40 */     return domCharacterData.getData();
/*  12:    */   }
/*  13:    */   
/*  14:    */   public void jsxSet_data(String newValue)
/*  15:    */   {
/*  16: 48 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  17: 49 */     domCharacterData.setData(newValue);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public int jsxGet_length()
/*  21:    */   {
/*  22: 57 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  23: 58 */     return domCharacterData.getLength();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void jsxFunction_appendData(String arg)
/*  27:    */   {
/*  28: 66 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  29: 67 */     domCharacterData.appendData(arg);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void jsxFunction_deleteData(int offset, int count)
/*  33:    */   {
/*  34: 76 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  35: 77 */     domCharacterData.deleteData(offset, count);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void jsxFunction_insertData(int offset, String arg)
/*  39:    */   {
/*  40: 87 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  41: 88 */     domCharacterData.insertData(offset, arg);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void jsxFunction_replaceData(int offset, int count, String arg)
/*  45:    */   {
/*  46:100 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  47:101 */     domCharacterData.replaceData(offset, count, arg);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String jsxFunction_substringData(int offset, int count)
/*  51:    */   {
/*  52:112 */     DomCharacterData domCharacterData = (DomCharacterData)getDomNodeOrDie();
/*  53:113 */     return domCharacterData.substringData(offset, count);
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.CharacterDataImpl
 * JD-Core Version:    0.7.0.1
 */