$(function() {
    $("#bodyContent").i18n();
    
    $("#pwdBtn").click(function() {
        changePwd();
        return false;
    });
    
    var inputs = $("#pwdBox input");
    if ($("#username").val() == '') {
        inputs[0].focus();
    } else {
        inputs[1].focus();
    }
    
    var len = inputs.length;
    inputs.each(function(index, element) {
        $(this).keypress(function(evt) {
            if (evt.keyCode == 13) {
                if (index == len - 1) {
                    changePwd();
                } else {
                    inputs[index + 1].focus();
                }
            }
        });
    });
    function changePwd() {
        if (!$("#pwdBox").form('validate')) {
            $.messager.alert($.i18n.get('error'), $.i18n.get('validateError'), 'error');
            return false;
        }
        var win = $.messager.progress({
            title : $.i18n.get('prompt'),
            msg : $.i18n.get('waitingMessage')
        });
        var params = $("form#pwdBox").serialize();
        var chgUrl = pwdFromLoginUser ? "changeOthersPassword.arz" : "changePassword.pub";
        $.ajax({
            type : 'post',
            url : chgUrl,
            data : params,
            success : function(resp, status, xhr) {
                $.messager.progress('close');
                if (!resp.success) {
                    $.messager.alert($.i18n.get('error'), resp.msg || $.i18n.get("prompt.exception"), 'error');
                    return;
                } else {
                    $.messager.alert($.i18n.get('change.password.success.title'), 
                            $.i18n.get("change.password.success.message"), 'info', function() {
                        window.location.href = "index.pub";
                    });
                }
            },
            error : function(xhr, status, error) {
                $.messager.progress('close');
                $.messager.alert($.i18n.get('error'), error || $.i18n.get("prompt.connection.exception"), 'error');
            }
        });
    }
});