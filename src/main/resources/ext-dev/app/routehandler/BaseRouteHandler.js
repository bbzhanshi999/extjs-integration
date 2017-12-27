/**
 * 路由handler，通过路由地址返回匹配的页面对象的xtype，并根据restful风格url解析，返回创建页面时传入的参数json对象
 * Created by Administrator on 2017/12/5.
 */
Ext.define('integration.routehandler.BaseRouterHandler', {

    /**
     * 匹配路由，返回对象模板{match:true,id:'xxx',xtype:'xxx',params:{....},params:{...},left:{id:'yyy',xtype:'',target:{...},params:{...}}/false}
     * @param hashTag url
     * @param application 全局app，供调用相关数据进行辅助判断
     */
    match: function (hashTag,application) {
        return {match:false};
    },

    idGen:function (url,prefix) {
        return Ext.String.trim(url)?prefix+url.replace(/\//g,'-'):null;
    }



});