package net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded;

import javax.swing.tree.TreeModel;

public abstract interface TreeTableModel
  extends TreeModel
{
  public abstract int getColumnCount();
  
  public abstract String getColumnName(int paramInt);
  
  public abstract Class getColumnClass(int paramInt);
  
  public abstract Object getValueAt(Object paramObject, int paramInt);
  
  public abstract boolean isCellEditable(Object paramObject, int paramInt);
  
  public abstract void setValueAt(Object paramObject1, Object paramObject2, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.downloaded.TreeTableModel
 * JD-Core Version:    0.7.0.1
 */