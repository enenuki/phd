/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.BorderLayout;
/*    4:     */ import java.awt.Component;
/*    5:     */ import java.awt.Dimension;
/*    6:     */ import java.awt.GridBagConstraints;
/*    7:     */ import java.awt.GridBagLayout;
/*    8:     */ import java.awt.GridLayout;
/*    9:     */ import java.awt.event.ActionEvent;
/*   10:     */ import java.awt.event.ActionListener;
/*   11:     */ import java.awt.event.ComponentEvent;
/*   12:     */ import java.awt.event.ComponentListener;
/*   13:     */ import java.awt.event.ContainerEvent;
/*   14:     */ import java.awt.event.ContainerListener;
/*   15:     */ import java.awt.event.WindowAdapter;
/*   16:     */ import java.awt.event.WindowEvent;
/*   17:     */ import java.awt.event.WindowListener;
/*   18:     */ import java.util.ArrayList;
/*   19:     */ import java.util.Collections;
/*   20:     */ import java.util.EventListener;
/*   21:     */ import java.util.List;
/*   22:     */ import javax.swing.JComboBox;
/*   23:     */ import javax.swing.JFrame;
/*   24:     */ import javax.swing.JLabel;
/*   25:     */ import javax.swing.JPanel;
/*   26:     */ import javax.swing.JScrollPane;
/*   27:     */ import javax.swing.JSplitPane;
/*   28:     */ import javax.swing.JTabbedPane;
/*   29:     */ import javax.swing.JToolBar;
/*   30:     */ import javax.swing.JViewport;
/*   31:     */ 
/*   32:     */ class ContextWindow
/*   33:     */   extends JPanel
/*   34:     */   implements ActionListener
/*   35:     */ {
/*   36:     */   private static final long serialVersionUID = 2306040975490228051L;
/*   37:     */   private SwingGui debugGui;
/*   38:     */   JComboBox context;
/*   39:     */   List<String> toolTips;
/*   40:     */   private JTabbedPane tabs;
/*   41:     */   private JTabbedPane tabs2;
/*   42:     */   private MyTreeTable thisTable;
/*   43:     */   private MyTreeTable localsTable;
/*   44:     */   private MyTableModel tableModel;
/*   45:     */   private Evaluator evaluator;
/*   46:     */   private EvalTextArea cmdLine;
/*   47:     */   JSplitPane split;
/*   48:     */   private boolean enabled;
/*   49:     */   
/*   50:     */   public ContextWindow(final SwingGui debugGui)
/*   51:     */   {
/*   52:2922 */     this.debugGui = debugGui;
/*   53:2923 */     this.enabled = false;
/*   54:2924 */     JPanel left = new JPanel();
/*   55:2925 */     JToolBar t1 = new JToolBar();
/*   56:2926 */     t1.setName("Variables");
/*   57:2927 */     t1.setLayout(new GridLayout());
/*   58:2928 */     t1.add(left);
/*   59:2929 */     JPanel p1 = new JPanel();
/*   60:2930 */     p1.setLayout(new GridLayout());
/*   61:2931 */     JPanel p2 = new JPanel();
/*   62:2932 */     p2.setLayout(new GridLayout());
/*   63:2933 */     p1.add(t1);
/*   64:2934 */     JLabel label = new JLabel("Context:");
/*   65:2935 */     this.context = new JComboBox();
/*   66:2936 */     this.context.setLightWeightPopupEnabled(false);
/*   67:2937 */     this.toolTips = Collections.synchronizedList(new ArrayList());
/*   68:2938 */     label.setBorder(this.context.getBorder());
/*   69:2939 */     this.context.addActionListener(this);
/*   70:2940 */     this.context.setActionCommand("ContextSwitch");
/*   71:2941 */     GridBagLayout layout = new GridBagLayout();
/*   72:2942 */     left.setLayout(layout);
/*   73:2943 */     GridBagConstraints lc = new GridBagConstraints();
/*   74:2944 */     lc.insets.left = 5;
/*   75:2945 */     lc.anchor = 17;
/*   76:2946 */     lc.ipadx = 5;
/*   77:2947 */     layout.setConstraints(label, lc);
/*   78:2948 */     left.add(label);
/*   79:2949 */     GridBagConstraints c = new GridBagConstraints();
/*   80:2950 */     c.gridwidth = 0;
/*   81:2951 */     c.fill = 2;
/*   82:2952 */     c.anchor = 17;
/*   83:2953 */     layout.setConstraints(this.context, c);
/*   84:2954 */     left.add(this.context);
/*   85:2955 */     this.tabs = new JTabbedPane(3);
/*   86:2956 */     this.tabs.setPreferredSize(new Dimension(500, 300));
/*   87:2957 */     this.thisTable = new MyTreeTable(new VariableModel());
/*   88:2958 */     JScrollPane jsp = new JScrollPane(this.thisTable);
/*   89:2959 */     jsp.getViewport().setViewSize(new Dimension(5, 2));
/*   90:2960 */     this.tabs.add("this", jsp);
/*   91:2961 */     this.localsTable = new MyTreeTable(new VariableModel());
/*   92:2962 */     this.localsTable.setAutoResizeMode(4);
/*   93:2963 */     this.localsTable.setPreferredSize(null);
/*   94:2964 */     jsp = new JScrollPane(this.localsTable);
/*   95:2965 */     this.tabs.add("Locals", jsp);
/*   96:2966 */     c.weightx = (c.weighty = 1.0D);
/*   97:2967 */     c.gridheight = 0;
/*   98:2968 */     c.fill = 1;
/*   99:2969 */     c.anchor = 17;
/*  100:2970 */     layout.setConstraints(this.tabs, c);
/*  101:2971 */     left.add(this.tabs);
/*  102:2972 */     this.evaluator = new Evaluator(debugGui);
/*  103:2973 */     this.cmdLine = new EvalTextArea(debugGui);
/*  104:     */     
/*  105:2975 */     this.tableModel = this.evaluator.tableModel;
/*  106:2976 */     jsp = new JScrollPane(this.evaluator);
/*  107:2977 */     JToolBar t2 = new JToolBar();
/*  108:2978 */     t2.setName("Evaluate");
/*  109:2979 */     this.tabs2 = new JTabbedPane(3);
/*  110:2980 */     this.tabs2.add("Watch", jsp);
/*  111:2981 */     this.tabs2.add("Evaluate", new JScrollPane(this.cmdLine));
/*  112:2982 */     this.tabs2.setPreferredSize(new Dimension(500, 300));
/*  113:2983 */     t2.setLayout(new GridLayout());
/*  114:2984 */     t2.add(this.tabs2);
/*  115:2985 */     p2.add(t2);
/*  116:2986 */     this.evaluator.setAutoResizeMode(4);
/*  117:2987 */     this.split = new JSplitPane(1, p1, p2);
/*  118:     */     
/*  119:2989 */     this.split.setOneTouchExpandable(true);
/*  120:2990 */     SwingGui.setResizeWeight(this.split, 0.5D);
/*  121:2991 */     setLayout(new BorderLayout());
/*  122:2992 */     add(this.split, "Center");
/*  123:     */     
/*  124:2994 */     final JToolBar finalT1 = t1;
/*  125:2995 */     final JToolBar finalT2 = t2;
/*  126:2996 */     final JPanel finalP1 = p1;
/*  127:2997 */     final JPanel finalP2 = p2;
/*  128:2998 */     final JSplitPane finalSplit = this.split;
/*  129:2999 */     final JPanel finalThis = this;
/*  130:     */     
/*  131:3001 */     ComponentListener clistener = new ComponentListener()
/*  132:     */     {
/*  133:3002 */       boolean t2Docked = true;
/*  134:     */       
/*  135:     */       void check(Component comp)
/*  136:     */       {
/*  137:3004 */         Component thisParent = finalThis.getParent();
/*  138:3005 */         if (thisParent == null) {
/*  139:3006 */           return;
/*  140:     */         }
/*  141:3008 */         Component parent = finalT1.getParent();
/*  142:3009 */         boolean leftDocked = true;
/*  143:3010 */         boolean rightDocked = true;
/*  144:3011 */         boolean adjustVerticalSplit = false;
/*  145:3012 */         if (parent != null) {
/*  146:3013 */           if (parent != finalP1)
/*  147:     */           {
/*  148:3014 */             while (!(parent instanceof JFrame)) {
/*  149:3015 */               parent = parent.getParent();
/*  150:     */             }
/*  151:3017 */             JFrame frame = (JFrame)parent;
/*  152:3018 */             debugGui.addTopLevel("Variables", frame);
/*  153:3033 */             if (!frame.isResizable())
/*  154:     */             {
/*  155:3034 */               frame.setResizable(true);
/*  156:3035 */               frame.setDefaultCloseOperation(0);
/*  157:3036 */               final EventListener[] l = frame.getListeners(WindowListener.class);
/*  158:     */               
/*  159:3038 */               frame.removeWindowListener((WindowListener)l[0]);
/*  160:3039 */               frame.addWindowListener(new WindowAdapter()
/*  161:     */               {
/*  162:     */                 public void windowClosing(WindowEvent e)
/*  163:     */                 {
/*  164:3042 */                   ContextWindow.this.context.hidePopup();
/*  165:3043 */                   ((WindowListener)l[0]).windowClosing(e);
/*  166:     */                 }
/*  167:     */               });
/*  168:     */             }
/*  169:3048 */             leftDocked = false;
/*  170:     */           }
/*  171:     */           else
/*  172:     */           {
/*  173:3050 */             leftDocked = true;
/*  174:     */           }
/*  175:     */         }
/*  176:3053 */         parent = finalT2.getParent();
/*  177:3054 */         if (parent != null) {
/*  178:3055 */           if (parent != finalP2)
/*  179:     */           {
/*  180:3056 */             while (!(parent instanceof JFrame)) {
/*  181:3057 */               parent = parent.getParent();
/*  182:     */             }
/*  183:3059 */             JFrame frame = (JFrame)parent;
/*  184:3060 */             debugGui.addTopLevel("Evaluate", frame);
/*  185:3061 */             frame.setResizable(true);
/*  186:3062 */             rightDocked = false;
/*  187:     */           }
/*  188:     */           else
/*  189:     */           {
/*  190:3064 */             rightDocked = true;
/*  191:     */           }
/*  192:     */         }
/*  193:3067 */         if ((leftDocked) && (this.t2Docked) && (rightDocked) && (this.t2Docked)) {
/*  194:3069 */           return;
/*  195:     */         }
/*  196:3071 */         this.t2Docked = rightDocked;
/*  197:3072 */         JSplitPane split = (JSplitPane)thisParent;
/*  198:3073 */         if (leftDocked)
/*  199:     */         {
/*  200:3074 */           if (rightDocked) {
/*  201:3075 */             finalSplit.setDividerLocation(0.5D);
/*  202:     */           } else {
/*  203:3077 */             finalSplit.setDividerLocation(1.0D);
/*  204:     */           }
/*  205:3079 */           if (adjustVerticalSplit) {
/*  206:3080 */             split.setDividerLocation(0.66D);
/*  207:     */           }
/*  208:     */         }
/*  209:3083 */         else if (rightDocked)
/*  210:     */         {
/*  211:3084 */           finalSplit.setDividerLocation(0.0D);
/*  212:3085 */           split.setDividerLocation(0.66D);
/*  213:     */         }
/*  214:     */         else
/*  215:     */         {
/*  216:3088 */           split.setDividerLocation(1.0D);
/*  217:     */         }
/*  218:     */       }
/*  219:     */       
/*  220:     */       public void componentHidden(ComponentEvent e)
/*  221:     */       {
/*  222:3092 */         check(e.getComponent());
/*  223:     */       }
/*  224:     */       
/*  225:     */       public void componentMoved(ComponentEvent e)
/*  226:     */       {
/*  227:3095 */         check(e.getComponent());
/*  228:     */       }
/*  229:     */       
/*  230:     */       public void componentResized(ComponentEvent e)
/*  231:     */       {
/*  232:3098 */         check(e.getComponent());
/*  233:     */       }
/*  234:     */       
/*  235:     */       public void componentShown(ComponentEvent e)
/*  236:     */       {
/*  237:3101 */         check(e.getComponent());
/*  238:     */       }
/*  239:3103 */     };
/*  240:3104 */     p1.addContainerListener(new ContainerListener()
/*  241:     */     {
/*  242:     */       public void componentAdded(ContainerEvent e)
/*  243:     */       {
/*  244:3106 */         Component thisParent = finalThis.getParent();
/*  245:3107 */         JSplitPane split = (JSplitPane)thisParent;
/*  246:3108 */         if (e.getChild() == finalT1)
/*  247:     */         {
/*  248:3109 */           if (finalT2.getParent() == finalP2) {
/*  249:3111 */             finalSplit.setDividerLocation(0.5D);
/*  250:     */           } else {
/*  251:3114 */             finalSplit.setDividerLocation(1.0D);
/*  252:     */           }
/*  253:3116 */           split.setDividerLocation(0.66D);
/*  254:     */         }
/*  255:     */       }
/*  256:     */       
/*  257:     */       public void componentRemoved(ContainerEvent e)
/*  258:     */       {
/*  259:3120 */         Component thisParent = finalThis.getParent();
/*  260:3121 */         JSplitPane split = (JSplitPane)thisParent;
/*  261:3122 */         if (e.getChild() == finalT1) {
/*  262:3123 */           if (finalT2.getParent() == finalP2)
/*  263:     */           {
/*  264:3125 */             finalSplit.setDividerLocation(0.0D);
/*  265:3126 */             split.setDividerLocation(0.66D);
/*  266:     */           }
/*  267:     */           else
/*  268:     */           {
/*  269:3129 */             split.setDividerLocation(1.0D);
/*  270:     */           }
/*  271:     */         }
/*  272:     */       }
/*  273:3133 */     });
/*  274:3134 */     t1.addComponentListener(clistener);
/*  275:3135 */     t2.addComponentListener(clistener);
/*  276:3136 */     setEnabled(false);
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void setEnabled(boolean enabled)
/*  280:     */   {
/*  281:3144 */     this.context.setEnabled(enabled);
/*  282:3145 */     this.thisTable.setEnabled(enabled);
/*  283:3146 */     this.localsTable.setEnabled(enabled);
/*  284:3147 */     this.evaluator.setEnabled(enabled);
/*  285:3148 */     this.cmdLine.setEnabled(enabled);
/*  286:     */   }
/*  287:     */   
/*  288:     */   public void disableUpdate()
/*  289:     */   {
/*  290:3155 */     this.enabled = false;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void enableUpdate()
/*  294:     */   {
/*  295:3162 */     this.enabled = true;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public void actionPerformed(ActionEvent e)
/*  299:     */   {
/*  300:3171 */     if (!this.enabled) {
/*  301:3171 */       return;
/*  302:     */     }
/*  303:3172 */     if (e.getActionCommand().equals("ContextSwitch"))
/*  304:     */     {
/*  305:3173 */       Dim.ContextData contextData = this.debugGui.dim.currentContextData();
/*  306:3174 */       if (contextData == null) {
/*  307:3174 */         return;
/*  308:     */       }
/*  309:3175 */       int frameIndex = this.context.getSelectedIndex();
/*  310:3176 */       this.context.setToolTipText((String)this.toolTips.get(frameIndex));
/*  311:3177 */       int frameCount = contextData.frameCount();
/*  312:3178 */       if (frameIndex >= frameCount) {
/*  313:3179 */         return;
/*  314:     */       }
/*  315:3181 */       Dim.StackFrame frame = contextData.getFrame(frameIndex);
/*  316:3182 */       Object scope = frame.scope();
/*  317:3183 */       Object thisObj = frame.thisObj();
/*  318:3184 */       this.thisTable.resetTree(new VariableModel(this.debugGui.dim, thisObj));
/*  319:     */       VariableModel scopeModel;
/*  320:     */       VariableModel scopeModel;
/*  321:3186 */       if (scope != thisObj) {
/*  322:3187 */         scopeModel = new VariableModel(this.debugGui.dim, scope);
/*  323:     */       } else {
/*  324:3189 */         scopeModel = new VariableModel();
/*  325:     */       }
/*  326:3191 */       this.localsTable.resetTree(scopeModel);
/*  327:3192 */       this.debugGui.dim.contextSwitch(frameIndex);
/*  328:3193 */       this.debugGui.showStopLine(frame);
/*  329:3194 */       this.tableModel.updateModel();
/*  330:     */     }
/*  331:     */   }
/*  332:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.ContextWindow
 * JD-Core Version:    0.7.0.1
 */