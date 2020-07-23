package chikitsune.swap_things.json_obj;

public class SWUserCacheJson {
 private String userName;
 private String UUID;
 
 public SWUserCacheJson() { }
 public SWUserCacheJson(String userNameIn, String UUIDIn) {
  this.userName=userNameIn;
  this.UUID=UUIDIn;
 }
 
 public String getUserName() {
  return userName;
 }
 public String getUUID() {
  return UUID;
 }
 public void setUserName(String newName) {
  this.userName=newName;
 }
 public void setUUID(String newUUID) {
  this.UUID=newUUID;
 }
 public String toString() {
  return userName+" "+UUID;
 }

}
