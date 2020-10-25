package hyext.ebs.examples.websocket;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import hyext.ebs.examples.utils.DemoData;
import hyext.ebs.examples.utils.ParamsUtil;

import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hyext.ebs.examples.utils.DemoMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * 开放API websocket 接入实现样例
 *
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    //将返回的json进行解析
    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoMessage> listMessage2 = new ArrayList<DemoMessage>();
    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
    	System.out.println("------ WebSocketClient onOpen ------");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
    	System.out.println("------ WebSocketClient onClose ------");
    }

    @Override
    public void onError(Exception arg0) {
    	System.out.println("------ WebSocketClient onError ------");
    }

    @Override
    public void onMessage(String arg0) {
    	//System.out.println("-------- 接收到服务端数据： " + arg0 + "--------");
    	try {
        	JSONObject res = JSONObject.parseObject(arg0);
        	if("command".equals(res.getString("notice"))) {//监听成功回包
        		System.out.println("-------- 监听事件： " + res.getJSONObject("data").getJSONArray("data") + " 成功--------");
        	}
        	
        	if("getMessageNotice".equals(res.getString("notice"))) {
                JSONObject data = JSONObject.parseObject(arg0).getJSONObject("data");
                //粉丝徽章名称
                String badgeName = data.getString("badgeName");
                //粉丝等级
                Integer fansLevel = data.getInteger("fansLevel");
                //粉丝头像
                String senderAvatarUrl = data.getString("senderAvatarUrl");
                //贵族等级
                Integer nobleLevel = data.getInteger("nobleLevel");
                //房间号
                Long roomId = data.getLong("roomId");
               //内容
                String content = data.getString("content");
                //送礼者昵称
                String sendNick = data.getString("sendNick");
                //用户等级
                Long senderLevel = data.getLong("senderLevel");
                //用户id
                String unionid = data.getString("unionId");
                //用户性别
                Integer senderGender = data.getInteger("senderGender");
                //写入excel
//                List<DemoMessage> listMessage = new ArrayList<DemoMessage>();

                DemoMessage Message = new DemoMessage();
                Message.setRoomId(roomId);
                Message.setUnionid(unionid);
                Message.setSenderGender(senderGender);
                Message.setBadgeName(badgeName);
                Message.setFansLevel(fansLevel);
                Message.setNobleLevel(nobleLevel);
                Message.setContent(content);
                Message.setSenderAvatarUrl(senderAvatarUrl);
                Message.setSenderLevel(senderLevel);
                Message.setSendNick(sendNick);
                listMessage2.add(Message);
                // 写法1
//                String fileName ="C:\\Users\\27148\\Desktop\\test2.xlsx";
//                // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//                // 如果这里想使用03 则 传入excelType参数即可
//                EasyExcel.write(fileName, DemoMessage.class).sheet("许仙").doWrite(listMessage);



//                System.out.println(String.format("-------- 粉丝勋章：%s,粉丝等级:%s,贵族等级:%s,弹幕发送者id:%s，弹幕发送者昵称:%s,弹幕发送者等级:%s,弹幕内容:%s--------"
//                		,badgeName,fansLevel,nobleLevel,unionid,sendNick,senderLevel,content));
        	}else if("getSendItemNotice".equals(res.getString("notice"))){
                JSONObject data = JSONObject.parseObject(arg0).getJSONObject("data");
                //粉丝徽章名称
                String badgeName = data.getString("badgeName");
                //粉丝等级
                Integer fansLevel = data.getInteger("fansLevel");
                //礼物id
                Integer giftId = data.getInteger("itemId");
                //贵族等级
                Integer nobleLevel = data.getInteger("nobleLevel");
                //房间号
                Long roomId = data.getLong("roomId");
                //送礼连击数
                Long sendItemCount = data.getLong("sendItemCount");
                //送礼者昵称
                String sendNick = data.getString("sendNick");
                //用户等级
                Long senderLevel = data.getLong("senderLevel");

                System.out.println(String.format("-------- 粉丝勋章：%s,粉丝等级:%s,礼物id:%s,贵族等级:%s,房间号:%s,送礼连击数:%s,送礼者昵称:%s,用户等级:%s --------"
                        ,badgeName,fansLevel,giftId,nobleLevel,roomId,sendItemCount,sendNick,senderLevel));
            }else if("getVipEnterBannerNotice".equals(res.getString("notice"))){
                JSONObject data = JSONObject.parseObject(arg0).getJSONObject("data");
                System.out.print(data.toString());
            }
    		
		} catch (Exception e) {
			System.out.println("-------- 数据处理异常 --------");
		}
    }
    
    /**
     * 生成开放API Websocket连接参数
     * @param appId  开发者ID（https://ext.huya.com成为开发者后自动生成）
     * @param secret 开发者密钥（https://ext.huya.com成为开发者后自动生成）
     * @param roomId 要监听主播的房间号
     * @return
     */
    public static Map<String, Object> getWebSocketJwtParamsMap(String appId, String secret, long roomId){
        //获取时间戳（毫秒）
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = System.currentTimeMillis() + 10 * 60 * 1000;  //超时时间:通常设置10分钟有效，即exp=iat+600，注意不少于当前时间且不超过当前时间60分钟
        Date iat = new Date(currentTimeMillis);
        Date exp = new Date(expireTimeMillis);

        try {
        	
            Map<String, Object> header = new HashMap<String, Object>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            
            //生成JWT凭证
            Algorithm algorithm = Algorithm.HMAC256(secret);   //开发者密钥
            String sToken = JWT.create()
                    .withHeader(header)                    //JWT声明
                    .withIssuedAt(iat)                     //jwt凭证生成时间
                    .withExpiresAt(exp)                    //jwt凭证超时时间
                    .withClaim("appId", appId)             //开发者ID
                    .sign(algorithm);


            Map<String, Object> authMap = new HashMap<String, Object>();
            authMap.put("iat", currentTimeMillis / 1000);    //jwt凭证生成时间戳（秒）
            authMap.put("exp", expireTimeMillis / 1000);     //jwt凭证超时时间戳（秒）
            authMap.put("sToken", sToken);                   //jwt签名串
            authMap.put("appId",appId);                      //开发者ID
            authMap.put("do", "comm");                       //接口默认参数
            authMap.put("roomId", roomId);                   //需要监听主播的房间号

            return authMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        String fileName = "C:\\Users\\27148\\Desktop\\tessn4.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoMessage.class).build();
    	try {
            //String fileName = "C:\\Users\\27148\\Desktop\\half_game1.xlsx";

            // 这里 需要指定写用哪个class去写
            //ExcelWriter excelWriter = EasyExcel.write(fileName, DemoMessage.class).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("半决赛").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来


            int ExecuteSecond = 4000; //监听时间秒
    		
    		String appId = "daa58c4fac166aa3";      //小程序开发者ID（成为开发者后，https://ext.huya.com可查）
    		String secret = "a9730b29062ba495eca4a121c283e2dd";     //小程序开发者密钥（成为开发者后，https://ext.huya.com可查）
    		long roomId = 660000;        //监听主播的房间号
    		
            Map<String, Object> map = new HashMap<String, Object>(16);
            map = WebSocketClient.getWebSocketJwtParamsMap(appId,secret,roomId);

            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append("ws://ws-apiext.huya.com/index.html").append(ParamsUtil.MapToUrlString(map));

            WebSocketClient client = new WebSocketClient(URI.create(urlBuffer.toString()));
            client.setConnectionLostTimeout(3600);
            client.connect();
            while (!client.getReadyState().equals(ReadyState.OPEN)) {
            }
            Long reqId = System.currentTimeMillis();
            String sendMsg = "{\"command\":\"subscribeNotice\",\"data\":[\"getMessageNotice\"],\"reqId\":\"" + reqId + "\"}";
            client.send(sendMsg);
            int count = 1;
            while (count < ExecuteSecond) {
                Thread.sleep(1000);
                System.out.println("connect time:" + count);
                client.send("ping");
                count++;
//多次写入测试一
                if(count%5==0) {
//                    System.out.print("写之前的长度");
//                    System.out.print(listMessage2.size());
                    excelWriter.write(listMessage2, writeSheet);
                   // System.out.print(count+"开始写");
//                    List<DemoMessage> listMessage3 = new ArrayList<DemoMessage>();
//                    DemoMessage Message2 = new DemoMessage();
//                    Message2.setRoomId(1L);
//                    Message2.setUnionid("2");
//                    Message2.setSenderGender(0);
//                    Message2.setBadgeName("34");
//                    Message2.setFansLevel(100);
//                    Message2.setNobleLevel(12);
//                    Message2.setContent("test");
//                    Message2.setSenderAvatarUrl("pp");
//                    Message2.setSenderLevel(2L);
//                    Message2.setSendNick("123");
//                    listMessage3.add(Message2);
//                    excelWriter.write(listMessage3, writeSheet);

                    listMessage2 = new ArrayList<DemoMessage>();
//                    System.out.print("写之后的长度");
//
//                    System.out.print(listMessage2.size());
                }
            }
            // 千万别忘记finish 会帮忙关闭流
            System.out.print("最后的长度");

            System.out.print(listMessage2.size());
            excelWriter.write(listMessage2, writeSheet);
            excelWriter.finish();
            client.closeConnection(0,"bye");
        }
    	catch (Exception e) {
            if(excelWriter !=null){
                excelWriter.finish();
            }
    	    System.out.print(e+"\n lalallal");
            e.printStackTrace();
        }
        finally {
            if(excelWriter !=null){
                excelWriter.finish();
            }
        }
	}


}

