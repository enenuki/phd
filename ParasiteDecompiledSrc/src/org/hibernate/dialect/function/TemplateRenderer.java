/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ 
/*   9:    */ public class TemplateRenderer
/*  10:    */ {
/*  11: 40 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TemplateRenderer.class.getName());
/*  12:    */   private final String template;
/*  13:    */   private final String[] chunks;
/*  14:    */   private final int[] paramIndexes;
/*  15:    */   
/*  16:    */   public TemplateRenderer(String template)
/*  17:    */   {
/*  18: 48 */     this.template = template;
/*  19:    */     
/*  20: 50 */     List<String> chunkList = new ArrayList();
/*  21: 51 */     List<Integer> paramList = new ArrayList();
/*  22: 52 */     StringBuffer chunk = new StringBuffer(10);
/*  23: 53 */     StringBuffer index = new StringBuffer(2);
/*  24: 55 */     for (int i = 0; i < template.length(); i++)
/*  25:    */     {
/*  26: 56 */       char c = template.charAt(i);
/*  27: 57 */       if (c == '?')
/*  28:    */       {
/*  29: 58 */         chunkList.add(chunk.toString());
/*  30: 59 */         chunk.delete(0, chunk.length());
/*  31:    */         for (;;)
/*  32:    */         {
/*  33: 61 */           i++;
/*  34: 61 */           if (i >= template.length()) {
/*  35:    */             break label147;
/*  36:    */           }
/*  37: 62 */           c = template.charAt(i);
/*  38: 63 */           if (!Character.isDigit(c)) {
/*  39:    */             break;
/*  40:    */           }
/*  41: 64 */           index.append(c);
/*  42:    */         }
/*  43: 67 */         chunk.append(c);
/*  44:    */         label147:
/*  45: 72 */         paramList.add(Integer.valueOf(index.toString()));
/*  46: 73 */         index.delete(0, index.length());
/*  47:    */       }
/*  48:    */       else
/*  49:    */       {
/*  50: 76 */         chunk.append(c);
/*  51:    */       }
/*  52:    */     }
/*  53: 80 */     if (chunk.length() > 0) {
/*  54: 81 */       chunkList.add(chunk.toString());
/*  55:    */     }
/*  56: 84 */     this.chunks = ((String[])chunkList.toArray(new String[chunkList.size()]));
/*  57: 85 */     this.paramIndexes = new int[paramList.size()];
/*  58: 86 */     for (int i = 0; i < this.paramIndexes.length; i++) {
/*  59: 87 */       this.paramIndexes[i] = ((Integer)paramList.get(i)).intValue();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getTemplate()
/*  64:    */   {
/*  65: 92 */     return this.template;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getAnticipatedNumberOfArguments()
/*  69:    */   {
/*  70: 96 */     return this.paramIndexes.length;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String render(List args, SessionFactoryImplementor factory)
/*  74:    */   {
/*  75:101 */     int numberOfArguments = args.size();
/*  76:102 */     if ((getAnticipatedNumberOfArguments() > 0) && (numberOfArguments != getAnticipatedNumberOfArguments())) {
/*  77:103 */       LOG.missingArguments(getAnticipatedNumberOfArguments(), numberOfArguments);
/*  78:    */     }
/*  79:105 */     StringBuilder buf = new StringBuilder();
/*  80:106 */     for (int i = 0; i < this.chunks.length; i++) {
/*  81:107 */       if (i < this.paramIndexes.length)
/*  82:    */       {
/*  83:108 */         int index = this.paramIndexes[i] - 1;
/*  84:109 */         Object arg = index < numberOfArguments ? args.get(index) : null;
/*  85:110 */         if (arg != null) {
/*  86:111 */           buf.append(this.chunks[i]).append(arg);
/*  87:    */         }
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:115 */         buf.append(this.chunks[i]);
/*  92:    */       }
/*  93:    */     }
/*  94:118 */     return buf.toString();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.TemplateRenderer
 * JD-Core Version:    0.7.0.1
 */