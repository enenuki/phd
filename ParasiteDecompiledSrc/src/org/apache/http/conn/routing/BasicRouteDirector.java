/*   1:    */ package org.apache.http.conn.routing;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.annotation.Immutable;
/*   6:    */ 
/*   7:    */ @Immutable
/*   8:    */ public class BasicRouteDirector
/*   9:    */   implements HttpRouteDirector
/*  10:    */ {
/*  11:    */   public int nextStep(RouteInfo plan, RouteInfo fact)
/*  12:    */   {
/*  13: 53 */     if (plan == null) {
/*  14: 54 */       throw new IllegalArgumentException("Planned route may not be null.");
/*  15:    */     }
/*  16: 58 */     int step = -1;
/*  17: 60 */     if ((fact == null) || (fact.getHopCount() < 1)) {
/*  18: 61 */       step = firstStep(plan);
/*  19: 62 */     } else if (plan.getHopCount() > 1) {
/*  20: 63 */       step = proxiedStep(plan, fact);
/*  21:    */     } else {
/*  22: 65 */       step = directStep(plan, fact);
/*  23:    */     }
/*  24: 67 */     return step;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected int firstStep(RouteInfo plan)
/*  28:    */   {
/*  29: 81 */     return plan.getHopCount() > 1 ? 2 : 1;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected int directStep(RouteInfo plan, RouteInfo fact)
/*  33:    */   {
/*  34: 97 */     if (fact.getHopCount() > 1) {
/*  35: 98 */       return -1;
/*  36:    */     }
/*  37: 99 */     if (!plan.getTargetHost().equals(fact.getTargetHost())) {
/*  38:100 */       return -1;
/*  39:    */     }
/*  40:108 */     if (plan.isSecure() != fact.isSecure()) {
/*  41:109 */       return -1;
/*  42:    */     }
/*  43:112 */     if ((plan.getLocalAddress() != null) && (!plan.getLocalAddress().equals(fact.getLocalAddress()))) {
/*  44:115 */       return -1;
/*  45:    */     }
/*  46:117 */     return 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected int proxiedStep(RouteInfo plan, RouteInfo fact)
/*  50:    */   {
/*  51:132 */     if (fact.getHopCount() <= 1) {
/*  52:133 */       return -1;
/*  53:    */     }
/*  54:134 */     if (!plan.getTargetHost().equals(fact.getTargetHost())) {
/*  55:135 */       return -1;
/*  56:    */     }
/*  57:136 */     int phc = plan.getHopCount();
/*  58:137 */     int fhc = fact.getHopCount();
/*  59:138 */     if (phc < fhc) {
/*  60:139 */       return -1;
/*  61:    */     }
/*  62:141 */     for (int i = 0; i < fhc - 1; i++) {
/*  63:142 */       if (!plan.getHopTarget(i).equals(fact.getHopTarget(i))) {
/*  64:143 */         return -1;
/*  65:    */       }
/*  66:    */     }
/*  67:146 */     if (phc > fhc) {
/*  68:147 */       return 4;
/*  69:    */     }
/*  70:150 */     if (((fact.isTunnelled()) && (!plan.isTunnelled())) || ((fact.isLayered()) && (!plan.isLayered()))) {
/*  71:152 */       return -1;
/*  72:    */     }
/*  73:154 */     if ((plan.isTunnelled()) && (!fact.isTunnelled())) {
/*  74:155 */       return 3;
/*  75:    */     }
/*  76:156 */     if ((plan.isLayered()) && (!fact.isLayered())) {
/*  77:157 */       return 5;
/*  78:    */     }
/*  79:162 */     if (plan.isSecure() != fact.isSecure()) {
/*  80:163 */       return -1;
/*  81:    */     }
/*  82:165 */     return 0;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.routing.BasicRouteDirector
 * JD-Core Version:    0.7.0.1
 */