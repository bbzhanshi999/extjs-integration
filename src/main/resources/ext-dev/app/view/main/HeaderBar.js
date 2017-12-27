/**
 * 顶部栏
 * Created by Administrator on 2017/11/13.
 */
Ext.define('integration.view.main.HeaderBar', {
    extend: 'Ext.toolbar.Toolbar',

    xtype: 'headerBar',
    height: 64,
    cls: 'header-bar',
    defaults: {
        enableToggle: true,
        toggleGroup: 'lv1-menu',
        width: 124,
        cls: ''
    },
    config:{
        menus:[]
    },

    setMenus:function(menus){
        debugger;
        var me = this;
        this.menus = menus;
        try{
            this.removeAll(true,true);
        }catch (e){
            console.warn(e);
        }
        var headerMenus = _.sortBy(menus, [function(menu) { return menu.sort; }]);
        _.forEach(headerMenus,function(menu){
            me.add({
                ui: 'header',
                iconCls:menu.iconCls,
                text:menu.menuName,
                id: 'main-navigation-btn-'+menu.id,
                href: '#'+menu.url,
                hrefTarget: '_self'
            })
        });
        console.log(me.items);
    },

    listeners: {
        menusChange: function (menus) {
            var me = this;
            try{
                this.removeAll(true,true);
            }catch (e){
                console.error(e);
            }
            var headerMenus = _.sortBy(menus, [function(menu) { return menu.sort; }]);
            _.forEach(headerMenus,function(menu){
                me.add({
                    ui: 'header',
                    iconCls:menu.iconCls,
                    text:menu.menuName,
                    id: 'main-navigation-btn-'+menu.id,
                    href: '#'+menu.url,
                    hrefTarget: '_self'
                })
            })
        }
    },
    /*tpl:[
     '<tpl for=".">',
     '<span>{#}. {menuName}</span>',
     '</tpl>'
     ],*/


    items: []
});