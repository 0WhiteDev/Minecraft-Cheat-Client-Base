/*    */ package client.whitedev.mods.thealtening.utilities;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLSession;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import javax.net.ssl.X509TrustManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSLVerification
/*    */ {
/*    */   private boolean verified = false;
/*    */   
/*    */   public void verify() {
/* 18 */     if (!this.verified) {
/* 19 */       bypassSSL();
/* 20 */       whitelistTheAltening();
/* 21 */       this.verified = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void bypassSSL() {
/* 27 */     TrustManager[] trustAllCerts = { new X509TrustManager()
/*    */         {
/*    */           public X509Certificate[] getAcceptedIssuers() {
/* 30 */             return null;
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*    */ 
/*    */ 
/*    */           
/*    */           public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/*    */         } };
/*    */     try {
/* 43 */       SSLContext sc = SSLContext.getInstance("SSL");
/* 44 */       sc.init(null, trustAllCerts, new SecureRandom());
/* 45 */       HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/* 46 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void whitelistTheAltening() {
/* 52 */     HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> 
/* 53 */         (hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com")));
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtenin\\utilities\SSLVerification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */