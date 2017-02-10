/*

 @Name: layui WebIM 1.0.0
 @Author：贤心
 @Date: 2014-04-25
 @Blog: http://sentsin.com

 */

//;!function(win, undefined){
var chatContent;
var messageCount = 0;
var unMessageList = [];
function startListener(websocket, userInfo){
    if (websocket) {
      //连接发生错误的回调方法
        websocket.onerror = function(){
            console.log("error");
        };

        //连接成功建立的回调方法
        websocket.onopen = function(event){
            console.log("open");
        };

        //接收到消息的回调方法
        /*websocket.onmessage = function(event){
            console.log(event.data);
            chatContent = event.data;
        };*/

        //连接关闭的回调方法
        websocket.onclose = function(){
            console.log("close");
        };

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
            websocket.close();
        };
    }
}
function layim(basePath, userInfo){

var config = {
    msgurl: '私信地址',
    chatlogurl: '聊天记录url前缀',
    aniTime: 200,
    right: -232,
    api: {
        friend: basePath+'/bin/friend.json', //好友列表接口
        group: basePath+'/bin/group.json', //群组列表接口
        chatlog: basePath+'/bin/.json', //聊天记录接口
        groups: basePath+'/bin/groups.json', //群组成员接口
        sendurl: '' //发送消息接口
    },
    user: { //当前用户信息
        id: userInfo.userid,
        name: userInfo.username,
        face: basePath+'/bin/upload/'+userInfo.grzp
    },

    //自动回复内置文案，也可动态读取数据库配置
    autoReplay: [
        '离线中~~ 天黑请闭眼！',
        '离线中~~ 狼人请杀人！',
        '离线中~~ 预言家请验人！',
        '离线中~~ 女巫是否救人？',
        '离线中~~ 女巫是否毒人？',
        '离线中~~ 猎人是否开枪？',
        '离线中~~ 狼人自爆了！',
        '离线中~~ 好人胜利！',
        '离线中~~ 狼人胜利！'
    ],


    chating: {},
    hosts: (function(){
        var dk = location.href.match(/\:\d+/);
        dk = dk ? dk[0] : '';
        return 'http://' + document.domain + dk + '/';
    })(),
    json: function(url, data, callback, error){
        return $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            success: callback,
            error: error
        });
    },
    stopMP: function(e){
        e ? e.stopPropagation() : e.cancelBubble = true;
    }
}, dom = [$(window), $(document), $('html'), $('body')], xxim = {};

//主界面tab
xxim.tabs = function(index){
    var node = xxim.node;
    node.tabs.eq(index).addClass('xxim_tabnow').siblings().removeClass('xxim_tabnow');
    node.list.eq(index).show().siblings('.xxim_list').hide();
    if(node.list.eq(index).find('li').length === 0){
        xxim.getDates(index);
    }
};

//节点
xxim.renode = function(){
    var node = xxim.node = {
        tabs: $('#xxim_tabs>span'),
        list: $('.xxim_list'),
        online: $('.xxim_online'),
        setonline: $('.xxim_setonline'),
        onlinetex: $('#xxim_onlinetex'),
        xximon: $('#xxim_on'),
        layimFooter: $('#xxim_bottom'),
        xximHide: $('#xxim_hide'),
        xximSearch: $('#xxim_searchkey'),
        searchMian: $('#xxim_searchmain'),
        closeSearch: $('#xxim_closesearch'),
        layimMin: $('#layim_min')
    };
};

//主界面缩放
xxim.expend = function(){
    var node = xxim.node;
    if(xxim.layimNode.attr('state') !== '1'){
        xxim.layimNode.stop().animate({right: config.right}, config.aniTime, function(){
            node.xximon.addClass('xxim_off');
            try{
                localStorage.layimState = 1;
            }catch(e){}
            xxim.layimNode.attr({state: 1});
            node.layimFooter.addClass('xxim_expend').stop().animate({marginLeft: config.right}, config.aniTime/2);
            node.xximHide.addClass('xxim_show');
        });
    } else {
        xxim.layimNode.stop().animate({right: 1}, config.aniTime, function(){
            node.xximon.removeClass('xxim_off');
            try{
                localStorage.layimState = 2;
            }catch(e){}
            xxim.layimNode.removeAttr('state');
            node.layimFooter.removeClass('xxim_expend');
            node.xximHide.removeClass('xxim_show');
        });
        node.layimFooter.stop().animate({marginLeft: 0}, config.aniTime);
    }
};

//初始化窗口格局
xxim.layinit = function(){
    var node = xxim.node;

    //主界面
    try{
        if(!localStorage.layimState){
            config.aniTime = 0;
            localStorage.layimState = 1;
        }
        if(localStorage.layimState === '1'){
            xxim.layimNode.attr({state: 1}).css({right: config.right});
            node.xximon.addClass('xxim_off');
            node.layimFooter.addClass('xxim_expend').css({marginLeft: config.right});
            node.xximHide.addClass('xxim_show');
        }
    }catch(e){
        layer.msg(e.message, 5, -1);
    }
};

//聊天窗口
xxim.popchat = function(param){
    var node = xxim.node, log = {};

    log.success = function(layero){
        layer.setMove();

        xxim.chatbox = layero.find('#layim_chatbox');
        log.chatlist = xxim.chatbox.find('.layim_chatmore>ul');

        log.chatlist.html('<li data-id="'+ param.id +'" type="'+ param.type +'"  id="layim_user'+ param.type + param.id +'"><span>'+ param.name +'</span><em>×</em></li>')
        xxim.tabchat(param, xxim.chatbox);

        //最小化聊天窗
        xxim.chatbox.find('.layer_setmin').on('click', function(){
            var indexs = layero.attr('times');
            layero.hide();
            node.layimMin.text(xxim.nowchat.name).show();
        });

        //关闭窗口
        xxim.chatbox.find('.layim_close').on('click', function(){
            var indexs = layero.attr('times');
            layer.close(indexs);
            //setChatRead(xxim.nowchat.id, config.user.id, xxim.nowchat.type);

            xxim.chatbox = null;
            config.chating = {};
            config.chatings = 0;
        });

        //关闭某个聊天
        log.chatlist.on('mouseenter', 'li', function(){
            $(this).find('em').show();
        }).on('mouseleave', 'li', function(){
            $(this).find('em').hide();
        });
        log.chatlist.on('click', 'li em', function(e){
            var parents = $(this).parent(), dataType = parents.attr('type');
            var dataId = parents.attr('data-id'), index = parents.index();
            var chatlist = log.chatlist.find('li'), indexs;

            config.stopMP(e);

            delete config.chating[dataType + dataId];
            config.chatings--;

            parents.remove();
            $('#layim_area'+ dataType + dataId).remove();
            if(dataType === 'group'){
                $('#layim_group'+ dataType + dataId).remove();
            }

            if(parents.hasClass('layim_chatnow')){
                if(index === config.chatings){
                    indexs = index - 1;
                } else {
                    indexs = index + 1;
                }
                xxim.tabchat(config.chating[chatlist.eq(indexs).attr('type') + chatlist.eq(indexs).attr('data-id')]);
            }

            if(log.chatlist.find('li').length === 1){
                log.chatlist.parent().hide();
            }
        });

        //聊天选项卡
        log.chatlist.on('click', 'li', function(){
            var othis = $(this), dataType = othis.attr('type'), dataId = othis.attr('data-id');
            xxim.tabchat(config.chating[dataType + dataId]);
        });

        //发送热键切换
        log.sendType = $('#layim_sendtype'), log.sendTypes = log.sendType.find('span');
        $('#layim_enter').on('click', function(e){
            config.stopMP(e);
            log.sendType.show();
        });
        log.sendTypes.on('click', function(){
            log.sendTypes.find('i').text('')
            $(this).find('i').text('√');
        });

        xxim.transmit();
    };

    log.html = '<div class="layim_chatbox" id="layim_chatbox">'
            +'<h6>'
            +'<span class="layim_move"></span>'
            +'    <a href="'+ param.url +'" class="layim_face" target="_blank"><img src="'+ param.face +'" ></a>'
            +'    <a href="'+ param.url +'" class="layim_names" target="_blank">'+ param.name +'</a>'
            +'    <span class="layim_rightbtn">'
            +'        <i class="layer_setmin"></i>'
            +'        <i class="layim_close"></i>'
            +'    </span>'
            +'</h6>'
            +'<div class="layim_chatmore" id="layim_chatmore">'
            +'    <ul class="layim_chatlist"></ul>'
            +'</div>'
            +'<div class="layim_groups" id="layim_groups"></div>'
            +'<div class="layim_chat">'
            +'    <div class="layim_chatarea" id="layim_chatarea">'
            +'        <ul class="layim_chatview layim_chatthis"  id="layim_area'+ param.type + param.id +'"></ul>'
            +'    </div>'
            +'    <div class="layim_tool">'
            +'        <i class="layim_addface" title="发送表情"></i>'
            +'        <a href="javascript:;"><i class="layim_addimage" title="上传图片"></i></a>'
            +'        <a href="javascript:;"><i class="layim_addfile" title="上传附件"></i></a>'
            +'        <a href="" target="_blank" class="layim_seechatlog"><i></i>聊天记录</a>'
            +'    </div>'
            +'    <textarea class="layim_write" id="layim_write"></textarea>'
            +'    <div class="layim_send">'
            +'        <div class="layim_sendbtn" id="layim_sendbtn">发送<span class="layim_enter" id="layim_enter"><em class="layim_zero"></em></span></div>'
            +'        <div class="layim_sendtype" id="layim_sendtype">'
            +'            <span><i>√</i>按Enter键发送</span>'
            +'            <span><i></i>按Ctrl+Enter键发送</span>'
            +'        </div>'
            +'    </div>'
            +'</div>'
            +'</div>';

    if(config.chatings < 1){
        $.layer({
            type: 1,
            border: [0],
            title: false,
            shade: [0],
            area: ['620px', '493px'],
            move: ['.layim_chatbox .layim_move', true],
            moveType: 1,
            closeBtn: false,
            offset: [(($(window).height() - 493)/2)+'px', ''],
            page: {
                html: log.html
            }, success: function(layero){
                log.success(layero);
            }
        })
    } else {
        log.chatmore = xxim.chatbox.find('#layim_chatmore');
        log.chatarea = xxim.chatbox.find('#layim_chatarea');

        log.chatmore.show();

        log.chatmore.find('ul>li').removeClass('layim_chatnow');
        log.chatmore.find('ul').append('<li data-id="'+ param.id +'" type="'+ param.type +'" id="layim_user'+ param.type + param.id +'" class="layim_chatnow"><span>'+ param.name +'</span><em>×</em></li>');

        log.chatarea.find('.layim_chatview').removeClass('layim_chatthis');
        log.chatarea.append('<ul class="layim_chatview layim_chatthis" id="layim_area'+ param.type + param.id +'"></ul>');

        xxim.tabchat(param);
    }

    //群组
    log.chatgroup = xxim.chatbox.find('#layim_groups');
    if(param.type === 'group'){
        log.chatgroup.find('ul').removeClass('layim_groupthis');
        log.chatgroup.append('<ul class="layim_groupthis" id="layim_group'+ param.type + param.id +'"></ul>');
        xxim.getGroups(param);
    }
    //点击群员切换聊天窗
    log.chatgroup.on('click', 'ul>li', function(){
        xxim.popchatbox($(this));
    });
};

//定位到某个聊天队列
xxim.tabchat = function(param){
    var node = xxim.node, log = {}, keys = param.type + param.id;
    xxim.nowchat = param;

    xxim.chatbox.find('#layim_user'+ keys).addClass('layim_chatnow').siblings().removeClass('layim_chatnow');
    xxim.chatbox.find('#layim_area'+ keys).addClass('layim_chatthis').siblings().removeClass('layim_chatthis');
    xxim.chatbox.find('#layim_group'+ keys).addClass('layim_groupthis').siblings().removeClass('layim_groupthis');

    xxim.chatbox.find('.layim_face>img').attr('src', param.face);
    xxim.chatbox.find('.layim_face, .layim_names').attr('href', param.href);
    xxim.chatbox.find('.layim_names').text(param.name);

    xxim.chatbox.find('.layim_seechatlog').attr('href', config.chatlogurl + param.id);

    log.groups = xxim.chatbox.find('.layim_groups');
    if(param.type === 'group'){
        log.groups.show();
    } else {
        log.groups.hide();
    }

    $('#layim_write').focus();

};

//弹出聊天窗
xxim.popchatbox = function(othis){
    var node = xxim.node, dataId = othis.attr('data-id'), param = {
        id: dataId, //用户ID
        type: othis.attr('type'),
        name: othis.find('.xxim_onename').text(),  //用户名
        face: othis.find('.xxim_oneface').attr('src'),  //用户头像
        href: config.hosts + 'user/' + dataId //用户主页
    }, key = param.type + dataId;
    if(!config.chating[key]){
        xxim.popchat(param);
        config.chatings++;
    } else {
        xxim.tabchat(param);
    }
    config.chating[key] = param;

    var chatbox = $('#layim_chatbox');
    if(chatbox[0]){
        node.layimMin.hide();
        chatbox.parents('.xubox_layer').show();
    }
    var list = getChatRecords(userInfo.userid ,dataId, param.type);

    if (unMessageList.indexOf(dataId) != -1) {
        messageCount = messageCount - 1;
        arrayRemove(unMessageList, dataId);
        showMessageCount(messageCount);
    }


    var log = {};
    $.each(list, function(index, item) {
        log.html = function(param, type){
            return '<li class="'+ (type === 'me' ? 'layim_chateme' : '') +'">'
                +'<div class="layim_chatuser">'
                    + function(){
                        if(type === 'me'){
                            return '<span class="layim_chattime">'+ param.time +'</span>'
                                   +'<span class="layim_chatname">'+ param.name +'</span>'
                                   +'<img src="'+ param.face +'" >';
                        } else {
                            return '<img src="'+ param.face +'" >'
                                   +'<span class="layim_chatname">'+ param.name +'</span>'
                                   +'<span class="layim_chattime">'+ param.time +'</span>';
                        }
                    }()
                +'</div>'
                +'<div class="layim_chatsay">'+ param.content +'<em class="layim_zero"></em></div>'
            +'</li>';
        };
        log.imarea = xxim.chatbox.find('#layim_area'+ key);
        if (param.type=="one") {
            if (item.MESSAGE_FROM == config.user.id) {
                log.imarea.append(log.html({
                    time: item.MESSAGE_TIME,
                    name: config.user.name,
                    face: config.user.face,
                    content: item.CONTENT
                }, 'me'));
            } else {
                log.imarea.append(log.html({
                    time: item.MESSAGE_TIME,
                    name: param.name,
                    face: param.face,
                    content: item.CONTENT
                }));
            }
        } else if (param.type=="group") {
            if (item.MESSAGE_FROM == config.user.id) {
                log.imarea.append(log.html({
                    time: item.MESSAGE_TIME,
                    name: config.user.name,
                    face: config.user.face,
                    content: item.CONTENT
                }, 'me'));
            } else {
                log.imarea.append(log.html({
                    time: item.MESSAGE_TIME,
                    name: item.NAME,
                    face: basePath+'/bin/upload/'+item.FACE,
                    content: item.CONTENT
                }));
            }
        }


        node.imwrite.val('').focus();
        log.imarea.scrollTop(log.imarea[0].scrollHeight);
    });
};

