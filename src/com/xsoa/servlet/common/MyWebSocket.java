package com.xsoa.servlet.common;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.service.login.ServiceLogin;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/wsServlet/{uid}")
public class MyWebSocket{
    public ServiceLogin service = new ServiceLogin();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //根据用户找session
    public static Map<String,Session> map=new HashMap<String,Session>();//根据用户找session
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    List <String> userList = new ArrayList<String>();
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") int uid){
        this.session = session;
        map.put(uid+"",session);
        //webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        try {
            List<Pojo_YHXX> list = service.getDataList();
            for (Pojo_YHXX po : list) {
                userList.add(po.getYHXX_YHID());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        if(map.containsValue(session)){
            for (Entry<String, Session> entry : map.entrySet()){
                 if(entry.getValue().equals(session)){
                     map.remove(entry.getKey());
                     break;
                 }
            }
        }
        //webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        int count = 0;
        for(String key : map.keySet()){
            count = count + 1;
        }
        JSONObject jsonObject=JSONObject.fromObject(message);
        //String content = jsonObject.getString("content");
        //System.out.println("来自客户端的消息:" + content);
        String id = jsonObject.getString("toid");
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date = new Date();
        String time=df.format(date);
        JSONObject toMessage=new JSONObject();
        toMessage.put("userid", jsonObject.getString("userid"));
        toMessage.put("content", jsonObject.getString("content"));
        toMessage.put("time",time);
        toMessage.put("username",jsonObject.getString("username"));
        toMessage.put("userface",jsonObject.getString("userface"));
        toMessage.put("type", jsonObject.getString("type"));

        switch (id) {
            case "101"://狼群
                  for (int i=0;i<userList.size();i++) {
                      if(map.containsKey(userList.get(i))) {
                          session=(Session)map.get(userList.get(i));
                          try {
                            session.getBasicRemote().sendText(toMessage.toString());
                            //System.out.println("群聊-来自客户端的消息:" + toMessage.toString());
                          } catch (IOException e) {
                            e.printStackTrace();
                          }
                      }
                  }
//                JSONArray memberList=JSONArray.fromObject(llClient.getGroupUser(toId));  //获取在线用户
//                llClient.saveGroupMessage(jsonObject);
//                    if(memberList.size()>0){
//                        for(int i=0;i<memberList.size();i++){                            //发送到在线用户(除了发送者)
//                            if(map.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
//                                session=(Session)map.get(memberList.get(i));
//                                try {
//                                      session.getBasicRemote().sendText(toMessage.toString());
//                                      System.out.println("群聊-来自客户端的消息:" + toMessage.toString());
//                                } catch (IOException e) {
//                                      e.printStackTrace();
//                                }
//                            }else if(memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
//                                      //如果是发送者自己，不做任何操作。
//                            }else{    //如果是离线用户,数据存到mongo待用户上线后推送。
//                                toMessage.put("userId",Integer.parseInt(memberList.get(i).toString()));
//                                toMessage.put("mStatus",0);
//                                llClient.saveOfflineMsgToMongo(toMessage);
//                            }
//                        }
//                    }
                break;
            case "102"://系统群

                break;
            default://单聊
                try {
                    if(map.containsKey(id+"")){               //如果在线，及时推送

                       map.get(id+"").getBasicRemote().sendText(toMessage.toString());               //发送消息给对方
                       //map.get(id+"").getAsyncRemote().sendText(toMessage.toString());
                       //jsonObject.put("mStatus", 1);            //消息状态0为未读，1为已读
                       //llClient.saveFriendMessage(jsonObject);
                       //System.out.println("单聊-来自客户端的消息:" + content);
                    }else{                                      //如果不在线 就记录到数据库，下次对方上线时推送给对方。
                        //jsonObject.put("mStatus",0);
                        //llClient.saveFriendMessage(jsonObject);
                        //System.out.println("单聊-对方不在线，消息已存数据库:" + toMessage.toString());
                    }
              }catch(IOException e) {
                  e.printStackTrace();
              }
                break;
            }
//        //群发消息
//        for(MyWebSocket item: webSocketSet){
//            try {
//                item.sendMessage(content);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{

        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}