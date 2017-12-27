/**
 * Created by Administrator on 2017/11/13.
 */
Ext.define('integration.view.main.HeaderContainerWrap', {
    extend: 'Ext.container.Container',


    xtype:'headercontainerWrap',
    height:65,
    cls:'header-wrap shadow',
    layout: {
        type: 'hbox',
        align: 'stretch',

        // Tell the layout to animate the x/width of the child items.
        animate: true,
        animatePolicy: {
            x: true,
            width: true
        }
    },

});