//请求群员
xxim.getGroups = function(param){
    var keys = param.type + param.id, str = '',
    groupss = xxim.chatbox.find('#layim_group'+ keys);
    groupss.addClass('loading');
    config.json(config.api.groups, {}, function(datas){
        if(datas.status === 1){
            var ii = 0, lens = datas.data.length;
            if(lens > 0){
                for(; ii < lens; ii++){
                    str += '<li id="'+ datas.data[ii].id +'" data-id="'+ datas.data[ii].id +'" type="one" class="off_line"><img src="'+ datas.data[ii].face +'"><span class="xxim_onename">'+ datas.data[ii].name +'</span></li>';
                }
            } else {
                str = '<li class="layim_errors">没有群员</li>';
            }

        } else {
            str = '<li class="layim_errors">'+ datas.msg +'</li>';
        }
        groupss.removeClass('loading');
        groupss.html(str);
        var list_on = getOnlineList();
        for(var i = 0; i < list_on.length; i++ ){
            //$("#"+list_on[i]+"").removeClass("off_line");
            groupss.find("#"+list_on[i]).removeClass("off_line");
            //$("#layim_group"+ keys +"li[id='"+list_on[i]+"']").removeClass("off_line");
        }
        var list_off = getOfflineList();
        for(var i = 0; i < list_off.length; i++ ){
            //$("#"+list_off[i]+"").addClass("off_line");
            //$("#layim_group"+ keys +"li[id='"+list_off[i]+"']").addClass("off_line");
            groupss.find("#"+list_off[i]).addClass("off_line");
        }
    }, function(){
        groupss.removeClass('loading');
        groupss.html('<li class="layim_errors">请求异常</li>');
    });
};
//消息接收
xxim.getMessage = function(event){
    var node = xxim.node, log = {};
    //var keys = xxim.nowchat.type + xxim.nowchat.id;
    var obj=eval("("+event.data+")");
    var flag = obj.flag;
    var userid = obj.userid;
    var username = obj.username;
    var onlineList = obj.onlineList;
    var offlineList = obj.offlineList;
    var unReadList = obj.unReadList;
    var toid = obj.toid;
    if (flag == "online") {
        for(var i = 0; i < onlineList.length; i++ ){
            $("#"+onlineList[i]+"").removeClass("off_line");
        }
        if (userInfo.userid != userid) {
            new $.zui.Messager('用户：'+username+'上线了！', {
                icon: 'bell',// 定义消息图标
                placement: 'bottom', // 定义显示位置
                close: false
            }).show();
        } else {
            $.each(unReadList, function(index, item) {
                unMessageList.push(item.MESSAGE_FROM);
            })
            messageCount = unReadList.length;
            showMessageCount(messageCount);
        }
        return;
    } else if (flag == "offline") {
        for(var i = 0; i < offlineList.length; i++ ){
            $("#"+offlineList[i]+"").addClass("off_line");
        }
        new $.zui.Messager('用户：'+username+'下线了！', {
            icon: 'bell',// 定义消息图标
            placement: 'bottom', // 定义显示位置
            close: false
        }).show();
        return;
    }
    var content = obj.content;
    var usertype = obj.type;
    var time = obj.time;
    var userface = obj.userface;
    //聊天模版
    log.html = function(param, type){
        return '<li class="'+ (type === 'me' ? 'layim_chateme' : '') +'">'
            +'<div class="layim_chatuser">'
                + function(){
                    if(type === 'me'){
                        return '<span class="layim_chattime">'+ param.time +'</span>'
                               +'<span class="layim_chatname">'+ param.name +'</span>'
                               +'<img src="'+ param.face +'" >';
                    } else {
                        return '<img src="'+ param.face +'" >'
                               +'<span class="layim_chatname">'+ param.name +'</span>'
                               +'<span class="layim_chattime">'+ param.time +'</span>';
                    }
                }()
            +'</div>'
            +'<div class="layim_chatsay">'+ param.content +'<em class="layim_zero"></em></div>'
        +'</li>';
    };

    if (xxim.chatbox == null) {
        if ("one" == usertype) {
            if (unMessageList.indexOf(userid) == -1) {
                unMessageList.push(userid);
                messageCount = messageCount + 1;
            }
            showMessageCount(messageCount);
        }
    } else if (xxim.chatbox.find('#layim_area'+ usertype + userid).length == 0 && "one" == usertype) {
        if (unMessageList.indexOf(userid) == -1) {
            unMessageList.push(userid);
            messageCount = messageCount + 1;
        }
        showMessageCount(messageCount);
    } else {
        var keys = usertype + xxim.nowchat.id
        document.getElementById("noMessage").style.display="none";
        log.imarea = xxim.chatbox.find('#layim_area'+ keys);
        if (config.user.id == userid) {
            log.imarea.append(log.html({
                time: time,
                name: username,
                face: userface,
                //content: data.content
                content: content
            }, 'me'));
        } else {
            log.imarea.append(log.html({
                time: time,
                name: username,
                face: userface,
                //content: data.content
                content: content
            }));
        }

        node.imwrite.val('').focus();
        log.imarea.scrollTop(log.imarea[0].scrollHeight);
    }
};
//消息传输
xxim.transmit = function(){
    var node = xxim.node, log = {};
    node.sendbtn = $('#layim_sendbtn');
    node.imwrite = $('#layim_write');

    //发送
    log.send = function(){
        var data = {
            content: node.imwrite.val(),
            type: xxim.nowchat.type,
            toid: xxim.nowchat.id,
            mine:true,
            userid: config.user.id,
            username: config.user.name,
            userface: config.user.face,
            _: +new Date
        };

        if(data.content.replace(/\s/g, '') === ''){
            layer.tips('说点啥呗！', '#layim_write', 2);
            node.imwrite.focus();
        } else {
            websocket.send(JSON.stringify(data));
            //此处皆为模拟
            var keys = xxim.nowchat.type + xxim.nowchat.id;
            if (xxim.nowchat.id != "101" && xxim.nowchat.id != "102" && xxim.nowchat.id != config.user.id) {
                log.html = function(param, type){
                    return '<li class="'+ (type === 'me' ? 'layim_chateme' : '') +'">'
                        +'<div class="layim_chatuser">'
                            + function(){
                                if(type === 'me'){
                                    return '<span class="layim_chattime">'+ param.time +'</span>'
                                           +'<span class="layim_chatname">'+ param.name +'</span>'
                                           +'<img src="'+ param.face +'" >';
                                } else {
                                    return '<img src="'+ param.face +'" >'
                                           +'<span class="layim_chatname">'+ param.name +'</span>'
                                           +'<span class="layim_chattime">'+ param.time +'</span>';
                                }
                            }()
                        +'</div>'
                        +'<div class="layim_chatsay">'+ param.content +'<em class="layim_zero"></em></div>'
                    +'</li>';
                };
                log.imarea = xxim.chatbox.find('#layim_area'+ keys);
                log.imarea.append(log.html({
                    time: getChatTime(),
                    name: config.user.name,
                    face: config.user.face,
                    content: data.content
                }, 'me'));
                node.imwrite.val('').focus();
                log.imarea.scrollTop(log.imarea[0].scrollHeight);
                if (getOnlineList().indexOf(xxim.nowchat.id) == -1) {
                    setTimeout(function(){
                      log.imarea.append(log.html({
                          time: getChatTime(),
                          name: xxim.nowchat.name,
                          face: xxim.nowchat.face,
                          content: config.autoReplay[(Math.random()*config.autoReplay.length) | 0]
                      }));
                      log.imarea.scrollTop(log.imarea[0].scrollHeight);
                  }, 500);
                }
            }
            /*websocket.onmessage = function(event){
                var obj=eval("("+event.data+")");
                var content = obj.content;
                var userid = obj.userid;
                var time = obj.time;
                var username = obj.username;
                var userface = obj.userface;
                //聊天模版
                log.html = function(param, type){
                    return '<li class="'+ (type === 'me' ? 'layim_chateme' : '') +'">'
                        +'<div class="layim_chatuser">'
                            + function(){
                                if(type === 'me'){
                                    return '<span class="layim_chattime">'+ param.time +'</span>'
                                           +'<span class="layim_chatname">'+ param.name +'</span>'
                                           +'<img src="'+ param.face +'" >';
                                } else {
                                    return '<img src="'+ param.face +'" >'
                                           +'<span class="layim_chatname">'+ param.name +'</span>'
                                           +'<span class="layim_chattime">'+ param.time +'</span>';
                                }
                            }()
                        +'</div>'
                        +'<div class="layim_chatsay">'+ param.content +'<em class="layim_zero"></em></div>'
                    +'</li>';
                };

                log.imarea = xxim.chatbox.find('#layim_area'+ keys);
                if (config.user.id == userid) {
                    log.imarea.append(log.html({
                        time: time,
                        name: username,
                        face: userface,
                        //content: data.content
                        content: content
                    }, 'me'));
                } else {
                    log.imarea.append(log.html({
                        time: time,
                        name: username,
                        face: userface,
                        //content: data.content
                        content: content
                    }));
                }

                node.imwrite.val('').focus();
                log.imarea.scrollTop(log.imarea[0].scrollHeight);
            };*/
//            setTimeout(function(){
//                log.imarea.append(log.html({
//                    time: getChatTime(),
//                    name: xxim.nowchat.name,
//                    face: xxim.nowchat.face,
//                    content: config.autoReplay[(Math.random()*config.autoReplay.length) | 0]
//                }));
//                log.imarea.scrollTop(log.imarea[0].scrollHeight);
//            }, 500);

            /*
            that.json(config.api.sendurl, data, function(datas){

            });
            */
        }

    };
    node.sendbtn.on('click', log.send);

    node.imwrite.keyup(function(e){
        if(e.keyCode === 13){
            log.send();
        }
    });
};

