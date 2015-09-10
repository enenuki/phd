/*   1:    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*   2:    */ 
/*   3:    */ import java.util.LinkedList;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ 
/*   6:    */ public class CategoryPath
/*   7:    */ {
/*   8: 40 */   protected LinkedList _categoryElements = new LinkedList();
/*   9:    */   
/*  10:    */   public CategoryPath() {}
/*  11:    */   
/*  12:    */   public CategoryPath(String category)
/*  13:    */   {
/*  14: 58 */     String processedCategory = category;
/*  15: 60 */     if (processedCategory == null) {
/*  16: 61 */       processedCategory = "Debug";
/*  17:    */     }
/*  18: 64 */     processedCategory = processedCategory.replace('/', '.');
/*  19: 65 */     processedCategory = processedCategory.replace('\\', '.');
/*  20:    */     
/*  21: 67 */     StringTokenizer st = new StringTokenizer(processedCategory, ".");
/*  22: 68 */     while (st.hasMoreTokens())
/*  23:    */     {
/*  24: 69 */       String element = st.nextToken();
/*  25: 70 */       addCategoryElement(new CategoryElement(element));
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int size()
/*  30:    */   {
/*  31: 82 */     int count = this._categoryElements.size();
/*  32:    */     
/*  33: 84 */     return count;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isEmpty()
/*  37:    */   {
/*  38: 88 */     boolean empty = false;
/*  39: 90 */     if (this._categoryElements.size() == 0) {
/*  40: 91 */       empty = true;
/*  41:    */     }
/*  42: 94 */     return empty;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void removeAllCategoryElements()
/*  46:    */   {
/*  47:102 */     this._categoryElements.clear();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void addCategoryElement(CategoryElement categoryElement)
/*  51:    */   {
/*  52:109 */     this._categoryElements.addLast(categoryElement);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public CategoryElement categoryElementAt(int index)
/*  56:    */   {
/*  57:116 */     return (CategoryElement)this._categoryElements.get(index);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toString()
/*  61:    */   {
/*  62:121 */     StringBuffer out = new StringBuffer(100);
/*  63:    */     
/*  64:123 */     out.append("\n");
/*  65:124 */     out.append("===========================\n");
/*  66:125 */     out.append("CategoryPath:                   \n");
/*  67:126 */     out.append("---------------------------\n");
/*  68:    */     
/*  69:128 */     out.append("\nCategoryPath:\n\t");
/*  70:130 */     if (size() > 0) {
/*  71:131 */       for (int i = 0; i < size(); i++)
/*  72:    */       {
/*  73:132 */         out.append(categoryElementAt(i).toString());
/*  74:133 */         out.append("\n\t");
/*  75:    */       }
/*  76:    */     } else {
/*  77:136 */       out.append("<<NONE>>");
/*  78:    */     }
/*  79:139 */     out.append("\n");
/*  80:140 */     out.append("===========================\n");
/*  81:    */     
/*  82:142 */     return out.toString();
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath
 * JD-Core Version:    0.7.0.1
 */