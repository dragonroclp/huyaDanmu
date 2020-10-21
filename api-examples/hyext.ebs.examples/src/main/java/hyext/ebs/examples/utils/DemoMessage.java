package hyext.ebs.examples.utils;
import com.alibaba.excel.annotation.ExcelProperty;
public class DemoMessage {

    @ExcelProperty("房间号")
    private Long roomId;

    @ExcelProperty("粉丝编号")
    private String unionid;

    @ExcelProperty("粉丝昵称")
    private String sendNick;

    @ExcelProperty("粉丝性别")
    private Integer senderGender;

    @ExcelProperty("粉丝等级")
    private Long senderLevel;

    @ExcelProperty("粉丝头像")
    private String senderAvatarUrl;

    @ExcelProperty("粉丝徽章")
    private String badgeName;

    @ExcelProperty("徽章等级")
    private Integer fansLevel;

    @ExcelProperty("贵族等级")
    private Integer nobleLevel;

    @ExcelProperty("弹幕内容")
    private String content;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getSendNick() {
        return sendNick;
    }

    public void setSendNick(String sendNick) {
        this.sendNick = sendNick;
    }

    public Integer getSenderGender() {
        return senderGender;
    }

    public void setSenderGender(Integer senderGender) {
        this.senderGender = senderGender;
    }

    public Long getSenderLevel() {
        return senderLevel;
    }

    public void setSenderLevel(Long senderLevel) {
        this.senderLevel = senderLevel;
    }

    public String getSenderAvatarUrl() {
        return senderAvatarUrl;
    }

    public void setSenderAvatarUrl(String senderAvatarUrl) {
        this.senderAvatarUrl = senderAvatarUrl;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public Integer getFansLevel() {
        return fansLevel;
    }

    public void setFansLevel(Integer fansLevel) {
        this.fansLevel = fansLevel;
    }

    public Integer getNobleLevel() {
        return nobleLevel;
    }

    public void setNobleLevel(Integer nobleLevel) {
        this.nobleLevel = nobleLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }





}
