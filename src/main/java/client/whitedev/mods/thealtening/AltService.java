/*    */ package client.whitedev.mods.thealtening;
/*    */ 
/*    */ import client.whitedev.mods.thealtening.utilities.ReflectionUtility;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AltService
/*    */ {
/* 15 */   private final ReflectionUtility userAuthentication = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
/* 16 */   private final ReflectionUtility minecraftSession = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
/*    */   
/*    */   private EnumAltService currentService;
/*    */   
/*    */   public void switchService(EnumAltService enumAltService) throws NoSuchFieldException, IllegalAccessException {
/* 21 */     if (this.currentService == enumAltService)
/*    */       return; 
/* 23 */     reflectionFields(enumAltService.hostname);
/*    */     
/* 25 */     this.currentService = enumAltService;
/*    */   }
/*    */   
/*    */   private void reflectionFields(String authServer) throws NoSuchFieldException, IllegalAccessException {
/* 29 */     HashMap<String, URL> userAuthenticationModifies = new HashMap<>();
/* 30 */     String useSecureStart = authServer.contains("thealtening") ? "http" : "https";
/* 31 */     userAuthenticationModifies.put("ROUTE_AUTHENTICATE", constantURL(useSecureStart + "://authserver." + authServer + ".com/authenticate"));
/* 32 */     userAuthenticationModifies.put("ROUTE_INVALIDATE", constantURL(useSecureStart + "://authserver" + authServer + "com/invalidate"));
/* 33 */     userAuthenticationModifies.put("ROUTE_REFRESH", constantURL(useSecureStart + "://authserver." + authServer + ".com/refresh"));
/* 34 */     userAuthenticationModifies.put("ROUTE_VALIDATE", constantURL(useSecureStart + "://authserver." + authServer + ".com/validate"));
/* 35 */     userAuthenticationModifies.put("ROUTE_SIGNOUT", constantURL(useSecureStart + "://authserver." + authServer + ".com/signout"));
/*    */     
/* 37 */     userAuthenticationModifies.forEach((key, value) -> {
/*    */           try {
/*    */             this.userAuthentication.setStaticField(key, value);
/* 40 */           } catch (Exception e) {
/*    */             e.printStackTrace();
/*    */           } 
/*    */         });
/* 44 */     this.userAuthentication.setStaticField("BASE_URL", useSecureStart + "://authserver." + authServer + ".com/");
/* 45 */     this.minecraftSession.setStaticField("BASE_URL", useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/");
/* 46 */     this.minecraftSession.setStaticField("JOIN_URL", constantURL(useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/join"));
/* 47 */     this.minecraftSession.setStaticField("CHECK_URL", constantURL(useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/hasJoined"));
/* 48 */     this.minecraftSession.setStaticField("WHITELISTED_DOMAINS", new String[] { ".minecraft.net", ".mojang.com", ".thealtening.com" });
/*    */   }
/*    */   
/*    */   private URL constantURL(String url) {
/*    */     try {
/* 53 */       return new URL(url);
/* 54 */     } catch (MalformedURLException ex) {
/* 55 */       throw new Error("Couldn't create constant for " + url, ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public EnumAltService getCurrentService() {
/* 60 */     if (this.currentService == null) this.currentService = EnumAltService.MOJANG;
/*    */     
/* 62 */     return this.currentService;
/*    */   }
/*    */   
/*    */   public enum EnumAltService
/*    */   {
/* 67 */     MOJANG("mojang"), THEALTENING("thealtening");
/*    */     String hostname;
/*    */     
/*    */     EnumAltService(String hostname) {
/* 71 */       this.hostname = hostname;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtening\AltService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */