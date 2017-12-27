/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('integration.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    listen: {
        controller: {
            '#': {
                unmatchedroute: 'onRouteChange'
            },
            /*'*': {
                changeAuthenticate: 'onLoginSuccess'
            }*/
        }
    },

    routes: {
        ':node': 'onRouteChange'
    },

    lastView: null,
    /**
     * 通过hashTag同步页面的内容，创建或切换显示内容
     * @param hashTag
     */
    setCurrentView: function (hashTag) {
        debugger;
        hashTag = hashTag || '';
        var me = this,mainCardPanel = this.getReferences().mainCardPanel,pageTabs=mainCardPanel.getLayout().getLayoutItems()
            ,viewModel = this.getViewModel(),leftPanel = this.getReferences().leftMenus,leftTabs = leftPanel.getLayout().getLayoutItems();
        var isAuth = Ext.commonUtil.getAuthenticateStatus(),rs,target;

        //如果上一个route是窗口页面，那么就销毁
        if (me.lastView && me.lastView.isWindow) {
            me.lastView.destroy();
        }

        //如果未登录或者无权限，直接跳转到登录页面
        if (hashTag === 'login' || !isAuth || isAuth === 'false') {
            if (hashTag === 'login') {
                me.lastView = Ext.create({
                    xtype: 'login',
                    routeId: 'login',  // for existingItem search later
                    hideMode: 'offsets'
                });
            } else {
                Ext.toast('<i class="x-fa fa-exclamation-triangle" aria-hidden="true"></i>系统检测到您还未登录，请重新登录');
                Ext.defer(function () {
                    me.lastView = Ext.create({
                        xtype: 'login',
                        routeId: 'login',  // for existingItem search later
                        hideMode: 'offsets'
                    });
                }, 2000);
            }
        } else {//如果已经登录,
            rs = Ext.commonUtil.routerMatch(hashTag);

            //如果未匹配到合适结果，就返回404页面
            if(!rs){
                //todo
            }


            var left =rs.left;
            target = mainCardPanel.getComponent(rs.id);
            if(target){
                mainCardPanel.setActiveTab(target);
            }else{
                mainCardPanel.add(rs.target);
                mainCardPanel.setActiveTab(rs.id);
            }

            if(left){
                if(leftPanel.getComponent(left.id)){
                    leftPanel.setActiveTab(left.id);
                }else{
                    leftPanel.add(left.target);
                    leftPanel.setActiveTab(left.id);
                }
                viewModel.set('leftPanelIsHidden',false);
            }else{
                viewModel.set('leftPanelIsHidden',true);
            }
        }


    },
    onRouteChange: function (id) {
        this.setCurrentView(id);
    },

    onMainViewRender: function () {
        var me = this;
        debugger;
        Ext.Ajax.request({
            url: ctx + '/isAuthenticated',
            method: 'POST',
            async: false,
            success: function (response, opts) {
                var obj = Ext.decode(response.responseText, true);
                if (obj && obj.loginStatus && obj.loginStatus === 403) {
                    Ext.commonUtil.changeAuthenticateStatus();
                    me.redirectTo('login');
                } else {
                    Ext.commonUtil.changeAuthenticateStatus(true);
                    me.loadData();
                    if (!window.location.hash) {
                        me.redirectTo('index');
                    }
                }

            },
            failure: function (response, opts) {
                Ext.commonUtil.notionDialog({
                    title: '系统信息',
                    msg: '你的登录已失效，请重新登录。',
                    fn: function () {
                        Ext.commonUtil.changeAuthenticateStatus();
                        me.redirectTo('login', true);
                    }
                });
            }
        });

    },

    /**
     * 测试用
     */
    onTest: function () {
     /*   console.log(this.getViewModel().get('headerMenus'));
        console.log(this.getViewModel().get('menus'));
        console.log(this.getViewModel().get('user'));*/
        Ext.commonUtil.request({
            url: ctx + '/test/testMethod1',
            method: 'POST',
            success: function (data, opts) {
                console.log(data);
            }
        });
    },

    onLogout: function () {
        Ext.MessageBox.confirm('退出登录', '你确定退出登录吗?', this.logout);
    },

    logout: function (buttonId, value, opt) {
        if (buttonId == 'yes') {
            Ext.Ajax.request({
                url: ctx + '/logout',
                method: 'POST',
                success: function (response, opts) {
                    Ext.commonUtil.changeAuthenticateStatus();
                    window.location.href = ctx + "/";
                },
                failure: function (response, opts) {
                    var obj = Ext.decode(response.responseText, true);
                    Ext.toast('<i class="x-fa fa-exclamation-triangle" aria-hidden="true"></i>系统错误，退出失败！');
                }
            });
        } else {

        }

    },


    onLoginSuccess: function (isAuth) {
        /*   debugger;
         var menutoolBar = this.getViewModel().get('menustoolbar');
         this.getView().insert(0,menutoolBar);*/
    },

    /**
     * 登录成功后，装载数据headerBar
     */
    loadData: function () {
        var me = this, viewModel = me.getViewModel();
        Ext.commonUtil.request({
            url: ctx + '/sys/userInfo',
            method: 'POST',
            success: function (data, opts) {
                viewModel.set('user', data);
                var user = viewModel.get('user'), menus = user.menus;
                me.initheaderBar(menus);

            }
        });
    },

    /**
     * 初始化菜单
     */
    initheaderBar: function (menus) {
        var viewModel = this.getViewModel(), headBar = this.lookup('headerBar');
        viewModel.set('menus', menus);
        var headMenu = _.filter(menus,function(menu){
            return menu.type=="0";
        });
        viewModel.set('headerMenus', headMenu);
      //  headBar.fireEvent("menusChange",headMenu);
    },

    toggleLeftTabBar: function () {
        var viewModel = this.getViewModel();
        viewModel.get('leftTabBarIsHidden')?viewModel.set('leftTabBarIsHidden',false):viewModel.set('leftTabBarIsHidden',true);

    },
    toggleScreenSize: function () {
        var viewModel = this.getViewModel();
        if(viewModel.get('isFullScreen')){
            viewModel.set('leftPanelIsHidden',false);
            viewModel.set('headerIsHidden',false);
            viewModel.set('mainTabBarIsHidden',false);
            viewModel.set('footBarIsHidden',false);
        }else{
            viewModel.set('leftPanelIsHidden',true);
            viewModel.set('headerIsHidden',true);
            viewModel.set('mainTabBarIsHidden',true);
            viewModel.set('footBarIsHidden',true);
        }
    },

    toggleLeftPanel: function () {
        var viewModel = this.getViewModel();
        viewModel.get('leftPanelIsHidden')?viewModel.set('leftPanelIsHidden',false):viewModel.set('leftPanelIsHidden',true);
    },

    onLeftPanelLayout:function(){
        var leftPanel = this.getReferences().leftMenus,tabs = leftPanel.getLayout().getLayoutItems()
            ,viewModel = this.getViewModel();
        if(!tabs||tabs.length===0){
            viewModel.set('leftPanelIsHidden',true);
        }else if(viewModel.get('leftPanelIsHidden')){
            viewModel.set('leftPanelIsHidden',false);
        }
    }
});
