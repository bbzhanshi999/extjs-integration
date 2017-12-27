/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('integration.view.main.Main', {
    extend: 'Ext.container.Viewport',
    xtype: 'app-main',

    requires: [
        'Ext.window.MessageBox',
        'integration.view.main.MainController',
        'integration.view.main.MainModel',
        'integration.view.main.List',
        'Ext.ux.TabReorderer',
        'integration.model.User'
    ],

    controller: 'main',
    viewModel: 'main',

    //ui: 'navigation',

    keyMap: {
        "Shift+U": 'toggleLeftTabBar',
        "Shift+Y": 'toggleLeftPanel',
        "ESC":'toggleScreenSize'
    },

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    itemId: 'mainView',

    listeners: {
        render: 'onMainViewRender'
    },

    items: [
        {
            xtype: 'headercontainerWrap',
            itemId: 'header',
            reference: 'header',
            bind:{
                hidden:'{headerIsHidden}'
            },
            items: [
                {
                    xtype: 'component',
                    reference: 'logo',
                    cls: 'logo',
                    html: '<div class="main-logo">' +
                    '<img src="' + ctx + '/ext-dev/build/development/integration/resources/images/logo.png">' +
                    //'<img src="resources/images/logo.png">' +
                    'tycho system</div>',
                    width: 250
                },
                {
                    xtype: 'headerBar',
                    reference: 'headerBar',
                    flex: 9,
                    bind: {
                        menus: '{headerMenus}'
                    }
                },
                {
                    xtype: 'toolbar',
                    cls: 'header-user',
                    height: 64,
                    //flex: 1,
                    items: [
                        '-',
                        {

                            iconCls: 'x-fa fa-user',
                            ui: 'user-btn',
                            enableToggle: false,
                            toggleGroup: 'user-btn',
                            //text: '赵千里',
                            bind: {
                                text: '{user.username}'
                            },
                            menu: [
                                {
                                    text: '测试',
                                    handler: 'onTest'
                                }, {
                                    text: '退出系统',
                                    handler: 'onLogout'
                                },
                            ]
                        }
                    ]
                }
            ]
        },
        {
            type: 'maincontainerwrap',
            layout: 'border',
            defaults: {
                collapsible: true,
                bodyPadding: 10
            },
            id: 'main-view-detail-wrap',
            reference: 'mainContainerWrap',
            flex: 1,
            items: [
                {
                    xtype: 'leftPanel',
                    reference: 'leftMenus',
                    region: 'west',
                    cls:'left-panel',
                    itemId: 'leftMenuPanel',
                    collapseMode: 'mini',
                    margin: '0 0 0 0',
                    listeners:{
                        afterlayout: 'onLeftPanelLayout'
                    },
                    bind:{
                        hidden:'{leftPanelIsHidden}'
                    },
                    tabBar: {
                        bind:{
                            hidden: '{leftTabBarIsHidden}'
                        }
                    },
                    items: [
                        {
                            id: 'left-wheelset_aaa',
                            title: 'left Tab 1',
                            html: 'The tabs in this example are reorderable. Drag any tab to change the order.'
                        }, {
                            title: 'left Tab 2',
                            html: "xsadasd"
                        }
                    ]
                },
                {
                    xtype: 'tabpanel',
                    reference: 'mainCardPanel',
                    itemId: 'contentPanel',
                    region: 'center',
                    collapsible: false,
                    plugins: 'tabreorderer',
                    ui: 'maintab',
                    flex: 1,
                    tabBar: {
                        bind:{
                            hidden:'{mainTabBarIsHidden}'
                        }
                    },
                    minTabWidth: 100,
                    cls: 'main-tab',
                    defaults: {
                        bodyPadding: 10,
                        scrollable: true,
                        closable: true,
                    },
                    items: [ {
                        title: '首页',
                        html: "I can't be moved",
                        id:"page-index",
                        width: 250,
                        reorderable: false,
                        closable: false
                    }]
                }
            ]
        },{
            xtype: 'toolbar',
            cls: 'bottom-tool',
            height: 24,
            bind:{
                hidden:'{footBarIsHidden}'
            },
            items: [{
                bind: {
                    iconCls: '{leftBarShowTogglerBtnIconCls}'
                },
                reference: 'expandButton',
                tooltip: 'Shift+U:显示/隐藏左侧标签栏',
                xtype: 'button',
                handler: 'toggleLeftTabBar'
            }, '-', {
                bind:{
                    iconCls: '{leftPanelTogglerBtnIconCls}'
                },
                xtype: 'button',
                tooltip: 'Shift+Y:显示/隐藏左侧所有标签',
                handler: 'toggleLeftPanel'
            }, '-', {
                bind:{
                    iconCls:'{fullScreenBtnText}',
                },
                xtype: 'button',
                tooltip: 'ESC:全屏切换',
                handler: 'toggleScreenSize'
            }]
        }
    ]
});
