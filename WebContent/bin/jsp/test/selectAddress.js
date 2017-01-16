require([ 'dojo/dom', 'dojo/request', 'dojo/on', 'dojo/dom-class', 'dojo/dom-style', 'dojo/domReady!' ], function(dom, request, on, domClass, domStyle) {
    var defaultAddress = dom.byId("defaultAddress");
    // 加载address.txt
    request.get("./resources/address.txt").then(function(response) {
        defaultAddress.innerHTML = response
    }, function(error) {
        defaultAddress.innerHTML = error
    });
    /**
     * 初始化单选按钮
     */
    var radioOne = new dijit.form.RadioButton({
        checked: true,
        value: "tea",
        name: "drink",
      }, "radioOne");

    /**
     * 点击使用默认地址
     */
    on(dom.byId("defaultAddressRadio"), "click", function() {
        domStyle.set(dom.byId("newAddress"), "display", "none");
        domStyle.set(dom.byId("defaultAddress"), "display", "block");
    });

    /**
     * 点击新地址
     */
    on(dom.byId("newAddressRadio"), "click", function() {
        domStyle.set(dom.byId("defaultAddress"), "display", "none");
        domStyle.set(dom.byId("newAddress"), "display", "block");
    });

    /**
     * 点击确定按钮
     */
    on(dom.byId("sureAddressBtn"), "click", function() {
        // 新地址被选中时
        var isChecked = dom.byId("newAddressRadio").checked;
        if (isChecked) {
            // 省
            var province = dom.byId("inputProvinceTxt").value;
            // 市
            var city = dom.byId("inputCityTxt").value;
            // 详细地址
            var detailed = dom.byId("inputDetailedTxt").value;
            if (!province) {
                alert("请填写省");
                return;
            }
            if (!city) {
                alert("请填写市");
                return;
            }
            if (!detailed) {
                alert("请填写详细地址");
                return;
            }
            dom.byId("addressInfo").innerHTML = province + "省" + city + "市" + detailed;
        } else {
            // 默认地址被选中
            dom.byId("addressInfo").innerHTML = defaultAddress.innerHTML;
        }
        setDisabledBtn();
    });

    /**
     * 点击字体放大按钮
     */
    on(dom.byId("enlargedFontBtn"), "click", function() {
        domClass.replace("addressInfo", "enlargedFont", "narrowFont");
        domStyle.set(dom.byId("enlargedFontBtn"), "display", "none");
        domStyle.set(dom.byId("narrowFontBtn"), "display", "block");
    });

    /**
     * 点击字体缩小按钮
     */
    on(dom.byId("narrowFontBtn"), "click", function() {
        domClass.replace("addressInfo", "narrowFont", "enlargedFont");
        domStyle.set(dom.byId("narrowFontBtn"), "display", "none");
        domStyle.set(dom.byId("enlargedFontBtn"), "display", "block");
    });

    /**
     * 确定按钮点击3秒后可用
     */
    function setDisabledBtn() {
        dom.byId("sureAddressBtn").disabled = "disabled";

        setTimeout(function() {
            dom.byId("sureAddressBtn").disabled = false;
        }, 3000);
    }
});