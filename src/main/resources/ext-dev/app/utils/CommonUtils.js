/**
 * Created by Administrator on 2017/11/8.
 */
Ext.define('integration.utils.CommonUtils', {

    /**
     * 验证ajax请求返回值是否存在登录失效状态码，如果存在直接跳转到登录页面
     * @param obj
     */

    routerHandlers:[],
    app:null,

    ajaxLoginValidate:function(obj){
        var me = this;
        if(obj&&obj.loginStatus&&obj.loginStatus===403){
            this.notionDialog({
                title:'系统信息',
                msg:'你的登录已失效，请重新登录。',
                fn:function(){
                    me.changeAuthenticateStatus(false);
                    window.location.href=ctx+"/";
                }
            });
           /* var store = Ext.util.LocalStorage.get('sysInfo');
            store.setItem("authenticated","false");
            store.release();
            this.toast({
                iconCls:'fa-exclamation-triangle',
                content:'系统检测到您的登录失效，将重新登录。'
            });
            Ext.defer(function() {
                window.location.href=ctx+"/";
            }, 2000);*/
        }
    },

    ajaxAccessDeniedValidate:function (obj) {
        if(obj&&obj.accessDenied&&obj.accessDenied===1){
            this.confirmDialog({
                title:'系统信息',
                msg:'你无权访问该系统资源。是否需要重新登录？',
                fn:function(buttonId,value,opt){
                    if(buttonId=='yes'){
                        this.changeAuthenticateStatus();
                        window.location.href=ctx+"/logout";
                    }
                }
            });
        }
    },

    /**
     * 封装ajax请求工具,使得在每次请求之后，判断是否处于登录和授权状态
     * @param options
     */
    request:function(options){
        var me =this;
        var successFn = options.success,failureFn = options.failure,successWrapper,failureWrapper;
        successWrapper = function(response, opts){
            var obj = Ext.decode(response.responseText,true);
            if(obj===null)obj=response.responseText;
            me.ajaxLoginValidate(obj);
            me.ajaxAccessDeniedValidate(obj);
            if(successFn){
                successFn(obj,opts);
            }
        };
        failureWrapper = function (response, opts) {
            var obj = Ext.decode(response.responseText,true);
            me.ajaxLoginValidate(obj);
            me.ajaxAccessDeniedValidate(obj);
            if(failureFn){
                failureFn(obj,opts);
            }
        };
        options.success = successWrapper;
        options.failure = failureWrapper;
        Ext.Ajax.request(options);
    },

    toast:function(type,content){
        var iconCls = 'x-fa fa-info',color = 'white';
        if(type==='error'){
            color = 'red';
            iconCls = 'x-fa fa-times';
        }else if(type==='warning'){
            color = '#FFB800';
            iconCls = 'x-fa fa-exclamation-triangle';
        }else if(type==='success'){
            iconCls= 'x-fa fa-check';
            color = '#5FB878';
        }
        var html = '<p style="font-size:20px">' +
        '<i class="'+iconCls+'" aria-hidden="true" style="color:'+color+'"></i>' +
            '&nbsp;&nbsp;&nbsp;<span style="font-size:12px">'+content+'</span></p>';
        Ext.toast({
            html: html,
            baseCls:'toast-class',
            bodyBorder:false
        });
    },

    /**
     * 确认窗口
     * @param opts
     */
    confirmDialog:function(opts){
        Ext.MessageBox.confirm(opts.title, opts.msg, opts.fn);
    },

    /**
     * 通知dialog
     * @param opts
     */
    notionDialog:function(opts){
        Ext.MessageBox.show({
            title: opts.title,
            msg: opts.msg,
            buttons: Ext.MessageBox.YES,
            /*buttonText:{
                yes: '确认'
            },*/
            /*buttonTips: {
                yes: {
                    text: "We would't!",
                    anchor: true,
                    align: 't-b'
                },
                no: {
                    text: "Probably best!",
                    anchor: true,
                    align: 't-b'
                }
            },*/
            //scope: this,
            fn: opts.fn

        });
    },

    changeAuthenticateStatus:function (status) {
        var store = Ext.util.LocalStorage.get('sysInfo');
        store.setItem("authenticated",status?"true":"false");
        store.release();
    },

    getAuthenticateStatus:function () {
        var store = Ext.util.LocalStorage.get('sysInfo');
        var isAuth = store.getItem("authenticated");
        store.release();
        return isAuth;
    },

    setRouterHandler:function (handler,index) {
        if(index&&Ext.isNumber(index)){
            this.routerHandlers.splice(index,0,handler);
        }else{
            this.routerHandlers.push(handler);
        }
    },

    setRouterHandlers:function(handlers){
        this.routerHandlers = handlers;
    },

    routerMatch:function(hashTag){
        var handlers =this.routerHandlers,result;
        for(var i=0;i<handlers.length;i++){
            result = handlers[i].match(hashTag);
            if(result&&result.match){
                return result;
            }
        }
        return false;
    }
});