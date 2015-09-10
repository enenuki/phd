/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.awt.Component;
/*    4:     */ import java.awt.Container;
/*    5:     */ import java.awt.Dimension;
/*    6:     */ import java.awt.event.ActionEvent;
/*    7:     */ import java.awt.event.ActionListener;
/*    8:     */ import java.awt.event.KeyAdapter;
/*    9:     */ import java.awt.event.KeyEvent;
/*   10:     */ import java.awt.event.MouseAdapter;
/*   11:     */ import java.awt.event.MouseEvent;
/*   12:     */ import java.util.Arrays;
/*   13:     */ import javax.swing.BorderFactory;
/*   14:     */ import javax.swing.Box;
/*   15:     */ import javax.swing.BoxLayout;
/*   16:     */ import javax.swing.DefaultListModel;
/*   17:     */ import javax.swing.JButton;
/*   18:     */ import javax.swing.JDialog;
/*   19:     */ import javax.swing.JLabel;
/*   20:     */ import javax.swing.JList;
/*   21:     */ import javax.swing.JPanel;
/*   22:     */ import javax.swing.JRootPane;
/*   23:     */ import javax.swing.JScrollPane;
/*   24:     */ 
/*   25:     */ class FindFunction
/*   26:     */   extends JDialog
/*   27:     */   implements ActionListener
/*   28:     */ {
/*   29:     */   private static final long serialVersionUID = 559491015232880916L;
/*   30:     */   private String value;
/*   31:     */   private JList list;
/*   32:     */   private SwingGui debugGui;
/*   33:     */   private JButton setButton;
/*   34:     */   private JButton cancelButton;
/*   35:     */   
/*   36:     */   public FindFunction(SwingGui debugGui, String title, String labelText)
/*   37:     */   {
/*   38:1801 */     super(debugGui, title, true);
/*   39:1802 */     this.debugGui = debugGui;
/*   40:     */     
/*   41:1804 */     this.cancelButton = new JButton("Cancel");
/*   42:1805 */     this.setButton = new JButton("Select");
/*   43:1806 */     this.cancelButton.addActionListener(this);
/*   44:1807 */     this.setButton.addActionListener(this);
/*   45:1808 */     getRootPane().setDefaultButton(this.setButton);
/*   46:     */     
/*   47:1810 */     this.list = new JList(new DefaultListModel());
/*   48:1811 */     DefaultListModel model = (DefaultListModel)this.list.getModel();
/*   49:1812 */     model.clear();
/*   50:     */     
/*   51:1814 */     String[] a = debugGui.dim.functionNames();
/*   52:1815 */     Arrays.sort(a);
/*   53:1816 */     for (int i = 0; i < a.length; i++) {
/*   54:1817 */       model.addElement(a[i]);
/*   55:     */     }
/*   56:1819 */     this.list.setSelectedIndex(0);
/*   57:     */     
/*   58:1821 */     this.setButton.setEnabled(a.length > 0);
/*   59:1822 */     this.list.setSelectionMode(1);
/*   60:1823 */     this.list.addMouseListener(new MouseHandler());
/*   61:1824 */     JScrollPane listScroller = new JScrollPane(this.list);
/*   62:1825 */     listScroller.setPreferredSize(new Dimension(320, 240));
/*   63:1826 */     listScroller.setMinimumSize(new Dimension(250, 80));
/*   64:1827 */     listScroller.setAlignmentX(0.0F);
/*   65:     */     
/*   66:     */ 
/*   67:     */ 
/*   68:     */ 
/*   69:     */ 
/*   70:1833 */     JPanel listPane = new JPanel();
/*   71:1834 */     listPane.setLayout(new BoxLayout(listPane, 1));
/*   72:1835 */     JLabel label = new JLabel(labelText);
/*   73:1836 */     label.setLabelFor(this.list);
/*   74:1837 */     listPane.add(label);
/*   75:1838 */     listPane.add(Box.createRigidArea(new Dimension(0, 5)));
/*   76:1839 */     listPane.add(listScroller);
/*   77:1840 */     listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*   78:     */     
/*   79:     */ 
/*   80:1843 */     JPanel buttonPane = new JPanel();
/*   81:1844 */     buttonPane.setLayout(new BoxLayout(buttonPane, 0));
/*   82:1845 */     buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
/*   83:1846 */     buttonPane.add(Box.createHorizontalGlue());
/*   84:1847 */     buttonPane.add(this.cancelButton);
/*   85:1848 */     buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
/*   86:1849 */     buttonPane.add(this.setButton);
/*   87:     */     
/*   88:     */ 
/*   89:1852 */     Container contentPane = getContentPane();
/*   90:1853 */     contentPane.add(listPane, "Center");
/*   91:1854 */     contentPane.add(buttonPane, "South");
/*   92:1855 */     pack();
/*   93:1856 */     addKeyListener(new KeyAdapter()
/*   94:     */     {
/*   95:     */       public void keyPressed(KeyEvent ke)
/*   96:     */       {
/*   97:1859 */         int code = ke.getKeyCode();
/*   98:1860 */         if (code == 27)
/*   99:     */         {
/*  100:1861 */           ke.consume();
/*  101:1862 */           FindFunction.this.value = null;
/*  102:1863 */           FindFunction.this.setVisible(false);
/*  103:     */         }
/*  104:     */       }
/*  105:     */     });
/*  106:     */   }
/*  107:     */   
/*  108:     */   public String showDialog(Component comp)
/*  109:     */   {
/*  110:1873 */     this.value = null;
/*  111:1874 */     setLocationRelativeTo(comp);
/*  112:1875 */     setVisible(true);
/*  113:1876 */     return this.value;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public void actionPerformed(ActionEvent e)
/*  117:     */   {
/*  118:1885 */     String cmd = e.getActionCommand();
/*  119:1886 */     if (cmd.equals("Cancel"))
/*  120:     */     {
/*  121:1887 */       setVisible(false);
/*  122:1888 */       this.value = null;
/*  123:     */     }
/*  124:1889 */     else if (cmd.equals("Select"))
/*  125:     */     {
/*  126:1890 */       if (this.list.getSelectedIndex() < 0) {
/*  127:1891 */         return;
/*  128:     */       }
/*  129:     */       try
/*  130:     */       {
/*  131:1894 */         this.value = ((String)this.list.getSelectedValue());
/*  132:     */       }
/*  133:     */       catch (ArrayIndexOutOfBoundsException exc)
/*  134:     */       {
/*  135:1896 */         return;
/*  136:     */       }
/*  137:1898 */       setVisible(false);
/*  138:1899 */       Dim.FunctionSource item = this.debugGui.dim.functionSourceByName(this.value);
/*  139:1900 */       if (item != null)
/*  140:     */       {
/*  141:1901 */         Dim.SourceInfo si = item.sourceInfo();
/*  142:1902 */         String url = si.url();
/*  143:1903 */         int lineNumber = item.firstLine();
/*  144:1904 */         this.debugGui.showFileWindow(url, lineNumber);
/*  145:     */       }
/*  146:     */     }
/*  147:     */   }
/*  148:     */   
/*  149:     */   class MouseHandler
/*  150:     */     extends MouseAdapter
/*  151:     */   {
/*  152:     */     MouseHandler() {}
/*  153:     */     
/*  154:     */     public void mouseClicked(MouseEvent e)
/*  155:     */     {
/*  156:1915 */       if (e.getClickCount() == 2) {
/*  157:1916 */         FindFunction.this.setButton.doClick();
/*  158:     */       }
/*  159:     */     }
/*  160:     */   }
/*  161:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.FindFunction
 * JD-Core Version:    0.7.0.1
 */