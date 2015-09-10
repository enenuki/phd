/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.awt.event.ActionListener;
/*   5:    */ import java.awt.event.WindowAdapter;
/*   6:    */ import java.awt.event.WindowEvent;
/*   7:    */ import java.io.File;
/*   8:    */ import javax.swing.ButtonGroup;
/*   9:    */ import javax.swing.JFileChooser;
/*  10:    */ import javax.swing.JFrame;
/*  11:    */ import javax.swing.JMenu;
/*  12:    */ import javax.swing.JMenuBar;
/*  13:    */ import javax.swing.JMenuItem;
/*  14:    */ import javax.swing.JOptionPane;
/*  15:    */ import javax.swing.JRadioButtonMenuItem;
/*  16:    */ import javax.swing.JScrollPane;
/*  17:    */ import javax.swing.SwingUtilities;
/*  18:    */ import javax.swing.UIManager;
/*  19:    */ import javax.swing.filechooser.FileFilter;
/*  20:    */ import net.sourceforge.htmlunit.corejs.javascript.SecurityUtilities;
/*  21:    */ 
/*  22:    */ public class JSConsole
/*  23:    */   extends JFrame
/*  24:    */   implements ActionListener
/*  25:    */ {
/*  26:    */   static final long serialVersionUID = 2551225560631876300L;
/*  27:    */   private File CWD;
/*  28:    */   private JFileChooser dlg;
/*  29:    */   private ConsoleTextArea consoleTextArea;
/*  30:    */   
/*  31:    */   public String chooseFile()
/*  32:    */   {
/*  33: 68 */     if (this.CWD == null)
/*  34:    */     {
/*  35: 69 */       String dir = SecurityUtilities.getSystemProperty("user.dir");
/*  36: 70 */       if (dir != null) {
/*  37: 71 */         this.CWD = new File(dir);
/*  38:    */       }
/*  39:    */     }
/*  40: 74 */     if (this.CWD != null) {
/*  41: 75 */       this.dlg.setCurrentDirectory(this.CWD);
/*  42:    */     }
/*  43: 77 */     this.dlg.setDialogTitle("Select a file to load");
/*  44: 78 */     int returnVal = this.dlg.showOpenDialog(this);
/*  45: 79 */     if (returnVal == 0)
/*  46:    */     {
/*  47: 80 */       String result = this.dlg.getSelectedFile().getPath();
/*  48: 81 */       this.CWD = new File(this.dlg.getSelectedFile().getParent());
/*  49: 82 */       return result;
/*  50:    */     }
/*  51: 84 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void main(String[] args)
/*  55:    */   {
/*  56: 88 */     new JSConsole(args);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void createFileChooser()
/*  60:    */   {
/*  61: 92 */     this.dlg = new JFileChooser();
/*  62: 93 */     FileFilter filter = new FileFilter()
/*  63:    */     {
/*  64:    */       public boolean accept(File f)
/*  65:    */       {
/*  66: 97 */         if (f.isDirectory()) {
/*  67: 98 */           return true;
/*  68:    */         }
/*  69:100 */         String name = f.getName();
/*  70:101 */         int i = name.lastIndexOf('.');
/*  71:102 */         if ((i > 0) && (i < name.length() - 1))
/*  72:    */         {
/*  73:103 */           String ext = name.substring(i + 1).toLowerCase();
/*  74:104 */           if (ext.equals("js")) {
/*  75:105 */             return true;
/*  76:    */           }
/*  77:    */         }
/*  78:108 */         return false;
/*  79:    */       }
/*  80:    */       
/*  81:    */       public String getDescription()
/*  82:    */       {
/*  83:113 */         return "JavaScript Files (*.js)";
/*  84:    */       }
/*  85:115 */     };
/*  86:116 */     this.dlg.addChoosableFileFilter(filter);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JSConsole(String[] args)
/*  90:    */   {
/*  91:121 */     super("Rhino JavaScript Console");
/*  92:122 */     JMenuBar menubar = new JMenuBar();
/*  93:123 */     createFileChooser();
/*  94:124 */     String[] fileItems = { "Load...", "Exit" };
/*  95:125 */     String[] fileCmds = { "Load", "Exit" };
/*  96:126 */     char[] fileShortCuts = { 'L', 'X' };
/*  97:127 */     String[] editItems = { "Cut", "Copy", "Paste" };
/*  98:128 */     char[] editShortCuts = { 'T', 'C', 'P' };
/*  99:129 */     String[] plafItems = { "Metal", "Windows", "Motif" };
/* 100:130 */     boolean[] plafState = { true, false, false };
/* 101:131 */     JMenu fileMenu = new JMenu("File");
/* 102:132 */     fileMenu.setMnemonic('F');
/* 103:133 */     JMenu editMenu = new JMenu("Edit");
/* 104:134 */     editMenu.setMnemonic('E');
/* 105:135 */     JMenu plafMenu = new JMenu("Platform");
/* 106:136 */     plafMenu.setMnemonic('P');
/* 107:137 */     for (int i = 0; i < fileItems.length; i++)
/* 108:    */     {
/* 109:138 */       JMenuItem item = new JMenuItem(fileItems[i], fileShortCuts[i]);
/* 110:    */       
/* 111:140 */       item.setActionCommand(fileCmds[i]);
/* 112:141 */       item.addActionListener(this);
/* 113:142 */       fileMenu.add(item);
/* 114:    */     }
/* 115:144 */     for (int i = 0; i < editItems.length; i++)
/* 116:    */     {
/* 117:145 */       JMenuItem item = new JMenuItem(editItems[i], editShortCuts[i]);
/* 118:    */       
/* 119:147 */       item.addActionListener(this);
/* 120:148 */       editMenu.add(item);
/* 121:    */     }
/* 122:150 */     ButtonGroup group = new ButtonGroup();
/* 123:151 */     for (int i = 0; i < plafItems.length; i++)
/* 124:    */     {
/* 125:152 */       JRadioButtonMenuItem item = new JRadioButtonMenuItem(plafItems[i], plafState[i]);
/* 126:    */       
/* 127:154 */       group.add(item);
/* 128:155 */       item.addActionListener(this);
/* 129:156 */       plafMenu.add(item);
/* 130:    */     }
/* 131:158 */     menubar.add(fileMenu);
/* 132:159 */     menubar.add(editMenu);
/* 133:160 */     menubar.add(plafMenu);
/* 134:161 */     setJMenuBar(menubar);
/* 135:162 */     this.consoleTextArea = new ConsoleTextArea(args);
/* 136:163 */     JScrollPane scroller = new JScrollPane(this.consoleTextArea);
/* 137:164 */     setContentPane(scroller);
/* 138:165 */     this.consoleTextArea.setRows(24);
/* 139:166 */     this.consoleTextArea.setColumns(80);
/* 140:167 */     addWindowListener(new WindowAdapter()
/* 141:    */     {
/* 142:    */       public void windowClosing(WindowEvent e)
/* 143:    */       {
/* 144:170 */         System.exit(0);
/* 145:    */       }
/* 146:172 */     });
/* 147:173 */     pack();
/* 148:174 */     setVisible(true);
/* 149:    */     
/* 150:    */ 
/* 151:    */ 
/* 152:178 */     Main.setIn(this.consoleTextArea.getIn());
/* 153:179 */     Main.setOut(this.consoleTextArea.getOut());
/* 154:180 */     Main.setErr(this.consoleTextArea.getErr());
/* 155:181 */     Main.main(args);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void actionPerformed(ActionEvent e)
/* 159:    */   {
/* 160:185 */     String cmd = e.getActionCommand();
/* 161:186 */     String plaf_name = null;
/* 162:187 */     if (cmd.equals("Load"))
/* 163:    */     {
/* 164:188 */       String f = chooseFile();
/* 165:189 */       if (f != null)
/* 166:    */       {
/* 167:190 */         f = f.replace('\\', '/');
/* 168:191 */         this.consoleTextArea.eval("load(\"" + f + "\");");
/* 169:    */       }
/* 170:    */     }
/* 171:193 */     else if (cmd.equals("Exit"))
/* 172:    */     {
/* 173:194 */       System.exit(0);
/* 174:    */     }
/* 175:195 */     else if (cmd.equals("Cut"))
/* 176:    */     {
/* 177:196 */       this.consoleTextArea.cut();
/* 178:    */     }
/* 179:197 */     else if (cmd.equals("Copy"))
/* 180:    */     {
/* 181:198 */       this.consoleTextArea.copy();
/* 182:    */     }
/* 183:199 */     else if (cmd.equals("Paste"))
/* 184:    */     {
/* 185:200 */       this.consoleTextArea.paste();
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:202 */       if (cmd.equals("Metal")) {
/* 190:203 */         plaf_name = "javax.swing.plaf.metal.MetalLookAndFeel";
/* 191:204 */       } else if (cmd.equals("Windows")) {
/* 192:205 */         plaf_name = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
/* 193:206 */       } else if (cmd.equals("Motif")) {
/* 194:207 */         plaf_name = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
/* 195:    */       }
/* 196:209 */       if (plaf_name != null) {
/* 197:    */         try
/* 198:    */         {
/* 199:211 */           UIManager.setLookAndFeel(plaf_name);
/* 200:212 */           SwingUtilities.updateComponentTreeUI(this);
/* 201:213 */           this.consoleTextArea.postUpdateUI();
/* 202:    */           
/* 203:    */ 
/* 204:216 */           createFileChooser();
/* 205:    */         }
/* 206:    */         catch (Exception exc)
/* 207:    */         {
/* 208:218 */           JOptionPane.showMessageDialog(this, exc.getMessage(), "Platform", 0);
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.JSConsole
 * JD-Core Version:    0.7.0.1
 */