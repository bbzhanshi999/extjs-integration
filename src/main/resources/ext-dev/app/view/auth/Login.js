/**
 * 登录页面
 * Created by Administrator on 2017/10/24.
 */
Ext.define('integration.view.auth.Login', {
    extend: 'Ext.window.Window',
    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    },
    requires: [
        'integration.view.auth.LoginForm'
    ],
    xtype: 'login',
    controller: 'login',
    closable: false,
    cls: 'login-window',
    resizable: false,
    autoShow: true,
    maximized: true,
    //header:false,
    titleAlign:'center',
    title:'<h2>tycho system</h2>',
    modal: true,
    onEsc: Ext.emptyFn,
    keyMap: {
        ENTER: 'onEnterKey'
    },
    items: [
        {
            xtype: 'form-login',
            items: [{
                reference: 'username',
                allowBlank: false,
                fieldLabel: '用户名',
                name: 'username',
                emptyText: '用户名'
            }, {
                reference: 'password',
                allowBlank: false,
                fieldLabel: '密码',
                name: 'password',
                emptyText: '密码',
                inputType: 'password'
            }],
            buttons: [
                {
                    text: '登陆',
                    listeners: {
                        click: 'onLoginButton'
                    }
                }
            ]
        }
    ]
});