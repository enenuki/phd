/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ 
/*  5:   */ public class NodeFilter
/*  6:   */   extends SimpleScriptable
/*  7:   */ {
/*  8:   */   public static final short FILTER_ACCEPT = 1;
/*  9:   */   public static final short FILTER_REJECT = 2;
/* 10:   */   public static final short FILTER_SKIP = 3;
/* 11:   */   public static final int SHOW_ALL = -1;
/* 12:   */   public static final int SHOW_ELEMENT = 1;
/* 13:   */   public static final int SHOW_ATTRIBUTE = 2;
/* 14:   */   public static final int SHOW_TEXT = 4;
/* 15:   */   public static final int SHOW_CDATA_SECTION = 8;
/* 16:   */   public static final int SHOW_ENTITY_REFERENCE = 16;
/* 17:   */   public static final int SHOW_ENTITY = 32;
/* 18:   */   public static final int SHOW_PROCESSING_INSTRUCTION = 64;
/* 19:   */   public static final int SHOW_COMMENT = 128;
/* 20:   */   public static final int SHOW_DOCUMENT = 256;
/* 21:   */   public static final int SHOW_DOCUMENT_TYPE = 512;
/* 22:   */   public static final int SHOW_DOCUMENT_FRAGMENT = 1024;
/* 23:   */   public static final int SHOW_NOTATION = 2048;
/* 24:   */   
/* 25:   */   public short acceptNode(Node n)
/* 26:   */   {
/* 27:85 */     return 1;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.NodeFilter
 * JD-Core Version:    0.7.0.1
 */