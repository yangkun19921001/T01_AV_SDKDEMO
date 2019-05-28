package com.lingyi.autiovideo.test.adapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 部门/组
 * 
 * @author 阳坤
 */
public class GroupEntity extends AbstractExpandableItem<UserEntity> implements Serializable, Comparable<GroupEntity>, MultiItemEntity {
	public static final int OPERATION_DELETE = 1;
	
	/** 激活 */
	public static final int GROUP_STATE_ACTIVE = 1;
	/** 休眠 */
	public static final int GROUP_STATE_SLEEP = 2;

	/** 实时在线 */
	public static final byte GROUP_TYPE_REALTIME = 1;
	/** 永久在线 */
	public static final byte GROUP_TYPE_FOREVER = 2;
	/** 临时在线 */
	public static final byte GROUP_TYPE_TEMP = 3;

	/** 用户加入类型 临时 还是 永久 0所属组 1长期跨入组 2临时跨入组 3调度台建的临时组 */
	/** 0所属组 */
	public static final byte ONLINE_STATE_OWNER = 0;
	/** 1长期跨入组 */
	public static final byte ONLINE_STATE_STEP_IN_FOREVER = 1;
	/** 2临时跨入组 */
	public static final byte ONLINE_STATE_STEP_IN_TEMP = 2;
	/** 3调度台建的临时组 */
	public static final byte ONLINE_STATE_DDT_CREATE = 3;
	
	private static final long serialVersionUID = 1L;

	/** 组id */
	private int groupId;
	/** 组内人数 */
	private int userSum;
	/** 组状态 */
	private int groupState;
	/** 组类型 */
	private byte groupType;
	/** 组名 */
	private String groupName;
	/** 组拼音名 */
	private String letterName;
	/** 组权限 */
	private int groupPriority;
	/** 用户加入类型 */
	private int onLineState;
	/** 是否可见，可见是可跨入的 服务器 0 不可见，1 可见 */
	private boolean visible;
	/** 所在单位id */
	private int unitId;
	/** 所属单位名 */
	private String unitName;
	/** 组操作标志 */
	private int operation;
	/** 是否被选中 */
	private boolean isChecked;

	@Override
	public String toString() {
		return "groupId :" + groupId + " userSum :" + userSum + " groupState :"
				+ groupState + " groupType :" + groupType + " groupName :"
				+ groupName + " letterName :" + letterName + " groupPriority :"
				+ groupPriority + " checked :" + " onLineState :"
				+ onLineState + " visible :" + visible + " unitId :"
				+ unitId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserSum() {
		return userSum;
	}

	public void setUserSum(int userSum) {
		this.userSum = userSum;
	}

	public int getGroupState() {
		return groupState;
	}

	public void setGroupState(int groupState) {
		this.groupState = groupState;
	}

	public byte getGroupType() {
		return groupType;
	}

	public void setGroupType(byte groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupPriority() {
		return groupPriority;
	}

	public void setGroupPriority(int groupPriority) {
		this.groupPriority = groupPriority;
	}

	public void setUnitId(int unitId) {

		this.unitId = unitId;
	}

	public int getUnitId() {

		return unitId;
	}

	public String getLetterName() {

		return letterName;
	}

	public void setLetterName(String name) {

		letterName = name;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getOnLineState() {
		return onLineState;
	}

	public void setOnLineState(int onLineState) {
		this.onLineState = onLineState;
	}
	
	public void setUnitName(String name) {
		
		this.unitName = name;
	}
	
	public String getUnitName() {
		
		return this.unitName;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	public void setChecked(boolean isCheck) {
		
		this.isChecked = isCheck;
	}
	
	public boolean isChecked() {
		
		return this.isChecked;
	}

	public int compareTo(GroupEntity an) {
		// TODO Auto-generated method stub
		return this.getLetterName().compareTo(an.getLetterName());
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean expand) {
		isExpand = expand;
	}

	private boolean isExpand;

	@Override
	public int getItemType() {
		return 1;
	}

	@Override
	public int getLevel() {
		return 1;
	}
}
