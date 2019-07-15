package com.lingyi.autiovideo.test.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.ALog;
import com.bnc.activity.PttApplication;
import com.bnc.activity.entity.UserEntity;
import com.bnc.activity.utils.PropertyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyi.autiovideo.test.R;

import org.doubango.ngn.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yangk on 2017/12/15.
 */

public class CurrentDepartmentAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {

    private int onLine_1 = 1; //在线
    private int offLine = 2;//离线
    private int showMe = 3;//我自己
    private int position;
    private ArrayList<UserEntity> onLine = null;

    private ArrayList<UserEntity> userList = null; //用户列表信息

    private PinyinComparator pinyinComparator;
    private IDComparator idComparator;
    private ItemClickListener itemClickListener;

    public CurrentDepartmentAdapter(List<UserEntity> data) {
        super(R.layout.item_current_bm_pserson,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, UserEntity user) {
        position = baseViewHolder.getPosition();
        int section = getSectionForPosition(baseViewHolder.getPosition());
                TextView letter = baseViewHolder.getView(R.id.user_catalog);
                ImageView user_icon = baseViewHolder.getView(R.id.user_icon);
                ImageView iv_voice_call = baseViewHolder.getView(R.id.iv_voice_call);
                ImageView iv_video_call = baseViewHolder.getView(R.id.iv_video_call);
                setCallListener(iv_voice_call, iv_video_call, user.getUserId(),user);
                View cb = baseViewHolder.getView(R.id.cb);
                cb.setVisibility(View.GONE);
                TextView user_name = baseViewHolder.getView(R.id.tv_name);
                TextView user_id = baseViewHolder.getView(R.id.tv_content);

                if (section == -1) {
                    letter.setVisibility(View.VISIBLE);
                    letter.setText("我的账号");
                } else {
                    String orderStyle = PropertyUtil.getInstance(PttApplication.getInstance()).getString(
                            Constants.ORDER_STYLE, "0x02"); // 字母:letter,ID:id
                    // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                    if (position == getPositionForSection(section)
                            && onLine.contains(user)) {
                        if ("0x02".equals(orderStyle)) {
                            letter.setVisibility(View.VISIBLE);
                            letter.setText(user.getLetterName());
                        } else {
                            letter.setVisibility(View.GONE);
                        }
                    } else {
                        if (position == onLine.size()) {
                            letter.setVisibility(View.VISIBLE);
                            letter.setText("离线用户");
                        } else {
                            letter.setVisibility(View.GONE);
                        }
                    }
                }
                if (null != user) {
                    int onLineState = user.getUserState();
                    Log.w(TAG, "userName - " + user.getUserId() + " onLineState - "
                            + onLineState);
                    user_id.setText(String.valueOf(user.getUserId()));
                    user_name.setText(user.getUserName());
                    // 断线状态
                    if (!Constants.isOnline) {
                        user_icon.setImageResource(R.mipmap.user_offline);
                    } else {
                        // 在线状态
                        // 是否在线头像状态
                        user_icon.setImageResource(onLineState == 1 ? R.mipmap.user_online
                                : R.mipmap.user_offline);
                        if (onLineState == 1 && user.getUserId() != Integer.parseInt(PttApplication.getInstance().getUserId())) {
                            iv_voice_call.setVisibility(View.VISIBLE);
                            iv_video_call.setVisibility(View.VISIBLE);
                        }else {
                            iv_voice_call.setVisibility(View.GONE);
                            iv_video_call.setVisibility(View.GONE);
                        }
                        // 呼叫空闲状态
                        if (Constants.callState == Constants.CALL_STATE_FREE) {
                            // 单选更改背景
                            if (user.getUserId() == Constants.targetUserId) {
                            } else {
                            }
                        } else if (Constants.callState == Constants.CALL_STATE_RECORDING) {
                            // 主呼
                            if (user.getUserId() == Constants.callUserId) {
                                user_icon.setImageResource(R.mipmap.speaker);
                            }

                            if (Constants.callType == Constants.CALL_TYPE_SINGLE) {
                                if (user.getUserId() == Constants.targetUserId) {
                                } else {
                                }
                            }
                        } else if (Constants.callState == Constants.CALL_STATE_PLAYING) {
                            ALog.w(
                                    TAG,
                                    "getView() --> " + " callType - "
                                            + Constants.callType + " callUserId - "
                                            + Constants.callUserId + " userId - "
                                            + user.getUserId());
                            // 被呼
                            if (Constants.callType == Constants.CALL_TYPE_SINGLE) {
                                if (user.getUserId() == Constants.callUserId)
                                    user_icon.setImageResource(R.mipmap.speaker);
                                if (user.getUserId() == Constants.targetUserId) {
                                } else {
                                }
                            } else if (Constants.callType == Constants.CALL_TYPE_MULTI) {
                                if (user.getUserId() == Constants.callUserId)
                                    user_icon.setImageResource(R.mipmap.speaker);
                                if (user.getUserId() == Constants.targetUserId) {
                                } else {
                                }
                            }
                        }
                    }
                }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
       try {
           if (getData().get(position).getUserId() == Constants.curUserId)
               return -1;
           return getData().get(position).getLetterName().charAt(0);
       }catch (Exception e){
           Log.e(TAG,e.getMessage());
       }

       return 0;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置 第一个位置不比较，为自己账号
     */
    public int getPositionForSection(int section) {
        try {
            for (int i = 1; i < getData().size(); i++) {
                String sortStr = getData().get(i).getLetterName();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }


        return -1;
    }

    public void setOnlineData(ArrayList<UserEntity> onLine) {

        this.onLine = onLine;
    }


    /**
     * 按字母顺序排列
     *
     * @author Administrator
     */
    public static class PinyinComparator implements Comparator<UserEntity> {
        public int compare(UserEntity o1, UserEntity o2) {
            if (o1.getUserId() == Constants.curUserId) {
                return -1;
            } else if (o2.getUserId() == Constants.curUserId) {
                return 1;
            } else if (o1.getUserState() == 0 || o2.getUserState() == 0) {
                return o2.getUserState() - o1.getUserState();
            } else if (o1.getLetterName().equals("@")
                    || o2.getLetterName().equals("#")) {
                return -1;
            } else if (o1.getLetterName().equals("#")
                    || o2.getLetterName().equals("@")) {
                return 1;
            } else {
                return o1.getLetterName().compareTo(o2.getLetterName());
            }
        }
    }

    public static class IDComparator implements Comparator<UserEntity> {
        public int compare(UserEntity obj1, UserEntity obj2) {
            if (obj1.getUserId() == Constants.curUserId) {
                return -1;
            } else if (obj2.getUserId() == Constants.curUserId) {
                return 1;
            } else if (obj1.getUserState() == 0 || obj2.getUserState() == 0) {
                return obj2.getUserState() - obj1.getUserState();
            } else if (obj1.getUserId() > obj2.getUserId()) {
                return 1;
            } else if (obj1.getUserId() < obj2.getUserId()) {
                return -1;
            }
            return 0;

        }
    }

    /**
     * 设置用户列表数据
     *
     * @param list
     */
    @SuppressWarnings("unchecked")
    public void setUserList(ArrayList<UserEntity> list) {
        Log.d(TAG, "****************begin**********************");
        if (list == null)
            return;

        if (userList == null)
            userList = new ArrayList<UserEntity>();
        else
            userList.clear();
        if (null == onLine)
            onLine = new ArrayList<UserEntity>();
        else
            onLine.clear();
        if (null == pinyinComparator)
            pinyinComparator = new PinyinComparator();
        if (null == idComparator)
            idComparator = new IDComparator();
        for (UserEntity user : list) {
            if (user.getUserState() == 1)
                onLine.add(user);
        }
        userList = (ArrayList<UserEntity>) list.clone();
        // for(UserEntity entity : userList) {
        // Log.d(TAG, "userId = " + entity.getUserId() + " UserName = " +
        // entity.getUserName());
        // }
        String orderStyle = PropertyUtil.getInstance(PttApplication.getInstance()).getString(
                Constants.ORDER_STYLE, "0x02"); // 字母:letter,ID:id
        if ("0x02".equals(orderStyle)) {
            // 根据a-z进行排序源数据
            Collections.sort(userList, pinyinComparator);
        } else {
            Collections.sort(userList, idComparator);
        }
        Log.d(TAG, "******************end********************");
    }

    public ArrayList<UserEntity> getUserList() {
        return userList;
    }


    public void setCallListener(ImageView voiceCall, ImageView viceoCall, int number, final UserEntity user) {
        voiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.voipVoiceCall(user);
//                NgnManage.getInstance(PttApplication.getInstance()).callPhone(SPUtils.getInstance(SpTag.PACKAGE_NAME).getString(SpTag.userID), number + "", NgnMediaType.Audio, CallActivity.class);
            }
        });

        viceoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.voipVideoCall(user);
//                NgnManage.getInstance(PttApplication.getInstance()).callPhone(SPUtils.getInstance(SpTag.PACKAGE_NAME).getString(SpTag.userID), number + "", NgnMediaType.AudioVideo, CallActivity.class);
            }
        });
    }

    public interface ItemClickListener{
        void voipVideoCall(UserEntity userEntity);
        void voipVoiceCall(UserEntity userEntity);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
