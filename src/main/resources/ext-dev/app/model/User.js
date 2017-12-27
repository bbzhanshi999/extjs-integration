/**
 * Created by Administrator on 2017/11/9.
 */
Ext.define('integration.model.User', {
    extend: 'integration.model.BaseModel',

    fields: [
        { name: 'name',     type: 'string' },
        { name: 'age',      type: 'int' },
        { name: 'sexual',    type: 'string' },
        { name: 'address',   type: 'string' },
        { name: 'phone', type: 'string' },
        { name: 'email', type: 'string' },
        { name: 'username', type: 'string' }
    ],
    hasMany:'Menu',


    /*proxy: {
        type: 'ajax',
        url : ctx+'/sys/userInfo',
        reader: {
            type: 'json'
        },
        autoLoad:true
    }*/

    /*
    Uncomment to add validation rules
    validators: {
        age: 'presence',
        name: { type: 'length', min: 2 },
        gender: { type: 'inclusion', list: ['Male', 'Female'] },
        username: [
            { type: 'exclusion', list: ['Admin', 'Operator'] },
            { type: 'format', matcher: /([a-z]+)[0-9]{2,3}/i }
        ]
    }
    */

    /*
    Uncomment to add a rest proxy that syncs data with the back end.
    proxy: {
        type: 'rest',
        url : '/users'
    }
    */
});