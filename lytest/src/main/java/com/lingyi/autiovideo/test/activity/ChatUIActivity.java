package com.lingyi.autiovideo.test.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.utils.LogHelper;
import com.iit.yk.chat_base_component.imuisample.manager.ChatComponentManager;
import com.iit.yk.chat_base_component.imuisample.models.MyMessage;
import com.lingyi.autiovideo.test.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.jiguang.imui.commons.models.IMessage;

import static com.iit.yk.chat_base_component.imuisample.manager.ChatComponentManager.REQUEST_CODE_CHOOSE;

/**
 * <pre>
 *     author  : devyk on 2019-10-11 12:03
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ChatUIActivity 正在测试阶段---》暂未开放使用
 * </pre>
 */
public class ChatUIActivity extends Activity {

    private ChatComponentManager mChatComponentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_chat);


        initChatUI();
    }

    private void initChatUI() {
        mChatComponentManager = new ChatComponentManager();
        mChatComponentManager.init(this);

        sendMessageListener();

        initChatUIMoreListener();
    }

    /**
     * 发送消息的监听
     */
    public void sendMessageListener() {
        mChatComponentManager.addIChatMessageListener(true, new ChatComponentManager.IChatMessageListener() {
            @Override
            public void sendMessage(int sendType, MyMessage message) {
                switch (sendType) {
                    case 5://IMessage.MessageType.SEND_VOICE
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_TEXT--" + message.toString());
                        break;
                    case 3:
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_TEXT--" + message.toString());
                        break;//IMessage.MessageType.SEND_IMAGE:
                    case 1://IMessage.MessageType.SEND_TEXT
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_TEXT--" + message.toString());
                        break;
                    case 11://IMessage.MessageType.SEND_FILE
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_FILE--" + message.toString());
                        break;
                    case 9://IMessage.MessageType.SEND_LOCATION:
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_LOCATION--" + message.toString());
                        break;
                    case 7://IMessage.MessageType.SEND_VIDEO:
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_VIDEO--" + message.toString());
                        break;
                    case 13:// IMessage.MessageType.SEND_CUSTOM
                        LogHelper.i("sendMessage", "--IMessage.MessageType.SEND_CUSTOM--" + message.toString());
                        break;
                }

            }

            /**
             * 选择图库
             */
            @Override
            public void onSelectPicture() {
                System.out.println("onSelectPicture");
            }

            /**
             * 开始扩展录制
             */
            @Override
            public void onExStartRecordVoice() {
                System.out.println("onExStartRecordVoice");
            }

            /**
             * 停止录制
             */
            @Override
            public void onExStopRecordVoice() {
                System.out.println("onExStopRecordVoice");
            }

            /**
             * 播放  G729 语音
             * @param message
             */
            @Override
            public void onPlayG729Audio(MyMessage message) {
                System.out.println("message = [" + message + "]");
            }

            @Override
            public void loadVideo(ImageView imageCover, String uri) {
                System.out.println("imageCover = [" + imageCover + "], uri = [" + uri + "]");
            }

            @Override
            public boolean onPlayVoice() {
                System.out.println("onPlayVoice");

                return true;
            }

            @Override
            public void onPreviewSend() {
                System.out.println("onPreviewSend");
            }

            @Override
            public void onPreviewCancel() {
                onCancelRecord();
            }

            @Override
            public void onCancelRecord() {
                System.out.println("onCancelRecord");
            }

            /**
             * 是否发送
             * @param isSend
             */
            @Override
            public void stopSendMessage(boolean isSend) {
                System.out.println("isSend = [" + isSend + "]");
            }

            @Override
            public void openFile(String mediaFilePath) {
                System.out.println("mediaFilePath = [" + mediaFilePath + "]");
            }

            /**
             * 这里发送消息异常
             * @param message
             */
            @Override
            public void onStatusViewClick(MyMessage message) {
                System.out.println("message = [" + message + "]");

            }
        });
    }

    /**
     * 更多功能监听
     */
    private void initChatUIMoreListener() {
        mChatComponentManager.addMoreFunctionListener(new ChatComponentManager.MoreFunctionCallBack() {
            @Override
            public void onClickFunction(ChatComponentManager.FunctionType type) {

                switch (type) {
                    case FILE:
                        ToastUtils.showShort(type.name());
                        break;
                    case LOCATION:
                    case VIDEO_CALL:
                    case VOICE_CALL:
                    case RED_ENVELOPES:
                        ToastUtils.showShort("暂未开放，敬请等待!");
                        break;
                }
            }
        });
    }
}
