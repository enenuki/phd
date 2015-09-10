/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import javax.swing.JCheckBoxMenuItem;
/*   7:    */ import javax.swing.JFrame;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.tools.shell.Global;
/*  13:    */ 
/*  14:    */ public class Main
/*  15:    */ {
/*  16:    */   private Dim dim;
/*  17:    */   private SwingGui debugGui;
/*  18:    */   
/*  19:    */   public Main(String title)
/*  20:    */   {
/*  21: 72 */     this.dim = new Dim();
/*  22: 73 */     this.debugGui = new SwingGui(this.dim, title);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public JFrame getDebugFrame()
/*  26:    */   {
/*  27: 80 */     return this.debugGui;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void doBreak()
/*  31:    */   {
/*  32: 87 */     this.dim.setBreak();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setBreakOnExceptions(boolean value)
/*  36:    */   {
/*  37: 94 */     this.dim.setBreakOnExceptions(value);
/*  38: 95 */     this.debugGui.getMenubar().getBreakOnExceptions().setSelected(value);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setBreakOnEnter(boolean value)
/*  42:    */   {
/*  43:102 */     this.dim.setBreakOnEnter(value);
/*  44:103 */     this.debugGui.getMenubar().getBreakOnEnter().setSelected(value);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setBreakOnReturn(boolean value)
/*  48:    */   {
/*  49:110 */     this.dim.setBreakOnReturn(value);
/*  50:111 */     this.debugGui.getMenubar().getBreakOnReturn().setSelected(value);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void clearAllBreakpoints()
/*  54:    */   {
/*  55:118 */     this.dim.clearAllBreakpoints();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void go()
/*  59:    */   {
/*  60:125 */     this.dim.go();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setScope(Scriptable scope)
/*  64:    */   {
/*  65:132 */     setScopeProvider(IProxy.newScopeProvider(scope));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setScopeProvider(ScopeProvider p)
/*  69:    */   {
/*  70:140 */     this.dim.setScopeProvider(p);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setSourceProvider(SourceProvider sourceProvider)
/*  74:    */   {
/*  75:148 */     this.dim.setSourceProvider(sourceProvider);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setExitAction(Runnable r)
/*  79:    */   {
/*  80:156 */     this.debugGui.setExitAction(r);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public InputStream getIn()
/*  84:    */   {
/*  85:164 */     return this.debugGui.getConsole().getIn();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public PrintStream getOut()
/*  89:    */   {
/*  90:172 */     return this.debugGui.getConsole().getOut();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public PrintStream getErr()
/*  94:    */   {
/*  95:180 */     return this.debugGui.getConsole().getErr();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void pack()
/*  99:    */   {
/* 100:187 */     this.debugGui.pack();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setSize(int w, int h)
/* 104:    */   {
/* 105:194 */     this.debugGui.setSize(w, h);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setVisible(boolean flag)
/* 109:    */   {
/* 110:201 */     this.debugGui.setVisible(flag);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isVisible()
/* 114:    */   {
/* 115:208 */     return this.debugGui.isVisible();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void dispose()
/* 119:    */   {
/* 120:215 */     clearAllBreakpoints();
/* 121:216 */     this.dim.go();
/* 122:217 */     this.debugGui.dispose();
/* 123:218 */     this.dim = null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void attachTo(ContextFactory factory)
/* 127:    */   {
/* 128:225 */     this.dim.attachTo(factory);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void detach()
/* 132:    */   {
/* 133:232 */     this.dim.detach();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void main(String[] args)
/* 137:    */   {
/* 138:240 */     Main main = new Main("Rhino JavaScript Debugger");
/* 139:241 */     main.doBreak();
/* 140:242 */     main.setExitAction(new IProxy(1));
/* 141:    */     
/* 142:244 */     System.setIn(main.getIn());
/* 143:245 */     System.setOut(main.getOut());
/* 144:246 */     System.setErr(main.getErr());
/* 145:    */     
/* 146:248 */     Global global = net.sourceforge.htmlunit.corejs.javascript.tools.shell.Main.getGlobal();
/* 147:249 */     global.setIn(main.getIn());
/* 148:250 */     global.setOut(main.getOut());
/* 149:251 */     global.setErr(main.getErr());
/* 150:    */     
/* 151:253 */     main.attachTo(net.sourceforge.htmlunit.corejs.javascript.tools.shell.Main.shellContextFactory);
/* 152:    */     
/* 153:    */ 
/* 154:256 */     main.setScope(global);
/* 155:    */     
/* 156:258 */     main.pack();
/* 157:259 */     main.setSize(600, 460);
/* 158:260 */     main.setVisible(true);
/* 159:    */     
/* 160:262 */     net.sourceforge.htmlunit.corejs.javascript.tools.shell.Main.exec(args);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Main mainEmbedded(String title)
/* 164:    */   {
/* 165:272 */     ContextFactory factory = ContextFactory.getGlobal();
/* 166:273 */     Global global = new Global();
/* 167:274 */     global.init(factory);
/* 168:275 */     return mainEmbedded(factory, global, title);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static Main mainEmbedded(ContextFactory factory, Scriptable scope, String title)
/* 172:    */   {
/* 173:286 */     return mainEmbeddedImpl(factory, scope, title);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static Main mainEmbedded(ContextFactory factory, ScopeProvider scopeProvider, String title)
/* 177:    */   {
/* 178:297 */     return mainEmbeddedImpl(factory, scopeProvider, title);
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static Main mainEmbeddedImpl(ContextFactory factory, Object scopeProvider, String title)
/* 182:    */   {
/* 183:306 */     if (title == null) {
/* 184:307 */       title = "Rhino JavaScript Debugger (embedded usage)";
/* 185:    */     }
/* 186:309 */     Main main = new Main(title);
/* 187:310 */     main.doBreak();
/* 188:311 */     main.setExitAction(new IProxy(1));
/* 189:    */     
/* 190:313 */     main.attachTo(factory);
/* 191:314 */     if ((scopeProvider instanceof ScopeProvider))
/* 192:    */     {
/* 193:315 */       main.setScopeProvider((ScopeProvider)scopeProvider);
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:317 */       Scriptable scope = (Scriptable)scopeProvider;
/* 198:318 */       if ((scope instanceof Global))
/* 199:    */       {
/* 200:319 */         Global global = (Global)scope;
/* 201:320 */         global.setIn(main.getIn());
/* 202:321 */         global.setOut(main.getOut());
/* 203:322 */         global.setErr(main.getErr());
/* 204:    */       }
/* 205:324 */       main.setScope(scope);
/* 206:    */     }
/* 207:327 */     main.pack();
/* 208:328 */     main.setSize(600, 460);
/* 209:329 */     main.setVisible(true);
/* 210:330 */     return main;
/* 211:    */   }
/* 212:    */   
/* 213:    */   /**
/* 214:    */    * @deprecated
/* 215:    */    */
/* 216:    */   public void setSize(Dimension dimension)
/* 217:    */   {
/* 218:339 */     this.debugGui.setSize(dimension.width, dimension.height);
/* 219:    */   }
/* 220:    */   
/* 221:    */   /**
/* 222:    */    * @deprecated
/* 223:    */    */
/* 224:    */   public void setOptimizationLevel(int level) {}
/* 225:    */   
/* 226:    */   /**
/* 227:    */    * @deprecated
/* 228:    */    */
/* 229:    */   public void contextEntered(Context cx)
/* 230:    */   {
/* 231:354 */     throw new IllegalStateException();
/* 232:    */   }
/* 233:    */   
/* 234:    */   /**
/* 235:    */    * @deprecated
/* 236:    */    */
/* 237:    */   public void contextExited(Context cx)
/* 238:    */   {
/* 239:362 */     throw new IllegalStateException();
/* 240:    */   }
/* 241:    */   
/* 242:    */   /**
/* 243:    */    * @deprecated
/* 244:    */    */
/* 245:    */   public void contextCreated(Context cx)
/* 246:    */   {
/* 247:370 */     throw new IllegalStateException();
/* 248:    */   }
/* 249:    */   
/* 250:    */   /**
/* 251:    */    * @deprecated
/* 252:    */    */
/* 253:    */   public void contextReleased(Context cx)
/* 254:    */   {
/* 255:379 */     throw new IllegalStateException();
/* 256:    */   }
/* 257:    */   
/* 258:    */   private static class IProxy
/* 259:    */     implements Runnable, ScopeProvider
/* 260:    */   {
/* 261:    */     public static final int EXIT_ACTION = 1;
/* 262:    */     public static final int SCOPE_PROVIDER = 2;
/* 263:    */     private final int type;
/* 264:    */     private Scriptable scope;
/* 265:    */     
/* 266:    */     public IProxy(int type)
/* 267:    */     {
/* 268:407 */       this.type = type;
/* 269:    */     }
/* 270:    */     
/* 271:    */     public static ScopeProvider newScopeProvider(Scriptable scope)
/* 272:    */     {
/* 273:414 */       IProxy scopeProvider = new IProxy(2);
/* 274:415 */       scopeProvider.scope = scope;
/* 275:416 */       return scopeProvider;
/* 276:    */     }
/* 277:    */     
/* 278:    */     public void run()
/* 279:    */     {
/* 280:425 */       if (this.type != 1) {
/* 281:425 */         Kit.codeBug();
/* 282:    */       }
/* 283:426 */       System.exit(0);
/* 284:    */     }
/* 285:    */     
/* 286:    */     public Scriptable getScope()
/* 287:    */     {
/* 288:435 */       if (this.type != 2) {
/* 289:435 */         Kit.codeBug();
/* 290:    */       }
/* 291:436 */       if (this.scope == null) {
/* 292:436 */         Kit.codeBug();
/* 293:    */       }
/* 294:437 */       return this.scope;
/* 295:    */     }
/* 296:    */   }
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main
 * JD-Core Version:    0.7.0.1
 */