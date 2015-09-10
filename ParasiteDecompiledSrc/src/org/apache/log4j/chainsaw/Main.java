/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Window;
/*   6:    */ import java.awt.event.WindowAdapter;
/*   7:    */ import java.awt.event.WindowEvent;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Properties;
/*  10:    */ import javax.swing.AbstractButton;
/*  11:    */ import javax.swing.BorderFactory;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ import javax.swing.JMenu;
/*  15:    */ import javax.swing.JMenuBar;
/*  16:    */ import javax.swing.JMenuItem;
/*  17:    */ import javax.swing.JOptionPane;
/*  18:    */ import javax.swing.JPanel;
/*  19:    */ import javax.swing.JScrollPane;
/*  20:    */ import javax.swing.JSplitPane;
/*  21:    */ import javax.swing.JTable;
/*  22:    */ import org.apache.log4j.Category;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.apache.log4j.PropertyConfigurator;
/*  25:    */ 
/*  26:    */ public class Main
/*  27:    */   extends JFrame
/*  28:    */ {
/*  29:    */   private static final int DEFAULT_PORT = 4445;
/*  30:    */   public static final String PORT_PROP_NAME = "chainsaw.port";
/*  31: 54 */   private static final Logger LOG = Logger.getLogger(Main.class);
/*  32:    */   
/*  33:    */   private Main()
/*  34:    */   {
/*  35: 61 */     super("CHAINSAW - Log4J Log Viewer");
/*  36:    */     
/*  37: 63 */     MyTableModel model = new MyTableModel();
/*  38:    */     
/*  39:    */ 
/*  40: 66 */     JMenuBar menuBar = new JMenuBar();
/*  41: 67 */     setJMenuBar(menuBar);
/*  42: 68 */     JMenu menu = new JMenu("File");
/*  43: 69 */     menuBar.add(menu);
/*  44:    */     try
/*  45:    */     {
/*  46: 72 */       LoadXMLAction lxa = new LoadXMLAction(this, model);
/*  47: 73 */       JMenuItem loadMenuItem = new JMenuItem("Load file...");
/*  48: 74 */       menu.add(loadMenuItem);
/*  49: 75 */       loadMenuItem.addActionListener(lxa);
/*  50:    */     }
/*  51:    */     catch (NoClassDefFoundError e)
/*  52:    */     {
/*  53: 77 */       LOG.info("Missing classes for XML parser", e);
/*  54: 78 */       JOptionPane.showMessageDialog(this, "XML parser not in classpath - unable to load XML events.", "CHAINSAW", 0);
/*  55:    */     }
/*  56:    */     catch (Exception e)
/*  57:    */     {
/*  58: 84 */       LOG.info("Unable to create the action to load XML files", e);
/*  59: 85 */       JOptionPane.showMessageDialog(this, "Unable to create a XML parser - unable to load XML events.", "CHAINSAW", 0);
/*  60:    */     }
/*  61: 92 */     JMenuItem exitMenuItem = new JMenuItem("Exit");
/*  62: 93 */     menu.add(exitMenuItem);
/*  63: 94 */     exitMenuItem.addActionListener(ExitAction.INSTANCE);
/*  64:    */     
/*  65:    */ 
/*  66: 97 */     ControlPanel cp = new ControlPanel(model);
/*  67: 98 */     getContentPane().add(cp, "North");
/*  68:    */     
/*  69:    */ 
/*  70:101 */     JTable table = new JTable(model);
/*  71:102 */     table.setSelectionMode(0);
/*  72:103 */     JScrollPane scrollPane = new JScrollPane(table);
/*  73:104 */     scrollPane.setBorder(BorderFactory.createTitledBorder("Events: "));
/*  74:105 */     scrollPane.setPreferredSize(new Dimension(900, 300));
/*  75:    */     
/*  76:    */ 
/*  77:108 */     JPanel details = new DetailPanel(table, model);
/*  78:109 */     details.setPreferredSize(new Dimension(900, 300));
/*  79:    */     
/*  80:    */ 
/*  81:112 */     JSplitPane jsp = new JSplitPane(0, scrollPane, details);
/*  82:    */     
/*  83:114 */     getContentPane().add(jsp, "Center");
/*  84:    */     
/*  85:116 */     addWindowListener(new WindowAdapter()
/*  86:    */     {
/*  87:    */       public void windowClosing(WindowEvent aEvent)
/*  88:    */       {
/*  89:118 */         ExitAction.INSTANCE.actionPerformed(null);
/*  90:    */       }
/*  91:121 */     });
/*  92:122 */     pack();
/*  93:123 */     setVisible(true);
/*  94:    */     
/*  95:125 */     setupReceiver(model);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void setupReceiver(MyTableModel aModel)
/*  99:    */   {
/* 100:134 */     int port = 4445;
/* 101:135 */     String strRep = System.getProperty("chainsaw.port");
/* 102:136 */     if (strRep != null) {
/* 103:    */       try
/* 104:    */       {
/* 105:138 */         port = Integer.parseInt(strRep);
/* 106:    */       }
/* 107:    */       catch (NumberFormatException nfe)
/* 108:    */       {
/* 109:140 */         LOG.fatal("Unable to parse chainsaw.port property with value " + strRep + ".");
/* 110:    */         
/* 111:142 */         JOptionPane.showMessageDialog(this, "Unable to parse port number from '" + strRep + "', quitting.", "CHAINSAW", 0);
/* 112:    */         
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:148 */         System.exit(1);
/* 118:    */       }
/* 119:    */     }
/* 120:    */     try
/* 121:    */     {
/* 122:153 */       LoggingReceiver lr = new LoggingReceiver(aModel, port);
/* 123:154 */       lr.start();
/* 124:    */     }
/* 125:    */     catch (IOException e)
/* 126:    */     {
/* 127:156 */       LOG.fatal("Unable to connect to socket server, quiting", e);
/* 128:157 */       JOptionPane.showMessageDialog(this, "Unable to create socket on port " + port + ", quitting.", "CHAINSAW", 0);
/* 129:    */       
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:162 */       System.exit(1);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static void initLog4J()
/* 138:    */   {
/* 139:174 */     Properties props = new Properties();
/* 140:175 */     props.setProperty("log4j.rootLogger", "DEBUG, A1");
/* 141:176 */     props.setProperty("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
/* 142:    */     
/* 143:178 */     props.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
/* 144:    */     
/* 145:180 */     PropertyConfigurator.configure(props);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void main(String[] aArgs)
/* 149:    */   {
/* 150:189 */     initLog4J();
/* 151:190 */     new Main();
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.Main
 * JD-Core Version:    0.7.0.1
 */