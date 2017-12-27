/**
 * Created by Administrator on 2017/10/24.
 */
Ext.define('integration.view.auth.LoginForm', {
    extend: 'Ext.form.Panel',
    xtype: 'form-login',

    title: '登陆',
    frame: true,
    width: 320,
    bodyPadding: 10,

    defaultType: 'textfield',


    defaults: {
        anchor: '100%',
        labelWidth: 120
    }
});