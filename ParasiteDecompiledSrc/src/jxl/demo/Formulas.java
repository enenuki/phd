/*   1:    */ package jxl.demo;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import jxl.Cell;
/*  12:    */ import jxl.CellType;
/*  13:    */ import jxl.FormulaCell;
/*  14:    */ import jxl.Sheet;
/*  15:    */ import jxl.Workbook;
/*  16:    */ import jxl.biff.CellReferenceHelper;
/*  17:    */ import jxl.biff.formula.FormulaException;
/*  18:    */ 
/*  19:    */ public class Formulas
/*  20:    */ {
/*  21:    */   public Formulas(Workbook w, OutputStream out, String encoding)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 58 */     if ((encoding == null) || (!encoding.equals("UnicodeBig"))) {
/*  25: 60 */       encoding = "UTF8";
/*  26:    */     }
/*  27:    */     try
/*  28:    */     {
/*  29: 65 */       OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
/*  30: 66 */       BufferedWriter bw = new BufferedWriter(osw);
/*  31:    */       
/*  32: 68 */       ArrayList parseErrors = new ArrayList();
/*  33: 70 */       for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++)
/*  34:    */       {
/*  35: 72 */         Sheet s = w.getSheet(sheet);
/*  36:    */         
/*  37: 74 */         bw.write(s.getName());
/*  38: 75 */         bw.newLine();
/*  39:    */         
/*  40: 77 */         Cell[] row = null;
/*  41: 78 */         Cell c = null;
/*  42: 80 */         for (int i = 0; i < s.getRows(); i++)
/*  43:    */         {
/*  44: 82 */           row = s.getRow(i);
/*  45: 84 */           for (int j = 0; j < row.length; j++)
/*  46:    */           {
/*  47: 86 */             c = row[j];
/*  48: 87 */             if ((c.getType() == CellType.NUMBER_FORMULA) || (c.getType() == CellType.STRING_FORMULA) || (c.getType() == CellType.BOOLEAN_FORMULA) || (c.getType() == CellType.DATE_FORMULA) || (c.getType() == CellType.FORMULA_ERROR))
/*  49:    */             {
/*  50: 93 */               FormulaCell nfc = (FormulaCell)c;
/*  51: 94 */               StringBuffer sb = new StringBuffer();
/*  52: 95 */               CellReferenceHelper.getCellReference(c.getColumn(), c.getRow(), sb);
/*  53:    */               try
/*  54:    */               {
/*  55:100 */                 bw.write("Formula in " + sb.toString() + " value:  " + c.getContents());
/*  56:    */                 
/*  57:102 */                 bw.flush();
/*  58:103 */                 bw.write(" formula: " + nfc.getFormula());
/*  59:104 */                 bw.flush();
/*  60:105 */                 bw.newLine();
/*  61:    */               }
/*  62:    */               catch (FormulaException e)
/*  63:    */               {
/*  64:109 */                 bw.newLine();
/*  65:110 */                 parseErrors.add(s.getName() + '!' + sb.toString() + ": " + e.getMessage());
/*  66:    */               }
/*  67:    */             }
/*  68:    */           }
/*  69:    */         }
/*  70:    */       }
/*  71:117 */       bw.flush();
/*  72:118 */       bw.close();
/*  73:120 */       if (parseErrors.size() > 0)
/*  74:    */       {
/*  75:122 */         System.err.println();
/*  76:123 */         System.err.println("There were " + parseErrors.size() + " errors");
/*  77:    */         
/*  78:125 */         Iterator i = parseErrors.iterator();
/*  79:126 */         while (i.hasNext()) {
/*  80:128 */           System.err.println(i.next());
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:    */     catch (UnsupportedEncodingException e)
/*  85:    */     {
/*  86:134 */       System.err.println(e.toString());
/*  87:    */     }
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.demo.Formulas
 * JD-Core Version:    0.7.0.1
 */