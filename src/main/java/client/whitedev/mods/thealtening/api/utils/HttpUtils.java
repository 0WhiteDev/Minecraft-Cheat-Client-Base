/*    */ package client.whitedev.mods.thealtening.api.utils;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
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
/*    */ public class HttpUtils
/*    */ {
/*    */   protected String connect(String link) throws IOException {
/* 30 */     URL url = new URL(link);
/* 31 */     InputStream inputStream = url.openStream();
/* 32 */     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
/*    */     
/* 34 */     StringBuilder stringBuilder = new StringBuilder(); String line;
/* 35 */     while ((line = br.readLine()) != null) {
/* 36 */       stringBuilder.append(line).append("\n");
/*    */     }
/* 38 */     return stringBuilder.toString();
/*    */   }
/*    */ }