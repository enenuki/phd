/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebugFrame;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*  6:   */ 
/*  7:   */ public class DebuggerImpl
/*  8:   */   extends DebuggerAdapter
/*  9:   */ {
/* 10:   */   public DebugFrame getFrame(Context cx, DebuggableScript functionOrScript)
/* 11:   */   {
/* 12:54 */     return new DebugFrameImpl(functionOrScript);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.DebuggerImpl
 * JD-Core Version:    0.7.0.1
 */