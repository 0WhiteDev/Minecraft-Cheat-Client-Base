/*    */ package client.whitedev.mods.thealtening.api.data;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LicenseData
/*    */ {
/*    */   private String username;
/*    */   private boolean premium;
/*    */   @SerializedName("premium_name")
/*    */   private String premiumName;
/*    */   @SerializedName("expires")
/*    */   private String expiryDate;
/*    */   
/*    */   public String getUsername() {
/* 36 */     return this.username;
/*    */   }
/*    */   
/*    */   public boolean isPremium() {
/* 40 */     return this.premium;
/*    */   }
/*    */   
/*    */   public String getPremiumName() {
/* 44 */     return this.premiumName;
/*    */   }
/*    */   
/*    */   public String getExpiryDate() {
/* 48 */     return this.expiryDate;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return String.format("LicenseData[%s:%s:%s:%s]", new Object[] { this.username, Boolean.valueOf(this.premium), this.premiumName, this.expiryDate });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 59 */     if (!(obj instanceof LicenseData)) {
/* 60 */       return false;
/*    */     }
/* 62 */     LicenseData castedLicenseInfo = (LicenseData)obj;
/*    */     
/* 64 */     return (castedLicenseInfo.getExpiryDate().equals(getExpiryDate()) && castedLicenseInfo.getPremiumName().equals(getPremiumName()) && castedLicenseInfo.isPremium() == isPremium() && castedLicenseInfo.getUsername().equals(getUsername()));
/*    */   }
/*    */ }