//事件
xxim.event = function(){
    var node = xxim.node;

    //主界面tab
    node.tabs.eq(0).addClass('xxim_tabnow');
    node.tabs.on('click', function(){
        var othis = $(this), index = othis.index();
        xxim.tabs(index);
    });

    //列表展收
    node.list.on('click', 'h5', function(){
        var othis = $(this), chat = othis.siblings('.xxim_chatlist'), parentss = othis.parent();
        if(parentss.hasClass('xxim_liston')){
            chat.hide();
            parentss.removeClass('xxim_liston');
        } else {
            chat.show();
            parentss.addClass('xxim_liston');
        }
    });

    //设置在线隐身
    node.online.on('click', function(e){
        config.stopMP(e);
        node.setonline.show();
    });
    node.setonline.find('span').on('click', function(e){
        var index = $(this).index();
        config.stopMP(e);
        if(index === 0){
            node.onlinetex.html('在线');
            node.online.removeClass('xxim_offline');
        } else if(index === 1) {
            node.onlinetex.html('隐身');
            node.online.addClass('xxim_offline');
        }
        node.setonline.hide();
    });

    node.xximon.on('click', xxim.expend);
    node.xximHide.on('click', xxim.expend);

    //搜索
    node.xximSearch.keyup(function(){
        var val = $(this).val().replace(/\s/g, '');
        if(val !== ''){
            node.searchMian.show();
            node.closeSearch.show();
            //此处的搜索ajax参考xxim.getDates
            node.list.eq(3).html('<li class="xxim_errormsg">没有符合条件的结果</li>');
        } else {
            node.searchMian.hide();
            node.closeSearch.hide();
        }
    });
    node.closeSearch.on('click', function(){
        $(this).hide();
        node.searchMian.hide();
        node.xximSearch.val('').focus();
    });

    //弹出聊天窗
    config.chatings = 0;
    node.list.on('click', '.xxim_childnode', function(){
        var othis = $(this);
        xxim.popchatbox(othis);
    });

    //点击最小化栏
    node.layimMin.on('click', function(){
        $(this).hide();
        $('#layim_chatbox').parents('.xubox_layer').show();
    });


    //document事件
    dom[1].on('click', function(){
        node.setonline.hide();
        $('#layim_sendtype').hide();
    });
};

//请求列表数据
xxim.getDates = function(index){
    var api = [config.api.friend, config.api.group, config.api.chatlog],
        node = xxim.node, myf = node.list.eq(index);
    myf.addClass('loading');
    config.json(api[index], {}, function(datas){
        if(datas.status === 1){
            var i = 0, myflen = datas.data.length, str = '', item;
            if(myflen > 1){
                if(index !== 2){
                    for(; i < myflen; i++){
                        str += '<li data-id="'+ datas.data[i].id +'" class="xxim_parentnode">'
                            +'<h5><i></i><span class="xxim_parentname">'+ datas.data[i].name +'</span><em class="xxim_nums">（'+ datas.data[i].nums +'）</em></h5>'
                            +'<ul class="xxim_chatlist">';
                        item = datas.data[i].item;
                        for(var j = 0; j < item.length; j++){
                            if(index != 1) {
                                str += '<li id="'+ item[j].id +'" data-id="'+ item[j].id +'" class="xxim_childnode off_line" type="'+ (index === 0 ? 'one' : 'group') +'"><img src="'+ item[j].face +'" class="xxim_oneface"><span class="xxim_onename">'+ item[j].name +'<i class="imessage"></i></span></li>';
                            } else {
                                str += '<li data-id="'+ item[j].id +'" class="xxim_childnode" type="'+ (index === 0 ? 'one' : 'group') +'"><img src="'+ item[j].face +'" class="xxim_oneface"><span class="xxim_onename">'+ item[j].name +'<i class="imessage"></i></span></li>';
                            }
                        }
                        str += '</ul></li>';
                    }
                } else {
                    str += '<li class="xxim_liston">'
                        +'<ul class="xxim_chatlist">';
                    for(; i < myflen; i++){
                        str += '<li data-id="'+ datas.data[i].id +'" class="xxim_childnode" type="one"><img src="'+ datas.data[i].face +'"  class="xxim_oneface"><span  class="xxim_onename">'+ datas.data[i].name +'</span><em class="xxim_time">'+ datas.data[i].time +'</em></li>';
                    }
                    str += '</ul></li>';
                }
                myf.html(str);

            } else {
                myf.html('<li class="xxim_errormsg">没有任何数据</li>');
            }
            myf.removeClass('loading');
        } else {
            myf.html('<li class="xxim_errormsg">'+ datas.msg +'</li>');
        }
    }, function(){
        myf.html('<li class="xxim_errormsg">请求失败</li>');
        myf.removeClass('loading');
    });
};

//渲染骨架
xxim.view = (function(){
    var xximNode = xxim.layimNode = $('<div id="xximmm" class="xxim_main">'
            +'<div class="xxim_top" id="xxim_top">'
            +'  <div class="xxim_search"><i></i><input id="xxim_searchkey" /><span id="xxim_closesearch">×</span></div>'
            +'  <div class="xxim_tabs" id="xxim_tabs"><span class="xxim_tabfriend" title="好友"><i></i></span><span class="xxim_tabgroup" title="群组"><i></i></span><span class="xxim_latechat"  title="最近聊天"><i></i></span></div>'
            +'  <ul class="xxim_list" style="display:block"></ul>'
            +'  <ul class="xxim_list"></ul>'
            +'  <ul class="xxim_list"></ul>'
            +'  <ul class="xxim_list xxim_searchmain" id="xxim_searchmain"></ul>'
            +'</div>'
            +'<ul class="xxim_bottom" id="xxim_bottom">'
            +'<li class="xxim_online" id="xxim_online">'
                +'<i class="xxim_nowstate"></i><span id="xxim_onlinetex">在线</span>'
                +'<div class="xxim_setonline">'
                    +'<span><i></i>在线</span>'
                    +'<span class="xxim_setoffline"><i></i>隐身</span>'
                +'</div>'
            +'</li>'
            //+'<li class="layui-icon layim-tool-msgbox" layim-event="msgbox" title="消息盒子"><span class="layui-anim layui-anim-loop layer-anim-05">5</span></li>'
            +'<li class="xxim_mymsg" id="xxim_mymsg" title="我的私信"><i></i><span id="noMessage" class="layui-anim layui-anim-loop layer-anim-05"></span></li>'
            +'<li class="xxim_seter" id="xxim_seter" title="设置">'
                +'<i></i>'
                +'<div class="">'

                +'</div>'
            +'</li>'
            +'<li class="xxim_hide" id="xxim_hide"><i></i></li>'
            +'<li id="xxim_on" class="xxim_icon xxim_on"></li>'
            +'<div class="layim_min" id="layim_min"></div>'
        +'</ul>'
    +'</div>');
    dom[3].append(xximNode);

    xxim.renode();
    xxim.getDates(0);
    xxim.event();
    xxim.layinit();
}());
var websocket = null;
//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
  var uid = userInfo.userid;
  var websocket = new WebSocket("ws://192.168.196.142:8080/LRSXT/wsServlet/"+uid);
  startListener(websocket, userInfo);
}
else{
  alert('Not support websocket')
}
websocket.onmessage = function(event){
    xxim.getMessage(event);
}
}
function getChatTime() {
    var today = new Date();
    var year= today.getFullYear();
    var mon = today.getMonth() + 1;
        mon = (mon<10) ? "0"+mon : mon;
    var date= today.getDate();
        date = (date<10) ? "0"+date : date;

    var hour =today.getHours();hour = (hour<10) ? "0"+hour : hour;
    var min=today.getMinutes();min = (min<10) ? "0"+min : min;
    var sec=today.getSeconds();sec = (sec<10) ? "0"+sec : sec;

    var curentDateTime    = year +"-"+mon+"-"+date+" "+hour+":"+min+":"+sec;
    return curentDateTime;
}
function setChatRead(from, to, type){
    $.ajax({
        async     : false,
        type      : "post",
        dataType  : "json",
        url: getRootPath()+"/MessageRecord",
        data: {
            CMD : "SET_MESSAGE_READ",
            FROM : from,
            TO : to,
            TYPE : type
        },
        complete : function(response) {},
        success : function(response) {
        }
    });
}
function getRootPath(){
    var result = "";
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPath=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    if(projectName == "/LRSXT"){
        result = localhostPath + projectName;
    }else{
        result = localhostPath + "/LRSXT";
    }
    return(result);
}
//获取在线用户
function getOnlineList(){
    var list = [];
    $.ajax({
        async     : false,
        type      : "post",
        dataType  : "json",
        url: getRootPath()+"/MessageRecord",
        data: {
            CMD    : "SELECT_ONLINE"
        },
        complete :function(response){},
        success: function(response) {
            list = response[0];
        }
    });
    return list;
}
//获取离线用户
function getOfflineList(){
    var list = [];
    $.ajax({
        async     : false,
        type      : "post",
        dataType  : "json",
        url: getRootPath()+"/MessageRecord",
        data: {
            CMD    : "SELECT_OFFLINE"
        },
        complete :function(response){},
        success: function(response) {
            list = response[0];
        }
    });
    return list;
}

function getChatRecords(from_id, to_id, type){
    var list = [];
    $.ajax({
        async     : false,
        type      : "post",
        dataType  : "json",
        url: getRootPath()+"/MessageRecord",
        data: {
            CMD    : "SELECT_MESSAGE",
            FROM   : from_id,
            TO     : to_id,
            TYPE   : type
        },
        complete :function(response){},
        success: function(response) {
            list = response[0];
        }
    });
    return list;
}

function showMessageCount(messageCount){
    if (messageCount > 0) {
        document.getElementById("noMessage").innerHTML = messageCount.toString();
        document.getElementById("noMessage").style.display="block";
    } else {
        document.getElementById("noMessage").style.display="none";
    }
}

function arrayRemove(arr, val) {
    for (var i=0; i<arr.length; i++) {
        if(arr[i] == val) {
          arr.splice(i, 1);
          break;
        }
    }
}
//(window);

