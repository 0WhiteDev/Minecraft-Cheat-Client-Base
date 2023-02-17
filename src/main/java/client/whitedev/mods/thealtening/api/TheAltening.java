/*     */ package client.whitedev.mods.thealtening.api;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import client.whitedev.mods.thealtening.api.data.AccountData;
/*     */ import client.whitedev.mods.thealtening.api.data.LicenseData;
/*     */ import client.whitedev.mods.thealtening.api.utils.HttpUtils;
/*     */ import client.whitedev.mods.thealtening.api.utils.exceptions.TheAlteningException;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TheAltening
/*     */   extends HttpUtils
/*     */ {
/*     */   private final String apiKey;
/*  35 */   private final String endpoint = "http://api.thealtening.com/v1/";
/*  36 */   private final Logger logger = Logger.getLogger("TheAltening");
/*  37 */   private final Gson gson = new Gson();
/*     */   
/*     */   public TheAltening(String apiKey) {
/*  40 */     this.apiKey = apiKey;
/*     */   }
/*     */   
/*     */   public LicenseData getLicenseData() throws TheAlteningException {
/*     */     try {
/*  45 */       String connectionData = connect("http://api.thealtening.com/v1/license?token=" + this.apiKey);
/*  46 */       JsonObject jsonObject = (JsonObject)this.gson.fromJson(connectionData, JsonObject.class);
/*  47 */       if (jsonObject == null) {
/*  48 */         throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + connectionData);
/*     */       }
/*  50 */       if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
/*  51 */         throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
/*     */       }
/*  53 */       return (LicenseData)this.gson.fromJson((JsonElement)jsonObject, LicenseData.class);
/*     */     }
/*  55 */     catch (IOException e) {
/*  56 */       throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   public AccountData getAccountData() throws TheAlteningException {
/*     */     try {
/*  62 */       String connectionData = connect("http://api.thealtening.com/v1/generate?info=true&token=" + this.apiKey);
/*  63 */       JsonObject jsonObject = (JsonObject)this.gson.fromJson(connectionData, JsonObject.class);
/*  64 */       if (jsonObject == null) {
/*  65 */         throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + connectionData);
/*     */       }
/*  67 */       if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
/*  68 */         throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
/*     */       }
/*  70 */       return (AccountData)this.gson.fromJson((JsonElement)jsonObject, AccountData.class);
/*     */     }
/*  72 */     catch (IOException e) {
/*  73 */       throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPrivate(String token) throws TheAlteningException {
/*     */     try {
/*  79 */       String connectionData = connect("http://api.thealtening.com/v1/private?acctoken=" + token + "&token=" + this.apiKey);
/*  80 */       JsonObject jsonObject = (JsonObject)this.gson.fromJson(connectionData, JsonObject.class);
/*  81 */       if (jsonObject == null) {
/*  82 */         throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + connectionData);
/*     */       }
/*  84 */       if (jsonObject.has("success"))
/*  85 */         return jsonObject.get("success").getAsBoolean(); 
/*  86 */       if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
/*  87 */         throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
/*     */       }
/*  89 */       return false;
/*     */     }
/*  91 */     catch (IOException e) {
/*  92 */       throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFavorite(String token) throws TheAlteningException {
/*     */     try {
/*  98 */       String connectionData = connect("http://api.thealtening.com/v1/favorite?acctoken=" + token + "&token=" + this.apiKey);
/*  99 */       JsonObject jsonObject = (JsonObject)this.gson.fromJson(connectionData, JsonObject.class);
/* 100 */       if (jsonObject == null) {
/* 101 */         throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + connectionData);
/*     */       }
/* 103 */       if (jsonObject.has("success"))
/* 104 */         return jsonObject.get("success").getAsBoolean(); 
/* 105 */       if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
/* 106 */         throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
/*     */       }
/* 108 */       return false;
/*     */     }
/* 110 */     catch (IOException e) {
/* 111 */       throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Asynchronous {
/*     */     private final TheAltening instance;
/*     */     
/*     */     public Asynchronous(TheAltening instance) {
/* 119 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public CompletableFuture<LicenseData> getLicenseData() {
/* 123 */       CompletableFuture<LicenseData> returnValue = new CompletableFuture<>();
/*     */       try {
/* 125 */         returnValue.complete(this.instance.getLicenseData());
/* 126 */       } catch (TheAlteningException exception) {
/* 127 */         returnValue.completeExceptionally((Throwable)exception);
/*     */       } 
/* 129 */       return returnValue;
/*     */     }
/*     */     
/*     */     public CompletableFuture<AccountData> getAccountData() {
/* 133 */       CompletableFuture<AccountData> returnValue = new CompletableFuture<>();
/*     */       try {
/* 135 */         returnValue.complete(this.instance.getAccountData());
/* 136 */       } catch (TheAlteningException exception) {
/* 137 */         returnValue.completeExceptionally((Throwable)exception);
/*     */       } 
/* 139 */       return returnValue;
/*     */     }
/*     */     
/*     */     public CompletableFuture<Boolean> isPrivate(String token) {
/* 143 */       CompletableFuture<Boolean> returnValue = new CompletableFuture<>();
/*     */       try {
/* 145 */         returnValue.complete(Boolean.valueOf(this.instance.isPrivate(token)));
/* 146 */       } catch (TheAlteningException exception) {
/* 147 */         returnValue.completeExceptionally((Throwable)exception);
/*     */       } 
/* 149 */       return returnValue;
/*     */     }
/*     */     
/*     */     public CompletableFuture<Boolean> isFavorite(String token) {
/* 153 */       CompletableFuture<Boolean> returnValue = new CompletableFuture<>();
/*     */       try {
/* 155 */         returnValue.complete(Boolean.valueOf(this.instance.isFavorite(token)));
/* 156 */       } catch (TheAlteningException exception) {
/* 157 */         returnValue.completeExceptionally((Throwable)exception);
/*     */       } 
/* 159 */       return returnValue;
/*     */     }
/*     */   }
/*     */ }