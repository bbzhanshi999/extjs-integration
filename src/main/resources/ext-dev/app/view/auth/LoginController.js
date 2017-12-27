/**
 *
 * Created by Administrator on 2017/10/25.
 */
Ext.define('integration.view.auth.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    id:'login',
    /**
     * Called when the view is created
     */
    init: function () {

    },

    onEnterKey:function(){
        this.onLoginButton();
    },

    onLoginButton: function (btn) {
        var me = this,
            refs = me.getReferences(),
            usertext = refs.username,
            passwordtext = refs.password,
            params = {username: usertext.getValue(), password: passwordtext.getValue()};
        Ext.Ajax.request({
            url: ctx + '/loginUser',
            method: 'POST',
            params: params,
            success: function (response, opts) {
                debugger;
                var obj = Ext.decode(response.responseText,true);
                if (1 === obj.status) {
                    Ext.commonUtil.toast('success','登陆成功，等待跳转页面...');
                    //Ext.toast('<i class="x-fa fa-info" aria-hidden="true"></i>登陆成功，等待跳转页面...');
                   /* btn.setDisabled(true);
                    store.setItem("authenticated","true");
                    store.release();
                    me.fireEvent("changeAuthenticate",true);
                    me.getView().destroy();
                    me.redirectTo('index', true);*/
                    window.location.href = ctx+'/';
                } else {
                    /*usertext.setValue('');
                    passwordtext.setValue('');*/
                    Ext.commonUtil.changeAuthenticateStatus();
                    Ext.commonUtil.toast('error','用户名或密码错误，请重新输入！');
                    //Ext.toast('<i class="x-fa fa-exclamation-triangle" aria-hidden="true"></i>用户名或密码错误，请重新输入！');
                    me.getView().destroy();
                    me.redirectTo('login', true);
                }
            },

            failure: function (response, opts) {
                console.log('server-side failure with status code ' + response.status);
            }
        })
    },


});