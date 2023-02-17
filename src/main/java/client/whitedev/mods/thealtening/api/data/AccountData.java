/*    */ package client.whitedev.mods.thealtening.api.data;
/*    */ 
/*    */ import client.whitedev.mods.thealtening.api.data.info.AccountInfo;
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
/*    */ 
/*    */ public class AccountData
/*    */ {
/*    */   private String token;
/*    */   private String password;
/*    */   private String username;
/*    */   private boolean limit;
/*    */   private AccountInfo info;
/*    */   
/*    */   public String getToken() {
/* 36 */     return this.token;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 40 */     return this.password;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 44 */     return this.username;
/*    */   }
/*    */   
/*    */   public boolean isLimit() {
/* 48 */     return this.limit;
/*    */   }
/*    */   
/*    */   public AccountInfo getInfo() {
/* 52 */     return this.info;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return String.format("AccountData[%s:%s:%s:%s]", new Object[] { this.token, this.username, this.password, Boolean.valueOf(this.limit) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 62 */     if (!(obj instanceof AccountData)) {
/* 63 */       return false;
/*    */     }
/* 65 */     AccountData castedAccountInfo = (AccountData)obj;
/* 66 */     return (castedAccountInfo.getUsername().equals(this.username) && castedAccountInfo.getToken().equals(this.token));
/*    */   }
/*    */ }