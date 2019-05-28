package com.lingyi.autiovideo.test.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * voip联系人实体类
 * 
 * @author
 * 
 */
public class VoipContactEntity implements Serializable, MultiItemEntity ,Parcelable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String name = "";
	public String number = "";
	public int type;
	public static final int TYPE_PHONE = 3;// 手机通讯录
	public static final int TYPE_SYSTEM = 0;// 系统用户ID
	public static final int TYPE_PHONE_ADD = 2;// 添加的手机号
	public static final int TYPE_ADD = 1;// 添加的用户ID
	// 首字母
	private String letterName;
	private boolean isSelect;// 会议室中是否选中此用户

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	// private SlideView slideView;
	//
	// public SlideView getSlideView() {
	// return slideView;
	// }
	//
	// public void setSlideView(SlideView slideView) {
	// this.slideView = slideView;
	// }
	/**
	 * 通话状态1 呼出 2 接听 3 未接
	 */
	private int call_state;

	public int getCall_state() {
		return call_state;
	}

	public void setCall_state(int call_state) {
		this.call_state = call_state;
	}

	public int getCall_type() {
		return call_type;
	}

	public void setCall_type(int call_type) {
		this.call_type = call_type;
	}

	public String getCall_time() {
		return call_time;
	}

	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}

	/**
	 * 呼叫类型
	 */
	private int call_type;
	/**
	 * 通话时间
	 */
	private String call_time;

	public VoipContactEntity() {
	}

	public VoipContactEntity(String name, String number, String letterName) {
		this.name = name;
		this.number = number;
		this.letterName = letterName;
	}

	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	@Override
	public int getItemType() {
		return 1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.number);
		dest.writeInt(this.type);
		dest.writeString(this.letterName);
		dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
		dest.writeInt(this.call_state);
		dest.writeInt(this.call_type);
		dest.writeString(this.call_time);
	}

	protected VoipContactEntity(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.number = in.readString();
		this.type = in.readInt();
		this.letterName = in.readString();
		this.isSelect = in.readByte() != 0;
		this.call_state = in.readInt();
		this.call_type = in.readInt();
		this.call_time = in.readString();
	}

	public static final Creator<VoipContactEntity> CREATOR = new Creator<VoipContactEntity>() {
		@Override
		public VoipContactEntity createFromParcel(Parcel source) {
			return new VoipContactEntity(source);
		}

		@Override
		public VoipContactEntity[] newArray(int size) {
			return new VoipContactEntity[size];
		}
	};
}
