Ext.define('Admin.view.main.MainContainerWrap', {
    extend: 'Ext.container.Container',
    xtype: 'maincontainerwrap',

    requires : [
        'Ext.layout.container.HBox'
    ],
    scrollable: 'y',
   /* scrollable: 'y',

    layout: {
        type: 'hbox',
        align: 'stretchmax',

        // Tell the layout to animate the x/width of the child items.
        animate: true,
        animatePolicy: {
            x: true,
            width: true
        }
    },*/


    beforeLayout : function() {
        // We setup some minHeights dynamically to ensure we stretch to fill the height
        // of the viewport minus the top toolbar
        debugger;
        var me = this;  // offset by topmost toolbar height
        me.height = Ext.Element.getViewportHeight() - 89;
        me.callParent(arguments);
    }
});
