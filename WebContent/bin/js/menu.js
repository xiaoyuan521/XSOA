var selMenus;

function makeMenu(menuContainer, menuList) {
	selMenus = new Array();

	for ( var i = 0, len = menuList.length; i < len; i++) {
		var menuObj = menuList[i];
		if (menuObj.MENU_FJDID == "0000000") {//一级菜单，则创建新菜单
			createParentMenuItem(menuContainer, menuObj);
		}else{
			createChildrenMenuItem(menuObj.MENU_FJDID, menuObj);	
		}
		
	}
}
function createParentMenuItem(menuContainer, menuObj) {
	var level = "1";
	var menu = document.createElement('DIV');
	menu.id = "M" + menuObj.MENU_CDID;
	menu.menucode = menuObj.MENU_CDID;
	menu.upcode = "0000000";
	menu.innerHTML = "<SPAN>" + menuObj.MENU_CDMC + "</SPAN>";
	menu.className = "menu_" + level + " menu_" + level + "_normal";
	
	menu.onmouseover = function() {
		btn_mouse_over(menu, level);
	};
	menu.onmouseout = function() {
		btn_mouse_out(menu, level);
	};
	menu.onclick = function() {
		btn_mouse_click(menu, level);
	};

	var menuBody = document.createElement('DIV');
	menuBody.id = "MB" + menuObj.MENU_CDID;
	menuBody.style.display = "none";

	menuContainer.appendChild(menu);
	menuContainer.appendChild(menuBody);
}
function createChildrenMenuItem(upCode, menuObj) {
	var level = "2";
	var menu = document.createElement('DIV');
	menu.id = "M" + menuObj.MENU_CDID;
	menu.menucode = menuObj.MENU_CDID;
	menu.upcode = upCode;
	menu.menuurl = menuObj.MENU_URL;
	menu.menupid = menuObj.MENU_CDID;
	menu.innerHTML = "<SPAN>" + menuObj.MENU_CDMC + "</SPAN>";
	menu.className = "menu_" + level + " menu_" + level + "_normal";
	menu.onmouseover = function() {
		btn_mouse_over(menu, level);
	};
	menu.onmouseout = function() {
		btn_mouse_out(menu, level);
	};
	menu.onclick = function() {
		btn_mouse_click(menu, level);
	};

	var menuBody = document.createElement('DIV');
	menuBody.id = "MB" + menuObj.MENU_CDID;
	menuBody.style.display = "none";

	var parentMenuBody = document.getElementById('MB' + upCode);
	parentMenuBody.appendChild(menu);

	parentMenuBody.appendChild(menu);
	parentMenuBody.appendChild(menuBody);
}
function btn_mouse_over(menu, level) {
	menu.className = "menu_" + level + " menu_" + level + "_onmouse";
}
function btn_mouse_out(menu, level) {
	if (selMenus[level] == menu) {
		menu.className = "menu_" + level + " menu_" + level + "_select";
	} else {
		menu.className = "menu_" + level + " menu_" + level + "_normal";
	}
}
function btn_mouse_click(menu, level) {
	if (selMenus[level] == undefined) {
		menu.className = "menu_" + level + " menu_" + level + "_select";
		document.getElementById("MB" + menu.menucode).style.display = "block";
	} else if (selMenus[level] == menu) {
		menu.className = "menu_" + level + " menu_" + level + "_select";
		document.getElementById("MB" + menu.menucode).style.display = "block";
	} else if (selMenus[level] != menu) {
		selMenus[level].className = "menu_" + level + " menu_" + level + "_normal";
		document.getElementById("MB" + selMenus[level].menucode).style.display = "none";
		menu.className = "menu_" + level + " menu_" + level + "_select";
		document.getElementById("MB" + menu.menucode).style.display = "block";
	}

	selMenus[level] = menu;

	if (menu.menupid != undefined) {
		var main_frame = document.getElementById("main_frame");
		var murl = menu.menuurl;
		var strPid = menu.menupid;
		main_frame.src = basePath + "/" + murl + strPid+".jsp";
	}


}

