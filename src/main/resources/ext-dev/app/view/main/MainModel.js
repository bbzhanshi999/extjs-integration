/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('integration.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',


    data: {
        user: {},
        menus: [],
        headerMenus: [],
        leftTabBarIsHidden: true,
        leftPanelIsHidden: false,
        headerIsHidden: false,
        mainTabBarIsHidden: false,
        footBarIsHidden:false
    },


    formulas: {
        // We'll explain formulas in more detail soon.
        leftBarShowTogglerBtnIconCls: function (get) {
            var isHIdden = get('leftTabBarIsHidden');
            return isHIdden ? 'x-fa fa-maxcdn' : 'x-fa fa-bars';
        },
        leftPanelTogglerBtnIconCls: function (get) {
            var isHIdden = get('leftPanelIsHidden');
            return isHIdden ? 'pictos pictos-right' : 'pictos pictos-left';
        },
        isFullScreen: function (get) {
            var headerIsHidden = get('headerIsHidden');
            var leftPanelIsHidden = get('leftPanelIsHidden');
            var mainTabBarIsHidden = get('mainTabBarIsHidden');
            var footBarIsHidden = get('footBarIsHidden');
            return !!(headerIsHidden && leftPanelIsHidden && mainTabBarIsHidden&&footBarIsHidden);

        },
        fullScreenBtnText: function (get) {
            return get('isFullScreen') ? 'x-fa fa-minus-square' : 'x-fa fa-arrows-alt';
        }
    }


    //TODO - add data, formulas and/or methods to support your view
});
