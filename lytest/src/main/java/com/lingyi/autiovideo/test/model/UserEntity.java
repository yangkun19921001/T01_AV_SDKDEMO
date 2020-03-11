package com.lingyi.autiovideo.test.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 用户
 */
public class UserEntity implements Serializable,Comparable<UserEntity>, MultiItemEntity {
	/** 离线 */
	public static final byte USER_STATE_OFFLINE = 0;
	/** 在线 */
	public static final byte USER_STATE_ONLINE = 1;
	/** 未知 */
	public static final byte USER_STATE_OTHER = 2;
	/** 用户从此组内删除 */
	public static final byte USER_STATE_DELETE = 3;
	/** 临时用户 */
	public static final byte USER_STATE_TEMP = 4;
	/** 用户增加到此组内 */
	public static final byte USER_STATE_ADD = 5;
	/** 正在激活 */
	public static final byte USER_STATE_ACTIVATE = 6;
	/** 用户选择从组中退出 */
	public static final byte USER_STATE_QUIT = 7;

	/** 普通用户 */
	public static final byte USER_STYLE_NORMAL = 0;
	/** 单位Id表示的调度台 */
	public static final byte USER_STYLE_DDT_UNIT_ID = 1;
	/** 调度台Id表示的调度台 */
	public static final byte USER_STYLE_DDT_DDT_ID = 2;

	/** 正常 */
	public static final byte USER_AVAILABLE_STATE_OK = 0;
	/** 遥毙 */
	public static final byte USER_AVAILABLE_STATE_DISABLED = 1;
	/** 引爆 */
	public static final byte USER_AVAILABLE_STATE_SUICIDE = 2;

	private static final long serialVersionUID = 1L;

	/** 用户id */
	private int userId;
	/** 用户名 */
	private String userName;
	/** 用户在线状态 */
	private byte userState;
	/** 用户优先级 */
	private byte userPriority;
	/** 用户可用状态 */
	private byte userAvailableState;
	/** 用户拼音名 */
	private String letterName;
	/** 用户类型 */
	private byte userStyle;

	public boolean isSelectUser() {
		return isSelectUser;
	}

	public void setSelectUser(boolean selectUser) {
		isSelectUser = selectUser;
	}

	/**是否选择*/
	private boolean isSelectUser = false;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte getUserState() {
		return userState;
	}

	public void setUserState(byte userState) {
		this.userState = userState;
	}

	public byte getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(byte userPriority) {
		this.userPriority = userPriority;
	}

	public void setLetterName(String name) {

		this.letterName = name;
	}

	public String getLetterName() {

		return letterName;
	}
	
	public byte getUserAvailableState() {
		return userAvailableState;
	}

	public void setUserAvailableState(byte userAvailableState) {
		this.userAvailableState = userAvailableState;
	}

	@Override
	public String toString() {
		return "userID :" + userId + ", userName : " + userName + ", state : "
				+ userState + " priority :" + userPriority + " letterName :"
				+ letterName + ", userStyle: " + userStyle;
	}

	public byte getUserStyle() {
		return userStyle;
	}

	public void setUserStyle(byte userStyle) {
		this.userStyle = userStyle;
	}

	@Override
	public int compareTo(UserEntity another) {
		// TODO Auto-generated method stub
		
		return this.letterName.compareTo(another.getLetterName());
	}

	public String getShowLocation() {
		return "2";
	}

	public void setShowLocation(String showLocation) {
		this.showLocation = showLocation;
	}

	private String showLocation;

	@Override
	public int getItemType() {
		return 2;
	}
}
