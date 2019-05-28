# T01 Android 端音视频 SDK API 文档

[公司官网](<http://www.t01.com.cn/>)

![](<http://12145169.s21i.faiusr.com/2/ABUIABACGAAgm4u-wgUoyK3c_Qcw8w441QQ.jpg>)

![](<http://12145169.s21i.faiusr.com/2/ABUIABACGAAgusm_wgUonufkhQIwgA842AQ.jpg>)

![](<http://12145169.s21i.faiusr.com/2/ABUIABACGAAg3Yi-wgUo2rT6zQQw-g44ywQ.jpg>)

## 注意

华为手机 APP 长连接 异常问题

```java
//华为手机使用嘀哒软件在后台运行出现人员掉线问题解决
手机 =》 电池 》 更多设置 》休眠保持网络连接
手机 =》 电池 》 启动应用 》切换为手动管理（允许自启动，允许关联启动，允许后台活动）
```

## 快速集成

1. 将 .aar SDK 放入 app/lib ,或者 module/lib 中

2. 在 module/build.gradle 或者 app/build.gradle 中配置

   ```java
   android{   
       ...
   allprojects {
           repositories {
               flatDir {
                   //dirs '../t01_module/libs'; //多模块开发参考这种集成方式。
                    dirs 'libs';
               }
           }
       }
   }
   
   dependencies{
       ...
       compile(name:'LY_AudioVideoHelp_SDK_debug', ext:'aar') //名字以 aar 名字为准
   }
   ```

3.  继承 PttApplication

4. 添加 XML 配置

   ```java
    <service android:name="com.bnc.activity.service.MessengerService" />
    <service android:name="com.bnc.activity.view.NativeService" />
    <receiver android:name="com.bnc.activity.receiver.SipEventReceiver">
               <intent-filter>
                   <action android:name="com.bnc.app.action.INCOMING" />
                   <action android:name="com.bnc.app.action.SMS" />
               </intent-filter>
    </receiver>
   ```

5. 初始化 SDK

   ```
   T01Helper.getInstance().initAppContext(mContext);
   ```

   

## RegisterEngine

- 是否注册

  ```java
  //loginState = 1 为登录
  getIsRegister(final IIsLoginListener iIsLoginListener);
  ```

- 登录

  ```java
  void login(String userId, String pwd, String server_ip, String server_port, final LoginCallBack loginCallBack)
  ```

  

## PttEngine

- 获取当前对讲组

  ```Java
  void getCurrentPttGroup(final PttListCallBack pttListListener)
  ```

  

- 开始对讲

  ```Java
  void startPttGroup();
  ```

  

- 停止对讲

  ```Java
  void stopPttGroup();
  ```

  

- 获取所有对讲组

  ```java
  void getAllPttGroupLists(final PttListCallBack pttListListener);
  ```

  

- 切换对讲组

  ```Java
  String setCurrentPttGroup(int groupId);
  ```

  

- 获取上一次切换的对讲组

  ```java
  int getHistoryChangePttGroup();
  ```

  

- 创建临时对讲组

  ```java
  void createTempPttGroup(String tempGroupName, final ArrayList<Integer> selectList, final ICreateTempListener iCreateTempListener);
  ```

  

- 删除临时对讲组

  ```Java
  void delTmpGroup(int groupId, TempGroupManager.IPttDeleteUpDataListener iPttDeleteUpDataListener);
  ```

  

- 获取所有属于我的组

  ```Java
  List<GroupEntity> getAllGroup();
  ```

  

- 获取对讲通讯录

  ```Java
  void getContactsLists(final IContactsListener callViewBackContact);
  ```

  

- 单呼

  ```Java
  void sendOnePttCall(boolean isCall, int number);
  ```

- 是否自动切换对讲组

  ```Java
  autoChangePtt(boolean isAuto);
  ```

  

## CallEngine

- 设置相机旋转的角度（一般不用设置）

  ```Java
  setCamRotation(int rotation)；
  ```

- 获取相机旋转的角度

  ```Java
  int getCamRotation()；
  ```

- 呼出/呼入 电话监听

  ```Java
  /*** type 1 来电，2 呼叫*/
  void setCallReceiverListener(final CallEventCallBack callEventListener)；
  ```

- 挂断电话

  ```Java
  boolean hangUpCall()；
  ```

- 接听电话

  ```Java
  boolean acceptCall()；
  ```

- 是否开启免提

  ```Java
  void isHandsfree(boolean isHandsfree) ；
  ```

- 获取当前免提状态

  ```Java
  boolean getHandsfreeState()；
  ```

- 是否静音

  ```Java
  boolean isMute()；
  ```

- 预览本地视频

  ```Java
  /***isEx: 如果填写 true 的话需要自己调用 pushYUVB API 自己传输 视频流数据*/
  void startPreviewLocalVideo(FrameLayout localVideoPreview, boolean isEx)；
  ```

- 预览对方视频

  ```Java
  void startPreviewRemoteVideo(FrameLayout remoteVideoPreview) ；
  ```

- 停止预览

  ```Java
  void stopPreviewVideo()；
  ```

- 切换摄像头

  ```Java
  boolean changeCamera()；
  ```

- 关闭本地摄像头

  ```Java
  Boolean stopLocalVideo()；
  ```

- 打电话

  ```Java
  /***launchType : 视频电话：VOIP_LAUNCH_TYPE_VIDEO，语音电话：VOIP_LAUNCH_TYPE_TELE，语音会议：VOIP_LAUNCH_TYPE_TELECONFERENCE，视频会议：VOIP_LAUNCH_TYPE_VIDEOCONFERENCE
  */
  void call(String num, int launchType, String callName) 
  ```

- 获取通话记录

  ```Java
  void getCallHistoryList(final ICallHistoryDataCallBack iCallHistoryDataCallBack);
  ```

- 获取视频通话中视频详细信息

  ```Java
  String getVideoCallInfo();
  ```

- 获取对方分辨率

  ```Java
  String getRemoteDisplay();
  ```

- push YUV 视频裸流

  ```Java
  pushYUV(byte[] videoFrame, int width, int height);
  ```

- 配置对应的 Activity 声明周期

  ```Java
  @Override
      protected void onStart() {
          super.onStart();
          T01Helper.getInstance().getCallEngine().onCallStart();
      }
  
      @Override
      protected void onResume() {
          super.onResume();
          T01Helper.getInstance().getCallEngine().onResume();
      }
  
      @Override
      protected void onPause() {
          T01Helper.getInstance().getCallEngine().onCallPause();
          super.onPause();
      }
  
      @Override
      protected void onStop() {
          T01Helper.getInstance().getCallEngine().onCallStop();
          getWindow().clearFlags(
                  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
          super.onStop();
      }
  ```

- 快速开始通话

  1. 自定义继承 BaseCallLayout ，实现相关函数，并填写对应的布局（详细可以参考 SDKDEMO） 

## MettingEngine

- 获取会议列表

  ```Java
  void getMeetingList(final MeetingCallBack meetingListsListener)c
  ```

- 创建会议组

  ```Java
  //meetingtype 0 语音会议，1 视频会议
  void createMettingGroup(int meetingtype, String groupName, ArrayList<Integer> meetingMember, MeetingCallBack meetingListsListener);
  ```

## MessageEngine

- 发送消息

  ```Java
  //me_0   0 :txt,1:视频,3:图片,4:录音,10:文件
  //type 1:单聊 ，2 群聊
  MsgMessageEntity sendMessage(int me_0, String sendContent, String sendPoliceId
              , String sendPoliceName, int recverID, String recverName, String file, int type, String sendVoicelong);
  ```

- 获取发送消息的监听

  ```Java
  void recvMessageListener(final IRecvMessageListener iRecvMessageListener, int sendType, final int targetUserId);
  ```

- 离开聊天室

  ```Java
  void onChatUiStateOnDestory();
  ```

- 聊天室不可见的情况

  ```Java
  void onChatUiStateOnPause(Class c, String targetUserName, String sendType)
  ```

- 加载历史消息

  ```Java
  void loadDefaultMeg(int sendType, int nowDataCount, int targetUserId)；
  ```

- 更新附件地址

  ```java
  void sendUpdateAttachPathRequest(MsgMessageEntity entity,
                                              boolean isDownLoad);
  ```

- 获取 IM 聊天会话列表

  ```Java
  void getIMLists(final IMListsCallBack imListsCallBack);
  ```

- 后台消息监听

  ```Java
  void showMegToNotity(final IShowNotityCallBack iShowNotity);
  ```

  

## ContactsEngine

- 获取所有的组织

  ```J
   void getALLUnitList(UnitManager.IOtherListener iUnitListener)；
  ```

- 获取 GPS 数据

  ```Java
  void getGPSInfoList(UnitManager.IOtherListener igpsInfoCallBack);
  ```

- 获取所有用户

  ```Java
  void getUserList(UnitManager.IOtherListener iUserInfoCallBack);
  ```

- 获取所有外部用户

  ```Java
  void getWBUserList(UnitManager.IGPSListener iWbInfoCallBack);
  ```

## LocationEngine

- 发送定位数据

  ```Java
  void sendLocation(int m_userLoginId,double longitude, double latitude,double derect,double speed,int location_type);
  ```

  

## SetEngine

- 对讲音量增强

  ```Java
  void setVoiceZoom(int i); //1,4,8,12,16
  ```

- 获取当前对讲音量

  ```Java
  int getVoiceZoom(Context mContext);
  ```

- 获取当前视频编码

  ```J
  String getCurVideoCoding()；
  ```

- 获取当前语音编码

  ```Java
  String getCurVoiceCoding();
  ```

- 获取当前视频质量

  ```J
  String getCurVideoQuality()；
  ```

- 获取当前视频监控质量

  ```Java
  String getCurVideoJKQuality();
  ```

- 获取当前摄像头是否是前置

  ```Java
  boolean isCameraFront();
  ```

- 设置摄像头前置或者后置

  ```Java
  void setCameraFrontOrAfter(boolean isFront) ;
  ```

- 设置视频参数

  ```Java
  //0，h264 , 1,h263 ,2 MPEG4
  void setVideoParameter(int type);
  ```

- 设置通话质量

  ```J
  // 0: 一般，1：标清，2：高清，3：超清，4：2K
  void setVideoCallInCallQuality(int type)；
  ```

- 设置视频监控质量

  ```Java
  void setVideoJKQuality(int type);
  ```

- 修改密码

  ```
  void doUpdatePassWord(String oldPassWord, String updatePassWord, final IUpdatePwdListener iUpdatePwdListener);
  ```

- 通话音量输入增益

  ```Java
  void setMediaInGain(int v); // 0 - 5；
  ```

- 通话音量输出增益

  ```Java
  void setMediaOutGain(int v) ;
  ```

- 获取通话音量输入增益

  ```J
  int getMediaInGain()；
  ```

- 获取通话音量输出增益

  ```Java
  int getMediaOutGain();
  ```

  





##