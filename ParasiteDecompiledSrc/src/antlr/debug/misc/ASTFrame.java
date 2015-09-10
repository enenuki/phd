/*  1:   */ package antlr.debug.misc;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import antlr.CommonAST;
/*  5:   */ import antlr.collections.AST;
/*  6:   */ import java.awt.Container;
/*  7:   */ import java.awt.Frame;
/*  8:   */ import java.awt.event.WindowAdapter;
/*  9:   */ import java.awt.event.WindowEvent;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import javax.swing.JFrame;
/* 12:   */ import javax.swing.event.TreeSelectionEvent;
/* 13:   */ import javax.swing.event.TreeSelectionListener;
/* 14:   */ import javax.swing.tree.TreePath;
/* 15:   */ 
/* 16:   */ public class ASTFrame
/* 17:   */   extends JFrame
/* 18:   */ {
/* 19:   */   static final int WIDTH = 200;
/* 20:   */   static final int HEIGHT = 300;
/* 21:   */   
/* 22:   */   class MyTreeSelectionListener
/* 23:   */     implements TreeSelectionListener
/* 24:   */   {
/* 25:   */     MyTreeSelectionListener() {}
/* 26:   */     
/* 27:   */     public void valueChanged(TreeSelectionEvent paramTreeSelectionEvent)
/* 28:   */     {
/* 29:27 */       TreePath localTreePath = paramTreeSelectionEvent.getPath();
/* 30:28 */       System.out.println("Selected: " + localTreePath.getLastPathComponent());
/* 31:   */       
/* 32:30 */       Object[] arrayOfObject = localTreePath.getPath();
/* 33:31 */       for (int i = 0; i < arrayOfObject.length; i++) {
/* 34:32 */         System.out.print("->" + arrayOfObject[i]);
/* 35:   */       }
/* 36:34 */       System.out.println();
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public ASTFrame(String paramString, AST paramAST)
/* 41:   */   {
/* 42:39 */     super(paramString);
/* 43:   */     
/* 44:   */ 
/* 45:42 */     MyTreeSelectionListener localMyTreeSelectionListener = new MyTreeSelectionListener();
/* 46:43 */     JTreeASTPanel localJTreeASTPanel = new JTreeASTPanel(new JTreeASTModel(paramAST), null);
/* 47:44 */     Container localContainer = getContentPane();
/* 48:45 */     localContainer.add(localJTreeASTPanel, "Center");
/* 49:46 */     addWindowListener(new WindowAdapter()
/* 50:   */     {
/* 51:   */       public void windowClosing(WindowEvent paramAnonymousWindowEvent)
/* 52:   */       {
/* 53:48 */         Frame localFrame = (Frame)paramAnonymousWindowEvent.getSource();
/* 54:49 */         localFrame.setVisible(false);
/* 55:50 */         localFrame.dispose();
/* 56:   */       }
/* 57:53 */     });
/* 58:54 */     setSize(200, 300);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static void main(String[] paramArrayOfString)
/* 62:   */   {
/* 63:59 */     ASTFactory localASTFactory = new ASTFactory();
/* 64:60 */     CommonAST localCommonAST = (CommonAST)localASTFactory.create(0, "ROOT");
/* 65:61 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C1"));
/* 66:62 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C2"));
/* 67:63 */     localCommonAST.addChild((CommonAST)localASTFactory.create(0, "C3"));
/* 68:   */     
/* 69:65 */     ASTFrame localASTFrame = new ASTFrame("AST JTree Example", localCommonAST);
/* 70:66 */     localASTFrame.setVisible(true);
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.misc.ASTFrame
 * JD-Core Version:    0.7.0.1
 */