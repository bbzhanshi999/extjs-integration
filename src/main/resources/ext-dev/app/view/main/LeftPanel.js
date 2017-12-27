/**
 * Created by Administrator on 2017/11/16.
 */
Ext.define('integration.view.main.LeftPanel', {
    extend: 'Ext.tab.Panel',

    requires: [
        'Ext.ux.TabReorderer',
        'integration.*'
    ],


    xtype:'leftPanel',
    header: false,
    plugins: 'tabreorderer',
    tabPosition: 'left',
    width: 250,
    cls: 'main-tab',
    animCollapse: true,
    ui: 'lefttab',
    defaults: {
        bodyPadding: 10,
        scrollable: true,
        closable: true
    },

    afterrender:function(){

    }

});