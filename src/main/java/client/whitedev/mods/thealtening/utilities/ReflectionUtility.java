/*    */ package client.whitedev.mods.thealtening.utilities;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionUtility
/*    */ {
/*    */   private String className;
/*    */   private Class<?> clazz;
/*    */   
/*    */   public ReflectionUtility(String className) {
/*    */     try {
/* 16 */       this.clazz = Class.forName(className);
/* 17 */     } catch (ClassNotFoundException e) {
/* 18 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setStaticField(String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
/* 24 */     Field field = this.clazz.getDeclaredField(fieldName);
/*    */     
/* 26 */     field.setAccessible(true);
/*    */     
/* 28 */     Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 29 */     modifiersField.setAccessible(true);
/* 30 */     modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*    */     
/* 32 */     field.set((Object)null, newValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtenin\\utilities\ReflectionUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */