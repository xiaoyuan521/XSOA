require(["dojo/dom", "dojo/on", "dojo/query", "dojo/request", "dojo/dom-style", "dojo/regexp", "dijit/form/CheckBox",
    "dojox/form/BusyButton", "dijit/form/Button", "dijit/form/TextBox", "dojo/domReady!"],
    function(dom, on, query, request, domStyle, regexp, CheckBox, BusyButton, Button, TextBox){
        //txt读入地址
        var txtAddress = dom.byId("txtAddress");
        //手动输入地址
        var newAddress = dom.byId("newAddress");
        //默认显示地址
        var defaultAddress = dom.byId("defaultAddress");
        //页面初始化
        defaultAddress.innerHTML = "XX省 XX市 XX区 XXXXX";
        domStyle.set(defaultAddress, "fontSize", "10px");
        domStyle.set(newAddress, "display", "none");
        query("#defaddress").attr('checked', true);

        /**
         * 加载外部文件
         */
        request.get("./files/address.txt").then(
            function(response) {
                txtAddress.innerHTML = response;
            },
            function(error) {
                txtAddress.innerHTML = error;
            }
        );

        /**
         * 点击使用默认地址
         */
        on(dom.byId("defaddress"), "click", function() {
            request.get("./files/address.txt").then(function(response){
                txtAddress.innerHTML = response;
            });
            domStyle.set(newAddress, "display", "none");
            domStyle.set(txtAddress, "display", "block");
        });

        /**
         * 点击新地址
         */
        on(dom.byId("newaddress"), "click", function() {
            domStyle.set(txtAddress, "display", "none");
            domStyle.set(newAddress, "display", "block");
        });

        /**
         * 确定按钮点击事件
         */
        var button = new dojox.form.BusyButton({
            id: "btnConfirm",
            busyLabel: "确定",
            timeout: 3000,
            onClick: function() {
                if (query("[name='addr']")[0].checked == true) {
                    defaultAddress.innerHTML = dojo.query("#txtAddress").attr("innerHTML");
                } else if (query("[name='addr']")[1].checked == true) {
                    if (!dom.byId("txtProvince").value.trim()) {
                        alert("省不能为空！");
                        return;
                    }
                    if (!dom.byId("txtCity").value.trim()) {
                        alert("市不能为空！");
                        return;
                    }
                    defaultAddress.innerHTML = dom.byId("txtProvince").value.trim()+"省"
                    +dom.byId("txtCity").value.trim()+"市"
                    +dom.byId("txtArea").value.trim();
                }
            }
        }, "btnConfirm");

        var btnFontcontrol = dom.byId("btnFontcontrol");
        /**
         * 字体控制按钮点击事件
         */
        on(btnFontcontrol, "click", function(evt) {
            if (btnFontcontrol.value == "字体放大") {
                btnFontcontrol.value = "字体缩小";
                domStyle.set(defaultAddress, "fontSize", "20px");
            } else {
                btnFontcontrol.value = "字体放大";
                domStyle.set(defaultAddress, "fontSize", "10px");
            }
        });
});