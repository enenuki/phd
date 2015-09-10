/*   1:    */ package javassist.tools.web;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.Socket;
/*   5:    */ 
/*   6:    */ class ServiceThread
/*   7:    */   extends Thread
/*   8:    */ {
/*   9:    */   Webserver web;
/*  10:    */   Socket sock;
/*  11:    */   
/*  12:    */   public ServiceThread(Webserver w, Socket s)
/*  13:    */   {
/*  14:396 */     this.web = w;
/*  15:397 */     this.sock = s;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void run()
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22:402 */       this.web.process(this.sock);
/*  23:    */     }
/*  24:    */     catch (IOException e) {}
/*  25:    */   }
/*  26:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.web.ServiceThread
 * JD-Core Version:    0.7.0.1
 */