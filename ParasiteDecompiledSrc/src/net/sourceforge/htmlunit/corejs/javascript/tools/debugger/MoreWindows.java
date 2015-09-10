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
/*   12:     */ import java.util.Map;
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
/*   25:     */ class MoreWindows
/*   26:     */   extends JDialog
/*   27:     */   implements ActionListener
/*   28:     */ {
/*   29:     */   private static final long serialVersionUID = 5177066296457377546L;
/*   30:     */   private String value;
/*   31:     */   private JList list;
/*   32:     */   private SwingGui swingGui;
/*   33:     */   private JButton setButton;
/*   34:     */   private JButton cancelButton;
/*   35:     */   
/*   36:     */   MoreWindows(SwingGui frame, Map<String, FileWindow> fileWindows, String title, String labelText)
/*   37:     */   {
/*   38:1653 */     super(frame, title, true);
/*   39:1654 */     this.swingGui = frame;
/*   40:     */     
/*   41:1656 */     this.cancelButton = new JButton("Cancel");
/*   42:1657 */     this.setButton = new JButton("Select");
/*   43:1658 */     this.cancelButton.addActionListener(this);
/*   44:1659 */     this.setButton.addActionListener(this);
/*   45:1660 */     getRootPane().setDefaultButton(this.setButton);
/*   46:     */     
/*   47:     */ 
/*   48:1663 */     this.list = new JList(new DefaultListModel());
/*   49:1664 */     DefaultListModel model = (DefaultListModel)this.list.getModel();
/*   50:1665 */     model.clear();
/*   51:1667 */     for (String data : fileWindows.keySet()) {
/*   52:1668 */       model.addElement(data);
/*   53:     */     }
/*   54:1670 */     this.list.setSelectedIndex(0);
/*   55:     */     
/*   56:1672 */     this.setButton.setEnabled(true);
/*   57:1673 */     this.list.setSelectionMode(1);
/*   58:1674 */     this.list.addMouseListener(new MouseHandler(null));
/*   59:1675 */     JScrollPane listScroller = new JScrollPane(this.list);
/*   60:1676 */     listScroller.setPreferredSize(new Dimension(320, 240));
/*   61:     */     
/*   62:     */ 
/*   63:1679 */     listScroller.setMinimumSize(new Dimension(250, 80));
/*   64:1680 */     listScroller.setAlignmentX(0.0F);
/*   65:     */     
/*   66:     */ 
/*   67:     */ 
/*   68:     */ 
/*   69:     */ 
/*   70:1686 */     JPanel listPane = new JPanel();
/*   71:1687 */     listPane.setLayout(new BoxLayout(listPane, 1));
/*   72:1688 */     JLabel label = new JLabel(labelText);
/*   73:1689 */     label.setLabelFor(this.list);
/*   74:1690 */     listPane.add(label);
/*   75:1691 */     listPane.add(Box.createRigidArea(new Dimension(0, 5)));
/*   76:1692 */     listPane.add(listScroller);
/*   77:1693 */     listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*   78:     */     
/*   79:     */ 
/*   80:1696 */     JPanel buttonPane = new JPanel();
/*   81:1697 */     buttonPane.setLayout(new BoxLayout(buttonPane, 0));
/*   82:1698 */     buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
/*   83:1699 */     buttonPane.add(Box.createHorizontalGlue());
/*   84:1700 */     buttonPane.add(this.cancelButton);
/*   85:1701 */     buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
/*   86:1702 */     buttonPane.add(this.setButton);
/*   87:     */     
/*   88:     */ 
/*   89:1705 */     Container contentPane = getContentPane();
/*   90:1706 */     contentPane.add(listPane, "Center");
/*   91:1707 */     contentPane.add(buttonPane, "South");
/*   92:1708 */     pack();
/*   93:1709 */     addKeyListener(new KeyAdapter()
/*   94:     */     {
/*   95:     */       public void keyPressed(KeyEvent ke)
/*   96:     */       {
/*   97:1712 */         int code = ke.getKeyCode();
/*   98:1713 */         if (code == 27)
/*   99:     */         {
/*  100:1714 */           ke.consume();
/*  101:1715 */           MoreWindows.this.value = null;
/*  102:1716 */           MoreWindows.this.setVisible(false);
/*  103:     */         }
/*  104:     */       }
/*  105:     */     });
/*  106:     */   }
/*  107:     */   
/*  108:     */   public String showDialog(Component comp)
/*  109:     */   {
/*  110:1726 */     this.value = null;
/*  111:1727 */     setLocationRelativeTo(comp);
/*  112:1728 */     setVisible(true);
/*  113:1729 */     return this.value;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public void actionPerformed(ActionEvent e)
/*  117:     */   {
/*  118:1738 */     String cmd = e.getActionCommand();
/*  119:1739 */     if (cmd.equals("Cancel"))
/*  120:     */     {
/*  121:1740 */       setVisible(false);
/*  122:1741 */       this.value = null;
/*  123:     */     }
/*  124:1742 */     else if (cmd.equals("Select"))
/*  125:     */     {
/*  126:1743 */       this.value = ((String)this.list.getSelectedValue());
/*  127:1744 */       setVisible(false);
/*  128:1745 */       this.swingGui.showFileWindow(this.value, -1);
/*  129:     */     }
/*  130:     */   }
/*  131:     */   
/*  132:     */   private class MouseHandler
/*  133:     */     extends MouseAdapter
/*  134:     */   {
/*  135:     */     private MouseHandler() {}
/*  136:     */     
/*  137:     */     public void mouseClicked(MouseEvent e)
/*  138:     */     {
/*  139:1755 */       if (e.getClickCount() == 2) {
/*  140:1756 */         MoreWindows.this.setButton.doClick();
/*  141:     */       }
/*  142:     */     }
/*  143:     */   }
/*  144:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.MoreWindows
 * JD-Core Version:    0.7.0.1
 */