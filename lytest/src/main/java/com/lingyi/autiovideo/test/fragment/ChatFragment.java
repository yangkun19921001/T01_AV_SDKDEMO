package com.lingyi.autiovideo.test.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.bnc.activity.PttApplication;
import com.bnc.activity.T01Helper;
import com.bnc.activity.callback.IRecvMessageListener;
import com.bnc.activity.entity.MsgMessageEntity;
import com.bnc.activity.service.module.message.MsgPlayManager;
import com.bnc.activity.service.module.message.MsgRecordManager;
import com.bnc.activity.utils.AudioPlayUtil;
import com.bnc.activity.utils.MsgUtil;
import com.lingyi.autiovideo.test.R;
import com.lingyi.autiovideo.test.adapter.ChatAdapter;

import java.io.File;
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
    private EditText editText, et_target;
    private RecyclerView recyclerView;
    private Button btnMessage, btn_save, btn_load_more, btn_test_record, btn_stop_play;
    private ChatAdapter chatAdapter;

    ArrayList<String> messageEntityArrayList = new ArrayList<>();
    private String targetNumber;

    /**
     * 每次加载 5 条数据
     */
    private int pageCount = 5;

    /**
     * 当前与聊天消息总数量
     */
    private int currentAllMessage = 0;

    /**
     * 获取数据的开始位置
     */
    private int pageIndex = 0;

    private boolean isLoadMore = false;

    public static ChatFragment getInstance() {
        chatFragment = new ChatFragment();
        return chatFragment;
    }


    @Override
    protected void initData() {
        recyclerView = mView.findViewById(R.id.rlv);
        editText = mView.findViewById(R.id.et_meg);
        et_target = mView.findViewById(R.id.et_target);
        btnMessage = mView.findViewById(R.id.btn_send_message);
        btn_save = mView.findViewById(R.id.btn_save);
        btn_load_more = mView.findViewById(R.id.btn_load_more);
        btn_test_record = mView.findViewById(R.id.btn_test_record);
        btn_stop_play = mView.findViewById(R.id.btn_stop_play);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter(messageEntityArrayList);
        recyclerView.setAdapter(chatAdapter);


    }

    @Override
    protected void initListener() {
        btnMessage.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_load_more.setOnClickListener(this);
        btn_stop_play.setOnClickListener(this);

        btn_test_record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ToastUtils.showShort("开始录音...");
                        btn_test_record.setText("正在录音");
                        recordAudio(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_test_record.setText("开始录音");
                        ToastUtils.showShort("录音结束...");
                        recordAudio(false);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void recordAudio(boolean b) {
        if (b) {
            T01Helper.getInstance().getMessageEngine().startRecordAudio(new MsgRecordManager.IMsgRecordListenter() {
                @Override
                public void onMessageRecordEnd(final File file, final long recordTime) {
                    ToastUtils.showShort("得到录音文件：" + file.getAbsolutePath() + " 时长：" + recordTime / 1000 + "s" + " 开始播放");

                    if (file != null && file.exists() && file.isFile())
                        T01Helper.getInstance().getMessageEngine().playRecordAudio(file.getAbsolutePath());


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatAdapter.addData(file.getAbsolutePath() + "   时长：" + (recordTime / 1000) + "");
                            recyclerView.smoothScrollToPosition(chatAdapter.getData().size() - 1);
                            chatAdapter.notifyDataSetChanged();
                        }
                    });
                    sendMessage(file.getAbsolutePath(), file.getAbsolutePath(), (recordTime / 1000) + "");
                }

                @Override
                public void onVolume(String volume) {
                    Log.d(TAG, "当前声音分贝：" + volume);
                }
            });
        } else {
            T01Helper.getInstance().getMessageEngine().stopRecordAudio();
        }
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
                if (et_target.getText().toString().trim().isEmpty()) {
                    ToastUtils.showShort("输入目标ID");
                    return;
                }


                chatAdapter.addData(editText.getText().toString().trim());
                recyclerView.smoothScrollToPosition(chatAdapter.getData().size() - 1);
                chatAdapter.notifyDataSetChanged();
                sendMessage(editText.getText().toString().trim());
                break;
            case R.id.btn_save:
                save(et_target.getText().toString().trim());
                break;
            case R.id.btn_load_more:
                if (isLoadMore) {
                    ToastUtils.showShort("没有更多消息了。");
                    return;
                }
                pageIndex -= pageCount;
                T01Helper.getInstance().getMessageEngine().loadMoreMeg(1, getIndex(pageIndex), getPageCount(pageIndex), Integer.parseInt(et_target.getText().toString().trim()));
                break;
            case R.id.btn_stop_play:
                T01Helper.getInstance().getMessageEngine().stopPlayRecordAudio();
                break;
        }

    }

    /**
     * 获取当前应该加载消息列表数量
     *
     * @param pageIndex
     * @return
     */
    private int getPageCount(int pageIndex) {
        if (pageIndex < 0)
            pageCount = pageIndex + pageCount;
        return pageCount;
    }

    /**
     * 获取当前加载索引
     *
     * @param pageIndex
     * @return
     */
    private int getIndex(int pageIndex) {
        if (pageIndex < 0) {
            isLoadMore = true;
            pageIndex = 0;
        }
        return pageIndex;
    }

    /**
     * 开始根据号码设置监听
     *
     * @param targetNumber
     */
    private void save(String targetNumber) {
        if (targetNumber.isEmpty()) {
            ToastUtils.showShort("输入目标ID");
            return;
        }
        //获取当前聊天室所有的聊天消息列表数量
        currentAllMessage = T01Helper.getInstance().getMessageEngine().getTargetMessageCount(Integer.parseInt(targetNumber), 1);
        //赋值给加载更多的索引
        pageIndex = currentAllMessage;
        //加载当前对话的默认聊天内容，默认加载 5 条
        T01Helper.getInstance().getMessageEngine().loadDefaultMeg(1, pageCount, Integer.parseInt(targetNumber));
        //如果需要加载更多就需要默认减去 5 条
        pageIndex -= pageCount;
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
                Log.i(TAG, "getAllCurrentMeg--->" + arrayList.size());
                for (MsgMessageEntity msgMessageEntity : arrayList) {
                    if (msgMessageEntity.getMessageContentType() == MsgUtil.IMsgType.RADIO)
                        chatAdapter.addData(msgMessageEntity.getMessageContent() + " 时长：" + msgMessageEntity.getRecordTime());
                    else
                        chatAdapter.addData(msgMessageEntity.getMessageContent());
                }
            }

            @Override
            public void getMoreMeg(ArrayList<MsgMessageEntity> arrayList, int i) {
                Log.i(TAG, "getMoreMeg--->" + arrayList.size());
                //这里定义的 index 目的是加载列表起始位置
                int index = 0;
                for (MsgMessageEntity msgMessageEntity : arrayList) {
                    chatAdapter.addData(index, msgMessageEntity.getMessageContent());
                    index += 1;
                }
                chatAdapter.notifyDataSetChanged();
            }
        }, 1, Integer.parseInt(targetNumber));
    }

    private void sendMessage(String message) {
        sendMessage(MsgUtil.IMsgType.TXT, message, Integer.parseInt(et_target.getText().toString().trim()), "接收者：" + Integer.parseInt(et_target.getText().toString().trim()), null, 1, null, null,false);
    }

    private void sendMessage(String message, String filePath, String time) {
        sendMessage(MsgUtil.IMsgType.RADIO, message,
                Integer.parseInt(et_target.getText().toString().trim()), "接收者：" + Integer.parseInt(et_target.getText().toString().trim()), filePath, 1, time, null,false);
    }

    /**
     * @param messageType    @see MsgUtil.IMsgType.TXT --> 0 :txt,1:视频,3:图片,4:录音,10:文件,
     * @param sendContent    发送的内容

     * @param recverID       对方 ID
     * @param recverName     对方姓名
     * @param file           发送的文件
     * @param type           1:单聊 ，2 群聊
     * @param sendVoicelong  语音长度
     * @param uuid           消息唯一 ID
     */
    public void sendMessage(int messageType, String sendContent, int recverID,
                            String recverName, String file, int type, String sendVoicelong, String uuid,boolean isResend) {
        T01Helper.getInstance().getMessageEngine().sendMessage(messageType, sendContent, recverID, recverName, file, type, sendVoicelong, uuid,isResend);
    }
}
