$(function() {
    $("body").i18n();

    function isFullUrl(actionUrl) {
        return /^(\w+):\/\//.test(actionUrl);
    }

    function addVersionToUrl(wrapUrl) {
        if (!isFullUrl(wrapUrl)) {
            wrapUrl = contextPath + wrapUrl;
        }
        if (wrapUrl.indexOf('?') != -1) {
            return wrapUrl + "&v=" + window.frameVersion;
        } else {
            return wrapUrl + "?v=" + window.frameVersion;
        }
    }
    
    $("#frame_home").attr('src', addVersionToUrl(homeUrl));

    var themes = [{
        value : 'default',
        text : $.i18n.get('theme.default'),
        group : $.i18n.get('theme.group.base')
    }, {
        value : 'metro',
        text : $.i18n.get('theme.metro'),
        group : $.i18n.get('theme.group.base')
    }, {
        value : 'bootstrap',
        text : $.i18n.get('theme.bootstrap'),
        group : $.i18n.get('theme.group.base')
    }, {
        value : 'black',
        text : $.i18n.get('theme.black'),
        group : $.i18n.get('theme.group.base')
    }, {
        value : 'light-blue',
        text : $.i18n.get('theme.light-blue'),
        group : $.i18n.get('theme.group.ui')
    }, {
        value : 'ui-cupertino',
        text : $.i18n.get('theme.ui-cupertino'),
        group : $.i18n.get('theme.group.ui')
    }, {
        value : 'ui-dark-hive',
        text : $.i18n.get('theme.ui-dark-hive'),
        group : $.i18n.get('theme.group.ui')
    }, {
        value : 'ui-pepper-grinder',
        text : $.i18n.get('theme.ui-pepper-grinder'),
        group : $.i18n.get('theme.group.ui')
    }, {
        value : 'ui-sunny',
        text : $.i18n.get('theme.ui-sunny'),
        group : $.i18n.get('theme.group.ui')
    }];
    
    function showTime() {
        var objd = new Date();
        var str = "";
        var yy = objd.getYear();
        if (yy < 1900)
            yy = yy + 1900;
        var mm = objd.getMonth() + 1;
        if (mm < 10)
            mm = '0' + mm;
        var dd = objd.getDate();
        if (dd < 10)
            dd = '0' + dd;
        var ww = objd.getDay();
        str = yy + "-" + mm + "-" + dd + " " + $.i18n.get('week.day' + ww);
        $("#time").html(str);
    }
    
    window.closeAllTabs = function() {
        hideMenuCoverLeft();
        var tabs = $('#mainTab').tabs('tabs');
        
        $.each(tabs,function(i){
            $('#mainTab').tabs('close',1);
        });
    }
    
    window.changePwd = function() {
        hideMenuCoverLeft();
        window.open(contextPath + "/password.pub");
    }
    
    window.goBackHome = function() {
        hideMenuCoverLeft();
        $('#mainTab').tabs('select',0);
    }
    
    window.infoSetting = function(select) {
        hideMenuCoverLeft();
        var title = $.i18n.get("versionAndSetting");
        var tabs = $('#mainTab')
        if (tabs.tabs('exists', title)) {
            tabs.tabs('select', title);
        } else {
            $("#versionAndSetting select.theme").addClass("easyui-combobox");
            $("#versionAndSetting select.role").addClass("easyui-combobox");
            var html = $("#versionAndSetting").html();
            var tab = tabs.tabs('add', {
                id : "tab_versionAndSetting__",
                title : title,
                content : html,
                iconCls : 'icon-version',
                closable : true,
                selected : select === true
            });
            var tabTheme = $("#tab_versionAndSetting__ select.theme")
            tabTheme.combobox({
                groupField : 'group',
                data : themes,
                editable : false,
                panelHeight : 'auto',
                onChange : onChangeTheme,
                onLoadSuccess : function() {
                    tabTheme.combobox('setValue', currentTheme);
                }
            });
            
            $("#tab_versionAndSetting__ select.lang").combobox({
                editable : false,
                panelHeight : 'auto',
                onLoadSuccess : function() {
                    $(this).combobox('setValue', currentLanguage);
                },
                onChange : function(lang) {
                    if (lang != currentLanguage) {
                        window.location.href = "frame.pvt?request_locale=" + lang + "&inSetting=true";
                    }
                }
            });
            
            fillinRoles($("#tab_versionAndSetting__ select.role"));
        }
    }
    
    function onChangeTheme(theme) {
        if (theme != currentTheme) {
            $.ajax({
                type : 'post',
                url : contextPath + '/switchTheme.pvt',
                data : {
                    'theme' : theme
                },
                success : function(resp, status, xhr) {
                    if (!resp.success && resp.msg != null) {
                        $.messager.alert($.i18n.get('error'), resp.msg, 'error', function() {
                            window.location.href = "frame.pvt?inSetting=true";
                        });
                    } else {
                        window.location.href = "frame.pvt?inSetting=true";
                    }
                },
                error : function(xhr, status, error) {
                    $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
                }
            });
        }
    }
    
    window.userRoleLoad = false;
    
    function fillinRoles(target) {
        if (target.length == 0)
            return;
        if (userRoleLoad) {
            setRoleCombobox(target, userRoles || []);
        } else {
            userRoleLoad = true;
            $.ajax({
                type : 'post',
                url : contextPath + '/userRoles.pvt',
                success : function(response, status, xhr) {
                    if (!response.success && response.msg != undefined) {
                        $.messager.alert($.i18n.get('error'), response.msg, 'error');
                    } else if (response.success) {
                        window.userRoles = response.userRoles;
                        setRoleCombobox(target, userRoles || []);
                    }
                },
                error : function(xhr, status, error) {
                    $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
                }
            });
        }
    }
    
    function setRoleCombobox(target, data) {
        target.combobox({
            editable : false,
            panelHeight : 'auto',
            data : data,
            valueField : 'roleId',
            textField : 'roleName',
            onChange : onChangeRole,
            onLoadSuccess : function() {
                $(this).combobox('setValue', currentRoleId);
            }
        });
    }
    
    function onChangeRole(roleId) {
        if (roleId != currentRoleId) {
            $.ajax({
                type : 'post',
                url : contextPath + '/switchRole.pvt',
                data : {
                    'roleId' : roleId
                },
                success : function(resp, status, xhr) {
                    window.location.href = "frame.pvt?inSetting=true";
                },
                error : function(xhr, status, error) {
                    $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
                }
            });
        }
    }
    
    window.checkLoginPopupShow = false;
    
    window.checkLogin = function() {
        if (window.checkLoginPopupShow === false) {
            $.ajax({
                url : contextPath + '/checkLogin.pub',
                cache : false,
                success : function(resp, status, xhr) {
                    if (resp !== "true") {
                        window.checkLoginPopupShow = true;
                        $.messager.alert($.i18n.get('prompt'), $.i18n.get("prompt.sessionTimeout"), 'info', function() {
                            window.location.href = window.location.href;
                        });
                    }
                }
            });
        }
    }
    
    try{
        setInterval("checkLogin()", sessionTimeout);
    } catch(e) {
    }

// Menu
    var menuLoadState = {};
    var menuLocationPair = {};
    var tabContentDivSize = {};
    
    function menuCoverHideClick(e) {
        var $el = $(e.target);
        var dv = $el.closest("div");
        if (dv.length > 0 && dv[0].id == "menu-tools") {
            return false;
        }
        if($el.closest("a").hasClass("menuLock")){
            return false;
        }
        if (0 == $el.closest(".menu-cover-left").length) {
            hideMenuCoverLeft();
            $(document).off("click", menuCoverHideClick);
        }
    }
    
    window.menuCoverLeft = function(noAnimate) {
        var mnDv = $("#menuCoverDiv");
        if (mnDv.css("left") == '0px') {
            return false;
        }
        if (noAnimate === true) {
            mnDv.css("left", 0);
        } else {
            mnDv.append($('#menu').removeClass("hidden")).animate({
                left : 0
            }, {
                duration : 600,
                step : function(now, fx) {
                    mnDv.css("left", now);
                }
            });
        }
        $(document).on("click", menuCoverHideClick);
        mnDv.mouseleave(hideMenuCoverLeft);
    }
    
    window.hideMenuCoverLeft = function(noAnimate) {
        var mnDv = $("#menuCoverDiv");
        if (mnDv.css("left") == '-218px') {
            return false;
        }
        if (noAnimate === true) {
            mnDv.css("left", -218);
        } else {
            mnDv.animate({
                left : -218
            }, {
                duration : 600,
                step : function(now, fx) {
                    mnDv.css("left", now);
                }
            });
        }
        $('#menu-tool_in_out').linkbutton('unselect');
        mnDv.off("mouseleave", hideMenuCoverLeft);
    }
    
    function hideMenuCoverLeft2() {
        var mnDv = $("#menuCoverDiv");
        mnDv.css("left", 0);
        mnDv.append($('#menu').removeClass("hidden")).animate({
            left : -218
        }, {
            duration : 600,
            step : function(now, fx) {
                mnDv.css("left", now);
            }
        });
        $('#menu-tool_in_out').linkbutton('unselect');
        mnDv.off("mouseleave", hideMenuCoverLeft);
    }
    
    window.menuLock = false;
    window.toggleMenuLock = function() {
        if (!menuLock) {
            $(".menuLock").addClass("locked");
        } else {
            $(".menuLock").removeClass("locked");
        }
        if (menuLock) {
            menuIn();
            hideMenuCoverLeft2();
        } else {
            hideMenu(true);
        }
        menuLock = !menuLock;
        if (menuLock) {
            showMenu();
        }
        $('#menu-tool_in_out').linkbutton(menuLock ? 'select' : 'unselect');
    }
    window.toggleMenu = function() {
        var there = $("#menu-tool_in_out").hasClass("l-btn-selected");
        there ? hideMenu() : showMenu();
    }
    function showMenu(noAnimate) {
        menuLock ? menuOut() : menuCoverLeft(noAnimate === true);
    }
    function hideMenu(noAnimate) {
        menuLock ? menuIn(): hideMenuCoverLeft(noAnimate === true);
    }

    $("#menuCoverDiv .menuLock").bind('click', toggleMenuLock); 

    window.menuIn = function() {
        $("body").append($('#menu').addClass("hidden"));
        $("#menuDiv").empty();
        $("#menu-tools").removeClass("noLogoImg").prepend($("#sfLogoImg"));
        $('#mainLayout').layout('remove', 'west');
    }
    
    window.menuOut = function() {
        $("#menu-tools").addClass("noLogoImg");
        $('#mainLayout').layout('add', {
            id : 'menuDiv',
            region : 'west',
            width : 210,
            height: 2000,
            title : "&nbsp;",
            collapsible : false
        });
        $(".layout-panel-west .panel-header").append($("<a href='#' class='menuLock reverse'></a>").linkbutton({
            plain : true
        }).bind('click', function() {
            toggleMenuLock();
        }));
        $(".layout-panel-west .panel-header .panel-title").prepend($("#sfLogoImg"));
        $("#menuDiv").append($('#menu').removeClass("hidden"));
    }
    
    var userAgent = navigator.userAgent.toLowerCase();
    // Figure out what browser is being used
    $.browser = {
        webkit : /webkit/.test(userAgent),
        chrome : /(chrome)\/([\w.]+)/.test(userAgent),
        msie : /(msie\s|trident.*rv:)([\w.]+)/.test(userAgent)
    };
 
    window.onMenuClick = function(moduleCode, moduleName, moduleIcon, actionUrl) {
        if (actionUrl == '' || actionUrl == undefined) {
            return;
        }
        var title = $.i18n.get(moduleCode + ".name");
        if (title == undefined || title == null || title == "") {
            title = moduleName;
        }
        var tabs = $('#mainTab')
        if (tabs.tabs('exists', title)) {
            hideMenuCoverLeft();
            tabs.tabs('select', title);
        } else {
            var tab = tabs.tabs('add', {
                id : "tab_" + moduleCode,
                title : title,
                iconCls : moduleIcon,
                content : "<div class='tab_content_div'><iframe id=\"frame_" + moduleCode + "\" class='tab_content_iframe' frameborder=\"no\" scrolling=\"no\"></iframe></div>",
                closable : true,
                selected : true
            });
            $("#frame_" + moduleCode).attr('src', addVersionToUrl(actionUrl));
            
            if ($.browser.msie) {
                var fm1 = window.frames["frame_" + moduleCode];
                var fmStChange = function() {
                    state = fm1.document.readyState;
                    if ("complete" == state) {
                        hideMenuCoverLeft();
                    }
                }
                document.getElementById("frame_" + moduleCode).onreadystatechange = fmStChange;
            } else {
                document.getElementById("frame_" + moduleCode).onload = function() {
                    hideMenuCoverLeft();
                }
            }
        }
    }

    window.onOpenMenuChild = function(parentModuleCode, moduleId, moduleCode, moduleName, moduleIcon, checkLoad) {
        if (checkLoad && !menuLoadState[moduleCode]) {
            loadMenuChild(parentModuleCode, moduleId, moduleCode, moduleName, moduleIcon);
        } else {
            childMenuNav(parentModuleCode, moduleCode, moduleName, moduleIcon);
        }
    }

    window.onMenuNavClick = function(parentModuleCode, moduleCode) {
        var menuNav = $('#menu-nav #menu-nav-' + moduleCode);
        menuNav.nextAll(".fg-menu-nav-item").addClass("hidden");
        var menuOfModule = $("#menu-" + moduleCode);
        if (!menuOfModule.hasClass("hidden")) {
            return;
        }
        resetMenu();
        var menuOfModule = $("#menu-" + moduleCode);
        var parentOfModule = menuOfModule.parent();
        $("#menu-content").append(menuOfModule.removeClass("hidden"));
        menuLocationPair[moduleCode] = parentOfModule;
    }

    function resetMenu() {
        $("#menu-content ul.fg-menu-content").addClass("hidden");
        for ( var moduleCode in menuLocationPair) {
            menuLocationPair[moduleCode].append($("#menu-" + moduleCode));
            delete menuLocationPair[moduleCode];
        }
    }
    
    window.resetMenuNav = function() {
        resetMenu();
        $("#menu-content ul.fg-menu-content:first").removeClass("hidden");
        $("#menu-nav .fg-menu-nav-item:first").nextAll().addClass("hidden");
    }

    function loadMenuChild(parentModuleCode, moduleId, moduleCode, moduleName, moduleIcon) {
        $.ajax({
            type : "POST",
            url : contextPath + '/menu.pvt',
            dataType : 'json',
            cache : true,
            data : {
                parentMenuId : moduleId
            },
            success : function(response, textStatus) {
                var childMenus = response.childMenus;
                appendLoadedChildren(moduleCode, childMenus);
                menuLoadState[moduleCode] = true;
                childMenuNav(parentModuleCode, moduleCode, moduleName, moduleIcon);
            },
            error : function(xhr, status, error) {
                $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
            }
        });
    }
    
    window.childMenuNav = function(parentModuleCode, moduleCode, moduleName, moduleIcon) {
        $("#menu-content ul.fg-menu-content").addClass("hidden");
        var menuOfModule = $("#menu-" + moduleCode);
        var parentOfModule = menuOfModule.parent();
        $("#menu-content").append(menuOfModule.removeClass("hidden"));
        menuLocationPair[moduleCode] = parentOfModule;
        addMenuNav(parentModuleCode, moduleCode, moduleName, moduleIcon);
    }
    
    function appendLoadedChildren(moduleCode, childMenus) {
        var ulNode = $('#menu-' + moduleCode);
        for (var i = 0; i < childMenus.length; i++) {
            ulNode.append(createLiNodeOfModule(moduleCode, childMenus[i]));
        }
    }
    
    function createUlNodeOfModule(module) {
        var ulNode = $("<ul id=\"menu-" + module.moduleCode + "\" class=\"fg-menu-content hidden\">");
        for (var i = 0; i < module.childModules.length; i++) {
            ulNode.append(createLiNodeOfModule(module.moduleCode, module.childModules[i]));
        }
        return ulNode;
    }
    
    function createLiNodeOfModule(parentModuleCode, module) {
        var liNode = $("<li class=\"menu fg-menu-item\">")
        var moduleName = $.i18n.get(module.moduleCode + ".name");
        moduleName = moduleName == undefined ? module.moduleName : moduleName;
        var moduleIcon = module.moduleIcon;
        if (module.childModules == undefined || module.childModules.length == 0) {
            moduleIcon = moduleIcon == null ? "tree-file" : moduleIcon;
            var btn = $("<a class=\"fg-menu-node fg-menu-all\"><span class=\"fg-menu-text\">" + moduleName + "</span></a>").linkbutton({
                plain : true,
                iconCls : moduleIcon
            }).bind('click', function() {
                onMenuClick(module.moduleCode, moduleName, moduleIcon, module.actionUrl);
            });
            liNode.append(btn);
        } else if (module.actionUrl != '' && module.actionUrl != undefined) {
            moduleIcon = moduleIcon == null ? "tree-folder" : moduleIcon;
            var btnLeft = $("<a class=\"fg-menu-folder fg-menu-left\"><span class=\"fg-menu-text\">" + moduleName + "</span>&nbsp;&nbsp;" 
                + "<span class=\"fg-menu-text-comment\">(" + module.childModules.length + ")</span></a>").linkbutton({
                plain : true,
                iconCls : moduleIcon
            }).bind('click', function() {
                onMenuClick(module.moduleCode, moduleName, moduleIcon, module.actionUrl);
            });
            var btnRight = $("<a class=\"fg-menu-right\">").linkbutton({
                plain : true,
                iconCls : 'menu-rightarrow'
            }).bind('click', function() {
                onOpenMenuChild(parentModuleCode, module.moduleId, module.moduleCode, moduleName, moduleIcon, false);
            });
            var childUlNode = createUlNodeOfModule(module);
            liNode.append(btnLeft).append(btnRight).append(childUlNode);
        } else {
            moduleIcon = moduleIcon == null ? "tree-folder" : moduleIcon;
            var btn = $("<a class=\"fg-menu-folder fg-menu-all\"><span class=\"fg-menu-text\">" + moduleName + "</span>&nbsp;&nbsp;" 
                + "<span class=\"fg-menu-text-comment\">(" + module.childModules.length + ")</span>"
                + "<span class=\"fg-menu-icon menu-rightarrow\">&nbsp;</span></a>").linkbutton({
                plain : true,
                iconCls : moduleIcon
            }).bind('click', function() {
                onOpenMenuChild(parentModuleCode, module.moduleId, module.moduleCode, moduleName, moduleIcon, false);
            });
            var childUlNode = createUlNodeOfModule(module);
            liNode.append(btn).append(childUlNode);
        }
        return liNode;
    }
    function addMenuNav(parentModuleCode, moduleCode, moduleName, moduleIcon) {
        var menuNav = $('#menu-nav #menu-nav-' + moduleCode);
        var moduleNameTmp = $.i18n.get(moduleCode + ".name");
        moduleName = moduleNameTmp == undefined ? moduleName : moduleNameTmp;
        var exist = menuNav.length == 1;
        if (exist) {
            menuNav.removeClass("hidden");
            menuNav.prev(".fg-menu-nav-item").removeClass("hidden");
            return;
        }
        var btn = $("<a id=\"menu-nav-" + moduleCode + "\" class=\"fg-menu-nav-item\">" + moduleName + "</a>").linkbutton({
            plain : true,
            iconCls : moduleIcon
        }).bind('click', function() {
            onMenuNavClick(parentModuleCode, moduleCode);
        });
        $('#menu-nav').append("<span class=\"fg-menu-nav-item layout-button-right\"></span>").append(btn);
    }
   
    showTime();
    if (inSetting) {
        infoSetting(inSetting);
    }
    if (!andriod) {
        window.menuLock = true;
    }
    showMenu(true);
});