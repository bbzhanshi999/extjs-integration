/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */
Ext.application({
    name: 'integration',

    extend: 'integration.Application',

    requires: [
        'integration.*'
    ],

    init:function(application){
        //装载工具类
        Ext.commonUtil = Ext.create('integration.utils.CommonUtils');
        Ext.commonUtil.app = application;

        //组装routehandler
        var routerHandlers = [];
        routerHandlers.push(Ext.create('integration.routehandler.IndexHandler'));
        Ext.commonUtil.setRouterHandlers(routerHandlers);
        Ext.routerHandlers= routerHandlers;
        this.callParent(arguments);
    },
    // The name of the initial view to create. With the classic toolkit this class
    // will gain a "viewport" plugin if it does not extend Ext.Viewport. With the
    // modern toolkit, the main view will be added to the Viewport.
    //
    mainView: 'integration.view.main.Main'
    //mainView: 'integration.view.auth.Login'


    //-------------------------------------------------------------------------
    // Most customizations should be made to integration.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------
});
