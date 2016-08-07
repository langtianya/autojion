function getBrowser(msg) {
    rMsie = /(msie\s|trident.*rv:)([\w.]+)/,
    rFirefox = /(firefox)\/([\w.]+)/,
    rOpera = /(opera).+version\/([\w.]+)/,
    rChrome = /(chrome)\/([\w.]+)/,
    rSafari = /version\/([\w.]+).*(safari)/;
    
    var ua = navigator.userAgent.toLowerCase();
    var match = rMsie.exec(ua);
    if (match != null) {
        return "IE:" + match[2];
    }
    var match = rChrome.exec(ua);
    if (match != null) {
        return match[1] + ":" + match[2];
    }
    if (msg) {
        alert(msg);
    }
    var match = rFirefox.exec(ua);
    if (match != null) {
        return match[1] + ":" + match[2];
    }
    var match = rOpera.exec(ua);
    if (match != null) {
        return match[1] + ":" + match[2];
    }
    var match = rSafari.exec(ua);
    if (match != null) {
        return match[2] + ":" + match[1];
    }
    return "";
}

$(function() {
    $("#bodyContent").i18n();
    
    getBrowser($.i18n.get('prompt.browser.notSupported'));
    
    $("#lang").combobox({
        editable : false,
        panelHeight : 'auto',
        onChange : function(lang) {
            if (lang != currentLang) {
                window.location.href = "index.pub?request_locale=" + lang;
            }
        },
        onLoadSuccess : function() {
            $(this).combobox('setValue', currentLang);
        }
    });

    $("#loginBtn").click(function() {
        return doLogin();
    });

    function doLogin() {
        if (!$("#loginBox").form('validate')) {
            $.messager.alert($.i18n.get('error'), $.i18n.get('validateError'), 'error');
            return false;
        }
        var win = $.messager.progress({
            title : $.i18n.get('prompt'),
            msg : $.i18n.get('waitingMessage')
        });
        $.ajax({
            type : 'post',
            url : contextPath + '/login.pub',
            data : $("form#loginBox").serialize(),
            success : function(resp, status, xhr) {
                $.messager.progress('close');
                if (!resp.success) {
                    $.messager.alert($.i18n.get('error'), resp.msg || $.i18n.get("prompt.exception"), 'error');
                    return;
                }
                var errorInt = resp.errorInt;
                if (errorInt != '0') {
                    if (errorInt == '90' || errorInt == '91' || errorInt == '92') {
                        var days = resp.errorParams[0];
                        promptLdapPassword(errorInt, days);
                    } else if(errorInt == '11'){ //密码过期
                        $.messager.alert($.i18n.get('error'), $.i18n.get("password.expiry"), 'error', function() {
                            window.location.href = contextPath + '/password.pub?expired=true&username=' + encodeURI($("#username").val());
                        });
                    } else {
                        var mappedMsgs = resp.mappedErrorMsgs || [$.i18n.get("prompt.exception")];
                        if (mappedMsgs != null) {
                            $.messager.alert($.i18n.get('error'), mappedMsgs[0], 'error');
                        }
                    }
                } else {
                    var afterLogin = "index.pub";
                    if (window.filterFrom != '') {
                        afterLogin = filterFrom;
                    }
                    window.location.href = afterLogin;
                }
            },
            error : function(xhr, status, error) {
                $.messager.progress('close');
                $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
            }
        });
        return false;
    }
    
    function promptLdapPassword(errorInt, days) {
        if (errorInt == '90') {
            promptLdapPassword90(days);
        } else if (errorInt == '91') {
            promptLdapPassword91(days);
        } else {
            promptLdapPassword92();
        }
    }

    function promptLdapPassword90(days) {
        $("#ldapExpired90_content").html($.i18n.get("Authorize.ldap.passwordExpireDays", [days]));
        $("#ldapExpired90").dialog({
            title : $.i18n.get("Authorize.ldap.passwordExpirePrompt"),
            width : 360,
            height : 160,
            closed : false,
            cache : false,
            modal : true,
            buttons : [{
                text : $.i18n.get('Authorize.ldap.modifyPassword'),
                handler : function() {
                    window.open(ldapPasswordChangeAddress);
                }
            }, {
                text : $.i18n.get('Authorize.ldap.modifyPasswordNextTime'),
                handler : function() {
                    window.location.href = "index.pub";
                }
            }]
        });
    }

    function promptLdapPassword91(days) {
        $("#ldapExpired91_content").html($.i18n.get("Authorize.ldap.passwordWillExpire", [days]));
        $("#ldapExpired91").dialog({
            title : $.i18n.get("Authorize.ldap.passwordExpirePrompt"),
            width : 360,
            height : 160,
            closed : false,
            cache : false,
            modal : true,
            buttons : [{
                text : $.i18n.get('Authorize.ldap.modifyPassword'),
                handler : function() {
                    window.open(ldapPasswordChangeAddress);
                }
            }, {
                text : $.i18n.get('Authorize.ldap.exitSystem'),
                handler : function() {
                    window.location.href = contextPath + "/logout.pub";
                }
            }]
        });
    }

    function promptLdapPassword92() {
        $("#ldapExpired92").dialog({
            title : $.i18n.get("Authorize.ldap.passwordExpirePrompt"),
            width : 360,
            height : 180,
            closed : false,
            cache : false,
            modal : true,
            buttons : [{
                text : $.i18n.get('Authorize.ldap.exitSystem'),
                handler : function() {
                    $('#ldapExpired92').dialog('close');
                }
            }]
        });
    }

    $("#username").keypress(function(evt) {
        if (evt.keyCode == 13) {
            $("#password")[0].focus();
        }
    });

    $("#password").keypress(function(evt) {
        if (evt.keyCode == 13) {
            if (verifyCodeActive) {
                $("#verifyCode")[0].focus();
            } else {
                doLogin();
            }
        }
    });
    
    if (verifyCodeActive) {
        $("#verifyCode").keypress(function(evt) {
            if (evt.keyCode == 13) {
                doLogin();
            }
        });
    }
    $("#username")[0].focus();
});