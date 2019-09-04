package com.lingyi.autiovideo.test.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.IRecvMessageListener;
import com.bnc.activity.entity.MsgMessageEntity;
import com.bnc.activity.utils.MsgUtil;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.ChatAdapter;

import java.util.ArrayList;

/**
 * <pre>
 *     author  : devyk on 2019-09-04 14:56
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ChatFragment
 * </pre>
 */
public class ChatFragment extends BaseFragment implements View.OnClickListener {


    private static ChatFragment chatFragment;
    private EditText editText;
    private RecyclerView recyclerView;
    private Button btnMessage;
    private ChatAdapter chatAdapter;

    ArrayList<String> messageEntityArrayList = new ArrayList<>();

    public static ChatFragment getInstance() {
        chatFragment = new ChatFragment();
        return chatFragment;
    }


    @Override
    protected void initData() {
        recyclerView = mView.findViewById(R.id.rlv);
        editText = mView.findViewById(R.id.et_meg);
        btnMessage = mView.findViewById(R.id.btn_send_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter(messageEntityArrayList);
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    protected void initListener() {
        btnMessage.setOnClickListener(this);
        /**
         * 当前发送消息的监听
         */
        T01Helper.getInstance().getMessageEngine().recvMessageListener(new IRecvMessageListener() {
            @Override
            public void setSEND_MSG_ERROR(String uniqueID, int reason) {
                Log.i(TAG, "失败");
                ToastUtils.showShort("发送失败");
                editText.setText("");
            }

            @Override
            public void setSEND_MSG_SUCCEED(String uniqueID) {
                Log.i(TAG, "成功");
                ToastUtils.showShort("发送成功");
                editText.setText("");
            }

            /**
             * 当前接收到的消息
             * @param msgMessageEntity
             */
            @Override
            public void getCurrentRevMeg(MsgMessageEntity msgMessageEntity) {
                Log.i(TAG, msgMessageEntity.toString());
                chatAdapter.addData(msgMessageEntity.getMessageContent());
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void getUserIsOnline(boolean b, boolean b1) {

            }

            /**
             * 第一次打开页面收到的历史消息
             * @param arrayList
             * @param i
             */
            @Override
            public void getAllCurrentMeg(ArrayList<MsgMessageEntity> arrayList, int i) {

            }

            @Override
            public void getMoreMeg(ArrayList<MsgMessageEntity> arrayList, int i) {

            }
        }, 1, 71004226);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_chat;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_message:
                if (editText.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("输入信息。");
                    return;
                }
                chatAdapter.addData(editText.getText().toString().trim());
                chatAdapter.notifyDataSetChanged();
                sendMessage(editText.getText().toString().trim());
                break;
        }

    }

    private void sendMessage(String message) {
        sendMessage(MsgUtil.IMsgType.TXT, message,
                "71004225", "发送者", 71004226, "接收者", null, 1, null, null);
    }

    /**
     * @param messageType    @see MsgUtil.IMsgType.TXT --> 0 :txt,1:视频,3:图片,4:录音,10:文件,
     * @param sendContent   发送的内容
     * @param sendPoliceId  发送者 ID
     * @param sendPoliceName 发送者姓名
     * @param recverID       对方 ID
     * @param recverName     对方姓名
     * @param file           发送的文件
     * @param type           1:单聊 ，2 群聊
     * @param sendVoicelong  语音长度
     * @param uuid           消息唯一 ID
     */
    public void sendMessage(int messageType, String sendContent, String sendPoliceId, String sendPoliceName, int recverID,
                            String recverName, String file, int type, String sendVoicelong, String uuid) {
        T01Helper.getInstance().getMessageEngine().sendMessage(messageType, sendContent, sendPoliceId, sendPoliceName, recverID, recverName, file, type, sendVoicelong, uuid);
    }
}
