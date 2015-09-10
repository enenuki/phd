/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebugFrame;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.Debugger;
/*  7:   */ 
/*  8:   */ public class DebuggerAdapter
/*  9:   */   implements Debugger
/* 10:   */ {
/* 11:   */   public void handleCompilationDone(Context cx, DebuggableScript functionOrScript, String source) {}
/* 12:   */   
/* 13:   */   public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript)
/* 14:   */   {
/* 15:38 */     return new DebugFrameAdapter();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.DebuggerAdapter
 * JD-Core Version:    0.7.0.1
 */